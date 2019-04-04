package com.autoserve.abc.web.vo.syslinkinfo;

import java.math.BigDecimal;

/**
 * 类SysLinkInfoVO.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月2日 下午4:22:34
 */
public class SysLinkInfoVO {

    private Integer    sys_link_id;
    private String     sys_link_title;
    private String     sys_link_logo;
    private String     sys_link_address;
    private String     sys_link_mark;
    private BigDecimal sys_link_width;
    private BigDecimal sys_link_height;
    private Integer    sys_link_order;

    public Integer getSys_link_id() {
        return sys_link_id;
    }

    public void setSys_link_id(Integer sys_link_id) {
        this.sys_link_id = sys_link_id;
    }

    public String getSys_link_title() {
        return sys_link_title;
    }

    public void setSys_link_title(String sys_link_title) {
        this.sys_link_title = sys_link_title;
    }

    public String getSys_link_logo() {
        return sys_link_logo;
    }

    public void setSys_link_logo(String sys_link_logo) {
        this.sys_link_logo = sys_link_logo;
    }

    public String getSys_link_address() {
        return sys_link_address;
    }

    public void setSys_link_address(String sys_link_address) {
        this.sys_link_address = sys_link_address;
    }

    public String getSys_link_mark() {
        return sys_link_mark;
    }

    public void setSys_link_mark(String sys_link_mark) {
        this.sys_link_mark = sys_link_mark;
    }

    public BigDecimal getSys_link_width() {
        return sys_link_width;
    }

    public void setSys_link_width(BigDecimal sys_link_width) {
        this.sys_link_width = sys_link_width;
    }

    public BigDecimal getSys_link_height() {
        return sys_link_height;
    }

    public void setSys_link_height(BigDecimal sys_link_height) {
        this.sys_link_height = sys_link_height;
    }

	public Integer getSys_link_order() {
		return sys_link_order;
	}

	public void setSys_link_order(Integer sys_link_order) {
		this.sys_link_order = sys_link_order;
	}

}
