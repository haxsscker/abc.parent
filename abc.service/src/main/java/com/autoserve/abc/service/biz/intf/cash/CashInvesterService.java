/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 投资人资金对账service
 * 
 * @author J.YL 2014年11月17日 下午5:17:18
 */
public interface CashInvesterService {

    /**
     * 查询投资人资金对账概览信息
     * 
     * @param pageCondition
     * @return
     */
    public PageResult<CashInvesterViewDO> queryCashInvester(String investorName, String investorRealName,
                                                            PageCondition pageCondition);

    /**
     * 根据userId个人资金预览信息
     * @param userId
     * @return
     */
	PlainResult<CashInvesterViewDO> queryCashInvester(int userId);
}
