package com.agile.demo.common.aspect;

import com.agile.demo.common.annotation.Cache;
import com.agile.demo.common.util.CacheKeyGenerator;
import com.alibaba.fastjson.JSON;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author liuyi
 * @date 2019/5/20
 *
 * 缓存注解切面
 */

@Component
@Aspect
@Slf4j
public class CacheAspect implements Ordered {

    @Resource
    private RedisTemplate redisTemplate;

    public CacheAspect() {

    }

    @Around("@annotation(com.agile.demo.common.annotation.Cache)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String clazz = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        Class[] paramTypes = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        Object[] args = pjp.getArgs();
        log.debug("Invoke {}.{}({})", clazz, methodName, args);
        Method method = pjp.getTarget().getClass().getMethod(methodName, paramTypes);
        Cache cache = method.getAnnotation(Cache.class);
        byte[] cacheKey = null;
        String cacheKeys = cache.cacheKey();
        if (StringUtils.isBlank(cacheKeys)) {
            StringBuilder sb = new StringBuilder();
            sb.append(clazz).append(":").append(CacheKeyGenerator.getMethodLongName(method)).append(":").append(
                    JSON.toJSONString(args));
            cacheKey = sb.toString().getBytes();
        } else {
            cacheKey = CacheKeyGenerator.generate(cacheKeys, args).getBytes();
        }
        Object result = null;
        boolean invokeProxy = true;
        try {
            String expired = cache.expired();
            String value = (String)redisTemplate.opsForValue().get(cacheKey);
            if (StringUtils.isBlank(value)) {
                try {
                    result = pjp.proceed();
                } catch (Exception e) {
                    invokeProxy = false;
                    throw e;
                }
                redisTemplate.opsForValue().set(cacheKey, value, Long.parseLong(expired), TimeUnit.MINUTES);
                return result;
            } else {
                return value;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (invokeProxy) {
                //当redis服务异常时，直接调被代理方法
                return pjp.proceed();
            }
            throw e;
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
