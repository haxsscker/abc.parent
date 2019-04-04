package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.AutoTransferDO;
import com.autoserve.abc.service.biz.entity.AutoTransfer;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.ReviewState;

/**
 * 类AutoTransferConvert.java的实现描述：TODO 类实现描述
 * 
 * @author Tiuwer 2015年4月23日 上午11:52:21
 */
public class AutoTransferConvert {

    public static AutoTransferDO toAutoTransferDO(AutoTransfer autoTransfer) {
        AutoTransferDO autoTransferDO = new AutoTransferDO();
        if (autoTransfer.getAuditState() != null)
            autoTransferDO.setAtAuditState(autoTransfer.getAuditState().state);
        autoTransferDO.setAtId(autoTransfer.getId());
        autoTransferDO.setAtMoneyAmount(autoTransfer.getMoneyAmount());
        autoTransferDO.setAtMoneyType(autoTransfer.getMoneyType());
        autoTransferDO.setAtOperateDate(autoTransfer.getOperateDate());
        autoTransferDO.setAtOperator(autoTransfer.getOperator());
        autoTransferDO.setAtOutSeqNo(autoTransfer.getOutSeqNo());
        autoTransferDO.setAtPayAccotunt(autoTransfer.getPayAccotunt());
        autoTransferDO.setAtReceibeAccotunt(autoTransfer.getReceibeAccotunt());
        if (autoTransfer.getState() != null)
            autoTransferDO.setAtState(autoTransfer.getState().state);
        autoTransferDO.setAtReceibeUserId(autoTransfer.getReceibeUserId());
        autoTransferDO.setAtRemarks(autoTransfer.getAtRemarks());
        return autoTransferDO;
    }

    public static AutoTransfer toAutoTransfer(AutoTransferDO autoTransferDO) {
        AutoTransfer autoTransfer = new AutoTransfer();
        if (autoTransferDO.getAtAuditState() != null)
            autoTransfer.setAuditState(ReviewState.valueOf(autoTransferDO.getAtAuditState()));
        autoTransfer.setId(autoTransferDO.getAtId());
        autoTransfer.setMoneyAmount(autoTransferDO.getAtMoneyAmount());
        autoTransfer.setMoneyType(autoTransferDO.getAtMoneyType());
        autoTransfer.setOperateDate(autoTransferDO.getAtOperateDate());
        autoTransfer.setOperator(autoTransferDO.getAtOperator());
        autoTransfer.setOutSeqNo(autoTransferDO.getAtOutSeqNo());
        autoTransfer.setPayAccotunt(autoTransferDO.getAtPayAccotunt());
        autoTransfer.setReceibeAccotunt(autoTransferDO.getAtReceibeAccotunt());
        autoTransfer.setReceibeUserId(autoTransferDO.getAtReceibeUserId());
        autoTransfer.setAtRemarks(autoTransferDO.getAtRemarks());
        if (autoTransferDO.getAtState() != null)
            autoTransfer.setState(DealState.valueOf(autoTransferDO.getAtState()));
        return autoTransfer;
    }

    public static List<AutoTransfer> toAutoTransferList(List<AutoTransferDO> listDO) {
        List<AutoTransfer> list = new ArrayList<AutoTransfer>();
        for (AutoTransferDO autoTransferDO : listDO) {
            list.add(toAutoTransfer(autoTransferDO));
        }
        return list;
    }
}
