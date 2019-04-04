package com.autoserve.abc.web.vo.user;

/**
 * 类UserContactVO.java
 * 
 * @author fangrui 2014年12月18日 上午10:21:11
 */
public class UserContactVO {

    /**
     * 主键 abc_user_contact.uc_id
     */
    private Integer ucId;

    /**
     * 用户ID abc_user_contact.uc_userid
     */
    private Integer ucUserid;

    /**
     * 手机号码 abc_user_contact.uc_phone
     */
    private String  ucPhone;

    /**
     * 居住地电话 abc_user_contact.uc_home_phone
     */
    private String  ucHomePhone;

    /**
     * 居住所在省市 abc_user_contact.uc_city
     */
    private String  ucCity;

    /**
     * 居住地邮编 abc_user_contact.uc_post
     */
    private String  ucPost;

    /**
     * 现居住地址 abc_user_contact.uc_address
     */
    private String  ucAddress;

    /**
     * 第二联系人姓名 abc_user_contact.uc_second_contact_name
     */
    private String  ucSecondContactName;

    /**
     * 第二联系人关系 abc_user_contact.uc_second_contact_relationship
     */
    private String  ucSecondContactRelationship;

    /**
     * 第二联系人电话 abc_user_contact.uc_second_contact_phone
     */
    private String  ucSecondContactPhone;

    /**
     * 第三联系人姓名 abc_user_contact.uc_third_contact_name
     */
    private String  ucThirdContactName;

    /**
     * 第三联系人关系 abc_user_contact.uc_third_contact_relationship
     */
    private String  ucThirdContactRelationship;

    /**
     * 第三联系人电话 abc_user_contact.uc_third_contact_phone
     */
    private String  ucThirdContactPhone;

    /**
     * 第四联系人姓名 abc_user_contact.uc_fourth_contact_name
     */
    private String  ucFourthContactName;

    /**
     * 第四联系人关系 abc_user_contact.uc_fourth_contact_relationship
     */
    private String  ucFourthContactRelationship;

    /**
     * 第四联系人电话 abc_user_contact.uc_fourth_contact_phone
     */
    private String  ucFourthContactPhone;

    /**
     * MSN abc_user_contact.uc_msn
     */
    private String  ucMsn;

    /**
     * QQ abc_user_contact.uc_qq
     */
    private String  ucQq;

    /**
     * @return abc_user_contact.uc_id
     */
    public Integer getUcId() {
        return ucId;
    }

    /**
     * @param Integer ucId (abc_user_contact.uc_id )
     */
    public void setUcId(Integer ucId) {
        this.ucId = ucId;
    }

    /**
     * @return abc_user_contact.uc_userid
     */
    public Integer getUcUserid() {
        return ucUserid;
    }

    /**
     * @param Integer ucUserid (abc_user_contact.uc_userid )
     */
    public void setUcUserid(Integer ucUserid) {
        this.ucUserid = ucUserid;
    }

    /**
     * @return abc_user_contact.uc_phone
     */
    public String getUcPhone() {
        return ucPhone;
    }

    /**
     * @param String ucPhone (abc_user_contact.uc_phone )
     */
    public void setUcPhone(String ucPhone) {
        this.ucPhone = ucPhone == null ? "" : ucPhone.trim();
    }

    /**
     * @return abc_user_contact.uc_home_phone
     */
    public String getUcHomePhone() {
        return ucHomePhone;
    }

    /**
     * @param String ucHomePhone (abc_user_contact.uc_home_phone )
     */
    public void setUcHomePhone(String ucHomePhone) {
        this.ucHomePhone = ucHomePhone == null ? "" : ucHomePhone.trim();
    }

    /**
     * @return abc_user_contact.uc_city
     */
    public String getUcCity() {
        return ucCity;
    }

    /**
     * @param String ucCity (abc_user_contact.uc_city )
     */
    public void setUcCity(String ucCity) {
        this.ucCity = ucCity == null ? "" : ucCity.trim();
    }

    /**
     * @return abc_user_contact.uc_post
     */
    public String getUcPost() {
        return ucPost;
    }

    /**
     * @param String ucPost (abc_user_contact.uc_post )
     */
    public void setUcPost(String ucPost) {
        this.ucPost = ucPost == null ? "" : ucPost.trim();
    }

    /**
     * @return abc_user_contact.uc_address
     */
    public String getUcAddress() {
        return ucAddress;
    }

    /**
     * @param String ucAddress (abc_user_contact.uc_address )
     */
    public void setUcAddress(String ucAddress) {
        this.ucAddress = ucAddress == null ? "" : ucAddress.trim();
    }

    /**
     * @return abc_user_contact.uc_second_contact_name
     */
    public String getUcSecondContactName() {
        return ucSecondContactName;
    }

    /**
     * @param String ucSecondContactName
     *            (abc_user_contact.uc_second_contact_name )
     */
    public void setUcSecondContactName(String ucSecondContactName) {
        this.ucSecondContactName = ucSecondContactName == null ? "" : ucSecondContactName.trim();
    }

    /**
     * @return abc_user_contact.uc_second_contact_relationship
     */
    public String getUcSecondContactRelationship() {
        return ucSecondContactRelationship;
    }

    /**
     * @param String ucSecondContactRelationship
     *            (abc_user_contact.uc_second_contact_relationship )
     */
    public void setUcSecondContactRelationship(String ucSecondContactRelationship) {
        this.ucSecondContactRelationship = ucSecondContactRelationship == null ? "" : ucSecondContactRelationship
                .trim();
    }

    /**
     * @return abc_user_contact.uc_second_contact_phone
     */
    public String getUcSecondContactPhone() {
        return ucSecondContactPhone;
    }

    /**
     * @param String ucSecondContactPhone
     *            (abc_user_contact.uc_second_contact_phone )
     */
    public void setUcSecondContactPhone(String ucSecondContactPhone) {
        this.ucSecondContactPhone = ucSecondContactPhone == null ? "" : ucSecondContactPhone.trim();
    }

    /**
     * @return abc_user_contact.uc_third_contact_name
     */
    public String getUcThirdContactName() {
        return ucThirdContactName;
    }

    /**
     * @param String ucThirdContactName (abc_user_contact.uc_third_contact_name
     *            )
     */
    public void setUcThirdContactName(String ucThirdContactName) {
        this.ucThirdContactName = ucThirdContactName == null ? "" : ucThirdContactName.trim();
    }

    /**
     * @return abc_user_contact.uc_third_contact_relationship
     */
    public String getUcThirdContactRelationship() {
        return ucThirdContactRelationship;
    }

    /**
     * @param String ucThirdContactRelationship
     *            (abc_user_contact.uc_third_contact_relationship )
     */
    public void setUcThirdContactRelationship(String ucThirdContactRelationship) {
        this.ucThirdContactRelationship = ucThirdContactRelationship == null ? "" : ucThirdContactRelationship.trim();
    }

    /**
     * @return abc_user_contact.uc_third_contact_phone
     */
    public String getUcThirdContactPhone() {
        return ucThirdContactPhone;
    }

    /**
     * @param String ucThirdContactPhone
     *            (abc_user_contact.uc_third_contact_phone )
     */
    public void setUcThirdContactPhone(String ucThirdContactPhone) {
        this.ucThirdContactPhone = ucThirdContactPhone == null ? "" : ucThirdContactPhone.trim();
    }

    /**
     * @return abc_user_contact.uc_fourth_contact_name
     */
    public String getUcFourthContactName() {
        return ucFourthContactName;
    }

    /**
     * @param String ucFourthContactName
     *            (abc_user_contact.uc_fourth_contact_name )
     */
    public void setUcFourthContactName(String ucFourthContactName) {
        this.ucFourthContactName = ucFourthContactName == null ? "" : ucFourthContactName.trim();
    }

    /**
     * @return abc_user_contact.uc_fourth_contact_relationship
     */
    public String getUcFourthContactRelationship() {
        return ucFourthContactRelationship;
    }

    /**
     * @param String ucFourthContactRelationship
     *            (abc_user_contact.uc_fourth_contact_relationship )
     */
    public void setUcFourthContactRelationship(String ucFourthContactRelationship) {
        this.ucFourthContactRelationship = ucFourthContactRelationship == null ? "" : ucFourthContactRelationship
                .trim();
    }

    /**
     * @return abc_user_contact.uc_fourth_contact_phone
     */
    public String getUcFourthContactPhone() {
        return ucFourthContactPhone;
    }

    /**
     * @param String ucFourthContactPhone
     *            (abc_user_contact.uc_fourth_contact_phone )
     */
    public void setUcFourthContactPhone(String ucFourthContactPhone) {
        this.ucFourthContactPhone = ucFourthContactPhone == null ? "" : ucFourthContactPhone.trim();
    }

    /**
     * @return abc_user_contact.uc_msn
     */
    public String getUcMsn() {
        return ucMsn;
    }

    /**
     * @param String ucMsn (abc_user_contact.uc_msn )
     */
    public void setUcMsn(String ucMsn) {
        this.ucMsn = ucMsn == null ? "" : ucMsn.trim();
    }

    /**
     * @return abc_user_contact.uc_qq
     */
    public String getUcQq() {
        return ucQq;
    }

    /**
     * @param String ucQq (abc_user_contact.uc_qq )
     */
    public void setUcQq(String ucQq) {
        this.ucQq = ucQq == null ? "" : ucQq.trim();
    }

}
