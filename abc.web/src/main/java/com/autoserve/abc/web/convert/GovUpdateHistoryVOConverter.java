package com.autoserve.abc.web.convert;

import com.autoserve.abc.service.biz.entity.GovUpdateHistory;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.government.GovUpdateHistoryVO;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/20 11:38.
 */
public class GovUpdateHistoryVOConverter {
    public static GovUpdateHistoryVO convertToHistoryVO(GovUpdateHistory history) {
        GovUpdateHistoryVO vo = new GovUpdateHistoryVO();
        vo.setHistory_id(history.getUpdateHistoryId());
        vo.setGovId(history.getGovId());
        vo.setGov_name(history.getGovName());
        vo.setUpdate_emp_name(history.getUpdateEmpName());
        vo.setGov_edit_date(new DateTime(history.getUpdateDate()).toString(DateUtil.DATE_FORMAT));
        //如果是客服修改的，不需要审核，因此审核相关的字段可能为空，做个判断
        if (history.getReviewEmpName() != null) {
            vo.setReview_emp_name(history.getReviewEmpName());
        }
        if (history.getReviewMessage() != null) {
            vo.setGov_check_idear(history.getReviewMessage());
        }
        if (history.getReviewState() != null) {
            vo.setGov_check_state(history.getReviewState().getDes());
        }
        if (history.getReviewDate() != null) {
            vo.setGov_check_date(new DateTime(history.getReviewDate()).toString(DateUtil.DATE_FORMAT));
        }

        return vo;
    }

    public static List<GovUpdateHistoryVO> convertToList(List<GovUpdateHistory> histories) {
        if (histories == null || histories.isEmpty())
            throw new BusinessException("传入的list为空");
        List<GovUpdateHistoryVO> result = new ArrayList<GovUpdateHistoryVO>();
        for (GovUpdateHistory history : histories) {
            result.add(convertToHistoryVO(history));
        }
        return result;
    }
}
