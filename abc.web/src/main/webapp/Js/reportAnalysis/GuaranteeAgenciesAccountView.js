/*------------------------------------------------
 * Author:潘健  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#LookUp").click(MyAction.LookUp);
    $("#Search").click(MyAction.Search);
    $("#Export").click(MyAction.Export);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});
var getData = function (page, rows) {
    $.ajax({
        type: "POST",
        url: "/reportAnalysis/json/GuaranteeAgencies.json?" + createSearchParam(),
        data: "page=" + page + "&rows=" + rows,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.progress('close');
        },
        success: function (rows) {
            $('#GuaranteeAgenciesAccountGrid').datagrid('loadData', rows);
        }
    });
};

var MyAction = {
    RefreshPanl: function () {
        MyGrid.Resize();
        $('#layout').layout('resize');
    },
    Init: function () {
        $("#GuaranteeAgenciesAccountGrid").datagrid({
            method: "GET",
            url: "/reportAnalysis/json/GuaranteeAgencies.json?"+ createSearchParam(),//具体获取对账表数据的地方
            height: $(window).height() - 95,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { 
            	field: "proposer", title: "申请人", width: 100, align: "center"
            },
            { 
            	field: "proposerRealName", title: "真实姓名", width: 100, align: "center"
            },
            {
            	field: "proposertype", title: "申请人类型", width: 100, align: "center"
            },
            {
            	field: "pro_user_name", title: "借款人", width: 100, align: "center"
            },
            {
            	field: "recharge_amount", title: "融资总额", width: 100, align: "right",formatter: function (value, rowData, index) {
                	return formatMoney(value, '￥');
            	}	
            },
            { 
            	field: "recovery_principal", title: "已还本金", width: 100, align: "right",formatter: function (value, rowData, index) {
                	return formatMoney(value, '￥');
            	}
            },
            { 
            	field: "recovery_interest", title: "已还利息", width: 100, align: "right",formatter: function (value, rowData, index) {
                	return formatMoney(value, '￥');
            	}
            },
            { 
            	field: "recovery_amount", title: "已还逾期罚息", width: 100, align: "right",formatter: function (value, rowData, index) {
                	return formatMoney(value, '￥');
            	} 
            },
            //add by 夏同同  20160419  新增统计违约罚金
            { 
            	field: "recovery_breach_amount", title: "已还违约罚金", width: 100, align: "right",formatter: function (value, rowData, index) {
                	return formatMoney(value, '￥');
            	} 
            },
            {
                field: "pro_collect_service_fee", title: "已还服务费", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "pro_collect_guar_fee", title: "已还担保费", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "pro_collect_total", title: "已还款总额", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            { field: "foreclosure_investing", title: "未还款总额", width: 100, align: "right", formatter: function (value, rowData, index) {
                	return formatMoney(value, '￥');
            	}
            },
            {
                field: "not_pay_money", title: "未还本金", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "not_pay_rate", title: "未还利息", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
//             {
//                 field: "not_pay_over_rate", title: "未还罚金", width: 100, align: "right", formatter: function (value, rowData, index) {
//                     return formatMoney(value, '￥');
//                 }
//             },
            {
                field: "not_pay_service_fee", title: "未还服务费", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            }
           // { field: "assignment", title: "可用余额", width: 100, align: "right" }
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#GuaranteeAgenciesAccountGrid').datagrid('getPager');
        $(p).pagination({
        	 onSelectPage: function (pageNumber, pageSize) {
                 getData(pageNumber, pageSize);
             },
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
    LookUp: function ()
    {
        var row = MyGrid.selectRow();
        if (row == null)
        {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/reportAnalysis/GuaranteeAgenciesLookUpView",
            iconCls: 'icon-add',
            title: "借款人账表详情",
            width: $(window).width() - 40,
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
    Search: function()
    {
    	var param = createSearchParam();
        var o = { modelAction: "Search" };
        $.post( "/reportAnalysis/json/GuaranteeAgencies.json?" + getParam(o), param, function (data) {
        	$('#GuaranteeAgenciesAccountGrid').datagrid('loadData', {total : 0,rows : []});
			$("#GuaranteeAgenciesAccountGrid").datagrid("loadData", data);
        }, "json");
    },
    Export: function(){
    	$("#form1").submit();
    }
}

function createSearchParam() {
    var form = {
            borrowerName : $("#pro_user_name").val()
        };
    if ($(".pagination-num").val()) {
        form.page = $(".pagination-num").val();
    }

    if ($(".pagination-page-list").val()) {
        form.rows = $(".pagination-page-list").val();
    }

    return getParam(form);
}