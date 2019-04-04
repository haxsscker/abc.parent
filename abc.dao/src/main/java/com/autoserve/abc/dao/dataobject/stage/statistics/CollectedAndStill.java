package com.autoserve.abc.dao.dataobject.stage.statistics;

import java.math.BigDecimal;

/**
 * 今日待收、今日待还
 * @author DS
 *
 * 2015下午1:38:14
 */
public class CollectedAndStill {
	/**
     * 今日待还
     */
    private BigDecimal      PpPayTotalMoney;
	
    /**
     * 今日待收
     */
    private BigDecimal      PiPayTotalMoney;

	public BigDecimal getPpPayTotalMoney() {
		return PpPayTotalMoney;
	}

	public void setPpPayTotalMoney(BigDecimal ppPayTotalMoney) {
		PpPayTotalMoney = ppPayTotalMoney;
	}

	public BigDecimal getPiPayTotalMoney() {
		return PiPayTotalMoney;
	}

	public void setPiPayTotalMoney(BigDecimal piPayTotalMoney) {
		PiPayTotalMoney = piPayTotalMoney;
	}
    
	
	
}
