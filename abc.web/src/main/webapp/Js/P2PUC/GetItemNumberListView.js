/*------------------------------------------------
 * Author:潘健  Date：2014-09-02
-------------------------------------------------*/


var MyGrid1 = {
    Resize: function () {
        var WH = {
            width: $(window).width(),
            height: $(window).height()
        };
        $('#ItemLayout').width(WH.width - 35).height(WH.height - 80).layout();
        var center = $('#ItemLayout').layout('panel', 'center');
        center.panel({
            onResize: function (w, h) {
                $('.GridView').datagrid('resize', { width: w - 6, height: h - 36 });
            }
        });
    },
    RefreshPanl: function () {
        MyGrid1.Resize();
        $('#ItemLayout').layout('resize');
    },
    selectRow: function () {
        return $('.GridView').datagrid('getSelected');
    }
}


var MyActionForItemNumber = {
    GetParam: function () {
        var Para = {
            isSingleSelect: true,
            isHtml: false,
            nameId: '',
            valueId:'',
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
        $("#ItemNumberListGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PLoanList.json",
            singleSelect: MyActionForItemNumber.GetParam().isSingleSelect,
            //sortOrder: 'desc',
            height: $(window).height() - 140,
            rownumbers: true,
            animate: true,
            collapsible: false,
            //idField: 'emp_Id',
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
                 { field: "loan_no", title: "项目名称", width: 150, align: "center" },
            { field: "loan_type", title: "项目类型", width: 150, align: "center" },
            { field: "loan_emp", title: "借款人", width: 150, align: "center" },
            { field: "loan_money", title: "借款金额", width: 150, align: "right" },
            { field: "loan_rate", title: "年化收益率", width: 150, align: "right" },
            { field: "loan_period", title: "借款期限", width: 150, align: "center" },
            {
                field: "loan_use", title: "借款用途", width: 150, align: "center", formatter: function (value, rowData, index) {
                    return CutString(value, 10);
                }
            },
            { field: "loan_step", title: "项目进度", width: 150, align: "right" },
            { field: "pay_type", title: "还款方式", width: 150, align: "center" },
            { field: "apply_date", title: "申请日期", width: 150, align: "center" },
            { field: "guar_gov", title: "担保机构", width: 150, align: "center" }

            ]],
            pagination: true,
            onDblClickRow: function (rowIndex, row) {
               // MyActionForItemNumber.ChooseItemNumber();
            }
        })
       
    },
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search", duty: $("#hdfDuty").val() };
        $.post("/DBUC/GetEmployeeList?" + getParam(o), param, function (data) {
            $("#ItemNumberListGrid").datagrid("loadData", data);
        }, "json");
    },
  
}

$(function () {

    MyGrid1.Resize();
    MyActionForItemNumber.Init();
    $(window).resize(function () {
        MyGrid1.RefreshPanl();
    });
    $("#searEmp").click(function () {
        MyActionForItemNumber.Search();
    });
});
