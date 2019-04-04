/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.projectmanage.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.LoanVOConverter;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.projectmanage.LoanVO;

/**
 * 项目发布、项目跟踪父类
 * 
 * @author segen189 2014年12月23日 下午5:32:47
 */
public abstract class AbstractLoanProjectListView {
    private static final Log    log = LogFactory.getLog(AbstractLoanProjectListView.class);

    @Resource
    protected LoanQueryService  loanQueryService;

    @Resource
    protected UserService       userService;

    @Resource
    protected GovernmentService governmentService;

    protected LoanSearchDO assembleLoanSearchParamIfNecessary(ParameterParser params) {
        String searchForm = params.getString("SearchForm");

        if (StringUtils.isNotBlank(searchForm)) {
            try {
                LoanSearchDO searchDO = new LoanSearchDO();

                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    // 项目名称
                    if ("loanNo".equals(field)) {
                        searchDO.setLoanNo(value);
                    }
                    // 借款人
                    if ("loaneeName".equals(field)) {
                        searchDO.setLoaneeName(value);
                    }
                    // 担保机构
                    else if ("guarGovName".equals(field)) {
                        searchDO.setGuarGovName(value);
                    }
                    // 项目类型 -2表示全部
                    else if ("loanCategory".equals(field) && !"-2".equals(value)) {
                        searchDO.setLoanCategory(Integer.valueOf(value));
                    }
                    // 申请日期低值
                    else if ("applyDateStart".equals(field)) {
                        searchDO.setApplyDateStart(DateUtil.parseDate(value, DateUtil.DATE_FORMAT));
                    }
                    // 申请日期高值
                    else if ("applyDateEnd".equals(field)) {
                        searchDO.setApplyDateEnd(DateUtil.parseDate(value, DateUtil.DATE_FORMAT));
                    }

                    // 借款金额低值
                    else if ("loanMoneyStart".equals(field)) {
                        searchDO.setLoanMoneyStart(Double.valueOf(value));
                    }
                    // 借款金额高值
                    else if ("loanMoneyEnd".equals(field)) {
                        searchDO.setLoanMoneyEnd(Double.valueOf(value));
                    }
                    // 项目状态 -2表示全部
                    else if ("loanStatus".equals(field) && !"-2".equals(value)) {
                        searchDO.setLoanState(Arrays.asList(Integer.valueOf(value)));
                    }
                }
                return searchDO;
            } catch (Exception e) {
                log.error("项目管理－搜索查询 查询参数解析出错", e);
            }
        }
        return null;
    }

    protected List<LoanVO> convertLoanVOFields(PageResult<Loan> result) {
        if (!result.isSuccess()) {
            return null;
        }

        List<LoanVO> data = LoanVOConverter.getInstance().convert(result.getData());

        if (CollectionUtils.isNotEmpty(data)) {
            // userIdset, govIds
            Set<Integer> userIdSet = new HashSet<Integer>(data.size());
            Set<Integer> govIdSet = new HashSet<Integer>(data.size());
            for (LoanVO loanVO : data) {
                userIdSet.add(loanVO.getLoanUserId());
                govIdSet.add(loanVO.getLoanGuarGov());
            }
            // 映射关系Map
            Map<Integer, String> userNameMap = new HashMap<Integer, String>();
            Map<Integer, String> govNameMap = new HashMap<Integer, String>();
            Map<Integer, String> userPhoneMap = new HashMap<Integer, String>();

            // 查询用户服务
            ListResult<UserDO> userResult = userService.findByList(new ArrayList<Integer>(userIdSet));
            if (userResult.isSuccess()) {
                for (UserDO userDO : userResult.getData()) {
                    userNameMap.put(userDO.getUserId(), userDO.getUserName());
                    userPhoneMap.put(userDO.getUserId(), userDO.getUserPhone());
                }
            }

            // 查询机构服务
            ListResult<GovernmentDO> govResult = governmentService.findByList(new ArrayList<Integer>(govIdSet));
            if (govResult.isSuccess()) {
                for (GovernmentDO govDO : govResult.getData()) {
                    govNameMap.put(govDO.getGovId(), govDO.getGovName());
                }
            }

            // 用户id转换成用户名
            // 机构id转换成机构名
            for (LoanVO loanVO : data) {
            	
                loanVO.setLoaneeName(userNameMap.get(loanVO.getLoanUserId()));
                loanVO.setGovName(govNameMap.get(loanVO.getLoanGuarGov()));
                loanVO.setLoanUserPhone(userPhoneMap.get(loanVO.getLoanUserId()));
            }

        }

        return data;
    }
}
