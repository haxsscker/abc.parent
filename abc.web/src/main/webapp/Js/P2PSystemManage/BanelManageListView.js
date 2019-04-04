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
        $("#BanelManagementGrid").datagrid({
            method: "POST",
            url: "/banel/json/actionBanelView.json",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
                { field: "banelUrl", title: "图片", width: 150, halign:"center", align: "left",
                	formatter: function(value,row,index){
    					return '<img src="' + value + '" style="height:30px;"/>';
	    			}
                },
	            { field: "linkUrl", title: "链接地址", width: 150, halign:"center", align: "left" },
	            {field: "groupCode", title: "所属组", width: 150, halign:"center", align: "left", 
	            	formatter: function(value,row,index){
	    				if(value == 'index') {
	    					return '网站首页';
	    				} else if(value=='app'){
	    					return '手机端首页';
	    				}
	    			}
	            }
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#BanelManagementGrid').datagrid('getPager');
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
            href: "/banel/banelFormView",
            iconCls: 'icon-add',
            title: "添加Banel",
            width: 500,
            height: 400,
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                 if ($('#Colyn').form('validate')) {
                	if($('#banelUrl').val().length == 0) {
                		alert('请上传图片');
                		return;
                	}
                	var param = $('#Colyn').serializeArray();
                    param = convertArray(param);
                    $.AjaxColynJson('/banel/json/addBanelView.json', param, function (data) {
                        if (data.success) {
                            Colyn.log("保存成功");
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.Msg);
                        }

                        MyAction.Init();
                        $("#BanelManagementGrid").datagrid("clearSelections");
                        addDialog.dialog('close');
                    });
                 }
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                	addDialog.dialog("close");
                }
            }]

        });

    },
    Edit: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var editDialog = $.hDialog({
                href: "/banel/banelFormView?id="+row.id,
                iconCls: 'icon-pencil',
                title: "修改Banel",
                width: 500,
                height: 400,
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                        	if($('#banelUrl').val().length == 0) {
                        		alert('请上传图片');
                        		return;
                        	}
                            var param = $('#Colyn').serializeArray();
                            param = convertArray(param);

                            $.AjaxColynJson('/banel/json/editBanelView.json', param, function (data) {
                                if (data.success) {
                                    Colyn.log("修改成功");
                                }
                                else {
                                    Colyn.log("修改失败");
                                }
                                MyAction.Init();
                                $("#BanelManagementGrid").datagrid("clearSelections");
                                editDialog.dialog('close');
                            });
                        }
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                    	editDialog.dialog("close");
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
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r) {
                    $.AjaxColynJson('/banel/json/delBanelView.json?id=' + row.id, function(data) {
                        if (data.success) {
                            Colyn.log("删除成功");
                        }
                        else {
                            Colyn.log("删除失败");
                        }
                        MyAction.Init();
                    })
                }
            })
        }
        else {
            Colyn.log("请选择内容删除！");
        }
    }
   
};


