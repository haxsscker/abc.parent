/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.sysset.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LoginLogJDO;
import com.autoserve.abc.dao.dataobject.search.LoginLogSearchDO;
import com.autoserve.abc.service.biz.intf.employee.LoginLogService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.LoginLogVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.sysset.LoginLogVO;

/**
 * 登录用户日志
 *
 * @author segen189 2015年1月5日 下午12:01:00
 */
public class LoginLogListView {

    @Resource
    private LoginLogService loginLogService;

    public JsonPageVO<LoginLogVO> execute(ParameterParser params) {
        PageCondition pageCondition = new PageCondition(params.getInt("page"), params.getInt("rows"));
        String employeeName = params.getString("employeeName");
        String loginState = params.getString("loginState");
        LoginLogSearchDO searchDO = new LoginLogSearchDO();
        searchDO.setEmployeeName(employeeName);
        if (loginState != null) {
            searchDO.setLoginState(Integer.valueOf(loginState));
        }
        PageResult<LoginLogJDO> logResult = loginLogService.queryPageList(searchDO, pageCondition);
        JsonPageVO<LoginLogVO> pageVO = new JsonPageVO<LoginLogVO>();
        pageVO.setRows(LoginLogVOConverter.convertToVOList(logResult.getData()));
        pageVO.setTotal(logResult.getTotalCount());
        return pageVO;

        //        return ResultMapper.toPageVO(logResult, convertedRows == null ? new ArrayList<LoginLogVO>() : convertedRows);
    }

}
