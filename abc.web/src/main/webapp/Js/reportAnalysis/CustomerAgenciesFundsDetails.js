$(function() {
  
    MyAction.Init();
    $("#Export").click(MyAction.Export);
   
});


var MyAction = {
    Init: function () {
        $("#CustomerAgenciesFundsDetailsGrid").datagrid({
            method: "GET",
            url: "/reportAnalysis/json/GuaranteeAgenciesFundsDetails.json?userId="+$("#userId").val()+"&accountNo="+$("#accountNo").val(),
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "drOperateDateStr", title: "交易日期", width: 150, align: "center" },
            { field: "drMoneyAmountStr", title: "交易金额(￥)", width: 150, align: "center" },
            { field: "drInnerSeqNo", title: "交易订单号", width: 180, align: "center" },
            { field: "drCustomerAccount", title: "交易对方", width: 140, align: "center" },
            { field: "drTypeStr", title: "交易类型", width: 140, align: "center" },
            { field: "drStateStr", title: "状态", width: 130, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#CustomerAgenciesFundsDetailsGrid').datagrid('getPager');
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
    Export: function(){
    	$("#Colyn").submit();
    }
}



