/*------------------------------------------------
 * Author:徐大龙  Date：2014-11-3 13:31:07
 -------------------------------------------------*/
//账户维护菜单下，添加账户时选择机构的列表页
var MyGrid1 = {
    Resize: function () {
        var WH = {
            width: $(window).width(),
            height: $(window).height()
        };
        $('#AllGovLayout').width(WH.width - 35).height(WH.height - 80).layout();
        var center = $('#AllGovLayout').layout('panel', 'center');
        center.panel({
            onResize: function (w, h) {
                $('.GridView').datagrid('resize', { width: w - 6, height: h - 36 });
            }
        });
    },
    RefreshPanl: function () {
        MyGrid1.Resize();
        $('#AllGovLayout').layout('resize');
    },
    selectRow: function () {
        return $('.GridView').datagrid('getSelected');
    }
}

var MyActionForAllGov = {
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
        MyGrid1.Resize();
        $('#AllGovLayout').layout('resize');
    },
    Init: function () {
        $("#AllGovListGrid").datagrid({
            method: "GET",
            url: "/government/json/getGovList.json",
            singleSelect: MyActionForAllGov.GetParam().isSingleSelect,
            //sortOrder: 'desc',
            height: $(window).height() - 140,
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: 'govId',
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            fitColumns: false,
            columns: [
                [
                    { field: "govName", title: "机构名称", width: 180, align: "center" },
                    { field: "govNo", title: "组织机构代码", width: 150, align: "center" },
                    {
                        field: "govIsOfferGuar", title: "机构类别", width: 80, align: "center", formatter: function (value) {
                        if (value == "1") {
                            return "担保机构";
                        } else {
                            return "小贷机构";
                        }
                    }
                    },
                    {
                        field: "govArea", title: "所属地区", width: 120, align: "center", formatter: function (value) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        }
                    }
                    },
                    {
                        field: "govTotalCapital", title: "资产总额", width: 120, align: "center", formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }
                    }
                ]
            ],
            pagination: true,
            onDblClickRow: function (rowIndex, row) {
                MyActionForAllGov.chooseLendingGov();
            }
        })

    },
    Search: function () {
        var param = createParam3("SearchItem");
        var o = { modelAction: "Search", duty: $("#hdfDuty").val() };
        $.post("/government/json/getGovList.json?"+ getParam(o), param, function (data) {
            $("#AllGovListGrid").datagrid("loadData", data);
        }, "json");
    },
    chooseAllGov: function () {
        var rows = $('#AllGovListGrid').datagrid('getSelections');
        if (rows.length == 0) {
            Colyn.log("请选择机构");
            return;
        }
        var params = MyActionForAllGov.GetParam();
        console.log(params)
        if (params.isHtml) {
            var Names = '';
            for (var i = 0; i < rows.length; i++) {
                if (i == rows.length - 1) {
                    Names += rows[i].govName;
                } else {
                    Names += rows[i].govName + ",";
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
                    Names += rows[i].govName;
                    ids += rows[i].govId;
                } else {
                    Names += rows[i].govName + ",";
                    ids += rows[i].govId + ",";
                }
            }
            $("#" + params.nameId).val(Names);
            $("#" + params.nameId).attr("readonly", true);
            $("#" + params.valueId).val(ids);
            dialog.dialog("close");
        }

    }
};

// TODO 下面注释掉的代码在“融资维护”页面不起作用，但在其他页面可以，不知道为什么，留待以后解决
//$(function () {
//    MyGrid1.Resize();
//    MyActionForAllGov.Init();
//    $(window).resize(function () {
//        MyGrid1.RefreshPanl();
//    });
//    $("#searEmp").click(function () {
//        MyActionForAllGov.Search();
//    });
//
//    MyArea.Init('area_noA', 'area_noB', 'hd_area_no');//初始化地区
//    $("#area_noA").change(function () { MyArea.AreaChange('area_noA', 'area_noB'); });
//});

$();
(function () {
    MyGrid1.Resize();
    MyActionForAllGov.Init();
    $(window).resize(function () {
        MyGrid1.RefreshPanl();
    });
    $("#searEmp").click(function () {
        MyActionForAllGov.Search();
    });

    MyArea.Init('area_noA', 'area_noB', 'hd_area_no');//初始化地区
    $("#area_noA").change(function () { MyArea.AreaChange('area_noA', 'area_noB'); });
})();
