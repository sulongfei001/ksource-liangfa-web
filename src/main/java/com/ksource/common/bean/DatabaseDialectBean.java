package com.ksource.common.bean;

import java.lang.reflect.InvocationTargetException;

import org.springframework.util.MethodInvoker;

import com.ksource.exception.BusinessException;
import com.ksource.syscontext.Const;

/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2012-3-6
 * time   上午9:36:13
 */
public class DatabaseDialectBean {
	private String dialect;
	private String nextValId;
	private String methodSuffix="";

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
		/*//得到序列ID
		String selectId="systemDAO.getSeqNextValByOracle";
    	if(Const.DIALECT_MYSQL.equals(dialect)){
    		selectId="systemDAO.getSeqNextValByMysql";
    	}
    	nextValId=selectId;*/
    	//得到方法后缀ID
    	if(Const.DIALECT_MYSQL.equals(dialect)){
    		methodSuffix="ByMsql";
		}
	}
	/**
	 * 根据数据方言配置调用不同的sql(sqlMap映射文件中的id标示)
	 * 具体参数可查看database_dialect.properties文件
	 * TODO:针对可配置sqlmap后缀进行重构
	 * @param mapperObject
	 * @param methodName
	 * @param args
	 * @return
	 */
	public Object callMapperByDialect(Object mapperObject,String methodName,Object[] args){
		MethodInvoker method = new MethodInvoker();
		method.setTargetObject(mapperObject);
		String realMethodName = new String(methodName);
		methodName+=methodSuffix;
		method.setTargetMethod(realMethodName);
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

	/*public String getNextValId() {
		String selectId="systemDAO.getSeqNextValByOracle";
    	if(Const.DIALECT_MYSQL.equals(dialect)){
    		selectId="systemDAO.getSeqNextValByMysql";
    	}
	}*/

}
