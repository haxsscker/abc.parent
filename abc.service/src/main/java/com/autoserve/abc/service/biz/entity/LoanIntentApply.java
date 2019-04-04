package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoaneeType;

/**
 * 意向申请
 *
 * @author 杜武强
 * @Date 2014年11月22日 上午9:41:41
 */
public class LoanIntentApply {
    /**
     * 申请id 主键 唯一
     */
    private Integer        id;

    /**
     * 意向编号
     */
    private String         intentNo;

    /**
     * 申请标题
     */
    private String         intentTitle;

    /**
     * 产品类型
     */
    private LoanCategory   intentCategory;

    /**
     * 融资金额
     */
    private BigDecimal     intentMoney;

    /**
     * 融资利率（年化收益率）
     */
    private BigDecimal     intentRate;

    /**
     * 借款人所在区域 以数字代替身份的名字 0：云南 1：北京 2：上海 3：重庆
     */
    private Integer        area;

    /**
     * 借款期限
     */
    private Integer        intentPeriod;

    /**
     * 期限类型
     */
    private LoanPeriodUnit intentPeriodUnit;

    /**
     * 融资用途
     */
    private String         intentUse;

    /**
     * 还款方式
     */
    private LoanPayType    intentPayType;

    /**
     * 借款人类型
     */
    private LoaneeType     intenteeType;

    /**
     * 借款人ID
     */
    private Integer        userId;

    /**
     * 借款人名称
     */
    private String         userName;

    /**
     * 手机号码
     */
    private String         phone;

    /**
     * 备注说明
     */
    private String         note;

    /**
     * 申请时间
     */
    private Date           intentTime;

    /**
     * 意向标状态
     */
    private LoanState      intentState;

    /**
     * 审核时间
     */
    private Date           auditTime;

    /**
     * 审核意见
     */
    private String         auditOpinion;

    /**
     * 相关项目ID
     */
    private Integer        loanId;

    /**
     * 附件url
     */
    private String fileUrl;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntentNo() {
        return intentNo;
    }

    public void setIntentNo(String intentNo) {
        this.intentNo = intentNo;
    }

    public String getIntentTitle() {
        return intentTitle;
    }

    public void setIntentTitle(String intentTitle) {
        this.intentTitle = intentTitle;
    }

    public LoanCategory getIntentCategory() {
        return intentCategory;
    }

    public void setIntentCategory(LoanCategory intentCategory) {
        this.intentCategory = intentCategory;
    }

    public BigDecimal getIntentMoney() {
        return intentMoney;
    }

    public void setIntentMoney(BigDecimal intentMoney) {
        this.intentMoney = intentMoney;
    }

    public BigDecimal getIntentRate() {
        return intentRate;
    }

    public void setIntentRate(BigDecimal intentRate) {
        this.intentRate = intentRate;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getIntentPeriod() {
        return intentPeriod;
    }

    public void setIntentPeriod(Integer intentPeriod) {
        this.intentPeriod = intentPeriod;
    }

    public LoanPeriodUnit getIntentPeriodUnit() {
        return intentPeriodUnit;
    }

    public void setIntentPeriodUnit(LoanPeriodUnit intentPeriodUnit) {
        this.intentPeriodUnit = intentPeriodUnit;
    }

    public String getIntentUse() {
        return intentUse;
    }

    public void setIntentUse(String intentUse) {
        this.intentUse = intentUse;
    }

    public LoanPayType getIntentPayType() {
        return intentPayType;
    }

    public void setIntentPayType(LoanPayType intentPayType) {
        this.intentPayType = intentPayType;
    }

    public LoaneeType getIntenteeType() {
        return intenteeType;
    }

    public void setIntenteeType(LoaneeType intenteeType) {
        this.intenteeType = intenteeType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getIntentTime() {
        return intentTime;
    }

    public void setIntentTime(Date intentTime) {
        this.intentTime = intentTime;
    }

    public LoanState getIntentState() {
        return intentState;
    }

    public void setIntentState(LoanState intentState) {
        this.intentState = intentState;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
