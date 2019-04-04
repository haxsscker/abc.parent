var dialog;
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Audit").click(MyAction.Audit);
    $("#MatainAdd").click(MyAction.MatainAdd);
    $("#SendToCheck").click(MyAction.SendToCheck);
    $("#GuarCheck").click(MyAction.GuarCheck);
    $("#CheckIdear").click(MyAction.CheckIdear);
    $("#btnSearch").click(MyAction.Search);
    $("#Recall").click(MyAction.Recall);

    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var getData = function (page, rows) {
    $.ajax({
        type: "POST",
        url: "/review/json/IntentListSearch.json?" + createSearchParam(),
        data: "page=" + page + "&rows=" + rows,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.progress('close');
        },
        success: function (rows) {
            $('#IntentListGrid').datagrid('loadData', rows);
        }
    });
};

var MyAction = {
    Init: function () {
        $("#IntentListGrid").datagrid({
            method: "Post",
            url: "/review/json/IntentListSearch.json?" + createSearchParam(),
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview, //重写当没有数据时
            emptyMsg: '没有找到数据', //返回数据字符
            columns: [[
                //{ field: "pro_intent_no", title: "意向编号", width: 200, align: "center" },
                { field: "pro_loan_no", title: "项目名称", width: 100, align: "center", formatter:function(value){
                	if(value==null||value==''){
                		return '-';
                	}
                	return value;
                }},
                { field: "pdo_product_name", title: "项目类型", width: 100, align: "center" },
                { field: "pro_user_name", title: "意向申请人", width: 100, align: "center" },
                { field: "pro_user_phone", title: "申请人电话", width: 100, align: "center" },
                {
                    field: "pro_loan_money", title: "借款金额", width: 100, align: "right", formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }
                },
                {
                    field: "pro_loan_rate", title: "年化收益率", width: 80, align: "right", formatter: function (value, rowData, index) {
                        return formatPercent(value);
                    }
                },
                { field: "pro_loan_period", title: "借款期限", width: 80, align: "center" },
                { field: "pro_add_date", title: "申请日期", width: 100, align: "center" },
                { field: "pro_loan_use", title: "借款用途", width: 250, align: "center" },
                { field: "pro_pay_type", title: "还款方式", width: 100, align: "center" },
                { field: "pro_guar_gov", title: "担保机构", width: 100, align: "center",
                	formatter: function(value){
                		if(value==undefined){
                			return '-';
                		} else {
                			return value;
                		}
                	}
                },
                { field: "pro_loan_intentstate", title: "审核状态", width: 80, align: "center" },
                { field: "pro_send_status", title: "发送状态", width: 100, align: "center" }
                //,
                //{ field: "pro_check_role", title: "待审角色", width: 80, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        });
        var p = $('#IntentListGrid').datagrid('getPager');
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
    Audit: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (!row.can_intent_check || row.pro_send_gov != -1) {
            Colyn.log("当前不可审核！");
            return;
        }
        // 只有资料补全后才可以发送
        if (!row.pro_has_loan) {
            Colyn.log("未进行资料补全，不可审核！");
            return;
        }

        var o = { intentId: row.pro_intent_id };
        dialog = $.hDialog({
            href: "/review/IntentCheckView?" + getParam(o),
            iconCls: 'icon-user_magnify',
            title: "意向融资审核",
            maximizable: true, //显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [
                {
                    text: '同意',
                    iconCls: 'icon-user_magnify',
                    handler: function () {
                	   if($("#Colynee").form('validate')){
                		   if ($("#check_idear").val() != "" && $("#check_idear").val() != "请输入...") {
                               ToAudit(row.pro_intent_id, 1);
                               dialog.dialog("close");
                           } else {
                               Colyn.log("审核意见不能为空！");
                           }
                       }
                    }
                },
                {
                    text: '否决',
                    iconCls: 'icon-user_magnify',
                    handler: function () {
                    	if($("#Colynee").form('validate')){
	                        if ($("#check_idear").val() != "" && $("#check_idear").val() != "请输入...") {
	                            ToAudit(row.pro_intent_id, 2);
	                            dialog.dialog("close");
	                        } else {
	                            Colyn.log("审核意见不能为空！");
	                        }
                        }
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        dialog.dialog("close");
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
        dialog = $.hDialog({
            href: "/review/IntentLookUpView?intentId=" + row.pro_intent_id,
            iconCls: 'icon-zoom',
            title: "意向融资信息查看",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dialog.dialog("close");
                }
            }]
        });
    },
    //发送平台审核
    SendToCheck: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        // 只有资料补全后才可以发送
        if (!row.pro_has_loan) {
            Colyn.log("未进行资料补全，不可发送！");
            return;
        }
        // 只有未发送时可以发送到平台初审
        if (row.pro_send_gov != -1) {
            Colyn.log("已发送，不可再次发送！");
            return;
        }
        if(row.pro_loan_intentstate=="审核未通过"){
        	 Colyn.log("审核未通过不可发送！");
             return;
        }
        
        if(row.pro_guar_gov != undefined){
       	 	Colyn.log("请先发送到担保机构审核！");
            return;
        }
        // 只有通过审核后才能发送到平台初审
        if (!row.can_intent_check) {
            var govType = 2; // 机构类型 2 代表平台
            var govId = 2;   // 机构ID 2 代表平台
            ToTransfer(row.pro_intent_id, govType, govId);
        } else {
            Colyn.log("未审核，不可发送！");
        }
    },
    //发送审核
    GuarCheck: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        // 只有资料补全后才可以发送
        if (!row.pro_has_loan) {
            Colyn.log("未进行资料补全，不可发送！");
            return;
        }
        
        if(row.pro_loan_intentstate=="审核未通过"){
	       	 Colyn.log("审核未通过不可发送！");
	         return;
	    }
        // 可以审核并且没有发送时才可发送
        if (!row.can_intent_check && row.pro_send_gov == -1) {
        	if(row.pro_guar_gov == undefined){
           	 	Colyn.log("没有担保机构");
                return;
            }
        	$.messager.confirm("发送担保", "发送到"+row.pro_guar_gov+"审核？", function(data){
            	if(data){
            		ToTransfer(row.pro_intent_id, "1", row.pro_gov_id); // "1" 为发送到担保
            	}
            });
            /*dialog = $.hDialog({
                href: "/government/allGovListView",
                iconCls: 'icon-user_magnify',
                title: "发送审核",
                width: $(window).width() - 40,
                height: $(window).height() - 50,
                buttons: [
                    {
                        text: '发送审核',
                        iconCls: 'icon-arrow_turn_right',
                        handler: function () {
                            var rows = $('#AllGovListGrid').datagrid('getSelections'); // 只支持单选
                            var govId = rows[0].govId;
                            var govType = rows[0].govIsOfferGuar; // 机构类型 1担保 0小贷

                            ToTransfer(row.pro_intent_id, govType, govId);
                            dialog.dialog("close");
                        }
                    },
                    {
                        text: '关闭',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            dialog.dialog("close");
                        }
                    }
                ]
            });*/
            
        } else {
            Colyn.log("请先审核后再发送！");
            //MyAction.Init();
        }
    },
    // 撤回
    Recall: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        // 有发送状态时可撤回
        if (row.pro_send_gov != -1) {
            var o = {
                reviewType: 1, // 意向审核
                applyId   : row.pro_intent_id,
                opType    : 6 //撤回
            };
            $.post("/review/json/ReviewCheckData.json?" + getParam(o), function (data) {
                if (data.success) {
                    Colyn.log("撤回成功！");
                } else {
                    Colyn.log(data.message);
                }

                $('#IntentListGrid').datagrid('loadData', {total: 0, rows: []});
                MyAction.Init();
            }, "json");
        } else {
            Colyn.log("此项目不可撤回！");
            //MyAction.Init();
        }
    },
    //资料补全
    MatainAdd: function () {
        var row = MyGrid.selectRow();
        if (row) {
        	if(row.can_intent_check && row.pro_send_gov == -1){
	            var o = {
	            	intentId: row.pro_intent_id
	            };
	            var editDialog = $.hDialog({
	                href: "/loan/loanAddViewForIntent?" + getParam(o),
	                iconCls: 'icon-pencil',
	                title: "资料补全",
	                 maximizable: true,//显示最大化
	                width: $(window).width() - 40,
	                height: $(window).height() - 50,
	                onLoad: function () {
	                    SetSelectValue();
	                },
	                buttons: [{
	                    text: '确认修改',
	                    iconCls: 'icon-pencil',
	                    handler: function () {
	                        $('#loanLogo').validatebox('disableValidation');
	                        if ($('#main').form('validate') && $("#addView").form('validate')) {
	                            var param = createParam2([
	                                { formId: 'main', relaClass: "main" }
	                            ], "add");
	                            var param1 = createParam4([
	                                { formClass: 'carInfo', isAnyRow: true },
	                                { formClass: 'houseInfo', isAnyRow: true }
	                            ]);
	                            var queryParam = getParam(param) + "&" + getParam(param1) + "&loanIntentId=" + row.pro_intent_id;
	                            console.log(queryParam);
	                            //适用范围
	                            var investRedsendRatio=$.trim($('#investRedsendRatio').val());   //投资反送红包的比例
	                            if(investRedsendRatio!=null && investRedsendRatio>0){
	                            	if($('input[name="redUseScope"]:checked').length==0){
	                            		Colyn.log("投资返送红包比例不为0,红包使用范围必填");
	                               	 return;
	                            	}               	 
	                            }
	                            var chk_value ="";
	                            $('input[name="redUseScope"]:checked').each(function(){ 
	                            	chk_value = chk_value + $(this).val() + ",";  
	                            });
	                            
	                            queryParam = queryParam + "&redUseScopes=" + chk_value;
	                            
	                            debugger;
	                            var proPayType = $('#pro_pay_type').val();//还款方式
	                            var loanPeriodUnit = $('#loanPeriodUnit').val();//期限单位
		           	             var pro_pay_date = $("#pro_pay_date").val();//付息日
		           	             var loanExpireDate = $("input[name='loanExpireDate']").val();//项目到期日
		           	             var pro_loan_period = $("#pro_loan_period").val();//借款期限
		           	             var loanInvestStarttime = $("input[name='loanInvestStarttime']").val();//投资开始时间
		           	             var pro_invest_endDate = $("input[name='pro_invest_endDate']").val();//招标到期日
		           	             
		           	             if(loanInvestStarttime==''){
		           	            	Colyn.log("投资开始时间不能为空");
		           	            	return;
		           	             }
			           	          if(loanPeriodUnit!=3 && proPayType!=4){//月标、年标同时不是利随本清或万分之五还款方式，验证项目到期日与借款期限
			      			    	var loanParam = { loanPeriodUnit:loanPeriodUnit, pro_pay_date:pro_pay_date,loanExpireDate:loanExpireDate,
			      			    		 pro_loan_period:pro_loan_period,loanInvestStarttime:loanInvestStarttime,pro_invest_endDate:pro_invest_endDate};
			      		          	$.post("/loan/json/ValidateLoanExpireDate.json", loanParam, function (data) {
			      		                    if (data.success) {
			      		                    	$.post("/loan/json/IntentLoanInfoSupplement.json?", queryParam, function (data) {
			    	                                if (data.success) {
			    	                                    Colyn.log(data.message);
	//		    	                                    MyAction.Init();                                    
			    	                                    editDialog.dialog("close");
			    	                                    $('#IntentListGrid').datagrid('reload');  //重载行。等同于'load'方法，但是它将保持在当前页。                                
			    	                                } else {
			    	                                    Colyn.log(data.message);
			    	                                }
			    	                            },'json');
			      		                    }else{
			      		                        Colyn.log(data.message);
			      		                        return;
			      		                    }
			      		            });
			      	            }else{
			      	            	$.post("/loan/json/IntentLoanInfoSupplement.json?", queryParam, function (data) {
		                                if (data.success) {
		                                    Colyn.log(data.message);
	//	                                    MyAction.Init();                                    
		                                    editDialog.dialog("close");
		                                    $('#IntentListGrid').datagrid('reload');  //重载行。等同于'load'方法，但是它将保持在当前页。                                
		                                } else {
		                                    Colyn.log(data.message);
		                                }
		                            },'json');
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
	            });
        	} else {
        		Colyn.log("当前状态不可资料补全");
        	}
        } else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    //查看审核意见
    CheckIdear: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = {
            applyId: row.pro_intent_id,
            reviewType: 1  // 意向审核
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
    //搜索
    Search: function () {
        var param = createSearchParam();
        $.post("/review/json/IntentListSearch.json?", param, function (data) {
            $('#IntentListGrid').datagrid('loadData', {total: 0, rows: []});
            $("#IntentListGrid").datagrid("loadData", data);
        }, "json");
    }
};

$("#pro_card_type").live("change", function () {
    var p1 = $(this).children('option:selected').val();
    var cardA = $("#cardA");//证件号码
    cardA.val("");
    //请选择时： 证件号码不能使用 并清空值
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

    //绑定证件号
    var p1 = $("#pro_card_type").children('option:selected').val();
    var cardA = $("#cardA");//证件号码
    //请选择时： 证件号码不能使用 并清空值
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

// 处理审核操作
function ToAudit(intentId, checkStatus) {
    var o = {
        applyId: intentId,
        opType: checkStatus,
        message: $("#check_idear").val(),
        reviewType: 1 // 意向审核的reviewType
    };
    $.post("/review/json/ReviewCheckData.json?" + getParam(o), function(data) {
        if (data.success) {
            Colyn.log("审核成功！");
        } else {
            Colyn.log("审核失败！");
        }

        $('#IntentListGrid').datagrid('loadData', {total: 0, rows: []});
        MyAction.Init();
        //$('#IntentListGrid').datagrid('reload');  //重载行。等同于'load'方法，但是它将保持在当前页。      
    }, "json");
}

function ToTransfer(intentId, govType, govId) {
    var o = {
        reviewType: 1, // 意向审核的reviewType
        applyId: intentId,
        govType: govType,  // 机构类型 1担保 0小贷
        govId: govId
    };

    $.post("/review/json/ReviewSendCheckData.json", o, function (data) {
        if (data.success) {
            Colyn.log("发送成功！");
        } else {
            Colyn.log(data.message);
        }

        $('#IntentListGrid').datagrid('loadData', {total: 0, rows: []});
        MyAction.Init();
    }, 'Json');
}

function createSearchParam() {
    var form = {
        reviewType : 1, //意向审核
        //intentNo : $("#intentNo").val(),
        userName : $("#userName").val(),
        reviewState : $("#reviewState").val(),
        intentCategory : $("#intentCategory").val(),
        intentTimeFrom : $("#intentTimeFrom").datebox("getValue"),
        intentTimeTo : $("#intentTimeTo").datebox("getValue"),
        intentMoneyFrom : $("#intentMoneyFrom").val(),
        intentMoneyTo : $("#intentMoneyTo").val()
    };

    if ($(".pagination-num").val()) {
        form.page = $(".pagination-num").val();
    }

    if ($(".pagination-page-list").val()) {
        form.rows = $(".pagination-page-list").val();
    }

    return getParam(form);
}
