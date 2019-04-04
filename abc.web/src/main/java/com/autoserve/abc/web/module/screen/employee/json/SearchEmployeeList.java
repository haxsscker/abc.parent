package com.autoserve.abc.web.module.screen.employee.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/2 14:14.
 */
public class SearchEmployeeList {
    @Resource
    private EmployeeService employeeService;

    private static Logger logger = LoggerFactory.getLogger(SearchEmployeeList.class);

    public JsonPageVO<Employee> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        logger.info("Employee list rows: " + rows + ", page: " + page);

        EmployeeDO employeeDO = new EmployeeDO();
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {//搜索
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    if ("empName".equals(field)) {
                        employeeDO.setEmpName(value);
                    } else if ("empRealName".equals(field)) {
                        employeeDO.setEmpRealName(value);
                    }
                }
            } catch (Exception e) {
                logger.error("客户经理信息－搜索查询 查询参数解析出错", e);
            }
        }

        PageResult<Employee> pageResult = employeeService.queryEmpWithRolesList(employeeDO, pageCondition);
        JsonPageVO<Employee> pageVO = new JsonPageVO<Employee>();
        pageVO.setRows(pageResult.getData());
        pageVO.setTotal(pageResult.getTotalCount());
        return pageVO;
    }
}
