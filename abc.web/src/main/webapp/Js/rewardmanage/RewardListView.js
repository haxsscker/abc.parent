
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#tnbSearch").click(MyAction.Search);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#RewardGrid").datagrid({
            method: "post",
            url: "/P2PRewardManage/RewardListView",
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
            { field: "cst_user_name", title: "被邀请人", width: 150, align: "center" },
            { field: "cst_add_date", title: "注册日期", width: 80, align: "center", formatter: function (v, d, i) { return convertToDate(v, "yyyy-MM-dd"); } },
            { field: "sys_reward_money", title: "奖励金额", width: 100, align: "center", formatter: function (v, d, i) { return formatMoney(v, "￥"); } },
            { field: "cst_realname_prove", title: "实名认证", width: 100, align: "center", formatter: function(v, d, i) {
                if (v == "1") {
                    return "已认证";
                } else {
                    return "未认证";
                }
            } },
            {
                field: "cst_binding_email", title: "邮箱认证", width: 100, align: "center", formatter: function (v, d, i) {
                    if (v == "1") {
                        return "已认证";
                    } else {
                        return "未认证";
                    }
                }
            },
            {
                field: "cst_binding_mobile", title: "手机认证", width: 100, align: "center", formatter: function (v, d, i) {
                    if (v == "1") {
                        return "已认证";
                    } else {
                        return "未认证";
                    }
                }
            },
            {
                field: "sys_user_state", title: "账户开户", width: 100, align: "center", formatter: function (v, d, i) {
                    if (v == "3") {
                        return "已开户";
                    } else {
                        return "未开户";
                    }
                }
            },
            {
                field: "cst_binding_bankcard", title: "银行绑卡", width: 100, align: "center", formatter: function (v, d, i) {
                    if (v == "1") {
                        return "已绑卡";
                    } else {
                        return "未绑卡";
                    }
                }
            },
            {
                field: "sys_user_state1", title: "是否充值", width: 100, align: "center", formatter: function (v, d, i) {
                    if (v == "4") {
                        return "已充值";
                    } else {
                        return "未充值";
                    }
                }
            },
            {
                field: "sys_user_state2", title: "是否投资", width: 100, align: "center", formatter: function (v, d, i) {
                    if (v == "5") {
                        return "已投资";
                    } else {
                        return "未投资";
                    }
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#RewardGrid').datagrid('getPager');
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
    Search: function() {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/P2PRewardManage/RewardListView?" + getParam(o), param, function (data) {
            $("#RewardGrid").datagrid("loadData", data);
        }, "json");
    }
}

function addname() {
    var v = $("#sl option:selected").val();
    switch (v) {
        case "0":
            $("#hiddenSelect").attr('name', '');
            $("#hiddenSelect").val("");
            break;
        case "1":
            $("#hiddenSelect").attr('name', 'cst_realname_prove');
            $("#hiddenSelect").val("0");
            break;
        case "2":
            $("#hiddenSelect").attr('name', 'cst_realname_prove');
            $("#hiddenSelect").val("1");
            break;
        case "3":
            $("#hiddenSelect").attr('name', 'cst_binding_mobile');
            $("#hiddenSelect").val("0");
            break;
        case "4":
            $("#hiddenSelect").attr('name', 'cst_binding_mobile');
            $("#hiddenSelect").val("1");
            break;
        case "5":
            $("#hiddenSelect").attr('name', 'cst_binding_email');
            $("#hiddenSelect").val("0");
            break;
        case "6":
            $("#hiddenSelect").attr('name', 'cst_binding_email');
            $("#hiddenSelect").val("1");
            break;
        case "7":
            $("#hiddenSelect").attr('name', 'sys_user_state');
            $("#hiddenSelect").attr('_method', 'NotEqual');
            $("#hiddenSelect").val("3");
            break;
        case "8":
            $("#hiddenSelect").attr('name', 'sys_user_state');
            $("#hiddenSelect").removeAttr('_method');
            $("#hiddenSelect").val("3");
            break;
        case "9":
            $("#hiddenSelect").attr('name', 'sys_user_state');
            $("#hiddenSelect").attr('_method', 'NotEqual');
            $("#hiddenSelect").val("4");
            break;
        case "10":
            $("#hiddenSelect").attr('name', 'sys_user_state');
            $("#hiddenSelect").removeAttr('_method');
            $("#hiddenSelect").val("4");
            break;
        case "11":
            $("#hiddenSelect").attr('name', 'sys_user_state');
            $("#hiddenSelect").attr('_method', 'NotEqual');
            $("#hiddenSelect").val("5");
            break;
        case "12":
            $("#hiddenSelect").attr('name', 'sys_user_state');
            $("#hiddenSelect").removeAttr('_method');

            $("#hiddenSelect").val("5");
            break;
    }
    
}