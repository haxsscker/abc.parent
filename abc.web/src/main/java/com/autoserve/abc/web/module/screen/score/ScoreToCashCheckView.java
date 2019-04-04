package com.autoserve.abc.web.module.screen.score;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordDO;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordWithNameDO;
import com.autoserve.abc.service.biz.intf.score.ScoreUsageRecordService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.ScoreUsageRecordVOConverter;
import com.autoserve.abc.web.vo.score.ScoreUsageRecordVO;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/17 11:13.
 */
public class ScoreToCashCheckView {
    @Resource
    private ScoreUsageRecordService scoreService;

    public void execute(Context context, ParameterParser params) {
        Integer scoId = params.getInt("scoId");
        if (!scoId.equals(0)) {
            ScoreUsageRecordDO recordDO = new ScoreUsageRecordDO();
            recordDO.setSurId(scoId);
            PlainResult<ScoreUsageRecordWithNameDO> result = scoreService.queryScoreUsageRecordById(scoId);
            if (result.isSuccess()) {
                ScoreUsageRecordVO vo = ScoreUsageRecordVOConverter.convertDOToVO(result.getData());
                context.put("sur", vo);
            }
        }
    }

}
