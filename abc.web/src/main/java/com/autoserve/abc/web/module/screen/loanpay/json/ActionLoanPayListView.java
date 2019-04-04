package com.autoserve.abc.web.module.screen.loanpay.json;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanJDO;
import com.autoserve.abc.dao.dataobject.search.PaymentPlanSearchDO;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.google.common.collect.Lists;

/**
 * 还款计划
 *
 * @author liuwei 2014年12月29日 下午12:23:35
 */
public class ActionLoanPayListView {

	@Resource
	private PaymentPlanService paymentPlanService;
	@Resource
	private EmployeeService employeeService;
	public JsonPageVO<PaymentPlanJDO> execute(@Param("rows") int rows,
			@Param("page") int page, Context context, ParameterParser params) {
		JsonPageVO<PaymentPlanJDO> vo = new JsonPageVO<PaymentPlanJDO>();
		PageCondition pageCondition = new PageCondition(page, rows);
		Integer empId = LoginUserUtil.getEmpId();
		PlainResult<EmployeeDO> employeeDO = employeeService.findById(empId);
		String searchForm = params.getString("SearchForm");
		PaymentPlanSearchDO searchDO = new PaymentPlanSearchDO();
		if(!"kefu".equals(employeeDO.getData().getEmpName())){
			searchDO.setGovId(employeeDO.getData().getEmpOrgId());
		}
		if (StringUtils.isNotBlank(searchForm)) {

			JSONObject searchFormJson = JSON.parseObject(searchForm);
			JSONArray itemsArray = JSON.parseArray(String
					.valueOf(searchFormJson.get("Items")));

			for (Object item : itemsArray) {
				JSONObject itemJson = JSON.parseObject(String.valueOf(item));
				String field = String.valueOf(itemJson.get("Field"));
				String value = String.valueOf(itemJson.get("Value"));

				if ("loanNO".equals(field)) {
					searchDO.setLoanNO(value);
				} else if ("payTime1".equals(field)) {
					Date payTime1 = DateUtil.parseDate(value,
							DateUtil.DATE_FORMAT);
					searchDO.setPayTime1(DateUtil.setTime(payTime1, 0, 0, 0));
				} else if ("payTime2".equals(field)) {
					Date payTime2 = DateUtil.parseDate(value,
							DateUtil.DATE_FORMAT);
					searchDO.setPayTime2(DateUtil.setTime(payTime2, 23, 59, 59));
				} else if ("payType".equals(field)) {
					if (!StringUtil.isBlank(value)) {
						searchDO.setPayType(Integer.parseInt(value));
					}
				} else if ("payState".equals(field)) {
					List<Integer> payStates = Lists.newArrayList();
					if (StringUtils.isBlank(value)) {
						// 查询全部还款状态
						payStates.addAll(Arrays.asList(0, 1, 2));
					} else {
						payStates.add(Integer.parseInt(value));
					}
					searchDO.setPayStates(payStates);
				}

			}

		}

		PageResult<PaymentPlanJDO> result = paymentPlanService.queryPlanList2(
				searchDO, pageCondition);
		if (!result.isSuccess()) {
			vo.setSuccess(false);
			vo.setMessage(result.getMessage());
			return vo;
		}

		List<PaymentPlanJDO> list = result.getData();
		for (PaymentPlanJDO paymentPlanJDO : list) {
			paymentPlanJDO.setPro_pay_date_str(DateUtil.formatDate(
					paymentPlanJDO.getPro_pay_date(), "yyyy-MM-dd"));
		}
		vo.setTotal(result.getTotalCount());
		vo.setRows(list);
		return vo;
	}
}
