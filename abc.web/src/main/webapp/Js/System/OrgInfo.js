
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $('#Del').click(MyAction.Del);
    $("#chkShareHolder").live("click",MyAction.CheckClick);
    $("#LookUp").click(MyAction.LookUp);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});
var MyAction = {
    Init: function () {
        $("#Grid").datagrid({
            url: "/System/OrgInfo",
            height: $(window).height() - 52,
            sortOrder: 'desc',
            pageSize: 20,
            fitColumns:true,
            rownumbers: true,
            animate: true,
            collapsible: false,
            idField: "org_Id",
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
         { field: "org_Name", title: "机构名称", width: 150, align: "center" },
         { field: "dic_Name", title: "机构类别", width: 150, align: "center"},
         { field: "org_Address", title: "地址", width: 150, align: "center" },
         { field: "org_PostCode", title: "邮编", width: 150, align: "center" },
         { field: "org_Telphone", title: "电话", width: 150, align: "center" },
         { field: "org_Fax", title: "传真", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect:true
        })
    },
    Add: function () {
        var addDialog = $.hDialog({
            href: "/System/OrgInfoEditFormView?modelAction=Add",
            iconCls: 'icon-add',
            title: "添加机构信息",
            width: $(window).width()-50,
            height: $(window).height() -50,
            onLoad: function () {
             
            },
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                      var param = createParam2([{formId:"org"}],"Add");
                     $.AjaxColynJson("/System/OrgInfoData",param, function (data) {
                            if (data.Success) {
                                Colyn.log("添加成功！");
                                MyAction.Init();
                            }
                            else {
                                Colyn.log(data.Msg);
                            }
                     });
                     addDialog.dialog("close");
                    }
                }
            }, {
                text: '取消返回',
                iconCls: 'icon-cancel',
                handler: function () {
                    addDialog.dialog("close");
                }
            }]
        })
    },
    Edit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num ==null) {
            Colyn.log("请选择一条数据进行操作");
            return;
        }
        else {
           var editDialog = $.hDialog({
               href: "/System/OrgInfoEditFormView?org_Id=" + row.org_Id,
                width: $(window).width() - 50,
                height: $(window).height() - 50,
                iconCls: 'icon-pencil',
                title: "修改机构信息",
                onLoad: function () {
                        $("#org_IsPublic").val($("#org_IsPublic").attr("_v"))
                        $("#org_IsRunGuar").val($("#org_IsRunGuar").attr("_v"))
                        $("#org_IsLocalOffice").val($("#org_IsLocalOffice").attr("_v"))
                        $("#org_IsNationHold").val($("#org_IsNationHold").attr("_v"))
                        $("#org_IsTransProvince").val($("#org_IsTransProvince").attr("_v"))
                },
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                             var param = createParam2([{ formId: "org" }], "Edit", org_Id);
                             var org_param = JSON.parse(param.org);//把表单的字符串属性转为对象
                             $.extend(org_param, obj); //给对象添加未有的属性(此处主要是obj时间)
                             param.org = JSON.stringify(org_param);//再将对象转为字符串 并重新给表单字符串属性赋值
                             $.AjaxColynJson("/System/OrgInfoData",param, function (data) {
                                if (data.Success) {
                                    Colyn.log("修改成功！");
                                    MyAction.Init();
                                }
                                else {
                                    Colyn.log(data.Msg);
                                }
                            });
                            editDialog.dialog("close");
                        }
                    }},
                {
                    text: '取消返回',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        editDialog.dialog("close");
                    }
                }]
            })
        }
    },
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("提示", "确认删除该条机构信息？", function (r) {
                if (r) {
                    $.AjaxColynJson("/System/DelOrgInfoData?org_Id=" + row.org_Id, function (data) {
                        if (data.Success) {
                            Colyn.log("删除成功");
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.Msg);
                        }
                    });
                }
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    LookUp: function ()
    {
        var row = MyGrid.selectRow();
        if (row) {
            var o = {
                org_Id:row.org_Id
            };
             var lookDialog = $.hDialog({
                href: "/System/OrgInfoLookUpFormView?"+getParam(o),
                // maximizable: true,//显示最大化
                width: $(window).width() - 50,
                height: $(window).height() - 50,
                iconCls: 'icon-zoom',
                title: "机构信息",
                buttons: [{
                    text: '取消返回',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        lookDialog.dialog("close");
                    }
                }]
            })
        }
        else {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
    },
    CheckClick: function () {
        if ($(this).attr("checked")) {
            $("#trShareHolder").slideDown(250);
        } else {
            $("#trShareHolder").slideUp(250);
        }
    }
}

