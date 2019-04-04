/*------------------------------------------------
 * Author:徐大龙  Date：2014-09-02
-------------------------------------------------*/

var MyGrid1 = {
    Resize: function () {
        var WH = {
            width: $(window).width(),
            height: $(window).height()
        };
        $('#AdminMaLayout').width(WH.width - 35).height(WH.height - 80).layout();
        var center = $('#AdminMaLayout').layout('panel', 'center');
        center.panel({
            onResize: function (w, h) {
                $('.GridView').datagrid('resize', { width: w - 6, height: h - 36 });
            }
        });
    },
    RefreshPanl: function () {
        MyGrid1.Resize();
        $('#AdminMaLayout').layout('resize');
    },
    selectRow: function () {
        return $('.GridView').datagrid('getSelected');
    }
}


var MyActionForAdminMa = {
    GetParam: function () {
        var Para = {
            isSingleSelect: true,
            isHtml: false,
            nameId: '',
            valueId: ''
        };
        var param = $("#hdfDuty").val();
        var paramArr = param.split('|');
        if (paramArr.length > 2) {
            if (paramArr[0].toLowerCase() == "false") {
                Para.isSingleSelect = false;
            }
            if (paramArr[1].toLowerCase() == "true") {
                Para.isHtml = true;
            }
            Para.nameId = paramArr[2];
            Para.valueId = paramArr[3];
        } else {
            Para.nameId = paramArr[0];
            Para.valueId = paramArr[1];
        }
        return Para;
    },
    RefreshPan: function () {
        // MyGrid1.Resize();
        // $('#empLayout').layout('resize');
    },
    Init: function () {
        $("#AdminMaListGrid").datagrid({
            method: "GET",
//            url: "/P2PSystemManage/AdminManageListForChoose",
            url: "/employee/json/getEmployeeList.json",
            singleSelect: MyActionForAdminMa.GetParam().isSingleSelect,
            //sortOrder: 'desc',
            height: $(window).height() - 140,
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: 'empId',
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            fitColumns: true,
            //frozenColumns: [[
            //    //{ field: 'ck', checkbox: true},
            //     {
            //         field: 'id',
            //         width: 30,
            //         formatter: function (value, rowData, rowIndex) {
            //             //return '<input type="radio" name="selectRadio" id="selectRadio"' + rowIndex + '    value="' + rowData.id + '" />';
            //             return '';
            //         },
            //         align: "center"
            //     },
            //]],
            columns: [[
            { field: "empName", title: "客户经理用户名", width: 100, align: "center" },
            {
                field: "empMobile", title: "手机号码", width: 100, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "empDocNo", title: "证件号码", width: 100, align: "center", formatter: function (value) {
                    if (value == "" || value == null) { return "-"; } else { return value; }
                }
            },
            {
                field: "empState", title: "用户状态", width: 50, align: "center", formatter: function (value) {
                    if (value == "1") { return '已启用'; } else { return '已停用'}
                }
            }
            ]],
            pagination: true,
            onDblClickRow: function (rowIndex, row) {
                MyActionForAdminMa.chooseAdminMa();
            }
        })

    },
    Search: function () {
        var param = createParam3("SearchItem");
//        param = JSON.stringify(param);
//        param = param.replace("searchForm", "SearchItem");
//        param = JSON.parse(param);
        var o = { modelAction: "Search", duty: $("#hdfDuty").val() };
        $.post("/employee/json/searchEmployeeList.json?" + getParam(o), param, function (data) {
            $("#AdminMaListGrid").datagrid("loadData", data);
            $("#AdminMaListGrid").datagrid("clearSelections");
        }, "json");
    },
    chooseAdminMa: function () {
        var rows = $('#AdminMaListGrid').datagrid('getSelections');
        if (rows.length == 0) { Colyn.log("请选择客户经理"); return; }
        var params = MyActionForAdminMa.GetParam();
        if (params.isHtml) {
            var Names = '';
            for (var i = 0; i < rows.length; i++) {
                if (i == rows.length - 1) {
                    Names += rows[i].empName;
                } else {
                    Names += rows[i].empName + ",";
                }
            }
            $("#" + params.nameId).val(Names);
            $("#" + params.nameId).attr("readonly", true);
            dialog.dialog("close");
        } else {
            var Names = '';
            var ids = '';
            for (var i = 0; i < rows.length; i++) {
                if (i == rows.length - 1) {
                    Names += rows[i].empName;
                    ids += rows[i].empId;
                } else {
                    Names += rows[i].empName + ",";
                    ids += rows[i].empId + ",";
                }
            }
            $("#" + params.nameId).val(Names);
            $("#" + params.nameId).attr("readonly", true);
            $("#" + params.valueId).val(ids);
            dialog.dialog("close");
        }

    }
}

$(function () {

    MyGrid1.Resize();
    MyActionForAdminMa.Init();
    $(window).resize(function () {
        MyGrid1.RefreshPanl();
    });
    $("#searEmp").click(function () {
        MyActionForAdminMa.Search();
    });
});
