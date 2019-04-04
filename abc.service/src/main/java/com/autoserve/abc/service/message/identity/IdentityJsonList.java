package com.autoserve.abc.service.message.identity;
import java.io.Serializable;


public class IdentityJsonList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6719709706991274701L;
	
	private String realName = "";
	private String identificationNo = "";
	private int state = 0;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdentificationNo() {
		return identificationNo;
	}
	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
