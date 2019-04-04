package com.autoserve.abc.web.module.screen.score.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
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
 * @author RJQ 2014/12/2 18:39.
 */
public class AddScoreItem {
    @Resource
    private ScoreService scoreService;

    @Resource
    private OperateLogService operateLogService;

    @Resource
    private HttpServletRequest request;


    public JsonBaseVO execute(ParameterParser params) {
        ScoreDO scoreDO = new ScoreDO();
        scoreDO.setScoreName(params.getString("scoreName"));
        scoreDO.setScoreCode(params.getString("scoreCode"));
        scoreDO.setScoreValue(params.getInt("scoreValue"));
        BaseResult result = scoreService.createScore(scoreDO);

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("积分管理");//操作模块
        operateLogDO.setOlOperateType("添加");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("添加了一个积分类型");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);

        JsonBaseVO vo = new JsonBaseVO();
        vo.setSuccess(result.isSuccess());
        vo.setMessage(result.getMessage());
        return vo;
    }
}
