package com.autoserve.abc.web.vo.company;

import java.math.BigDecimal;

public class CompanyCustomerVO {

    /**
     * abc_company_customer.cc_id
     */
    private Integer        ccId;

    /**
     * 用户ID abc_company_customer.cc_userid
     */
    private Integer        ccUserid;

    /**
     * 公司名称 abc_company_customer.cc_company_name
     */
    private String         ccCompanyName;

    /**
     * 组织机构代码 abc_company_customer.cc_no
     */
    private String         ccNo;

    /**
     * 企业性质 1：政府机关 2：国有企业 3：台（港、澳）资企业 4：合资企业 5：个体户 6：事业性单位 7：私营企业
     * abc_company_customer.cc_company_type
     */
    private Integer  ccCompanyType;

    /**
     * 企业规模 1: 50人之内 2: 50~500人之间 3: 500人以上
     * abc_company_customer.cc_company_scale
     */
    private Integer ccCompanyScale;

    /**
     * 资产总额 abc_company_customer.cc_total_capital
     */
    private BigDecimal     ccTotalCapital;

    /**
     * 注册资金 abc_company_customer.cc_register_capital
     */
    private BigDecimal     ccRegisterCapital;

    /**
     * 注册日期 abc_company_customer.cc_register_date
     */
    private String         ccRegisterDate;

    /**
     * 注册地址 abc_company_customer.cc_register_address
     */
    private String         ccRegisterAddress;

    /**
     * 联系人姓名 abc_company_customer.cc_contact_name
     */
    private String         ccContactName;

    /**
     * 联系电话 abc_company_customer.cc_contact_phone
     */
    private String         ccContactPhone;
    /**
     * 联系人电子邮箱 abc_company_customer.cc_contact_email
     */
    private String         ccContactEmail;

    /**
     * 联系地址 abc_company_customer.cc_contact_address
     */
    private String         ccContactAddress;
    
    /*-------------------------abc_user表 start--------------------------*/
    /**
     * 登录账号 abc_user.user_name
     */
    private String ccUserName;
    
    /**
     * 联系人电子邮箱 abc_user.user_email
     */
    private String ccUserEmail;
    
    /**
     * 交易密码 abc_user.user_deal_pwd
     */
    private String ccUserDealPwd;

    /**
     * 法定代表人 abc_company_customer.cc_corporate
     */
    private String         ccCorporate;

    /**
     * 证件类型 abc_company_customer.cc_doc_type
     */
    private String         ccDocType;

    /**
     * 证件号码 abc_company_customer.cc_doc_no
     */
    private String         ccDocNo;
    /**
     * 法人手机号码
     */
    private String         ccUserPhone;
    /*-------------------------abc_user表 end--------------------------*/
    
    /**
     * 公司地址 abc_company_customer.cc_company_address
     */
    private String         ccCompanyAddress;

    /**
     * 营业执照 abc_company_customer.cc_business_license
     */
    private String         ccBusinessLicense;

    /**
     * 营业执照号码 abc_company_customer.cc_license_no
     */
    private String         ccLicenseNo;

    /**
     * 公司简介 abc_company_customer.cc_company_profile
     */
    private String         ccCompanyProfile;

    /**
     * 公司成就 abc_company_customer.cc_company_achievement
     */
    private String         ccCompanyAchievement;

    /**
     * @return abc_company_customer.cc_id
     */
    public Integer getCcId() {
        return ccId;
    }

    /**
     * @param Integer ccId (abc_company_customer.cc_id )
     */
    public void setCcId(Integer ccId) {
        this.ccId = ccId;
    }

    /**
     * @return abc_company_customer.cc_userid
     */
    public Integer getCcUserid() {
        return ccUserid;
    }

    /**
     * @param Integer ccUserid (abc_company_customer.cc_userid )
     */
    public void setCcUserid(Integer ccUserid) {
        this.ccUserid = ccUserid;
    }

    /**
     * @return abc_company_customer.cc_company_name
     */
    public String getCcCompanyName() {
        return ccCompanyName;
    }

    /**
     * @param String ccCompanyName (abc_company_customer.cc_company_name )
     */
    public void setCcCompanyName(String ccCompanyName) {
        this.ccCompanyName = ccCompanyName == null ? "" : ccCompanyName.trim();
    }

    /**
     * @return abc_company_customer.cc_no
     */
    public String getCcNo() {
        return ccNo;
    }

    /**
     * @param String ccNo (abc_company_customer.cc_no )
     */
    public void setCcNo(String ccNo) {
        this.ccNo = ccNo == null ? "" : ccNo.trim();
    }

    /**
     * @return abc_company_customer.cc_company_type
     */
    public Integer getCcCompanyType() {
        return ccCompanyType;
    }

    /**
     * @param Integer ccCompanyType (abc_company_customer.cc_company_type )
     */
    public void setCcCompanyType(Integer ccCompanyType) {
        this.ccCompanyType = ccCompanyType;
    }

    /**
     * @return abc_company_customer.cc_company_scale
     */
    public Integer getCcCompanyScale() {
        return ccCompanyScale;
    }

    /**
     * @param Integer ccCompanyScale (abc_company_customer.cc_company_scale )
     */
    public void setCcCompanyScale(Integer ccCompanyScale) {
        this.ccCompanyScale = ccCompanyScale;
    }

    /**
     * @return abc_company_customer.cc_total_capital
     */
    public BigDecimal getCcTotalCapital() {
        return ccTotalCapital;
    }

    /**
     * @param BigDecimal ccTotalCapital (abc_company_customer.cc_total_capital )
     */
    public void setCcTotalCapital(BigDecimal ccTotalCapital) {
        this.ccTotalCapital = ccTotalCapital;
    }

    /**
     * @return abc_company_customer.cc_register_capital
     */
    public BigDecimal getCcRegisterCapital() {
        return ccRegisterCapital;
    }

    /**
     * @param BigDecimal ccRegisterCapital
     *            (abc_company_customer.cc_register_capital )
     */
    public void setCcRegisterCapital(BigDecimal ccRegisterCapital) {
        this.ccRegisterCapital = ccRegisterCapital;
    }

    /**
     * @return abc_company_customer.cc_register_date
     */
    public String getCcRegisterDate() {
        return ccRegisterDate;
    }

    /**
     * @param Date ccRegisterDate (abc_company_customer.cc_register_date )
     */
    public void setCcRegisterDate(String ccRegisterDate) {
        this.ccRegisterDate = ccRegisterDate;
    }

    /**
     * @return abc_company_customer.cc_register_address
     */
    public String getCcRegisterAddress() {
        return ccRegisterAddress;
    }

    /**
     * @param String ccRegisterAddress (abc_company_customer.cc_register_address
     *            )
     */
    public void setCcRegisterAddress(String ccRegisterAddress) {
        this.ccRegisterAddress = ccRegisterAddress == null ? "" : ccRegisterAddress.trim();
    }

    /**
     * @return abc_company_customer.cc_contact_name
     */
    public String getCcContactName() {
        return ccContactName;
    }

    /**
     * @param String ccContactName (abc_company_customer.cc_contact_name )
     */
    public void setCcContactName(String ccContactName) {
        this.ccContactName = ccContactName == null ? "" : ccContactName.trim();
    }

    /**
     * @return abc_company_customer.cc_contact_phone
     */
    public String getCcContactPhone() {
        return ccContactPhone;
    }

    /**
     * @param String ccContactPhone (abc_company_customer.cc_contact_phone )
     */
    public void setCcContactPhone(String ccContactPhone) {
        this.ccContactPhone = ccContactPhone == null ? "" : ccContactPhone.trim();
    }

    /**
     * @return abc_company_customer.cc_contact_address
     */
    public String getCcContactAddress() {
        return ccContactAddress;
    }

    /**
     * @param String ccContactAddress (abc_company_customer.cc_contact_address )
     */
    public void setCcContactAddress(String ccContactAddress) {
        this.ccContactAddress = ccContactAddress == null ? "" : ccContactAddress.trim();
    }

    /**
     * @return abc_company_customer.cc_corporate
     */
    public String getCcCorporate() {
        return ccCorporate;
    }

    /**
     * @param String ccCorporate (abc_company_customer.cc_corporate )
     */
    public void setCcCorporate(String ccCorporate) {
        this.ccCorporate = ccCorporate == null ? "" : ccCorporate.trim();
    }

    /**
     * @return abc_company_customer.cc_doc_type
     */
    public String getCcDocType() {
        return ccDocType;
    }

    /**
     * @param String ccDocType (abc_company_customer.cc_doc_type )
     */
    public void setCcDocType(String ccDocType) {
        this.ccDocType = ccDocType == null ? "" : ccDocType.trim();
    }

    /**
     * @return abc_company_customer.cc_doc_no
     */
    public String getCcDocNo() {
        return ccDocNo;
    }

    /**
     * @param String ccDocNo (abc_company_customer.cc_doc_no )
     */
    public void setCcDocNo(String ccDocNo) {
        this.ccDocNo = ccDocNo == null ? "" : ccDocNo.trim();
    }

    /**
     * @return abc_company_customer.cc_company_address
     */
    public String getCcCompanyAddress() {
        return ccCompanyAddress;
    }

    /**
     * @param String ccCompanyAddress (abc_company_customer.cc_company_address )
     */
    public void setCcCompanyAddress(String ccCompanyAddress) {
        this.ccCompanyAddress = ccCompanyAddress == null ? "" : ccCompanyAddress.trim();
    }

    /**
     * @return abc_company_customer.cc_business_license
     */
    public String getCcBusinessLicense() {
        return ccBusinessLicense;
    }

    /**
     * @param String ccBusinessLicense (abc_company_customer.cc_business_license
     *            )
     */
    public void setCcBusinessLicense(String ccBusinessLicense) {
        this.ccBusinessLicense = ccBusinessLicense == null ? "" : ccBusinessLicense.trim();
    }

    /**
     * @return abc_company_customer.cc_license_no
     */
    public String getCcLicenseNo() {
        return ccLicenseNo;
    }

    /**
     * @param String ccLicenseNo (abc_company_customer.cc_license_no )
     */
    public void setCcLicenseNo(String ccLicenseNo) {
        this.ccLicenseNo = ccLicenseNo == null ? "" : ccLicenseNo.trim();
    }

    public String getCcCompanyProfile() {
        return ccCompanyProfile;
    }

    public void setCcCompanyProfile(String ccCompanyProfile) {
        this.ccCompanyProfile = ccCompanyProfile;
    }

    public String getCcCompanyAchievement() {
        return ccCompanyAchievement;
    }

    public void setCcCompanyAchievement(String ccCompanyAchievement) {
        this.ccCompanyAchievement = ccCompanyAchievement;
    }

	public String getCcUserName() {
		return ccUserName;
	}

	public void setCcUserName(String ccUserName) {
		this.ccUserName = ccUserName;
	}

	public String getCcUserEmail() {
		return ccUserEmail;
	}

	public void setCcUserEmail(String ccUserEmail) {
		this.ccUserEmail = ccUserEmail;
	}

	public String getCcUserDealPwd() {
		return ccUserDealPwd;
	}

	public void setCcUserDealPwd(String ccUserDealPwd) {
		this.ccUserDealPwd = ccUserDealPwd;
	}

	public String getCcUserPhone() {
		return ccUserPhone;
	}

	public void setCcUserPhone(String ccUserPhone) {
		this.ccUserPhone = ccUserPhone;
	}

	public String getCcContactEmail() {
		return ccContactEmail;
	}

	public void setCcContactEmail(String ccContactEmail) {
		this.ccContactEmail = ccContactEmail;
	}
	
}