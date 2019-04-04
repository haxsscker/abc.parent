package com.autoserve.abc.service.biz.intf.user;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RealnameAuthDO;
import com.autoserve.abc.dao.dataobject.RealnameAuthJDO;
import com.autoserve.abc.service.biz.entity.RealnameAuth;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/26 21:56.
 */
public interface RealnameAuthService {

    /**
     * 查询用户列表
     *
     * @param realnameAuthDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<RealnameAuthDO>
     */
    public PageResult<RealnameAuthDO> queryList(RealnameAuthDO realnameAuthDO, PageCondition pageCondition);

    /**
     * 查询用户列表
     *
     * @param realnameAuthJDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<RealnameAuthDO>
     */
    public PageResult<RealnameAuthJDO> queryRealnameAuthJDOList(RealnameAuthJDO realnameAuthJDO,
                                                                PageCondition pageCondition);

    /**
     * 查询实名认证信息
     *
     * @param id 实名认证表主键ID
     * @return PlainResult<RealnameAuthDO>
     */
    public PlainResult<RealnameAuthDO> findRealNameAuthById(int id);

    /**
     * 查询实名认证信息
     *
     * @param id 实名认证表主键ID
     * @return PlainResult<RealnameAuthJDO>
     */
    public PlainResult<RealnameAuthJDO> findRealNameAuthJDOById(RealnameAuthJDO realnameAuthJDO);

    /**
     * 实名认证审核通过
     *
     * @param id 实名认证表主键id
     * @param reviewNote 审核意见
     * @return BaseResult
     */
    public BaseResult realNamePassReview(int id, String reviewNote);

    /**
     * 实名认证审核未通过
     *
     * @param id 实名认证表主键id
     * @param reviewNote 审核意见
     * @return BaseResult
     */
    public BaseResult realNameFailedPassReview(int id, String reviewNote);

    /**
     * 发送实名认证审核
     *
     * @param id 实名认证表主键id
     * @return BaseResult
     */
    public BaseResult transmitRealNameAudit(int id);

    /**
     * 更新实名认证审核
     *
     * @param id 实名认证表主键id
     * @return BaseResult
     */
    public BaseResult updateRealNameAudit(RealnameAuth realNameAuth);

    /**
     * 前台实名认证
     *
     * @param id 实名认证表主键id
     * @return BaseResult
     */
    public BaseResult realNameAudit(int userId);

}
