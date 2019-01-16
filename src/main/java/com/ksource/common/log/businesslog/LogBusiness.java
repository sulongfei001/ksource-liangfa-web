package com.ksource.common.log.businesslog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *描述：用于标识出哪些方法需要记录日志<br>
 *1、支持记录修改前和修改后的业务实体：调用mybatis代码生成工具生成的模板方法，所以需要设置MapperClass<br>
 *2、记录业务实体有两种配置方式：<br>
 *	2.1指定target_domain_position<br>
 *	2.2指定target_domain_code_position和target_domain_class（：注意参数的类型要与业务实例主键类型一致）<br>
 *3、update 操作如要记录修改前后变动，需要needLogChange设置为true（默认为true，不需要设置）<br>
 *4、日志开启禁用：设置disabled，默认开启
 *@author gengzi
 *@data 2012-4-16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogBusiness {
	/**
	 * 禁用日志记录，默认开启日志
	 * @return
	 */
	boolean disabled() default false;
	/**
	 * 操作描述
	 * @return
	 */
	String operation();
	/**
	 * 业务操作类型
	 * @return
	 */
	int business_opt_type() default LogConst.LOG_OPERATION_TYPE_WORK;
	/**
	 * 数据库操作类型
	 * @return
	 */
	int db_opt_type();
	
	/**
	 * 是否需要记录修改前后结果
	 * @return
	 */
	boolean needLogChange() default true;
	
	/**
	 * 操作目标业务实例所在参数索引
	 * @return
	 */
	int target_domain_position() default -1;
	/**
	 * 操作目标业务唯一标识（主键）所在参数索引（：注意类型要与业务实例主键类型一致）
	 * @return
	 */
	int target_domain_code_position() default -1;
	/**
	 * 操作目标业务实例类型
	 * @return
	 */
	Class<?> target_domain_class() default Object.class;
	/**
	 * 操作目标业务实例数据库操作Mybatis Mapper类
	 * @return
	 */
	Class<?> target_domain_mapper_class();
	
}
