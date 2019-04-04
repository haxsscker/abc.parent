package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.autoserve.abc.dao.dataobject.enums.AheadRepayState;

/**
 * 提前还款申请实体类
 * 
 * @author zhangkang
 *
 */
public class AheadRepay {
	private Integer id;
	private Integer userId;//申请人id
	private Integer loanId;//项目id
	private AheadRepayState state;//状态
	private Date createDate;//申请时间
	private Integer auditUserId;//审核人
	private Date auditDate;//审核时间
	private String auditOpinion;//审核意见
	/** add by 夏同同 20160415  新增金额属性用于后台审核列表页面展示*/
	private BigDecimal shouldCapital; //应还本金
	private BigDecimal shouldInterest; //应还利息
	private BigDecimal shouldServeFee; //应还服务费
	private BigDecimal shouldAll; //应还总额
	
	/**关联对象*/
	private UserDO userDO;
	private LoanDO loanDO;
	private EmployeeDO employeeDO;
	
	/**查询条件*/
	private List<AheadRepayState> searchState;
	
	/**前台显示*/
	private String createDateStr;
	private String stateStr;
	private String auditDateStr;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	
	
	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public EmployeeDO getEmployeeDO() {
		return employeeDO;
	}

	public void setEmployeeDO(EmployeeDO employeeDO) {
		this.employeeDO = employeeDO;
	}

	public String getCreateDateStr() {
		return sdf.format(createDate);
		//return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getStateStr() {
		return state.desc;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	public String getAuditDateStr() {
		if(auditDate==null){
			return null;
		}
		return sdf.format(auditDate);
//		return auditDateStr;
	}

	public void setAuditDateStr(String auditDateStr) {
		this.auditDateStr = auditDateStr;
	}

	public UserDO getUserDO() {
		return userDO;
	}

	public void setUserDO(UserDO userDO) {
		this.userDO = userDO;
	}

	public LoanDO getLoanDO() {
		return loanDO;
	}

	public void setLoanDO(LoanDO loanDO) {
		this.loanDO = loanDO;
	}

	public Integer getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(Integer auditUserId) {
		this.auditUserId = auditUserId;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public List<AheadRepayState> getSearchState() {
		return searchState;
	}

	public void setSearchState(List<AheadRepayState> searchState) {
		this.searchState = searchState;
	}

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

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public AheadRepayState getState() {
		return state;
	}

	public void setState(AheadRepayState state) {
		this.state = state;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getShouldCapital() {
		return shouldCapital;
	}

	public void setShouldCapital(BigDecimal shouldCapital) {
		this.shouldCapital = shouldCapital;
	}

	public BigDecimal getShouldInterest() {
		return shouldInterest;
	}

	public void setShouldInterest(BigDecimal shouldInterest) {
		this.shouldInterest = shouldInterest;
	}

	public BigDecimal getShouldAll() {
		return shouldAll;
	}

	public void setShouldAll(BigDecimal shouldAll) {
		this.shouldAll = shouldAll;
	}

	public BigDecimal getShouldServeFee() {
		return shouldServeFee;
	}

	public void setShouldServeFee(BigDecimal shouldServeFee) {
		this.shouldServeFee = shouldServeFee;
	}

}
