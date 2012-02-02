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
@Target({ ElementType.METHOD, ElementType.TYPE })
@Inherited
public @interface DaoDaoDBDataSet {

	String[] locations();

	String connection();

	DaoDaoDBDataSetOperation operation() default DaoDaoDBDataSetOperation.INSERT;
}
