package com.autoserve.abc.web.module.screen.selfprove;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.service.biz.convert.CompanyCustomerConverter;
import com.autoserve.abc.service.biz.entity.CompanyCustomer;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.CompanyCustomerVOConverter;
import com.autoserve.abc.web.vo.company.CompanyCustomerVO;

public class CommpanyBaseInfoAddView {

	@Resource
	private CompanyCustomerService companyCustomerService;

	public void execute(Context context, ParameterParser params) {
		Integer cinId=params.getInt("cinId");
		if (cinId != 0) {
			PlainResult<CompanyCustomerDO> plainResult = companyCustomerService
					.findById(cinId);
			if (plainResult.isSuccess()) {
				CompanyCustomer companyCustomer = CompanyCustomerConverter
						.toCompanyCustomer(plainResult.getData());
				CompanyCustomerVO companyCustomerVO = CompanyCustomerVOConverter
						.convertToCompanyCustomerVO(companyCustomer);
				context.put("companyCustomerVO", companyCustomerVO);
			}
		}
	}
}
