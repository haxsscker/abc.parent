package com.autoserve.abc.service.biz.impl.cash;

public class SecondaryJsonList implements java.io.Serializable {

    private static final long serialVersionUID = 6599513965529397315L;

    private String            LoanInMoneymoremore;
    private String            Amount;
    private String            TransferName;
    private String            Remark;

    public SecondaryJsonList() {

    }

    public SecondaryJsonList(String loanInMoneymoremore, String amount, String transferName, String remark) {
        super();
        LoanInMoneymoremore = loanInMoneymoremore;
        Amount = amount;
        TransferName = transferName;
        Remark = remark;
    }

    public String getLoanInMoneymoremore() {
        return LoanInMoneymoremore;
    }

    public void setLoanInMoneymoremore(String loanInMoneymoremore) {
        LoanInMoneymoremore = loanInMoneymoremore;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTransferName() {
        return TransferName;
    }

    public void setTransferName(String transferName) {
        TransferName = transferName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

}
