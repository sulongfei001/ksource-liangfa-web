package com.ksource.common.log.businesslog;
/**
 *描述：日志常量<br>
 *@author gengzi
 *@data 2012-4-16
 */
public class LogConst {

	/**
	 * 登录日志
	 */
	public static final int LOG_OPERATION_TYPE_LOGIN = 0;
	/**
	 * 退出日志
	 */
	public static final int LOG_OPERATION_TYPE_LOGOUT = 1;
	/**
	 * 业务操作日志
	 */
	public static final int LOG_OPERATION_TYPE_WORK = 2;
	
	/**
	 * 业务数据操作类型：（批量）增删改查
	 */
	public static final int LOG_DB_OPT_TYPE_INSERT = 1;
	public static final int LOG_DB_OPT_TYPE_DELETE = 2;
	public static final int LOG_DB_OPT_TYPE_UPDATE = 3;
	public static final int LOG_DB_OPT_TYPE_SELECT = 4;
	public static final int LOG_DB_OPT_TYPE_BATCH_INSERT = 5;
	public static final int LOG_DB_OPT_TYPE_BATCH_DELETE = 6;
	public static final int LOG_DB_OPT_TYPE_BATCH_UPDATE = 7;
	public static final int LOG_DB_OPT_TYPE_TASKDEAL = 8;//案件办理
}
