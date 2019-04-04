package com.autoserve.abc.web.vo.score;

import java.math.BigDecimal;

/**
 * @author RJQ 2014/12/17 11:53.
 */
public class LevelVO {
    private Integer    sys_level_id;

    private String     sys_level_name;

    private BigDecimal sys_min_score;

    private BigDecimal sys_max_score;

    private String     sys_leve_pic;

    public Integer getSys_level_id() {
        return sys_level_id;
    }

    public void setSys_level_id(Integer sys_level_id) {
        this.sys_level_id = sys_level_id;
    }

    public String getSys_level_name() {
        return sys_level_name;
    }

    public void setSys_level_name(String sys_level_name) {
        this.sys_level_name = sys_level_name;
    }

    public BigDecimal getSys_min_score() {
        return sys_min_score;
    }

    public void setSys_min_score(BigDecimal sys_min_score) {
        this.sys_min_score = sys_min_score;
    }

    public BigDecimal getSys_max_score() {
        return sys_max_score;
    }

    public void setSys_max_score(BigDecimal sys_max_score) {
        this.sys_max_score = sys_max_score;
    }

    public String getSys_leve_pic() {
        return sys_leve_pic;
    }

    public void setSys_leve_pic(String sys_leve_pic) {
        this.sys_leve_pic = sys_leve_pic;
    }
}
