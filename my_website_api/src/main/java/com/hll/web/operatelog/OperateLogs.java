package com.hll.web.operatelog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface OperateLogs {
	// 模块名
	String moduleName() default "";

	// 操作内容
	String option() default "";
	
	//url
	String url() default "";
}
