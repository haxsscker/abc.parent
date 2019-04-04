$(function () {
    MyGrid.Resize();
    $("#Add").click(MyAction.Add);
    $("#Del").click(MyAction.Del);
    $("#btnSearch").click(MyAction.Search);
    $("#LookUp").click(MyAction.LookUp);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });

});
var Dialoga;
var MyAction = {
    //列表Ok
    Init: function () {
        $("#MailManagementGrid").datagrid({
            method: "Post",
            url: "/P2PMailManagement/MailManagementList",
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
                        field: "sys_message_title", title: "留言标题", width: 250, align: "center",
                        formatter: function(value, row, index) {
                            if (value == "" || value == null) {
                                return "-";
                            } else {
                                return CutString(value, 10);
                            }
                        }
                    },
                    {
                        field: 'sys_message_date', title: '留言日期', width: 250, align: 'center',
                        formatter: function(value, row, index) {
                            if (value == "" || value == null) {
                                return "-";
                            } else {
                                return convertToDate(value, "yyyy-MM-dd");
                            }
                        }
                    },
                    { field: "sys_user_name", title: "留言人", width: 250, align: "center" },
                    { field: "sys_to_name", title: "接收人", width: 250, align: "center" },
                    {
                        field: "sys_message_state",
                        title: "留言状态",
                        width: 250,
                        align: "center",
                        formatter: function(value, row, index) {
                            if (value == "0") {
                                return "未回复";
                            } else {
                                return "已回复";
                            }
                        }
                    }
                ]
            ],
            pagination: true,
            singleSelect: true
        });
        var p = $('#MailManagementGrid').datagrid('getPager');
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
   ///添加OK 
    Add: function () {
        Dialoga = $.hDialog({
            href: "/P2PMailManagement/MailManagementAddOrEdiet",
            iconCls: 'icon-add',
            title: "添加留言信箱",
            maximizable: true,//显示最大化
            width: $(window).width() - 50, //$(window).width() - 50,
            height: $(window).height() - 50, //$(window).height() - 50,
            buttons: [
                {
                    text: '确认添加',
                    iconCls: 'icon-ok',
                    handler: function() {
                       return  AddTo();
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function() {
                        Dialoga.dialog("close");
                    }
                }
            ],
            onBeforeClose: function () { ue.destroy(); }
        });
    },
    //删除OK
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r) {
                    $.post("/P2PMailManagement/MailManagementDelete?sys_message_id=" + row.sys_message_id, function (data) {
                        if (data.Success) {
                            Colyn.log("删除成功！");
                            MyAction.Init();
                            Dialog.dialog('close');
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
    },
    //搜索OK
    Search: function() {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        if ($("#SearchFormto").form("validate"))
        $.post("/P2PMailManagement/MailManagementList?" + getParam(o), param, function (data) {
            $("#MailManagementGrid").datagrid("loadData", data);
        }, "json");
    },
    //查看回复
    LookUp: function () {
        var row = MyGrid.selectRow();
        if (row) {

            var Dialogst = $.hDialog({
                href: "/P2PMailManagement/MailManagementLookUp?sys_message_id=" + row.sys_message_id,
                iconCls: 'icon-add',
                title: "留言查看",
                maximizable: true, //显示最大化
                width: $(window).width() - 50, //$(window).width() - 50,
                height: $(window).height() - 50, //$(window).height() - 50,
                buttons: [
                    {
                        text: '回复',
                        iconCls: 'icon-ok',
                        handler: function() {
                            var o = { sys_message_id: row.sys_message_id, sys_reply_content: uelk.getContent() };
                            if (uelk.hasContents()) {
                                $.post("/P2PMailManagement/MailManagementLookUpData", o, function(data) {
                                    if (data.Success) {
                                        Colyn.log(data.Msg);
                                        Dialogst.dialog("close");
                                        MyAction.Init();
                                    } else {
                                        Colyn.log(data.Msg);
                                    }
                                }, "json");
                            } else {
                                Colyn.log("回复内容不能为空！");
                            }
                        }
                    }, {
                        text: '关闭',
                        iconCls: 'icon-cancel',
                        handler: function() {
                            Dialogst.dialog("close");
                        }
                    }
                ],
                onBeforeClose: function() { uelk.destroy(); }
            });
        } else {
            Colyn.log("请选择内容查看！");
        }
    }
}
//添加方法OK
function AddTo() {
    var listnames = "";
    $("#listname option").each(function (i, listname) {
        listnames += $(listname).val() + "~";
    });
    if (!$("#Colyn").form("validate")){ return false; }
    if ($("#listname option").val() == undefined) { Colyn.log("接收人不能为空！"); return false;}
    var o = { sys_message_title: $("#sys_message_titles").val(), sys_message_content: ue.getContent(), sys_to_user: listnames.substring(0, listnames.length - 1) };
        if (ue.hasContents()) {
        $.post("/P2PMailManagement/MailManagementAddOrEdietData",o, function(data) {
            if (data.Success) {
                Colyn.log(data.Msg);
                Dialoga.dialog("close");
                MyAction.Init();
            } else {
                Colyn.log(data.Msg);
            }
        }, "json");
    } else {
        Colyn.log("留言内容不能为空！");
    }
}