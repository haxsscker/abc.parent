package com.autoserve.abc.service.biz.intf.score;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 积分服务
 *
 * @author RJQ 2014/11/22 14:29.
 */
public interface ScoreService {

    /**
     * 查询积分类型
     *
     * @param id 积分类型ID
     * @return PlainResult<ScoreDO>
     */
    public PlainResult<ScoreDO> findById(int id);

    /**
     * 查询积分类型列表
     *
     * @param scoreDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<ScoreDO>
     */
    public PageResult<ScoreDO> queryScoreList(ScoreDO scoreDO, PageCondition pageCondition);

    /**
     * 新增积分选项
     *
     * @param scoreDO 积分选项信息
     * @return BaseResult
     */
    public BaseResult createScore(ScoreDO scoreDO);

    /**
     * 修改积分类型 逻辑修改：先将原来的积分类型状态位置为已删除状态，然后插入一条新的积分类型记录
     * 原因：abc_score_history表是积分兑换记录，记录了积分类型的id作为外键，如果后来该积分类型的信息做了修改，这条积分兑换记录就失真了
     * 所以修改积分记录实际上是插入了新的记录，旧的记录保存下来
     *
     * @param scoreDO 积分选项信息
     * @return BaseResult
     */
    public BaseResult modifyScore(ScoreDO scoreDO);

    /**
     * 启用某个积分选项
     *
     * @param id 积分选项id
     * @return BaseResult
     */
    public BaseResult enableScore(int id);

    /**
     * 禁用积分选项
     *
     * @param id 积分选项id
     * @return BaseResult
     */
    public BaseResult disableScore(int id);

    /**
     * 删除积分选项（逻辑删除，置状态位） 注意：ScoreType枚举类型中定义的积分类型，目前不允许删除，需要做判断
     *
     * @param id 积分选项id
     * @return BaseResult
     */
    public BaseResult removeScore(int id);

    /**
     * 积分兑换红包
     * 
     * @param userId 用户id 必选
     * @param score 用来兑换的积分数 可选
     * @return BaseResult
     */
    public BaseResult convertRedEnvelope(int userId, Integer score);

    /**
     * 查询所有积分类型列表
     *
     * @return
     */
    public ListResult<ScoreDO> findAllList();

    /**
     * 查询积分类型
     * 
     * @param code 积分代码（不允许重复）
     * @return ScoreDO
     */
    public PlainResult<ScoreDO> findByScoreCode(String code);

    /**
     * 积分兑换红包
     * 
     * @param userId
     * @param creditTypeId
     * @return
     */
    public BaseResult creditConvertRed(int userId, Integer creditTypeId);

}
