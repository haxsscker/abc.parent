package com.autoserve.abc.service.biz.entity;

public class AttrOfMenuNode {

    private String  url;
    private Integer parentid;
    private Integer sortnum;

    public AttrOfMenuNode() {

    }

    public AttrOfMenuNode(String url, Integer parentid, Integer sortnum) {
        this.url = url;
        this.parentid = parentid;
        this.sortnum = sortnum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getSortnum() {
        return sortnum;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

}
