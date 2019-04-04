$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Audit").click(MyAction.Audit);
    $("#CheckIdear").click(MyAction.CheckIdear);
    $("#Recall").click(MyAction.Recall);
    $("#Return").click(MyAction.Return);
    $("#btnSearch").click(MyAction.Search);
    $("#SendToCheck").click(MyAction.SendToCheck);//发送到平台审核
    $("#GuarCheck").click(MyAction.GuarCheck);//发送到担保审核
    $("#MatainAdd").click(MyAction.MatainAdd);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var getData = function (page, rows) {
    $.ajax({
        type: "POST",
        url: "/review/json/LoanGuarGovListSearch.json?" + createSearchParam(),
        data: "page=" + page + "&rows=" + rows,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
            $.messager.progress('close');
        },
        success: function (data) {
            $('#LoanGovCheckGrid').datagrid('loadData', data);
        }
    });
};

var MyAction = {
    Init: function () {
        $("#LoanGovCheckGrid").datagrid({
            //method: "GET",
            url: "/review/json/LoanGuarGovListSearch.json?" + createSearchParam(),
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "pro_loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
                { field: "pro_loan_no", title: "编号", width: 150, align: "center" },
                { field: "pdo_product_name", title: "项目类型", width: 100, align: "center" },
                { field: "pro_user_name", title: "借款人", width: 80, align: "center" },
                {
                    field: "pro_loan_money", title: "借款金额", width: 110, align: "right", formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }
                },
                {
                    field: "pro_loan_rate", title: "年化收益率", width: 80, align: "right", formatter: function (value, rowData, index) {
                        return formatPercent(value);
                    }
                },
                { field: "pro_loan_period", title: "借款期限", width: 80, align: "center" },
                { field: "pro_add_date", title: "申请日期", width: 80, align: "center" },
                { field: "pro_invest_endDate", title: "招标到期日", width: 80, align: "center" },
                { field: "pro_loan_use", title: "借款用途", width: 80, align: "center" },
                { field: "pro_pay_type", title: "还款方式", width: 80, align: "center" },
                { field: "pro_check_state", title: "审核状态", width: 80, align: "center"}
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#LoanGovCheckGrid').datagrid('getPager');
        $(p).pagination({
            onSelectPage: function (pageNumber, pageSize) {
                getData(pageNumber, pageSize);
            },
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
    Audit: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (!row.can_check) {
            Colyn.log("该项目不可审核！");
            return;
        }

        var o = { loanId: row.pro_loan_id };
        var Dialog = $.hDialog({
            href: "/review/LoanGuarGovCheckView?" + getParam(o),
            iconCls: 'icon-add',
            title: "信贷审核",
            maximizable: true,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [
            {
                text: '同意',
                iconCls: 'icon-user_magnify',
                handler: function () {
                	if($("#Colyn").form('validate')){
	                    var msg = $("#checkIdear").val();
	                    if (msg == "" || msg == null || msg == "请输入...") {
	                        Colyn.log("请输入审核意见！");
	                        return;
	                    }
	
	                    var param = {
	                        applyId: row.pro_check_id,
	                        reviewType: row.pro_check_type,
	                        opType: 1, // 通过
	                        message: msg
	                    };
	                    ToAudit(param, "审核");
	                    Dialog.dialog("close");
	                }
                }
            },
            {
               text: '否决',
               iconCls: 'icon-user_magnify',
               handler: function () {
            	   if($("#Colyn").form('validate')){
	                   var msg = $("#checkIdear").val();
	                   if (msg == "" || msg == null || msg == "请输入...") {
	                       Colyn.log("请输入审核意见！");
	                       return;
	                   }
	
	                   var param = {
	                       applyId: row.pro_check_id,
	                       reviewType: row.pro_check_type,
	                       opType: 2, // 否决
	                       message: msg
	                   };
	                   ToAudit(param, "审核");
	                   Dialog.dialog("close");
	            	   }
            	  }
            },
            {
               text: '关闭',
               iconCls: 'icon-cancel',
               handler: function () {
                   Dialog.dialog("close");
               }
            }
            ]
        });
    },
    //查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = { loanId: row.pro_loan_id };
        var Dialog = $.hDialog({
            href: "/review/LoanInfoLookUpView?" + getParam(o),
            iconCls: 'icon-add',
            title: "借款信息查看",
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
    //发送平台审核
    SendToCheck: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        // 发送状态为发送到小贷时才可以发送到平台初审
        if (row.pro_send_gov == 1) {
            // 只有通过审核后才能发送到平台初审
            if (!row.can_check) {
                var param= {
                    govType: 2, // 机构类型 2 代表平台
                    govId: 2,   // 机构ID  2 代表平台
                    applyId: row.pro_check_id,
                    reviewType: row.pro_check_type
                };
                ToTransfer(param);
            } else {
                Colyn.log("未审核，不可发送！");
            }
        } else {
            Colyn.log("该审核不可发送！");
        }
    },
    //发送担保审核
    GuarCheck: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }

        var govId = $("#govId").val();
        var govDialog = $.hDialog({
            title: '担保机构信息',
            width: 800,
            height: 400,
            cache: false,
            href: '/government/relatedGuarGovListView?govId=' + govId,
            modal: true,
            onClose: function () {},
            buttons: [
            {
                text: '确认选择',
                iconCls: 'icon-ok',
                handler: function () {
                    var rows = $('#GuaranteeGovListGrid').datagrid('getSelections'); // 只支持单选

                    var param= {
                        govType: 1, // 机构类型 1担保 0小贷
                        govId: rows[0].govId, // 机构ID
                        applyId: row.pro_check_id,
                        reviewType: row.pro_check_type
                    };
                    ToTransfer(param);
                    govDialog.dialog("close");
                }
            },
            {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    govDialog.dialog("close");
                }
            }
            ]
        });
    },
    // 撤回
    Recall: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        // 已发送担保或已发送平台初审时可撤回
        if (row.pro_send_gov == 2 || row.pro_send_gov == 0) {
            var param = {
                reviewType: row.pro_check_type,
                applyId   : row.pro_check_id,
                opType    : 6 //撤回
            };
            ToAudit(param, "撤回");
        } else {
            Colyn.log("此项目不可撤回！");
        }
    },
    //退回
    Return: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/review/rollbackCheckView",
            iconCls: 'icon-add',
            title: "退回意见",
            width: $(window).width() - 80,
            buttons: [
            {
                text: '确认退回',
                iconCls: 'icon-add',
                handler: function () {
                    var msg = $("#checkIdear").val();
                    if (msg == "" || msg == null || msg == "请输入...") {
                        Colyn.log("请输入退回意见！");
                        return;
                    }

                    var param = {
                        reviewType: row.pro_check_type,
                        applyId   : row.pro_check_id,
                        opType    : 3, // 退回
                        message   : msg
                    };
                    ToAudit(param, "退回");
                    Dialog.dialog("close");
                }
            },
            {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }
            ]
        });
    },
    //审核意见
    CheckIdear: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = {
            applyId: row.pro_check_id,
            reviewType: row.pro_check_type
        };
        var Dialog = $.hDialog({
            href: "/review/AuditOpinionListView?" + getParam(o),
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
    // 搜索
    Search: function () {
        var param = createSearchParam();
        $.post("/review/json/LoanGuarGovListSearch.json?", param, function (data) {
            $("#LoanGovCheckGrid").datagrid("loadData", data)
        }, 'json');
    },
    //资料补全
    MatainAdd: function () {
        var row = MyGrid.selectRow();
        if (row) {
        	if(row.can_check && row.pro_send_gov == 1){
        		var editPageUrl;
        		
        		if(row.pro_intent_id != 0) {
        			var o = { intentId: row.pro_intent_id };
        			editPageUrl = "/loan/loanAddViewForIntent?" + getParam(o);
        		}
        		else {
        			editPageUrl = "/loan/loanAddView?loanId=" + row.pro_loan_id;
        		}
        		
	            var editDialog = $.hDialog({
	                href: editPageUrl,
	                iconCls: 'icon-pencil',
	                title: "资料补全",
	                // maximizable: true,//显示最大化
	                width: $(window).width() - 40,
	                height: $(window).height() - 50,
	                onLoad: function () {
	                    SetSelectValue();
	                    getUnit();
	                    removeStyle();
	                    //MyArea.Init('act_bank_area_noA', 'act_bank_area_noB', 'hdfact_bank_area_no');//初始化地区
	                    //$("#act_bank_area_noA").change(function () { MyArea.AreaChange('act_bank_area_noA', 'act_bank_area_noB'); });
	                },
	                buttons: [{
	                    text: '确认修改',
	                    iconCls: 'icon-pencil',
	                    handler: function () {
	                        if ($('#Colyn').form('validate')) {
	                            var pro_loan_id = row.pro_loan_id;
	                            var pro_intent_id = row.pro_intent_id;
	                            var param = createParam2([
	                                { formId: 'main', relaClass: "main" },
	                                { formId: 'loan_person' }
	                            ], "edit", pro_loan_id);
	                            var param1 = createParam4([
		                           { formClass: 'carInfo', isAnyRow: true },
		                           { formClass: 'houseInfo', isAnyRow: true }
		                           ]);
		                        var queryParam = getParam(param) + "&" + getParam(param1);
	                            if (row.pro_check_type == 17) {//融资维护发过来的审核
		                            $.AjaxColynJson("/loan/json/LoanInfoAddOrEdit.json?loanId="+pro_loan_id, queryParam, function (data) {
		                                if (data.success) {
		                                    //updateZtreeHref(data.msg);
		                                    //getUpload(imgObj, 2, '项目信息', row.pro_loan_id, "修改成功", "影像资料上传失败");
		                                    MyAction.Init();
		                                    //step();
		                                    editDialog.dialog("close");
		                                } else {
		                                    Colyn.log(data.message);
		                                }
		                            });
	                            } else if (row.pro_check_type == 1) {
	                            	 $.AjaxColynJson("/loan/json/IntentLoanInfoSupplement.json?loanIntentId="+pro_intent_id, queryParam, function (data) {
	 	                                if (data.success) {
	 	                                    //updateZtreeHref(data.msg);
	 	                                    //getUpload(imgObj, 2, '项目信息', row.pro_loan_id, "修改成功", "影像资料上传失败");
	 	                                    MyAction.Init();
	 	                                    //step();
	 	                                    editDialog.dialog("close");
	 	                                } else {
	 	                                    Colyn.log(data.message);
	 	                                }
	 	                            });
	                            }
	                        }
	                    }
	                }, {
	                    text: '取消返回',
	                    iconCls: 'icon-cancel',
	                    handler: function () {
	                        editDialog.dialog("close");
	                    }
	                }]
	            })
        	}
        	else{
            	Colyn.log("当前状态不可资料补全");
            }
        } else {
            Colyn.log("请选择一条数据进行操作");
        }
    }
};

$("#pro_card_type").live("change", function () {
    var p1 = $(this).children('option:selected').val();
    var cardA = $("#cardA");
    var cardB = $("#cardB");
    //选项发生变化，清空值
    cardA.val("");
    cardB.val("");
    //若果没有选择证件类型，则禁用输入证件号
    if (p1 == "") {
        cardA.attr("disabled", "disabled");
        cardB.attr("disabled", "disabled");
        cardB.attr("name", "pro_card_no");
        cardB.css("display", "inline");
        cardA.css("display", "none");
        cardA.removeAttr("name");
    } else {
        cardA.removeAttr("disabled");
        cardB.removeAttr("disabled");
        if (p1 == "身份证") {
            cardA.attr("name", "pro_card_no");
            cardA.css("display", "inline");
            cardB.css("display", "none");
            cardB.removeAttr("name");
        } else {
            cardB.attr("name", "pro_card_no");
            cardB.css("display", "inline");
            cardA.css("display", "none");
            cardA.removeAttr("name");
        }
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

    var p1 = $("#pro_card_type").children('option:selected').val();
    var cardA = $("#cardA");
    var cardB = $("#cardB");
    //若果没有选择证件类型，则禁用输入证件号
    if (p1 == "") {
        cardA.attr("disabled", "disabled");
        cardB.attr("disabled", "disabled");
        cardB.attr("name", "pro_card_no");
        cardB.css("display", "inline");
        cardA.css("display", "none");
        cardA.removeAttr("name");
    } else {
        cardA.removeAttr("disabled");
        cardB.removeAttr("disabled");
        if (p1 == "身份证") {
            cardA.attr("name", "pro_card_no");
            cardA.css("display", "inline");
            cardB.css("display", "none");
            cardB.removeAttr("name");
        } else {
            cardB.attr("name", "pro_card_no");
            cardB.css("display", "inline");
            cardA.css("display", "none");
            cardA.removeAttr("name");
            cardA.val("");
        }
    }
}

// 处理审核操作
function ToAudit(param, opName) {
    if ($('#Colyn').form('validate')) {
        $.AjaxColynJson("/review/json/ReviewCheckData.json?" + getParam(param), function (data) {
            if (data.success) {
                Colyn.log(opName + "成功！");
            } else {
                Colyn.log(data.message);
            }

            MyAction.Init();
        });
    }
}

// 处理发送
function ToTransfer(param) {
    $.post("/review/json/ReviewSendCheckData.json", getParam(param), function (data) {
        if (data.success) {
            Colyn.log("发送成功！");
        } else {
            Colyn.log(data.message);
        }

        MyAction.Init();
    }, 'Json');
}

// 创建搜索参数
function createSearchParam() {
    var param = {
        loanNo : $("#loanNo").val(),
        userName : $("#userName").val(),
        reviewState : $("#reviewState").val(),
        loanCategory : $("#loanCategory").val(),
        applyTimeFrom : $("#applyTimeFrom").datebox("getValue"),
        applyTimeTo : $("#applyTimeTo").datebox("getValue"),
        loanMoneyFrom : $("#loanMoneyFrom").val(),
        loanMoneyTo : $("#loanMoneyTo").val(),
        currRoleIdx: 4 // 信贷审核
    };

    if ($(".pagination-num").val()) {
        param.page = $(".pagination-num").val();
    }

    if ($(".pagination-page-list").val()) {
        param.rows = $(".pagination-page-list").val();
    }

    return getParam(param);
}
