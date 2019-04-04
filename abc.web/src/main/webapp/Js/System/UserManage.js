/*------------------------------------------------
* Author:Colyn-王群林  Date：2013-11-26 13:59:21
-------------------------------------------------*/

$(function () {
    MyGrid.Resize();
    MyAction.Init();
    MyDepTree.Init();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#EditPwd").click(MyAction.EditPwd);
    $("#SetRole").click(MyAction.SetRole);
    $("#SearchHide").click(MyAction.Search);
    $("#Authorization").click(MyAction.SetMenu);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyDepTree = {
    Init: function () {
        //绑定部门列表
        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: MyDepTree.zTreeClick
            }
        };
        $.post("/System/GetDepTree", function (data) {
            data.push({ "id": 0, pId: 0, name: "所有部门列表", open: true, icon: "/Images/icon/16/chart_org_inverted.png" });
            $.fn.zTree.init($("#DepTree"), setting, data);
        }, "json");

    },
    zTreeClick: function (event, treeId, treeNode) {
        if (treeNode.id == 0) {
            MyAction.Init();
        }
        else {
            var arr = [];
            arr.push({ Field: "dep_Id", Value: treeNode.id, Method: "Equal", OrGroup: "treeDep" });
            var Nodes = treeNode.children;
            if (Nodes) {               
                for (var i = 0; i < Nodes.length; i++) {
                    arr.push({ Field: "dep_Id", Value: Nodes[i].id, Method: "Equal", OrGroup: "treeDep" });
                }
            }
            $.post("/System/GetUserListByDepId?modelAction=Search", 'searchForm=' + JSON.stringify({ Items: arr }), function (data) {
                $("#UserGrid").datagrid("loadData", data);
            }, "json");
        }
    }
}

var MyAction = {
    Init: function (param) {
        $("#UserGrid").datagrid({
            url: "/System/UserManage",
            idField: "emp_Id",
            height: $(window).height() - 52,
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            onDblClickRow: function (rowIndex, rowData) {
                MyAction.Edit();
            },
            queryParams: param,
            columns: [[
            { title: '用户名', field: 'emp_Name', width: 200, align: 'center' },
            { title: '昵称', field: 'emp_NickName', align: 'center', width: 200 },
            { title: '机构', field: 'org_Id', align: 'center', width: 200, hidden: true },
            { title: '登陆次数', field: 'emp_LoginCount', align: 'center', width: 80 },
            {
                title: '最后登陆时间', field: 'emp_LastTime', align: 'center', sortable: true, width: 200,
                formatter: function (v, d, i) {
                    return convertToDate(v);
                }
            },
            {
                title: '用户状态', field: 'emp_Status', align: 'center', width: 100,
                formatter: function (v, d, i) {
                    return '<img style="cursor:pointer" title="禁用启用账号"  src="/Images/icon/16/bullet_' + (v == 1 ? "tick.png" : "minus.png") + '" />';
                }
            }

            ]]
        });
    },
    Add: function () {
        var addDialog = $.hDialog({
            href: "/Html/System/UserAdd.html?" + Math.random(),
            width: 500,
            height: 300,
            iconCls: 'icon-user_add',
            title: "添加用户",
            onLoad: function () {
                MyAction.SetOrg(0, false);
                MyAction.SetDep("0", "0", false);
                MyAction.SetDuty("0", "0", false);
            },
            submit: function () {
                var orgId = $('#org_Id').combotree('getValue');
                if (orgId == 0 || orgId == undefined) {
                    Colyn.log("请选择机构！");
                    return false;
                };
                var deps = $('#Deps').combotree('getValues');
                if (deps == "0" || deps == undefined) {
                    Colyn.log("请选择所属部门！");
                    return false;
                };
                var dutys = $('#Dutys').combotree('getValues');
                if (dutys == "0" || dutys == undefined) {
                    Colyn.log("请选择职务！");
                    return false;
                }; 
                if ($('#Colyn').form('validate')) {
                    var param = $('#Colyn').serializeArray();
                    param = convertArray(param);
                    $.AjaxColynJson('/System/UserAciton?adeps=' + deps + '&adutys=' + dutys, param, function (data) {
                        if (data.Success) {
                            Colyn.log("添加成功！");
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.Msg);
                        }
                        addDialog.dialog('close');
                    });
                }
            }
        })
    },
    //搜索
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/System/UserManage?" + getParam(o), param, function (data) {
            $("#UserGrid").datagrid("loadData", data);
        }, "json");
    },
    Edit: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var editDialog = $.hDialog({
                href: "/Html/System/UserEdit.html?" + Math.random(),
                width: 500,
                height: 260,
                iconCls: 'icon-user_add',
                title: "修改用户",
                onLoad: function () {
                    MyAction.getInfo(row);
                    MyAction.SetDep(row.org_Id,row.emp_Id, true);
                    MyAction.SetDuty(row.org_Id, row.emp_Id, true);
                    MyAction.SetOrg(row.org_Id, true);
                },
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
                        var orgId = $('#org_Id').combotree('getValue');
                        if (orgId == 0 || orgId == undefined) {
                            Colyn.log("请选择机构！");
                            return false;
                        };
                        var deps = $('#Deps').combotree('getValues');
                        if (deps == "0" || deps == undefined) {
                            Colyn.log("请选择所属部门！");
                            return false;
                        };
                        var dutys = $('#Dutys').combotree('getValues');
                        if (dutys == "0" || dutys == undefined) {
                            Colyn.log("请选择职务！");
                            return false;
                        };
                        if ($('#Colyn').form('validate')) {
                            var param = $('#Colyn').serializeArray();
                            param = convertArray(param);
                            $.AjaxColynJson('/System/UserAciton?empId=' + row.emp_Id + '&adeps=' + deps + '&adutys=' + dutys, param, function (data) {
                                if (data.Success) {
                                    Colyn.log("修改成功！")
                                    MyAction.Init("UserGrid");
                                }
                                else {
                                    Colyn.log(data.Msg);
                                }
                                editDialog.dialog('close');
                            });
                        }
                    }
                }, {
                    text: '取消返回',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        editDialog.dialog("close");
                    }
                }]               
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    EditPwd: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var editPwdDialog = $.hDialog({
                href: "/Html/System/UserEditPwd.html?" + Math.random(),
                width: 440,
                height: 160,
                iconCls: 'icon-key',
                title: "修改密码",
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                            $.AjaxColynText('/System/EditPwd', { id: row.emp_Id, pwd: $("#emp_Password1").val() }, function (data) {
                                if (data == "ok") {
                                    Colyn.log("修改成功！")
                                    MyAction.Init("UserGrid");
                                }
                                else {
                                    Colyn.log("修改失败！");
                                }
                                editPwdDialog.dialog('close');
                            });
                        }
                    }
                }, {
                    text: '取消返回',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        editPwdDialog.dialog("close");
                    }
                }]       
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("提示", "确认删除该条员工信息？", function (r) {
                if (r) {
                    $.AjaxColynJson('/System/UserDel', { id: row.emp_Id }, function (data) {
                        if (data.Success) {
                            Colyn.log("删除成功。");
                            MyAction.Init("UserGrid");
                            jQuery('#UserGrid').datagrid('clearSelections')
                        }
                        else {
                            Colyn.log(data.Msg);
                        }
                    });
                }
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    getInfo: function (row) {         
            $("#emp_Name").val(row.emp_Name);
            $("#emp_NickName").val(row.emp_NickName);
            $("#emp_Email").val(row.emp_Email);
            $("#emp_Mobile").val(row.emp_Mobile);
            $("#emp_Phone").val(row.emp_Phone);
            $("#emp_State").val(row.emp_State);
            $("#emp_Id").val(row.emp_Id);
            $("#emp_No").val(row.emp_No);
    },
    SetOrg: function (id, r) {
        $.AjaxColynJson("/System/GetOrgSelect", function (data) {
            $('#org_Id').combobox({
                data: data,
                valueField: 'id',
                textField: 'text',
                editable: false,
                panelHeight: "auto",
                onSelect: function (item) {
                    var getValue = $('#org_Id').combobox('getValue');
                    //部门
                    MyAction.SetDep(getValue);
                    //职务
                    MyAction.SetDuty(getValue);
                }
            }).combobox('setValue', id);
            ///设置机构默认为空
            if (!r) {
                $('#org_Id').combobox('setValue', " ");
            }
        });
    },
    SetDep: function (id,empid, r) { 
        $.AjaxColynJson("/System/GetDepAndSelDepByOrg", { id: id, empid: empid }, function (data) {
            $('#Deps').combotree({
                data: data.json,
                valueField: 'id',
                textField: 'text',
                editable: false,
                multiple: true,
                cascadeCheck: false,
                required: false
            });
            if (r) {
                if (data.ids) {
                    $('#Deps').combotree('setValues',data.ids );
                }
            }
        });
    },
    SetDuty: function (org_Id,empid,r) {
        $.AjaxColynJson("/System/GetDutAndSelDutByOrg", { org_Id: org_Id, empid: empid }, function (data) {
            $('#Dutys').combotree({
                data: data.json,
                valueField: 'id',
                editable: false,
                textField: 'text',
                multiple: true,
                cascadeCheck: false,
                required: false
            });
            if (r) {
                if (data.ids) {
                    $('#Dutys').combotree('setValues', data.ids);
                }
            }
           
        });
    },
    SetRole: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var SetRoleDialog = $.hDialog({
                href: "/Html/System/SetRole.html?" + Math.random(),
                width: 500,
                height: 400,
                iconCls: 'icon-user_add',
                title: "角色设置",
                onLoad: function () {
                    $('#LeftRoles,#RightRoles').datagrid({
                        width: 220,
                        height: 300,
                        iconCls: 'icon-group',
                        rownumbers: true,
                        striped: true,
                        idField: 'rol_Id',//主键
                        singleSelect: true,
                        columns: [[
                            { title: '角色名称', field: 'rol_Name', width: 190 }
                        ]],
                        pagination: false
                    });

                    $('#LeftRoles').datagrid({
                        url: '/System/GetRole?id=' + row.emp_Id + "&isAll=true",
                        onDblClickRow: function (rowIndex, rowData) {
                            $('#selectRole').click();
                        }
                    });

                    $('#RightRoles').datagrid({
                        url: '/System/GetRole?id=' + row.emp_Id + "&isAll=false",
                        onDblClickRow: function (rowIndex, rowData) {
                            $('#delRole').click();
                        }
                    });

                    $('#selectRole').click(function () {
                        var _row = $('#LeftRoles').datagrid('getSelected');
                        if (_row) {
                            var hasRoleName = false;
                            var roles = $('#RightRoles').datagrid('getRows');
                            $.each(roles, function (i, n) {
                                if (n.rol_Name == _row.rol_Name) {
                                    hasRoleName = true;
                                }
                            });
                            if (!hasRoleName)
                                $('#RightRoles').datagrid('appendRow', _row);
                            else {
                                Colyn.log("角色已存在，请不要重复添加");
                            }
                        } else {
                            Colyn.log("请选择一个角色");
                        }
                    });

                    $('#delRole').click(function () {
                        var trow = $('#RightRoles').datagrid('getSelected');
                        if (trow) {
                            var rIndex = $('#RightRoles').datagrid('getRowIndex', trow);
                            $('#RightRoles').datagrid('deleteRow', rIndex).datagrid('unselectAll');
                        } else {
                            Colyn.log("请选择一个角色");
                        }
                    });
                },
                submit: function () {
                    var selectedRoles = $('#RightRoles').datagrid('getRows');
                    var roleIdArr = [];
                    $.each(selectedRoles, function (i, n) {
                        roleIdArr.push(n.rol_Id);
                    });

                    $.AjaxColynText("/System/SetRole?id=" + row.emp_Id, { roleids: roleIdArr.join(',') }, function (data) {
                        if (data == "ok") {
                            Colyn.log("设置成功！");
                        }
                        else {
                            Colyn.log("失败");
                        }
                        SetRoleDialog.dialog('close');
                    });
                }
            })
        } else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    SetMenu: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var r = $.hDialog({
                title: '授权：' + row.emp_Name,
                max: true,
                content: '<div style="padding:2px;overflow:hidden;"><table id="setBtn"></table></div>',
                toolbar: [
                { text: '全选', iconCls: 'icon-checkbox_yes', handler: function () { MyAction.ActionChecked(true) } },
                { text: '取消全选', iconCls: 'icon-checkbox_no', handler: function () { MyAction.ActionChecked(false) } },
                '-',
                //{ text: '编辑全部', iconCls: 'icon-pencil', handler: function () { MyAction.Apply("beginEdit") } },
                { text: '应用', iconCls: 'icon-disk_multiple', handler: function () { MyAction.Apply("endEdit") } }
                ],
                submit: function () {
                    MyAction.Apply("endEdit");
                    var data = MyAction.SubmitGetAllBtn(row.emp_Id);
                   // console.log(data)
                    $.AjaxColynText('/System/SetMenuBtnList?emp_Id=' + row.emp_Id, { data: data }, function (data) {
                        if (data == "ok") {
                            Colyn.log("添加成功！");
                        }
                        else {
                            Colyn.log("添加失败！");
                        }
                        r.dialog('close');
                    });
                }
            });

            var setMenu = $("#setBtn").treegrid({
                url: "/System/GetMenuBtnList",
                title: '菜单列表',
                queryParams: { UserID: row.emp_Id },
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
            else return v;
        } else {
            return Enumerable.from(d.hasbtns).any("n=>n=='" + field + "'") ? "<font color=\"#39CB00\"><b>√</b></font>" : "x";
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
                        $(this.target).attr("checked", flag);
                });
            });
        } else {
            Colyn.log("请选择菜单！");
        }
    },
    SubmitGetAllBtn: function (emp_Id) {
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