package com.autoserve.abc.web.module.screen.score;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.LevelDO;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.intf.score.LevelService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.convert.LevelVOConverter;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.score.LevelVO;

/**
 * @author RJQ 2014/12/17 11:51.
 */
public class LevelManageEdit {
    @Resource
    private LevelService       levelService;

    @Resource
    private OperateLogService  operateLogService;

    @Resource
    private HttpServletRequest request;

    public JsonBaseVO execute(Context context, ParameterParser param) {
        String flag = param.getString("flag");
        JsonBaseVO vo = new JsonBaseVO();
        if ("add".equals(flag)) {
            LevelVO lvo = new LevelVO();
            lvo.setSys_leve_pic(param.getString("sys_leve_pic"));
            lvo.setSys_level_name(param.getString("sys_level_name"));
            lvo.setSys_max_score(new BigDecimal(param.getString("sys_max_score")));
            lvo.setSys_min_score(new BigDecimal(param.getString("sys_min_score")));
            List<LevelDO> list = levelService.findLevelByName(param.getString("sys_level_name"));
            BaseResult result = levelService.createLevel(LevelVOConverter.convertToDO(lvo));
            vo = ResultMapper.toBaseVO(result);
            OperateLogDO operateLogDO = new OperateLogDO();
            operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());// 操作人ID
            operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));// 操作人IP地址
            operateLogDO.setOlModule("积分管理-等级管理");// 操作模块
            operateLogDO.setOlOperateType("添加");// 操作类型：添加/修改/删除
            operateLogDO.setOlContent("添加了一个等级");// 具体操作内容
            operateLogService.createOperateLog(operateLogDO);

        } else if ("modfiy".equals(flag)) {
            LevelVO lvo = LevelVOConverter.convertToVO(levelService.findLevelByID(param.getInt("levId")).getData());
            context.put("level", lvo);

        } else if ("edit".equals(flag)) {
            LevelVO lvo = new LevelVO();
            lvo.setSys_level_id(param.getInt("sys_level_id"));
            lvo.setSys_leve_pic(param.getString("sys_leve_pic"));
            lvo.setSys_level_name(param.getString("sys_level_name"));
            lvo.setSys_max_score(new BigDecimal(param.getString("sys_max_score")));
            lvo.setSys_min_score(new BigDecimal(param.getString("sys_min_score")));
            LevelDO ldo = LevelVOConverter.convertToDO(lvo);
            BaseResult result = levelService.editLevel(ldo);
            vo = ResultMapper.toBaseVO(result);
            OperateLogDO operateLogDO = new OperateLogDO();
            operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());// 操作人ID
            operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));// 操作人IP地址
            operateLogDO.setOlModule("积分管理-等级管理");// 操作模块
            operateLogDO.setOlOperateType("修改");// 操作类型：添加/修改/删除
            operateLogDO.setOlContent("修改了一个等级");// 具体操作内容
            operateLogService.createOperateLog(operateLogDO);
        } else if ("del".equals(flag)) {
            BaseResult result = levelService.delLevel(param.getInt("levId"));
            vo = ResultMapper.toBaseVO(result);
            OperateLogDO operateLogDO = new OperateLogDO();
            operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());// 操作人ID
            operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));// 操作人IP地址
            operateLogDO.setOlModule("积分管理-等级管理");// 操作模块
            operateLogDO.setOlOperateType("删除");// 操作类型：添加/修改/删除
            operateLogDO.setOlContent("删除了一个等级");// 具体操作内容
            operateLogService.createOperateLog(operateLogDO);
        }
        return vo;
    }

}
