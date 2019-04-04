package com.autoserve.abc.web.module.screen.selfprove;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.convert.CompanyCustomerConverter;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.convert.CompanyCustomerVOConverter;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.company.CompanyCustomerVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author RJQ 2014/12/10 14:11.
 */
public class SaveCommpanyBaseInfo {
	@Resource
	private CompanyCustomerService companyCustomerService;

	@Resource
	private OperateLogService operateLogService;

	@Resource
	private HttpServletRequest request;

	public JsonBaseVO execute(@Params CompanyCustomerVO cvo,
			ParameterParser param) {
		JsonBaseVO vo = new JsonBaseVO();
		BaseResult result = companyCustomerService
				.createCompanyCustomer(CompanyCustomerConverter
						.convertToCompanyCustomerDO(CompanyCustomerVOConverter
								.toCompanyCustomer(cvo)));
		vo = ResultMapper.toBaseVO(result);

		OperateLogDO operateLogDO = new OperateLogDO();
		operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());// 操作人ID
		operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));// 操作人IP地址
		operateLogDO.setOlModule("自营认证管理-客户查询-公司客户-添加");// 操作模块
		operateLogDO.setOlOperateType("添加");// 操作类型：添加/修改/删除
		operateLogDO.setOlContent("添加了一个公司客户");// 具体操作内容
		operateLogService.createOperateLog(operateLogDO);

		return vo;
	}
}
