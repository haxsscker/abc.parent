package com.autoserve.abc.service.biz.enums;

/**
 * 请求的枚举类 类RequestMethodType.java的实现描述：TODO 类实现描述
 * 
 * @author pp 2014-11-27 下午05:33:56
 */
public enum RequestMethodType {

    POST("POST", "post请求"),
    GET("GET", "get请求");
    public String value;
    public String desc;

    private RequestMethodType(String v, String d) {
        this.value = v;
        this.desc = d;
    }
}
