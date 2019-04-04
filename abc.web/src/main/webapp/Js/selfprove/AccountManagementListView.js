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
    $("#CashQuotaApply").click(MyAction.CashQuotaApply);
    $("#Export").click(MyAction.Export);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#AccountManagementListGrid").datagrid({
            //method: "GET",
            url: "/selfprove/json/AccountManagementListView.json",
            height: $(window).height() - 15,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
           // remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "userId", title: "用户ID", width: 100, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "userName", title: "客户名称", width: 100, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "userRealName", title: "真实姓名", width: 100, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            {
                field: "userSex", title: "性别", width: 60, align: "center",formatter:
                function (value)
                {
                    if (value == "0")
                        return "女";
                    else if (value == "1")
                        return "男";
                    else
                        return "-";
                }
            },
            { field: "userDocType", title: "证件类型", width: 100, align: "center", formatter: function (value) { if (!value) return "-"; else return value;}},
            { field: "userDocNo", title: "证件号码", width: 180, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "userPhone", title: "手机号码", width: 180, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "userEmail", title: "邮箱", width: 180, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "userCashQuota", title: "免费提现额度", width: 180, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },           
            {
                field: "userNative", title: "所在地", width: 190, align: "center", formatter: function (value, rowData, index) {
                    if (!value)
                        return "-";
                    return CutString(value, 10);
                }
            },
            { field: "recommendUserRealName", title: "推荐人名称", width: 80, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "recommendUserName", title: "推荐人用户名", width: 80, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "userRegisterDate", title: "注册日期", width: 150, align: "center", formatter: function (value) { if (value ) { return creatStringDate(value);}else return "-"}},
            { field: "userState", title: "状态", width: 80, align: "center" ,formatter:function(value){if(value == "1"){return "启用"}else if(value == "0")return "停用"; else return "-"}}
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
    Edit: function () {
		var row = MyGrid.selectRow();
        if (row) {
            var editDialog = $.hDialog({
                href: "/Html/account/userAccount.vm?" + Math.random(),
                width: 500,
                height: 400,
                iconCls: 'icon-add',
                title: "用户修改",
                onLoad: function () {
                    $("#act_user_id").val(row.userId);
                    $("#act_user_name").val(row.userRealName);
                    $("#act_user_card").val(row.userDocNo);
                    $("#act_user_phone").val(row.userPhone);
                    $("#act_user_email").val(row.userEmail);
                    $("#act_user_cash").val(row.userCashQuota);
                },
                submit: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        $.AjaxColynJson('/account/editAccount.json', param, function (data) {
                            if (data.success) {
                                Colyn.log("修改成功！");
                            }
                            else {
                                Colyn.log(data.message);
                            }
                            editDialog.dialog('close');
                            MyAction.Init();
                        });
                    }
                }
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    //搜索
    Search: function () {
    	
       var param = createParam3("SearchForm");
       console.log(param);
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
    },
    Export: function () {
    	$('#searchForm').attr("action","/reportAnalysis/json/accountManagementReportExcel.json");
		$('#searchForm').submit();
    	
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
