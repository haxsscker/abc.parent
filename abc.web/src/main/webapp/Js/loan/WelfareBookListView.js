
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Audit").click(MyAction.Audit);
    $("#CheckIdear").click(MyAction.CheckIdear);
    $("#SearchHide").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#WelfareBookGrid").datagrid({
            method: "GET",
            url: "/loan/WelfareBookListView",
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
            { field: "emp_NickName", title: "联系人", width: 100, align: "center" },
            { field: "fun_user_phone", title: "联系电话", width: 100, align: "center" },
            {
                field: "fun_recharge_money", title: "充值金额（万）", width: 100, align: "center", formatter: function (value) {

                    if (!(value == null)) {
                        return value;
                    }
                    else {
                        return "-";
                    }
                }
            },
            {
                field: "fun_recharge_date", title: "充值日期", width: 100, align: "center", formatter: function (value) {

                    if (!(value==null)) {
                        return convertToDate(value, "yyyy-MM-dd");
                    }
                    else {
                        return "-";
                    }

                }
            },
            {
                field: "fun_check_state", title: "审核状态", width: 100, align: "center", formatter: function (value) {
                    if (value=='1') {
                        return "已确认";
                    }
                    else if (value == '2') {
                        return "已放弃";
                    } else {
                        return "待审核";
                    }
                }
            },
            {
                field: "emp_NickName", title: "审核人", width: 100, align: "center", formatter: function (value) {
                    if (value != null) {
                        return value;
                    }
                    else {
                        return "-";

                    }
                }
            },

            {
                field: "fun_check_date", title: "审核日期", width: 100, align: "center", formatter: function (value) {
                    if (!(value==null)) {
                        return convertToDate(value, "yyyy-MM-dd");
                    } else {
                        return "-";
                    }
                }
            },
            {
                field: "fun_check_idear", title: "审核意见", width: 100, align: "center", formatter: function (value) {
                    if (value==null) {
                        return "-";
                    }
                   else if (value.length>5) {
                        var idear = value.substring(0, 5) + "...";
                        return idear;
                    }
                    else {
                        return value;
                    }
                }
            },
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#WelfareBookGrid').datagrid('getPager');
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
    //审核
    Audit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var state = row.fun_check_state;
        if (state=='1'||state=='2') {
            Colyn.log(" 该数据已经被审核，不可再次审核！");
            return;
        }
        var Dialog = $.hDialog({
            href: "/loan/WelfareBookCheckView?fun_fund_id=" + row.fun_fund_id + "&fun_order_id=" + row.fun_order_id,
            iconCls: 'icon-add',
            title: "有限合伙预约审核",
            maximizable: true,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '确认',
                iconCls: 'icon-user_magnify',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                        var fun_recharge_money = $("#fun_recharge_money").val();
                        var fun_recharge_date = $("#fun_recharge_date").datebox("getValue");;
                        var fun_check_idear = $("#fun_check_idear").val();
                        var fun_order_id = $("#fun_order_id").val();
                       // var o = { fun_fund_id: row.fun_fund_id, confirmOrAbandon: "1", fun_check_idear: row.fun_check_idear, fun_recharge_money: row.fun_recharge_money, fun_recharge_date: row.fun_recharge_date, fun_order_id: row.fun_order_id };
                        $.post("/loan/WelfareBookCheckData?confirmOrAbandon=1&t=" + new Date() + "&fun_recharge_money=" + fun_recharge_money + "&fun_recharge_date=" + fun_recharge_date + "&fun_check_idear=" + fun_check_idear + "&fun_order_id=" + fun_order_id + "&fun_fund_id=" + row.fun_fund_id, function (data) {
                            if (data.Success) {
                                Colyn.log("审核成功！");
                                MyAction.Init();
                            }
                            else {
                                Colyn.log(data.Msg);
                            }
                        }, "json");
                        Dialog.dialog("close");
                    }
                }
            },
               {
                   text: '放弃',
                   iconCls: 'icon-user_magnify',
                   handler: function () {
                       if ($('#Colyn').form('validate')) {
                           var fun_recharge_money = $("#fun_recharge_money").val();
                           var fun_recharge_date = $("#fun_recharge_date").datebox("getValue");;
                           var fun_check_idear = $("#fun_check_idear").val();
                           var fun_order_id = $("#fun_order_id").val();
                           //var o = { fun_fund_id: row.fun_fund_id, confirmOrAbandon: "2", fun_check_idear: row.fun_check_idear, fun_recharge_money: row.fun_recharge_money, fun_recharge_date: row.fun_recharge_date, fun_order_id: row.fun_order_id };
                           $.post("/loan/WelfareBookCheckData?confirmOrAbandon=2&t=" + new Date() + "&fun_recharge_money=" + fun_recharge_money + "&fun_recharge_date=" + fun_recharge_date + "&fun_check_idear=" + fun_check_idear + "&fun_order_id=" + fun_order_id + "&fun_fund_id=" + row.fun_fund_id, function (data) {
                               if (data.Success) {
                                   Colyn.log("审核成功！");
                                   MyAction.Init();
                               }
                               else {
                                   Colyn.log(data.Msg);
                               }
                           }, "json");
                           Dialog.dialog("close");
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
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/loan/WelfareBookLookUpView?fun_fund_id=" + row.fun_fund_id + "&fun_order_id=" + row.fun_order_id + "&fun_check_id=" + row.fun_check_id,
            iconCls: 'icon-add',
            title: "有限合伙预约查看",
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
    //CheckIdear: function () {
    //    var row = MyGrid.selectRow();
    //    var num = row;
    //    //if (num == null) {
    //    //    Colyn.log("请选择一条记录进行操作");
    //    //    return;
    //    //}
    //    var Dialog = $.hDialog({
    //        href: "/loan/WelfareCheckIdearLookUpView",
    //        iconCls: 'icon-add',
    //        title: "审核意见",
    //        width: $(window).width(),
    //        height: $(window).height() - 20,
    //        buttons: [{
    //            text: '关闭',
    //            iconCls: 'icon-cancel',
    //            handler: function () {

    //                Dialog.dialog("close");
    //            }
    //        }]
    //    })
    //}
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/loan/WelfareBookListView?t=" + new Date() + "&" + getParam(o), param, function (data) {
            $("#WelfareBookGrid").datagrid("loadData", data);
            $("#WelfareBookGrid").datagrid("clearSelections");
        }, "json");
    }
}
