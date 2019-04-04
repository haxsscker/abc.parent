$(function () {
    MyGrid.Resize();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#btnSearch").click(MyAction.Search);
    $("#LookUp").click(MyAction.LookUp);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    //列表OK
    Init: function () {
        $("#BankAccountManage").datagrid({
            url: "/loan/BankAccountManageList",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
           // idField: "pro_loan_id",
            striped: true,
            remoteSort: true,
            view: myview, //重写当没有数据时
            emptyMsg: '没有找到数据', //返回数据字符
            columns: [
                [
                    { field: "fun_fund_name", title: "基金名称", width: 150, align: "center" },
                    { field: "mon_bank_name", title: "银行名称", width: 100, align: "center" },
                    { field: "mon_bank_card", title: "银行卡号", width: 100, align: "center" },
                    { field: "mon_user_name", title: "账户户名", width: 100, align: "center" }
                ]
            ],
            pagination: true,
            singleSelect: true
        });
        var p = $('#BankAccountManage').datagrid('getPager');
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
    },
    //添加OK
    Add: function() {
        var dialog = $.hDialog({
            href: "/loan/BankAccountManageAddOrEdiet",
            iconCls: 'icon-add',
            title: "银行卡添加",
            width: 400,
            height: 250,
            buttons: [
                {
                    text: '确认添加',
                    iconCls: 'icon-add',
                    handler: function () {
                        if ($("#Colynaddorediet").form("validate")) {
                            if ($("#fun_fund_names option:selected").text() != "") {
                                var param = createParam2([{ formId: 'bankInfo' }], { fun_fund_name: $("#fun_fund_names option:selected").text() });
                                {
                                };
                                $.post("/loan/AddOrEdietData", param, function(data) {
                                    if (data.Success) {
                                        Colyn.log("添加成功！");
                                        MyAction.Init();
                                        dialog.dialog("close");
                                    } else {
                                        Colyn.log("添加失败！");
                                    }
                                }, "json");
                            } else {
                                Colyn.log("请选择基金名称！");
                            }
                        }
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function() {
                        dialog.dialog("close");
                    }
                }
            ]
        });
    },
    //修改OK
    Edit: function () {
        var row = MyGrid.selectRow();
        if (row==null) {
            Colyn.log("请选择一条数据操作！");
            return;
        }
        var dialog = $.hDialog({
            href: "/loan/BankAccountManageAddOrEdiet?mon_bank_id=" + row.mon_bank_id,
            iconCls: 'icon-pencil',
            title: "银行卡修改",
            width: 400,
            height: 250,
            buttons: [
                {
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
                        if ($("#Colynaddorediet").form("validate")) {
                            var param = createParam2([{ formId: 'bankInfo' }], { fun_fund_name: $("#fun_fund_names option:selected").text() });
                            {
                            };
                            $.post("/loan/AddOrEdietData", param, function(data) {
                                if (data.Success) {
                                    Colyn.log("修改成功！");
                                    MyAction.Init();
                                    dialog.dialog("close");
                                } else {
                                    Colyn.log("修改失败！");
                                }
                            }, "json");
                        }
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        dialog.dialog("close");
                    }
                }
            ]
        });
    },
    //删除OK
    Del: function () {
        var row = MyGrid.selectRow();
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条数据操作！");
            return;
        }
        $.messager.confirm("系统提示", "是否确认删除？", function(r) {
            if (r) {
                $.post("/loan/BankAccountManagegDeleteData?mon_bank_id=" + row.mon_bank_id, function(data) {
                    if (data.Success) {
                        Colyn.log("删除成功！");
                        MyAction.Init();
                    } else {
                        Colyn.log("删除失败！");
                    }
                }, "json");
            }
        });
    },
    //搜索OK
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/loan/BankAccountManageList?" + getParam(o), param, function (data) {
            $("#BankAccountManage").datagrid("loadData", data);
        }, "json");
    },
    //查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条数据操作！");
            return;
        }
        var dialog = $.hDialog({
            href: "/loan/BankAccountManagegLookUp?mon_bank_id=" + row.mon_bank_id,
            iconCls: 'icon-zoom',
            title: "银行卡详情",
            width: 400,
            height: 250,
            buttons: [
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        dialog.dialog("close");
                    }
                }
            ]
        });
    }
}

