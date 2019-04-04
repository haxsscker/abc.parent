package com.autoserve.abc.web.module.screen.score.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.ScoreDO;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonPlainVO;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/16 17:14.
 */
public class GetScoreItem {
    @Resource
    private ScoreService scoreService;

    public JsonPlainVO<ScoreDO> execute(ParameterParser params) {
        JsonPlainVO<ScoreDO> vo = new JsonPlainVO<ScoreDO>();
        String scoreCode = params.getString("scoreCode");
        PlainResult<ScoreDO> plainResult = scoreService.findByScoreCode(scoreCode);
        if (plainResult.isSuccess()) {
            ScoreDO scoreDO = plainResult.getData();
            vo.setData(scoreDO);
        }
        return vo;
    }
}
