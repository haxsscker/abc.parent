/*------------------------------------------------
 * Author:Colyn-王群林  Date：2013-11-26 13:59:21
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#SitePub").click(MyAction.SitePub);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#RoleGrid").datagrid({
            url: "/site/json/actionColumnView.json",
            idField: "configcode",
            height: $(window).height() - 52,
            pagination: true,
            fitColumns: true,
            singleSelect: true,
            rownumbers: true,
            animate: true,
            columns: [[
                { title: '栏目名称', field: 'columnname', width: 150 },
                { title: "栏目类型",field: "columntype", width: 150, align: "center", formatter: function (value) {
                    if(value == '0'){
                        return "系统内";
                    } else if(value == '1'){
                        return "系统外";
                    }else{
                    	return "-";
                    }
                    
                }
                },
                { title: '关键词', field: 'columnkeyword', width: 150 },
                { title: '父栏目名称', field: 'columnClassName',width: 150, align: 'center' }
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

      $("#edittemplate").die().live('click',(function () {
            var templateDialog = top.$.hDialog({
                href: "/site/TemplateEditView?acId=" + $("#columnid").val(),
                iconCls: 'icon-edit',
                width: 600,
                height: 400,
                fit:true,
                title: "模板编辑",
                submit: function () {
                    if (top.$('#Colyn1').form('validate')) {
                        var param = top.$('#Colyn1').serializeArray();
                        param = convertArray(param);
                        $.AjaxColynJson('/site/json/saveTemplateEdit.json', param, function (data) {
                            if (data.Success) {
                                Colyn.log("模板编辑成功！");
                                MyAction.Init();                                
                            }
                            else {
                                Colyn.log(data.message);
                            }
                            templateDialog.dialog('close');
                        });
                    }
                }
            })
        }));
    },
    Add: function () {
        var addDialog = $.hDialog({
            href: "/Html/SiteManage/Column",
            width: 600,
            height: 400,
            iconCls: 'icon-add',
            title: "新增栏目",
            submit: function () {
                if ($('#Colyn').form('validate')) {
                    var param = $('#Colyn').serializeArray();
                    param = convertArray(param);
                    $.AjaxColynJson('/site/json/AddColumn.json', param, function (data) {
                        if (data.success) {
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
                href: "/Html/SiteManage/Column",
                width: 600,
                height: 400,
                iconCls: 'icon-add',
                title: "修改栏目",
                onLoad: function () {
                    $('#Colyn').form("load", row);
                    if(row.columnClass != 0){
                      $('#classId').combobox('select', row.columnClass);
                    }
                   
                },
                submit: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        $.AjaxColynJson('/site/json/EditColumn.json', param, function (data) {
                            if (data.success) {
                                Colyn.log("修改成功！")
                                MyAction.Init();
                            }
                            else {
                                Colyn.log("修改失败");
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
            $.messager.confirm("删除栏目", "确认要删除选中的栏目吗？", function (r) {
                if (r) {
                    $.AjaxColynJson('/site/json/DelColumn.json', { id: row.columnid }, function (data) {
                        if (data.success) {
                            Colyn.log("删除成功！")
                            MyAction.Init();
                            jQuery('#RoleGrid').datagrid('clearSelections')
                        }
                        else {
                            Colyn.log(data.Msg)
                        }
                    });
                }
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    SitePub: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.AjaxColynJson('/site/ActionSitePubColumnIndex', {id: row.columnid }, function (data) {
                if (data.Success) {
                    Colyn.log("栏目页面生成成功！")
                    MyAction.Init();
                    jQuery('#RoleGrid').datagrid('clearSelections')
                }
                else {
                    Colyn.log(data.Msg)
                }
            });
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    SetBtn: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var r = $.hDialog({
                title: '授权：' + row.rol_Name,
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
                    $.loading();
                    MyAction.Apply("endEdit");
                    var data = MyAction.SubmitGetAllBtn(row.rol_Id);
                    $.AjaxColynText('/System/RoleBtnAdd?RoleID='+ row.rol_Id, {data:data } , function (data) {
                        if (data == "ok") {
                            $.loaded();
                            Colyn.log("菜单权限设置成功");
                        }
                        else {
                            Colyn.log("菜单权限设置失败");
                        }
                        r.dialog('close');
                    });
                }
            });

            var setMenu = $("#setBtn").treegrid({
                url: "/System/GetMenuList?RoleID=" + row.rol_Id + "&" + Math.random(),
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