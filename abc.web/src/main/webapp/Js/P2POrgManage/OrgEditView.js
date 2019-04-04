/*------------------------------------------------
 * Author:徐大龙  Date：2014年10月16日14:58:47 
-------------------------------------------------*/

$(function () {
    MyArea.Init('act_bank_area_noA', 'act_bank_area_noB', 'hdfact_bank_area_no');//初始化地区
    $("#act_bank_area_noA").change(function () { MyArea.AreaChange('act_bank_area_noA', 'act_bank_area_noB'); });
    //初始化担保机构信息
    var names = $("#gov_guar_name").val();
    var ids = $("#gov_guar_id").val();
    var namearr = names.split(',');
    var idarr = ids.split(',');
    var NamesHtml = "";
    for (var i = 0; i < namearr.length - 1; i++) {
        NamesHtml += '<span>' + namearr[i] + '</span><img src="../../Images/icon/16/bullet_cross.png" _name="' + namearr[i] + '" _id="' + idarr[i] + '" onclick="DelRow(this)"/>';
    }
    $("#show_names").html(NamesHtml);
    //下拉列表绑定
    var gov_scale = $("#gov_scale");
    gov_scale.val(gov_scale.attr("_select"));
    var gov_card_type = $("#gov_card_type");
    gov_card_type.val(gov_card_type.attr("_select"));
    var p1 = $(this).children('option:selected').val();
    var cardA = $("#cardA");//证件号码
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
    $('#file_upload').uploadify({
        'swf': '/Js/Plug/uploadify/uploadify.swf',  //FLash文件路径
        'buttonText': '浏  览',                                 //按钮文本
        'uploader': '../../Ashx/JsonHelper.ashx?action=upload',                       //处理文件上传Action
        'queueID': 'fileQueue',                        //队列的ID
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
            ShowUpFiles($("#Attachment_GUID").val(), "div_files");  //完成后更新已上传的文件列表
            $.messager.alert("提示", "上传完毕！");                                     //提示完成           
        },
        'onUploadStart': function (file) {
            $("#file_upload").uploadify("settings", 'formData', { 'folder': '机构Logo', 'guid': $("#Attachment_GUID").val() }); //动态传参数
        },
        'onUploadError': function (event, queueId, fileObj, errorObj) {
            //alert(errorObj.type + "：" + errorObj.info);
        },
        onUploadSuccess: function (file, data, response) {
            $('#' + file.id).find('.data').html(' 上传完毕');
            $("#showImg").attr("src", "/upload/" + CurrentDate(data) + "/" + file.name);
            $("#gov_logo").val("/upload/" + CurrentDate(data) + "/" + file.name);
            imgObj.push({ file_Name: file.name, file_Path: "/upload/" + CurrentDate() + "/" + file.name });
        },
        onClearQueue: function (event, data) {
            $("#showImg").attr("src", "/Images/NoImg.png");
            $("#gov_logo").val("");
        },
        //返回一个错误，选择文件的时候触发  
        'onSelectError': function (file, errorCode, errorMsg) {
            switch (errorCode) {
                case -100:
                    alert("上传的文件数量已经超出系统限制的" + $('#file_upload').uploadify('settings', 'queueSizeLimit') + "个文件！");
                    break;
                case -110:
                    alert("文件 [" + file.name + "] 大小超出系统限制的" + $('#file_upload').uploadify('settings', 'fileSizeLimit') + "大小！");
                    break;
                case -120:
                    alert("文件 [" + file.name + "] 大小异常！");
                    break;
                case -130:
                    alert("文件 [" + file.name + "] 类型不正确！");
                    break;
            }
            return false;
        }

    });

    $("#editOrgBtn").click(function () {
        if ($('#Colyn').form('validate')) {
            var gov_id = $("#gov_id").val();
            var guarIds = $("#gov_guar_id").val();
            var obj = {
                dataChange: JSON.stringify($saveMainArr),
                govId: gov_id,
                guarIds: guarIds,
                errorMsg: ""
            }
            // obj = getParam(obj);

            var url = "/P2POrgManage/OrgChangeData";
            obj = { "dataChange": JSON.stringify(obj) };
            getAjaxDataChange(url, obj);
        }
    })
});


function DelRow(arr) {
    var $this = $(arr);
    var names = $("#gov_guar_name");
    var ids = $("#gov_guar_id");
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

$("#gov_card_type").live("change", function () {
    var p1 = $(this).children('option:selected').val();
    var cardA = $("#cardA");//证件号码
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