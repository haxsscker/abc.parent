package com.autoserve.abc.service.biz.intf.article;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 栏目管理 类ArticleClassService.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年11月28日 下午3:25:23
 */
public interface ArticleClassService {

    /**
     * 添加文章栏目
     * 
     * @param pojo
     * @return BaseResult
     */
    BaseResult createArticleClass(ArticleClass pojo);

    /**
     * 删除文章栏目
     * 
     * @param id
     * @return
     */
    BaseResult removeArticleClass(int id);

    /**
     * 修改文章栏目
     * 
     * @param pojo
     * @return BaseResult
     */
    BaseResult modifyArticleClass(ArticleClass pojo);

    /**
     * 查询单条文章栏目
     * 
     * @param id
     * @return
     */
    PlainResult<ArticleClass> queryById(int id);

    /**
     * 根据页面代号查询单条文章栏目
     * 
     * @param id
     * @return
     */
    PlainResult<ArticleClass> queryByTemplateSymbol(String templateSymbol);

    /**
     * 查询全部文章栏目
     * 
     * @param id
     * @return
     */
    PageResult<ArticleClass> queryListByParam(PageCondition pageCondition);

    /**
     * 查询全部文章栏目
     * 
     * @param id
     * @return
     */
    ListResult<ArticleClass> queryList();

}
