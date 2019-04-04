/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.account;

import java.io.Serializable;

/**
 * 机构账户信息前台展示VO
 * 
 * @author J.YL 2014年12月2日 下午5:44:16
 */
public class ComAccountVO implements Serializable {

    private static final long serialVersionUID = 6898481275286929560L;

    private Integer           act_account_id;
    private String            gov_name;
    private String            act_legal_name;
    private String            act_user_name;
    private String            act_user_card;
    private String            act_user_phone;
    private String            act_business_licence;
    private String            act_user_email;
    private String  		  act_platform_no;

    /**
     * @return the gov_name
     */
    public String getGov_name() {
        return gov_name;
    }

    /**
     * @param gov_name the gov_name to set
     */
    public void setGov_name(String gov_name) {
        this.gov_name = gov_name;
    }

    /**
     * @return the act_legal_name
     */
    public String getAct_legal_name() {
        return act_legal_name;
    }

    /**
     * @param act_legal_name the act_legal_name to set
     */
    public void setAct_legal_name(String act_legal_name) {
        this.act_legal_name = act_legal_name;
    }

    /**
     * @return the act_user_name
     */
    public String getAct_user_name() {
        return act_user_name;
    }

    /**
     * @param act_user_name the act_user_name to set
     */
    public void setAct_user_name(String act_user_name) {
        this.act_user_name = act_user_name;
    }

    /**
     * @return the act_user_card
     */
    public String getAct_user_card() {
        return act_user_card;
    }

    /**
     * @param act_user_card the act_user_card to set
     */
    public void setAct_user_card(String act_user_card) {
        this.act_user_card = act_user_card;
    }

    /**
     * @return the act_user_phone
     */
    public String getAct_user_phone() {
        return act_user_phone;
    }

    /**
     * @param act_user_phone the act_user_phone to set
     */
    public void setAct_user_phone(String act_user_phone) {
        this.act_user_phone = act_user_phone;
    }

    /**
     * @return the act_account_id
     */
    public Integer getAct_account_id() {
        return act_account_id;
    }

    /**
     * @param act_account_id the act_account_id to set
     */
    public void setAct_account_id(Integer act_account_id) {
        this.act_account_id = act_account_id;
    }

	public String getAct_business_licence() {
		return act_business_licence;
	}

	public void setAct_business_licence(String act_business_licence) {
		this.act_business_licence = act_business_licence;
	}

	public String getAct_user_email() {
		return act_user_email;
	}

	public void setAct_user_email(String act_user_email) {
		this.act_user_email = act_user_email;
	}

	public String getAct_platform_no() {
		return act_platform_no;
	}

	public void setAct_platform_no(String act_platform_no) {
		this.act_platform_no = act_platform_no;
	}
    
}
