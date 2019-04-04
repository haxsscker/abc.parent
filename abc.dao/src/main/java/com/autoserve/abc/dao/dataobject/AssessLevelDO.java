package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * 评估等级信息
 *
 * @author sxd
 */
public class AssessLevelDO {
    /**
     * 主键
     * abc_assess_level.ass_id
     */
    private Integer assId;

    /**
     * 等级名称
     * abc_assess_level.ass_name
     */
    private String assName;

    /**
     * 评估类型
     * abc_assess_level.ass_type
     */
    private Integer assType;

    /**
     * 
     * abc_assess_level.ass_minscore
     */
    private Integer assMinscore;
    
    /**
     * 
     * abc_assess_level.ass_maxscore
     */
    private Integer assMaxscore;

    /**
     * 投资金额上限
     * abc_assess_level.ass_value
     */
    private BigDecimal assValue;

	public Integer getAssId() {
		return assId;
	}

	public void setAssId(Integer assId) {
		this.assId = assId;
	}

	public String getAssName() {
		return assName;
	}

	public void setAssName(String assName) {
		this.assName = assName;
	}

	public Integer getAssType() {
		return assType;
	}

	public void setAssType(Integer assType) {
		this.assType = assType;
	}

	public Integer getAssMinscore() {
		return assMinscore;
	}

	public void setAssMinscore(Integer assMinscore) {
		this.assMinscore = assMinscore;
	}

	public Integer getAssMaxscore() {
		return assMaxscore;
	}

	public void setAssMaxscore(Integer assMaxscore) {
		this.assMaxscore = assMaxscore;
	}

	public BigDecimal getAssValue() {
		return assValue;
	}

	public void setAssValue(BigDecimal assValue) {
		this.assValue = assValue;
	}
}
