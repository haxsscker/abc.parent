/*------------------------------------------------
 * Author:李冬冬  Date：2014-8-19 
-------------------------------------------------*/

$(function () {
    var o = { ParamId: ParamId, LoanId: LoanId };
    $("#LoanStepListGird").datagrid({
        url: "/P2PUC/P2PLoanStepList2?" + getParam(o),
        pageSize: 10,
        fitColumns: true,
        rownumbers: true,
        nowrap: false,
        striped: true,
        remoteSort: true,
        view: myview,//重写当没有数据时
        emptyMsg: '没有找到数据',//返回数据字符
        columns: [[
            { field: "pro_flow_step", title: "环节名称", width: 150, align: "center" },
            { field: "emp_Name", title: "操作人", width: 150, align: "center" },
            {
                field: "pro_operate_date", title: "操作日期", width: 150, align: "center",
                formatter: function (v, r, i) {
                    return GetFormatDate(v);
                }
            },
            {
                field: "pro_flow_mark", title: "备注", width: 150, align: "center", formatter: function (value, rowData, index) {
                    return CutString(value, 10);
                }
            }
        ]],
        pagination: true,
        singleSelect: true,
        onLoadSuccess: function (data) {
            if (data.total > 0) return;
            $('#LoanStepListGird').datagrid('resize', { height: 100 });
        }
    })
    var p = $('#LoanStepListGird').datagrid('getPager');
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



