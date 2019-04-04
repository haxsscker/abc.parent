package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.ActUserDO;

public interface ActUserDao extends BaseDao<ActUserDO, Integer> {
    /**
     * 获取前几个中奖信息  act_id
     * 
     * @return List<ActPrizeDO>
     */
    List<ActUserDO> findTopUser(@Param("actId") int actId, @Param("topNum") int topNum);
    
    /**
     * 查询今天用户有没有参加
     * @param actId
     * @param auUserId
     * @return
     */
    int countUserToday(@Param("actId") int actId, @Param("auUserId") int auUserId);
    
    /**
     * 查询用户有没有参加
     * @param actId
     * @param auUserId
     * @return
     */
    int countUserPrize(@Param("actId") int actId, @Param("auUserId") int auUserId);
    
    /**
     * 获取自己的中奖信息
     * 
     * @return List<ActPrizeDO>
     */
    List<ActUserDO> findUserPrize(@Param("actId") int actId, @Param("auUserId") int auUserId);
    
    /**
     * 是否有参与资格
     * @param auUserId
     * @param invMoney
     * @return
     */
    int isInvestUser(@Param("auUserId") int auUserId, @Param("invMoney") int invMoney, 
    		@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 获取活动类型 1:抽奖 2:参与 3:问答 ...
     * @param actId
     * @return
     */
    int getActType(@Param("actId") int actId);
    
    /**
     * 是否已经报名
     * @param uname
     * @param uPhone
     * @return
     */
    int isSign(@Param("uName")String uName, @Param("uPhone")String uPhone);
    
    /**
     * 标的奖励活动是否开启
     * @param uname
     * @param uPhone
     * @return
     */
    int isLoanPriceActive(@Param("actId") int actId);
    
    /**
     * 报名
     * @param uName
     * @param uPhone
     * @param uNote
     * @return
     */
    void actSignIn(@Param("uName")String uName, @Param("uPhone")String uPhone, @Param("uNote")String uNote);
}
