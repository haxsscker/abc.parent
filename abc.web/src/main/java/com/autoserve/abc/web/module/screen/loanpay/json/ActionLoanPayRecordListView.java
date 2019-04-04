package com.autoserve.abc.web.module.screen.loanpay.json;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.convert.AccountConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 还款计划
 *
 * @author liuwei 2014年12月29日 下午12:23:35
 */
public class ActionLoanPayRecordListView {

	@Resource
	private PaymentPlanService paymentPlanService;
	@Resource
	private EmployeeService employeeService;
	@Resource
    private DealRecordService dealRecordService;
	@Resource
    private AccountInfoService    accountInfoService;
	public JsonPageVO<DealRecordDO> execute(@Param("rows") int rows,
			@Param("page") int page, Context context, ParameterParser params) {
		JsonPageVO<DealRecordDO> vo = new JsonPageVO<DealRecordDO>();
		PageCondition pageCondition = new PageCondition(page, rows);
		Integer empId = LoginUserUtil.getEmpId();
		// 担保机构账户
        AccountInfoDO accountDo =  accountInfoService.queryByAccountMark(empId,UserType.PARTNER.type);
        Account guarAccount = AccountConverter.toUserAccount(accountDo);
		String searchForm = params.getString("SearchForm");
		DealRecordDO dealRecord = new DealRecordDO();
		dealRecord.setDrPayAccount(guarAccount.getAccountNo());
		dealRecord.setDrReceiveAccount(guarAccount.getAccountNo());
		try {
	        if (StringUtils.isNotBlank(searchForm)) {
	            JSONObject searchFormJson = JSON.parseObject(searchForm);
	            JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
	
	            for (Object item : itemsArray) {
	                JSONObject itemJson = JSON.parseObject(String.valueOf(item));
	                String field = String.valueOf(itemJson.get("Field"));
	                String value = String.valueOf(itemJson.get("Value"));
	
	                // 交易时间
	                if ("startTradeDate".equals(field)) {
	                	dealRecord.setStartTradeDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
	                }// 交易时间
	                else if ("endTradeDate".equals(field)) {
	                	dealRecord.setEndTradeDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
	                }
	            }
	        } 
        }catch (Exception e) {
           
        }

        PageResult<DealRecordDO> result = dealRecordService
				.queryGuarRecord(dealRecord, pageCondition);
        vo.setTotal(result.getTotalCount());
		vo.setRows(result.getData());
		return vo;
	}
}
