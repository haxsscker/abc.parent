/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-22 10:02:25
-------------------------------------------------*/
$(function () {
    var Dialog;
    MyGrid.Resize();
    $("#EditPass").click(MyAction.EditPass);
    $("#edit").click(MyAction.Edit);//修改时显示的页面
    $("#subm").click(MyAction.SubmitData);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });

});

var MyAction = {
    RefreshPanl: function () {
        MyGrid.Resize();
        $('#layout').layout('resize');
    },
    Edit: function () {
        window.location.href = "/DBPerInfo/PersonInfoFormView?MenuID=" + getUrl("MenuID");
    },
    EditPass: function () {
        var empId = $("#empId").val();
//        var oldPwd = { empId: empId, oldPwd: $("#oldPass").val() };
        var o2 = { empId: empId, password: $("#empPassword").val(), oldPwd: $("#oldPass").val() };
//        $.post("/P2POrgManage/ValidOldPass", oldPwd, function (data) {
//            if (data.Success) {
                if ($('#Colyn').form('validate')) {
                    $.post("/employee/json/updatePassword.json", o2, function (data) {
                        if (data.success) {
                            Colyn.log("修改成功！");
                            $("#oldPass").val("");
                            $("#newPass").val("");
                            $("#empPassword").val("");
                        }
                        else {
                            Colyn.log(data.message);
                        }
                    })
                }
//            } else {
//                Colyn.log(" 输入的旧密码不正确！");
//            }
//        })

    },
    SubmitData: function () {
        if ($("#Colyn").form('validate')) {
            var param = $('#Colyn').serializeArray();
            param = convertArray(param);
            $.AjaxColynText('/DBPerInfo/PersonInfoData?empId=' + $("#empId").val(), param, function (data) {
                if (data == "ok") {
                    Colyn.log("修改成功！");
                }
                else {
                    Colyn.log("修改失败！");
                }
                addDialog.dialog('close');
            });
        }
    }

}