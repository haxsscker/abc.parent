$(function () {
    MyTreeGrid.Resize();
    MyTreeGrid.Init();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
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
            url: "/system/json/getWeChatMenuItem.json",
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
				{
					field : 'menuImage',
					title : '按钮图片',
					width : 200,
					align : 'center',
					formatter : function(value, row, index) {
					if (value != "" && value != null) {
						return '<img src="'
						+ value
						+ '" width="150" height="100"/>';
					} else {
						return "-";
						}
					}
				},
              
                { title: '按钮关键字', field: 'menuKey', align: 'center', width: 150 },
                { title: '按钮类型', field: 'menuType', align: 'center', width: 150 },
                { title: '链接地址', field: 'menuUrl', align: 'center', width: 320 },
                {
                    title: '是否启用', field: 'isVisible', align: 'center', width: 100,
                    formatter: function (v, d, i) {
                        if (v == 1) {
                            return "已启用";
                        }
                        else if (v == 0) {
                            return "未启用";
                        }
                        else {
                            return "无";
                        }
                    }
                },
                { title: '排序', field: 'menuSort', align: 'center', width: 60 },
                { title: '是否为授权网页', field: 'menuSafe', align: 'center', width: 60,
                	formatter: function (v, d, i) {
                        if (v == 0) {
                            return "非授权网页";
                        }
                        else if (v == 1) {
                            return "授权网页";
                        }
                        else {
                            return "-";
                        }
                    }
                }
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
            href: "/Html/System/weChatMenu.vm?" + Math.random(),
            width: 400,
            height: 350,
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
                    $.AjaxColynJson('/system/json/addWeChatMenu.json', param, function (data) {
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
                href: "/Html/System/weChatMenu?" + Math.random(),
                width: 400,
                height: 350,
                iconCls: 'icon-add',
                title: "修改菜单",
                onLoad: function () {
                    MyAction.InitCtrl(row.menuId);
                    $("#men_Id").val(row.menuId);
                    $("#men_Name").val(row.menuName);
                    $("#men_URL").val(row.menuUrl);
                    $('#men_Sort').numberbox().numberbox('setValue', row.menuSort);
                    $("#men_IsVisible").val(row.men_IsVisible);
                    $('#men_ParentID').combotree('setValue', row.parentId);
                    $('#men_Key').val(row.menuKey);
                    $('#men_Type').val(row.menuType);
                    $("#men_Key1").val(row.menuKey);
                    $("#menu_image").attr("src",row.menuImage);
                },
                submit: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        $.AjaxColynJson('/system/json/editWeChatMenu.json', param, function (data) {
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
                    $.AjaxColynJson('/system/json/delWeChatMenu.json', { id: row.menuId }, function (data) {
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
    }
}
