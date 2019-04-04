/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.recharge.json;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DownRechargeJDO;
import com.autoserve.abc.dao.dataobject.search.DownRechargeSearchDO;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.intf.mon.DownRechargeService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.downRecharge.DownRechargeVO;

/**
 * 线下审核
 * 
 * @author J.YL 2015年1月8日 下午1:57:06
 */
public class DownLineRechargeListCheckView {
    private static final Logger logger = LoggerFactory.getLogger(DownLineRechargeListCheckView.class);
    @Resource
    private DownRechargeService downRechargeService;

    public JsonPageVO<DownRechargeVO> execute(ParameterParser params, @Param("page") int page, @Param("rows") int rows) {
        JsonPageVO<DownRechargeVO> resultVO = new JsonPageVO<DownRechargeVO>();
        String modelAction = params.getString("modelAction");
        PageCondition pageCondition = new PageCondition(page, rows);
        DownRechargeSearchDO downRechargeSearchDO = null;
        if (modelAction != null) {
            downRechargeSearchDO = new DownRechargeSearchDO();
            downRechargeSearchDO.setUserRealName(params.getString("userRealName"));
            String moneyFrom = params.getString("rechargeMoneyFrom");
            try {
                if (moneyFrom != null) {
                    downRechargeSearchDO.setRechargeMoneyFrom(new BigDecimal(moneyFrom));
                }
                String moneyTo = params.getString("rechargeMoneyTo");
                if (moneyTo != null) {
                    downRechargeSearchDO.setRechargeMoneyTo(new BigDecimal(moneyTo));
                }
            } catch (Exception ex) {
                logger.info("查询参数出错");
            }
            downRechargeSearchDO.setRechargeDateFrom(params.getDate("rechargeDateFrom", new SimpleDateFormat(
                    DateUtil.DATE_FORMAT)));
            downRechargeSearchDO.setRechargeDateTo(params.getDate("rechargeDateTo", new SimpleDateFormat(
                    DateUtil.DATE_FORMAT)));
            downRechargeSearchDO.setRechargeBankNo(params.getString("rechargeBankNo"));
            Integer state = params.getInt("checkStatus");
            if (state.compareTo(Integer.valueOf(-1)) == 0) {
                state = null;
            }
            downRechargeSearchDO.setCheckStatus(state);
        }
        PageResult<DownRechargeJDO> result = downRechargeService.queryDownRecharge(downRechargeSearchDO, pageCondition);
        if (!result.isSuccess()) {
            resultVO.setMessage(result.getMessage());
            resultVO.setSuccess(false);
            resultVO.setRows(new ArrayList<DownRechargeVO>());
            resultVO.setTotal(0);
            return resultVO;
        }
        List<DownRechargeJDO> resultList = result.getData();
        List<DownRechargeVO> resultData = new ArrayList<DownRechargeVO>();
        for (DownRechargeJDO drj : resultList) {
            DownRechargeVO temp = new DownRechargeVO();
            temp.setDownRechargeId(drj.getDownRechargeId());
            temp.setBankNo(drj.getBankNo());
            temp.setDownRechargeCheckDate(DateUtil.formatDate(drj.getDownRechargeCheckDate(), DateUtil.DATE_FORMAT));
            temp.setDownRechargeCheckIdear(drj.getDownRechargeCheckIdear());
            temp.setDownRechargeCheckOperator(drj.getDownRechargeCheckOperator());
            temp.setDownRechargeCheckState(drj.getDownRechargeCheckState());
            temp.setDownRechargeDate(DateUtil.formatDate(drj.getDownRechargeDate(), DateUtil.DATE_FORMAT));
            temp.setDownRechargeFileId(drj.getDownRechargeFileId());
            temp.setDownRechargeMoney(drj.getDownRechargeMoney());
            temp.setDrBankId(drj.getDrBankId());
            temp.setUserRealName(drj.getUserRealName());
            temp.setDrMark(drj.getDrMark());
            if (ReviewState.valueOf(drj.getDownRechargeCheckState()).equals(ReviewState.PENDING_REVIEW)) {
                temp.setCanCheck(true);
            } else {
                temp.setCanCheck(false);
            }
            resultData.add(temp);
        }
        resultVO.setRows(resultData);
        resultVO.setTotal(result.getTotalCount());
        return resultVO;
    }
}
