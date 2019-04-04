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

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.InvestVOConverter;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.projectmanage.InvestVO;

/**
 * 项目的分页投资列表
 *
 * @author segen189 2014年12月25日 下午12:05:32
 */
public class ProjectInvestListView {

    @Resource
    private InvestQueryService                investQueryService;

    @Resource
    private UserService                       userService;

    private static final JsonPageVO<InvestVO> defaultSuccess = new JsonPageVO<InvestVO>();

    public JsonPageVO<InvestVO> execute(ParameterParser params, Context context) {
        int bidType = params.getInt("bidType");
        int bidId = params.getInt("bidId");
        int page = params.getInt("page");
        int rows = params.getInt("rows");

        if (BidType.valueOf(bidType) != null) {
            InvestSearchDO searchDO = new InvestSearchDO();

            searchDO.setBidId(bidId);
            searchDO.setBidType(bidType);
            //2015-12-7 加上被转让状态，因满标资金划转查看时需要查看投标记录
            searchDO.setInvestStates(Arrays.asList(InvestState.PAID.getState(), InvestState.EARNING.getState(),
                    InvestState.EARN_COMPLETED.getState(), InvestState.TRANSFERED.getState()));

            PageResult<Invest> investResult = investQueryService.queryInvestList(searchDO,
                    new PageCondition(page, rows));

            List<InvestVO> convertedRows = convertFields(investResult);

            return ResultMapper.toPageVO(investResult, convertedRows);
        } else {
            return defaultSuccess;
        }
    }

    protected List<InvestVO> convertFields(PageResult<Invest> result) {
        if (!result.isSuccess()) {
            return null;
        }

        List<InvestVO> data = InvestVOConverter.getInstance().convert(result.getData());

        if (CollectionUtils.isNotEmpty(data)) {
            // userIdset
            Set<Integer> userIdSet = new HashSet<Integer>(data.size());
            for (Invest invest : data) {
                userIdSet.add(invest.getUserId());
            }

            // 映射关系Map
            Map<Integer, String> userNameMap = new HashMap<Integer, String>();

            // 查询用户服务
            ListResult<UserDO> userResult = userService.findByList(new ArrayList<Integer>(userIdSet));
            if (userResult.isSuccess()) {
                for (UserDO userDO : userResult.getData()) {
                    userNameMap.put(userDO.getUserId(), userDO.getUserName());
                }
            }

            // 用户id转换成用户名
            for (InvestVO investVO : data) {
                investVO.setInvesterName(userNameMap.get(investVO.getUserId()));
            }

        }

        return data;
    }
}
