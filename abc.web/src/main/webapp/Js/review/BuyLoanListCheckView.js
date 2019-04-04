$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Audit").click(MyAction.Audit);
    $("#CheckIdear").click(MyAction.CheckIdear);
    $("#search").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var getData = function (page, rows) {
    $.ajax({
        type: "POST",
        url: "/review/json/BuyLoanListSearch.json?" + createSearchParam(),
        data: "page=" + page + "&rows=" + rows,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.progress('close');
        },
        success: function (rows) {
            $('#BuyLoanCheckGrid').datagrid('loadData', rows);
        }
    });
};

var MyAction = {
    Init: function () {
        $("#BuyLoanCheckGrid").datagrid({
            url: "/review/json/BuyLoanListSearch.json?" + createSearchParam(),
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
                { field: "loan_user_name", title: "借款人", width: 80, align: "center" },
                {
                   field: "pro_loan_money", title: "借款金额", width: 80, align: "right", formatter: function (value, row, index) {
                       return formatMoney(value, '￥');
                   }
                },
                {
                   field: "pro_loan_rate", title: "年化收益率", width: 80, align: "right", formatter: function (value, row, index) {
                       return formatPercent(value);
                   }
                },
                { field: "cst_real_name", title: "收购人", width: 80, align: "center" },
                {
                   field: "pro_buy_total", title: "收购报价", width: 80, align: "right", formatter: function (value, row, index) {
                       return formatMoney(value, '￥');
                   }
                },
                {
                   field: "pro_buy_money", title: "收购份额", width: 80, align: "right", formatter: function (value, row, index) {
                       return formatMoney(value, '￥');
                   }
                },
                {
                   field: "pro_buy_loan_fee", title: "收购手续费", width: 80, align: "right", formatter: function (value, row, index) {
                       return formatMoney(value, '￥');
                   }
                },
                {
                   field: "pro_buy_loan_date", title: "申请日期", width: 80, align: "center", formatter: function (value, row, index) {
                       return value;
                   }
                },
                { field: "pro_buy_loan_state", title: "审核状态", width: 80, align: "center"}
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#BuyLoanCheckGrid').datagrid('getPager');
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
        if (row.can_check) {
            var buyLoanId = row.pro_buy_loan_id;
            var loanId = row.pro_loan_id;
            var loanNO = row.pro_loan_no;
            var Dialog = $.hDialog({
                href: "/review/buyLoanFullCheckView?loanId=" + loanId + "&buyLoanId=" + buyLoanId,
                iconCls: 'icon-add',
                title: "收购审核",
                maximizable: false,//显示最大化
                width: $(window).width() - 40,
                height: $(window).height() - 50,
                buttons: [
                {
                    text: '同意',
                    iconCls: 'icon-user_magnify',
                    handler: function () {
                    	if($("#Colyn").form('validate')){
	                        if ($("#checkIdea").val() != "") {
	                            if ($('#Colyn').form('validate')) {
	                                ToAudit(buyLoanId, 1);
	                                Dialog.dialog("close");
	                            }
	                        } else {
	                            Colyn.log("请输入审核意见");
	                        }
                        }
                    }
                },
                {
                    text: '不同意',
                    iconCls: 'icon-user_magnify',
                    handler: function () {
                    	if($("#Colyn").form('validate')){
	                       if ($("#checkIdea").val() != "") {
	                           if ($('#Colyn').form('validate')) {
	                               ToAudit(buyLoanId, 2);
	                               Dialog.dialog("close");
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
            });
        } else {
            Colyn.log("该项目已审核，不可再操作！");
        }
    },
    LookUp: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/review/buyLoanFullLookUpView?loanId=" + row.pro_loan_id + "&buyLoanId=" + row.pro_buy_loan_id,
            iconCls: 'icon-add',
            title: "收购 信息查看",
            maximizable: true,//显示最大化
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
    CheckIdear: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/review/auditOpinionListView?reviewType=6&applyId=" + row.pro_buy_loan_id,
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
    Search: function () {
        var param = createSearchParam("SearchForm");
        $.post( "/review/json/BuyLoanListSearch.json?", param, function (data) {
        	$('#BuyLoanCheckGrid').datagrid('loadData', {total : 0,rows : []});
			$("#BuyLoanCheckGrid").datagrid("loadData", data);
        }, "json");
    }
};

// 处理审核操作
function ToAudit(buyLoanId, checkStatus) {
    var o = {
        applyId: buyLoanId,
        opType: checkStatus,
        reviewType: 6,
        message: $("#checkIdea").val()
    };

    $.AjaxColynJson('/review/json/ReviewCheckData.json?' + getParam(o), function (data) {
        if (data.success) {
            Colyn.log("审核成功");
        } else {
            Colyn.log(data.message);
        }

        MyAction.Init();
        $("#BuyLoanCheckGrid").datagrid("clearSelections");
    });
}

function createSearchParam() {
    var form = {
        reviewType : 6, // 收购审核
        loanNo : $("#pro_loan_no").val(),
        loanCategory : $("#pro_product_type").val(),
        //showGuaranteeGov : $("#showGuaranteeGov").val(),
        userName : $("#cst_user_name").val(),
        buyTotalFrom : $("#pro_buy_total_from").val(),
        buyTotalTo : $("#pro_buy_total_to").val(),
        applyDateFrom : $("#pro_loan_buy_date_from").val(),
        applyDateTo : $("#pro_loan_buy_date_to").val(),
        applyState : $("#pro_buy_loan_state").val()
    };

    if ($(".pagination-num").val()) {
        form.page = $(".pagination-num").val();
    }

    if ($(".pagination-page-list").val()) {
        form.rows = $(".pagination-page-list").val();
    }

    return getParam(form);
}

