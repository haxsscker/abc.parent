/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import java.math.BigDecimal;
import java.util.Map;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordJDO;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 提现Service
 * 
 * @author J.YL 2014年11月26日 下午8:47:28
 */
public interface ToCashService {
    /**
     * 提现
     * 
     * @param userId
     * @param moneyAmount
     * @return
     */
    public BaseResult toCash(Integer userId, UserType userType, BigDecimal moneyAmount);
    
    /**
     * 提现
     * 
     * @param userId
     * @param moneyAmount
     * @return
     */
    public PlainResult<DealReturn> toCashOther(Integer userId, UserType userType, BigDecimal moneyAmount,Map param);

    /**
     * 后台人员提现记录查询
     * 
     * @param tocashRecordJDO
     * @return
     */
    public PageResult<TocashRecordJDO> queryUserInvestorExtr(TocashRecordJDO tocashRecordJDO,
                                                             PageCondition pageCondition, String startDate,
                                                             String endDate);

    /**
     * 机构提现记录查询
     * 
     * @param tocashRecordJDO
     * @param endDate
     * @param startDate
     * @return
     */
    public PageResult<TocashRecordJDO> queryEmpInvestorExtr(TocashRecordJDO tocashRecordJDO,
                                                            PageCondition pageCondition, String startDate,
                                                            String endDate);
    
    /**
     * 用户个人提现记录查询
     * 
     * @param tocashRecordJDO
     * @return
     */
    public PageResult<TocashRecordDO> queryListByUserId(TocashRecordDO tocashRecordDO,
                                                             PageCondition pageCondition, String startDate,
                                                             String endDate);
    /**
     * 更新充值记录表状态增加双乾接口业务流水号
     * @param toCashDo
     * @return
     */
    public BaseResult updateBySeqNo(TocashRecordDO toCashDo);
    /**
     * 通过内部流水号查询充值记录
     * @param seqNo
     * @return
     */
    public PlainResult<TocashRecordDO> queryBySeqNo(String seqNo);
    
    /**
     * 用户个人提现记录查询
     * 
     * @param tocashRecordDO
     * @return
     */
    public PageResult<TocashRecordDO> countToCashDealByParams(TocashRecordDO tocashRecordDO,
                                                             PageCondition pageCondition, String startDate,
                                                             String endDate);
    /**
     * 平台承担的手续费比例计算
     * @param userid   用户id
     * @param cashMoney   提现金额
     * @param map    调用提现接口封装的参数
     * @return
     */
    public BaseResult calculationPlatformFee(Integer userid,BigDecimal cashMoney,Map<String,Object> map);
    
    /**
     * 查询当月用户的提现次数
     * @param userId
     * @return
     */
    public  PlainResult<Integer> countTocashCurrentMonth(Integer userId);
    /**
     * 平台提现
     * @param userId
     * @param moneyAmount
     * @param param
     * @return
     */
	PlainResult<DealReturn> platformToCashOther(Integer userId,
			BigDecimal moneyAmount, Map param);

	public Map<String, String> toBackCash(Integer empId, UserType userType, BigDecimal cashMoney,Map<String, String> map);

	public void updateCash(Integer empId, BigDecimal cashMoney, TocashRecordDO toCashDo);
    
}
