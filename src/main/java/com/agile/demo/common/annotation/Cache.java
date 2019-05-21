package com.agile.demo.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuyi
 * @date 2019/5/20
 *
 * 缓存注解
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

    /**
     * 缓存key
     * @return
     */
    String cacheKey() default "";

    /**
     * 过期时间 minute
     * @return
     */
    String expired() default "60";


}
