/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.fund;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FundOrderApplyUserJDO;
import com.autoserve.abc.dao.dataobject.FundOrderDO;
import com.autoserve.abc.service.biz.entity.FundOrder;
import com.autoserve.abc.service.biz.entity.FundOrderApplyUser;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 有限合伙人预约服务
 * 
 * @author wangyongheng 2014/12/08
 */
public interface FundOrderService {
    /**
     * 添加有限合伙人预约信息
     * 
     * @param pojo 待添加的有限合伙人信息, 必选
     * @return BaseResult
     */
    BaseResult createFundOrder(FundOrder pojo);

    /**
     * 删除有限合伙人预约信息
     * 
     * @param pojo 待修改的有限合伙人信息，必选
     * @return BaseResult
     */
    BaseResult removeFundOrder(FundOrder pojo);

    /**
     * 修改有限合伙人预约信息
     * 
     * @param pojo 待修改的有限合伙人信息，必选
     * @return BaseResult
     */
    BaseResult modifyFundOrder(FundOrder pojo);

    /**
     * 查询单条有限合伙人预约信息
     * 
     * @param pojo 查询条件，必选
     * @return PlainResult<FundOrder>
     */

    PlainResult<FundOrder> queryById(int id);

    /**
     * 有限预约审核通过
     * 
     * @param id 表主键id
     * @param 审核意见
     * @return BaseResult
     */
    public BaseResult passReview(int id, String reviewNote);

    /**
     * 有限预约审核未通过
     * 
     * @param id 表主键id
     * @param reviewNote 审核意见
     * @return BaseResult
     */
    public BaseResult failedPassReview(int id, String reviewNote);

    public PageResult<FundOrderDO> queryList(FundOrderDO pojo, PageCondition pageCondition);

    public PageResult<FundOrderApplyUserJDO> queryList(FundOrderApplyUserJDO pojo, PageCondition pageCondition);

    PlainResult<FundOrderApplyUser> queryByOrderId(Integer id);

}
