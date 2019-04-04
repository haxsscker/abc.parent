package com.autoserve.abc.web.module.screen.selfprove.json;

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
 * 类EmailListView.java的实现描述：邮箱信息查询
 * @author lipeng 2014年12月22日 下午3:25:36
 */
public class EmailListView {
    private static Logger logger = LoggerFactory
            .getLogger(AccountManagementListView.class);
    
    
    @Resource
    private UserService userService;
    
    public JsonPageVO<UserDO> execute(ParameterParser params){
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        
        UserDO userDO = new UserDO();
        String userRegisterDateStop = null;
        String userRegisterDateStart = null;
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
                    else if ("userEmailIsbinded".equals(field)) {
                      userDO.setUserEmailIsbinded(Integer.parseInt(value));
                    }
                    //查询开始日期
                    else if ("userRegisterDate_Stop".equals(field)){
                        userRegisterDateStop = value;
                    }
                    //查询结束日期
                    else if ("userRegisterDate_Start".equals(field)){
                        userRegisterDateStart = value;
                    }
                   
                }
            } catch (Exception e) {
                logger.error("客户信息－个人客户－搜索查询 查询参数解析出错", e);
            }
        }
        
        PageResult<UserDO> pageResult = userService.queryListEmail(userDO,userRegisterDateStart,userRegisterDateStop,pageCondition);
        return ResultMapper.toPageVO(pageResult);
    }


}
