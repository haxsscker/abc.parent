package com.autoserve.abc.web.module.screen.selfprove.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RealnameAuthJDO;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class GetRealNameAuthList {
    @Resource
    private RealnameAuthService realnameAuthService;
    private static Logger logger = LoggerFactory.getLogger(GetRealNameAuthList.class);

    public JsonPageVO<RealnameAuthJDO> execute(ParameterParser params) {
        //建立分页查询条件
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        logger.info("Employee list rows: " + rows + ", page: " + page);

        //建立搜索条件，可为空
        RealnameAuthJDO realnameAuthJDO = new RealnameAuthJDO();

        /*
         * {"Items":[{"Field":"userName","Value":"admin","Method":"Contains"},
         * {"Field":"rnpName","Value":"ww","Method":"Contains"},
         * {"Field":"rnpDocType","Value":"1","Method":"Equal"},
         * {"Field":"rnpReviewState","Value":"0","Method":"Equal"}]}
         */

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
                        realnameAuthJDO.setUserName(value);
                    }
                    // 真实姓名
                    else if ("rnpName".equals(field)) {
                        realnameAuthJDO.setRnpName(value);
                    }
                    // 证件类型
                    else if ("rnpDocType".equals(field)) {
                        realnameAuthJDO.setRnpDocType(value);
                    }
                    // 审核状态
                    else if ("rnpReviewState".equals(field)) {
                        realnameAuthJDO.setRnpReviewState(Integer.parseInt(value));
                    }
                }
            } catch (Exception e) {
                logger.error("自经营管理－实名认证－搜索查询 查询参数解析出错", e);
            }
        }

        PageResult<RealnameAuthJDO> pageResult = realnameAuthService.queryRealnameAuthJDOList(realnameAuthJDO, pageCondition);

        return ResultMapper.toPageVO(pageResult);
    }
}
