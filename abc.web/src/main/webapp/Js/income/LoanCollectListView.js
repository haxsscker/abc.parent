/*------------------------------------------------
 * Author:吕小东  Date：2014年10月29日10:42:00
-------------------------------------------------*/


var MyAction1 = {
    Init: function () {
        $("#CollectGrid").datagrid({
            url: "/income/json/LoanCollectList.json?planId=" + planId,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            {
                field: "cst_real_name", title: "投资人用户名", width: 100, align: "center"
            },
            {
                field: "user_real_name", title: "投资人姓名", width: 100, align: "center"
            },
            {
                field: "user_phone", title: "投资人手机号", width: 100, align: "center"
            },
            {
                field: "pro_invest_money", title: "投资金额", width: 100, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, "￥");
                }
            },
            {
                field: "pro_invest_date", title: "投资日期", width: 100, align: "center"
            },
            {
                field: "pro_pay_money", title: "本期应收本金", width: 120, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, "￥");
                }
            },
            {
                field: "pro_pay_rate", title: "本期应收利息", width: 120, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, "￥");
                }
            },
            {
                field: "pro_pay_over_rate", title: "本期应收罚息", width: 120, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, "￥");
                }
            },
            {
                field: "pro_collect_money", title: "本期实收本金", width: 120, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, "￥");
                }
            },
            {
                field: "pro_collect_rate", title: "本期实收利息", width: 120, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, "￥");
                }
            },
            {
                field: "pro_collect_over_rate", title: "本期实收罚息", width: 120, align: "right", formatter: function (value, rowData, index) {
                    return formatMoney(value, "￥");
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#CollectGrid').datagrid('getPager');
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
    }
}

MyAction1.Init();


