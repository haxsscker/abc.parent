package com.autoserve.abc.web.module.screen.invite.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InviteJDO;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.RoleUtil;
import com.autoserve.abc.web.convert.InviteVOConverter;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.invite.InviteVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author RJQ 2015/1/5 15:07.
 */
public class GetRewardList {
    @Autowired
    private InviteService inviteService;

    private static Logger logger = LoggerFactory.getLogger(GetRewardList.class);

    public JsonPageVO<InviteVO> execute(ParameterParser params) {
        JsonPageVO<InviteVO> vo = new JsonPageVO<InviteVO>();

        //建立分页查询条件
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        InviteJDO inviteJDO = new InviteJDO();
        String searchForm = params.getString("SearchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    String method = String.valueOf(itemJson.get("Method"));

                    if ("cst_user_name".equals(field)) {
                        inviteJDO.setInviteeName(value);
                    }
                    //由于params.getInt("xxx")返回值为0时表示前台没有此参数传来，和业务需要的0值有混淆，这里用getString去取，再转换
                    if ("cst_realname_prove".equals(field)) {
                        inviteJDO.setUserRealnameIsproven(Integer.valueOf(value));
                    }
                    if ("cst_binding_mobile".equals(field)) {
                        inviteJDO.setUserMobileIsbinded(Integer.valueOf(value));
                    }
                    if ("cst_binding_email".equals(field)) {
                        inviteJDO.setUserEmailIsbinded(Integer.valueOf(value));
                    }
                    if ("sys_user_state".equals(field)) {
                        if ("NotEqual".equals(method)) {
                            inviteJDO.setExcludeState4Search(Integer.valueOf(value));
                        } else {
                            inviteJDO.setUserBusinessState(Integer.valueOf(value));
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("邀请奖励－搜索查询 查询参数解析出错", e);
            }
        }

        //平台客服返回所有邀请奖励列表，其他角色返回自己邀请的相关列表
        if(!RoleUtil.checkRole(LoginUserUtil.getEmpRoles(), BaseRoleType.PLATFORM_SERVICE)){//当前角色不是平台
            inviteJDO.setInviteUserId(LoginUserUtil.getEmpId());
        }
        PageResult<InviteJDO> result = inviteService.queryList(inviteJDO, pageCondition);
        if (result.isSuccess()) {
            List<InviteVO> inviteVOs = InviteVOConverter.convertToVOList(result.getData());
            vo.setTotal(result.getTotalCount());
            vo.setRows(inviteVOs);
        }

        return vo;
    }
}
