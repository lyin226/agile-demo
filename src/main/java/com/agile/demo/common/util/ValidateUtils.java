package com.agile.demo.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author liuyi
 * @date 2019/5/12
 *
 * 参数校验相关方法
 */
public class ValidateUtils {

    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 参数非空校验
     * @param str
     * @param message
     */
    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(-1, message);
        }
    }

    /**
     * 待校验对象
     * @param object
     * @param groups
     * @throws BusinessException
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws BusinessException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new BusinessException(msg.toString());
        }
    }
}
