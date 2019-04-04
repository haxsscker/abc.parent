/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.user;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashQuotaApplyDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.CashQuotaApplyDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.intf.user.CashQuotaApplyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;

/**
 * 用户免费提现额度申请服务
 * 
 * @author zhangkang 2015年6月17日 下午2:01:07
 */
@Service
public class CashQuotaApplyServiceImpl implements CashQuotaApplyService {

    @Resource
    private CashQuotaApplyDao cashQuotaApplyDao;

    @Resource
    private UserDao           userDao;

    private Logger            logger = LoggerFactory.getLogger(getClass());

    @Override
    public BaseResult submitApply(CashQuotaApplyDO cashQuotaApply) {
        BaseResult result = new BaseResult();
        int line = cashQuotaApplyDao.insert(cashQuotaApply);
        if (line != 1) {
            logger.error("提交免费提现额度申请失败！");
            result.setSuccess(false);
            result.setMessage("提交免费提现额度申请失败！");
        }
        return result;
    }

    @Override
    @Transactional
    public BaseResult audit(Integer applyId, Employee caiwu, boolean isPass, String auditOpinion) {
        BaseResult result = new BaseResult();
        CashQuotaApplyDO apply = this.cashQuotaApplyDao.findById(applyId);
        if (apply.getCqaState() != 1) {
            result.setSuccess(false);
            result.setMessage("已审核过的申请");
            return result;
        }
        //审核通过
        if (isPass) {
            apply.setCqaState(2);
            //更新用户表的免费提现额度
            BigDecimal userCashQuota = apply.getCqaApplyCashQuota();
            if (apply.getCqaOldCashQuota() != null) {
                userCashQuota = apply.getCqaOldCashQuota().add(apply.getCqaApplyCashQuota());
            }
            if (userCashQuota.compareTo(new BigDecimal("0")) < 0) {
                userCashQuota = new BigDecimal("0.00");
            }
            UserDO user = new UserDO();
            user.setUserId(apply.getCqaUserId());
            user.setUserCashQuota(userCashQuota);
            userDao.updateByPrimaryKeySelective(user);
        }
        //审核不通过
        else {
            apply.setCqaState(3);
        }
        apply.setCqaAuditId(caiwu.getEmpId());
        apply.setCqaAuditOpinion(auditOpinion);
        cashQuotaApplyDao.update(apply);
        return result;
    }

    @Override
    public ListResult<CashQuotaApplyDO> queryList(CashQuotaApplyDO apply, PageCondition page) {
        ListResult<CashQuotaApplyDO> result = new ListResult<CashQuotaApplyDO>();
        int count = this.cashQuotaApplyDao.count(apply);
        if (count != 0) {
            List<CashQuotaApplyDO> list = this.cashQuotaApplyDao.findList(apply, page);
            result.setData(list);
        }
        result.setCount(count);
        return result;
    }
}
