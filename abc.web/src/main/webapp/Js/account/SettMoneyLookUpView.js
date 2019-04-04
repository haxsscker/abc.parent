/*------------------------------------------------
 * Author:吕小东  Date：2014年9月17日10:44:07
-------------------------------------------------*/
$(function () {
    $("#btnSerch").click(MyAction.Edit);//修改
});

var MyAction = {
//
//    Edit: function () {
//        if ($('#Colyn').form('validate')) {
//            var act_user_name = $('#act_user_name').html();//用户名
//            if (act_user_name == "-")
//            {
//                return;
//            }
//            $.AjaxColynText('/P2PAccountManage/BalanceInquiryData?t=' + new Date(), { act_user_name: act_user_name }, function (data) {
//                if (data != null) {
//                    var arry = data.split('|');
//                    if (arry[0] == "交易成功") {
//                        if (arry.length == 5) {
//                            $("#spantotalBookBalance").html(arry[1]);   //账面总余额
//                            $("#spanavailableBalance").html(arry[2]);   //可用余额
//                            $("#spanamountFrozen").html(arry[3]);       //冻结余额
//                            $("#spannotTransferBalance").html(arry[4]); //未转结余额
//                        }
//                        Colyn.log("查询成功！");
//                    }
//                    else {
//                        $("#spantotalBookBalance").html("-");   //账面总余额
//                        $("#spanavailableBalance").html("-");   //可用余额
//                        $("#spanamountFrozen").html("-");       //冻结余额
//                        $("#spannotTransferBalance").html("-"); //未转结余额
//
//                        Colyn.log(arry[0] + "！");
//                    }
//                }
//            });
//        }

//   }
}