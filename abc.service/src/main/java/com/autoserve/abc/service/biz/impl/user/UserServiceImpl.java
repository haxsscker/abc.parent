package com.autoserve.abc.service.biz.impl.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.RealnameAuthDO;
import com.autoserve.abc.dao.dataobject.ScoreDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;
import com.autoserve.abc.dao.dataobject.SysConfigDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.UserInvestInfoReportDO;
import com.autoserve.abc.dao.dataobject.UserRecommendDO;
import com.autoserve.abc.dao.dataobject.WebLoginLogDO;
import com.autoserve.abc.dao.intf.CompanyCustomerDao;
import com.autoserve.abc.dao.intf.LoginLogDao;
import com.autoserve.abc.dao.intf.RealnameAuthDao;
import com.autoserve.abc.dao.intf.SysConfigDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.dao.intf.WebLoginLogDao;
import com.autoserve.abc.service.biz.convert.UserConverter;
import com.autoserve.abc.service.biz.entity.Red;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserScoreDetail;
import com.autoserve.abc.service.biz.enums.DocType;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.OnlineState;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.enums.ScoreType;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.score.ScoreHistoryService;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.message.sms.SendMsgService;
import com.autoserve.abc.service.util.AddressForSinaUtils;
import com.autoserve.abc.service.util.AddressUtils;

/**
 * @author RJQ 2014/11/20 17:47.
 */
@Service
public class UserServiceImpl implements UserService,InitializingBean{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	private UserDao userDao;

	@Resource
	private LoginLogDao loginLogDao;

	@Resource
	private WebLoginLogDao webLoginLogDao;
	
	@Resource
	private ScoreService scoreService;

	@Resource
	private ScoreHistoryService scoreHistoryService;

	@Resource
	private RedService redService;

	@Resource
	private RealnameAuthDao realnameAuthDao;
	@Resource
	private SysConfigDao sysConfigDao;
	@Autowired
	private CompanyCustomerDao companyCustomerDao;
	
	@Resource
	private SendMsgService sendMsgService;
	
	/**
	 * 错误登录次数限制
	 */
	private int loginCountLimit = 4;
	/**
	 * 错误登录禁止登录时间（分钟）
	 */
	private int loginTimeLimit = 10;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		SysConfigDO sysConfigDO = sysConfigDao.findByConfigKey("LOGIN_COUNT_LIMIT");
		if(sysConfigDO!=null){
			loginCountLimit = Integer.parseInt(sysConfigDO.getConfValue());
		}
		sysConfigDO = sysConfigDao.findByConfigKey("LOGIN_TIME_LIMIT");
		if(sysConfigDO!=null){
			loginTimeLimit = Integer.parseInt(sysConfigDO.getConfValue());
		}
	}

	@Override
	public BaseResult modifyUserSelective(UserDO userDO) {
		BaseResult result = new BaseResult();
//		UserDO oldUser = userDao.findById(userDO.getUserId());
//		//实名认证后，不允许修改用户真实姓名和身份证号
//		if(oldUser!=null && new Integer(1).equals(oldUser.getUserRealnameIsproven())){
//			if(!StringUtil.isEmpty(userDO.getUserDocNo()) || !StringUtil.isEmpty(userDO.getUserRealName())){
//				return result.setErrorMessage(CommonResultCode.BIZ_ERROR, "实名认证后不允许修改");
//			}
//		}
		int returnVal = userDao.updateByPrimaryKeySelective(userDO);
		if (returnVal <= 0) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新失败");
		}
		return result;
	}

	@Override
	public BaseResult modifyUserSelective(User user) {
		BaseResult result = new BaseResult();
		int returnVal = userDao.updateByPrimaryKeySelective(UserConverter
				.toUserDO(user));
		if (returnVal <= 0) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新失败");
		}
		return result;
	}

	@Override
	public PlainResult<UserDO> findById(int userId) {
		PlainResult<UserDO> result = new PlainResult<UserDO>();
		UserDO userDO = userDao.findById(userId);
		if(userDO!=null && UserType.ENTERPRISE.type == userDO.getUserType()){
			CompanyCustomerDO companyCustomerDO = companyCustomerDao.findByUserId(userId);
			userDO.setUserPhone(companyCustomerDO.getCcContactPhone());
		}
		if (userDO == null) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询用户失败！");
			return result;
		}
		result.setData(userDO);
		return result;
	}

	@Override
	public PlainResult<UserDO> findByIdWithLock(int userId) {
		PlainResult<UserDO> result = new PlainResult<UserDO>();
		UserDO userDO = userDao.findByIdWithLock(userId);
		if (userDO == null) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询用户失败！");
			return result;
		}
		result.setData(userDO);
		return result;
	}

	@Override
	public ListResult<UserDO> findByList(List<Integer> ids) {
		ListResult<UserDO> result = new ListResult<UserDO>();
		if (ids == null) {
			throw new BusinessException("参数不能为null");
		}
		List<UserDO> users = userDao.findByList(ids);
		result.setData(users);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public PageResult<UserDO> queryList(UserDO userDO,
			PageCondition pageCondition) {
		PageResult<UserDO> result = new PageResult<UserDO>(
				pageCondition.getPage(), pageCondition.getPageSize());
		int totalCount = userDao.countListByParam(userDO);
		result.setTotalCount(totalCount);

		if (totalCount > 0) {
			result.setData(userDao.findListByParam(userDO, pageCondition));
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public PageResult<UserDO> queryListMobile(UserDO userDO,
			String userMobileVerifyDateStart, String userMobileVerifyDateStop,
			PageCondition pageCondition) {
		PageResult<UserDO> result = new PageResult<UserDO>(
				pageCondition.getPage(), pageCondition.getPageSize());
		int totalCount = userDao.countMobileListByParam(userDO, userMobileVerifyDateStart, userMobileVerifyDateStop);
		result.setTotalCount(totalCount);

		if (totalCount > 0) {
			result.setData(userDao.findMobileListByParam(userDO,
					userMobileVerifyDateStart, userMobileVerifyDateStop,
					pageCondition));
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public PageResult<UserDO> queryListEmail(UserDO userDO,
			String userRegisterDateStart, String userRegisterDateStop,
			PageCondition pageCondition) {
		PageResult<UserDO> result = new PageResult<UserDO>(
				pageCondition.getPage(), pageCondition.getPageSize());
		int totalCount = userDao.countEmailListByParam(userDO, userRegisterDateStart, userRegisterDateStop);
		result.setTotalCount(totalCount);

		if (totalCount > 0) {
			result.setData(userDao.findEmailListByParam(userDO,
					userRegisterDateStart, userRegisterDateStop, pageCondition));
		}
		return result;
	}

	@Override
	public PlainResult<User> findEntityById(int id) {
		PlainResult<User> result = new PlainResult<User>();

		if (id <= 0) {
			return result.setError(CommonResultCode.ILEEGAL_ARGUMENT, "id");
		}

		UserDO userDO = userDao.findById(id);
		if (userDO == null) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "没找到指定用户信息！");
			return result;
		}
		User user = UserConverter.toUser(userDO);
		result.setData(user);
		return result;
	}

	@Override
	public BaseResult removeUser(int id) {
		BaseResult result = new BaseResult();
		int returnVal = updateUserState(id, EntityState.STATE_DELETED);
		if (returnVal <= 0) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除失败！");
		}
		return result;
	}

	@Override
	public BaseResult disableUser(int id) {
		BaseResult baseResult = new BaseResult();
		int returnVal = updateUserState(id, EntityState.STATE_DISABLE);
		if (returnVal <= 0) {
			baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "禁用失败！");
		}
		return baseResult;
	}

	@Override
	public BaseResult enableUSer(int id) {
		BaseResult baseResult = new BaseResult();
		int returnVal = updateUserState(id, EntityState.STATE_ENABLE);
		if (returnVal <= 0) {
			baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "启用失败！");
		}
		return baseResult;
	}

	/**
	 * 更新用户状态
	 * 
	 * @param id
	 *            用户id
	 * @param state
	 *            状态
	 * @return int
	 */
	private int updateUserState(int id, EntityState state) {
		UserDO userDO = new UserDO();
		userDO.setUserId(id);
		userDO.setUserState(state.getState());

		return userDao.updateByPrimaryKeySelective(userDO);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseResult createUser(UserDO userDO) {
		BaseResult baseResult = new BaseResult();
		if (userDO.getUserName() == null || userDO.getUserPwd() == null) {
			baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "用户名密码不能为空");
			return baseResult;
		}
		// 验证用户名是否已存在
		if (userDao.findByName(userDO.getUserName()) != null) {
			baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "用户名已存在");
			return baseResult;
		}
		userDO.setUserScore(0);
		userDO.setUserBusinessState(UserBusinessState.REGISTERED.getState());
		userDO.setUserCreditSett(userDO.getUserLoanCredit() == null ? null
				: userDO.getUserLoanCredit());
		int val = userDao.insert(userDO);
		if (val <= 0) {
			baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "新增用户失败");
			return baseResult;
		} else {
			// 发送红包
			sendRegistRed(userDO);
		}
		return baseResult;
	}

	/**
	 * 注册成功后发送红包
	 */
	private BaseResult sendRegistRed(UserDO userDO) {
		BaseResult baseResult = new BaseResult();

		Red redParam = new Red();
		redParam.setRedType(RedenvelopeType.REGISTOR_RED);
		// 查询注册红包
		ListResult<Red> redResult = redService.queryList(redParam, null);
		if (!redResult.isSuccess()) {
			baseResult.setError(CommonResultCode.BIZ_ERROR,
					redResult.getMessage());
			return baseResult;
		}

		// 不存在注册红包直接返回
		List<Red> redList = redResult.getData();
		if (CollectionUtils.isEmpty(redList)) {
			return baseResult;
		}

		// 对用户发放注册红包
		List<Redsend> sendList = new ArrayList<Redsend>();
		DateTime fullDaytime = new DateTime();
		double redAmount = 0.00;
		for (Red red : redList) {
			if (RedState.EFFECTIVE.equals(red.getRedState())) {
				Redsend redsend = new Redsend();

				redsend.setRsTheme(red.getRedTheme());
				redsend.setRsUserid(userDO.getUserId());
				redsend.setRsRedId(red.getRedId());
				redsend.setRsUseScope(red.getRedUseScope());
				redsend.setRsState(RsState.WITHOUT_USE);
				redsend.setRsClosetime(fullDaytime.plusDays(
						red.getRedClosetime()).toDate());
				redsend.setRsStarttime(new Date());
				redsend.setRsSender(red.getRedCreator());
				redsend.setRsType(RedenvelopeType.REGISTOR_RED);
				redsend.setRsAmt(red.getRedAmt());
				redsend.setRsValidAmount(red.getRedAmount());

				redAmount += red.getRedAmount();
				sendList.add(redsend);
			}
		}

		if (CollectionUtils.isEmpty(sendList)) {
			return baseResult;
		}

		baseResult = redService.batchSendRed(sendList);
		
		//发送邀请注册短信
		if (0.00 != redAmount)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("尊敬的客户：恭喜您成功注册，特派发您").append(redAmount).append("元注册红包，可登录账户查询并在投资时使用，感谢您支持和信赖。回T退订");
			sendMsgService.sendMsg(userDO.getUserPhone(), sb.toString(), "","1");
		}
		
		return baseResult;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public PlainResult<User> login(String loginValue, String password,
			String loginIp,String loginType) {
		
		PlainResult<User> result = new PlainResult<User>();
		// 判断用户输入的是 邮箱，手机，用户名
		String emailReg = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.[a-zA-Z0-9_-]+)$";
		String phoneReg = "^1\\d{10}$";
		UserDO userDO = null;
		if (Pattern.matches(emailReg, loginValue)) {
			userDO = userDao.findByEmail(loginValue);
		} else if (Pattern.matches(phoneReg, loginValue)) {
			userDO = userDao.findByPhone(loginValue);
		} else {
			userDO = userDao.findByName(loginValue);
		}
		
		if (userDO == null) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "不存在的用户！");
			return result;
		}
		if (userDO.getUserState() != EntityState.STATE_ENABLE.getState()) {// 账号非启用状态
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "账号"+EntityState.valueOf(userDO.getUserState()).des);
			return result;
		}

		// 登录错误次数判断
		UserDO updateUser = new UserDO();
		updateUser.setUserId(userDO.getUserId());
		if (userDO.getUserErrorTime() != null) {
			//当前时间距用户上次错误登录时间间隔（分钟）
			long intervalSecond = (new Date().getTime() - userDO
					.getUserErrorTime().getTime()) / 1000 / 60;
			if (userDO.getUserErrorCount() >= loginCountLimit
					&& intervalSecond < loginTimeLimit) {
				log.debug("已锁定");
				result.setErrorMessage(CommonResultCode.BIZ_ERROR,
						"登录密码出错已达上限将锁定密码" + loginTimeLimit
								+ "分钟，请稍后登录。");
				return result;
			}
		}

		if (!password.equals(userDO.getUserPwd())) {
			// 密码错误，登录失败- 更新用户错误登录次数，时间
			updateUser.setUserErrorCount(userDO.getUserErrorCount() + 1);
			updateUser.setUserErrorTime(new Date());
			userDao.updateByPrimaryKeySelective(updateUser);
			int remain = loginCountLimit-(userDO.getUserErrorCount()+1);
			result.setSuccess(false);
			if(remain<=3 && remain>0){
				result.setMessage("密码错误，还有"+remain+"次机会");
			}else if(remain==0){
				log.debug("最后一次尝试失败");
				result.setMessage("登录密码出错已达上限将锁定密码" + loginTimeLimit
								+ "分钟，请稍后登录。");
			} else{
				result.setMessage("密码错误");
				if(remain<0){
					//用户锁定时间已到，再次尝试错误
					updateUser.setUserErrorCount(1);
					updateUser.setUserErrorTime(new Date());
					userDao.updateByPrimaryKeySelective(updateUser);
				}
			}
			return result;
		} else {
			// 登录成功- 清除用户错误登录信息 更新登录次数，最后登录时间和在线状态
			updateUser.setUserErrorCount(0);
			Integer loginCount = userDO.getUserLoginCount();
			if(loginCount==null){
				loginCount = 0;
			}
			updateUser.setUserLoginCount(++loginCount);
			updateUser.setUserIsonline(OnlineState.STATE_ONLINE.getState());
			userDao.updateByPrimaryKeySelective(updateUser);
			//添加登录日志
			WebLoginLogDO webLoginLogDO = new WebLoginLogDO();
			webLoginLogDO.setLlIp(loginIp);
			webLoginLogDO.setLlLoginType(loginType);
			webLoginLogDO.setLlLoginTime(new Date());
			webLoginLogDO.setLlUserId(updateUser.getUserId());
			webLoginLogDO.setLlLoginState(1);
			try {
				//淘宝接口
				//webLoginLogDO.setLlArea(AddressUtils.getAddresses("ip="+loginIp, "utf-8"));
				//新浪接口
				String [] logIp=loginIp.split(",");
				if(logIp.length>1){
					loginIp=logIp[0].trim();
				}
				webLoginLogDO.setLlArea(AddressForSinaUtils.getAddresses(loginIp, "GBK"));
			} catch (Exception e) {
				log.error("通过IP获取登录地址失败，ip="+loginIp+",userId="+updateUser.getUserId());
			}
			webLoginLogDao.insert(webLoginLogDO);
			User entity = UserConverter.toUser(userDO);
			result.setData(entity);
		}
		return result;
	}

	@Override
	public PlainResult<BigDecimal> checkBalanceByUserId(int userId) {
		BigDecimal balance = this.userDao.checkBalanceByUserId(userId);
		PlainResult<BigDecimal> plainResult = new PlainResult<BigDecimal>();
		plainResult.setData(balance);
		return plainResult;
	}

	@Override
	public PlainResult<Integer> queryTotal() {
		PlainResult<Integer> plainResult = new PlainResult<Integer>();

		plainResult.setData(this.userDao.findTotal());
		return plainResult;
	}
	
	@Override
	public PlainResult<Integer> queryInvestUserTotal() {
		PlainResult<Integer> plainResult = new PlainResult<Integer>();

		plainResult.setData(this.userDao.queryInvestUserTotal());
		return plainResult;
	}
	
	@Override
	public PlainResult<Integer> queryLoanUserTotal() {
		PlainResult<Integer> plainResult = new PlainResult<Integer>();

		plainResult.setData(this.userDao.queryLoanUserTotal());
		return plainResult;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseResult addSettCredit(Integer userId, BigDecimal candidateValue) {
		BaseResult result = new BaseResult();
		if (candidateValue.compareTo(BigDecimal.ZERO) < 0) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "待增加的信用额度参数不能为负");
			return result;
		} else if (candidateValue.compareTo(BigDecimal.ZERO) == 0) {
			return result;
		}

		UserDO userDO = userDao.findByIdWithLock(userId);
		if (userDO == null) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定用户");
			return result;
		}
		BigDecimal maxLoanCredit = userDO.getUserLoanCredit();// 最大信用额度
		BigDecimal settCredit = userDO.getUserCreditSett();// 可用信用额度
		BigDecimal tmpCandidateValue = candidateValue.add(settCredit);
		if (tmpCandidateValue.compareTo(maxLoanCredit) > 0) {
			tmpCandidateValue = maxLoanCredit.subtract(settCredit);
		} else {
			tmpCandidateValue = candidateValue;
		}
		int val = userDao.computeSettCredit(userId, tmpCandidateValue);
		if (val <= 0) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新信用保额度出错");
		}
		return result;
	}

	@Override
	public BaseResult reduceSettCredit(Integer userId, BigDecimal candidateValue) {
		BaseResult result = new BaseResult();
		// if (candidateValue.compareTo(BigDecimal.ZERO) < 0) {
		// result.setErrorMessage(CommonResultCode.BIZ_ERROR, "待减少的信用额度参数不能为负");
		// return result;
		// } else if (candidateValue.compareTo(BigDecimal.ZERO) == 0) {
		// return result;
		// }

		UserDO userDO = userDao.findById(userId);
		if (userDO == null) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定用户");
			return result;
		}
		BigDecimal settCredit = userDO.getUserCreditSett();// 可用信用额度
		if (settCredit.compareTo(candidateValue) < 0) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "可用信用额度不足");
			return result;
		}
		// 减少信用额度
		int val = userDao.computeSettCredit(userId,
				candidateValue.multiply(new BigDecimal(-1)));
		if (val <= 0) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新信用保额度出错");
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseResult modifyUserScore(Integer userId, ScoreType scoreType,
			String note) {
		String scoreCode = scoreType.getCode();
		return modifyUserScore(userId, scoreCode, note);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseResult modifyUserScore(Integer userId, String scoreCode,
			String note) {
		PlainResult<ScoreDO> scoreResult = scoreService
				.findByScoreCode(scoreCode);
		if (!scoreResult.isSuccess()) {
			throw new BusinessException("未找到指定积分类型！");
		}
		ScoreDO scoreDO = scoreResult.getData();
		
		// 积分规则>0才记录
		if (scoreDO.getScoreValue() > 0)
		{
			// 在score_history表中记录userId，积分id和value等记录
			ScoreHistoryDO scoreHistoryDO = new ScoreHistoryDO();
			scoreHistoryDO.setShUserId(userId);
			scoreHistoryDO.setShScoreId(scoreDO.getScoreId());
			scoreHistoryDO.setShNote(note);
			BaseResult createResult = scoreHistoryService
					.createScoreHistory(scoreHistoryDO);
			if (!createResult.isSuccess()) {
				throw new BusinessException("积分记录失败！");
			}

			// 修改user表中用户的积分值和修改时间
			UserDO userDO = userDao.findByIdWithLock(userId);
			if (userDO == null) {
				throw new BusinessException("未找到指定用户");
			}
			Integer userScore = userDO.getUserScore();
			if (userScore == null) {
				userScore = 0;
			}
			userDO.setUserScore(scoreDO.getScoreValue() + userScore);
			int val = userDao.updateByPrimaryKeySelective(userDO);
			if (val <= 0) {
				throw new BusinessException("修改用户记录失败！");
			}
		}

		return BaseResult.SUCCESS;
	}
	
//	public void investModifyUserScore(InvestDO investDO, String note){
//		PlainResult<ScoreDO> scoreResult = scoreService
//				.findByScoreCode(ScoreType.INVEST_SCORE.code);
//		if (!scoreResult.isSuccess()) {
//			throw new BusinessException("未找到指定积分类型！");
//		}
//		ScoreDO scoreDO = scoreResult.getData();
//		// 在score_history表中记录userId，积分id和value等记录
//		ScoreHistoryDO scoreHistoryDO = new ScoreHistoryDO();
//		scoreHistoryDO.setShUserId(investDO.getInUserId());
//		scoreHistoryDO.setShScoreId(scoreDO.getScoreId());
//		scoreHistoryDO.setShNote(note);
//		BaseResult createResult = scoreHistoryService
//				.createScoreHistory(scoreHistoryDO);
//		if (!createResult.isSuccess()) {
//			throw new BusinessException("积分记录失败！");
//		}
//
//		// 修改user表中用户的积分值和修改时间
//		UserDO userDO = userDao.findByIdWithLock(investDO.getInUserId());
//		
//		Integer userScore = userDO.getUserScore();
//		if (userScore == null) {
//			userScore = 0;
//		}
//		//计算用户所得积分=(投资金额*设置值)/100
//		BigDecimal scoreValue = new BigDecimal(scoreDO.getScoreValue()+"");
//		BigDecimal score = investDO.getInInvestMoney().multiply(scoreValue).divide(new BigDecimal("100"), RoundingMode.FLOOR);
//		userDO.setUserScore(score.intValue()+userDO.getUserScore());
//		int val = userDao.updateByPrimaryKeySelective(userDO);
//		if (val <= 0) {
//			throw new BusinessException("修改用户记录失败！");
//		}
//	}

	@Override
	public BaseResult modifyUserBusinessState(Integer userId,
			UserType userType, UserBusinessState userBusinessState) {
		BaseResult result = new BaseResult();
		UserDO userDO;
		if (userType.equals(UserType.ENTERPRISE)
				|| userType.equals(UserType.PERSONAL)) {// 前台（个人/企业）用户需要更改状态，平台用户不需要
			userDO = userDao.findByIdWithLock(userId);
			switch (userBusinessState) {
			case REGISTERED:// 注册
				userDO.setUserBusinessState(userBusinessState.getState());
				break;
			case ACCOUNT_OPENED:// 开户
				userDO.setUserBusinessState(userBusinessState.getState());
				break;
			case RECHARGED:// 充值
				if (UserBusinessState.INVESTED.getState() != userDO
						.getUserBusinessState()
						&& UserBusinessState.RECHARGED.getState() != userDO
								.getUserBusinessState()) {
					userDO.setUserBusinessState(userBusinessState.getState());
				}
				break;
			case INVESTED:// 投资
				if (UserBusinessState.INVESTED.getState() != userDO
						.getUserBusinessState()) {
					userDO.setUserBusinessState(userBusinessState.getState());
				}
				break;
			default:
				return result;
			}
			userDO.setUserId(userId);
			if (userDao.updateByPrimaryKeySelective(userDO) <= 0) {
				result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改失败！");
			}
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public PlainResult<UserScoreDetail> findUserScoreDetail(int userId,
			PageCondition pageCondition) {
		PlainResult<UserScoreDetail> result = new PlainResult<UserScoreDetail>();
		ScoreHistoryDO scoreHistoryDO = new ScoreHistoryDO();
		scoreHistoryDO.setShUserId(userId);
		List<ScoreHistoryWithValueDO> list = scoreHistoryService
				.queryScoreHistoryList(scoreHistoryDO, pageCondition).getData();
		UserDO userDO = userDao.findById(userId);
		if (userDO == null) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定用户");
			return result;
		}

		UserScoreDetail userScoreDetail = new UserScoreDetail();
		userScoreDetail.setUserId(userId);
		userScoreDetail.setUserName(userDO.getUserName());
		userScoreDetail.setUserRealName(userDO.getUserRealName());
		userScoreDetail.setUserScore(userDO.getUserScore());
		userScoreDetail.setUserScoreLastmodifytime(userDO
				.getUserScoreLastmodifytime());
		userScoreDetail.setScoreHistoryDOList(list);

		result.setData(userScoreDetail);
		return result;
	}

	@Override
	public PageResult<UserRecommendDO> queryRecommendList(
			UserRecommendDO userDO, PageCondition pageCondition) {
		PageResult<UserRecommendDO> result = new PageResult<UserRecommendDO>(
				pageCondition.getPage(), pageCondition.getPageSize());
		int totalCount = userDao.countRecommendListByParam(userDO);
		result.setTotalCount(totalCount);

		if (totalCount > 0) {
			result.setData(userDao.findRecommendListByParam(userDO,
					pageCondition));
		}

		return result;
	}
	
	@Override
	public PageResult<UserDO> queryForBatchUserRegister(
			UserDO userDO, PageCondition pageCondition) {
		PageResult<UserDO> result = new PageResult<UserDO>(
				pageCondition.getPage(), pageCondition.getPageSize());
		int totalCount = userDao.countForBatchUserRegister(userDO);
		result.setTotalCount(totalCount);

		if (totalCount > 0) {
			result.setData(userDao.findForBatchUserRegister(userDO,
					pageCondition));
		}

		return result;
	}
	
	@Override
	public PageResult<UserDO> queryToBatchUserRegister(
			UserDO userDO, PageCondition pageCondition) {
		PageResult<UserDO> result = new PageResult<UserDO>(
				pageCondition.getPage(), pageCondition.getPageSize());
		int totalCount = userDao.countToBatchUserRegister(userDO);
		result.setTotalCount(totalCount);

		if (totalCount > 0) {
			result.setData(userDao.findToBatchUserRegister(userDO,
					pageCondition));
		}

		return result;
	}
	
	@Override
	public List<UserDO> queryToBatchUserRegister(UserDO userDO) {
		return userDao.findToBatchUserRegister(userDO, null);
	}

	@Override
	public BaseResult modifyInfo(UserDO userDO) {
		BaseResult result = new BaseResult();
		try {
			userDao.updateByPrimaryKeySelective(userDO);

		} catch (Exception e) {
			e.printStackTrace();
			result.setError(CommonResultCode.BIZ_ERROR, "内部服务异常");
		}
		return result;
	}

	@Override
	public BaseResult modifyRealAuthInfo(UserDO userDO) {
		BaseResult result = new BaseResult();
		try {
			userDao.updateByPrimaryKeySelective(userDO);
			UserDO user = userDao.findById(userDO.getUserId());
			user.setUserRealName(userDO.getUserRealName());
			user.setUserDocNo(userDO.getUserDocNo());

			RealnameAuthDO realnameAuthDO = new RealnameAuthDO();
			realnameAuthDO.setRnpUserid(user.getUserId());
			realnameAuthDO.setRnpName(user.getUserRealName());
			realnameAuthDO.setRnpDocType(DocType.ID_CARD.getType());
			realnameAuthDO.setRnpDocNo(user.getUserDocNo());
			realnameAuthDO.setRnpApplyDate(new Date());
			int updateCount = realnameAuthDao.updateByUserId(realnameAuthDO);
			if (updateCount == 0) { // 插入
				realnameAuthDao.insert(realnameAuthDO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(CommonResultCode.BIZ_ERROR, "内部服务异常");
		}
		return result;
	}

	@Override
	public BaseResult reduceCashQuota(Integer userId, BigDecimal cashQuotaValue) {
		BaseResult result = new BaseResult();
		UserDO userDO = userDao.findById(userId);
		if (userDO == null) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定用户");
			return result;
		}
		BigDecimal cashQuota = userDO.getUserCashQuota();
		if (cashQuota.compareTo(cashQuotaValue) < 0) {
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "免费提现额度不足");
			return result;
		}
		int reduce = userDao.reduceCashQuota(userId, cashQuotaValue);
		if (reduce < 0) {
			result.setError(CommonResultCode.BIZ_ERROR, "更新免费提现额度失败");
		}
		return result;
	}

	@Override
	public PageResult<UserInvestInfoReportDO> queryUserInvestInfo(UserDO user,
			PageCondition pageCondition) {
		PageResult<UserInvestInfoReportDO> pageResult = new PageResult<UserInvestInfoReportDO>(
				pageCondition.getPage(), pageCondition.getPageSize());
		int totalCount = userDao.countQueryUserInvestInfo(user);
		pageResult.setTotalCount(totalCount);

		if (totalCount > 0) {
			try {
				List<UserInvestInfoReportDO> list = userDao
						.queryUserInvestInfo(user, pageCondition);
				pageResult.setData(list);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return pageResult;
	}

	@Override
	public int countQueryUserInvestInfo(UserDO user) {

		int count = userDao.countQueryUserInvestInfo(user);

		return count;
	}

	@Override
	public PlainResult<UserDO> queryUserByUserName(String UserName) {
		PlainResult<UserDO> userDO = new PlainResult<UserDO>();
		UserDO user = userDao.findByName(UserName);
		userDO.setData(user);
		return userDO;
	}

	@Override
	public PlainResult<UserDO> queryUserByWeChatId(String wechatid,
			Integer state) {
		PlainResult<UserDO> result = new PlainResult<UserDO>();
		UserDO userDO = userDao.findUserByWeChatId(wechatid, state);
		result.setData(userDO);
		return result;
	}

	@Override
	public int updateWeChatInfo(String userwechatid) {
		int result = userDao.updateWeChatState(userwechatid);
		return result;
	}

	@Override
	public ListResult<UserDO> queryList(UserDO userDO) {
		ListResult<UserDO> result = new ListResult<UserDO>();
		List<UserDO> lists = userDao.findListByParamNoPage(userDO);
		result.setData(lists);
		return result;
	}
	/**
	 * 按照后台设置比率对积分类型为 投资送积分(%)进行处理
	 **/
	@Override
	public BaseResult modifyUserScoreByInvest(Integer userId,
			ScoreType scoreType, String note, BigDecimal investMoney) {
		String scoreCode = scoreType.getCode();
		
		PlainResult<ScoreDO> scoreResult = scoreService
				.findByScoreCode(scoreCode);
		if (!scoreResult.isSuccess()) {
			throw new BusinessException("未找到指定积分类型！");
		}
		ScoreDO scoreDO = scoreResult.getData();
		BigDecimal scoreValue =new BigDecimal(scoreDO.getScoreValue()).divide(new BigDecimal(100),2,BigDecimal.
				ROUND_HALF_EVEN).multiply(investMoney).setScale(0,BigDecimal.ROUND_HALF_EVEN);
		if(scoreValue.compareTo(BigDecimal.ZERO) >0){//积分不为0时存入积分记录表
			// 在score_history表中记录userId，积分id和value等记录
			ScoreHistoryDO scoreHistoryDO = new ScoreHistoryDO();
			scoreHistoryDO.setShUserId(userId);
			scoreHistoryDO.setShScoreId(scoreDO.getScoreId());
			scoreHistoryDO.setShNote(note);
			scoreHistoryDO.setShValue(scoreValue);
			BaseResult createResult = scoreHistoryService
					.createScoreHistory(scoreHistoryDO);
			if (!createResult.isSuccess()) {
				throw new BusinessException("积分记录失败！");
			}
		}
		// 修改user表中用户的积分值和修改时间
		UserDO userDO = userDao.findByIdWithLock(userId);
		if (userDO == null) {
			throw new BusinessException("未找到指定用户");
		}
		Integer userScore = userDO.getUserScore();
		if (userScore == null) {
			userScore = 0;
		}
		userDO.setUserScore(scoreValue.intValue() + userScore);
		int val = userDao.updateByPrimaryKeySelective(userDO);
		if (val <= 0) {
			throw new BusinessException("修改用户记录失败！");
		}

		return BaseResult.SUCCESS;
	}

	/**
     * 校验证件号码唯一性
     * xiatt 20160625
     */
	@Override
	public int countDocNoByParam(UserDO user) {
		int result = userDao.countDocNoByParam(user);
		return result;
	}

	@Override
	public int countPhoneByParam(UserDO userDO) {
		int result = userDao.countPhoneByParam(userDO);
		return result;
	}
	@Override
	public void cleanAuthorizeByUserId(UserDO userDO,String type){
		if("1".equals(type)){
			userDao.cleanInvestAuthorizeByUserId(userDO);
		}else if("2".equals(type)){
			userDao.cleanLoanAuthorizeByUserId(userDO);
		}
	}

}
