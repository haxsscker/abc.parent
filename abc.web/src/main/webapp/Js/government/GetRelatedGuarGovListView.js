var MyGrid1 = {
    Resize: function () {
        var WH = {
            width: $(window).width(),
            height: $(window).height()
        };
        $('#GuaranteeGovLayout').width(WH.width - 35).height(WH.height - 80).layout();
        var center = $('#GuaranteeGovLayout').layout('panel', 'center');
        center.panel({
            onResize: function (w, h) {
                $('.GridView').datagrid('resize', { width: w - 6, height: h - 36 });
            }
        });
    },
    RefreshPanl: function () {
        MyGrid1.Resize();
        $('#GuaranteeGovLayout').layout('resize');
    },
    selectRow: function () {
        return $('.GridView').datagrid('getSelected');
    }
}


var MyActionForGuaranteeGov = {
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
        var gov_id = $("#govId").val();
        var o = {
            govId: gov_id
        }
        $("#GuaranteeGovListGrid").datagrid({
            method: "GET",
            url: "/government/json/GetRelatedGuarGovList.json?" + getParam(o),
            singleSelect: MyActionForGuaranteeGov.GetParam().isSingleSelect,
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
            columns: [
                [

                    { field: "govName", title: "机构名称", width: 150, align: "center" },
                    { field: "govNo", title: "组织机构代码", width: 150, align: "center" },
                    {
                        field: "govRegisterCapital", title: "注册资金", width: 150, align: "right", formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }
                    },
                    {
                        field: "govMaxGuarAmount", title: "最大担保额度", width: 150, align: "right", formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }
                    },
                    {
                        field: "govSettGuarAmount", title: "可担保额度", width: 150, align: "right", formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }
                    },
                ]
            ],
            pagination: true,
            onDblClickRow: function (rowIndex, row) {
                MyActionForGuaranteeGov.chooseGuaranteeGov();
            }
        })

    },
    Search: function () {
        if ($('#SearchItems').form('validate')) {
            var o = { govName: $("#govName").val()};
            $.post("/government/json/GetRelatedGuarGovList.json?" + getParam(o), function (data) {
                $("#GuaranteeGovListGrid").datagrid("loadData", data);
                $("#GuaranteeGovListGrid").datagrid("clearSelections");
            }, "json");
        }
    },
    chooseGuaranteeGov: function () {
        var rows = $('#GuaranteeGovListGrid').datagrid('getSelections');
        if (rows.length == 0) {
            Colyn.log("请选择担保机构");
            return;
        }
        var params = MyActionForGuaranteeGov.GetParam();
        if (params.isHtml) {
            var Names = '';
            var Ids = '';
            var NamesHtml = '';
            for (var i = 0; i < rows.length; i++) {
                Names += rows[i].govName + ",";
                Ids += rows[i].govId + ",";
                NamesHtml += '<span>' + rows[i].govName + '</span><img src="../../Images/icon/16/bullet_cross.png" _name="' + rows[i].govName + '" _id="' + rows[i].govId + '" onclick="DelRow(this)"/>';
            }
            $("#" + params.nameId).val(Names);
            $("#" + params.valueId).val(Ids);
            $("#" + params.govId).attr("readonly", true);
            $("#show_names").html(NamesHtml);
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
    },
    DelRows: function () {
        $(this).parents('img:first').remove();
        $(this).parents('span:first').remove();
    }
};

$(function () {
    MyGrid1.Resize();
    MyActionForGuaranteeGov.Init();
    $(window).resize(function () {
        MyGrid1.RefreshPanl();
    });
    $("#searEmp").click(function () {
        MyActionForGuaranteeGov.Search();
    });
    $('.DelRows').unbind('click').live('click', function () {
        MyActionForGuaranteeGov.DelRows();
    });
});
