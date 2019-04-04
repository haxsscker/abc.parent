/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.stage.statistics.RecentDeal;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealRecord;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 交易Service
 * 
 * @author J.YL 2014年11月21日 下午3:36:52
 */
public interface DealRecordService {
    /**
     * 查询交易记录
     *
     * @param deal
     * @return 交易记录和交易记录相关操作记录
     */
    public PlainResult<DealReturn> queryBusinessRecord(Deal deal);

    /**
     * 通过交易流水号查询交易
     */
    public ListResult<DealRecord> queryDealRecordsByInnerSeqNo(List<String> seqNos);

    /**
     * 创建交易
     *
     * @param deal
     * @return
     */
    public PlainResult<DealReturn> createBusinessRecord(Deal deal, Callback<DealNotify> callback);

    /**
     * 交易支付完成后，更新交易记录，资金操作记录 和对账表状态
     *
     * @return
     */
    public BaseResult modifyDealRecordState(String seqNo, String tradeStatus, BigDecimal totalFee);

    /**
     * 发起交易，平台模式下本地操作部分会进行本地操作，需要调用第三方接口的会发起第三方接口调用请求
     * 
     * @notice 投资、收购 付款账户投资人账户；充值付款账户为用户账户，收款为平台账户；提现
     *         付款账户为用户账户，收款为平台账户；退款，付款账户为平台账户，收款为用户账户。
     * @param innerSeqNo
     * @return
     */
    public BaseResult invokePayment(String innerSeqNo);

    /**
     * 流标，撤投，收购失败、 根据交易流水号将冻结的资金进行解冻 注：目前收购不支持部分收购
     * 
     * @param innerSeqNo
     * @return
     */
    public BaseResult unfrozenDealMoney(String innerSeqNo);

    /**
     * 通过内部交易流水号查询dealRecord
     * 
     * @param innerSeqNo
     * @return
     */
    public List<DealRecordDO> queryDealRecordsByInnerSeqNo(String innerSeqNo);

    /**
     * 支付接口回调方法
     * 
     * @param notifyData
     * @return
     */
    public BaseResult payMentNotify(Map notifyData);

    /**
     * 支付接口回调方法
     * 
     * @param notifyData
     * @return
     */
    public BaseResult modifyDealRecordStateWithDouble(Map params);

    /**
     * 双乾支付接口回调方法
     * 
     * @param notifyData
     * @return
     */
    public BaseResult doublePayMentNotify(Map notifyData);
    
    /**
     * 银行接口回调方法
     * 
     * @param notifyData
     * @return
     */
    public BaseResult bhyhPayMentNotify(Map notifyData);

    /**
     * 双乾审核接口回调方法
     * 
     * @param notifyData
     * @return
     */
    public BaseResult auditPayMentNotify(Map notifyData);

    /**
     * 双乾还款接口回调方法
     * 
     * @param notifyData
     * @return
     */
    public BaseResult PayBackNotify(Map notifyData);

    /**
     * 最近交易
     */
    public ListResult<RecentDeal> findMyRecentDeal(String accountNo1,String accountNo2);

    /**
     * 根据参数查询交易记录
     */
    public PageResult<DealRecordDO> queryDealByParams(DealRecordDO dealRecordDO, PageCondition pageCondition,
                                                      String startDate, String endDate);

    /**
     * 流标回调
     * 
     * @param notifyData 接口返回信息
     * @return
     */
    public BaseResult loanFreeNotify(Map<String, String> notifyData);
    
    /**
	  * 汇付天下
	  * 
	  * @param seqNo
	  * @param investId
	  * @param tradeStatus
	  * @param totalFee
	  * @return
	  */
	 public BaseResult modifyDealRecordState(String seqNo, Integer investId,String tradeStatus, BigDecimal totalFee);
	 
	 public BaseResult modifyDealRecordStateByCrdit(String seqNo, String tradeStatus, BigDecimal totalFee);
	 
	 public BaseResult chinapnrPayMentNotify(Map notifyData);
	 
	 /**
	     * 汇付还款接口回调方法
	     * 
	     * @param notifyData
	     * @return
	     */
	 public BaseResult chinapnrPayBackNotify(Map notifyData);	 
    
	 
	 /**
	     * 通过外部交易流水号查询dealRecord
	     * 2015/3/20 by lz.ge
	     * @param outSeqNo
	     * @return
	     */
	    public List<DealRecordDO> queryDealRecordsByOutSeqNo(String outSeqNo);

	public int updateDealRecordState(DealRecordDO updateDealRecord);

	public int batchInsert(List<DealRecordDO> dealRecordDoList);

	public void insertRecord(DealRecordDO dealRecord);

	public void updateRecord(DealRecordDO dealRecord);

	public PageResult<DealRecordDO> queryRecord(DealRecordDO dealRecord, PageCondition pageCondition);

	public int queryCountRecordReport(DealRecordDO dealRecord);
	
	public PageResult<DealRecordDO> queryGuarRecord(DealRecordDO dealRecord, PageCondition pageCondition);

	public PageResult<DealRecordDO> queryDealZzlsByParams(DealRecordDO dealrecordo, PageCondition pageCondition);

}
