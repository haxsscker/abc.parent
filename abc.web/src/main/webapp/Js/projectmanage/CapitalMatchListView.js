/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-20
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#MatchingFunds").click(MyAction.MatchingFunds);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#CapitalMatchingGrid").datagrid({
            method:"GET",
            url: "/Demo/Json/P2PCapitalMatchingList.json",
            height: $(window).height() - 52,
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
            { field: "investor", title: "投资人", width: 150, align: "center" },
            { field: "pro_type", title: "项目类型", width: 150, align: "center" },
            { field: "invest_money", title: "投资金额", width: 150, align: "right" },
            {
                field: "invest_date", title: "投资日期", width: 150, align: "center", formatter: function (v, r, i) {
                    return convertToDate(v, "yyyy-MM-dd");
                }
            },
            { field: "matching_money", title: "匹配金额", width: 150, align: "right" },
            {
                field: "matching_date", title: "匹配日期", width: 150, align: "center", formatter: function (v, r, i) {
                    return convertToDate(v, "yyyy-MM-dd");
                }
            },
            { field: "state", title: "匹配状态", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#CapitalMatchingGrid').datagrid('getPager');
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
    //资金匹配
    MatchingFunds: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/P2PProjectManage/MatchFundsListView",
            iconCls: 'icon-add',
            title: "资金匹配",
            maximizable: true,//显示最大化
            width: 750,
            height: $(window).height() - 50,
            buttons: [{
                text: '同意',
                iconCls: 'icon-user_magnify',
                handler: function () {
                    Dialog.dialog("close");
                }
            },{
                   text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })
    },
    Send: function () {
        alert("已发送");
    },
    Recall: function () {
        alert("已撤回");
    }
}