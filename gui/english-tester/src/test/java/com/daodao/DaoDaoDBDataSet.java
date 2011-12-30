/**
 * 下午5:29:05
 */
package com.daodao;

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
@Target({ElementType.METHOD,ElementType.TYPE})
@Inherited
public @interface DaoDaoDBDataSet {

	String[] locations();
	DaoDaoDBDataSetOperation operation() default DaoDaoDBDataSetOperation.INSERT;
}
