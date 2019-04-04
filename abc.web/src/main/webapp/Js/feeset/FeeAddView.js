$(function () {
    $("#selFeeType").live("change", function () {
        if ($(this).val() == "2") {
            $("#feeRate").text("收费比例(%)：");
            $('#txtRate').validatebox({
                validType: ['IntOrFloat']
            });
            $("#txtRate").data().validatebox.options.missingMessage =  "请输入收费比例！";
        } else {
            $("#feeRate").text("收费金额(元)：");
            $('#txtRate').validatebox({
                validType: ['Money']
            });
            $("#txtRate").data().validatebox.options.missingMessage = "请输入收费金额";
        }
         $("#txtRate").validatebox('validate');
    });
   

});