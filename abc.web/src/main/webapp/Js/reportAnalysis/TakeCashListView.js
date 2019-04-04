/*------------------------------------------------
 * Author:李冬冬  Date：2014-9-1
-------------------------------------------------*/


var MyAction = {
    Init: function () {
        $("#TakeCashGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PTakeCashListView.json",
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "take_date", title: "提现日期", width: 150, align: "center" },
            { field: "take_money", title: "提现金额", width: 150, align: "right" },
            { field: "take_fee", title: "提现费用", width: 150, align: "right" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#TakeCashGrid').datagrid('getPager');
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


