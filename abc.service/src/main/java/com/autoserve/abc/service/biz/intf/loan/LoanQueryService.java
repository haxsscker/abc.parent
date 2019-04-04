/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan;

import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LoanReportDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.dao.dataobject.summary.PostageSummaryDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.enums.GovProvideGuarState;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 普通标查询服务
 * 
 * @author segen189 2015年1月16日 上午10:37:17
 */
public interface LoanQueryService {

    /**
     * 判断是否存在项目依赖于机构：<br>
     * 如果是小贷机构，检查是否存在借款、投资<br>
     * 如果是担保机构，检查是否存在借款、担保、投资<br>
     * 
     * @param govId 机构id, 必选
     * @param govProvidState 机构是否提供担保，必选
     * @return PlainResult<Boolean> 存在项目依赖机构时，返回true结果；不存在依赖时，返回false结果
     */
    PlainResult<Boolean> existProjectRelateGovment(int govId, GovProvideGuarState govProvidState);

    /**
     * 查询单条普通标信息
     * 
     * @param loanId 查询条件，必选
     * @return PlainResult<Loan>
     */
    PlainResult<Loan> queryById(int loanId);

    /**
     * 查询id包含在ids列表里的普通标信息集合
     * 
     * @param ids 查询条件，必选
     * @return ListResult<Loan> 如果结果不含任何数据会返回错误结果；否则返回成功数据
     */
    ListResult<Loan> queryByIds(List<Integer> ids);

    /**
     * 查询单条普通标信息
     * 
     * @param pojo 查询条件，必选
     * @return PlainResult<Loan>
     */
    PlainResult<Loan> queryByParam(Loan pojo);

    /**
     * 查询单条普通标信息
     * 
     * @param intentApplyId 意向申请id，必选
     * @return PlainResult<Loan> 如果结果为空，返回错误结果
     */
    PlainResult<Loan> queryByIntentApplyId(int intentApplyId);

    /**
     * 通过LoanIntentApply的ID查询多条Loan信息
     * 
     * @param intentIds 意向申请的id列表，必选
     * @return ListResult<Loan> 如果结果为空，返回错误结果
     */
    ListResult<Loan> queryByIntentIds(List<Integer> intentIds);

    /**
     * 查询分页的普通标列表
     * 
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<Loan>
     */
    PageResult<Loan> queryLoanListByParam(Loan pojo, PageCondition pageCondition);

    /**
     * 查询分页的普通标列表
     * 
     * @param searchDO 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<Loan>
     */
    PageResult<Loan> queryLoanListBySearchParam(LoanSearchDO searchDO, PageCondition pageCondition);

    /**
     * 搜索分页的普通标列表
     * 
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<Loan>
     */
    PageResult<Loan> querySearchLoanListByParam(Loan pojo, PageCondition pageCondition, String minSer, String maxSer,
                                                String minCha, String maxCha);

    /**
     * 查询普通标项目跟踪列表
     * 
     * @param loanId 普通标id，必选
     * @param pageCondition 分页条件，必选
     * @return
     */
    PageResult<LoanTraceRecord> queryTraceRecordList(int loanId, PageCondition pageCondition);

    /**
     * 查询普通标列表
     * 
     * @param searchDO 查询条件，可选
     * @return ListResult<Loan>
     */
    ListResult<Loan> queryLoanListBySearchParam(LoanSearchDO searchDO);

    /**
     * 查询优质推荐
     * 
     * @return
     */
    ListResult<Loan> queryOptimization(Integer topN);
    
    /**
     * 优质推荐标找不到时，寻找分页列表中的第一个标
     * xiatt 20160630
     * @return
     */
    ListResult<Loan> queryOptimizationFy(Integer topN);

    /**
     * 查询将要到期借口总数
     * 
     * @return
     */
    PlainResult<Integer> queryCountLoanByExpire(Integer ExpireDay);
    
    /**
     * 项目发布统计表
     * @param pageCondition
     * @return
     */
    PageResult<LoanReportDO> queryLoanReport(LoanReportDO loanReportDO,PageCondition pageCondition);
    /**
     * 项目发布统计表总数
     * @return
     */
    int queryCountLoanReport(LoanReportDO loanReportDO);
    
    
    /**
     * 查询用户上次投资的类型
     * @param userId
     * @return
     */
    int queryLastLoanType(Integer userId);
    
    /**
     * 统计提前还款概况
     * @return
     */
    PostageSummaryDO queryAheadPay();
}
