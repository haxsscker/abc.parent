package com.autoserve.abc.dao.dataobject;

/**
 * 机构text字段信息
 * @author RJQ 2014/11/13 18:11.
 */
public class GovernmentWithBLOBsDO extends GovernmentDO {
    /**
     * 公司概况
     * abc_goverment.gov_profile
     */
    private String govProfile;

    /**
     * 团队管理
     * abc_goverment.gov_team_management
     */
    private String govTeamManagement;

    /**
     * 发展历史
     * abc_goverment.gov_development_history
     */
    private String govDevelopmentHistory;

    /**
     * 融资性担保牌照
     * abc_goverment.gov_guar_card
     */
    private String govGuarCard;

    /**
     * 合作机构
     * abc_goverment.gov_partner
     */
    private String govPartner;

    /**
     * 合作协议
     * abc_goverment.gov_cooperate_agreement
     */
    private String govCooperateAgreement;

    @Override
	public String getGovProfile() {
        return govProfile;
    }

    @Override
	public void setGovProfile(String govProfile) {
        this.govProfile = govProfile;
    }

    @Override
	public String getGovTeamManagement() {
        return govTeamManagement;
    }

    @Override
	public void setGovTeamManagement(String govTeamManagement) {
        this.govTeamManagement = govTeamManagement;
    }

    @Override
	public String getGovDevelopmentHistory() {
        return govDevelopmentHistory;
    }

    @Override
	public void setGovDevelopmentHistory(String govDevelopmentHistory) {
        this.govDevelopmentHistory = govDevelopmentHistory;
    }

    @Override
	public String getGovGuarCard() {
        return govGuarCard;
    }

    @Override
	public void setGovGuarCard(String govGuarCard) {
        this.govGuarCard = govGuarCard;
    }

    @Override
	public String getGovPartner() {
        return govPartner;
    }

    @Override
	public void setGovPartner(String govPartner) {
        this.govPartner = govPartner;
    }

    @Override
	public String getGovCooperateAgreement() {
        return govCooperateAgreement;
    }

    @Override
	public void setGovCooperateAgreement(String govCooperateAgreement) {
        this.govCooperateAgreement = govCooperateAgreement;
    }
}
