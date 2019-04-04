/*------------------------------------------------
 * Author:吕小东  Date：2014年9月17日10:44:00
-------------------------------------------------*/
$(function () {
    $("#btnOK").click(MyAction.Edit);//修改
    MyArea.BankLevel("act_bank_level");//首次加载
    MyAction.Init();//初始化

    MyArea.Init('act_bank_area_noA', 'act_bank_area_noB', 'hdfact_bank_area_no');//初始化地区
    $("#act_bank_area_noA").change(function () { MyArea.AreaChange('act_bank_area_noA', 'act_bank_area_noB'); });
});

var MyAction = {
    Init: function () {

        //选中[开户行地区]
        if ($("#hdfact_bank_area_no").val() != "") {
            if ($("#act_bank_area_noB").css("display") != "none") {
                $("#act_bank_area_noB").val($("#hdfact_bank_area_no").val());
            }
        }
        //选中[开户行行别]
        if ($("#hdfact_bank_level").val() != "") {
            $("#act_bank_level").val($("#hdfact_bank_level").val());
        }

        if ($("#act_user_name").val() != "") {
            var tTip = "未做过充值的时候可修改此项";//可修改提示
            $("#btnOK").val("确认修改");
            $("#act_user_name").attr('onfocus', 'this.blur()');      //[客户姓名]不可修改
            $("#act_account_no").attr('onfocus', 'this.blur()');     //[开户账户]不可修改
            $("#act_cash_pwdA").attr('onfocus', 'this.blur()');      //[提现密码]不可修改
            $("#act_cash_pwdB").attr('onfocus', 'this.blur()');      //[确认提现密码]不可修改
            $("#act_login_pwd").attr('onfocus', 'this.blur()');      //[登录密码]不可修改
            $("#act_account_mark").attr('onfocus', 'this.blur()');   //[备注]不可修改
            $("#tdKhhdqdm").attr('title', tTip);                //[开户行地区代码]鼠标悬乎提示
            $("#tdKhhhb").attr('title', tTip);                  //[开户行地区代码]鼠标悬乎提示
            $("#tdKhhzhmc").attr('title', tTip);                //[开户行地区代码]鼠标悬乎提示
            $("#tdKhhyhzh").attr('title', tTip);                //[开户行地区代码]鼠标悬乎提示
        }
    },
    Edit: function () {
        if ($("#hdfIsOk").val() == "0")//是否有操作权限（0：否；1：是）
        {
            Colyn.log("对不起您无权操作此功能！");
            return;
        }
        var tip = $("#btnOK").val();
        tip = tip.substring(2);
        if ($('#Colyn').form('validate')) {
            var param = $('#Colyn').serializeArray();
            param = convertArray(param);
            $.AjaxColynText('/account/json/OpenAccount.json?modelAction=Edit', param, function (data) {
            	 var data = JSON.parse(data);
            	if (data.success) {
                    $("#hdfact_bank_level").val($("#act_bank_level").val())
                    Colyn.log(tip + "成功！");
                    MyAction.Init();
                }
                else {
                    Colyn.log(data.message);
                }
            });
        }

    }
}