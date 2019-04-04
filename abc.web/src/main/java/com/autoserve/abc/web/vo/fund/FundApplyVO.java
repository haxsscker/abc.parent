package com.autoserve.abc.web.vo.fund;

import java.io.Serializable;

/**
 * @author wangyongheng 2014/12/18
 */
public class FundApplyVO implements Serializable {
	private static final long serialVersionUID = -998799298086695430L;

	/**
	 * 主键ID
	 */
	private Integer faFundId;

	/**
	 * 目项编号
	 */
	private String faFundNo;

	/**
	 * 基金名称
	 */
	private String faFundName;

	/**
	 * 基金公司
	 */
	private String faFundComp;

	/**
	 * 预计发行规模
	 */
	private String faFundMoney;

	/**
	 * 存续期
	 */
	private String faFundPeriod;

	/**
	 * 预期年收益率
	 */
	private String faFundRate;

	/**
	 * 最低认购金额
	 */
	private String faMinMoney;

	/**
	 * 投资行业
	 */
	private String faFundIndustry;

	/**
	 * 还款方式(1：等额本息 2：按月还息到期还本 3：等额本金)
	 */
	private String faPayType;

	/**
	 * 基金类型
	 */
	private String faFundType;

	/**
	 * 抵押率
	 */
	private String faMartgageRate;

	/**
	 * 抵押物
	 */
	private String faMartgageIntrol;

	/**
	 * 产品说明
	 */
	private String faFundIntrol;

	/**
	 * 资金用途
	 */
	private String faFundUse;

	/**
	 * 风险控制
	 */
	private String faFundRisk;

	/**
	 * 还款来源
	 */
	private String faPayResource;

	/**
	 * 项目简介
	 */
	private String faProjectIntrol;

	/**
	 * 基金公司简介
	 */
	private String faCompIntrol;

	/**
	 * 是否发布
	 */
	private String faFundState;

	/**
	 * 发售日期
	 */
	private String faAddDate;

	public Integer getFaFundId() {
		return faFundId;
	}

	public void setFaFundId(Integer faFundId) {
		this.faFundId = faFundId;
	}

	public String getFaFundNo() {
		return faFundNo;
	}

	public void setFaFundNo(String faFundNo) {
		this.faFundNo = faFundNo;
	}

	public String getFaFundName() {
		return faFundName;
	}

	public void setFaFundName(String faFundName) {
		this.faFundName = faFundName;
	}

	public String getFaFundComp() {
		return faFundComp;
	}

	public void setFaFundComp(String faFundComp) {
		this.faFundComp = faFundComp;
	}

	public String getFaFundMoney() {
		return faFundMoney;
	}

	public void setFaFundMoney(String faFundMoney) {
		this.faFundMoney = faFundMoney;
	}

	public String getFaFundPeriod() {
		return faFundPeriod;
	}

	public void setFaFundPeriod(String faFundPeriod) {
		this.faFundPeriod = faFundPeriod;
	}

	public String getFaFundRate() {
		return faFundRate;
	}

	public void setFaFundRate(String faFundRate) {
		this.faFundRate = faFundRate;
	}

	public String getFaMinMoney() {
		return faMinMoney;
	}

	public void setFaMinMoney(String faMinMoney) {
		this.faMinMoney = faMinMoney;
	}

	public String getFaFundIndustry() {
		return faFundIndustry;
	}

	public void setFaFundIndustry(String faFundIndustry) {
		this.faFundIndustry = faFundIndustry;
	}

	public String getFaPayType() {
		return faPayType;
	}

	public void setFaPayType(String faPayType) {
		this.faPayType = faPayType;
	}

	public String getFaFundType() {
		return faFundType;
	}

	public void setFaFundType(String faFundType) {
		this.faFundType = faFundType;
	}

	public String getFaMartgageRate() {
		return faMartgageRate;
	}

	public void setFaMartgageRate(String faMartgageRate) {
		this.faMartgageRate = faMartgageRate;
	}

	public String getFaMartgageIntrol() {
		return faMartgageIntrol;
	}

	public void setFaMartgageIntrol(String faMartgageIntrol) {
		this.faMartgageIntrol = faMartgageIntrol;
	}

	public String getFaFundIntrol() {
		return faFundIntrol;
	}

	public void setFaFundIntrol(String faFundIntrol) {
		this.faFundIntrol = faFundIntrol;
	}

	public String getFaFundUse() {
		return faFundUse;
	}

	public void setFaFundUse(String faFundUse) {
		this.faFundUse = faFundUse;
	}

	public String getFaFundRisk() {
		return faFundRisk;
	}

	public void setFaFundRisk(String faFundRisk) {
		this.faFundRisk = faFundRisk;
	}

	public String getFaPayResource() {
		return faPayResource;
	}

	public void setFaPayResource(String faPayResource) {
		this.faPayResource = faPayResource;
	}

	public String getFaProjectIntrol() {
		return faProjectIntrol;
	}

	public void setFaProjectIntrol(String faProjectIntrol) {
		this.faProjectIntrol = faProjectIntrol;
	}

	public String getFaCompIntrol() {
		return faCompIntrol;
	}

	public void setFaCompIntrol(String faCompIntrol) {
		this.faCompIntrol = faCompIntrol;
	}

	public String getFaFundState() {
		return faFundState;
	}

	public void setFaFundState(String faFundState) {
		this.faFundState = faFundState;
	}

	public String getFaAddDate() {
		return faAddDate;
	}

	public void setFaAddDate(String faAddDate) {
		this.faAddDate = faAddDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
