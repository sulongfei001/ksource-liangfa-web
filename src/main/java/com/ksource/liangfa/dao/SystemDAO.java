package com.ksource.liangfa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ksource.common.bean.DatabaseDialectBean;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PaginationContext;
import com.ksource.syscontext.SpringContext;


@Component("systemDAO")
public class SystemDAO extends SqlSessionDaoSupport {
    @Autowired
    public SystemDAO(SqlSessionFactory sqlSessionFactory) {
        this.setSqlSessionFactory(sqlSessionFactory);
    }

    /**
     * mybatis分页插件测试
     */
    @Deprecated
    public void testPage() {
    	System.out.println(
    			this.getSqlSession().selectList("testPage",null,new RowBounds(10, 20))
    			);
    }
    
    /**
     * 获得数据库(oracle)序列的下一个值
     * @param seqName
     * @return
     */
    public int getSeqNextVal(String tableName) {
    	String dialect=((DatabaseDialectBean)SpringContext.
    			getApplicationContext().
    			getBean("databaseConfigBean")).getDialect();
    	String selectId="systemDAO.getSeqNextValByOracle";
    	if(Const.DIALECT_MYSQL.equals(dialect)){
    		selectId="systemDAO.getSeqNextValByMysql";
    	}
        return (Integer) this.getSqlSession()
                             .selectOne(selectId, tableName);
    }

    /**
    * 内存分页。
    * 在查询之前对每个条件进行了字符串两边去空处理。
    * 比如：
    * <pre>
    * String con = "  condition ";
    * con.trim();
    * </pre>
    * @param <T>
    * @param domain
    * @return
    */
    public <T> List<T> find(T domain,Map<String,Object> paramMap) {
    	Map<String,Object> map = domainToMap(domain);
    	if(paramMap!=null&&!paramMap.isEmpty()){
    		map.putAll(paramMap);
    	}
        return getList(map,domain,null);
    }
    /**
     * 内存分页。
     * 在查询之前对每个条件进行了字符串两边去空处理。
     * 比如：
     * <pre>
     * String con = "  condition ";
     * con.trim();
     * </pre>
     * @param <T>
     * @param domain
     * @return
     */
     public <T> List<T> find(T domain) {
         return getList(domainToMap(domain),domain,null);
     }

    /**
     * 条件领域类和返回集合内的领域类一样
     * @param <T>　　保存查询条件的对象，也是用于保存单条查询记录 的对象
     * @param domain
     * @param page
     * @return
     */
    public <T> PaginationHelper<T> find(T domain, String page) {
        //数据库分页对象
        PaginationContext<T> helper = new PaginationContext<T>();

        Map<String, Object> map = domainToMap(helper,domain,page);
        String simpleName = domain.getClass().getSimpleName();

        return helper.getPaginationList(getCount(map, simpleName,null),
            (List<T>) getList(map,domain,null));
    }

    public <T> PaginationHelper<T> find(T domain, String page,
        Map<String, Object> map) {
        //数据库分页对象
        PaginationContext<T> helper = new PaginationContext<T>();

        Map<String, Object> domainMap = domainToMap(helper, domain,page);

        if(map !=null){
        	domainMap.putAll(map);
        }

        String simpleName = domain.getClass().getSimpleName();

        return helper.getPaginationList(getCount(domainMap, simpleName,null),
            getList(domainMap,domain,null));
    }

    /**
     * 条件领域类和返回集合内的领域类不一样
     * @param <T>　　保存查询条件的对象
     * @param <R>　　用于保存单条查询记录的对象
     * @param domain
     * @param page
     * @param returnType
     * @return
     */
    public <T, R> PaginationHelper<R> find(T domain, String page, R returnType) {
        //数据库分页对象
        PaginationContext<R> helper = new PaginationContext<R>();

        Map<String, Object> map = helper.getConditionMap(domain, page);
        String simpleName = returnType.getClass().getSimpleName();

        return helper.getPaginationList(getCount(map, simpleName,null),
            getList(map,returnType,null));
    }

    public int getCount(Map<String, Object> map, String simpleName,String methodId) {
    	String sqlMapId;
    	if(methodId!=null&&methodId.contains(".")){//如果传进来的是全路径(如果有"."则认为是全路径)
    		sqlMapId = methodId;
    	}else{//如果传进来的是id,则用domain解析全路径CaseBasicMapper
    		String id = methodId==null?"getPaginationCount":methodId;
        	sqlMapId = "com.ksource.liangfa.mapper." +
        	         simpleName + "Mapper."+id;
    	}
        Integer userCount = (Integer) getSqlSession().selectOne(sqlMapId, map);
        return userCount;
    }

    public <T> List<T> getList(Map<String, Object> map,T domain,String methodId) {
    	String sqlMapId;
    	if(methodId!=null&&methodId.contains(".")){//如果传进来的是全路径(如果有"."则认为是全路径)
    		sqlMapId = methodId;
    	}else{//如果传进来的是id,则用domain解析全路径
    		String id = methodId==null?"getPaginationList":methodId;
        	String simpleName = domain.getClass().getSimpleName();
        	sqlMapId = "com.ksource.liangfa.mapper." +
        	         simpleName + "Mapper."+id;
    	}
    	RowBounds rowBounds=null;
    	List<T> list=null;
    	if(map.containsKey("start")&&map.containsKey("end")){
    		int limit = Integer.parseInt(map.get("end").toString())-Integer.parseInt(map.get("start").toString());
    		rowBounds=new RowBounds(Integer.parseInt(map.get("start").toString()),limit);
    		list = getSqlSession()
                    .selectList(sqlMapId, map,rowBounds);

    	}else{
    		list = getSqlSession()
                    .selectList(sqlMapId, map);
    	}
        
        return list;
    }

    /**
     * 把domain类转换成map.<br/>
     * map里的键/值对应domain类中的属性/属性值.
     * @param <T>
     * @param domain
     * @return
     */
    private <T> Map<String, Object> domainToMap(T domain) {
        return domainToMap(new PaginationContext<T>(), domain);
    }

    private <T> Map<String, Object> domainToMap(PaginationContext<T> helper,
        T domain) {
        return helper.getConditionMap(domain);
    }
    private <T> Map<String, Object> domainToMap(PaginationContext<T> helper,
            T domain,String page) {
            return helper.getConditionMap(domain,page);
        }

	public <T> List<T> find(Class<T> class1, Map<String, Object> map,int start,int limit,String methodId) {
		String simpleName = class1.getSimpleName();
    	RowBounds rowBounds=null;
    	List<T> list=null;
    	rowBounds=new RowBounds(start,limit);
    		list = getSqlSession()
                    .selectList("com.ksource.liangfa.mapper." +
         simpleName + "Mapper."+methodId, map,rowBounds);
        return list;
	}
	public <T> PaginationHelper<T> find(T domain, Map<String, Object> paramMap,String page,String countMethodId,String listMethodId) {
		//数据库分页对象
        PaginationContext<T> helper = new PaginationContext<T>();

        Map<String, Object> map = domainToMap(helper,domain,page);
        if(paramMap!=null&&!paramMap.isEmpty()){
        	map.putAll(paramMap);
        }
        String simpleName = domain.getClass().getSimpleName();

        return helper.getPaginationList(getCount(map,simpleName,countMethodId),
            (List<T>) getList(map,domain,listMethodId));
	}
	public <T> PaginationHelper<T> find(Map<String, Object> paramMap,String page,String countMethodId,String listMethodId) {
		//数据库分页对象
        PaginationContext<T> helper = new PaginationContext<T>();

        Map<String, Object> map = domainToMap(helper,null,page);
        if(paramMap!=null&&!paramMap.isEmpty()){
        	map.putAll(paramMap);
        }

        return helper.getPaginationList(getCount(map,null,countMethodId),
            (List<T>) getList(map,null,listMethodId));
	}
	
	public void reflashOrgDate(){
		this.getSqlSession().selectList("systemDAO.reflashOrgDate");
	}
}
