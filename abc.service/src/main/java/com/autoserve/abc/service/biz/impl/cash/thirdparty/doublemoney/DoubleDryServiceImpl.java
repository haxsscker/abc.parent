package com.autoserve.abc.service.biz.impl.cash.thirdparty.doublemoney;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.TransferActionStste;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.Common;
import com.autoserve.abc.service.biz.impl.cash.DigestUtil;
import com.autoserve.abc.service.biz.impl.cash.LoanJsonList;
import com.autoserve.abc.service.biz.impl.cash.MiscUtil;
import com.autoserve.abc.service.biz.impl.cash.MoneymoremorePlatConstant;
import com.autoserve.abc.service.biz.impl.cash.SecondaryJsonList;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.result.MapResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.http.AbcHttpCallService;
import com.autoserve.abc.service.http.AbcHttpCallServiceImpl;
import com.autoserve.abc.service.util.RsaHelper;
import com.autoserve.abc.service.util.SystemGetPropeties;

@Service
public class DoubleDryServiceImpl implements DoubleDryService {
    private static final Logger      logger             = LoggerFactory.getLogger(DoubleDryServiceImpl.class);

    @Resource
    private final AbcHttpCallService abcHttpCallService = new AbcHttpCallServiceImpl();
    
    @Autowired
	private UserDao userDao;
	@Autowired
	private AccountInfoService accountInfoService;
    
	@Override
    public BigDecimal queryRZAvlBalance(int userId){
    	UserDO userDO = userDao.findById(userId);

		UserIdentity userIdentity = new UserIdentity();
		userIdentity.setUserId(userDO.getUserId());
		userIdentity.setUserType(UserType.valueOf(userDO.getUserType()));
		PlainResult<Account> account = accountInfoService
				.queryByUserId(userIdentity);
		String accountMark = account.getData().getAccountMark(); // 多多号id
		Double[] accountBacance = { 0.00, 0.00, 0.00 };
		if (accountMark != null && !"".equals(accountMark)) {
			accountBacance = this.queryBalance(accountMark,
					"1");
		}
		return new BigDecimal(accountBacance[1]+"");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public Double[] queryBalance(String PlatformId, String PlatformType) {
        StringBuffer logBuffer = new StringBuffer("st[");
        RsaHelper rsa = RsaHelper.getInstance();
        Double AcctBal = 0d;//账面总余额
        Double AvlBal = 0d;//可用金额
        Double FrzBal = 0d;//冻结金额
        Map<String, String> paramsMap = new HashMap<String, String>();
        String PlatformMoneymoremore = SystemGetPropeties.getStrString("PlatformMoneymoremore");
        paramsMap.put("PlatformId", PlatformId);
        paramsMap.put("PlatformType", PlatformType);
        paramsMap.put("PlatformMoneymoremore", PlatformMoneymoremore);

        String dataStr = PlatformId + PlatformType + PlatformMoneymoremore;
        String privatekey = SystemGetPropeties.getStrString("privatekey");
        String SignInfo = rsa.signData(dataStr, privatekey);
        paramsMap.put("SignInfo", SignInfo);
        String submitUrl = SystemGetPropeties.getStrString("balancequeryUrl");
        //发送请求,获取数据
        
        System.out.println("============requestDataMap================");
        System.out.println(paramsMap);
        
        AbcHttpCallService callService = new AbcHttpCallServiceImpl();
        String resultStr = callService.httpPost(submitUrl, paramsMap).getData();
        logger.info("resultStr:"+resultStr);
        System.out.println("resultStr:"+resultStr);
        logBuffer.append(" paramsMap:{").append(paramsMap).append("}");
        logBuffer.append(" submitUrl:{").append(submitUrl).append("}");
        logBuffer.append(" dataStr:{").append(dataStr).append("}");
        logBuffer.append(" resultStr:{").append(resultStr).append("}");

        //if (StringUtil.isBlank(resultStr)) {
            //logBuffer.append("]ed");
            //throw new IllegalArgumentException("查询余额失败");
        //}
        if (resultStr != null && !resultStr.trim().equals("")) {
        	if(resultStr.indexOf("|")>0){
        		String[] moneys = resultStr.split("\\|");
                if (moneys.length == 3) {
                    if (moneys[0] != null && !moneys[0].trim().equals("")) {
                        AvlBal = Double.valueOf(moneys[0]);
                    }
                    if (moneys[2] != null && !moneys[2].trim().equals("")) {
                        FrzBal = Double.valueOf(moneys[2]);
                    }
                    AcctBal = AvlBal + FrzBal;
                } 
                //查询p613时，返回的结果是4个
                else if(moneys.length==4){
                	AvlBal = Double.valueOf(moneys[1]);
                }
        	}else{
        		AvlBal = 0.0;
        		FrzBal = 0.0;
        		AcctBal = 0.0;
        	}
            
        }
        Double[] dou = new Double[3];
        dou[0] = AcctBal;
        dou[1] = AvlBal;
        dou[2] = FrzBal;
        logBuffer.append(" 余额:").append(dou[0]).append("|").append(dou[1]).append("|").append(dou[2]).append("]ed");
        logger.info("余额查询  : " + logBuffer);
        return dou;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Map> openAccent(Map params) {

        /**
         * 开户接口用到的数据
         */
        String OpenType = "1";//账户类型
        String MobileNo = "";//手机号
        String UsrName = "";//真实姓名
        String IdentNo = "";//身份证号/营业执照号
        String PageReturnUrl = "";//页面返回网址
        String BgRetUrl = "";//后台通知网址
        String mac = "";//签名
        PlainResult<Map> returnData = new PlainResult<Map>();
        SystemGetPropeties sgp = new SystemGetPropeties();
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            if ("MobileNo".equals(key)) {
            	MobileNo = entry.getValue();
            }
            if ("UsrName".equals(key)) {
            	UsrName = entry.getValue();
            }
            if ("IdentNo".equals(key)) {
            	IdentNo = entry.getValue();
            }
        }

        Map<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put("OpenType", OpenType);
        paramsMap.put("MobileNo", MobileNo);
        paramsMap.put("UsrName", UsrName);
        paramsMap.put("IdentNo", IdentNo);


    	BgRetUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                + SystemGetPropeties.getStrString("openAccountNotifyUrl");
  
        PageReturnUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                + SystemGetPropeties.getStrString("registerReturnUrl");
        paramsMap.put("NotifyURL", BgRetUrl);
        String dataStr = "";
        String submitUrl = SystemGetPropeties.getStrString("registerUrl");
        String privatekey = SystemGetPropeties.getStrString("privatekey");
        RsaHelper rsa = RsaHelper.getInstance();
       // SignInfo = rsa.signData(dataStr, privatekey);
        paramsMap.put("SignInfo", mac);
        paramsMap.put("accept-charset", "gbk");
        paramsMap.put("onsubmit", "document.charset='gbk';");
        
        paramsMap.put("ReturnURL", PageReturnUrl);
        paramsMap.put("SubmitURL", submitUrl);
        paramsMap.put("result", "moneyRegister");
        returnData.setSuccess(true);
        returnData.setData(paramsMap);
        return returnData;

    }

    /**
     * 充值接口
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Map> recharge(Map params) {
        PlainResult<Map> returnData = new PlainResult<Map>();
//        SystemGetPropeties sgp = new SystemGetPropeties();
        String PlatformMoneymoremore = SystemGetPropeties.getStrString("PlatformMoneymoremore");
//        StringBuffer logBuffer = new StringBuffer("st[");
        String returnUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                + SystemGetPropeties.getStrString("rechargeReturnURL");
        String notifyUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                + SystemGetPropeties.getStrString("rechargeNotifyURL");
        String submitUrl = SystemGetPropeties.getStrString("rechargeSubmitUrl");
        params.put("PlatformMoneymoremore", "PlatformMoneymoremore");
        params.put("returnUrl", "returnUrl");
        params.put("notifyUrl", "notifyUrl");
        params.put("submitUrl", "submitUrl");
        String dataStr = params.get("RechargeMoneymoremore") + PlatformMoneymoremore + params.get("OrderNo")
                + params.get("Amount") + returnUrl + notifyUrl;
        RsaHelper rsa = RsaHelper.getInstance();
        String SignInfo = rsa.signData(dataStr, SystemGetPropeties.getStrString("privatekey"));
        returnData.setSuccess(true);
        returnData.setData(params);
        
        System.out.println("============requestDataMap================");
        System.out.println(params);

        return returnData;
    }

    @Override
    public MapResult<String, String> transfer(String LoanJsonList, String TransferAction, String Action,
                                              String TransferType, String NeedAudit, Map<String, String> params) {
        MapResult<String, String> result = new MapResult<String, String>();
        StringBuffer logBuffer = new StringBuffer("st[");
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("PlatformMoneymoremore", SystemGetPropeties.getStrString("PlatformMoneymoremore"));
        paramsMap.put("Action", Action);
        paramsMap.put("TransferType", TransferType);
        paramsMap.put("NeedAudit", NeedAudit);
        String returnUrl = "";
        String notifyUrl = "";
        String Remark1 = "";
        String Remark2 = "";
        String outTransferAction = "";
        if (TransferActionStste.INVEST.state.equals(TransferAction)) {
            outTransferAction = TransferAction;
            paramsMap.put("TransferAction", TransferAction);
            returnUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                    + SystemGetPropeties.getStrString("transferReturnURL");
            notifyUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                    + SystemGetPropeties.getStrString("transferNotifyURL");
        } else if (TransferActionStste.REPAY.state.equals(TransferAction)) {
            outTransferAction = TransferAction;
            paramsMap.put("TransferAction", TransferAction);
            returnUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                    + SystemGetPropeties.getStrString("repayReturnURL");
            notifyUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                    + SystemGetPropeties.getStrString("repayNotifyURL");
        } else if (TransferActionStste.OTHER.state.equals(TransferAction)) {
            outTransferAction = TransferAction;
            paramsMap.put("TransferAction", TransferAction);
            returnUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                    + SystemGetPropeties.getStrString("transferReturnURL");
            notifyUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                    + SystemGetPropeties.getStrString("transferNotifyURL");
        } else if (TransferActionStste.STAGE_OTHER.state.equals(TransferAction)) {
            outTransferAction = TransferActionStste.REPAY.state;
            Remark1 = params.get("Remark2").toString();
            Remark2 = params.get("ResultCode").toString();
            paramsMap.put("TransferAction", outTransferAction);
            returnUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                    + SystemGetPropeties.getStrString("repayReturnURL");
            notifyUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                    + SystemGetPropeties.getStrString("repayNotifyURL");
            //            returnUrl = SystemGetPropeties.getStrString("notifyurlprefix")
            //                    + SystemGetPropeties.getStrString("otherPayReturnURL");
            //            notifyUrl = SystemGetPropeties.getStrString("notifyurlprefix")
            //                    + SystemGetPropeties.getStrString("otherPayNotifyURL");
        }

        paramsMap.put("Remark1", Remark1);
        paramsMap.put("Remark2", Remark2);
        paramsMap.put("ReturnURL", returnUrl);
        paramsMap.put("NotifyURL", notifyUrl);
        String submitUrl = SystemGetPropeties.getStrString("transferSubmitUrl");
        String privatekey = SystemGetPropeties.getStrString("privatekey");
        RsaHelper rsa = RsaHelper.getInstance();
        Map<String, String> returnDataMap = new HashMap<String, String>();
        String dataStr = new String();
        String SignInfo = new String();
        String resultStr = new String();
        String toLoanJson = LoanJsonList.replace("[", "").replace("]", "");
        String[] LoanJsonListArray = toLoanJson.split(",/");
        //判断是转账列表是否超过200
        if (LoanJsonListArray.length > 200) {
            String outLoanJsonList = new String();
            for (int i = 0; i < LoanJsonListArray.length; i++) {
                if (i % 200 == 0) {
                    outLoanJsonList = "[";
                }
                if (i != 0 && (i + 1) % 200 == 0 || i == LoanJsonListArray.length - 1) {
                    outLoanJsonList = outLoanJsonList + LoanJsonListArray[i] + "]";
                    paramsMap.put("LoanJsonList", Common.UrlEncoder(outLoanJsonList, "utf-8"));
                    dataStr = outLoanJsonList + SystemGetPropeties.getStrString("PlatformMoneymoremore")
                            + Integer.parseInt(outTransferAction) + Integer.parseInt(Action)
                            + Integer.parseInt(TransferType) + NeedAudit + Remark1 + Remark2 + returnUrl + notifyUrl;
                    SignInfo = rsa.signData(dataStr, privatekey);
                    paramsMap.put("SignInfo", SignInfo);
                    logger.info("转账privatekey  : " + privatekey);
                    logger.info("转账dataStr  : " + dataStr);
                    //发送请求,获取数据w
                    
                    System.out.println("============requestDataMap================");
                    System.out.println(paramsMap);
                    
                    resultStr = abcHttpCallService.httpPost(submitUrl, paramsMap).getData();
                    outLoanJsonList = new String();
                    if (returnDataMap.isEmpty()) {
                        returnDataMap = MiscUtil.parseJSON(resultStr.toString());
                        String loanJsonList = Common.UrlDecoder(returnDataMap.get("LoanJsonList"), "utf-8");
                        returnDataMap.put("LoanJsonList", loanJsonList);
                    } else {
                        Map<String, String> returnMap = new HashMap<String, String>();
                        String oneLoanJsonList = returnDataMap.get("LoanJsonList");
                        returnMap = MiscUtil.parseJSON(resultStr.toString());
                        String loanJsonList = Common.UrlDecoder(returnMap.get("LoanJsonList"), "utf-8");
                        String toMapJson = oneLoanJsonList + loanJsonList;
                        returnDataMap.put("LoanJsonList", toMapJson.replace("][", ","));
                        returnMap = new HashMap<String, String>();
                    }
                } else {
                    outLoanJsonList = outLoanJsonList + LoanJsonListArray[i] + ",";
                }
            }

        } else {
            paramsMap.put("LoanJsonList", Common.UrlEncoder(LoanJsonList, "utf-8"));
            dataStr = LoanJsonList + SystemGetPropeties.getStrString("PlatformMoneymoremore")
                    + Integer.parseInt(outTransferAction) + Integer.parseInt(Action) + Integer.parseInt(TransferType)
                    + NeedAudit + Remark1 + Remark2 + returnUrl + notifyUrl;
            SignInfo = rsa.signData(dataStr, privatekey);
            paramsMap.put("SignInfo", SignInfo);
            logger.info("转账privatekey  : " + privatekey);
            logger.info("转账dataStr  : " + dataStr);
            
            System.out.println("============requestDataMap================");
            System.out.println(paramsMap);
            
            //发送请求,获取数据w
            resultStr = abcHttpCallService.httpPost(submitUrl, paramsMap).getData();

            if (resultStr == null) {
                returnDataMap.put("ResultCode", "ResultCode");
                returnDataMap.put("Message", "连接失败");
                returnDataMap.put("LoanJsonList", Common.UrlDecoder(paramsMap.get("LoanJsonList"), "utf-8"));
                result.setData(returnDataMap);
                return result;
            }
            if (NeedAudit.equals(MoneymoremorePlatConstant.AUTOMATIC_THROUTH)) {
                List<String> list = Common.dealJsonStr(resultStr);
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        //转化返回数据，返回一个map集合
                        returnDataMap = MiscUtil.parseJSON(list.get(i));
                        String loanJsonList = Common.UrlDecoder(returnDataMap.get("LoanJsonList"), "utf-8");
                        StringBuffer dataBuffer = new StringBuffer();
                        dataBuffer.append(loanJsonList).append(returnDataMap.get("PlatformMoneymoremore"))
                                .append(returnDataMap.get("Action")).append(returnDataMap.get("ResultCode"));
                        returnDataMap.put("LoanJsonList", loanJsonList);
                    }
                }
            } else {
                returnDataMap = MiscUtil.parseJSON(resultStr.toString());
                String loanJsonList = Common.UrlDecoder(returnDataMap.get("LoanJsonList"), "utf-8");
                StringBuffer dataBuffer = new StringBuffer();
                dataBuffer.append(loanJsonList).append(returnDataMap.get("PlatformMoneymoremore"))
                        .append(returnDataMap.get("Action")).append(returnDataMap.get("ResultCode"));
                //对返回数据进行验证签名
                DigestUtil.check(dataBuffer.toString(), returnDataMap, SystemGetPropeties.getStrString("publickey"),
                        "utf-8");
                returnDataMap.put("LoanJsonList", loanJsonList);
            }
            logBuffer.append(" returnDataMap:{").append(returnDataMap).append("}");
            logBuffer.append("]ed");
            logger.info("转账  : " + logBuffer);
        }
        result.setData(returnDataMap);
        return result;
    }

    @Override
    public PlainResult<Map<String, String>> transferaudit(String LoanNoList, String AuditType, String seq, String money) {
        PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
        StringBuffer logBuffer = new StringBuffer("st[");
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("PlatformMoneymoremore", SystemGetPropeties.getStrString("PlatformMoneymoremore"));
        paramsMap.put("AuditType", AuditType);
        String returnUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                + SystemGetPropeties.getStrString("auditReturnURL");
        String notifyUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                + SystemGetPropeties.getStrString("auditNotifyURL");
        paramsMap.put("ReturnURL", returnUrl);
        paramsMap.put("NotifyURL", notifyUrl);
        paramsMap.put("Remark1", seq);
        paramsMap.put("Remark2", money);
        String submitUrl = SystemGetPropeties.getStrString("auditSubmitUrl");
        String privatekey = SystemGetPropeties.getStrString("privatekey");
        RsaHelper rsa = RsaHelper.getInstance();

        Map<String, String> returnDataMap = new HashMap<String, String>();
        String[] LoanJsonListArray = LoanNoList.split(",");
        //判断是转账列表是否超过200
        if (LoanJsonListArray.length > 200) {
            String outLoanJsonList = new String();
            for (int i = 0; i < LoanJsonListArray.length; i++) {
                if (i != 0 && (i + 1) % 200 == 0 || i == LoanJsonListArray.length - 1) {
                    outLoanJsonList = outLoanJsonList + LoanJsonListArray[i];
                    paramsMap.put("LoanNoList", outLoanJsonList);
                    String dataStr = outLoanJsonList + SystemGetPropeties.getStrString("PlatformMoneymoremore")
                            + AuditType + seq + money + returnUrl + notifyUrl;
                    String SignInfo = rsa.signData(dataStr, privatekey);
                    paramsMap.put("SignInfo", SignInfo);
                    
                    System.out.println("============requestDataMap================");
                    System.out.println(paramsMap);
                    
                    //发送请求,获取数据
                    String resultStr = abcHttpCallService.httpPost(submitUrl, paramsMap).getData();
                    logBuffer.append(" paramsMap:{").append(paramsMap).append("}");

                    outLoanJsonList = new String();
                    if (returnDataMap.isEmpty()) {
                        returnDataMap = MiscUtil.parseJSON(resultStr);
                        String loanJsonList = returnDataMap.get("LoanNoList");
                        returnDataMap.put("LoanNoList", loanJsonList);
                    } else {
                        Map<String, String> returnMap = new HashMap<String, String>();
                        String oneLoanJsonList = returnDataMap.get("LoanNoList");
                        returnMap = MiscUtil.parseJSON(resultStr.toString());
                        String loanJsonList = returnMap.get("LoanNoList");
                        String toMapJson = oneLoanJsonList + "," + loanJsonList;
                        returnDataMap.put("LoanNoList", toMapJson);
                        returnMap = new HashMap<String, String>();
                    }
                } else {
                    outLoanJsonList = outLoanJsonList + LoanJsonListArray[i] + ",";
                }
            }

        } else {
            paramsMap.put("LoanNoList", LoanNoList);
            String dataStr = LoanNoList + SystemGetPropeties.getStrString("PlatformMoneymoremore") + AuditType + seq
                    + money + returnUrl + notifyUrl;
            String SignInfo = rsa.signData(dataStr, privatekey);
            paramsMap.put("SignInfo", SignInfo);
            
            System.out.println("============requestDataMap================");
            System.out.println(paramsMap);
            
            //发送请求,获取数据
            String resultStr = abcHttpCallService.httpPost(submitUrl, paramsMap).getData();
            logBuffer.append(" paramsMap:{").append(paramsMap).append("}");

            //转化返回数据，返回一个map集合
            returnDataMap = MiscUtil.parseJSON(resultStr);
        }

        //      logger.info("返回参数===="+returnDataMap);
        result.setData(returnDataMap);
        StringBuffer dataBuffer = new StringBuffer();
        System.out.println("============returnDataMap================");
        System.out.println(returnDataMap);
        dataBuffer.append(returnDataMap.get("LoanNoList")).append(returnDataMap.get("LoanNoListFail"))
                .append(returnDataMap.get("PlatformMoneymoremore")).append(returnDataMap.get("AuditType"))
                .append(returnDataMap.get("ResultCode"));
        //对返回数据进行验证签名
        //DigestUtil.check(dataBuffer.toString(), returnDataMap, SystemGetPropeties.getStrString("publickey"), "utf-8");
        logBuffer.append(" returnDataMap:{").append(returnDataMap).append("}");

        logBuffer.append("]ed");

        logger.info(" 审核: " + logBuffer);
        return result;
    }
    
    @Override
    public String loanJsonList(String inusrId, String outUsrId, String operateMoney, String batchNo, String orderNo,
                               String SecondaryJsonList) {
        List<LoanJsonList> list = new ArrayList<LoanJsonList>();
        LoanJsonList loan = new LoanJsonList();
        String loanJsonList = "";
        if (inusrId != null) {
            loan.setLoanInMoneymoremore(inusrId);
        }
        if (outUsrId != null) {
            loan.setLoanOutMoneymoremore(outUsrId);
        }
        loan.setOrderNo(orderNo);
        loan.setBatchNo(batchNo);
        loan.setAmount(operateMoney);
        loan.setSecondaryJsonList(SecondaryJsonList);
        list.add(loan);
        loanJsonList = Common.JSONEncode(list);
        return loanJsonList;
    }

    @Override
    public String secondaryJsonList(String LoanInMoneymoremore, String Amount, String TransferName, String Remark) {
        List<SecondaryJsonList> list = new ArrayList<SecondaryJsonList>();
        SecondaryJsonList loan = new SecondaryJsonList();
        String secondaryJsonList = "";
        loan.setLoanInMoneymoremore(LoanInMoneymoremore);
        loan.setAmount(Amount);
        loan.setTransferName(TransferName);
        loan.setRemark(Remark);
        list.add(loan);
        secondaryJsonList = Common.JSONEncode(list);
        return secondaryJsonList;
    }

    @Override
    public AccountInfoDO formatAccount(Map params) {
        String AccountNumber = params.get("AccountNumber").toString();//多多号
        String AccountType = params.get("AccountType").toString();//账户类型
        String Mobile = params.get("Mobile").toString();//手机号
        String Email = params.get("Email").toString();//邮箱
        String RealName = params.get("RealName").toString();//真实姓名
        String IdentificationNo = params.get("IdentificationNo").toString();//身份证
        String LoanPlatformAccount = params.get("Remark1").toString();//用户在网贷平台的账号
        AccountInfoDO account = new AccountInfoDO();
        account.setAccountUserId(Integer.parseInt(IdentificationNo));
        account.setAccountLegalName(RealName);
        account.setAccountUserType(Integer.parseInt(AccountType));
        account.setAccountUserName(RealName);
        account.setAccountUserCard(IdentificationNo);
        account.setAccountNo(LoanPlatformAccount);
        account.setAccountUserEmail(Email);
        account.setAccountUserPhone(Mobile);

        return account;
    }

    @Override
    public Map<String, String> authorize(Map<String, String> map) {
        String Remark1 = "";
        String AuthorizeTypeClose = "";
        String AuthorizeTypeOpen = "";
        StringBuffer logBuffer = new StringBuffer("st[");
        String monemapymoremoreId = map.get("MoneymoremoreId");
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("MoneymoremoreId", monemapymoremoreId);
        paramsMap.put("PlatformMoneymoremore", SystemGetPropeties.getStrString("PlatformMoneymoremore"));
        String returnUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                + SystemGetPropeties.getStrString("authorizeReturnURL");
        String notifyUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                + SystemGetPropeties.getStrString("authorizeNotifyURL");
        paramsMap.put("ReturnURL", returnUrl);
        paramsMap.put("NotifyURL", notifyUrl);
        String submitUrl = SystemGetPropeties.getStrString("authorizeSubmitUrl");
        paramsMap.put("SubmitURL", submitUrl);
        if (map.get("Remark1") != null) {
            Remark1 = map.get("Remark1");
            paramsMap.put("Remark1", Remark1);
        }
        if (map.get("AuthorizeTypeClose") != null) {
            AuthorizeTypeClose = map.get("AuthorizeTypeClose");
            paramsMap.put("AuthorizeTypeClose", AuthorizeTypeClose);
        }
        if (map.get("AuthorizeTypeOpen") != null) {
            AuthorizeTypeOpen = map.get("AuthorizeTypeOpen");
            paramsMap.put("AuthorizeTypeOpen", AuthorizeTypeOpen);
        }
        String dataStr = monemapymoremoreId + SystemGetPropeties.getStrString("PlatformMoneymoremore")
                + AuthorizeTypeOpen + AuthorizeTypeClose + Remark1 + returnUrl + notifyUrl;
        RsaHelper rsa = RsaHelper.getInstance();
        String SignInfo = rsa.signData(dataStr, SystemGetPropeties.getStrString("privatekey"));
        paramsMap.put("SignInfo", SignInfo);
        /*
         * String resultStr = abcHttpCallService.httpPost(submitUrl,
         * paramsMap).getData(); Map<String, String> returnDataMap =
         * MiscUtil.parseJSON(resultStr);
         */
        logBuffer.append(" paramsMap:{").append(paramsMap).append("}");
        logBuffer.append("]ed");
        logger.info(" 授权: " + logBuffer);
        return paramsMap;

    }

    @Override
    public Map<String, String> trannfee(String LoanJsonList) {
        Map<String, String> params = new HashMap();
        MapResult<String, String> result = this.transfer(LoanJsonList, "2", "2", "2", "1", params);
        Map<String, String> reurnMap = result.getData();
        return reurnMap;
    }

    @Override
    public Map<String, String> toloanrelease(String Amount, String OrderNo, String MoneymoremoreId) {
        StringBuffer logBuffer = new StringBuffer("st[");
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("MoneymoremoreId", MoneymoremoreId);
        paramsMap.put("PlatformMoneymoremore", SystemGetPropeties.getStrString("PlatformMoneymoremore"));
        paramsMap.put("OrderNo", OrderNo);
        paramsMap.put("Amount", Amount);
        String returnUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                + SystemGetPropeties.getStrString("auditReturnURL");
        String notifyUrl = SystemGetPropeties.getStrString("notifyurlprefix")
                + SystemGetPropeties.getStrString("auditNotifyURL");
        paramsMap.put("ReturnURL", returnUrl);
        paramsMap.put("NotifyURL", notifyUrl);

        String dataStr = MoneymoremoreId + SystemGetPropeties.getStrString("PlatformMoneymoremore") + OrderNo
                + returnUrl + notifyUrl;
        String submitUrl = SystemGetPropeties.getStrString("toloanreleaseSubmitUrl");
        String privatekey = SystemGetPropeties.getStrString("privatekey");
        //发送请求,获取数据
        RsaHelper rsa = RsaHelper.getInstance();
        String SignInfo = rsa.signData(dataStr, privatekey);
        
        System.out.println("============requestDataMap================");
        System.out.println(paramsMap);
        
        String resultStr = abcHttpCallService.sendPost(submitUrl, paramsMap).getData();
        Map<String, String> returnDataMap = MiscUtil.parseJSON(resultStr.toString());
        logBuffer.append(" paramsMap:{").append(paramsMap).append("}");
        //转化返回数据，返回一个map集合

        logger.info("返回参数====" + returnDataMap);

        StringBuffer dataBuffer = new StringBuffer();
        dataBuffer.append(returnDataMap.get("MoneymoremoreId")).append(returnDataMap.get("PlatformMoneymoremore"))
                .append(returnDataMap.get("LoanNo")).append(returnDataMap.get("OrderNo"))
                .append(returnDataMap.get("Amount")).append(returnDataMap.get("ReleaseType"))
                .append(returnDataMap.get("ResultCode"));
        //对返回数据进行验证签名
        DigestUtil.check(dataBuffer.toString(), returnDataMap, SystemGetPropeties.getStrString("publickey"), "utf-8");

        logBuffer.append(" returnDataMap:{").append(returnDataMap).append("}");

        logBuffer.append("]ed");

        logger.info(" 资金释放: " + logBuffer);
        return returnDataMap;
    }

    /**
     * 封装发送三方接口的http请求
     * 
     * @param Map 接口文档中所有的数据和提交的URL
     */
    @Override
    public PlainResult<Map> postHttpToDD(String submitUrl, Map params) {
        PlainResult<Map> result = new PlainResult<Map>();
        AbcHttpCallService callService = new AbcHttpCallServiceImpl();
        String resultStr = callService.httpPost(submitUrl, params).getData();

        Map paramsMap = new HashMap();
        paramsMap = (Map) JSON.parse(resultStr);
        result.setData(paramsMap);
        return result;
    }

    /**
     * 投资发第三方接口
     */
    @Override
    public PlainResult<Map<String, String>> invest(String LoanJsonList) {
        PlainResult<Map<String, String>> resultMap = new PlainResult<Map<String, String>>();
        try {
            Map<String, String> param = new HashMap<String, String>();
            MapResult<String, String> params = this.transfer(LoanJsonList, "1", "2", "2", "", param);
            Map<String, String> reurnMap = params.getData();
            if (!"88".equals(reurnMap.get("ResultCode"))) {
                resultMap.setSuccess(false);
            }
            resultMap.setData(reurnMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 满标资金划转
     */
    @Override
    public PlainResult<Map<String, String>> fullTranfer(String LoanNoList, String LoanJsonList, String seqNo,
                                                        String money) {
        PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
        String AuditType = "1";
        //审核通过所有外部流水号
        PlainResult<Map<String, String>> resultParams = this.transferaudit(LoanNoList, AuditType, seqNo, money);
        Map<String, String> params = resultParams.getData();
        String resultCode = params.get("ResultCode").toString();
        Map<String, String> map = new HashMap<String, String>();
        map.put("transferaudit", params.toString());
        if ("88".equals(resultCode)) {
        	//手续费，直接转账通过，无需审核
            MapResult<String, String> resultMap = this.transfer(LoanJsonList, "5", "2", "2", "1", params);
            Map<String, String> reurnMap = resultMap.getData();
            map.put("transfer", reurnMap.toString());
            result.setData(map);
            if ("88".equals(reurnMap.get("ResultCode").toString())) {
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
            }
        } else {
            result.setData(map);
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public PlainResult<Map<String, String>> payBack(String LoanJsonList) {
        PlainResult<Map<String, String>> resultMap = new PlainResult<Map<String, String>>();
        try {
            Map<String, String> param = new HashMap<String, String>();
            MapResult<String, String> params = this.transfer(LoanJsonList, "2", "2", "2", "1", param);
            Map<String, String> reurnMap = params.getData();
            if (!"88".equals(reurnMap.get("ResultCode"))) {
                resultMap.setSuccess(false);
            }
            resultMap.setData(reurnMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 对账接口
     */
    @Override
    public PlainResult<Map> balanceAccount(Map<String, String> params) {
        PlainResult<Map> resultMap = new PlainResult<Map>();
        String PlatformMoneymoremore = SystemGetPropeties.getStrString("PlatformMoneymoremore");
        String Action = "";
        String LoanNo = "";
        String OrderNo = "";
        String BatchNo = "";
        String BeginTime = "";
        String EndTime = "";
        params.put("PlatformMoneymoremore", PlatformMoneymoremore);
        Iterator keys = params.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if ("Action".equals(key)) {
                Action = params.get("Action").toString();
            }
            if ("LoanNo".equals(key)) {
                LoanNo = params.get("LoanNo").toString();
            }
            if ("OrderNo".equals(key)) {
                OrderNo = params.get("OrderNo").toString();
            }
            if ("BatchNo".equals(key)) {
                BatchNo = params.get("BatchNo").toString();
            }
            if ("BeginTime".equals(key)) {
                BeginTime = params.get("BeginTime").toString();
            }
            if ("EndTime".equals(key)) {
                EndTime = params.get("EndTime").toString();
            }
        }
        String dataStr = PlatformMoneymoremore + Action + LoanNo + OrderNo + BatchNo + BeginTime + EndTime;
        String submitUrl = SystemGetPropeties.getStrString("balanceAccountSubmitUrl");
        String privatekey = SystemGetPropeties.getStrString("privatekey");
        //发送请求,获取数据
        RsaHelper rsa = RsaHelper.getInstance();
        String SignInfo = rsa.signData(dataStr, privatekey);
        params.put("SignInfo", SignInfo);
        
        System.out.println("============requestDataMap================");
        System.out.println(params);
        String resultStr = abcHttpCallService.httpPost(submitUrl, params).getData();
        //	        Map<String, String> returnDataMap = MiscUtil.parseJSON(resultStr.toString());
        if (resultStr != null && !"".equals(resultStr)) {
            JSONArray array = (JSONArray) JSON.parse(resultStr.toString());
            Map<String, String> returnDataMap = MiscUtil.parseJSON(array.getString(0));
            resultMap.setData(returnDataMap);
        }
        logger.info("对账接口: " + resultStr);
        return resultMap;

    }

    @Override
    public PlainResult<Map<String, String>> loanFree(String LoanNoList, String seqNo, String money) {
        PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
        String AuditType = "2";
        PlainResult<Map<String, String>> resultParams = this.transferaudit(LoanNoList, AuditType, seqNo, money);
        Map<String, String> params = resultParams.getData();
        result.setData(params);
        return result;
    }

    /**
     * 三合一接口
     */
    @Override
    public PlainResult<Map> fastPay(Map<String, String> params) {
        PlainResult<Map> resultMap = new PlainResult<Map>();
        String PlatformMoneymoremore = SystemGetPropeties.getStrString("PlatformMoneymoremore");
        String MoneymoremoreId = "";
        String Action = "";
        String CardNo = "";
        String WithholdBeginDate = "";
        String WithholdEndDate = "";
        String SingleWithholdLimit = "";
        String TotalWithholdLimit = "";
        String RandomTimeStamp = "";
        String Remark1 = "";
        String Remark2 = "";
        String Remark3 = "";
        String ReturnURL = "";
        String NotifyURL = "";
        
        params.put("PlatformMoneymoremore", PlatformMoneymoremore);
        Iterator keys = params.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if ("MoneymoremoreId".equals(key)) {
            	MoneymoremoreId = params.get("MoneymoremoreId").toString();
            }
            if ("Action".equals(key)) {
            	Action = params.get("Action").toString();
            }
            if ("CardNo".equals(key)) {
            	CardNo = params.get("CardNo").toString();
            }
            if ("WithholdBeginDate".equals(key)) {
            	WithholdBeginDate = params.get("WithholdBeginDate").toString();
            }
            if ("WithholdEndDate".equals(key)) {
            	WithholdEndDate = params.get("WithholdEndDate").toString();
            }
            if ("SingleWithholdLimit".equals(key)) {
            	SingleWithholdLimit = params.get("SingleWithholdLimit").toString();
            }
            if ("TotalWithholdLimit".equals(key)) {
            	TotalWithholdLimit = params.get("TotalWithholdLimit").toString();
            }
        }
        
        //根据Action做不同处理
		if (null != Action) {
			int actionType = Integer.valueOf(Action);
			switch (actionType) {
				case 1 : {break;}
				case 2 : {
					ReturnURL = SystemGetPropeties.getStrString("notifyurlprefix")
		                    + SystemGetPropeties.getStrString("bankBindReturnURL");
					NotifyURL = SystemGetPropeties.getStrString("notifyurlprefix")
		                    + SystemGetPropeties.getStrString("bankBindNotifyURL");
					
					params.put("ReturnURL", ReturnURL);
					params.put("NotifyURL", NotifyURL);
					break;
				}
				case 3 : {break;}
				case 4 : {break;}
				case 5 : {break;}
				default : break;
			}
		}
        
        
        String dataStr = MoneymoremoreId + PlatformMoneymoremore + Action + CardNo + WithholdBeginDate + WithholdEndDate + SingleWithholdLimit + TotalWithholdLimit 
        		+ RandomTimeStamp + Remark1 + Remark2 + Remark3 + ReturnURL + NotifyURL;
        String submitUrl = SystemGetPropeties.getStrString("submiturlprefix") + SystemGetPropeties.getStrString("bankBindSubmitUrl");
        String privatekey = SystemGetPropeties.getStrString("privatekey");
        //发送请求,获取数据
        RsaHelper rsa = RsaHelper.getInstance();
        String SignInfo = rsa.signData(dataStr, privatekey);
        params.put("SignInfo", SignInfo);
        params.put("submitUrl", submitUrl);
//        String resultStr = abcHttpCallService.httpPost(submitUrl, params).getData();
        //	        Map<String, String> returnDataMap = MiscUtil.parseJSON(resultStr.toString());
//        if (resultStr != null && !"".equals(resultStr)) {
//            JSONArray array = (JSONArray) JSON.parse(resultStr.toString());
//            Map<String, String> returnDataMap = MiscUtil.parseJSON(array.getString(0));
//            resultMap.setData(returnDataMap);
//        }
//        logger.info("对账接口: " + resultStr);
        
        resultMap.setData(params);
        return resultMap;
    }

	@Override
	public PlainResult<Map> bindCard(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BankInfoDO> queryCard(String accountNo, String userPhone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double[] queryBalanceDetail(String PlatformId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlainResult<Map> webInvest(Map params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PlainResult<Map> changPhone(Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PlainResult<Map> changPwd(Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> returnRed(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> queryAuthorizeInfo(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlainResult<Map<String, String>> backInvest(String innerSeqNo,
			List<DealRecordDO> dealRecords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> queryChargeAccount(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlainResult<Map> openChargeAccent(Map params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, String> queryPlatBalance(String accountNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> queryChargeAccountResult(String accountUserAccount,
			String mark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryChargeDetail(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlainResult<Map> closeAccount(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> queryGuarAccountResult(
			String accountUserAccount, String mark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> guarAuthorize(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> queryUserInf(String userPhone) {
		// TODO Auto-generated method stub
		return null;
	}

}