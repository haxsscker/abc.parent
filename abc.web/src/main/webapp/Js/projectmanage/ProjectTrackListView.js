/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-20 
-------------------------------------------------*/
$(function() {
	MyGrid.Resize();
	MyAction.Init();
	$("#LookUp").click(MyAction.LookUpssss);
	$("#Export").click(MyAction.Export);
	$("#btnSearch").click(MyAction.Search);
	$("#ComFull").click(MyAction.ComFull);
	$("#loanDataTransfer").click(MyAction.LoanDataTransfer);
	$("#loanDataTransQuery").click(MyAction.LoanDataTransQuery);
	$("#generateProContract").click(MyAction.generateProContract);
	$(window).resize(function() {
		MyGrid.RefreshPanl();
	});
});

var MyAction = {
	Init : function() {
		$("#SchNormalGrid").datagrid({
			method : "Post",
			url : "/projectmanage/json/ProjectTrackListView.json",
			height : $(window).height() - 52,
			pageSize : 10,
			fitColumns : false,
			rownumbers : true,
			nowrap : false,
			striped : true,
			remoteSort : true,
			idField : "loanId",
			view : myview,// 重写当没有数据时
			emptyMsg : '没有找到数据',// 返回数据字符
			columns : [ [ {
				field : "loanNo",
				title : "项目名称",
				width : 150,
				align : "center"
			}, {
				field : "loanCategory",
				title : "项目类型",
				width : 150,
				align : "center",
				formatter : function(value, rowData, index) {
					return value;
				}
			}, {
				field : "loaneeName",
				title : "借款人",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value == null) {
						return "-";
					} else {
						return value;
					}
				}
			}, {
				field : "loanUserPhone",
				title : "借款人号码",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value == null) {
						return "-";
					} else {
						return value;
					}
				}
			}, {
				field : "loanMoney",
				title : "借款金额",
				width : 100,
				align : "right",
				formatter : function(value, rowData, index) {
					return formatMoney(value, '￥');
				}
			}, {
				field : "loanRate",
				title : "年化收益率",
				width : 80,
				align : "right",
				formatter : function(value, rowData, index) {
					if (value != null) {
						return value + "%";
					} else {
						return value;
					}
				}
			}, {
				field : "loanPeriod",
				title : "借款期限",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value == "" || value == null) {
						return "-";
					} else {
						return value;
					}
					;

				}
			}, {
				field : "loanPeriodUnit",
				title : "期限单位",
				width : 80,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value == "" || value == null) {
						return "-";
					} else {
						return value;
					}
					;
				}
			}, {
				field : "loanUse",
				title : "借款用途",
				width : 200,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value != null) {
						return CutString(value, 10);
					} else {
						return "-";
					}
				}
			}, {
				field : "loanSpeed",
				title : "项目进度",
				width : 80,
				align : "center",
				formatter : function(value, rowData, index) {
					var state = rowData.loanState;
					if (state=='满标待审' || state=='满标审核通过' || state=='划转中'||state=='还款中' || state=='已结清') {
						return "100%";
					} else if (value != null) {
						return formatPercento(value, "%");
					} else {
						return "-";
					}
				}
			}, {
				field : "loanPayType",
				title : "还款方式",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					return value;
				}
			}, {
				field : "loanCreatetime",
				title : "申请日期",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value == "" || value == null) {
						return "-";
					} else
						return value;
				}
			}, {
				field : "loanInvestEndtime",
				title : "招标到期日",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value == "" || value == null) {
						return "-";
					} else
						return value;
				}
			}, {
				field : "loanInvestStarttime",
				title : "开始日期",
				width : 100,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value == "" || value == null) {
						return "-";
					} else
						return value;
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
				field : "govName",
				title : "担保机构",
				width : 150,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value != null) {
						return value;
					} else {
						return "-";
					}
				}
			}, {
				field : "loanState",
				title : "项目状态",
				width : 80,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value == "" || value == null) {
						return "-";
					} else {
						return value;
					}
				}
			} ] ],
			pagination : true,
			singleSelect : true
		})
		var p = $('#SchNormalGrid').datagrid('getPager');
		$(p).pagination({
			pageSize : 10,
			pageList : [ 5, 10, 15, 20, 30, 50, 100 ],
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onBeforeRefresh : function() {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			}
		});
	},
	// 查看
	LookUpssss : function() {
		var row = MyGrid.selectRow();
		var num = row;
		if (num == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		var dialogs = $.hDialog({
			href : "/projectmanage/LoanProjectLookUpView?loanId=" + row.loanId + "&loanFileUrl=" + row.loanFileUrl,
			iconCls : 'icon-zoom',
			title : "项目跟踪",
			width : $(window).width() - 40,
			height : $(window).height() - 50,
			buttons : [ {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					dialogs.dialog("close");
				}
			} ]
		});
	},
	Export : function() {
		
		if ($('#SchNormalGrid').datagrid('getRows').length > 0) {
			var param = createParam3("SearchForm");
			window.location.href = '/projectmanage/json/ExportBussinessExamTrackList.json?'
					+ getParam(param);
		} else {
			Colyn.log("暂无数据");
		}
	},
	Recall : function() {
		alert("已撤回");
	},
	
	//搜索
    Search: function () {
    	 if ($('#main').form('validate')) {
        var param = createParam3("SearchForm");
        var loanStatus = $(".SearchForm select[name='loanStatus']").val() ;
        if(loanStatus == "-1" && !param.searchForm){
        	if(!param.searchForm){
        		
        		param.searchForm =JSON.stringify( { Items:[{ Field:"loanStatus",Value:-1 }] })
        	}
        	
        }
        else{
        	var arr = JSON.parse(param.searchForm).Items;
        	if(loanStatus == "-1"){
        		arr.push({ Field:"loanStatus",Value:"-1"  })
        	}
        	for(var item in arr){
        		if(arr[item].Field == "loanStatus"){
        			arr[item].Value = loanStatus;
        		}
        	}
        	param.searchForm = JSON.stringify({ Items:arr })
        }
       
        var o = { modelAction: "Search" };
        $.post("/projectmanage/json/ProjectTrackListView.json?" + getParam(o), param, function (data) {
            $("#SchNormalGrid").datagrid("loadData", data);
        }, "json");
    	 }
    },
    // 【生成合同】
	generateProContract : function() {
		var row = MyGrid.selectRow();
		if (row == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		var state = row.loanState;
        if(state != '已划转' && state != '已结清'){
        	Colyn.log("已划转或已结清的项目才可以生成合同");
            return;
        }
		var LoanId = row.loanId;
		$.AjaxColynJson("/moneyManage/json/GenerateProContract.json?loanId="+ LoanId+"&bidType=0&isSendMail="+false,
				"", function(data) {
						if (data.success) {
							Colyn.log("合同生成成功！");
							$('#SchNormalGrid').datagrid('reload');
						} else {
							Colyn.log(data.message);
						}
				});
	},
	// 【强制满标】
	ComFull : function() {
		var row = MyGrid.selectRow();
		if (row == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		if(row.loanState!='招标中'){
			Colyn.log("只有招标中的项目，可以进行强制满标操作");
			return;
		}
		var loan_id = row.loanId;// 项目申请表主键
		var param = {
			loanId : loan_id
		};
		$.AjaxColynJson("/projectmanage/json/ProjectComFull.json", param, function(data) {
			if (data.success) {
				Colyn.log("满标成功！");
			} else {
				Colyn.log(data.message);
			}
			MyAction.Init();
		});
	},
    
 // 【标的迁移】
	LoanDataTransfer : function() {
		var row = MyGrid.selectRow();
		if (row == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		if(row.loanState!='还款中'){
			Colyn.log("只有还款中的项目，可以进行标的迁移操作");
			return;
		}
		var loan_id = row.loanId;// 项目申请表主键
		var param = {
			loanId : loan_id
		};
		$.AjaxColynJson("/dataMigration/json/LoanDataTransferToBh.json", param, function(data) {
			if (data.success) {
				Colyn.log("迁移成功！");
			} else {
				Colyn.log(data.message);
			}
			MyAction.Init();
		});
	},
	// 【标的迁移结果查询】
	LoanDataTransQuery : function() {
		var row = MyGrid.selectRow();
		if (row == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		if(row.loanState!='还款中'){
			Colyn.log("只有还款中的项目，可以进行标的迁移查询操作");
			return;
		}
		var loan_id = row.loanId;// 项目申请表主键
		var param = {
			loanId : loan_id
		};
		$.AjaxColynJson("/dataMigration/json/DownLoadLoanDataTransfer.json", param, function(data) {
			if (data.success) {
				Colyn.log(data.message);
			} else {
				Colyn.log("标的迁移失败："+data.message);
			}
			MyAction.Init();
		});
	}
};
