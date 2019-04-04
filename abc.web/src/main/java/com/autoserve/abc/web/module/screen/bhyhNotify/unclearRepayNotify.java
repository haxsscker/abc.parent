package com.autoserve.abc.web.module.screen.bhyhNotify;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.ChargeRecordService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.EasyPayUtils;

public class unclearRepayNotify {
	private final static Logger logger = LoggerFactory.getLogger(unclearRepayNotify.class);
	@Resource
	private HttpServletResponse resp;
	@Resource
	private HttpServletRequest resq;
    @Resource
    private AccountInfoService        accountInfoService;
    @Resource
    private DealRecordDao             dealRecordDao;
	@Resource
	private ChargeRecordService chargeRecord;
    
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public void execute(Context context, Navigator nav, ParameterParser params) {
		logger.info("===================未结清还款异步通知===================");
		/**
		 * 修改交易记录
		 */
		Map returnMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
		logger.info(returnMap.toString());
		String innerSeqNo = FormatHelper.GBKDecodeStr(params.getString("MerBillNo"));
		String RespCode = FormatHelper.GBKDecodeStr(params.getString("RespCode"));
		String RespDesc = FormatHelper.GBKDecodeStr(params.getString("RespDesc"));
		
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
			resp.getWriter().print("SUCCESS");
		} catch (Exception e) {
			logger.error("[还款] error: ", e);
			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),e.getMessage());
		}
	}
}
