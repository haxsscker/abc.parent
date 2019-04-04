/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.selfprove.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CompanyUserJDO;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 类CompanyCustomersListView.java的实现描述：TODO 类实现描述
 * 
 */
public class CompanyCustomersListView {
    private static Logger          logger = LoggerFactory.getLogger(CompanyCustomersListView.class);

    @Resource
    private CompanyCustomerService companyCustomerService;

    public JsonPageVO<CompanyUserJDO> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        CompanyUserJDO companyUserJDO = new CompanyUserJDO();
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    
                    // 公司名称
                    if ("ccCompanyName".equals(field)) {
                        companyUserJDO.setCcCompanyName(value);
                    }
                    // 状态
                    else if ("userState".equals(field)) {
                        companyUserJDO.setUserState(Integer.parseInt(value));
                    }
                    // 推荐人
                    else if ("referees".equals(field)) {
                        //字段还没有，暂时不实现
                    }// 注册开始时间
                    else if ("startRegisterDate".equals(field)) {
                    	companyUserJDO.setStartRegisterDate(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                    }// 注册结束时间
                    else if ("endRegisterDate".equals(field)) {
                    	companyUserJDO.setEndRegisterDate(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                    }
                }
            } catch (Exception e) {
                logger.error("客户信息－公司客户－搜索查询 查询参数解析出错", e);
            }
        }

        PageResult<CompanyUserJDO> pageResult = companyCustomerService.queryList(companyUserJDO, pageCondition);
        for(CompanyUserJDO companyUser:pageResult.getData()){
        	Date registerDate=companyUser.getCcRegisterDate();
        	if(registerDate!=null){
        		companyUser.setCcRegisterDateStr(new SimpleDateFormat("yyyy-MM-dd").format(registerDate));
        	}       	
        }
        return ResultMapper.toPageVO(pageResult);
    }

}
