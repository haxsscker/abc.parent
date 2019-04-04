
$(function () {
    MyGrid.Resize();
    $("#search").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#InvestorsGrid").datagrid({
            method: "GET",
            url: "/recharge/json/ActionInvestorRechargeListView.json?" + createParam3("SearchForm"),
            height: $(window).height() - 52,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "cust_name", title: "客户名称", width: 150, align: "center" },
            { field: "cust_fullname", title: "真实姓名", width: 150, align: "center" },
            { field: "extcash_numberofbank", title: "充值账号", width: 250, align: "center" },
            // { field: "extcash_bank", title: "充值银行", width: 150, align: "center" },
            { field: "extcash_money", title: "充值金额", width: 150, align: "right" },
            { field: "extcash_date", title: "充值时间", width: 250, align: "center" },
            { field: "extcash_state", title: "状态", width: 100, align: "center",formatter: function(value, rowData, index) {
                if (value == "0") {
                    return "充值中";
                } else
                if (value == "1"){
                    return "充值成功";
                }else if(value == "2")
                {
                	 return "充值失败";
                }
            } }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#InvestorsGrid').datagrid('getPager');
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
    Search: function () {
        var param = createParam3("SearchForm");
        $.post("/recharge/json/ActionInvestorRechargeListView.json", param, function (data) {
             $("#InvestorsGrid").datagrid("loadData", data)
        }, 'json');
    }
}