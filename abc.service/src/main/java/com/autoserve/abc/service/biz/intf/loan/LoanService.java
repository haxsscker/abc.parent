/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myborrow.BorrowStatistics;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowClean;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowReceived;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowTender;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 普通标服务
 * 
 * @author segen189 2014年11月17日 下午6:14:59
 */
public interface LoanService {
    /**
     * 创建普通标
     * 
     * @param pojo 待添加的贷款信息, 必选
     * @return PlainResult<Integer>
     */
    PlainResult<Integer> createLoan(Loan pojo);

    /**
     * 修改普通标信息
     * 
     * @param pojo 待修改的普通标信息，必选
     * @param traceRecord 项目跟踪状态记录，如果不修改标状态则此值可选，如果修改标状态则此值必选
     * @return BaseResult
     */
    BaseResult modifyLoanInfo(Loan pojo, LoanTraceRecord traceRecord);

    /**
     * 融资申请普通标信息资料补全
     * 
     * @param pojo 待修改的普通标信息，必选
     * @param traceRecord 项目跟踪状态记录，如果不修改标状态则此值可选，如果修改标状态则此值必选
     * @return BaseResult
     */
    BaseResult infoSupplementForLoan(Loan pojo, LoanTraceRecord traceRecord);

    /**
     * 添加项目跟踪状态记录，并修改普通标状态,同时更改用户的信用额度，以及担保的信用额度
     * 
     * @param traceRecord 项目跟踪状态记录，必选
     * @return BaseResult
     */
    BaseResult changeLoanState(LoanTraceRecord traceRecord);

    /**
     * 查询id包含在ids列表里的普通标信息集合
     * 
     * @param ids 查询条件，必选
     * @return ListResult<Loan> 如果结果不含任何数据会返回错误结果；否则返回成功数据
     */
    ListResult<Loan> queryByIds(List<Integer> ids, InvestSearchJDO InvestSearchJDO);

    /**
     * 后台发标后发起融资审核
     * 
     * @param loanId 借款id
     * @return BaseResult
     */
    BaseResult initiateFirstReview(int loanId);

    /**
     * 查询平台实际交易总额
     * 
     * @return
     */
    PlainResult<BigDecimal> queryTotal();
    
    /**
     * 查询平台当年实际交易总额
     * 
     * @return
     */
    PlainResult<BigDecimal> findTotalNowYear();
    
    /**
     * 查询上个月成交额
     * 
     * @return
     */
    PlainResult<BigDecimal> findTotalLastMonth();
    
    /**
     * 查询平台待收总额
     * 
     * @return
     */
    PlainResult<Integer> sumLoanToPay();
    
    /**
     * 查询平台交易项目额
     * 
     * @return
     */
    PlainResult<BigDecimal> sumLoanTotal();
    
    /**
     * 查询平台总项目数
     * 
     * @return
     */
    PlainResult<Integer> sumLoanNum();

    /**
     * 我的借款投标中列表
     */
    PageResult<MyBorrowTender> queryMyBorrowTender(InvestSearchJDO searchDO, PageCondition pageCondition);

    /**
     * 我的借款还款中列表
     */
    PageResult<MyBorrowReceived> queryMyBorrowReceived(InvestSearchJDO searchDO, PageCondition pageCondition);

    /**
     * 我的借款已结清列表
     */
    PageResult<MyBorrowClean> queryMyBorrowClean(InvestSearchJDO searchDO, PageCondition pageCondition);

    /**
     * 我的借款统计明细
     */
    PageResult<BorrowStatistics> queryBorrowStatistics(Integer userId, PageCondition pageCondition);

    /**
     * 自定义表创建
     * edit by xiatt 20161018  新增参数empId  业务流程 加上融资维护
     * @param loan
     */
    PlainResult<Integer> createCustomLoan(Loan loan, JSONArray list,Integer empId);

    /**
     * 自定义表资料补全
     */
    BaseResult infoSupplementForCustomLoan(Loan loan, JSONArray list);

    /**
     * 自定义标意向申请资料补全
     * 
     * @param pojo
     * @param list
     * @return
     */
    BaseResult modifyCustomLoan(Loan pojo, JSONArray list);
    
    /**
     * 修改合同PDF的物理路径
     * @param loanId
     * @param contractPath
     * @return
     */
    BaseResult updateContractPath(Integer loanId,String contractPath);
    
    /**
     * 通过主键查询标的信息
     * @param loanId  标id
     * @return
     */
    PlainResult<Loan> findLoanById(Integer loanId);
    
    /**
     * 贷后信息修改
     * @param loanId
     * @param postLoanInfo
     * @return
     */
    BaseResult postLoanInfoEdit(Integer loanId, String postLoanInfo);
}
