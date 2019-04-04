package com.autoserve.abc.web.vo.government;

/**
 * 机构修改记录VO（字段命名和前端保持一致）
 * @author RJQ 2014/12/17 21:32.
 */
public class GovUpdateHistoryVO {
    /**
     * 修改记录主键
     */
    private int history_id;

    /**
     * 机构主键
     */
    private int govId;

    /**
     * 机构名
     */
    private String gov_name;

    /**
     * 修改人
     */
    private String update_emp_name;

    /**
     * 修改日期
     */
    private String gov_edit_date;
    /**
     * 审核人
     */
    private String review_emp_name;
    /**
     * 审核日期
     */
    private String gov_check_date;
    /**
     * 审核意见
     */
    private String gov_check_idear;
    /**
     * 审核状态
     */
    private String gov_check_state;

    public String getGov_name() {
        return gov_name;
    }

    public void setGov_name(String gov_name) {
        this.gov_name = gov_name;
    }

    public int getGovId() {
        return govId;
    }

    public void setGovId(int govId) {
        this.govId = govId;
    }

    public String getUpdate_emp_name() {
        return update_emp_name;
    }

    public void setUpdate_emp_name(String update_emp_name) {
        this.update_emp_name = update_emp_name;
    }

    public String getReview_emp_name() {
        return review_emp_name;
    }

    public void setReview_emp_name(String review_emp_name) {
        this.review_emp_name = review_emp_name;
    }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public String getGov_edit_date() {
        return gov_edit_date;
    }

    public void setGov_edit_date(String gov_edit_date) {
        this.gov_edit_date = gov_edit_date;
    }

    public String getGov_check_date() {
        return gov_check_date;
    }

    public void setGov_check_date(String gov_check_date) {
        this.gov_check_date = gov_check_date;
    }

    public String getGov_check_idear() {
        return gov_check_idear;
    }

    public void setGov_check_idear(String gov_check_idear) {
        this.gov_check_idear = gov_check_idear;
    }

    public String getGov_check_state() {
        return gov_check_state;
    }

    public void setGov_check_state(String gov_check_state) {
        this.gov_check_state = gov_check_state;
    }
}
