package com.autoserve.abc.service.biz.intf.score;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreConfigDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/21 22:10.
 */
public interface ScoreConfigService {
    /**
     * 新增积分兑现条目
     *
     * @param scoreConfigDO 积分兑现条目内容
     * @return BaseResult
     */
    public BaseResult createScoreConfig(ScoreConfigDO scoreConfigDO);

    /**
     * 查询积分兑换设置信息
     *
     * @param id 主键
     * @return
     */
    public PlainResult<ScoreConfigDO> findScoreConfigById(int id);

    /**
     * 修改积分兑现条目
     *
     * @param scoreConfigDO 积分兑现条目内容
     * @return BaseResult
     */
    public BaseResult modifyScoreConfig(ScoreConfigDO scoreConfigDO);

    /**
     * 查询积分兑换列表
     *
     * @param scoreConfigDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<ScoreConfigDO>
     */
    public PageResult<ScoreConfigDO> queryScoreConfigList(ScoreConfigDO scoreConfigDO, PageCondition pageCondition);

    /**
     * 删除积分兑现条目
     *
     * @param id 积分兑现条目id
     * @return BaseResult
     */
    public BaseResult removeScoreConfig(int id);
}
