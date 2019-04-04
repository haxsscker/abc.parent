package com.autoserve.abc.web.module.screen.redrewardmanage.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * web 层 个人奖励红包添加
 *
 * @author fangrui 2015年1月5日 下午12:15:58
 */
public class PersonalRewardAddData {
    private static Logger logger = LoggerFactory.getLogger(PersonalRewardAddData.class);

    @Resource
    private RedService    redService;

    public JsonBaseVO execute(ParameterParser params) {
        BaseResult baseResult = new BaseResult();
        //奖励主题
        String redTheme = params.getString("redTheme");
        //奖励用户
        int redUserId = params.getInt("act_loanee_id");
        //使用范围
        if (StringUtil.isEmpty(params.getString("redUseScopes"))) {
            baseResult.setError(CommonResultCode.BIZ_ERROR, "请填写使用范围");
            return ResultMapper.toBaseVO(baseResult);
        }
        String redUseScopeStr = redSendScopeConverse(params.getString("redUseScopes"));
        //红包使用状态 
        //            RsState redState;
        //            if (params.containsKey("isEnable")) {
        //                redState = RsState.valueOf(params.getInt("isEnable"));
        //            } else {
        //                redState = RsState.WITHOUT_USE;
        //            }
        RsState redState = RsState.WITHOUT_USE;
        //有效日期
        int redClosetime = params.getInt("redClosetime");
        //红包总额
        int redTotalVal = params.getInt("redTotalVal");
        int calcuTotal = 0;
        int rsRedId;

        //        Red param = new Red();
        //        param.setRedType(RedenvelopeType.PERSON_RED);
        //        PlainResult<Red> searchResult = redService.findByParam(param);
        //        if (!searchResult.isSuccess() || searchResult.getData() == null) {
        //            baseResult.setError(CommonResultCode.BIZ_ERROR, searchResult.getMessage());
        //            return ResultMapper.toBaseVO(baseResult);
        //        } else {
        //            rsRedId = searchResult.getData().getRedId();
        //        }

        List<Redsend> sendList = new ArrayList<Redsend>();

        JSONArray detailArray = JSON.parseArray(params.getString("RedDetail"));
        DateTime fullDaytime = new DateTime();
        for (Object detailObj : detailArray) {
            JSONObject jsonObj = (JSONObject) detailObj;
            for (int i = 0; i < jsonObj.getIntValue("faceAmt"); i++) {
                Redsend redsend = new Redsend();

                redsend.setRsTheme(redTheme);
                redsend.setRsUserid(redUserId);
                //redsend.setRsRedId(rsRedId);
                redsend.setRsUseScope(redUseScopeStr);
                redsend.setRsState(redState);
                redsend.setRsClosetime(fullDaytime.plusDays(redClosetime).toDate());
                redsend.setRsStarttime(new Date());
                redsend.setRsSender(LoginUserUtil.getEmpId());
                redsend.setRsType(RedenvelopeType.PERSON_RED);
                redsend.setRsAmt(Double.valueOf(redTotalVal));
                redsend.setRsValidAmount(jsonObj.getDouble("faceVal"));

                calcuTotal += jsonObj.getDouble("faceVal");

                sendList.add(redsend);
            }
        }

        if (redTotalVal != calcuTotal) {
            baseResult.setError(CommonResultCode.BIZ_ERROR, "红包总金额和各面值总额需相等");
        } else {
            baseResult = redService.batchSendRed(sendList);
            if (baseResult.isSuccess()) {
                baseResult.setMessage("个人奖励红包插入成功");
            } else {
                baseResult.setMessage("个人奖励红包插入失败");
            }
        }

        return ResultMapper.toBaseVO(baseResult);
    }
    
    //红包范围转换
    private String redSendScopeConverse(String redScope) {
        StringBuffer redScopes = new StringBuffer();
        if (redScope != null && !"".equals(redScope)) {
            String[] str = redScope.split(",");
            for (int i = 0; i < str.length; i++) {
                if (i == str.length - 1) {
                    redScopes.append(LoanCategory.valueOf(Integer.parseInt(str[i])).getPrompt());
                } else {
                    redScopes.append(LoanCategory.valueOf(Integer.parseInt(str[i])).getPrompt() + ",");
                }
            }
        }
        return redScopes.toString();
    }
}
