package com.autoserve.abc.dao.dataobject;

public class BanelDO {
	
	private Integer id;
	
	private String banelTitle;
	
	private String banelUrl;
	
	private String linkUrl;
	
	private Integer orderBy;
	
	private String groupCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBanelTitle() {
		return banelTitle;
	}

	public void setBanelTitle(String banelTitle) {
		this.banelTitle = banelTitle;
	}

	public String getBanelUrl() {
		return banelUrl;
	}

	public void setBanelUrl(String banelUrl) {
		this.banelUrl = banelUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
}
