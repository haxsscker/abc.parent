/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.util.UuidUtil;

/**
 * 内部唯一交易号
 *
 * @author segen189 2014年11月22日 下午9:26:58
 */
public final class InnerSeqNo {
    private String uniqueNo;

    private InnerSeqNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public static InnerSeqNo getInstance() {
        return new InnerSeqNo(UuidUtil.generateUuid());
    }
    
    /**
     * @Title: getMerchInstance  
     * @Description: 生成满足渤海银行接口规则的流水号  
     * @param  
     * @return InnerSeqNo
     * @author xiatt 20180410
     */
    public static InnerSeqNo getMerchInstance(){
    	return new InnerSeqNo(ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16));
    }

    @Override
    public String toString() {
        return uniqueNo;
    }

}
