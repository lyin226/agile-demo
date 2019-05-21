package com.agile.demo.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * @author liuyi
 * @date 2019/5/6
 */
public class BusinessException extends RuntimeException {

    private Integer code;
    private String message;
    private String trace;

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(Integer code, String message, Exception e) {
        this.code = code;
        this.message = message;
        if (e != null) {
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            try{
                e.printStackTrace(new PrintStream(fos,false,"UTF-8"));
                this.trace = fos.toString("UTF-8");
            }catch (UnsupportedEncodingException e1){
            }
        }
    }

    public BusinessException(Exception e) {
        this.code = BusinessErrorCode.SERVICE_ERROR.getCode();
        this.message = BusinessErrorCode.SERVICE_ERROR.getDesc();
        if (e != null) {
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            try{
                e.printStackTrace(new PrintStream(fos,false,"UTF-8"));
                this.trace = fos.toString("UTF-8");
            }catch (UnsupportedEncodingException e1){
            }
        }
    }

    public BusinessException(String message) {
        this.code = BusinessErrorCode.SERVICE_ERROR.getCode();
        this.message = message;
    }

    public BusinessException(BusinessErrorCode e) {
        super(e.getDesc());
        this.code = e.getCode();
        this.message = e.getDesc();
    }
    public BusinessException(){}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BusinessException :{");
        sb.append("\"code\":").append(code).append(",");
        sb.append("\"message\":\"").append(message).append("\",");
        sb.append("\"stackTrace\":\"").append(trace).append("\"}");
        return sb.toString();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getTrace() {
        return trace;
    }
}
