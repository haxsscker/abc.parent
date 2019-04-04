/*------------------------------------------------
 * Author:李冬冬  Date：2014-9-1 
-------------------------------------------------*/


var MyAction = {
    Init: function () {
        $("#LoanHistoryGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PLoanHistoryListView.json",
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "loan_date", title: "贷款日期", width: 150, align: "center" },
            { field: "loan_money", title: "贷款金额", width: 150, align: "right" },
            { field: "check_money", title: "审批金额", width: 150, align: "right" },
            { field: "loan_period", title: "贷款期限", width: 150, align: "center" },
            { field: "is_flow", title: "是否流标", width: 150, align: "center" },
            { field: "is_over", title: "是否逾期", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#LoanHistoryGrid').datagrid('getPager');
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


