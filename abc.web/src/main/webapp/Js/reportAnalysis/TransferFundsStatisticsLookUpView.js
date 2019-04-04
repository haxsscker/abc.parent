/*------------------------------------------------
 * Author:潘健  Date：2014-9-01 
-------------------------------------------------*/

var MyAction = {
    Init: function () {
        $("#TransferFundsStatisticsLookUpGrid").datagrid({
        	method: "GET",
            url: "/reportAnalysis/json/TransferFundsStatisticsLookUpView.json?model=search&" + createSearchParam(),
            height: $(window).height() - 205,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "loanNo", title: "项目名称", width: 150, align: "center" },
            { field: "realName", title: "借款人", width: 150, align: "center" },
            {
                field: "loanMoney", title: "借款金额", width: 120, align: "right",
                formatter: function (v, r, i) {
                    return formatMoney(v, '￥');
                }
            },
            {
                field: "loanRate", title: "年化收益率", width: 100, align: "right",
                formatter: function (v, r, i) {
                    return formatPercent(v);
                }
            },
            { field: "productName", title: "项目类型", width: 150, align: "center" },
            {
                field: "loanType", title: "标种", width: 80, align: "center", formatter: function (value, rowData, index) {
                    switch (value) {
                        case 0:
                            return "正常标";
                        case 1:
                            return "转让标";
                        case 2:
                            return "收购标";
                        default:
                            return "-";
                    }
                }
            },
            {
                field: "lendMoney", title: "放款金额", width: 100, align: "right",
                formatter: function (v, r, i) {
                    return formatMoney(v, '￥');
                }
            },
            {
                field: "collectFee", title: "已收手续费", width: 100, align: "right",
                formatter: function (v, r, i) {
                    return formatMoney(v, '￥');
                }
            },
            {
                field: "collectGuarFee", title: "已收担保费", width: 100, align: "right",
                formatter: function (v, r, i) {
                    return formatMoney(v, '￥');
                }
            },
            {
                field: "lendDate", title: "放款日期", width: 100, align: "center"
            }
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#TransferFundsStatisticsLookUpGrid').datagrid('getPager');
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
    Searchs: function () {
        var po = { tdate: $("#tdate").val() };
        var param= createSearchParam();
        $.post("/reportAnalysis/json/TransferFundsStatisticsLookUpView.json?model=search&" + getParam(po), param, function (data) {
        	$('#TransferFundsStatisticsLookUpGrid').datagrid('loadData', {total : 0,rows : []});
        	$("#TransferFundsStatisticsLookUpGrid").datagrid("loadData", data);
        }, "json");
    },
    ExportExcel: function(){
    	$("#SearchItems").submit();
    }
};


function createSearchParam() {
//	alert($("#SearchItems #len_lend_date_from").datebox("getValue"));
//	alert($("#SearchItems #len_lend_date_to").datebox("getValue"));
    var form = {
		tdate: $("#tdate").val(),
        loanNo : $("#pro_loan_no").val(),
        loanCategory : $("#pro_product_type").val(),
        loanType : $("#len_loan_type").val(),
//        lendDateFrom : $("#SearchItems #len_lend_date_from").datebox("getValue"),
//        lendDateTo : $("#SearchItems #len_lend_date_to").datebox("getValue")
    };
    return getParam(form);
}
MyAction.Init(); 
$("#search").click(MyAction.Searchs);
$("#exportExcel").click(MyAction.ExportExcel);

