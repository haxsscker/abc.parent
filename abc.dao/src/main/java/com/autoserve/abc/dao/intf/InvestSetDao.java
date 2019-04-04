/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue Mar 10 16:24:28 CST 2015
 * Description:
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.dataobject.InvestSetDO;

public interface InvestSetDao {
    /**
     * deleteByPrimaryKey
     * 
     * @param Integer isId
     * @return int
     */
    int deleteByPrimaryKey(Integer isId);

    /**
     * insert
     * 
     * @param InvestSet record
     * @return int
     */
    int insert(InvestSetDO record);

    /**
     * insertSelective
     * 
     * @param InvestSet record
     * @return int
     */
    int insertSelective(InvestSetDO record);

    /**
     * selectByPrimaryKey
     * 
     * @param Integer isId
     * @return InvestSet
     */
    InvestSetDO selectByPrimaryKey(Integer isId);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param InvestSet record
     * @return int
     */
    int updateByPrimaryKeySelective(InvestSetDO record);

    /**
     * updateByPrimaryKey
     * 
     * @param InvestSet record
     * @return int
     */
    int updateByPrimaryKey(InvestSetDO record);

    List<InvestSetDO> findiInvestSet(InvestSetDO investSetDO);
}
