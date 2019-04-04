package com.autoserve.abc.web.pipeline.valve;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;

/**
 * 权限验证
 */
public class AbcPermissionValve extends AbstractValve {
    private static final Logger log = LoggerFactory.getLogger(AbcPermissionValve.class);

    @Autowired
    private HttpServletRequest  request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private EmployeeService     employeeService;

    @Override
    public void invoke(PipelineContext pipelineContext) throws Exception {
        try {
            TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);

            // 登录获取用户信息失败，无需验证权限
            if (LoginUserInfoHelper.getLoginUserInfo() == null) {
                return;
            }

            // 不在白名单，并且没有权限，则内部重定向到错误页面
            if (!isAuthorized()) {
                rundata.forwardTo("error/noPermission").end();
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            pipelineContext.invokeNext();
        }
    }

    private boolean isAuthorized() {

        return true;
    }

}
