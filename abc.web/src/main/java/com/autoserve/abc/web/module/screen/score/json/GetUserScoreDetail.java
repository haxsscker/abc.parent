package com.autoserve.abc.web.module.screen.score.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreHistoryDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;
import com.autoserve.abc.service.biz.intf.score.ScoreHistoryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.ScoreHistoryVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.score.ScoreHistoryWithValueVO;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author RJQ 2014/12/15 21:58.
 */
public class GetUserScoreDetail {
    @Resource
    private ScoreHistoryService scoreHistoryService;

    public JsonPageVO<ScoreHistoryWithValueVO> execute(ParameterParser params) {
        JsonPageVO<ScoreHistoryWithValueVO> vo = new JsonPageVO<ScoreHistoryWithValueVO>();
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        Integer userId = params.getInt("userId");
        ScoreHistoryDO scoreHistoryDO = new ScoreHistoryDO();
        scoreHistoryDO.setShUserId(userId);
        PageResult<ScoreHistoryWithValueDO> pageResult = scoreHistoryService.queryScoreHistoryList(scoreHistoryDO, pageCondition);
        if(pageResult.isSuccess()){
        	for(ScoreHistoryWithValueDO shw : pageResult.getData()){
        		if(shw.getScoreId() == 4 ){
        			shw.setScoreValue(shw.getShValue().toString());
        		}
        	}
            vo.setRows(ScoreHistoryVOConverter.convertToList(pageResult.getData()));
            vo.setTotal(pageResult.getTotalCount());
            return vo;
        }

        vo.setTotal(0);
        vo.setRows(new ArrayList<ScoreHistoryWithValueVO>());
        return vo;
    }
}
