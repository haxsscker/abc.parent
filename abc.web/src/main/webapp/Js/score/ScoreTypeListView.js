/*------------------------------------------------
 * Author:潘健  Date：2014-8-25 
 -------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
//    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
//    $("#Del").click(MyAction.Del);
    $("#Search").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        var param = createParam3("SearchForm");
        $("#IntegralTypesListGrid").datagrid({
            method: "GET",
            url: "/score/json/getScoreItemList.json?" + param,
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
            columns: [
                [
                    { field: "scoreId", hidden: true},
                    { field: "scoreName", title: "类型名称", width: 150, align: "center" },
                    { field: "scoreCode", title: "类型代码", width: 150, align: "center" },
                    { field: "scoreValue", title: "积分", width: 150, align: "center" }
                ]
            ],
            pagination: true,
            singleSelect: true
        })
        var p = $('#IntegralTypesListGrid').datagrid('getPager');
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
    Add: function () {
        var Dialog = $.hDialog({
            href: "/score/scoreTypeAddOrEditView",
            iconCls: 'icon-add',
            title: "添加积分类型",
            width: 400,
            height: 300,
            buttons: [
                {
                    text: '确认添加',
                    iconCls: 'icon-edit',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                            var param = $('#Colyn').serializeArray();
                            param = convertArray(param);
                            console.log(param);
                            $.AjaxColynJson("/score/json/addScoreItem.json", param, function (data) {
                                if (data.success) {
                                    Colyn.log(data.message);
                                    MyAction.Init();
                                } else {
                                    Colyn.log(data.message);
                                }
                            }, "json");
                            Dialog.dialog("close");
                        }
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        Dialog.dialog("close");
                    }
                }
            ]
        })
    },
    Edit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/score/scoreTypeAddOrEditView?" + getParam({ scoreId: row.scoreId, editFlag: true}),
            iconCls: 'icon-edit',
            title: "修改积分类型",
            width: 400,
            height: 300,
            buttons: [
                {
                    text: '确认修改',
                    iconCls: 'icon-edit',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                            var param = $('#Colyn').serializeArray();
                            $.post("/score/json/modifyScoreItem.json?scoreId=" + row.scoreId, param, function (data) {
                                if (data.success) {
                                    Colyn.log(data.message);
                                    MyAction.Init();
                                } else {
                                    Colyn.log(data.message);
                                }
                            }, "json");
                            Dialog.dialog("close");
                        }
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        Dialog.dialog("close");
                    }
                }
            ]
        })
    },
    Del: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r) {
                    $.AjaxColynJson("/score/json/removeScoreItem.json?scoreId=" + row.scoreId, function (data) {
                        if (data.success) {
                            Colyn.log(data.message);
                        }
                        else {
                            Colyn.log(data.message);
                        }
                        MyAction.Init();
                    })
                }
            })
        }
        else {
            Colyn.log("请选择一条记录进行操作");
        }
        return;
    },
    Search: function () {
        var param = createParam3("SearchForm");
        $.post("/score/json/getScoreItemList.json?t=" + new Date, param, function (data) {
            $("#IntegralTypesListGrid").datagrid("loadData", data);
        }, "json");
    }
}