/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.invest;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.intf.ActivityRecordDao;
import com.autoserve.abc.service.biz.convert.ActivityRecordConverter;
import com.autoserve.abc.service.biz.entity.ActivityRecord;
import com.autoserve.abc.service.biz.intf.invest.ActivityRecordService;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 活动记录管理服务
 *
 * @author segen189 2014年11月24日 下午12:05:59
 */
@Service
public class ActivityRecordServiceImpl implements ActivityRecordService {

    @Resource
    private ActivityRecordDao activityRecordDao;

    @Override
    public BaseResult createActivityRecord(ActivityRecord pojo) {
        activityRecordDao.insert(ActivityRecordConverter.toActivityRecordDO(pojo));
        return BaseResult.SUCCESS;
    }

}
