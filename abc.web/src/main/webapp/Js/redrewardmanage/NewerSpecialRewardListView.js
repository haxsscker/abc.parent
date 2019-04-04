/*------------------------------------------------
 * Author:fangrui  Date：2014-12-30
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#Search").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
	Init: function () {
        $("#NovicePrivilegeListGrid").datagrid({
            method: "POST",
            url: "/redrewardmanage/json/InvestRewardListView.json",
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
        var p = $('#NovicePrivilegeListGrid').datagrid('getPager');
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
    //查询
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/redrewardmanage/json/InvestRewardListView.json?t=" + new Date() + "&" + getParam(o), param, function (data) {
            $("#NovicePrivilegeListGrid").datagrid("loadData", data);
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
