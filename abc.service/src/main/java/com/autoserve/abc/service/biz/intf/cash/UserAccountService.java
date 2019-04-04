/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import java.util.List;

import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.UserAccountDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.UserAccountMoney;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 用户资金账户详情Service
 * 
 * @author J.YL 2014年11月26日 上午11:34:58
 */
public interface UserAccountService {
    /**
     * 通过账户号查询账户财务信息
     * 
     * @param accountNo
     * @return
     */
    public PlainResult<UserAccountMoney> queryUserAccountByAccountNo(String accountNo);

    /**
     * 更新用户账户信息表的财务状况（平台模式） 除充值和提现以外的交易类型 已弃用
     * 
     * @param userAccounts
     * @return
     */
    public BaseResult updateUserAccountFinancial(List<Account> userAccounts, List<DealRecordDO> dealRecords);

    /**
     * 更新第三方支付返回通知单的交易类型的用户资金
     * 
     * @param userAccounts
     * @param dealRecords
     * @return
     */
    public BaseResult updateThridPartReturn(List<Account> userAccounts, List<DealRecordDO> dealRecords);

    /**
     * 通过用户账户查询用户资金状态
     * 
     * @param accountNo
     * @return
     */
    public ListResult<UserAccountDO> queryByAccountNo(List<String> accountNo);

    /**
     * 批量创建用户资金记录
     * 
     * @param userAccounts
     * @return
     */
    public PlainResult<Integer> batchCreateUserAccount(List<UserAccountDO> userAccounts);

    /**
     * 创建用户资金记录
     * 
     * @param accountNo
     * @return
     */
    public BaseResult createUserAccount(String accountNo);

}
