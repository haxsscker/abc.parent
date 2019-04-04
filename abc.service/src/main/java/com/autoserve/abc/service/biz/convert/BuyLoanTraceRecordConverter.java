/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.TraceRecordDO;
import com.autoserve.abc.service.biz.entity.BuyLoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.enums.BuyLoanTraceOperation;

/**
 * BuyLoanTraceRecordDO与BuyLoanTraceRecord互相转换
 *
 * @author segen189 2014年11月21日 下午8:47:13
 */
public class BuyLoanTraceRecordConverter {

    public static TraceRecordDO toTraceRecordDO(BuyLoanTraceRecord traceRecord) {
        if (traceRecord == null) {
            return null;
        }

        TraceRecordDO traceRecordDO = new TraceRecordDO();

        traceRecordDO.setTrId(traceRecord.getId());
        traceRecordDO.setTrLoanId(traceRecord.getLoanId());
        traceRecordDO.setTrBidId(traceRecord.getBuyLoanId());
        traceRecordDO.setTrBidType(BidType.BUY_LOAN.getType());

        if (traceRecord.getOldBuyLoanState() != null) {
            traceRecordDO.setTrBidOldState(traceRecord.getOldBuyLoanState().getState());
        }
        if (traceRecord.getNewBuyLoanState() != null) {
            traceRecordDO.setTrBidNewState(traceRecord.getNewBuyLoanState().getState());
        }
        if (traceRecord.getBuyLoanTraceOperation() != null) {
            traceRecordDO.setTrBidTraceOperation(traceRecord.getBuyLoanTraceOperation().getState());
        }

        traceRecordDO.setTrCreator(traceRecord.getCreator());
        traceRecordDO.setTrCreatetime(traceRecord.getCreatetime());
        traceRecordDO.setTrNote(traceRecord.getNote());

        return traceRecordDO;
    }

    public static BuyLoanTraceRecord toTraceRecord(TraceRecordDO traceRecordDO) {
        if (traceRecordDO == null) {
            return null;
        }

        BuyLoanTraceRecord traceRecord = new BuyLoanTraceRecord();

        traceRecord.setId(traceRecordDO.getTrId());
        traceRecord.setLoanId(traceRecordDO.getTrLoanId());
        traceRecord.setBuyLoanId(traceRecordDO.getTrBidId());
        traceRecord.setOldBuyLoanState(BuyLoanState.valueOf(traceRecordDO.getTrBidOldState()));
        traceRecord.setNewBuyLoanState(BuyLoanState.valueOf(traceRecordDO.getTrBidNewState()));
        traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.valueOf(traceRecordDO.getTrBidTraceOperation()));
        traceRecord.setCreator(traceRecordDO.getTrCreator());
        traceRecord.setCreatetime(traceRecordDO.getTrCreatetime());
        traceRecord.setNote(traceRecordDO.getTrNote());

        return traceRecord;
    }

    public static List<BuyLoanTraceRecord> toTraceRecordList(List<TraceRecordDO> traceRecordDOList) {
        if (traceRecordDOList == null) {
            return null;
        }

        List<BuyLoanTraceRecord> traceRecordList = new ArrayList<BuyLoanTraceRecord>(traceRecordDOList.size());
        for (TraceRecordDO traceRecordDO : traceRecordDOList) {
            traceRecordList.add(toTraceRecord(traceRecordDO));
        }

        return traceRecordList;
    }

}
