package com.autoserve.abc.dao.dataobject;

public class SmsNotifyCfg {

	private int switchState;

	private String contentTemplate;

	public SmsNotifyCfg() {

	}

	public SmsNotifyCfg(int switchState, String contentTemplate) {
		this.switchState = switchState;
		this.contentTemplate = contentTemplate;
	}
	
	public int getSwitchState() {
		return switchState;
	}

	public void setSwitchState(int switchState) {
		this.switchState = switchState;
	}

	public String getContentTemplate() {
		return contentTemplate;
	}

	public void setContentTemplate(String contentTemplate) {
		this.contentTemplate = contentTemplate;
	}

}
