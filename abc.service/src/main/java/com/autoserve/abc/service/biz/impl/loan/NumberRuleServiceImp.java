/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.citrus.util.StringUtil;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.loan.NumberRuleService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.DateUtil;

/**
 * 项目编号模板规则
 * 
 * @author segen189 2015年1月1日 下午10:43:51
 */
@Service
public class NumberRuleServiceImp implements NumberRuleService {

    @Resource
    private SysConfigService sysConfigService;

    @Override
    public PlainResult<String> queryNumberRule() {
        PlainResult<String> result = new PlainResult<String>();

        PlainResult<SysConfig> numberRuleResult = sysConfigService.querySysConfig(SysConfigEntry.NUMBER_RULE);
        if (!numberRuleResult.isSuccess()) {
            result.setError(CommonResultCode.BIZ_ERROR, "项目编号配置查询失败");
            return result;
        }

        result.setData(numberRuleResult.getData().getConfValue());
        return result;
    }

    @Override
    public PlainResult<String> createNumberByNumberRule(int originNumber) {
        PlainResult<String> result = new PlainResult<String>();

        PlainResult<SysConfig> numberRuleResult = sysConfigService.querySysConfig(SysConfigEntry.NUMBER_RULE);
        if (!numberRuleResult.isSuccess()) {
            result.setError(CommonResultCode.BIZ_ERROR, "项目编号配置查询失败");
            return result;
        }

        String rawData = String.format(numberRuleResult.getData().getConfValue(), originNumber);
        String data = DateUtil.formatDate(new Date(), rawData);
        result.setData(data);

        return result;
    }

    @Override
    public PlainResult<String> createNumberByYear() {
        PlainResult<String> result = new PlainResult<String>();

        PlainResult<SysConfig> numberResult = sysConfigService.querySysConfig(SysConfigEntry.PRO_NUMBER);
        if (!numberResult.isSuccess()) {
            result.setError(CommonResultCode.BIZ_ERROR, "项目编号初始值配置查询失败");
            return result;
        }
        PlainResult<SysConfig> numberRuleResult = sysConfigService.querySysConfig(SysConfigEntry.NUMBER_RULE);
        if (!numberRuleResult.isSuccess()) {
            result.setError(CommonResultCode.BIZ_ERROR, "项目编号配置查询失败");
            return result;
        }

        PlainResult<SysConfig> yearRuleResult = sysConfigService.querySysConfig(SysConfigEntry.NUMBER_YEAR);
        if (!yearRuleResult.isSuccess()) {
            result.setError(CommonResultCode.BIZ_ERROR, "项目编号年份配置查询失败");
            return result;
        }

        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR);

        Integer no = Integer.valueOf(numberResult.getData().getConfValue()) + 1;

        if (yearRuleResult.getData() != null && year > Integer.valueOf(yearRuleResult.getData().getConfValue())) {

            no = 1;

            this.sysConfigService.modifySysConfig(SysConfigEntry.PRO_NUMBER, "1");

            this.sysConfigService.modifySysConfig(SysConfigEntry.NUMBER_YEAR, year.toString());

        } else {

            this.sysConfigService.modifySysConfig(SysConfigEntry.PRO_NUMBER, no.toString());
        }

        String rawData = String.format(numberRuleResult.getData().getConfValue(), no);
        String data = DateUtil.formatDate(new Date(), rawData);
        result.setData(data);
        return result;
    }

    @Override
    public BaseResult modifyNumberRule(String newNumberRule) {
        if (StringUtil.isBlank(newNumberRule)) {
            BaseResult result = new BaseResult();
            result.setError(CommonResultCode.ILLEGAL_PARAM_BLANK);
            return result;
        }

        try {
            int demoLoanId = 88;
            String numberData = String.format(newNumberRule, demoLoanId);
            DateUtil.formatDate(new Date(), numberData);
        } catch (Exception e) {
            BaseResult result = new BaseResult();
            result.setError(CommonResultCode.BIZ_ERROR, "项目编号配置修改失败");
            return result;
        }

        return sysConfigService.modifySysConfig(SysConfigEntry.NUMBER_RULE, newNumberRule);
    }

}
