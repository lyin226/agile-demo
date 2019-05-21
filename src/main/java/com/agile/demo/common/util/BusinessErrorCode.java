package com.agile.demo.common.util;

/**
 * @author liuyi
 * @date 2019/5/6
 */
public enum  BusinessErrorCode {

    SUCCESS(1, "成功"),

    ERROR_UNKNOW(000000,"未知类型错误"),

    SERVICE_ERROR(10500, "服务内部错误");

    private Integer code;
    private String desc;

    BusinessErrorCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
