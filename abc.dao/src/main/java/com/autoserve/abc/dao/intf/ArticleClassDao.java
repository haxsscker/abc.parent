package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ArticleClassDO;

/**
 * 栏目管理接口 类ArticleClassDao.java的实现描述：TODO 类实现描述
 *
 * @author liuwei 2014年11月28日 下午1:31:59
 */
public interface ArticleClassDao extends BaseDao<ArticleClassDO, Integer> {

    /**
     * 按条件查询分页结果
     *
     * @param articleInfo 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<ArticleClassDO>
     */
    List<ArticleClassDO> findListByParam(@Param("articleClass") ArticleClassDO articleClass,
            @Param("pageCondition") PageCondition pageCondition);

    /**
     * 根据页面代号查询单个栏目
     * 
     * @param templateSymbol 页面代号
     * @return ArticleClassDO
     */
    ArticleClassDO findByTemplateSymbol(String templateSymbol);

}
