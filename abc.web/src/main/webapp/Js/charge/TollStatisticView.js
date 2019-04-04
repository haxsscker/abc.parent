/*------------------------------------------------
 * Author:潘健  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#Add").click(MyAction.Add);
    $("#search").click(MyAction.Search);
    $("#Export").click(MyAction.ExcelExport);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    RefreshPanl: function () {
        MyGrid.Resize();
        $('#layout').layout('resize');
    },
    Init: function () {
    	var param = createParam3("SearchForm");
        $("#TollStatisticGrid").datagrid({
            method: "GET",
            url: "/charge/json/SearchTollStatistic.json?"+param,
            height: $(window).height() - 120,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "project_number", title: "项目名称", width: 150, align: "center" },
            { field: "project_type", title: "项目类型", width: 80, align: "center" },
            { field: "borrower", title: "借款人", width: 100, align: "center" },
            { 
            	field: "borrowing_amount", title: "借款金额", width: 100, align: "right",formatter: function (value, rowData, index) {
            	return formatMoney(value, '￥');
        	}	
            },
            { field: "annual_rate", title: "年化收益率", width: 80, align: "right" ,
            	formatter: function(value, rowData, index) {
	                if (value != null) {
	                    return value + "%";
	                } else {
	                    return value;
	                }
            	}
            },
            { field: "loan_period", title: "借款期限", width: 80, align: "center" },
            { 
            	field: "service_fee", title: "收取服务费", width: 80, align: "right",formatter: function (value, rowData, index) {
            	return formatMoney(value, '￥');
        	}	 
            	
            },
            { 
            	field: "charge_fee", title: "收取手续费", width: 80, align: "right", formatter: function (value, rowData, index) {
            	return formatMoney(value, '￥');
        	}	
            },
            { field: "star_date", title: "开始日期", width: 100, align: "center" },
            { field: "end_date", title: "到期日期", width: 100, align: "center" },
            { field: "guarantee_institutions", title: "担保机构", width: 100, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#TollStatisticGrid').datagrid('getPager');
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
        $.post("/charge/json/SearchTollStatistic.json", param, function (data) {
             $("#TollStatisticGrid").datagrid("loadData", data)
        }, 'json');
    },
    //导出excel
    ExcelExport: function(){
//    	alert('ss');
//    	$('#f').attr("action","/charge/json/SearchTollStatisticExcel.json");
		$('#form1').submit();
    }
}