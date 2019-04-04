/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan;

import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.BuyLoanTraceRecord;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 收购标服务
 *
 * @author segen189 2014年11月17日 下午6:14:59
 */
public interface BuyLoanService {
    /**
     * 添加收购标信息
     *
     * @param pojo 待添加的收购标信息, 必选
     * @return PlainResult<Integer>
     */
    PlainResult<Integer> createBuyLoan(BuyLoan pojo);

    /**
     * 修改收购标信息
     *
     * @param pojo 待修改的收购标信息，必选
     * @param traceRecord 项目跟踪状态记录，如果不修改标状态则此值可选，如果修改标状态则此值必选
     * @return BaseResult
     */
    BaseResult modifyBuyLoanInfo(BuyLoan pojo, BuyLoanTraceRecord traceRecord);

    /**
     * 修改收购标状态
     *
     * @param traceRecord 收购项目跟踪状态记录
     * @return BaseResult
     */
    BaseResult changeBuyLoanState(BuyLoanTraceRecord traceRecord);

    /**
     * 查询单条收购标信息
     *
     * @param id 收购标id，必选
     * @return PlainResult<BuyLoan>
     */
    PlainResult<BuyLoan> queryById(int id);

    /**
     * 查询多条收购标信息
     *
     * @param id 收购标id列表，必选
     * @return PlainResult<BuyLoan>
     */
    ListResult<BuyLoan> queryByIds(List<Integer> ids);

    /**
     * 查询单条收购标信息
     *
     * @param pojo 查询条件，必选
     * @return PlainResult<BuyLoan>
     */
    PlainResult<BuyLoan> queryByParam(BuyLoan pojo);

    /**
     * 查询分页的收购标列表
     *
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<BuyLoan>
     */
    PageResult<BuyLoan> queryListByParam(BuyLoan pojo, PageCondition pageCondition);
}
