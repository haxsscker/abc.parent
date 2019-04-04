package com.autoserve.abc.web.module.screen.score.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.dao.dataobject.ScoreDO;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author RJQ 2014/12/2 21:13.
 */
public class ModifyScoreItem {
    @Resource
    private ScoreService scoreService;

    @Resource
    private OperateLogService operateLogService;

    @Resource
    private HttpServletRequest request;

    public JsonBaseVO execute(@Param("scoreId") int scoreId, ParameterParser params) {
        ScoreDO scoreDO = new ScoreDO();
        scoreDO.setScoreId(scoreId);
        scoreDO.setScoreName(params.getString("scoreName"));
        scoreDO.setScoreCode(params.getString("scoreCode"));
        scoreDO.setScoreValue(params.getInt("scoreValue"));
        BaseResult result = scoreService.modifyScore(scoreDO);

        JsonBaseVO vo = new JsonBaseVO();
        vo.setSuccess(result.isSuccess());
        vo.setMessage(result.getMessage());

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("积分管理");//操作模块
        operateLogDO.setOlOperateType("修改");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("修改了ID为" + scoreId + "的积分类型");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);

        return vo;
    }
}
