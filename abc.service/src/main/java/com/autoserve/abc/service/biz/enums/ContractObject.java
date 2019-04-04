/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 合同对象
 *
 * @author sunlu 2018年12月11日
 */
public enum ContractObject {
    /**
     * 借款人
     */
	BORROWER(0),

    /**
     * 投资人
     */
	INVESTOR(1),

    /**
     * 转让人
     */
	ASSIGNOR(2),
    /**
     * 受让人
     */
	ASSIGNEE(4);

    ContractObject(int type) {
        this.type = type;
    }

    public static ContractObject valueOf(Integer type) {
        for (ContractObject value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

    private final int type;
}
