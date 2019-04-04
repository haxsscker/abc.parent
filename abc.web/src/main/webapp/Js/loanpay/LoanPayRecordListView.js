/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-19 
-------------------------------------------------*/
$(function() {
	MyGrid.Resize();
	$("#Search").click(MyAction.Search);// 【搜索】
	$("#Export").click(MyAction.ExportExcel);
	MyAction.Init();// 页面初始化
	$(window).resize(function() {
		MyGrid.RefreshPanl();
	});
});

var MyAction = {
	// 页面初始化
	Init : function() {
		$("#LoanRepayRecordListGrid").datagrid({
			method : "POST",// 请求远程数据的方法类型。
			url : "/loanpay/json/ActionLoanPayRecordListView.json",// 一个用以从远程站点请求数据的超链接地址。
			height : $(window).height() - 52,
			pageSize : 10,// 当设置分页属性时，初始化每页记录数。
			fitColumns : false,// 设置为true将自动使列适应表格宽度以防止出现水平滚动。
			rownumbers : true,// 设置为true将显示行数。
			nowrap : false,// 设置为true，当数据长度超出列宽时将会自动截取。
			striped : false,// 设置为true将交替显示行背景。
			remoteSort : true,// 定义是否通过远程服务器对数据排序。
			view : myview,// 定义数据表格的视图。【重写当没有数据时】
			emptyMsg : '没有找到数据',// 返回数据字符
			columns: [[
		               { field: "drDetailType", title: "交易类型", width: 150, align: "center",
		            	   formatter: function (value, rowData, index) {
	                           if (value == "3") {
	                               return "还款利息";
	                           } else if (value == "2"){
	                        	   return "还款本金";
	                           } else if (value == "4"){
	                        	   return "还款罚息";
	                           } else if (value == "20"){
	                        	   return "还款罚金";
	                           } else if (value == "5"){
	                        	   return "平台服务费";
	                           }
	                       }
		               },
		               { field: "drPayAccount", title: "付款账号", width: 150, align: "center" },
		               { field: "drReceiveAccount", title: "收款账号", width: 150, align: "center" },
		               { field: "drMoneyAmountStr", title: "交易金额(元)", width: 150, align: "center" },
		               { field: "drInnerSeqNo", title: "交易流水号", width: 200, align: "center" },
		               { field: "drStateStr", title: "交易状态", width: 150, align: "center" ,
		            	   formatter: function (value, rowData, index) {
	                           if (value == "0") {
	                               return "等待响应";
	                           }else if (value == "1"){
	                               return "成功";
	                           }else if (value == "2"){
	                               return "失败";
	                           }else{
	                        	   return "-";
	                           }
	                       }
		               },
		               { field: "drOperateDateStr", title: "交易时间", width: 150, align: "center"}
		            ]],
			pagination : true,
			singleSelect : true
		});
		var p = $('#LoanRepayRecordListGrid').datagrid('getPager');
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
			$.post("/loanpay/json/actionLoanPayRecordListView.json?t=" + new Date() + "&"
					+ getParam(o), param, function(data) {
				$("#LoanRepayRecordListGrid").datagrid("loadData", data);
				$("#LoanRepayRecordListGrid").datagrid("clearSelections");
			}, "json");
		}
	},
	//export excel
	ExportExcel: function(){
		$('#form1').attr("action","/reportAnalysis/json/actionLoanPayRecordListViewExcel.json");
		$('#form1').submit();
	}
};