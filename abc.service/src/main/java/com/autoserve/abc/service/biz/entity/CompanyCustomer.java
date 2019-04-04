package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.CcCompanyScale;
import com.autoserve.abc.service.biz.enums.CcCompanyType;

public class CompanyCustomer {
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
    private CcCompanyType  ccCompanyType;

    /**
     * 企业规模 1: 50人之内 2: 50~500人之间 3: 500人以上
     * abc_company_customer.cc_company_scale
     */
    private CcCompanyScale ccCompanyScale;

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
    private Date           ccRegisterDate;

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
     * 联系地址 abc_company_customer.cc_contact_address
     */
    private String         ccContactAddress;

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
     * 法人手机号码
     */
    private String         ccUserPhone;
    /**
     * 联系人邮箱 abc_company_customer.cc_contact_email
     */
    private String         ccContactEmail;

    public Integer getCcId() {
        return ccId;
    }

    public void setCcId(Integer ccId) {
        this.ccId = ccId;
    }

    public Integer getCcUserid() {
        return ccUserid;
    }

    public void setCcUserid(Integer ccUserid) {
        this.ccUserid = ccUserid;
    }

    public String getCcCompanyName() {
        return ccCompanyName;
    }

    public void setCcCompanyName(String ccCompanyName) {
        this.ccCompanyName = ccCompanyName;
    }

    public String getCcNo() {
        return ccNo;
    }

    public void setCcNo(String ccNo) {
        this.ccNo = ccNo;
    }

    public CcCompanyType getCcCompanyType() {
        return ccCompanyType;
    }

    public void setCcCompanyType(CcCompanyType ccCompanyType) {
        this.ccCompanyType = ccCompanyType;
    }

    public CcCompanyScale getCcCompanyScale() {
        return ccCompanyScale;
    }

    public void setCcCompanyScale(CcCompanyScale ccCompanyScale) {
        this.ccCompanyScale = ccCompanyScale;
    }

    public BigDecimal getCcTotalCapital() {
        return ccTotalCapital;
    }

    public void setCcTotalCapital(BigDecimal ccTotalCapital) {
        this.ccTotalCapital = ccTotalCapital;
    }

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

    public Date getCcRegisterDate() {
        return ccRegisterDate;
    }

    public void setCcRegisterDate(Date ccRegisterDate) {
        this.ccRegisterDate = ccRegisterDate;
    }

    public String getCcRegisterAddress() {
        return ccRegisterAddress;
    }

    /**
     * @param String ccRegisterAddress (abc_company_customer.cc_register_address
     *            )
     */
    public void setCcRegisterAddress(String ccRegisterAddress) {
        this.ccRegisterAddress = ccRegisterAddress;
    }

    public String getCcContactName() {
        return ccContactName;
    }

    public void setCcContactName(String ccContactName) {
        this.ccContactName = ccContactName;
    }

    public String getCcContactPhone() {
        return ccContactPhone;
    }

    public void setCcContactPhone(String ccContactPhone) {
        this.ccContactPhone = ccContactPhone;
    }

    public String getCcContactAddress() {
        return ccContactAddress;
    }

    public void setCcContactAddress(String ccContactAddress) {
        this.ccContactAddress = ccContactAddress;
    }

    public String getCcCorporate() {
        return ccCorporate;
    }

    public void setCcCorporate(String ccCorporate) {
        this.ccCorporate = ccCorporate;
    }

    public String getCcDocType() {
        return ccDocType;
    }

    public void setCcDocType(String ccDocType) {
        this.ccDocType = ccDocType;
    }

    public String getCcDocNo() {
        return ccDocNo;
    }

    public void setCcDocNo(String ccDocNo) {
        this.ccDocNo = ccDocNo;
    }

    public String getCcCompanyAddress() {
        return ccCompanyAddress;
    }

    public void setCcCompanyAddress(String ccCompanyAddress) {
        this.ccCompanyAddress = ccCompanyAddress;
    }

    public String getCcBusinessLicense() {
        return ccBusinessLicense;
    }

    /**
     * @param String ccBusinessLicense (abc_company_customer.cc_business_license
     *            )
     */
    public void setCcBusinessLicense(String ccBusinessLicense) {
        this.ccBusinessLicense = ccBusinessLicense;
    }

    public String getCcLicenseNo() {
        return ccLicenseNo;
    }

    public void setCcLicenseNo(String ccLicenseNo) {
        this.ccLicenseNo = ccLicenseNo;
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
