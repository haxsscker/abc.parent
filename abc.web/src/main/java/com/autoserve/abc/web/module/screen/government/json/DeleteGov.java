package com.autoserve.abc.web.module.screen.government.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author RJQ 2014/12/6 11:16.
 */
public class DeleteGov {
    @Resource
    private GovernmentService govService;

    @Resource
    private OperateLogService operateLogService;

    @Resource
    private HttpServletRequest request;

    private static Logger logger = LoggerFactory.getLogger(DeleteGov.class);

    public JsonBaseVO execute(@Param("govId") Integer govId) {
        logger.info("删除机构 empId={}, govId={}", LoginUserUtil.getEmpId(), govId);
        BaseResult baseResult = govService.removeGovernment(govId);

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("机构管理");//操作模块
        operateLogDO.setOlOperateType("删除");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("删除了ID为"+ govId +"的机构");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);

        return ResultMapper.toBaseVO(baseResult);
    }
}
