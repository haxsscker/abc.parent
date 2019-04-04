package com.autoserve.abc.dao.dataobject;

public class UserRecommendDO extends UserDO {
    /**
     * 推荐人用户名
     */
    private String recommendUserName;
    /**
     * 推荐人真实名
     */
    private String recommendUserRealName;

    public String getRecommendUserName() {
        return recommendUserName;
    }

    public void setRecommendUserName(String recommendUserName) {
        this.recommendUserName = recommendUserName;
    }

    public String getRecommendUserRealName() {
        return recommendUserRealName;
    }

    public void setRecommendUserRealName(String recommendUserRealName) {
        this.recommendUserRealName = recommendUserRealName;
    }

}
