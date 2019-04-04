package com.autoserve.abc.service.biz.entity;

public class InvestPdf {

    private String tableOne;
    private String tableTow;
    private String tableThree;
    private String tableFour;

    public InvestPdf() {
        super();
        // TODO Auto-generated constructor stub
    }

    public InvestPdf(String tableOne, String tableTow, String tableThree, String tableFour) {
        super();
        this.tableOne = tableOne;
        this.tableTow = tableTow;
        this.tableThree = tableThree;
        this.tableFour = tableFour;
    }

    public String getTableOne() {
        return tableOne;
    }

    public void setTableOne(String tableOne) {
        this.tableOne = tableOne;
    }

    public String getTableTow() {
        return tableTow;
    }

    public void setTableTow(String tableTow) {
        this.tableTow = tableTow;
    }

    public String getTableThree() {
        return tableThree;
    }

    public void setTableThree(String tableThree) {
        this.tableThree = tableThree;
    }

    public String getTableFour() {
        return tableFour;
    }

    public void setTableFour(String tableFour) {
        this.tableFour = tableFour;
    }

}
