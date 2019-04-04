package com.autoserve.abc.service.biz.impl.cash;

import com.autoserve.abc.service.util.SystemGetPropeties;

public class LoanJsonList implements java.io.Serializable {

    private static final long serialVersionUID     = 5344272972009037999L;

    private String            LoanOutMoneymoremore = SystemGetPropeties.getStrString("LoanOutMoneymoremoreId");
    private String            LoanInMoneymoremore  = SystemGetPropeties.getStrString("LoanOutMoneymoremoreId");
    private String            OrderNo;
    private String            BatchNo;
    private String            LoanNo;
    private String            Amount;
    private Double            FullAmount;
    private String            TransferName;

    private String            ExchangeBatchNo;
    private String            AdvanceBatchNo;
    private String            Remark;
    private String            SecondaryJsonList;

    public LoanJsonList(String loanOutMoneymoremore, String loanInMoneymoremore, String orderNo, String LoanNo,
                        String batchNo, String amount, Double fullAmount, String transferName, String ExchangeBatchNo,
                        String AdvanceBatchNo, String Remark, String SecondaryJsonList) {

        super();
        LoanOutMoneymoremore = loanOutMoneymoremore;
        LoanInMoneymoremore = loanInMoneymoremore;
        OrderNo = orderNo;
        BatchNo = batchNo;
        this.LoanNo = LoanNo;
        Amount = amount;
        FullAmount = fullAmount;
        TransferName = transferName;
//        ExchangeBatchNo = ExchangeBatchNo;
//        AdvanceBatchNo = AdvanceBatchNo;
//        Remark = Remark;
        this.SecondaryJsonList = SecondaryJsonList;
    }

    public String getExchangeBatchNo() {
        return ExchangeBatchNo;
    }

    public void setExchangeBatchNo(String exchangeBatchNo) {
        ExchangeBatchNo = exchangeBatchNo;
    }

    public String getAdvanceBatchNo() {
        return AdvanceBatchNo;
    }

    public void setAdvanceBatchNo(String advanceBatchNo) {
        AdvanceBatchNo = advanceBatchNo;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public LoanJsonList() {
        // TODO Auto-generated constructor stub
    }

    public String getLoanOutMoneymoremore() {
        return LoanOutMoneymoremore;
    }

    public void setLoanOutMoneymoremore(String loanOutMoneymoremore) {
        LoanOutMoneymoremore = loanOutMoneymoremore;
    }

    public String getLoanInMoneymoremore() {
        return LoanInMoneymoremore;
    }

    public void setLoanInMoneymoremore(String loanInMoneymoremore) {
        LoanInMoneymoremore = loanInMoneymoremore;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public Double getFullAmount() {
        return FullAmount;
    }

    public void setFullAmount(Double fullAmount) {
        FullAmount = fullAmount;
    }

    public String getTransferName() {
        return TransferName;
    }

    public void setTransferName(String transferName) {
        TransferName = transferName;
    }

    public String getSecondaryJsonList() {
        return SecondaryJsonList;
    }

    public void setSecondaryJsonList(String secondaryJsonList) {
        SecondaryJsonList = secondaryJsonList;
    }

    public String getLoanNo() {
        return LoanNo;
    }

    public void setLoanNo(String loanNo) {
        LoanNo = loanNo;
    }

}
