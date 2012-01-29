package com.daodao.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hzhou
 * this annotation can avoid the class be ignore by default behaviour that declared in {@link PrepareForTest}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface DaoDaoIgnoreMock {

    Class[] value();
}
