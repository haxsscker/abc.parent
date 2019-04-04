package com.autoserve.abc.web.module.screen.bank.json;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.MonBankInfo;
import com.autoserve.abc.service.biz.intf.mon.MonBankInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类AddMonBankInfo.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月23日 下午4:27:07
 */
public class AddMonBankInfo {
    private static Logger      logger = LoggerFactory.getLogger(AddMonBankInfo.class);

    @Resource
    private MonBankInfoService monBankInfoService;

    public JsonBaseVO execute(ParameterParser params) {
        MonBankInfo monBankInfo = new MonBankInfo();

        String searchForm = params.getString("bank");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONArray itemsArray = JSON.parseArray(searchForm);

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));

                    if (itemJson.containsKey("funFundName")) {
                        Object fundFundName = itemJson.get("funFundName");
                        monBankInfo.setFunFundName(fundFundName == null ? null : fundFundName.toString());
                    } else if (itemJson.containsKey("monBankName")) {
                        Object monBankName = itemJson.get("monBankName");
                        monBankInfo.setMonBankName(monBankName == null ? null : monBankName.toString());
                    } else if (itemJson.containsKey("monBankCard")) {
                        Object monBankCard = itemJson.get("monBankCard");
                        monBankInfo.setMonBankCard(monBankCard == null ? null : monBankCard.toString());
                    } else if (itemJson.containsKey("monUserNamer")) {
                        Object monUserNamer = itemJson.get("monUserNamer");
                        monBankInfo.setMonUserNamer(monUserNamer == null ? null : monUserNamer.toString());
                    }
                }
            } catch (Exception e) {
                logger.error("银行信息管理 添加参数解析出错", e);
            }
        }

        BaseResult result = monBankInfoService.createMonBankInfo(monBankInfo);

        JsonBaseVO vo = new JsonBaseVO();
        vo.setSuccess(result.isSuccess());
        vo.setMessage(result.getMessage());
        return vo;
    }

}
