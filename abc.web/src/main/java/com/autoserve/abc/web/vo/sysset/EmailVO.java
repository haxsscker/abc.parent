package com.autoserve.abc.web.vo.sysset;

public class EmailVO {
	
	private String sys_smtp;
	
	private String sys_port;
	
	private String sys_email_address;
	
	private String sys_email_pwd;
	
	private String sys_send_name;

	public String getSys_smtp() {
		return sys_smtp;
	}

	public void setSys_smtp(String sys_smtp) {
		this.sys_smtp = sys_smtp;
	}

	public String getSys_email_address() {
		return sys_email_address;
	}

	public void setSys_email_address(String sys_email_address) {
		this.sys_email_address = sys_email_address;
	}

	public String getSys_email_pwd() {
		return sys_email_pwd;
	}

	public void setSys_email_pwd(String sys_email_pwd) {
		this.sys_email_pwd = sys_email_pwd;
	}

	public String getSys_send_name() {
		return sys_send_name;
	}

	public void setSys_send_name(String sys_send_name) {
		this.sys_send_name = sys_send_name;
	}

	public String getSys_port() {
		return sys_port;
	}

	public void setSys_port(String sys_port) {
		this.sys_port = sys_port;
	}
	
}
