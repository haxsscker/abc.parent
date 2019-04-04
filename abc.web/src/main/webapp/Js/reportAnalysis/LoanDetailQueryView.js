/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-22
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#LoanDetailQueryGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PLoanDetailQueryList.json",
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
            { field: "project_num", title: "项目名称", width: 150, align: "center" },
            { field: "project_type", title: "项目类型", width: 150, align: "center" },
            { field: "integral_man", title: "借款人", width: 150, align: "center" },
            { field: "repayment_type", title: "还款方式", width: 150, align: "center" },
            { field: "integral_money", title: "借款金额", width: 150, align: "right" },
            { field: "annualized_return", title: "年化收益率", width: 150, align: "right" },
            {
                field: "shouldrepayment_date", title: "应还日期", width: 150, align: "center", formatter: function (v, r, i) {
                    return convertToDate(v, "yyyy-MM-dd");
                }
            },
            { field: "integral_limit", title: "借款期限", width: 150, align: "center" },
            { field: "periods", title: "期数", width: 150, align: "center" },
            { field: "shouldrepay_money", title: "本期应还本金", width: 150, align: "right" },
            { field: "shouldrepay_interest", title: "本期应还利息", width: 150, align: "right" },
            { field: "shouldrepay_fine", title: "本期应还罚款", width: 150, align: "right" },
            { field: "already_money", title: "实还总额", width: 150, align: "right" },
            { field: "state", title: "状态", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#LoanDetailQueryGrid').datagrid('getPager');
        $(p).pagination({
            pageSize: 10,
            pageList: [5, 10, 15, 20, 30, 50, 100],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            onBeforeRefresh: function () {
                $(this).pagination('loading');
                $(this).pagination('loaded');
            }
        });
    },
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        //var o = {
        //    gscId: row.gsc_Id,
        //    gteId: row.gte_Id
        //};
        var Dialog = $.hDialog({
            href: "/P2PStatisticsAnalysis/RepaymentDetailFormView",
            iconCls: 'icon-add',
            title: "还款详情",
            width: 750,
            height: $(window).height() - 50,
            //onLoad: function () {
            //    MyAction.GetTemplate(row.gsc_Id, row.gte_Id);
            //},
            buttons: [{
                text: '取消返回',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })
    },
    CustManagerDetail: function (CustId) {

        var url = "/DBCustManage/CustManagerView?CustId=" + CustId;
        var name = "客户经理信息";
        var para = 'height=800,width=1000,resizable=yes,scrollbars=yes'
        window.open(url, name, para);

    }

}