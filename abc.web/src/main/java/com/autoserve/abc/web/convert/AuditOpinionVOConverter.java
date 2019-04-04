package com.autoserve.abc.web.convert;

import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.review.AuditOpinionVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-29,12:51
 */
public class AuditOpinionVOConverter {

    public static AuditOpinionVO toAuditOpinionVO(ReviewOp reviewOp) {
        AuditOpinionVO vo = new AuditOpinionVO();
        if (reviewOp == null) {
            return vo;
        }

        vo.setPro_check_role(reviewOp.getRole().roleName);
        vo.setPro_check_emp(reviewOp.getEmployee().getEmpName());
        vo.setPro_check_date(DateUtil.formatDate(reviewOp.getDate()));
        vo.setPro_check_op(reviewOp.getOpType().prompt);
        vo.setPro_check_idear(reviewOp.getMessage());

        if (reviewOp.getNextRole() != null) {
            vo.setPro_check_next_role(reviewOp.getNextRole().roleName);
        }

        if (reviewOp.getNextEmp() != null) {
            vo.setPro_check_next_emp(StringUtils.defaultIfBlank(reviewOp.getNextEmp().getEmpName(), "-"));
        }

        return vo;
    }

    public static List<AuditOpinionVO> toVoList(List<ReviewOp> reviewOpList) {
        if (CollectionUtils.isEmpty(reviewOpList)) {
            return Collections.emptyList();
        }

        List<AuditOpinionVO> voList = Lists.newArrayListWithCapacity(reviewOpList.size());
        for (ReviewOp reviewOp : reviewOpList) {
            voList.add(toAuditOpinionVO(reviewOp));
        }

        return voList;
    }
}
