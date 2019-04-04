package com.autoserve.abc.dao.dataobject;

/**
 * 公司客户附件信息
 * @author RJQ 2014/11/13 18:01.
 */
public class CompanyCustomerFileDO {
    /**
     *
     * abc_company_customer_file.ccf_id
     */
    private Integer ccfId;

    /**
     * 客户ID
     * abc_company_customer_file.ccf_company_customer_id
     */
    private Integer ccfCompanyCustomerId;

    /**
     * 附件名称
     * abc_company_customer_file.ccf_file_name
     */
    private String ccfFileName;

    /**
     * 附近路径
     * abc_company_customer_file.ccf_file_url
     */
    private String ccfFileUrl;

    public Integer getCcfId() {
        return ccfId;
    }

    public void setCcfId(Integer ccfId) {
        this.ccfId = ccfId;
    }

    public Integer getCcfCompanyCustomerId() {
        return ccfCompanyCustomerId;
    }

    public void setCcfCompanyCustomerId(Integer ccfCompanyCustomerId) {
        this.ccfCompanyCustomerId = ccfCompanyCustomerId;
    }

    public String getCcfFileName() {
        return ccfFileName;
    }

    public void setCcfFileName(String ccfFileName) {
        this.ccfFileName = ccfFileName;
    }

    public String getCcfFileUrl() {
        return ccfFileUrl;
    }

    public void setCcfFileUrl(String ccfFileUrl) {
        this.ccfFileUrl = ccfFileUrl;
    }
}
