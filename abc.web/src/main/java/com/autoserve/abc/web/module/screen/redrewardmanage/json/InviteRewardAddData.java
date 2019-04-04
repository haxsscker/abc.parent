package com.autoserve.abc.web.module.screen.redrewardmanage.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.Red;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 推荐送红包
 *
 * @author fangrui 2015年1月5日 下午12:15:58
 */
public class InviteRewardAddData {
    private static Logger logger = LoggerFactory.getLogger(InviteRewardAddData.class);

    @Resource
    private RedService    redService;

    public JsonBaseVO execute(ParameterParser params) {
        BaseResult baseResult = new BaseResult();
        //奖励主题
        String redTheme = params.getString("redTheme");
        //使用范围
        String redUseScopeStr = params.getString("redUseScopes").replace(",", "|");
        //红包使用状态 
        RedState redState = RedState.valueOf(params.getInt("isEnable"));
        //有效日期
        int redClosetime = params.getInt("redClosetime");
        //红包总额
        int redTotalVal = params.getInt("redTotalVal");
        int calcuTotal = 0;

//        Red param = new Red();
//        param.setRedType(RedenvelopeType.PERSON_RED);
//        PlainResult<Red> searchResult = redService.findByParam(param);
//        if (!searchResult.isSuccess() || searchResult.getData() == null) {
//            baseResult.setError(CommonResultCode.BIZ_ERROR, searchResult.getMessage());
//            return ResultMapper.toBaseVO(baseResult);
//        }

        List<Red> redList = new ArrayList<Red>();

        JSONArray detailArray = JSON.parseArray(params.getString("RedDetail"));
        for (Object detailObj : detailArray) {
            JSONObject jsonObj = (JSONObject) detailObj;
            DateTime fullDaytime = new DateTime();
            for (int i = 0; i < jsonObj.getIntValue("faceAmt"); i++) {
                Red red = new Red();
                red.setRedTheme(redTheme);
                red.setRedUseScope(redUseScopeStr);
                red.setRedState(redState);
                red.setRedClosetime(redClosetime);
                red.setRedSendtime(new Date());
                red.setRedCreator(LoginUserUtil.getEmpId());
                red.setRedType(RedenvelopeType.INVIT_RED);
                red.setRedAmt(Double.valueOf(redTotalVal));
                red.setRedAmount(jsonObj.getDouble("faceVal"));

                calcuTotal += jsonObj.getDouble("faceVal");
                redList.add(red);
            }
        }

        if (redTotalVal != calcuTotal) {
            baseResult.setError(CommonResultCode.BIZ_ERROR, "红包总金额和各面值总额需相等");
        } else {
            baseResult = redService.batchCreateRed(redList);
            if (baseResult.isSuccess()) {
                baseResult.setMessage("推荐送红包插入成功");
            } else {
                baseResult.setMessage("推荐送红包插入失败");
            }
        }

        return ResultMapper.toBaseVO(baseResult);
    }
}
