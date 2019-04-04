$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Audit").click(MyAction.Audit);
    $("#CheckIdear").click(MyAction.CheckIdear);
    $("#search").click(MyAction.Search);
    $("#cancelTransferLoan").click(MyAction.CancelTransferLoan);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var getData = function (page, rows) {
    $.ajax({
        type: "POST",
        url: "/review/json/TransferListSearch.json?" + createSearchParam(),
        data: "page=" + page + "&rows=" + rows,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.progress('close');
        },
        success: function (rows) {
            $('#LoanRedeemCheckGrid').datagrid('loadData', rows);
        }
    });
};

var MyAction = {
    Init: function () {
        $("#LoanRedeemCheckGrid").datagrid({
            url: "/review/json/TransferListSearch.json?" + createSearchParam(),
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
                { field: "pro_transferloan_no", title: "转让项目编号", width: 150, align: "center" },
                { field: "pdo_product_name", title: "项目类型", width: 80, align: "center" },
                { field: "loan_user_name", title: "借款人", width: 100, align: "center" },
                { field: "loan_user_phone", title: "借款人号码", width: 100, align: "center" },
                {
                    field: "pro_loan_money", title: "借款金额", width: 100, align: "right", formatter: function (value, row, index) {
                        return formatMoney(value, '￥');
                    }
                },
                {
                    field: "pro_loan_rate", title: "年化收益率", width: 70, align: "right", formatter: function (value, row, index) {
                        return formatPercent(value);
                    }
                },
                { field: "pro_loan_period", title: "借款期限", width: 70, align: "right" },
                { field: "payCount", title: "转让期数", width: 60, align: "center" },
                { field: "cst_real_name", title: "转让人", width: 100, align: "center" },
                {
                    field: "pro_transfer_money", title: "转让金额", width: 100, align: "right", formatter: function (value, row, index) {
                        return formatMoney(value, '￥');
                    }
                },
                {
                    field: "pro_transfer_fee", title: "手续费", width: 100, align: "right", formatter: function (value, row, index) {
                        return formatMoney(value, '￥');
                    }
                },
                { field: "pro_transfer_date", title: "申请日期", width: 100, align: "center" },
                { field: "pro_transfer_state", title: "转让状态", width: 100, align: "center" },
                { field: "pro_check_state", title: "审核状态", width: 70, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        });

        var p = $('#LoanRedeemCheckGrid').datagrid('getPager');
        $(p).pagination({
//            onSelectPage: function (pageNumber, pageSize) {
//                getData(pageNumber, pageSize);
//            },
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
            var o = {
                loanId: row.pro_loan_id,
                transferId: row.pro_transfer_id
            };
            var transferId = row.pro_transfer_id;
            var Dialog = $.hDialog({
                href: "/review/LoanRedeemCheckView?" + getParam(o),
                iconCls: 'icon-add',
                title: "转让审核",
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
	                                ToAudit(transferId, 1);
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
		                               $.messager.confirm("审核提示", "确认不同意该审核信息吗？", function (r) {
		                                   if (r) {
		                                       ToAudit(transferId, 2);
		                                       Dialog.dialog("close");
		                                   }
		                               });
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
        } else {
            Colyn.log("该项目已审核，不可再操作！");
            return;
        }
    },
    // 查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/review/LoanRedeemInfoLookUpView?loanId=" + row.pro_loan_id + "&transferId=" + row.pro_transfer_id,
            iconCls: 'icon-add',
            title: "转让申请查看",
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
    // 查看审核意见
    CheckIdear: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = {
            applyId: row.pro_transfer_id,
            reviewType: 4  // 转让初审
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
	// 搜索
	Search : function() {
		var param = createSearchParam();
		$.post("/review/json/TransferListSearch.json?", param,
			function(data) {
				$('#LoanRedeemCheckGrid').datagrid('loadData', {total : 0,rows : []});
				$("#LoanRedeemCheckGrid").datagrid("loadData", data);
			}, 'json');
	},
	//手动流标
	CancelTransferLoan: function(){
		var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        //(待审核、转让招标中、满标待审、满标审核通过、满标审核未通过)可流标
        var state = row.pro_transfer_state;
        if(state=='已划转'||state=='已流标'||state=='划转中'||state=='初审未通过'){
        	Colyn.log(state+",不可流标");
            return;
        }
        $.messager.confirm('提示','确定要流标吗?',function(r){
            if (r){
              var transferLoanId = row.pro_transfer_id;
              $.ajax({
              	url: '/review/json/cancelTransferLoan.json?transferLoanId='+transferLoanId,
              	success: function(result){
              		 Colyn.log(result.message);
              	}
              });
            }
        });

           
	}
};


// 处理审核操作
function ToAudit(transferId, checkStatus) {
    var o = {
        applyId: transferId,
        opType: checkStatus,
        reviewType: 4, // 项目转让审核
        message: $("#checkIdea").val()
    };

    $.AjaxColynJson('/review/json/ReviewCheckData.json?' + getParam(o), function (data) {
        if (data.success) {
            Colyn.log("审核成功");
        } else {
            Colyn.log(data.message);
        }

        MyAction.Init();
        $("#LoanRedeemCheckGrid").datagrid("clearSelections");
    });
}

function createSearchParam() {
	var form = {
        reviewType : 4, // 转让初审
		loanNo : $("#loanNo").val(),
		loanUser : $("#loanUser").val(),
		transferMoneyFrom : $("#transferMoneyFrom").val(),
		transferMoneyTo : $("#transferMoneyTo").val(),
		createTimeFrom : $("#createTimeFrom").datebox("getValue"),
		createTimeTo : $("#createTimeTo").datebox("getValue"),
		loanCategory : $("#loanCategory").val(),
		reviewState : $("#reviewState").val()
	};

    if ($(".pagination-num").val()) {
        form.page = $(".pagination-num").val();
    }

    if ($(".pagination-page-list").val()) {
        form.rows = $(".pagination-page-list").val();
    }

	return getParam(form);
}
