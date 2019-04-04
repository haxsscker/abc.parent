
$(function () {
    var Dialog;
    MyGrid.Resize();
    MyAction.Init();
    $("#LookUp").click(MyAction.LookUp);
    $("#Dels").click(MyAction.Del);
    $("#Del").click(MyAction.Del);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});
var MyAction = {
    Init: function () {
        $("#Grid").datagrid({
            url: "/Demo/list.aspx?r=true",
            height: $(window).height() - 20,
            sortOrder: 'desc',
            sortName: 'FB_SetName',
            fitColumns: true,
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: 'ID',
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
         { field: "r2", title: "日志文件", width: 150, align: "center" },
         { field: "r3", title: "文件大小", width: 150, align: "center" },
          { field: "r3", title: "文件最后修改时间", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect:true
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
                href: "/System/LogSystemErrorLookUpListView",
                // maximizable: true,//显示最大化
                width: 620,
                height: 450,
                iconCls: 'icon-edit',
                title: "查看错误日志",
                onLoad: function () {
                    $(".BtnGoBack").click(function () {
                        Dialog.dialog("close");
                    });
                },
                submit: function () {
                  
                },
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        Dialog.dialog("close");
                    }
                }]
            })
        }
    },
    Del: function () {
        alert("确定要删除所选记录？");
    }
}

