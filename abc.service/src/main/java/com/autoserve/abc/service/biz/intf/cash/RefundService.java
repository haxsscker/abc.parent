/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RefundRecordDO;
import com.autoserve.abc.dao.dataobject.search.RefundRecordSearchDO;
import com.autoserve.abc.service.biz.entity.Refund;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 退费Service
 * 
 * @author J.YL 2014年11月17日 下午5:17:50
 */
public interface RefundService {

    /**
     * 发起退款，创建退款交易并退款
     * 
     * @param refund
     * @return
     */
    public BaseResult refund(RefundRecordDO refund);

    /**
     * 查询退款
     * 
     * @param userId
     * @param pageCondition
     * @return
     */
    public PageResult<RefundRecordDO> queryListRefundRecord(RefundRecordSearchDO refund, PageCondition pageCondition);

    /**
     * 创建退款请求
     * 
     * @param refund
     * @return
     */
    public BaseResult createRefundApply(Refund refund);

}
