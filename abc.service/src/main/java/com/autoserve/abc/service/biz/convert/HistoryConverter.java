package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.GovernmentUpdateDetailDO;
import com.autoserve.abc.service.biz.entity.History;
import com.autoserve.abc.service.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/12 15:58.
 */
public class HistoryConverter {
    public static GovernmentUpdateDetailDO convertToDO(History history) {
        GovernmentUpdateDetailDO gdo = new GovernmentUpdateDetailDO();
        gdo.setGuhField(history.getGuhField());
        gdo.setGuhFieldOld(String.valueOf(history.getGuhFieldOld()));
        gdo.setGuhFiledNew(String.valueOf(history.getGuhFiledNew()));

        return gdo;
    }

    public static History convertToHistory(GovernmentUpdateDetailDO gdo) {
        History history = new History();
        history.setGuhField(gdo.getGuhField());
        history.setGuhFieldOld(gdo.getGuhFieldOld());
        history.setGuhFiledNew(gdo.getGuhFiledNew());

        return history;
    }

    public static List<History> convertToHistoryList(List<GovernmentUpdateDetailDO> detailDOs){
        if (detailDOs == null || detailDOs.isEmpty())
            throw new BusinessException("传入的list为空");
        List<History> result = new ArrayList<History>();
        for (GovernmentUpdateDetailDO gdo : detailDOs) {
            result.add(convertToHistory(gdo));
        }
        return result;
    }
}
