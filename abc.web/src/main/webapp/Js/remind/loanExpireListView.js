$(function() {
    MyGrid.Resize();
    $("#LookUp").click(MyActionForLoanList.LookUp);
    $("#CheckIdear").click(MyActionForLoanList.CheckIdear);
    $("#btnSearch").click(MyActionForLoanList.Search); // 查询
    $("#sendMail").click(MyActionForLoanList.sendMail);
    $("#sendMessage").click(MyActionForLoanList.sendMessage);
    MyActionForLoanList.Init();
    $(window).resize(function() {
        MyGrid.RefreshPanl();
    });
});

var MyActionForLoanList = {
    Init: function() {
        $("#LoanGrid").datagrid({
            method: "Post",
            url: "/remind/json/loanExpireListView.json?isFromIntent=false",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            idField: "loanId",
            view: myview,
            // 重写当没有数据时
            emptyMsg: '没有找到数据',
            // 返回数据字符
            columns: [[{
                field: "loanNo",
                title: "项目名称",
                width: 150,
                align: "center"
            },
            {
                field: "loanCategory",
                title: "项目类型",
                width: 150,
                align: "center",
                formatter: function(value, rowData, index) {
                    return value;
                }
            },
            {
                field: "loaneeName",
                title: "借款人",
                width: 100,
                align: "center",
                formatter: function(value, rowData, index) {
                    if (value == null) {
                        return "-";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: "loanUserPhone",
                title: "借款人电话",
                width: 100,
                align: "center",
                formatter: function(value, rowData, index) {
                    if (value == null) {
                        return "-";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: "loanMoney",
                title: "借款金额",
                width: 100,
                align: "right",
                formatter: function(value, rowData, index) {
                    return formatMoney(value, '￥');
                }
            },
            {
                field: "loanRate",
                title: "年化收益率",
                width: 80,
                align: "right",
                formatter: function(value, rowData, index) {
                    if (value != null) {
                        return value + "%";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: "loanPeriod",
                title: "借款期限",
                width: 100,
                align: "center",
                formatter: function(value, rowData, index) {
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
                formatter: function(value, rowData, index) {
                    if (value == "" || value == null) {
                        return "-";
                    } else {
                        return value;
                    };
                }
            },
            {
                field: "loanExpireDate",
                title: "到期日",
                width: 100,
                align: "center",
                formatter: function(value, rowData, index) {
                    if (value == "" || value == null) {
                        return "-";
                    } else return value;
                }
            },
            
            {
                field: "loanUse",
                title: "借款用途",
                width: 200,
                align: "center",
                formatter: function(value, rowData, index) {
                    if (value != null) {
                        return CutString(value, 10);
                    } else {
                        return "-";
                    }
                }
            },
            {
                field: "loanSpeed",
                title: "项目进度",
                width: 80,
                align: "center",
                formatter: function(value, rowData, index) {
                    if (value != null) {
                        return formatPercento(value, "%");
                    } else if (parseInt(rowData.pro_loan_state) >= 8) {
                        return "100%";
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
                formatter: function(value, rowData, index) {
                    return value;
                }
            },
            {
                field: "loanCreatetime",
                title: "申请日期",
                width: 100,
                align: "center",
                formatter: function(value, rowData, index) {
                    if (value == "" || value == null) {
                        return "-";
                    } else return value;
                }
            },
            {
                field: "loanInvestEndtime",
                title: "招标到期日",
                width: 100,
                align: "center",
                formatter: function(value, rowData, index) {
                    if (value == "" || value == null) {
                        return "-";
                    } else return value;
                }
            },
             
            {
                field: "loanInvestStarttime",
                title: "开始日期",
                width: 100,
                align: "center",
                formatter: function(value, rowData, index) {
                    if (value == "" || value == null) {
                        return "-";
                    } else return value;
                }
            },
            // {
            // field: "loanEndtime", title: "到期日期", width: 100, align: "center",
            // formatter: function (value, rowData, index) {
            // if (value == "" || value == null) {
            // return "-";
            // } else return convertToDate(value, "yyyy-MM-dd");
            // }
            // },
            {
                field: "govName",
                title: "担保机构",
                width: 150,
                align: "center",
                formatter: function(value, rowData, index) {
                    if (value != null) {
                        return value;
                    } else {
                        return "-";
                    }
                }
            },
            {
                field: "loanState",
                title: "项目状态",
                width: 80,
                align: "center",
                formatter: function(value, rowData, index) {
                    if (value == "" || value == null) {
                        return "-";
                    } else {
                        return value;
                    }
                }
            }]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#SchNormalGrid').datagrid('getPager');
        alert();
        $(p).pagination({
            pageSize: 10,
            pageList: [5, 10, 15, 20, 30, 50, 100],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            onBeforeRefresh: function() {
                $(this).pagination('loading');
                $(this).pagination('loaded');
            }
        });
    },
    // 查看
    LookUp: function() {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }

        var dialogs = $.hDialog({
            href: "/projectmanage/loanProjectLookUpView?loanId=" + row.loanId + "&loanFileUrl=" + row.loanFileUrl,
            iconCls: 'icon-zoom',
            title: "融资查看",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function() {
                    dialogs.dialog("close");
                }
            }]
        });
    },
    
    // 审核意见
    CheckIdear: function() {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }

        var o = {
            applyId: row.loanId,
            reviewType: 17  // 融资审核
        };
        var Dialog = $.hDialog({
            href: "/review/auditOpinionListView?" + getParam(o),
            iconCls: 'icon-add',
            title: "审核意见",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        });
    },
    //邮件
    sendMail: function() {
        var row = MyGrid.selectRow();
        if (row) {
                $.messager.confirm("发送邮件", "确认要发送邮件提醒吗？", function (r) {
                	if(r){
                		$.AjaxColynText('/remind/json/loanExpireSendMail.json', { empId: row.loanUserId,loanExpireDate: row.loanExpireDate,loanNo:row.loanNo}, function (data) {
                        	data = eval('(' + data + ')');
                            if (data.success == true) {	
                            	Colyn.log("发送成功");
                                jQuery('#AdminMaintainListGrid').datagrid('clearSelections');
                            }
                            else {
                                Colyn.log(data.message);
                            }
                        });
                	}                       
                })
        }
        else {
            Colyn.log("请选择项目发送邮件！");
        }
    },
    // 发送信息
    sendMessage: function() {
    	var row = MyGrid.selectRow();
    	if(!row){
    		Colyn.log("请选择项目发送短信！");
    		return;
    	}
        var o = {
    			userPhone: row.loanUserPhone,
    			loanNo: row.loanNo,
                loanId: row.loanId,
                userName: row.loaneeName,
                loanExpireDate: row.loanExpireDate
            };
        $.messager.confirm("发送短信", "确认要发送信息提醒吗？", function (r) {
        	if(r){
        		   $.post("/remind/json/loanSendMessage.json?" + getParam(o), "",
        		            function(data) {
        		            	if (data.success) {
        		                    Colyn.log(data.message);
        		                    MyAction.Init();
        		                    dss.dialog('close');
        		                } else {
        		                    Colyn.log(data.message);
        		                }
        		            },
        		            "json");
        	}        
        });
    }
};

$("#pro_card_type").live("change",
function() {
    var p1 = $(this).children('option:selected').val();
    var cardA = $("#cardA"); // 证件号码
    cardA.val("");
    // 请选择时： 证件号码不能使用 并清空值
    if (p1 == "") {
        cardA.validatebox({
            required: false,
            validType: ['CheckQuote']
        });
        cardA.attr("disabled", "disabled");
    } else if (p1 == "身份证") {
        cardA.removeAttr("disabled");
        cardA.validatebox({
            required: true,
            validType: ['IdCard']
        });
    } else {
        cardA.removeAttr("disabled");
        cardA.validatebox({
            required: true,
            validType: ['CheckQuote']
        });
    }
});

function SetSelectValue() {
    var custtype = $("#pro_cust_type");
    var typeval = custtype.attr("_select");
    custtype.val(typeval);
    var industry = $("#pro_cust_industry");
    var industryval = industry.attr("_select");
    industry.val(industryval);
    var scale = $("#pro_cust_scale");
    scale.val(scale.attr("_select"));
    var cardtype = $("#pro_card_type");
    cardtype.val(cardtype.attr("_select"));
    var pro_card_type = $("#pro_card_type");
    pro_card_type.val(pro_card_type.attr("_select"));
    var pro_is_marry = $("#pro_is_marry");
    pro_is_marry.val(pro_is_marry.attr("_select"));
    var pro_work_type = $("#pro_work_type");
    pro_work_type.val(pro_work_type.attr("_select"));
    var pro_work_year = $("#pro_work_year");
    pro_work_year.val(pro_work_year.attr("_select"));
    var pro_house_mortgage = $("#pro_house_mortgage");
    pro_house_mortgage.val(pro_house_mortgage.attr("_select"));

    // 绑定证件号
    var p1 = $("#pro_card_type").children('option:selected').val();
    var cardA = $("#cardA"); // 证件号码
    // 请选择时： 证件号码不能使用 并清空值
    if (p1 == "") {
        cardA.attr("disabled", "disabled");
        cardA.validatebox({
            required: false,
            validType: ['CheckQuote']
        });
    } else if (p1 == "身份证") {
        cardA.removeAttr("disabled");
        cardA.validatebox({
            required: true,
            validType: ['IdCard']
        });
    } else {
        cardA.removeAttr("disabled");
        cardA.validatebox({
            required: true,
            validType: ['CheckQuote']
        });
    }
}


