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
        $("#ReturnStatisticGrid").datagrid({
            method: "GET",
            url: "/statistic/json/returnStatisticView.json?"+createParam3("SearchForm"),
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
            { field: "returns_object", title: "退费对象", width: 150, align: "center" },
            { field: "refund_account", title: "退费账号", width: 150, align: "center" },
            { field: "phone_number", title: "手机号码", width: 150, align: "center" },
            { field: "return_costs", title: "退回费用", width: 150, align: "right" },
            { field: "return_reasons", title: "退回原因", width: 150, align: "center" },
            { field: "refund_state", title: "退费状态", width: 150, align: "center" , formatter: function (value, row, index) {
                if (value == '0') {
                    return "退费中";
                } else if (value == '1')  {
                    return "成功";
                }else if(value == '2'){
                	return "失败";
                }
            }}
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#ReturnStatisticGrid').datagrid('getPager');
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
        $.post("/statistic/json/ReturnStatisticView.json", param, function (data) {
             $("#ReturnStatisticGrid").datagrid("loadData", data)
        }, 'json');
    }
}