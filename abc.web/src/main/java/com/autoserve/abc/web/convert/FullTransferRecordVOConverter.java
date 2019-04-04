/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.dao.dataobject.FullTransferRecordJDO;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.moneyManage.FullTransferRecordVO;

public class FullTransferRecordVOConverter {

    public static FullTransferRecordVO toFullTransferRecordVO(FullTransferRecordJDO fullTransferRecordJDO) {
        FullTransferRecordVO result = new FullTransferRecordVO();

        BeanCopier copier = BeanCopier.create(FullTransferRecordJDO.class, FullTransferRecordVO.class, false);
        copier.copy(fullTransferRecordJDO, result, null);

        if (result.getProFullDate() != null) {
            result.setProFullDateStr(DateUtil.formatDate(result.getProFullDate(), DateUtil.DEFAULT_DAY_STYLE));
        }
        if (result.getProBuyDate() != null) {
            result.setProBuyDateStr(DateUtil.formatDate(result.getProBuyDate(), DateUtil.DEFAULT_DAY_STYLE));
        }
        if (result.getProTransferDate() != null) {
            result.setProTransferDateStr(DateUtil.formatDate(result.getProTransferDate(), DateUtil.DEFAULT_DAY_STYLE));
        }
        if (result.getProStartDate() != null) {
            result.setProStartDateStr(DateUtil.formatDate(result.getProStartDate(), DateUtil.DEFAULT_DAY_STYLE));
        }

        return result;
    }

    public static List<FullTransferRecordVO> toFullTransferRecordVOList(List<FullTransferRecordJDO> fullTransferRecordJDOList) {
        if (fullTransferRecordJDOList == null) {
            return null;
        }

        List<FullTransferRecordVO> result = new ArrayList<FullTransferRecordVO>(fullTransferRecordJDOList.size());
        for (FullTransferRecordJDO fullTransferRecordJDO : fullTransferRecordJDOList) {
            result.add(toFullTransferRecordVO(fullTransferRecordJDO));
        }
        return result;
    }

}
