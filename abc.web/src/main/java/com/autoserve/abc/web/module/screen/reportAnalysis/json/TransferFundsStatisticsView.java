/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.summary.FullTransferSummaryDO;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferQueryService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.reportAnalysis.TransferFundsVO;

/**
 * 类TransferFundsStatisticsView.java的实现描述：资金划转统计
 * 
 * @author J.YL 2014年12月31日 下午5:04:49
 */
public class TransferFundsStatisticsView {
    @Resource
    private FullTransferQueryService fullTransferQueryService;

    public JsonPageVO<TransferFundsVO> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        PageResult<TransferFundsVO> resultVO = new PageResult<TransferFundsVO>(pageCondition);
        Integer year = params.getInt("year");
        Integer count = 1;
        List<Integer> years = new ArrayList<Integer>();
        if (year == null || year == 0) {
            PageResult<Integer> yearRes = fullTransferQueryService.queryYears(pageCondition);
            count = yearRes.getTotalCount();
            years = yearRes.getData();
        } else {
            years.add(year);
        }
        ListResult<FullTransferSummaryDO> queryRes = fullTransferQueryService.queryTransferSummaryInfo(years);
        if (!queryRes.isSuccess()) {
            resultVO.setTotalCount(0);
            resultVO.setErrorMessage(queryRes.getCode(), queryRes.getMessage());
            return ResultMapper.toPageVO(resultVO);
        }
        List<FullTransferSummaryDO> queryResult = queryRes.getData();

        Map<Integer, TransferFundsVO> resultMap = new HashMap<Integer, TransferFundsVO>();
        for (FullTransferSummaryDO ftsd : queryResult) {
            Integer key = ftsd.getYear();
            TransferFundsVO temp = resultMap.get(key);
            if (temp == null) {
                temp = new TransferFundsVO();
                temp.setYears(key);
                resultMap.put(key, temp);
            }
            switch (ftsd.getMonth()) {
                case 1:
                    temp.setJan(ftsd.getTotalMoney());
                    break;
                case 2:
                    temp.setFeb(ftsd.getTotalMoney());
                    break;
                case 3:
                    temp.setMar(ftsd.getTotalMoney());
                    break;
                case 4:
                    temp.setApr(ftsd.getTotalMoney());
                    break;
                case 5:
                    temp.setMay(ftsd.getTotalMoney());
                    break;
                case 6:
                    temp.setJun(ftsd.getTotalMoney());
                    break;
                case 7:
                    temp.setJul(ftsd.getTotalMoney());
                    break;
                case 8:
                    temp.setAug(ftsd.getTotalMoney());
                    break;
                case 9:
                    temp.setSept(ftsd.getTotalMoney());
                    break;
                case 10:
                    temp.setOct(ftsd.getTotalMoney());
                    break;
                case 11:
                    temp.setNov(ftsd.getTotalMoney());
                    break;
                case 12:
                    temp.setDec(ftsd.getTotalMoney());
                    break;
                default:
                    break;
            }
        }
        resultVO.setTotalCount(count);
        resultVO.setData(new ArrayList<TransferFundsVO>(resultMap.values()));
        return ResultMapper.toPageVO(resultVO);
    }
}
