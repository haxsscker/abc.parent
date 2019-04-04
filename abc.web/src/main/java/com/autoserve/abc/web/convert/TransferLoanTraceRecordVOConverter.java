package com.autoserve.abc.web.convert;

import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.loan.TransferLoanTraceRecordVO;

/**
 * 类实现描述 VO工具
 * 
 * @author Tiuwer 2015年3月31日 下午3:46:43
 */
public class TransferLoanTraceRecordVOConverter {

    public static TransferLoanTraceRecordVO toTransferLoanTraceRecordConvert(TransferLoanTraceRecord transferLoanTraceRecord) {
        if (transferLoanTraceRecord == null) {
            return null;
        }
        TransferLoanTraceRecordVO transferLoanTraceRecordVO = new TransferLoanTraceRecordVO();
        transferLoanTraceRecordVO.setId(transferLoanTraceRecord.getId());
        transferLoanTraceRecordVO.setLoanId(transferLoanTraceRecord.getLoanId());
        transferLoanTraceRecordVO.setCreatetime(DateUtil.formatDate(transferLoanTraceRecord.getCreatetime(),
                DateUtil.DEFAULT_FORMAT_STYLE));
        transferLoanTraceRecordVO.setCreator(transferLoanTraceRecord.getCreator());
        if (transferLoanTraceRecord.getNewTransferLoanState() != null)
            transferLoanTraceRecordVO.setNewTransferLoanState(transferLoanTraceRecord.getNewTransferLoanState().prompt);
        transferLoanTraceRecordVO.setNote(transferLoanTraceRecord.getNote());
        if (transferLoanTraceRecord.getOldTransferLoanState() != null)
            transferLoanTraceRecordVO.setOldTransferLoanState(transferLoanTraceRecord.getOldTransferLoanState()
                    .getPrompt());
        transferLoanTraceRecordVO.setTransferLoanId(transferLoanTraceRecord.getLoanId());
        if (transferLoanTraceRecord.getTransferLoanTraceOperation() != null)
            transferLoanTraceRecordVO.setTransferLoanTraceOperation(transferLoanTraceRecord
                    .getTransferLoanTraceOperation().getPrompt());
        return transferLoanTraceRecordVO;

    }

}
