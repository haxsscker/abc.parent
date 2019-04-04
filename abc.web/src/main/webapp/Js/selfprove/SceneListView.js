/*------------------------------------------------
 * Author:潘健  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#Audit").click(MyAction.Audit);
    $("#btnSearch").click(MyAction.Search);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        var param = createParam3("SearchForm");
        $("#SceneListGrid").datagrid({
            url: "/P2PSelfProve/SceneListView?" + param,
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "cst_user_name", title: "客户名称", width: 150, align: "center" },
            { field: "cst_real_name", title: "真实姓名", width: 150, align: "center" },
            {
                field: "cst_prove_state", title: "认证状态", width: 150, align: "center",
                formatter: function (v, r, i) {
                    return GetStatus(v);
                }
            },
            {
                field: "cst_apply_date", title: "申请日期", width: 150, align: "center",
                formatter: function (v, r, i) {
                    return GetFormatDate(v);
                }
            },
            { field: "emp_Name", title: "审核人", width: 150, align: "center" },
            {
                field: "cst_check_idear", title: "审核意见", width: 150, align: "center",
                formatter: function (value, rowData, index) {
                    return CutString(value, 10);
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#SceneListGrid').datagrid('getPager');
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
    //审核
    Audit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (row.cst_prove_state == "1" || row.cst_prove_state == "2") {
            Colyn.log("认证已审核");
            return;
        }
        var o = { csiId: row.cst_scene_id };
        var Dialog = $.hDialog({
            href: "/P2PSelfProve/SceneExamineView?" + getParam(o),
            iconCls: 'icon-add',
            title: "现场认证",
            maximizable: true,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '同意',
                iconCls: 'icon-user_magnify',
                handler: function () {
                    MyAction.ToAudit(row.cst_scene_id, 1);
                    Dialog.dialog("close");
                }
            },
               {
                   text: '不同意',
                   iconCls: 'icon-user_magnify',
                   handler: function () {
                       MyAction.ToAudit(row.cst_scene_id, 2);
                       Dialog.dialog("close");
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
    ToAudit: function (csiId, checkStatus) {
        if ($('#Colyn').form('validate')) {
            var o = {
                csiId: csiId,
                //积分类型为实名认证
                scoreTypeId: 1,
                checkStatus: checkStatus,
                checkIdear: $("#check_idear").val()
            };
            $.AjaxColynJson("/P2PSelfProve/SceneExamineData?" + getParam(o), function (data) {
                if (data.Success) {
                    Colyn.log("审核成功！");
                }
                else {
                    Colyn.log(data.Msg);
                }
                MyAction.Init();
                $('#RealNameListGrid').datagrid('clearSelections');

            });
        }
    },
    //搜索
    Search: function () {
        var param = createParam3("SearchForm");
        $.post("/P2PSelfProve/SceneListView", param, function (data) {
            $("#SceneListGrid").datagrid("loadData", data)
        }, 'json');
    }
}