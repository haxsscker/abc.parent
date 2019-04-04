package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.web.vo.government.GovernmentVO;

/**
 * @author RJQ 2014/12/10 14:19.
 */
public class GovernmentVOConverter {
    public static GovPlainJDO convertToGovPlainDO(GovernmentVO vo) {
        GovPlainJDO govPlainJDO = new GovPlainJDO();
        govPlainJDO.setGovId(vo.getGovId());
        govPlainJDO.setGovUserName(vo.getGovUserName());
        govPlainJDO.setGovName(vo.getGovName());
        govPlainJDO.setGovEmail(vo.getGovEmail());
        govPlainJDO.setGovNo(vo.getGovNo());
        govPlainJDO.setGovBusinessLicense(vo.getGovBusinessLicense());
        govPlainJDO.setGovScale(vo.getGovScale());
        govPlainJDO.setGovRegisterCapital(vo.getGovRegisterCapital());
        govPlainJDO.setGovTotalCapital(vo.getGovTotalCapital());
        govPlainJDO.setGovMaxLoanAmount(vo.getGovMaxLoanAmount());
        govPlainJDO.setGovRegisterDivision(vo.getGovRegisterDivision());
        govPlainJDO.setGovRegisterAddress(vo.getGovRegisterAddress());
        govPlainJDO.setGovRegisterDate(vo.getGovRegisterDate());
        govPlainJDO.setGovContact(vo.getGovContact());
        govPlainJDO.setGovContactPhone(vo.getGovContactPhone());
        govPlainJDO.setGovCustomerManager(vo.getGovCustomerManager());
        govPlainJDO.setGovCorporate(vo.getGovCorporate());
        govPlainJDO.setGovDocType(vo.getGovDocType());
        govPlainJDO.setGovDocNo(vo.getGovDocNo());
        govPlainJDO.setGovCreditLevel(vo.getGovCreditLevel());
        govPlainJDO.setGovArea(vo.getGovArea());
        govPlainJDO.setGovAddressDetail(vo.getGovAddressDetail());
        govPlainJDO.setGovIsOfferGuar(vo.getGovIsOfferGuar());
        govPlainJDO.setGovMaxGuarAmount(vo.getGovMaxGuarAmount());
        govPlainJDO.setGovSettGuarAmount(vo.getGovSettGuarAmount());
        govPlainJDO.setGovLogo(vo.getGovLogo());
        govPlainJDO.setGovGuarName(vo.getGovGuarName());
        govPlainJDO.setGovGuarId(vo.getGovGuarId());
        govPlainJDO.setGovIsEnable(vo.getGovIsEnable());
        govPlainJDO.setGovState(vo.getGovState());
        govPlainJDO.setGovReviewer(vo.getGovReviewer());
        govPlainJDO.setGovReviewDate(vo.getGovReviewDate());
        govPlainJDO.setGovAddEmp(vo.getGovAddEmp());
        govPlainJDO.setGovAddDate(vo.getGovAddDate());
        govPlainJDO.setGovProfile(vo.getGovProfile());
        govPlainJDO.setGovTeamManagement(vo.getGovTeamManagement());
        govPlainJDO.setGovDevelopmentHistory(vo.getGovDevelopmentHistory());
        govPlainJDO.setGovGuarCard(vo.getGovGuarCard());
        govPlainJDO.setGovPartner(vo.getGovPartner());
        govPlainJDO.setGovCooperateAgreement(vo.getGovCooperateAgreement());

        return govPlainJDO;
    }
}
