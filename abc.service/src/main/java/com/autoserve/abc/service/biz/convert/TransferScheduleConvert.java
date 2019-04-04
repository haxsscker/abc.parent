package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.TransferScheduleDO;
import com.autoserve.abc.service.biz.entity.TransferSchedule;
import com.autoserve.abc.service.biz.enums.ScheduleStage;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.util.DateUtil;

/**
 * 转让进度转换
 *
 * @author liuwei 2015年1月6日 下午3:42:07
 */
public class TransferScheduleConvert {

    public static TransferSchedule toTransferSchedule(TransferScheduleDO transferScheduleDO) {
        if (transferScheduleDO == null) {
            return null;
        }
        TransferSchedule transferSchedule = new TransferSchedule();
        transferSchedule.setId(transferScheduleDO.getTsId());
        transferSchedule.setLoanId(transferScheduleDO.getTsLoanId());
        if (transferScheduleDO.getTsName() != null) {
            transferSchedule.setName(ScheduleStage.valueOf(transferScheduleDO.getTsName()).des);
        }

        transferSchedule.setOperateTime(transferScheduleDO.getTsOperateTime());
        transferSchedule.setStrOperateTime(DateUtil.formatDay(transferScheduleDO.getTsOperateTime()));
        transferSchedule.setOperatorId(transferScheduleDO.getTsOperatorId());
        if (transferScheduleDO.getTsState() != null) {
            transferSchedule.setStateName(TransferLoanState.valueOf(transferScheduleDO.getTsState()).prompt);
        }
        transferSchedule.setTransferLoanId(transferScheduleDO.getTsTransferLoanId());
        transferSchedule.setOperator(transferScheduleDO.getOperator());
        return transferSchedule;
    }
}
