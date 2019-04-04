package com.autoserve.abc.web.vo;

/**
 * 统一的 json 输出结构
 */
public class JsonPlainVO<T> extends JsonBaseVO {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
