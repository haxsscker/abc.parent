var MyActionForGuaranteeGov = {
    Init: function () {
        $("#OrgLoanCheckIdearGrid").datagrid({
            method: "Post",
            url: "/P2PMailManagement/MailManagementAddUsreName",
            height: $(window).height() - 150,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            checkbox: true,
            columns: [
                [
                    { field: "id",width: 50, align: "center",checkbox:true },
                    { field: "name", title: "接收人", width: 150, align: "center" },
                    { field: "type", title: "用户类型", width: 150, align: "center",formatter: function(v, r, i) {
                        if (v == "1") {
                            return "网友用户";
                        } else {
                            return "平台用户";
                        }
                    } }
                ]
            ],
            pagination: true,
            singleSelect: false,
            onLoadSuccess: function(data) {
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
}
MyActionForGuaranteeGov.Init();

