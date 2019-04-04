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
            url: "/reportAnalysis/json/loanReportView.json",
            height: $(window).height() - 120,
            pageSize: 10,
            fitColumns: false,	
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
//            idField: "user_name",
            view: myview,
            // 重写当没有数据时
            emptyMsg: '没有找到数据',
            // 返回数据字符
            columns: [[
               { field: "loan_invest_starttime", title: "发标日期", width: 150, align: "center" },
               { field: "loan_no", title: "项目编号", width: 150, align: "center" },
               { field: "loan_category", title: "项目类型", width: 150, align: "center" },
               { field: "user_name", title: "借款人", width: 150, align: "center" },
               { field: "loan_use", title: "借款用途", width: 150, align: "center" },
               { field: "guaranty_mode", title: "保证方式", width: 150, align: "center" },
               { field: "loan_pay_type", title: "还款方式", width: 150, align: "center" },
               { field: "loan_money", title: "借款金额", width: 150, align: "center" },
               { field: "loan_current_valid_invest", title: "满标金额", width: 150, align: "right" },
               { field: "loan_period", title: "借款期限", width: 150, align: "right" },
               { field: "loan_rate", title: "年化率", width: 150, align: "right" },
               { field: "loan_invest_endtime", title: "招标到期日", width: 150, align: "right" },
               { field: "loan_invest_fulltime", title: "满标日", width: 150, align: "center" },
               { field: "loan_expire_date", title: "借款到期日", width: 80, align: "center" },
               { field: "loan_pay_date", title: "付息日", width: 80, align: "center" },
               { field: "collecttime", title: "实际借款天数", width: 80, align: "center" },
               { field: "service_fee", title: "平台服务费", width: 80, align: "center" },
               
               { field: "guar_fee", title: "担保费", width: 80, align: "center" },
               { field: "pp_pay_capital", title: "待还本金", width: 80, align: "center" },
               { field: "pp_pay_interest", title: "待还利息", width: 80, align: "center" },
               { field: "pp_pay_collect_capital", title: "已还本金", width: 80, align: "center" },
               { field: "pp_pay_collect_interest", title: "已还利息", width: 80, align: "center" },
               
               { field: "yq_pay_capital", title: "逾期本金", width: 80, align: "center" },
               { field: "yq_pay_interest", title: "逾期利息", width: 80, align: "center" },
               { field: "jieqing", title: "是否结清", width: 80, align: "center" }
               
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
         $.AjaxColynJson("/reportAnalysis/json/loanReportView.json?"+ getParam(o) ,param, function (data) {
             $("#reportGrid").datagrid("loadData", data);
             },JSON)
    
    },
    Export: function () {
    	$('#searchForm1').attr("action","/reportAnalysis/json/loanReportExcel.json");
		$('#searchForm1').submit();
    	
    }

};

