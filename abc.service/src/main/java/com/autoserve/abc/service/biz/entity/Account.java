/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.UserType;

/**
 * 用户资金账户entity
 * 
 * @author J.YL 2014年11月18日 上午11:13:54
 */
public class Account {
    private Integer  accountUserId;

    /**
     * 用户类型 abc_account_info.account_user_type
     */
    private UserType accountUserType;

    /**
     * 法人姓名 abc_account_info.account_legal_name
     */
    private String   accountLegalName;

    /**
     * 用户姓名(企业名称) abc_account_info.account_user_name
     */
    private String   accountUserName;

    /**
     * 身份证号(法人身份证号) abc_account_info.account_user_card
     */
    private String   accountUserCard;

    /**
     * 开户账户 abc_account_info.account_no
     */
    private String   accountNo;

    /**
     * 手机号码(法人手机号码) abc_account_info.account_user_phone
     */
    private String   accountUserPhone;

    /**
     * 邮箱地址(法人邮箱地址) abc_account_info.account_user_email
     */
    private String   accountUserEmail;
    /**
     * 开户银行地区 abc_account_info.account_bank_area
     */
    private String   accountBankArea;

    /**
     * 开户银行名称 abc_account_info.account_bank_name
     */
    private String   accountBankName;

    /**
     * 开户银行支行名称 abc_account_info.account_bank_branch_name
     */
    private String   accountBankBranchName;

    /**
     * 帐号(企业对公账户号) abc_account_info.account_user_account
     */
    private String   accountUserAccount;
    /**
     * 对公账户名                        abc_account_info.account_user_account_name
     */
    private String  accountUserAccountName;
    /**
     * 修改对公账户名                        abc_account_info.account_update_name
     */
    private String  accountUpdateName;
    /**
     * 清算行号                          abc_account_info.account_user_account_bk
     */
    private String  accountUserAccountBk;
    /**
     * 线下充值账号                   abc_account_info.account_user_charge_account
     */
    private String  accountUserChargeAccount;
    /**
     * 线下充值账户户名          abc_account_info.account_user_charge_name
     */
    private String  accountUserChargeName;
    /**
     * 开户状态（用于企业开户   0-开户中，已开户-1） abc_account_info.account_state
     */
    private Integer  accountState;

    /**
     * 备注 abc_account_info.account_mark
     */
    private String   accountMark;
    /**
     * 审核意见 abc_account_info.account_audit_desc
     */
    private String   accountAuditDesc;
    /**
     * 账户类型
     * 1：投资账户 - 2：融资账户
     */
    private Integer accountCategory;
    
    public Integer getAccountCategory() {
		return accountCategory;
	}

	public void setAccountCategory(Integer accountCategory) {
		this.accountCategory = accountCategory;
	}

	public Integer getAccountUserId() {
        return accountUserId;
    }

    public void setAccountUserId(Integer accountUserId) {
        this.accountUserId = accountUserId;
    }

    public UserType getAccountUserType() {
        return accountUserType;
    }

    public void setAccountUserType(UserType accountUserType) {
        this.accountUserType = accountUserType;
    }

    public String getAccountLegalName() {
        return accountLegalName;
    }

    public void setAccountLegalName(String accountLegalName) {
        this.accountLegalName = accountLegalName;
    }

    public String getAccountUserName() {
        return accountUserName;
    }

    public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName;
    }

    public String getAccountUserCard() {
        return accountUserCard;
    }

    public void setAccountUserCard(String accountUserCard) {
        this.accountUserCard = accountUserCard;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountUserPhone() {
        return accountUserPhone;
    }

    public void setAccountUserPhone(String accountUserPhone) {
        this.accountUserPhone = accountUserPhone;
    }

    public String getAccountUserEmail() {
        return accountUserEmail;
    }

    public void setAccountUserEmail(String accountUserEmail) {
        this.accountUserEmail = accountUserEmail;
    }

    public String getAccountBankName() {
        return accountBankName;
    }

    public void setAccountBankName(String accountBankName) {
        this.accountBankName = accountBankName;
    }

    public String getAccountUserAccount() {
        return accountUserAccount;
    }

    public void setAccountUserAccount(String accountUserAccount) {
        this.accountUserAccount = accountUserAccount;
    }

    public String getAccountMark() {
        return accountMark;
    }

    public void setAccountMark(String accountMark) {
        this.accountMark = accountMark;
    }

    public String getAccountBankArea() {
        return accountBankArea;
    }

    public void setAccountBankArea(String accountBankArea) {
        this.accountBankArea = accountBankArea;
    }

    public String getAccountBankBranchName() {
        return accountBankBranchName;
    }

    public void setAccountBankBranchName(String accountBankBranchName) {
        this.accountBankBranchName = accountBankBranchName;
    }

	/**
	 * @return the accountAuditDesc
	 */
	public String getAccountAuditDesc() {
		return accountAuditDesc;
	}

	/**
	 * @param accountAuditDesc the accountAuditDesc to set
	 */
	public void setAccountAuditDesc(String accountAuditDesc) {
		this.accountAuditDesc = accountAuditDesc;
	}

	public String getAccountUserAccountName() {
		return accountUserAccountName;
	}

	public void setAccountUserAccountName(String accountUserAccountName) {
		this.accountUserAccountName = accountUserAccountName;
	}

	public String getAccountUserAccountBk() {
		return accountUserAccountBk;
	}

	public void setAccountUserAccountBk(String accountUserAccountBk) {
		this.accountUserAccountBk = accountUserAccountBk;
	}

	public String getAccountUserChargeAccount() {
		return accountUserChargeAccount;
	}

	public void setAccountUserChargeAccount(String accountUserChargeAccount) {
		this.accountUserChargeAccount = accountUserChargeAccount;
	}

	public String getAccountUserChargeName() {
		return accountUserChargeName;
	}

	public void setAccountUserChargeName(String accountUserChargeName) {
		this.accountUserChargeName = accountUserChargeName;
	}

	public Integer getAccountState() {
		return accountState;
	}

	public void setAccountState(Integer accountState) {
		this.accountState = accountState;
	}

	public String getAccountUpdateName() {
		return accountUpdateName;
	}

	public void setAccountUpdateName(String accountUpdateName) {
		this.accountUpdateName = accountUpdateName;
	}

}
