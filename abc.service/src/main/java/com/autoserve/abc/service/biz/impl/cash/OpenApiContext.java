package com.autoserve.abc.service.biz.impl.cash;


/**
 * 
 *                       
 * @Filename OpenApiContext.java
 *
 * @Description 基本参数类 
 *
 * @Version 1.0
 *
 * @Author qianji
 *
 * @History
 *<li>Author: qianji</li>
 *<li>Date: 2014-5-08</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class OpenApiContext {
	
	/**
	 * 平台乾多多标识。开通乾多多帐号为平台帐号时生成，以p开头
	 */
	String			PlatformMoneymoremore;
	/**
	 * 发送到乾多多支付平台的接口地址
	 */
	String			SubmitURL;
	/**
	 * 返回页面接口地址
	 */
	String			ReturnURL;
	/**
	 * 后台通知接口地址
	 */
	String			NotifyURL;
	
	/**
	 * 编码方式
	 */
	String			inputCharset	= "utf-8";
	
	
	
	public String getPlatformMoneymoremore() {
		return PlatformMoneymoremore;
	}



	public void setPlatformMoneymoremore(String platformMoneymoremore) {
		PlatformMoneymoremore = platformMoneymoremore;
	}



	public String getSubmitURL() {
		return SubmitURL;
	}



	public void setSubmitURL(String submitURL) {
		SubmitURL = submitURL;
	}



	public String getReturnURL() {
		return ReturnURL;
	}



	public void setReturnURL(String returnURL) {
		ReturnURL = returnURL;
	}



	public String getNotifyURL() {
		return NotifyURL;
	}



	public void setNotifyURL(String notifyURL) {
		NotifyURL = notifyURL;
	}



	public String getInputCharset() {
		return inputCharset;
	}



	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OpenApiContext [PlatformMoneymoremore=");
		builder.append(PlatformMoneymoremore);
		builder.append(", SubmitURL=");
		builder.append(SubmitURL);
		builder.append(", ReturnURL=");
		builder.append(ReturnURL);
		builder.append(", NotifyURL=");
		builder.append(NotifyURL);
		builder.append(", inputCharset=");
		builder.append(inputCharset);
		builder.append("]");
		return builder.toString();
	}

}
