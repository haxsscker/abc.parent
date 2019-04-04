$(function () {
	MyGrid.Resize();
	MyAction.Init();
	$("#Export").click(MyAction.Export);
	$("#search").click(MyAction.Search);
    $("#recharge").click(MyAction.Recharge);//充值
    $("#tocash").click(MyAction.Tocash);//提现
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});


var MyAction = {
		Init: function() {
	        $("#reportGrid").datagrid({
	            method: "Get",
	            url: "/moneyManage/plateAccountView.json",
	            height: $(window).height() - 360,
	            pageSize: 10,
	            fitColumns: false,	
	            rownumbers: true,
	            nowrap: false,
	            striped: true,
	            remoteSort: true,
	            view: myview,
	            // 重写当没有数据时
	            emptyMsg: '没有找到数据',
	            // 返回数据字符
	            columns: [[
	               { field: "drDetailType", title: "交易类型", width: 150, align: "center",
	            	   formatter: function (value, rowData, index) {
                           if (value == "3") {
                               return "还款利息";
                           } else if (value == "4"){
                               return "超期罚金";
                           }else if (value == "5"){
                               return "平台服务费";
                           }else if (value == "6"){
                               return "充值金额";
                           }else if (value == "7"){
                               return "提现金额";
                           }else if (value == "8"){
                               return "退款金额";
                           }else if (value == "9"){
                               return "划转金额";
                           }else if (value == "11"){
                               return "平台手续费";
                           }else if (value == "12"){
                               return "担保服务费";
                           }else if (value == "13"){
                               return "转让金额";
                           }else if (value == "14"){
                               return "转让手续费";
                           }else if (value == "15"){
                               return "收购金额";
                           }else if (value == "16"){
                               return "红包金额";
                           }else if (value == "17"){
                               return "流标金额";
                           }else if (value == "18"){
                               return "流标退回金额";
                           }else if (value == "20"){
                               return "违约罚金";
                           }else if (value == "21"){
                               return "银行手续费";
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
                        	   return "-"
                           }
                       }
	               },
	               { field: "drOperateDateStr", title: "交易时间", width: 150, align: "center"}
	            ]],
	            pagination: true,
	            singleSelect: true
	        });
	        var p = $('#reportGrid').datagrid('getPager');
	        $(p).pagination({
	            pageSize: 10,
	            pageList: [5, 10, 15, 20, 30, 50, 100],
	            beforePageText: '第',
	            afterPageText: '页    共 {pages} 页',
	            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	            onBeforeRefresh: function() {
	                $(this).pagination('loading');
	                $(this).pagination('loaded');
	            }
	        });
	    },
	    Recharge: function () {
	        var rechargeAmount = $("#rechargeAmount").val();
	        var merAccTyp = $("#MerAccTyp").val();
	        var o2 = { rechargeAmount: rechargeAmount,merAccTyp:merAccTyp};
	                    $.post("/moneyManage/rechargeMoeny.json", o2, function (data) {
	                        if (data.success) {  
	                        	Colyn.log("充值成功！");
	                        	location.reload();
	                        }
	                        else {
	                            Colyn.log(data.message);
	                        }
	                    })
	    },
	    Tocash: function () {
	        var tocashAmount = $("#tocashAmount").val();
	        var o3 = { tocashAmount: tocashAmount };
	                    $.post("/moneyManage/cashMoeny.json", o3, function (data) {
	                        if (data.success) {  
	                        	Colyn.log("提现成功！");
	                        	location.reload();
	                        }
	                        else {
	                            Colyn.log(data.message);
	                        }
	                    })
	    },
	    Search: function(){
	    	 var param = createParam3("SearchForm");
	         console.log(param);
	         var o = { modelAction: "search" };
	         $.AjaxColynJson("/moneyManage/plateAccountView.json?"+ getParam(o) ,param, function (data) {
	             $("#reportGrid").datagrid("loadData", data);
	             },JSON)
	    	
	    },
	    
	    Export: function () {
	    	$('#searchForm1').attr("action","/reportAnalysis/json/plateAccountViewExcel.json");
			$('#searchForm1').submit();
	    	
	    }
	};