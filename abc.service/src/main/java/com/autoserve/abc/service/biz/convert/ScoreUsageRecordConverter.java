package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.ScoreUsageRecordDO;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordWithNameDO;
import com.autoserve.abc.service.biz.entity.ScoreUsageRecord;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.exception.BusinessException;
import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/3 14:06.
 */
public class ScoreUsageRecordConverter {
    public static ScoreUsageRecord convertDOToScoreUsageRecord(ScoreUsageRecordDO recordDO) {
        ScoreUsageRecord record = new ScoreUsageRecord();
        BeanCopier beanCopier = BeanCopier.create(ScoreUsageRecordDO.class, ScoreUsageRecord.class, false);
        beanCopier.copy(recordDO, record, null);
        record.setSurReviewState(ReviewState.valueOf(recordDO.getSurReviewState()));

        return record;
    }

    public static ScoreUsageRecordDO convertScoreUsageRecordToDO(ScoreUsageRecord record) {
        ScoreUsageRecordDO recordDO = new ScoreUsageRecordDO();
        BeanCopier beanCopier = BeanCopier.create(ScoreUsageRecord.class, ScoreUsageRecordDO.class, false);
        beanCopier.copy(record, recordDO, null);
        recordDO.setSurReviewState(record.getSurReviewState() == null ? null : record.getSurReviewState().getState());

        return recordDO;
    }

    public static List<ScoreUsageRecord> convertList(List<ScoreUsageRecordDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<ScoreUsageRecord> result = new ArrayList<ScoreUsageRecord>();
        for (ScoreUsageRecordDO recordDO : list) {
            result.add(convertDOToScoreUsageRecord(recordDO));
        }
        return result;
    }


    public static ScoreUsageRecord convertNameDOtoScoreUsageRecord(ScoreUsageRecordWithNameDO recordDO) {
        ScoreUsageRecord record = new ScoreUsageRecord();
        BeanCopier beanCopier = BeanCopier.create(ScoreUsageRecordWithNameDO.class, ScoreUsageRecord.class, false);
        beanCopier.copy(recordDO, record, null);
        record.setSurReviewState(ReviewState.valueOf(recordDO.getSurReviewState()));

        return record;
    }

    public static List<ScoreUsageRecord> convertNameList(List<ScoreUsageRecordWithNameDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<ScoreUsageRecord> result = new ArrayList<ScoreUsageRecord>();
        for (ScoreUsageRecordWithNameDO record : list) {
            result.add(convertNameDOtoScoreUsageRecord(record));
        }
        return result;
    }
}
