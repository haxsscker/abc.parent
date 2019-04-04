/*------------------------------------------------
 * Author:李冬冬  Date：2014-8-18 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Recall").click(MyAction.Recall);
    $("#btnSearch").click(MyAction.Search);
    $("#Audit").click(MyAction.Audit);

    $("#CheckIdear").click(MyAction.CheckIdear);
    //$(".evt-Borrower").click(MyAction.chooseBorrower);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var getData = function (page, rows) {
    $.ajax({
        type: "POST",
        url: "/moneyManage/json/RefundApplyListCheck.json?reviewType=2&pro_check_state=" + $("#ProStatus").val(),
        data: "page=" + page + "&rows=" + rows,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
            $.messager.progress('close');
        },
        success: function (data) {
            alert(textStatus);
            $('#RefundApplyCheckGrid').datagrid('loadData', data);

        }
    });
};

var MyAction = {
    Init: function () {
        var o = { pro_check_state:  $("#ProStatus").val() };
        $("#RefundApplyCheckGrid").datagrid({
            method: "GET",
            url: "/moneyManage/json/RefundApplyListCheck.json?reviewType=2&" + getParam(o),
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            idField: "refundId",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
                { field: "userName", title: "收款人", width: 100, align: "center" },
                { field: "accountNo", title: "收款人帐号", width: 120, align: "center" },
                { field: "userPhone", title: "收款人手机号", width: 120, align: "center" },
                {
                    field: "refundMoney", title: "退款金额", width: 120, align: "center",
                    formatter: function (v, r, i) {
                        return formatMoney(v, '￥');
                    }
                },
                //{field: "refundReason", title: "退款原因", width: 150, align: "center",},
                {field: "refundState", title: "退款状态", width: 120, align: "center",},
                {field: "reviewState", title: "审核状态", width: 140, align: "center",},
                {field: "applyDate", title: "申请日期", width: 200, align: "center",},
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#RefundApplyCheckGrid').datagrid('getPager');
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
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (row.canCheck == false) {
            Colyn.log("此项目已审核，不可再次审核");
            return;
        }
        var o = { refundId: row.refundId };
        var Dialog = $.hDialog({
            href: "/moneyManage/RefundApplyInfoReviewLookUpView?" + getParam(o),
            iconCls: 'icon-add',
            title: "退款审核",
            maximizable: false, //显示最大化
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
	                            ToAudit(row.refundId, 1);
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
                text: '否决',
                iconCls: 'icon-user_magnify',
                handler: function () {
                	if($("#Colyn").form('validate')){
	                   if ($("#check_idear").val() != "") {
	                       if ($('#Colyn').form('validate')) {
	                           ToAudit(row.refundId, 2);
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
        })
    },
    // 查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = { refundId: row.refundId };
        var Dialog = $.hDialog({
            href: "/moneyManage/RefundApplyInfoLookUpView?" + getParam(o),
            iconCls: 'icon-add',
            title: "退款信息查看",
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
        var param = createSearchParam();
        console.log(param);
        $.post("/moneyManage/json/RefundApplyListSearch.json?reviewType=2&", param, function (data) {
            $('#LoanCheckGrid').datagrid('loadData', { total: 0, rows: [] });
            $("#LoanCheckGrid").datagrid("loadData", data)
        }, 'json');
    }
};

// 处理审核操作
function ToAudit(applyId, checkStatus) {
    var o = {
    	applyId: applyId,
        opType: checkStatus,
        message: $("#check_idear").val(),
        reviewType: 9      // 项目初审的reviewType
    };
    $.post("/review/json/ReviewCheckData.json", o, function (data) {
        if (data.success) {
            Colyn.log("审核成功！");
        } else {
            Colyn.log(data.message);
        }
        MyAction.Init();
        $('#LoanCheckGrid').datagrid('clearSelections');
    }, 'Json');
}

function createSearchParam() {
    var form = {
        loanNo : $("#showNo").val(),
        loanUser : $("#showName").val(),
        //showGuaranteeGov : $("#showGuaranteeGov").val(),
        loanState : $("#ProStatus").val(),
        loanCategory : $("#productType").val(),
        loanCreatetimeFrom : $("#addDateFrom").datebox("getValue"),
        loanCreatetimeTo : $("#addDateTo").datebox("getValue"),
        loanMoneyFrom : $("#loanMoneyFrom").val(),
        loanMoneyTo : $("#loanMoneyTo").val()
    };

    return getParam(form);
}
