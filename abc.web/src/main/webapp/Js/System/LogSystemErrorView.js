
$(function () {
    var Dialog;
    MyGrid.Resize();
    MyAction.Init();
    $("#LookUp").click(MyAction.LookUp);
    $("#Dels").click(MyAction.DelAll);
    $("#Del").click(MyAction.Del);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});
var MyAction = {
    Init: function () {
        $("#Grid").datagrid({
            url: "/System/LogSystemErrorView",
            singleSelect: false,
            height: $(window).height() - 20,
            sortOrder: 'desc',
            sortName: 'lastUpdateTime',
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: 'fileName',
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
         { field: 'ck', checkbox: true },
         { field: "fileName", title: "日志文件", width: 200, align: "center" },
         {
             field: "fileSize", title: "文件大小", width: 120, align: "center", align: "center", formatter: function (value, row, index) {
                 return   value+" KB";
             }
         },
          { field: "lastUpdateTime", title: "文件最后修改时间", width: 150, align: "center",formatter: function (value, row, index) {
              return convertToDate(value, "yyyy-MM-dd hh:mm:ss");
          } }
            ]],
            pagination: true
        })
    },
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num ==null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        else {
            Dialog = $.hDialog({
                href: "/System/LogSystemErrorDetailView?filePath="+row.fullName,
                // maximizable: true,//显示最大化
                width: 620,
                height: 450,
                iconCls: 'icon-edit',
                title: "查看错误日志",
               
                buttons: [{
                    text: '取消返回',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        Dialog.dialog("close");
                    }
                }]
            })
        }
    },
    Del: function () {
        var rows = $('#Grid').datagrid('getSelections');
        if (rows.length == 0) { Colyn.log("请选择一条数据进行操作"); return; }
        var ids = [];
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].fileName);
        }
        ids = ids.join();
        $.messager.confirm("提示", "确认要删除选中的统错误日志吗？", function (r) {
            if (r) {
                $.AjaxColynText("/System/DelErrorLog", { fileNames: ids }, function (data) {
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
    }, DelAll: function () {
        $.messager.confirm("提示", "确认要删除全部的系统错误日志吗？", function (r) {
            if (r) {
                $.AjaxColynText("/System/DelErrorLog", { fileNames: "all" }, function (data) {
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
}

