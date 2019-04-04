$(function() {
    $('#file_upload').uploadify({
        'swf': '/Js/Plug/uploadify/uploadify.swf', //FLash文件路径
        'buttonText': '浏  览', //按钮文本
//        'uploader': '../../Ashx/JsonHelper.ashx?action=upload', //处理文件上传Action
        'uploader': '/common/json/uploadFile.json?',
        'queueID': 'imgQueue', //队列的ID
        'queueSizeLimit': 1, //队列最多可上传文件数量，默认为999
        'auto': false, //选择文件后是否自动上传，默认为true
        'multi': false, //是否为多选，默认为true
        'removeCompleted': true, //是否完成后移除序列，默认为true
        'fileSizeLimit': '10MB', //单个文件大小，0为无限制，可接受KB,MB,GB等单位的字符串值
        'fileTypeDesc': '请上传图片', //文件描述
        'fileTypeExts': '*.gif; *.jpg; *.png;*.tif;*.bmp;', //上传的文件后缀过滤器
        'width': '80',//按钮的宽度
        'height': '20',//按钮的高度
        //检测FLASH失败调用
        'onFallback': function() {
            Colyn.log("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
        },
        'onQueueComplete': function(event, data) { //所有队列完成后事件
            $.messager.alert("提示", "上传完毕！"); //提示完成           
        },
        'onUploadStart': function(file) {
            //$("#file_upload").uploadify("settings", 'formData', { 'folder': '机构Logo', 'guid': $("#Attachment_GUID").val() }); //动态传参数
        },
        'onUploadError': function(event, queueId, fileObj, errorObj) {
            //alert(errorObj.type + "：" + errorObj.info);
        },
        onUploadSuccess: function(file, data, response) {
            $('#' + file.id).find('.data').html(' 上传完毕');
            data = JSON.parse(data);
            $("#showImg").attr("src", data.data);
            $("#scScorePic").val(data.data);
//            $("#showImg").attr("src", "/upload/" + CurrentDate(data) + "/" + file.name);
//            $("#sco_score_pic").val("/upload/" + CurrentDate(data) + "/" + file.name);
        },
        onClearQueue: function(event, data) {
            //$("#showImg").attr("src", "");
            // $("#pro_loan_logo").val("");
        },
        'onSelectError': function (file, errorCode, errorMsg) {
            switch (errorCode) {
                case -130:
                    alert("文件 [" + file.name + "] 类型不正确！");
                    break;
            }
            return false;
        },
        onUploadCancel: function(fileId) {
        	
            // $("#showImg").attr("src", "");
            // $("#pro_loan_logo").val("");
        }

    });
});


function ScoreManageAdd() {
    var param = $("#Colyn").serializeArray();
    if ($("#Colyn").form("validate")) {
        $.post("/P2PScoreManage/ScoreManageAddOrEditData", param, function (data) {
            if (data.Success) {
                Colyn.log("添加成功！");
                MyAction.Init();
                Dialog.dialog('close');
            } else {
                Colyn(data.Msg);
            }
        }, "json");
    }
}