/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-22
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#GuarMatch").click(MyAction.GuarMatch);
    $("#Export").click(MyAction.Export);
    $("#Return").click(MyAction.Return);
    $("#Send").click(MyAction.Send);
    $("#Recall").click(MyAction.Recall);
    $("#AuditSche").click(MyAction.AuditSche);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#LoanMatchingGrid").datagrid({
            method:"GET",
            url: "/Demo/Json/P2PLoanMatchingList.json",
            height: $(window).height() - 52,
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

            { field: "loan_no", title: "项目名称", width: 150, align: "center" },
            { field: "loan_type", title: "项目类型", width: 80, align: "center" },
            { field: "loan_emp", title: "借款人", width: 100, align: "center" },
            { field: "loan_money", title: "借款金额", width: 100, align: "right" },
            { field: "loan_rate", title: "年化收益率", width: 80, align: "right" },
            { field: "loan_period", title: "借款期限", width: 70, align: "center" },
            { field: "apply_date", title: "申请日期", width: 100, align: "center" },
            { field: "apply_endDate", title: "招标到期日", width: 100, align: "center" },
            { field: "loan_use", title: "借款用途", width: 70, align: "center" },
            { field: "pay_type", title: "还款方式", width: 80, align: "center" },
            { field: "guar_gov", title: "担保机构", width: 150, align: "center" },
            { field: "match_state", title: "匹配状态", width: 80, align: "center" }

            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#LoanMatchingGrid').datagrid('getPager');
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
    //担保匹配
    GuarMatch: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/loan/MatchOrgAddView",
            iconCls: 'icon-add',
            title: "项目匹配",
            maximizable: true,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '确认匹配',
                iconCls: 'icon-user_magnify',
                handler: function () {
                    Dialog.dialog("close");
                }
            },{
                   text: '取消返回',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })
    },
    //查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        //var o = {
        //    gscId: row.gsc_Id,
        //    gteId: row.gte_Id
        //};
        var Dialog = $.hDialog({
            href: "/loan/MatchOrgLookUpView",
            iconCls: 'icon-add',
            title: "信用额度审批详情",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            //onLoad: function () {
            //    MyAction.GetTemplate(row.gsc_Id, row.gte_Id);
            //},
            buttons: [{
                text: '取消返回',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })
    },
    //审核进度
    AuditSche: function () {
        
    },
    Send: function () {
        alert("已发送");
    },
    Recall: function () {
        alert("已撤回");
    },
    Return: function () {
        alert("已退回");
    }
}
