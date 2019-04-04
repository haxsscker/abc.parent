/*------------------------------------------------
 * Author:吕小东  Date：2014年10月27日16:23:55 
   功能模块：强制还款子页面
-------------------------------------------------*/

var timerD;

var MyActionD = {
    /*还款（还款页面）*/
    PubOpeRepayment: function (varTemp) {
    	var row = MyGrid.selectRow();
		var num = row;
		if (num == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		var plan_id = row.pro_plan_id;// 还款计划表主键
		var loan_id = row.pro_loan_id;// 项目申请表主键
		var payMoney = row.pro_pay_money;
		var payRate = row.pro_pay_rate;
		var serviceFee = row.pro_service_fee
        var Dialog = $.hDialog({
            href: "/loanpay/repaymentView?loanId=" + loan_id+"&planId="+plan_id,
            iconCls: 'icon-add',
            title: "强制还款",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            onLoad: function () {
            	$("#collect_money").val(payMoney);
            	$("#collect_rate").val(payRate);
            	$("#collect_service_fee").val(serviceFee);
                //获取验证码
                //MyActionD.PubGetCheckCode();
                //获取手机验证码[事件移除]
                //$("#btnCheckCodeQZ").die('click', MyActionD.PubGetCheckCode);
                //按钮绑定获取验证码事件
                //$("#btnCheckCodeQZ").live('click', MyActionD.PubGetCheckCode);
                $("#hdfOpeRepayment").val(varTemp);
            },
            onBeforeClose: function () {
                MyActionD.resetAccessCode();
            },
            buttons: [{
                text: '确认还款',
                iconCls: 'icon-add',
                handler: function () {
                    //确认还款
                    MyActionD.PubConfirmPayment(Dialog);
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    MyActionD.resetAccessCode();
                    Dialog.dialog("close");
                }
            }]
        })
    },
    /*重新获取验证码（还款计划页面）*/
    PubGetCheckCode: function () {
        $("#imgCode").attr("src", "/P2PLoanPayManage/ValidateCode?type=3&t=" + new Date());
        MyActionD.PubGetMbpVfcCodeAfter();
    },
    /*确认还款（还款计划页面）*/
    PubConfirmPayment: function (Dialog) {
    	var row = MyGrid.selectRow();
		var num = row;
		if (num == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		var plan_id = row.pro_plan_id;// 还款计划表主键
		var loan_id = row.pro_loan_id;// 项目申请表主键

 //       var vTemp = $("#hdfOpeRepayment").val();
        var check_code = $("#txtCheckCode").val();//验证码

        if (check_code == "")//验证码必填
        {
            Colyn.log("请输入验证码！");
            return;
        }

//        vTemp = vTemp.split('|');
//        if (vTemp != "") {
//            vTemp += "&check_code=" + check_code;
            $.AjaxColynJson('/loanpay/json/RepaymentConfirmPaymentData.json?type=Compel&loanId=' + loan_id+'&planId='+plan_id+"&securityCode="+check_code, "", function (data) {
                if (data.success) {
                    Colyn.log(data.message);
                    Dialog.dialog("close");
                    MyAction.Init();
                }
                else {
                    Colyn.log(data.message);
                    $("#cCode").click(); 
                }
            });
//        }
    },
    //获取验证码后续（还款计划页面）
    PubGetMbpVfcCodeAfter: function () {
        $("#btnCheckCodeQZ").attr('disabled', "true");//取消绑定点击事件
        var second = 120;//倒计时秒
        function ShowCountDown() {
            if (second > 0) {
                second--;
                $("#btnCheckCodeQZ").val("剩余" + second + "秒");
            }
            else {
                MyActionD.resetAccessCode();
            }

        }
        timerD = window.setInterval(function () { ShowCountDown(); }, 1000);
    },
    //重置获取验证码
    resetAccessCode: function () {
        $("#btnCheckCodeQZ").val("重新获取");
        $("#btnCheckCodeQZ").removeAttr("disabled");//重新获取验证码（还款计划页面）
        clearInterval(timerD);
        $.AjaxColynJson("/P2PLoanPayManage/RemoveCheckCode?type=3&t=" + new Date(), "", function (data) {
        });
    }
}


