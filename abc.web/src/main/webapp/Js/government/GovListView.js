/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-20 
 -------------------------------------------------*/
//机构维护菜单下的列表页面
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#Enable").click(MyAction.Enable);
    $("#LockStop").click(MyAction.LockStop);
    $("#InitPassword").click(MyAction.InitPassword);
    $("#btnSearch").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
    MyArea.Init('org_area_noA', 'org_area_noB', 'hdorg_area_no');//初始化地区
    $("#org_area_noA").change(function () {
        MyArea.AreaChange('org_area_noA', 'org_area_noB');
    });
    //添加界面的附件管理
    ///FileUpload/Upload


});
var MyAction = {
    Init: function () {
        var param = createParam3("SearchForm");
        $("#OrgMaintainListGrid").datagrid({
            method: "GET",
            url: "/government/json/getGovList.json?" + param,
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            idField: "govId",
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [
                [
                    { field: "govUserName", title: "用户名", width: 100, align: "center" },
                    { field: "govName", title: "机构名称", width: 150, align: "center" },
                    { field: "govNo", title: "组织机构代码", width: 100, align: "center" },
                    {
                        field: "govTotalCapital", title: "资产总额", width: 100, align: "center", formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }
                    },
                    //{
                    //    field: "govMaxLoanAmount", title: "最大借款额度", width: 100, align: "center", formatter: function (value, rowData, index) {
                    //    return formatMoney(value, '￥');
                    //}
                    //},
                    {
                        field: "govSettGuarAmount", title: "可用担保额度", width: 100, align: "center", formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }
                    },
                    {
                        field: "govMaxGuarAmount", title: "最大担保额度", width: 100, align: "center", formatter: function (value, rowData, index) {
                        return formatMoney(value, '￥');
                    }
                    },
                  //  {
                  //      field: "govGuarName", title: "担保机构", width: 150, align: "center", formatter: function (value) {
                  //      if (value == "" || value == null) {
                  //          return "-";
                  //      } else {
                  //          return value;
                  //      }
                  //  }
                  // },
                    //{
                    //   field: "govIsOfferGuar", title: "是否提供担保", width: 80, align: "center", formatter: function (value) {
                    //    if (value == "1") {
                    //        return "是";
                    //    } else {
                    //        return "否";
                    //    }
                    //}
                    //},
                    {
                        field: "govContact", title: "联系人", width: 100, align: "center", formatter: function (value) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        }
                    }
                    },
                    {
                        field: "govContactPhone", title: "联系电话", width: 100, align: "center", formatter: function (value) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        }
                    }
                    },
                    {
                        field: "govEmail", title: "公司邮箱", width: 150, align: "center", formatter: function (value) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        }
                    }
                    },
                    {
                        field: "govCustomerManagerName", title: "客户经理", width: 80, align: "center", formatter: function (value) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        }
                    }
                    },
                    {
                        field: "govArea", title: "所属地区", width: 100, align: "center", formatter: function (value) {
                        if (value == "" || value == null) {
                            return "-";
                        } else {
                            return value;
                        }
                    }
                    },
                    {
                        field: "govIsEnable", title: "启用状态", width: 80, align: "center", formatter: function (value) {
                        if (value == "1") {
                            return "已启用";
                        } else if (value == "0") {
                            return "已停用";
                        } else {
                            return "-";
                        }
                    }
                    }
                ]
            ],
            pagination: true,
            singleSelect: true
        });
        var p = $('#OrgMaintainListGrid').datagrid('getPager');
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
        var o = { modelAction: 'Add' };
        var addDialog = $.hDialog({
            href: "/government/govAddOrEditView?" + getParam(o),
            iconCls: 'icon-add',
            title: "添加机构信息",
            // maximizable: true,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            onLoad: function () {
                upload();
                MyArea.Init('act_bank_area_noA', 'act_bank_area_noB', 'hdfact_bank_area_no');//初始化地区
                $("#act_bank_area_noA").change(function () {
                    MyArea.AreaChange('act_bank_area_noA', 'act_bank_area_noB');
                });
                MyValid.Init();
            },
            buttons: [
                {
                    text: '确认添加',
                    iconCls: 'icon-ok',
                    handler: function () {
                        $('#govLogo').validatebox('disableValidation');
                        if ($('#Colyn').form('validate')) {
                            var gov_id = $("#govId").val();
                            //var guarIds = $("#govGuarId").val();
                            var param = createParam2([
                                { formId: "org" }
                            ], "Add", gov_id);
                            param.org = param.org.replace("true", "1");
                            param.org = param.org.replace('false', '0');
                            //var param1 = {
                            //    guarIds: guarIds
                            //}
                            //param = $.extend(param, param1);
                            $.AjaxColynText("/government/json/addGov.json", param, function (data) {
                                var data = JSON.parse(data);
                                if (data.success) {
                                    Colyn.log("添加成功！");
                                    MyAction.Init();
                                    addDialog.dialog("close");
                                }
                                else {
                                    Colyn.log(data.message);
                                    if (data.message != "所选担保机构不足以提供最大借款做担保") {
                                        addDialog.dialog("close");
                                    }
                                }
                            });
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
            var o = { govId: row.govId, modelAction: 'Edit' };
            var editDialog = $.hDialog({
                href: "/government/govAddOrEditView?" + getParam(o),
                // maximizable: true,//显示最大化
                width: $(window).width() - 40,
                height: $(window).height() - 50,
                iconCls: 'icon-pencil',
                title: "修改机构信息",
                onLoad: function () {
                    //加载上传控件
                    upload();
                    //初始化地区信息
                    MyArea.Init('act_bank_area_noA', 'act_bank_area_noB', 'hdfact_bank_area_no');//初始化地区
                    $("#act_bank_area_noA").change(function () {
                        MyArea.AreaChange('act_bank_area_noA', 'act_bank_area_noB');
                    });
                    //初始化担保机构信息
                    //var names = $("#govGuarName").val();
                    //var ids = $("#govGuarId").val();
                    //var namearr = names.split(',');
                    //var idarr = ids.split(',');
                    //var NamesHtml = "";
                    //for (var i = 0; i < namearr.length - 1; i++) {
                    //    NamesHtml += '<span>' + namearr[i] + '</span><img src="../../Images/icon/16/bullet_cross.png" _name="' + namearr[i] + '" _id="' + idarr[i] + '" onclick="DelRow(this)"/>';
                    //}
                    //$("#show_names").html(NamesHtml);
                    //下拉列表绑定
                    var gov_scale = $("#govScale");
                    gov_scale.val(gov_scale.attr("_select"));
                    var gov_card_type = $("#govDocType");
                    gov_card_type.val(gov_card_type.attr("_select"));
                    //绑定证件号
                    var p1 = $("#govDocType").children('option:selected').val();
                    var cardA = $("#govDocNo");//证件号码
                    //请选择时： 证件号码不能使用 并清空值
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
                    MyValid.Init();
                },
                buttons: [
                    {
                        text: '确认修改',
                        iconCls: 'icon-pencil',
                        handler: function () {
                            $('#govLogo').validatebox('disableValidation');
                            if ($('#Colyn').form('validate')) {
                                var govId = $("#govId").val();
                                //var guarIds = $("#govGuarId").val();
                                var param = createParam2([
                                    { formId: "org" }
                                ], "Edit", govId);
                                param.org = param.org.replace("true", "1");
                                param.org = param.org.replace('false', '0');
                                param = $.extend(param, { govId: govId });
                                //param = $.extend(param, { guarIds: guarIds });
                                $.AjaxColynText("/government/json/editGov.json", param, function (data) {
                                    var data = JSON.parse(data);
                                    if (data.success) {
                                        Colyn.log(data.message);
                                        MyAction.Init();
                                    }
                                    else {
                                        Colyn.log(data.message);
                                    }
                                });
                                editDialog.dialog("close");
                            }
                        }
                    },
                    {
                        text: '关闭',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            editDialog.dialog("close");
                        }
                    }
                ]
            })
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
        var o = { govId: row.govId }
        var Dialog = $.hDialog({
            href: "/government/govLookUpView?" + getParam(o),
            iconCls: 'icon-add',
            title: "机构信息查看",
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
    //删除
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r) {
                    $.AjaxColynText('/government/json/deleteGov.json', { govId: row.govId }, function (data) {
                        var data = JSON.parse(data);
                        if (data.success) {
                            Colyn.log("删除成功");
                            MyAction.Init();
                            jQuery('#OrgMaintainListGrid').datagrid('clearSelections')
                        }
                        else {
                            Colyn.log(data.message);
                        }
                    });
                }
            })
        }
        else {
            Colyn.log("请选择内容删除！");
        }
    },
    //启用
    Enable: function () {
        var row = MyGrid.selectRow();
        if (row) {
            if (row.govIsEnable != "1") {
                $.messager.confirm("启用", "确定要启用该机构？", function (r) {
                    if (r) {
                        $.AjaxColynText('/government/json/enableGov.json', { govId: row.govId}, function (data) {
                            var data = JSON.parse(data);
                            if (data.success) {
                                Colyn.log("启用成功");
                                MyAction.Init();
                                jQuery('#OrgMaintainListGrid').datagrid('clearSelections')
                            }
                            else {
                                Colyn.log(data.message);
                            }
                        });
                    }
                })
            } else {
                Colyn.log("已启用，不可重复操作");
            }
        }
        else {
            Colyn.log("请选择需要启用的机构信息！");
        }
    },
    LockStop: function () {
        var row = MyGrid.selectRow();
        if (row) {
            if (row.govIsEnable != "0") {
                $.messager.confirm("停用", "确定要停用该机构？", function (r) {
                    if (r) {
                        $.AjaxColynText('/government/json/disableGov.json', { govId: row.govId}, function (data) {
                            var data = JSON.parse(data);
                            if (data.success) {
                                Colyn.log("停用成功")
                                MyAction.Init();
                                jQuery('#OrgMaintainListGrid').datagrid('clearSelections')
                            }
                            else {
                                Colyn.log(data.message);
                            }
                        });
                    }
                })
            } else {
                Colyn.log("已停用，不可重复操作");
            }
        }
        else {
            Colyn.log("请选择需要停用的机构信息！");
        }
    },
    //初始化密码InitPassword
    InitPassword: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("初始化密码", "确定要初始化该机构密码？", function (r) {
                if (r) {
                    $.AjaxColynText('/government/json/initPassword.json?', { govId: row.govId }, function (data) {
                        var data = JSON.parse(data);
                        if (data.success) {
                            Colyn.log(data.message + " 初始密码为：" + data.data);
                            MyAction.Init();
                            jQuery('#OrgMaintainListGrid').datagrid('clearSelections')
                        }
                        else {
                            Colyn.log(data.message);
                        }
                    });
                }
            })
        }
        else {
            Colyn.log("请选择需要初始化密码的机构信息！");
        }
    },
    //搜索
    Search: function () {
        if ($('#SearchForm').form('validate')) {
            var param = createParam3("SearchForm");
            var o = { modelAction: "Search" };
            $.post("/government/json/GetGovList.json?" + getParam(o), param, function (data) {
                $("#OrgMaintainListGrid").datagrid("loadData", data);
                $("#OrgMaintainListGrid").datagrid("clearSelections");
            }, "json");
        }
    }
}
function DelRow(arr) {
    var $this = $(arr);
    var names = $("#govGuarName");
    var ids = $("#govGuarId");
    //删除隐藏域对应的值
    var _name = $this.attr("_name") + ',';
    var _id = $this.attr("_id") + ',';
    names.val(names.val().replace(_name, ''));
    ids.val(ids.val().replace(_id, ''));
    //删除显示节点
    $this.prev().remove();
    $this.remove();
}
$("#isOffer").live("change", function () {
    var p1 = $(this).children('option:selected').val();
    var $lend = $("#gov_max_lend_money");//最大借款额度
    var $guar = $("#gov_max_guar_money");//最大担保额度
    //担保公司
    if (p1 == "1") {
        //最大借款额度 不能使用 并清空值 非必填项
        $lend.val("");
        $lend.validatebox({
            required: false
        })
        $lend.attr("disabled", "disabled");
        //最大担保额度 可以使用 并位必填项
        $guar.val("");
        $guar.removeAttr("disabled");
        $guar.validatebox({
            required: true,
            validType: ['Money']
        })
    } else {//信贷机构
        //最大借款额度 可以使用 必填项 清空值
        $lend.val("");
        $lend.removeAttr("disabled");
        $lend.validatebox({
            required: true,
            validType: ['Money']
        })
        //最大担保额度 不可使用 非必填 清空值
        $guar.val("");
        $guar.validatebox({
            required: false
        })
        $guar.attr("disabled", "disabled");
    }
});

$("#govDocType").live("change", function () {
    var p1 = $(this).children('option:selected').val();
    var cardA = $("#govDocNo");//证件号码
    cardA.val("");
    //请选择时： 证件号码不能使用 并清空值
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

$("#govIsOfferGuar").live("change", function () {
    var p1 = $(this).children('option:selected').val();
    var govMaxGuarAmount = $("#govMaxGuarAmount");
    var govMaxLoanAmount = $("#govMaxLoanAmount");
    if (p1 == 0) {//不提供担保，即为小贷
        govMaxGuarAmount.validatebox({
            required: false
        });
        govMaxGuarAmount.attr("disabled", "disabled");
        govMaxLoanAmount.removeAttr("disabled");
        govMaxLoanAmount.validatebox({
            required: true,
            validType: ['Money']
        });
    } else if (p1 == 1) {
        govMaxGuarAmount.removeAttr("disabled");
        govMaxGuarAmount.validatebox({
            required: true,
            validType: ['Money']
        });
        govMaxLoanAmount.validatebox({
            required: false
        });
        govMaxLoanAmount.attr("disabled", "disabled");
    }
});

function upload() {
    $('#file_upload').uploadify({
        'swf': '/Js/Plug/uploadify/uploadify.swf',  //FLash文件路径
        'buttonText': '浏  览',                                 //按钮文本
//        'uploader': '../../Ashx/JsonHelper.ashx?action=upload',                       //处理文件上传Action
        'uploader': '/common/json/uploadFile.json',
        'queueID': 'fileQueue',                        //队列的ID
        'queueSizeLimit': 1,                          //队列最多可上传文件数量，默认为999
        'overrideEvents': ['onSelectError', 'onDialogClose'],           //取消默认提示
        'auto': false,                                 //选择文件后是否自动上传，默认为true
        'multi': false,                                 //是否为多选，默认为true
        'removeCompleted': true,                       //是否完成后移除序列，默认为true
        'fileSizeLimit': '10MB',                       //单个文件大小，0为无限制，可接受KB,MB,GB等单位的字符串值
        'fileTypeDesc': '请上传图片',                 //文件描述
        'fileTypeExts': '*.gif; *.jpg; *.png;*.bmp;*.tif;',  //上传的文件后缀过滤器
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
            data = JSON.parse(data);
            $("#showImg").attr("src", data.data);
            $("#govLogo").val(data.data);
//            $("#showImg").attr("src", "/upload/" + CurrentDate(data) + "/" + file.name);
//            $("#govLogo").val("/upload/" + CurrentDate(data) + "/" + file.name);
            //imgObj.push({ file_Name: file.name, file_Path: "/upload/" + CurrentDate() + "/" + file.name });
        },
        onClearQueue: function (event, data) {
            $("#showImg").attr("src", "/Images/NoImg.png");
            $("#govLogo").val("");
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
