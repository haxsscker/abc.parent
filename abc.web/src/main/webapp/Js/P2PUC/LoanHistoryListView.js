/*------------------------------------------------
 * Author:李冬冬  Date：2014-9-1 
-------------------------------------------------*/

$(function () {
    var o = { UserId: UserId };
    $("#LoanHistoryGrid").datagrid({
        url: "/P2PUC/P2PApplyInfoList2?" + getParam(o),
        pageSize: 10,
        fitColumns: true,
        rownumbers: true,
        nowrap: false,
        striped: true,
        remoteSort: true,
        view: myview,//重写当没有数据时
        emptyMsg: '没有找到数据',//返回数据字符
        columns: [[
         {
             field: "pro_add_date", title: "贷款日期", width: 150, align: "center", formatter: function (value, rowData, index) {
                 return convertToDate(value, "yyyy-MM-dd");
             }
         },
            {
                field: "pro_loan_money", title: "贷款金额", width: 150, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "pro_loan_money", title: "审批金额", width: 150, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "pro_loan_period", title: "借款期限", width: 80, align: "center", formatter: function (value, rowData, index) {
                    if (value == "" || value == null) { return "-"; } else { return GetPayType(value, rowData.pro_period_type) }
                }
            },
            { field: "ynfailed", title: "是否流标", width: 150, align: "center" },
            { field: "ynOverdue", title: "是否逾期", width: 150, align: "center" }
        ]],
        pagination: true,
        singleSelect: true,
        onLoadSuccess: function (data) {
            if (data.total > 0) return;
            $('#LoanHistoryGrid').datagrid('resize', { height: 100 });
        }

    })
    var p = $('#LoanHistoryGrid').datagrid('getPager');
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



