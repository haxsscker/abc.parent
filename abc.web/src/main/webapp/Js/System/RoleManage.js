/*------------------------------------------------
 * Author:Colyn-王群林  Date：2013-11-26 13:59:21
-------------------------------------------------*/
$(function () {
    var btns;
    MyGrid.Resize();
    MyAction.Init();
    MyAction.GetButtonS();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#Authorization").click(MyAction.SetBtn);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    GetButtonS: function(){
        $.get("/system/json/getButtonList.json",function(data){
            btns=data;
        });
    },
    Init: function () {
        $("#RoleGrid").datagrid({
            url: "/system/json/getRoleList.json",
            idField: "roleId",
            height: $(window).height() - 52,
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            animate: true,
            columns: [[
                { title: '编号', field: 'roleId', width: 50 },
                { title: '角色名称', field: 'roleName', width: 320, align: 'center'},
                {
                    title: '是否是默认', field: 'roleDefault', align: 'center', width: 80,
                    formatter: function (v, d, i) {
                        return '<img src="/Images/' + (v == '1' ? "checkmark.gif" : "checknomark.gif") + '" />';
                    }
                },
                { title: '排序', field: 'roleSort', align: 'center', width: 80 },
                { title: '备注', field: 'roleNode', align: 'center', width: 360 }
            ]]
        });

        var p = $('#RoleGrid').datagrid('getPager');
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
            href: "/Html/System/role.vm?" + Math.random(),
            width: 440,
            height: 300,
            iconCls: 'icon-add',
            title: "添加角色",
            submit: function () {
                if ($('#Colyn').form('validate')) {
                    var param = $('#Colyn').serializeArray();
                    param = convertArray(param);
                    $.AjaxColynJson('/system/json/addRole.json', param, function (data) {
                        if (data.success) {
                            Colyn.log("添加成功！");
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.message);
                        }
                        addDialog.dialog('close');
                    });
                }
            }
        })
    },
    Edit: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var editDialog = $.hDialog({
                href: "/Html/System/role.vm?" + Math.random(),
                width: 440,
                height: 300,
                iconCls: 'icon-add',
                title: "修改角色",
                onLoad: function () {
                    $('#Colyn').form("load", row);
                    $("#rol_Id").val(row.roleId);
                    $("#rRolName").val(row.roleName);
                    $("#validname").val(row.roleName);
                    $("#rol_IsDefault").attr("checked", row.roleDefault == 1);
                },
                submit: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        $.AjaxColynJson('/system/json/editRole.json', param, function (data) {
                            if (data.success) {
                                Colyn.log("修改成功！")
                                MyAction.Init();
                            }
                            else {
                                Colyn.log(data.message);
                            }
                            editDialog.dialog('close');
                        });
                    }
                }
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {            
            $.messager.confirm("删除用户", "确认要删除选中的角色吗？", function (r) {
                if (r) {
                    $.AjaxColynJson('/system/json/delRole.json', { id: row.roleId }, function (data) {
                        if (data.success) {
                            Colyn.log("删除成功！")
                            MyAction.Init();
                            jQuery('#RoleGrid').datagrid('clearSelections');
                        }
                        else {
                            Colyn.log(data.message);
                        }
                    });
                }
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    SetBtn: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var r = $.hDialog({
                title: '授权：' + row.roleName,
                max: true,
                content: '<div style="padding:2px;overflow:hidden;"><table id="setBtn"></table></div>',
                submit: function () {
                    $.loading();
                    MyAction.Apply("endEdit");
                    var data = MyAction.SubmitGetAllBtn(row.roleId);
                    $.AjaxColynText('/system/json/authoritySave.json?roleId='+ row.roleId,{"data":data}, function (data) {
                        $.loaded();
                        Colyn.log("菜单权限设置成功");
                        r.dialog('close');
                    });
                }
            });

            var setMenu = $("#setBtn").treegrid({
                url: "/system/json/getAuthorityMatrix.json?roleId=" + row.roleId + "&" + Math.random(),
                title: '菜单列表',
                height: r.dialog("options").height - 115,
                idField: "men_Id",
                treeField: "men_Name",
                iconCls: "icon-nav",
                nowrap: false,
                rownumbers: true,
                animate: true,
                collapsible: false,
                frozenColumns: [[{ title: '菜单名称', field: 'men_Name', width: 200 }]],
                columns: [MyAction.GetBtn()],
                onClickRow: function (row) {
                    if (lastIndex != row.men_Id) {
                        setMenu.treegrid('endEdit', lastIndex);
                    }
                    MyAction.Apply("beginEdit", row.men_Id);
                    lastIndex = row.men_Id;
                }
            });
        } else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    GetBtn: function () {
        Enumerable.from(btns).forEach("o=>o.formatter=function(v,d,i){return MyAction.formatter(v,d,i,o.field);}");
        return btns;
    },
    formatter: function (v, d, i, field) {
        if (v) {
            if (v == '√')
                return '<font color=\"#39CB00\"><b>' + v + '</b></font>';
            else return '<font color=\"#ff0000\">' + v + '</font>';
        } else {
            return Enumerable.from(d.hasbtns).any("n=>n=='" + field + "'") ? "<font color=\"#39CB00\"><b>√</b></font>" : "<font color=\"#ff0000\">x</font>";
        }
    },
    Apply: function (action, index) {
        if (!index) {
            $("#setBtn").treegrid('selectAll');
        }
        var rows = $("#setBtn").treegrid('getSelections');        
        $.each(rows, function (i, n) {
            $("#setBtn").treegrid(action, this.men_Id);
            if (action == "beginEdit") {
                var editors = $('#setBtn').treegrid('getEditors', n.men_Id);
                Enumerable.from(btns).forEach(function (x, z) {
                    var hasbtn = Enumerable.from(n.Buttons).any('$=="' + x.field + '"');
                    Enumerable.from(editors).forEach(function (b) {
                        if (!hasbtn && b.field == x.field)
                            $(b.target).remove();
                    });
                });
            }            
        });
        if (action != "beginEdit") {
            $('#setBtn').treegrid('clearSelections');
        }
    },
    ActionChecked: function (flag) {
        var rows = $("#setBtn").treegrid('getSelections');
        if (rows) {
            $.each(rows, function (i, n) {
                var editors = $("#setBtn").treegrid('getEditors', n.men_Id);
                $.each(editors, function () {
                    if (!$(this.target).is(":hidden"))
                        $(this.target).attr("checked",flag);
                });
            });
        } else {
            Colyn.log("请选择菜单！");
        }
    },
    SubmitGetAllBtn: function (RoleID) {
        var rows = $("#setBtn").treegrid("getChildren");
        var arr = { Btns: [] };
        Enumerable.from(rows).forEach(function (s) {
            var n = { MenuId: s.men_Id, buttons: [] };
            n.buttons = Enumerable.from(s).where('t=>t.value=="√"').select('$.key').toArray();
            if (n.buttons.length > 0) {
                arr.Btns.push(n);
            }
        });
        return JSON.stringify(arr);
    }
}
var lastIndex = 0;
