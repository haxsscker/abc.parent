/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.account.json;

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
import com.autoserve.abc.dao.dataobject.GovAccountDO;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.account.ComAccountVO;

/**
 * 类AccountManage.java的实现描述：查询机构账户列表
 * 
 * @author J.YL 2014年12月2日 下午5:35:41
 */
public class ComAccountManage {

    private final Logger       logger = LoggerFactory.getLogger(ComAccountManage.class);
    @Resource
    private AccountInfoService accountService;

    public JsonPageVO<ComAccountVO> execute(ParameterParser params) {
        //搜索
        String searchForm = params.getString("searchForm");
        GovAccountDO govAccountDO = new GovAccountDO();
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    //机构名称
                    if ("govName".equals(field)) {
                        govAccountDO.setGovName(value);
                    }
                    //法人姓名
                    if ("actLegalName".equals(field)) {
                        govAccountDO.setAccountLegalName(value);
                    }
                   
                    if ("actUserName".equals(field)) {
                        govAccountDO.setAccountUserName(value);
                    }
                    //法人身份证号
                    if ("actUserCard".equals(field)) {
                        govAccountDO.setAccountUserCard(value);
                    }
                    //手机号码
                    if ("actUserPhone".equals(field)) {
                        govAccountDO.setAccountUserPhone(value);
                    }

                }
            } catch (Exception e) {
                logger.error("统计查询条件解析出错！", e);
            }
        }

        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        PageResult<GovAccountDO> pageResult = accountService.queryByUserType(UserType.PARTNER, pageCondition,
                govAccountDO);
        List<ComAccountVO> accountList = new ArrayList<ComAccountVO>();
        List<GovAccountDO> govAccountDOs = pageResult.getData();
        for (GovAccountDO govAccount : govAccountDOs) {
            ComAccountVO comAccount = new ComAccountVO();
            comAccount.setAct_account_id(govAccount.getAccountId());
            comAccount.setGov_name(govAccount.getGovName());       //机构名称
            comAccount.setAct_business_licence(govAccount.getBusinessLicense());   //营业执照号
            comAccount.setAct_user_name(govAccount.getAccountUserName());   //机构平台号
            comAccount.setAct_legal_name(govAccount.getAccountLegalName());  //法人姓名
            comAccount.setAct_user_card(govAccount.getAccountUserCard());    //身份证号(法人身份证号)
            comAccount.setAct_user_email(govAccount.getAccountUserEmail());   //邮箱
            comAccount.setAct_user_phone(govAccount.getAccountUserPhone());   //手机号码
            comAccount.setAct_platform_no(govAccount.getAccountMark());      //第三方开户号            
            accountList.add(comAccount);
        }
        JsonPageVO<ComAccountVO> resultVO = new JsonPageVO<ComAccountVO>();
        resultVO.setRows(accountList);
        resultVO.setTotal(pageResult.getTotalCount());
        return resultVO;
    }
}
