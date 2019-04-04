package com.autoserve.abc.web.module.screen.employee.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.convert.EmployeeConverter;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.employee.EmployeeVO;

/**
 * @author RJQ 2014/12/4 13:39.
 */
public class AddEmployee {

    @Resource
    private EmployeeService    employeeService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private OperateLogService  operateLogService;

    private static Logger      logger = LoggerFactory.getLogger(AddEmployee.class);

    public JsonBaseVO execute(@Params EmployeeVO vo, ParameterParser parser) {
        logger.info("employee vo is ： " + JSON.toJSONString(vo));
        JsonBaseVO result = new JsonBaseVO();

        //zString roleIds = parser.getString("empRole");
        //if (!StringUtil.isBlank(roleIds)) {//角色不能为空，前台已做控制，至少选取一个角色
        EmployeeDO employeeDO = EmployeeConverter.convert(vo);
        employeeDO.setEmpState(EntityState.STATE_ENABLE.getState());
        //List<Integer> idList = getIdList(roleIds);
        BaseResult baseResult = employeeService.createEmployee(employeeDO);
        if (baseResult.isSuccess()) {
            OperateLogDO operateLogDO = new OperateLogDO();
            operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
            operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
            operateLogDO.setOlModule("管理员管理");//操作模块
            operateLogDO.setOlOperateType("添加");//操作类型：添加/修改/删除
            operateLogDO.setOlContent("添加了一个管理员");//具体操作内容
            operateLogService.createOperateLog(operateLogDO);

            return ResultMapper.toBaseVO(baseResult);
        }
        //}

        result.setSuccess(false);
        result.setMessage(baseResult.getMessage());
        return result;
    }

    /**
     * 将字符串形式的多个id即1,2,3,转成list
     * 
     * @param roleIds id字符串
     * @return List<Integer>
     */
    private List<Integer> getIdList(String roleIds) {
        List<Integer> idList = new ArrayList<Integer>();
        String[] strId = roleIds.split(",");
        for (String id : strId) {
            idList.add(Integer.parseInt(id));
        }
        return idList;
    }
}
