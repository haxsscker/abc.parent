var MyActionForGuaranteeGov = {
    Init: function () {
        //var row = MyGrid.selectRow();
        $("#OrgLoanCheckIdearGrid").datagrid({
            method: "Post",
            url: "/review/json/AuditOpinionList.json?reviewType=" + reviewType + "&applyId=" + applyId,
            height: $(window).height() - 150,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            columns: [[
                { field: "pro_check_role", title: "审核角色", width: 150, align: "center" },
                { field: "pro_check_emp", title: "审核人", width: 150, align: "center" },
                { field: "pro_check_date", title: "审核日期", width: 150, align: "center" },
                { field: "pro_check_op", title: "审核操作", width: 150, align: "center" },
                { field: "pro_check_next_role", title: "下一审核角色", width: 150, align: "center" },
                { field: "pro_check_next_emp", title: "下一审核人", width: 150, align: "center" },
                { field: "pro_check_idear", title: "审核意见", width: 150, align: "center", formatter: function (value, rowData, index) { return CutString(value, 10); } }
            ]],
            pagination: true,
            singleSelect: true,
            onLoadSuccess: function (data) {
                if (data.total == 0) {
                    var body = $(this).data().datagrid.dc.body2;
                    body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;">没有找到数据</td></tr>');
                }
            }
        });
        var p = $('#OrgLoanCheckIdearGrid').datagrid('getPager');
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
};
MyActionForGuaranteeGov.Init();

