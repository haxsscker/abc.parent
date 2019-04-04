package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.LevelDO;
import com.autoserve.abc.web.vo.score.LevelVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/17 11:57.
 */
public class LevelVOConverter {
    public static LevelVO convertToVO(LevelDO level){
        LevelVO vo = new LevelVO();
        vo.setSys_level_id(level.getLevId());
        vo.setSys_level_name(level.getLevName());
        vo.setSys_min_score(level.getLevMinScore());
        vo.setSys_max_score(level.getLevMaxScore());
        vo.setSys_leve_pic(level.getLevIcon());

        return vo;
    }

    public static LevelDO convertToDO(LevelVO vo){
        LevelDO levelDO = new LevelDO();
        levelDO.setLevId(vo.getSys_level_id());
        levelDO.setLevName(vo.getSys_level_name());
        levelDO.setLevMinScore(vo.getSys_min_score());
        levelDO.setLevMaxScore(vo.getSys_max_score());
        levelDO.setLevIcon(vo.getSys_leve_pic());

        return levelDO;
    }

    public static List<LevelVO> convertToList(List<LevelDO> levelDOs) {
        List<LevelVO> result = new ArrayList<LevelVO>();
        if (levelDOs == null || levelDOs.isEmpty()) {
            return result;
        }
        for (LevelDO levelDO : levelDOs) {
            result.add(convertToVO(levelDO));
        }
        return result;
    }

    public static List<LevelDO> convertToDOList(List<LevelVO> levelVOs) {
        List<LevelDO> result = new ArrayList<LevelDO>();
        if (levelVOs == null || levelVOs.isEmpty()) {
            return result;
        }
        for (LevelVO LevelVO : levelVOs) {
            result.add(convertToDO(LevelVO));
        }
        return result;
    }
}
