package com.autoserve.abc.web.module.screen.account.myLoan;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AheadRepay;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.loan.repay.AheadRepayService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.module.screen.account.mySetting.SysMessageInfoVO;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 提前还款申请记录
 * 
 * @author zhangkang
 *
 */
public class AheadRepayRecord {
	@Autowired
	private AheadRepayService aheadRepayService;
	@Autowired
	private HttpSession session;

	public void execute(Context context, ParameterParser params) {
		AheadRepay aheadRepay = new AheadRepay();

		User user = (User) session.getAttribute("user");
		aheadRepay.setUserId(user.getUserId());

		Integer currentPage = params.getInt("currentPage");
		Integer pageSize = params.getInt("pageSize");
		
		PageResult<AheadRepay> pageResult = aheadRepayService.findPage(
				aheadRepay, new PageCondition(currentPage, pageSize));
		Pagebean<AheadRepay> pagebean = new Pagebean<AheadRepay>(currentPage,
				pageResult.getPageSize(), pageResult.getData(),
				pageResult.getTotalCount());
		context.put("pagebean", pagebean);
	}
}
