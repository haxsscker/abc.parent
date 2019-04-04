/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.LoanReportDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.dao.dataobject.summary.PostageSummaryDO;
import com.autoserve.abc.dao.intf.EmployeeDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TraceRecordDao;
import com.autoserve.abc.service.biz.convert.LoanConverter;
import com.autoserve.abc.service.biz.convert.LoanTraceRecordConverter;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.GovProvideGuarState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoaneeType;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 普通标查询服务
 * 
 * @author segen189 2015年1月16日 上午10:42:46
 */
@Service
public class LoanQueryServiceImpl implements LoanQueryService {
    private static final Logger logger = LoggerFactory.getLogger(LoanQueryServiceImpl.class);

    @Resource
    private LoanDao             loanDao;

    @Resource
    private EmployeeDao         employeeDao;

    @Resource
    private InvestDao           investDao;

    @Resource
    private TraceRecordDao      traceRecordDao;

    @Override
    public PlainResult<Boolean> existProjectRelateGovment(int govId, GovProvideGuarState govProvidState) {
        PlainResult<Boolean> result = new PlainResult<Boolean>();
        boolean isRelated = false;

        if (govId <= 0 || govProvidState == null) {
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        EmployeeDO govEmp = employeeDao.findByGovId(govId);
        if (govEmp == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "机构对应的用户不存在");
        }

        // 1. 小贷机构
        // 如果是小贷机构，检查是否存在借款、投资<br>
        if (GovProvideGuarState.NOT_PROVIDE.equals(govProvidState)) {
            // 1.1 检查借款
            LoanSearchDO loanSearchDO = new LoanSearchDO();
            loanSearchDO.setLoaneeId(govEmp.getEmpId());
            loanSearchDO.setLoaneeType(LoaneeType.GOV.getType());
            loanSearchDO.setLoanState(Arrays.asList(LoanState.WAIT_INTENT_REVIEW.getState(),
                    LoanState.INTENT_REVIEW_PASS.getState(), LoanState.WAIT_PROJECT_REVIEW.getState(),
                    LoanState.PROJECT_REVIEW_PASS.getState(), LoanState.PROJECT_REVIEW_BACK.getState(),
                    LoanState.WAIT_RELEASE.getState(), LoanState.BID_INVITING.getState(),
                    LoanState.FULL_WAIT_REVIEW.getState(), LoanState.FULL_REVIEW_PASS.getState(),
                    LoanState.MONEY_TRANSFERING.getState(), LoanState.REPAYING.getState(),
                    LoanState.WAIT_MAINTAIN_REVIEW.getState(), LoanState.MAINTAIN_REVIEW_PASS.getState()));

            int loanCount = loanDao.countListBySearchParam(loanSearchDO);
            if (loanCount > 0) {
                result.setData(true);
                return result;
            }

            // 1.2 检查投资
            InvestSearchDO investSearchDO = new InvestSearchDO();
            investSearchDO.setInvestUserId(govEmp.getEmpId());
            investSearchDO.setInvestStates(Arrays.asList(InvestState.PAID.getState(), InvestState.EARNING.getState()));

            int investCount = investDao.countListBySearchParam(investSearchDO);
            if (investCount > 0) {
                result.setData(true);
                return result;
            }

        }
        // 2. 担保机构
        // 如果是担保机构，检查是否存在借款、担保、投资<br>      
        /**注意：现在担保机构只支持担保代还，不支持投资借款，所以投资借款校验都屏蔽**/
        else if (GovProvideGuarState.PROVIDE.equals(govProvidState)) {
            // 2.1 检查借款
        	/**LoanSearchDO loanSearchDO = new LoanSearchDO();
            loanSearchDO.setLoaneeId(govEmp.getEmpId());
            loanSearchDO.setLoaneeType(LoaneeType.GOV.getType());
            loanSearchDO.setLoanState(Arrays.asList(LoanState.WAIT_INTENT_REVIEW.getState(),
                    LoanState.INTENT_REVIEW_PASS.getState(), LoanState.WAIT_PROJECT_REVIEW.getState(),
                    LoanState.PROJECT_REVIEW_PASS.getState(), LoanState.PROJECT_REVIEW_BACK.getState(),
                    LoanState.WAIT_RELEASE.getState(), LoanState.BID_INVITING.getState(),
                    LoanState.FULL_WAIT_REVIEW.getState(), LoanState.FULL_REVIEW_PASS.getState(),
                    LoanState.MONEY_TRANSFERING.getState(), LoanState.REPAYING.getState(),
                    LoanState.WAIT_MAINTAIN_REVIEW.getState(), LoanState.MAINTAIN_REVIEW_PASS.getState()));

            int loanCount = loanDao.countListBySearchParam(loanSearchDO);
            if (loanCount > 0) {
                result.setData(true);
                return result;
            }**/

            // 2.2 检查担保
        	LoanSearchDO loanSearchDO = new LoanSearchDO();
            loanSearchDO.setGuarGovId(govId);
            loanSearchDO.setLoanState(Arrays.asList(LoanState.WAIT_INTENT_REVIEW.getState(),
                    LoanState.INTENT_REVIEW_PASS.getState(), LoanState.WAIT_PROJECT_REVIEW.getState(),
                    LoanState.PROJECT_REVIEW_PASS.getState(), LoanState.PROJECT_REVIEW_BACK.getState(),
                    LoanState.WAIT_RELEASE.getState(), LoanState.BID_INVITING.getState(),
                    LoanState.FULL_WAIT_REVIEW.getState(), LoanState.FULL_REVIEW_PASS.getState(),
                    LoanState.MONEY_TRANSFERING.getState(), LoanState.REPAYING.getState(),
                    LoanState.WAIT_MAINTAIN_REVIEW.getState(), LoanState.MAINTAIN_REVIEW_PASS.getState(),LoanState.WAIT_GUAR_REVIEW.getState(),LoanState.GUAR_REVIEW_PASS.getState()));

            int loanCount = loanDao.countListBySearchParam(loanSearchDO);
            if (loanCount > 0) {
                result.setData(true);
                return result;
            }

            // 2.3 检查投资
            /**InvestSearchDO investSearchDO = new InvestSearchDO();
            investSearchDO.setInvestUserId(govEmp.getEmpId());
            investSearchDO.setInvestStates(Arrays.asList(InvestState.PAID.getState(), InvestState.EARNING.getState()));

            int investCount = investDao.countListBySearchParam(investSearchDO);
            if (investCount > 0) {
                result.setData(true);
                return result;
            }**/
        }

        result.setData(isRelated);
        return result;
    }

    @Override
    public PlainResult<Loan> queryById(int id) {
        PlainResult<Loan> result = new PlainResult<Loan>();

        if (id <= 0) {
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        Loan loan = LoanConverter.toLoan(loanDao.findById(id));
        if (loan == null) {
            return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "Loan");
        }

        result.setData(loan);
        return result;
    }

    @Override
    public ListResult<Loan> queryByIds(List<Integer> ids) {
        ListResult<Loan> result = new ListResult<Loan>();

        if (CollectionUtils.isEmpty(ids)) {
            return result.setErrorMessage(CommonResultCode.ILEEGAL_ARGUMENT, "loanIds");
        }

        List<LoanDO> loanDOList = loanDao.findByList(ids);

        if (CollectionUtils.isEmpty(loanDOList)) {
            return result.setErrorMessage(CommonResultCode.ERROR_DATA_NOT_EXISTS, "loanIds");
        }

        result.setData(LoanConverter.toLoanList(loanDOList));
        return result;
    }

    @Override
    public PlainResult<Loan> queryByParam(Loan pojo) {
        PlainResult<Loan> result = new PlainResult<Loan>();
        result.setData(LoanConverter.toLoan(loanDao.findByParam(LoanConverter.toLoanDO(pojo))));
        return result;
    }

    @Override
    public PlainResult<Loan> queryByIntentApplyId(int intentApplyId) {
        PlainResult<Loan> result = new PlainResult<Loan>();
        LoanDO loanDO = loanDao.findByIntentApplyId(intentApplyId);
        if (loanDO == null) {
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS);
            return result;
        }

        result.setData(LoanConverter.toLoan(loanDO));
        return result;
    }

    @Override
    public ListResult<Loan> queryByIntentIds(List<Integer> intentIds) {
        ListResult<Loan> result = new ListResult<Loan>();

        if (CollectionUtils.isEmpty(intentIds)) {
            logger.warn("意向融资ID列表为空");
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        List<LoanDO> loanDOs = loanDao.findByIntentIdList(intentIds);
        if (CollectionUtils.isEmpty(loanDOs)) {
            logger.warn("未找到相关项目信息，intentIds={}", intentIds.toString());
            return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS);
        }

        result.setData(LoanConverter.toLoanList(loanDOs));
        return result;
    }

    @Override
    public PageResult<Loan> queryLoanListByParam(Loan pojo, PageCondition pageCondition) {
        PageResult<Loan> result = new PageResult<Loan>(pageCondition.getPage(), pageCondition.getPageSize());
        int totalCount = loanDao.countListByParam(LoanConverter.toLoanDO(pojo));
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(LoanConverter.toLoanList(loanDao.findListByParam(LoanConverter.toLoanDO(pojo), pageCondition)));
            result.setTotalCount(totalCount);
        }
        return result;
    }

    @Override
    public PageResult<Loan> queryLoanListBySearchParam(LoanSearchDO searchDO, PageCondition pageCondition) {
        PageResult<Loan> result = new PageResult<Loan>(pageCondition);

        int totalCount = loanDao.countListBySearchParam(searchDO);
        if (totalCount > 0) {
            result.setData(LoanConverter.toLoanList(loanDao.findListBySearchParam(searchDO, pageCondition)));
            result.setTotalCount(totalCount);
        }
        return result;
    }

    @Override
    public PageResult<Loan> querySearchLoanListByParam(Loan pojo, PageCondition pageCondition, String minSer,
                                                       String maxSer, String minCha, String maxCha) {
        PageResult<Loan> result = new PageResult<Loan>(pageCondition.getPage(), pageCondition.getPageSize());
        result.setData(LoanConverter.toLoanList(loanDao.searchListByParam(LoanConverter.toLoanDO(pojo), pageCondition,
                minSer, maxSer, minCha, maxCha)));
        result.setTotalCount(loanDao.coutListByParam(LoanConverter.toLoanDO(pojo), minSer, maxSer, minCha, maxCha));
        return result;
    }

    @Override
    public PageResult<LoanTraceRecord> queryTraceRecordList(int loanId, PageCondition pageCondition) {
        PageResult<LoanTraceRecord> result = new PageResult<LoanTraceRecord>(pageCondition);

        int totalCount = traceRecordDao.countList(loanId, BidType.COMMON_LOAN.getType());
        if (totalCount > 0) {
            result.setData(LoanTraceRecordConverter.toTraceRecordList(traceRecordDao.findList(loanId,
                    BidType.COMMON_LOAN.getType(), pageCondition)));
            result.setTotalCount(totalCount);
        }

        return result;
    }

    @Override
    public ListResult<Loan> queryLoanListBySearchParam(LoanSearchDO searchDO) {
        ListResult<Loan> result = new ListResult<Loan>();
        result.setData(LoanConverter.toLoanList(loanDao.findListBySearch(searchDO)));
        return result;
    }

    @Override
    public ListResult<Loan> queryOptimization(Integer topN) {
        ListResult<Loan> result = new ListResult<Loan>();
        List<LoanDO> loanDOs = loanDao.findOptimization(topN);
        //如果找不到优选推荐可投的标，找分页列表中的第一个标
        //if(loanDOs==null || loanDOs.size()==0){
        	//LoanSearchDO searchDO = new LoanSearchDO();
        	//searchDO.setFlag(0);
            //searchDO.setLoanState(Arrays.asList(LoanState.BID_INVITING.state, LoanState.FULL_WAIT_REVIEW.state,
                    //LoanState.FULL_REVIEW_PASS.state, LoanState.FULL_REVIEW_FAIL.state, LoanState.BID_CANCELED.state,
                    //LoanState.MONEY_TRANSFERING.state, LoanState.REPAYING.state, LoanState.REPAY_COMPLETED.state));
            //loanDOs = loanDao.findListBySearchParam(searchDO, new PageCondition(1, topN));
        //}
        result.setData(LoanConverter.toLoanList(loanDOs));
        return result;
    }
    
    //add by xiatt 20160630 如果找不到优选推荐可投的标，找分页列表中的第一个标
    @Override
    public ListResult<Loan> queryOptimizationFy(Integer topN) {
        ListResult<Loan> result = new ListResult<Loan>();
    	LoanSearchDO searchDO = new LoanSearchDO();
    	searchDO.setFlag(0);
    	searchDO.setIsShow(1);
        searchDO.setLoanState(Arrays.asList(LoanState.BID_INVITING.state, LoanState.FULL_WAIT_REVIEW.state,
                LoanState.FULL_REVIEW_PASS.state, LoanState.FULL_REVIEW_FAIL.state, LoanState.BID_CANCELED.state,
                LoanState.MONEY_TRANSFERING.state, LoanState.REPAYING.state, LoanState.REPAY_COMPLETED.state));
        List<LoanDO> loanDOs = loanDao.findListBySearchParam(searchDO, new PageCondition(1, topN));
        result.setData(LoanConverter.toLoanList(loanDOs));
        return result;
    }

    @Override
    public PlainResult<Integer> queryCountLoanByExpire(Integer ExpireDay) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        Integer count = loanDao.countLoanByExpire(ExpireDay);

        result.setData(count);
        return result;
    }
    
    @Override
    public PageResult<LoanReportDO> queryLoanReport(LoanReportDO loanReportDO,PageCondition pageCondition) {
        PageResult<LoanReportDO> pageResult = new PageResult<LoanReportDO>(pageCondition.getPage(), pageCondition.getPageSize());
        int totalCount = loanDao.countLoanReport(loanReportDO);
        pageResult.setTotalCount(totalCount);

        if (totalCount > 0) {
            List<LoanReportDO> list = loanDao.findLoanReport(loanReportDO,pageCondition);
            pageResult.setData(list);
        }

        return pageResult;
    }
    
    @Override
    public int queryCountLoanReport(LoanReportDO loanReportDO) {
        int totalCount = loanDao.countLoanReport(loanReportDO);
        return totalCount;
    }
    
    @Override
    public int queryLastLoanType(Integer userId) {
        return loanDao.queryLastLoanType(userId);
    }
    
    @Override
    public PostageSummaryDO queryAheadPay()
    {
    	return loanDao.queryAheadPay();
    }
}
