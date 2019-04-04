/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-19 
-------------------------------------------------*/
$(function() {
	MyGrid.Resize();
	$("#Search").click(MyAction.Search);// 【搜索】
	$("#LookUp").click(MyAction.LookUp);// 【查看】
	//$("#Repayment").click(MyActionB.PubOpeRepayment);// 【还款】
	$("#ReplacePay").click(MyActionC.PubOpeRepayment);// 【代还】
	//$("#ComPay").click(MyActionD.PubOpeRepayment);// 【强制还款】
	$("#sendMessage").click(MyAction.SendMessage);//发送还款提醒短信
	$("#Export").click(MyAction.ExportExcel);
	MyAction.Init();// 页面初始化
	$(window).resize(function() {
		MyGrid.RefreshPanl();
	});
});

var MyAction = {
	// 页面初始化
	Init : function() {
		$("#LoanRepaymentListGrid").datagrid({
			method : "POST",// 请求远程数据的方法类型。
			url : "/loanpay/json/actionLoanPayListView.json",// 一个用以从远程站点请求数据的超链接地址。
			height : $(window).height() - 52,
			pageSize : 10,// 当设置分页属性时，初始化每页记录数。
			fitColumns : false,// 设置为true将自动使列适应表格宽度以防止出现水平滚动。
			rownumbers : true,// 设置为true将显示行数。
			nowrap : false,// 设置为true，当数据长度超出列宽时将会自动截取。
			striped : false,// 设置为true将交替显示行背景。
			idField : "pro_plan_id", // 表明该列是一个唯一列。【还款计划表主键】
			remoteSort : true,// 定义是否通过远程服务器对数据排序。
			view : myview,// 定义数据表格的视图。【重写当没有数据时】
			emptyMsg : '没有找到数据',// 返回数据字符
			columns : [ [ {
				field : "pro_loan_no",
				title : "项目名称",
				width : 270,
				align : "center",
				formatter : function(value, rowData, index) {
					return CutString(value, 19);
				}
			}, {
				field : "pdo_product_name",
				title : "项目类型",
				width : 80,
				align : "center",
				formatter : function(value, rowData, index) {
					if (rowData.pdo_product_name == "1") {
						return value = "信用贷";
					} else if (rowData.pdo_product_name == "2") {
						return value = "抵押贷";
					} else if (rowData.pdo_product_name == "3") {
						return value = "担保贷";
					} else if (rowData.pdo_product_name == "4") {
						return value = "综合贷";
					}
				}
			}, {
				field : "pro_add_emp",
				title : "借款人",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return CutString(value, 12);
				}
			}, {
				field : "pro_loan_money",
				title : "借款金额(元)",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatMoney(value, "￥");
				}
			}, {
				field : "pro_loan_rate",
				title : "年化收益率",
				width : 80,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatPercent(value);
				}
			}, {
				field : "pro_borrowing_period",
				title : "借款期限",
				width : 80,
				align : "center"
			},  {
				field : "pro_borrowing_period_unit",
				title : "期限类型",
				width : 30,
				align : "center",
				formatter : function(value, rowData, index) {
					if(value=='1'){return '年'}
					else if(value=='2'){return '月'}
					else if(value=='3'){return '日'}
				}
			}, {
				field : "pro_loan_period",
				title : "期数",
				width : 80,
				align : "center",
				formatter : function(value, rowData, index) {
					return value+"期";
				}
			}, {
				field : "pro_pay_date_str",
				title : "应还日期",
				width : 80,
				align : "center"

			}, {
				field : "pro_overdue_days",
				title : "逾期天数",
				width : 80,
				align : "center",
				formatter : function(value, rowData, index) {
					if (rowData.pro_is_clear == "是") {
						return value = "0";
					} else {
						return value == null ? "-" : (value < 0 ? 0 : value);
					}
				}
			}, {
				field : "pro_pay_money",
				title : "本期应还本金",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatMoney(value, "￥");
				}
			}, {
				field : "pro_pay_rate",
				title : "本期应还利息",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatMoney(value, "￥");
				}
			}, {
				field : "pro_service_fee",
				title : "本期应还服务费",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatMoney(value, "￥");
				}
			}, {
				field : "pro_collect_money",
				title : "本期已还本金",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatMoney(value, "￥");
				}
			}, {
				field : "pro_collect_rate",
				title : "本期已还利息",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatMoney(value, "￥");
				}
			}, {
				field : "collect_service_fee",
				title : "本期已还服务费",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatMoney(value, "￥");
				}
			},{
				field : "pro_pay_type",
				title : "还款方式",
				width : 110,
				align : "center",
				formatter : function(value, rowData, index) {
					if (rowData.pro_pay_type == "1") {
						return value = "等额本息";
					} else if(rowData.pro_pay_type == "2"){
						return value = "按月付息到期还本";
					}else if(rowData.pro_pay_type == "3"){
						return value = "等额本金";
					}else if(rowData.pro_pay_type == "4"){
						return value = "利随本清";
					}else if(rowData.pro_pay_type == "5"){
						return value = "万分之五";
					}
				}
			}, {
				field : "gov_name",
				title : "担保机构",
				width : 80,
				align : "center",
				formatter : function(value, rowData, index) {
					return value == null ? "-" : value;
				}
//			}, {
//				field : "pro_is_clear",
//				title : "是否还清",
//				width : 80,
//				align : "center",
//				formatter : function(value, rowData, index) {
//					if (rowData.pro_is_clear == true) {
//						return value = "已还清";
//					} else {
//						return value = "未还清";
//					}
//				}
			}, {
				field : "ppPayType",
				title : "还款类型",
				width : 80,
				align : "center",
				formatter : function(value, rowData, index) {
					if(value==null){
						return "-";
					} else if(value==1){
						return "正常还款";
					} else if(value==2){
						return "平台代还";
					} else if(value==3){
						return "强制还款";
					}else if(value==4){
						return "提前还款";
					}else if(value==5){
						return "担保代还";
					}
				}
			},{
				field : "pro_payment_status",
				title : "还款状态",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					if(rowData.pro_payment_status==-1){
						return value ="未激活";
					}else if(rowData.pro_payment_status==0){
						return value ="未还清";
					}else if(rowData.pro_payment_status==1){
						return value ="付款中";
					}else if(rowData.pro_payment_status==2){
						return value ="已还清";
					}else if(rowData.pro_payment_status==3){
						return value ="被取消";
					}
				}
			} ] ],
			pagination : true,
			singleSelect : true
		})
		var p = $('#LoanRepaymentListGrid').datagrid('getPager');
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
	// 【搜索】
	Search : function() {
		if ($('#SearchForm').form('validate')) {
			var param = createParam3("SearchForm");
			var o = {
				modelAction : "Search"
			};
			$.post("/loanpay/json/actionLoanPayListView.json?t=" + new Date() + "&"
					+ getParam(o), param, function(data) {
				$("#LoanRepaymentListGrid").datagrid("loadData", data);
				$("#LoanRepaymentListGrid").datagrid("clearSelections");
			}, "json");
		}
	},
	// 【查看】
	LookUp : function() {
		var row = MyGrid.selectRow();
		var num = row;
		if (num == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		var plan_id = row.pro_plan_id;// 还款计划表主键
		var loan_id = row.pro_loan_id;// 项目申请表主键
		var Dialog = $.hDialog({
			href : "/loanpay/loanPayLookUpView?loanId=" + loan_id + "&planId="
					+ plan_id + "&t=" + new Date(),
			iconCls : 'icon-add',
			title : "查看",
			width : $(window).width() - 40,
			height : $(window).height() - 50,
			buttons : [ {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					Dialog.dialog("close");
				}
			} ]
		})
	},
	// 【还款】
	RepaymentPay : function() {
		var row = MyGrid.selectRow();
		var num = row;
		alert(11);
		if (num == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		if (row.pro_is_clear == true) {
			Colyn.log("项目已还清,不可还款");
			return;
		}
		var borTypes = row.pro_add_emp_type;// 借款人类型（1：个人 2：企业 3：借款机构 4：平台）
		var emp_type = $("#hdEmpType").val();// 用户类型(1：平台用户 2：小贷/担保用户)
		var plan_id = row.pro_plan_id;// 还款计划表主键
		var loan_id = row.pro_loan_id;// 项目申请表主键
		var payType = row.pro_payment_status;// 还款状态(1：正常还款 2：平台代还 3：强制还款)
		var pay_type = payType == "正常还款" ? "1" : (payType == "平台代还" ? "2"
				: (payType == "强制还款" ? "3" : ""));// 还款状态(1：正常还款 2：平台代还
		// 3：强制还款)
		var is_clear = row.pro_is_clear;// 是否还清（是，否）
		var param = {
			borTypes : borTypes,
			emp_type : emp_type,
			plan_id : plan_id,
			loan_id : loan_id,
			pay_type : pay_type,
			is_clear : is_clear
		};

		if (emp_type == "1" && borTypes != "4") {
			Colyn.log("对不起，您无权操作该记录！");
			return;
		}

		if (is_clear == "否") {
			$.AjaxColynJson("/loanpay/RepaymentView?type=0&t="
					+ new Date(), param, function(data) {
				if (data.Success) {
					var varTemp = "borTypes=" + borTypes + "&emp_type="
							+ emp_type + "&plan_id=" + plan_id + "&loan_id="
							+ loan_id + "&pay_type=" + pay_type + "&is_clear="
							+ is_clear;
					MyActionB.PubOpeRepayment(varTemp);
				} else {
					Colyn.log(data.Msg);
				}
			});
		} else if (is_clear == "是") {
			Colyn.log("该期还款计划已还清！");
		}
	},
	// 【代还】
	ReplacePay : function() {
		var row = MyGrid.selectRow();
		var num = row;
		if (num == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}

		var borTypes = row.pro_add_emp_type;// 借款人类型（1：个人 2：企业 3：借款机构 4：平台）
		var emp_type = $("#hdEmpType").val();// 用户类型(1：平台用户 2：小贷/担保用户)
		var plan_id = row.pro_plan_id;// 还款计划表主键
		var loan_id = row.pro_loan_id;// 项目申请表主键
		var is_clear = row.pro_is_clear;// 是否还清（是，否）
		var param = {
			borTypes : borTypes,
			emp_type : emp_type,
			plan_id : plan_id,
			loan_id : loan_id,
			is_clear : is_clear
		};

		if (emp_type != "1" || borTypes == "4") {
			Colyn.log("对不起，您无权操作该记录！");
			return;
		}

		if (is_clear == "否") {
			$.AjaxColynJson("/loanpay/LoanPayView?type=0&t="
					+ new Date(), param, function(data) {
				if (data.Success) {
					var varTemp = "borTypes=" + borTypes + "&emp_type="
							+ emp_type + "&plan_id=" + plan_id + "&loan_id="
							+ loan_id + "&is_clear=" + is_clear;
					MyActionC.PubOpeRepayment(varTemp);
				} else {
					Colyn.log(data.Msg);
				}
			});
		} else if (is_clear == "是") {
			Colyn.log("该期还款计划已还清！");
		}
	},
	// 【强制还款】
	ComPay : function() {
		var row = MyGrid.selectRow();
		var num = row;
		if (num == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}

		// var borTypes = row.pdo_product_name;// 借款人类型（1：个人 2：企业 3：借款机构 4：平台）
		// var emp_type = $("#hdEmpType").val();// 用户类型(1：平台用户 2：小贷/担保用户)
		var plan_id = row.pro_plan_id;// 还款计划表主键
		var loan_id = row.pro_loan_id;// 项目申请表主键
		var is_clear = row.pro_is_clear;// 是否还清（是，否）
		var param = {
			planId : plan_id,
			loanId : loan_id,
			isClear : is_clear
		};

//		if (emp_type != "1" || borTypes == "4") {
//			Colyn.log("对不起，您无权操作该记录！");
//			return;
//		}

		// if (is_clear == "否") {
		// $.AjaxColynJson("/loanpay/json/loanPayComRepView.json", param,
		// function(data) {
		// if (data.Success) {
		// //var varTemp = "borTypes=" + borTypes + "&emp_type="
		// // + emp_type + "&plan_id=" + plan_id + "&loan_id="
		// // + loan_id + "&is_clear=" + is_clear;
		// //MyActionD.PubOpeRepayment(varTemp);
		// } else {
		// Colyn.log(data.Msg);
		// }
		// });
		// } else if (is_clear == "是") {
		// Colyn.log("该期还款计划已还清！");
		// }

		$.AjaxColynJson("/loanpay/json/loanPayComRep.json", param,
				function(data) {
					if (data.success) {
						Colyn.log("还款成功！");
					} else {
						Colyn.log(data.message);
					}
				});
	},
	SendMessage: function(){
		var row = MyGrid.selectRow();
		if (row == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		if(row.pro_payment_status==true){
			Colyn.log("该条记录已还清");
			return;
		}
		var Dialog = $.hDialog({
            href: "/loanpay/sendMessage?planPaymentId="+row.pro_plan_id,
            title: "发送短信",
            width: 500,
            height: 300,
            buttons: [{
                text: '发送',
                iconCls: 'icon-add',
                handler: function () {
                    $.ajax({
                    	url: "/loanpay/json/sendMessageAction.json?planPaymentId="+row.pro_plan_id,
                    	success: function(data){
                    		Dialog.dialog("close");
                    		Colyn.log(data.message);
                    	}
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })// dialog end
	},
	
	//export excel
	ExportExcel: function(){
		$("#form1").submit();
	}
}