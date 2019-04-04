/*------------------------------------------------
 * Author:fangrui  Date：2014-12-30
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#Search").click(MyAction.Search);
    $("#Add").click(MyAction.Add);
    $("#Enable").click(MyAction.Enable);
    $("#Unable").click(MyAction.Unable);
    $("#Del").click(MyAction.Del);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#SuccessAdvancedListGrid").datagrid({
            method: "POST",
            url: "/redrewardmanage/json/SuccessRewardListView.json",
            height: $(window).height() - 140,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            idField: "redId",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            {
                field: "redTheme", title: "红包标题", width: 100, align: "center",
            },
            {
                field: "redAmount", title: "发放金额", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "redUseScope", title: "使用范围", width: 100, align: "center",
                formatter: function (value) { if (!value) return "-"; else return value; }
                	/*, formatter: function formatProduct(val, rowData, i) {
                    if (val == "" || val == null)
                    {
                        return "-";
                    }
                    else
                    {
                        var obj = $.ajax({ url: "/redrewardmanage/GetProductData?red_use=" + val, async: false });
                        return obj.responseText;
                    }
                }*/
            },
            {
                field: "redSendtime", title: "发放日期", width: 100, align: "center",
                formatter: function (value) {
                	if(value != null){
                		return creatStringDate(value);
                	}else{
                		return "-";
                	}
            	}
            },
            {
                field: "redClosetime", title: "到期日期", width: 100, align: "center",
                formatter: function (value) {
                	if(value != null){
                		return value + "天";
                	}else{
                		return "-";
                	} 
            	}
            },
            {
                field: "redState", title: "状态", width: 100, align: "center",
                formatter: function (value) {
                    if(value == "DELETE"){
                    	return "已删除";
                    }else if(value == "FAILURE"){
                    	return "失效";
                    }else if(value == "EFFECTIVE"){
                    	return "有效";
                    }else{
                    	return "-";
                    }
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#SuccessAdvancedListGrid').datagrid('getPager');
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
        
        var addDialog = $.hDialog({
            href: "/redrewardmanage/ScoreRedRewardAddView?t=1",
            iconCls: 'icon-add',
            title: "添加个人奖励",
            width: $(window).width() - 20,
            height: $(window).height() - 20,
            onLoad: function () {
 			
            },
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    //if ($('#Colyn').form('validate')) {
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        
	                    var param1 = createParam4([
	                          { formClass: 'RedDetail', isAnyRow: true }
	                    ]);
	                    var queryParam = getParam(param) + "&" + getParam(param1);
	                    
	                    //适用范围
	                    var chk_value ="";
	                    $('input[name="redUseScope"]:checked').each(function(){ 
	                    	chk_value = chk_value + $(this).val() + ",";
	                    });
	                    queryParam = queryParam + "&redUseScopes=" + chk_value;
	                    
                        $.AjaxColynJson('/redrewardmanage/json/ScoreRedRewardAddData.json', queryParam, function (data) {
                            if (data.success) {
                                Colyn.log(data.message);
                                MyAction.Init();
                            }
                            else {
                                Colyn.log(data.message);
                            }
                                
                            MyAction.Init();
                            $("#SuccessAdvancedListGrid").datagrid("clearSelections");
                            addDialog.dialog('close');
                        });
                    }
                    
                //}
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    addDialog.dialog("close");
                }
            }]
        })
    },
    //启用
    Enable: function () {
    	var row = MyGrid.selectRow();
    	if(row==null){
    		Colyn.log("请先选择一条记录！");
    		return;
    	}
    	if(row.redState == "EFFECTIVE"){
    		Colyn.log("该红包已启用！");
    		return;
    	}
        $.post("/redrewardmanage/json/RewardEnable.json?enable=true&redId="+row.redId, function (data) {
            if (data.success) {
                Colyn.log("启用成功！");
            } else {
                Colyn.log(data.message);
            }

            $('#NoviceExclusiveListGrid').datagrid('loadData', {total: 0, rows: []});
            MyAction.Init();
        }, "json");
    },
    //停用
    Unable: function () {
    	var row = MyGrid.selectRow();
    	if(row==null){
    		Colyn.log("请先选择一条记录！");
    		return;
    	}
    	if(row.redState == "FAILURE"){
    		Colyn.log("该红包已停用！");
    		return;
    	}
        $.post("/redrewardmanage/json/RewardEnable.json?enable=false&redId="+row.redId, function (data) {
            if (data.success) {
                Colyn.log("停用成功！");
            } else {
                Colyn.log(data.message);
            }

            $('#NoviceExclusiveListGrid').datagrid('loadData', {total: 0, rows: []});
            MyAction.Init();
        }, "json");
    },
    //删除
    Del: function () {
    	var row = MyGrid.selectRow();
    	if(row==null){
    		Colyn.log("请先选择一条记录！");
    		return;
    	}
    	console.info("del"+row.redId);
		 $.messager.confirm("操作提示", "您确定要删除这条记录吗？", function (data) {
			 
	         if (data) {
	             $.post("/redrewardmanage/json/RewardDel.json?redId="+row.redId, function (data) {
	                 if (data.success) {
	                     Colyn.log("删除成功！");
	                 } else {
	                     Colyn.log(data.message);
	                 }

	                 MyAction.Init();
	                 $("#SuccessAdvancedListGrid").datagrid("clearSelections");
	             }, "json");
	         }//end if
	     });
    },
    //查询
    Search: function () {
    var param = createParam3("SearchForm");
    var o = { modelAction: "Search" };
    $.post("/redrewardmanage/json/SuccessRewardListView.json?t=" + new Date() + "&" + getParam(o), param, function (data) {
        $("#SuccessAdvancedListGrid").datagrid("loadData", data);
    }, "json");
}
}

//格式化时间
function creatStringDate(t){
	var d=new Date(t);
	var year=d.getFullYear();
	var day=d.getDate();
	var month=+d.getMonth()+1;
	
	var f=year+"-"+formate(month)+"-"+formate(day);
	return f;
	}
	function formate(d){
	return d>9?d:'0'+d;
	}
