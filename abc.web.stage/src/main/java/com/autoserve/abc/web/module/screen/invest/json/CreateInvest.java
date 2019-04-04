/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.invest.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserAssessService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 创建投资
 *
 * @author segen189 2015年1月13日 下午3:31:24
 */
public class CreateInvest {
	private static final Logger log = LoggerFactory
			.getLogger(CreateInvest.class);

	@Resource
	private InvestService investService;
	
	@Resource
	private InvestQueryService investQueryService;

	@Resource
	private DealRecordService dealRecordService;

	@Resource
	private UserAssessService userAssessService;

	@Resource
	private HttpSession session;
	@Resource
	private UserService userService;
	@Autowired
	private DeployConfigService deployConfigService;
	@Autowired
	private SysConfigService sysConfigService;
	@Resource
	private HttpServletRequest request;

	public JsonBaseVO execute(Context context, ParameterParser params,
			Navigator nav) {
		try {
			// 投资
			User user = (User) session.getAttribute("user");
			String verification = params.getString("Verification");
			String passw = params.getString("passw");
			if (user != null) { // 校验交易密码
				PlainResult<UserDO> userDoResult = userService.findById(user
						.getUserId());
				if (passw == null
						|| !CryptUtils.md5(passw).equals(
								userDoResult.getData().getUserDealPwd())) {
					BaseResult baseResult = new BaseResult();
					baseResult.setSuccess(false);
					baseResult.setMessage("交易密码不正确!");
					return directReturn(baseResult);
				}
			} else {
				nav.forwardTo(deployConfigService.getLoginUrl(request));
				return null;
			}

			Integer loanId = params.getInt("loanId");
			Integer flagLoan = params.getInt("flagLoan");
			Double investMoney = params.getDouble("investedMoney");
			Integer assId = params.getInt("assId");
			String investCode = params.getString("investCode");
			
			PlainResult<SysConfig> userInvestLevelResult = sysConfigService.querySysConfig(SysConfigEntry.USER_INVEST_LEVEL);
	        if (!userInvestLevelResult.isSuccess()) {
	        	BaseResult baseResult = new BaseResult();
				baseResult.setSuccess(false);
				baseResult.setMessage("查询风险配置信息失败!");
				return directReturn(baseResult);
	        }

	        if (0 != Integer.parseInt(userInvestLevelResult.getData().getConfValue()))
	        {
	        	BigDecimal dstzze = investService.dstzze(user.getUserId());
				// 判断投资是否合法
				if (userAssessService.isValidInvest(dstzze.doubleValue()
						+ investMoney, user.getUserId(), assId) < 1) {
					JsonBaseVO result = new JsonBaseVO();
					result.setMessage("您的投资金额超过您的风险评估等级!");
					result.setSuccess(false);
					result.setRedirectUrl("/account/myAccount/userAssessLevel");
					return result;
				}
	        }

			// 获取红包参数
			List<Integer> reds = new ArrayList<Integer>();
			int[] redsTemp = params.getInts("red", null);
			if (redsTemp != null) {
				for (int i = 0; i < redsTemp.length; i++) {
					reds.add(redsTemp[i]);
				}
			}
			BidType bidType = BidType.COMMON_LOAN;
			if (loanId != null && flagLoan == 1) { // 普通标
				bidType = BidType.COMMON_LOAN;
			}
			if (loanId != null && flagLoan == 2) { // 转让标
				bidType = BidType.TRANSFER_LOAN;
			}

			int userId = user.getUserId();
			if (bidType == null) {
				bidType = BidType.BUY_LOAN;
			}

			int bidId = params.getInt("loanId");
			Invest inv = new Invest();
			inv.setUserId(userId);
			inv.setBidType(bidType); // 设置标的类型
			
			if (BidType.COMMON_LOAN.equals(bidType)) {
				inv.setBidId(bidId);
				inv.setOriginId(bidId);
			} else {
				inv.setBidId(bidId);
				inv.setVerification(verification);
			}

			if (investMoney != null) {
				inv.setInvestMoney(BigDecimal.valueOf(investMoney));
			}
			
			if (null != investCode)
			{
				inv.setInvestCode(investCode);
			}
			
			//判断单标投资次数
			int maxInvOfOneLoan = 10;
			PlainResult<SysConfig> maxInvNum = sysConfigService.querySysConfig(SysConfigEntry.MAX_INVEST_OF_ONE_LOAN);
	        if (maxInvNum.isSuccess()) {
	        	maxInvOfOneLoan = Integer.parseInt(maxInvNum.getData().getConfValue());
	        }
	        if (investService.investNumOfOneLoan(inv) >= maxInvOfOneLoan)
	        {
	        	JsonBaseVO result = new JsonBaseVO();
				result.setMessage("您的单标投资次数超过系统限制!");
				result.setSuccess(false);
				return result;
	        }

			PlainResult<Integer> investCreateResult = investService
					.createInvest(inv, reds);
			String innerSeqNo = inv.getInnerSeqNo();
			if (!investCreateResult.isSuccess()) {
				return directReturn(investCreateResult);
			}

			// 支付
			if (BidType.BUY_LOAN.equals(inv.getBidType())) {
				return JsonBaseVO.SUCCESS;
			} else {
				PlainResult<Invest> investQueryResult = investQueryService
						.queryById(investCreateResult.getData());
				if (!investQueryResult.isSuccess()) {
					return directReturn(investQueryResult);
				}
				BaseResult invokeResult = new BaseResult();
				if (investCreateResult.isSuccess()) {
					invokeResult.setSuccess(true);
					invokeResult.setMessage("投资成功");
					invokeResult.setMessage(innerSeqNo);
				}
				return ResultMapper.toBaseVO(invokeResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("投资失败", e);
		}

		return directReturn(new BaseResult().setError(
				CommonResultCode.BIZ_ERROR, "投资失败"));
	}

	private JsonBaseVO directReturn(BaseResult serviceResult) {
		JsonBaseVO result = new JsonBaseVO();
		result.setMessage(serviceResult.getMessage());
		result.setSuccess(serviceResult.isSuccess());
		return result;
	}

}
