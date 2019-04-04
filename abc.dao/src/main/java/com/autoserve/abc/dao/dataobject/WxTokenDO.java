package com.autoserve.abc.dao.dataobject;

/**
 * 用户账户
 */
public class WxTokenDO {
    /**
     * 主键id abc_wx_token.wx_Id
     */
    private Integer wxId;

    /**
     * 微信官方声称access_token abc_wx_token.wx_access_token
     */
    private String wxAccessToken;

    /**
     * 平台生成 用于绑定微平台 abc_wx_token.wx_token
     */
    private String wxToken;

    /**
     * 微信公众号ID abc_wx_token.wx_open_id
     */
    private String  wxOpenId;

    /**
     * 公众账号 abc_wx_token.wx_name
     */
    private String  wxName;
    /**
     * 公众账号 abc_wx_token.wx_state
     * 
     */
    private String  wxState;
    
	public String getWxState() {
		return wxState;
	}

	public void setWxState(String wxState) {
		this.wxState = wxState;
	}

	public Integer getWxId() {
		return wxId;
	}

	public void setWxId(Integer wxId) {
		this.wxId = wxId;
	}

	public String getWxAccessToken() {
		return wxAccessToken;
	}

	public void setWxAccessToken(String wxAccessToken) {
		this.wxAccessToken = wxAccessToken;
	}

	public String getWxToken() {
		return wxToken;
	}

	public void setWxToken(String wxToken) {
		this.wxToken = wxToken;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

    

  
}
