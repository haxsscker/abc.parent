/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.index.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.module.screen.index.BillboardVO;
import com.autoserve.abc.web.util.NumberUtil;
import com.autoserve.abc.web.util.SafeUtil;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 类TopInvestList.java的实现描述：TODO 类实现描述
 * 
 * @author zhangkang 2015年6月16日 上午10:52:40
 */
public class BillBoard {

    @Resource
    private InvestQueryService investQueryService;
    @Resource
    private UserService        userService;

    public JsonPageVO<BillboardVO> execute(ParameterParser param) {
        JsonPageVO<BillboardVO> pageVo = new JsonPageVO<BillboardVO>();
        String time = param.getString("time");
        ListResult<InvestDO> billboard = investQueryService.investmentList(time, new PageCondition(1, 5));
        List<BillboardVO> listBillboardVO = new ArrayList<BillboardVO>();
        for (int i = 0; i < billboard.getData().size(); i++) {
            Integer userid = billboard.getData().get(i).getInUserId();
            PlainResult<UserDO> userdos = userService.findById(userid);
            BillboardVO billboardVO = new BillboardVO();
            billboardVO.setNumber(i + 1);
            billboardVO.setUsername(SafeUtil.hideTaobaoNick(userdos.getData().getUserName()));
            BigDecimal money = billboard.getData().get(i).getInInvestMoney();
            billboardVO.setInInvestMoney(money);
            billboardVO.setFormatInInvestMoney(new NumberUtil().currency(money));
            listBillboardVO.add(billboardVO);

        }
        pageVo.setRows(listBillboardVO);
        pageVo.setTotal(billboard.getCount());
        return pageVo;
    }
}
