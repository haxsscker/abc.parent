/*------------------------------------------------
 * Author:潘健  Date：2014-8-19 
-------------------------------------------------*/


var MyAction = {
    Init: function () {
        $("#RechargeGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PRechageListView.json",
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "recharge_date", title: "充值日期", width: 150, align: "center" },
            { field: "recharge_money", title: "充值金额", width: 150, align: "right" },
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#RechargeGrid').datagrid('getPager');
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
    }
}

MyAction.Init();


