package com.autoserve.abc.dao.dataobject;


public class MonthReportDO {
	
	private Integer rptId;
	
	private String rptTitle;
	
	private String logoUrl;
	
	private String fileUrl;
	
	private String createTime;
	
	private String modifyTime;

	private Integer createUserId;
	
	private String rptYear;
	
	private String rptMonth;

	public Integer getRptId() {
		return rptId;
	}

	public void setRptId(Integer rptId) {
		this.rptId = rptId;
	}

	public String getRptTitle() {
		return rptTitle;
	}

	public void setRptTitle(String rptTitle) {
		this.rptTitle = rptTitle;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getRptYear() {
		return rptYear;
	}

	public void setRptYear(String rptYear) {
		this.rptYear = rptYear;
	}

	public String getRptMonth() {
		return rptMonth;
	}

	public void setRptMonth(String rptMonth) {
		this.rptMonth = rptMonth;
	}
	

	
}
