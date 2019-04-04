package com.autoserve.abc.web.module.screen.projectmanage.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.vo.JsonBaseVO;


/**
 * 查询还款交易状态
 * @author sunlu
 *
 * 
 */
public class UnclearRepaySearch {
	private final static Logger logger = LoggerFactory.getLogger(UnclearRepaySearch.class);
    @Resource
    private AccountInfoService        accountInfoService;
    @Resource
    private DealRecordDao             dealRecordDao;

	public JsonBaseVO execute(ParameterParser params,Context context) {
		JsonBaseVO result = new JsonBaseVO();
		String seqNo = params.getString("seqNo");
		String type = "FileRepayment";
		if (StringUtil.isEmpty(seqNo)) {
			result.setSuccess(false);
			result.setMessage("还款流水号不能为空！");
			return result;
		}
		Map<String, String> resultMap = accountInfoService.queryTransStatus(
				seqNo, type);
		Map<String, String> paramMap = new HashMap<String, String>();
		String RespCode = resultMap.get("RespCode");
		String RespDesc = resultMap.get("RespDesc");
		String TransStat = resultMap.get("TransStat");
		String MerBillNo = resultMap.get("MerBillNo");
		paramMap.put("MerBillNo", MerBillNo);
		paramMap.put("RespDesc", "还款成功");
		logger.info("RespCode：{}，TransStat：{}，RespDesc：{}",RespCode,TransStat,RespDesc);
		// 更新交易、资金操作记录
		if ("S1".equals(TransStat) || "S3".equals(TransStat)) {// S3放款解冻成功
			result.setSuccess(true);
			result.setMessage("还款成功");
			paramMap.put("RespDesc", "还款成功");
			paramMap.put("RespCode", "000000");
			try {
				result = this.doMoneyTransfer(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
				result.setMessage(e.getMessage());
				return result;
			}
		} else if (null == TransStat || "F1".equals(TransStat)
				|| "R9".equals(TransStat)) {// R9审批拒绝
			result.setSuccess(false);
			result.setMessage("还款失败");
			paramMap.put("RespDesc", "还款失败");
			paramMap.put("RespCode", "111111");
			try {
				result = this.doMoneyTransfer(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
				result.setMessage(e.getMessage());
				return result;
			}
		} else if ("W2".equals(TransStat)) {
			logger.info("请求处理中");
			result.setSuccess(false);
			result.setMessage("请求处理中");
		} else if ("W3".equals(TransStat)) {
			logger.info("系统受理中");
			result.setSuccess(false);
			result.setMessage("系统受理中");
		} else if ("W4".equals(TransStat)) {
			logger.info("银行受理中");
			result.setSuccess(false);
			result.setMessage("银行受理中");
		}
		return result;

	}
	/**
	 * 还款查询结果处理
	 * @param paramMap
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	private JsonBaseVO doMoneyTransfer(Map<String, String> params){
		JsonBaseVO result = new JsonBaseVO();
		logger.info("===================还款查询结果处理===================");
		String innerSeqNo = params.get("MerBillNo");// 
		String RespCode = params.get("RespCode");// 
		String RespDesc = params.get("RespDesc");// 
		result.setMessage(RespDesc);
		try {
			List<DealRecordDO> dealRecords = dealRecordDao
					.findDealRecordsByInnerSeqNo(innerSeqNo);
			if (CollectionUtils.isEmpty(dealRecords)) {
				logger.error("交易流水innerSeqNo："+innerSeqNo+"无对应的交易记录");
			}else{
				DealRecordDO dealRecordDo = new DealRecordDO();
				// 接口支持批量 故cashSeqNo和innerSeqNo相同 否则此处为outSeqNo
				dealRecordDo.setDrInnerSeqNo(innerSeqNo);
			
				if("000000".equals(RespCode)){
					logger.info("============还款成功===========");
					dealRecordDo.setDrState(DealState.SUCCESS.getState());
				}else{
					logger.info("============还款失败====="+RespDesc);
					dealRecordDo.setDrState(DealState.FAILURE.getState());
				}
				// 更新交易记录状态
				int flag = 0;
				for (DealRecordDO dr : dealRecords) {
					dr.setDrState(dealRecordDo.getDrState());
					flag += dealRecordDao.updateDealRecordStateById(dr);
				}
				if (flag <= 0) {// 更新交易
					logger.warn(
							"[DealRecordServiceImpl][modifyDealRecordState] 更新交易记录状态警告：无交易记录可更新。交易流水号：{}",
							innerSeqNo);
					throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),
							"交易状态更新失败");
				}
			}
		} catch (Exception e) {
			logger.error("[还款] error: ", e);
			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),e.getMessage());
		}
		return result;
	}
}
