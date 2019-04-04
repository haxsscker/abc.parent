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
            url: "/sysset/json/getSystemErrorLogList.json",
            singleSelect: true,
            height: $(window).height() - 20,
            sortOrder: 'desc',
            sortName: 'lastUpdateTime',
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: 'id',
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [
                [
                    { field: 'ck', checkbox: true },
                    { field: "fileName", title: "日志文件", width: 200, align: "center" },
                    { field: "fileSize", title: "文件大小", width: 120, align: "center", align: "center" },
                    { field: "createTime", title: "文件创建时间", width: 150, align: "center"},
                    { field: "serverMacAddress", title: "日志文件所属服务器的mac地址", width: 180, align: "center"}
                ]
            ],
            pagination: true
        })
    },
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        else {
            window.location.href = row.ossUrl;
//            Dialog = $.hDialog({
//                href: "/System/LogSystemErrorDetailView?filePath=" + row.fullName,
//                // maximizable: true,//显示最大化
//                width: 620,
//                height: 450,
//                iconCls: 'icon-edit',
//                title: "查看错误日志",
//
//                buttons: [
//                    {
//                        text: '取消返回',
//                        iconCls: 'icon-cancel',
//                        handler: function () {
//                            Dialog.dialog("close");
//                        }
//                    }
//                ]
//            })
        }
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
        $.messager.confirm("提示", "确认要删除选中的统错误日志吗？", function (r) {
            if (r) {
                $.AjaxColynText("/sysset/json/deleteErrorLog.json?", { id: ids }, function (data) {
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
    }, DelAll: function () {
        $.messager.confirm("提示", "确认要删除全部的系统错误日志吗？", function (r) {
            if (r) {
                $.AjaxColynText("/sysset/json/deleteErrorLog.json?", { id: "all" }, function (data) {
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
    }
}

