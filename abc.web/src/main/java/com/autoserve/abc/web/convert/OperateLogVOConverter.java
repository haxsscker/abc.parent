package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.OperateLogJDO;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.sysset.OperateLogVO;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2015/1/10 22:11.
 */
public class OperateLogVOConverter {
    public static OperateLogVO convertToVO(OperateLogJDO operateLogJDO) {
        OperateLogVO vo = new OperateLogVO();
        vo.setOlo_Id(operateLogJDO.getOlId());
        vo.setOlEmpId(operateLogJDO.getOlEmpId());
        vo.setEmp_Name(operateLogJDO.getEmpName());
        vo.setOlo_Content(operateLogJDO.getOlContent());
        vo.setOlo_IP(operateLogJDO.getOlIp());
        vo.setOlo_Model(operateLogJDO.getOlModule());
        vo.setOlo_OperateTime(new DateTime(operateLogJDO.getOlOperateTime()).toString(DateUtil.DATE_TIME_FORMAT));
        vo.setOlo_OperateType(operateLogJDO.getOlOperateType());

        return vo;
    }

    public static List<OperateLogVO> convertToVOList(List<OperateLogJDO> operateLogVOs) {
        List<OperateLogVO> result = new ArrayList<OperateLogVO>();
        if (operateLogVOs == null || operateLogVOs.isEmpty()) {
            return result;
        }
        for (OperateLogJDO operateLogJDO : operateLogVOs) {
            result.add(convertToVO(operateLogJDO));
        }
        return result;
    }
}
