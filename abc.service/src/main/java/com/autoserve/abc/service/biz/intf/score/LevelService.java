package com.autoserve.abc.service.biz.intf.score;

import java.math.BigDecimal;
import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LevelDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 用户等级服务
 * 
 * @author RJQ 2014/11/22 10:47.
 */
public interface LevelService {
    /**
     * 根据积分查询对应等级
     * 
     * @param score 积分值
     * @return PlainResult<LevelDO>
     */
    public PlainResult<LevelDO> findLevelByScore(BigDecimal score);

    /**
     * 查询等级列表
     * 
     * @return PageResult<LevelDO>
     */
    public PageResult<LevelDO> findAllLevel(LevelDO levelDO, PageCondition pageCondition);

    public List<LevelDO> findLevelByName(String name);

    /**
     * 修改等级
     * 
     * @param levelDOs 等级列表
     * @return
     */
    public BaseResult modifyLevel(List<LevelDO> levelDOs);

    /**
     * 增加等级
     * 
     * @param levelDO
     * @return
     */
    public BaseResult createLevel(LevelDO levelDO);

    /**
     * 根据等级ID查询等级信息
     * 
     * @param leveid
     * @return
     */
    public PlainResult<LevelDO> findLevelByID(Integer leveid);

    /**
     * 修改等级信息
     * 
     * @param levelDO
     * @return
     */
    public BaseResult editLevel(LevelDO levelDO);

    /**
     * 删除等级信息
     * 
     * @param leveid
     * @return
     */
    public BaseResult delLevel(Integer leveid);
}
