/*------------------------------------------------
 * Author:潘健  Date：2014-8-27
 * Editor:徐大龙 Date：2014-10-21
 -------------------------------------------------*/
    var btns;
$(function () {
    MyGrid.Resize();
    MyAction.GetButtonS();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#Enable").click(MyAction.Enable);
    $("#LockStop").click(MyAction.LockStop);
    $("#LookUp").click(MyAction.LookUp);
    $("#InitPassword").click(MyAction.InitPassword);
    $("#Authorization").click(MyAction.SetBtn);
    MyAction.Init();
    $("#Search").click(MyAction.Search);
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
        $("#AdminMaintainListGrid").datagrid({
            method: "POST",
            url: "/employee/json/getEmployeeList.json",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            idField: "empId",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [
                [
                    { field: "empName", title: "用户名称", width: 150, align: "center" },
                    { field: "empRealName", title: "真实姓名", width: 150, align: "center" },
                    //{ field: "empRole", title: "用户角色", width: 150, align: "center" },
                    { field: "empMobile", title: "手机号码", width: 150, align: "center", formatter: function (value) {
                        if (value == null) {
                            return "-";
                        } else {
                            return value;
                        }
                    }
                    },
                    { field: "empState", title: "状态", width: 150, align: "center", formatter: function (value, row, index) {
                        if (value == "1") {
                            return "启用";
                        } else {
                            return "停用";
                        }
                    }
                    }
                ]
            ],
            pagination: true,
            singleSelect: true
        })
        var p = $('#AdminMaintainListGrid').datagrid('getPager');
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
    //添加
    Add: function () {
        var o = { modelAction: 'Add', lod: "1" };
        var addDialog = $.hDialog({
            href: "/employee/employeeAddOrEditView?" + getParam(o),
            iconCls: 'icon-add',
            title: "管理员添加",
            // maximizable: true,//显示最大化
            width: $(window).width() - 50,
            height: $(window).height() - 50,
            onLoad: function () {
                //initAllRole();
                uploadImg();
                MyArea.Init('act_bank_area_noA', 'act_bank_area_noB', 'hdfact_bank_area_no');//初始化地区
                $("#act_bank_area_noA").change(function () {
                    MyArea.AreaChange('act_bank_area_noA', 'act_bank_area_noB');
                });
            },
            buttons: [
                {
                    text: '确认添加',
                    iconCls: 'icon-ok',
                    handler: function () {
                        $('#empHeadImg').validatebox('disableValidation');
                        if ($('#Colyn').form('validate')) {
                            var param = $('#Colyn').serializeArray();
                            var empRole = "";
                            //if ($("input:checked[name=roles]:first").val() == undefined) {
                            //    Colyn.log("请选择至少一个角色");
                            //    return;
                            //}
                            $("input:checked[name=roles]").each(function () {
                                empRole += $(this).val() + ",";
                            });
                            var param1 = { empRole: empRole };
                            $.post("/employee/json/addEmployee.json?" + getParam(param1), param, function (data) {
                                console.log(data);
                                if (data.success) {
                                    Colyn.log("添加成功！");
                                    MyAction.Init();
                                } else {
                                    Colyn.log(data.message);
                                }
                            }, "json");
                            addDialog.dialog("close");
                        }
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        addDialog.dialog("close");
                    }
                }
            ]
        })
    },
    //编辑
    Edit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        else {
            var o = { empId: row.empId, flag: "edit"};
            var empId = row.empId;
            var editDialog = $.hDialog({
                href: "/employee/employeeAddOrEditView?" + getParam(o),
                width: $(window).width() - 50,
                height: $(window).height() - 50,
                iconCls: 'icon-pencil',
                title: "管理员修改",
                onLoad: function () {
                    initEmpInfo(empId);
                    MyArea.Init('act_bank_area_noA', 'act_bank_area_noB', 'hdfact_bank_area_no');//初始化地区
                    $("#act_bank_area_noA").change(function () {
                        MyArea.AreaChange('act_bank_area_noA', 'act_bank_area_noB');
                    });
                    uploadImg();
                },
                buttons: [
                    {
                        text: '确认修改',
                        iconCls: 'icon-pencil',
                        handler: function () {
                            $('#empHeadImg').validatebox('disableValidation');
                            if ($('#Colyn').form('validate')) {
                                var param = $('#Colyn').serializeArray();
                                var roleIds = "";
                                $("input:checked[name=roles]").each(function () {
                                    roleIds += $(this).val() + ",";
                                });
                                var empId = $("#empId").val();
                                var param1 = { empRole: roleIds, empId: empId};
                                $.post("/employee/json/modifyEmployee.json?" + getParam(param1), param, function (data) {
                                    if (data.success) {
                                        Colyn.log("修改成功！");
                                        MyAction.Init();
                                    } else {
                                        Colyn.log(data.message);
                                    }
                                }, "json");
                                editDialog.dialog("close");
                            }
                        }
                    },
                    {
                        text: '取消返回',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            editDialog.dialog("close");
                        }
                    }
                ]
            })
        }
    },
    //删除
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            if (row.emp_State != "1") {
                $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                    if (r) {
                        $.AjaxColynText('/employee/json/removeEmployeeItem.json', { empId: row.empId }, function (data) {
                            var data = JSON.parse(data);
                            if (data.success) {
                                Colyn.log("删除成功");
                                MyAction.Init();
                                jQuery('#AdminMaintainListGrid').datagrid('clearSelections')
                            }
                            else {
                                Colyn.log(data.message);
                            }
                        });
                    }
                })
            } else {
                Colyn.log("已启用的管理员不能删除！");
            }
        }
        else {
            Colyn.log("请选择管理员删除！");
        }
    },
    //授权
      SetBtn: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var r = $.hDialog({
                title: '授权：' + row.empName,
                max: true,
                content: '<div style="padding:2px;overflow:hidden;"><table id="setBtn"></table></div>',
                submit: function () {
                    $.loading();
                    MyAction.Apply("endEdit");
                    var data = MyAction.SubmitGetAllBtn(row.roleId);
                    $.AjaxColynText('/system/json/authoritySave.json?empId='+ row.empId,{"data":data}, function (data) {
                        $.loaded();
                        Colyn.log("菜单权限设置成功");
                        r.dialog('close');
                    });
                }
            });

            var setMenu = $("#setBtn").treegrid({
                url: "/system/json/getAuthorityMatrix.json?empId=" + row.empId + "&" + Math.random(),
                title: '菜单列表',
                height: r.dialog("options").height - 115,
                idField: "men_Id",
                treeField: "men_Name",
                iconCls: "icon-nav",
                nowrap: false,
                rownumbers: true,
                animate: true,
                collapsible: false,
                frozenColumns: [[{ title: '菜单名称', field: 'men_Name', width: 200 }]],
                columns: [MyAction.GetBtn()],
                onClickRow: function (row) {
                    if (lastIndex != row.men_Id) {
                        setMenu.treegrid('endEdit', lastIndex);
                    }
                    MyAction.Apply("beginEdit", row.men_Id);
                    lastIndex = row.men_Id;
                }
            });
        } else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
      GetBtn: function () {
        Enumerable.from(btns).forEach("o=>o.formatter=function(v,d,i){return MyAction.formatter(v,d,i,o.field);}");
        return btns;
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
    },
    //启用
    Enable: function () {
        var row = MyGrid.selectRow();
        if (row) {
            if (row.empState != "1") {
                $.messager.confirm("启用", "确定要启用？", function (r) {
                    if (r) {
                        var o = { Enable: "1", empId: row.empId }
                        $.post("/employee/json/enableEmployee.json?" + getParam(o), function (data) {
                            if (data.success) {
                                Colyn.log("启用成功");
                                MyAction.Init();
                            } else {
                                Colyn.log("启用失败");
                            }
                        }, "json");
                    }
                })
            } else {
                Colyn.log("已启用，不可重复操作");
            }
        }
        else {
            Colyn.log("请选择需要启用的信息");
        }
    },
    //停用
    LockStop: function () {
        var row = MyGrid.selectRow();
        if (row) {
            if (row.empState != "0") {
                $.messager.confirm("停用", "确定要停用？", function (r) {
                    if (r) {
                        var o = { Enable: "0", empId: row.empId }
                        $.post("/employee/json/disableEmployee.json?" + getParam(o), function (data) {
                            if (data.success) {
                                Colyn.log("停用成功");
                                MyAction.Init();
                            } else {
                                Colyn.log("停用失败");
                            }
                        }, "json");
                    }
                })
            } else {
                Colyn.log("已停用，不可重复操作");
            }
        }
        else {
            Colyn.log("请选择需要停用的信息！");
        }
    },
    //初始化密码InitPassword
    InitPassword: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("初始化密码", "确定要初始化密码？", function (r) {
                if (r) {
                    var o = { Enable: "0", empId: row.empId };
                    $.AjaxColynText('/employee/json/initPassword.json?', getParam(o), function (data) {
                        var data = JSON.parse(data);
                        if (data.success) {
                            Colyn.log(data.message + " 初始密码为：" + data.data);
                        } else {
                            Colyn.log("初始化密码失败");
                        }
                    }, "json");
                }
            })
        }
        else {
            Colyn.log("请选择需要初始化密码的信息");
        }
    },
    //授权
     SetBtn: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var r = $.hDialog({
                title: '授权：' + row.empName,
                max: true,
                content: '<div style="padding:2px;overflow:hidden;"><table id="setBtn"></table></div>',
                submit: function () {
                    $.loading();
                    MyAction.Apply("endEdit");
                    var data = MyAction.SubmitGetAllBtn(row.roleId);
                    $.AjaxColynText('/system/json/authoritySave.json?empId='+ row.empId,{"data":data}, function (data) {
                        $.loaded();
                        Colyn.log("菜单权限设置成功");
                        r.dialog('close');
                    });
                }
            });

            var setMenu = $("#setBtn").treegrid({
                url: "/system/json/getAuthorityMatrix.json?empId=" + row.empId + "&" + Math.random(),
                title: '菜单列表',
                height: r.dialog("options").height - 115,
                idField: "men_Id",
                treeField: "men_Name",
                iconCls: "icon-nav",
                nowrap: false,
                rownumbers: true,
                animate: true,
                collapsible: false,
                frozenColumns: [[{ title: '菜单名称', field: 'men_Name', width: 200 }]],
                columns: [MyAction.GetBtn()],
                onClickRow: function (row) {
                    if (lastIndex != row.men_Id) {
                        setMenu.treegrid('endEdit', lastIndex);
                    }
                    MyAction.Apply("beginEdit", row.men_Id);
                    lastIndex = row.men_Id;
                }
            });
        } else {
            Colyn.log("请选择一条数据进行操作");
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
        var o = { empId: num.empId }
        var Dialog = $.hDialog({
            href: "/employee/employeeLookUpView?" + getParam(o),
            iconCls: 'icon-add',
            title: "管理员信息查看",
            width: $(window).width() - 50,
            height: $(window).height() - 50,
            buttons: [
                {
                    text: '取消返回',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        Dialog.dialog("close");
                    }
                }
            ]
        })
    },
    //搜索
    Search: function () {
		var param = createParam3("SearchForm");
        var o = { modelAction: "Search" };
        $.AjaxColynJson("/employee/json/getEmployeeList.json?"+ getParam(o) ,param, function (data) {
           	$("#AdminMaintainListGrid").datagrid("loadData", data);
            $("#AdminMaintainListGrid").datagrid("clearSelections");
        },JSON);
    }
}

function initAllRole() {
    $.ajax({
        type: "GET",
        url: "/system/json/getAllRole.json",
        async: false,
        success: function (data) {
            var len = data.rows.length;
            var roleDom = "";
            for (var i = 0; i < len; i++) {
                roleDom += "<input type='checkbox' name='roles' id='roles" + data.rows[i].roleId + "' value='" + data.rows[i].roleId + "'/><span>" + data.rows[i].roleName + "</span>";
            }
            $("#roleDiv").html(roleDom);
        }
    });
}

function initEmpInfo(empId) {
    initAllRole();
    var param = { empId: empId};
    $.ajax({
        type: "GET",
        url: "/employee/json/getEmployeeRole.json",
        data: getParam(param),
        dataType: "json",
        async: false,
        success: function (data) {
            var roleIds = data.data.split(",");
            for (var i = 0; i < roleIds.length; ++i) {
                $("#roles" + roleIds[i]).attr("checked", true);
            }
        }
    });
}

function uploadImg() {
    $('#file_upload').uploadify({
        'swf': '/Js/Plug/uploadify/uploadify.swf',  //FLash文件路径
        'buttonText': '浏  览',                                 //按钮文本
//        'uploader': '../../Ashx/JsonHelper.ashx?type=headImg',                       //处理文件上传Action
        'uploader': '/common/json/uploadFile.json',                       //处理文件上传Action
        'queueID': 'imgQueue',                        //队列的ID
        'queueSizeLimit': 1,                          //队列最多可上传文件数量，默认为999
        'overrideEvents': ['onSelectError', 'onDialogClose'],           //取消默认提示
        'auto': false,                                 //选择文件后是否自动上传，默认为true
        'multi': false,                                 //是否为多选，默认为true
        'removeCompleted': true,                       //是否完成后移除序列，默认为true
        'fileSizeLimit': '10MB',                       //单个文件大小，0为无限制，可接受KB,MB,GB等单位的字符串值
        'fileTypeDesc': '请上传图片',                 //文件描述
        'fileTypeExts': '*.gif; *.jpg; *.png;',  //上传的文件后缀过滤器
        //检测FLASH失败调用
        'onFallback': function () {
            Colyn.log("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
        },
        'onQueueComplete': function (event, data) {                 //所有队列完成后事件
            //ShowUpFiles($("#Attachment_GUID").val(), "div_files");  //完成后更新已上传的文件列表
            $.messager.alert("提示", "上传完毕！");                                     //提示完成           
        },
        'onUploadStart': function (file) {
//            $("#file_upload").uploadify("settings", 'formData', { 'folder': '机构Logo', 'guid': $("#Attachment_GUID").val() }); //动态传参数
        },
        'onUploadError': function (event, queueId, fileObj, errorObj) {
            //alert(errorObj.type + "：" + errorObj.info);
        },
        onUploadSuccess: function (file, data, response) {
            $('#' + file.id).find('.data').html(' 上传完毕');
//            $("#showImg").attr("src", "/upload/headImg/" + CurrentDate(data) + "/" + file.name);
//            $("#emp_headImg").val("/upload/headImg/" + CurrentDate(data) + "/" + file.name);
            data = JSON.parse(data);
            var imgSrc = data.data;
            $("#showImg").attr("src", imgSrc);
            $("#empHeadImg").val(imgSrc);
            //imgObj.push({ file_Name: file.name, file_Path: "/upload/headImg/" + CurrentDate() + "/" + file.name });
        },
        onClearQueue: function (event, data) {
            $("#showImg").attr("src", "/Images/NoImg.png");
            $("#empHeadImg").val("");
        },
        //返回一个错误，选择文件的时候触发  
        'onSelectError': function (file, errorCode, errorMsg) {
            switch (errorCode) {
                case -100:
                    Colyn.log("上传的文件数量已经超出系统限制的" + $('#file_upload').uploadify('settings', 'queueSizeLimit') + "个文件！");
                    break;
                case -110:
                    Colyn.log("文件 [" + file.name + "] 大小超出系统限制的" + $('#file_upload').uploadify('settings', 'fileSizeLimit') + "大小！");
                    break;
                case -120:
                    Colyn.log("文件 [" + file.name + "] 大小异常！");
                    break;
                case -130:
                    Colyn.log("文件 [" + file.name + "] 类型不正确！");
                    break;
            }
            return false;
        }

    });
}
var lastIndex = 0;