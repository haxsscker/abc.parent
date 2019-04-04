package com.autoserve.abc.web.module.screen.score.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author RJQ 2014/12/16 17:26.
 */
public class ModifyUserScore {
    @Resource
    private UserService userService;

    @Resource
    private OperateLogService operateLogService;

    @Resource
    private HttpServletRequest request;

    private static Logger logger = LoggerFactory.getLogger(ModifyUserScore.class);

    public JsonBaseVO execute(ParameterParser params) {
        JsonBaseVO vo = new JsonBaseVO();
        if(params.getInt("userId") == 0 || params.getString("scoreCode") == null){
            logger.warn("修改用户积分参数不正确 userId={}, scoreCode={}", params.getInt("userId"), params.getString("scoreCode"));
            vo.setSuccess(false);
            vo.setMessage("修改失败");
            return vo;
        }

        Integer userId = params.getInt("userId");
        String scoreCode = params.getString("scoreCode");
        BaseResult result = userService.modifyUserScore(userId, scoreCode, null);
        if (result.isSuccess()) {
            vo.setMessage("修改用户积分成功！");
            return vo;
        }

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("积分管理");//操作模块
        operateLogDO.setOlOperateType("修改");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("修改了ID为" + userId + "的用户的积分");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);

        return vo;
    }
}
