package com.ksource.common.log.businesslog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ksource.common.util.DateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsonMapper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.BusinessLogWithBLOBs;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;
import com.ksource.syscontext.SystemContext;
import com.ksource.syscontext.ThreadContext;
/**
 *描述：业务操作日志切面<br>
 *@author gengzi
 *@data 2012-4-16
 */
@Aspect
public class BusinessLogAspect{

	private static JsonMapper binder = JsonMapper.buildNonNullMapper();
	// 日志
	private static final Logger log = LogManager.getLogger(BusinessLogAspect.class);
	private MybatisAutoMapperService mapperService;
	private LogDBService logDB;
    private Map<String,Boolean> isDelLogMap =new HashMap<String,Boolean>();

	// 声明切入点   
	@Pointcut("execution(@com.ksource.common.log.businesslog.LogBusiness * * (..))")
    public void aroundPointCut() {}
    
    //执行前记录到日志
    @Around("com.ksource.common.log.businesslog.BusinessLogAspect.aroundPointCut()")
    public Object doLoggerPointCut(ProceedingJoinPoint jp) {
    	mapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
    	logDB = SpringContext.getApplicationContext().getBean(LogDBService.class);
    	Object returnVal=null;
    	User optUser = ThreadContext.getCurUser();
    	if(optUser!=null){
    		log.debug("操作用户："+optUser.getUserId()+"----"+optUser.getUserName());
    	}
    	log.debug("日志aop拦截器："+jp.toString());
    	try {
             //删除三个月前的业务日志
            delLog();
    		// 获取连接点的方法签名对象   
            MethodSignature joinPointObject = (MethodSignature) jp.getSignature();   
            // 连接点对象的方法   
            Method method = joinPointObject.getMethod();   
            // 连接点方法方法名   
            String name = method.getName();   
            Class<?>[] parameterTypes = method.getParameterTypes();   
            // 获取连接点所在的目标对象   
            Object target = jp.getTarget();   
            // 获取目标方法   
            method = target.getClass().getMethod(name, parameterTypes);
            // 返回@AroundPointCut的注释对象   
            LogBusiness logBusiness = method.getAnnotation(LogBusiness.class);
            
        	if(logBusiness.disabled()){//日志禁用
        		return jp.proceed();
        	}
        	//方法参数列表
            Object[] args = jp.getArgs();
            //获取日志配置
            String operation = logBusiness.operation();
            Class<?> domainMapperClass = logBusiness.target_domain_mapper_class();
            Class<?> domainClass = logBusiness.target_domain_class();
            boolean needLogChange=logBusiness.needLogChange();
        	//操作类型
        	int business_opt_type = logBusiness.business_opt_type();
        	//数据库操作类型
        	int db_opt_type = logBusiness.db_opt_type();
        	
        	BusinessLogWithBLOBs businessLog = new BusinessLogWithBLOBs();
        	businessLog.setBusinessOptType(business_opt_type);
        	businessLog.setDbOptType(db_opt_type);
        	businessLog.setOperation(operation);
        	businessLog.setOptTime(new Date());
        	String optUserId = optUser==null?null: optUser.getUserId();
        	businessLog.setUserId(optUserId);
        	businessLog.setSucceed(Const.STATE_INVALID);//默认执行失败
        	
        	Object domainCode=null;
        	Map<String, Object> resultMap= settingDomainClassAndDomainCode(domainClass, domainCode, args, logBusiness);
        	domainClass = (Class<?>)resultMap.get("domainClass");
        	domainCode = resultMap.get("domainCode");
        	//如果是登录和退出日志，只记录时间和登录人
        	if(business_opt_type==LogConst.LOG_OPERATION_TYPE_LOGIN
        			 ||business_opt_type==LogConst.LOG_OPERATION_TYPE_LOGOUT){
        		Object returnVal1= jp.proceed();
        		int succeed=Const.STATE_VALID;
                String resultDesc="";
                if(returnVal1 instanceof ServiceResponse){
             	   ServiceResponse response = (ServiceResponse)returnVal1;
             	   if(!response.getResult()){
             		   succeed=0;
             		   resultDesc = response.getMsg();
             	   }
                }
                businessLog.setSucceed(succeed);businessLog.setResultDesc(resultDesc);
                businessLog.setDbOptType(null);
                insertLoginOutLog(domainCode, domainClass, businessLog, domainMapperClass, args, logBusiness);
                return returnVal1;
        	}
        	
        	if(db_opt_type==LogConst.LOG_DB_OPT_TYPE_DELETE){
        		insertLogFromDb(domainCode, domainClass, businessLog, domainMapperClass);
           }else if(db_opt_type==LogConst.LOG_DB_OPT_TYPE_UPDATE && needLogChange==true){
        	   insertLogFromDb(domainCode, domainClass, businessLog, domainMapperClass);
           }else if(db_opt_type==LogConst.LOG_DB_OPT_TYPE_SELECT){
        	   if(domainCode != null){
        		   businessLog.setDomainCode(domainCode.toString());
        		   businessLog.setDomainName(domainCode.toString());
        	   }
        	   logDB.addLog(businessLog);
           }else if(db_opt_type==LogConst.LOG_DB_OPT_TYPE_TASKDEAL){
        	   if(domainCode != null){
        		   businessLog.setDomainCode(domainCode.toString());
        		   businessLog.setDomainName("任务办理ID:"+domainCode.toString());
        	   }
        	   logDB.addLog(businessLog);
           }else if(db_opt_type==LogConst.LOG_DB_OPT_TYPE_BATCH_INSERT
        			||db_opt_type==LogConst.LOG_DB_OPT_TYPE_BATCH_DELETE
        			||db_opt_type==LogConst.LOG_DB_OPT_TYPE_BATCH_UPDATE){
        		//TODO：暂时不拦截
        		return jp.proceed();
        	}
           returnVal = jp.proceed();
         //--------后置开始
	        
           int succeed=Const.STATE_VALID;
           String resultDesc="";
           if(returnVal instanceof ServiceResponse){
        	   ServiceResponse response = (ServiceResponse)returnVal;
        	   if(!response.getResult()){
        		   succeed=0;
        		   resultDesc = response.getMsg();
        	   }
           }
	       	if(db_opt_type==LogConst.LOG_DB_OPT_TYPE_DELETE){//更新操作结果
	       		logDB.updateOptResult(businessLog.getId(),succeed,resultDesc);
	       }else if(db_opt_type==LogConst.LOG_DB_OPT_TYPE_SELECT){//查询操作
	    	   logDB.updateOptResult(businessLog.getId(),succeed,resultDesc);//案件办理
	       }else if(db_opt_type==LogConst.LOG_DB_OPT_TYPE_TASKDEAL){
	    	   logDB.updateOptResult(businessLog.getId(),succeed,resultDesc);
	       }else if(db_opt_type==LogConst.LOG_DB_OPT_TYPE_INSERT
        		   ||(db_opt_type==LogConst.LOG_DB_OPT_TYPE_UPDATE && needLogChange==false)){//新增操作|更新(不记录变动)操作完成后，记录日志
	    	   resultMap= settingDomainClassAndDomainCode(domainClass, domainCode, args, logBusiness);
		       domainCode = resultMap.get("domainCode");
	    	   businessLog.setSucceed(succeed);
	    	   businessLog.setResultDesc(resultDesc);
        	   insertLogFromDb(domainCode, domainClass, businessLog, domainMapperClass);
           }else if(db_opt_type==LogConst.LOG_DB_OPT_TYPE_UPDATE && needLogChange==true){//更新（记录变动）操作前，记录原业务实例，操作完成后，记录实例的变动（实例最新状态）
        	   if(succeed==Const.STATE_VALID){//更新成功
        		   //更新业务实体修改记录
        		   Object domain=queryDomainFromDB(domainCode, domainMapperClass, domainClass);
           		   logDB.updateChangeLog(businessLog.getId(), binder.toJson(domain),succeed,resultDesc);
        	   }else{//更新失败
        		   logDB.updateOptResult(businessLog.getId(),succeed,resultDesc);
        	   }
           }

		} catch (Throwable e) {
			e.printStackTrace();
			log.error("日志aop拦截器失败，本次调用信息："+jp.toString());
			throw new BusinessException(e.getMessage());//记得抛出异常，不然所拦截的service事物不能回滚
		}
    	return returnVal;
    }

    private void delLog() {
       String currDate =DateUtil.convertDateToString(new Date());
       if(!isDelLogMap.containsKey(currDate)){
        //删除三个月前的日志信息
        logDB.delLog();
        isDelLogMap.clear();
        isDelLogMap.put(currDate,true);
       }
    }

    @SuppressWarnings({"unchecked", "rawtypes" })
	private Object findReturnValByAnnotation(Object target,Class clazz) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
    	Object object=null;
    	Method[] methodList = target.getClass().getMethods();
    	for(Method method:methodList){
    		if(method.getAnnotation(clazz)!=null){
    			object = method.invoke(target,new Object[0]);
    			break;
    		}
    	}
    	if(target.getClass().equals(String.class)){
    		object = target;
    	}
    	return object;
    }
    
    private void insertLogFromDb(Object domainCode,Class<?> domainClass,BusinessLogWithBLOBs businessLog, Class<?> domainMapperClass) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
    	Object domain = null;
   		String domainName = null;
   		Object objName = null;
   		domain = queryDomainFromDB(domainCode, domainMapperClass, domainClass);
   		objName = findReturnValByAnnotation(domain,ModelName.class);
   		domainName = objName == null?"":objName.toString();
   		businessLog.setDomain1(binder.toJson(domain));
   		businessLog.setDomainCode(String.valueOf(domainCode));
   		businessLog.setDomainName(domainName);
   		businessLog.setDomainClass(domainClass.getName());
   		logDB.addLog(businessLog);
    }
    private void insertLoginOutLog(Object domainCode,Class<?> domainClass,BusinessLogWithBLOBs businessLog, Class<?> domainMapperClass,Object[] args,LogBusiness logBusiness) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
    	int domainPosition = logBusiness.target_domain_position();
    	Object domain = null;
    	String domainName = null;
    	Object objName = null;
    	if(domainPosition==-1){
    		domain = queryDomainFromDB(domainCode, domainMapperClass, domainClass);
   		}else{
   			domain = args[domainPosition];
   		}
   		objName = findReturnValByAnnotation(domain,ModelName.class);
   		domainName = objName == null?"":objName.toString();
    	businessLog.setDomainCode(String.valueOf(domainCode));
    	businessLog.setDomainName(domainName);
    	businessLog.setDomainClass(domainClass.getName());
    	logDB.addLog(businessLog);
    }
    
    //设置domainClass 和domainCode
    private Map<String, Object> settingDomainClassAndDomainCode(Class<?> domainClass, Object domainCode, Object[] args, LogBusiness logBusiness) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
    	int domainPosition = logBusiness.target_domain_position();
        int domainCodePosition = logBusiness.target_domain_code_position();
    	Object domain = null;
   		if(domainPosition==-1){
   			domainCode = args[domainCodePosition];
   		}else{
   			domain = args[domainPosition];
   			domainClass = domain.getClass();
   			domainCode=findReturnValByAnnotation(domain,ModelCode.class);
   		}
   		Map<String, Object> val = new HashMap<String, Object>(2);
   		val.put("domainClass", domainClass);
   		val.put("domainCode", domainCode);
   		return val;
    }
    
    private Object queryDomainFromDB(Object domainCode, Class<?> domainMapperClass, Class<?> domainClass){
    	Object domain = null;
    	domain = mapperService.selectByPrimaryKey(domainMapperClass, domainCode, domainClass);//查询最新数据库记录
    	return domain;
    }
}
