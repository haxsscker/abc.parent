/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RefundRecordDO;
import com.autoserve.abc.dao.dataobject.search.RefundRecordSearchDO;

/**
 * 退款记录表Dao
 * 
 * @author J.YL 2014年11月15日 下午6:28:51
 */
public interface RefundRecordDao extends BaseDao<RefundRecordDO, Integer> {

    /**
     * 更新RefundRecord状态
     * 
     * @param refundRecord
     * @return
     */
    public int updateStateBySeqNo(RefundRecordDO refundRecord);

    /**
     * 通过refundRecordId更新 seqNo和operator
     * 
     * @param refundRecord
     * @return
     */
    public int updateRefundRecord(RefundRecordDO refundRecord);

    /**
     * 找到所有数据
     * 
     * @param refundRecordSearchDO
     * @param pageCondition
     * @return
     */
    public List<RefundRecordDO> findList(@Param("refundRecordSearchDO") RefundRecordSearchDO refundRecordSearchDO,
                                         @Param("pageCondition") PageCondition pageCondition);

    /**
     * 更新申请状态
     * 
     * @param refundRecord
     * @return
     */
    public int updateRefundApplyState(RefundRecordDO refundRecord);

    /**
     * 通过seqNo查询退款交易记录
     * 
     * @param seqNo
     * @return
     */
    public RefundRecordDO findBySeqNo(String seqNo);

    public int countBySearchDO(@Param("refundRecordSearchDO") RefundRecordSearchDO refundRecordSearchDO);
}
