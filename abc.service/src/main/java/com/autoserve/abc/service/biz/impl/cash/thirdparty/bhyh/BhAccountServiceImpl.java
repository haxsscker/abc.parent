package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.dao.dataobject.GovAccountDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.UserIdentityDO;
import com.autoserve.abc.dao.intf.AccountInfoDao;
import com.autoserve.abc.service.biz.convert.AccountConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 用户账户ServiceImpl
 * 
 * @author J.YL 2014年11月17日 下午4:20:48
 */
@Service
public class BhAccountServiceImpl implements AccountInfoService {
    private static final Logger logger = LoggerFactory.getLogger(BhAccountServiceImpl.class);
    @Resource
    private AccountInfoDao      accountDao;
    @Resource
    private UserAccountService  userAccountService;
    @Resource
    private UserService         userService;

    @Override
    public PlainResult<Account> queryByUserId(UserIdentity userIdentity) {
        PlainResult<Account> result = new PlainResult<Account>();
        Integer userId = userIdentity.getUserId();
        UserType userType = userIdentity.getUserType();
        Integer accountCategory = userIdentity.getAccountCategory();
        AccountInfoDO accountDO = null;
        if(null!=accountCategory){
        	accountDO = this.queryByAccountMark(userId, userType.getType(),accountCategory);
        }else{
        	accountDO = this.qureyAccountByUserIdAndUserType(userId, userType.getType());
        }
        if (accountDO == null) {
            accountDO = new AccountInfoDO();
            result.setSuccess(false);
            result.setMessage("该用户未开户");
            result.setCode(CommonResultCode.ERROR_DATA_NOT_EXISTS.getCode());
        }
        
        Account account = AccountConverter.toUserAccount(accountDO);
        result.setData(account);
        return result;
    }
    
    /**
     * 如果accountCategory为空则判断是否两个账户是否都没有开户或都开户了(暂时可能没用)
     * 如果accountCategory为1则判断投资户有没有开户
     * 如果accountCategory为2则判断融资户有没有开户
     * @param UserDO
     * @return
     */
    @Override
    public  BaseResult isOpenAccount(UserDO userDO) {
    	BaseResult result = new BaseResult();
        Integer userId = userDO.getUserId();
        Integer userType = userDO.getUserType();
        Integer accountCategory = userDO.getAccountCategory();
        if(accountCategory==null){
        	AccountInfoDO accountDO1 = accountDao.findByUserId2(userId, userType, AccountCategory.INVESTACCOUNT.getType());
        	AccountInfoDO accountDO2 = accountDao.findByUserId2(userId, userType, AccountCategory.LOANACCOUNT.getType());
        	if((null==accountDO1 || StringUtils.isEmpty(accountDO1.getAccountNo())) 
        			&& (null==accountDO2 || StringUtils.isEmpty(accountDO2.getAccountNo()))){
        		result.setSuccess(false);
                result.setMessage(CommonResultCode.NO_INVEST_LOAN_ACCOUNT.message);
                return result;
        	}
        	if(null==accountDO1 || StringUtils.isEmpty(accountDO1.getAccountNo())){
        		result.setSuccess(false);
                result.setMessage(CommonResultCode.NO_INVEST_ACCOUNT.message);
        	}
        	if(null==accountDO2 || StringUtils.isEmpty(accountDO2.getAccountNo())){
        		result.setSuccess(false);
                result.setMessage(CommonResultCode.NO_LOAN_ACCOUNT.message);
            }
        	if(null!=accountDO1 && StringUtils.isNotEmpty(accountDO1.getAccountNo()) 
        			&& null!=accountDO2 && StringUtils.isNotEmpty(accountDO2.getAccountNo())){
        		result.setSuccess(true);
                result.setMessage(CommonResultCode.INVEST_LOAN_ACCOUNTED.message);
        	}
        }else{
        	AccountInfoDO accountDO = accountDao.findByUserId2(userId, userType, accountCategory);
        	if (null==accountDO || StringUtils.isEmpty(accountDO.getAccountNo())) {
        		result.setSuccess(false);
        		if(AccountCategory.INVESTACCOUNT.getType()==accountCategory){
        			result.setMessage(CommonResultCode.NO_INVEST_ACCOUNT.message);
        		}else if(AccountCategory.LOANACCOUNT.getType()==accountCategory){
        			result.setMessage(CommonResultCode.NO_LOAN_ACCOUNT.message);
        		}
        	}else{
        		result.setMessage(CommonResultCode.ACCOUNTED.message);
        	}
        }
        return result;
    }
    @Override
    public AccountInfoDO getAccountByCategory(UserDO userDO){
    	return accountDao.findByUserId2(userDO.getUserId(), userDO.getUserType(), userDO.getAccountCategory());
    }
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public BaseResult openAccount(AccountInfoDO account) {
        BaseResult result = new BaseResult();
//        BaseResult userAccountResult;
//        String accountNo = account.getAccountNo();
        AccountInfoDO ac = accountDao.findByUserId2(account.getAccountUserId(), account.getAccountUserType(), account.getAccountCategory());
        int flag = 0;
        int flagInsert = 0;
        if (ac != null) {
            //对于已开户的用户账户只允许修改部分信息，开户账户号，用户名称，密码等是不允许修改的
            ac.setAccountBankArea(account.getAccountBankArea());
            ac.setAccountBankBranchName(account.getAccountBankBranchName());
            ac.setAccountBankName(account.getAccountBankName());
            ac.setAccountUserAccount(account.getAccountUserAccount());
            ac.setAccountUserEmail(account.getAccountUserEmail());
            ac.setAccountUserPhone(account.getAccountUserPhone());
            flag = accountDao.updateByUserIdentity(ac);
        } else {
            flag = accountDao.insert(account);
            flagInsert = flag;
        }
        if (flag <= 0) {
            result.setCode(CommonResultCode.ERROR_DB.getCode());
            result.setSuccess(false);
            result.setMessage("账户操作失败");
        }
        if (flagInsert > 0) {
            //创建成功初始化资金操作记录
            /*userAccountResult = userAccountService.createUserAccount(accountNo);
            if (!userAccountResult.isSuccess()) {
                result.setMessage(userAccountResult.getMessage());
            }*/
            //创建成功设置用户已开户
            if (!userService.modifyUserBusinessState(account.getAccountUserId(),
                    UserType.valueOf(account.getAccountUserType()), UserBusinessState.ACCOUNT_OPENED).isSuccess()) {
                logger.warn("设置用户开户状态失败：用户ID={}, 用户类型={}", account.getAccountUserId(), account.getAccountUserType());
            }
        }
        return result;
    }

    /**
     * 暂时没用
     */
    @Override
    public PlainResult<Boolean> bindCard(Account account) {
        PlainResult<Boolean> result = new PlainResult<Boolean>();
        AccountInfoDO accountDO = AccountConverter.toAccountInfoDO(account);
        if (accountDO.getAccountNo() == null || accountDO.getAccountUserAccount() == null) {
            result.setCode(CommonResultCode.ILLEGAL_PARAM.getCode());
            result.setSuccess(false);
            result.setData(false);
            result.setMessage("绑卡参数错误");
            return result;
        }

        int flag = accountDao.updateByAccountNo(accountDO);
        if (flag <= 0) {
            result.setCode(CommonResultCode.FAIL.getCode());
            result.setData(false);
            result.setSuccess(false);
            result.setMessage("绑卡失败");
        }
        return result;
    }
    
    /**
     * 交易状态查询
     */
    @Override
    public Map<String, String> queryTransStatus(String id,String type) {
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("xml_data_element", "RespData");
        paramsMap.put("biz_type", "QueryTransStat");
        paramsMap.put("MerBillNo", id);
        String typeDet="";
        if("CZJL".equals(type)){
        	typeDet="WebRecharge";
        }else if("TXJL".equals(type)){
        	typeDet="Drawings";
        }else if("PTTXJL".equals(type)){
        	typeDet="MercWithdraw";
        }else{
        	typeDet=type;
        }
        paramsMap.put("QueryTransType", typeDet);
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        return resultMap;
    }

    @Override
    public ListResult<Account> queryByUserIds(List<UserIdentity> userIdentities) {
        List<UserIdentityDO> queryList = new ArrayList<UserIdentityDO>();
        for (UserIdentity uid : userIdentities) {
            UserIdentityDO uido = new UserIdentityDO();
            uido.setUserId(uid.getUserId());
            uido.setUserType(uid.getUserType().getType());
            uido.setAccountCategory(uid.getAccountCategory());
            queryList.add(uido);
        }
        List<AccountInfoDO> accounts = accountDao.queryListByUserIdentitis(queryList);
        ListResult<Account> resultList = new ListResult<Account>();
        List<Account> al = new ArrayList<Account>();
        for (AccountInfoDO aid : accounts) {
            Account ac = AccountConverter.toUserAccount(aid);
            al.add(ac);
        }
        resultList.setData(al);
        return resultList;
    }
    
    
    
    /**
     * 查询动态口令
     */
    @Override
    public Map<String, String> querySmsCode(String  MobileNo) {
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("xml_data_element", "RespData");
        paramsMap.put("biz_type", "sendUapMsg");
        paramsMap.put("MerBillNo", ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16));
        paramsMap.put("MobileNo", MobileNo);
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        return resultMap;
    }

    @Override
    public PageResult<GovAccountDO> queryByUserType(UserType type, PageCondition pageCondition, GovAccountDO govAccount) {
        PageResult<GovAccountDO> result = new PageResult<GovAccountDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        govAccount.setAccountUserType(type.getType());

        List<GovAccountDO> queryResult = accountDao.findGovAccount(govAccount, pageCondition);

        Integer count = accountDao.countGovAccount(govAccount);
        result.setData(queryResult);
        result.setTotalCount(count);
        return result;
    }

    @Override
    public ListResult<Account> queryByAccountNos(List<String> accountNos) {
        List<AccountInfoDO> accounts = accountDao.findByAccountNos(accountNos);
        List<Account> accountList = new ArrayList<Account>();
        for (AccountInfoDO aid : accounts) {
            Account ac = AccountConverter.toUserAccount(aid);
            accountList.add(ac);
        }
        ListResult<Account> result = new ListResult<Account>();
        result.setData(accountList);
        return result;
    }

    @Override
    public GovAccountDO queryByAccountId(Integer accountId) {
        GovAccountDO aid = accountDao.findGovAccountById(accountId);
        if (aid == null) {
            aid = new GovAccountDO();
        }
        return aid;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public BaseResult modifyAccountInfo(AccountInfoDO account) {
        BaseResult result = new BaseResult();
        Integer accountId = account.getAccountId();
        if (accountId == null) {
            result.setSuccess(false);
            result.setMessage("更新的账户ID为空");
            return result;
        }
        AccountInfoDO oldAccount = accountDao.findById(accountId);
        if (!oldAccount.getAccountNo().equals(account.getAccountNo())) {
            result.setSuccess(false);
            result.setMessage("不能修改已开户的账户号，本次修改未生效！");
            return result;
        }
        int flag = 0;
        if (account.getAccountId() != null) {
            flag = accountDao.updateByAccountId(account);
        }

        if (flag <= 0) {
            result.setSuccess(false);
            result.setMessage("更新失败");
        }
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public BaseResult addNewOrgAccountInfo(AccountInfoDO account) {
        BaseResult result = new BaseResult();
        int flag = accountDao.insert(account);
        if (flag <= 0) {
            result.setSuccess(false);
            result.setMessage("增加新机构账户失败");
        } else {
            //为新创建的用户初始化资金操作记录
            BaseResult userAccountResult = userAccountService.createUserAccount(account.getAccountNo());
            if (!userAccountResult.isSuccess()) {
                result.setMessage(userAccountResult.getMessage());
            }
        }
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public BaseResult updateOrgAccountInfo(AccountInfoDO account) {
        BaseResult result = new BaseResult();
        int flag = accountDao.updateByUserIdentity(account);
        if (flag <= 0) {
            result.setSuccess(false);
            result.setMessage("跟新机构钱多多标示失败，请联系管理员");
        }
        return result;
    }

    @Override
    public PlainResult<AccountInfoDO> queryByUserIdentity(UserIdentity userIdentity) {
        Integer userId = userIdentity.getUserId();
        Integer userType = userIdentity.getUserType().getType();
        Integer accountCategory = userIdentity.getAccountCategory();
        AccountInfoDO aid = accountDao.findByUserId2(userId, userType , accountCategory);
        if (aid == null) {
            aid = new AccountInfoDO();
        }
        PlainResult<AccountInfoDO> result = new PlainResult<AccountInfoDO>();
        result.setData(aid);
        return result;
    }

    @Override
    public PageResult<AccountInfoDO> queryByAccount(AccountInfoDO accountInfo, PageCondition pageCondition) {
        int count = accountDao.count(accountInfo);
        List<AccountInfoDO> queryData = accountDao.find(accountInfo, pageCondition);
        PageResult<AccountInfoDO> result = new PageResult<AccountInfoDO>(pageCondition);
        result.setData(queryData);
        result.setTotalCount(count);
        return result;
    }

    @Override
    public AccountInfoDO queryByAccountNo(String accountNo) {
        AccountInfoDO accountInfo = accountDao.findByAccountNo(accountNo);
        if (accountInfo == null) {
            accountInfo = new AccountInfoDO();
        }
        return accountInfo;
    }
    
    @Override
    public AccountInfoDO queryByAccountNoAndType(String accountNo,int type) {
        AccountInfoDO accountInfo = accountDao.findByAccountNoAndType(accountNo,type);
        if (accountInfo == null) {
            accountInfo = new AccountInfoDO();
        }
        return accountInfo;
    }

    @Override
    public BaseResult deleteAccountById(Integer accountId) {
        BaseResult result = new BaseResult();
        int flag = accountDao.delete(accountId);
        if (flag <= 0) {
            result.setMessage("删除失败");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public AccountInfoDO queryByAccountMark(int userId, int type,Integer accountCategory) {
    	if(UserType.PLATFORM.type==type){
    		//平台
    		AccountInfoDO accountInfoDo = new AccountInfoDO();
    		accountInfoDo.setAccountUserType(type);
    		return accountDao.find(accountInfoDo, new PageCondition(1, 1)).get(0);
    	}
        return accountDao.findByUserId2(userId, type , accountCategory);
    }

    @Override
    public BaseResult modifyAccountInfo(Account account) {
        BaseResult result = new BaseResult();
        AccountInfoDO oldAccount = new AccountInfoDO();
        oldAccount.setAccountUserId(account.getAccountUserId());
        oldAccount.setAccountUserCard(account.getAccountUserCard());
        oldAccount.setAccountUserEmail(account.getAccountUserEmail());
        oldAccount.setAccountUserPhone(account.getAccountUserPhone());
        int flag = 0;
        flag = accountDao.updateByUserId(oldAccount);
        if (flag <= 0) {
            result.setSuccess(false);
            result.setMessage("更新失败");
        }
        return result;
    }

    @Override
    public AccountInfoDO qureyAccountByUserIdAndUserType(int userId,int userType) {
        return accountDao.findAccountByUserIdAndUserType(userId,userType);
    }

	@Override
	public List<AccountInfoDO> qureyAccountByUserId(AccountInfoDO AccountInfoDO) {
		List<AccountInfoDO> accountInfoDO =accountDao.find(AccountInfoDO, null);
		return accountInfoDO;
	}
	
	@Override
	public AccountInfoDO queryByAccountMark(String mark){
		return accountDao.findByAccountMark(mark);
	}

	@Override
	public BaseResult updateByUserId(AccountInfoDO account) {
		BaseResult result = new BaseResult();
        int flag = accountDao.updateByUserId(account);
        if (flag <= 0) {
            result.setMessage("更新失败");
            result.setSuccess(false);
        }
        return result;
	}
	
    /**
     * 商户账户交易查询
     */
    @Override
    public Map<String, String> queryMerchantTrans() {
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); 
        String date = sdf.format(new Date());
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(new Date());  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        Date date2 = calendar.getTime(); 
        String date1 = sdf.format(date2);
        paramsMap.put("xml_data_element", "RespData");
        paramsMap.put("biz_type", "QueryMerchantTrans");
        paramsMap.put("StartDate", date1);
        paramsMap.put("EndDate", date);
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        return resultMap;
    }

	@Override
	public AccountInfoDO queryByAccountMark(int userId, int type) {
		return accountDao.findByUserId(userId, type);
	}

	@Override
	public PageResult<AccountInfoDO> qureyAccountByRealName(String realName,PageCondition pageCondition) {
		PageResult<AccountInfoDO> result = new PageResult<AccountInfoDO>(pageCondition.getPage(),pageCondition.getPageSize());
		int total = accountDao.countUserNum(realName);
		List<AccountInfoDO> investers = accountDao.qureyAccountByRealName(realName,pageCondition);
		result.setTotalCount(total);
		result.setData(investers);
		return result;
	}

	@Override
	public int updateByAccountId(AccountInfoDO account) {
		return accountDao.updateByAccountId(account);
	}

}
