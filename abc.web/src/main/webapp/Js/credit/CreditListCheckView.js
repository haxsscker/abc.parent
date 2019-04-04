/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-19 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Audit").click(MyAction.Audit);
    $("#btnSearch").click(MyAction.Search)
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        var param = createParam3("SearchForm");
        $("#CreditReviewListGrid").datagrid({
            //method:"GET",
            url: "/credit/json/getCreditList.json?" + param,
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            idField: "cre_apply_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "cst_user_name", title: "用户名", width: 100, align: "center" },
            { field: "cst_real_name", title: "真实姓名", width: 100, align: "center" ,formatter: function (value, rowData, index) {
                if (value == null || value == "") return "-"; else return value;
            }},
            {
                field: "cst_user_score", title: "用户积分", width: 70, align: "right", formatter: function (value, rowData, index) {
                    if (value == null || value == "") return "0.00"; else return formatPercent(value,"");
                }
            },
            {
                field: "cst_loan_credit", title: "信用额度", width: 100, align: "right", formatter: function (value, rowData, index) {
                    if (value == null || value == "") return "￥0.00"; else return formatMoney(value, '￥');
                }
            },
            {
                field: "cre_apply_money", title: "申请额度", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "cre_check_money", title: "审批额度", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            { field: "cre_apply_date", title: "申请日期", width: 100, align: "center"
            },
            //{
            //    field: "cre_credit_type", title: "申请额度类型", width: 100, align: "center", formatter: function (value, rowData, index) {
            //        switch (value) {
            //            case "1":
            //                return "借款信用额度";
            //            case "2":
            //                return "投资担保额度";
            //            case "3":
            //                return "借款担保额度";
            //        }
            //    }
            //},
            {
                field: "cre_check_state", title: "审核状态", width: 100, align: "center"
            },
            {
                field: "cre_check_idear", title: "审核意见", width: 100, align: "center", formatter: function (value, rowData, index) {
                    if (value == "" || value == null) { return "-"} else return CutString(value, 10);
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#CreditReviewListGrid').datagrid('getPager');
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
    ToAudit: function (creId, checkStatus) {
   
        if ($('#Colyn').form('validate')) {
            var o = {
                creId: creId,
                checkStatus: checkStatus,
                checkMoney: $("#cre_check_money").val(),
                checkIdear: $("#cre_check_idear").val()
            };
            $.AjaxColynJson("/P2PSelfCheck/CreditCheckData?" + getParam(o), function (data) {
                if (data.Success) {
                    Colyn.log("审核成功！");
                }
                else {
                    Colyn.log(data.Msg);
                }
                MyAction.Init();
                $('#CreditReviewListGrid').datagrid('clearSelections');

            });
        }
    },
    //审核
    Audit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
//        if (row.cre_check_state == "1" || row.cre_check_state == "2") {
        if (row.cre_check_state != "待审核") {
            Colyn.log("信用额度申请已审核");
            return;
        }
        var o = { creditId: row.cre_apply_id, userId: row.cre_user_id };
        var Dialog = $.hDialog({
            href: "/credit/CreditCheckView?"+ getParam(o),
            iconCls: 'icon-add',
            title: "信用额度审批",
            maximizable: true,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '同意',
                iconCls: 'icon-user_magnify',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                        var o = {
                            creId: row.cre_apply_id,
                            userId: row.cre_user_id,
                            opType: 1,
                            reviewType: 18,
                            checkMoney: $("#cre_check_money").val(),
                            message: $("#cre_check_idear").val()
                        };
                        var checkMoney=$("#cre_check_money").val();
                        if (checkMoney == "" || checkMoney == null)
                        {
                            Colyn.log("请输入审核额度！");
                            return;
                        }

                        if ($("#cre_check_idear").val() == "请输入...")
                        {
                            Colyn.log("请输入审核意见！");
                            return;
                        }
                        
                        $.post("/credit/json/reviewCreditApply.json", o, function (data) {
                        	if (data.success) {
                                Colyn.log("审核成功！");
                                MyAction.Init();
                                $('#CreditReviewListGrid').datagrid('clearSelections');
                                Dialog.dialog("close");
                            }
                            else {
                                Colyn.log(data.message);
                            }
                        }, 'Json');
                        
                    }
                   
                }
            }, {
                text: '不同意',
                iconCls: 'icon-user_magnify',
                handler: function () {
                        var o = {
                            creId: row.cre_apply_id,
                            userId: row.cre_user_id,
                            opType: 2,
                            reviewType: 18,
                            checkMoney: $("#cre_check_money").val(),
                            message: $("#cre_check_idear").val()
//                            creId: row.cre_apply_id,
//                            checkStatus: "2",
//                            checkMoney: $("#cre_check_money").val(),
//                            checkIdear: $("#cre_check_idear").val()
                        };
                        var checkMoney = $("#cre_check_money").val();
                        if (checkMoney == "" || checkMoney == null) {
                            Colyn.log("请输入审核额度！");
                            return;
                        }

                        if ($("#cre_check_idear").val() == "请输入...") {
                            Colyn.log("请输入审核意见！");
                            return;
                        }
                        
                        $.post("/credit/json/reviewCreditApply.json", o, function (data) {
                        	if (data.success) {
                                Colyn.log("审核成功！");
                                MyAction.Init();
                                $('#CreditReviewListGrid').datagrid('clearSelections');
                                Dialog.dialog("close");
                            }
                            else {
                                Colyn.log(data.message);
                            }
                        }, 'Json');
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
        var o = { creditId: row.cre_apply_id, userId: row.cre_user_id };
        var Dialog = $.hDialog({
            href: "/credit/CreditLookUpView?" + getParam(o),
            iconCls: 'icon-add',
            title: "信用额度审批详情",
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
    //搜索
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/credit/json/getCreditList.json?" + getParam(o), param, function (data) {
            $("#CreditReviewListGrid").datagrid("loadData", data);
        }, "json");
    }
}