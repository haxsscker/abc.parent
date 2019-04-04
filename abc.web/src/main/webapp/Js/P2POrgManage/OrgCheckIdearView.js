/*------------------------------------------------
 * Author:李冬冬  Date：2014-8-19 
 * Editor:徐大龙  Date：2014-10-20
-------------------------------------------------*/


var MyAction = {
    Init: function () {
        $("#OrgCheckIdearGrid").datagrid({
            method: "GET",
            url: "/P2POrgManage/OrgCheckIdearData?" + getParam({ govId: $("#govId").val() }),
            height: 370,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            idField: "gov_id",
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            {
                field: "gov_name", title: "机构名称", width: 150, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "gov_edit_date", title: "修改日期", width: 100, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return convertToDate(value, "yyyy-MM-dd"); }
                }
            },
            {
                field: "gov_check_state", title: "审核状态", width: 100, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else {
                        switch (value) {
                            case "0":
                                return "待审核";
                                break;
                            case "1":
                                return "审核已通过";
                                break;
                            case "2":
                                return "审核未通过";
                                break;

                        }
                    }
                }
            },
            {
                field: "gov_check_date", title: "审核日期", width: 100, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return convertToDate(value, "yyyy-MM-dd"); }
                }
            }, {
                field: "gov_check_idear", title: "审核意见", width: 150, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#OrgCheckIdearGrid').datagrid('getPager');
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


