/*------------------------------------------------
 * Author:李冬冬  Date：2014-8-19 
-------------------------------------------------*/

$(function () {
    var o = { bidId: BidId, bidType: BidType};
    $("#InvestGrid").datagrid({
        method: "post",
        url: "/projectmanage/json/ProjectInvestListView.json?" + getParam(o),
        pageSize: 10,
        fitColumns: true,
        rownumbers: true,
        nowrap: false,
        striped: true,
        remoteSort: true,
        view: myview,//重写当没有数据时
        emptyMsg: '没有找到数据',//返回数据字符
        columns: [[
        { field: "investerName", title: "投资人", width: 150, align: "center" },
        {
            field: "investMoney", title: "投资金额", width: 150, align: "center",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
         {
             field: "validInvestMoney", title: "实际投资金额", width: 150, align: "center",
             formatter: function (v, r, i) {
                 return v == null ? "-" : formatMoney(v, '￥');
             }
         },
        { field: "investTimeStr", title: "投资日期", width: 150, align: "center" },
	    { field: "investStateStr", title: "投资状态", width: 150, align: "center" }
        
        ]],
        pagination: true,
        singleSelect: true,
        onLoadSuccess: function (data) {
            if (data.total > 0) return;
            $('#InvestGrid').datagrid('resize', { height: 100 });
        }

    })
    var p = $('#InvestGrid').datagrid('getPager');
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
});





