package com.autoserve.abc.service.biz.impl.article;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.citrus.util.StringUtil;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ArticleClassDO;
import com.autoserve.abc.dao.intf.ArticleClassDao;
import com.autoserve.abc.service.biz.convert.ArticleClassConverter;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

@Service
public class ArticleClassImpl implements ArticleClassService {
    @Autowired
    private ArticleClassDao articleClassDao;

    @Override
    public BaseResult createArticleClass(ArticleClass pojo) {
        articleClassDao.insert(ArticleClassConverter.toArticleClassDO(pojo));
        return new BaseResult();
    }

    @Override
    public BaseResult removeArticleClass(int id) {
        articleClassDao.delete(id);
        return new BaseResult();
    }

    @Override
    public BaseResult modifyArticleClass(ArticleClass pojo) {
        articleClassDao.update(ArticleClassConverter.toArticleClassDO(pojo));
        return new BaseResult();
    }

    @Override
    public PlainResult<ArticleClass> queryById(int id) {
        PlainResult<ArticleClass> result = new PlainResult<ArticleClass>();
        ArticleClass articleClass = ArticleClassConverter.toArticleClass(articleClassDao.findById(id));
        if (articleClass == null) {
            return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "ArticleClassDO");
        }
        result.setData(articleClass);
        return result;
    }

    @Override
    public PlainResult<ArticleClass> queryByTemplateSymbol(String templateSymbol) {
        PlainResult<ArticleClass> result = new PlainResult<ArticleClass>();

        if (StringUtil.isBlank(templateSymbol)) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM_BLANK);
        }

        ArticleClass articleClass = ArticleClassConverter.toArticleClass(articleClassDao
                .findByTemplateSymbol(templateSymbol));
        result.setData(articleClass);
        return result;
    }

    @Override
    public PageResult<ArticleClass> queryListByParam(PageCondition pageCondition) {
        PageResult<ArticleClass> result = new PageResult<ArticleClass>(pageCondition);
        List<ArticleClass> list = new ArrayList<ArticleClass>();
        int count = this.articleClassDao.count(null);

        if (count > 0) {
            List<ArticleClassDO> listDO = this.articleClassDao.findListByParam(null, pageCondition);

            for (ArticleClassDO articleClassDo : listDO) {
                list.add(ArticleClassConverter.toArticleClass(articleClassDo));
            }
        }
        result.setData(list);
        result.setTotalCount(count);
        return result;
    }

    @Override
    public ListResult<ArticleClass> queryList() {

        ListResult<ArticleClass> result = new ListResult<ArticleClass>();

        List<ArticleClassDO> listDO = this.articleClassDao.findListByParam(null, null);

        List<ArticleClass> list = new ArrayList<ArticleClass>();

        for (ArticleClassDO articleClassDO : listDO) {
            list.add(ArticleClassConverter.toArticleClass(articleClassDO));
        }

        result.setData(list);
        return result;
    }
}
