/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Audit").click(MyAction.Audit);
    $("#CheckIdear").click(MyAction.CheckIdear);
    $("#SearchBtn").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
    MyArea.Init('org_area_noA', 'org_area_noB', 'hdorg_area_no');//初始化地区
    $("#org_area_noA").change(function () { MyArea.AreaChange('org_area_noA', 'org_area_noB'); });
});
var MyAction = {
    Init: function () {
        $("#OrgModifyAuditListGrid").datagrid({
            method: "GET",
            url: "/P2POrgManage/OrgModifyListCheckViewData",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            idField: "gov_id",
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
           {
               field: "gov_user_name", title: "用户名", width: 100, align: "center", formatter: function (value) {
                   if (value == "" || value == null) { return "-"; } else { return value; }
               }
           },
            {
                field: "gov_name", title: "机构名称", width: 150, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "gov_no", title: "组织机构代码", width: 100, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "gov_total_capital", title: "资产总额", width: 100, align: "center", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "gov_max_lend_money", title: "最大借款额度", width: 100, align: "center", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "gov_guar_name", title: "担保机构", width: 150, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "gov_is_offer_guar", title: "是否提供担保", width: 100, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else {
                        return value == "1" ? "是" : "否";
                    }
                }
            },
            {
                field: "gov_contracter", title: "联系人", width: 100, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "gov_contract_phone", title: "联系电话", width: 100, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "gov_email", title: "公司邮箱", width: 150, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "emp_Name", title: "客户经理", width: 100, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "AreaName", title: "所属地区", width: 100, align: "center", formatter: function (value) {
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
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#OrgModifyAuditListGrid').datagrid('getPager');
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
            title: "机构修改查看",
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
    //审核
    Audit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (num.gov_check_state != "0") {
            Colyn.log("已审核的记录不能再次审核");
            return;
        }
        var o = {
            govId: num.gov_id,
            updateNum: num.gov_update_number
        }
        var Dialog = $.hDialog({
            href: "/P2POrgManage/OrgModifyCheckView?" + getParam(o),
            iconCls: 'icon-add',
            title: "机构审核",
            maximizable: true,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '同意',
                iconCls: 'icon-user_magnify',
                handler: function () {
                    var checkTrue = {
                        govId: num.gov_id,
                        updateNum: num.gov_update_number,
                        checkStatus: "1",
                        checkIdear: $("#checkIdear").val()
                    }
                    if ($("#checkIdear").val() == "" || $("#checkIdear").val() == "请输入...") {
                        Colyn.log("审核意见不能为空");
                        return;
                    }
                    $.AjaxColynText('/P2POrgManage/OrgAuditChangeData', checkTrue, function (data) {
                        var data = JSON.parse(data);
                        Colyn.log(data.Msg);
                        if (data.Success) {
                            MyAction.Init();
                            jQuery('#OrgModifyAuditListGrid').datagrid('clearSelections');
                        }
                        Dialog.dialog("close");
                    });
                }
            },
               {
                   text: '不同意',
                   iconCls: 'icon-user_magnify',
                   handler: function () {
                       var checkFalse = {
                           govId: num.gov_id,
                           updateNum: num.gov_update_number,
                           checkStatus: "2",
                           checkIdear: $("#checkIdear").val()
                       }
                       if ($("#checkIdear").val() == "" || $("#checkIdear").val() == "请输入...") {
                           Colyn.log("审核意见不能为空");
                           return;
                       }
                       $.AjaxColynText('/P2POrgManage/OrgAuditChangeData', checkFalse, function (data) {
                           var data = JSON.parse(data);
                           Colyn.log(data.Msg);
                           if (data.Success) {
                               MyAction.Init();
                               jQuery('#OrgModifyAuditListGrid').datagrid('clearSelections');
                           }
                           Dialog.dialog("close");
                       });
                   }
               }, {
                   text: '关闭',
                   iconCls: 'icon-cancel',
                   handler: function () {
                       Dialog.dialog("close");
                   }
               }]
        })
    },
    CheckIdear: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/P2POrgManage/OrgCheckIdearView?" + getParam({ govId: num.gov_id }),
            iconCls: 'icon-add',
            title: "审核意见",
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
    Search: function () {
        if ($('#SearchForm').form('validate')) {
            var param = createParam3("SearchForm");
            var o = { modelAction: "Search" };
            $.post("/P2POrgManage/OrgModifyListCheckView?" + getParam(o), param, function (data) {
                $("#OrgModifyAuditListGrid").datagrid("loadData", data);
                $("#OrgModifyAuditListGrid").datagrid("clearSelections");
            }, "json");
        }
    }
}