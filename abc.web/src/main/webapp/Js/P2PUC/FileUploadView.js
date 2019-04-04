//var imgObj = [];
//var num = 0;
var isLoad = false;
var classId = $("#classId").val();
var secondClassId=$("#secondClassId").val();
var dataId = $("#dataId").val();
var loanId = $("#loanId").val();
var isUpload = $("#isUpload").val();

function gradeChange(){
	secondClassId=$("#secondClassId").val();
	InitTable();
	//closeLoad();
	 $('#uploadify').uploadify('cancel', '*');
	 imgObj = [];
}


function InitTable() {
    $("#fileList").datagrid({
        url: "/common/json/GetFileList.json?fileUploadClassType=" + $("#classId").val() + "&fileUploadSecondaryClass=" + $("#secondClassId").val() + "&dataId=" + $("#dataId").val()+ "&loanId=" + $("#loanId").val(),
        pageSize: 10,
        fitColumns: true,
        rownumbers: true,
        nowrap: false,
        striped: true,
        idField: "file_Id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
        remoteSort: true,
        //  height: $(window).height() - 300,
        view: myview,//重写当没有数据时
        emptyMsg: '没有找到数据',//返回数据字符
        columns: [
            [
                {
                    field: "file_Id", hidden: true
                },
                {
                    field: "file_Name", title: "附件名称 ", width: 150, align: "center"
                },
                {
                    field: "file_CreateTime", title: "上传时间", width: 100, align: "center"
                },
                {
                    field: "op", title: "操作", width: 150, align: "center", formatter: function (value, row, index) {
                    //if ($("#isUpload").val() == "False") {
                        //return "<a href='#' onclick =\"DownLoadFile('" + row.file_Path + "','" + row.file_Name + "')\">预览</a>";
                    //}
                    return "<a href='javascript:;' onclick =\"DownLoadFile('" + row.file_Path + "','" + row.file_Name + 
                    	"')\">预览</a> |<a  href='javascript:;' onclick =\"DownLoadFile2('" + row.file_Path + "','" + row.file_Name + 
                    	"')\">下载</a>|  <a  onclick =\"DeleteFile('" + row.file_Id + "')\">删除</a>";
                }
                }
            ]
        ],
        pagination: false,
        singleSelect: true
    });

    $('#fileList').datagrid({
        onLoadSuccess: function (data) {
            if (!isLoad) {
                if (data.total == 0) {
                    $('#fileList').datagrid({ height: 200 });
                }
            }
            isLoad = true;
        }
    });
}

function doUplaod() {
	var queue = $("#fileQueue").html();
	if(queue.length==0){
		Colyn.log("请先选择文件后再上传");
		return;
	}
    $('#uploadify').uploadify('upload', '*');
}

function closeLoad() {
	var queue = $("#fileQueue").html();
	if(queue.length==0){
		Colyn.log("没有可以取消的文件");
		return;
	}
    $('#uploadify').uploadify('cancel', '*');
    imgObj = [];
}

//预览？
function DownLoadFile(filePath, fileName) {
	var curWwwPath=window.document.location.href;
	var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var path = curWwwPath.substring(0,pos);
    console.log(path+filePath);
    window.open(path+filePath);
}

//下载
function DownLoadFile2(filePath, fileName) {
	window.location = '/common/json/downloadFile.json?path='+filePath+'&fileName='+fileName;
}

function DeleteFile(fileId) {
    $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
        if (r) {
            $.AjaxColynJson('/common/json/DeleteImageData.json?fileId=' + fileId, function (data) {
                Colyn.log(data.message);
                InitTable();
                $("#fileList").datagrid("clearSelections");
            });
        }
    });
}


InitTable();
//if ($("#isUpload").val() == "False") {
    //$("#uploadTr").addClass("hideClass");
//}
function uploadFile() {
	setTimeout(function(){
$('#uploadify').uploadify({
    'swf': '/Js/Plug/uploadify/uploadify.swf',
    'buttonText': '选择文件',
    //上传文件的大小限制
    'fileSizeLimit': '10MB',
    'overrideEvents': ['onSelectError', 'onDialogClose'],           //取消默认提示
    //上传数量
    'queueSizeLimit': 15,
//        'uploader': '/Ashx/JsonHelper.ashx?action=upload',
    'uploader': '/common/json/uploadImageData.json',
    //'formData': {fileUploadClassType: classId, fileUploadSecondaryClass: $("#secondClassId").val(), dataId: dataId},
    'cancelImg': '/Js/Plug/img/uploadify-cancel.png',
//        'folder': 'upload',
    'queueID': 'fileQueue',
    'auto': false,
    'multi': false,
    'onUploadStart': function (file) {  
        $("#uploadify").uploadify("settings", "formData", { fileUploadClassType: $("#classId").val(), fileUploadSecondaryClass: $("#secondClassId").val(), dataId: $("#dataId").val(),loanId: $("#loanId").val() });  
        //在onUploadStart事件中，也就是上传之前，把参数写好传递到后台。  
    } ,
    //检测FLASH失败调用
    'onFallback': function () {
        Colyn.log("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
    },
    //返回一个错误，选择文件的时候触发
    'onSelectError': function (file, errorCode, errorMsg) {
    	console.log(errorCode);
        switch (errorCode) {
            case -100:
                Colyn.log("上传的文件数量已经超出系统限制的" + $('#file_upload').uploadify('settings', 'queueSizeLimit') + "个文件！");
                break;
            case -110:
                Colyn.log("文件 [" + file.name + "] 大小超出系统限制的" + $('#file_upload').uploadify('settings', 'fileSizeLimit') + "大小！");
                break;
            case -120:
            	 Colyn.log("文件 [" + file.name + "]为空文件");
                 break;
        }
    },
    'onUploadSuccess': function (file, data, response) {
        $('#' + file.id).find('.data').html(' 上传完毕');
        InitTable();
    },
    'onQueueComplete': function (uploadsSuccessful, uploadsErrored) {
        Colyn.log("上传成功！");
    },
    'onError': function (event, queueID, fileObj) {
        Colyn.log("上传失败！");
    }
});
},10);
}

function uploadImg() {
	setTimeout(function(){
        $('#file_upload').uploadify({
            'swf': '/Js/Plug/uploadify/uploadify.swf',  //FLash文件路径
            'buttonText': '浏  览',                                 //按钮文本
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
                //ShowUpFiles($("#Attachment_GUID").val(), "div_imgs");  //完成后更新已上传的文件列表
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
                data = JSON.parse(data);
                var imgSrc = data.data;
                $("#showImg").attr("src", imgSrc);
                $("#loanLogo").val(imgSrc);
            },
            onClearQueue: function (event, data) {
                $("#showImg").attr("src", "/Images/NoImg.png");
                $("#pro_loan_logo").val("");
                $("#pro_loan_logo").attr("_value", "");
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
        },10);
    }
$(window).resize(function () {
    MyGrid.RefreshPanl();
});
