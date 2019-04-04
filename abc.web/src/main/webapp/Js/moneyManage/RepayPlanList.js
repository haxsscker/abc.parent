 function createPlan() {
	Action.Init();
};


var Action = {
		
	// 页面初始化
	Init : function() {
		var periods = $("#txtPeriods").val();
		$("#tbdRepaySchedule").datagrid({
			method : "POST",// 请求远程数据的方法类型。
			url : "/moneyManage/json/CreateRepaySchedule.json?loanId="+loanId+"&periods="+periods+"&servFee="+servFee,// 一个用以从远程站点请求数据的超链接地址。
			pageSize : 10,// 当设置分页属性时，初始化每页记录数。
			fitColumns : true,// 设置为true将自动使列适应表格宽度以防止出现水平滚动。
			rownumbers : true,// 设置为true将显示行数。
			nowrap : true,// 设置为true，当数据长度超出列宽时将会自动截取。
			striped : true,// 设置为true将交替显示行背景。
			idField : "id", // 表明该列是一个唯一列。【贷款申请ID】
			remoteSort : true,// 定义是否通过远程服务器对数据排序。
			view : myview,// 定义数据表格的视图。【重写当没有数据时】
			emptyMsg : '没有找到数据',// 返回数据字符
			columns : [ [ {
				field : "pro_pay_money",
				title : "应还本金",
				width : 80,
				align : "center",
				 formatter: function (v, r, i) {
		                return v == null ? "-" : formatMoney(v, '￥');
		            }
			}, {
				field : "pro_pay_rate",
				title : "应还利息",
				width : 160,
				align : "center",
				 formatter: function (v, r, i) {
		                return v == null ? "-" : formatMoney(v, '￥');
		            }
			}, {
				field : "DeInterest",
				title : "应还罚金",
				width : 100,
				align : "center",
				 formatter: function (v, r, i) {
		                return v == null ? "-" : formatMoney(v, '￥');
		            }
			}, {
				field : "pro_pay_serve_fee",
				title : "应还服务费",
				width : 90,
				align : "center",formatter: function (v, r, i) {
	                return v == null ? "-" : formatMoney(v, '￥');
	            }
			}, {
				field : "pro_pay_total",
				title : "应还总额",
				width : 80,
				align : "center", formatter: function (v, r, i) {
	                return v == null ? "-" : formatMoney(v, '￥');
	            }
			}, {
				field : "pro_pay_date",
				title : "应还日期",
				width : 80,
				align : "center", formatter: function (v, r, i) {
	                return v;
	            }
			}
			] ],
			pagination : true,
			singleSelect : true
		})
		var p = $('#tbdRepaySchedule').datagrid('getPager');
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
	}
}