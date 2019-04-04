package com.autoserve.abc.web.module.screen.score;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.ScoreDO;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author RJQ 2014/12/26 18:11.
 */
public class ScoreTypeAddOrEditView {
    @Autowired
    private ScoreService scoreService;

    public void execute(Context context, ParameterParser params) {
        boolean editFlag = params.getBoolean("editFlag");
        if (editFlag) {
            Integer scoreId = params.getInt("scoreId");
            PlainResult<ScoreDO> plainResult = scoreService.findById(scoreId);
            if(plainResult.isSuccess()){
                context.put("score", plainResult.getData());
            }
        }
    }
}
