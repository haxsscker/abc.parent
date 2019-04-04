package com.autoserve.abc.web.module.screen.webnotify.hfnotify;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.util.SystemGetPropeties;

/**
 * @author andy
 * 
 * @since 2015/3/11
 */

public class InvestBgRetUrl {
	
@Resource
HuifuPayService huifuPayService;
@Resource
CashRecordService cashRecordService;
@Resource
private InvestOrderService  investOrderService;
@Resource
DealRecordService dealRecordService;
@Resource
HttpServletRequest request;
@Resource
private InvestDao investDao;
@Resource
private LoanDao loanDao;


/*汇付接口回调业务逻辑处理，成功 success 为true 否则为false*/

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
public synchronized BaseResult execute(Context context,ParameterParser params,Navigator nav){
	BigDecimal mount = null;
	BaseResult result = new BaseResult();
	HuiFuData data = new HuiFuData();
	data = huifuPayService.investResult(request);
	
	//汇付接口返回回来的数据
	 String  cmdId=data.getCmdId();      //消息类型
	 String respCode=data.getRespCode();  //应答返回码
	 String respDesc=data.getRespDesc();   //应答描述
	 String merCustId=data.getMerCustId();  //商户客户号
	 String ordId = data.getOrdId();      //订单号
	 String ordDate = data.getOrdDate();   //订单日期
	 String transAmt = data.getTransAmt(); //交易金额
	 String usrCustId = data.getUsrCustId();  //用户客户号 
	 String trxId = data.getTrxId();      //本平台交易唯一标识
	 String isFreeze = data.getIsFreeze();  //是否冻结
	 String freezeOrdId = data.getFreezeOrdId();  //冻结订单号 
	 String freezeTrxId = data.getFreezeTrxId();  //冻结标识
	 String retUrl = data.getRetUrl();        //页面返回url
	 String bgRetUrl = data.getBgRetUrl();     //商户后台应答地址
	 String merPriv  = data.getMerPriv();     //商户私有域   对应数据库内部交易流水号 innerSeqNo
	 String RespExt = data.getRespExt();      //返参扩展域   
	 String chkValue = data.getChkValue();   //返回签名
	 
	 String responseList = JSON.toJSONString(data);  
	 String recvOrdId="RECV_ORD_ID_"+data.getOrdId();
	
	 context.put("recvOrdId", recvOrdId);
	 context.put("trxId", trxId);
	 context.put("ordId", ordId);
	 nav.forwardTo("/receive");
	
	 //投资金额
	String money = transAmt;
	mount = BigDecimal.valueOf(Double.valueOf(money));
	
    String orderNo = "";
    String proId = "";
    String vocherAmt = "";
    if(merPriv != null){//内部交易流水号
    	orderNo = merPriv.split(",")[0];
    	proId = merPriv.split(",")[1];
    }
    if(RespExt != null && RespExt.length()>0){
    	Map vocherMap = (Map)JSON.parse(RespExt);
    	Map vocherRet = (Map)JSON.parse(vocherMap.get("Vocher").toString());
    	vocherAmt = vocherRet.get("VocherAmt").toString();//代金券金额
    }
    
    /**
     * 判断表的更新是否处理完毕  若处理完毕则 abc_deal_record表中 dr_state 状态为3 处理完毕
     * 1、根据orderNo查询 判断dr_state 
     * 2、若为3则说明该笔投资处理完毕
     *
     */
    List<DealRecordDO> dealRecords = dealRecordService.queryDealRecordsByInnerSeqNo(orderNo);
    
    if(dealRecords.size() > 0){
    	Integer drState = dealRecords.get(0).getDrState();
    	if(drState == 3){
    		result.setMessage("投资已处理完毕");
    		result.setSuccess(false);
    		return result;
    	}
    	
    }
    
    /**
	  * 投资成功返回
	  * 判断标的投资总金额有没有大于借款金额，若大于借款金额将该笔投资流标
	  * 不执行callback 
	  * 提示用户投资失败，刷新页面重新投资
	  * 2015/5/6 11:47
	  */
    InvestDO investDo = new InvestDO();
    investDo.setInInnerSeqNo(orderNo);
    InvestDO investDO = investDao.findByParam(investDo);
    final LoanDO loanDO = loanDao.findByLoanIdWithLock(investDO.getInBidId());
    //该笔投资过后的总投资金额 与 借款金额 进行比较
    BigDecimal loanTotalMoney = loanDO.getLoanCurrentValidInvest().add(mount);
    if(loanTotalMoney.compareTo(loanDO.getLoanMoney()) > 0){
    	
    	
    	HuiFuData data1 = new HuiFuData();
    	data.setUrl(SystemGetPropeties.getChinaPnrString("url").toString());
		
	    String unfrezzOrdId=String.valueOf(System.currentTimeMillis());
	    String unfrezzOrdDate=new SimpleDateFormat("yyyyMMdd").format(new Date());
	    data1.setUrl(SystemGetPropeties.getChinaPnrString("url"));
	    data1.setVersion(SystemGetPropeties.getChinaPnrString("Version"));
	    data1.setCmdId("UsrUnFreeze");
	    data1.setMerCustId(SystemGetPropeties.getChinaPnrString("MerCustId"));
	    data1.setOrdId(unfrezzOrdId);
	    data1.setOrdDate(unfrezzOrdDate);
	    data1.setTrxId(freezeTrxId.toString());
	    data1.setRetUrl("");
	    data1.setBgRetUrl(SystemGetPropeties.getChinaPnrString("notifyurlprefix")+SystemGetPropeties.getChinaPnrString("UnFreezeBgRetUrl"));
	    data1.setMerPriv("");
	    Map<String, String> unfreezeMap=huifuPayService.unFreeze(data1);
	    result.setError(CommonResultCode.BIZ_ERROR, "您的投资没有成功，请重新刷新页面查看您要投资的标");
    	return result;
    }
    
    
    
  //保存回调
    CashRecordDO cashRecord = new CashRecordDO();
    
    cashRecord.setCrSeqNo(orderNo);   
    cashRecord.setCrResponse(responseList);
    cashRecord.setCrResponseState(200);
    
    //更新cashRecord
    cashRecordService.modifyCashRecordState(cashRecord);
    
    String orderIdDateFreezeOrdId = ordId + "," + ordDate + "," + freezeTrxId ;   //拼接 订单号和订单日期和冻结订单号和冻结订单标识    
    
    InvestOrderDO order = new InvestOrderDO();
    order.setIoInnerSeqNo(orderNo);       
    order.setIoOutSeqNo(orderIdDateFreezeOrdId);
    
    //更新项目投资订单表
    BaseResult baseResult = investOrderService.updateBySeqNo(order);
    if (!baseResult.isSuccess()) {
        return result.setError(CommonResultCode.BIZ_ERROR, "更新第三方业务流水记录失败");
    }
    
    String resultCode = "";
    if(!("".equals(respCode) || respCode == null)){
    	resultCode = respCode;
    }
    String status;
    if ("000".equals(resultCode)) {
        status = "TRADE_FINISHED";
    } else {
        status = "TRADE_FAILURE";
    }
    BaseResult modifyResult = dealRecordService.modifyDealRecordState(orderNo, status, mount);
    if (!modifyResult.isSuccess()) {
        result.setSuccess(false);
    } else {
        result.setSuccess(true);
    }
    result.setMessage(respDesc);
	return result;
	
    }

}
