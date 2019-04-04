/*------------------------------------------------
 * Author:李冬冬  Date：2014-8-19 
-------------------------------------------------*/


var MyAction = {
    Init: function () {
        $("#CollectGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PLoanCollectListView.json",
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "invest_emp", title: "投资人", width: 100, align: "center" },
            { field: "invest_money", title: "投资金额", width: 100, align: "right" },
            { field: "invest_date", title: "投资日期", width: 100, align: "center" },
            { field: "pay_money", title: "本期应收本金", width: 120, align: "right" },
            { field: "pay_rate", title: "本期应收利息", width: 120, align: "right" },
            { field: "pay_fee", title: "本期应收罚息", width: 120, align: "right" },
            { field: "collect_money", title: "本期实收本金", width: 120, align: "right" },
            { field: "collect_rate", title: "本期实收利息", width: 120, align: "right" },
            { field: "collect_fee", title: "本期实收罚息", width: 120, align: "right" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#CollectGrid').datagrid('getPager');
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


