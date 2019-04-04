/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.TraceRecordDO;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;

/**
 * LoanTraceRecordDO与LoanTraceRecord互相转换
 *
 * @author segen189 2014年11月21日 下午8:47:13
 */
public class LoanTraceRecordConverter {

    public static TraceRecordDO toTraceRecordDO(LoanTraceRecord traceRecord) {
        if (traceRecord == null) {
            return null;
        }

        TraceRecordDO traceRecordDO = new TraceRecordDO();

        traceRecordDO.setTrId(traceRecord.getId());
        traceRecordDO.setTrLoanId(traceRecord.getLoanId());
        traceRecordDO.setTrBidId(traceRecord.getLoanId());
        traceRecordDO.setTrBidType(BidType.COMMON_LOAN.getType());

        if (traceRecord.getOldLoanState() != null) {
            traceRecordDO.setTrBidOldState(traceRecord.getOldLoanState().getState());
        }
        if (traceRecord.getNewLoanState() != null) {
            traceRecordDO.setTrBidNewState(traceRecord.getNewLoanState().getState());
        }
        if (traceRecord.getLoanTraceOperation() != null) {
            traceRecordDO.setTrBidTraceOperation(traceRecord.getLoanTraceOperation().getState());
        }

        traceRecordDO.setTrCreator(traceRecord.getCreator());
        traceRecordDO.setTrCreatetime(traceRecord.getCreatetime());
        traceRecordDO.setTrNote(traceRecord.getNote());

        return traceRecordDO;
    }

    public static LoanTraceRecord toTraceRecord(TraceRecordDO traceRecordDO) {
        if (traceRecordDO == null) {
            return null;
        }

        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setId(traceRecordDO.getTrId());
        traceRecord.setLoanId(traceRecordDO.getTrBidId());
        traceRecord.setOldLoanState(LoanState.valueOf(traceRecordDO.getTrBidOldState()));
        traceRecord.setNewLoanState(LoanState.valueOf(traceRecordDO.getTrBidNewState()));
        traceRecord.setLoanTraceOperation(LoanTraceOperation.valueOf(traceRecordDO.getTrBidTraceOperation()));
        traceRecord.setCreator(traceRecordDO.getTrCreator());
        traceRecord.setCreatetime(traceRecordDO.getTrCreatetime());
        traceRecord.setNote(traceRecordDO.getTrNote());

        return traceRecord;
    }

    public static List<LoanTraceRecord> toTraceRecordList(List<TraceRecordDO> traceRecordDOList) {
        if (traceRecordDOList == null) {
            return null;
        }

        List<LoanTraceRecord> traceRecordList = new ArrayList<LoanTraceRecord>(traceRecordDOList.size());
        for (TraceRecordDO traceRecordDO : traceRecordDOList) {
            traceRecordList.add(toTraceRecord(traceRecordDO));
        }

        return traceRecordList;
    }

}
