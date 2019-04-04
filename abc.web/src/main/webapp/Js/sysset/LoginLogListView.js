$(function () {
    MyGrid.Resize();
    MyAction.Init();

    $("#Del").click(MyAction.Del);
    $("#Dels").click(MyAction.DelAll);
    $("#SearchHide").click(MyAction.Search);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});
var MyAction = {
    Init: function () {
        $("#Grid").datagrid({
            url: "/sysset/json/LoginLogListView.json",
            height: $(window).height() - 20,
            sortOrder: 'desc',
            sortName: 'FB_SetName',
            fitColumns: true,
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: 'id',
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            singleSelect: false,
            columns: [
                [
                    {
                        field: "id",
                        checkbox: true,
                        formatter: function (value) {
                            return value;
                        }
                    },
                    { field: "employeeName", title: "登录名", width: 150, align: "center" } ,
                    { field: "loginTime", title: "登录时间", width: 150, align: "center" },
                    { field: "ip", title: "登录ip", width: 150, align: "center" },
                    { field: "loginState", title: "登录状态", width: 150, align: "center" }
                ]
            ],
            pagination: true
        })
    },

    Del: function () {
        var rows = $('#Grid').datagrid('getSelections');
        if (rows.length == 0) {
            Colyn.log("请选择一条数据进行操作");
            return;
        }
        var ids = [];
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
        }
        ids = ids.join();
        $.messager.confirm("提示", "确认要删除选中的登录日志吗？", function (r) {
            if (r) {
                $.AjaxColynText("/sysset/json/DeleteLoginLog.json?", { id: ids }, function (data) {
                    var data = JSON.parse(data);
                    if (data.success) {
                        Colyn.log("删除成功");
                        MyAction.Init();
                        $("#Grid").datagrid('clearSelections');
                    }
                    else {
                        Colyn.log("删除失败");
                    }
                });
            }
        });
    },
//    Dels: function () {
//        var rows = $("#Grid").datagrid("getSelections");
//        if (rows == null) {
//            Colyn.log("请选择一条数据进行操作");
//            return;
//        }
//        else {
//            $.messager.confirm("提示", "确认删除所有用户登录日志信息", function (r) {
//                if (r) {
//                    var ids = [];
//                    for (var i = 0; i < rows.length; i++) {
//                        ids.push(rows[i].id);
//                    }
//                    ids = ids.join();
//                    $.AjaxColynText("/sysset/json/DeleteLoginLog.json", { id: ids }, function (data) {
//                        var data = JSON.parse(data);
//                        if (data.success) {
//                            Colyn.log("删除成功");
//                            MyAction.Init();
//                            $("#Grid").datagrid('clearSelections');
//                        }
//                        else {
//                            Colyn.log("删除失败");
//                        }
//                    });
//                }
//            });
//
//        }
//    },
    DelAll: function () {
        $.messager.confirm("提示", "确认删除所有用户登录日志信息", function (r) {
            if (r) {
                $.AjaxColynText("/sysset/json/DeleteLoginLog.json?", { id: "all" }, function (data) {
                    var data = JSON.parse(data);
                    if (data.success) {
                        Colyn.log("删除成功");
                        MyAction.Init();
                        $("#Grid").datagrid('clearSelections');
                    }
                    else {
                        Colyn.log("删除失败");
                    }
                });
            }
        });

    },
    Search: function () {
//        var param = createParam3("SearchForm");
        var o = {employeeName: $('#employeeName').val(), loginState: $('#llo_Status').val()};
        $.post("/sysset/json/LoginLogListView.json", getParam(o), function (data) {
            $("#Grid").datagrid("loadData", data);
        }, "json");
    }
}

