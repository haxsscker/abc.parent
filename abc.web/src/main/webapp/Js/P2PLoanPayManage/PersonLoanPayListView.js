/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-21 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Repayment").click(MyAction.Repayment);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#SmallLoanRepaymentListGrid").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PSmallLoanRepaymentList.json",
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
            { field: "loan_no", title: "项目名称", width: 150, align: "center" },
            { field: "loan_type", title: "项目类型", width: 150, align: "center" },
            { field: "loan_emp", title: "借款人", width: 150, align: "center" },
            { field: "guar_gov", title: "担保机构", width: 150, align: "center" },
            { field: "loan_money", title: "借款金额", width: 150, align: "right" },
            {
                field: "repayment_date", title: "应还日期", width: 150, align: "center", formatter: function (v, r, i) {
                    return convertToDate(v, "yyyy-MM-dd");
                }
            },
            { field: "loan_limit", title: "借款期限", width: 150, align: "center" },
            { field: "overdue_days", title: "逾期天数", width: 150, align: "center" },
            { field: "repayment_type", title: "还款方式", width: 150, align: "center" },
            { field: "repayment_shouldprincipal", title: "本期应还本金", width: 150, align: "right" },
            { field: "repayment_shouldinterest", title: "本期应还利息", width: 150, align: "right" },
            { field: "repayment_alreadyprincipal", title: "本期已还本金", width: 150, align: "right" },
            { field: "repayment_alreadyinterest", title: "本期已还利息", width: 150, align: "right" },
            { field: "repayment_stilloweprincipal", title: "本期尚欠本金", width: 150, align: "right" },
            { field: "repayment_stilloweinterest", title: "本期尚欠利息", width: 150, align: "right" },
            { field: "repayment_state", title: "状态", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#SmallLoanRepaymentListGrid').datagrid('getPager');
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
    //审核
    Repayment: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/P2PRepaymentManage/RepaymentView",
            iconCls: 'icon-add',
            title: "贷款到期",
            maximizable: true,//显示最大化
            width: 750,
            height: $(window).height() - 50,
            buttons: [{
                text: '还款',
                iconCls: 'icon-user_magnify',
                handler: function () {
                    Dialog.dialog("close");
                }
            },
            {
                text: '取消返回',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        })
    },
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/P2PLoanPayManage/LoanPayLookUpView",
            iconCls: 'icon-add',
            title: "贷款到期",
            width: 750,
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
    Send: function () {
        alert("已发送");
    },
    Recall: function () {
        alert("已撤回");
    },

    CustManagerDetail: function (CustId) {

        var url = "/DBCustManage/CustManagerView?CustId=" + CustId;
        var name = "客户经理信息";
        var para = 'height=800,width=1000,resizable=yes,scrollbars=yes'
        window.open(url, name, para);

    }

}