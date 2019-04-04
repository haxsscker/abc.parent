package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.FundCheckDO;
import com.autoserve.abc.service.biz.entity.FundCheck;
import com.autoserve.abc.service.biz.enums.FundCheckState;

public class FundCheckConverter {

    public static FundCheckDO toFundCheckDO(FundCheck fundCheck) {
    	FundCheckDO fundCheckDO = new FundCheckDO();
    	fundCheckDO.setFcCheckId(fundCheck.getFcCheckId());
    	fundCheckDO.setFcOrderId(fundCheck.getFcOrderId());
    	fundCheckDO.setFcCheckEmp(fundCheck.getFcCheckEmp());
    	fundCheckDO.setFcCheckDate(fundCheck.getFcCheckDate());
    	fundCheckDO.setFcCheckIdear(fundCheck.getFcCheckIdear());
    	fundCheckDO.setFcRechargeMoney(fundCheck.getFcRechargeMoney());
    	fundCheckDO.setFcRechargeDate(fundCheck.getFcRechargeDate());
    	fundCheckDO.setFcCheckState(fundCheck.getFcCheckState().getCheckState());
    	return fundCheckDO;
    }

    public static FundCheck toFundCheck(FundCheckDO fundCheckDO) {
    	FundCheck fundCheck = new FundCheck();
    	fundCheck.setFcCheckId(fundCheckDO.getFcCheckId());
    	fundCheck.setFcOrderId(fundCheckDO.getFcOrderId());
    	fundCheck.setFcCheckEmp(fundCheckDO.getFcCheckEmp());
    	fundCheck.setFcCheckDate(fundCheckDO.getFcCheckDate());
    	fundCheck.setFcCheckIdear(fundCheckDO.getFcCheckIdear());
    	fundCheck.setFcRechargeMoney(fundCheckDO.getFcRechargeMoney());
    	fundCheck.setFcRechargeDate(fundCheckDO.getFcRechargeDate());
    	fundCheck.setFcCheckState(FundCheckState.valueOf(fundCheckDO.getFcCheckState()));
        return fundCheck;
    }

}
