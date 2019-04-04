package com.autoserve.abc.service.biz.impl.cashsq;

import java.text.SimpleDateFormat;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashSqDO;
import com.autoserve.abc.dao.intf.CashSqDao;
import com.autoserve.abc.dao.intf.LoginLogDao;
import com.autoserve.abc.dao.intf.RealnameAuthDao;
import com.autoserve.abc.service.biz.intf.cashsq.CashSqService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.score.ScoreHistoryService;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * @author RJQ 2014/11/20 17:47.
 */
@Service
public class CashSqServiceImpl implements CashSqService {
 
    @Resource
    private CashSqDao             cashsqDao;
    @Resource
    private LoginLogDao         loginLogDao;

    @Resource
    private ScoreService        scoreService;

    @Resource
    private ScoreHistoryService scoreHistoryService;

    @Resource
    private RedService          redService;

    @Resource
    private RealnameAuthDao     realnameAuthDao;

   

	@Override
	public BaseResult insetcashsq(CashSqDO cashsqDO) {
	       BaseResult baseResult = new BaseResult();
		 int val = cashsqDao.insert(cashsqDO);
	        if (val <= 0) {
	            baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "新增用户失败");
	            return baseResult;
	        } 
	        return baseResult;
	}

 
	@Override
	public PageResult<CashSqDO> queryListCashSq(CashSqDO cashSqDO,
			PageCondition pageCondition) {
		  PageResult<CashSqDO> result = new PageResult<CashSqDO>(pageCondition.getPage(),
	                pageCondition.getPageSize());
	        int totalCount = cashsqDao.countRecommendListByParam(cashSqDO);
	        result.setTotalCount(totalCount);
	    	System.out.println("totalCount"+totalCount);
	        if (totalCount > 0) {
	            result.setData(cashsqDao.findRecommendListByParam(cashSqDO, pageCondition));
	        }
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
		 
	        for (CashSqDO CashSqDO2 : result.getData()) {
	        	System.out.println(CashSqDO2.getId());
				System.out.println(CashSqDO2.getUserId());
				CashSqDO2.setSqtimestr(sdf.format(CashSqDO2.getSqtime()));
				System.out.println(CashSqDO2.getSqtimestr());
				System.out.println(sdf.format(CashSqDO2.getSqtime()));
				System.out.println(CashSqDO2.getUserCashQuotaSqadd());
		 
			}
	        
	        return result;
	}


	@Override
	public BaseResult modifyCreditApply(CashSqDO cashSqDO) {
	     BaseResult result = new BaseResult();
	        int val = cashsqDao.updateByPrimaryKeySelective(cashSqDO);
	        if (val <= 0) {
	            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改信用额度记录失败！");
	        }
	        return result;
	}
}
