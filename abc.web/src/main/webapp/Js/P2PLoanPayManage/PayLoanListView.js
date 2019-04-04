/*------------------------------------------------
 * Author:李冬冬  Date：2014-8-19 
-------------------------------------------------*/


var MyAction = {
    Init: function () {
        $("#PayLoanGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PPayLoanListView.json",
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "loan_no", title: "项目名称", width: 150, align: "center" },
            { field: "plan_number", title: "期数", width: 150, align: "center" },
            { field: "plan_payDate", title: "应还日期", width: 150, align: "center" },
            { field: "plan_payMoney", title: "应还本金", width: 150, align: "right" },
            { field: "plan_payRate", title: "应还利息", width: 150, align: "right" },
            { field: "plan_payFee", title: "应还罚息", width: 150, align: "right" },
            { field: "plan_serviceFee", title: "应还服务费", width: 150, align: "right" },
            { field: "pay_date", title: "实还日期", width: 150, align: "center" },
            { field: "collect_Money", title: "已还本金", width: 150, align: "right" },
            { field: "collect_Rate", title: "已还利息", width: 150, align: "right" },
            { field: "collect_Fee", title: "已还罚息", width: 150, align: "right" },
            { field: "collect_serviceFee", title: "已还服务费", width: 150, align: "right" },
            { field: "pay_state", title: "状态", width: 150, align: "center" },
            { field: "pay_mark", title: "备注", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#PayLoanGrid').datagrid('getPager');
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
    }
}

MyAction.Init();


