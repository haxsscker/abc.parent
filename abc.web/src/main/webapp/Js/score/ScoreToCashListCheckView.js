/*------------------------------------------------
 * Author:李冬冬  Date：2014-8-18 
 -------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#Audit").click(MyAction.Audit);
    $("#btnSearch").click(MyAction.Search);
    $("#Recall").click(MyAction.Recall);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#IntegralToCashGrid").datagrid({
            url: "/score/json/getScoreToCashList.json",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [
                [
                    { field: "cst_user_name", title: "兑现用户", width: 150, align: "center" },
                    {
                        field: "sco_to_cash", title: "兑现金额", width: 100, align: "right", formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }

                    },
                    { field: "sco_to_score", title: "兑现积分", width: 100, align: "right" },
                    {
                        field: "sco_to_cash_date", title: "兑现日期", width: 100, align: "center", formatter: function (value, rowData, index) {
                        if (value == null || value == "") {
                            return "-";
                        } else return value;
                    }
                    },
                    {
                        field: "sco_check_date", title: "审核日期", width: 100, align: "center", formatter: function (value, rowData, index) {
                        if (value == null || value == "") {
                            return "-";
                        } else return value;
                    }
                    },
                    {
                        field: "sco_check_state", title: "审核状态", width: 100, align: "center"
                    },
                    {
                        field: "sco_check_idear", title: "审核意见", width: 100, align: "center", formatter: function (value, rowData, index) {
                        if (value == "" || value == null) return "-"; else  return CutString(value, 10);
                    }
                    }
                ]
            ],
            pagination: true,
            singleSelect: true
        })
        var p = $('#IntegralToCashGrid').datagrid('getPager');
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
        if (row.sco_check_state == "审核未通过" || row.sco_check_state == "审核已通过") {
            Colyn.log("此时不可审核！");
            return;
        }
        var o = { scoId: row.sco_score_id };
        var Dialog = $.hDialog({
            href: "/score/scoreToCashCheckView?" + getParam(o),
            iconCls: 'icon-add',
            title: "积分兑现审核",
            maximizable: true,//显示最大化
            width: $(window).width() - 52,
            height: $(window).height() - 50,
            buttons: [
                {
                    text: '同意',
                    iconCls: 'icon-user_magnify',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                            if ($("#checkIdear").val() == "请输入...") {
                                Colyn.log("请输入审核意见！");
                                return;
                            }
                            var o = {
//                                surId: row.sco_score_id,
//                                checkStatus: "1",
//                                checkIdear: $("#checkIdear").val()
                                surId: row.sco_score_id,
                                opType: 1,
                                reviewType: 16,
                                message: $("#checkIdear").val()
                            };
                            $.AjaxColynJson("/score/json/reviewScoreUsageRecord.json?" + getParam(o), function (data) {
                                if (data.success) {
                                    Colyn.log("审核成功！");
                                }
                                else {
                                    Colyn.log("审核失败！");
                                }
                                MyAction.Init();
                                $('#IntegralToCashGrid').datagrid('clearSelections');
                                Dialog.dialog("close");
                            });
                        }

                    }
                },
                {
                    text: '不同意',
                    iconCls: 'icon-user_magnify',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                            if ($("#checkIdear").val() == "请输入...") {
                                Colyn.log("请输入审核意见！");
                                return;
                            }
                            var o = {
//                                scoId: row.sco_score_id,
//                                checkStatus: "2",
//                                checkIdear: $("#checkIdear").val()
                                surId: row.sco_score_id,
                                opType: 2,
                                reviewType: 16,
                                message: $("#checkIdear").val()
                            };
                            $.AjaxColynJson("/score/json/reviewScoreUsageRecord.json?" + getParam(o), function (data) {
                                if (data.success) {
                                    Colyn.log("审核成功");
                                }
                                else {
                                    Colyn.log("审核失败！");
                                }
                                MyAction.Init();
                                $('#IntegralToCashGrid').datagrid('clearSelections');

                            });
                        }
                        Dialog.dialog("close");
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        Dialog.dialog("close");
                    }
                }
            ]
        })
    },
    //查询
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/score/json/getScoreToCashList.json?" + getParam(o), param, function (data) {
            $("#IntegralToCashGrid").datagrid("loadData", data);
        }, "json");
    },
    //撤回
    Recall: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = { scoId: row.sco_score_id, };
        $.AjaxColynJson("/P2PScoreManage/ScoreToCashWithdrawData?" + getParam(o), function (data) {
            if (data.Success) {
                Colyn.log(data.Msg);
            }
            else {
                Colyn.log(data.Msg);
            }
            MyAction.Init();
            $('#IntegralToCashGrid').datagrid('clearSelections');

        });
    }
}