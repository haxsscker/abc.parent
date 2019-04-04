package com.autoserve.abc.web.vo.sysset;

/**
 * 短信账户设置
 * @author liuwei
 *
 */
public class MessageVO {
	private String sys_user_name;
	private String sys_user_pwd;
	public String getSys_user_name() {
		
		return sys_user_name;
	}
	public void setSys_user_name(String sys_user_name) {
		this.sys_user_name = sys_user_name;
	}
	public String getSys_user_pwd() {
		return sys_user_pwd;
	}
	public void setSys_user_pwd(String sys_user_pwd) {
		this.sys_user_pwd = sys_user_pwd;
	}
}
