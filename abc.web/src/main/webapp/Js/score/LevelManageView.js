/*------------------------------------------------
 * Author:潘健  Date：2014-8-25 
 -------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
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
            url: "/score/json/GetLevelManageList.json?" + param,
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
                    { field: "levId", hidden: true},
                    { field: "levName", title: "等级名称", width: 150, align: "center" },
                    { field: "levMinScore", title: "等级最小积分值", width: 150, align: "center" },
                    { field: "levMaxScore", title: "等级最大积分值", width: 150, align: "center" },
                    { field: "levIcon", title: "等级图片路径", width: 150, align: "center",
	                    formatter: function(value, row, index) {
		                    if (value != "" && value != null) {
		                        return '<img src="' + value + '"/>';
		                    } else {
		                        return "";
		                    }
	                	} 
	                }
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
        var addDialog = $.hDialog({
            href: "/score/LevelManageEdit?flag=view",
            iconCls: 'icon-add',
            title: "添加等级",
            width: 400,
            height: 300,
            buttons: [
                {
                    text: '确认添加',
                    iconCls: 'icon-ok',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                            var param = $("#Colyn").serializeArray();
                            $.AjaxColynJson("/score/LevelManageEdit.json?flag=add", param, function (data) {
                                if (data.success) {
                                    Colyn.log(data.message);
                                    MyAction.Init();
                                } else {
                                    Colyn.log(data.message);
                                }
                            }, "json");
                           addDialog.dialog("close");
                        }
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        addDialog.dialog("close");
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
            href: "/score/LevelManageEdit?levId=" + row.levId +"&flag=modfiy",
            iconCls: 'icon-edit',
            title: "修改等级",
            width: 400,
            height: 300,
            buttons: [
                {
                    text: '确认修改',
                    iconCls: 'icon-edit',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                            var param = $('#Colyn').serializeArray();
                            $.post("/score/LevelManageEdit.json?flag=edit", param, function (data) {
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
                    $.AjaxColynJson("/score/LevelManageEdit.json?flag=del&levId=" + row.levId, function (data) {
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