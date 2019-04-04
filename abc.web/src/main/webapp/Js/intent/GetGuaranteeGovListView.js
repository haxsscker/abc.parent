$(function () {
    $("#searEmp").click(function () {
        MyActionForGuaranteeGov.Searchs();
    });
});
var MyActionForGuaranteeGov = {
    Init: function () {
        var row = MyGrid.selectRow();
        $("#GuaranteeGovListGrid").datagrid({
            method: "Post",
            url: "/P2PFinancingManage/SendGuarAudit",
            height: $(window).height() - 220,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            columns: [[
                 { field: "gov_name", title: "机构名称", width: 250, align: "center" },
                 { field: "gov_no", title: "组织机构代码", width: 100, align: "center" },
                 {
                     field: "gov_total_capital", title: "资产总额", width: 100, align: "right", formatter: function (v) {
                         return formatMoney(v, '￥');
                     }
                 },
                 {
                     field: "gov_max_lend_money", title: "最大借款额度", width: 100, align: "right", formatter: function (v) {
                         return formatMoney(v, '￥');
                     }
                 },
                 {
                     field: "gov_max_guar_money", title: "最大担保额度", width: 100, align: "right", formatter: function (v) {
                         return formatMoney(v, '￥');
                     }
                 },
                 {
                     field: "gov_guar_name", title: "担保机构", width: 250, align: "center", formatter: function (v) {
                         return CutString(v, 15);
                     }
                 },
                 {
                     field: "gov_is_offer_guar",
                     title: "是否提供担保",
                     width: 80,
                     align: "center",
                     formatter: function (value, rowData, index) {
                         if (value == "1") {
                             return "是";
                         } else {
                             return "否";
                         }
                     }
                 },
                 { field: "gov_contracter", title: "联系人", width: 100, align: "center" },
                 { field: "gov_contract_phone", title: "联系电话", width: 100, align: "center" },
                 { field: "gov_email", title: "公司邮箱", width: 150, align: "center" }
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
    },
    //查询
    Searchs: function () {
        var param = createParam3("SearchItems");
        param = JSON.stringify(param);
        param = param.replace("searchForm", "searchForms");
        param = JSON.parse(param);
        var os = { modelAction: "Search" };
        $.post("/P2PFinancingManage/SendGuarAudit?" + getParam(os), param, function (data) {
            $("#GuaranteeGovListGrid").datagrid("loadData", data);
        }, "json");
    },
    ///发送担保审核
    chooseGuaranteeGov: function () {
        var row = MyGrid.selectRow();
        var rowgov = $('#GuaranteeGovListGrid').datagrid('getSelected');
        var o = { pro_loan_id: row.pro_loan_id, gov_is_offer_guar: rowgov.gov_is_offer_guar, pro_gov_id: rowgov.gov_id };
        if (rowgov.length == 0) {
            Colyn.log("请选择一条记录进行操作");
            return;
        } else {
            $.post("/P2PFinancingManage/SendGuaranteeData?" + getParam(o), function (data) {
                if (data.Success) {
                    Colyn.log("已发送");
                    dialog.dialog("close");
                    MyAction.Init();
                } else {
                    Colyn.log(data.Msg);
                    MyAction.Init();
                }
            }, "json");

        }
    }
};
MyActionForGuaranteeGov.Init();
