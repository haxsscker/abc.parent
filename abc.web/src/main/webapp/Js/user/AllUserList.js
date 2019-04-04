/*------------------------------------------------
 * Author:徐大龙  Date：2014-09-02
 -------------------------------------------------*/

var MyGrid1 = {
    Resize: function () {
        var WH = {
            width: $(window).width(),
            height: $(window).height()
        };
        $('#UserLayout').width(WH.width - 35).height(WH.height - 80).layout();
        var center = $('#UserLayout').layout('panel', 'center');
        center.panel({
            onResize: function (w, h) {
                $('.GridView').datagrid('resize', { width: w - 6, height: h - 36 });
            }
        });
    },
    RefreshPanl: function () {
        MyGrid1.Resize();
        $('#UserLayout').layout('resize');
    },
    selectRow: function () {
        return $('.GridView').datagrid('getSelected');
    }
}


var MyActionForUser = {
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
    	alert("yehui ");
        $("#UserListGrid").datagrid({
            method: "GET",
            // url: "/user/json/getUserList.json?categoryValue=" + categoryValue,
            url: "/user/json/getUserList.json",
            singleSelect: MyActionForUser.GetParam().isSingleSelect,
            //sortOrder: 'desc',
            height: $(window).height() - 140,
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: 'userId',
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
            columns: [
                [
                    { field: "userId", hidden: true },
                    { field: "userName", title: "用户名", width: 100, align: "center" },
                    {
                        field: "userRealName", title: "用户真实姓名", width: 100, align: "center", formatter: function (value) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        }
                    }
                    },
                    {
                        field: "userPhone", title: "手机号码", width: 100, align: "center", formatter: function (value) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        }
                    }
                    },
                    { field: "userType", title: "用户类型", width: 100, align: "center" },
                    { field: "userDocType", title: "证件类型", width: 100, align: "center" },
                    { field: "userDocNo", title: "证件号码", width: 100, align: "center" },
                    { field: "userSex", title: "性别", width: 100, align: "center" },
//                ,
//            {
//                field: "userType", title: "用户类型", width: 50, align: "center", formatter: function (value) {
//                    if (value == "1") { return '已启用'; } else { return '已停用'}
//                }
//            }
                ]
            ],
            pagination: true,
            onDblClickRow: function (rowIndex, row) {
                MyActionForUser.chooseUser();
            }
        })

    },
    Search: function () {
//        var param = createParam2("SearchItem");
//        param = JSON.stringify(param);
//        param = param.replace("searchForm", "SearchItem");
//        param = JSON.parse(param);
//        var o = { modelAction: "Search", duty: $("#hdfDuty").val() };
//        $.post("/user/json/getUserList.json?" + getParam(o), param, function (data) {
        var o = { userName: $("#userName").val() };
        $.post("/user/json/getUserList.json?" + getParam(o), function (data) {
            $("#UserListGrid").datagrid("loadData", data);
            $("#UserListGrid").datagrid("clearSelections");
        }, "json");
    },
    chooseUser: function () {
        var rows = $('#UserListGrid').datagrid('getSelections');
        if (rows.length == 0) {
            Colyn.log("请选择借款人");
            return;
        }
        var params = MyActionForUser.GetParam();
        if (params.isHtml) {
            var Names = '';
            for (var i = 0; i < rows.length; i++) {
                if (i == rows.length - 1) {
                    Names += rows[i].userRealName;
                } else {
                    Names += rows[i].userRealName + ",";
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
                    Names += rows[i].userName;
                    ids += rows[i].userId;
                } else {
                    Names += rows[i].userName + ",";
                    ids += rows[i].userId + ",";
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
    MyActionForUser.Init();
    $(window).resize(function () {
        MyGrid1.RefreshPanl();
    });
    $("#searchUser").click(function () {
        MyActionForUser.Search();
    });
});
