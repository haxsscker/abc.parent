package com.autoserve.abc.service.biz.intf.credit;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CreditApplyDO;
import com.autoserve.abc.dao.dataobject.CreditJDO;
import com.autoserve.abc.dao.dataobject.search.CreditSearchDO;
import com.autoserve.abc.service.biz.entity.CreditApply;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2015/1/6 15:51.
 */
public interface CreditService {

    /**
     * 查询列表
     * 
     * @param creditJDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<CreditJDO>
     */
    public PageResult<CreditJDO> queryList(CreditJDO creditJDO, PageCondition pageCondition);

    /**
     * 搜索列表
     * 
     * @param searchDO
     * @param pageCondition
     * @return
     */
    public PageResult<CreditJDO> searchList(CreditSearchDO searchDO, PageCondition pageCondition);

    /**
     * 新建信用额度申请
     * 
     * @param creditApply
     * @return
     */
    public BaseResult createCreditApply(CreditApply creditApply);

    /**
     * 新建信用额度申请
     * 
     * @param creditApplyDO
     * @return
     */
    public BaseResult createCreditApply(CreditApplyDO creditApplyDO);

    /**
     * 查询信用额度信息（包括用户名等信息）
     * 
     * @param creditId 信用额度申请表主键
     * @return PlainResult<CreditJDO>
     */
    public PlainResult<CreditJDO> findFullCreditInfoById(int creditId);

    /**
     * 修改信用额度记录
     * 
     * @param creditApply
     * @return BaseResult
     */
    public BaseResult modifyCreditApply(CreditApply creditApply);

    /**
     * 审核后修改信用额度
     * 
     * @param creditId 信用额度记录id
     * @param userId 用户id
     * @param creditAmount 审核
     * @param passReview 审核通过与否
     * @return BaseResult
     */
    public BaseResult modifyCreditAfterReview(int creditId, ReviewState reviewState);

    /**
     * 发起信用申请审核
     * 
     * @param creditId
     * @return
     */
    BaseResult initReview(int creditId);

    /**
     * 查询用户获取积分的总数
     * 
     * @param userid
     * @return
     */
    PlainResult<Integer> queryCountScore(int userid);
}
