package com.autoserve.abc.dao.dataobject;

/**
 * 个人单位资料
 * 
 * @author RJQ 2014/11/13 18:16.
 */
public class UserCompanyDO {
    /**
     * 主键 abc_user_company.uc_id
     */
    private Integer ucId;

    /**
     * 用户ID abc_user_company.uc_userid
     */
    private Integer ucUserId;

    /**
     * 单位名称 abc_user_company.uc_company_name
     */
    private String  ucCompanyName;

    /**
     * 单位性质 1：政府机关 2：国有企业 3：台（港、澳）资企业 4：合资企业 5：个体户 6：事业性单位 7：私营企业
     * abc_user_company.uc_company_type
     */
    private Integer ucCompanyType;

    /**
     * 单位行业 "1：农、林、牧、渔业 2：制造业 3：电力、燃气及水的生产和供应业 4：建筑业 5：交通运输、仓储和邮政业
     * 6：信息传输、计算机服务和软件业 7：批发和零售业 8：金融业 9：房地产业 10：采矿业 11：租赁和商务服务业
     * 12：科学研究、技术服务和地质勘查业 13：水利、环境和公共设施管理业 14：居民服务和其他服务业 15：教育 16：卫生、社会保障和社会福利业
     * 17：文化、体育和娱乐业 18：公共管理和社会组织 19：国际组织" abc_user_company.uc_company_industry
     */
    private Integer ucCompanyIndustry;

    /**
     * 工作年限 1：一年以内 2：一年以上 3：二年以上 4：三年以上 5：四年以上 6：五年以上 7 六年以上
     * abc_user_company.uc_work_year
     */
    private Integer ucWorkYear;

    /**
     * 职位 abc_user_company.uc_position
     */
    private String  ucPosition;

    /**
     * 工作级别 abc_user_company.uc_level
     */
    private String  ucLevel;

    /**
     * 工作电话 abc_user_company.uc_phone
     */
    private String  ucPhone;

    /**
     * 工作时间 abc_user_company.uc_work_period
     */
    private String  ucWorkPeriod;

    /**
     * 公司网址 abc_user_company.uc_company_site
     */
    private String  ucCompanySite;

    /**
     * 公司地址 abc_user_company.uc_company_address
     */
    private String  ucCompanyAddress;

    /**
     * 备注 abc_user_company.uc_note
     */
    private String  ucNote;

    public Integer getUcId() {
        return ucId;
    }

    public void setUcId(Integer ucId) {
        this.ucId = ucId;
    }

    public Integer getUcUserId() {
        return ucUserId;
    }

    public void setUcUserId(Integer ucUserId) {
        this.ucUserId = ucUserId;
    }

    public String getUcCompanyName() {
        return ucCompanyName;
    }

    public void setUcCompanyName(String ucCompanyName) {
        this.ucCompanyName = ucCompanyName;
    }

    public Integer getUcCompanyType() {
        return ucCompanyType;
    }

    public void setUcCompanyType(Integer ucCompanyType) {
        this.ucCompanyType = ucCompanyType;
    }

    public Integer getUcCompanyIndustry() {
        return ucCompanyIndustry;
    }

    public void setUcCompanyIndustry(Integer ucCompanyIndustry) {
        this.ucCompanyIndustry = ucCompanyIndustry;
    }

    public Integer getUcWorkYear() {
        return ucWorkYear;
    }

    public void setUcWorkYear(Integer ucWorkYear) {
        this.ucWorkYear = ucWorkYear;
    }

    public String getUcPosition() {
        return ucPosition;
    }

    public void setUcPosition(String ucPosition) {
        this.ucPosition = ucPosition;
    }

    public String getUcLevel() {
        return ucLevel;
    }

    public void setUcLevel(String ucLevel) {
        this.ucLevel = ucLevel;
    }

    public String getUcPhone() {
        return ucPhone;
    }

    public void setUcPhone(String ucPhone) {
        this.ucPhone = ucPhone;
    }

    public String getUcWorkPeriod() {
        return ucWorkPeriod;
    }

    public void setUcWorkPeriod(String ucWorkPeriod) {
        this.ucWorkPeriod = ucWorkPeriod;
    }

    public String getUcCompanySite() {
        return ucCompanySite;
    }

    public void setUcCompanySite(String ucCompanySite) {
        this.ucCompanySite = ucCompanySite;
    }

    public String getUcCompanyAddress() {
        return ucCompanyAddress;
    }

    public void setUcCompanyAddress(String ucCompanyAddress) {
        this.ucCompanyAddress = ucCompanyAddress;
    }

    public String getUcNote() {
        return ucNote;
    }

    public void setUcNote(String ucNote) {
        this.ucNote = ucNote;
    }
}
