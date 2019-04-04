package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.BooleanType;

public class ArticleInfo {
    private Integer     aiId;
    private Integer     aiClassId;            //父栏目ID
    private String      aiArticleTitle;       //文章标题
    private String      aiArticlecontent;     //文章内容
    private BooleanType aiIsTop;              //是否置顶
    private String      aiArticleSource;      //文章来源
    private Integer     aiAddEmp;             //发布者ID
    private Date        aiAddDate;            //发布时间
    private Integer     aiArticleCount;       //浏览次数
    private String      aiArticleIntroduction; //文章简介
    private String      aiArticleLogo;        //文章logo
    private String		aiArticleUrl;		  //图片链接

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

    public BooleanType getAiIsTop() {
        return aiIsTop;
    }

    public void setAiIsTop(BooleanType aiIsTop) {
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
