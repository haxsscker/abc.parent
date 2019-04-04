/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue Jan 06 14:27:21 CST 2015
 * Description:
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.TransferScheduleDO;

public interface TransferScheduleDao extends BaseBidDao<TransferLoanDO, Integer> {
    /**
     * deleteByPrimaryKey
     * 
     * @param Integer tsId
     * @return int
     */
    int deleteByPrimaryKey(Integer tsId);

    /**
     * insert
     * 
     * @param TransferScheduleDO record
     * @return int
     */
    int insert(TransferScheduleDO record);

    /**
     * insertSelective
     * 
     * @param TransferScheduleDO record
     * @return int
     */
    int insertSelective(TransferScheduleDO record);

    /**
     * selectByPrimaryKey
     * 
     * @param Integer tsId
     * @return TransferScheduleDO
     */
    TransferScheduleDO selectByPrimaryKey(Integer tsId);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param TransferScheduleDO record
     * @return int
     */
    int updateByPrimaryKeySelective(TransferScheduleDO record);

    /**
     * updateByPrimaryKey
     * 
     * @param TransferScheduleDO record
     * @return int
     */
    int updateByPrimaryKey(TransferScheduleDO record);

    /**
     * @param loanId
     * @param pageCondition
     * @return
     */
    List<TransferScheduleDO> selectByLoanId(@Param("loanId") Integer loanId,
                                            @Param("pageCondition") PageCondition pageCondition);

    /**
     * @param loanId
     * @param pageCondition
     * @return
     */
    int countSelectByLoanId(@Param("loanId") Integer loanId, @Param("pageCondition") PageCondition pageCondition);

}
