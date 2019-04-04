package com.autoserve.abc.web.module.screen.bank.json;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.MonBankInfoDO;
import com.autoserve.abc.service.biz.intf.mon.MonBankInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.module.screen.selfprove.json.AccountManagementListView;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 有限合伙银行账户管理
 * 
 * @author dengjingyu 2014年12月23日 下午4:22:48
 */
public class GetMonBankInfoList {
    private static Logger      logger = LoggerFactory.getLogger(AccountManagementListView.class);
    @Resource
    private MonBankInfoService monBankInfoService;

    public JsonPageVO<MonBankInfoDO> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        MonBankInfoDO monBankInfoDO = new MonBankInfoDO();

        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    // 基金名称
                    if ("funFundName".equals(field)) {
                        monBankInfoDO.setFunFundName(value);
                    }
                    // 银行名称
                    else if ("monBankName".equals(field)) {
                        monBankInfoDO.setMonBankName(value);
                    }
                    // 银行卡号
                    else if ("monBankCard".equals(field)) {
                        monBankInfoDO.setMonBankCard(value);
                    }
                    // 账户户名
                    else if ("monUserNamer".equals(field)) {
                        monBankInfoDO.setMonUserNamer(value);
                    }
                }
            } catch (Exception e) {
                logger.error("搜索查询 查询参数解析出错", e);
            }
        }
        PageResult<MonBankInfoDO> pageResult = monBankInfoService.queryList(monBankInfoDO, pageCondition);
        return ResultMapper.toPageVO(pageResult);
    }
}
