/*------------------------------------------------
 * Author:Colyn-王群林  Date：2013-11-26 13:59:21
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});


var MyAction = {
    Init: function () {
        $("#DepGrid").treegrid({
            url: "/System/GetDepsTree",
            idField: "dep_Id",
            treeField: "dep_Name",
            height: $(window).height() - 52,
            pagination: false,
            nowrap: false,
            rownumbers: true,
            singleSelect: true,
            animate: true,
            collapsible: false,
            frozenColumns: [[
                   { title: '编号', field: 'dep_Id', width: 50 },
                   { title: '部门名称', field: 'dep_Name', width: 320 }
            ]],
            columns: [[
                { title: '排序', field: 'dep_Sort', align: 'center', width: 80 },
                { title: '备注', field: 'DepDesc', align: 'center', width: 360 }
            ]]
        });
    },
    Add: function () {
        var addDialog = $.hDialog({
            href: "/Html/System/Dep.html?" + Math.random(),
            width: 440,
            height: 260,
            iconCls: 'icon-add',
            title: "添加部门",
            onLoad: function () {
                MyAction.InitCtrl(0);
                MyAction.ComBox(0);
                var row = MyGrid.selectRow();
                if (row) {
                    $('#org_Id').combotree().combotree('setValue', row.org_Id);
                    $('#dep_ParentId').combotree().combotree('setValue', row.dep_Id);
                }
            },
            submit: function () {
                var orgId = $('#org_Id').combotree('getValue');
                if (orgId == 0 || orgId == undefined) {
                    Colyn.log("请选择机构！");
                    return false;
                };
                if ($('#Colyn').form('validate')) {
                    var param = $('#Colyn').serializeArray();
                    param = convertArray(param);
                    $.AjaxColynJson('/System/ActionDep?modelAction=Add', param, function (data) {
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
    Edit: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var editDialog = $.hDialog({
                href: "/Html/System/Dep.html?" + Math.random(),
                width: 460,
                height: 260,
                iconCls: 'icon-pencil',
                title: "修改部门",
                onLoad: function () {
                    MyAction.InitCtrl(row.dep_Id);
                    MyAction.ComBox(row.org_Id);
                    $("#dep_Id").val(row.dep_Id);
                    $("#dep_Name").val(row.dep_Name);
                    $("#rDepName").val(row.dep_Name);
                    $("#DepDesc").val(row.DepDesc);
                    $('#dep_ParentId').combotree('setValue', row.dep_ParentId);
                },
                submit: function () {
                    var orgId = $('#org_Id').combotree('getValue');
                    if (orgId == 0 || orgId == undefined) {
                        Colyn.log("请选择机构！");
                        return false;
                    }

                    if ($('#Colyn').form('validate')) {
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        $.AjaxColynJson('/System/ActionDep?modelAction=Edit', param, function (data) {
                            if (data.Success) {
                                Colyn.log("修改成功！")
                                MyAction.Init();
                            }
                            else {
                                Colyn.log(data.Msg);
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
            if ($('#DepGrid').treegrid('getChildren', row.dep_Id).length > 0) {
                Colyn.log("该部门拥有子部门,不能删除");
                return false;
            }
            $.messager.confirm("提示", "确认删除该条部门信息？", function (r) {
                if (r) {
                    $.AjaxColynJson('/System/DepDel', { depId: row.dep_Id }, function (data) {
                        if (data.Success) {
                            Colyn.log("删除成功");
                            MyAction.Init();
                            jQuery('#DepGrid').datagrid('clearSelections')
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
    InitCtrl: function (dep_Id) {
        var TreeData = $("#DepGrid").treegrid("getData");
        TreeData = JSON.stringify(TreeData).replace(/dep_Name/g, 'text').replace(/dep_Id/g, 'id');
        if (TreeData.length > 2) {
            TreeData = '[{"id":0,"selected":true,"text":"请选择上级部门"},' + TreeData.substr(1, TreeData.length - 1);
        } else {
            TreeData = '[{"id":0,"selected":true,"text":"请选择上级部门"}]'
        };
        $('#dep_ParentId').combotree({
            data:JSON.parse(TreeData),
            valueField: 'id',
            textField: 'text',
            editable: false,
            panelWidth: '180',
            lines: true,
            multiple: false
        }).combotree('setValue', dep_Id);

    }
    ,
    ComBox: function (org_ID) {
        $('#org_Id').combobox({
            url: "/System/GetOrgSelect",
            valueField: 'id',
            textField: 'text',
            editable: false,
            panelHeight: 'auto'
        });
        if(org_ID!=0)
            $('#org_Id').combobox("setValue", org_ID);
    }
}