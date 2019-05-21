package com.agile.demo.common.util;

import java.util.HashMap;

/**
 * @author liuyi
 * @date 2019/5/6
 */
public class WebResult  extends HashMap<String,Object> {

    private int code;
    private String msg;

    public WebResult(){
        put("code", 0);
        put("msg", "success");
    }

    public static WebResult error() {
        WebResult webResult = new WebResult();
        webResult.put("code", BusinessErrorCode.ERROR_UNKNOW.getCode());
        webResult.put("msg", BusinessErrorCode.ERROR_UNKNOW.getDesc());
        return webResult;
    }

    public static WebResult error(String msg) {
        WebResult webResult = new WebResult();
        webResult.put("code", BusinessErrorCode.ERROR_UNKNOW.getCode());
        webResult.put("msg", msg);
        return webResult;
    }

    public static WebResult error(int code, String msg) {
        WebResult webResult = new WebResult();
        webResult.put("code", code);
        webResult.put("msg", msg);
        return webResult;
    }
    public WebResult(int code){
        this.code = code;
    }

    public WebResult(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public WebResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
