package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.intf.CashInvesterViewDao;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashBorrowerService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.user.UserCompanyService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;


public class GuaranteeAgenciesFundsDetails {
	@Resource
	private CashBorrowerService cashBorrower;
	@Resource
	private UserCompanyService userCompanyService;
	@Resource
	private	PaymentPlanService paymentPlanService;
	@Resource
	private InvestService investService;
	@Resource
	private CashInvesterViewDao cashInvesterViewDao;
	@Resource
	private DealRecordService dealrecordservice;
	@Resource
	private AccountInfoService accountInfoService;
	
	public JsonPageVO<DealRecordDO> execute(Context context,@Param("page") int page, @Param("rows") int rows,@Param("accountNo") String accountNo, @Param("userId") Integer userId, ParameterParser params){
		
		AccountInfoDO  accountInfo = accountInfoService.queryByAccountNo(accountNo);
		accountNo = accountInfo.getAccountNo();
		DealRecordDO dealrecorddo = new DealRecordDO();
	    if(null==accountInfo.getAccountNo() || accountInfo.getAccountNo().length()<=0){
	    	dealrecorddo.setDrPayAccount("");
	    }else{
	    	dealrecorddo.setDrPayAccount(accountInfo.getAccountNo());
	    }
		PageResult<DealRecordDO> result = dealrecordservice.queryDealByParams(dealrecorddo,	new PageCondition(page,rows), null, null);
		if(result.getData().size() > 0){
			for(DealRecordDO dealrecord:result.getData()){
			
				//交易日期
				dealrecord.setDrOperateDateStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dealrecord.getDrOperateDate()));
				//交易金额
				switch(dealrecord.getDrType()){
				case 0://投资冻结
					dealrecord.setDrMoneyAmountStr("-"+dealrecord.getDrMoneyAmount().toString());
					break;
				case 2:
				case 3:
					if(dealrecord.getDrPayAccount().equals(accountNo)){
						dealrecord.setDrMoneyAmountStr("-"+dealrecord.getDrMoneyAmount().toString());
					}else if(dealrecord.getDrReceiveAccount().equals(accountNo)){
						dealrecord.setDrMoneyAmountStr(dealrecord.getDrMoneyAmount().toString());
					}
					break;
				case 6:
					dealrecord.setDrMoneyAmountStr(dealrecord.getDrMoneyAmount().toString());
					break;
				case 7:
					dealrecord.setDrMoneyAmountStr("-"+dealrecord.getDrMoneyAmount().toString());
					break;
				case 8:
					dealrecord.setDrMoneyAmountStr(dealrecord.getDrMoneyAmount().toString());
					break;
				case 10://红包返还
					dealrecord.setDrMoneyAmountStr(dealrecord.getDrMoneyAmount().toString());
					break;
				default:dealrecord.setDrMoneyAmountStr("-");
				}
				//交易对方
				if((dealrecord.getDrType()==2 && accountNo.equals(dealrecord.getDrReceiveAccount())) ||
						(dealrecord.getDrType()==3 && accountNo.equals(dealrecord.getDrReceiveAccount()))
						||(dealrecord.getDrType()==10 && accountNo.equals(dealrecord.getDrReceiveAccount()))){
					dealrecord.setDrCustomerAccount(dealrecord.getDrPayAccount());
				}else if(dealrecord.getDrType()==9){
					if(accountNo.equals(dealrecord.getDrPayAccount()))
						dealrecord.setDrCustomerAccount(dealrecord.getDrReceiveAccount());
                 	else
						dealrecord.setDrCustomerAccount(dealrecord.getDrPayAccount());
				}else{
					dealrecord.setDrCustomerAccount(dealrecord.getDrReceiveAccount());
				}	
				//类型
				dealrecord.createDrTypeStr(accountNo);
				//状态
				if(dealrecord.getDrState()==0){
					dealrecord.setDrStateStr("进行中");
				}else if(dealrecord.getDrState()==1){
					dealrecord.setDrStateStr("成功");
				}else {
					dealrecord.setDrStateStr("失败");
				}
				
			}
		}
		return ResultMapper.toPageVO(result);
		
	}
}