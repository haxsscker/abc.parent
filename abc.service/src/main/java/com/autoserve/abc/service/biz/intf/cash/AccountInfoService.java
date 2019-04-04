/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.dao.dataobject.GovAccountDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 用户账户Service接口
 * 
 * @author J.YL 2014年11月17日 下午3:36:06
 */
public interface AccountInfoService {

    public BaseResult deleteAccountById(Integer accountId);

    /**
     * 根据批量的userId和用户类型查询批量的用户账户信息。
     * 
     * @param userIds
     * @return
     */
    public ListResult<Account> queryByUserIds(List<UserIdentity> userIdentities);

    /**
     * 查询机构用户的账户信息
     * 
     * @param type
     * @param pageCondition
     * @return
     */
    public PageResult<GovAccountDO> queryByUserType(UserType type, PageCondition pageCondition,
                                                    GovAccountDO govAccountDO);

    /**
     * 根据用户id 和用户类型查询其账户信息
     * 
     * @param userId
     * @return
     */
    public PlainResult<Account> queryByUserId(UserIdentity userIdentity);

    /**
     * 开户,账户已存在则更新，否则创建。<br>
     * 更新时只允许更新部分字段<br>
     * 开户和更新是针对当前登录的用户
     * 
     * @return
     */
    public BaseResult openAccount(AccountInfoDO account);

    /**
     * 支付账户绑定银行卡
     * 
     * @param account
     * @return
     */
    public PlainResult<Boolean> bindCard(Account account);

    /**
     * 通过账号查询用户账户信息
     * 
     * @param accountNos
     * @return
     */
    public ListResult<Account> queryByAccountNos(List<String> accountNos);

    /**
     * 通过AccountId主键查询AccountInfoDO
     * 
     * @param accountId
     * @return
     */
    public GovAccountDO queryByAccountId(Integer accountId);

    /**
     * 更新AccountInfo
     * 
     * @param account
     * @return
     */
    public BaseResult modifyAccountInfo(Account account);

    /**
     * 更新AccountInfo
     * 
     * @param account
     * @return
     */
    public BaseResult modifyAccountInfo(AccountInfoDO account);

    /**
     * 新增机构账户OrgAccount
     * 
     * @param account
     * @return
     */
    public BaseResult addNewOrgAccountInfo(AccountInfoDO account);

    /**
     * 更新机构账户的钱多多标示
     * 
     * @param account
     * @return
     */
    public BaseResult updateOrgAccountInfo(AccountInfoDO account);

    /**
     * 根据用户查询AccountInfo
     * 
     * @param userIdentity
     * @return
     */
    public PlainResult<AccountInfoDO> queryByUserIdentity(UserIdentity userIdentity);

    /**
     * 根据账户查询账户信息
     * 
     * @param account
     * @return
     */
    public PageResult<AccountInfoDO> queryByAccount(AccountInfoDO account, PageCondition pageCondition);

    /**
     * 根据账户号查询
     * 
     * @param accountNo
     * @return
     */
    public AccountInfoDO queryByAccountNo(String accountNo);

    /**
     * 根据账户号和userType查询
     * 
     * @param accountNo
     * @return
     */
    public AccountInfoDO queryByAccountMark(int userId, int type);
    public AccountInfoDO queryByAccountMark(int userId, int type,Integer accountCategory);

    /**
     * 仅根据userId查询账户号
     * @param userId
     * @return
     */
    //public AccountInfoDO qureyAccountByUserId(int userId);
   /**
    * 查询用户
    * @param AccountInfoDO
    * @return
    */
    public List<AccountInfoDO> qureyAccountByUserId(AccountInfoDO AccountInfoDO);

	public PageResult<AccountInfoDO> qureyAccountByRealName(String realName,PageCondition pageCondition);

    /**
     * 查询用户
     * @param mark
     * @return
     */
    AccountInfoDO queryByAccountMark(String mark);

    Map<String, String> queryTransStatus(String id,String type);

	Map<String, String> querySmsCode(String MobileNo);

	public BaseResult updateByUserId(AccountInfoDO account);

	public Map<String, String> queryMerchantTrans();
	/**
	 * 判断是否开户
	 * @param userDO
	 * @return
	 */
	BaseResult isOpenAccount(UserDO userDO);
	/**
	 * 根据用户id及账号类型查询账号
	 * @param userDO
	 * @return
	 */
	AccountInfoDO getAccountByCategory(UserDO userDO);

	AccountInfoDO queryByAccountNoAndType(String accountNo, int type);

	AccountInfoDO qureyAccountByUserIdAndUserType(int userId, int userType);

	public int updateByAccountId(AccountInfoDO account);

}
