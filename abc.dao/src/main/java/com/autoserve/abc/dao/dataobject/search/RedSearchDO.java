package com.autoserve.abc.dao.dataobject.search;

/**
 * 红包搜索条件
 * 
 * @author fangrui 2014年12月31日 上午9:28:31
 */
public class RedSearchDO {
	/**
     * 红包所属用户ID（可用于邀请人）
     */
    private Integer  userId;
    /**
     * 红包适用类型
     */
    private String  userScope;
	
    /**
     * 红包所属用户名（可用于邀请人）
     */
    private String  userName;

    /**
     * 红包使用状态 : 0:失效 1:未使用，2：已使用
     */
    private Integer rsState;
    /**
     * 红包失效时间 abc_red_send.rs_closetime
     */
    private String rsClosetime;
    /**
     * 搜索红包发放日期开始时间(待删)
     */
    private String  rsSendtimeStart;

    /**
     * 搜索红包发放结束时间(待删)
     */
    private String  rsSendtimeEnd;

    /**
     * 搜索红包到期时间开始时间(待删)
     */
    private String  rsClosetimeStart;

    /**
     * 搜索红包到期时间结束时间(待删)
     */
    private String  rsClosetimeEnd;

    /**
     * 奖励主题(待删)
     */
    private String  rsTheme;

    /**
     * 红包类型 1：新手专享红包 2：新手特权红包 3：成功进阶红包 4：项目奖励红包 5：个人奖励红包 6：推荐邀请红包
     */
    private Integer redType;

    /**
     * 红包发送表 中 红包表外键
     */
    private Integer rsRedId;

    /* 红包表新加字段 开始 */

    /**
     * 红包主题 abc_red.red_theme
     */
    private String  redTheme;
    /**
     * 搜索红包发放日期开始时间
     */
    private String  redSendtimeStart;

    /**
     * 搜索红包发放结束时间
     */
    private String  redSendtimeEnd;

    /**
     * 搜索红包到期时间开始时间
     */
    private String  redClosetimeStart;

    /**
     * 搜索红包到期时间结束时间
     */
    private String  redClosetimeEnd;

    
    /* 红包表新加字段 结束 */
    /**
     * 红包排序sql
     */
    private String order;

    public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getUserScope() {
		return userScope;
	}

	public void setUserScope(String userScope) {
		this.userScope = userScope;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRsState() {
        return rsState;
    }

    public void setRsState(Integer rsState) {
        this.rsState = rsState;
    }

    public String getRsSendtimeStart() {
        return rsSendtimeStart;
    }

    public void setRsSendtimeStart(String rsSendtimeStart) {
        this.rsSendtimeStart = rsSendtimeStart;
    }

    public String getRsSendtimeEnd() {
        return rsSendtimeEnd;
    }

    public void setRsSendtimeEnd(String rsSendtimeEnd) {
        this.rsSendtimeEnd = rsSendtimeEnd;
    }

    public String getRsClosetimeStart() {
        return rsClosetimeStart;
    }

    public void setRsClosetimeStart(String rsClosetimeStart) {
        this.rsClosetimeStart = rsClosetimeStart;
    }

    public String getRsClosetimeEnd() {
        return rsClosetimeEnd;
    }

    public void setRsClosetimeEnd(String rsClosetimeEnd) {
        this.rsClosetimeEnd = rsClosetimeEnd;
    }

    public String getRsTheme() {
        return rsTheme;
    }

    public void setRsTheme(String rsTheme) {
        this.rsTheme = rsTheme;
    }

    public Integer getRedType() {
        return redType;
    }

    public void setRedType(Integer redType) {
        this.redType = redType;
    }

    public Integer getRsRedId() {
        return rsRedId;
    }

    public void setRsRedId(Integer rsRedId) {
        this.rsRedId = rsRedId;
    }

    public String getRedTheme() {
        return redTheme;
    }

    public void setRedTheme(String redTheme) {
        this.redTheme = redTheme;
    }

    public String getRedSendtimeStart() {
        return redSendtimeStart;
    }

    public void setRedSendtimeStart(String redSendtimeStart) {
        this.redSendtimeStart = redSendtimeStart;
    }

    public String getRedSendtimeEnd() {
        return redSendtimeEnd;
    }

    public void setRedSendtimeEnd(String redSendtimeEnd) {
        this.redSendtimeEnd = redSendtimeEnd;
    }

    public String getRedClosetimeStart() {
        return redClosetimeStart;
    }

    public void setRedClosetimeStart(String redClosetimeStart) {
        this.redClosetimeStart = redClosetimeStart;
    }

    public String getRedClosetimeEnd() {
        return redClosetimeEnd;
    }

    public void setRedClosetimeEnd(String redClosetimeEnd) {
        this.redClosetimeEnd = redClosetimeEnd;
    }

	public String getRsClosetime() {
		return rsClosetime;
	}

	public void setRsClosetime(String rsClosetime) {
		this.rsClosetime = rsClosetime;
	}

}
