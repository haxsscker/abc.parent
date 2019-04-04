/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.TraceRecordDO;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanTraceOperation;

/**
 * TransferLoanTraceRecordDO与TransferLoanTraceRecord互相转换
 * 
 * @author segen189 2014年11月21日 下午8:47:13
 */
public class TransferLoanTraceRecordConverter {

    public static TraceRecordDO toTraceRecordDO(TransferLoanTraceRecord traceRecord) {
        if (traceRecord == null) {
            return null;
        }

        TraceRecordDO traceRecordDO = new TraceRecordDO();

        traceRecordDO.setTrId(traceRecord.getId());
        traceRecordDO.setTrLoanId(traceRecord.getLoanId());
        traceRecordDO.setTrBidId(traceRecord.getTransferLoanId());
        traceRecordDO.setTrBidType(BidType.TRANSFER_LOAN.getType());

        if (traceRecord.getOldTransferLoanState() != null) {
            traceRecordDO.setTrBidOldState(traceRecord.getOldTransferLoanState().getState());
        }
        if (traceRecord.getNewTransferLoanState() != null) {
            traceRecordDO.setTrBidNewState(traceRecord.getNewTransferLoanState().getState());
        }
        if (traceRecord.getTransferLoanTraceOperation() != null) {
            traceRecordDO.setTrBidTraceOperation(traceRecord.getTransferLoanTraceOperation().getState());
        }

        traceRecordDO.setTrCreator(traceRecord.getCreator());
        traceRecordDO.setTrCreatetime(traceRecord.getCreatetime());
        traceRecordDO.setTrNote(traceRecord.getNote());

        return traceRecordDO;
    }

    public static TransferLoanTraceRecord toTraceRecord(TraceRecordDO traceRecordDO) {
        if (traceRecordDO == null) {
            return null;
        }

        TransferLoanTraceRecord traceRecord = new TransferLoanTraceRecord();

        traceRecord.setId(traceRecordDO.getTrId());
        traceRecord.setLoanId(traceRecordDO.getTrLoanId());
        traceRecord.setTransferLoanId(traceRecordDO.getTrBidId());
        traceRecord.setOldTransferLoanState(TransferLoanState.valueOf(traceRecordDO.getTrBidOldState()));
        traceRecord.setNewTransferLoanState(TransferLoanState.valueOf(traceRecordDO.getTrBidNewState()));
        traceRecord.setTransferLoanTraceOperation(TransferLoanTraceOperation.valueOf(traceRecordDO
                .getTrBidTraceOperation()));
        traceRecord.setCreator(traceRecordDO.getTrCreator());
        traceRecord.setCreatetime(traceRecordDO.getTrCreatetime());
        traceRecord.setNote(traceRecordDO.getTrNote());

        return traceRecord;
    }

    public static List<TransferLoanTraceRecord> toTraceRecordList(List<TraceRecordDO> traceRecordDOList) {
        if (traceRecordDOList == null) {
            return null;
        }

        List<TransferLoanTraceRecord> traceRecordList = new ArrayList<TransferLoanTraceRecord>(traceRecordDOList.size());
        for (TraceRecordDO traceRecordDO : traceRecordDOList) {
            traceRecordList.add(toTraceRecord(traceRecordDO));
        }

        return traceRecordList;
    }

}
