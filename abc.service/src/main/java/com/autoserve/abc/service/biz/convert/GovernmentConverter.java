package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.service.biz.entity.Government;
import net.sf.cglib.beans.BeanCopier;

/**
 * @author RJQ 2014/12/8 16:40.
 */
public class GovernmentConverter {

    public static GovPlainJDO convertToGovPlainJDO(GovernmentDO governmentDO) {
        GovPlainJDO govPlainJDO = new GovPlainJDO();
        govPlainJDO.setGovId(governmentDO.getGovId());
        govPlainJDO.setGovName(governmentDO.getGovName());
        govPlainJDO.setGovEmail(governmentDO.getGovEmail());
        govPlainJDO.setGovNo(governmentDO.getGovNo());
        govPlainJDO.setGovBusinessLicense(governmentDO.getGovBusinessLicense());
        govPlainJDO.setGovScale(governmentDO.getGovScale());
        govPlainJDO.setGovRegisterCapital(governmentDO.getGovRegisterCapital());
        govPlainJDO.setGovTotalCapital(governmentDO.getGovTotalCapital());
        govPlainJDO.setGovMaxLoanAmount(governmentDO.getGovMaxLoanAmount());
        govPlainJDO.setGovRegisterDivision(governmentDO.getGovRegisterDivision());
        govPlainJDO.setGovRegisterAddress(governmentDO.getGovRegisterAddress());
        govPlainJDO.setGovRegisterDate(governmentDO.getGovRegisterDate());
        govPlainJDO.setGovContact(governmentDO.getGovContact());
        govPlainJDO.setGovContactPhone(governmentDO.getGovContactPhone());
        govPlainJDO.setGovCustomerManager(governmentDO.getGovCustomerManager());
        govPlainJDO.setGovCorporate(governmentDO.getGovCorporate());
        govPlainJDO.setGovDocType(governmentDO.getGovDocType());
        govPlainJDO.setGovDocNo(governmentDO.getGovDocNo());
        govPlainJDO.setGovCreditLevel(governmentDO.getGovCreditLevel());
        govPlainJDO.setGovArea(governmentDO.getGovArea());
        govPlainJDO.setGovAddressDetail(governmentDO.getGovAddressDetail());
        govPlainJDO.setGovIsOfferGuar(governmentDO.getGovIsOfferGuar());
        govPlainJDO.setGovMaxGuarAmount(governmentDO.getGovMaxGuarAmount());
        govPlainJDO.setGovSettGuarAmount(governmentDO.getGovSettGuarAmount());
        govPlainJDO.setGovLogo(governmentDO.getGovLogo());
        govPlainJDO.setGovIsEnable(governmentDO.getGovIsEnable());
        govPlainJDO.setGovState(governmentDO.getGovState());
        govPlainJDO.setGovReviewer(governmentDO.getGovReviewer());
        govPlainJDO.setGovReviewDate(governmentDO.getGovReviewDate());
        govPlainJDO.setGovAddEmp(governmentDO.getGovAddEmp());
        govPlainJDO.setGovAddDate(governmentDO.getGovAddDate());
        govPlainJDO.setGovProfile(governmentDO.getGovProfile());
        govPlainJDO.setGovTeamManagement(governmentDO.getGovTeamManagement());
        govPlainJDO.setGovDevelopmentHistory(governmentDO.getGovDevelopmentHistory());
        govPlainJDO.setGovGuarCard(governmentDO.getGovGuarCard());
        govPlainJDO.setGovPartner(governmentDO.getGovPartner());
        govPlainJDO.setGovCooperateAgreement(governmentDO.getGovCooperateAgreement());
        govPlainJDO.setGovOutSeqNo(governmentDO.getGovOutSeqNo());
        return govPlainJDO;
    }

    public static GovernmentDO convertToGovernmentDO(GovPlainJDO govPlainJDO) {
        GovernmentDO governmentDO = new GovernmentDO();
        BeanCopier beanCopier = BeanCopier.create(GovPlainJDO.class, GovernmentDO.class, false);
        beanCopier.copy(govPlainJDO, governmentDO, null);

        return governmentDO;
    }

    public static Government convertToGovernment(GovPlainJDO govPlainJDO){
        Government government = new Government();
        BeanCopier beanCopier = BeanCopier.create(GovPlainJDO.class, Government.class, false);
        beanCopier.copy(govPlainJDO, government, null);

        return government;
    }

    public static GovPlainJDO convertToGovPlainJDO(Government government){
        GovPlainJDO  govPlainJDO = new GovPlainJDO();
        BeanCopier beanCopier = BeanCopier.create(Government.class, GovPlainJDO.class, false);
        beanCopier.copy(government, govPlainJDO, null);

        return govPlainJDO;
    }

}
