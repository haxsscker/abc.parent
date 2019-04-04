/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-19 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#StatisticsWithdrawalsGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PStatisticsWithdrawalsView.json",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
           {
               field: "loan_1", title: "一月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_1 + "</a>";
               }
           },
           {
               field: "loan_2", title: "二月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_2 + "</a>";
               }
           },
           {
               field: "loan_3", title: "三月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_3 + "</a>";
               }
           },
           {
               field: "loan_4", title: "四月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_4 + "</a>";
               }
           },
           {
               field: "loan_5", title: "五月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_5 + "</a>";
               }
           },
           {
               field: "loan_6", title: "六月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_6 + "</a>";
               }
           },
           {
               field: "loan_7", title: "七月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_7 + "</a>";
               }
           },
           {
               field: "loan_8", title: "八月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_8 + "</a>";
               }
           },
           {
               field: "loan_9", title: "九月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_9 + "</a>";
               }
           },
           {
               field: "loan_10", title: "十月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_10 + "</a>";
               }
           },
           {
               field: "loan_11", title: "十一月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_11 + "</a>";
               }
           },
           {
               field: "loan_12", title: "十二月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                   return "<a href='#' onclick =\"LookUpDetails()\">" + row.loan_12 + "</a>";
               }
           }
            ]],
            pagination: true,
            singleSelect: true
        });
    },
    CustManagerDetail: function (CustId) {

        var url = "/DBCustManage/CustManagerView?CustId=" + CustId;
        var name = "客户经理信息";
        var para = 'height=800,width=1000,resizable=yes,scrollbars=yes'
        window.open(url, name, para);

    }

}

//查看
function LookUpDetails() {
    var Dialog = $.hDialog({
        href: "/P2PStatisticsAnalysis/StatisticsWithdrawalsLookUpView",
        iconCls: 'icon-add',
        title: "提现详情查看",
        width: 820,
        height: 400,
        maximizable: false,
        buttons: [{
            text: '关闭',
            iconCls: 'icon-cancel',
            handler: function () {
                Dialog.dialog("close");
                $('#StatisticsRechargeGrid').datagrid('reload');

            }
        }]
    })
}