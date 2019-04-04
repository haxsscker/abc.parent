/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.enums.UserType;

/**
 * Account 与 AccountInfoDO 转换
 * 
 * @author J.YL 2014年11月20日 下午4:37:55
 */
public class AccountConverter {
    public static AccountInfoDO toAccountInfoDO(Account account) {
        AccountInfoDO accountDO = new AccountInfoDO();
        if (account == null) {
            return accountDO;
        }
        accountDO.setAccountUserType(account.getAccountUserType().getType());
        accountDO.setAccountUserPhone(account.getAccountUserPhone());
        accountDO.setAccountUserId(account.getAccountUserId());
        accountDO.setAccountUserName(account.getAccountUserName());
        accountDO.setAccountUserEmail(account.getAccountUserEmail());
        accountDO.setAccountUserCard(account.getAccountUserCard());
        accountDO.setAccountUserAccount(account.getAccountUserAccount());
        accountDO.setAccountNo(account.getAccountNo());
        accountDO.setAccountMark(account.getAccountMark());
        accountDO.setAccountLegalName(account.getAccountLegalName());
        accountDO.setAccountBankBranchName(account.getAccountBankBranchName());
        accountDO.setAccountBankName(account.getAccountBankName());
        accountDO.setAccountBankArea(account.getAccountBankArea());
        accountDO.setAccountUserChargeAccount(account.getAccountUserChargeAccount());
        accountDO.setAccountUserChargeName(account.getAccountUserChargeName());
        accountDO.setAccountUserAccountName(account.getAccountUserAccountName());
        accountDO.setAccountUpdateName(account.getAccountUpdateName());
        accountDO.setAccountState(account.getAccountState());
        accountDO.setAccountUserAccountBk(account.getAccountUserAccountBk());
        accountDO.setAccountCategory(account.getAccountCategory());
        return accountDO;
    }

    public static Account toUserAccount(AccountInfoDO accountDO) {
        Account account = new Account();
        if (accountDO == null) {
            return account;
        }
        account.setAccountUserType(UserType.valueOf(accountDO.getAccountUserType()));
        account.setAccountUserPhone(accountDO.getAccountUserPhone());
        account.setAccountUserId(accountDO.getAccountUserId());
        account.setAccountUserName(accountDO.getAccountUserName());
        account.setAccountUserEmail(accountDO.getAccountUserEmail());
        account.setAccountUserCard(accountDO.getAccountUserCard());
        account.setAccountUserAccount(accountDO.getAccountUserAccount());
        account.setAccountNo(accountDO.getAccountNo());
        account.setAccountMark(accountDO.getAccountMark());
        account.setAccountLegalName(accountDO.getAccountLegalName());
        account.setAccountBankBranchName(accountDO.getAccountBankBranchName());
        account.setAccountBankName(accountDO.getAccountBankName());
        account.setAccountBankArea(accountDO.getAccountBankArea());
        account.setAccountUserChargeAccount(accountDO.getAccountUserChargeAccount());
        account.setAccountUserChargeName(accountDO.getAccountUserChargeName());
        account.setAccountUserAccountName(accountDO.getAccountUserAccountName());
        account.setAccountUpdateName(accountDO.getAccountUpdateName());
        account.setAccountState(accountDO.getAccountState());
        account.setAccountUserAccountBk(accountDO.getAccountUserAccountBk());
        account.setAccountCategory(accountDO.getAccountCategory());
        return account;
    }
}
