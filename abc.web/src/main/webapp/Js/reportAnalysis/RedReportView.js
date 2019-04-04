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
        $("#redReportGrid").datagrid({
            method: "Get",
            url: "/reportAnalysis/json/redReportView.json",
            height: $(window).height() - 135,
            pageSize: 10,
            fitColumns: false,	
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            //idField: "loanId",
            view: myview,
            // 重写当没有数据时
            emptyMsg: '没有找到数据',
            // 返回数据字符
            columns: [[
               { field: "rs_sendtime", title: "派发日期", width: 150, align: "center" },
               { field: "rs_type_name", title: "派发类型", width: 150, align: "center" },
               { field: "rs_amt", title: "派发金额", width: 60, align: "center" },
               { field: "ru_amount", title: "使用金额", width: 60, align: "center" },
               { field: "user_name", title: "注册用户名", width: 150, align: "center" },
               { field: "user_real_name", title: "姓名", width: 80, align: "center" },
              // { field: "user_phone", title: "手机号", width: 150, align: "center" },
               { field: "red_type_name", title: "红包状态", width: 60, align: "center" },
               { field: "redUseTime", title: "使用时间", width: 130, align: "right" },
               { field: "loan_no", title: "使用红包项目", width: 150, align: "center" },
               { field: "in_invest_money", title: "投资金额", width: 60, align: "right" },
               { field: "rs_closetime", title: "截止有效期", width: 150, align: "center" },
               { field: "rs_theme", title: "派发来源", width: 150, align: "center" },
                    ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#redReportGrid').datagrid('getPager');
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
         $.AjaxColynJson("/reportAnalysis/json/redReportView.json?"+ getParam(o) ,param, function (data) {
             $("#redReportGrid").datagrid("loadData", data);
             },JSON)
    
    },
    Export: function () {
    	$('#searchForm1').attr("action","/reportAnalysis/json/redReportExcel.json");
		$('#searchForm1').submit();
    	
    }

};

