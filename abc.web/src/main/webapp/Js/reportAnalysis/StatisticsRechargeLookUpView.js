/*------------------------------------------------
 * Author:潘健  Date：2014-9-01 
-------------------------------------------------*/
var MyAction = {
    Init: function () {
        $("#StatisticsRechargeLookUpGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PStatisticsRechargeLookUpView.json",
            width:800,
            height: 300,
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
            { field: "recharge", title: "充值人", width: 150, align: "center" },
            { field: "recharge_amount", title: "充值金额", width: 150, align: "right" },
            { field: "recharge_time", title: "充值时间", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        });
    }

}

MyAction.Init();

