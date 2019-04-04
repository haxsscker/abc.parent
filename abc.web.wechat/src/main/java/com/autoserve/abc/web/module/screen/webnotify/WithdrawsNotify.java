package com.autoserve.abc.web.module.screen.webnotify;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.ToCashState;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class WithdrawsNotify {
	private final static Logger logger = LoggerFactory.getLogger(WithdrawsNotify.class);
    @Resource
    private AccountInfoService   accountInfoService;
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;
    @Resource
    private InvestService        investService;
	@Resource
	private DealRecordService dealRecord;
	@Resource
	private CashRecordService cashrecordservice;
	@Resource
	private BankInfoService	bankinfoservice;
	@Resource
	private ToCashService tocashservice;
	@Resource
	private UserService userService;
		
	   public void execute(Context context, Navigator nav, ParameterParser params) {
		    Map paramterMap = resq.getParameterMap();
            Map notifyMap = EasyPayUtils.transformRequestMap(paramterMap);
            //交易状态
            String ResultCode =  (String)notifyMap.get("ResultCode");    
            //内部交易流水号
            String OrderNo = (String)notifyMap.get("OrderNo");    
            //外部交易流水号
            String LoanNo = (String)notifyMap.get("LoanNo");
            
            //更新交易流水
	        BaseResult result = dealRecord.modifyDealRecordStateWithDouble(notifyMap);
	        System.out.println("支付回调接口："+result.isSuccess()+result.getMessage());
	        
	        //更新资金操作记录表
	        PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo((String)notifyMap.get("OrderNo"));
	        CashRecordDO cashrecord = cashrecorddo.getData(); 
	        cashrecord.setCrResponse(notifyMap.toString());
	        cashrecord.setCrResponseState(Integer.valueOf((String)notifyMap.get("ResultCode")));
	        BaseResult cashresult = cashrecordservice.modifyCashRecordState(cashrecord);
	        System.out.println("修改资金交易记录："+cashresult.isSuccess()+cashresult.getMessage());
	        
	        //更新银行卡信息
	        //获取银行卡信息ID
	        String Remark1 = (String)notifyMap.get("Remark1");	        
	        if(Remark1!=null && !"".equals(Remark1)){	        	
	        	PlainResult<BankInfoDO> bankResult = bankinfoservice.queryListBankInfoById(Integer.valueOf(Remark1));
	        	if(bankResult.isSuccess() && bankResult.getData().getCardStatus()==0){
	        		BankInfo bankinfo = new BankInfo();
	        		bankinfo.setBankId(Integer.valueOf(Remark1));
	        		if(ResultCode.equals("88")){
	        			bankinfo.setCardStatus(CardStatus.STATE_ENABLE);
	        			BaseResult cardresult = bankinfoservice.modifyBankInfo(bankinfo);
		        		System.out.println("修改银行卡："+cardresult.isSuccess()+cardresult.getMessage());
	        		}	
	        	}
	        }
	        
	        //更新提现记录
	        PlainResult<TocashRecordDO> resultRecord=tocashservice.queryBySeqNo(OrderNo);
	        TocashRecordDO toCashDo = new TocashRecordDO();
	        toCashDo.setTocashSeqNo(OrderNo);
	        toCashDo.setTocashOutSeqNo(LoanNo);
			if (ResultCode.equals("88")) {
				toCashDo.setTocashState(ToCashState.SUCCESS.getState());
				toCashDo.setTocashValidquota(resultRecord.getData().getTocashQuota());
				//更新免费提现额度
				userService.reduceCashQuota(resultRecord.getData().getTocashUserId(), resultRecord.getData().getTocashQuota());
			} else {
				toCashDo.setTocashState(ToCashState.FAILURE.getState());
				toCashDo.setTocashValidquota(new BigDecimal(0));
			}
	        BaseResult tocashresult = tocashservice.updateBySeqNo(toCashDo);	      
	        System.out.println("修改提现记录："+tocashresult.isSuccess()+tocashresult.getMessage());
	        
	        try {
	            if (result.isSuccess()&&cashresult.isSuccess()) {
	                resp.getWriter().print("SUCCESS");
	            } else {
	                resp.getWriter().print("fail");
	            }
	        } catch (Exception e) {
	            logger.error("[withdraw] error: ", e);
	        }
	    }
}
