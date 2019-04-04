$(function() {
    MyGrid.Resize();
    $("#Add").click(MyActionForLoanList.Add);
    $("#Edit").click(MyActionForLoanList.Edit);
    $("#Del").click(MyActionForLoanList.Del);
    $("#LookUp").click(MyActionForLoanList.LookUp);
    $("#SendToCheck").click(MyActionForLoanList.SendToCheck); // 发送到平台审核
    $("#GuarCheck").click(MyActionForLoanList.GuarCheck); // 发送到担保审核
    $("#Recall").click(MyActionForLoanList.Recall); // 撤回
    $("#CheckIdear").click(MyActionForLoanList.CheckIdear);
    $("#btnSearch").click(MyActionForLoanList.Search); // 查询
    MyActionForLoanList.Init();
    $(window).resize(function() {
        MyGrid.RefreshPanl();
    });
});

var MyActionForLoanList = {
    Init: function() {
        $("#LoanGrid").datagrid({
            method: "Post",
            url: "/projectmanage/json/ProjectTrackListView.json?isFromIntent=false",
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
                field: "loanId",
                title: "标号",
                width: 120,
                align: "center"
            },{
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
                        return value+"%";
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
            //{
            //    field: "loanType",
            //    title: "项目类型",
            //    width: 100,
            //    align: "center",
            //    formatter: function(value, rowData, index) {
            //    	 if (value == null) {
            //             return "-";
            //         } else {
            //             return value;
            //         }
            //      
            //    }
            //},
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
        var p = $('#LoanGrid').datagrid('getPager');
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
            href: "/projectmanage/LoanProjectLookUpView?loanId=" + row.loanId + "&loanFileUrl=" + row.loanFileUrl,
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
    Add: function() {
        window.location.href = "/loan/LoanTempListView"+ window.location.search +"&btnToBack=1"
    },
    //修改
    Edit: function() {
        var row = MyGrid.selectRow();
        if (row) {
            if (row.loanState != "融资维护待审核" && row.loanState != "待项目初审" && row.loanState != "项目初审已退回") {
                Colyn.log("当前节点状态下不可以修改");
                return;
            }
            var o = { loanId: row.loanId };
            var url = "/loan/loanCustomAddView.vm";
            var editDialog = $.hDialog({
                href: url + "?" + getParam(o) + "&btnToAdd=1&btnToBack=1&num=" + Date.now,
                iconCls: 'icon-pencil',
                title: "修改项目申请信息",
                // maximizable: true,//显示最大化
                width: 900,
                height: $(window).height() - 50,
                onLoad: function() {
                	uploadImg();
                    SetSelectValue();
                },
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function() {
                    	
                    	 //二次分配时，控制收款人和借款人不能为同一人
                        if($("#payee_status").val()==1 && $("#act_loanee_id").val()==$("#act_payee_id").val()){
                        	Colyn.log("二次分配时，收款人和借款人不能为同一人");
                        	return;
                        }
                        
                        if ($('#main').form('validate')&& $("#carHouseForm").form('validate')) {
                        	//手续费收取方式是分期收取
            	        	if($('#handFeeKind').val()=="2"){
            	        		if($('#fsRate').val()==""){
        				            Colyn.log("请填写服务费年化率！");
        				            return;
        			            }
            		            //if($('#fsChargeType').val()=="2"){//比例
            			        //    if($('#fsRate').val()==""){
            				    //        Colyn.log("请填写收费比例！");
            				    //        return;
            			        //    }
            		            //}
            		            //if($('#fsChargeType').val()=="1"){//每笔
            		            //	if($('#fsAccurateAmount').val()==""){
            				    //        Colyn.log("请填写按笔收费金额！");
            				    //        return;
            			        //    }
            		            //}
            	            }
                        	if($('#category').val()==2||$('#category').val()==3){
                                if (!($('#addView').form('validate'))) { 
                                return;
                              }
                              }
                            var proLoanId = $("#loanId").val();
                            var param = createParam2([{ formId: 'main', relaClass: "main" }]);
                			var param1 = createParam4([{ formClass: 'carInfo', isAnyRow: true },{ formClass: 'houseInfo', isAnyRow: true }]);
   
                			var queryParam = getParam(param) + "&" + getParam(param1);
        					console.log(queryParam);
                            //适用范围
                            var chk_value ="";
                            $('input[name="redUseScope"]:checked').each(function(){ 
                            	chk_value = chk_value + $(this).val() + ",";  
                            });
                            
                            queryParam = queryParam + "&redUseScopes=" + chk_value;
                			
                            $.AjaxColynJson("/loan/json/LoanInfoAddOrEdit.json?loanId=" + proLoanId, queryParam,
                            function(data) {
                                if (data.success) {
                                	Colyn.log(data.message);
                                	$('#LoanGrid').datagrid('loadData', {total: 0, rows: []});
                                	MyActionForLoanList.Init();
                                    editDialog.dialog("close");
                                    
                                } else {
                                    Colyn.log(data.message);
                                }
                            });
                        }
                    }
                },
                {
                    text: '取消返回',
                    iconCls: 'icon-cancel',
                    handler: function() {
                        editDialog.dialog("close");
                    }
                }]
            });
        } else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    Del: function() {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？",
            function(r) {
                if (r) {
                    var param = {
                        loanId: row.loanId
                    };
                    $.post('/loan/json/LoanDeleteData.json', param, function(data) {
                    	if (data.success) {
                            Colyn.log("删除成功");
                            $('#LoanGrid').datagrid('loadData', {total: 0, rows: []});
                            MyActionForLoanList.Init();
                        } else {
                            Colyn.log(data.message);
                        }
                    }, "json");
                }
            });
        } else {
            Colyn.log("请选择内容删除！");
        }
    },
    // 发送平台审核
    SendToCheck: function() {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if(row.loanGuarGov != undefined){
       	 	Colyn.log("请先发送到担保机构审核！");
            return;
        }
        var o = {
            loanId: row.loanId
        };
        $.post('/loan/json/SendPlatformData.json', o, function(data) {
            if (data.success) {
                Colyn.log("发送成功");
            } else {
                Colyn.log(data.message);
            }
            $('#LoanGrid').datagrid('loadData', {total: 0, rows: []});
            MyActionForLoanList.Init();
        },"json");
    },
    // 发送审核
    GuarCheck: function() {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if(row.loanGuarGov == undefined){
        	 Colyn.log("没有担保机构");
             return;
        }
        $.messager.confirm("发送担保", "发送到"+row.govName+"审核？", function(data){
        	if(data){
        		ToTransfer(row.loanId, "1", row.loanGuarGov);
        	}
        });
        //改为直接发送到loan中的担保机构，不需要选择
       /* var dialog = $.hDialog({
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
                        if(rows==null || rows.length==0){
                        	 Colyn.log("请选择一条记录进行操作");
                        	 return;
                        }
                        var govId = rows[0].govId;
                        var govType = rows[0].govIsOfferGuar; // 机构类型 1担保 0小贷

                        ToTransfer(row.loanId, govType, govId);
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
        }); */
    },
    // 撤回
    Recall: function() {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var o = {
            reviewType: 17, // 融资审核
            applyId   : row.loanId,
            opType    : 6 //撤回
        };
        $.post("/review/json/ReviewCheckData.json?" + getParam(o), function (data) {
            if (data.success) {
                Colyn.log("撤回成功！");
            } else {
                Colyn.log(data.message);
            }

            $('#LoanGrid').datagrid('loadData', {total: 0, rows: []});
            MyActionForLoanList.Init();
        }, "json");
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
    Search: function() {
        if ($('#SearchForm').form('validate')) {
            var param = createParam3("SearchForm");
            var o = {
                modelAction: "Search",
                isFromIntent: false
            };
            $.post("/projectmanage/json/ProjectTrackListView.json?" + getParam(o), param,
            function(data) {
                $("#LoanGrid").datagrid("loadData", data);
                $("#LoanGrid").datagrid("clearSelections");
            },
            "json");
        }
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

// 处理发送审核到小贷或担保
function ToTransfer(intentId, govType, govId) {
    var o = {
        reviewType: 17, // 融资审核
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

        $('#LoanGrid').datagrid('loadData', {total: 0, rows: []});
        MyActionForLoanList.Init();
    }, 'Json');
}
