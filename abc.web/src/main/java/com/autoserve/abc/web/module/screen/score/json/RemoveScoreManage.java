package com.autoserve.abc.web.module.screen.score.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.intf.score.ScoreConfigService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author RJQ 2014/12/16 21:19.
 */
public class RemoveScoreManage {
    @Resource
    private ScoreConfigService scoreConfigService;

    @Resource
    private OperateLogService operateLogService;

    @Resource
    private HttpServletRequest request;

    public JsonBaseVO execute(@Param("scId") Integer scId) {
        BaseResult baseResult = scoreConfigService.removeScoreConfig(scId);
        JsonBaseVO vo = ResultMapper.toBaseVO(baseResult);
        vo.setMessage("删除成功！");

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("积分管理");//操作模块
        operateLogDO.setOlOperateType("删除");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("删除了ID为" + scId + "的积分配置");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);

        return vo;
    }
}
