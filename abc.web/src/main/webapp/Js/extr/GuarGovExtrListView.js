/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-19 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#Export").click(MyAction.Export);
    $("#search").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#GuarGovRecordListGrid").datagrid({
            method: "GET",
            url: "/extr/json/ActionGuarGovExtrListView.json",
            height: $(window).height() - 80,
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
            { field: "cust_name", title: "机构账号", width: 150, align: "center" },
            { field: "cust_fullname", title: "机构名称", width: 150, align: "center" },
            { field: "extcash_numberofbank", title: "提现账号", width: 150, align: "right" },
            { field: "extcash_bank", title: "提现银行", width: 150, align: "center" },
            { field: "extcash_money", title: "提现金额", width: 150, align: "right" },
            {
                field: "extcash_date", title: "提现时间", width: 150, align: "center"
            },
            { field: "extcash_state", title: "状态", width: 150, align: "center" ,formatter: function(value, rowData, index) {
                if (value == "0") {
                    return "提现中";
                } else
                if (value == "1"){
                    return "提现成功";
                }else if(value == "2")
                {
                	 return "提现失败";
                }
            }}
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#GuarGovRecordListGrid').datagrid('getPager');
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
    //导出excel
    Export: function () {
        
    },
    Send: function () {
        alert("已发送");
    },
    Recall: function () {
        alert("已撤回");
    },
    Search: function () {
        var param = createParam3("SearchForm");
        $.post("/extr/json/ActionGuarGovExtrListView.json", param, function (data) {
             $("#GuarGovRecordListGrid").datagrid("loadData", data)
        }, 'json');
    }
}