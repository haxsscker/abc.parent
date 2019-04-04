$(function () {
    MyTreeGrid.Resize();
    MyTreeGrid.Init();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#SetBtn").click(MyAction.SetBtn);
    $(window).resize(function () {
        MyTreeGrid.RefreshPanl();
    });
});

var MyTreeGrid = {
    Resize: function () {
        var WH = {
            width: $(window).width(),
            height: $(window).height()
        }
        $('#layout').width(WH.width - 20).height(WH.height - 20).layout();
        var center = $('#layout').layout('panel', 'center');
        center.panel({
            onResize: function (w, h) {
                $('#MenuGrid').treegrid('resize', { width: w - 6, height: h - 36 });
            }
        });
    },
    RefreshPanl: function () {
        MyTreeGrid.Resize();
        $('#layout').layout('resize');
    },
    Init: function () {       
        $("#MenuGrid").treegrid({
            url: "/system/json/getMenuItem.json",
            idField: "menuId",
            treeField: "menuName",
            height: $(window).height() - 52,
            pagination: false,
            nowrap: false,
            rownumbers: true,
            fitColumns:true,
            animate: true,
            collapsible: false,
            frozenColumns: [[
                   { title: '编号', field: 'menuId', width: 50 },
                   { title: '菜单名称', field: 'menuName', width: 220 }
            ]],
            columns: [[
                { title: '链接地址', field: 'menuUrl', align: 'center', width: 320 },
                { title: '图标', field: 'iconCls', align: 'center', width: 200 },
                { title: '大图标', field: 'bigIcon', align: 'center', width: 100 },
                {
                    title: '是否显示', field: 'isVisible', align: 'center', width: 100,
                    formatter: function (v, d, i) {
                        if (v == 1) {
                            return "显示";
                        }
                        else if (v == 0) {
                            return "不显示";
                        }
                        else if (v == 2) {
                            return "默认展开";
                        }
                        else {
                            return "无";
                        }
                    }
                },
                { title: '排序', field: 'menuSort', align: 'center', width: 60 }
            ]]
        });
    },
    selectRow: function () {
        return $('#MenuGrid').treegrid('getSelected');
    }
}

var MyAction = {
    Add: function () {
        var addDialog = $.hDialog({
            href: "/Html/System/menu.vm?" + Math.random(),
            width: 440,
            height: 360,
            iconCls: 'icon-add',
            title: "添加菜单",
            onLoad: function () {
                MyAction.InitCtrl();
                var row = MyTreeGrid.selectRow();
                if (row) {
                    $('#men_ParentID').combotree('setValue', row.men_Id);
                }
            },
            submit: function () {
                if ($('#Colyn').form('validate')) {
                	if($.trim($("#men_Name").val())==""){
                		Colyn.log("菜单名不能包含空格");
                		return;
                	}
                	if($.trim($("#men_URL").val())==""){
                		Colyn.log("菜单链接不能包含空格");
                		return;
                	}
                    var param = $('#Colyn').serializeArray();
                    param = convertArray(param);
                    $.AjaxColynJson('/system/json/addMenu.json', param, function (data) {
                        if (data.success) {
                            Colyn.log("添加成功！");
                            MyTreeGrid.Init();
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
        var row = MyTreeGrid.selectRow();
        if (row) {
            var editDialog = $.hDialog({
                href: "/Html/System/menu.vm?" + Math.random(),
                width: 440,
                height: 360,
                iconCls: 'icon-add',
                title: "修改菜单",
                onLoad: function () {
                    MyAction.InitCtrl(row.menuId);
                    $("#men_Id").val(row.menuId);
                    $("#men_Name").val(row.menuName);
                    $("#men_URL").val(row.menuUrl);
                    $("#men_SmallIcon").val(row.iconCls);
                    $("#showSmallIcon").removeClass("icon showIcon icon-note").addClass("icon showIcon " + row.iconCls);
                    $('#men_Sort').numberbox().numberbox('setValue', row.menuSort);
                    $("#men_IsVisible").val(row.men_IsVisible);
                    $('#men_ParentID').combotree('setValue', row.parentId);

                    $('#men_BigIcon').val(row.bigIcon);
                    $("#showBigIcon").css('background', "url('')").css("cursor", "pointer");
                    $("#showBigIcon").removeClass().html("<img src='/Images/icon/iconBig/" + row.bigIcon + "' border='0' width='16' height='16' />")
                },
                submit: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        $.AjaxColynJson('/system/json/editMenu.json', param, function (data) {
                            if (data.success) {
                                Colyn.log("修改成功！")
                                MyTreeGrid.Init();
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
        var row = MyTreeGrid.selectRow();
        if (row) {
            if($('#MenuGrid').treegrid('getChildren', row.menuId).length>0){
                Colyn.log("有子菜单不能删除！");
                return false;
            }
            $.messager.confirm("删除用户", "确认要删除选中的菜单吗？", function (r) {
                if (r) {
                    $.AjaxColynJson('/system/json/delMenu.json', { id: row.menuId }, function (data) {
                        if (data.success) {
                            Colyn.log("删除成功！")
                            MyTreeGrid.Init();
                            jQuery('#MenuGrid').datagrid('clearSelections')
                        }
                        else {
                            Colyn.log(data.message)
                        }
                    });
                }
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    InitCtrl: function (MenuId) {
        var TreeData = $("#MenuGrid").treegrid("getData");
        TreeData = JSON.stringify(TreeData).replace(/menuId/g, 'id').replace(/menuName/g, 'text');
        if (TreeData.length > 2) {
            TreeData = '[{"id":0,"selected":true,"text":"根菜单"},' + TreeData.substr(1, TreeData.length - 1);
        } else {
            TreeData = '[{"id":0,"selected":true,"text":"根菜单"}]'
        }
        $('#men_ParentID').combotree({
            data: JSON.parse(TreeData),
            valueField: 'id',
            textField: 'text',
            panelWidth: '180',
            editable: false,
            lines: true,
            multiple: false,
            onSelect: function (item) {
                var NodeId = $('#men_ParentID').combotree('getValue');
                if (item.id == MenuId) {
                    $('#men_ParentID').combotree('setValue', NodeId);
                    Colyn.log("上下级菜单不能相同");
                }
            }
        }).combotree('setValue', 0);
        
        //加载图标
        $('#showSmallIcon').click(function () {
            var $this = $(this);
            var iconDialog = $.hDialog({
                iconCls: 'icon-application_view_icons',
                href: '/Html/System/icon.vm?v=' + Math.random(),
                title: '选取图标', width: 800, height: 400, showBtns: false,
                onLoad: function () {
                        $('#iconlist li').attr('style', 'float:left;border:1px solid #fff;margin:2px;width:16px;cursor:pointer').click(function () {
                        var iconCls = $(this).find('span').attr('class').replace('icon ', ''); 
                        $('#men_SmallIcon').val(iconCls);
                        $this.removeClass("icon showIcon icon-note").addClass("icon showIcon " + iconCls);
                        iconDialog.dialog('close');
                    }).hover(function () {
                        $(this).css({ 'border': '1px solid red' });
                    }, function () {
                        $(this).css({ 'border': '1px solid #fff' });
                    });
                }
            });
        });

        $('#showBigIcon').click(function () {
            var $this = $(this);
            var iconDialog = $.hDialog({
                iconCls: 'icon-application_view_icons',
                href: '/Html/System/iconBig.vm?v=' + Math.random(),
                title: '选取图标', width: 800, height: 400, showBtns: false,
                onLoad: function () {
                    $('#Bigiconlist li').click(function () {
                        var iconCls = $(this).attr('title');
                        $('#men_BigIcon').val(iconCls);
                        $this.css('background', "url('')").css("cursor", "pointer");
                        $this.removeClass().html("<img src='/Images/icon/iconBig/"+ iconCls +"' border='0' width='16' height='16' />")
                        iconDialog.dialog('close');
                    }).hover(function () {
                        $(this).css({ 'border': '1px solid red' });
                    }, function () {
                        $(this).css({ 'border': '1px solid #fff' });
                    });
                }
            });
        });
    },
    SetBtn: function () {
        var row = MyTreeGrid.selectRow();
        if (row) {
            var SetBtnDialog = $.hDialog({
                href: "/Html/System/allocatBtn.vm?" + Math.random(),
                width: 500,
                height: 400,
                iconCls: 'icon-user_add',
                title: "分配按钮",
                onLoad: function () {
                    $('#LeftBtn,#RightBtn').datagrid({
                        width: 220,
                        height: 300,
                        iconCls: 'icon-group',
                        rownumbers: true,
                        striped: true,
                        idField: 'btnId',
                        singleSelect: true,
                        columns: [[
                            {
                                title: '图标', field: 'btnIcon', width: 40,align: 'center', formatter: function (v, i, d) {
                                    return '<span class="icon ' + v + '">&nbsp;</span>';
                                }
                            },
                            { title: '按钮名称', field: 'btnName', width: 120, align: 'center' }
                        ]],
                        pagination: false
                    });

                    $('#LeftBtn').datagrid({
                        url: '/system/json/getLeftBtn.json',
                        onDblClickRow: function (rowIndex, rowData) {
                            $('#selectBtn').click();
                        }
                    });

                    $('#RightBtn').datagrid({
                        url: '/system/json/getRightBtn.json?menuId=' + row.menuId,
                        onDblClickRow: function (rowIndex, rowData) {
                            $('#delBtn').click();
                        }
                    });

                    $('#selectBtn').click(function () {
                        var _row = $('#LeftBtn').datagrid('getSelected');
                        if (_row) {
                            var hasBtnName = false;
                            var btns = $('#RightBtn').datagrid('getRows');
                            $.each(btns, function (i, n) {
                                if (n.btnName == _row.btnName) {
                                    hasBtnName = true;
                                }
                            });
                            if (!hasBtnName)
                                $('#RightBtn').datagrid('appendRow', _row);
                            else {
                                Colyn.log("按钮已存在，请不要重复添加");
                            }
                        } else {
                            Colyn.log("请选择一个按钮");
                        }
                    });

                    $('#delBtn').click(function () {
                        var trow = $('#RightBtn').datagrid('getSelected');
                        if (trow) {
                            var rIndex = $('#RightBtn').datagrid('getRowIndex', trow);
                            $('#RightBtn').datagrid('deleteRow', rIndex).datagrid('unselectAll');
                        } else {
                            Colyn.log("请选择一个按钮");
                        }
                    });
                },
                submit: function () {
                    var selectedBtns = $('#RightBtn').datagrid('getRows');
                    var btnIdArr = [];
                    $.each(selectedBtns, function (i, n) {
                        btnIdArr.push(n.btnId);
                    });
                    $.AjaxColynJson("/system/json/allocatBtn.json?menuId=" + row.menuId, { btnids: btnIdArr.join(',') }, function (data) {
                        if (data.success) {
                            Colyn.log("设置成功！");
                        }
                        else {
                            Colyn.log("设置失败");
                        }
                        SetBtnDialog.dialog('close');
                    });
                }
            })
        } else {
            Colyn.log("请选择一条数据进行操作");
        }
    }
}
