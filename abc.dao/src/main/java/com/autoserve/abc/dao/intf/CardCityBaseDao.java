/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Wed Jan 28 16:44:42 CST 2015
 * Description:
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.CardCityBaseDO;

public interface CardCityBaseDao extends BaseDao<CardCityBaseDO, Integer> {
    /**
     * deleteByPrimaryKey
     * 
     * @param Integer cityCode
     * @return int
     */
    int deleteByPrimaryKey(Integer cityCode);

    /**
     * insertSelective
     * 
     * @param CardCityBaseDO record
     * @return int
     */
    int insertSelective(CardCityBaseDO record);

    /**
     * selectByPrimaryKey
     * 
     * @param Integer cityCode
     * @return CardCityBase
     */
    CardCityBaseDO selectByPrimaryKey(Integer cityCode);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param CardCityBaseDO record
     * @return int
     */
    int updateByPrimaryKeySelective(CardCityBaseDO record);

    /**
     * updateByPrimaryKey
     * 
     * @param CardCityBaseDO record
     * @return int
     */
    int updateByPrimaryKey(CardCityBaseDO record);
    
    /**
     * queryCardByCode
     * 根据省code查询集合信息
    * @param CardCityBaseDO record
     * @return List
     */
    List<CardCityBaseDO> queryCardByProvcode(Integer provCode);
    
     /**
     * queryCardByCode
     * 查询所有的city信息
     * @param null
     * @return List
     */
    List<CardCityBaseDO> queryAllCity();
}
