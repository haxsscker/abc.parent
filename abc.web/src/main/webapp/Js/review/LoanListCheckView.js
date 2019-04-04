$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Recall").click(MyAction.Recall);
    $("#SearchBtn").click(MyAction.Search);
    $("#Audit").click(MyAction.Audit);
    $("#CheckIdear").click(MyAction.CheckIdear);
    $("#Return").click(MyAction.Return);

    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var getData = function (page, rows) {
    $.ajax({
        type: "POST",
        url: "/review/json/LoanListSearch.json?" + createSearchParam(),
        data: "page=" + page + "&rows=" + rows,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.progress('close');
        },
        success: function (rows) {
            $('#LoanCheckGrid').datagrid('loadData', rows);
        }
    });
};

var MyAction = {
    Init: function () {
        $("#LoanCheckGrid").datagrid({
            method: "GET",
            url: "/review/json/LoanListSearch.json?" + createSearchParam(),
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
                { field: "pro_loan_no", title: "项目名称", width: 150, align: "center" },
                { field: "pdo_product_name", title: "项目类型", width: 100, align: "center" },
                { field: "pro_user_name", title: "借款人", width: 80, align: "center" },
                { field: "pro_user_phone", title: "借款人电话", width: 90, align: "center" },
                {
                    field: "pro_loan_money", title: "借款金额", width: 80, align: "right",
                    formatter: function (v, r, i) {
                        return formatMoney(v, '￥');
                    }
                },
                {
                    field: "pro_loan_rate", title: "年化收益率", width: 80, align: "right",
                    formatter: function (v, r, i) {
                        return formatPercent(v);
                    }
                },
                { field: "pro_loan_period", title: "借款期限", width: 80, align: "center" },
                { field: "pro_add_date", title: "申请日期", width: 80, align: "center" },
                { field: "pro_invest_endDate", title: "招标到期日", width: 80, align: "center" },
                { field: "pro_loan_use", title: "借款用途", width: 80, align: "center",
                	formatter: function(value){
	                	if(value!=null && value.length>10){
	                		return value.substring(0,10)+"...";
	                	}
	                	return value;
                	} 
                },
                { field: "pro_pay_type", title: "还款方式", width: 80, align: "center" },
                {
                    field: "gov_name", title: "担保机构", width: 80, align: "center",
                    formatter: function (v, r, i) {
                        return v == null ? "-" : v;
                    }
                },
                { field: "pro_check_state", title: "审核状态", width: 80, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#LoanCheckGrid').datagrid('getPager');
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
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (row.can_check == false) {
            Colyn.log("此项目已审核，不可再次审核");
            return;
        }
        var o = { loanId: row.pro_loan_id };
        var Dialog = $.hDialog({
            href: "/review/LoanCheckView?" + getParam(o),
            iconCls: 'icon-add',
            title: "项目初审",
            maximizable: true, //显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [
            {
                text: '同意',
                iconCls: 'icon-user_magnify',
                handler: function () {
                	if($("#Colyn").form('validate')){
	                    if ($("#check_idear").val() != "") {
	                        if ($('#Colyn').form('validate')) {
	                            var param = {
	                                reviewType: 2, // 项目初审
	                                applyId   : row.pro_loan_id,
	                                opType    : 1, // 通过
	                                message   : $("#check_idear").val()
	                            };
	                            ToAudit(param, "审核");
	                            Dialog.dialog("close");
	                        }
	                    } else {
	                        Colyn.log("请输入审核意见");
	                    }
                    }
                }
            },
            {
                text: '否决',
                iconCls: 'icon-user_magnify',
                handler: function () {
                	if($("#Colyn").form('validate')){
		                   if ($("#check_idear").val() != "") {
		                       if ($('#Colyn').form('validate')) {
		                           var param = {
		                               reviewType: 2, // 项目初审
		                               applyId   : row.pro_loan_id,
		                               opType    : 2, // 否决
		                               message   : $("#check_idear").val()
		                           };
		                           ToAudit(param, "审核");
		                           Dialog.dialog("close");
		                       }
		                   }
		                   else {
		                       Colyn.log("请输入审核意见");
		                   }
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
        });
    },
    // 查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = { loanId: row.pro_loan_id };
        var Dialog = $.hDialog({
            href: "/review/LoanInfoLookUpView?" + getParam(o),
            iconCls: 'icon-add',
            title: "借款信息查看",
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
    // 撤回
    Recall: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条数据进行操作");
            return;
        }
        // 只有审核已通过（即状态已为待发布）的项目才能撤回
        if (!row.can_revoke) {
            Colyn.log("该项目此时不可撤回！");
            return;
        }

        $.messager.confirm("提示", "确认撤回？", function (r) {
            if (r) {
                $.post("/review/json/LoanReviewRevoke.json?loanId=" + row.pro_loan_id, function (data) {
                    if (data.success) {
                        Colyn.log("撤回成功！");
                    } else {
                        Colyn.log(data.message);
                    }

                    MyAction.Init();
                }, "json");
            }

        });
    },
    // 退回
    Return: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }

        // 只有未结束（待审核）的才能退回
        // 审核结束后，不能执行退回操作
        if (!row.can_check) {
            Colyn.log("审核已结束，不能退回！");
            return;
        }

        var Dialog = $.hDialog({
            href: "/review/rollbackCheckView",
            iconCls: 'icon-add',
            title: "退回意见",
            width: $(window).width() - 80,
            buttons: [
            {
                text: '确认退回',
                iconCls: 'icon-add',
                handler: function () {
                    var msg = $("#checkIdear").val();
                    if (msg == "" || msg == null || msg == "请输入...") {
                        Colyn.log("请输入退回意见！");
                        return;
                    }

                    var param = {
                        reviewType: 2, // 项目初审
                        applyId   : row.pro_loan_id,
                        opType    : 3, // 退回
                        message   : msg
                    };
                    ToAudit(param, "退回");
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
        });
    },
    // 审核意见
    CheckIdear: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = {
            applyId: row.pro_loan_id,
            reviewType: 2  // 项目初审
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
        $.post("/review/json/LoanListSearch.json?", param, function (data) {
            $("#LoanCheckGrid").datagrid("loadData", data)
        }, 'json');
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
        reviewType : 2, // reviewType=2表示是项目初审
        loanNo : $("#loanNo").val(),
        loanUser : $("#loanUser").val(),
        //showGuaranteeGov : $("#showGuaranteeGov").val(),
        reviewState : $("#reviewState").val(),
        loanCategory : $("#loanCategory").val(),
        loanCreatetimeFrom : $("#loanCreatetimeFrom").datebox("getValue"),
        loanCreatetimeTo : $("#loanCreatetimeTo").datebox("getValue"),
        loanMoneyFrom : $("#loanMoneyFrom").val(),
        loanMoneyTo : $("#loanMoneyTo").val()
    };

    if ($(".pagination-num").val()) {
        form.page = $(".pagination-num").val();
    }

    if ($(".pagination-page-list").val()) {
        form.rows = $(".pagination-page-list").val();
    }

    return getParam(form);
}
