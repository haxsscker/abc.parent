package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;

/**
 * service 层 红包实体类 abc_red
 * 
 * @author fangrui 2014年12月27日 下午2:24:08
 */
public class Red {

    /**
     * 红包编号 abc_red.red_id
     */
    private Integer         redId;

    /**
     * 红包名称 abc_red.red_name
     */
    private String          redName;

    /**
     * 红包描述 abc_red.red_desc
     */
    private String          redDesc;

    /**
     * 红包类型 1：注册送红包 2：投资返送红包 3：活动派送红包 4：推荐送红包 5：积分兑换红包 6：项目奖励红包 abc_red.red_type
     */
    private RedenvelopeType redType;

    /**
     * 红包金额 abc_red.red_amount
     */
    private Double          redAmount;

    /**
     * 红包创建时间 abc_red.red_createtime
     */
    private Date            redCreatetime;

    /**
     * 创建红包的用户id abc_user abc_red.red_creator
     */
    private Integer         redCreator;

    /**
     * 红包机制状态 0：无效 1：有效 abc_red.red_state
     */
    private RedState        redState;

    /**
     * 红包发放次数,当红包为个人奖励类型时，记录个人奖励红包数；当红包为项目奖励类型时，记录项目奖励红包数
     * abc_red.red_reward_number
     */
    private Integer         redRewardNumber;

    /**
     * 红包发放金额 abc_red.red_amt
     */
    private Double          redAmt;

    /**
     * 红包主题 abc_red.red_theme
     */
    private String          redTheme;

    /**
     * 使用范围 abc_red.red_use_scope
     */
    private String          redUseScope;

    /**
     * 项目id abc_red.red_bizid
     */
    private Integer         redBizid;

    /**
     * 有效日期(红包失效时间) abc_red.red_closetime
     */
    private Integer         redClosetime;

    /**
     * 红包发放时间 abc_red.red_sendtime
     */
    private Date            redSendtime;

    /**
     * 奖励总金额（冗余字段）
     */
    private Double          totalAmount;

    /**
     * @return abc_red.red_id
     */
    public Integer getRedId() {
        return redId;
    }

    /**
     * @param Integer redId (abc_red.red_id )
     */
    public void setRedId(Integer redId) {
        this.redId = redId;
    }

    /**
     * @return abc_red.red_name
     */
    public String getRedName() {
        return redName;
    }

    /**
     * @param String redName (abc_red.red_name )
     */
    public void setRedName(String redName) {
        this.redName = redName;
    }

    /**
     * @return abc_red.red_desc
     */
    public String getRedDesc() {
        return redDesc;
    }

    /**
     * @param String redDesc (abc_red.red_desc )
     */
    public void setRedDesc(String redDesc) {
        this.redDesc = redDesc;
    }

    /**
     * @return abc_red.red_type
     */
    public RedenvelopeType getRedType() {
        return redType;
    }

    /**
     * @param Boolean redType (abc_red.red_type )
     */
    public void setRedType(RedenvelopeType redType) {
        this.redType = redType;
    }

    /**
     * @return abc_red.red_amount
     */
    public Double getRedAmount() {
        return redAmount;
    }

    /**
     * @param BigDecimal redAmount (abc_red.red_amount )
     */
    public void setRedAmount(Double redAmount) {
        this.redAmount = redAmount;
    }

    /**
     * @return abc_red.red_createtime
     */
    public Date getRedCreatetime() {
        return redCreatetime;
    }

    /**
     * @param Date redCreatetime (abc_red.red_createtime )
     */
    public void setRedCreatetime(Date redCreatetime) {
        this.redCreatetime = redCreatetime;
    }

    /**
     * @return abc_red.red_creator
     */
    public Integer getRedCreator() {
        return redCreator;
    }

    /**
     * @param Integer redCreator (abc_red.red_creator )
     */
    public void setRedCreator(Integer redCreator) {
        this.redCreator = redCreator;
    }

    /**
     * @return abc_red.red_state
     */
    public RedState getRedState() {
        return redState;
    }

    /**
     * @param Integer redState (abc_red.red_state )
     */
    public void setRedState(RedState redState) {
        this.redState = redState;
    }

    public Integer getRedRewardNumber() {
        return redRewardNumber;
    }

    public void setRedRewardNumber(Integer redRewardNumber) {
        this.redRewardNumber = redRewardNumber;
    }

    public Double getRedAmt() {
        return redAmt;
    }

    public void setRedAmt(Double redAmt) {
        this.redAmt = redAmt;
    }

    public String getRedTheme() {
        return redTheme;
    }

    public void setRedTheme(String redTheme) {
        this.redTheme = redTheme;
    }

    public String getRedUseScope() {
        return redUseScope;
    }

    public void setRedUseScope(String redUseScope) {
        this.redUseScope = redUseScope;
    }

    public Integer getRedBizid() {
        return redBizid;
    }

    public void setRedBizid(Integer redBizid) {
        this.redBizid = redBizid;
    }

    public Date getRedSendtime() {
        return redSendtime;
    }

    public void setRedSendtime(Date redSendtime) {
        this.redSendtime = redSendtime;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getRedClosetime() {
        return redClosetime;
    }

    public void setRedClosetime(Integer redClosetime) {
        this.redClosetime = redClosetime;
    }

}
