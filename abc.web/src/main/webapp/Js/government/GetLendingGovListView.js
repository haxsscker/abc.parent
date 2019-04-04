/*------------------------------------------------
 * Author:潘健  Date：2014-09-02
-------------------------------------------------*/

var MyGrid1 = {
    Resize: function () {
        var WH = {
            width: $(window).width(),
            height: $(window).height()
        };
        $('#LendingGovLayout').width(WH.width - 35).height(WH.height - 80).layout();
        var center = $('#LendingGovLayout').layout('panel', 'center');
        center.panel({
            onResize: function (w, h) {
                $('.GridView').datagrid('resize', { width: w - 6, height: h - 36 });
            }
        });
    },
    RefreshPanl: function () {
        MyGrid1.Resize();
        $('#LendingGovLayout').layout('resize');
    },
    selectRow: function () {
        return $('.GridView').datagrid('getSelected');
    }
}


var MyActionForLendingGov = {
    GetParam: function () {
        var Para = {
            isSingleSelect: true,
            isHtml: false,
            nameId: '',
            valueId:''
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
            Para.nameId   = paramArr[2];
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
        var gov_id = $("#govId").val();
        var o = {
            govId: gov_id
        }
        $("#LendingGovListGrid").datagrid({
            method: "GET",
            url: "/government/json/GetLendingGovList.json?"+getParam(o),
            singleSelect: MyActionForLendingGov.GetParam().isSingleSelect,
            //sortOrder: 'desc',
            height: $(window).height() - 140,
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: 'govId',
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            fitColumns: true,
            frozenColumns: [[
                //{ field: 'ck', checkbox: true},
                 {
                     field: 'id',
                    width: 30,
                    formatter: function (value, rowData, rowIndex) {
                        return '<input type="radio" name="selectRadio" id="selectRadio"' + rowIndex + '    value="' + rowData.id + '" />';
                    },
                    align: "center"
                },
            ]],
            columns: [[
            { field: "govName", title: "机构名称", width: 150, align: "center" },
            { field: "govNo", title: "组织机构代码", width: 150, align: "center" },
            { field: "govTotalCapital", title: "资产总额", width: 150, align: "center" },
            { field: "govContact", title: "联系人", width: 150, align: "center" },
            { field: "govGuarName", title: "担保机构", width: 150, align: "center" },
            { field: "govContactPhone", title: "联系电话", width: 150, align: "center" },
            { field: "govEmail", title: "公司邮箱", width: 150, align: "center" },
            { field: "govCustomerManagerName", title: "客户经理", width: 150, align: "center" },
            { field: "govArea", title: "所属地区", width: 150, align: "center" },
            ]],
            pagination: true,
            onDblClickRow: function (rowIndex, row) {
                MyActionForLendingGov.chooseLendingGov();
            }
        })
       
    },
    Search: function () {
//        var param = createParam3("SearchForm");
//        var o = { modelAction: "Search", duty: $("#hdfDuty").val() };
        var o = { govName: $("#govName").val()};
//        $.post("/DBUC/GetEmployeeList?" + getParam(o), param, function (data) {
        $.post("/government/json/GetLendingGovList.json?"+getParam(o), function (data) {
            $("#LendingGovListGrid").datagrid("loadData", data);
        }, "json");
    },
    chooseLendingGov: function () {
        var rows = $('#LendingGovListGrid').datagrid('getSelections');
        if (rows.length == 0) { Colyn.log("请选择借款机构"); return; }
        var params = MyActionForLendingGov.GetParam();
        if (params.isHtml) {
            var Names = '';
            for (var i = 0; i < rows.length; i++) {
                if (i == rows.length - 1) {
                    Names += rows[i].emp_Name;
                } else {
                    Names += rows[i].emp_Name + ",";
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
                    Names += rows[i].emp_Name;
                    ids += rows[i].emp_Id;
                } else {
                    Names += rows[i].emp_Name + ",";
                    ids += rows[i].emp_Id + ",";
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
    MyActionForLendingGov.Init();
    $(window).resize(function () {
        MyGrid1.RefreshPanl();
    });
    $("#searEmp").click(function () {
        MyActionForLendingGov.Search();
    });
});
