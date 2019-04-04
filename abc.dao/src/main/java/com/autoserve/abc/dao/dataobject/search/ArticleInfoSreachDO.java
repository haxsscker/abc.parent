package com.autoserve.abc.dao.dataobject.search;

import java.util.Date;

/**
 * 文章搜索 类ArticleInfoSreachDO.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年3月14日 下午1:17:10
 */
public class ArticleInfoSreachDO {

    /**
     * 文章名称
     */
    private String articleTitle;
    
    /**
     * 文章栏目ID
     */
    private Integer articleClassId;
    
    /**
     * 最小发布日期
     */
    private Date startDate;
    
    /**
     * 最大发布日期
     */
    private Date endDate;

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public Integer getArticleClassId() {
		return articleClassId;
	}

	public void setArticleClassId(Integer articleClassId) {
		this.articleClassId = articleClassId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
