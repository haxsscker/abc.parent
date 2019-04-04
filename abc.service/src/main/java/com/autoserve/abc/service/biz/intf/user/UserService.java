package com.autoserve.abc.service.biz.intf.user;

import java.math.BigDecimal;
import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.UserInvestInfoReportDO;
import com.autoserve.abc.dao.dataobject.UserRecommendDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserScoreDetail;
import com.autoserve.abc.service.biz.enums.ScoreType;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 前台用户业务类
 * 
 * @author RJQ 2014/11/20 17:48.
 */
public interface UserService {

    /**
     * 修改部分信息(userDO需包含主键)
     * 
     * @param userDO
     * @return
     */
    public BaseResult modifyUserSelective(UserDO userDO);

    /**
     * 修改部分信息(user需包含主键)
     * 
     * @param user
     * @return
     */
    public BaseResult modifyUserSelective(User user);

    /**
     * 查询用户信息
     * 
     * @param userId 用户id
     * @return
     */
    public PlainResult<UserDO> findById(int userId);

    public PlainResult<UserDO> findByIdWithLock(int userId);

    /**
     * 查询用户实体信息
     * 
     * @param id 用户id
     * @return PlainResult<User>
     */
    public PlainResult<User> findEntityById(int id);

    /**
     * 查询用户列表
     * 
     * @param userDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<UserDO>
     */
    public PageResult<UserDO> queryList(UserDO userDO, PageCondition pageCondition);
    
    /**
     * 查询用户列表
     * 
     * @param userDO 查询条件，可选
     * @return ListResult<UserDO>
     */
    public ListResult<UserDO> queryList(UserDO userDO);

    /**
     * 查询用户列表关联出推荐人列表
     * 
     * @param userDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<UserDO>
     */
    public PageResult<UserRecommendDO> queryRecommendList(UserRecommendDO userRecommendDO, PageCondition pageCondition);
    
    /**
     * 查询用户,用户批量注册时使用
     * 
     * @param userDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<UserDO>
     */
    public PageResult<UserDO> queryForBatchUserRegister(UserDO userdDO, PageCondition pageCondition);
    /**
     * 查询用户,用户批量注册时使用
     * 
     * @param userDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<UserDO>
     */
    public PageResult<UserDO> queryToBatchUserRegister(UserDO userdDO, PageCondition pageCondition);

    /**
     * 批量查询
     * 
     * @param ids 用户id
     * @return ListResult<UserDO>
     */
    public ListResult<UserDO> findByList(List<Integer> ids);

    /**
     * 删除用户
     * 
     * @param id 用户id
     * @return BaseResult
     */
    public BaseResult removeUser(int id);

    /**
     * 禁用某个用户
     * 
     * @param id 用户ID
     * @return BaseResult
     */
    public BaseResult disableUser(int id);

    /**
     * 启用用户
     * 
     * @param id 用户ID
     * @return BaseResult
     */
    public BaseResult enableUSer(int id);

    /**
     * 新增用户
     * 
     * @param userDO 用户信息
     * @return BaseResult
     */
    public BaseResult createUser(UserDO userDO);

    /**
     * 查询用户手机列表
     * 
     * @param userDO 查询条件，可选
     * @param userMobileVerifyDateStart 查询时间开始条件 可选
     * @param userMobileVerifyDateStop 查询时间结束条件 可选
     * @param pageCondition 分页条件
     * @return PageResult<UserDO>
     */
    public PageResult<UserDO> queryListMobile(UserDO userDO, String userMobileVerifyDateStart,
                                              String userMobileVerifyDateStop, PageCondition pageCondition);

    /**
     * 查询用户邮箱列表
     * 
     * @param userDO 查询条件，可选
     * @param userMobileVerifyDateStart 查询时间开始条件 可选
     * @param userMobileVerifyDateStop 查询时间结束条件 可选
     * @param pageCondition 分页条件
     * @return PageResult<UserDO>
     */
    public PageResult<UserDO> queryListEmail(UserDO userDO, String userRegisterDateStart, String userRegisterDateStop,
                                             PageCondition pageCondition);

    /**
     * 
     * 登录
     * @param loginValue 用户名,邮箱,手机号
     * @param password md5后的密码
     * @param loginIp 登录ip
     * @return PlainResult<UserDO>
     */
    public PlainResult<User> login(String loginValue, String password, String loginIp,String loginType);

    /**
     * 根据userId查询用户剩余可用金额
     */
    public PlainResult<BigDecimal> checkBalanceByUserId(int userId);

    /**
     * 查询用户总数
     * 
     * @return
     */
    PlainResult<Integer> queryTotal();
    
    /**
     * 查询投资用户总数
     * 
     * @return
     */
    PlainResult<Integer> queryInvestUserTotal();
    
    /**
     * 查询借款用户总数
     * 
     * @return
     */
    PlainResult<Integer> queryLoanUserTotal();

    /**
     * 增加用户的可用信用额度
     * 
     * @param userId 用户id
     * @param candidateValue 待增加的值（传正值）
     * @return BaseResult
     */
    public BaseResult addSettCredit(Integer userId, BigDecimal candidateValue);

    /**
     * 减少或增加用户的可用信用额度
     * 
     * @param userId 用户id
     * @param candidateValue 正数：减少信用额度，负数：增加信用额度
     * @return
     */
    public BaseResult reduceSettCredit(Integer userId, BigDecimal candidateValue);

    /**
     * 根据积分类型规则增加或减少用户积分（投资固定送积分改成比率送积分）
     * 
     * @param userId 用户id
     * @param scoreType 积分类型
     * @param note 备注（可以为空）
     * @param investMoney 投资金额（当积分类型为投资时）
     * @return BaseResult
     */
    public BaseResult modifyUserScore(Integer userId, ScoreType scoreType, String note);
    public BaseResult modifyUserScoreByInvest(Integer userId, ScoreType scoreType, String note,BigDecimal investMoney);

    /**
     * 根据积分代码规则增加或减少用户积分
     * 
     * @param userId 用户id
     * @param scoreCode 积分代码
     * @param note 备注（可以为空）
     * @return
     */
    public BaseResult modifyUserScore(Integer userId, String scoreCode, String note);

    /**
     * 更改用户业务相关的状态（注册成功，账户已开户，已充值，已投资）
     * 
     * @param userId 用户id
     * @param userType 用户类型
     * @param userBusinessState 业务相关状态
     * @return BaseResult
     */
    public BaseResult modifyUserBusinessState(Integer userId, UserType userType, UserBusinessState userBusinessState);

    /**
     * 查询用户积分细则
     * 
     * @param userId 查询条件
     * @param pageCondition 分页条件
     * @return PlainResult<UserScoreDetail>
     */
    public PlainResult<UserScoreDetail> findUserScoreDetail(int userId, PageCondition pageCondition);

    /**
     * 修改相关实名认证的信息
     * 
     * @param userDO
     * @return
     */
    public BaseResult modifyRealAuthInfo(UserDO userDO);

    
    /**
     * 修改信息
     * 
     * @param userDO
     * @return
     */
    public BaseResult modifyInfo(UserDO userDO);
    
    
    
    /**
     * 减少用户的免费提现额度
     * 
     * @param userId 用户id
     * @param cashQuotaValue 待减少的值
     * @return
     */
    public BaseResult reduceCashQuota(Integer userId, BigDecimal cashQuotaValue);
    /**
     * 用户投资信息查询
     * 
     * @param user
     * @param pageCondition
     * @return
     */
    public PageResult<UserInvestInfoReportDO> queryUserInvestInfo(UserDO user, PageCondition pageCondition);

	int countQueryUserInvestInfo(UserDO user);
	
	
    public PlainResult<UserDO> queryUserByUserName(String UserName);
    /**
     * 根据用户微信号查询用户信息
     * 
     * @param wechatid
     * @return
     */
    public PlainResult<UserDO> queryUserByWeChatId(String wechatid, Integer state);

    /**
     * 更新用户微信绑定状态
     * 
     * @param userwechatid
     * @return
     */
    public int updateWeChatInfo(String userwechatid);
    
    /**
     * 校验证件号码唯一性
     * xiatt 20160625
     */
    public int countDocNoByParam(UserDO user);

	List<UserDO> queryToBatchUserRegister(UserDO userDO);

	public int countPhoneByParam(UserDO userDO);
	
	void cleanAuthorizeByUserId(UserDO userDO,String type);
	
}
