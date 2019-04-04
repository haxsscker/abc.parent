package com.autoserve.abc.service.biz.intf.activity;

import com.autoserve.abc.dao.dataobject.ActHolidayDO;
import com.autoserve.abc.dao.dataobject.ActPrizeDO;
import com.autoserve.abc.dao.dataobject.ActUserDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;

/**
 * 前台活动业务类
 * 
 * @author sxd
 */
public interface ActivityService {

    /**
     * 新增用户
     * 
     * @param userDO 用户信息
     * @return BaseResult
     */
    public BaseResult createActUser(ActUserDO actUserDO);

    /**
     * 判断今天用户是否参与了活动
     * 
     * @param 
     * @return
     */
    public int countUserToday(int actId, int userId);
    
    /**
     * 判断是否参与了活动
     * 
     * @param 
     * @return
     */
    public int countUserPrize(int actId, int userId);
    
    /**
     * 查询奖项中奖数量
     * @param apId
     * @return
     */
    public int countPrizeUser(int apId, int actId);
    
    /**
     * 查询前几个中奖信息
     * @return
     */
    ListResult<ActUserDO> findTopUser(int actId, int topNum);
    
    /**
     * 查询自己的中奖信息
     * @return
     */
    ListResult<ActUserDO> findUserPrize(int actId, int userId);
    
    /**
     * 查询奖项
     * @return
     */
    ListResult<ActPrizeDO> findAllPrize(int actId);
    
    /**
     * 查询节日
     * @return
     */
    ActHolidayDO findAllHoliday(int actId, String ahDay);
    
    /**
     * 是否可参与活动者
     * @param userId
     * @param invMoney
     * @param startTime
     * @param endTime
     * @return
     */
    public boolean isInvestUser(int userId, int invMoney, String startTime, String endTime);
    
    /**
     * 获取活动类型  1:抽奖 2:参与 3:问答 ...
     * @param actId
     * @return
     */
    int getActType(int actId);
    
    /**
     * 是否已经报名
     * @param uname
     * @param uPhone
     * @return
     */
    int isSign(String uName, String uPhone);
    
    /**
     * 报名
     * @param uName
     * @param uPhone
     * @param uNote
     * @return
     */
    void actSignIn(String uName, String uPhone, String uNote);
    
    /**
     * 判断标的奖励活动是否开启
     * @param actId
     * @return
     */
    boolean isLoanPriceActive(int actId);
}
