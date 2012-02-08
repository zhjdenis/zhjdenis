package com.daodao.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhjdenis
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD,  ElementType.LOCAL_VARIABLE })
@Inherited
public @interface DaoDaoDBConnection {

	String connection();
}