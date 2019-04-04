package com.autoserve.abc.web.module.screen.credit.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CreditJDO;
import com.autoserve.abc.dao.dataobject.search.CreditSearchDO;
import com.autoserve.abc.service.biz.intf.credit.CreditService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.CreditVOConverter;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.credit.CreditApplyVO;

/**
 * 信用额度审核列表
 * 
 * @author RJQ 2015/1/6 15:40.
 */
public class GetCreditList {
    @Autowired
    private CreditService creditService;

    private static Logger logger = LoggerFactory.getLogger(GetCreditList.class);

    public JsonPageVO<CreditApplyVO> execute(ParameterParser params) {
        //建立分页查询条件
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        JsonPageVO<CreditApplyVO> vo = new JsonPageVO<CreditApplyVO>();
        PageResult<CreditJDO> pageResult;

        String searchForm = params.getString("SearchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            CreditSearchDO searchDO = new CreditSearchDO();
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    String method = String.valueOf(itemJson.get("Method"));

                    if ("cst_user_name".equals(field)) {
                        searchDO.setUserName(value);
                    }
                    if ("cre_apply_date".equals(field)) {
                        if ("GreaterThanOrEqual".equals(method)) {
                            searchDO.setCreditApplyStartDate(DateUtil.parseDate(value, DateUtil.DATE_TIME_FORMAT));
                        } else if ("LessThanOrEqual".equals(method)) {
                            searchDO.setCreditApplyEndDate(DateUtil.parseDate(value, DateUtil.DATE_TIME_FORMAT));
                        }
                    }
                    if ("cre_check_money".equals(field)) {
                        if ("GreaterThanOrEqual".equals(method)) {
                            searchDO.setCreditReviewStartAmount(new BigDecimal(value));
                        } else if ("LessThanOrEqual".equals(method)) {
                            searchDO.setCreditReviewEndAmount(new BigDecimal(value));
                        }
                    }
                    if ("cre_credit_type".equals(field)) {
                        searchDO.setCreditType(Integer.parseInt(value));
                    }
                    if ("cre_check_state".equals(field)) {
                        searchDO.setCreditReviewState(Integer.parseInt(value));
                    }
                }
            } catch (Exception e) {
                logger.error("信用额度－搜索查询 查询参数解析出错", e);
            }

            pageResult = creditService.searchList(searchDO, pageCondition);

        } else {
            pageResult = creditService.queryList(new CreditJDO(), pageCondition);
        }

        List<CreditJDO> creditJDOs = pageResult.getData();
        if (!CollectionUtils.isEmpty(creditJDOs)) {
            vo.setRows(CreditVOConverter.convertToVOList(creditJDOs));
            vo.setTotal(pageResult.getTotalCount());
        } else {
            vo.setRows(new ArrayList<CreditApplyVO>());
            vo.setTotal(0);
        }

        return vo;
    }
}
