/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Wed Jan 28 16:44:42 CST 2015
 * Description:
 */
package com.autoserve.abc.dao.dataobject;

/**
 *  CardCityBase
 *  abc_card_city_base
 */
public class CardCityBaseDO {
    /**
     * 代码
     * abc_card_city_base.CITY_CODE
     */
    private Integer cityCode;

    /**
     * 城市名称
     * abc_card_city_base.CITY_NAME
     */
    private String cityName;

    /**
     * 所属省份代码
     * abc_card_city_base.PROV_CODE
     */
    private Integer provCode;

    /**
     * @return abc_card_city_base.CITY_CODE
     */
    public Integer getCityCode() {
        return cityCode;
    }

    /**
     * @param Integer cityCode (abc_card_city_base.CITY_CODE )
     */
    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * @return abc_card_city_base.CITY_NAME
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param String cityName (abc_card_city_base.CITY_NAME )
     */
    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    /**
     * @return abc_card_city_base.PROV_CODE
     */
    public Integer getProvCode() {
        return provCode;
    }

    /**
     * @param Integer provCode (abc_card_city_base.PROV_CODE )
     */
    public void setProvCode(Integer provCode) {
        this.provCode = provCode;
    }
}