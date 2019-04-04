/*------------------------------------------------
 * Author:王武  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Release").click(MyAction.Release);
    $("#Export").click(MyAction.Export);
    $("#btnSearch").click(MyAction.Search);
    $("#CancelRelease").click(MyAction.Recall);
    $('#Return').click(MyAction.Return);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    //列表OK 
    Init: function () {
        $("#ProjectReleaseGrid").datagrid({
            method: "Post",
            url: "/projectmanage/json/ProjectReleaseListView.json",
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
                    { field: "loanUserPhone", title: "借款人号码", width: 100, align: "center" },
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
    },
    //发布
    Release: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作！ddd");
            return;
        } else {
            if (row.loanState == '待发布') {
            	/*$.post("/projectmanage/json/ReleaseProject.json?releaseOrNot=release&loanId=" + row.loanId, function (data) {
	                  if (data.Success) {
	                      Colyn.log("发布成功！");
	                      MyAction.Init();
	                  } else {
	                      Colyn.log(data.message);
	                      MyAction.Init();
	                  }
	              }, "json");*/
            	$.AjaxColynJson("/projectmanage/json/ReleaseProject.json?releaseOrNot=release&loanId=" + row.loanId,
						'',
						function(data) {
							if (data.success) {
								Colyn.log("发布成功！");
							} else {
								Colyn.log(data.message);
							}
							MyAction.Init();
						});
//                $.messager.confirm('系统提示', '是否需要修改招标到期日期？(点击取消直接发布)', function (r) {
//                    if (!r) {
//                    	$.post("/projectmanage/json/ReleaseProject.json?releaseOrNot=release&loanId=" + row.loanId, function (data) {
//                            if (data.Success) {
//                                Colyn.log("发布成功！");
//                                MyAction.Init();
//                            } else {
//                                Colyn.log(data.message);
//                                MyAction.Init();
//                            }
//                        }, "json");
//                    } else {//修改日期
//                       var dialogs = $.hDialog({
//                            href: "/projectmanage/TenderMaturity?TypeId=1&LoanId=" + row.loanId + "&ParamId=0",
//                            iconCls: 'icon-add',
//                            title: "修改招标到期日期",
//                            maximizable: true, //显示最大化
//                            width: $(window).width() - 40,
//                            height: $(window).height() - 50,
//                            buttons: [
//                                {
//                                    text: '确认修改',
//                                    iconCls: 'icon-pencil',
//                                    handler: function() {
//                                        var isValid = $("#Colyn").form('validate');
//                                        if (isValid) {
//                                            $.post("/projectmanage/TenderMaturityData?expirationDate=" + $("#pro_invest_endDate").datebox('getValue') + "&loanId=" + row.pro_loan_id, function(data) {
//                                                if (data.Success) {
//                                                    //Colyn.log("修改成功！");
//                                                    var oo = { Release: "0", pro_loan_id: row.pro_loan_id };
//                                                    $.post("/projectmanage/ProjectReleaseOrCanceloResult?" + getParam(oo), function (datas) {
//                                                        if (datas.Success) {
//                                                            Colyn.log("发布成功！");
//                                                            MyAction.Init();
//                                                        } else {
//                                                            Colyn.log(datas.message);
//                                                        }
//                                                    }, "json");
//                                                    MyAction.Init();
//                                                    dialogs.dialog("close");
//                                                } else {
//                                                    Colyn.log(data.message);
//                                                }
//                                            }, "json");
//                                        }
//                                    }
//                                }, {
//                                    text: '关闭',
//                                    iconCls: 'icon-cancel',
//                                    handler: function () {
//                                        dialogs.dialog("close");
//                                    }
//                                }]
//                        });
//                    }
//                })

            } else {
                Colyn.log("已发布，不可再次发布！");
                MyAction.Init();
            }
        }
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
            href: "/projectmanage/LoanProjectLookUpView.htm?loanId=" + row.loanId + "&loanFileUrl=" + row.loanFileUrl,
            iconCls: 'icon-add',
            title: "项目信息查看",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        Dialog.dialog("close");
                    }
                }
            ]
        })
    },
    //导出
    Export: function () {
        if ($('#ProjectReleaseGrid').datagrid('getRows').length > 0) {
            var param = createParam3("SearchForm");
            window.location.href = '/projectmanage/ExportBussinessExamList?' + getParam(param);
        }
        else {
            Colyn.log("暂无数据");
        }
    },
    //取消发布
    Recall: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        } else {
            if (row.loanState == "招标中") {
                $.post("/projectmanage/json/ReleaseProject.json?releaseOrNot=cancelRelease&loanId=" + row.loanId, function (data) {
                    if (data.Success) {
                        Colyn.log("已撤回");
                        MyAction.Init();
                    } else {
                        Colyn.log(data.message);
                        MyAction.Init();
                    }
                }, "json");
            } else {
                Colyn.log("未发布，不可撤回！");
                MyAction.Init();
            }
        }
    },
    Return: function () {
        var row = MyGrid.selectRow();
        if (row == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        
       if(row.loanState == "招标中"){
    	   Colyn.log("招标中的项目不能退回");
           return;
       };
        var param = {loanId : row.loanId};
        $.post("/projectmanage/json/ProjectReleaseReturn.json?", param, function(data) {
            if (data.success) {
                Colyn.log("退回成功！")
            } else {
                Colyn.log(data.message);
            }
            MyAction.Init();
        }, "json");
    },
    //搜索OK
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/projectmanage/json/ProjectReleaseListView.json?" + getParam(o), param, function (data) {
            $("#ProjectReleaseGrid").datagrid("loadData", data);
        }, "json");
    }
};
