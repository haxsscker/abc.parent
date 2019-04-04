/*------------------------------------------------
 * Author:潘健  Date：2014-9-1 
-------------------------------------------------*/
$(function () {
    $("#Search").click(MyAction.Search);
    MyGrid.Resize();
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#TransferFundsStatisticsGrid").datagrid({
           // method: "GET",
            url: "/reportAnalysis/json/TransferFundsStatisticsView.json",
            height: $(window).height() - 50,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            {
                     field: "years", title: "年份", width: 150, align: "center"
            },
            {
                field: "jan", title: "一月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-01-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            },
            {
                field: "feb", title: "二月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-02-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            },
            {
                field: "mar", title: "三月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-03-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            },
            {
                field: "apr", title: "四月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-04-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            },
            {
                field: "may", title: "五月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-05-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            },
            {
                field: "jun", title: "六月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-06-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            },
            {
                field: "jul", title: "七月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-07-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            },
            {
                field: "aug", title: "八月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-08-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            },
            {
                field: "sept", title: "九月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-09-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            },
            {
                field: "oct", title: "十月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-10-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            },
            {
                field: "nov", title: "十一月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-11-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            },
            {
                field: "dec", title: "十二月份", width: 150, align: "right", formatter: function (value, row, rowIndex) {
                    var tdate = row.years + "-12-01";
                    return "<a href='#' onclick =\"LookUpDetails('" + tdate + "')\">" + formatMoney(value, '￥') + "</a>";
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        });
    },
    //搜索
    Search: function () {
        var param = createSearchParam();
        $.post("/reportAnalysis/json/TransferFundsStatisticsView.json", param, function (data) {
            $('#TransferFundsStatisticsGrid').datagrid('loadData', { total: 0, rows: [] });
            $("#TransferFundsStatisticsGrid").datagrid("loadData", data)
        }, 'json');
    }
}
//查看
function LookUpDetails(thisdate) {
    var o = { tdate: thisdate };
    var Dialog = $.hDialog({
        href: "/reportAnalysis/TransferFundsStatisticsLookUpView?" + getParam(o),
        iconCls: 'icon-add',
        title: "资金划转详情查看",
        width: $(window).width() - 40,
        height: $(window).height() - 50,
        maximizable: false,
        buttons: [{
            text: '关闭',
            iconCls: 'icon-cancel',
            handler: function () {
                Dialog.dialog("close");
                $('#StatisticsRechargeGrid').datagrid('reload');

            }
        }]
    })
}
function createSearchParam() {
    var form = {
            year : $("#year").val()
        };
        return getParam(form);
    }
