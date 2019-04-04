var MyAction = {
    Init: function () {
        $("#InvestGrid").datagrid({
            method: "GET",
            url: "/Demo/json/buyTransferList.json",
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "transfer_emp", title: "转让人", width: 150, align: "center" },
            { field: "transfer_money", title: "转让金额", width: 150, align: "right" },
            { field: "transfer_date", title: "转让日期", width: 150, align: "center" },
            { field: "transfer_rate", title: "转让收益", width: 150, align: "right" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#InvestGrid').datagrid('getPager');
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


