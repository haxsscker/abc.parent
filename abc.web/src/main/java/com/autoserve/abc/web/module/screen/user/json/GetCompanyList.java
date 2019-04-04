/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.user.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CompanyUserJDO;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 类GetCompanyList.java的实现描述：TODO 类实现描述
 * 
 * @author zhangkang 2015年6月10日 下午1:48:15
 */
public class GetCompanyList {
    private static Logger          logger = LoggerFactory.getLogger(GetCompanyList.class);

    @Resource
    private CompanyCustomerService companyCustomerService;

    public JsonPageVO<CompanyUserJDO> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        CompanyUserJDO companyUserJDO = new CompanyUserJDO();

        String companyName = params.getString("companyName");
        if (!StringUtils.isBlank(companyName)) {
            companyUserJDO.setCcCompanyName(companyName);
        }
        PageResult<CompanyUserJDO> pageResult = companyCustomerService.queryOpenAccountList(companyUserJDO,
                pageCondition);
        for (CompanyUserJDO companyUser : pageResult.getData()) {
            Date registerDate = companyUser.getCcRegisterDate();
            if (registerDate != null) {
                companyUser.setCcRegisterDateStr(new SimpleDateFormat("yyyy-MM-dd").format(registerDate));
            }
        }
        return ResultMapper.toPageVO(pageResult);
    }

}
