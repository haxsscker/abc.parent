package com.autoserve.abc.web.vo;

public class JsonPageVO<T> extends JsonListVO<T> {

    /**
     * 记录条数
     */
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
