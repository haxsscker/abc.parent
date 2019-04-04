/*------------------------------------------------
 * Author:吕小东  Date：2014年9月28日14:52:59
-------------------------------------------------*/
$(function () {
    $("#returnMoneyBtn").click(MyAction.Return);//确认退费
    MyAction.Load();
});

var MyAction = {
    Load: function () {
        $("#txt_rep_back_emp").attr('onfocus', 'this.blur()');  //收款人
        $("#rep_back_bank").attr('onfocus', 'this.blur()');     //收款人账号
        $("#rep_user_phone").attr('onfocus', 'this.blur()');    //手机号码
    },
    Init: function () {
        $("#txt_rep_back_emp").val("");//收款人【输入框】
        $("#hdf_rep_back_emp").val("0");//收款人【隐藏域】
        $("#rep_back_bank").val("");//收款人账号
        $("#rep_user_phone").val("");//手机号码
        $("#rep_return_amount").val("");//退回金额
        $("#rep_reject_reason").val("");//退回原因
    },
    Return: function () {
        if ($('#Colyn').form('validate')) {
            var param = $('#Colyn').serializeArray();
            param = convertArray(param);
            $.AjaxColynJson('/moneyManage/json/RefundApply.json?modelAction=Edit', param, function (data) {
                if (data.success) {
                    Colyn.log(data.message);
                    MyAction.Init();
                }
                else {
                    Colyn.log(data.Msg);
                    MyAction.Init();
                }
            });
        }

    }
}