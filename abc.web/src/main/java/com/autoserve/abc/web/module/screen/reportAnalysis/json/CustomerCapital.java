package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 用户资金明细
 */
public class CustomerCapital {
    @Resource
    private CashInvesterService cashInvesterService;

    @Resource
    private UserAccountService  userAccountService;

    @Resource
    private AccountInfoService  accountInfoService;

    private static Logger logger = LoggerFactory.getLogger(CustomerCapital.class);
    public JsonPageVO<AccountInfoDO> execute(ParameterParser params) {
        String investorRealName = "";
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    // 真实姓名
                    if ("investorRealName".equals(field)) {
                    	investorRealName = value;
                    }
                }
            } catch (Exception e) {
                logger.error("客户信息－个人客户－搜索查询 查询参数解析出错", e);
            }
        }
        
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        JsonPageVO<AccountInfoDO> resultVO = new JsonPageVO<AccountInfoDO>();
        PageResult<AccountInfoDO> queryResult = accountInfoService.qureyAccountByRealName(investorRealName, pageCondition);
        if (!queryResult.isSuccess()) {
            resultVO.setMessage(queryResult.getMessage());
            resultVO.setTotal(0);
            resultVO.setRows(new ArrayList<AccountInfoDO>());
            return resultVO;
        }
          
        resultVO.setTotal(queryResult.getTotalCount());
        resultVO.setRows(queryResult.getData());
        return resultVO;
    }
}
