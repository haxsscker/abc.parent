package com.autoserve.abc.web.module.screen.mobileIos;

import com.alibaba.fastjson.JSON;
/**
 * 手机app更新数据
 * @author zhangkang
 *
 */
public class MobileInfo {
	/**
	 * 应用名称
	 */
	private String appName;
	/**
	 * 应用版本
	 */
	private String appVersion;
	/**
	 * 下载地址
	 */
	private String link;
	/**
	 * 更新描述
	 */
	private String updateDesc;
	/**
	 * 是否强制更新
	 */
	private boolean force;
	
	public boolean isForce() {
		return force;
	}
	public void setForce(boolean force) {
		this.force = force;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getUpdateDesc() {
		return updateDesc;
	}
	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
