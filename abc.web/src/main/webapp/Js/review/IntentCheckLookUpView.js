var MyAction = {
    Init: function () {
        $("#FinancingAuditLookUpGrid").datagrid({
            method: "GET",
            url: "/review/json/IntentCheckLookUpView.json",
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "check_step", title: "审核环节", width: 150, align: "center" },
            { field: "check_emp", title: "审核人", width: 150, align: "center" },
            { field: "check_date", title: "审核日期", width: 150, align: "center" },
            { field: "check_state", title: "审核状态", width: 150, align: "center" },
            { field: "check_idear", title: "审核意见", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#FinancingAuditLookUpGrid').datagrid('getPager');
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
};

MyAction.Init();


