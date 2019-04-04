/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Export").click(MyAction.Export);
    $("#btnSearch").click(MyAction.Search);
    $("#ComFull").click(MyAction.ComFull);
    $("#generateProContract").click(MyAction.generateProContract);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    //列表OK
    Init: function () {
        $("#ProjectScheduleGrid").datagrid({
            method: "Post",
            url: "/projectmanage/json/ProjectScheduleListView.json",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview, //重写当没有数据时
            emptyMsg: '没有找到数据', //返回数据字符
            columns: [
                [
                    { field: "loanNo", title: "项目名称", width: 150, align: "center" },
                    { field: "loanCategory", title: "项目类型", width: 120, align: "center" },
                    { field: "loaneeName", title: "借款人", width: 120, align: "center" },
                    { field: "loanUserPhone", title: "借款人号码", width: 120, align: "center" },
                    { field: "loanMoney", title: "借款金额", width: 120, align: "right", formatter: function(value, rowData, index) { return formatMoney(value, '￥'); } },
                    {
                        field: "loanRate", title: "年化收益率", width: 120, align: "right", formatter: function (value, rowData, index) {
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
                        formatter: function (value, rowData, index) {
                            if (value == "" || value == null) {
                                return "-";
                            } else {
                                return value;
                            };
                        }
                    },
                    {
                        field: "payedPaymentPeriod",
                        title: "已还期数",
                        width: 70,
                        align: "center",
                        formatter: function(value, rowData, index) {
                            if (value == "" || value == null) {
                                return "0";
                            } else {
                                return value;
                            };
                        }
                    },
                    {
                        field: "tranferUserName",
                        title: "转让人",
                        width: 120,
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
                        field: "transferMoney",
                        title: "转让金额",
                        width: 100,
                        align: "right",
                        formatter: function(value, rowData, index) {
                            if (value == "" || value == null) {
                                return "-";
                            } else {
                                return formatMoney(value, '￥');
                            };
                        }
                    },
                    {
                        field: "transferFee",
                        title: "手续费",
                        width: 100,
                        align: "right",
                        formatter: function(value, rowData, index) {
                            if (value == "" || value == null) {
                                return "-";
                            } else {
                                return formatMoney(value, '￥');
                            };
                        }
                    },
                    {
                        field: "createtimeStr", title: "申请日期", width: 100, align: "center", formatter: function (value, rowData, index) {
                            if (value == "" || value == null) {
                                return "-";
                            } else {
                                return value;
                            }; 
                        }
                    },
//                    {
//                        field: "investEndtimeStr", title: "招标到期日", width: 100, align: "center", formatter: function (value, rowData, index) {
//                            if (value == "" || value == null) {
//                                return "-";
//                            } else {
//                                return value;
//                            }; 
//                        }
//                    },
                    {
                        field: "transferLoanStateStr",
                        title: "转让状态",
                        width: 100,
                        align: "center",
                        formatter: function(value, rowData, index) {
                            return value;
                        }
                    }
                ]
            ],
            pagination: true,
            singleSelect: true
        });
        var p = $('#ProjectScheduleGrid').datagrid('getPager');
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
    //查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            //href: "/projectmanage/TransferLoanProjectLookUpView?loanId=" + row.originId +"&transferId=" + row.id,
            href: "/projectmanage/TransferLoanProjectLookUpView?transferId=" + row.id + "&loanFileUrl=" + row.loanFileUrl,
            method: "Post",
            iconCls: 'icon-add',
            title: "转让跟踪",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function() {
                        Dialog.dialog("close");
                    }
                }
            ]
        });
    },
    Export: function () {
        if ($('#ProjectScheduleGrid').datagrid('getRows').length > 0) {
            var param = createParam3("SearchForm");
            window.location = "/P2PProjectManage/ProjectScheduleListExcl?" + getParam(param);
        } else {
            Colyn.log("暂无数据");
        }
    },
    ///搜索OK
    Search: function () {
        var param = createParam3("SearchForm");
        var transLoanStatus = $(".SearchForm select[name='transferLoanState']").val() ;
        if(transLoanStatus == "-1" && !param.searchForm){
        	if(!param.searchForm){
        		
        		param.searchForm =JSON.stringify( { Items:[{ Field:"transferLoanState",Value:-1 }] })
        	}
        	
        }
        else{
        	var arr = JSON.parse(param.searchForm).Items;
        	if(transLoanStatus == "-1"){
        		arr.push({ Field:"transferLoanState",Value:"-1"  })
        	}
        	for(var item in arr){
        		if(arr[item].Field == "transferLoanState"){
        			arr[item].Value = transLoanStatus;
        		}
        	}
        	param.searchForm = JSON.stringify({ Items:arr })
        }
       
        var o = { modelAction: "Search" };
        $.post("/projectmanage/json/ProjectScheduleListView.json?" + getParam(o), param, function (data) {
            $("#ProjectScheduleGrid").datagrid("loadData", data);
        }, "json");
    },
    // 【生成合同】
	generateProContract : function() {
		var row = MyGrid.selectRow();
		if (row == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		var state = row.transferLoanStateStr;
        if(state != '已划转'){
        	Colyn.log("已划转的项目才可以生成合同");
            return;
        }
		var LoanId = row.id;
		$.AjaxColynJson("/moneyManage/json/GenerateProContract.json?loanId="+ LoanId+"&bidType=1&isSendMail="+false,
				"", function(data) {
						if (data.success) {
							Colyn.log("合同生成成功！");
							$('#ProjectScheduleGrid').datagrid('reload');
						} else {
							Colyn.log(data.message);
						}
				});
	},
    //强制满标
    ComFull: function(){
		var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        //(待审核、转让招标中、满标待审、满标审核通过、满标审核未通过)可流标
        var state = row.transferLoanStateStr;
        if(state=='已划转'||state=='已流标'||state=='划转中'||state=='初审未通过'){
        	Colyn.log(state+",不可强制满标");
            return;
        }
        $.messager.confirm('提示','确定要进行强制满标吗?',function(r){
            if (r){
              var transferLoanId = row.id;
              $.ajax({
              	url: '/review/json/cancelTransferLoan.json?transferLoanId='+transferLoanId,
              	success: function(result){
              		 Colyn.log(result.message);
              	}
              });
            }
        });
	}
}