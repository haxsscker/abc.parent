package com.autoserve.abc.service.job;

import java.util.Arrays;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.manage.TransferLoanManageService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;

/**
 * 债权标的自动流标
 * @author DS
 *
 * 2015年4月29日下午1:18:35
 */
public class AutoCancelTransferLoanJob implements BaseJob {
	@Resource
	private TransferLoanService transferLoanService;
	@Resource
	private PaymentPlanDao paymentPlanDao;
	@Resource
	private TransferLoanManageService transferLoanManageService;
	@Resource
	private LoanService loanService;
	
    private static final Logger logger = LoggerFactory.getLogger(AutoCancelTransferLoanJob.class);
    @Override
    public void run() {
        //查询债权标(待审核、转让招标中、满标待审、满标审核通过、满标审核未通过)
    	TransferLoanSearchDO pojo=new TransferLoanSearchDO();
    	pojo.setTransferLoanStates(Arrays.asList(TransferLoanState.WAIT_REVIEW.state, TransferLoanState.TRANSFERING.state,TransferLoanState.FULL_WAIT_REVIEW.state,
    			TransferLoanState.FULL_REVIEW_PASS.state,TransferLoanState.FULL_REVIEW_FAIL.state));
    	ListResult<TransferLoanJDO> result=transferLoanService.queryListByParam(pojo);
    	if(result!=null && result.getData()!=null){
    		for(int i=0;i<result.getData().size();i++){
    			TransferLoanJDO transferLoanJDO=result.getData().get(i);
    			 // 1.1 加行级锁住还款计划
    	        PaymentPlanDO paymentLock = paymentPlanDao.findWithLock(transferLoanJDO.getTlNextPaymentId());
    	        if (paymentLock == null) {
    	        	logger.error("查询下期还款计划失败");
    	           continue;
    	        }

    	        // 1.2 满标前有还款行为，则直接流标
    	        // 1.3 如果债权转让的期限小于0，则直接流标
    	        if (paymentLock.getPpPayState().equals(PayState.CLEAR.getState()) || transferLoanJDO.getTimelimit()<0) {
    	            BaseResult cancelTransResult = transferLoanManageService.cancelTransferLoan(transferLoanJDO.getTlId(), 0,
    	                    "转让标发起后到满标资金划转期间借款人有还款行为");
    	            if (!cancelTransResult.isSuccess()) {
    	            	logger.error("自动流标失败");
    	            	continue;
    	            }
    	        }
    		}
    	}
    	
    }  
}
