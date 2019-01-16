package com.ksource.common.log.businesslog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *描述：操作业务对象的唯一标识（注意返回值类型必须与业务实体主键类型一致）<br>
 *@author gengzi
 *@data 2012-4-16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModelCode {
}
