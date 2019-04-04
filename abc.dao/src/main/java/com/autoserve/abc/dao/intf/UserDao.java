package com.autoserve.abc.dao.intf;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.UserInvestInfoReportDO;
import com.autoserve.abc.dao.dataobject.UserRecommendDO;

public interface UserDao extends BaseDao<UserDO, Integer> {
	
	Integer countEmailListByParam(@Param("user") UserDO userDO,
            @Param("userRegisterDateStart") String userRegisterDateStart,
            @Param("userRegisterDateStop") String userRegisterDateStop);
	
	Integer countMobileListByParam(@Param("user") UserDO userDO,
            @Param("userMobileVerifyDateStart") String userMobileVerifyDateStart,
            @Param("userMobileVerifyDateStop") String userMobileVerifyDateStop);
    /**
     * 按条件查询分页结果
     * 
     * @param userDO 查询条件，可选
     * @param userMobileVerifyDateStart 查询时间开始条件 可选
     * @param userMobileVerifyDateStop 查询时间结束条件 可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<UserDO>
     */
    List<UserDO> findMobileListByParam(@Param("user") UserDO userDO,
                                       @Param("userMobileVerifyDateStart") String userMobileVerifyDateStart,
                                       @Param("userMobileVerifyDateStop") String userMobileVerifyDateStop,
                                       @Param("pageCondition") PageCondition pageCondition);

    /**
     * 按条件查询分页结果
     * 
     * @param userDO 查询条件，可选
     * @param userRegisterDateStart 查询时间开始条件 可选
     * @param userRegisterDateStop 查询时间结束条件 可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<UserDO>
     */
    List<UserDO> findEmailListByParam(@Param("user") UserDO userDO,
                                      @Param("userRegisterDateStart") String userRegisterDateStart,
                                      @Param("userRegisterDateStop") String userRegisterDateStop,
                                      @Param("pageCondition") PageCondition pageCondition);

    /**
     * 根据参数获取记录条数
     * 
     * @param userDO
     * @return
     */
    public int countListByParam(UserDO userDO);

    /**
     * 按条件查询分页结果
     * 
     * @param userDO 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<UserDO>
     */
    List<UserDO> findListByParam(@Param("user") UserDO userDO, @Param("pageCondition") PageCondition pageCondition);
    
    
    /**
     * 按条件查询分页结果
     * 
     * @param userDO 查询条件，可选
     * @return List<UserDO>
     */
    List<UserDO> findListByParamNoPage(@Param("user") UserDO userDO);

    /**
     * 根据推荐人取总数
     * 
     * @param userRecommendDO
     * @return
     */
    int countRecommendListByParam(@Param("user") UserRecommendDO userRecommendDO);

    /**
     * 按条件查询分页结果
     * 
     * @param userDO 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<UserRecommendDO>
     */
    List<UserRecommendDO> findRecommendListByParam(@Param("user") UserRecommendDO userRecommendDO,
                                                   @Param("pageCondition") PageCondition pageCondition);
    
    /**
     * 查询用户,用户批量注册时使用
     * 
     * @param userRecommendDO
     * @return
     */
    int countForBatchUserRegister(@Param("user") UserDO userDO);

    /**
     * 查询用户,用户批量注册时使用
     * 
     * @param userDO 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<UserRecommendDO>
     */
    List<UserDO> findForBatchUserRegister(@Param("user") UserDO userDO,
                                                   @Param("pageCondition") PageCondition pageCondition);
    
    /**
     * 查询用户,用户批量注册时使用
     * 
     * @param userRecommendDO
     * @return
     */
    int countToBatchUserRegister(@Param("user") UserDO userDO);

    /**
     * 查询用户,用户批量注册时使用
     * 
     * @param userDO 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<UserRecommendDO>
     */
    List<UserDO> findToBatchUserRegister(@Param("user") UserDO userDO,
                                                   @Param("pageCondition") PageCondition pageCondition);

    /**
     * 有选择更新
     * 
     * @param userDO 更新信息
     * @return int
     */
    public int updateByPrimaryKeySelective(UserDO userDO);
    
    public int cleanUserAuthorizeInfo(UserDO userDO);

    /**
     * 有选择更新
     * 
     * @param userDO 更新信息
     * @return int
     */
    public int updateByPrimaryKeyId(UserDO userDO);

    /**
     * 批量查询
     * 
     * @param ids 员工id
     * @return List<UserDO>
     */
    public List<UserDO> findByList(List<Integer> ids);
    
    /**
     * 查找今天生日的用户
     * @return
     */
    public List<UserDO> findBirthdayUser();
    
    /**
     * 查找
     * @return user_phone
     */
    public List<UserDO> findRedSendValidUser();

    /**
     * 用户登录查询
     * 
     * @param userName
     * @param password
     * @return
     */
    public UserDO findUserByNameAndPass(@Param("userName") String userName, @Param("password") String password);

    /**
     * 根据用户名查询用户信息
     *
     * @param userName 用户名
     * @return UserDO
     */
    public UserDO findByName(@Param("userName") String userName);
    /**
     * 根据邮箱查询用户信息
     * @param userEmail
     * @return
     */
    public UserDO findByEmail(@Param("userEmail") String userEmail);
    /**
     * 根据手机号查询用户信息
     * @param userPhone
     * @return
     */
    public UserDO findByPhone(@Param("userPhone") String userPhone);

    /**
     * 更新信用额度
     * 
     * @param userId
     * @param candidateValue
     * @return
     */
    public int computeSettCredit(@Param("userId") Integer userId, @Param("val") BigDecimal candidateValue);

    /**
     * 减少免费提现额度
     * 
     * @param userId
     * @param cashQuotaValue
     * @return
     */
    public int reduceCashQuota(@Param("userId") Integer userId, @Param("val") BigDecimal cashQuotaValue);


    public UserDO findByIdWithLock(int id);

    /**
     * 查询用户可用余额
     * 
     * @param userId
     * @return
     */
    public BigDecimal checkBalanceByUserId(int userId);

    /**
     * 查询用户总数
     * 
     * @param userId
     * @return
     */
    public int findTotal();
    
    int queryInvestUserTotal();
    
    int queryLoanUserTotal();

    /**
     * 用户投资信息查询报表
     * 
     * @param user
     * @return
     */
    public List<UserInvestInfoReportDO> queryUserInvestInfo(@Param("user") UserDO user,
                                                            @Param("pageCondition") PageCondition pageCondition);

    int countQueryUserInvestInfo(@Param("user") UserDO user);
    
    /**
     * 根据用户的微信ID查询用户个数量
     * 
     * @param wechatid
     * @return
     */
    int queryUserByWeChatId(String wechatid);

    /**
     * 根据微信用户的Id查询用户信息
     * 
     * @param wechatid
     * @return
     */
    public UserDO findUserByWeChatId(@Param("wechatid") String wechatid, @Param("state") Integer state);

    public int updateWeChatState(@Param("userwechatid") String userwechatid);
    
    /**
     * 校验证件号码唯一性
     * xiatt 20160625 
     * @param userDO 查询条件，可选
     * @return List<UserDO>
     */
    public int countDocNoByParam(UserDO userDO);
    
    public UserDO findUserByAuthorizeSeqNo(UserDO user);
    
	public void updateAuthorizeBySeqNo(UserDO user);

	public void updateAuthorizeByUserId(UserDO userDo);
	//清除投资账户授权信息
	public void cleanInvestAuthorizeByUserId(UserDO userDo);
	//清除融资账户授权信息
	public void cleanLoanAuthorizeByUserId(UserDO userDo);
	
	int countPhoneByParam(UserDO userDO);
}
