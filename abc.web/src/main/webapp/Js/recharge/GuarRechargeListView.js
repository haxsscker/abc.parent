/*------------------------------------------------
 * Author:潘健  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#Add").click(MyAction.Add);
    $("#search").click(MyAction.Search);
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
        $("#GuaranteeAgenciesGrid").datagrid({
            method: "GET",
            url: "/recharge/json/ActionGuarRechargeListView.json",
            height: $(window).height() - 95,

            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "cust_name", title: "机构账号", width: 250, align: "center" },
            { field: "cust_fullname", title: "机构名称", width: 150, align: "center" },
            { field: "extcash_numberofbank", title: "充值账号", width: 250, align: "center" },
            { field: "extcash_bank", title: "充值银行", width: 150, align: "center" },
            { field: "extcash_money", title: "充值金额", width: 100, align: "right" },
            { field: "extcash_date", title: "充值时间", width: 250, align: "center" },
            { field: "extcash_state", title: "状态", width: 100, align: "center",formatter: function(value, rowData, index) {
                if (value == "0") {
                    return "充值中";
                } else
                if (value == "1"){
                    return "充值成功";
                }else if(value == "2")
                {
                	 return "充值失败";
                }
            } }
            ]],
            pagination: true,
            singleSelect: true
        })

    },   
    Search: function () {
        var param = createParam3("SearchForm");
        $.post("/recharge/json/ActionGuarRechargeListView.json", param, function (data) {
             $("#GuaranteeAgenciesGrid").datagrid("loadData", data)
        }, 'json');
    }
}