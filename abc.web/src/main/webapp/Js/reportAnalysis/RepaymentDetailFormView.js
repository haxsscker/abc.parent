/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-26
-------------------------------------------------*/
var MyAction = {
    Init: function () {
        $("#RepaymentDetailFormGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PRepaymentDetailFormView.json",
            height: $(window).height() - 140,
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
                field: "repayment_date", title: "应还日期", width: 150, align: "center", formatter: function (v, r, i) {
                    return convertToDate(v, "yyyy-MM-dd");
                }
            },
            {
                field: "already_date", title: "实还日期", width: 150, align: "center", formatter: function (v, r, i) {
                    return convertToDate(v, "yyyy-MM-dd");
                }
            },
            { field: "shouldprincipal", title: "应还本金", width: 150, align: "right" },
            { field: "shouldinterest", title: "应还利息", width: 150, align: "right" },
            { field: "shouldmoney", title: "应还罚息", width: 150, align: "center" },
            { field: "total_money", title: "累计还款金额", width: 150, align: "center" },
            { field: "repayment_man", title: "还款人", width: 150, align: "center" },
            { field: "state", title: "状态", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#RepaymentDetailFormGrid').datagrid('getPager');
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
    CustManagerDetail: function (CustId) {

        var url = "/DBCustManage/CustManagerView?CustId=" + CustId;
        var name = "客户经理信息";
        var para = 'height=800,width=1000,resizable=yes,scrollbars=yes'
        window.open(url, name, para);

    }

}
MyAction.Init();