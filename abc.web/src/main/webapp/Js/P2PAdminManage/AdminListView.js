/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-25 15:47:56
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Enable").click(MyAction.Enable);
    $("#InitPassword").click(MyAction.InitPassword);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#AdminMaintainListGrid").datagrid({
            method:"GET",
            url: "/Demo/Json/P2PAdminMaintainListView.json",
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
            { field: "user_name", title: "用户名", width: 150, align: "center" },
            { field: "real_name", title: "真实姓名", width: 150, align: "center" },
            { field: "contact_information", title: "联系方式", width: 150, align: "center" },
            { field: "state", title: "状态", width: 150, align: "center" },
            {
                field: "add_date", title: "创建日期", width: 150, align: "center", formatter: function (v, r, i) {
                    return convertToDate(v, "yyyy-MM-dd");
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#AdminMaintainListGrid').datagrid('getPager');
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
    //添加
    Add: function () {
        var o = { modelAction: 'Add' };
        var addDialog = $.hDialog({
            href: "/P2PAdminManage/AdminAddView?" + getParam(o),
            iconCls: 'icon-add',
            title: "管理员添加",
            // maximizable: true,//显示最大化
            width: $(window).width() - 50,
            height: $(window).height() - 50,
            onLoad: function () {

            },
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = createParam2([{ formId: "org" }], "Add");
                        
                        addDialog.dialog("close");
                    }
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    addDialog.dialog("close");
                }
            }]
        })
    },
    //编辑
    Edit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        else {
            var editDialog = $.hDialog({
                href: "/P2PAdminManage/AdminAddView",
                // maximizable: true,//显示最大化
                width: $(window).width() - 50,
                height: $(window).height() - 50,
                iconCls: 'icon-pencil',
                title: "管理员修改",
                onLoad: function () {
                },
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                           
                            editDialog.dialog("close");
                        }
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        editDialog.dialog("close");
                    }
                }]
            })
        }
    },
    //启用
    Enable: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("启用", "确定要启用？", function (r) {
                if (r) {
                    Dialog.dialog("close");
                }
            })
        }
        else {
            Colyn.log("请选择需要启用的信息");
        }
    },
    //初始化密码InitPassword
    InitPassword: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("初始化密码", "确定要初始化密码？", function (r) {
                if (r) {
                    Dialog.dialog("close");
                }
            })
        }
        else {
            Colyn.log("请选择需要初始化密码的信息");
        }
    },

    CustManagerDetail: function (CustId) {

        var url = "/DBCustManage/CustManagerView?CustId=" + CustId;
        var name = "客户经理信息";
        var para = 'height=800,width=1000,resizable=yes,scrollbars=yes'
        window.open(url, name, para);

    }

}