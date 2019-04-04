$(function() {
	MyGrid.Resize();
    MyAction.Init();
    $("#Search").click(MyAction.Search);
    $("#Export").click(MyAction.Export);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});


var MyAction = {
	RefreshPanl: function () {
        MyGrid.Resize();
        $('#layout').layout('resize');
    },
    Init: function () {
        $("#CustomerCapitalFlowGrid").datagrid({
            method: "POST",
            url: "/reportAnalysis/json/CustomerCapitalFlow.json",
            height: $(window).height() - 120,
            pageSize: 20,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
			{ field: "payUserName", title: "付款方用户名", width: 140, align: "center" },
			{ field: "payRealName", title: "付款方姓名", width: 140, align: "center" },
			{ field: "drPayAccount", title: "付款方账号", width: 160, align: "center" },
		   	{ field: "receiveUserName", title: "收款方用户名", width: 140, align: "center" },
            { field: "receiveRealName", title: "收款方姓名", width: 140, align: "center" },
           	{ field: "drReceiveAccount", title: "收款方账号", width: 160, align: "center" },
            { field: "drOperateDateStr", title: "交易日期", width: 160, align: "center" },
            { field: "drMoneyAmountStr", title: "交易金额(￥)", width: 150, align: "center" },
            { field: "drInnerSeqNo", title: "交易订单号", width: 180, align: "center" },
            { field: "drTypeStr", title: "交易类型", width: 120, align: "center" },
            { field: "drStateStr", title: "状态", width: 100, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#CustomerCapitalFlowGrid').datagrid('getPager');
        $(p).pagination({
            pageSize:20,
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
    Search: function () {
    	var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.AjaxColynJson("/reportAnalysis/json/CustomerCapitalFlow.json?"+ getParam(o) ,param, function (data) {
            $("#CustomerCapitalFlowGrid").datagrid("loadData", data);
            },JSON);
    
    },
    Export: function(){
    	if ($('#CustomerCapitalFlowGrid').datagrid('getRows').length > 0) {
			var param = createParam3("SearchForm");
			window.location.href = '/reportAnalysis/json/ExportCustomerCapitalFlow.json?'
					+ getParam(param);
		} else {
			Colyn.log("暂无数据");
		}
    }
}



