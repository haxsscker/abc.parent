/*------------------------------------------------
 * Author:李冬冬  Date：2014-8-18 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Audit").click(MyAction.Audit);
    $("#CheckIdear").click(MyAction.CheckIdear);
    $("#bntSearch").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var getData = function (page, rows) {
    $.ajax({
        type: "POST",
        url: "/review/json/LoanBackListSearch.json?" + createSearchParam(),
        data: "page=" + page + "&rows=" + rows,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.progress('close');
        },
        success: function (rows) {
            $('#LoanBackListGrid').datagrid('loadData', rows);
        }
    });
};

var MyAction = {
    Init: function () {
        $("#LoanBackListGrid").datagrid({
            method: "Post",
            url: "/review/json/LoanBackListSearch.json?" + createSearchParam(),
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
                { field: "pro_intent_no", title: "项目名称", width: 150, align: "center" },
                { field: "pdo_product_name", title: "项目类型", width: 100, align: "center" },
                { field: "pro_user_name", title: "借款人", width: 100, align: "center" },
                { field: "pro_add_date", title: "退回申请日期", width: 100, align: "center" },
                {
                    field: "pro_loan_money", title: "借款金额", width: 150, align: "right", formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }
                },
                {
                    field: "pro_loan_rate", title: "年化收益率", width: 80, align: "right", formatter: function (value, rowData, index) {
                        return formatPercent(value);
                    }
                },
                { field: "pro_loan_period", title: "借款期限", width: 80, align: "center" },
                { field: "pro_loan_use", title: "借款用途", width: 250, align: "center" },
                { field: "pro_pay_type", title: "还款方式", width: 100, align: "center" },
                { field: "pro_invest_endDate", title: "招标到期日", width: 100, align: "center" },
                { field: "pro_guar_gov", title: "担保机构", width: 180, align: "center" },
                { field: "pro_loan_intentstate", title: "审核状态", width: 80, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#LoanBackListGrid').datagrid('getPager');
        $(p).pagination({
            onSelectPage: function (pageNumber, pageSize) {
                getData(pageNumber, pageSize);
            },
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
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (!row.can_intent_check) {
            Colyn.log("当前不可审核！");
            return;
        }

        var o = { intentId: row.pro_intent_id };
        dialog = $.hDialog({
            href: "/review/IntentCheckView?" + getParam(o),
            iconCls: 'icon-user_magnify',
            title: "退回审核",
            maximizable: true, //显示最大化
            width: $(window).width() - 200,
            height: $(window).height() - 250,
            buttons: [
                {
                    text: '同意',
                    iconCls: 'icon-user_magnify',
                    handler: function () {
                    	if($("#Colynee").form('validate')){
	                        if ($("#check_idear").val() != "" && $("#check_idear").val() != "请输入...") {
	                            var param = {
	                                reviewType: 19, // 退回审核
	                                applyId   : row.pro_intent_id,
	                                opType    : 1, // 通过
	                                message   : $("#check_idear").val()
	                            };
	                            ToAudit(param, "审核");
	                            dialog.dialog("close");
	                        } else {
	                            Colyn.log("审核意见不能为空！");
	                        }
	                    }
                    }
                },
                {
                    text: '否决',
                    iconCls: 'icon-user_magnify',
                    handler: function () {
                    	if($("#Colynee").form('validate')){
	                        if ($("#check_idear").val() != "" && $("#check_idear").val() != "请输入...") {
	                            var param = {
	                                reviewType: 19, // 退回审核
	                                applyId   : row.pro_intent_id,
	                                opType    : 2, // 否决
	                                message   : $("#check_idear").val()
	                            };
	                            ToAudit(param, "审核");
	                            dialog.dialog("close");
	                        } else {
	                            Colyn.log("审核意见不能为空！");
	                        }
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
    //查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }

        var dialog = $.hDialog({
            href: "/review/IntentLookUpView?intentId=" + row.pro_intent_id,
            iconCls: 'icon-zoom',
            title: "退回审核查看",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dialog.dialog("close");
                }
            }]
        });
    },
    //审核意见
    CheckIdear: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = {
            applyId: row.pro_intent_id,
            reviewType: 19  // 退回审核
        };
        var Dialog = $.hDialog({
            href: "/review/AuditOpinionListView?" + getParam(o),
            iconCls: 'icon-add',
            title: "审核意见",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        });
    },
    //搜索
    Search: function () {
        var param = createSearchParam();
        $.post("/review/json/LoanBackListSearch.json?", param, function (data) {
            $('#LoanBackListGrid').datagrid('loadData', {total: 0, rows: []});
            $("#LoanBackListGrid").datagrid("loadData", data);
        }, "json");
    }
};

// 处理审核操作
// opName是操作的名称，如“撤回”，“退回”等
function ToAudit(param, opName) {
    $.AjaxColynJson("/review/json/ReviewCheckData.json?" + getParam(param), function (data) {
        if (data.success) {
            Colyn.log(opName + "成功！");
        } else {
            Colyn.log(data.message);
        }

        MyAction.Init();
    });
}


function createSearchParam() {
    var form = {
        reviewType : 19, // 退回审核
        intentNo : $("#intentNo").val(),
        userName : $("#userName").val(),
        reviewState : $("#reviewState").val(),
        intentCategory : $("#intentCategory").val(),
        intentTimeFrom : $("#intentTimeFrom").datebox("getValue"),
        intentTimeTo : $("#intentTimeTo").datebox("getValue"),
        intentMoneyFrom : $("#intentMoneyFrom").val(),
        intentMoneyTo : $("#intentMoneyTo").val()
    };

    if ($(".pagination-num").val()) {
        form.page = $(".pagination-num").val();
    }

    if ($(".pagination-page-list").val()) {
        form.rows = $(".pagination-page-list").val();
    }

    return getParam(form);
}
