package com.autoserve.abc.web.module.screen.dataMigration.json;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 类AccountManagementListView.java的实现描述：TODO 类实现描述
 * 
 * @author ipeng 2014年12月20日 下午1:18:33
 */
public class AccountManagementListView {
    private static Logger logger = LoggerFactory.getLogger(AccountManagementListView.class);

    @Resource
    private UserService   userService;

    public JsonPageVO<UserDO> execute(ParameterParser params) {
    	logger.info("查询个人客户");
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        UserDO userDO = new UserDO();
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    // 客户名称
                    if ("userName".equals(field)) {
                    	userDO.setUserName(value);
                    }
                    // 真实姓名
                    else if ("userRealName".equals(field)) {
                    	userDO.setUserRealName(value);
                    }
                    // 用户手机
                    else if ("userPhone".equals(field)) {
                    	userDO.setUserPhone(value);
                    }
                    // 账户体系类别
                    else if ("accountKind".equals(field)) {
                    	userDO.setAccountKind(value);
                    }
                    // 注册开始时间
                    else if ("startRegisterDate".equals(field)) {
                    	userDO.setStartRegisterDate(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                    }// 注册结束时间
                    else if ("endRegisterDate".equals(field)) {
                    	userDO.setEndRegisterDate(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                    }
                }
            } catch (Exception e) {
                logger.error("客户信息－个人客户－搜索查询 查询参数解析出错", e);
            }
        }
        //默认查询个人客户
        userDO.setUserType(UserType.PERSONAL.getType());
        PageResult<UserDO> pageResult = userService.queryForBatchUserRegister(userDO, pageCondition);
        return ResultMapper.toPageVO(pageResult);
    }
}
