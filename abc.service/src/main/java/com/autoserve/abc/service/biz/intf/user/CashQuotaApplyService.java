/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.user;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashQuotaApplyDO;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;

/**
 * 用户免费提现额度申请服务
 * 
 * @author zhangkang 2015年6月17日 下午2:01:41
 */
public interface CashQuotaApplyService {

    /**
     * 提交免费提现额度申请
     * 
     * @param cashQuotaApply
     * @return
     */
    BaseResult submitApply(CashQuotaApplyDO cashQuotaApply);

    /**
     * 审核
     * 
     * @param applyId
     * @param caiwu
     * @param isPass 是否通过
     * @param auditOpinion 审核意见
     * @return
     */
    BaseResult audit(Integer applyId, Employee caiwu, boolean isPass, String auditOpinion);

    /**
     * 申请列表
     * 
     * @param apply
     * @param page
     * @return
     */
    ListResult<CashQuotaApplyDO> queryList(CashQuotaApplyDO apply, PageCondition page);

}
