/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import java.util.List;
import java.util.Map;

import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 资金的所有操作
 *
 * @author J.YL 2014年11月14日 下午4:20:46
 */
public interface PayMentService {

    /**
     * 转账
     * 
     * @param seqNo
     * @param dealRecords
     * @return
     */
    public BaseResult transferMoney(String seqNo, List<DealRecordDO> dealRecords);

    /**
     * 退款
     * 
     * @param seqNo
     * @param dealRecords
     * @return
     */
    public BaseResult refundMoney(String seqNo, List<DealRecordDO> dealRecords);

    /**
     * 冻结
     * 
     * @param seqNo
     * @param dealRecords
     * @return
     */
    public BaseResult freeze(String seqNo, List<DealRecordDO> dealRecords);

    /**
     * 解冻
     * 
     * @param seqNo
     * @param dealRecords
     * @return
     */
    public BaseResult unFreeze(String seqNo, List<DealRecordDO> dealRecords);

    /**
     * 充值
     * 
     * @param seqNo
     * @param dealRecords
     * @return
     */
    public BaseResult reCharge(String seqNo, List<DealRecordDO> dealRecords);

    /**
     * 提现
     * 
     * @param seqNo
     * @param dealRecords
     * @return
     */
    public BaseResult toCash(String seqNo, List<DealRecordDO> dealRecords);

    /**
     * 构造第三方支付接口请求参数
     */
    public CashRecordDO constructParam(Deal deals);

    /**
     * 还款和划转的三方接口待会数据方法
     */
    public PlainResult<Map<String, String>> tranfulAll(String seqNo, List<DealRecordDO> dealRecords);

    /**
     * 流标
     */
    public PlainResult<Map<String, String>> loanFree(String seqNo, List<DealRecordDO> dealRecords);

    /**
     * 融资账户转账
     * @param accountNo
     * @return
     */
	Map<String, String> investToMargin(String money,String accountNo);

	Map<String, String> zQTransfer(Map<String,String> params);

}
