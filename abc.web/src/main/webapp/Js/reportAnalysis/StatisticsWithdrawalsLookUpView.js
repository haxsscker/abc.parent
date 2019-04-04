/*------------------------------------------------
 * Author:潘健  Date：2014-9-01 
-------------------------------------------------*/
var MyAction = {
    Init: function () {
        $("#StatisticsWithdrawalsLookUpGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PStatisticsWithdrawalsLookUpView.json",
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
            { field: "carry_people", title: "提现人", width: 150, align: "center" },
            { field: "withdrawal", title: "提现金额", width: 150, align: "right" },
            { field: "fee", title: "提现手续费", width: 150, align: "right" },
            { field: "time", title: "提现时间", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        });
    }

}

MyAction.Init();

