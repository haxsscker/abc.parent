package com.autoserve.abc.web.module.screen.article.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.ArticleInfoSreachDO;
import com.autoserve.abc.service.biz.entity.ArticleClass;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.ArticleInfoVOConverter;
import com.autoserve.abc.web.module.screen.employee.json.GetEmployeeList;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.article.ArticleInfoItemVO;
import com.autoserve.abc.web.vo.article.ArticleInfoVO;

/**
 * 类ActionArticleView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月18日 下午3:13:15
 */
public class ActionArticleView {

	private static Logger logger = LoggerFactory.getLogger(GetEmployeeList.class);
	
    @Resource
    private ArticleInfoService  articleInfoService;
    @Resource
    private ArticleClassService articleClassService;

    public ArticleInfoItemVO execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        
        ArticleInfoSreachDO searchDo = new ArticleInfoSreachDO();
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {//搜索
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
                
                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    if ("sys_article_title".equals(field)) {
                    	searchDo.setArticleTitle(value);
                    } else if ("sys_min_add_date".equals(field)) {
                    	searchDo.setStartDate(DateUtil.parseDate(value, "yyyy-MM-dd"));
                    } else if ("sys_max_add_date".equals(field)) {
                    	searchDo.setEndDate(DateUtil.parseDate(value, "yyyy-MM-dd"));
                    } else if ("sys_class_id".equals(field)) {
                    	searchDo.setArticleClassId(Integer.parseInt(value));
                    }
                }
            } catch (Exception e) {
                logger.error("文章列表－搜索查询 查询参数解析出错", e);
            }
        }
        
        PageResult<ArticleInfo> result = articleInfoService.queryListBySearchParam(searchDo, pageCondition);
        List<ArticleInfo> list = result.getData();
        ArticleInfoItemVO vo = new ArticleInfoItemVO();
        List<ArticleInfoVO> listVo = new ArrayList<ArticleInfoVO>();
        for (ArticleInfo articleInfo : list) {
            PlainResult<ArticleClass> classResult = this.articleClassService.queryById(articleInfo.getAiClassId());
            listVo.add(ArticleInfoVOConverter.toArticleInfoVO(articleInfo, classResult.getData().getAcName()));
        }
        vo.setTotal(result.getTotalCount());
        vo.setRows(listVo);
        return vo;
    }
    
}
