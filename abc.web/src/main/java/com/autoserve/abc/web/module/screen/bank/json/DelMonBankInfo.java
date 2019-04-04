package com.autoserve.abc.web.module.screen.bank.json;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.mon.MonBankInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类AddMonBankInfo.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月23日 下午4:27:07
 */
public class DelMonBankInfo {
    @Resource
    private MonBankInfoService monBankInfoService;

    public JsonBaseVO execute(@Param("cuiId") int monBankId) {
        BaseResult result = monBankInfoService.removeMonBankInfo(monBankId);
        JsonBaseVO vo = new JsonBaseVO();
        vo.setSuccess(result.isSuccess());
        vo.setMessage(result.getMessage());
        return vo;
    }
}
