/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-20 
-------------------------------------------------*/
$(function() {
	MyGrid.Resize();
	MyAction.Init();
	$("#LookUp").click(MyAction.LookUpssss);
	$("#Export").click(MyAction.Export);
	$("#searEmp").click(MyAction.Search);
	$("#ComFull").click(MyAction.ComFull);
	$("#Edit").click(MyAction.Edit);
	$(window).resize(function() {
		MyGrid.RefreshPanl();
	});
});

var MyAction = {
	Init : function() {
		$("#SchNormalGrid").datagrid({
			method : "Post",
			url : "/account/json/UserAccountList.json?&t="+new Date() + createSearchParam(),
			height : $(window).height() - 52,
			pageSize : 10,
			fitColumns : false,
			rownumbers : true,
			nowrap : false,
			striped : true,
			remoteSort : true,
			idField : "act_account_id",
			view : myview,// 重写当没有数据时
			emptyMsg : '没有找到数据',// 返回数据字符
			columns : [ [ { field: 'act_user_id', title: "平台账号", width: 150, align: "center" },
            {
                field: "act_user_name", title: "用户名", width: 150, align: "center", formatter: function (value, rowData, index) {
                    return CutString(value, 6);
                }
            },
           
            { field: "act_user_type", title: "用户类型", width: 150, align: "center" },
            { field: "act_account_no", title: "开户账户", width: 150, align: "center" },
            { field: "act_user_phone", title: "手机号码", width: 150, align: "center" },
            { field: "account_user_card", title: "身份号码", width: 200, align: "center" },
            { field: "account_user_email", title: "邮箱", width: 200, align: "center" },
            ] ],
			pagination : true,
			singleSelect : true
		})
		var p = $('#SchNormalGrid').datagrid('getPager');
		$(p).pagination({
			pageSize : 10,
			pageList : [ 5, 10, 15, 20, 30, 50, 100 ],
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onBeforeRefresh : function() {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			}
		});
	},
	// 查看
	LookUpssss : function() {
		var row = MyGrid.selectRow();
		var num = row;
		if (num == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		var dialogs = $.hDialog({
			href : "/projectmanage/LoanProjectLookUpView?loanId=" + row.loanId + "&loanFileUrl=" + row.loanFileUrl,
			iconCls : 'icon-zoom',
			title : "项目跟踪",
			width : $(window).width() - 40,
			height : $(window).height() - 50,
			buttons : [ {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					dialogs.dialog("close");
				}
			} ]
		});
	},
	Export : function() {
		if ($('#SchNormalGrid').datagrid('getRows').length > 0) {
			var param = createParam3("SearchForm");
			window.location.href = '/projectmanage/json/ExportBussinessExamTrackList.json?'
					+ getParam(param);
		} else {
			Colyn.log("暂无数据");
		}
	},
	Recall : function() {
		alert("已撤回");
	}, 
	Edit: function () {
		var row = MyGrid.selectRow();
        if (row) {
            var editDialog = $.hDialog({
                href: "/Html/account/userAccount.vm?" + Math.random(),
                width: 500,
                height: 600,
                iconCls: 'icon-add',
                title: "用户修改",
                onLoad: function () {
                    MyAction.InitCtrl(row.menuId);
                    $("#men_Id").val(row.menuId);
                    $("#men_Name").val(row.menuName);
                    $("#men_URL").val(row.menuUrl);
                    $("#men_SmallIcon").val(row.iconCls);
                    $("#showSmallIcon").removeClass("icon showIcon icon-note").addClass("icon showIcon " + row.iconCls);
                    $('#men_Sort').numberbox().numberbox('setValue', row.menuSort);
                    $("#men_IsVisible").val(row.men_IsVisible);
                    $('#men_ParentID').combotree('setValue', row.parentId);

                    $('#men_BigIcon').val(row.bigIcon);
                    $("#showBigIcon").css('background', "url('')").css("cursor", "pointer");
                    $("#showBigIcon").removeClass().html("<img src='/Images/icon/iconBig/" + row.bigIcon + "' border='0' width='16' height='16' />")
                },
                submit: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        $.AjaxColynJson('/system/json/editMenu.json', param, function (data) {
                            if (data.success) {
                                Colyn.log("修改成功！")
                                MyTreeGrid.Init();
                            }
                            else {
                                Colyn.log(data.message);
                            }
                            editDialog.dialog('close');
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
        var loanStatus = $(".SearchForm select[name='loanStatus']").val() ;
        if(loanStatus == "-1" && !param.searchForm){
        	if(!param.searchForm){
        		
        		param.searchForm =JSON.stringify( { Items:[{ Field:"loanStatus",Value:-1 }] })
        	}
        	
        }
        else{
        	var arr = JSON.parse(param.searchForm).Items;
        	if(loanStatus == "-1"){
        		arr.push({ Field:"loanStatus",Value:"-1"  })
        	}
        	for(var item in arr){
        		if(arr[item].Field == "loanStatus"){
        			arr[item].Value = loanStatus;
        		}
        	}
        	param.searchForm = JSON.stringify({ Items:arr })
        }
       
        var o = { modelAction: "Search" };
        $.post("/account/json/UserAccountList.json?" + getParam(o), param, function (data) {
            $("#SchNormalGrid").datagrid("loadData", data);
        }, "json");
    },
	
	// 【强制满标】
	ComFull : function() {
		var row = MyGrid.selectRow();
		if (row == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		var loan_id = row.loanId;// 项目申请表主键
		var param = {
			loanId : loan_id
		};
		$.AjaxColynJson("/projectmanage/json/ProjectComFull.json", param, function(data) {
			if (data.success) {
				Colyn.log("满标成功！");
			} else {
				Colyn.log(data.message);
			}
			MyAction.Init();
		});
	}
};



function createSearchParam() {
    var form = {
    	act_user_name : $("#act_user_name").val(),
    	act_account_no:$("#act_account_no").val(),
    	act_user_phone : $("#act_user_phone").val(),
    	act_user_type :$("#act_user_type").val()
    };
    if ($(".pagination-num").val()) {
        form.page = $(".pagination-num").val();
    }

    if ($(".pagination-page-list").val()) {
        form.rows = $(".pagination-page-list").val();
    }

    return getParam(form);
}
