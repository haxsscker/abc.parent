
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
            url: "/System/LogUserLoginView",
            height: $(window).height() - 20,
            sortOrder: 'desc',
            sortName: 'FB_SetName',
            fitColumns: true,
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: 'llo_Id',
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            singleSelect:false,
            columns: [[
                { field: 'ck', checkbox: true },
         { field: "emp_Name", title: "登录名", width: 150, align: "center" },
         {
             field: "llo_LoginTime", title: "登录时间", width: 150, align: "center", formatter: function (value, row, index) {
                return convertToDate(value, "yyyy-MM-dd hh:mm:ss");
             }
         },
         { field: "llo_IP", title: "登录ip", width: 150, align: "center" },
         {
             field: "llo_Status", title: "登录状态", width: 150, align: "center", formatter: function (value, row, index) {
                 if (value == 0) {
                     return "登录失败"
                 } else {
                     return "登录成功";
                 }
             }
         }
            ]],
            pagination: true,      
        })
    },
   
    Del: function () {
        var rows = $('#Grid').datagrid('getSelections');
        if (rows.length == 0) { Colyn.log("请选择一条数据进行操作"); return; }
        var ids = [];
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].llo_Id);
        }
        ids = ids.join();
        $.messager.confirm("提示", "确认要删除选中的登录日志吗？", function (r) {
            if (r) {
                $.AjaxColynText("/System/DelLoginLog", { id: ids }, function (data) {
                    if (data == "ok") {
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
    Dels: function () {
        var rows = $("#Grid").datagrid("getSelections");
        if (rows == null) {
            Colyn.log("请选择一条数据进行操作");
            return;
        }       
        else {
            $.messager.confirm("提示", "确认删除所有用户登录日志信息", function (r) {
                if (r) {
                    var ids = [];
                    for (var i = 0; i < rows.length; i++) {
                        ids.push(rows[i].llo_Id);
                    }
                    ids = ids.join();
                     $.AjaxColynText("/System/DelLoginLog", { id:ids }, function (data) {
                        if (data == "ok") {
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

        }
    },
    DelAll: function () {
        $.messager.confirm("提示", "确认删除所有用户登录日志信息", function (r) {
            if (r) {
                $.AjaxColynText("/System/DelLoginLog", { id: "all" }, function (data) {
                    if (data == "ok") {
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
        var param = createParam3("SearchForm");
        $.post("/System/LogUserLoginView?", param, function (data) {
            $("#Grid").datagrid("loadData", data);
        }, "json");
    }
}

