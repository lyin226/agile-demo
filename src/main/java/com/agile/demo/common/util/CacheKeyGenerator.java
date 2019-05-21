package com.agile.demo.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * @author liuyi
 * @date 2019/5/20
 *
 * 缓存key生成规则
 */

@Slf4j
public class CacheKeyGenerator {

    private static final String NULL = "NULL";

    /**
     * 根据key和参数生成redis中的key
     * @param key
     * @param args
     * @return
     */
    public static String generate(String key, Object[] args) {
        StringBuilder sb = new StringBuilder(key == null ? NULL : key);
        for(int i=0; i<args.length; i++){
            sb.append(args[i] == null ? NULL : args[i]).append((i == args.length - 1) ? StringUtils.EMPTY : ".");
        }
        log.debug("generated key is {}", sb.toString());
        return sb.toString();
    }

    public static String getMethodLongName(Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName()).append("(");
        for (Class<?> type : method.getParameterTypes()) {
            sb.append(type.getName()).append(",");
        }
        if (method.getParameterTypes().length > 0)
            sb.delete(sb.length() - 1, sb.length());
        sb.append(")");
        return sb.toString();
    }


}
