package com.autoserve.abc.service.biz.entity;

/**
 * 记录属性变更历史
 *
 * @author RJQ 2014/12/12 15:31.
 */
public class History {

    private String guhField;

    private Object guhFieldOld;

    private Object guhFiledNew;

    public String getGuhField() {
        return guhField;
    }

    public void setGuhField(String guhField) {
        this.guhField = guhField;
    }

    public Object getGuhFieldOld() {
        return guhFieldOld;
    }

    public void setGuhFieldOld(Object guhFieldOld) {
        this.guhFieldOld = guhFieldOld;
    }

    public Object getGuhFiledNew() {
        return guhFiledNew;
    }

    public void setGuhFiledNew(Object guhFiledNew) {
        this.guhFiledNew = guhFiledNew;
    }
}