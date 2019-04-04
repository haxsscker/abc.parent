package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ArticleInfoDO;
import com.autoserve.abc.dao.dataobject.search.ArticleInfoSreachDO;

public interface ArticleInfoDao extends BaseDao<ArticleInfoDO, Integer> {
    /**
     * 按条件查询分页结果
     * 
     * @param articleInfo 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<ArticleInfoDO>
     */
    List<ArticleInfoDO> findListByParam(@Param("articleInfo") ArticleInfoDO articleInfo,
                                        @Param("pageCondition") PageCondition pageCondition);

    /**
     * 按条件查询总数
     * 
     * @param articleInfo 查询条件，可选
     * @return int
     */
    int countListByParam(@Param("articleInfo") ArticleInfoDO articleInfo);
    
    /**
     * 按条件查询分页结果
     * @param searchDo
     * @param pageCondition
     * @return
     */
    List<ArticleInfoDO> findListBySearchParam(@Param("searchDo") ArticleInfoSreachDO searchDo,
            @Param("pageCondition") PageCondition pageCondition);
    
    /**
     * 按条件查询总数
     * @param searchDo
     * @return
     */
    int countListBySearchParam(@Param("searchDo") ArticleInfoSreachDO searchDo);
    
    /**
     * 根据栏目关键字查询对应文章
     * 
     * @param keyWord
     * @return
     */
    List<ArticleInfoDO> findAllByKey(@Param("keyWord") String keyWord,
                                     @Param("pageCondition") PageCondition pageCondition);

    /**
     * 统计对应栏目文章数目
     * 
     * @param keyWord
     * @return
     */
    int CountAllByKey(String keyWord);
    /**
     *根据文章关键字查询文章信息
     * 
     * @param acKeyWord
     * @return
     */
    List<ArticleInfoDO> findByArticleKeyWord(@Param("keyWord") String keyWord,
    		                                 @Param("aiIsTop") Integer aiIsTop,
    		                                 @Param("pageCondition") PageCondition pageNotice);

}
