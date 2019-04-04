/*------------------------------------------------
 * Author:潘健  Date：2014-8-18
-------------------------------------------------*/
$(function () {
    var Dialog;
    MyGrid.Resize();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });

});
var MyAction = {
    Init: function () {
        $("#ForumPlate").datagrid({
            method: "Post",
            url: "/P2PForumPlate/P2PForumPlateList",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview, //重写当没有数据时
            emptyMsg: '没有找到数据', //返回数据字符
            columns: [
            [
            {
                field: "new_plate_name", title: "板块名称", width: 250, align: "center", formatter: function (value, rowData, index) {
                    if (value == "" || value == null) { return "-"; } else { return CutString(value, 10); }
                }
            },
            {
                field: 'new_plate_pic',
                title: '板块图标',
                width: 250,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<img src="' + value + '" width="100" height="30"/>';
                    } else {
                        return "";
                    }
                }
            },
            { field: "emp_NickName", title: "创建人", width: 250, align: "center" },
            {
                field: "new_add_date", title: "创建日期", width: 250, align: "center", formatter: function (value, rowData, index) {
                    return convertToDate(value, "yyyy-MM-dd");
                }
            }

            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#ForumPlate').datagrid('getPager');
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
        var dialog = $.hDialog({
            href: "/P2PForumPlate/P2PForumPlateAddOrEdit",
            iconCls: 'icon-add',
            title: "板块添加",
            // maximizable: true,//显示最大化
            width: 400,//$(window).width() - 50,
            height: 300,//$(window).height() - 50,
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    var param = $("#Colyn").serializeArray();
                    if ($("#Colyn").form("validate")) {
                        $.post("/P2PForumPlate/P2PForumPlateAddOrEditData", param, function (data) {
                            if (data.Success) {
                                Colyn.log(data.Msg);
                                dialog.dialog("close");
                                MyAction.Init();
                            } else {
                                Colyn.log(data.Msg);
                            }
                           
                        }, "json");
                    }
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dialog.dialog("close");
                }
            }]

        })

    },
    Edit: function () {
        var row = MyGrid.selectRow();
        if (row) {
           var Dialoga = $.hDialog({
               href: "/P2PForumPlate/P2PForumPlateAddOrEdit?new_plate_id=" + row.new_plate_id,
                iconCls: 'icon-pencil',
                title: "板块修改",
                // maximizable: true,//显示最大化
                width: 400,//$(window).width() - 50,
                height: 300,//$(window).height() - 50,
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
                        var param = $("#Colyn").serializeArray();
                        if ($("#Colyn").form("validate")) {
                            $.post("/P2PForumPlate/P2PForumPlateAddOrEditData", param, function (data) {
                                if (data.Success) {
                                    Dialoga.dialog("close");
                                    Colyn.log(data.Msg);
                                    MyAction.Init();
                                } else {
                                    Colyn.log(data.Msg);
                                }
                            }, "json");
                        }
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        Dialoga.dialog("close");
                    }
                }]

            })
        } else {
            Colyn.log("请选择内容修改！");
        }
    },
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r) {
                    $.post("/P2PForumPlate/P2PForumPlateDeleteData?new_plate_id=" + row.new_plate_id, function (data) {
                        if (data.Success) {
                            Colyn.log("删除成功！");
                            MyAction.Init();
                        } else {
                            Colyn(data.Msg);
                        }
                    }, "json");
                }
            });
        }
        else {
            Colyn.log("请选择内容删除！");
        }
    }
}