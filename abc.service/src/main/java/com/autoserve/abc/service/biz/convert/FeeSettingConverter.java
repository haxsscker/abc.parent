package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.FeeSettingDO;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.enums.ChargeType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author yuqing.zheng Created on 2014-11-25,17:05
 */
public class FeeSettingConverter {
    public static FeeSettingDO toFeeSettingDO(FeeSetting feeSetting) {
        FeeSettingDO feeSettingDO = new FeeSettingDO();
        if (feeSetting == null) {
            return feeSettingDO;
        }

        feeSettingDO.setFsId(feeSetting.getId());
        feeSettingDO.setFsType(feeSetting.getFeeType().type);
        feeSettingDO.setFsLoanCategory(feeSetting.getLoanCategory().category);
        feeSettingDO.setFsChargeType(feeSetting.getChargeType().type);
        feeSettingDO.setFsRate(feeSetting.getRate());
        feeSettingDO.setFsMinAmount(feeSetting.getMinAmount());
        feeSettingDO.setFsMaxAmount(feeSetting.getMaxAmount());
        feeSettingDO.setFsAccurateAmount(feeSetting.getAccurateAmount());
        feeSettingDO.setFsUpdateTime(feeSetting.getUpdateTime());

        return feeSettingDO;
    }

    public static FeeSetting toFeeSetting(FeeSettingDO feeSettingDO) {
        FeeSetting feeSetting = new FeeSetting();
        if (feeSettingDO == null) {
            return feeSetting;
        }

        feeSetting.setId(feeSettingDO.getFsId());
        feeSetting.setFeeType(FeeType.valueOf(feeSettingDO.getFsType()));
        feeSetting.setLoanCategory(LoanCategory.valueOf(feeSettingDO.getFsLoanCategory()));
        feeSetting.setChargeType(ChargeType.valueOf(feeSettingDO.getFsChargeType()));
        feeSetting.setRate(feeSettingDO.getFsRate());
        feeSetting.setMinAmount(feeSettingDO.getFsMinAmount());
        feeSetting.setMaxAmount(feeSettingDO.getFsMaxAmount());
        feeSetting.setAccurateAmount(feeSettingDO.getFsAccurateAmount());
        feeSetting.setUpdateTime(feeSettingDO.getFsUpdateTime());

        return feeSetting;
    }

    public static List<FeeSetting> toFeeSettingList(List<FeeSettingDO> feeSettingDOs) {
        if (CollectionUtils.isEmpty(feeSettingDOs)) {
            return Collections.emptyList();
        }

        List<FeeSetting> feeSettings = Lists.newArrayListWithCapacity(feeSettingDOs.size());
        for (FeeSettingDO feeSettingDO : feeSettingDOs) {
            feeSettings.add(toFeeSetting(feeSettingDO));
        }

        return feeSettings;
    }
}
