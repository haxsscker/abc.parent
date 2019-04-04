/*------------------------------------------------
 * Author:李冬冬  Date：2014-8-19 
-------------------------------------------------*/

$(function () {
    var o = { LoanId: LoanId };
    $("#TransferGrid").datagrid({
        url: "/P2PUC/P2PTransferList2?" + getParam(o),
        fitColumns: true,
        rownumbers: true,
        nowrap: false,
        striped: true,
        remoteSort: true,
        view: myview,//重写当没有数据时
        emptyMsg: '没有找到数据',//返回数据字符
        columns: [[
        { field: "cst_user_name", title: "转让人", width: 150, align: "center" },
        {
            field: "pro_transfer_money", title: "转让金额", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
        {
            field: "pro_transfer_date", title: "转让日期", width: 150, align: "center",
            formatter: function (v, r, i) {
                return GetFormatDate(v);
            }
        },
        {
            field: "pro_earn_money", title: "转让收益", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        }
        ]],
        pagination: true,
        singleSelect: true,
        onLoadSuccess: function (data) {
            if (data.total > 0) return;
            $('#TransferGrid').datagrid('resize', { height: 100 });
        }
    })
    var p = $('#TransferGrid').datagrid('getPager');
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


