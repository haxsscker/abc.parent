package com.autoserve.abc.dao.dataobject;


/**
 * 银行编码信息
 * 
 * @author LZ
 */
public class SessionRecordDO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * sessionId
     */
    private String  sessionId;

  
    /**
     * 用户名
     */
    private String  userName;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

}
