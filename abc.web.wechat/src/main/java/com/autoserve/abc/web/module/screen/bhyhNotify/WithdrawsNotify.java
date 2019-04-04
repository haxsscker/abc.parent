package com.autoserve.abc.web.module.screen.bhyhNotify;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
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
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.ToCashState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
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
	@Resource
    private SysConfigService  sysConfigService;
	@Resource
	private DealRecordDao dealRecordDao;
	   public void execute(Context context, Navigator nav, ParameterParser params) {
		   logger.info("===================提现异步通知===================");
			Map notifyMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
			logger.info(notifyMap.toString());
			String partner_id = FormatHelper.GBK2Chinese(params.getString("PartnerId"));// 
			String MerBillNo = FormatHelper.GBK2Chinese(params.getString("MerBillNo"));//
			String TransAmt = FormatHelper.GBK2Chinese(params.getString("TransAmt"));
			String TransId = FormatHelper.GBK2Chinese(params.getString("TransId"));
			String MerFeeAmt = FormatHelper.GBK2Chinese(params.getString("MerFeeAmt"));
			String FeeAmt = FormatHelper.GBK2Chinese(params.getString("FeeAmt"));
			String MerPriv = FormatHelper.GBK2Chinese(params.getString("MerPriv"));
			String version_no = FormatHelper.GBK2Chinese(params.getString("Version_No"));
			String biz_type = FormatHelper.GBK2Chinese(params.getString("Biz_Type"));// 
			String RespCode = FormatHelper.GBK2Chinese(params.getString("RpCode"));// 
			String RespDesc = FormatHelper.GBK2Chinese(params.getString("RpDesc"));//
			String sign_type = FormatHelper.GBK2Chinese(params.getString("Sign_Type"));//
			String mac = params.getString("Mac");
			BaseResult result = new BaseResult();
			//验签
			Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
			paramsMap.put("partner_id", partner_id);
			paramsMap.put("version_no", version_no);
			paramsMap.put("biz_type", biz_type);
			paramsMap.put("sign_type", sign_type);
			paramsMap.put("MerBillNo", MerBillNo);
			paramsMap.put("RespCode", RespCode);
			paramsMap.put("RespDesc", RespDesc);
			paramsMap.put("TransAmt", TransAmt);
			paramsMap.put("TransId", TransId);
			paramsMap.put("MerFeeAmt", MerFeeAmt);
			paramsMap.put("FeeAmt", FeeAmt);
			paramsMap.put("MerPriv", MerPriv);

//            //交易状态
//            String ResultCode =  (String)notifyMap.get("ResultCode");    
//            //内部交易流水号
//            String OrderNo = (String)notifyMap.get("OrderNo");    
//            //外部交易流水号
//            String LoanNo = (String)notifyMap.get("LoanNo");
//            //平台承担的手续费比例
//            String FeePercent=(String)notifyMap.get("FeePercent");
//            //用户实际承担的手续费金额
//            String FeeWithdraws=(String)notifyMap.get("FeeWithdraws");
//            //平台承担的手续费金额
//            String Fee=(String)notifyMap.get("Fee");
//            //平台扣除的免费提现额
//            String FreeLimit=(String)notifyMap.get("FreeLimit");
            
			User user = userService.findEntityById(Integer.valueOf(MerPriv)).getData();
			UserIdentity userIdentity =new UserIdentity();
	    	userIdentity.setUserId(user.getUserId());
	    	if(user.getUserType()==null || user.getUserType().getType()==1){
	    		user.setUserType(UserType.PERSONAL);
	    	}else{
	    		user.setUserType(UserType.ENTERPRISE);
	    	}
	    	userIdentity.setUserType(user.getUserType());	
	    	PlainResult<Account> res = accountInfoService.queryByUserId(userIdentity);
	    	Account acc= res.getData();
	        try {
	        	//更新交易流水
	        	if(!RespCode.equals("000000")){//只有当调用接口失败的时候更新记录，成功后也只是待响应状态，真正的成功与否状态需要定时任务或者提现记录流水列表中的查询按钮处理
	        		result = dealRecord.modifyDealRecordStateWithDouble(notifyMap);
			        System.out.println("支付回调接口："+result.isSuccess()+result.getMessage());
			        
			        //更新资金操作记录表
			        PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo((String)notifyMap.get("MerBillNo"));
			        CashRecordDO cashrecord = cashrecorddo.getData(); 
			        cashrecord.setCrResponse(notifyMap.toString());
			        cashrecord.setCrResponseState(Integer.valueOf((String)notifyMap.get("RespCode")));
			        BaseResult cashresult = cashrecordservice.modifyCashRecordState(cashrecord);
			        System.out.println("修改资金交易记录："+cashresult.isSuccess()+cashresult.getMessage());
	        	}
		        
		        
		        //更新银行卡信息
		        //获取银行卡信息ID
//		        String Remark1 = (String)notifyMap.get("Remark1");	        
//		        if(Remark1!=null && !"".equals(Remark1)){	        	
//		        	PlainResult<BankInfoDO> bankResult = bankinfoservice.queryListBankInfoById(Integer.valueOf(Remark1));
//		        	if(bankResult.isSuccess() && bankResult.getData().getCardStatus()==0){
//		        		BankInfo bankinfo = new BankInfo();
//		        		bankinfo.setBankId(Integer.valueOf(Remark1));
//		        		if(ResultCode.equals("88")){
//		        			bankinfo.setCardStatus(CardStatus.STATE_ENABLE);
//		        			BaseResult cardresult = bankinfoservice.modifyBankInfo(bankinfo);
//			        		System.out.println("修改银行卡："+cardresult.isSuccess()+cardresult.getMessage());
//		        		}	
//		        	}
//		        }
		        
		        //更新提现记录
		        PlainResult<TocashRecordDO> resultRecord=tocashservice.queryBySeqNo(MerBillNo);
		        TocashRecordDO toCashDo = new TocashRecordDO();
		        toCashDo.setTocashSeqNo(MerBillNo);
		        toCashDo.setTocashOutSeqNo(TransId);
				if (RespCode.equals("000000")) {
					toCashDo.setTocashState(ToCashState.PROCESSING.getState());
					toCashDo.setTocashValidquota(resultRecord.getData().getTocashQuota());
					//toCashDo.setTocashFeePercent(new BigDecimal(FeePercent));
					//toCashDo.setTocashFeeWithdraws(new BigDecimal(FeeWithdraws));
					//toCashDo.setTocashFee(new BigDecimal(Fee));
					//toCashDo.setTocashFeeLimit(new BigDecimal(FreeLimit));
					//更新免费提现额度
					if(resultRecord.getData()!=null && resultRecord.getData().getTocashQuota()!=null){
						userService.reduceCashQuota(resultRecord.getData().getTocashUserId(), resultRecord.getData().getTocashQuota());
					}			
					//更新免费提现次数
//					String monthtimes=(String)notifyMap.get("Remark2");
//					if(monthtimes!=null && !"".equals(monthtimes)){
//						UserDO userDO=new UserDO();
//						userDO.setUserId(resultRecord.getData().getTocashUserId());
//						userDO.setUserTocashMonthtimes(monthtimes);
//						userService.modifyInfo(userDO);
//					}
					if(!"0".equals(MerPriv)){
						String receiveNo = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT).getData().getConfValue();
						DealRecordDO dealRecordDo = new DealRecordDO();
				        dealRecordDo.setDrDetailType(DealDetailType.PLA_FEE.getType());
				        dealRecordDo.setDrInnerSeqNo(MerBillNo);
				        dealRecordDo.setDrMoneyAmount(new BigDecimal(FormatHelper.changeF2Y(MerFeeAmt)));
				        dealRecordDo.setDrReceiveAccount(receiveNo);
				        dealRecordDo.setDrPayAccount(acc.getAccountNo());
				        dealRecordDo.setDrType(DealType.TRANSFER.getType());
				        dealRecordDo.setDrState(DealState.NOCALLBACK.getState());
				        dealRecordDao.insertRecord(dealRecordDo);
			        }
				} else {
					toCashDo.setTocashState(ToCashState.FAILURE.getState());
					toCashDo.setTocashValidquota(new BigDecimal(0));
				}
		        BaseResult tocashresult = tocashservice.updateBySeqNo(toCashDo);	      
		        System.out.println("修改提现记录："+tocashresult.isSuccess()+tocashresult.getMessage());
	        	
	            if (tocashresult.isSuccess()) {
	                resp.getWriter().print("SUCCESS");
	            } else {
	                resp.getWriter().print("fail");
	            }
	        } catch (Exception e) {
	            logger.error("[withdraw] error: ", e);
	        }
	    }
}
