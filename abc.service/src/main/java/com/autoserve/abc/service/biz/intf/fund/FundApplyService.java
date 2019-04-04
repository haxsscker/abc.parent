/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.fund;

import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FundApplyDO;
import com.autoserve.abc.dao.dataobject.search.FundApplySearchDO;
import com.autoserve.abc.service.biz.entity.FundApply;
import com.autoserve.abc.service.biz.entity.FundProfit;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 有限合伙人服务
 * 
 * @author wangyongheng 2014/12/08
 */
public interface FundApplyService {
    /**
     * 添加有限合伙人信息
     * 
     * @param pojo 待添加的有限合伙人信息, 必选
     * @return BaseResult
     */
    BaseResult createFundApply(FundApply pojo);

    /**
     * 添加有限合伙人信息
     * 
     * @param fundApply 有限合伙人信息
     * @param fundProfitList 收益信息集合
     * @return
     */
    BaseResult createFundApply(FundApply fundApply, List<FundProfit> fundProfitList);

    /**
     * 修改有限合伙人信息
     * 
     * @param fundApply 有限合伙人信息
     * @param fundProfitList 收益信息集合
     * @return
     */
    public BaseResult updateFundApply(FundApply fundApply, List<FundProfit> fundProfitList);

    /**
     * 删除有限合伙人信息
     * 
     * @param pojo 待修改的有限合伙人信息，必选
     * @return BaseResult
     */
    BaseResult removeFundApply(FundApply pojo);

    /**
     * 修改有限合伙人信息
     * 
     * @param pojo 待修改的有限合伙人信息，必选
     * @return BaseResult
     */
    BaseResult modifyFundApply(FundApply pojo);

    /**
     * 查询单条有限合伙人信息
     * 
     * @param pojo 查询条件，必选
     * @return PlainResult<FundApply>
     */
    PlainResult<FundApply> queryById(int id);

    /**
     * 分页查询有限合伙人信息列表
     * 
     * @param fo 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<FundOrderDO>
     */
    public PageResult<FundApplyDO> queryList(FundApplyDO pojo, PageCondition pageCondition);

    /**
     * @param pojo
     * @return
     */
    public ListResult<FundApplyDO> queryAllApplyList(FundApplyDO pojo);

    /**
     * 按检索栏检索条件分页查询
     * 
     * @param pojo 查询条件
     * @param pageCondition 分页条件
     * @return
     */
    public PageResult<FundApplyDO> queryPageListBySearchParam(FundApplySearchDO pojo, PageCondition pageCondition);

}
