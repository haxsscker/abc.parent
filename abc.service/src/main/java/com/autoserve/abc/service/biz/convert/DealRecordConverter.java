/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.entity.DealRecord;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;

/**
 * DealRecord to DealRecordDO
 * 
 * @author J.YL 2014年11月22日 下午3:54:57
 */
public class DealRecordConverter {
    public static DealRecord toDealRecord(DealRecordDO dealRecordDo) {
        DealRecord dealRecord = new DealRecord();
        if (dealRecordDo == null) {
            return dealRecord;
        }
        dealRecord.setBusinessId(dealRecordDo.getDrBusinessId());
        dealRecord.setCashId(dealRecordDo.getDrCashId());
        dealRecord.setId(dealRecordDo.getDrId());
        dealRecord.setInnerSeqNo(dealRecordDo.getDrInnerSeqNo());
        dealRecord.setMoneyAmount(dealRecordDo.getDrMoneyAmount());
        dealRecord.setOperateDate(dealRecordDo.getDrOperateDate());
        dealRecord.setOperator(dealRecordDo.getDrOperator());
        // dealRecord.setOutSeqNo(dealRecordDo.getDrOutSeqNo());
        dealRecord.setPayAccount(dealRecordDo.getDrPayAccount());
        dealRecord.setReceiveAccount(dealRecordDo.getDrReceiveAccount());
        dealRecord.setState(DealState.valueOf(dealRecordDo.getDrState()));
        dealRecord.setType(DealType.valueOf(dealRecordDo.getDrType()));
        dealRecord.setDetailType(dealRecordDo.getDrDetailType());
        return dealRecord;

    }
}
