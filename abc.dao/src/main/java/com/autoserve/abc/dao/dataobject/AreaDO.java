package com.autoserve.abc.dao.dataobject;

/**
 * 区域信息
 *
 * @author RJQ 2014/11/13 17:28.
 */
public class AreaDO {
    /**
     * 主键
     * abc_area.area_id
     */
    private Integer areaId;

    /**
     * 地区名称
     * abc_area.area_name
     */
    private String areaName;

    /**
     * 父级地区
     * abc_area.area_super_area
     */
    private String areaSuperArea;

    /**
     * 地区编号
     * abc_area.area_area_code
     */
    private String areaAreaCode;

    /**
     * 0：省 1：市 2：县
     * abc_area.area_sign
     */
    private Integer areaSign;


    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaSuperArea() {
        return areaSuperArea;
    }

    public void setAreaSuperArea(String areaSuperArea) {
        this.areaSuperArea = areaSuperArea;
    }

    public String getAreaAreaCode() {
        return areaAreaCode;
    }

    public void setAreaAreaCode(String areaAreaCode) {
        this.areaAreaCode = areaAreaCode;
    }

    public Integer getAreaSign() {
        return areaSign;
    }

    public void setAreaSign(Integer areaSign) {
        this.areaSign = areaSign;
    }
}
