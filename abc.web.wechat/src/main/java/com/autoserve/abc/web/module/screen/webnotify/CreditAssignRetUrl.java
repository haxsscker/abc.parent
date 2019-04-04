package com.autoserve.abc.web.module.screen.webnotify;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.enums.FullTransferType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.message.mail.SendMailService;

public class CreditAssignRetUrl {
	
	@Resource
	private HuifuPayService huifuPayService;
	
	@Resource 
	private HttpServletRequest request;
	
	@Resource
	private CashRecordService cashRecordService;
	
	@Resource
	private InvestOrderService investOrderService;
	
	@Resource
	private DealRecordService dealRecordService;
	
	@Resource
    private IncomePlanService     incomePlanService;
	
	@Resource
    private FullTransferService fullTransferService;
	@Resource
	private FullTransferRecordDao fullTransferRecordDao;
	@Resource
    private SendMailService       sendMailService;
	@Resource
    private TransferLoanDao       transferLoanDao;
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public void execute(ParameterParser params,Context context,Navigator nav){
		
		
		BigDecimal mount = null;
		BaseResult result = new BaseResult();
		HuiFuData data = huifuPayService.CreditAssignResult(request);
		String respCode = data.getRespCode();
		String respDesc = data.getRespDesc();
		String creditAmt = data.getCreditAmt(); //转让金额
		System.out.println(creditAmt+"11111111");
		if(creditAmt == null || "".equals(creditAmt)){
			creditAmt = "";
		}
		String creditDealAmt = data.getCreditDealAmt(); //承接金额
		System.out.println(creditDealAmt+"2222222");
		if(creditDealAmt == null || "".equals(creditDealAmt)){
			creditDealAmt = "";
		}
		String merPriv = request.getParameter("MerPriv");
		if(merPriv == null || merPriv == ""){
			merPriv = "";
		}else{
			merPriv = merPriv.replace("%2C", ",").trim().trim();
		}
		String orderNo = merPriv.split(",")[0].toString();
		Integer tlId	= Integer.valueOf(merPriv.split(",")[1].toString());
		String ordId = data.getOrdId();
		String ordDate = data.getOrdDate();
		
		String responseList = JSON.toJSONString(data);
		
		
		//保存回调
		CashRecordDO cashRecordDo = new CashRecordDO();
		cashRecordDo.setCrSeqNo(orderNo);
		cashRecordDo.setCrResponse(responseList);
		cashRecordDo.setCrResponseState(200);
		
		//更新cashRecord
		cashRecordService.modifyCashRecordState(cashRecordDo);
		
		String money = creditDealAmt;
		if("".equals(money) || money == null){
			money = "";
			mount = BigDecimal.ZERO;
		}else{
			mount = BigDecimal.valueOf(Double.valueOf(money));
		}
		String ordIdDate = ordId + "," + ordDate;  //拼接字符串
		
		InvestOrderDO order = new InvestOrderDO();
		order.setIoInnerSeqNo(orderNo);
		order.setIoOutSeqNo(ordIdDate);
		
		//更新项目投资订单表
		BaseResult baseResult = investOrderService.updateBySeqNo(order);
		if(!baseResult.isSuccess()){
			return ;
		}
//		mount = BigDecimal.valueOf(Double.valueOf(money));

		String status = "";
		if("000".equals(respCode)){
			status = "TRADE_FINISHED";
		}else{
			status = "TRADE_FAILURE";
		}
		
		BaseResult modifyResult = dealRecordService.modifyDealRecordState(orderNo, status, mount);
		if(!modifyResult.isSuccess()){
			result.setSuccess(false);
		}else {
	        result.setSuccess(true);
	    }
		
		if(!data.getRespCode().equals("000")){
			context.put("resultCode", respCode);
			context.put("resultDesc", respDesc);
			nav.forwardTo("/error");
		}else{
			/*
			 * 此处为了防止在还款时候对债权的冲突所以
			 * 根据汇付的债权转让接口直接对金额进行资金的划转 无需后台
			 * 1，将后台各种对数据库数据的状态修改放在此处
			 * 2，无需后台进行操作，因为债权转让一个人投资且直接满标，要想用后台的话需要商户花钱买通汇付债权接口
			 * 3，在还款的时候做判断 只要有还款且还款成功的 所有债权全部完蛋，不需要进行流标
			 * */
			
			BaseResult result1 = this.fullTransferService.transferBidMoneyTransfer(tlId, 0.00,
	                FullTransferType.TRANSF_LOAN_FULL_TRANSFER, 0);
			if(result1.isSuccess()){
				System.out.println("success");
			}else{
				System.out.println("failed");
			}
			nav.redirectToLocation("/account/debtManage/myDebt?Type=HKZ");
		}
	}
	
}
