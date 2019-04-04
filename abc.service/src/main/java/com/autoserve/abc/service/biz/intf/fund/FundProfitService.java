/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.fund;

import com.autoserve.abc.service.biz.entity.FundProfit;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 有限合伙预约-收益服务
 *
 * @author wangyongheng 2014/12/19
 */
public interface FundProfitService {
    /**
     * 添加有限合伙预约-收益信息
     *
     * @param pojo 待添加的有限合伙预约-收益信息, 必选
     * @return BaseResult
     */
    BaseResult createFundProfit(FundProfit pojo);

    /**
     * 删除有限合伙预约-收益信息
     *
     * @param pojo 待修改的有限合伙预约-收益信息，必选
     * @return BaseResult
     */
    BaseResult removeFundProfit(FundProfit pojo);
    
    /**
     * 修改有限合伙预约-收益信息
     *
     * @param pojo 待修改的有限合伙预约-收益信息，必选
     * @return BaseResult
     */
    BaseResult modifyFundProfit(FundProfit pojo);

    /**
     * 查询单条有限合伙预约-收益信息
     *
     * @param pojo 查询条件，必选
     * @return PlainResult<FundProfit>
     */
    PlainResult<FundProfit> queryById(int id);

    /**
     * 查询多条有限合伙预约-收益信息
     *
     * @param pojo 查询条件，必选
     * @return PlainResult<FundProfit>
     */
    ListResult<FundProfit> queryList(FundProfit pojo);


}
