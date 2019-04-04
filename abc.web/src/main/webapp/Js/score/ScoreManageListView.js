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
        $("#ScoreManageGrid").datagrid({
            method: "Post",
            url: "/score/json/getScoreManageList.json",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            idField: "scId",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview, //重写当没有数据时
            emptyMsg: '没有找到数据', //返回数据字符
            columns: [
            [
            { field: "scMaxScore", title: "兑现积分", width: 250, align: "center" },
            {
                field: 'scScorePic',
                title: '积分兑现图片',
                width: 250,
                align: 'center',
                formatter: function(value, row, index) {
                    if (value != "" && value != null) {
                        return '<img src="' + value + '" width="180" height="100"/>';
                    } else {
                        return "-";
                    }
                }
            },
            {
                field: "scScoreCash", title: "积分兑现金额", width: 250, align: "center", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#ScoreManageGrid').datagrid('getPager');
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
        Dialog = $.hDialog({
            href: "/score/scoreManageAddView",
            iconCls: 'icon-add',
            title: "添加积分设置",
            // maximizable: true,//显示最大化
            width: 450,//$(window).width() - 50,
            height: 350,//$(window).height() - 50,
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    var param = $("#Colyn").serializeArray();
                    $('#scScorePic').validatebox('disableValidation');
                    if ($("#Colyn").form("validate")) {
                        $.post("/score/json/addScoreManage.json?", param, function (data) {
                            if (data.success) {
                                Colyn.log(data.message);
                                MyAction.Init();
                                Dialog.dialog('close');
                            } else {
                                Colyn.log(data.message);
                            }
                        }, "json");
                    }
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
    Edit: function () {
        var row = MyGrid.selectRow();
        if (row) {
            Dialog = $.hDialog({
                href: "/score/scoreManageAddView?scId=" + row.scId,
                iconCls: 'icon-pencil',
                title: "修改积分设置",
                // maximizable: true,//显示最大化
                width: 450,//$(window).width() - 50,
                height: 300,//$(window).height() - 50,
                onLoad: function () {
                },
                submit: function () {

                },
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
                        $('#scScorePic').validatebox('disableValidation');
                        var param = $("#Colyn").serializeArray();
                        if ($("#Colyn").form("validate")) {
                            $.post("/score/json/editScoreManage.json?", param, function (data) {
                                if (data.success) {
                                    Colyn.log(data.message);
                                    MyAction.Init();
                                    Dialog.dialog('close');
                                } else {
                                    Colyn.log(data.message);
                                }
                            }, "json");
                        }
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        Dialog.dialog("close");
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
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function(r) {
                if (r) {
                    $.AjaxColynJson('/score/json/removeScoreManage.json?', { scId: row.scId }, function (data) {
                        if (data.success) {
                            Colyn.log(data.message);
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.message)
                        }
                    });
                }
            });
        }
        else {
            Colyn.log("请选择内容删除！");
        }
    }
}
