
var MyAction = {
    Init: function () {
        $("#HistoryGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/SuppleHistoryListView.json",
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "update_word", title: "修改字段", width: 150, align: "center" },
            { field: "old_value", title: "原字段值", width: 150, align: "center" },
            { field: "new_value", title: "新字段值", width: 150, align: "center" },
            { field: "update_date", title: "修改日期", width: 150, align: "center" },
            { field: "update_emp", title: "修改人", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#HistoryGrid').datagrid('getPager');
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


