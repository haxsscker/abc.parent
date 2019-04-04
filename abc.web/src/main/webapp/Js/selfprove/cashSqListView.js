/*------------------------------------------------
 * Author:潘健  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#Enable").click(MyAction.Enable);
    $("#Del").click(MyAction.Del);
    $("#LookUp").click(MyAction.LookUp);
    $("#LockStop").click(MyAction.LockStop);
    $("#btnSearch").click(MyAction.Search);
    $("#Edit").click(MyAction.Edit);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#AccountManagementListGrid").datagrid({
            //method: "GET",
            url: "/cashsq/json/CashSqListView.json",
            height: $(window).height() - 15,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "userId", title: "用户ID", width: 100, align: "center" },
            { field: "sqtimestr", title: "申请时间", width: 100, align: "center" },
            { field: "userCashQuotaSqadd", title: "申请额度", width: 100, align: "center"},
            {
                field: "state", title: "状态", width: 60, align: "center",formatter:
                function (value)
                {
                    if (value == "1")
                        return "待审核";
                    else if (value == "2")
                        return "通过";
                    else if (value == "3")
                        return "不通过";
                    else
                        return "-";
                }} 
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#AccountManagementListGrid').datagrid('getPager');
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
    Edit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
//        if (row.cre_check_state == "1" || row.cre_check_state == "2") {
        if (row.state != 1) {
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
                            creId: row.id,
                            userId: row.user_id,
                            opType: 2,
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
                       // if (checkMoney > 10000)
                       // {
                       //     Colyn.log("审核额度不可大于一万！");
                       //     return;
                       // }

                        if ($("#cre_check_idear").val() == "请输入...")
                        {
                            Colyn.log("请输入审核意见！");
                            return;
                        }
                        
                        $.post("/credit/json/cashSqCreditApply.json", o, function (data) {
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
                            Id: row.id,
                            userId: row.user_id,
                            opType: 3,
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
                        if (checkMoney > 10000) {
                            Colyn.log("审核额度不可大于一万！");
                            return;
                        }

                        if ($("#cre_check_idear").val() == "请输入...") {
                            Colyn.log("请输入审核意见！");
                            return;
                        }
                        
                        $.post("/credit/json/cashSqCreditApply.json", o, function (data) {
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

     
    //搜索
    Search: function () {
    	
    	/*  var param = createSearchParam();
          $.post("/selfprove/json/AccountManagementListView.json?", param, function (data) {
        	  $("#AccountManagementListGrid").datagrid("loadData", data);
          }, 'json');
          */
       var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.AjaxColynJson("/selfprove/json/AccountManagementListView.json?"+ getParam(o) ,param, function (data) {
            $("#AccountManagementListGrid").datagrid("loadData", data);
            },JSON)
    },
    //启用
    Enable: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        else {
        	if(row.userState != "1"){
        	$.messager.confirm("启用", "确定要启用该客户？", function (r) {
        	if(r){	
            $.AjaxColynJson("/selfprove/json/AccountManagementEnableData.json?cinId=" + row.userId, function (data) {
                if (data.success) {
                    Colyn.log("启用成功");
                }
                else {
                    Colyn.log(data.message);
                }
                MyAction.Init();
            })
        	}
        	})
        	}else {
                Colyn.log("已启用，不可重复操作");
            }
        }
    },

    //停用
    LockStop: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        else {
        	if(row.userState != "0"){
        	$.messager.confirm("停用", "确定要停用该客户？", function (r) {
        	if(r){
            $.AjaxColynJson("/selfprove/json/AccountManagementLockStopData.json?cinId=" + row.userId , function (data) {
                if (data.success) {
                    Colyn.log("停用成功");
                }
                else {
                    Colyn.log(data.message);
                }
                MyAction.Init();
            })
        		}
        	})
        	}else {
                Colyn.log("已停用，不可重复操作");
            }
        }
    },

    //删除
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("删除内容", "确认要删除所选记录？", function (r) {
                if (r) {
                    $.AjaxColynJson("/selfprove/json/AccountManagementDelData.json?cinId=" + row.userId, function (data) {
                        if (data.success) {
                            Colyn.log("删除成功！");
                        }
                        else {
                            Colyn.log("删除失败！");
                        }
                        MyAction.Init();
                    })
                }
            })
        }
        else {
            Colyn.log("请选择内容删除！");
        }
    },
    //查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        if (row) {
            window.location.href = "/selfprove/AccountManagementLookUpView?MenuName=客户信息查看&cinId="+ row.userId;
        }
        else {
            Colyn.log("请选择内容查看！");
        }
    }
}
//格式化时间
function creatStringDate(t){
	var d=new Date(t);
	var year=d.getFullYear();
	var day=d.getDate();
	var month=+d.getMonth()+1;
	
	var f=year+"-"+formate(month)+"-"+formate(day);
	return f;
	}
	function formate(d){
	return d>9?d:'0'+d;
	}
	
	
	
	function createSearchParam() {
	    var form = {
	        userName : $("#userName").val(),
	        userRealName : $("#userRealName").val(),
	        userRecommendUserid : $("#userRecommendUserid").val(),
	        userState : $("#userState").val()
	    };

	    if ($(".pagination-num").val()) {
	        form.page = $(".pagination-num").val();
	    }
	    if ($(".pagination-page-list").val()) {
	        form.rows = $(".pagination-page-list").val();
	    }

	    return getParam(form);
	}
