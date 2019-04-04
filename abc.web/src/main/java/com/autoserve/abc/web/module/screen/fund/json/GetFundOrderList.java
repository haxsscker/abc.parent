/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.fund.json;

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
import com.autoserve.abc.dao.dataobject.FundOrderApplyUserJDO;
import com.autoserve.abc.service.biz.intf.fund.FundOrderService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.FundOrderVOConvert;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.fund.FundOrderApplyUserJVO;

/**
 * 类GetFundOrderList.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月23日 下午12:24:20
 */
public class GetFundOrderList {
    private static Logger    logger = LoggerFactory.getLogger(GetFundOrderList.class);

    @Resource
    private FundOrderService fundOrderService;

    public JsonPageVO<FundOrderApplyUserJVO> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        logger.info("fundOrder list rows:" + rows + " page:" + page);
        FundOrderApplyUserJDO pojo = new FundOrderApplyUserJDO();

        String searchForm = params.getString("searchForm");
        JSONObject searchFormJson = JSON.parseObject(searchForm);
        if (null != searchFormJson) {
            JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
            String faFundNo = "";
            String faFundName = "";
            String foUserName = "";
            String foUserPhone = "";
            Integer foOrderState = -1;
            for (Object item : itemsArray) {
                JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                String field = String.valueOf(itemJson.get("Field"));
                String value = String.valueOf(itemJson.get("Value"));

                // 项目名称
                if ("fun_fund_no".equals(field)) {
                    faFundNo = value;
                }
                // 基金名称
                if ("fun_fund_name".equals(field)) {
                    faFundName = value;
                }
                // 联系人
                if ("emp_NickName".equals(field)) {
                    foUserName = value;
                }
                // 联系电话
                if ("fun_user_phone".equals(field)) {
                    foUserPhone = value;
                }
                // 审核状态
                if ("fun_check_state".equals(field)) {
                    foOrderState = Integer.parseInt(value);
                }
            }
            if (StringUtils.isBlank(faFundNo)) {
                pojo.setFaFundNo(faFundNo);
            }
            if (StringUtils.isBlank(faFundName)) {
                pojo.setFaFundName(faFundName);
            }
            if (StringUtils.isBlank(foUserName)) {
                pojo.setFoUserName(foUserName);
            }
            if (StringUtils.isBlank(foUserPhone)) {
                pojo.setFoUserPhone(foUserPhone);
            }
            if (null != foOrderState) {
                pojo.setFoOrderState(foOrderState);
            }
        }

        PageResult<FundOrderApplyUserJDO> pageReslut = fundOrderService.queryList(pojo, pageCondition);
        List<FundOrderApplyUserJVO> fundOrderVOList = new ArrayList<FundOrderApplyUserJVO>();
        if (pageReslut.getData() != null && !pageReslut.getData().isEmpty()) {
            fundOrderVOList = FundOrderVOConvert.convertToList(pageReslut.getData());
        }
        JsonPageVO<FundOrderApplyUserJVO> pageVO = new JsonPageVO<FundOrderApplyUserJVO>();
        PageResult<FundOrderApplyUserJVO> pageReslutVO = new PageResult<FundOrderApplyUserJVO>(pageCondition);
        pageReslutVO.setData(fundOrderVOList);
        if (fundOrderVOList != null) {
            pageVO.setRows(pageReslutVO.getData());
            pageVO.setTotal(pageReslutVO.getData().size());
        }
        return pageVO;
    }

}
