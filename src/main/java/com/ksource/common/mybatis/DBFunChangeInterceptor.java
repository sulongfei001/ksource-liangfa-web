package com.ksource.common.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ksource.common.bean.DatabaseDialectBean;
import com.ksource.syscontext.SpringContext;
@Intercepts({@Signature(
		type= Executor.class,
		method = "query",
		args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DBFunChangeInterceptor implements Interceptor {

	private static final Logger logger = LogManager
			.getLogger(DBFunChangeInterceptor.class);

	private String dialect;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation
				.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);

		String methodName = invocation.getMethod().getName();

		String sql = boundSql.getSql().trim();

		if (sql.contains("$needAuthc")) {
			/*StringBuffer resource = new StringBuffer(
					sql.split("\"]")[0].substring(sql.indexOf("\"") + 1));
			String nextSql = sql.split("\"]")[1].trim();*/
			String nextSql = new String(sql);
			//if (nextSql
			//		.matches("^(?i)select.*\\$needAuthc.*")) {
				String regEx = "(?i)[_a-z0-9]+\\$needAuthc";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(nextSql);
				List<String> list = new ArrayList<String>();
				while (m.find()) {
					list.add(m.group());
				}
				for (String str : list) {// login_name$needAuthc("loginName")
					//1.解析默认函数方法名,转换类型
					String [] strs = str.split("\\$");
					String funName = strs[0];
					String changeType = strs[1];
					//2.根据数据方言找出对应的函数方法名
					String realFunName =getRealFunName(changeType);
					if(!realFunName.equals(funName)){
						//3.改变nextSql
						nextSql = nextSql.replaceAll(funName,realFunName);
					}
					logger.debug(str);
					logger.debug(nextSql);
				}
			//}
			// 将sql转换为正常的sql语句
			sql = nextSql.replaceAll("(?i)\\$needAuthc", "");
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
		/*dialect=((DatabaseDialectBean)SpringContext.
    			getApplicationContext().
    			getBean("databaseConfigBean")).getDialect();*/
		System.out.println(dialect+"---------------");
	}

	private  String getRealFunName(String changeType) {
		return "str_to_data";
		
	}
}