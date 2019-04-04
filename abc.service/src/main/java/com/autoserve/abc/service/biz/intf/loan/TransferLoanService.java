/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan;

import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 普通标服务
 *
 * @author segen189 2014年11月17日 下午6:14:59
 */
public interface TransferLoanService {
    /**
     * 添加发标信息
     *
     * @param pojo 待添加的标信息, 必选
     * @return PlainResult<Integer>
     */
    PlainResult<Integer> createTransferLoan(TransferLoan pojo);

    /**
     * 修改转让标信息
     *
     * @param pojo 待修改的转让标信息，必选
     * @param traceRecord 项目跟踪状态记录，如果不修改标状态则此值可选，如果修改标状态则此值必选
     * @return BaseResult
     */
    BaseResult modifyTransferLoanInfo(TransferLoan pojo, TransferLoanTraceRecord traceRecord);

    /**
     * 修改转让标状态
     *
     * @param traceRecord 转让项目跟踪状态记录
     * @return BaseResult
     */
    BaseResult changeTransferLoanState(TransferLoanTraceRecord traceRecord);

    /**
     * 查询单条转让标信息
     *
     * @param id 转让标id，必选
     * @return PlainResult<TransferLoan>
     */
    PlainResult<TransferLoan> queryById(int id);

    /**
     * 查询id包含在ids列表里的转让标信息集合
     *
     * @param ids 查询条件，必选
     * @return ListResult<TransferLoan> 如果结果不含任何数据会返回错误结果；否则返回成功数据
     */
    ListResult<TransferLoan> queryByIds(List<Integer> ids);

    /**
     * 查询单条转让标信息
     *
     * @param pojo 查询条件，必选
     * @return PlainResult<TransferLoan>
     */
    PlainResult<TransferLoan> queryByParam(TransferLoan pojo);

    /**
     * 查询分页的转让标列表
     *
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<TransferLoan>
     */
    PageResult<TransferLoan> queryListByParam(TransferLoan pojo, PageCondition pageCondition);
    
    /**
     * 查询分页的转让标列表
     *
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<TransferLoan>
     */
    PageResult<TransferLoanJDO> queryListByParam(TransferLoanSearchDO pojo, PageCondition pageCondition);

    /**
     * 查询转让标列表
     *
     * @param pojo 查询条件，可选
     * @return ListResult<TransferLoan>
     */
    ListResult<TransferLoanJDO> queryListByParam(TransferLoanSearchDO pojo);
    
    /**
     * 查询转让项目跟踪列表
     *
     * @param transferLoanId 转让标id，必选
     * @param pageCondition 分页条件，必选
     * @return
     */
    PageResult<TransferLoanTraceRecord> queryTraceRecordList(int transferLoanId, PageCondition pageCondition);
    
    /**
     * 查询优选债券
     * xiatt 20160630
     */
    ListResult<TransferLoanJDO> queryTrOptimization(Integer topN);
    
    public ListResult<TransferLoan> queryListByParam(TransferLoan pojo);
}
