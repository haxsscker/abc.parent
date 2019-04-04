package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 发放红包记录DO
 * 
 * @author lipeng 2014年12月26日 下午8:05:22
 */
public class RedsendDO {
    /**
     * 发放的红包记录编号 abc_red_send.rs_id
     */
    private Integer rsId;

    /**
     * 红包机制 abc_redenvelope abc_red_send.rs_red_id
     */
    private Integer rsRedId;

    /**
     * 红包发放金额 abc_red_send.rs_amt
     */
    private Double  rsAmt;

    /**
     * 红包奖励主题 abc_red_send.rs_theme
     */
    private String  rsTheme;

    /**
     * 红包有效金额 abc_red_send.rs_valid_amount
     */
    private Double  rsValidAmount;

    /**
     * 红包奖励类型：1：个人奖励；2：项目奖励 abc_red_send.rs_type
     */
    private Integer rsType;

    /**
     * 红包用户id abc_red_send.rs_userid
     */
    private Integer rsUserid;

    /**
     * 项目id abc_red_send.rs_Bizid
     */
    private Integer rsBizid;

    /**
     * 使用范围（如：投资|受让|收购）多个产品范围时，用“|”分割 abc_red_send.rs_use_scope
     */
    private String  rsUseScope;

    /**
     * 红包有效使用期限 即发送红包后的有效使用时间 单位/月 abc_red_send.rs_lifetime
     */
    private Integer rsLifetime;

    /**
     * 投资奖励自动发放条件 abc_red_send.rs_invest_amount
     */
    private Double  rsInvestAmount;

    /**
     * 红包开始有效时间 abc_red_send.rs_starttime
     */
    private Date    rsStarttime;

    /**
     * 红包失效时间 abc_red_send.rs_closetime
     */
    private Date    rsClosetime;

    /**
     * 红包发放时间 abc_red_send.rs_sendtime
     */
    private Date    rsSendtime;

    /**
     * 发放红包的用户id abc_user abc_red_send.rs_sender
     */
    private Integer rsSender;

    /**
     * 红包使用状态 : 0:失效 1:未使用，2：已使用 abc_red_send.rs_state
     */
    private Integer rsState;

    /**
     * @return abc_red_send.rs_id
     */
    public Integer getRsId() {
        return rsId;
    }

    /**
     * @param Integer rsId (abc_red_send.rs_id )
     */
    public void setRsId(Integer rsId) {
        this.rsId = rsId;
    }

    /**
     * @return abc_red_send.rs_red_id
     */
    public Integer getRsRedId() {
        return rsRedId;
    }

    /**
     * @param Integer rsRedId (abc_red_send.rs_red_id )
     */
    public void setRsRedId(Integer rsRedId) {
        this.rsRedId = rsRedId;
    }

    /**
     * @return abc_red_send.rs_amt
     */
    public Double getRsAmt() {
        return rsAmt;
    }

    /**
     * @param BigDecimal rsAmt (abc_red_send.rs_amt )
     */
    public void setRsAmt(Double rsAmt) {
        this.rsAmt = rsAmt;
    }

    /**
     * @return abc_red_send.rs_theme
     */
    public String getRsTheme() {
        return rsTheme;
    }

    /**
     * @param String rsTheme (abc_red_send.rs_theme )
     */
    public void setRsTheme(String rsTheme) {
        this.rsTheme = rsTheme;
    }

    /**
     * @return abc_red_send.rs_valid_amount
     */
    public Double getRsValidAmount() {
        return rsValidAmount;
    }

    /**
     * @param BigDecimal rsValidAmount (abc_red_send.rs_valid_amount )
     */
    public void setRsValidAmount(Double rsValidAmount) {
        this.rsValidAmount = rsValidAmount;
    }

    /**
     * @return abc_red_send.rs_type
     */
    public Integer getRsType() {
        return rsType;
    }

    /**
     * @param Boolean rsType (abc_red_send.rs_type )
     */
    public void setRsType(Integer rsType) {
        this.rsType = rsType;
    }

    /**
     * @return abc_red_send.rs_userid
     */
    public Integer getRsUserid() {
        return rsUserid;
    }

    /**
     * @param Integer rsUserid (abc_red_send.rs_userid )
     */
    public void setRsUserid(Integer rsUserid) {
        this.rsUserid = rsUserid;
    }

    /**
     * @return abc_red_send.rs_use_scope
     */
    public String getRsUseScope() {
        return rsUseScope;
    }

    /**
     * @param String rsUseScope (abc_red_send.rs_use_scope )
     */
    public void setRsUseScope(String rsUseScope) {
        this.rsUseScope = rsUseScope;
    }

    /**
     * @return abc_red_send.rs_lifetime
     */
    public Integer getRsLifetime() {
        return rsLifetime;
    }

    /**
     * @param Integer rsLifetime (abc_red_send.rs_lifetime )
     */
    public void setRsLifetime(Integer rsLifetime) {
        this.rsLifetime = rsLifetime;
    }

    /**
     * @return abc_red_send.rs_invest_amount
     */
    public Double getRsInvestAmount() {
        return rsInvestAmount;
    }

    public Integer getRsBizid() {
        return rsBizid;
    }

    public void setRsBizid(Integer rsBizid) {
        this.rsBizid = rsBizid;
    }

    /**
     * @param BigDecimal rsInvestAmount (abc_red_send.rs_invest_amount )
     */
    public void setRsInvestAmount(Double rsInvestAmount) {
        this.rsInvestAmount = rsInvestAmount;
    }

    /**
     * @return abc_red_send.rs_starttime
     */
    public Date getRsStarttime() {
        return rsStarttime;
    }

    /**
     * @param Date rsStarttime (abc_red_send.rs_starttime )
     */
    public void setRsStarttime(Date rsStarttime) {
        this.rsStarttime = rsStarttime;
    }

    /**
     * @return abc_red_send.rs_closetime
     */
    public Date getRsClosetime() {
        return rsClosetime;
    }

    /**
     * @param Date rsClosetime (abc_red_send.rs_closetime )
     */
    public void setRsClosetime(Date rsClosetime) {
        this.rsClosetime = rsClosetime;
    }

    /**
     * @return abc_red_send.rs_sendtime
     */
    public Date getRsSendtime() {
        return rsSendtime;
    }

    /**
     * @param Date rsSendtime (abc_red_send.rs_sendtime )
     */
    public void setRsSendtime(Date rsSendtime) {
        this.rsSendtime = rsSendtime;
    }

    /**
     * @return abc_red_send.rs_sender
     */
    public Integer getRsSender() {
        return rsSender;
    }

    /**
     * @param Integer rsSender (abc_red_send.rs_sender )
     */
    public void setRsSender(Integer rsSender) {
        this.rsSender = rsSender;
    }

    /**
     * @return abc_red_send.rs_state
     */
    public Integer getRsState() {
        return rsState;
    }

    /**
     * @param Integer rsState (abc_red_send.rs_state )
     */
    public void setRsState(Integer rsState) {
        this.rsState = rsState;
    }

}
