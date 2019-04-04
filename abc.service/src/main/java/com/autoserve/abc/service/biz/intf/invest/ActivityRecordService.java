/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.invest;

import com.autoserve.abc.service.biz.entity.ActivityRecord;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 活动记录管理服务
 *
 * @author segen189 2014年11月22日 下午12:56:36
 */
public interface ActivityRecordService {
    /**
     * 添加活动记录
     *
     * @param pojo 待添加的活动记录
     * @return BaseResult
     */
    BaseResult createActivityRecord(ActivityRecord pojo);

    /**
     * 查询某个人的投资记录信息集合
     *
     * @param loanId 项目id，必选
     * @param pageCondition，必选
     * @return PageResult<ActivityRecord>
     */
    //PageResult<ActivityRecord> queryActivityRecordList(int userId, PageCondition pageCondition);
}
