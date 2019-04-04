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
        url: "/reportAnalysis/json/InvestorCapital.json?" + createSearchParam(),
        data: "page=" + page + "&rows=" + rows,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.progress('close');
        },
        success: function (rows) {
            $('#InvestorCapitalAccountGrid').datagrid('loadData', rows);
        }
    });
};
var MyAction = {
    RefreshPanl: function () {
        MyGrid.Resize();
        $('#layout').layout('resize');
    },
    Init: function () {
        $("#InvestorCapitalAccountGrid").datagrid({
            method: "GET",
            url: "/reportAnalysis/json/InvestorCapital.json?"+ createSearchParam(),//具体获取列表数据的URL

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
            { field: "customer_name", hidden:true, title: "客户名称", width: 100, align: "center" },
            { field: "real_name", title: "真实姓名", width: 100, align: "center" },
            //{ field: "assets_total", title: "资产总额", width: 100, align: "right",  formatter: function (value, rowData, index) {
            //    return formatMoney(value, '￥');
            //	}
            //},
            //{
            //    field: "availableBalance", title: "可用余额", width: 100, align: "right", formatter: function (value, rowData, index) {
            //        return formatMoney(value, '￥');
            //    }
            //},
            //{
            //    field: "amountFrozen", title: "冻结金额", width: 100, align: "right", formatter: function (value, rowData, index) {
            //        return formatMoney(value, '￥');
            //    }
            //},
            {
                field: "pro_collect_money", title: "回收本金", width: 100, align: "right", formatter: function (value, rowData, index) {
                return formatMoney(value, '￥');
            }
            },
            {
                field: "pro_collect_rate", title: "回收利息", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "pro_collect_over_rate", title: "回收罚息", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "pro_invest_money", title: "投资金额", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "buy_money", title: "买入债权", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "transfer_money", title: "转让债权", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "transfer_fee", title: "转让手续费", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            //{
            //    field: "purchase_money", title: "收购债权", width: 100, align: "right", formatter: function (value, rowData, index) {
            //        return formatMoney(value, '￥');
            //    }
            //},
            //{
            //    field: "purchasefee", title: "收购手续费", width: 100, align: "right", formatter: function (value, rowData, index) {
            //        return formatMoney(value, '￥');
            //    }
            //}
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#InvestorCapitalAccountGrid').datagrid('getPager');
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
            href: "/reportAnalysis/InvestorAgenciesLookUpView",
            iconCls: 'icon-add',
            title: "投资人账表详情",
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
    Search: function () {
        var param = createSearchParam("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/reportAnalysis/json/InvestorCapital.json?" + getParam(o), param, function (data) {
            $('#InvestorCapitalAccountGrid').datagrid('loadData', {total : 0,rows : []});
			$("#InvestorCapitalAccountGrid").datagrid("loadData", data);
        }, "json");
    },
    Export: function(){
    	$("#form1").submit();
    }
}
function createSearchParam() {
    var form = {
            investorName : $("#cst_user_name").val(),
            investorRealName : $("#cst_real_name").val()
        };
    if ($(".pagination-num").val()) {
        form.page = $(".pagination-num").val();
    }

    if ($(".pagination-page-list").val()) {
        form.rows = $(".pagination-page-list").val();
    }

    return getParam(form);
}