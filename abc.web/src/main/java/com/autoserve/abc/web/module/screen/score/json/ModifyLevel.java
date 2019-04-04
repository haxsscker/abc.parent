package com.autoserve.abc.web.module.screen.score.json;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.LevelDO;
import com.autoserve.abc.service.biz.intf.score.LevelService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.convert.LevelVOConverter;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.score.LevelVO;

/**
 * @author RJQ 2014/12/17 15:27.
 */
public class ModifyLevel {
    @Resource
    private LevelService levelService;

    public JsonBaseVO execute(ParameterParser params) {
        JsonBaseVO vo = new JsonBaseVO();
        String levels = params.getString("events");
        List<LevelVO> levelVOs = JSON.parseArray(levels, LevelVO.class);
        if (isOverLapInList(levelVOs)) {
            vo.setSuccess(false);
            vo.setMessage("等级分值区间不能重叠！");
            return vo;
        }

        List<LevelDO> levelDOs = LevelVOConverter.convertToDOList(levelVOs);
        BaseResult result = levelService.modifyLevel(levelDOs);
        vo = ResultMapper.toBaseVO(result);
        return vo;
    }

    /**
     * 判断等级列表之间是否存在区域重叠
     *
     * @param levelVOs 等级
     * @return
     */
    private boolean isOverLapInList(List<LevelVO> levelVOs) {
        boolean flag = false;
        for (int i = 0, j = levelVOs.size(); i < j - 1; ++i) {
            for (int k = i + 1; k < j; ++k) {
                if (isOverLapBetweenTwo(levelVOs.get(i), levelVOs.get(k))) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 判断两个等级之间是否存在区域重叠
     *
     * @param vo1
     * @param vo2
     * @return
     */
    private boolean isOverLapBetweenTwo(LevelVO vo1, LevelVO vo2) {
        boolean flag = false;
        if ((vo1.getSys_min_score().floatValue() <= vo2.getSys_min_score().floatValue() && vo1.getSys_max_score()
                .floatValue() >= vo2.getSys_min_score().floatValue())
                || vo1.getSys_min_score().floatValue() >= vo2.getSys_max_score().floatValue()
                && vo1.getSys_max_score().floatValue() <= vo2.getSys_max_score().floatValue()) {
            return true;
        }

        return flag;
    }
}
