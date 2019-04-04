package com.autoserve.abc.web.module.screen.score.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.convert.UserConverter;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.UserVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.user.UserVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author RJQ 2014/12/3 13:44.
 */
public class GetScoreList {
    @Resource
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(GetScoreList.class);

    public JsonPageVO<UserVO> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        UserDO userDO = new UserDO();
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {//搜索
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    if ("cst_user_name".equals(field)) {
                        userDO.setUserName(value);
                    } else if ("cst_real_name".equals(field)) {
                        userDO.setUserRealName(value);
                    }
                }
            } catch (Exception e) {
                logger.error("积分列表－搜索查询 查询参数解析出错", e);
            }
        }

        userDO.setUserState(EntityState.STATE_ENABLE.getState());
        PageResult<UserDO> pageResult = userService.queryList(userDO, pageCondition);
        JsonPageVO<UserVO> pageVO = new JsonPageVO<UserVO>();
        List<User> list = UserConverter.convertList(pageResult.getData());
        pageVO.setRows(UserVOConverter.convertToList(list));
        pageVO.setTotal(pageResult.getTotalCount());
        return pageVO;
    }
}
