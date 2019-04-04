/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.mon;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DownRechargeDO;
import com.autoserve.abc.dao.dataobject.DownRechargeJDO;
import com.autoserve.abc.dao.dataobject.search.DownRechargeSearchDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 线下充值
 * 
 * @author J.YL 2015年1月8日 下午2:34:20
 */
public interface DownRechargeService {
    /**
     * 获取线下充值数据
     * 
     * @param downRechargeSearchDO
     * @param pageCondition
     * @return
     */
    public PageResult<DownRechargeJDO> queryDownRecharge(DownRechargeSearchDO downRechargeSearchDO,
                                                         PageCondition pageCondition);

    /**
     * 通过主键获取线下充值数据
     * 
     * @param downRechargeId
     * @return
     */
    public PlainResult<DownRechargeJDO> queryById(Integer downRechargeId);

    /**
     * 创建线下充值审核
     * 
     * @param downRechargeDO
     * @return
     */
    public BaseResult createDownRechargeApply(DownRechargeDO downRechargeDO);
}
