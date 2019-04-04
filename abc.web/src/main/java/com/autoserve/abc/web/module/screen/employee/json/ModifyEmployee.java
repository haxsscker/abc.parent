package com.autoserve.abc.web.module.screen.employee.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.convert.EmployeeConverter;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.employee.EmployeeVO;

/**
 * @author RJQ 2014/12/5 16:12.
 */
public class ModifyEmployee {
    @Resource
    private EmployeeService    employeeService;

    @Resource
    private OperateLogService  operateLogService;

    @Autowired
    private HttpServletRequest request;

    private static Logger      logger = LoggerFactory.getLogger(AddEmployee.class);

    public JsonBaseVO execute(@Params EmployeeVO vo) {
        logger.info("employee vo is ： " + JSON.toJSONString(vo));
        JsonBaseVO result = new JsonBaseVO();

        String roleIds = vo.getEmpRole();
        //if (!StringUtil.isBlank(roleIds)) {//角色不能为空，前台已做控制，至少选取一个角色
        EmployeeDO employeeDO = EmployeeConverter.convert(vo);
        //List<Integer> idList = getIdList(roleIds);
        BaseResult baseResult = employeeService.modifyEmployee(employeeDO);
        if (baseResult.isSuccess()) {
            return result;
        }
        //   }

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("管理员管理");//操作模块
        operateLogDO.setOlOperateType("修改");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("修改了ID为" + vo.getEmpId() + "的管理员");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);

        result.setSuccess(false);
        result.setMessage("修改失败！");
        return result;
    }

    private List<Integer> getIdList(String roleIds) {
        List<Integer> idList = new ArrayList<Integer>();
        String[] strId = roleIds.split(",");
        for (String id : strId) {
            idList.add(Integer.parseInt(id));
        }
        return idList;
    }
}
