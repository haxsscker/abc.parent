/*------------------------------------------------
 * Author:潘健  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    //$("#Enable").click(MyAction.Enable);
    $("#btnSearch").click(MyAction.Search);
    $("#CashQuotaApply").click(MyAction.CashQuotaApply);
    $("#toBoHaiReg").click(MyAction.ToBoHaiReg);
    $("#getBoHaiRegResult").click(MyAction.GetBoHaiRegResult);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#AccountManagementListGrid").datagrid({
            //method: "GET",
            url: "/dataMigration/json/AccountManagementListView.json",
            height: $(window).height() - 15,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
           // remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "userId", title: "用户ID", width: 100, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "userName", title: "客户名称", width: 100, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "userRealName", title: "真实姓名", width: 100, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "userDocType", title: "证件类型", width: 100, align: "center", formatter: function (value) { if (!value) return "-"; else return value;}},
            { field: "userDocNo", title: "证件号码", width: 180, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "userPhone", title: "手机号码", width: 180, align: "center", formatter: function (value) { if (!value) return "-"; else return value; } },
            { field: "userRegisterDate", title: "注册日期", width: 150, align: "center", formatter: function (value) { if (value ) { return creatStringDate(value);}else return "-"}},
            { field: "accountKind", title: "账户体系", width: 80, align: "center" ,formatter:function(value){if(value == "DM"){return "双乾"}else if(value == "BOHAI")return "渤海银行";else if(value == "HANDLING")return "处理中"; else return "-"}}
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#AccountManagementListGrid').datagrid('getPager');
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
    //搜索
    Search: function () {
       var param = createParam3("SearchForm");
       console.log(param);
       var o = { modelAction: "Search" };
       $.AjaxColynJson("/dataMigration/json/AccountManagementListView.json?"+ getParam(o) ,param, function (data) {
    	   $("#AccountManagementListGrid").datagrid("loadData", data);
       },JSON)
    },
    //类发送渤海银行批量注册
    ToBoHaiReg: function () {
       var param = createParam3("SearchForm");
       console.log(param);
       var o = { modelAction: "Search" };
       $.AjaxColynJson("/dataMigration/json/BatchUserRegister.json?"+ getParam(o) ,param, function (data) {
    	   if(data)
    		   Colyn.log(data.message);
       },JSON)
    },
    //下载并解析渤海银行注册结果
    GetBoHaiRegResult: function () {
        $.AjaxColynJson("/dataMigration/json/DownLoadBatchUserReg.json",'', function (data) {
        	if(data)
     		   Colyn.log(data.message);
        },JSON)
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
	
	
	function createSearchParam() {
	    var form = {
	        userName : $("#userName").val(),
	        userRealName : $("#userRealName").val(),
	        userRecommendUserid : $("#userRecommendUserid").val(),
	        userState : $("#userState").val()
	    };

	    if ($(".pagination-num").val()) {
	        form.page = $(".pagination-num").val();
	    }
	    if ($(".pagination-page-list").val()) {
	        form.rows = $(".pagination-page-list").val();
	    }

	    return getParam(form);
	}
