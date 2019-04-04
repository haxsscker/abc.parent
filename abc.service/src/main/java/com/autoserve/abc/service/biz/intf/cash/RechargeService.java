/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import java.math.BigDecimal;
import java.util.Map;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordJDO;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 充 值
 * 
 * @author J.YL 2014年11月26日 下午8:03:22
 */
public interface RechargeService {
    /**
     * 充值
     * 
     * @param userId
     * @param moneyAmount
     * @param map 发送第三方数据（可选）
     * @return
     */
    public PlainResult<DealReturn> recharge(Integer userId, UserType userType, BigDecimal moneyAmount,Map<String,String> map);

    /**
     * 查询个人充值记录
     * 
     * @param tocashRecordJDO
     * @param pageCondition
     * @return
     */
    public PageResult<TocashRecordJDO> queryUserRecharge(TocashRecordJDO tocashRecordJDO, PageCondition pageCondition,
                                                         String startDate, String endDate);

    /**
     * 查询机构充值记录
     * 
     * @param tocashRecordJDO
     * @param pageCondition
     * @return
     */
    public PageResult<TocashRecordJDO> queryEmpRecharge(TocashRecordJDO tocashRecordJDO, PageCondition pageCondition,
                                                        String startDate, String endDate);
    /**
     * 查询充值记录
     * 
     * @param RechargeRecordDO
     * @param pageCondition
     * @return
     */
    public PageResult<RechargeRecordDO> queryRechargeRecordByparam(RechargeRecordDO rechargeRecordDO, PageCondition pageCondition,
            String startDate, String endDate);
    /**
     * 更新三方返回数据到充值表
     * @param rechargeDo
     * @return
     */
    public BaseResult updateBackStatus(RechargeRecordDO rechargeDo);

	public Map<String, String> backRecharge(Integer empId, UserType platform,BigDecimal bigdecimal, Map<String, String> map);

	public void updateRecharge(Integer empId, BigDecimal moneyAmount, RechargeRecordDO recharge);
    
}
