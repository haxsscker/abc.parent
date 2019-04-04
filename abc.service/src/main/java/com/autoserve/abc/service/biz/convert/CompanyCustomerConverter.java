package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;


import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.service.biz.entity.CompanyCustomer;
import com.autoserve.abc.service.biz.enums.CcCompanyScale;
import com.autoserve.abc.service.biz.enums.CcCompanyType;
import com.autoserve.abc.service.exception.BusinessException;
/**
 * 类CompanyCustomerConverter.java的实现描述：TODO 类实现描述
 * 
 * @author fangrui 2014年12月19日 下午2:34:18
 */
public class CompanyCustomerConverter {
	public static CompanyCustomer toCompanyCustomer(
			CompanyCustomerDO companyCustomerDO) {
		CompanyCustomer companyCustomer = new CompanyCustomer();
		BeanCopier beanCopier = BeanCopier.create(CompanyCustomerDO.class,
				CompanyCustomer.class, false);
		beanCopier.copy(companyCustomerDO, companyCustomer, null);
		companyCustomer.setCcCompanyScale(CcCompanyScale
				.valueOf(companyCustomerDO.getCcCompanyScale()));
		companyCustomer.setCcCompanyType(CcCompanyType
				.valueOf(companyCustomerDO.getCcCompanyType()));

		return companyCustomer;
	}

	public static CompanyCustomerDO convertToCompanyCustomerDO(
			CompanyCustomer companyCustomer) {
		CompanyCustomerDO companyCustomerDO = new CompanyCustomerDO();
		BeanCopier beanCopier = BeanCopier.create(CompanyCustomer.class,
				CompanyCustomerDO.class, false);
		beanCopier.copy(companyCustomer, companyCustomerDO, null);
		if(companyCustomer.getCcCompanyScale()!=null){
			companyCustomerDO
			.setCcCompanyScale(companyCustomer.getCcCompanyScale().state);
		}	
		if(companyCustomer.getCcCompanyType()!=null){
			companyCustomerDO
			.setCcCompanyType(companyCustomer.getCcCompanyType().state);
		}		
//		companyCustomerDO.setCcRegisterDate(new DateTime(companyCustomer
//				.getCcRegisterDate()).toString(DateUtil.DATE_FORMAT));
		return companyCustomerDO;
	}

	public static List<CompanyCustomer> convertList(List<CompanyCustomerDO> list) {
		if (list == null || list.isEmpty())
			throw new BusinessException("传入的list为空");
		List<CompanyCustomer> result = new ArrayList<CompanyCustomer>();
		for (CompanyCustomerDO cdo : list) {
			result.add(toCompanyCustomer(cdo));
		}
		return result;
	}
}
