package com.autoserve.abc.service.biz.intf.article;

import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ArticleInfoDO;
import com.autoserve.abc.dao.dataobject.search.ArticleInfoSreachDO;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 文章管理 类ArticleInfoService.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年11月29日 下午2:06:29
 */
public interface ArticleInfoService {

    /**
     * 添加文章
     * 
     * @param pojo
     * @return BaseResult
     */
    BaseResult createArticleInfo(ArticleInfo pojo);

    /**
     * 删除文章
     * 
     * @param id
     * @return
     */
    BaseResult removeArticleInfo(int id);

    /**
     * 修改文章
     * 
     * @param pojo
     * @return BaseResult
     */
    BaseResult modifyArticleInfo(ArticleInfo pojo);

    /**
     * 查询单条文章
     * 
     * @param id
     * @return
     */
    PlainResult<ArticleInfo> queryById(int id);

    /**
     * 查询文章
     * 
     * @param id
     * @return
     */
    PageResult<ArticleInfo> queryArticleInfoListByParam(ArticleInfo articleInfo, PageCondition pageCondition);
    /**
     * 查询文章列表
     * 
     * @param articleInfo
     * @return
     */
    ListResult<ArticleInfo> queryArticleInfoListByParam(ArticleInfo articleInfo);

    /**
     * 查询全部文章
     * 
     * @param id
     * @return
     */
    PageResult<ArticleInfo> queryListByParam(PageCondition pageCondition);
    
    /**
     * 根据条件查询文章列表
     * @param searchDo
     * @param pageCondition
     * @return
     */
    PageResult<ArticleInfo> queryListBySearchParam( ArticleInfoSreachDO searchDo,
             PageCondition pageCondition);
    
    /**
     * 根据关键字查询对应文章
     * 
     * @param keyWord
     * @return
     */
    PageResult<ArticleInfoDO> queryListByKeyWord(String keyWord, PageCondition pageCondition);
    
    /**
     *首页查询公司公告和行业动态
     * 
     * @param acKeyWord
     * @return
     */
    List<ArticleInfoDO> findByArticleKeyWord(String keyWord,Integer aiIsTop,PageCondition pageNotice);

    
}
