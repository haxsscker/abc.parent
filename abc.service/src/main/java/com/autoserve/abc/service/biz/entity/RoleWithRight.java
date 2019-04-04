package com.autoserve.abc.service.biz.entity;

/**
 * 类RoleWithRight.java的实现描述：TODO 类实现描述
 * 
 * @author pp 2014年11月19日 上午11:22:11
 */
public class RoleWithRight {

    private Integer butId;
    private Integer select;   //是否已经被勾选
    private Integer canSelect; //是否可选

    public Integer getButId() {
        return butId;
    }

    public void setButId(Integer butId) {
        this.butId = butId;
    }

    public Integer getSelect() {
        return select;
    }

    public void setSelect(Integer select) {
        this.select = select;
    }

    public Integer getCanSelect() {
        return canSelect;
    }

    public void setCanSelect(Integer canSelect) {
        this.canSelect = canSelect;
    }

}
