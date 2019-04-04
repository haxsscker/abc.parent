/*------------------------------------------------
 * Author:沈帅  Date：2014年12月26日22:22:13
-------------------------------------------------*/

var MyGrid1 = {
    Resize: function () {
        var WH = {
            width: '700px',
            height: $(window).height()
        };
        $('#UserAccountLayout').width(WH.width - 35).height(WH.height - 80).layout();
        var center = $('#UserAccountLayout').layout('panel', 'center');
        center.panel({
            onResize: function (w, h) {
                $('.GridView').datagrid('resize', { width: w - 6, height: h - 36 });
            }
        });
    },
    RefreshPanl: function () {
        MyGrid1.Resize();
        $('#UserAccountLayout').layout('resize');
    },
    selectRow: function () {
        return $('.GridView').datagrid('getSelected');
    }
}


var MyActionForUserAcc = {
    GetParam: function () {
        var Para = {
            isSingleSelect: true,
            isHtml: false,
            name: '',       //用户名称
            nameId: '',     //用户ID
            backBank: '',   //开户账户
            userPhone: '',  //手机号码
        };
        var param = $("#hdfDuty").val();
        var paramArr = param.split('|');
        if (paramArr.length == 4) {
            Para.name = paramArr[0];
            Para.nameId = paramArr[1];
            Para.backBank = paramArr[2];
            Para.userPhone = paramArr[3];
        } 
        return Para;
    },
   
    Init: function () {
        $("#UserAccListGrid").datagrid({
             
            url: "/account/json/UserAccountList.json?t=" + new Date(),
            singleSelect: MyActionForUserAcc.GetParam().isSingleSelect,
            //sortOrder: 'desc',
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: 'act_account_id',//账户开户表主键
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            fitColumns: true,
            frozenColumns: [[
                { field: 'act_user_id', checkbox: true },
            ]],
            columns: [[
            {
                field: "act_user_name", title: "用户名", width: 100, align: "center", formatter: function (value, rowData, index) {
                    return CutString(value, 6);
                }
            },
            { field: "act_user_type", title: "用户类型", width: 100, align: "center" },
            { field: "act_account_no", title: "开户账户", width: 100, align: "center" },
            { field: "act_user_phone", title: "手机号码", width: 100, align: "center" }
            ]],
            pagination: true,
            onDblClickRow: function (rowIndex, row) {
                MyActionForUserAcc.chooseAdminMa();
            }
        })
        var p = $('#UserAccListGrid').datagrid('getPager');
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
    Search: function () {
        var param = createSearchParam("SearchForm");
        var o = { modelAction: "Search", duty: $("#hdfDuty").val() };
        $.post("/account/json/UserAccountList.json?" + getParam(o), param, function (data) {
            $("#UserAccListGrid").datagrid("loadData", data);
            $("#UserAccListGrid").datagrid("clearSelections");
        }, "json");
    },
    chooseUserAcc: function () {
        var rows = $('#UserAccListGrid').datagrid('getSelections');
        if (rows.length == 0) { Colyn.log("请选择用户账户信息"); return; }
        var params = MyActionForUserAcc.GetParam();
        console.log(params);
        if (params.isHtml) {

        } else {
            var name = '';
            var nameId = '';
            var backBank = '';
            var userPhone = '';
            for (var i = 0; i < rows.length; i++) {
                if (i == rows.length - 1) {
                    name += rows[i].act_user_name;
                    nameId += rows[i].act_user_id;
                    backBank += rows[i].act_account_no;
                    userPhone += rows[i].act_user_phone;
                } else {
                    name += rows[i].act_user_name + ",";
                    nameId += rows[i].act_user_id + ",";
                    backBank += rows[i].act_account_no + ",";
                    userPhone += rows[i].act_user_phone + ",";
                }
            }
            $("#" + params.name).val(name);
            $("#" + params.nameId).val(nameId);
            $("#" + params.backBank).val(backBank);
            $("#" + params.userPhone).val(userPhone);
            dialog.dialog("close");
        }

    }
}

$(function () {

    MyGrid1.Resize();
    MyActionForUserAcc.Init();
    $(window).resize(function () {
        MyGrid1.RefreshPanl();
    });
    $("#searEmp").click(function () {
        MyActionForUserAcc.Search();
    });
});
function createSearchParam() {
    var form = {
    	act_user_name : $("#act_user_name").val(),
    	act_account_no:$("#act_account_no").val(),
    	act_user_phone : $("#act_user_phone").val(),
    	act_user_type :$("#act_user_type").val()
    };

    return getParam(form);
}