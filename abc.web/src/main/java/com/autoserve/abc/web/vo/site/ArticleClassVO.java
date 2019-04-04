package com.autoserve.abc.web.vo.site;

public class ArticleClassVO {

    /**
     * 栏目ID ac_id
     */
    private Integer columnid;

    /**
     * 栏目名称 ac_name
     */
    private String  columnname;
    /**
     * 
     */
    private String  columnmode;

    /**
     * 父级栏目ID ac_class
     */
    private Integer columnClass;
    
    /**
     * 父级栏目名称ID ac_class
     */
    private String columnClassName;

    /**
     * 关键字 ac_key_word
     */
    private String  columnkeyword;
    /**
     * 栏目描述 ac_desc
     */
    private String  columndesc;
    /**
     * 栏目路径 ac_path
     */
    private String  columnpath;
    /**
     * 模版地址 ac_template
     */
    private String  columntemplate;
    /**
     * 内容页模版地址 ac_content_template
     */
    private String  columncontenttemplate;
    /**
     * 栏目类型 ac_type
     */
    private Integer columntype;
    /**
     * 排序 ac_type
     */
    private Integer columnorder;

    public Integer getColumnid() {
        return columnid;
    }

    public void setColumnid(Integer columnid) {
        this.columnid = columnid;
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname;
    }

    public String getColumnmode() {
        return columnmode;
    }

    public void setColumnmode(String columnmode) {
        this.columnmode = columnmode;
    }

    public Integer getColumnClass() {
        return columnClass;
    }

    public void setColumnClass(Integer columnClass) {
        this.columnClass = columnClass;
    }


    public String getColumnkeyword() {
		return columnkeyword;
	}

	public void setColumnkeyword(String columnkeyword) {
		this.columnkeyword = columnkeyword;
	}

	public String getColumndesc() {
        return columndesc;
    }

    public void setColumndesc(String columndesc) {
        this.columndesc = columndesc;
    }

    public String getColumnpath() {
        return columnpath;
    }

    public void setColumnpath(String columnpath) {
        this.columnpath = columnpath;
    }

    public String getColumntemplate() {
        return columntemplate;
    }

    public void setColumntemplate(String columntemplate) {
        this.columntemplate = columntemplate;
    }

    public String getColumncontenttemplate() {
        return columncontenttemplate;
    }

    public void setColumncontenttemplate(String columncontenttemplate) {
        this.columncontenttemplate = columncontenttemplate;
    }

    public Integer getColumntype() {
        return columntype;
    }

    public void setColumntype(Integer columntype) {
        this.columntype = columntype;
    }

    public Integer getColumnorder() {
        return columnorder;
    }

    public void setColumnorder(Integer columnorder) {
        this.columnorder = columnorder;
    }

	public String getColumnClassName() {
		return columnClassName;
	}

	public void setColumnClassName(String columnClassName) {
		this.columnClassName = columnClassName;
	}

    
    
}
