/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.fund;

import com.autoserve.abc.service.biz.entity.FundCheck;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 有限合伙人预约审核服务
 *
 * @author wangyongheng 2014/12/08
 */
public interface FundCheckService {
    /**
     * 添加有限合伙人预约审核信息
     *
     * @param pojo 待添加的有限合伙人预约审核信息, 必选
     * @return BaseResult
     */
    BaseResult createFundCheck(FundCheck pojo);

    /**
     * 删除有限合伙人预约审核信息
     *
     * @param pojo 待修改的有限合伙人预约审核信息，必选
     * @return BaseResult
     */
    BaseResult removeFundCheck(FundCheck pojo);
    
    /**
     * 修改有限合伙人预约审核信息
     *
     * @param pojo 待修改的有限合伙人预约审核信息，必选
     * @return BaseResult
     */
    BaseResult modifyFundCheck(FundCheck pojo);

    /**
     * 查询单条有限合伙人预约审核信息
     *
     * @param pojo 查询条件，必选
     * @return PlainResult<FundCheck>
     */
    PlainResult<FundCheck> queryById(int id);


}
