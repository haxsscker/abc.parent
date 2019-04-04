/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.account.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 查询用户账户信息
 * 
 * @author J.YL 2014年12月2日 下午4:22:31
 */
public class UserAccountList {
    @Resource
    private AccountInfoService accountInfoService;
    @Resource
    private HttpServletRequest req;

    public JsonPageVO<UserAccountVO> execute(ParameterParser params) {
        JsonPageVO<UserAccountVO> returnResult = new JsonPageVO<UserAccountVO>();
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        AccountInfoDO queryAccount = new AccountInfoDO();
        String userName = params.getString("act_user_name");
        String accountNo = params.getString("act_account_no");
        String userPhone = params.getString("act_user_phone");
        String userType = params.getString("act_user_type");

        if (userName != null || accountNo != null || userPhone != null || userType != null) {
            queryAccount.setAccountNo(accountNo);
            queryAccount.setAccountUserName(userName);
            if (userType != null) {
                if (userType.equals(UserType.PERSONAL.getDes())) {
                    queryAccount.setAccountUserType(UserType.PERSONAL.getType());
                } else if (userType.equals(UserType.ENTERPRISE.getDes())) {
                    queryAccount.setAccountUserType(UserType.ENTERPRISE.getType());
                } else if (userType.equals(UserType.PARTNER.getDes())) {
                    queryAccount.setAccountUserType(UserType.PARTNER.getType());
                }
            }
            queryAccount.setAccountUserPhone(userPhone);
        }
        PageResult<AccountInfoDO> queryResult = accountInfoService.queryByAccount(queryAccount, pageCondition);
        List<AccountInfoDO> accountData = queryResult.getData();
        List<UserAccountVO> result = new ArrayList<UserAccountVO>();
        for (AccountInfoDO ac : accountData) {
            UserAccountVO temp = new UserAccountVO();
            temp.setAct_account_no(ac.getAccountNo());
            temp.setAct_user_name(ac.getAccountUserName());
            temp.setAct_user_id(ac.getAccountUserId());
            temp.setAct_user_phone(ac.getAccountUserPhone());
            temp.setAct_user_type(UserType.valueOf(ac.getAccountUserType()).getDes());
            temp.setAct_user_card(ac.getAccountUserCard());
            temp.setAct_user_email(ac.getAccountUserEmail());
            result.add(temp);
        }
        returnResult.setTotal(queryResult.getTotalCount());
        returnResult.setRows(result);
        return returnResult;
    }

    class UserAccountVO {
        private Integer act_user_id;
        private String  act_user_name;
        private String  act_account_no;
        private String  act_user_phone;
        private String  act_user_type;
        private String  act_user_card;

        public String getAct_user_card() {
            return act_user_card;
        }

        public void setAct_user_card(String act_user_card) {
            this.act_user_card = act_user_card;
        }

        public String getAct_user_email() {
            return act_user_email;
        }

        public void setAct_user_email(String act_user_email) {
            this.act_user_email = act_user_email;
        }

        private String act_user_email;

        public String getAct_user_name() {
            return act_user_name;
        }

        public void setAct_user_name(String act_user_name) {
            this.act_user_name = act_user_name;
        }

        public String getAct_account_no() {
            return act_account_no;
        }

        public void setAct_account_no(String act_account_no) {
            this.act_account_no = act_account_no;
        }

        public String getAct_user_phone() {
            return act_user_phone;
        }

        public void setAct_user_phone(String act_user_phone) {
            this.act_user_phone = act_user_phone;
        }

        public String getAct_user_type() {
            return act_user_type;
        }

        public void setAct_user_type(String act_user_type) {
            this.act_user_type = act_user_type;
        }

        public Integer getAct_user_id() {
            return act_user_id;
        }

        public void setAct_user_id(Integer act_user_id) {
            this.act_user_id = act_user_id;
        }

    }
}
