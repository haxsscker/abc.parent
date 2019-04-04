package com.autoserve.abc.web.module.screen.account.myLoan.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.AheadRepay;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.loan.repay.AheadRepayService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.Md5Util;

/**
 * 提前还款申请
 * 
 * @author zhangkang
 *
 */
public class AheadRepayApply {
	@Autowired
	private AheadRepayService aheadRepayService;
	@Autowired
	private HttpSession session;
	@Resource
	private UserService userService;

	public BaseResult execute(Context context, ParameterParser params) {
		BaseResult result = BaseResult.SUCCESS;
		int loanId = params.getInt("loanId");
		String dealPsw = params.getString("dealPsw");
		User user = (User) session.getAttribute("user");
		PlainResult<UserDO> userDO = userService.findById(user.getUserId());
		if(!userDO.getData().getUserDealPwd().equals(CryptUtils.md5(dealPsw))){
			result.setSuccess(false);
			result.setMessage("交易密码错误");
			return result;
		}
		
		AheadRepay aheadRepay = new AheadRepay();
		aheadRepay.setUserId(user.getUserId());
		aheadRepay.setLoanId(loanId);
		return aheadRepayService.apply(aheadRepay);
	}
}
