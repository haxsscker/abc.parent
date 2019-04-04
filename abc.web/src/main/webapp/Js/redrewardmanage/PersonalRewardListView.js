/*------------------------------------------------
 * Author:fangrui  Date：2015-02-04
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#Add").click(MyAction.Add);
    //$("#LookUp").click(MyAction.LookUp); 
    $("#Search").click(MyAction.Search);
    
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#PersonalRewardListGrid").datagrid({
            method: "POST",
            url: "/redrewardmanage/json/PersonalRewardListView.json",
            height: $(window).height() - 140,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "sys_reward_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "rsTheme", title: "奖励主题", width: 100, align: "center" },
            { field: "userName", title: "奖励用户", width: 100, align: "center" },
            {
                field: "rsValidAmount", title: "红包金额", width: 100, align: "right",
                	formatter: function (value, rowData, index) {
                		return formatMoney(value, '￥');
                	}
            },
            {
                field: "rsUseScope", title: "使用范围", width: 200, align: "center"
            },
            {
                field: "rsSendtime", title: "发放日期", width: 100, align: "center",
                formatter: function (v) {
                	if(v != null){
                		return creatStringDate(v);
                	} else{
                		return "-";
                	}
                }
            },
            {
                field: "rsClosetime", title: "到期日期", width: 100, align: "center",
                formatter: function (v) {
                	if(v != null){
                		return creatStringDate(v);
                	} else{
                		return "-";
                	}
                }
            },
            {
                field: "rsState", title: "状态", width: 100, align: "center",
                formatter: function (value) {
                    if(value == "FAILURE"){
                    	return "已过期";
                    }else if(value == "WITHOUT_USE"){
                    	return "未使用";
                    }else if(value == "USE"){
                    	return "已使用";
                    }else{
                    	return "-";
                    }
                }
            }
            
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#PersonalRewardListGrid').datagrid('getPager');
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
            href: "/redrewardmanage/PersonalRewardAddView?t=1",
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
	                    
                        $.AjaxColynJson('/redrewardmanage/json/PersonalRewardAddData.json', queryParam, function (data) {
                            if (data.success) {
                                Colyn.log(data.message);
                                MyAction.Init();
                            }
                            else {
                                Colyn.log(data.message);
                            }
                                
                            MyAction.Init();
                            $("#PersonalRewardListGrid").datagrid("clearSelections");
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
    //查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/redrewardmanage/PersonalRewardLookUpView?redId=" + row.redId,
            iconCls: 'icon-add',
            title: "个人奖励红包详情",
            width: $(window).width() - 20,
            height: $(window).height() - 50,
            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })
    },
    //查询
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/redrewardmanage/json/PersonalRewardListView.json?t=" + new Date() + "&" + getParam(o), param, function (data) {
            $("#PersonalRewardListGrid").datagrid("loadData", data);
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

