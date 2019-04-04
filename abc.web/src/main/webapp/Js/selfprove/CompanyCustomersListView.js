/*------------------------------------------------
 * Author:潘健  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);    
    $("#Enable").click(MyAction.Enable);
    $("#Del").click(MyAction.Del);
    $("#LookUp").click(MyAction.LookUp);
    $("#LockStop").click(MyAction.LockStop);
    $("#HiddenSearch").click(MyAction.Search);
    $("#Export").click(MyAction.Export);
    $("#CashQuotaApply").click(MyAction.CashQuotaApply);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#CompanyCustomersListGrid").datagrid({
            method: "GET",
            url: "/selfprove/json/CompanyCustomersListView.json",
            height: $(window).height() - 15,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,// 重写当没有数据时
            emptyMsg: '没有找到数据',// 返回数据字符
            columns: [[
            { field: "ccCompanyName", title: "公司名称", width: 270, align: "center" },
            { field: "ccCorporate", title: "法定代表人", width: 120, align: "center" },
            { field: "ccRegisterCapital", title: "注册资金", width: 150, align: "center" },
            { field: "userCashQuota", title: "免费提现额度", width: 150, align: "center" },
            { field: "userName", title: "平台用户名", width: 120, align: "center" },
            { field: "ccContactName", title: "联系人", width: 100, align: "center" },
            { field: "ccContactPhone", title: "联系手机号", width: 120, align: "center" },
//            { field: "referees", title: "推荐人", width: 100, align: "center" },
            { field: "ccRegisterDateStr", title: "注册日期", width: 120, align: "center" },
            { field: "userState", title: "状态", width: 100, align: "center", formatter: function (value) { if (value == "1") return "启用"; else if (value == "0") return "停用";else return"-"} },
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#CompanyCustomersListGrid').datagrid('getPager');
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
    // 添加
    Add: function(){
         var addDialog = $.hDialog({
             href: "/selfprove/CommpanyBaseInfoAddView",
             iconCls: 'icon-add',
             title: "添加公司客户",
             width: $(window).width() - 40,
             height: $(window).height() - 50,
             buttons: [
                 {
                     text: '确认添加',
                     iconCls: 'icon-ok',
                     handler: function () {
                         if ($('#Colyn').form('validate')) {
                        	 var param = $("#Colyn").serializeArray();
                             $.AjaxColynText("/selfprove/SaveCommpanyBaseInfo.json", param, function (data) {
                                 var data = JSON.parse(data);
                                 if (data.success) {
                                     Colyn.log("添加成功！");
                                     MyAction.Init();
                                     addDialog.dialog("close");
                                 }
                                 else {
                                     Colyn.log(data.message);
                                 }
                             });
                         }
                     }
                 },
                 {
                     text: '关闭',
                     iconCls: 'icon-cancel',
                     handler: function () {
                         addDialog.dialog("close");
                     }
                 }
             ]
         })
    },
    //编辑
    Edit: function () {
		var row = MyGrid.selectRow();
        if (row) {
            var editDialog = $.hDialog({
                href: "/selfprove/CommpanyBaseInfoAddView?cinId="+row.ccId,
                width: $(window).width() - 40,
             	height: $(window).height() - 50,
                iconCls: 'icon-add',
                title: "公司客户修改",
                onLoad: function () {
                	//初始化企业性质
                	$("#ccCompanyType").val($("#ccCompanyType").attr("_select"));
                    $("#ccCompanyType").children('option:selected').val();
                	//初始化企业规模
                	$("#ccCompanyScale").val($("#ccCompanyScale").attr("_select"));
                    $("#ccCompanyScale").children('option:selected').val();
                	//初始化证件类型
                    $("#ccDocType").val($("#ccDocType").attr("_select"));
                    $("#ccDocType").children('option:selected').val();
                    MyValid.Init();
                },
                buttons: [
                 {
                     text: '保存编辑',
                     iconCls: 'icon-ok',
                     handler: function () {
                         if ($('#Colyn').form('validate')) {
                        	 var param = $("#Colyn").serializeArray();
                             $.AjaxColynText("/selfprove/EditCommpanyBaseInfo.json", param, function (data) {
                                 var data = JSON.parse(data);
                                 if (data.success) {
                                     Colyn.log("编辑成功！");
                                     MyAction.Init();
                                     editDialog.dialog("close");
                                 }
                                 else {
                                     Colyn.log(data.message);
                                 }
                             });
                         }
                     }
                 },
                 {
                     text: '关闭',
                     iconCls: 'icon-cancel',
                     handler: function () {
                         editDialog.dialog("close");
                     }
                 }
             ]
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    // 查找
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.AjaxColynJson("/selfprove/json/CompanyCustomersListView.json?=" + getParam(o), param, function (data) {
            $("#CompanyCustomersListGrid").datagrid("loadData", data);
        },JSON)
    },
    // 停用
    LockStop: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        else {
        	if(row.userState != "0"){
        	$.messager.confirm("停用", "确定要停用该机构？", function (r) {
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
    // 启用
    Enable: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        else {
        	if(row.userState != "1"){
        	$.messager.confirm("启用", "确定要启用该机构？", function (r) {
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
    Export: function () {
    	$('#searchForm1').attr("action","/reportAnalysis/json/companyCustomerReportExcel.json");
		$('#searchForm1').submit();
    	
    },
    // 删除
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r) {
                    $.AjaxColynJson("/selfprove/json/CompanyCustomersDelData.json?cinId=" + row.userId, function (data) {
                        if (data.success) {
                            Colyn.log("删除成功！");
                        }
                        else {
                            Colyn.log(data.Msg);
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
    // 查看
    // LookUp: function () {
    // var row = MyGrid.selectRow();
    // if (row!=null) {
    // //window.location.href =
	// "/P2PSelfCertification/CommpanyBaseInfoView?MenuName=客户信息查看";
    // var Dialog = $.hDialog({
    // href: "/selfprove/CommpanyBaseInfoView?cinId=" + row.cst_user_id ,
    // iconCls: 'icon-add',
    // title: "公司客户查看",
    // maximizable: true,//显示最大化
    // width: $(window).width() - 40,
    // height: $(window).height() - 50,
    // buttons: [{
    // text: '关闭',
    // iconCls: 'icon-cancel',
    // handler: function () {
    // Dialog.dialog("close");
    // }
    // }]
    // })
    // }
    // else {
    // Colyn.log("请选择内容查看！");
    // }
    // },

    LookUp: function () {
    var row = MyGrid.selectRow();
    var num = row;
    if (num == null) {
        Colyn.log("请选择一条记录进行操作");
        return;
    }
    var Dialog = $.hDialog({
        href: "/selfprove/CommpanyBaseInfoView?cinId=" + row.ccId,
        iconCls: 'icon-add',
        title: "公司客户查看",
        width: $(window).width() - 40,
        height: $(window).height() - 50,
        // onLoad: function () {
        // MyAction.GetTemplate(row.gsc_Id, row.gte_Id);
        // },
        buttons: [{
            text: '关闭',
            iconCls: 'icon-cancel',
            handler: function () {
                Dialog.dialog('close');
            }
        }]
    })
},
//免费提现额度申请
CashQuotaApply: function(){
	var row = MyGrid.selectRow();
	if(row==null){
		Colyn.log("请先选择一个用户！");
		return;
	}
	var applyDialog = $.hDialog({
        href: "/Html/account/cashQuotaApply.vm?" + Math.random(),
        width: 500,
        height: 400,
        iconCls: 'icon-add',
        title: "免费提现额度申请",
        onLoad: function () {
            $("#act_user_id").val(row.userId);
            $("#act_user_name").val(row.userName);
            console.log(row.userCashQuota);
            if(typeof(row.userCashQuota)=='undefined'){
            	$("#act_user_cash").val('0');
            }else {
            	$("#act_user_cash").val(row.userCashQuota);
            }
        },
        submit: function () {
        	if(!$('#applyForm').form('validate')){
        		return;
        	}
            $.ajax({
            	url: '/selfprove/json/CashQuotaApply.json',
            	data: $("#applyForm").serialize(),
            	success: function(result){
            		if(result.success){
            			Colyn.log('提交申请成功，等待财务审核！');
            		}else {
            			colyn.log(result.message);
            		}
            		applyDialog.dialog('close');
            	}
            });
        }
    })
}// 免费提现额度申请end
}