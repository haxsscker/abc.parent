package com.autoserve.abc.web.module.screen.government.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.convert.GovernmentVOConverter;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.government.GovernmentVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author RJQ 2014/12/10 14:11.
 */
public class AddGov {
    @Resource
    private GovernmentService governmentService;

    @Resource
    private OperateLogService operateLogService;

    @Resource
    private HttpServletRequest request;

    private static Logger logger = LoggerFactory.getLogger(AddGov.class);

    public JsonBaseVO execute(@Param("org") String org) {
        JsonBaseVO vo = new JsonBaseVO();
        logger.info("添加机构 empId={}, org={}", LoginUserUtil.getEmpId(), org);
        if (org == null) {
            vo.setSuccess(false);
            vo.setMessage("参数不正确");
            logger.warn("添加机构参数不正确 org={}, empId={}", org, LoginUserUtil.getEmpId());
            return vo;
        }

        GovernmentVO gov = JSON.parseObject(org, GovernmentVO.class);
        Integer govAddEmpId = LoginUserInfoHelper.getLoginUserInfo().getEmpId();
        gov.setGovAddEmp(govAddEmpId);
        GovPlainJDO govPlainJDO = GovernmentVOConverter.convertToGovPlainDO(gov);

        BaseResult result = governmentService.createGovernment(govPlainJDO);
        vo = ResultMapper.toBaseVO(result);

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("机构管理");//操作模块
        operateLogDO.setOlOperateType("添加");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("添加了一个机构");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);

        return vo;
    }
}
