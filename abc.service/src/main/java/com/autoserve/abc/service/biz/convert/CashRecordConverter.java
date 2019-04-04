/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.service.biz.entity.CashRecord;
import com.autoserve.abc.service.biz.enums.CashOperateState;
import com.autoserve.abc.service.biz.enums.CashOperateType;

/**
 * 资金操作记录Converter
 * 
 * @author J.YL 2014年11月22日 下午4:16:29
 */
public class CashRecordConverter {

    public static CashRecord toCashRecord(CashRecordDO cashRecordDo) {
        CashRecord cashRecord = new CashRecord();
        if (cashRecordDo == null) {
            return cashRecord;
        }
        cashRecord.setCrId(cashRecordDo.getCrId());
        cashRecord.setCrMoneyAmount(cashRecordDo.getCrMoneyAmount());
        cashRecord.setCrOperateDate(cashRecordDo.getCrOperateDate());
        cashRecord.setCrOperateType(CashOperateType.valueOf(cashRecordDo.getCrOperateType()));
        cashRecord.setCrRequestMethod(cashRecordDo.getCrRequestMethod());
        cashRecord.setCrRequestParameter(cashRecordDo.getCrRequestParameter());
        cashRecord.setCrRequestUrl(cashRecordDo.getCrRequestUrl());
        cashRecord.setCrResponse(cashRecordDo.getCrResponse());
        cashRecord.setCrResponseState(CashOperateState.valueOf(cashRecordDo.getCrResponseState()));
        cashRecord.setCrSeqNo(cashRecordDo.getCrSeqNo());
        return cashRecord;
    }
}
