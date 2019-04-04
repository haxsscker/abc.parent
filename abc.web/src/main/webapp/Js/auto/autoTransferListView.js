$(function () {
	MyGrid.Resize();
    $("#Add").click(MyAction.Add);
    $("#Audit").click(MyAction.Audit);
    $("#SearchBtn").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});
var MyAction = {
    Init: function () {
        $("#FriendLinkGrid").datagrid({
            method: "Post",
            url: "/autotransfer/json/ActionAutoTransfer.json",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview, //重写当没有数据时
            emptyMsg: '没有找到数据', //返回数据字符
            columns: [
            [
             { field: "payAccotunt", title: "付款人账户", width: 150, halign:"center", align: "center" },
             { field: "payRealName", title: "付款人姓名", width: 150, halign:"center", align: "center" },
             { field: "payUserName", title: "付款人用户名", width: 150, halign:"center", align: "center" },
             { field: "receibeAccotunt", title: "收款人账户", width: 150, halign:"center", align: "center" },
 			{ field: "receibeUser", title: "收款人姓名", width: 150, halign:"center", align: "center" },
 			{ field: "receiveUsername", title: "收款人用户名", width: 150, halign:"center", align: "center" },
            { field: "moneyAmount", title: "交易金钱数", width: 150, halign:"center", align: "center" },
			{ field: "operateDate", title: "交易创建日期", width: 200, halign:"center", align: "center" },
			{ field: "operator", title: "交易操作人", width: 200, halign:"center", align: "center" },
			{ field: "auditState", title: "审核状态", width: 200, halign:"center", align: "center" },
			{ field: "atRemarks", title: "审核意见", width: 200, halign:"center", align: "center" },
			//{ field: "moneyType", title: "金额类型", width: 250, halign:"center", align: "center" }
			{ field: "state", title: "交易状态", width: 200, halign:"center", align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#FriendLinkGrid').datagrid('getPager');
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
    Add: function () {
       var dss = $.hDialog({
            href: "/autotransfer/autoTransferAddView",
            iconCls: 'icon-add',
            title: "添加自主转账",
            // maximizable: true,//显示最大化
            width: 400,//$(window).width() - 50,
            height: 350,//$(window).height() - 50,
            onLoad: function () {
            	 $(".evt-Borrower").die("click", MyAction.chooseBorrower);
            	//选择借款人
                $(".evt-Borrower").live("click", MyAction.chooseBorrower);
            },
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = $("#Colyn").serializeArray();
                        if ($("#Colyn").form("validate")) {
                            $.AjaxColynJson("/autotransfer/json/AutoTransferAddOrEdit.json", param, function (data) {
                                if (data.success) {
                                    Colyn.log(data.message);
                                    MyAction.Init();
                                    dss.dialog('close');
                                } else {
                                	dss.dialog('close');
                                    Colyn.log(data.message);
                                }
                            }, "json");
                        }
                    }
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dss.dialog("close");
                }
            }]
        })

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
        if (row.auditState != "待审核") {
            Colyn.log("自主转账已审核");
            return;
        }
        var o = { creditId: row.cre_apply_id, userId: row.cre_user_id };
        var Dialog = $.hDialog({
            href: "/autotransfer/autoTransferCheckView?"+ getParam(o),
            iconCls: 'icon-add',
            title: "自主转账审批",
            maximizable: true,//显示最大化
            width: $(window).width() - 400,
            height: $(window).height() - 300,
            buttons: [{
                text: '同意',
                iconCls: 'icon-user_magnify',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                        var o = {
                            creId: row.id,
                            opType: 1,
                            reviewType: 20,
                            message: $("#checkIdear").val()
                        };
                        if ($("#checkIdear").val() == "请输入...")
                        {
                            Colyn.log("请输入审核意见！");
                            return;
                        }
                        $.AjaxColynJson("/autotransfer/json/AutoTransferCheck.json?" + getParam(o), function (data) {
                            if (data.success) {
                                Colyn.log("审核成功！");
                                MyAction.Init();
                                $('#CreditReviewListGrid').datagrid('clearSelections');
                                Dialog.dialog("close");
                            }
                            else {
                                Colyn.log(data.message);
                            } 
                        });
                    }
                   
                }
            }, {
                text: '不同意',
                iconCls: 'icon-user_magnify',
                handler: function () {
                        var o = {
                            creId: row.id,
                            opType: 2,
                            reviewType: 20,
                            message: $("#checkIdear").val()
                        };
                        if ($("#checkIdear").val() == "请输入...") {
                            Colyn.log("请输入审核意见！");
                            return;
                        }
                        $.AjaxColynJson("/autotransfer/json/AutoTransferCheck.json?" + getParam(o), function (data) {
                            if (data.success) {
                                Colyn.log("审核成功！");
                                MyAction.Init();
                                $('#CreditReviewListGrid').datagrid('clearSelections');
                                Dialog.dialog("close");
                            }
                            else {
                                Colyn.log(data.message);
                            }

                        });
                    
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
    chooseBorrower: function () {
    	var duty = $(this).attr("_duty");
        //var category = $(this).attr("_category");
    	var loanType = 1;
         dialog = $.hDialog({
             title: '转入人信息',
             width: 800,
             height: 400,
             cache: false,
             href: '/user/userListView?duty=' + duty +'&loanType='+loanType,
             modal: true,
             onClose: function () {

             }, buttons: [{
                 text: '确认选择',
                 iconCls: 'icon-ok',
                 handler: function () {
                     MyActionForUser.chooseUser();
                 }
             }, {
                 text: '关闭',
                 iconCls: 'icon-cancel',
                 handler: function () {
                     dialog.dialog("close");
                 }
             }]

         });
     },
     //搜索
     Search: function(){
    	 param = createSearchParam();
    	 console.info(param);
         $.post("/autotransfer/json/ActionAutoTransfer.json", param, function (data) {
             //$('#FriendLinkGrid').datagrid('loadData', { total: 0, rows: [] });
             $("#FriendLinkGrid").datagrid("loadData", data);
         }, 'json');
     }
    
}

function createSearchParam() {
    var form = {
    	atPayAccotunt : $("#atPayAccotunt").val(),
    	atReceibeAccotunt : $("#atReceibeAccotunt").val(),
    	atAuditState : $("#atAuditState").val(),
    };

    if ($(".pagination-num").val()) {
        form.page = $(".pagination-num").val();
    }

    if ($(".pagination-page-list").val()) {
        form.rows = $(".pagination-page-list").val();
    }

    return getParam(form);
}
