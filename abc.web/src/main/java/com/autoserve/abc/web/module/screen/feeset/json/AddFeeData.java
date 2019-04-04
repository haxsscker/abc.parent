package com.autoserve.abc.web.module.screen.feeset.json;

import java.math.BigDecimal;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.search.FeeSettingSearchDO;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.enums.ChargeType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class AddFeeData {
    @Resource
    private FeeSettingService feeSettingService;

    public JsonBaseVO execute(ParameterParser params) {
        Integer sys_fee_type = params.getInt("feeType");
        Integer sys_min_money = params.getInt("sys_min_money");
        Integer sys_max_money = params.getInt("sys_max_money");
        Integer sys_collect_type = params.getInt("sys_collect_type");
        Double sys_fee_rate = params.getDouble("sys_fee_rate");
        //Double accurateAmount = params.getDouble("accurateAmount");
        Integer sys_product_id = params.getInt("sys_product_id");
        

        FeeSetting feeSetting = new FeeSetting();
        feeSetting.setFeeType(FeeType.valueOf(sys_fee_type));
        feeSetting.setMinAmount(BigDecimal.valueOf(sys_min_money));
        feeSetting.setMaxAmount(BigDecimal.valueOf(sys_max_money));
        feeSetting.setChargeType(ChargeType.valueOf(sys_collect_type));
        feeSetting.setLoanCategory(LoanCategory.valueOf(sys_product_id));
        if(ChargeType.valueOf(sys_collect_type) == ChargeType.BY_DEAL){
        	feeSetting.setAccurateAmount(BigDecimal.valueOf(sys_fee_rate));        	
        }
        if(ChargeType.valueOf(sys_collect_type) == ChargeType.BY_RATIO){
        	feeSetting.setRate(sys_fee_rate);        	
        }
        BaseResult result = new BaseResult();
        FeeSettingSearchDO feeSettingSearchDO = new FeeSettingSearchDO();
        feeSettingSearchDO.setProductType(sys_product_id);
        boolean flag = true;
        ListResult<FeeSetting> ListResult = this.feeSettingService.queryByFeeType(FeeType.valueOf(sys_fee_type), feeSettingSearchDO);
        for(FeeSetting FeeSetting : ListResult.getData()){
        	if(BigDecimal.valueOf(sys_min_money).compareTo(FeeSetting.getMaxAmount())==1||BigDecimal.valueOf(sys_max_money).compareTo(FeeSetting.getMinAmount())==-1){
        		flag = true;
        	}else{
            	result.setMessage("费用设置区间有重复");
            	result.setSuccess(false);
        		return ResultMapper.toBaseVO(result);
        	}
        }
		result = feeSettingService.creatFeeSetting(feeSetting);
        return ResultMapper.toBaseVO(result);
    }
}
