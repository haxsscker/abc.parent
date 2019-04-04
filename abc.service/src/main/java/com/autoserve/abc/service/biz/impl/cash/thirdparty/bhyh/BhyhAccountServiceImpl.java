package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.SysConfigDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.AccountInfoDao;
import com.autoserve.abc.dao.intf.EmployeeDao;
import com.autoserve.abc.dao.intf.SysConfigDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.convert.UserConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.BalanceSynAccount;
import com.autoserve.abc.service.biz.enums.ScoreType;
import com.autoserve.abc.service.biz.enums.TransferActionStste;
import com.autoserve.abc.service.biz.enums.UserAuthorizeFlag;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.Common;
import com.autoserve.abc.service.biz.impl.cash.DigestUtil;
import com.autoserve.abc.service.biz.impl.cash.LoanJsonList;
import com.autoserve.abc.service.biz.impl.cash.MiscUtil;
import com.autoserve.abc.service.biz.impl.cash.MoneymoremorePlatConstant;
import com.autoserve.abc.service.biz.impl.cash.SecondaryJsonList;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.XmlHelper;
import com.autoserve.abc.service.biz.impl.sys.SysConfigServiceImpl;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.MapResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.http.AbcHttpCallService;
import com.autoserve.abc.service.http.AbcHttpCallServiceImpl;
import com.autoserve.abc.service.util.RsaHelper;
import com.autoserve.abc.service.util.SystemGetPropeties;

@Service
public class BhyhAccountServiceImpl implements DoubleDryService {
    private static final Logger      logger             = LoggerFactory.getLogger(BhyhAccountServiceImpl.class);

    @Resource
    private final AbcHttpCallService abcHttpCallService = new AbcHttpCallServiceImpl();
    
    @Autowired
	private UserDao userDao;
	@Autowired
	private AccountInfoService accountInfoService;
	@Autowired
	private AccountInfoDao      accountDao;
	@Autowired
    private UserService  userService;
	@Autowired
    private HttpSession session;
	@Autowired
	private SysConfigDao configDao;
	@Autowired
	private EmployeeDao employeeDao;
	/**
	    * @Title: queryRZAvlBalance  
	    * @Description: 查询融资账户可用余额，用于还款钱判断余额是否足够 
	    * @param @param userId
	    * @param @return    参数  
	    * @return BigDecimal    返回类型  
	    * @throws
	 */
	@Override
    public BigDecimal queryRZAvlBalance(int userId){
    	UserDO userDO = userDao.findById(userId);

		UserIdentity userIdentity = new UserIdentity();
		userIdentity.setUserId(userDO.getUserId());
		userIdentity.setUserType(UserType.valueOf(userDO.getUserType()));
		userIdentity.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
		PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
		String accountNo = account.getData().getAccountNo();
		Double[] accountBacance = { 0.00, 0.00, 0.00 };
		if (accountNo != null && !"".equals(accountNo)) {
			accountBacance = this.queryBalanceDetail(accountNo);
		}
		return new BigDecimal(accountBacance[3]+"");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public Double[] queryBalanceDetail(String accountNo) {
        StringBuffer logBuffer = new StringBuffer("st[");
        //发送请求,获取数据
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("xml_data_element", "RespData");
        paramsMap.put("biz_type", "QueryBalance");
        paramsMap.put("PlaCustId", accountNo);
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        Double[] dou = { 0.00, 0.00, 0.00, 0.00, 0.00, 0.00};
        if (resultMap != null) {
        	String jsonStr = resultMap.get("respData");
        		try {
        			int accountTypeCount = jsonStr.length() - jsonStr.replace("cap_typ", "").length();
        			if(accountTypeCount>"cap_typ".length()){
        				List<Map> mapList = JSON.parseArray(jsonStr, Map.class);
        				for (int i=0,length=mapList.size();i<length;i++) {
        					String cap_typ = mapList.get(i).get("cap_typ").toString();
        					if("1".equals(cap_typ)){//投资户
        						dou[0] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("AvlBal"))));//投资户
        						dou[1] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("FrzBal"))));//投资户
        						dou[2] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("AcctBal"))));//投资户
        					}else if("2".equals(cap_typ)){//融资户
        						dou[3] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("AvlBal"))));//融资户
        						dou[4] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("FrzBal"))));//融资户
        						dou[5] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("AcctBal"))));//融资户
        					}
        				}
        			}else{
        				Map map=JSON.parseObject(jsonStr, Map.class);
        				String cap_typ = map.get("cap_typ").toString();
        				if("1".equals(cap_typ)){//投资户
        					dou[0] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(map.get("AvlBal"))));//投资户
        					dou[1] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(map.get("FrzBal"))));//投资户
        					dou[2] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(map.get("AcctBal"))));//投资户
        				}else if("2".equals(cap_typ)){//融资户
        					dou[3] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(map.get("AvlBal"))));//融资户
        					dou[4] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(map.get("FrzBal"))));//融资户
        					dou[5] = Double.valueOf(FormatHelper.changeF2Y(String.valueOf(map.get("AcctBal"))));//融资户
        				}
        			}
        		} catch (NumberFormatException e) {
        			logger.info("查询余额分转元失败  : " + e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					logger.info("余额查询失败 : " + e.getMessage());
					e.printStackTrace();
				}
        }
        logBuffer.append(" 投资户余额:").append(dou[0]).append("|").append(dou[1]).append("|").append(dou[2]).append("  融资户余额：").append(dou[3]).append("|").append(dou[4]).append("|").append(dou[5]).append("]ed");
        logger.info("余额查询  : " + logBuffer);
        return dou;
    }

    
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public Double[] queryBalance(String accountNo, String PlatformType) {
        StringBuffer logBuffer = new StringBuffer("st[");
        Double AcctBal = 0d;//账面总余额
        Double AvlBal = 0d;//可用金额
        Double FrzBal = 0d;//冻结金额
        int investCapTyp = 0;//投资户
        int loanCapTyp = 0;//融资户
        //发送请求,获取数据
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("xml_data_element", "RespData");
        paramsMap.put("biz_type", "QueryBalance");
        paramsMap.put("PlaCustId", accountNo);
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        if (resultMap != null) {
        	try {
				String jsonStr = resultMap.get("respData");
				int accountTypeCount = jsonStr.length() - jsonStr.replace("cap_typ", "").length();
				if(accountTypeCount>"cap_typ".length()){
					int captyp = 0;
					List<Map> mapList = JSON.parseArray(jsonStr, Map.class);
					for (int i=0,length=mapList.size();i<length;i++) {
						try {
							AvlBal+=Double.valueOf(FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("AvlBal"))));
							FrzBal+=Double.valueOf(FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("FrzBal"))));
							AcctBal+=Double.valueOf(FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("AcctBal"))));
							captyp=Integer.valueOf(String.valueOf(mapList.get(i).get("cap_typ")));
							if(AccountCategory.INVESTACCOUNT.getType()==captyp){
								investCapTyp = captyp;
							}else if(AccountCategory.LOANACCOUNT.getType()==captyp){
								loanCapTyp = captyp;
							}
						} catch (NumberFormatException e) {
							logger.info("查询余额分转元失败 : " + e.getMessage());
						} catch (Exception e) {
							logger.info("余额查询失败 : " + e.getMessage());
							e.printStackTrace();
						}
						
					}
				}else{
					Map map=JSON.parseObject(jsonStr, Map.class);
					try {
						AvlBal+=Double.valueOf(FormatHelper.changeF2Y(String.valueOf(map.get("AvlBal"))));
						FrzBal+=Double.valueOf(FormatHelper.changeF2Y(String.valueOf(map.get("FrzBal"))));
						AcctBal+=Double.valueOf(FormatHelper.changeF2Y(String.valueOf(map.get("AcctBal"))));
						int captyp=Integer.valueOf(String.valueOf(map.get("cap_typ")));
						if(AccountCategory.INVESTACCOUNT.getType()==captyp){
							investCapTyp = captyp;
						}else if(AccountCategory.LOANACCOUNT.getType()==captyp){
							loanCapTyp = captyp;
						}
					} catch (NumberFormatException e) {
						logger.info("查询余额分转元失败 : " + e.getMessage());
					}
				}
			} catch (Exception e) {
				logger.info("余额查询失败 : " + e.getMessage());
				e.printStackTrace();
			}
        }
        Double[] dou = new Double[3];
        dou[0] = AcctBal;
        dou[1] = AvlBal;
        dou[2] = FrzBal;
        logBuffer.append(" 余额:").append(dou[0]).append("|").append(dou[1]).append("|").append(dou[2]).append("]ed");
        logger.info("余额查询  : " + logBuffer);
        /***
         * 余额中有投资户的，同步本地开户记录，融资户同样，企业用户不同步投资户
         */
        SysConfigDO configDO = configDao.findByConfigKey(BalanceSynAccount.QUERYBALANCE_SYNCH_ACCOUNT.value);
        if(null != configDO && BalanceSynAccount.BALANCE_SYNCHACCOUNTON.value.equals(configDO.getConfValue())){
        	try {
        		if(AccountCategory.INVESTACCOUNT.getType()==investCapTyp){//投资户已开户
        			//查询本地投资开户记录是否存在
        			AccountInfoDO investAccount = accountDao.findByAccountNoAndType(accountNo, investCapTyp);
        			AccountInfoDO oldAccount = accountDao.findByAccountNo(accountNo);
        			if(null == investAccount && null != oldAccount && UserType.ENTERPRISE.type!=oldAccount.getAccountUserType()){
        				investAccount = new AccountInfoDO();
        				investAccount.setAccountUserId(oldAccount.getAccountUserId());
        				investAccount.setAccountUserName(oldAccount.getAccountUserName());
        				investAccount.setAccountLegalName(oldAccount.getAccountLegalName());
        				investAccount.setAccountNo(accountNo);
        				investAccount.setAccountUserCard(oldAccount.getAccountUserCard());
        				investAccount.setAccountBankName(oldAccount.getAccountBankName());
        				investAccount.setAccountUserAccount(oldAccount.getAccountUserAccount());
        				investAccount.setAccountUserEmail(oldAccount.getAccountUserEmail());
        				investAccount.setAccountUserPhone(oldAccount.getAccountUserPhone());
        				investAccount.setAccountMark("余额查询同步");
        				investAccount.setAccountUserType(oldAccount.getAccountUserType());
        				investAccount.setAccountKind(AccountInfoDO.KIND_BOHAI);
        				investAccount.setAccountCategory(investCapTyp);
        				int r = accountDao.insert(investAccount);
        				if(r>0){
        					logger.info("-----------------------余额查询同步投资户本地开户记录成功--------------------------");
        				}
        			}
        		}
        		if(AccountCategory.LOANACCOUNT.getType()==loanCapTyp){//融资户已开户
        			//查询本地融资开户记录是否存在
        			AccountInfoDO loanAccount = accountDao.findByAccountNoAndType(accountNo, loanCapTyp);
        			AccountInfoDO oldAccount = accountDao.findByAccountNo(accountNo);
        			if(null == loanAccount && null != oldAccount){
        				loanAccount = new AccountInfoDO();
        				loanAccount.setAccountUserId(oldAccount.getAccountUserId());
        				loanAccount.setAccountUserName(oldAccount.getAccountUserName());
        				loanAccount.setAccountLegalName(oldAccount.getAccountLegalName());
        				loanAccount.setAccountNo(accountNo);
        				loanAccount.setAccountUserCard(oldAccount.getAccountUserCard());
        				loanAccount.setAccountBankName(oldAccount.getAccountBankName());
        				loanAccount.setAccountUserAccount(oldAccount.getAccountUserAccount());
        				loanAccount.setAccountUserEmail(oldAccount.getAccountUserEmail());
        				loanAccount.setAccountUserPhone(oldAccount.getAccountUserPhone());
        				loanAccount.setAccountMark("余额查询同步");
        				loanAccount.setAccountUserType(oldAccount.getAccountUserType());
        				loanAccount.setAccountKind(AccountInfoDO.KIND_BOHAI);
        				loanAccount.setAccountCategory(loanCapTyp);
        				int r = accountDao.insert(loanAccount);
        				if(r>0){
        					logger.info("-----------------------余额查询同步融资户本地开户记录成功--------------------------");
        				}
        			}
        		}
        	} catch (Exception e) {
        		logger.error("修改本地开户记录异常");
        		e.printStackTrace();
        	}
        }
        return dou;
    }
    /**
     * 后台用户商户余额查询接口
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public Map<String, String> queryPlatBalance(String accountNo) {
        //发送请求,获取数据
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("xml_data_element", "rs");
        paramsMap.put("biz_type", "QueryMerchantAccts");
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        return resultMap;
	}
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Map> openAccent(Map params) {
        PlainResult<Map> returnData = new PlainResult<Map>();
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
        String agent = String.valueOf(params.get("Agent"));
        String source = String.valueOf(params.get("source"));//开户操作的来源
        if("wx".equals(agent) || "app".equals(agent)){
        	paramsMap.put("Transid", "CBHBNetLoanRegister");
        	paramsMap.put("MobileNo", String.valueOf(params.get("MobileNo")));
        	if("app".equals(agent)){
        		paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("appOpenAccountNotifyUrl"));
        	}else{
        		paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("openAccountNotifyUrl"));
        	}
        	paramsMap.put("FrontUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("registerReturnUrl"));
        	paramsMap.put("IdentType", "");
        	paramsMap.put("IdentNo", String.valueOf(null!=params.get("IdentNo")?params.get("IdentNo"):""));
        	paramsMap.put("UsrName", String.valueOf(null!=params.get("UsrName")?params.get("UsrName"):""));
            paramsMap.put("UsrFlag", "0");
            paramsMap.put("OpenBankId", String.valueOf(null!=params.get("OpenBankId")?params.get("OpenBankId"):""));
            paramsMap.put("OpenAcctId", String.valueOf(null!=params.get("OpenAcctId")?params.get("OpenAcctId"):""));
            paramsMap.put("MerPriv", String.valueOf(null!=params.get("MerPriv")?params.get("MerPriv"):""));
            paramsMap.put("TransTyp", String.valueOf(null!=params.get("TransTyp")?params.get("TransTyp"):""));
            resultMap = ExchangeDataUtils.getMobileSubmitData(paramsMap);
        }else{
        	paramsMap.put("biz_type", "RealNameWeb");
        	paramsMap.put("OpenType", "1");
        	paramsMap.put("IdentType", "00");
        	paramsMap.put("IdentNo", String.valueOf(null!=params.get("IdentNo")?params.get("IdentNo"):""));
        	paramsMap.put("UsrName", String.valueOf(null!=params.get("UsrName")?params.get("UsrName"):""));
        	paramsMap.put("MobileNo", String.valueOf(params.get("MobileNo")));
        	paramsMap.put("OpenBankId", String.valueOf(null!=params.get("OpenBankId")?params.get("OpenBankId"):""));
        	paramsMap.put("OpenAcctId", String.valueOf(null!=params.get("OpenAcctId")?params.get("OpenAcctId"):""));
        	if("register".equals(source)){//从注册开户的操作
        		paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("regOpenAccounReturnUrl"));
        	}else{
        		paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("registerReturnUrl"));
        	}
        	paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("openAccountNotifyUrl"));
        	paramsMap.put("TransTyp", String.valueOf(null!=params.get("TransTyp")?params.get("TransTyp"):""));
        	paramsMap.put("MerPriv", String.valueOf(null!=params.get("MerPriv")?params.get("MerPriv"):""));
        	resultMap = ExchangeDataUtils.getSubmitData(paramsMap);
        }
        returnData.setSuccess(true);
        returnData.setData(resultMap);
        return returnData;

    }
    
    /**
     * 企业开户
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Map> openChargeAccent(Map params) {
        PlainResult<Map> returnData = new PlainResult<Map>();
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
        String agent = String.valueOf(params.get("Agent"));
        int accountUserTyp=2;
        if("wx".equals(agent) || "app".equals(agent)){
        	paramsMap.put("Transid", "CBHBNetLoanRegisterPublic");
            paramsMap.put("TxnTyp", String.valueOf(params.get("TxnTyp")));
            paramsMap.put("AccountNo", String.valueOf(params.get("AccountNo")));
            paramsMap.put("AccountName", String.valueOf(params.get("AccountName")));
            paramsMap.put("AccountBk", String.valueOf(params.get("AccountBk")));
            if("app".equals(agent)){
        		paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("appOpenChargeAccountNotifyUrl"));
        	}else{
        		paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("openChargeAccountNotifyUrl"));
        	}
        	paramsMap.put("FrontUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("openChargeAccountReturnUrl"));
            paramsMap.put("AccountTyp", "1");
            resultMap = ExchangeDataUtils.getMobileSubmitData(paramsMap);
        }else{
        	paramsMap.put("biz_type", "OpenChargeAccountWeb");
            paramsMap.put("TxnTyp", String.valueOf(params.get("TxnTyp")));
            if("2".equals(String.valueOf(params.get("AccountTyp")))){
            	 paramsMap.put("AccountTyp", "2");
            	 accountUserTyp=3;
            }else{
            	paramsMap.put("AccountTyp", "1");
            }
            paramsMap.put("AccountNo", String.valueOf(params.get("AccountNo")));
            paramsMap.put("AccountName", String.valueOf(params.get("AccountName")));
            paramsMap.put("AccountBk", String.valueOf(params.get("AccountBk")));
            paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("openChargeAccountReturnUrl"));
            paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("openChargeAccountNotifyUrl"));
            resultMap = ExchangeDataUtils.getSubmitData(paramsMap);
        }
        
        
        returnData.setSuccess(true);
        returnData.setData(resultMap);

        String userId=params.get("userId").toString();
        String MerBillNo =resultMap.get("MerBillNo");
        if("1".equals(String.valueOf(params.get("TxnTyp")))){
            //插入账户
    		AccountInfoDO account = new AccountInfoDO();
    		account.setAccountUserId(Integer.valueOf(userId));
    		account.setAccountUserType(accountUserTyp);
    		account.setAccountKind(AccountInfoDO.KIND_BOHAI);
    		PageResult<AccountInfoDO> pageResult = accountInfoService.queryByAccount(account, new PageCondition());
			if(pageResult.getDataSize()==0){
				account.setAccountUserAccount(String.valueOf(params.get("AccountNo")));
	    		account.setAccountUserAccountName(String.valueOf(params.get("AccountName")));
	    		account.setAccountUpdateName(String.valueOf(params.get("AccountName")));
	    		account.setAccountUserAccountBk(String.valueOf(params.get("AccountBk")));
	    		account.setAccountMark(MerBillNo);
	    		if("2".equals(String.valueOf(params.get("AccountTyp")))){//1-普通对公户 2-担保户 
	    			account.setAccountUserType(3);
	    			account.setAccountCategory(AccountCategory.GUARANTEEACCOUNT.getType());
	            }else{
	            	account.setAccountUserType(2);
	            	account.setAccountCategory(AccountCategory.LOANACCOUNT.getType());//机构账户默认是融资户
	            }
	    		account.setAccountState(0);
	    		account.setAccountUserAccount(String.valueOf(params.get("AccountNo")));
	    		accountDao.insert(account);
			}else{
				account=pageResult.getData().get(0);
				account.setAccountUserAccount(String.valueOf(params.get("AccountNo")));
	    		account.setAccountUserAccountName(String.valueOf(params.get("AccountName")));
	    		account.setAccountUpdateName(String.valueOf(params.get("AccountName")));
	    		account.setAccountUserAccountBk(String.valueOf(params.get("AccountBk")));
	    		account.setAccountMark(MerBillNo);
	    		if("2".equals(String.valueOf(params.get("AccountTyp")))){
	    			account.setAccountUserType(3);
	    			account.setAccountCategory(AccountCategory.GUARANTEEACCOUNT.getType());
	            }else{
	            	account.setAccountUserType(2);
	            	account.setAccountCategory(AccountCategory.LOANACCOUNT.getType());//机构账户默认是融资户
	            }
	    		account.setAccountState(0);
	    		account.setAccountUserAccount(String.valueOf(params.get("AccountNo")));
	    		accountInfoService.updateByAccountId(account);
			}
        }else{
        	//修改账户不需要像新增账户那样提前更新数据库，因为本来就已经有数据了才要修改，最后的更新动作在回调里完成
        	AccountInfoDO account = new AccountInfoDO();
        	account.setAccountUserAccount(String.valueOf(params.get("AccountNo")));
			PageResult<AccountInfoDO> pageResult = accountInfoService.queryByAccount(account, new PageCondition());
			account = pageResult.getData().get(0);
    		account.setAccountUpdateName(String.valueOf(params.get("AccountName")));
			account.setAccountMark(MerBillNo);
			account.setAccountState(2); //修改中
			accountInfoService.updateByAccountId(account);
        	
        }
        return returnData;
    }
    /**
     * 企业开户结果查询
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public Map<String, String> queryChargeAccountResult(String accountUserAccount, String mark) {
    	Map<String, String> chargeAccountMap = new HashMap<String, String>();
        StringBuffer logBuffer = new StringBuffer("st[");
        //发送请求,获取数据
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("xml_data_element", "rs");
        paramsMap.put("biz_type", "QueryChargeAccountResult");
        paramsMap.put("AccountTyp", "1");
        paramsMap.put("AccountNo", accountUserAccount);
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        String accountNo=null;
        String RespDesc=null;
        if (resultMap != null) {
        	String RespCode=resultMap.get("RespCode").toString();
        	RespDesc=resultMap.get("RespDesc").toString();
			AccountInfoDO account = new AccountInfoDO();
			account= accountInfoService.queryByAccountMark(mark);
			Integer accountState = account.getAccountState();
			if("000000".equals(RespCode)){
        		String ChargeAccount =resultMap.get("ChargeAccount").toString();
        		String AccountName =resultMap.get("AccountName").toString();
        		String PlaCustId =resultMap.get("PlaCustId").toString();
        		String RealNameFlg =resultMap.get("RealNameFlg").toString();
        		String AccountBk =resultMap.get("AccountBk").toString();
        		String ChargeAmt ="";
        		if(RealNameFlg.equals("01")){
            		ChargeAmt =FormatHelper.changeF2Y(resultMap.get("ChargeAmt").toString());
        		}		
        		//插入账户
				account.setAccountUserChargeAccount(ChargeAccount);
				account.setAccountUserChargeName(AccountName);
				account.setAccountUserAccountName(account.getAccountUpdateName());
				account.setAccountNo(PlaCustId);
				account.setAccountUserAccountBk(AccountBk);
				account.setAccountState(1);
				accountInfoService.updateByAccountId(account);
				
				User user= userService.findEntityById(account.getAccountUserId()).getData();
				int userType = user.getUserType().getType();
				//将用户状态改为已开户
				userService.modifyUserBusinessState(account.getAccountUserId(), user.getUserType(), UserBusinessState.ACCOUNT_OPENED);
				if(accountState == 0){
					//开户送积分
	                userService.modifyUserScore(user.getUserId(), ScoreType.REALNAME_SCORE, null);
					
				}
				accountNo = PlaCustId;
				chargeAccountMap.put("RealNameFlg", RealNameFlg);
				chargeAccountMap.put("ChargeAmt",  ChargeAmt);
        	}else{
        		if(accountState == 0){
            		accountInfoService.deleteAccountById(account.getAccountId());
				}else if(accountState == 2){
					account.setAccountState(1);
					accountInfoService.updateByAccountId(account);
				}
        	}
        }
        logBuffer.append(" 企业开户结果:").append(RespDesc).append("]ed");
        logger.info("企业开户结果查询 : " + logBuffer);
        chargeAccountMap.put("accountNo", accountNo);
        return chargeAccountMap;
    }
    
    
    
    /**
     * 但保户开户结果查询
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public Map<String, String> queryGuarAccountResult(String accountUserAccount, String mark) {
    	Map<String, String> chargeAccountMap = new HashMap<String, String>();
        StringBuffer logBuffer = new StringBuffer("st[");
        //发送请求,获取数据
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("xml_data_element", "rs");
        paramsMap.put("biz_type", "QueryChargeAccountResult");
        paramsMap.put("AccountTyp", "2");
        paramsMap.put("AccountNo", accountUserAccount);
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
//        String accountNo=null;
        String RespDesc=null;
        if (resultMap != null) {
        	String RespCode=resultMap.get("RespCode").toString();
        	RespDesc=resultMap.get("RespDesc").toString();
			AccountInfoDO account = new AccountInfoDO();
			account= accountInfoService.queryByAccountMark(mark);
			Integer accountState = account.getAccountState();
			if("000000".equals(RespCode)){
        		String ChargeAccount =resultMap.get("ChargeAccount").toString();
        		String AccountName =resultMap.get("AccountName").toString();
        		String PlaCustId =resultMap.get("PlaCustId").toString();
        		String RealNameFlg =resultMap.get("RealNameFlg").toString();
        		String AccountBk =resultMap.get("AccountBk").toString();
        		String ChargeAmt ="";
        		if(RealNameFlg.equals("01")){
            		ChargeAmt =FormatHelper.changeF2Y(resultMap.get("ChargeAmt").toString());
        		}		
        		//插入账户
				account.setAccountUserChargeAccount(ChargeAccount);
				account.setAccountUserChargeName(AccountName);
				account.setAccountUserAccountName(account.getAccountUpdateName());
				account.setAccountNo(PlaCustId);
				account.setAccountUserAccountBk(AccountBk);
				account.setAccountState(1);
				account.setAccountUserType(UserType.PARTNER.type);
				account.setAccountKind(AccountInfoDO.KIND_BOHAI);
				accountInfoService.updateByAccountId(account);
				
//				accountNo = PlaCustId;
				resultMap.put("accountNo", PlaCustId);
//				resultMap.put("RealNameFlg", RealNameFlg);
				resultMap.put("ChargeAmt",  ChargeAmt);
        	}else{
        		if(accountState == 0){
            		accountInfoService.deleteAccountById(account.getAccountId());
				}else if(accountState == 2){
					account.setAccountState(1);
					accountInfoService.updateByAccountId(account);
				}
        	}
        }
        logBuffer.append(" 担保户开户结果:").append(RespDesc).append("]ed");
        logger.info("担保户开户结果查询 : " + logBuffer);
        return resultMap;
    }
    
    /**
     * 销户接口（企业）
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Map> closeAccount(Map params) {
    	PlainResult<Map> returnData = new PlainResult<Map>();
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
        String agent = String.valueOf(params.get("Agent"));
        if("wx".equals(agent) || "app".equals(agent)){
        	paramsMap.put("Transid", "CBHBNetLoanCloseTrs");
            paramsMap.put("PlaCustId", String.valueOf(params.get("PlaCustId")));
        	paramsMap.put("FrontUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("closeAccountReturnUrl"));
            if("app".equals(agent)){
        		paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("appCloseAccountNotifyUrl"));
        	}else{
        		paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("closeAccountNotifyUrl"));
        	}
            paramsMap.put("TransTyp", String.valueOf(params.get("TransTyp")));
            resultMap = ExchangeDataUtils.getMobileSubmitData(paramsMap);
        }else{
        	paramsMap.put("biz_type", "CloseAccount");
            paramsMap.put("PlaCustId", String.valueOf(params.get("PlaCustId")));
            paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("closeAccountReturnUrl"));
            paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("closeAccountNotifyUrl"));
            paramsMap.put("TransTyp", String.valueOf(params.get("TransTyp")));
            resultMap = ExchangeDataUtils.getSubmitData(paramsMap);
        }
        returnData.setSuccess(true);
        returnData.setData(resultMap);
        return returnData;

    }
    
    /**
     * 投资（后台方式）
     */
	@Override
	public PlainResult<Map<String, String>> backInvest(String innerSeqNo,
			List<DealRecordDO> dealRecords) {
		StringBuffer logBuffer = new StringBuffer("st[ 投资:");
		PlainResult<Map<String, String>> returnData = new PlainResult<Map<String, String>>();
		//发送请求,获取数据
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("xml_data_element", "rs");
        paramsMap.put("biz_type", "BackInvest");
        paramsMap.put("MerBillNo", innerSeqNo);
        paramsMap.put("OldMerBillNo", "");
        paramsMap.put("BusiType", "1");
        paramsMap.put("PlaCustId", dealRecords.get(0).getDrPayAccount());
        paramsMap.put("SmsCode", "");
        paramsMap.put("TransAmt", FormatHelper.changeY2F(dealRecords.get(0).getDrMoneyAmount().toString()));
        paramsMap.put("MarketAmt", "");
        paramsMap.put("BorrowId", dealRecords.get(0).getDrBusinessId().toString());
        paramsMap.put("MerPriv", "");
        paramsMap.put("PayPassWord", "");
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        returnData.setData(resultMap);
        return returnData;
	}
	/**
     * 个人用户信息查询
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public Map<String, String>  queryUserInf (String userPhone) {
    	StringBuffer logBuffer = new StringBuffer("st[ 个人用户信息:");
    	UserDO userDO = userDao.findByPhone(userPhone);
        //发送请求,获取数据
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("xml_data_element", "rs");
        paramsMap.put("biz_type", "QueryUserInf");
        paramsMap.put("PlaCustId", "");
        paramsMap.put("MobileNo", userPhone);
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        if (resultMap != null && "000000".equals(resultMap.get("RespCode"))) {
        	String jsonStr = resultMap.get("respData");
        	String PlaCustId = resultMap.get("PlaCustId");
        	String IdentNo = resultMap.get("IdentNo");
        	String realName ="";
        	String mobile ="";
        	logBuffer.append(IdentNo).append(";").append(PlaCustId).append(";");
        	if(jsonStr.indexOf("},")<0){
        		Map map = JSON.parseObject(jsonStr, Map.class);
            	if(null != map && !map.isEmpty()){
            		    BankInfoDO bank = new BankInfoDO();
            		    realName = map.get("usr_nm").toString();
            		    mobile = map.get("mbl_no").toString();
            		    String capType = map.get("cap_typ").toString();// 账户类型(1 投资 2 融资)
            			//插入账户
            			AccountInfoDO account = new AccountInfoDO();
            			account.setAccountUserId(userDO.getUserId());
            			account.setAccountUserName(userDO.getUserName());
            			account.setAccountLegalName(realName);
            			account.setAccountNo(PlaCustId);
            			account.setAccountUserCard(IdentNo);
            			account.setAccountUserPhone(mobile);
            			account.setAccountMark("个人用户信息查询");
            			account.setAccountKind(AccountInfoDO.KIND_BOHAI);
            			account.setAccountUserType(UserType.PERSONAL.type);
            			account.setAccountCategory(Integer.valueOf(capType));
            			account.setAccountState(1);
            			accountInfoService.openAccount(account);
            			if("1".equals(capType)){
            				resultMap.put("INVESTACCOUNT", PlaCustId);
            			}else if("2".equals(capType)){
            				resultMap.put("LOANACCOUNT", PlaCustId);
            			}
            			logBuffer.append(map.get("cap_typ")).append(";").append(capType).append(";").append(map.get("cap_crd_no").toString()).append(";");
            	}
        	}else{
        		List<Map> mapList = JSON.parseArray(jsonStr, Map.class);
            	if(null != mapList && !mapList.isEmpty()){
            		String capType = "";
            		for (int i=0,length=mapList.size();i<length;i++) {
            			realName = mapList.get(i).get("usr_nm").toString();
            			mobile = mapList.get(i).get("mbl_no").toString();
            			capType = mapList.get(i).get("cap_typ").toString();// 账户类型(1 投资 2 融资)
            			//插入账户
            			AccountInfoDO account = new AccountInfoDO();
            			account.setAccountUserId(userDO.getUserId());
            			account.setAccountUserName(userDO.getUserName());
            			account.setAccountLegalName(realName);
            			account.setAccountNo(PlaCustId);
            			account.setAccountUserCard(IdentNo);
            			account.setAccountUserPhone(mobile);
            			account.setAccountMark("个人用户信息查询");
            			account.setAccountKind(AccountInfoDO.KIND_BOHAI);
            			account.setAccountUserType(UserType.PERSONAL.type);
            			account.setAccountCategory(Integer.valueOf(capType));
            			account.setAccountState(1);
            			accountInfoService.openAccount(account);
            			if("1".equals(capType)){
            				resultMap.put("INVESTACCOUNT", PlaCustId);
            			}else if("2".equals(capType)){
            				resultMap.put("LOANACCOUNT", PlaCustId);
            			}
            			logBuffer.append(capType).append(";").append(mapList.get(i).get("cap_corg").toString()).append(";").append(mapList.get(i).get("cap_crd_no").toString()).append(";");
            		}
            	}
        	}
        	//回填实名认证信息
			User user= UserConverter.toUser(userDO);
			user.setUserDocType("身份证");
			user.setUserRealName(realName);
			user.setUserDocNo(IdentNo);
			user.setUserRealnameIsproven(1);
			userService.modifyInfo(UserConverter.toUserDO(user));
        }
        logBuffer.append("]ed");
        logger.info("个人用户信息查询  : " + logBuffer);
        return resultMap;
    }
    /**
     * 银行卡查询
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public List<BankInfoDO> queryCard(String accountNo, String userPhone) {
    	StringBuffer logBuffer = new StringBuffer("st[ 银行卡:");
    	List<BankInfoDO> banklist=new ArrayList<BankInfoDO>();
        //发送请求,获取数据
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("xml_data_element", "rs");
        paramsMap.put("biz_type", "QueryUserInf");
        paramsMap.put("PlaCustId", accountNo);
        paramsMap.put("MobileNo", "");
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        if (resultMap != null) {
        	String jsonStr = resultMap.get("respData");
        	if(jsonStr.indexOf("},")<0){
        		Map map = JSON.parseObject(jsonStr, Map.class);
            	if(null != map && !map.isEmpty()){
            		    BankInfoDO bank = new BankInfoDO();
            		    bank.setBankAccountType(null!=map.get("cap_typ")? Integer.valueOf(map.get("cap_typ").toString()):AccountCategory.INVESTACCOUNT.getType());
            		    bank.setBankCode(map.get("cap_corg").toString());
            		    bank.setBankNo(map.get("cap_crd_no").toString());
            		    bank.setBindMobile(map.get("mbl_no").toString());
            		    banklist.add(bank);
            			logBuffer.append(map.get("cap_crd_no").toString()).append(";");
            	}else{
            		banklist=null;
            	}
        	}else{
        		List<Map> mapList = JSON.parseArray(jsonStr, Map.class);
            	if(null != mapList && !mapList.isEmpty()){
            		for (int i=0,length=mapList.size();i<length;i++) {
            			BankInfoDO bank = new BankInfoDO();
            			bank.setBankAccountType(null!=mapList.get(i).get("cap_typ")? Integer.valueOf(mapList.get(i).get("cap_typ").toString()):AccountCategory.INVESTACCOUNT.getType());
            		    bank.setBankCode(mapList.get(i).get("cap_corg").toString());
            		    bank.setBankNo(mapList.get(i).get("cap_crd_no").toString());
            		    bank.setBindMobile(mapList.get(i).get("mbl_no").toString());
            		    banklist.add(bank);
            			logBuffer.append(mapList.get(i).get("cap_crd_no").toString()).append(";");
            		}
            	}else{
            		banklist=null;
            	}
        	}
        	
        }
        logBuffer.append("]ed");
        logger.info("银行卡查询  : " + logBuffer);
        return banklist;
    }
    
    /**
     * 更换银行卡接口
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Map> bindCard(Map params) {
        PlainResult<Map> returnData = new PlainResult<Map>();
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
        String agent = String.valueOf(params.get("Agent"));
        if("wx".equals(agent) || "app".equals(agent)){
	        paramsMap.put("Transid", "CBHBNetLoanBindCardMessage");
	        paramsMap.put("PlaCustId", String.valueOf(params.get("PlaCustId")));
	        if("app".equals(agent)){
	        	paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("appBindCardNotifyUrl"));
	        }else{
	        	paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("bindCardNotifyUrl"));
	        }
	    	paramsMap.put("FrontUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("bindCardReturnUrl"));
	        paramsMap.put("MerPriv", String.valueOf(null!=params.get("MerPriv")?params.get("MerPriv"):""));
	        paramsMap.put("TransTyp", String.valueOf(params.get("TransTyp")));
	        resultMap = ExchangeDataUtils.getMobileSubmitData(paramsMap);
        }else{
	    	paramsMap.put("biz_type", "BindCardWeb");
	        paramsMap.put("PlaCustId", String.valueOf(params.get("PlaCustId")));
	        paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("bindCardReturnUrl"));
	        paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("bindCardNotifyUrl"));
	        paramsMap.put("TransTyp", String.valueOf(params.get("TransTyp")));
	        paramsMap.put("MerPriv", String.valueOf(params.get("MerPriv")));
	        resultMap = ExchangeDataUtils.getSubmitData(paramsMap);	
        }
        returnData.setSuccess(true);
        returnData.setData(resultMap);
        return returnData;

    }
    
    /**
     * 更换手机号码
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Map> changPhone(Map<String, String> params) {
        PlainResult<Map> returnData = new PlainResult<Map>();
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
        String agent = String.valueOf(params.get("Agent"));
        if("wx".equals(agent) || "app".equals(agent)){
	    	paramsMap.put("Transid", "CBHBNetLoanUpdatePhone");
	        paramsMap.put("PlaCustId", String.valueOf(params.get("PlaCustId")));
	        paramsMap.put("MobileNo", String.valueOf(params.get("MobileNo")));
	        paramsMap.put("NewMobileNo", String.valueOf(params.get("NewMobileNo")));
	        paramsMap.put("UsrName", String.valueOf(params.get("UsrName")));
	        paramsMap.put("IdentType", String.valueOf(params.get("IdentType")));
	        paramsMap.put("IdentNo", String.valueOf(params.get("IdentNo")));
	        if("wx".equals(agent)){
	            paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("changePhoneNotifyUrl"));
		   	}else{
		        paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("appChangePhoneNotifyUrl"));
		   	}
	    	paramsMap.put("FrontUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("changePhoneReturnUrl"));
	        paramsMap.put("TransTyp", String.valueOf(params.get("TransTyp")));
	        resultMap = ExchangeDataUtils.getMobileSubmitData(paramsMap);
        }else{
	    	paramsMap.put("biz_type", "BindMobileNo");
	        paramsMap.put("PlaCustId", String.valueOf(params.get("PlaCustId")));
	        paramsMap.put("MobileNo", String.valueOf(params.get("MobileNo")));
	        paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("changePhoneReturnUrl"));
	        paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("changePhoneNotifyUrl"));
	        paramsMap.put("TransTyp", String.valueOf(params.get("TransTyp")));
	        paramsMap.put("MerPriv", String.valueOf(params.get("MerPriv")));
	        resultMap = ExchangeDataUtils.getSubmitData(paramsMap);	
        }
        returnData.setSuccess(true);
        returnData.setData(resultMap);
        return returnData;

    }
    /**
     * 修改存管密码
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public PlainResult<Map> changPwd(Map<String, String> param) {
    	 PlainResult<Map> returnData = new PlainResult<Map>();
         Map<String, String> resultMap = new HashMap<String, String>();
         Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
         String agent = String.valueOf(param.get("Agent"));
         if("wx".equals(agent) || "app".equals(agent)){
        	 String type = String.valueOf(param.get("type"));
        	 if("1".equals(type)){
        		 paramsMap.put("Transid", "CBHBNetLoanGetPassword");//1个人找回密码
        	 }else if("2".equals(type)){
        		 paramsMap.put("Transid", "CBHBNetLoanUpdatePassword");//2个人修改密码
        	 }else if("3".equals(type)){
        		 paramsMap.put("Transid", "CBHBNetLoanGetPwdPublic");//3企业找回密码
        	 }else if("4".equals(type)){
        		 paramsMap.put("Transid", "CBHBNetLoanUpdatePwdPublic");//4企业修改密码
        	 }
        	 paramsMap.put("PlaCustId", String.valueOf(param.get("PlaCustId")));
        	 if("1".equals(type)||"2".equals(type)){
        		 paramsMap.put("MobileNo", String.valueOf(param.get("MobileNo")));
        	 }
        	 if("wx".equals(agent)){
                 paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("modifyPwdNotify"));
        	 }else{
                 paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("appModifyPwdNotify"));
        	 }
        	 paramsMap.put("FrontUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("modifyPwdReturnUrl"));
        	 paramsMap.put("TransTyp", String.valueOf(param.get("TransTyp")));
             resultMap = ExchangeDataUtils.getMobileSubmitData(paramsMap);
         }else{
        	 paramsMap.put("biz_type", "BindPass");
             paramsMap.put("PlaCustId", String.valueOf(param.get("PlaCustId")));
             paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("modifyPwdReturnUrl"));
             paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("modifyPwdNotifyUrl"));
             paramsMap.put("TransTyp", param.get("TransTyp"));
             resultMap = ExchangeDataUtils.getSubmitData(paramsMap);
         }
         returnData.setSuccess(true);
         returnData.setData(resultMap);
         return returnData;
	}
    /**
     * 投资接口（页面方式）
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Map> webInvest(Map params) {
        PlainResult<Map> returnData = new PlainResult<Map>();
        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
        paramsMap.put("biz_type", "WebInvest");
        paramsMap.put("MerBillNo", "MerBillNo");
        paramsMap.put("PlaCustId", String.valueOf(params.get("PlaCustId")));
        paramsMap.put("TransAmt", String.valueOf(params.get("TransAmt")));
        paramsMap.put("MarketAmt", String.valueOf(params.get("MarketAmt")));
        paramsMap.put("BorrowId", String.valueOf(params.get("BorrowId")));
        paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("webInvestReturnUrl"));
        paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("webInvestNotifyUrl"));
        //paramsMap.put("MerPriv", String.valueOf(params.get("MerPriv")));
        Map<String, String> resultMap = ExchangeDataUtils.getSubmitData(paramsMap);
        returnData.setSuccess(true);
        returnData.setData(resultMap);
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
      //发送请求,获取数据
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("biz_type", "FileRelease");
        paramsMap.put("MerBillNo", ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16));
        paramsMap.put("BorrowId", "");
        paramsMap.put("FileName", ConfigHelper.getConfig("moneyTransferFileName"));
        paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("moneyTransferNotifyUrl"));
        paramsMap.put("MerPriv", "");
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        result.setData(resultMap);
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
    
    
    /**
     * 授权/解授权
     * @param map
     * @return
     */
    @Override
    public Map<String, String> authorize(Map<String, String> map) {
    	Map<String, String> resultMap = new HashMap<String, String>();
    	Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
    	String userId = map.remove("userId");
    	String agent = String.valueOf(map.get("Agent"));
    	String source = String.valueOf(map.get("source"));//授权操作的来源
    	 if("wx".equals(agent) || "app".equals(agent)){
            paramsMap.put("Transid", "CBHBNetLoanAuthTrs");
            paramsMap.put("PlaCustId", map.get("PlaCustId"));
            paramsMap.put("TxnTyp", map.get("TxnTyp"));//1、授权 2、解授权
         	paramsMap.put("FrontUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("authorizeReturnURL"));
            if("app".equals(agent)){
         		paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("appAuthorizeNotifyURL"));
         	}else{
         		paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("authorizeNotifyURL"));
         	}
            paramsMap.put("TransTyp", map.get("TransTyp"));//1、投资户 2、融资户
            resultMap = ExchangeDataUtils.getMobileSubmitData(paramsMap);
    	}else{
            paramsMap.put("biz_type", "AutoInvestAuth");
            paramsMap.put("PlaCustId", map.get("PlaCustId"));
            paramsMap.put("TxnTyp", map.get("TxnTyp"));//1、授权 2、解授权
            if("register".equals(source)){//从注册授权的操作
        		paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("regAuthorizeReturnURL"));
        	}else{
        		paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("authorizeReturnURL"));
        	}
            paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("authorizeNotifyURL"));
            paramsMap.put("TransTyp", map.get("TransTyp"));//1、投资户 2、融资户
            resultMap = ExchangeDataUtils.getSubmitData(paramsMap);
    	}
        //修改用户授权流水号
        UserDO user = new UserDO();
        user.setUserId(Integer.valueOf(userId));
        user.setAuthorizeSeqNo(resultMap.get("MerBillNo"));
        userDao.updateAuthorizeByUserId(user);
        return resultMap;
    }
    
    /**
     * 担保账户 授权/解授权
     * @param map
     * @return
     */
    @Override
    public Map<String, String> guarAuthorize(Map<String, String> map) {
    	Map<String, String> resultMap = new HashMap<String, String>();
    	Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
    	String empId = map.remove("empId");
        paramsMap.put("biz_type", "AutoInvestAuth");
        paramsMap.put("PlaCustId", map.get("PlaCustId"));
        paramsMap.put("TxnTyp", map.get("TxnTyp"));//1、授权 2、解授权
        paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("authorizeReturnURL"));
        paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("authorizeNotifyURL"));
        paramsMap.put("TransTyp", map.get("TransTyp"));//1、投资户 2、融资户
        resultMap = ExchangeDataUtils.getSubmitData(paramsMap);
        //修改用户授权流水号
        EmployeeDO employee = new EmployeeDO();
        employee.setEmpId(Integer.valueOf(empId));
        employee.setAuthorizeSeqNo(resultMap.get("MerBillNo"));
        employeeDao.updateAuthorizeByEmpId(employee);
        return resultMap;
    }
    /**
     * 线下充值账号查询（后台方式）
     * @param map
     * @return
     */
    @Override
    public Map<String, String> queryChargeAccount(Map<String, String> map) {
    	Map<String, String> chargeAccountMap = new HashMap<String, String>();
    	Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
        paramsMap.put("biz_type", "QueryChargeAccount");
        paramsMap.put("AccountNo", map.get("PlaCustId"));
        paramsMap.put("xml_data_element", "RespData");
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        if (resultMap != null) {
        	chargeAccountMap.put("ChargeAccount", resultMap.get("ChargeAccount"));
        	chargeAccountMap.put("AccountName", resultMap.get("AccountName"));
        }
        return chargeAccountMap;
    }
    /**
     * 用户授权信息查询
     * @param map
     * @return
     */
    @Override
    public Map<String, String> queryAuthorizeInfo(Map<String, String> map) {
    	Map<String, String> authInfMap = new HashMap<String, String>();
    	Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
        paramsMap.put("biz_type", "QueryAuthInf");
        paramsMap.put("PlaCustId", map.get("PlaCustId"));
        paramsMap.put("xml_data_element", "RespData");
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        String respDataJsonStr = resultMap.get("respData");
        int respDataCount = respDataJsonStr.length() - respDataJsonStr.replace("auth_typ", "").length();
        String authorizeInvestType="";
        String authorizeInvestStartDate="";
        String authorizeInvestEndDate="";
        String authorizeInvestAmount="0";
		String authorizeFeeType="";
		String authorizeFeeStartDate="";
		String authorizeFeeEndDate="";
		String authorizeFeeAmount="0";
		String authorizeRepayType="";
		String authorizeRepayStartDate="";
		String authorizeRepayEndDate="";
		String authorizeRepayAmount="0";
		if(respDataCount>"auth_typ".length()){
			List <Map> respDataListMap=JSON.parseArray(respDataJsonStr, Map.class);
			for(Map mp : respDataListMap){
				if("11".equals(mp.get("auth_typ").toString())){//11、投标 
					authorizeInvestType=mp.get("auth_typ").toString();
					authorizeInvestStartDate=mp.get("start_dt").toString();
					authorizeInvestEndDate=mp.get("end_dt").toString();
					authorizeInvestAmount=mp.get("auth_amt").toString();
				}else if("59".equals(mp.get("auth_typ").toString())){//59、缴费 
					authorizeFeeType=mp.get("auth_typ").toString();
					authorizeFeeStartDate=mp.get("start_dt").toString();
					authorizeFeeEndDate=mp.get("end_dt").toString();
					authorizeFeeAmount=mp.get("auth_amt").toString();
				}else if("60".equals(mp.get("auth_typ").toString())){//60、还款
					authorizeRepayType=mp.get("auth_typ").toString();
					authorizeRepayStartDate=mp.get("start_dt").toString();
					authorizeRepayEndDate=mp.get("end_dt").toString();
					authorizeRepayAmount=mp.get("auth_amt").toString();
				}
			}
		}else{
			Map respDataMap=JSON.parseObject(respDataJsonStr, Map.class);
			if("11".equals(respDataMap.get("auth_typ").toString())){//11、投标 
				authorizeInvestType=respDataMap.get("auth_typ").toString();
				authorizeInvestStartDate=respDataMap.get("start_dt").toString();
				authorizeInvestEndDate=respDataMap.get("end_dt").toString();
				authorizeInvestAmount=respDataMap.get("auth_amt").toString();
			}else if("59".equals(respDataMap.get("auth_typ").toString())){//59、缴费 
				authorizeFeeType=respDataMap.get("auth_typ").toString();
				authorizeFeeStartDate=respDataMap.get("start_dt").toString();
				authorizeFeeEndDate=respDataMap.get("end_dt").toString();
				authorizeFeeAmount=respDataMap.get("auth_amt").toString();
			}else if("60".equals(respDataMap.get("auth_typ").toString())){//60、还款
				authorizeRepayType=respDataMap.get("auth_typ").toString();
				authorizeRepayStartDate=respDataMap.get("start_dt").toString();
				authorizeRepayEndDate=respDataMap.get("end_dt").toString();
				authorizeRepayAmount=respDataMap.get("auth_amt").toString();
			}
		}
		authInfMap.put("authorizeInvestType", authorizeInvestType);
		authInfMap.put("authorizeInvestStartDate", authorizeInvestStartDate);
		authInfMap.put("authorizeInvestEndDate", authorizeInvestEndDate);
		authInfMap.put("authorizeInvestAmount", FormatHelper.changeF2Y(authorizeInvestAmount));
		authInfMap.put("authorizeFeeType", authorizeFeeType);
		authInfMap.put("authorizeFeeStartDate", authorizeFeeStartDate);
		authInfMap.put("authorizeFeeEndDate", authorizeFeeEndDate);
		authInfMap.put("authorizeFeeAmount", FormatHelper.changeF2Y(authorizeFeeAmount));
		authInfMap.put("authorizeRepayType", authorizeRepayType);
		authInfMap.put("authorizeRepayStartDate", authorizeRepayStartDate);
		authInfMap.put("authorizeRepayEndDate", authorizeRepayEndDate);
		authInfMap.put("authorizeRepayAmount", FormatHelper.changeF2Y(authorizeRepayAmount));
		logger.info("==================授权信息==================");
		logger.info(authInfMap.toString());
        return authInfMap;
    }
    /**
	 * 红包实时返还接口
	 * @param map
	 * @return
	 */
    @Override
	public Map<String, String> returnRed(Map<String, Object> map) {
		Map<String, String> resultMap = new HashMap<String, String>();
		//发送请求,获取数据
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("biz_type", "ExperBonus");
		paramsMap.put("MerBillNo", ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16));
		paramsMap.put("PlaCustId", map.get("PlaCustId").toString());
		paramsMap.put("TransAmt", FormatHelper.changeY2F(map.get("TransAmt")));
		paramsMap.put("MerFeeAmt", "0");
		paramsMap.put("TransTyp", String.valueOf(null==map.get("TransTyp")?AccountCategory.INVESTACCOUNT.getType():map.get("TransTyp")));
		paramsMap.put("MerPriv", "");
		resultMap = ExchangeDataUtils.submitData(paramsMap);
		return resultMap;
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
        Map<String, String> map = new HashMap<String, String>();
        map.put("transferaudit", params.get("RespDesc"));
        if ("000000".equals(params.get("RespCode"))) {
            result.setSuccess(true);
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
    /**
     * 用户线下充值记录查询
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> queryChargeDetail(Map<String, String> map) {
    	Map<String, Object> chargeDetailResultMap = new HashMap<String, Object>();
    	Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
        paramsMap.put("biz_type", "QueryChargeDetail");
        paramsMap.put("AccountNo", map.get("AccountNo"));
        paramsMap.put("QueryTyp", map.get("QueryTyp"));//1历史记录查询 2当前记录查询
        paramsMap.put("StartDate", StringUtils.isNotEmpty(map.get("StartDate"))?map.get("StartDate"):"");
        paramsMap.put("EndDate", StringUtils.isNotEmpty(map.get("EndDate"))?map.get("EndDate"):""); 
        paramsMap.put("TransId", StringUtils.isNotEmpty(map.get("TransId"))?map.get("TransId"):"0");
        paramsMap.put("PageNo", StringUtils.isNotEmpty(map.get("PageNo"))?map.get("PageNo"):"1");
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        List <RechargeRecordDO> list=new ArrayList<RechargeRecordDO>();
        int totalNum =0;
        String sessionStartDate="";
        String sessionEndDate="";
        if("000000".equals(resultMap.get("RespCode"))){
        	String RespData = resultMap.get("Detail");
        	logger.info("RespData解码前========"+RespData);
    		RespData=FormatHelper.GBKDecodeStr(RespData);
    		logger.info("RespData解码后========"+RespData);
    		String xml=XmlHelper.getRepXml(RespData);
    		logger.info("==================线下充值记录==================");
    		logger.info(xml);
    		String respDataJsonStr = XmlHelper.xmlToJsonStr(xml,"rs");
        	int respDataCount = respDataJsonStr.length() - respDataJsonStr.replace("Acdate", "").length();
        	RechargeRecordDO rechargeRecordDO=null;
        	if(respDataCount>"Acdate".length()){
        		List <Map> respDataListMap=JSON.parseArray(respDataJsonStr, Map.class);
        		for(Map mp : respDataListMap){
        			rechargeRecordDO = new RechargeRecordDO();
        			rechargeRecordDO.setRechargeDate(FormatHelper.formatStr2Date(mp.get("Acdate").toString(), "yyyyMMdd"));
        			rechargeRecordDO.setRechargeSeqNo(mp.get("TransId").toString());
        			rechargeRecordDO.setRechargeAmount(new BigDecimal(FormatHelper.changeF2Y(mp.get("TransAmt").toString())));
        			rechargeRecordDO.setRechargeFeeAmt(new BigDecimal(FormatHelper.changeF2Y(mp.get("FeeAmt").toString())));
        			rechargeRecordDO.setRechargeStateDes("S1".equals(mp.get("TransStat"))?"成功":mp.get("FalRsn").toString());
        			list.add(rechargeRecordDO);
        		}
        	}else{
        		Map mp=JSON.parseObject(respDataJsonStr, Map.class);
        		rechargeRecordDO = new RechargeRecordDO();
    			rechargeRecordDO.setRechargeDate(FormatHelper.formatStr2Date(mp.get("Acdate").toString(), "yyyyMMdd"));
    			rechargeRecordDO.setRechargeSeqNo(mp.get("TransId").toString());
    			rechargeRecordDO.setRechargeAmount(new BigDecimal(FormatHelper.changeF2Y(mp.get("TransAmt").toString())));
    			rechargeRecordDO.setRechargeFeeAmt(new BigDecimal(FormatHelper.changeF2Y(mp.get("FeeAmt").toString())));
    			rechargeRecordDO.setRechargeStateDes("S1".equals(mp.get("TransStat"))?"成功":mp.get("FalRsn").toString());
    			list.add(rechargeRecordDO);
        	}
        	if(null != session.getAttribute(map.get("AccountNo")+"_XXCZJL_StartDate")){
        		sessionStartDate = (String) session.getAttribute(map.get("AccountNo")+"_XXCZJL_StartDate");
        	}
        	if(null != session.getAttribute(map.get("AccountNo")+"_XXCZJL_EndDate")){
        		sessionEndDate = (String) session.getAttribute(map.get("AccountNo")+"_XXCZJL_EndDate");
        	}
        	//同样的查询条件总数用查询第一页时返回的总数，因为接口在查询第一页之后就不返回总数了
        	if(null != session.getAttribute(map.get("AccountNo")+"_XXCZJL_TOTALNUM") && !map.get("PageNo").equals("1")
        			&& sessionStartDate.equals(map.get("StartDate")) && sessionEndDate.equals(map.get("EndDate"))){
        		totalNum = (Integer) session.getAttribute(map.get("AccountNo")+"_XXCZJL_TOTALNUM");
        	}else{
        		totalNum =Integer.valueOf(resultMap.get("TotalNum"));
        		session.setAttribute(map.get("AccountNo")+"_XXCZJL_TOTALNUM", totalNum);
        	}
        	session.setAttribute(map.get("AccountNo")+"_XXCZJL_StartDate", map.get("StartDate"));
        	session.setAttribute(map.get("AccountNo")+"_XXCZJL_EndDate", map.get("EndDate"));
        	chargeDetailResultMap.put("pageCount", resultMap.get("TotalPage"));
        	chargeDetailResultMap.put("recordCount", totalNum);
        }
        chargeDetailResultMap.put("recordList", list);
        return chargeDetailResultMap;
    }
}
