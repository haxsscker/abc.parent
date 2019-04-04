package com.autoserve.abc.service.biz.intf.refund;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RefundRecordDO;
import com.autoserve.abc.dao.dataobject.search.RefundRecordSearchDO;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 充值记录查询Service
 * 
 * @author liuwei 2014年12月18日 上午11:22:43
 */
public interface RefundRecordService {

    /**
     * 查询所有数据
     * 
     * @param refundRecordSearchDO
     * @return
     */
    public PageResult<RefundRecordDO> queryList(RefundRecordSearchDO refundRecordSearchDO, PageCondition pageCondition);

    /**
     * 根据refundId查询退款记录
     * 
     * @param refundRecordId
     * @return
     */
    public RefundRecordDO queryById(Integer refundRecordId);
}
