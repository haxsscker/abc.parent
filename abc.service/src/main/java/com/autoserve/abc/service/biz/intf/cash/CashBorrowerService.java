/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashBorrowerViewDO;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 借款人资金对账service
 * 
 * @author J.YL 2014年11月17日 下午5:16:53
 */
public interface CashBorrowerService {
    /**
     * 批量查询借款人资金对账概览信息
     * 
     * @param pageCondition
     * @return
     */
    public PageResult<CashBorrowerViewDO> queryCashBorrower(String borrowerName, PageCondition pageCondition);

    PlainResult<CashBorrowerViewDO> queryCashBorrower(int userId);
}
