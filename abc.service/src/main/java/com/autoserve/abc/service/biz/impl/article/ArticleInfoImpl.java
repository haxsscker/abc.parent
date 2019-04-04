package com.autoserve.abc.service.biz.impl.article;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ArticleInfoDO;
import com.autoserve.abc.dao.dataobject.search.ArticleInfoSreachDO;
import com.autoserve.abc.dao.intf.ArticleInfoDao;
import com.autoserve.abc.service.biz.convert.ArticleInfoConverter;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

@Service
public class ArticleInfoImpl implements ArticleInfoService {

    @Resource
    private ArticleInfoDao articleInfoDao;

    @Override
    public BaseResult createArticleInfo(ArticleInfo pojo) {
        this.articleInfoDao.insert(ArticleInfoConverter.toArticleInfoDO(pojo));
        System.out.println(pojo.getAiArticleTitle());
        return new BaseResult();
    }

    @Override
    public BaseResult removeArticleInfo(int id) {
        this.articleInfoDao.delete(id);
        return new BaseResult();
    }

    @Override
    public BaseResult modifyArticleInfo(ArticleInfo pojo) {
        this.articleInfoDao.update(ArticleInfoConverter.toArticleInfoDO(pojo));
        return new BaseResult();
    }

    @Override
    public PlainResult<ArticleInfo> queryById(int id) {
        PlainResult<ArticleInfo> result = new PlainResult<ArticleInfo>();
        ArticleInfo articleInfo = ArticleInfoConverter.toArticleInfo(this.articleInfoDao.findById(id));
        if (articleInfo == null) {
            return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "ArticleInfo");
        }
        result.setData(articleInfo);
        return result;
    }

    @Override
    public PageResult<ArticleInfo> queryArticleInfoListByParam(ArticleInfo articleInfo, PageCondition pageCondition) {
        PageResult<ArticleInfo> result = new PageResult<ArticleInfo>(pageCondition.getPage(),
                pageCondition.getPageSize());

        int count = this.articleInfoDao.countListByParam(ArticleInfoConverter.toArticleInfoDO(articleInfo));
        if (count > 0) {
            List<ArticleInfoDO> list = this.articleInfoDao.findListByParam(
                    ArticleInfoConverter.toArticleInfoDO(articleInfo), pageCondition);

            List<ArticleInfo> articleInfoList = new ArrayList<ArticleInfo>();
            for (ArticleInfoDO obj : list) {
                articleInfoList.add(ArticleInfoConverter.toArticleInfo(obj));
            }
            result.setTotalCount(count);
            result.setData(articleInfoList);
        }
        return result;
    }

    @Override
    public PageResult<ArticleInfo> queryListByParam(PageCondition pageCondition) {
        PageResult<ArticleInfo> pageResult = new PageResult<ArticleInfo>(pageCondition);
        List<ArticleInfo> list = new ArrayList<ArticleInfo>();

        int count = this.articleInfoDao.countListByParam(null);
        if (count > 0) {
            List<ArticleInfoDO> listDO = this.articleInfoDao.findListByParam(null, pageCondition);
            for (ArticleInfoDO articleInfoDO : listDO) {
                list.add(ArticleInfoConverter.toArticleInfo(articleInfoDO));
            }
        }
        pageResult.setTotalCount(count);
        pageResult.setData(list);
        return pageResult;
    }

	@Override
	public ListResult<ArticleInfo> queryArticleInfoListByParam(
			ArticleInfo articleInfo) {
		ListResult<ArticleInfo> result=new ListResult<ArticleInfo>();
		List<ArticleInfoDO> list=this.articleInfoDao.findListByParam(ArticleInfoConverter.toArticleInfoDO(articleInfo), null);
		List<ArticleInfo> listx=new ArrayList<ArticleInfo>();
		for(ArticleInfoDO articleInfoDo:list){
			listx.add(ArticleInfoConverter.toArticleInfo(articleInfoDo));
		}
		result.setData(listx);
		return result;
	}

	@Override
	public PageResult<ArticleInfo> queryListBySearchParam(ArticleInfoSreachDO searchDo, PageCondition pageCondition) {
		PageResult<ArticleInfo> pageResult = new PageResult<ArticleInfo>(pageCondition);
		List<ArticleInfo> list = new ArrayList<ArticleInfo>();
		int count = this.articleInfoDao.countListBySearchParam(searchDo);
        if (count > 0) {
            List<ArticleInfoDO> listDO = this.articleInfoDao.findListBySearchParam(searchDo, pageCondition);
            for (ArticleInfoDO articleInfoDO : listDO) {
                list.add(ArticleInfoConverter.toArticleInfo(articleInfoDO));
            }
        }
        System.out.println(list.size());
        pageResult.setTotalCount(count);
        pageResult.setData(list);
        return pageResult;
	}

    @Override
    public PageResult<ArticleInfoDO> queryListByKeyWord(String keyWord, PageCondition pageCondition) {

        PageResult<ArticleInfoDO> pageResult = new PageResult<ArticleInfoDO>(pageCondition);
        List<ArticleInfoDO> list = new ArrayList<ArticleInfoDO>();
        int count = this.articleInfoDao.CountAllByKey(keyWord);
        if (count > 0) {
            list = this.articleInfoDao.findAllByKey(keyWord, pageCondition);
            pageResult.setMessage("文章查询成功!");
        }
        pageResult.setTotalCount(count);
        pageResult.setData(list);

        return pageResult;
    }
    
    @Override
    public List<ArticleInfoDO> findByArticleKeyWord(String keyWord, Integer aiIsTop, PageCondition pageNotice) {
        List<ArticleInfoDO> list = articleInfoDao.findByArticleKeyWord(keyWord, aiIsTop, pageNotice);
        return list;
    }
}
