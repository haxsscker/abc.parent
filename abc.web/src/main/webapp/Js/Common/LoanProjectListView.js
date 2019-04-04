$(function () {
    $("#ProjectListGrid").datagrid({
    	method: "post",
        url: "/projectmanage/json/LoanProjectListView.json?loaneeId=" + loaneeId,
        height: $(window).height() - 52,
        pageSize: 10,
        fitColumns: false,
        rownumbers: true,
        nowrap: false,
        striped: true,
        idField: "loanId",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
        remoteSort: true,
        view: myview, //重写当没有数据时
        emptyMsg: '没有找到数据', //返回数据字符
        columns: [
            [
                { field: "loanNo", title: "项目名称", width: 150, align: "center" },
                { 
                	field: "loanCategory", 
                	title: "项目类型", 
                	width: 80, 
                	align: "center", 
                	formatter: function (value, rowData, index) {
                		return value
                	}
                },
                { field: "loaneeName", title: "借款人", width: 100, align: "center" },
                {
                    field: "loanMoney",
                    title: "借款金额",
                    width: 100,
                    align: "right",
                    formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }
                },
                {
                    field: "loanRate",
                    title: "年化收益率",
                    width: 80,
                    align: "right",
                    formatter: function (value, rowData, index) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return formatPercent(value);
                        };
                        
                    }
                },
                {
                    field: "loanPeriod",
                    title: "借款期限",
                    width: 80,
                    align: "center",
                    formatter: function (value, rowData, index) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        };
                    }
                },
                {
                    field: "loanPeriodUnit",
                    title: "期限单位",
                    width: 80,
                    align: "center",
                    formatter: function (value, rowData, index) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        };
                    }
                },
                {
                    field: "loanCreatetime", title: "申请日期", width: 100, align: "center", formatter: function (value, rowData, index) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        }; 
                    }
                },
                {
                    field: "loanInvestEndtime", title: "招标到期日", width: 100, align: "center", formatter: function (value, rowData, index) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        };
                    }
                },
                {
                    field: "loanUse",
                    title: "借款用途",
                    width: 100,
                    align: "center",
                    formatter: function (value, rowData, index) {
                        if (value != null) {
                            return CutString(value, 10);
                        } else {
                            return "-";
                        }
                    }
                },
                {
                    field: "loanPayType",
                    title: "还款方式",
                    width: 100,
                    align: "center",
                    formatter: function (value, rowData, index) {
                        return value;
                    }
                },
                {
                    field: "govName",
                    title: "担保机构",
                    width: 150,
                    align: "center",
                    formatter: function (value, rowData, index) {
                        if (value != null) {
                            return value;
                        } else {
                            return "-";
                        }
                    }
                },
                {
                    field: "loanState",
                    title: "发布状态",
                    width: 100,
                    align: "center",
                    formatter: function (value, rowData, index) {
                        return value;
                    }
                }
            ]
        ],
        pagination: true,
        singleSelect: true
    });
    var p = $('#ProjectReleaseGrid').datagrid('getPager');
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