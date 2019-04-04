/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.job;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.dao.intf.TocashRecordDao;
import com.autoserve.abc.service.biz.enums.ToCashState;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 提现状态查询定时任务
 *
 * @author J.YL 2014年12月1日 下午2:08:27
 */
public class ToCashJob implements BaseJob {
    @Resource
    private DealRecordService dealRecord;
    @Resource
    private TocashRecordDao tocashRecordDao;
    @Autowired
    private AccountInfoService  accountInfoService;
    @Resource
	private CashRecordService cashrecordservice;
    @Resource
	private ToCashService tocashservice;
	@Resource
	private UserService userService;
    /**
     * 提现结果查询job
     */
    @Override
    public void run() {
        Logger logger = LoggerFactory.getLogger(ToCashJob.class);
        logger.info("提现查询任务开始"+new Date());
        List<TocashRecordDO> toCashList = tocashRecordDao.queryPendingRestoCashRecord();
        String seqNo = null;
        for (TocashRecordDO query : toCashList) {
            try {
            	Map<String,String> resultMap = new HashMap<String,String>();
            	if(!"800055100010001".equals(query.getTocashAccountId())){
            		resultMap = accountInfoService.queryTransStatus(query.getTocashSeqNo(), "TXJL");
            	}else{
                	resultMap = accountInfoService.queryTransStatus(query.getTocashSeqNo(), "PTTXJL");
            	}
            	String RespCode = resultMap.get("RespCode");
    			String TransStat = resultMap.get("TransStat");
    			//更新交易、资金操作记录
    			if(null!=TransStat&&TransStat.equals("S1")){
    				dealRecord.modifyDealRecordStateWithDouble(resultMap);
//    				PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo((String)resultMap.get("MerBillNo"));
//    		        CashRecordDO cashrecord = cashrecorddo.getData(); 
//    		        cashrecord.setCrResponseState(Integer.valueOf(RespCode));
//    		        cashrecordservice.modifyCashRecordState(cashrecord);
    			}else if(null==TransStat||TransStat.equals("F1")){//交易失败
    				RespCode = "111111";//自定义失败状态码为111111
    				resultMap.put("RespCode", RespCode);
    				dealRecord.modifyDealRecordStateWithDouble(resultMap);
//    				PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo((String)resultMap.get("MerBillNo"));
//    		        CashRecordDO cashrecord = cashrecorddo.getData(); 
//    		        cashrecord.setCrResponseState(Integer.valueOf(RespCode));
//    		        cashrecordservice.modifyCashRecordState(cashrecord);
    			}
            	
    			//更新提现记录
		        PlainResult<TocashRecordDO> resultRecord=tocashservice.queryBySeqNo(resultMap.get("MerBillNo"));
		        TocashRecordDO toCashDo = new TocashRecordDO();
		        toCashDo.setTocashSeqNo(resultMap.get("MerBillNo"));
		        if(null!=TransStat&&"S1".equals(TransStat)){
		        	toCashDo.setTocashState(ToCashState.SUCCESS.getState());
					toCashDo.setTocashValidquota(resultRecord.getData().getTocashQuota());
					//更新免费提现额度
					if(resultRecord.getData()!=null && resultRecord.getData().getTocashQuota()!=null){
						userService.reduceCashQuota(resultRecord.getData().getTocashUserId(), resultRecord.getData().getTocashQuota());
					}			
		        }else if(null==TransStat||"F1".equals(TransStat)){
		        	toCashDo.setTocashState(ToCashState.FAILURE.getState());
					toCashDo.setTocashValidquota(new BigDecimal(0));
		        }
		        BaseResult tocashresult = tocashservice.updateBySeqNo(toCashDo);	      
		        logger.info("修改提现记录："+tocashresult.isSuccess()+tocashresult.getMessage());
            } catch (Exception ex) {
                logger.error("ToCashJob，Seq：{}", seqNo, ex);
            }
        }
        logger.info("提现查询任务结束"+new Date());
    }
}
