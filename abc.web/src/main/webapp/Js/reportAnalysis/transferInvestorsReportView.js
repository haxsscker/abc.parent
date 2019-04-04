$(function() {
    MyGrid.Resize();
    MyAction.Init();
    $("#Export").click(MyAction.Export);
    $("#search").click(MyAction.Search);
    $(window).resize(function() {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function() {
        $("#reportGrid").datagrid({
            method: "Get",
            url: "/reportAnalysis/json/transferInvestorsReportView.json",
            height: $(window).height() - 120,
            pageSize: 10,
            fitColumns: false,	
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
//            idField: "loanId",
            view: myview,
            // 重写当没有数据时
            emptyMsg: '没有找到数据',
            // 返回数据字符
            columns: [[
               { field: "loan_no", title: "项目代码", width: 150, align: "center" },
               { field: "origin_loan_no", title: "原始项目代码", width: 150, align: "center" },
               { field: "user_name", title: "投资人用户名", width: 150, align: "center" },
               { field: "user_real_name", title: "姓名", width: 150, align: "center" },
               { field: "user_doc_no", title: "身份证号", width: 150, align: "center" },
               { field: "user_phone", title: "手机号", width: 150, align: "center" },
               { field: "user_email", title: "邮箱", width: 150, align: "center" },
               { field: "recommend_name", title: "推荐人", width: 150, align: "center" },
               { field: "in_createtime", title: "投资时间", width: 150, align: "center" },
               { field: "in_invest_money", title: "投资金额", width: 150, align: "center" },
               { field: "pay_interest", title: "应收利息", width: 150, align: "center",
            	   formatter: function(value, row, index){
	            	    	return value;
              	   } 
               },
               { field: "collect_interest", title: "已收利息", width: 150, align: "center",
            	   formatter: function(value, row, index){
	            	    	return value;
               	   }
               },
               { field: "collect_capital", title: "已收本金", width: 150, align: "center",
            	   formatter: function(value, row, index){
	            	    	return value;
              	   }
               },
//               { field: "loan_expire_date", title: "借款到期日", width: 150, align: "center" },
               { field: "in_invest_state", title: "是否转让", width: 80, align: "center" },
               { field: "in_transfer_date", title: "转让时间", width: 150, align: "center" },
               { field: "income_end", title: "是否结清", width: 80, align: "center" }
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
    Search: function(){
    	
    	 var param = createParam3("SearchForm");
         var o = { modelAction: "search" };
         $.AjaxColynJson("/reportAnalysis/json/transferInvestorsReportView.json?"+ getParam(o) ,param, function (data) {
             $("#reportGrid").datagrid("loadData", data);
             },JSON)
    },
    Export: function () {
    	$('#searchForm1').attr("action","/reportAnalysis/json/transferInvestorsReportExcel.json");
		$('#searchForm1').submit();
    	
    }

};

