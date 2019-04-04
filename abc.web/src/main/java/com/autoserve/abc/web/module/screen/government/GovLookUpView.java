package com.autoserve.abc.web.module.screen.government;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.service.biz.entity.History;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.result.PlainResult;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RJQ 2014/12/2 16:38.
 */
public class GovLookUpView {
    @Resource
    private GovernmentService governmentService;

    public void execute(Context context, ParameterParser params) {
        Integer govId = params.getInt("govId");
        String lookUpType = params.getString("lookUpType");
        Integer updateHistoryId = params.getInt("updateHistoryId");

        PlainResult<GovPlainJDO> plainResult = governmentService.findGovPlainWithAreaById(govId);
        if (plainResult.isSuccess()) {
            GovPlainJDO govPlainJDO = plainResult.getData();
            context.put("gov", govPlainJDO);
        }

        if (lookUpType == null) {//一般的查看页面，直接返回
            return;
        }
        List<History> histories = null;
        if ("review".equals(lookUpType)) {//更新审核的查看页面，需要展示字段更新前后的值
            histories = governmentService.findRecentListByGovId(govId).getData();
        } else if ("history".equals(lookUpType)) {//维护记录的查看页面，需要展示当前修改记录字段更新前后的值
            histories = governmentService.findListByHistoryId(updateHistoryId).getData();
        }
        if (null != histories && histories.size() != 0) {
            Map<String, String> historyMap = getHistoryMap(histories);
            context.put("history", historyMap);
        }
    }

    private Map<String, String> getHistoryMap(List<History> histories) {
        Map<String, String> result = new HashMap<String, String>();
        StringBuilder colunmsNames = new StringBuilder();
        StringBuilder oldValues = new StringBuilder();
        StringBuilder newValues = new StringBuilder();
        for (History h : histories) {
            if ("govGuarId".equals(h.getGuhField())) {//如果修改的是担保机构，由于存储的是担保机构的id，形如（1,2,3，），要将这些机构对应的名字查询出来显示
                colunmsNames.append(h.getGuhField()).append("|");
                oldValues.append(findGuarGovNames((String)h.getGuhFieldOld())).append("|");
                newValues.append(findGuarGovNames((String)h.getGuhFiledNew())).append("|");
                continue;
            }
            colunmsNames.append(h.getGuhField()).append("|");
            oldValues.append(h.getGuhFieldOld()).append("|");
            newValues.append(h.getGuhFiledNew()).append("|");
        }
        result.put("columsNames", colunmsNames.toString());
        result.put("oldValues", oldValues.toString());
        result.put("newValues", newValues.toString());

        return result;
    }

    private String findGuarGovNames(String ids) {
        StringBuilder sb = new StringBuilder();
        List<Integer> govIds = new ArrayList<Integer>();
        String[] strIds = ids.split(",");
        for (String id : strIds) {
            govIds.add(Integer.parseInt(id));
        }
        List<GovernmentDO> governmentDOs = governmentService.findByList(govIds).getData();
        if(governmentDOs != null){
            for (GovernmentDO gdo : governmentDOs){
                sb.append(gdo.getGovName()).append(",");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
