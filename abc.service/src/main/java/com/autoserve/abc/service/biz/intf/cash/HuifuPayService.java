package com.autoserve.abc.service.biz.intf.cash;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.result.PlainResult;

public interface HuifuPayService {
	
	/**
     * 开户接口
     *
     * 
     */
    public PlainResult<Map> openAccount(Map params);
    
    /**
     * 开户接口返回结果
     *
     * 
     */
    public HuiFuData openAccountResult(ParameterParser params);
    
	/**
	 * 绑卡
	 */
	public String bankCard(HuiFuData  data) ;
	/**
	 * 绑卡前后台返回结果
	 */
	public HuiFuData  bankCardResult(HttpServletRequest request) ;
	
	
	/**
	 * 删除绑卡
	 */
	public HuiFuData delBankCard(String url ,Map<String,String>  map) ;
    
	/**
	 * 还款
	 */
	//public Map<String,String> repayment(HuiFuData data);
	public PlainResult<Map<String,String>> repayment(String loanJsonList);
	
	/**
	 * 还款后台返回结果
	 */
	public Map<String,String> repaymentBgRetUrl(HttpServletRequest request);
	
	/**
	 * 充值接口
	 */
	public PlainResult<Map> recharge(HuiFuData data);
	
	/**
	 * 充值接口返回结果 
	 */
	public HuiFuData rechargeResult(HttpServletRequest request);
	
	
	/**
	 * 投资接口 
	 */
//	public PlainResult<Map> invest(HuiFuData data,List<OrdDetail> list);
	public PlainResult<Map<String,String>> invest(String loanJsonList);
	/**
	 * 
	 * 投资前后台返回结果
	 */
	public HuiFuData investResult(HttpServletRequest request);
	
	/**
	 * 资金冻结接口
	 */
	public PlainResult<Map> freeze(HuiFuData data);
	
	/**
	 * 资金解冻接口
	 */
	public Map<String,String> unFreeze(HuiFuData data);
	/**
	 * 资金解冻返回接口
	 */
	public HuiFuData unfreezehResult(HttpServletRequest req);
	
	/**
	 * 后台余额查询
	 */
	public Map<String,String> queryBalanceBg(String url,Map<String,String>map);
	
	/**
	 * 提现
	 */
	public  PlainResult<Map> cash(HuiFuData data);
	
	/**
	 * 提现前后台返回结果
	 */
	public HuiFuData cashResult(HttpServletRequest request);
	
	/**
	 * 提现复审接口
	 */
	public Map<String, String> cashAudit(HuiFuData data);
	
	/**
	 * 提现复审返回结果
	 */
	public HuiFuData  cashAuditResult(HttpServletRequest request) ;
	
	/**
	 * 放款
	 */
	public PlainResult<Map<String, String>>  crdit(String loanJsonList);
	/**
	 * 放款后台返回结果
	 */
	public Map<String,String> crditBgRetUrl(HttpServletRequest request)   ;
	/**
	 * 债权转让接口
	 *  
	 */
	 public PlainResult<Map> CreditAssign(HuiFuData data);
	 
	 /**
	  * 债权转让接口
	  */
	 public HuiFuData CreditAssign(String jsonList);
	 /**
	  * 债权转让返回结果
	  */
	 public HuiFuData CreditAssignResult(HttpServletRequest request);
	 
	 /**
	  * 撤销投资,撤销的时候同时对资金解冻
	  */ 
	 public Map<String,String> withdrawInvest(HuiFuData data);
	 /**
	  * 撤销前后台返回结果
	  */ 
	 public HuiFuData withdrawInvestResult(HttpServletRequest req);
	 
	 /**
	  * 标的信息录入接口
	  */
	 public HuiFuData investMessageLog(HuiFuData data);
	 
	 /**
	  * 自动扣款转账(商户用)
	  */
	 public Map<String, String> transferHuiFu(HuiFuData  data);
}
