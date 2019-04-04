package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.score.ScoreHistoryWithValueVO;
import net.sf.cglib.beans.BeanCopier;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/16 15:34.
 */
public class ScoreHistoryVOConverter {
    public static ScoreHistoryWithValueVO convertToVO(ScoreHistoryWithValueDO sdo){
        ScoreHistoryWithValueVO vo = new ScoreHistoryWithValueVO();
        BeanCopier beanCopier = BeanCopier.create(ScoreHistoryWithValueDO.class, ScoreHistoryWithValueVO.class, false);
        beanCopier.copy(sdo,vo, null);
        vo.setCreateTime(new DateTime(sdo.getCreateTime()).toString(DateUtil.DATE_FORMAT));

        return vo;
    }

    public static List<ScoreHistoryWithValueVO> convertToList(List<ScoreHistoryWithValueDO> sdos){
        if (sdos == null || sdos.isEmpty())
            throw new BusinessException("传入的list为空");
        List<ScoreHistoryWithValueVO> result = new ArrayList<ScoreHistoryWithValueVO>();
        for (ScoreHistoryWithValueDO sdo : sdos) {
            result.add(convertToVO(sdo));
        }
        return result;
    }
}
