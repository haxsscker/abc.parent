package com.autoserve.abc.web.module.screen.account.myAward;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreConfigDO;
import com.autoserve.abc.service.biz.intf.score.ScoreConfigService;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 类CreditGoods.java的实现描述：积分兑换商品展示
 * 
 * @author WangMing 2015年5月14日 下午2:51:53
 */
public class CreditGoods {

    @Resource
    private ScoreConfigService scoreConfigService;

    public void execute(Context context, ParameterParser params) {

        Integer pageSize = 5;
        Integer currentPage = 1;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        PageResult<ScoreConfigDO> pageResult = scoreConfigService.queryScoreConfigList(new ScoreConfigDO(),
                pageCondition);
        context.put("pageResult", pageResult);
    }
}
