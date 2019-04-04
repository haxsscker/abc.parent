package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import org.joda.time.DateTime;

import com.autoserve.abc.service.biz.entity.CompanyCustomer;
import com.autoserve.abc.service.biz.enums.CcCompanyScale;
import com.autoserve.abc.service.biz.enums.CcCompanyType;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.company.CompanyCustomerVO;

public class CompanyCustomerVOConverter {
    public static CompanyCustomerVO convertToCompanyCustomerVO(CompanyCustomer companyCustomer) {
        CompanyCustomerVO companyCustomerVO = new CompanyCustomerVO();
        BeanCopier beanCopier = BeanCopier.create(CompanyCustomer.class, CompanyCustomerVO.class, false);
        beanCopier.copy(companyCustomer, companyCustomerVO, null);
        companyCustomerVO.setCcRegisterDate(new DateTime(companyCustomer.getCcRegisterDate())
                .toString(DateUtil.DATE_FORMAT));
        if(companyCustomer!=null && companyCustomer.getCcCompanyScale()!=null){
        	companyCustomerVO.setCcCompanyScale(companyCustomer.getCcCompanyScale().state);
        }
        if(companyCustomer!=null && companyCustomer.getCcCompanyType()!=null){
        	 companyCustomerVO.setCcCompanyType(companyCustomer.getCcCompanyType().state);
        }       
        return companyCustomerVO;
    }
    
    public static CompanyCustomer toCompanyCustomer(CompanyCustomerVO companyCustomerVO) {
        CompanyCustomer companyCustomer = new CompanyCustomer();
        BeanCopier beanCopier = BeanCopier.create(CompanyCustomerVO.class, CompanyCustomer.class, false);
        beanCopier.copy(companyCustomerVO, companyCustomer, null);
        companyCustomer.setCcCompanyScale(CcCompanyScale.valueOf(companyCustomerVO.getCcCompanyScale()));
        companyCustomer.setCcCompanyType(CcCompanyType.valueOf(companyCustomerVO.getCcCompanyType()));
        String registerDateStr = companyCustomerVO.getCcRegisterDate();
        companyCustomer.setCcRegisterDate(DateUtil.parseDate(registerDateStr, DateUtil.DATE_FORMAT));
        return companyCustomer;
    }

    public static List<CompanyCustomerVO> convertToList(List<CompanyCustomer> companyCustomers) {
        if (companyCustomers == null || companyCustomers.isEmpty())
            throw new BusinessException("传入的list为空");
        List<CompanyCustomerVO> result = new ArrayList<CompanyCustomerVO>();
        for (CompanyCustomer cc : companyCustomers) {
            result.add(convertToCompanyCustomerVO(cc));
        }
        return result;
    }
}