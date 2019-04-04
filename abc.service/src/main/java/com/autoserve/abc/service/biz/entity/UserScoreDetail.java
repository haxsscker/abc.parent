package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;

import java.util.Date;
import java.util.List;

/**
 * 用户积分明细
 *
 * @author RJQ 2014/11/26 18:46.
 */
public class UserScoreDetail {
    /**
     * 主键
     * abc_user.user_id
     */
    private Integer userId;

    /**
     * 用户名
     * abc_user.user_name
     */
    private String userName;

    /**
     * 真实姓名
     * abc_user.user_real_name
     */
    private String userRealName;

    /**
     * 用户积分
     * abc_user.user_score
     */
    private Integer userScore;

    /**
     * 积分最后修改时间
     * abc_user.user_score_lastmodifytime
     */
    private Date userScoreLastmodifytime;

    /**
     * 用户积分变动记录
     */
    private List<ScoreHistoryWithValueDO> scoreHistoryDOList;

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

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public Date getUserScoreLastmodifytime() {
        return userScoreLastmodifytime;
    }

    public void setUserScoreLastmodifytime(Date userScoreLastmodifytime) {
        this.userScoreLastmodifytime = userScoreLastmodifytime;
    }

    public List<ScoreHistoryWithValueDO> getScoreHistoryDOList() {
        return scoreHistoryDOList;
    }

    public void setScoreHistoryDOList(List<ScoreHistoryWithValueDO> scoreHistoryDOList) {
        this.scoreHistoryDOList = scoreHistoryDOList;
    }
}
