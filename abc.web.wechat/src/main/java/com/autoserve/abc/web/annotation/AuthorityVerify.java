package com.autoserve.abc.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 后台系统验证权限功能 类AuthorityVerify.java的实现描述：TODO 类实现描述
 * 
 * @author pp 2014-11-28 上午10:50:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD })
public @interface AuthorityVerify {
}
