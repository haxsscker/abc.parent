package com.autoserve.abc.web.module.screen.moneyManage;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 资金划转-查看
 * 
 * @author zhangkang
 *
 */
public class MoneyTransferLookUp {

	@Autowired
	private FullTransferRecordDao ftrDao;
	@Resource
	private FeeSettingService feeSettingService;
	@Resource
	private LoanQueryService loanQueryService;

	public void execute(ParameterParser params,
			@Param("loanId") int loanId, Context context,
			@Param("ftrId") int ftrId) {
		Loan loan = loanQueryService.queryById(loanId).getData();
		// 1. 计算平台服务费
		PlainResult<BigDecimal> operatingFeeResult = feeSettingService.calcFee(
				loanId, FeeType.PLA_SERVE_FEE);
		BigDecimal operating = operatingFeeResult.getData();
		
		// 2. 计算担保费（如果没有担保机构则不收取担保费）
		BigDecimal guarFee = BigDecimal.ZERO;
		//暂时不考虑担保费，所以这段暂时注掉
		//if (loan.getLoanGuarGov() != null) {
		//	PlainResult<BigDecimal> insureFeeResult = feeSettingService
		//			.calcFee(loanId, FeeType.INSURANCE_FEE);
		//	guarFee = insureFeeResult.getData();
		//}
		BigDecimal shouldTransfer = BigDecimal.ZERO;
		if("2".equals(loan.getHandFeeKind())){
			shouldTransfer = loan.getLoanCurrentValidInvest().subtract(guarFee);
			operating = BigDecimal.ZERO;
		}else if("1".equals(loan.getHandFeeKind())){//服务费方式由原先的一次性收取改为了线下收取 ，所以服务费都置0；
			shouldTransfer = loan.getLoanCurrentValidInvest().subtract(BigDecimal.ZERO).subtract(guarFee);
			operating = BigDecimal.ZERO;
		}
		context.put("guarFee", guarFee);
		context.put("operating", operating);
		context.put("shouldTransfer", shouldTransfer);
		
		FullTransferRecordDO ftr = ftrDao.findById(ftrId);
		context.put("ftr", ftr);

		context.put("loanId", loanId);
		context.put("bidId", loanId);
		context.put("bidType", BidType.COMMON_LOAN.getType());
		
	}
}
