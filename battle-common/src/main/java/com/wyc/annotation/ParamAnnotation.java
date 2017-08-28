package com.wyc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//在每个类的属性上注解指明参数的名称
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamAnnotation {
	
	public static final String REQUEST_TYPE="request";
	public static final String SESSION_TYPE="session";
	public static final String CONTEXT_TYPE="context";
	public String name()default "";
	
	public String type()default REQUEST_TYPE;
}
