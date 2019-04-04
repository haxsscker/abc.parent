package com.autoserve.abc.web.module.screen.selfprove.json;

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
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
/**
 * 
 * 类MobileListView.java的实现描述：TODO 类实现描述 
 * @author lipeng 2014年12月22日 下午12:56:34
 */
public class MobileListView {
    private static Logger logger = LoggerFactory
            .getLogger(AccountManagementListView.class);
    @Resource
    private UserService userService;
    
    public JsonPageVO<UserDO> execute(ParameterParser params){
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        
        UserDO userDO = new UserDO();
        
        String userMobileVerifyDateStart = null;
        String userMobileVerifyDateStop = null;
       
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
                    // 认证状态
                    else if ("userMobileIsbinded".equals(field)) {
                      userDO.setUserMobileIsbinded(Integer.parseInt(value));
                    }
                    //认证日期
                    else if ("userMobileVerifyDate_Start".equals(field)){
                        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        userMobileVerifyDateStart = value;
                    }
                    else if("userMobileVerifyDate_Stop".equals(field)){
                        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        userMobileVerifyDateStop = value;
                    }
                }
            } catch (Exception e) {
                logger.error("客户信息－个人客户－搜索查询 查询参数解析出错", e);
            }
        }
        PageResult<UserDO> pageResult = userService.queryListMobile(userDO,userMobileVerifyDateStart,userMobileVerifyDateStop,pageCondition);
        return ResultMapper.toPageVO(pageResult);
    }
}
