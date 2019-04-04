/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Export").click(MyAction.Export);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#ProjectScheduleGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PProjectScheduleList.json",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
             { field: "loan_no", title: "项目名称", width: 150, align: "center" },
             { field: "loan_type", title: "项目类型", width: 80, align: "center" },
             { field: "loan_emp", title: "借款人", width: 100, align: "center" },
             { field: "loan_money", title: "借款金额", width: 100, align: "right" },
             { field: "loan_rate", title: "年化收益率", width: 80, align: "right" },
             { field: "loan_limit", title: "借款期限", width: 80, align: "center" },
             { field: "loan_yhqs", title: "已还期数", width: 70, align: "center" },
             { field: "loan_redeemman", title: "赎回人", width: 80, align: "center" },
             { field: "loan_redeemmoney", title: "赎回金额", width: 100, align: "right" },
             { field: "loan_counterfee", title: "手续费", width: 100, align: "right" },
             { field: "loan_redeemdate", title: "申请日期", width: 100, align: "center" },
             { field: "loan_period", title: "项目进度", width: 70, align: "right" },
             { field: "loan_endDate", title: "招标到期日", width: 100, align: "center" },
             { field: "loan_redeemcheckstate", title: "赎回状态", width: 70, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#ProjectScheduleGrid').datagrid('getPager');
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
    //查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/P2PProjectManage/ProjectSpeedLookUpView",
            iconCls: 'icon-add',
            title: "赎回跟踪",
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
    Export: function () {
    },
    Recall: function () {
        alert("已撤回");
    }
}