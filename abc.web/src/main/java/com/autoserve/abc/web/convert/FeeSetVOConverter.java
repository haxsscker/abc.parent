package com.autoserve.abc.web.convert;

import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.enums.ChargeType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.web.vo.feeset.FeeSettingVO;

/**
 * VO转换工具 类FeeListViewConvert.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月9日 上午10:35:28
 */

public class FeeSetVOConverter {

    /**
     * @param feeSeting
     * @return
     */
    public static FeeSettingVO toFeeSetingVO(FeeSetting feeSeting) {
        FeeSettingVO vo = new FeeSettingVO();
        if (feeSeting == null) {
            return vo;
        }

        vo.setAccurateAmount(feeSeting.getAccurateAmount());
        vo.setSys_max_money(feeSeting.getMaxAmount());
        vo.setSys_min_money(feeSeting.getMinAmount());

        if (feeSeting.getFeeType() != null) {
            vo.setSys_fee_type(feeSeting.getFeeType().type);
        }

        if (feeSeting.getChargeType() != null) {
            vo.setSys_collect_type(feeSeting.getChargeType().type);
                vo.setSys_fee_rate(feeSeting.getRate());
       
        }

        vo.setSys_fee_id(feeSeting.getId());
        if (feeSeting.getLoanCategory() != null)
            vo.setSys_product_id(feeSeting.getLoanCategory().category);
        vo.setUpdateTime(feeSeting.getUpdateTime());

        return vo;
    }

    public static FeeSetting toFeeSeting(FeeSettingVO feeSettingVO) {
        FeeSetting feeSetting = new FeeSetting();
        if (feeSettingVO == null) {
            return feeSetting;
        }

        feeSetting.setAccurateAmount(feeSettingVO.getAccurateAmount());
        feeSetting.setMaxAmount(feeSettingVO.getSys_max_money());
        feeSetting.setMinAmount(feeSettingVO.getSys_min_money());
        feeSetting.setChargeType(ChargeType.valueOf(feeSettingVO.getSys_collect_type()));
        feeSetting.setId(feeSettingVO.getSys_fee_id());
        feeSetting.setRate(feeSettingVO.getSys_fee_rate());
        feeSetting.setFeeType(FeeType.valueOf(feeSettingVO.getSys_fee_type()));
        feeSetting.setLoanCategory(LoanCategory.valueOf(feeSettingVO.getSys_product_id()));
        feeSetting.setUpdateTime(feeSettingVO.getUpdateTime());

        return feeSetting;
    }

}
