/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.loan.json;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 贷后资料补全
 * 
 * @author segen189 2014年12月27日 下午9:16:56
 */
public class PostLoanInfoEdit {
    private static final Log log = LogFactory.getLog(PostLoanInfoEdit.class);

    @Resource
    private LoanService      loanService;

    public JsonBaseVO execute(Context context, ParameterParser params) {
    	try {
            BaseResult baseResult = new BaseResult();

            Integer loanId = params.getInt("loanId");
            String postLoanInfo = params.getString("postLoanInfo");
            
            baseResult = this.loanService.postLoanInfoEdit(loanId, postLoanInfo);

            return ResultMapper.toBaseVO(baseResult);
        } catch (Exception e) {
            if (log.isInfoEnabled()) {
                log.info("解析出错", e);
            }
            return directReturnError("参数解析失败");
        }
    }

    private JsonBaseVO directReturnError(String msg) {
        JsonBaseVO result = new JsonBaseVO();
        result.setMessage(msg);
        result.setSuccess(false);
        return result;
    }
}
