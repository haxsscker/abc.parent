/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.dao.dataobject.GovAccountDO;
import com.autoserve.abc.dao.dataobject.UserIdentityDO;

/**
 * 用户资金账户Dao
 * 
 * @author J.YL 2014年11月15日 上午9:23:31
 */
public interface AccountInfoDao extends BaseDao<AccountInfoDO, Integer> {

    /**
     * 根据accountNo更新账户信息
     * 
     * @param accountDO
     * @return
     */
    public int updateByAccountNo(AccountInfoDO accountDO);

    public AccountInfoDO findByUserId(@Param("userId") int userId, @Param("userType") int userType);
    public AccountInfoDO findByAccountMark(String mark);
    //add by 夏同同 20160105
    public AccountInfoDO findByUserId2(@Param("userId") int userId, @Param("userType") int userType, @Param("accountCategory") Integer accountCategory);
    public AccountInfoDO findByUserId2(@Param("userId") int userId, @Param("userType") int userType);
    /**
     * 根据userId查询账户信息
     * @param userId
     * @return
     */
    //public AccountInfoDO findAccountByUserId(@Param("userId") int userId);
    
    public AccountInfoDO findAccountByUserIdAndUserType(@Param("userId") int userId,@Param("userType") int userType);
    
    public AccountInfoDO findByAccountNo(String accountNo);
    
    public AccountInfoDO findByAccountNoAndType(@Param("accountNo") String accountNo,@Param("accountCategory")int accountCategory);

    public List<AccountInfoDO> find(@Param("accountInfoDO") AccountInfoDO accountInfoDo,
                                    @Param("pageCondition") PageCondition pageCondition);

    public List<GovAccountDO> findGovAccount(@Param("govAccount") GovAccountDO govAccount,
                                             @Param("pageCondition") PageCondition pageCondition);

    /**
     * 统计总数
     * 
     * @param govAccount
     * @return
     */
    public Integer countGovAccount(GovAccountDO govAccount);

    /**
     * 通过userId 和userType批量查询
     * 
     * @param userIdentitis
     * @return
     */
    public List<AccountInfoDO> queryListByUserIdentitis(List<UserIdentityDO> userIdentitis);

    /**
     * 通过账户号批量查询
     * 
     * @param accountNos
     * @return
     */
    public List<AccountInfoDO> findByAccountNos(List<String> accountNos);

    /**
     * 通过accountid查询GovAccountDO
     * 
     * @param accountId
     * @return
     */
    public GovAccountDO findGovAccountById(Integer accountId);
    

    /**
     * 通过AccountId更新AccountInfo
     * 
     * @param account
     * @return
     */
    public Integer updateByAccountId(AccountInfoDO account);

    /**
     * 通过用户Identity更新 <userId,userType>
     * 
     * @param account
     * @return
     */
    public Integer updateByUserIdentity(AccountInfoDO account);

    /**
     * 通过用户Identity更新 <userId,userType>
     * 
     * @param account
     * @return
     */
    public Integer updateByUserId(AccountInfoDO account);
    
    /**
     * 通过账户变更的批次流水修改账户信息
     * 
     * @param account
     * @return
     */
    public Integer updateByModifyBatchno(AccountInfoDO account);
    
    public Integer updateAfterBatchReg(AccountInfoDO account);
    
    /** 查询待处理的批次号*/
    public List<String> queryHandlingBatchno();
    /**根据真实姓名查询账户*/
	public List<AccountInfoDO> qureyAccountByRealName(@Param("realName") String realName,
            @Param("pageCondition") PageCondition pageCondition);
	
	/**
     * 获取总人数
     * 
     * @return
     */
    public int countUserNum(@Param("realName") String realName);

}
