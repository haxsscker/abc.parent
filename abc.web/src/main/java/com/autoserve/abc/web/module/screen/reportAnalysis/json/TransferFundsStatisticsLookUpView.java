/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.util.StringUtil;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.FullTransferFundsSearchDO;
import com.autoserve.abc.service.biz.entity.TransferFundsDetailInfo;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 类TransferFundsStatisticsLookUpView.java的实现描述：划转资金详情
 * 
 * @author J.YL 2014年12月31日 下午5:16:51
 */
public class TransferFundsStatisticsLookUpView {
    private static final Logger      logger = LoggerFactory.getLogger(TransferFundsStatisticsLookUpView.class);
    @Resource
    private FullTransferQueryService fullTransferQueryService;

    public JsonPageVO<TransferFundsDetailInfo> execute(ParameterParser params) {
        String model = params.getString("model");
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT);
        FullTransferFundsSearchDO search = null;
        try {
            if (model != null) {
                search = new FullTransferFundsSearchDO();
                int bidType = params.getInt("loanType");
                if (bidType == -1) {
                    search.setBidType(null);
                } else {
                    search.setBidType(bidType);
                }
                String dateFrom = params.getString("lendDateFrom");
                if (!StringUtil.isBlank(dateFrom)) {
                    search.setLendDateFrom(DateUtil.parseWsDate(dateFrom));
                }
                String dateTo = params.getString("lendDateTo");
                if (!StringUtil.isBlank(dateTo)) {
                    search.setLendDateTo(DateUtil.parseWsDate(dateTo));
                }
                search.setLoanCategory(params.getInt("loanCategory"));
                search.setLoanNo(params.getString("loanNo"));
            }
        } catch (Exception e) {
            logger.warn("参数解析失败");
        }
        JsonPageVO<TransferFundsDetailInfo> resultVO = new JsonPageVO<TransferFundsDetailInfo>();
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        String dateStr = params.getString("tdate");
        Date date = new Date();
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("Date formate出错", e);
        }
        PageResult<TransferFundsDetailInfo> result = fullTransferQueryService.queryDetail(search, date, pageCondition);
        resultVO.setTotal(result.getTotalCount());
        resultVO.setRows(result.getData());
        return resultVO;
    }
}
