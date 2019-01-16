package com.ksource.liangfa.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MethodInvoker;
import com.ksource.syscontext.SpringContext;

/**
 * 封装mybatis插件生成的mapper的模板方法，加入service层事物<br>
 * 注意：并不是所有mapper都有这些方法,带有"WithBLOBs"的方法只适用domain字段里包含clob或blob数据类型的mapper
 * @author gengzi
 *
 */
@Service("MybatisAutoMapperService")
public class MybatisAutoMapperService {
	
	/**
	 *  调用mapper的countByExample
	 * @param mapperClass Mybatis自动生成的Mapper class
	 * @param example Mybatis自动生成的Example对象
	 * @return
	 */
	@Transactional(readOnly=true)
	public int countByExample(Class<?> mapperClass,Object example){
		int result = (Integer)invokeMapperMethod(mapperClass, "countByExample", new Object[]{example});
		return result;
	}

	/**
	 *  调用mapper的deleteByExample
	 * @param mapperClass Mybatis自动生成的Mapper class
	 * @param example Mybatis自动生成的Example对象
	 * @return
	 */
	@Transactional
	public int deleteByExample(Class<?> mapperClass,Object example){
		int result = (Integer)invokeMapperMethod(mapperClass, "deleteByExample", new Object[]{example});
		return result;
	}

	/**
	 *  调用mapper的deleteByPrimaryKey
	 * @param mapperClass Mybatis自动生成的Mapper class
	 * @param key 实体的主键，如果是联合主键，传入主键对象
	 * @return
	 */
	@Transactional
	public int deleteByPrimaryKey(Class<?> mapperClass,Object key){
		int result = (Integer)invokeMapperMethod(mapperClass, "deleteByPrimaryKey", new Object[]{key});
		return result;
	}

	/**
	 *  调用mapper的insert
	 * @param mapperClass Mybatis自动生成的Mapper class
	 * @param record 实体对象(Mybatis生成的domain)
	 * @return
	 */
	@Transactional
	public int insert(Class<?> mapperClass,Object record){
		int result = (Integer)invokeMapperMethod(mapperClass, "insert", new Object[]{record});
		return result;
	}

	/**
	 *  调用mapper的insertSelective：插入全部非空字段
	 * @param mapperClass Mybatis自动生成的Mapper class
	 * @param record 实体对象(Mybatis生成的domain)
	 * @return
	 */
	@Transactional
	public <T> int insertSelective(Class<?> mapperClass,Object record){
		int result = (Integer)invokeMapperMethod(mapperClass, "insertSelective", new Object[]{record});
		return result;
	}
	
	/**
	 * 调用mapper的selectByExampleWithBLOBs方法，查询结果包含BLOB和CLOB类型的字段
	 * @param <T> 
	 * @param domainClass 实体对象(Mybatis生成的domain)
	 * @param example Mybatis自动生成的Example对象
	 * @return
	 */
	@Transactional(readOnly=true)
	public <T> List<T> selectByExampleWithBLOBs(Class<?> mapperClass,Object example, Class<T> domainClass){
		@SuppressWarnings("unchecked")
		List<T> result = (List<T>)invokeMapperMethod(mapperClass, "selectByExampleWithBLOBs", new Object[]{example});
		return result;
	}

	/**
	 * 调用mapper的selectByExample：根据Example查询（查询结果不包含BLOB和CLOB类型的字段--如果有的话）
	 * @param <T>
	 * @param domainClass 实体对象(Mybatis生成的domain)
	 * @param example Mybatis自动生成的Example对象
	 * @return
	 */
	@Transactional(readOnly=true)
	public <T> List<T> selectByExample(Class<?> mapperClass,Object example, Class<T> domainClass){
		@SuppressWarnings("unchecked")
		List<T> result = (List<T>)invokeMapperMethod(mapperClass, "selectByExample", new Object[]{example});
		return result;
	}

	/**
	 * 调用mapper的selectByPrimaryKey：根据主键查询（查询结果包含全部字段）
	 * @param domainClass 实体对象(Mybatis生成的domain)
	 * @param key 实体的主键，如果是联合主键，传入主键对象
	 * @param domainClass 实体对象(Mybatis生成的domain)
	 * @return
	 */
	@Transactional(readOnly=true)
	public <T> T selectByPrimaryKey(Class<?> mapperClass,Object key,Class<T> domainClass){
		@SuppressWarnings("unchecked")
		T result = (T)invokeMapperMethod(mapperClass, "selectByPrimaryKey", new Object[]{key});
		return result;
	}

	/**
	 * 调用mapper的updateByExampleSelective：更新全部非空的字段
	 * @param domainClass 实体对象(Mybatis生成的domain)
	 * @param record 实体对象(Mybatis生成的domain)
	 * @param example Mybatis自动生成的Example对象
	 * @return
	 */
	@Transactional
	public int updateByExampleSelective(Class<?> mapperClass,Object record,Object example){
		int result = (Integer)invokeMapperMethod(mapperClass, "updateByExampleSelective", new Object[]{record,example});
		return result;
	}

	/**
	 * 调用mapper的updateByExampleWithBLOBs：更新全部字段（包含BLOB和CLOB字段）
	 * @param domainClass 实体对象(Mybatis生成的domain)
	 * @param record 实体对象(Mybatis生成的domain)
	 * @param example Mybatis自动生成的Example对象
	 * @return
	 */
	@Transactional
	public int updateByExampleWithBLOBs(Class<?> mapperClass,Object record,Object example){
		int result = (Integer)invokeMapperMethod(mapperClass, "updateByExampleWithBLOBs", new Object[]{record,example});
		return result;
	}

	/**
	 * 调用mapper的updateByExampleWithBLOBs：根据Example更新全部字段（不包含BLOB和CLOB字段--如果有的话）
	 * @param domainClass 实体对象(Mybatis生成的domain)
	 * @param record 实体对象(Mybatis生成的domain)
	 * @param example Mybatis自动生成的Example对象
	 * @return
	 */
	@Transactional
	public int updateByExample(Class<?> mapperClass,Object record,Object example){
		int result = (Integer)invokeMapperMethod(mapperClass, "updateByExample", new Object[]{record,example});
		return result;
	}

	/**
	 * 调用mapper的updateByPrimaryKeySelective：更新全部非空字段
	 * @param domainClass 实体对象(Mybatis生成的domain)
	 * @param record 实体对象(Mybatis生成的domain)
	 * @return
	 */
	@Transactional
	public int updateByPrimaryKeySelective(Class<?> mapperClass,Object record){
		int result = (Integer)invokeMapperMethod(mapperClass, "updateByPrimaryKeySelective", new Object[]{record});
		return result;
	}
	/**
	 * 调用mapper的updateByPrimaryKeyWithBLOBs：更新全部字段（包含BLOB和CLOB字段）
	 * @param domainClass 实体对象(Mybatis生成的domain)
	 * @param record 实体对象(Mybatis生成的domain)
	 * @return
	 */
	@Transactional
	public int updateByPrimaryKeyWithBLOBs(Class<?> mapperClass,Object record){
		int result = (Integer)invokeMapperMethod(mapperClass, "updateByPrimaryKeyWithBLOBs", new Object[]{record});
		return result;
	}
	/**
	 * 调用mapper的updateByPrimaryKeyWithBLOBs：更新全部字段（不包含BLOB和CLOB字段--如果有的话）
	 * @param domainClass 实体对象(Mybatis生成的domain)
	 * @param record 实体对象(Mybatis生成的domain)
	 * @return
	 */
	@Transactional
	public int updateByPrimaryKey(Class<?> mapperClass,Object record){
		int result = (Integer)invokeMapperMethod(mapperClass, "updateByPrimaryKey", new Object[]{record});
		return result;
	}

	/**
	 * 动态的从spring容器内得到mybatis自动生成mapper<br>
	 * 反射调用mapper的方法，返回结果
	 * @param mapperClass
	 * @param methodName
	 * @param args
	 * @return
	 */
	private Object invokeMapperMethod(Class<?> mapperClass,String methodName,Object[] args){
		//动态的从spring容器内得到mybatis自动生成mapper
		Object t = SpringContext.getApplicationContext().getBean(mapperClass);
		MethodInvoker method = new MethodInvoker();
		method.setTargetObject(t);
		method.setTargetMethod(methodName);
		method.setArguments(args);
		Object result = null;
		try {
			method.prepare();
			result=method.invoke();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}
}
