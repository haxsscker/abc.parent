package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.ToCashState;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonBaseVO;


/**
 * 查询交易状态
 * @author LZ
 *
 * 
 */
public class QueryOrder {
	@Resource
	private HttpSession session;
	@Resource
	private UserService userService;
	@Resource
	private CompanyCustomerService companyCustomerService;
	@Autowired
    private AccountInfoService  accountInfoService;
	@Resource
	private DealRecordService dealRecord;
	@Resource
	private CashRecordService cashrecordservice;
	@Resource
	private ToCashService tocashservice;
	@Resource
	private RechargeService rechargeservice;
	@Resource
	private DealRecordDao dealRecordDao;
	public JsonBaseVO execute(Context context, ParameterParser params){
		 JsonBaseVO result = new JsonBaseVO();
		 User user=(User)session.getAttribute("user");
		 String id = params.getString("id");
		 String type = params.getString("type");
		 if(id==null){
			 id="";
		 }
		if(user!=null){
			Map<String,String> resultMap = accountInfoService.queryTransStatus(id, type);
			String RespCode = resultMap.get("RespCode");
			String TransStat = resultMap.get("TransStat");
			//更新交易、资金操作记录
			if(null!=TransStat&&TransStat.equals("S1")){
				dealRecord.modifyDealRecordStateWithDouble(resultMap);
				PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo((String)resultMap.get("MerBillNo"));
		        CashRecordDO cashrecord = cashrecorddo.getData(); 
		        cashrecord.setCrResponseState(Integer.valueOf(RespCode));
		        cashrecordservice.modifyCashRecordState(cashrecord);
			}else if(null==TransStat||TransStat.equals("F1")){//交易失败
				RespCode = "111111";//自定义失败状态码为111111
				resultMap.put("RespCode", RespCode);
				dealRecord.modifyDealRecordStateWithDouble(resultMap);
				PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo((String)resultMap.get("MerBillNo"));
		        CashRecordDO cashrecord = cashrecorddo.getData(); 
		        cashrecord.setCrResponseState(Integer.valueOf(RespCode));
		        cashrecordservice.modifyCashRecordState(cashrecord);
			}
			
	        if("CZJL".equals(type)){
	        	RechargeRecordDO rechargeDo = new RechargeRecordDO();
		        rechargeDo.setRechargeSeqNo(resultMap.get("MerBillNo"));
		        if(null!=TransStat&&TransStat.equals("S1")){
		        	rechargeDo.setRechargeState(1);}
		        else if(null==TransStat||TransStat.equals("F1")){
		        	rechargeDo.setRechargeState(2);
		        }
		        BaseResult rechargeresult = rechargeservice.updateBackStatus(rechargeDo);
		        System.out.println("修改充值记录："+rechargeresult.isSuccess()+rechargeresult.getMessage());
	        }else if("TXJL".equals(type)){
	        	//更新提现记录
		        PlainResult<TocashRecordDO> resultRecord=tocashservice.queryBySeqNo(resultMap.get("MerBillNo"));
		        TocashRecordDO toCashDo = new TocashRecordDO();
		        toCashDo.setTocashSeqNo(resultMap.get("MerBillNo"));
		        DealRecordDO dealRecordDo = new DealRecordDO();
		        dealRecordDo.setDrInnerSeqNo(resultMap.get("MerBillNo"));
		        if(null!=TransStat&&"S1".equals(TransStat)){
		        	toCashDo.setTocashState(ToCashState.SUCCESS.getState());
					toCashDo.setTocashValidquota(resultRecord.getData().getTocashQuota());
					dealRecordDo.setDrState(DealState.SUCCESS.getState());
					//更新免费提现额度
					if(resultRecord.getData()!=null && resultRecord.getData().getTocashQuota()!=null){
						userService.reduceCashQuota(resultRecord.getData().getTocashUserId(), resultRecord.getData().getTocashQuota());
					}			
		        }else if(null==TransStat||"F1".equals(TransStat)){
		        	toCashDo.setTocashState(ToCashState.FAILURE.getState());
					toCashDo.setTocashValidquota(new BigDecimal(0));
					dealRecordDo.setDrState(DealState.FAILURE.getState());
		        }
		        BaseResult tocashresult = tocashservice.updateBySeqNo(toCashDo);	
		        dealRecordDao.updateDealRecordState(dealRecordDo);
		        System.out.println("修改提现记录："+tocashresult.isSuccess()+tocashresult.getMessage());
	        }
			if(null!=TransStat&&"S1".equals(TransStat)){
				result.setSuccess(true);
				result.setMessage("交易成功");
			}else if(null==TransStat||"F1".equals(TransStat)){
				result.setSuccess(false);
				result.setMessage("交易失败");
			}else if("W2".equals(TransStat)){
				result.setSuccess(false);
				result.setMessage("请求处理中");
			}else if("W3".equals(TransStat)){
				result.setSuccess(false);
				result.setMessage("系统受理中");
			}else if("W4".equals(TransStat)){
				result.setSuccess(false);
				result.setMessage("银行受理中");
			}
		}else{
			result.setSuccess(false);
			result.setMessage("您还没有登录,请先登录");
			result.setRedirectUrl("/login/login");
		}
		 return result;
		 
	 }
}
