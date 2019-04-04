package com.autoserve.abc.service.biz.intf.sys;

import java.math.BigDecimal;

import com.autoserve.abc.dao.dataobject.search.FeeSettingSearchDO;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-11-26,10:39
 */
public interface FeeSettingService {
    public BaseResult creatFeeSetting(FeeSetting feeSetting);

    public BaseResult modifyFeeSetting(FeeSetting feeSetting);

    public ListResult<FeeSetting> queryByFeeType(FeeType feeType, FeeSettingSearchDO feeSettingSearchDO);

    public PlainResult<FeeSetting> queryByFeeTypeLoanCategory(FeeType feeType, LoanCategory loanCategory,
                                                              BigDecimal feeMoney);

    public BaseResult deleteFeeSetting(int feeSettingId);

    /**
     * 计算手续费
     * 
     * @param loanId
     * @param feeType
     * @return
     */
    PlainResult<BigDecimal> calcFee(Integer loanId, FeeType feeType);
	/**
	 * 计算手续费
	 * @param loanId
	 * @return
	 */
	PlainResult<BigDecimal> calcFee(Integer loanId);
}
