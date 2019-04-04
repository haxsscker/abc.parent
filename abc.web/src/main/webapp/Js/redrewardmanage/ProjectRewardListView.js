/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-25
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#Add").click(MyAction.Add);
    $("#LookUp").click(MyAction.LookUp);
    $("#Search").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#ProjectRewardListGrid").datagrid({
        	method: "POST",
            url: "/redrewardmanage/json/ProjectRewardListView.json",
            height: $(window).height() - 140,
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
            {field: "redTheme", title: "奖励主题", width: 100, align: "center" },
            {field: "redAmt", title: "奖励金额(投资者每人:元)", width: 150, align: "right"},
            {field: "redRewardNumber", title: "奖励项目数", width: 80, align: "center" },
            {field: "totalAmount", title: "总计奖励金额", width: 100, align: "right"},
            {field: "redUseScope", title: "使用范围", width: 200, align: "center"},
            {field: "redSendtime", title: "发放日期", width: 100, align: "center", 
            	formatter: function (value) {
            		if(value != null){
                		return creatStringDate(value);
                	}else{
                		return "-";
                	}
                }
            },
            {field: "redClosetime", title: "到期日期", width: 100, align: "center", 
            	formatter: function (value) {
            		if(value != null){
                		return creatStringDate(value);
                	}else{
                		return "-";
                	}
                }
            },
            {
                field: "rsState", title: "状态", width: 100, align: "center",
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
        var p = $('#ProjectRewardListGrid').datagrid('getPager');
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
            href: "/redrewardmanage/ProjectRewardAddView",
            iconCls: 'icon-add',
            title: "添加项目奖励",
            width: $(window).width() - 20,
            height: $(window).height() - 20,
            onLoad: function () {
            },
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    var rows = $('#ProjectRewardAddFormGrid').datagrid('getSelections');
                    if (rows.length == 0) { Colyn.log("请选择一条数据进行操作"); return; }
                    var ids = [];
                    for (var i = 0; i < rows.length; i++) {
                        ids.push(rows[i].loanId);
                    }
                    ids = ids.join();
                    if ($('#Colyn').form('validate')) {
                    	// 红包奖励的项目复选框值传到后台
                        var loanStr = "";
                		$('input[type="checkbox"][name="loanId"]:checked').each(
                            function() {
                            	loanStr = loanStr + $(this).val() + "|";   
                            }
                        );
                		$("#loanIds").val(loanStr);
                		
                		// 红包的使用范围复选框值传到后台
                        var scopeStr = "";
                		$('input[type="checkbox"][name="redUseScope"]:checked').each(
                            function() {
                            	scopeStr = scopeStr + $(this).val() + "|";   
                            }
                        );
                		$("#redUseScopes").val(scopeStr);
                    	
                		// 序列化表单数据
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        
                        var param1 = createParam4([
                            { formClass: 'RedDetail', isAnyRow: true }
                        ]);
                        var queryParam = getParam(param) + "&" + getParam(param1);
                        
                        $.AjaxColynJson('/redrewardmanage/json/ProjectRewardAddData.json', queryParam, function (data) {
                            if (data.Sucess) {
                                Colyn.log(data.message);
                            }
                            else {
                                Colyn.log(data.message);
                            }
                        });
                        MyAction.Init();
                      //  $('#ProjectRewardListGrid').datagrid('reload');
                        addDialog.dialog("close");
                    }
                }
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
            title: "项目奖励红包详情",
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
        $.post("/redrewardmanage/json/ProjectRewardListView.json?t=" + new Date() + "&" + getParam(o), param, function (data) {
            $("#ProjectRewardListGrid").datagrid("loadData", data);
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
