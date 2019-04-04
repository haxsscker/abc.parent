
$(function () {
    MyGrid.Resize();
    $("#Audit").click(MyAction.Audit);
    $("#LookUp").click(MyAction.LookUp);
    $("#btnSearch").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var getData = function (page, rows) {
    $.ajax({
        type: "POST",
        url: "/recharge/json/DownLineRechargeListCheckView.json?" + createSearchParam(),
        data: "page=" + page + "&rows=" + rows,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.progress('close');
        },
        success: function (rows) {
            $('#RechargeGrid').datagrid('loadData', rows);
        }
    });
};
var MyAction = {
    //列表
    Init: function () {
        $("#RechargeGrid").datagrid({
            method: "Post",
            url: "/recharge/json/DownLineRechargeListCheckView.json?" + createSearchParam(),
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview, //重写当没有数据时
            emptyMsg: '没有找到数据', //返回数据字符
            animate: true,
            collapsible: false,
            columns: [
                [
                    { field: "userRealName", title: "充值人", width: 100, align: "center" },
                    { field: "downRechargeMoney", title: "充值金额", width: 80, align: "right", formatter: function (v, r, i) { return formatMoney(v, "￥"); } },
                    { field: "bankNo", title: "充值账户", width: 120, align: "center" },
                    { field: "drMark", title: "备注", width: 120, align: "center" },
	                    { field: "downRechargeDate", title: "充值日期", width: 80, align: "center", formatter: function (v, r, i) {
	                    	if (v != null && v!="") {
	                    		return v;
	                    	} else {
	                    		return "-";
	                    	}
	                    }
                    },
                    {
                        field: "downRechargeCheckState", title: "审核状态", width: 80, align: "center", formatter: function (v, r, i) {
                            var ve = "-";
                            switch (v) {
                                case 0:
                                    ve = "待审核";
                                    break;
                                case 1:
                                    ve = "审核已通过";
                                    break;
                                case 2:
                                    ve = "审核未通过";
                                    break;
                            }
                            return ve;
                        }
                    },
                    {
                        field: "downRechargeCheckDate", title: "审核日期", width: 80, align: "center", formatter: function (v, r, i) {
                            if (v != null&& v!="") {
                                return v;
                            } else {
                                return "-";
                            }
                        }
                    },
                    {
                        field: "downRechargeCheckIdear", title: "审核意见", width: 120, align: "center", formatter: function (v, r, i) {
                            if (v != null&& v!="") {
                                return CutString(v, 10);
                            } else {
                                return "-";
                            }
                        }
                    }
                ]
            ],
            pagination: true,
            singleSelect: true
        });
        var p = $('#RechargeGrid').datagrid('getPager');
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
        if (row.canCheck) {
            var downRechargeId = row.downRechargeId;
            var Dialog = $.hDialog({
                href: "/recharge/downLineCheckView?downRechargeId=" + downRechargeId,
                iconCls: 'icon-add',
                title: "线下充值审核",
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
		                                ToAudit(downRechargeId, 1);
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
	                               ToAudit(downRechargeId, 2);
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
            return;
        }
    },
    //查看 
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/recharge/DownLineCheckLookUpView?downRechargeId=" +  row.downRechargeId,
            iconCls: 'icon-add',
            title: "线下充值查看",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [
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
    //搜索OK
    Search: function () {
        var param = createSearchParam();
        $.post("/recharge/json/DownLineRechargeListCheckView.json", param, function (data) {
        	$('#RechargeGrid').datagrid('loadData', {total : 0,rows : []});
			$("#RechargeGrid").datagrid("loadData", data);
        }, "json");
    }
}
//处理审核操作
function ToAudit(downRechargeId, checkStatus) {
    var o = {
        applyId: downRechargeId,
        opType: checkStatus,
        reviewType: 14, //线下充值审核
        message: $("#checkIdea").val()
    };

    $.AjaxColynJson('/review/json/ReviewCheckData.json?' + getParam(o), function (data) {
        if (data.success) {
            Colyn.log("审核成功");
        } else {
            Colyn.log(data.message);
        }
        $("#RechargeGrid").datagrid('loadData',{total:0,rows:[]});
        MyAction.Init();
        $("#RechargeGrid").datagrid("clearSelections");
    },'Json');
}
function createSearchParam() {
    var form = {
    		modelAction: "Search",
    		userRealName : $("#cst_user_name").val(),
    		rechargeMoneyFrom : $("#mon_money_from").val(),
    		rechargeMoneyTo : $("#mon_money_to").val(),
    		rechargeDateFrom : $("#mon_date_from").datebox("getValue"),
    		rechargeDateTo : $("#mon_date_to").datebox("getValue"),
    		rechargeBankNo : $("#mon_recharge_bank").val(),
    		checkStatus : $("#mon_recharge_state").val()
        };
    if ($(".pagination-num").val()) {
        form.page = $(".pagination-num").val();
    }

    if ($(".pagination-page-list").val()) {
        form.rows = $(".pagination-page-list").val();
    }

        return getParam(form);
}