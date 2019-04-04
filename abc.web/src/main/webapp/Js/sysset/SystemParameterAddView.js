$(function () {
    $("#btnUpdate").click(MyAction.AddOrEdeit);
    $("#btnUpdateEmail").click(MyAction.AddOrEdeitEmail);
})

var MyAction= {
    AddOrEdeit: function() {
        if ($('#Colyn').form('validate')) {
            var param = $('#Colyn').serializeArray();
            param = convertArray(param);
            $.AjaxColynJson("/sysset/json/systemParametersData.json", param, function (data) {
                if (data.success) {
                    //提示成功
                    Colyn.log(data.message);
                }
                else {
                    Colyn.log(data.message);
                }
            });
        }
    },
    AddOrEdeitEmail: function () {
        if ($('#Colyn').form('validate')) {
            var param = $('#Colyn').serializeArray();
            param = convertArray(param);
            $.AjaxColynJson("/sysset/json/EmailInfoAddOrEditData.json", param, function (data) {
                if (data.success) {
                    //提示成功
                    Colyn.log(data.message);
                }
                else {
                    Colyn.log(data.message);
                }
            });
        }
    }
}


$('.chkTurnNumber').live('click', function () {
    if ($(".chkTurnNumber").attr("checked")) {
        $("#divInput").addClass("hideClass");
        $("#sys_transfer_count").val("");
    }
    else {
        $("#divInput").removeClass("hideClass");

    }
});