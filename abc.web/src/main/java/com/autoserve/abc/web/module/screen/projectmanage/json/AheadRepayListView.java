package com.autoserve.abc.web.module.screen.projectmanage.json;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AheadRepay;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.service.biz.intf.loan.repay.AheadRepayService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;

public class AheadRepayListView {

	@Autowired
	private AheadRepayService aheadRepayService;

	public JsonPageVO<AheadRepay> execute(ParameterParser params,
			Context context) {
		AheadRepay aheadRepay = new AheadRepay();
		String loanNo = params.getString("loanNo");
		if(StringUtils.isNotBlank(loanNo)){
			LoanDO loan = new LoanDO();
			loan.setLoanNo(loanNo);
			aheadRepay.setLoanDO(loan);
		}
		int page = params.getInt("page");
		int rows = params.getInt("rows");
		
		PageResult<AheadRepay> pageResult = aheadRepayService.findPage(
				aheadRepay, new PageCondition(page, rows));
		return ResultMapper.toPageVO(pageResult);
	}
}
