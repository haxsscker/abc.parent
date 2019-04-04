$(function () {
    MyGrid.Resize();
    $("#Add").click(MyAction.Add);
    $("#Del").click(MyAction.Del);
    $("#Edit").click(MyAction.Edit);
    $("#LookUp").click(MyAction.LookUp);
    $("#Release").click(MyAction.Release);
    $("#CancelRelease").click(MyAction.CancelRelease);
    $("#SearchHide").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#WelfareGrid").datagrid({
            method: "GET",
            url: "/loan/WelfareListView",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "fun_fund_no", title: "项目名称", width: 150, align: "center" },
            { field: "fun_fund_name", title: "基金名称", width: 150, align: "center" },
            {
                field: "fun_fund_money", title: "预计发行规模（万）", width: 120, align: "right", formatter: function (value) {
                    if (value==null) {
                        return "-";
                    }
                    else {
                        return "￥" + formatMoney(value);
                    }
                }
            },
            { field: "fun_fund_period", title: "存续期（个月）", width: 100, align: "center" },
            {
                field: "fun_fund_rate", title: "预期年化收益率（%）", width: 120, align: "right", formatter: function (value) {
                    if (value == null) {
                        return "-";
                    }
                    else {
                        return formatMoney(value)+"%";
                    }
                }
            },
            {
                field: "fun_min_money", title: "最低认购金额（万）", width: 120, align: "right", formatter: function (value) {
                    if (value == null) {
                        return "-";
                    }
                    else {
                        return "￥" + formatMoney(value);
                    }
                }
            },
            {
                field: "fun_fund_industry", title: "投资行业", width: 80, align: "center"
            },
            {
                field: "fun_add_date", title: "申请日期", width: 100, align: "center",
                formatter: function (value) {
                    if (!(value == null)) {
                        return convertToDate(value, "yyyy-MM-dd");
                    } else return "-";
                }
            },
            {
                field: "fun_fund_state", title: "发布状态", width: 100, align: "center",
                formatter: function (value) {
                    if (value=='0') {
                        return "未发布";
                    }
                    else {
                        return "已发布";
                    }
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#WelfareGrid').datagrid('getPager');
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
    Add: function () {
        var p = {
            btnToAdd: 1
        }
        Dialog = $.hDialog({

            href: "/loan/WelfareAddView",
            iconCls: 'icon-add',
            title: "有限合伙添加",
            maximizable: true,//显示最大化
            width: $(window).width() - 40,//$(window).width() - 50,
            height: $(window).height() - 50,
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    var fun_fund_no = $("#fun_fund_no").val();
                    var fun_fund_id = $("#fun_fund_id").val();
                    if ($('#Colyn').form('validate')) {
                        var param = createParam2([{ formId: 'main', relaClass: "main" }, { formId: 'fund_profit', isAnyRow: true }]);
                        console.log(param);
                        $.AjaxColynJson("/loan/ScoreTypeAddData?modelAction=Add&fun_fund_no=" + fun_fund_no + "&fun_fund_id=" + fun_fund_id, param, function (data) {
                            if (data.Success) {
                                Colyn.log("添加成功！");
                                MyAction.Init();
                                Dialog.dialog("close");
                            }
                            else {
                                Colyn.log(data.Msg);

                            }
                        }, "json");
                       
                    }
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })
    },
    CustSaveBtn: function () {
        if (r) {
            if ($('#Colyn').form('validate')) {
                var param = createParam2([{ formId: 'main', relaClass: "main" }, { formId: 'fund_profit', isAnyRow: true }]);
                console.log(param);
                $.AjaxColynJson("/loan/ScoreTypeAddsData?modelAction=Add", param, function (data) {
                    if (data.Success) {
                        Colyn.log("添加成功！")
                        MyAction.Init();
                    }
                    else {
                        Colyn.log(data.Msg)
                    }
                });

                jQuery('#LoanGrid').datagrid('clearSelections')
            }
        }
    },
    Del: function () {
        var row = MyGrid.selectRow();
        var numb = row.fun_fund_id;
        var num = row.fun_fund_state;
        if (numb==null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (num=='1') {
            Colyn.log("该数据已发布，不可删除！");
            return;
        }
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r) {
                    $.AjaxColynJson("/loan/ScoreTypeDeleteData?fun_fund_id=" + numb, function (data) {
                        if (data.Success) {
                            Colyn.log("删除成功！")
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.Msg)
                        }
                    });
                    jQuery('#LoanGrid').datagrid('clearSelections')
                }
            })
        }
        else {
            Colyn.log("请选择内容删除！");
        }
    },
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/loan/WelfareLookUpView",
            iconCls: 'icon-add',
            title: "有限合伙查看",
            width: $(window).width() - 40,
            height: $(window).height() - 50,

            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })
    },
    Release: function () {
        var row = MyGrid.selectRow();
        var numb = row.fun_fund_id;
        var num = row.fun_fund_state;
        if (numb==null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (num=='1') {
            Colyn.log("该数据已发布，不可再次发布！");
            return;
        }
        $.AjaxColynJson("/loan/ScoreTypeUpdateData?ReleaseOrCancel=1&fun_fund_id=" + numb, function (data) {
            if (data.Success) {
                Colyn.log("发布成功！");
                MyAction.Init();
            }
            else {
                Colyn.log(data.Msg)
            }
        });
    },
    CancelRelease: function () {
        var row = MyGrid.selectRow();
        var numb = row.fun_fund_id;
        var num = row.fun_fund_state;
        if (numb == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (num == '0') {
            Colyn.log("该数据未发布，不可取消发布！");
            return;
        }
        $.AjaxColynJson("/loan/ScoreTypeUpdateData?ReleaseOrCancel=0&fun_fund_id=" + numb, function (data) {
            if (data.Success) {
                Colyn.log("取消发布成功！");
                MyAction.Init();
            }
            else {
                Colyn.log(data.Msg)
            }
        });
    },
    Edit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (row.fun_fund_state == '1') {
            Colyn.log("该数据已发布,不可进行修改操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/loan/WelfareAddView?fun_fund_id=" + row.fun_fund_id,
            iconCls: 'icon-edit',
            title: "有限合伙修改",
            width: $(window).width() - 40,//$(window).width() - 50,
            height: $(window).height() - 50,
            buttons: [{
                text: '确认修改',
                iconCls: 'icon-edit',
                handler: function () {
                    var fun_fund_no = $("#fun_fund_no").val();
                    if ($('#Colyn').form('validate')) {
                        var param = createParam2([{ formId: 'main', relaClass: "main" }, { formId: 'fund_profit', isAnyRow: true }]);
                        console.log(param);
                        $.post("/loan/ScoreTypeAddData?fun_fund_id=" + row.fun_fund_id + "&fun_fund_no=" + fun_fund_no, param, function (data) {
                            if (data.Success) {
                                Colyn.log("修改成功！");
                                MyAction.Init();
                                Dialog.dialog("close");
                            } else {
                                Colyn.log(data.Msg);
                            }
                        }, "json");
                    }
                }
            },
            {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })
    },
    Search: function () {
        var moneystart = $("#moneystart").val();
        var moneystarts = $("#moneystarts").val();
        var MinDateTime = $("#MinDateTime").datebox("getValue");
        var MaxDateTime = $("#MaxDateTime").datebox("getValue");
        if (MinDateTime != "" && MaxDateTime!="") {
            if (MinDateTime > MaxDateTime) {
                return;
            }
        }
        if (moneystart != "" && moneystarts!="") {
            if (moneystart > moneystarts) {
                return;
            }
        }
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/loan/WelfareListView?t=" + new Date() + "&" + getParam(o), param, function (data) {
            $("#WelfareGrid").datagrid("loadData", data);
        }, "json");
    }
}
