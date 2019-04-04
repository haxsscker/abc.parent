/*------------------------------------------------
 * Author:Colyn-邓敬雨  Date：2014-12-11
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    MyAction.GetButtonS();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#LookUp").click(MyAction.LookUp);
    $("#SearchHide").click(MyAction.Search);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    GetButtonS: function(){
        $.get("/bank/json/getButtonList.json",function(data){
            btns=data;
        });
    },
    Init: function () {
        $("#MonBankInfo").datagrid({
            url: "/bank/json/GetMonBankInfoList.json?"+createParam3("SearchForm"),
            idField: "monBankId",
            height: $(window).height() - 52,
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            animate: true,
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
				{ title: '基金名称', field: 'funFundName', width: 300, align: 'center' },
				{ title: '银行名称', field: 'monBankName', width: 300, align: 'center'},
				{ title: '银行卡号', field: 'monBankCard', width: 300, align: 'center'},
				{ title: '账户户名', field: 'monUserNamer', width: 300, align: 'center'},
            ]]
        });

        var p = $('#MonBankInfo').datagrid('getPager');
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
            href: "/bank/addMonBankInfo.vm",
            iconCls: 'icon-add',
            title: "银行账户添加",
            maximizable: true,//显示最大化
            width: $(window).width() - 700,//$(window).width() - 50,
            height: $(window).height() - 350,
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = createParam2([{ formId: 'bank', isAnyRow: true }]);
                        $.AjaxColynJson("/bank/json/AddMonBankInfo.json", param, function (data) {
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
        
        var editDialog1 = $.hDialog({
            href: "/bank/editMonBankInfo?cuiId =" + row.monBankId,
            iconCls: 'icon-edit',
            title: "银行信息修改",
            width: $(window).width() - 700,//$(window).width() - 50,
            height: $(window).height() - 350,
            buttons: [{
                text: '确认修改',
                iconCls: 'icon-edit',
                handler: function () {
                    //var faFundNo = $("#faFundNo").val();
                    if ($('#Colyn').form('validate')) {
                        var param = createParam2([{ formId: 'bank', isAnyRow: true }]);
                        console.log(param);
                        $.post("/bank/json/EditMonBankInfo.json?cuiId =" + row.monBankId, param, function (data) {
                            if (data.success) {
                                Colyn.log("修改成功！");
                                MyAction.Init();
                                editDialog1.dialog("close");
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
                	editDialog1.dialog("close");
                }
            }]
        });
    	
    },
    Del: function () {
    	var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r) {
                    $.AjaxColynJson("/bank/json/DelMonBankInfo.json?cuiId =" + row.monBankId, function (data) {
                        if (data.success) {
                            Colyn.log("删除成功！");
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.Msg);
                        }
                    });
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
        //var o = { cpiId: row.rnpId };
        var Dialog = $.hDialog({
            href: "/bank/LookUpMonBankInfo?cuiId =" + row.monBankId,
            iconCls: 'icon-add',
            title: "银行信息查看",
            maximizable: true,//显示最大化
            width: $(window).width() - 700,
            height: $(window).height() - 350,
            buttons: [{
                text: '关闭',
                   iconCls: 'icon-cancel',
                   handler: function () {
                       Dialog.dialog("close");
                   }
               }]
        })
    },
    
    Search: function () {
        var param = createParam3("SearchForm");
        $.AjaxColynJson("/bank/json/GetMonBankInfoList.json?" ,param, function (data) {
            $("#MonBankInfo").datagrid("loadData", data);
            },JSON);
    },
    
    
    formatter: function (v, d, i, field) {
        if (v) {
            if (v == '√')
                return '<font color=\"#39CB00\"><b>' + v + '</b></font>';
            else return '<font color=\"#ff0000\">' + v + '</font>';
        } else {
            return Enumerable.from(d.hasbtns).any("n=>n=='" + field + "'") ? "<font color=\"#39CB00\"><b>√</b></font>" : "<font color=\"#ff0000\">x</font>";
        }
    },
    Apply: function (action, index) {
        if (!index) {
            $("#setBtn").treegrid('selectAll');
        }
        var rows = $("#setBtn").treegrid('getSelections');        
        $.each(rows, function (i, n) {
            $("#setBtn").treegrid(action, this.men_Id);
            if (action == "beginEdit") {
                var editors = $('#setBtn').treegrid('getEditors', n.men_Id);
                Enumerable.from(btns).forEach(function (x, z) {
                    var hasbtn = Enumerable.from(n.Buttons).any('$=="' + x.field + '"');
                    Enumerable.from(editors).forEach(function (b) {
                        if (!hasbtn && b.field == x.field)
                            $(b.target).remove();
                    });
                });
            }            
        });
        if (action != "beginEdit") {
            $('#setBtn').treegrid('clearSelections');
        }
    },
    ActionChecked: function (flag) {
        var rows = $("#setBtn").treegrid('getSelections');
        if (rows) {
            $.each(rows, function (i, n) {
                var editors = $("#setBtn").treegrid('getEditors', n.men_Id);
                $.each(editors, function () {
                    if (!$(this.target).is(":hidden"))
                        $(this.target).attr("checked",flag);
                });
            });
        } else {
            Colyn.log("请选择菜单！");
        }
    },
    SubmitGetAllBtn: function (RoleID) {
        var rows = $("#setBtn").treegrid("getChildren");
        var arr = { Btns: [] };
        Enumerable.from(rows).forEach(function (s) {
            var n = { MenuId: s.men_Id, buttons: [] };
            n.buttons = Enumerable.from(s).where('t=>t.value=="√"').select('$.key').toArray();
            if (n.buttons.length > 0) {
                arr.Btns.push(n);
            }
        });
        return JSON.stringify(arr);
    }
}
var lastIndex = 0;
