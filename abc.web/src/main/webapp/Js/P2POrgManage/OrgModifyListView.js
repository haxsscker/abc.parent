/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#SearchBtn").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});
var MyAction = {
    Init: function () {
        var o = createParam3("SearchForm");
        $("#ModifyRecordListGrid").datagrid({
            method: "GET",
            url: "/P2POrgManage/OrgModifyListCheck?" + o,
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            idField: "history_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            {
                field: "eEditName", title: "修改人", width: 150, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "gov_edit_date", title: "修改日期", width: 150, align: "center", formatter: function (value) {
                    return convertToDate(value, "yyyy-MM-dd");
                }
            },
            {
                field: "eAuditName", title: "审核人", width: 150, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "gov_check_date", title: "审核日期", width: 150, align: "center", formatter: function (value) {
                    return convertToDate(value, "yyyy-MM-dd");
                }
            },
            {
                field: "gov_check_idear", title: "审核意见", width: 150, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return CutString(value, 10); }
                }
            },
            {
                field: "gov_check_state", title: "审核状态", width: 150, align: "center", formatter: function (value) {
                    switch (value) {
                        case "0":
                            return "待审核";
                        case "1":
                            return "审核已通过";
                        case "2":
                            return "审核未通过";
                    }
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#ModifyRecordListGrid').datagrid('getPager');
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
    //查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = {
            govId: num.gov_id,
            updateNum: num.gov_update_number
        }
        var Dialog = $.hDialog({
            href: "/P2POrgManage/OrgLookUpView?" + getParam(o),
            iconCls: 'icon-add',
            title: "机构审核",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })
    },
    //搜索
    Search: function () {
        if ($('#SearchForm').form('validate')) {
            var param = createParam3("SearchForm");
            var o = { modelAction: "Search" };
            $.post("/P2POrgManage/OrgModifyListCheck?" + getParam(o), param, function (data) {
                $("#ModifyRecordListGrid").datagrid("loadData", data);
                $("#ModifyRecordListGrid").datagrid("clearSelections");
            }, "json");
        }
    }
}