$(function() {
	$("#btnCrtRepSch").click(MyAction.Init);// 【搜索】
	$("#TransferFunds").click(MyAction.TransferFunds);// 资金划转
	
});

var timer;

var MyAction = {
	// 页面初始化
	Init : function() {
		$("#FullTransferMoneyGrid").datagrid({
			method : "POST",// 请求远程数据的方法类型。
			url : "/moneyManage/json/buyFullTransferListView.json?"+createSearchParam(),// 一个用以从远程站点请求数据的超链接地址。
			height : $(window).height() - 52,
			pageSize : 10,// 当设置分页属性时，初始化每页记录数。
			fitColumns : false,// 设置为true将自动使列适应表格宽度以防止出现水平滚动。
			rownumbers : true,// 设置为true将显示行数。
			nowrap : false,// 设置为true，当数据长度超出列宽时将会自动截取。
			striped : false,// 设置为true将交替显示行背景。
			idField : "proBuyLoanId", // 表明该列是一个唯一列。【收购申请表主键】
			remoteSort : true,// 定义是否通过远程服务器对数据排序。
			view : myview,// 定义数据表格的视图。【重写当没有数据时】
			emptyMsg : '没有找到数据',// 返回数据字符
			columns : [ [ {
				field : "proLoanNo",
				title : "项目名称",
				width : 270,
				align : "center",
				formatter : function(value, rowData, index) {
					return CutString(value, 19);
				}
			}, {
				field : "pdoProductName",
				title : "项目类型",
				width : 80,
				align : "center",
				formatter : function(value, row, index) {
					if (value == '1') {
						return "个人信用贷";
					} else if (value == '2') {
						return "汽车抵押贷";
					} else if (value == '3') {
						return "房产抵押贷";
					} else if (value == '4') {
						return "企业经营贷";
					}
				}
			}, {
				field : "proAddEmp",
				title : "借款人",
				width : 160,
				align : "center",
				formatter : function(value, rowData, index) {
					return CutString(value, 10);
				}
			}, {
				field : "proLoanMoney",
				title : "借款金额(元)",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatMoney(value, "￥");
				}
			}, {
				field : "proLoanRate",
				title : "年化收益率",
				width : 80,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatPercent(value);
				}
			}, {
				field : "cstRealName",
				title : "收购人",
				width : 160,
				align : "center",
				formatter : function(value, rowData, index) {
					return CutString(value, 10);
				}
			}, {
				field : "proBuyMoney",
				title : "收购金额",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return formatMoney(value, "￥");
				}
			}, {
				field : "proBuyDateStr",
				title : "收购日期",
				width : 80,
				align : "center",
				formatter : function(value, row, index) {
					if (!value) {
						return "-";
					} else {
						return value;
					}
				}
			}, {
				field : "proFullDateStr",
				title : "满标日期",
				width : 80,
				align : "center"
			}, {
				field : "proStartDateStr",
				title : "划转日期",
				width : 80,
				align : "center",
				formatter : function(value, row, index) {
					if (!value) {
						return "-";
					} else {
						return value;
					}
				}
			}, {
				field : "proTransferState",
				title : "划转状态",
				width : 100,
				align : "center",
				formatter : function(value, row, index) {
					if (value == '0') {
						return "待响应";
					} else if (value == '1') {
						return "划转成功";
					} else if (value == '2') {
						return "划转失败";
					}
					else {
						return '待划转';
					}
				}
			} ] ],
			pagination : true,
			singleSelect : true
		})
		var p = $('#FullTransferMoneyGrid').datagrid('getPager');
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
			var param = createSearchParam();
			$.post("/moneyManage/json/BuyFullTransferListView.json",
							param, function(data) {
								$("#FullTransferMoneyGrid").datagrid(
										"loadData", data);
								$("#FullTransferMoneyGrid").datagrid(
										"clearSelections");
							}, "json");
		}
	},
	// 【资金划转】
//	TransferFunds : function() {
//		var row = MyGrid.selectRow();
//		if (row == null) {
//			Colyn.log("请选择一条记录进行操作");
//			return;
//		}
//		var buyId = row.proBuyLoanId;// 收购申请表主键
//		var LoanState = row.pro_transfer_state;// 划转状态
		//if (LoanState == "待划转") {
//			$.AjaxColynJson("/moneyManage/json/BuyFullTransferAddView.json?buyId="+buyId, "", function(data) {
//				if (data.success) {
//					Colyn.log("划转成功！");
//				} else {
//					Colyn.log(data.message);
//				}
//			});
		//} else if (LoanState == "已划转") {
		//	Colyn.log("该项目已划转！");
		//}
//	},
	// 打开资金划转页面[方法]
	TransferFunds : function() {	
//	TransferView : function(temp) {
		var row = MyGrid.selectRow();
		if (row == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		var loanId = row.proLoanId;
		var bidId = row.bidId;
		var bidType = row.bidType;
		var proBuyLoanId = row.proBuyLoanId;
		var Dialog = $.hDialog({
			href : "/moneyManage/BuyFullTransferAddView?loanId="+loanId+"&bidId="+bidId+"&bidType="+bidType+"&buyLoanId="+proBuyLoanId,
			iconCls : 'icon-add',
			title : "收购满标资金划转",
			width : $(window).width() - 40,
			height : $(window).height() - 50,
			onLoad : function() {
				$("#cstRealName").val(row.cstRealName);
				$("#proBuyDate").val(row.proBuyDateStr);
				$("#proBuyMoney").val(row.proBuyMoney);
				$("#proBuyFee").val(row.proBuyFee);
				
				// 实收手续费改变
				$("#len_collect_fee").live('keyup', MyAction.collectFeeChange);
				// 获取手机验证码[事件移除]
				$("#btnCheckCode").die('click', MyAction.getMbphoneVfcCode);
				// 获取手机验证码
				$("#btnCheckCode").live('click', MyAction.getMbphoneVfcCode);
			},
			onBeforeClose : function() {
				MyAction.resetAccessCode();
			},
			buttons : [ {
				text : '确认划转',
				iconCls : 'icon-add',
				handler : function() {
					// 确认划转
					MyAction.getCfmTransfer(Dialog);

				}
			}, {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					MyAction.resetAccessCode();
					Dialog.dialog("close");
				}
			} ]
		})
	},
	// 实收手续费改变【资金划转子页面】
	collectFeeChange : function() {
		var varYSSXF = parseFloat($("#len_pay_fee").val());// 应收手续费
		var varYHZJE = parseFloat($("#len_pay_total").val());// 应划转金额
		var varHZJE = parseFloat($("#len_pay_total").val()) + parseFloat($("#len_pay_fee").val());// 划转金额
		var varSSSXF = parseFloat($("#len_collect_fee").val());// 实收手续费
		//var varTZZE = varSSSXF - varYSSXF;// 临时变量 = 实收手续费 - 应收手续费

		if (!isNaN(varSSSXF)) {
			if (varSSSXF > varYSSXF) {
				Colyn.log("实收手续费不能大于应收手续费！");
			} else if (varSSSXF < 0) {
				Colyn.log("实收手续费不能小于0！");
			} else {
				$("#len_lend_money").val((varHZJE - varSSSXF).toFixed(2));
			}
		}
	},
	// 获取手机验证码【资金划转子页面】
	getMbphoneVfcCode : function() {
		$.AjaxColynJson("/moneyManage/json/MoneyGetCheckCode.json?t=" + new Date(), "",
				function(data) {
					if (data.success) {
						Colyn.log(data.message);
						MyAction.getMbpVfcCodeAfter();
					} else {
						Colyn.log(data.message);
					}
				});
	},
	// 确认划转【资金划转子页面】
	getCfmTransfer : function(Dialog) {
		if ($('#tbTransferFunds').form('validate')) {
			var CheckCode = $("#txtCheckCode").val();// 验证码
			var collectFee = $("#len_collect_fee").val();// 实收费用
			var row = MyGrid.selectRow();
			if (row == null) {
				Colyn.log("请选择一条记录进行操作");
				return;
			}
			var proBuyLoanId = row.proBuyLoanId;
			
			$.AjaxColynJson("/moneyManage/json/BuyTransferAddData.json?strCheckCode="
									+ CheckCode + "&collectFee=" + collectFee + "&buyLoanId=" + proBuyLoanId,"",function(data) {
								if (data.success) {
									Colyn.log(data.message);
									Dialog.dialog("close");
//									setTimeout(
//											function() {
//												gotoUrl("/P2PMoneyManage/BuyFullTransferListView?MenuId=301&MenuName=收购满标划转");
//											}, 3000);
								} else {
									Colyn.log(data.message);
									Dialog.dialog("close");
								}
							});
		}
	},
	// 获取手机验证码后续【资金划转子页面】
	getMbpVfcCodeAfter : function() {
		$("#btnCheckCode").attr('disabled', "true");
		var second = 120;// 倒计时秒
		function ShowCountDown() {
			if (second > 0) {
				second--;
				$("#btnCheckCode").val("剩余" + second + "秒");
			} else {
				MyAction.resetAccessCode();
			}

		}
		timer = window.setInterval(function() {
			ShowCountDown();
		}, 1000);
	},
	// 重置获取验证码
	resetAccessCode : function() {
		$("#btnCheckCode").val("重新获取手机验证码");
		$("#btnCheckCode").removeAttr("disabled");
		clearInterval(timer);
		$.AjaxColynJson("/P2PMoneyManage/BuyFullRemoveCheckCode?t="
				+ new Date(), "", function(data) {
		});
	}
}
function createSearchParam() {
	var form = {
		cstRealName : $("#cst_real_name").val(),
		minBuyMoney : $("#min_buy_money").val(),
		maxBuyMoney : $("#max_buy_money").val(),
		pdoProductName : $("#pdo_product_name").val(),
		minLendDate : $("#min_lend_date").datebox("getValue"),
		maxLendDate : $("#max_lend_date").datebox("getValue"),
		proLoanState : $("#pro_transfer_state").val()
	};
	return getParam(form);
}