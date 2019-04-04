/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject.search;

/**
 * 标的唯一标识
 * 
 * @author J.YL 2015年1月5日 下午6:03:59
 */
public class BidIdentitySearchDO {
    /**
     * 标的id
     */
    private Integer bidId;
    /**
     * 标的type
     */
    private Integer bidType;

    /**
     * @return the bidId
     */
    public Integer getBidId() {
        return bidId;
    }

    /**
     * @param bidId the bidId to set
     */
    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    /**
     * @return the bidType
     */
    public Integer getBidType() {
        return bidType;
    }

    /**
     * @param bidType the bidType to set
     */
    public void setBidType(Integer bidType) {
        this.bidType = bidType;
    }

}
