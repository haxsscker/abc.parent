package com.autoserve.abc.dao.dataobject;

/**
 * 公司客户text字段信息
 * @author RJQ 2014/11/13 18:00.
 */
public class CompanyCustomerWithBLOBsDO extends CompanyCustomerDO {
    /**
     * 公司简介
     * abc_company_customer.cc_company_profile
     */
    private String ccCompanyProfile;

    /**
     * 公司成就
     * abc_company_customer.cc_company_achievement
     */
    private String ccCompanyAchievement;

    @Override
	public String getCcCompanyProfile() {
        return ccCompanyProfile;
    }

    @Override
	public void setCcCompanyProfile(String ccCompanyProfile) {
        this.ccCompanyProfile = ccCompanyProfile;
    }

    @Override
	public String getCcCompanyAchievement() {
        return ccCompanyAchievement;
    }

    @Override
	public void setCcCompanyAchievement(String ccCompanyAchievement) {
        this.ccCompanyAchievement = ccCompanyAchievement;
    }
}
