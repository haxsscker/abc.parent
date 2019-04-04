/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.ActivityRecordDO;
import com.autoserve.abc.service.biz.entity.ActivityRecord;
import com.autoserve.abc.service.biz.enums.ActivityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Invest与InvestDO互相转换
 *
 * @author segen189 2014年11月21日 下午8:47:13
 */
public class ActivityRecordConverter {

    public static ActivityRecordDO toActivityRecordDO(ActivityRecord activityRecord) {
        if (activityRecord == null) {
            return null;
        }

        ActivityRecordDO activityRecordDO = new ActivityRecordDO();

        activityRecordDO.setArId(activityRecord.getId());
        activityRecordDO.setArType(activityRecord.getActivityType().getType());
        activityRecordDO.setArForeignId(activityRecord.getForeignId());
        activityRecordDO.setArCreatetime(activityRecord.getCreatetime());
        activityRecordDO.setArCreator(activityRecord.getCreator());
        activityRecordDO.setArEndtime(activityRecord.getEndtime());

        return activityRecordDO;
    }

    public static ActivityRecord toActivityRecord(ActivityRecordDO activityRecordDO) {
        if (activityRecordDO == null) {
            return null;
        }

        ActivityRecord activityRecord = new ActivityRecord();

        activityRecord.setId(activityRecordDO.getArId());
        activityRecord.setActivityType(ActivityType.valueOf(activityRecordDO.getArType()));
        activityRecord.setForeignId(activityRecordDO.getArForeignId());
        activityRecord.setCreatetime(activityRecordDO.getArCreatetime());
        activityRecord.setCreator(activityRecordDO.getArCreator());
        activityRecord.setEndtime(activityRecordDO.getArEndtime());

        return activityRecord;
    }

    public static List<ActivityRecord> toActivityRecord(List<ActivityRecordDO> activityRecordDOList) {
        if (activityRecordDOList == null) {
            return null;
        }

        List<ActivityRecord> activityRecordList = new ArrayList<ActivityRecord>(activityRecordDOList.size());
        for (ActivityRecordDO activityRecordDO : activityRecordDOList) {
            activityRecordList.add(toActivityRecord(activityRecordDO));
        }

        return activityRecordList;
    }

}
