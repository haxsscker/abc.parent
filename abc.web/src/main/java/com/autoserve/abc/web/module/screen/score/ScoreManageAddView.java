package com.autoserve.abc.web.module.screen.score;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.ScoreConfigDO;
import com.autoserve.abc.service.biz.intf.score.ScoreConfigService;
import com.autoserve.abc.service.biz.result.PlainResult;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/16 20:39.
 */
public class ScoreManageAddView {
    @Resource
    private ScoreConfigService scoreService;

    public void execute(Context context, ParameterParser params){
        Integer scId = params.getInt("scId");
        if(!scId.equals(0)){//参数不为空，修改页面，取出详细信息
            PlainResult<ScoreConfigDO> plainResult = scoreService.findScoreConfigById(scId);
            if(plainResult.isSuccess()){
                ScoreConfigDO scoreConfigDO = plainResult.getData();
                context.put("sc", scoreConfigDO);
            }
        }
    }
}
