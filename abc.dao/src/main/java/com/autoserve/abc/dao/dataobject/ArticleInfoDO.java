package com.autoserve.abc.dao.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章管理 类ArticleInfoDO.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年11月29日 上午10:10:22
 */
public class ArticleInfoDO implements Serializable {

    private static final long serialVersionUID = -1198652757841527437L;

    /**
     * ai_id
     */
    private Integer           aiId;
    /**
     * 栏目ID ai_class_id
     */
    private Integer           aiClassId;

    /**
     * 文章标题 ai_article_title
     */
    private String            aiArticleTitle;

    /**
     * 文章内容 ai_article_content
     */
    private String            aiArticlecontent;

    /**
     * 是否置顶 ai_is_top
     */
    private Integer           aiIsTop;

    /**
     * 文章来源 ai_article_source
     */
    private String            aiArticleSource;

    /**
     * 发布者 ai_add_emp
     */
    private Integer           aiAddEmp;

    /**
     * 发布时间 ai_add_date
     */
    private Date              aiAddDate;

    /**
     * 浏览次数 ai_article_count
     */
    private Integer           aiArticleCount;

    /**
     * 文章简介 ai_article_introduction
     */
    private String            aiArticleIntroduction;
    
    /**
     * 文章图片 ai_article_logo
     */
    private String            aiArticleLogo;
    
    /**
     * 文章标题图片链接
     */
    private String 			aiArticleUrl;
    
    public Integer getAiId() {
        return aiId;
    }

    public void setAiId(Integer aiId) {
        this.aiId = aiId;
    }

    public Integer getAiClassId() {
        return aiClassId;
    }

    public void setAiClassId(Integer aiClassId) {
        this.aiClassId = aiClassId;
    }

    public String getAiArticleTitle() {
        return aiArticleTitle;
    }

    public void setAiArticleTitle(String aiArticleTitle) {
        this.aiArticleTitle = aiArticleTitle;
    }

    public String getAiArticlecontent() {
        return aiArticlecontent;
    }

    public void setAiArticlecontent(String aiArticlecontent) {
        this.aiArticlecontent = aiArticlecontent;
    }

    public Integer getAiIsTop() {
        return aiIsTop;
    }

    public void setAiIsTop(Integer aiIsTop) {
        this.aiIsTop = aiIsTop;
    }

    public String getAiArticleSource() {
        return aiArticleSource;
    }

    public void setAiArticleSource(String aiArticleSource) {
        this.aiArticleSource = aiArticleSource;
    }

    public Integer getAiAddEmp() {
        return aiAddEmp;
    }

    public void setAiAddEmp(Integer aiAddEmp) {
        this.aiAddEmp = aiAddEmp;
    }

    public Date getAiAddDate() {
        return aiAddDate;
    }

    public void setAiAddDate(Date aiAddDate) {
        this.aiAddDate = aiAddDate;
    }

    public Integer getAiArticleCount() {
        return aiArticleCount;
    }

    public void setAiArticleCount(Integer aiArticleCount) {
        this.aiArticleCount = aiArticleCount;
    }

	public String getAiArticleIntroduction() {
		return aiArticleIntroduction;
	}

	public void setAiArticleIntroduction(String aiArticleIntroduction) {
		this.aiArticleIntroduction = aiArticleIntroduction;
	}

	public String getAiArticleLogo() {
		return aiArticleLogo;
	}

	public void setAiArticleLogo(String aiArticleLogo) {
		this.aiArticleLogo = aiArticleLogo;
	}

	public String getAiArticleUrl() {
		return aiArticleUrl;
	}

	public void setAiArticleUrl(String aiArticleUrl) {
		this.aiArticleUrl = aiArticleUrl;
	}
    
}
