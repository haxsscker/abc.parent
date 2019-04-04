package com.autoserve.abc.web.module.screen.redrewardmanage.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.Red;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 红包管理-红包查询-项目奖励红包
 * 
 * @author lipeng 2015年1月5日 下午8:15:58
 */
public class ProjectRewardAddData {
    private static Logger  logger = LoggerFactory.getLogger(ProjectRewardAddData.class);

    @Resource
    private RedService     redService;

    @Resource
    private RedsendService redsendService;

    public JsonBaseVO execute(ParameterParser params) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        String redTheme = params.getString("redTheme");
        Double redAmt = params.getDouble("redAmt");
        String redUseScope = params.getString("redUseScopes");
        int redClosetime = params.getInt("redClosetime");
        String[] ids = params.getString("loanIds").split("\\|");
        List<Integer> lists = new ArrayList<Integer>();

        for (int i = 0; i < ids.length; i++) {
            lists.add(Integer.parseInt(ids[i]));
        }

        List<Red> listRed = new ArrayList<Red>();
        for (Integer bizid : lists) {
            Red red = new Red();
            red.setRedType(RedenvelopeType.PROJECT_RED);
            red.setRedTheme(redTheme);
            red.setRedAmt(redAmt);
            red.setRedState(RedState.EFFECTIVE);
            red.setRedUseScope(redUseScope);
            red.setRedClosetime(redClosetime);
            red.setRedCreatetime(new Date());
            red.setRedRewardNumber(lists.size());
            red.setRedName(redTheme);
            red.setRedBizid(bizid);
            red.setRedAmount(redAmt);
            listRed.add(red);
        }

        result = redService.batchCreateRed(listRed);

        if (result.isSuccess()) {
            result.setMessage("项目奖励红包插入成功");
        } else {
            result.setMessage("项目奖励红包插入失败");
        }

        return ResultMapper.toBaseVO(result);
    }

}
