package com.autoserve.abc.service.biz.enums;

/**
 * 类ButtonType.java的实现描述：TODO 类实现描述
 * 
 * @author pp 2014年11月20日 上午11:21:29
 */
public enum ButtonType {

    Browser("Browser", "icon-zoom", "浏览"),
    Add("Add", "icon-add", "添加"),
    Edit("Edit", "icon-pencil", "修改"),
    Del("Del", "icon-cross", "删除"),
    Export("Export", "icon-arrow_turn_right", "导出Excel"),
    Import("Import", "icon-arrow_turn_left", "导入"),
    Print("Print", "icon-printer", "打印"),
    Upload("Upload", "icon-folder_up", "上传"),
    DownLoad("DownLoad", "icon-download", "下载"),
    Audit("Audit", "icon-user_magnify", "审核"),
    Copy("Copy", "icon-page_copy", "复制"),
    Authorization("Authorization", "icon-lock", "授权"),
    SetRole("SetRole", "icon-calculator_link", "分配角色"),
    SetBtn("SetBtn", "icon-plugin_add", "分配按钮"),
    EditPwd("EditPwd", "icon-key", "修改密码"),
    Initialize("Initialize", "icon-cross", "初始化"),
    Restore("Restore", "icon-coins", "还原"),
    Send("Send", "icon-arrow_turn_right", "发送"),
    GoBack("GoBack", "icon-arrow_rotate_clockwise", "返回"),
    ViewFlow("ViewFlow", "icon-zoom", "查看流程图"),
    Recall("Recall", "icon-arrow_redo", "撤回"),
    Return("Return", "icon-arrow_undo", "退回"),
    Maintain("Maintain", "icon-user_red", "维护"),
    Approval("Approval", "icon-cog_edit", "审批配置"),
    LookUp("LookUp", "icon-zoom", "查看"),
    Search("Search", "icon-search", "搜索"),
    Vote("Vote", "icon-user", "表决"),
    FeeMaintain("FeeMaintain", "icon-group_edit", "费用维护"),
    ConfirmLoan("ConfirmLoan", "icon-tux", "确认放款"),
    PrintDocument("PrintDocument", "icon-w_fBtnSelectDate", "打印呈批件"),
    ReviewComments("ReviewComments", "icon-group_gear", "查看评审意见"),
    ProcTrack("ProcTrack", "icon-book_red", "跟踪流程"),
    Calculate("Calculate", "icon-calculator", "计算"),
    Signing("Signing", "icon-wand", "签批"),
    CheckIdear("CheckIdear", "icon-report_user", "审核意见"),
    DataAuth("DataAuth", "icon-folder_key", "数据授权"),
    ReplacePay("ReplacePay", "icon-coins", "代还"),
    Enable("Enable", "icon-user_edit", "启用"),
    InitPassword("InitPassword", "icon-key_start", "初始化密码"),
    Release("Release", "icon-report", "发布"),
    MatchingFunds("MatchingFunds", "icon-coins", "资金匹配"),
    MatchingOrg("MatchingOrg", "icon-group_link", "匹配机构"),
    TransferFunds("TransferFunds", "icon-shield_go", "资金划转"),
    PrintFundTransfer("PrintFundTransfer", "icon-printer", "打印资金划转单"),
    PrintBill("PrintBill", "icon-printer", "打印收费单"),
    PrintBid("PrintBid", "icon-printer", "打印投标清单"),
    Repayment("Repayment", "icon-coins_delete", "还款"),
    StartSta("StartSta", "icon-chart_bar", "开始统计"),
    ViewIdea("ViewIdea", "icon-report_user", "查看意见"),
    GuarMatch("GuarMatch", "icon-computer_link", "担保匹配"),
    AuditSche("AuditSche", "icon-application_form_magnify", "审核进度"),
    ComPay("ComPay", "icon-coins", "强制还款"),
    LockStop("LockStop", "icon-lock_stop", "停用"),
    CashTransfer("CashTransfer", "icon-shield_go", "兑现划转"),
    GuarCheck("GuarCheck", "icon-arrow_turn_right", "发送担保审核"),
    MatainAdd("MatainAdd", "icon-add", "资料补全"),
    SendToCheck("SendToCheck", "icon-arrow_turn_right", "发送平台审核"),
    ViewHistory("ViewHistory", "icon-bell", "查看历史"),
    CancelRelease("CancelRelease", "icon-arrow_redo", "取消发布"),
    PrintTransfer("PrintTransfer", "icon-printer", "打印转让清单"),
    SitePub("SitePub", "icon-ok", "生成页面");

    public String tag;
    public String icon;
    public String name;

    ButtonType(String t, String i, String n) {
        tag = t;
        icon = i;
        name = n;
    }

}
