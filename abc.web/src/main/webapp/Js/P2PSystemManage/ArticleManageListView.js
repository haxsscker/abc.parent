/*------------------------------------------------
 * Author:潘健  Date：2014-8-26
-------------------------------------------------*/
var ue;
$(function () {
    MyGrid.Resize();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#Search").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });

});
var MyAction = {
    Init: function () {
        $("#ArticleManagementGrid").datagrid({
            method: "POST",
            url: "/article/json/actionArticleView.json",
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
            { field: "sys_article_title", title: "文章标题", width: 150, align: "center" },
            { field: "className", title: "栏目名称", width: 150, align: "center" },
            {
                field: "sys_add_date", title: "发布日期", width: 150, align: "center"
            },
            {
                field: "sys_is_top", title: "是否置顶", align: "center", width: 80,
                formatter: function (v, d, i) {
                    return '<img src="/Images/' + (v == '1' ? "checkmark.gif" : "checknomark.gif") + '" />';
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#ArticleManagementGrid').datagrid('getPager');
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
            href: "/article/articleAddView",
            iconCls: 'icon-add',
            title: "添加文章",
            //maximizable: true,//显示最大化
            width: $(window).width(),
            height: $(window).height(),
            onLoad: function () {
            	ue = UE.getEditor('Ueditor');
            	$("#Ueditor").removeClass("validatebox-text");
            },
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                 if ($('#Colyn').form('validate')) {
                    var param = $('#Colyn').serializeArray();
                    param = convertArray(param);
                    //param.sys_article_content = encodeURIComponent(param.sys_article_content);
                    ue.ready(function(){   
                	param.sys_article_content = ue.getContent();
					});
                    console.log(param.sys_article_content);
                    if(param.sys_article_content==null || param.sys_article_content==''){
                    	Colyn.log("请输入文章内容");
                    	return;
                    }
                    $.AjaxColynJson('/article/json/addArticleView.json', param, function (data) {
                        if (data.success) {
                            Colyn.log("保存成功");
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.Msg);
                        }

                        MyAction.Init();
                        $("#ArticleManagementGrid").datagrid("clearSelections");
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
                href: "/article/articleAddView?articleId="+row.sys_article_id,
                iconCls: 'icon-pencil',
                title: "修改文章",
                width: $(window).width() - 50,
                height: $(window).height() - 50,
                onLoad: function () {
                	ue = UE.getEditor('Ueditor');
                	$("#Ueditor").removeClass("validatebox-text");
	                	ue.ready(function(){    
	                	ue.setContent(row.sys_article_content);
                	});
                },
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
//                    alert($('#Colyn').form('validate'));
                        if ($('#Colyn').form('validate')) {
                            var param = $('#Colyn').serializeArray();
                            param = convertArray(param);
                            ue.ready(function(){    
                            	param.sys_article_content = ue.getContent();
            				});

                            $.AjaxColynJson('/article/json/editArticleView.json', param, function (data) {
                                if (data.success) {
                                    Colyn.log("修改成功");

                                }
                                else {
                                    Colyn.log("修改失败");
                                }
                                MyAction.Init();
                                $("#ArticleManagementGrid").datagrid("clearSelections");
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
                    $.AjaxColynJson('/article/json/delArticleView.json?article_id=' + row.sys_article_id, function (data) {
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
    },
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
   
            $.post("/article/json/actionArticleView.json?t=" + new Date() + "&" + getParam(o), param, function (data) {
                $("#ArticleManagementGrid").datagrid("loadData", data);
            }, "json");

    }
   
}
 function createSearchParam() {
	var form = {
		sys_article_title : $("#sys_article_title").val(),
		sys_max_add_date : $("#sys_max_add_date").val(),
		sys_min_add_date : $("#sys_min_add_date").val(),
		sys_class_id : $("#sys_class_id").val(),
	};
	return getParam(form);
}


