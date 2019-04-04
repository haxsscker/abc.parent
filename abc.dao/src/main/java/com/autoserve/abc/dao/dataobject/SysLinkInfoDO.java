package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * 友情链接字段信息
 * 
 * @author liuwei
 */
public class SysLinkInfoDO {
    /**
     * sys_link_id.sl_id
     */
    private Integer           slId;
    /**
     * 链接标题 sys_link_title.sl_title
     */
    private String            slTitle;
    /**
     * 标题logo sys_link_logo.sl_logo
     */
    private String            slLogo;
    /**
     * 链接url sys_link_address.sl_Address
     */
    private String            slAddress;
    /**
     * 链接描述 sys_link_mark.sl_mark
     */
    private String            slMark;
    /**
     * 链接宽度 sys_link_width.sl_width
     */
    private BigDecimal        slWidth;
    /**
     * 链接高度 sys_link_height.sl_height
     */
    private BigDecimal        slHeight;
    
    private Integer 		  slOrder;

    public Integer getSlId() {
        return slId;
    }

    public void setSlId(Integer slId) {
        this.slId = slId;
    }

    public String getSlTitle() {
        return slTitle;
    }

    public void setSlTitle(String slTitle) {
        this.slTitle = slTitle;
    }

    public String getSlLogo() {
        return slLogo;
    }

    public void setSlLogo(String slLogo) {
        this.slLogo = slLogo;
    }

    public String getSlAddress() {
        return slAddress;
    }

    public void setSlAddress(String slAddress) {
        this.slAddress = slAddress;
    }

    public String getSlMark() {
        return slMark;
    }

    public void setSlMark(String slMark) {
        this.slMark = slMark;
    }

    public BigDecimal getSlWidth() {
        return slWidth;
    }

    public void setSlWidth(BigDecimal slWidth) {
        this.slWidth = slWidth;
    }

    public BigDecimal getSlHeight() {
        return slHeight;
    }

    public void setSlHeight(BigDecimal slHeight) {
        this.slHeight = slHeight;
    }

	public Integer getSlOrder() {
		return slOrder;
	}

	public void setSlOrder(Integer slOrder) {
		this.slOrder = slOrder;
	}
    
}
