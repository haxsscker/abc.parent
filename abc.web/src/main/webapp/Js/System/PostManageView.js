
$(function () {
    var Dialog;
    MyGrid.Resize();
    MyAction.Init();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#DataAuth").click(MyAction.DataAuth);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});
var MyAction = {
    Init: function () {
        $("#Grid").treegrid({
            url: "/System/PostManageView",
            idField: "dut_Id",
            treeField: "dut_Name",
            height: $(window).height() - 52,
            pagination: false,
            nowrap: false,
            rownumbers: true,
            singleSelect: true,
            animate: true,
            collapsible: false,
            frozenColumns: [[
                   { title: '职务名称', field: 'dut_Name', width: 320 }
            ]],
            columns: [[
                { title: '排序', field: 'dut_Sort', align: 'center', width: 80 },
                { title: '备注', field: 'dut_Note', align: 'center', width: 360 }
            ]]
        })
    },
    Add: function () {
        Dialog = $.hDialog({
            href: "/System/PostAddFormView",
            iconCls: 'icon-add',
            title: "添加职务信息",
            width: 480,
            height: 280,
            onLoad: function () {
                MyAction.InitCtrl();
                MyAction.ComBox(0);
                var row = MyGrid.selectRow();
                if (row) {
                    $('#dut_ParentId').combotree('setValue', row.dut_Id);
                }
            },
            submit: function () {
                var orgId = $('#org_Id').combotree('getValue');
                if (orgId == 0 || orgId == undefined) {
                    Colyn.log("请选择机构！");
                    return false;
                };
                if ($('#Colyn').form('validate')) {
                    var param = createParam2([{ formId: 'duty' }], "edit");
                    $.AjaxColynJson('/System/PostFormData', param, function (data) {
                        if (data.Success) {
                            Colyn.log("添加成功！");
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.Msg);
                        }
                        Dialog.dialog('close');
                    });
                }
            }
        })
    },
    Edit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条数据进行操作");
            return;
        }
        else {
            Dialog = $.hDialog({
                href: "/System/PostAddFormView?dut_Id=" + row.dut_Id,
                width: 480,
                height: 300,
                iconCls: 'icon-edit',
                title: "职务信息修改",
                onLoad: function () {
                    MyAction.InitCtrl();
                    MyAction.ComBox(num.org_Id);
                    var row = MyGrid.selectRow();
                    if (row) {
                        $('#dut_ParentId').combotree('setValue', row.dut_ParentId);
                    }
                },
                submit: function () {
                    var orgId = $('#org_Id').combotree('getValue');
                    if (orgId == 0 || orgId == undefined) {
                        Colyn.log("请选择机构！");
                        return false;
                    };
                    if ($('#Colyn').form('validate')) {
                        var param = createParam2([{ formId: 'duty' }], "edit", dut_Id);
                        $.AjaxColynJson('/System/PostFormData', param, function (data) {
                            if (data.Success) {
                                Colyn.log("修改成功！");
                                MyAction.Init();
                            }
                            else {
                                Colyn.log(data.Msg);
                            }
                            Dialog.dialog('close');
                        });
                    }
                }
            })
        }
    },
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            if ($('#Grid').treegrid('getChildren', row.dut_Id).length > 0) {
                Colyn.log("该职务拥有子职务,不能删除");
                return false;
            }
            $.messager.confirm("提示", "确认删除该条职务信息？", function (r) {
                if (r) {
                    $.AjaxColynJson('/System/DelPost', { dut_Id: row.dut_Id }, function (data) {
                        if (data.Success) {
                            Colyn.log(data.Msg);
                            MyAction.Init();
                            jQuery('#Grid').datagrid('clearSelections')
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
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        else {
            Dialog = $.hDialog({
                href: "/System/OrgInfoLookUpFormView",
                // maximizable: true,//显示最大化
                width: $(window).width() - 50,
                height: $(window).height() - 50,
                iconCls: 'icon-zoom',
                title: "职务信息",
                onLoad: function () {
                    $(".BtnGoBack").click(function () {
                        Dialog.dialog("close");
                    });
                },
                submit: function () {

                }
            })
        }
    },
    InitCtrl: function (MenuId) {
        var TreeData = $("#Grid").treegrid("getData");
        TreeData = JSON.stringify(TreeData).replace(/dut_Id/g, 'id').replace(/dut_Name/g, 'text');
        if (TreeData.length > 2) {
            TreeData = '[{"id":0,"selected":true,"text":"请选择上级职务"},' + TreeData.substr(1, TreeData.length - 1);
        } else {
            TreeData = '[{"id":0,"selected":true,"text":"请选择上级职务"}]';
        };

        $('#dut_ParentId').combotree({
            data: JSON.parse(TreeData),
            valueField: 'dut_Id',
            textField: 'dut_Name',
            panelWidth: '180',
            editable: false,
            lines: true,
            multiple: false,
            onSelect: function (item) {
                var NodeId = $('#dut_ParentId').combotree('getValue');
                if (item.id == MenuId) {
                    $('#dut_ParentId').combotree('setValue', NodeId);
                    Colyn.log("上下级职务不能相同");
                }
                $("#rDutName").val(NodeId);
            }
        }).combotree('setValue', 0);

    },
    ComBox: function (org_ID) {
      $('#org_Id').combobox({
          url: "/System/GetOrgSelect",
            valueField: 'id',
            textField: 'text',
            editable:false,
            panelHeight: 'auto'
       });
        if(org_ID!=0 )
            $('#org_Id').combobox("setValue", org_ID);
    },
    DataAuth: function () {
        //数据授权
        var row = MyGrid.selectRow();
        if (row) {
            var r = $.hDialog({
                title: '数据权限授权：' + row.dut_Name,
                max: true,
                content: '<div class="treeCheckbox" style="padding:2px;overflow:hidden;"><table id="setBtn"></table></div>',
                toolbar: [
               { text: '全选', iconCls: 'icon-checkbox_yes', handler: function () { MyAction.ActionChecked("chkAll") } },
               { text: '反选', iconCls: 'icon-checkbox_yes', handler: function () { MyAction.ActionChecked("cheReverse") } },
               { text: '取消全选', iconCls: 'icon-checkbox_no', handler: function () { MyAction.ActionChecked("cheUnAll") } },
               '-',
               { text: '全选第一列', iconCls: 'icon-checkbox_yes', handler: function () { MyAction.ActionChecked("cheAllFirst") } },
               { text: '全选第二列', iconCls: 'icon-checkbox_yes', handler: function () { MyAction.ActionChecked("cheAllSecond") } }
                ],
                submit: function () {
                    var data = MyAction.SubmitGetAllBtn(row.dut_Id);
                    $.AjaxColynJson('/System/SetDutyBtns?duty_Id=' + row.dut_Id, { data: data }, function (data) {
                        if (data.success) {
                            Colyn.log("添加成功！");
                        }
                        else {
                            Colyn.log("添加失败！");
                        }
                        r.dialog('close');
                    });
                }
            });
            var setMenu = $("#setBtn").treegrid({
                url: "/System/GetPostMenuData",
                title: '菜单列表',
                height: r.dialog("options").height - 115,
                idField: "men_Id",
                treeField: "men_Name",
                queryParams: { dutyId: row.dut_Id },
                iconCls: "icon-nav",
                nowrap: false,
                rownumbers: true,
                animate: true,
                collapsible: false,
                frozenColumns: [[{ title: '菜单名称', field: 'men_Name', width: 200 }]],
                columns: [[
                    {
                        field: 'dutyBtn', title: '同级', width: 100, align: "center",
                        formatter: function (value, rec, rowIndex) {
                            if (value.sLevel) {
                                return "<input type=\"checkbox\" _type=\"2\"  value=\"" + rec.men_Id + "\" _menu_Id=\"" + rec.men_Id + "\"  checked=\"true\" >";
                            }
                            else
                                return "<input type=\"checkbox\"  _type=\"2\"   value=\"" + rec.men_Id + "\"  _menu_Id=\"" + rec.men_Id + "\"   >";
                        }
                      }
                          , {
                              field: 'dutyBtn2', title: '下级', width: 100, align: "center",
                        formatter: function (value, rec, rowIndex) {
                            if (rec.dutyBtn.lLevel) {
                                return "<input type=\"checkbox\"  _type=\"1\"   value=\"" + rec.men_Id + "\" _menu_Id=\"" + rec.men_Id + "\"  checked=\"true\" >";
                            }
                            else
                                return "<input type=\"checkbox\"  _type=\"1\"   value=\"" + rec.men_Id + "\"  _menu_Id=\"" + rec.men_Id + "\"   >";
                        }}
                    
                ]]
            });
        } else {
            Colyn.log("请选择用户进行设置！");
        }
    },
    SubmitGetAllBtn: function (dutyId) {
        var obj = [];
        $(".treeCheckbox input:checkbox").each(function (i, v) {
            var $this = $(this);
            if ($this.attr("checked")) {
                var n = { men_Id: $this.attr("_menu_Id"), dbt_OperateType: $this.attr("_type"),dut_Id:dutyId };
                obj.push(n);
            }           
        });
        return JSON.stringify(obj);
    },
    ConvertToSel: function (v) {
        switch (v) {
            case "0":
                return "<font color=\"#CCC\">无</font>";
            case "1":
                return "<font color=\"#39CB00\">同级</font>";
            case "2":
                return "<font color=\"#9932CC\">下级</font>";
            case "3":
                return "<font color=\"#FF4500\">同级|下级</font>";
            default:
                return "x";
        }
    },
    ActionChecked: function (action) {
        switch (action) {
            case "chkAll":
                $(".treeCheckbox input:checkbox").attr("checked", true);
                break;
            case "cheUnAll":
                $(".treeCheckbox input:checkbox").attr("checked", false);
                break;
            case "cheReverse":
                $(".treeCheckbox input:checkbox").each(function () {
                    $(this).attr("checked", !$(this).attr("checked"));
                });
                break;
            case "cheAllFirst":
                var cellClass = $("#setBtn").datagrid("getColumnOption", "dutyBtn").cellClass;
                $("." + cellClass + " input:checkbox").attr("checked", true);
                break;
            case "cheAllSecond":
                var cellClass = $("#setBtn").datagrid("getColumnOption", "dutyBtn2").cellClass;
                $("." + cellClass + " input:checkbox").attr("checked", true);
                break;          
        }
    }
}

var lastIndex = 0;