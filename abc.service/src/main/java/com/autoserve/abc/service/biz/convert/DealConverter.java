/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.enums.DealState;

/**
 * Deal与DealRecordDO转换
 * 
 * @author J.YL 2014年11月21日 下午4:51:26
 */
public class DealConverter {
    public static List<DealRecordDO> toDealRecordDO(Deal deal) {
        List<DealRecordDO> dealRecordDOList = new LinkedList<DealRecordDO>();
        if (deal == null || CollectionUtils.isEmpty(deal.getDealDetail())) {
            return dealRecordDOList;
        }
        //根据新华久久贷业务，存入资金划转存入外部流水号
        String LoanNoList = "";
        String[] loannolists = {};
        if (deal.getDealDetail() != null && deal.getDealDetail().get(0) != null) {
            Map map = deal.getDealDetail().get(0).getData();
            if (map != null) {
                LoanNoList = (String) map.get("LoanNoList");
            }
        }
        if (LoanNoList != null && !"".equals(LoanNoList)) {
            loannolists = LoanNoList.split(",");
        }

        int i = 0;
        int k = 0; //标志内部流水号的尾号
        int index = 0;
        for (DealDetail detail : deal.getDealDetail()) {
            DealRecordDO dealRecord = new DealRecordDO();
            dealRecord.setDrBusinessId(deal.getBusinessId());
            dealRecord.setDrMoneyAmount(detail.getMoneyAmount());
            dealRecord.setDrOperateDate(new Date());
            dealRecord.setDrOperator(deal.getOperator());
            dealRecord.setDrPayAccount(detail.getPayAccountId());
            dealRecord.setDrReceiveAccount(detail.getReceiveAccountId());
            dealRecord.setDrInnerSeqNo(deal.getInnerSeqNo().getUniqueNo());
            dealRecord.setDrType(deal.getBusinessType().getType());
            if (detail.getDealDetailType() == null) {
                dealRecord.setDrDetailType(null);
            } else {
                dealRecord.setDrDetailType(detail.getDealDetailType().getType());
            }

            if (i < loannolists.length) {
            	dealRecord.setDrOutSeqNo(loannolists[i]);
            	
                //外部流水 xiatt 20161207 二次分配  获取下一个detail，若果是二次分配，让i-1
            	if(deal.getDealDetail().size() > index+1){
            		DealDetail detailNext = deal.getDealDetail().get(index+1);
            		if (detailNext.getDealDetailType() != null
            				&& detailNext.getDealDetailType().getType() == 19) {
                       i = i -1;
                    }
            	}
            }
            
            //还款的时候把传入第三方的内部流水存入DrOutSeqNo这个字段中（业务需要）
            if (deal.getBusinessType().getType() == 3) { //三次一循环            	
                if (i < 3) {
                    dealRecord.setDrOutSeqNo(deal.getInnerSeqNo().getUniqueNo() + k);
                } else {
                    i = 0;
                    k = k + 1;
                    dealRecord.setDrOutSeqNo(deal.getInnerSeqNo().getUniqueNo() + k);
                }

            }

            dealRecord.setDrState(DealState.NOCALLBACK.getState());
            dealRecordDOList.add(dealRecord);
            i++;
            index++;
        }
        return dealRecordDOList;
    }
}
