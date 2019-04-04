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
        $("#MonthRptManagementGrid").datagrid({
            method: "POST",
            url: "/banel/json/actionMonthRptView.json",
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
                {field: "rptTitle", title: "标题", width: 150, halign:"center", align: "center"},
                {field: "rptYear", title: "年份", width: 80, halign:"center", align: "center"},
                {field: "rptMonth", title: "月份", width: 50, halign:"center", align: "center",
                	formatter: function(value,row,index){
                		if(value=='1'){
                			return '一月';
                		}else if(value=='2'){
                			return '二月';
                		}else if(value=='3'){
                			return '三月';
                		}else if(value=='4'){
                			return '四月';
                		}else if(value=='5'){
                			return '五月';
                		}else if(value=='6'){
                			return '六月';
                		}else if(value=='7'){
                			return '七月';
                		}else if(value=='8'){
                			return '八月';
                		}else if(value=='9'){
                			return '九月';
                		}else if(value=='10'){
                			return '十月';
                		}else if(value=='11'){
                			return '十一月';
                		}else if(value=='12'){
                			return '十二月';
                		}
	    			}},
                { field: "logoUrl", title: "图片", width: 150, halign:"center", align: "center",
                	formatter: function(value,row,index){
    					return '<img src="' + value + '" style="height:30px;"/>';
	    			}
                },
	            { field: "fileUrl", title: "附件", width: 70, halign:"center", align: "center" , 
                	formatter: function (value, row, index) {
                		//if ($("#isUpload").val() == "False") {
                		//return "<a href='#' onclick =\"DownLoadFile('" + row.file_Path + "','" + row.file_Name + "')\">预览</a>";
                		//}
                		return "<a href='javascript:;' onclick =\"MyAction.DownLoadFile('" + row.fileUrl + "','"+row.rptYear+"年"+row.rptMonth+"月份报告.pdf" + 
                		"')\">查看</a>";
                	}
                },
                {field: "createTime", title: "创建时间", width: 100, halign:"center", align: "center"},
                {field: "modifyTime", title: "修改时间", width: 100, halign:"center", align: "center", 
                	formatter: function (value, row, index) {
                		if(value==''||value==null){
                			return "未修改";
                		}else{
                			return value;
                		}
                	}}
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#MonthRptManagementGrid').datagrid('getPager');
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
            href: "/banel/MonthRptFormView?type=1",
            iconCls: 'icon-add',
            title: "添加",
            width: 500,
            height: 400,
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                 if ($('#Colyn').form('validate')) {
                	if($('#logoUrl').val().length == 0) {
                		alert('请上传图片');
                		return;
                	}
                	if($('#fileUrl').val().length == 0) {
                		alert('请上传附件');
                		return;
                	}
                	var param = $('#Colyn').serializeArray();
                    param = convertArray(param);
                    $.AjaxColynJson('/banel/json/addMonthRptView.json', param, function (data) {
                        if (data.success) {
                            Colyn.log("保存成功");
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.message);
                        }

                        MyAction.Init();
                        $("#MonthRptManagementGrid").datagrid("clearSelections");
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
                href: "/banel/monthRptFormView?type=2&id="+row.rptId,
                iconCls: 'icon-pencil',
                title: "修改",
                width: 500,
                height: 400,
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                        	if($('#logoUrl').val().length == 0) {
                        		alert('请上传图片');
                        		return;
                        	}
                        	if($('#fileUrl').val().length == 0) {
                        		alert('请上传附件');
                        		return;
                        	}
                            var param = $('#Colyn').serializeArray();
                            param = convertArray(param);

                            $.AjaxColynJson('/banel/json/editMonthRptView.json', param, function (data) {
                                if (data.success) {
                                    Colyn.log("修改成功");
                                }
                                else {
                                    Colyn.log(data.message);
                                }
                                MyAction.Init();
                                $("#MonthRptManagementGrid").datagrid("clearSelections");
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
                    $.AjaxColynJson('/banel/json/delMonthRptView.json?id=' + row.rptId, function(data) {
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
   
            $.post("/banel/json/actionMonthRptView.json?t=" + new Date() + "&" + getParam(o), param, function (data) {
                $("#MonthRptManagementGrid").datagrid("loadData", data);
            }, "json");

    },
    //预览
    DownLoadFile : function (filePath, fileName) {
    	var curWwwPath=window.document.location.href;
    	var pathName=window.document.location.pathname;
        var pos=curWwwPath.indexOf(pathName);
        var path = curWwwPath.substring(0,pos);
        console.log(path+filePath);
        window.open(path+filePath);
    },

    //下载
    DownLoadFile2:function (filePath, fileName) {
    	window.location = '/common/json/downloadFile.json?path='+filePath+'&fileName='+fileName;
    }
   
};


