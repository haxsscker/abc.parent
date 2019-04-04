$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Recall").click(MyAction.Recall);
    $("#CheckIdear").click(MyAction.CheckIdear);
    $("#SearchBtn").click(MyAction.Search);
    $("#Audit").click(MyAction.Audit);
    $("#cancelSearch").click(MyAction.cancelSearch);
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
            $('#LoanCheckFullGrid').datagrid('loadData', rows);
        }
    });
};

var MyAction = {
    Init: function () {
        $("#LoanCheckFullGrid").datagrid({
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
                { field: "pro_user_phone", title: "借款人号码", width: 90, align: "center" },
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
                {
                    field: "pro_invest_all", title: "当前投资总额", width: 80, align: "right",
                    formatter: function (v, r, i) {
                        return formatMoney(v, '￥');
                    }
                },
                {
                    field: "pro_curr_invest", title: "当前有效投资金额", width: 80, align: "right",
                    formatter: function (v, r, i) {
                        return formatMoney(v, '￥');
                    }
                },
                { field: "pro_loan_period", title: "借款期限", width: 80, align: "center" },
                { field: "pro_full_date", title: "满标日期", width: 80, align: "center" },
                { field: "pro_invest_endDate", title: "招标到期日", width: 80, align: "center" },
                { field: "pro_loan_use", title: "借款用途", width: 80, align: "center" },
                { field: "pro_pay_type", title: "还款方式", width: 80, align: "center" },
                { field: "gov_name", title: "担保机构", width: 80, align: "center" },
                { field: "pro_check_state", title: "审核状态", width: 80, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#LoanCheckFullGrid').datagrid('getPager');
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
        if (row.can_check == false) {
            Colyn.log("此项目已审核，不可再次审核");
            return;
        }

        var o = { loanId: row.pro_loan_id };
        var Dialog = $.hDialog({
            href: "/review/LoanFullCheckView?" + getParam(o),
            iconCls: 'icon-add',
            title: "满标审核",
            maximizable: false,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [
            {
                text: '满标',
                iconCls: 'icon-user_magnify',
                handler: function () {
                	if($("#Colyn").form('validate')){
		                    if ($("#check_idear").val() != "") {
		                        if ($('#Colyn').form('validate')) {
		                            ToAudit(row.pro_loan_id, 1);
		                            Dialog.dialog("close");
		                        }
		                    } else {
		                        Colyn.log("请输入审核意见");
		                    }
		                }
                	}
            },
            // edit by xiatt 20160626 放开流标
            {
               text: '流标',
               iconCls: 'icon-user_magnify',
               handler: function () {
            	   if($("#Colyn").form('validate')){
	                   if ($("#check_idear").val() != "") {
	                       if ($('#Colyn').form('validate')) {
	                           $.messager.confirm("提示", "确认流标？", function (r) {
	                               if (r) {
	                                   ToAudit(row.pro_loan_id, 2);
	                                   Dialog.dialog("close");
	                               }
	
	                           })
	                       }
	                   } else {
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
        })
    },
    //查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = { loanId: row.pro_loan_id };
        var Dialog = $.hDialog({
            href: "/review/LoanFullLookUpView?" + getParam(o),
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
        })
    },
    //撤回
    Recall: function () {
        var row = MyGrid.selectRow();
        if (row) {
            if (!(row.pro_check_state == "9")) {
                Colyn.log("该项目此时不可撤回！");
                return;
            }

            $.messager.confirm("提示", "确认撤回？", function (r) {
                if (r) {
                    var o = { loanId: row.pro_loan_id };
                    $.AjaxColynJson('/review/LoanFullCheckRecallData?' + getParam(o), function (data) {
                        if (data.success) {
                            Colyn.log("撤回成功！");
                        } else {
                            Colyn.log(data.message);
                        }
                        MyAction.Init();
                        $('#LoanCheckFullGrid').datagrid('clearSelections');
                    });
                }
            })
        } else {
            Colyn.log("请选择一条数据进行操作！");
        }
    },
    // 查看审核意见
    CheckIdear: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = {
            applyId: row.pro_loan_id,
            reviewType: 3  // 满标审核
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
            $('#LoanCheckFullGrid').datagrid('loadData', { total: 0, rows: [] });
            $("#LoanCheckFullGrid").datagrid("loadData", data);
        }, 'json');
    },
    //流标结果查询
    cancelSearch: function () {
    	//debugger;
        var row = MyGrid.selectRow();
        if (row) {
        	//console.log(row.pro_check_state);
            if (row.pro_check_state != "流标处理中") {
                Colyn.log("该项目暂不支持流标结果查询！");
                return;
            }
            var o = { loanId: row.pro_loan_id };
            $.AjaxColynJson('/review/json/CancelLoanResultSearch.json?' + getParam(o), function (data) {
                if (data.success) {
                    Colyn.log("流标成功！");
                } else {
                    Colyn.log(data.message);
                }
                MyAction.Init();
                $('#LoanCheckFullGrid').datagrid('clearSelections');
            });
        } else {
            Colyn.log("请选择一条数据进行操作！");
        }
    }
};

// 处理审核操作
function ToAudit(loanId, checkStatus) {
    var o = {
        applyId: loanId,
        opType: checkStatus,
        message: $("#check_idear").val(),
        reviewType: 3   // 满标审核的reviewType
    };
    $.AjaxColynJson("/review/json/ReviewCheckData.json?" + getParam(o), function (data) {
        if (data.success) {
        	if(checkStatus==2){
        		Colyn.log("审核成功，如果标的有投资记录，请在5分钟之后查询处理结果！");
        	}else{
        		Colyn.log("审核成功！");
        	}
        } else {
            Colyn.log(data.message);
        }
        MyAction.Init();
        $('#LoanCheckFullGrid').datagrid('clearSelections');
    });
}

function createSearchParam() {
    var form = {
        reviewType : 3, // reviewType=3表示是满标审核
        loanNo : $("#loanNo").val(),
        loanUser : $("#loanUser").val(),
        //showGuaranteeGov : $("#showGuaranteeGov").val(),
        reviewState : $("#reviewState").val(),
        loanCategory : $("#loanCategory").val(),
        loanInvestFulltimeFrom : $("#loanInvestFulltimeFrom").datebox("getValue"),
        loanInvestFulltimeTo : $("#loanInvestFulltimeTo").datebox("getValue"),
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
