/*------------------------------------------------
 * Author:Colyn-王永恒  Date：2014-12-11
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    MyAction.GetButtonS();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#LookUp").click(MyAction.LookUp);
    $("#Release").click(MyAction.Release);
    $("#CancelRelease").click(MyAction.CancelRelease);
    $("#Authorization").click(MyAction.SetBtn);
    $("#SearchHide").click(MyAction.Search);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    GetButtonS: function(){
        $.get("/system/json/getButtonList.json",function(data){
            btns=data;
        });
    },
    Init: function () {
    	var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $("#FundApplyGrid").datagrid({
        	method:"POST",
            url: "/fund/json/GetFundApplyList.json?modelAction=Search&"+param,
            idField: "foOrderId",
            height: $(window).height() - 52,
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            animate: true,
            columns: [[
				{ title: '项目名称', field: 'faFundNo', width: 100 },
				{ title: '基金名称', field: 'faFundName', width: 130, align: 'center'},
				{ title: '预计发行规模（万）', field: 'faFundMoney', width: 130, align: 'center'},
				{ title: '存续期（个月）', field: 'faFundPeriod', width: 100, align: 'center'},
				{ title: '预期年化收益率（%）', field: 'faFundRate', width: 130, align: 'center'},
				{ title: '最低认购金额（万）', field: 'faMinMoney', width: 130, align: 'center'},
				{ title: '投资行业', field: 'faFundIndustry', width: 100, align: 'center'},
				{ title: '申请日期', field: 'faAddDate', width: 100, align: 'center'},
				{title: '发布状态', field: 'faFundState', align: 'center', width: 100}
            ]]
        });

        var p = $('#FundApplyGrid').datagrid('getPager');
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
    Add: function () {
    	
    	var p = {
            btnToAdd: 1
        };
        Dialog = $.hDialog({

            href: "/fund/fundApplyAddView",
            iconCls: 'icon-add',
            title: "有限合伙添加",
            maximizable: true,//显示最大化
            width: $(window).width() - 40,//$(window).width() - 50,
            height: $(window).height() - 50,
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    
                    if ($('#Colyn').form('validate')) {
                        var param = createParam2([{ formId: 'main', relaClass: "main" }, { formId: 'fund_profit', isAnyRow: true }]);
                        $.AjaxColynJson("/fund/json/AddFundApply.json", param, function (data) {
                            if (data.success) {
                                Colyn.log("添加成功！");
                                MyAction.Init();
                                Dialog.dialog("close");
                            }
                            else {
                                Colyn.log(data.Msg);

                            }
                        }, "json");
                       
                    }
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    Dialog.dialog("close");
                }
            }]
        });
    	
    },
    Edit: function () {
    	var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (row.faFundState == '已发布') {
            Colyn.log("该数据已发布,不可进行修改操作");
            return;
        }
        var editDialog = $.hDialog({
            href: "/fund/fundApplyEditView?faFundId=" + row.faFundId,
            iconCls: 'icon-edit',
            title: "有限合伙修改",
            width: $(window).width() - 40,//$(window).width() - 50,
            height: $(window).height() - 50,
            buttons: [{
                text: '确认修改',
                iconCls: 'icon-edit',
                handler: function () {
                    //var faFundNo = $("#faFundNo").val();
                    if ($('#Colyn').form('validate')) {
                        var param = createParam2([{ formId: 'main', relaClass: "main" }, { formId: 'fund_profit', isAnyRow: true }]);
                        console.log(param);
                        $.post("/fund/json/EditFundApply.json", param, function (data) {
                            if (data.success) {
                                Colyn.log("修改成功！");
                                MyAction.Init();
                                editDialog.dialog("close");
                            } else {
                                Colyn.log(data.Msg);
                            }
                        }, "json");
                    }
                }
            },
            {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                	editDialog.dialog("close");
                }
            }]
        });
    	
    },
    Del: function () {
    	var row = MyGrid.selectRow();
        if (row) {
            var numb = row.faFundId;
            var num = row.faFundState;
            if (num=='已发布') {
                Colyn.log("该数据已发布，不可删除！");
                return;
            }
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r) {
                    $.AjaxColynJson("/fund/json/DelFundApply.json",{ id: numb }, function (data) {
                        if (data.success) {
                            Colyn.log("删除成功！");
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.Msg);
                        }
                    });
                    jQuery('#LoanGrid').datagrid('clearSelections');
                }
            });
        }
        else {
            Colyn.log("请选择内容删除！");
        }
    },
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/fund/fundApplyLookUpView?faFundId=" + row.faFundId,
            iconCls: 'icon-add',
            title: "有限合伙查看",
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
    Release: function () {
        var row = MyGrid.selectRow();
        if(row){
        	var numb = row.faFundId;
            var num = row.faFundState;
            if (num=='已发布') {
                Colyn.log("该数据已发布，不可再次发布！");
                return;
            }
            $.AjaxColynJson("/fund/json/ReleaseFundApply.json", {id:numb, releaseOrCancel:1}, function (data) {
                if (data.success) {
                    Colyn.log("发布成功！");
                    MyAction.Init();
                }
                else {
                    Colyn.log(data.Msg);
                }
            });
        }else{
        	Colyn.log("请选择一条记录进行操作");
        }
    },
    CancelRelease: function () {
        var row = MyGrid.selectRow();
        if(row){
        	var numb = row.faFundId;
            var num = row.faFundState;
            if (num=='未发布') {
                Colyn.log("该数据未发布，不可取消发布！");
                return;
            }
            $.AjaxColynJson("/fund/json/ReleaseFundApply.json", {id:numb, releaseOrCancel:0}, function (data) {
                if (data.success) {
                    Colyn.log("取消发布成功！");
                    MyAction.Init();
                }
                else {
                    Colyn.log(data.Msg);
                }
            });
        }else{
        	Colyn.log("请选择一条记录进行操作");
        }
    },
    Search: function () {
        var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.post("/fund/json/GetFundApplyList.json?t=" + new Date() + "&" + getParam(o), param, function (data) {
            $("#FundApplyGrid").datagrid("loadData", data);
        }, "json");
        
    }
    
};
var lastIndex = 0;
