package com.autoserve.abc.web.vo.user;

import com.autoserve.abc.service.biz.enums.CompanyType;
import com.autoserve.abc.service.biz.enums.IndustryType;
import com.autoserve.abc.service.biz.enums.WorkYear;

public class UserCompanyVO {
    /**
     * 主键
     */
    private Integer      id;

    /**
     * 用户ID
     */
    private Integer      userId;

    /**
     * 单位名称
     */
    private String       companyName;

    /**
     * 单位性质 1：政府机关 2：国有企业 3：台（港、澳）资企业 4：合资企业 5：个体户 6：事业性单位 7：私营企业
     */
    private CompanyType  companyType;

    /**
     * 单位行业 "1：农、林、牧、渔业 2：制造业 3：电力、燃气及水的生产和供应业 4：建筑业 5：交通运输、仓储和邮政业
     * 6：信息传输、计算机服务和软件业 7：批发和零售业 8：金融业 9：房地产业 10：采矿业 11：租赁和商务服务业
     * 12：科学研究、技术服务和地质勘查业 13：水利、环境和公共设施管理业 14：居民服务和其他服务业 15：教育 16：卫生、社会保障和社会福利业
     * 17：文化、体育和娱乐业 18：公共管理和社会组织 19：国际组织"
     */
    private IndustryType companyIndustry;

    /**
     * 工作年限 1：一年以内 2：一年以上 3：二年以上 4：三年以上 5：四年以上 6：五年以上 7 六年以上
     */
    private WorkYear     workYear;

    /**
     * 职位
     */
    private String       userMonthIncome;

    /**
     * 月收入
     */
    private String       position;

    /**
     * 工作级别
     */
    private String       level;

    /**
     * 工作电话
     */
    private String       phone;

    /**
     * 工作时间
     */
    private String       workPeriod;

    /**
     * 公司网址
     */
    private String       companySite;

    /**
     * 公司地址
     */
    private String       companyAddress;

    /**
     * 备注
     */
    private String       note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public IndustryType getCompanyIndustry() {
        return companyIndustry;
    }

    public void setCompanyIndustry(IndustryType companyIndustry) {
        this.companyIndustry = companyIndustry;
    }

    public WorkYear getWorkYear() {
        return workYear;
    }

    public void setWorkYear(WorkYear workYear) {
        this.workYear = workYear;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(String workPeriod) {
        this.workPeriod = workPeriod;
    }

    public String getCompanySite() {
        return companySite;
    }

    public void setCompanySite(String companySite) {
        this.companySite = companySite;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserMonthIncome() {
        return userMonthIncome;
    }

    public void setUserMonthIncome(String userMonthIncome) {
        this.userMonthIncome = userMonthIncome;
    }

}
