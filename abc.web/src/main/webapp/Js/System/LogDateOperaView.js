
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#Dels").click(MyAction.DelAll);
    $("#Del").click(MyAction.Del);
    $("#Search1").click(MyAction.Search);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});
var MyAction = {
    Init: function () {
        $("#Grid").datagrid({
            url: "/System/LogDateOperaView",
            height: $(window).height() - 20,
            sortOrder: 'desc',
            sortName: 'FB_SetName',
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: 'olo_Id',
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符     
            columns: [[
                 { field: 'ck', checkbox: true },
            { field: "emp_Name", title: "操作员", width: 150, align: "center" },
            { field: "olo_Model", title: "操作模块", width: 150, align: "center" },
            {
                field: "olo_Content", title: "操作内容", width: 180, align: "center", formatter: function (value, rowData, index) {
                    return CutString(value, 10);
                }
            },
            { field: "olo_OperateType", title: "操作类型", width: 100, align: "center" },
            {
                field: "olo_OperateTime", title: "操作时间", width: 150, align: "center", formatter: function (value, row, index) {
                    return convertToDate(value, "yyyy-MM-dd hh:mm:ss");
                }
            },
            { field: "olo_IP", title: "登陆IP", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: false
        });
    },
    Add: function () {
        var addDialog = $.hDialog({
            href: "/System/GradeClassEditFormView",
            iconCls: 'icon-add',
            title: "添加类别名称",
            // maximizable: true,//显示最大化
            width: 450,
            height: 300,
            onLoad: function () {
            },
            submit: function () {
             
            }
        })
    },
    Edit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num ==null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        else {
            var editDialog = $.hDialog({
                href: "/System/GradeClassEditFormView",
                // maximizable: true,//显示最大化
                width: 450,
                height: 300,
                iconCls: 'icon-edit',
                title: "修改类别名称",
                onLoad: function () {
                  
                },
                submit: function () {
                  
                }
            })
        }
    },
    Del: function () {
        var rows = $('#Grid').datagrid('getSelections');
        if (rows.length == 0) { Colyn.log("请选择一条数据进行操作"); return; }
        var ids = [];
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].olo_Id);
        }
        ids = ids.join();
        $.messager.confirm("提示", "确认要删除选中的操作日志吗？", function (r) {
            if (r) {
                $.AjaxColynJson("/System/DelOperatorLog", { ids: ids }, function (data) {
                    if (data.Success) {
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
    DelAll: function () {
        $.messager.confirm("提示", "确认要删除全部的操作日志吗？", function (r) {
            if (r) {
                $.AjaxColynJson("/System/DelOperatorLog", { ids: "all" }, function (data) {
                    if (data.Success) {
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
        if ($('#SearchForm').form('validate')) {
            var param = createParam3("SearchForm");
            $.post("/System/LogDateOperaView?", param, function (data) {
                $("#Grid").datagrid("loadData", data);
            }, "json");
        }
    }
}

