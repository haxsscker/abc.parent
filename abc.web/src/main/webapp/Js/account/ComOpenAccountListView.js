/*------------------------------------------------
 * Author:吕小东  Date：2014年10月30日14:26:03
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#Search").click(MyAction.Search);//【搜索】
    $("#LookUp").click(MyAction.LookUp);//【查看】
    $("#Add").click(MyAction.Add);//【添加】
    $("#Edit").click(MyAction.Edit);//【修改】
    $("#Del").click(MyAction.Del);//【删除】
    MyAction.Init();//页面初始化
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    //页面初始化
    Init: function () {
    	var param = createParam3("SearchForm");
        $("#LoanComAccountListGrid").datagrid({
            method: "POST",//请求远程数据的方法类型。
            url: "/account/json/ComAccountManage.json?t=" + new Date() +"&"+ param,//一个用以从远程站点请求数据的超链接地址。
            height: $(window).height() - 52,
           // pageSize: 10,//当设置分页属性时，初始化每页记录数。
            fitColumns: false,//设置为true将自动使列适应表格宽度以防止出现水平滚动。
            rownumbers: true,//设置为true将显示行数。
            nowrap: false,//设置为true，当数据长度超出列宽时将会自动截取。
            striped: false,//设置为true将交替显示行背景。
            idField: "act_account_id",  //表明该列是一个唯一列。【账户开户表主键】
            remoteSort: true,//定义是否通过远程服务器对数据排序。
            view: myview,//定义数据表格的视图。【重写当没有数据时】
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            {
                field: "gov_name", title: "机构名称", width: 220, align: "center"
                	, formatter: function (value, rowData, index) {
                    return CutString(value, 24);
                }
            },
            {
            	field: "act_business_licence", title: "营业执照号", width: 120, align: "center"
            },
            {
            	field: "act_user_name", title: "机构平台号", width: 120, align: "center"
            },
            {
                field: "act_legal_name", title: "法人姓名", width: 120, align: "center"
//                	, formatter: function (value, rowData, index) {
//                    return CutString(value, 9);
//                }
            },
            {field: "act_user_card", title: "法人身份证号", width: 150, align: "center"},
            { field: "act_user_phone", title: "手机号码", width: 150, align: "center" },
            { field: "act_user_email", title: "邮箱", width: 150, align: "center" },
            { field: "act_platform_no", title: "第三方开户号", width: 150, align: "center" },
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#LoanComAccountListGrid').datagrid('getPager');
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
    //【搜索】
    Search: function () {
        if ($('#SearchForm').form('validate')) {
            var param = createParam3("SearchForm");
            console.log(param);
            $.post("/account/json/comAccountManage.json", param, function (data) {
                $("#LoanComAccountListGrid").datagrid("loadData", data);
                $("#LoanComAccountListGrid").datagrid("clearSelections");
            }, "json");
        }
    },
    //【查看】
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var account_id = row.act_account_id;//账户开户表ID
        var gov_id = row.gov_id;            //机构信息表ID

        var Dialog = $.hDialog({
            href: "/account/orgOpenAccountLookView?account_id=" + account_id + "&gov_id=" + gov_id + "&t=" + new Date(),
            iconCls: 'icon-add',
            title: "贷款到期",
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
    //【添加】
    Add: function () {
        var o = { modelAction: 'Add'};
        var addDialog = $.hDialog({
            href: "/account/orgOpenAccountView?" + getParam(o),
            iconCls: 'icon-add',
            title: "添加账户信息",
            // maximizable: true,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            onLoad: function () {
                MyArea.MyBankLevel("act_bank_level");//开户行行别
                MyArea.Init('act_bank_area_noA', 'act_bank_area_noB', 'hdfact_bank_area_no');//初始化地区
                $("#act_bank_area_noA").change(function () { MyArea.AreaChange('act_bank_area_noA', 'act_bank_area_noB'); });
            },
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        $.AjaxColynText("/account/json/AddOrgAccount.json?modelAction=Add", param, function (data) {
                            var data = JSON.parse(data);
                            if (data.success) {
                                Colyn.log("添加成功！");
                                MyAction.Init();
                                addDialog.dialog("close");
                            }
                            else {
                                Colyn.log(data.message);
                            }
                        });
                    }
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    addDialog.dialog("close");
                }
            }]
        })
    },
    //【修改】
    Edit: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var o = { modelAction: 'Edit', account_id: row.act_account_id, gov_id: row.gov_id };
            var addDialog = $.hDialog({
                href: "/account/ModifyOrgAccountView?" + getParam(o),
                iconCls: 'icon-add',
                title: "修改账户信息",
                // maximizable: true,//显示最大化
                width: $(window).width() - 40,
                height: $(window).height() - 50,
                onLoad: function () {
                    MyArea.MyBankLevel("act_bank_level");//开户行行别
                    MyArea.Init('act_bank_area_noA', 'act_bank_area_noB', 'hdfact_bank_area_no');//初始化地区
                    $("#act_bank_area_noA").change(function () { MyArea.AreaChange('act_bank_area_noA', 'act_bank_area_noB'); });
                    $("#act_bank_level").val($("#hdfact_bank_level").val());

                    if ($("#isAllowEdit").val() == "0")//允许编辑权限（1:允许编辑所有；0：允许编辑部分）
                    {
                        $("#spanguar_name").hide();                              //隐藏机构选择图片
                        $("#act_account_no").attr('onfocus', 'this.blur()');     //[开户账户]不可修改
                    }
                    else {
                        $("#spanguar_name").show();                              //隐藏机构选择图片
                        $("#act_account_no").removeattr('onfocus');     //[开户账户]不可修改
                    }
                },
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-ok',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                            if ($("#act_cash_pwdA").val() != "" && $("#act_cash_pwdB").val() != "")
                            {
                                if ($("#act_cash_pwdA").val() != $("#act_cash_pwdB").val())
                                {
                                    Colyn.log("[确认提现密码]与[提现密码]不一致！");
                                    return;
                                }
                            }

                            var account_id = $("#act_account_no").val();
                            var gov_id = $("#act_user_id").val();

                            var param = $('#Colyn').serializeArray();
                            param = convertArray(param);
                            $.AjaxColynText("/account/json/ModifyOrgAccount.json?modelAction=Edit&account_id=" + account_id + "&gov_id=" + gov_id + "&t=" + new Date(), param, function (data) {
                                var data = JSON.parse(data);
                                if (data.success) {
                                    Colyn.log("修改成功！");
                                    MyAction.Init();
                                    addDialog.dialog("close");
                                }
                                else {
                                    Colyn.log(data.message);
                                }
                            });
                        }
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        addDialog.dialog("close");
                    }
                }]
            })
        }
        else {
            Colyn.log("请选择一条数据！");
        }
    },
    //删除
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r == true) {
                    var temp = { accountId: row.act_account_id, govId: row.gov_id };
                    $.AjaxColynText('/account/json/DeleteAccount.json', temp, function (data) {
                        var data = JSON.parse(data);
                        if (data.success) {
                            Colyn.log("删除成功");
                            MyAction.Init();
                            jQuery('#LoanComAccountListGrid').datagrid('clearSelections')
                        }
                        else {
                            Colyn.log("删除失败");
                        }
                    });
                }
            })
        }
        else {
            Colyn.log("请选择一条数据！");
        }
    }
}