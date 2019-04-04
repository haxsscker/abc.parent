package com.autoserve.abc.web.module.screen.score.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreConfigDO;
import com.autoserve.abc.service.biz.intf.score.ScoreConfigService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/16 19:43.
 */
public class GetScoreManageList {
    @Resource
    private ScoreConfigService scoreConfigService;

    public JsonPageVO<ScoreConfigDO> execute(ParameterParser params) {
        JsonPageVO<ScoreConfigDO> vo = new JsonPageVO<ScoreConfigDO>();
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        PageResult<ScoreConfigDO> pageResult  = scoreConfigService.queryScoreConfigList(new ScoreConfigDO(), pageCondition);
        if(pageResult.isSuccess()){
            vo.setRows(pageResult.getData());
            vo.setTotal(pageResult.getTotalCount());
        }
        return vo;
    }
}
