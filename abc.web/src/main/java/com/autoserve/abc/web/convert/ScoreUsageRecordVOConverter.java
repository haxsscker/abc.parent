package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.ScoreUsageRecordWithNameDO;
import com.autoserve.abc.service.biz.entity.ScoreUsageRecord;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.score.ScoreUsageRecordVO;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/16 21:50.
 */
public class ScoreUsageRecordVOConverter {
    public static ScoreUsageRecordVO convertDOToVO(ScoreUsageRecordWithNameDO record) {
        ScoreUsageRecordVO vo = new ScoreUsageRecordVO();
        vo.setSco_to_cash(record.getSurExchangeCash());
        vo.setCst_user_name(record.getUserName());
//        vo.setSco_check_idear(record.getSurReviewNote());
        vo.setSco_check_state(ReviewState.valueOf(record.getSurReviewState()).getDes());
        vo.setSco_score_id(record.getSurId());
        vo.setSco_to_score(record.getSurExchangeScore());
        if (record.getSurExchangeDate() != null) {
            vo.setSco_to_cash_date(new DateTime(record.getSurExchangeDate()).toString(DateUtil.DATE_FORMAT));
        }

        return vo;
    }

    public static List<ScoreUsageRecordVO> convertDOListToVOList(List<ScoreUsageRecordWithNameDO> recordDOs) {
        List<ScoreUsageRecordVO> result = new ArrayList<ScoreUsageRecordVO>();
        if (recordDOs == null || recordDOs.isEmpty()) {
            return result;
        }
        for (ScoreUsageRecordWithNameDO recordDO : recordDOs) {
            result.add(convertDOToVO(recordDO));
        }
        return result;
    }


    public static ScoreUsageRecordVO convertToVO(ScoreUsageRecord record) {
        ScoreUsageRecordVO vo = new ScoreUsageRecordVO();
        vo.setCst_user_name(record.getUserName());
        vo.setSco_to_cash(record.getSurExchangeCash());
        vo.setSco_check_idear(record.getReviewNote());
        vo.setSco_check_state(record.getSurReviewState().getDes());
        vo.setSco_score_id(record.getSurId());
        vo.setSco_to_score(record.getSurExchangeScore());
        if (record.getReviewDate() != null) {
            vo.setSco_check_date(new DateTime(record.getReviewDate()).toString(DateUtil.DATE_FORMAT));
        }
        if (record.getSurExchangeDate() != null) {
            vo.setSco_to_cash_date(new DateTime(record.getSurExchangeDate()).toString(DateUtil.DATE_FORMAT));
        }

        return vo;
    }

    public static List<ScoreUsageRecordVO> convertToVOList(List<ScoreUsageRecord> recordDOs) {
        List<ScoreUsageRecordVO> result = new ArrayList<ScoreUsageRecordVO>();
        if (recordDOs == null || recordDOs.isEmpty()) {
            return result;
        }
        for (ScoreUsageRecord record : recordDOs) {
            result.add(convertToVO(record));
        }
        return result;
    }

    public static ScoreUsageRecord convertVOToEntity(ScoreUsageRecordVO vo) {
        ScoreUsageRecord record = new ScoreUsageRecord();
        record.setUserName(vo.getCst_user_name());
        record.setSurExchangeScore(vo.getSco_to_score());
        record.setSurExchangeCash(vo.getSco_to_cash());
        record.setReviewNote(vo.getSco_check_idear());
        if (vo.getSco_check_state() != null) {
            record.setSurReviewState(ReviewState.valueOf(Integer.parseInt(vo.getSco_check_state())));
        }
        record.setSurId(vo.getSco_score_id());
        if (vo.getSco_check_date() != null) {
            record.setReviewDate(DateUtil.parseDate(vo.getSco_check_date(), DateUtil.DATE_FORMAT));
        }
        if (vo.getSco_to_cash_date() != null) {
            record.setSurExchangeDate(DateUtil.parseDate(vo.getSco_to_cash_date(), DateUtil.DATE_FORMAT));
        }

        return record;
    }
}
