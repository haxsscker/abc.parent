package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.employee.EmployeeVO;
import org.joda.time.DateTime;

/**
 * @author RJQ 2014/12/4 20:18.
 */
public class EmployeeConverter {

    public static EmployeeVO convert(EmployeeDO empDO){
        EmployeeVO vo = new EmployeeVO();
        vo.setEmpId(empDO.getEmpId());
        vo.setEmpAddress(empDO.getEmpAddress());
        vo.setEmpArea(empDO.getEmpArea());
        vo.setEmpEmail(empDO.getEmpEmail());
        vo.setEmpHeadImg(empDO.getEmpHeadImg());
        vo.setEmpMobile(empDO.getEmpMobile());
        vo.setEmpPhone(empDO.getEmpPhone());
        vo.setEmpName(empDO.getEmpName());
        vo.setEmpQq(empDO.getEmpQq());
        vo.setEmpRealName(empDO.getEmpRealName());
        vo.setEmpSex(empDO.getEmpSex());
        vo.setEmpBirthday(new DateTime(empDO.getEmpBirthday()).toString(DateUtil.DATE_FORMAT));

        return vo;
    }

    public static EmployeeDO convert(EmployeeVO empVO){
        EmployeeDO eDo = new EmployeeDO();
        eDo.setEmpId(empVO.getEmpId());
        eDo.setEmpAddress(empVO.getEmpAddress());
        eDo.setEmpArea(empVO.getEmpArea());
        eDo.setEmpEmail(empVO.getEmpEmail());
        eDo.setEmpHeadImg(empVO.getEmpHeadImg());
        eDo.setEmpMobile(empVO.getEmpMobile());
        eDo.setEmpPhone(empVO.getEmpPhone());
        eDo.setEmpName(empVO.getEmpName());
        eDo.setEmpQq(empVO.getEmpQq());
        eDo.setEmpRealName(empVO.getEmpRealName());
        eDo.setEmpSex(empVO.getEmpSex());
        eDo.setEmpBirthday(new DateTime(empVO.getEmpBirthday()).toDate());

        return eDo;
    }
}
