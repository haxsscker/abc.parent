/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 按钮button
 * 
 * @author pp 2014-11-13 下午03:07:17
 */
public class ButtonDO {
    /**
     * 主键自增 abc_btn.btn_id
     */
    private Integer btnId;

    /**
     * 按钮名称 abc_btn.btn_name
     */
    private String  btnName;

    /**
     * 按钮图标 abc_btn.btn_icon
     */
    private String  btnIcon;

    /**
     * 按钮在前端显示的标示 abc_btn.btn_tag
     */
    private String  btnTag;

    /**
     * 排序 abc_btn.btn_sort
     */
    private Integer btnSort;

    /**
     * 备注 abc_btn.btn_des
     */
    private String  btnDes;

    /**
     * abc_btn.btn_createtime
     */
    private Date    btnCreatetime;

    /**
     * abc_btn.btn_modifytime
     */
    private Date    btnModifytime;

    public Integer getBtnId() {
        return btnId;
    }

    public void setBtnId(Integer btnId) {
        this.btnId = btnId;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getBtnIcon() {
        return btnIcon;
    }

    public void setBtnIcon(String btnIcon) {
        this.btnIcon = btnIcon;
    }

    public String getBtnTag() {
        return btnTag;
    }

    public void setBtnTag(String btnTag) {
        this.btnTag = btnTag;
    }

    public Integer getBtnSort() {
        return btnSort;
    }

    public void setBtnSort(Integer btnSort) {
        this.btnSort = btnSort;
    }

    public String getBtnDes() {
        return btnDes;
    }

    public void setBtnDes(String btnDes) {
        this.btnDes = btnDes;
    }

    public Date getBtnCreatetime() {
        return btnCreatetime;
    }

    public void setBtnCreatetime(Date btnCreatetime) {
        this.btnCreatetime = btnCreatetime;
    }

    public Date getBtnModifytime() {
        return btnModifytime;
    }

    public void setBtnModifytime(Date btnModifytime) {
        this.btnModifytime = btnModifytime;
    }
}
