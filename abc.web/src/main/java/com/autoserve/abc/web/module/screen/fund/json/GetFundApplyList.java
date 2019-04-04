package com.autoserve.abc.web.module.screen.fund.json;

import java.math.BigDecimal;
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
import com.autoserve.abc.dao.dataobject.FundApplyDO;
import com.autoserve.abc.dao.dataobject.search.FundApplySearchDO;
import com.autoserve.abc.service.biz.intf.fund.FundApplyService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.FundApplyVOConvert;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.fund.FundApplyVO;

/**
 * @author wangyongheng 2014/12/10
 */
public class GetFundApplyList {
    private static Logger    logger = LoggerFactory.getLogger(GetFundApplyList.class);

    @Resource
    private FundApplyService fundApplyService;

    public JsonPageVO<FundApplyVO> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        logger.info("fundApply list rows:" + rows + " page:" + page);
        String searchFlg = params.getString("modelAction");
        JsonPageVO<FundApplyVO> pageVO = new JsonPageVO<FundApplyVO>();
        if (("Search").equals(searchFlg)) { // 检索框检索
            FundApplySearchDO fundApplySearchDO = new FundApplySearchDO();
            String searchForm = params.getString("SearchForm");
            if (StringUtils.isNotBlank(searchForm)) {
                try {
                    JSONObject searchFormJson = JSON.parseObject(searchForm);
                    JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
                    for (Object item : itemsArray) {
                        JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                        String field = String.valueOf(itemJson.get("Field"));
                        String value = String.valueOf(itemJson.get("Value"));
                        String method = String.valueOf(itemJson.get("Method"));

                        if ("faFundName".equals(field)) {
                            fundApplySearchDO.setFaFundName(value);
                        }
                        if ("faFundMoney".equals(field) && "GreaterThanOrEqual".equals(method)) {
                            fundApplySearchDO.setFaFundMoneyFrom(new BigDecimal(value));
                        }
                        if ("faFundMoney".equals(field) && "LessThanOrEqual".equals(method)) {
                            fundApplySearchDO.setFaFundMoneyTo(new BigDecimal(value));
                        }
                        if ("faAddDate".equals(field) && "GreaterThanOrEqual".equals(method)) {
                            fundApplySearchDO.setFaAddDateFrom(DateUtil.parseDate(value, DateUtil.DATE_FORMAT));
                        }
                        if ("faAddDate".equals(field) && "LessThanOrEqual".equals(method)) {
                            fundApplySearchDO.setFaAddDateTo(DateUtil.parseDate(value, DateUtil.DATE_FORMAT));
                        }
                        if ("faFundState".equals(field)) {
                            fundApplySearchDO.setFaFundState(Integer.valueOf(value));
                        }
                    }
                } catch (Exception e) {
                    logger.error("有限合伙管理-有限合伙维护 查询参数解析出错", e);
                }
            }

            PageResult<FundApplyDO> pageReslut = fundApplyService.queryPageListBySearchParam(fundApplySearchDO,
                    pageCondition);
            if (pageReslut != null) {
                List<FundApplyVO> fundApplyVOList = FundApplyVOConvert.convertVoList(pageReslut.getData());
                pageVO.setRows(fundApplyVOList);
                pageVO.setTotal(pageReslut.getTotalCount());
            } else {
                pageVO.setRows(new ArrayList<FundApplyVO>());
                pageVO.setTotal(0);
            }
        } else { // 全检索
            FundApplyDO pojo = new FundApplyDO();
            PageResult<FundApplyDO> pageReslut = fundApplyService.queryList(pojo, pageCondition);
            if (pageReslut.isSuccess()) {
                List<FundApplyVO> fundApplyVOList = FundApplyVOConvert.convertVoList(pageReslut.getData());
                pageVO.setRows(fundApplyVOList);
                pageVO.setTotal(pageReslut.getTotalCount());
            }
        }

        return pageVO;
    }

}
