/*------------------------------------------------
 * Author:李冬冬  Date：2014-8-19 
-------------------------------------------------*/

$(function () {
    var o = { loanId: loanId };
    $("#LoanPayPlanGrid").datagrid({
        url: "/loanpay/json/actionLoanPayPlanView.json?" + getParam(o),
        pageSize: 10,
        fitColumns: true,
        rownumbers: true,
        nowrap: false,
        striped: true,
        remoteSort: true,
        view: myview,//重写当没有数据时
        emptyMsg: '没有找到数据',//返回数据字符
        columns: [[
        {
            field: "pro_pay_date", title: "应还日期", width: 150, align: "center"
        },
        {
            field: "pro_pay_money", title: "应还本金", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
        {
            field: "pro_pay_rate", title: "应还利息", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
        //edit by 夏同同 新增违约罚金 20160412
        {
            field: "deInterest", title: "应还逾期罚息", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
        {
            field: "deBreachInterest", title: "应还违约罚金", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
        {
            field: "pro_pay_serve_fee", title: "应还平台服务费", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
        //end edit
        {
            field: "pro_collect_date", title: "实还日期", width: 150, align: "center"
        },
        {
            field: "pro_collect_money", title: "已还本金", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
        {
            field: "pro_collect_rate", title: "已还利息", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
        //edit by 夏同同 新增违约罚金 20160412
        {
            field: "pro_collect_over_rate", title: "已还逾期罚息", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
        {
            field: "pro_collect_breach_rate", title: "已还违约罚金", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
        {
            field: "pro_collect_serve_fee", title: "已还平台服务费", width: 150, align: "right",
            formatter: function (v, r, i) {
                return v == null ? "-" : formatMoney(v, '￥');
            }
        },
        // end edit
        {
            field: "pro_pay_type", title: "是否结清", width: 150, align: "center"
        }
        //{
        //    field: "pro_pay_type", title: "备注", width: 150, align: "center",
        //    formatter: function (v, r, i) {
        //        return v == null ? "-" : GetPayStatus(v);
        //    }
        //}
        ]],
        pagination: true,
        singleSelect: true,
        onLoadSuccess: function (data) {
            if (data.total > 0) return;
            $('#LoanPayPlanGrid').datagrid('resize', { height: 100 });
        }
    });
    var p = $('#LoanPayPlanGrid').datagrid('getPager');
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
});


