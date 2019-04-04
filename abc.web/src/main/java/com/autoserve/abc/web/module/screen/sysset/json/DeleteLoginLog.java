package com.autoserve.abc.web.module.screen.sysset.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.employee.LoginLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 删除登录用户日志
 *
 * @author RJQ 2015年1月5日 下午12:01:00
 */
public class DeleteLoginLog {

    @Resource
    private LoginLogService loginLogService;

    public JsonBaseVO execute(ParameterParser params) {
        JsonBaseVO vo = new JsonBaseVO();
        String ids = params.getString("id");
        if(ids != null){
            BaseResult result;
            if(ids.equals("all")){
                result = loginLogService.removeAll();
            }else{
                String[] idsStr = ids.split(",");
                List<Integer> delIds = new ArrayList<Integer>();
                for (String idStr : idsStr){
                    delIds.add(Integer.valueOf(idStr));
                }
                result = loginLogService.removeByIds(delIds);
            }

            if(!result.isSuccess()){
                vo = ResultMapper.toBaseVO(result);
            }
        }

        return vo;
    }

}
