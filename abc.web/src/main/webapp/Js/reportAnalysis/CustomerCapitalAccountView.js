
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#LookUp").click(MyAction.LookUp);
    $("#Search").click(MyAction.Search);
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
        $("#CustomerCapitalAccountGrid").datagrid({
            method: "GET",
            url: "/reportAnalysis/json/CustomerCapital.json?",//具体获取列表数据的URL

            height: $(window).height() - 95,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
//            { field: "customer_name", title: "客户名称",hidden:true, width: 200, align: "center" },
            { field: "accountUserName", title: "真实姓名", width: 200, align: "center" },
//            { field: "assets_total", title: "资产总额", width: 100, align: "right",  formatter: function (value, rowData, index) {
//                return formatMoney(value, '￥');
//            	}
//            },
//            {
//                field: "availableBalance", title: "可用余额", width: 100, align: "right", formatter: function (value, rowData, index) {
//                    return formatMoney(value, '￥');
//                }
//            },
//            {
//                field: "amountFrozen", title: "冻结金额", width: 100, align: "right", formatter: function (value, rowData, index) {
//                    return formatMoney(value, '￥');
//                }
//            },
//            {
//                field: "pro_collect_money",hidden:true, title: "已收本金", width: 100, align: "right", formatter: function (value, rowData, index) {
//                return formatMoney(value, '￥');
//            }
//            },
//            {
//                field: "pro_collect_rate",hidden:true, title: "已收利息", width: 100, align: "right", formatter: function (value, rowData, index) {
//                    return formatMoney(value, '￥');
//                }
//            },
//            {
//                field: "pro_collect_over_rate",hidden:true, title: "已收罚息", width: 100, align: "right", formatter: function (value, rowData, index) {
//                    return formatMoney(value, '￥');
//                }
//            },
//            {
//                field: "pro_invest_money",hidden:true, title: "投资金额", width: 100, align: "right", formatter: function (value, rowData, index) {
//                    return formatMoney(value, '￥');
//                }
//            },
//            {
//                field: "buy_money",hidden:true, title: "买入债权", width: 100, align: "right", hidden:true, formatter: function (value, rowData, index) {
//                    return formatMoney(value, '￥');
//                }
//            },
//            {
//                field: "transfer_money",hidden:true, title: "转让债权", width: 120, align: "right", formatter: function (value, rowData, index) {
//                    return formatMoney(value, '￥');
//                }
//            },
            {
            	field: "accountUserId", title: "用户Id", width: 120, align: "right"
            },
            {
            	field: "accountNo", title: "用户编号", width: 120, align: "right"
            }
//            {
//                field: "transfer_fee", title: "转让手续费", width: 100, align: "right", formatter: function (value, rowData, index) {
//                    return formatMoney(value, '￥');
//                }
//            }
//            {
//                field: "purchase_money", title: "收购债权", width: 100, align: "right", formatter: function (value, rowData, index) {
//                    return formatMoney(value, '￥');
//                }
//            },
//            {
//                field: "purchasefee", title: "收购手续费", width: 100, align: "right", formatter: function (value, rowData, index) {
//                    return formatMoney(value, '￥');
//                }
//            }
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#CustomerCapitalAccountGrid').datagrid('getPager');
        $(p).pagination({
            pageSize: 10,
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
    LookUp: function ()
    {
        var row = MyGrid.selectRow();
        if (row == null)
        {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/reportAnalysis/CustomerAgenciesLookUpView?userId="+row.accountUserId+"&accountNo="+row.accountNo,
            iconCls: 'icon-add',
            title: "资金明细统计",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            onLoad:function() {
            	MyAction.Init();
            	$("#Export").click(MyAction.Export);
            },
            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })
    },
    Search: function () {
    	var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.AjaxColynJson("/reportAnalysis/json/CustomerCapital.json?"+ getParam(o) ,param, function (data) {
            $("#CustomerCapitalAccountGrid").datagrid("loadData", data);
            },JSON)
    
    }
}
