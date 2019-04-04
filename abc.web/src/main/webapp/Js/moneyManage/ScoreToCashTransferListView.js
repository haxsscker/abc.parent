/*------------------------------------------------
 * Author:李冬冬  Date：2014-9-3
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#CashTransfer").click(MyAction.CashTransfer);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#IntegralToCashListGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/IntegralToCashListView.json",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "toCash_emp", title: "兑现用户", width: 150, align: "center" },
            { field: "toCash_money", title: "兑现金额", width: 100, align: "right" },
            { field: "toCash_integral", title: "兑现积分", width: 100, align: "right" },
            { field: "toCash_date", title: "兑现日期", width: 100, align: "center" },
            { field: "lend_date", title: "划转日期", width: 100, align: "center" },
            { field: "lend_state", title: "划转状态", width: 100, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#IntegralToCashListGrid').datagrid('getPager');
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
    //积分兑现划转
    CashTransfer: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        alert("划转成功！");
    }
}