/*------------------------------------------------
 * Author:潘健  Date：2014-8-19 
-------------------------------------------------*/


var MyAction = {
    Init: function () {
        $("#InvestorAgenciesFundsDetailsGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PGuaranteeAgenciesFundsDetails.json",
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "operation_type", title: "操作类型", width: 150, align: "center" },
            { field: "operation_money", title: "操作金额", width: 150, align: "right" },
            { field: "operation_date", title: "操作日期", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#InvestorAgenciesFundsDetailsGrid').datagrid('getPager');
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


