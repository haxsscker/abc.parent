/*------------------------------------------------
 * Author:潘健  Date：2014-8-18
-------------------------------------------------*/
$(function () {
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
        $("#FriendLinkGrid").datagrid({
            method: "Post",
            url: "/link/json/actionFriendLinkListView.json",
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
            { field: "sys_link_title", title: "链接网站标题", width: 150, halign:"center", align: "left" },
            { field: "sys_link_logo", title: "链接网站logo", width: 120, halign:"center", align: "left" ,
            	formatter: function (value) {
	                return '<img style="max-width:100px"src="'+value+'"></img>';
	            }
            },
            { field: "sys_link_address", title: "链接网站地址", width: 250, halign:"center", align: "left" },
            { field: "sys_link_order", title: "排序", width: 250, halign:"center", align: "left" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#FriendLinkGrid').datagrid('getPager');
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
       var dss = $.hDialog({
            href: "/link/FriendLinkAddView",
            iconCls: 'icon-add',
            title: "添加友情链接",
            // maximizable: true,//显示最大化
            width: 600,//$(window).width() - 50,
            height: 500,//$(window).height() - 50,
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = $("#Colyn").serializeArray();
                        if ($("#Colyn").form("validate")) {
                            $.post("/link/json/addFriendLink.json", param, function (data) {
                                if (data.success) {
                                    Colyn.log("保存成功！");
                                    MyAction.Init();
                                    dss.dialog('close');
                                } else {
                                    Colyn.log("保存失败！");
                                }
                            }, "json");
                        }
                    }
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dss.dialog("close");
                }
            }]
        })

    },
    Edit: function () {
        var row = MyGrid.selectRow();
        if (row) {
           var ds = $.hDialog({
                href: "/link/FriendLinkAddView?sys_link_id="+row.sys_link_id,
                iconCls: 'icon-pencil',
                title: "修改友情链接",
                // maximizable: true,//显示最大化
                width: 600,//$(window).width() - 50,
                height: 500,//$(window).height() - 50,
                onLoad: function () {
                	$("#sys_link_id").val(row.sys_link_id);
                    $("#link_title").val(row.sys_link_title);
                    $("#link_url").val(row.sys_link_address);
                    $("#link_order").val(row.sys_link_order);
                },
                submit: function () {
                	
                },
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                            var param = $("#Colyn").serializeArray();
                            if ($("#Colyn").form("validate")) {
                                $.post("/link/json/editFriendLink.json", param, function (data) {
                                    if (data.success) {
                                        Colyn.log("修改成功");
                                        MyAction.Init();
                                        ds.dialog('close');
                                    } else {
                                        Colyn("保存失败");
                                    }
                                }, "json");
                            }
                        }
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        ds.dialog("close");
                    }
                }]

            })
        } else {
            Colyn.log("请选择内容修改！");
        }
    },
    Del: function () {
        var row = MyGrid.selectRow();
        console.log(row);
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function(r) {
                if (r) {
                    $.post("/link/json/delFriendLink.json?sys_link_id=" + row.sys_link_id, function (data) {
                        if (data.success) {
                            Colyn.log("删除成功！");
                            MyAction.Init();
                        } else {
                            Colyn("保存失败");
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