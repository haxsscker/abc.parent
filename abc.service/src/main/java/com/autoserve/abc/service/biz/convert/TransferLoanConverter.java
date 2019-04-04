/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.TransferLoanState;

/**
 * TransferLoan与TransferLoanDO互相转换
 * 
 * @author segen189 2014年11月21日 下午8:47:13
 */
public class TransferLoanConverter {

    public static TransferLoanDO toTransferLoanDO(TransferLoan transferLoan) {
        if (transferLoan == null) {
            return null;
        }

        TransferLoanDO transferLoanDO = new TransferLoanDO();

        transferLoanDO.setTlId(transferLoan.getId());
        transferLoanDO.setTlOriginId(transferLoan.getOriginId());
        transferLoanDO.setTlInvestId(transferLoan.getInvestId());
        transferLoanDO.setTlUserId(transferLoan.getUserId());
        transferLoanDO.setTlTransferTotal(transferLoan.getTransferTotal());
        transferLoanDO.setTlTransferMoney(transferLoan.getTransferMoney());
        transferLoanDO.setTlTransferRate(transferLoan.getTransferRate());
        transferLoanDO.setTlTransferFee(transferLoan.getTransferFee());
        transferLoanDO.setTlTransferDiscountRate(transferLoan.getTransferDiscountRate());
        transferLoanDO.setTlTransferDiscountFee(transferLoan.getTransferDiscountFee());
        transferLoanDO.setTlTransferPeriod(transferLoan.getTransferPeriod());
        if (transferLoan.getTransferLoanState() != null) {
            transferLoanDO.setTlState(transferLoan.getTransferLoanState().getState());
        }
        List<Integer> tlStates = null; 
        if (transferLoan.getTransferLoanStates() != null && transferLoan.getTransferLoanStates().size()>0) {
        	tlStates = new ArrayList<Integer>();
        	for(TransferLoanState tl : transferLoan.getTransferLoanStates()){
        		tlStates.add(tl.getState());
        	}
        }
        transferLoanDO.setTlStates(tlStates);
        transferLoanDO.setTlCreatetime(transferLoan.getCreatetime());
        transferLoanDO.setTlModifytime(transferLoan.getModifytime());
        transferLoanDO.setTlInvestStarttime(transferLoan.getInvestStarttime());
        transferLoanDO.setTlInvestEndtime(transferLoan.getInvestEndtime());
        transferLoanDO.setTlFulltime(transferLoan.getFulltime());
        transferLoanDO.setTlFullTransferedtime(transferLoan.getFullTransferedtime());
        transferLoanDO.setTlCurrentInvest(transferLoan.getCurrentInvest());
        transferLoanDO.setTlCurrentValidInvest(transferLoan.getCurrentValidInvest());
        transferLoanDO.setTlNextPaymentId(transferLoan.getNextPaymentId());
        transferLoanDO.setTlLoanNo(transferLoan.getTransferLoanNo());
        transferLoanDO.setTlReleaseDate(transferLoan.getReleaseDate());
        return transferLoanDO;
    }

    public static TransferLoan toTransferLoan(TransferLoanDO transferLoanDO) {
        if (transferLoanDO == null) {
            return null;
        }

        TransferLoan transferLoan = new TransferLoan();

        transferLoan.setId(transferLoanDO.getTlId());
        transferLoan.setOriginId(transferLoanDO.getTlOriginId());
        transferLoan.setInvestId(transferLoanDO.getTlInvestId());
        transferLoan.setUserId(transferLoanDO.getTlUserId());
        transferLoan.setTransferTotal(transferLoanDO.getTlTransferTotal());
        transferLoan.setTransferMoney(transferLoanDO.getTlTransferMoney());
        transferLoan.setTransferRate(transferLoanDO.getTlTransferRate());
        transferLoan.setTransferFee(transferLoanDO.getTlTransferFee());
        transferLoan.setTransferDiscountRate(transferLoanDO.getTlTransferDiscountRate());
        transferLoan.setTransferDiscountFee(transferLoanDO.getTlTransferDiscountFee());
        transferLoan.setTransferPeriod(transferLoanDO.getTlTransferPeriod());
        transferLoan.setTransferLoanState(TransferLoanState.valueOf(transferLoanDO.getTlState()));
        transferLoan.setCreatetime(transferLoanDO.getTlCreatetime());
        transferLoan.setModifytime(transferLoanDO.getTlModifytime());
        transferLoan.setInvestStarttime(transferLoanDO.getTlInvestStarttime());
        transferLoan.setInvestEndtime(transferLoanDO.getTlInvestEndtime());
        transferLoan.setFulltime(transferLoanDO.getTlFulltime());
        transferLoan.setFullTransferedtime(transferLoanDO.getTlFullTransferedtime());
        transferLoan.setCurrentInvest(transferLoanDO.getTlCurrentInvest());
        transferLoan.setCurrentValidInvest(transferLoanDO.getTlCurrentValidInvest());
        transferLoan.setNextPaymentId(transferLoanDO.getTlNextPaymentId());
        transferLoan.setNextPaymentId(transferLoanDO.getTlNextPaymentId());
        transferLoan.setTransferLoanNo(transferLoanDO.getTlLoanNo());
        transferLoan.setReleaseDate(transferLoanDO.getTlReleaseDate());
        return transferLoan;
    }

    public static List<TransferLoan> toTransferLoanList(List<TransferLoanDO> transLoanDOList) {
        if (transLoanDOList == null) {
            return null;
        }

        List<TransferLoan> transLoanList = new ArrayList<TransferLoan>(transLoanDOList.size());
        for (TransferLoanDO loanDO : transLoanDOList) {
            transLoanList.add(toTransferLoan(loanDO));
        }

        return transLoanList;
    }

}
