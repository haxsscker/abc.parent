$(function () {
    $("#UserName").focus();
    $("#imgCode").click(Action.clickCode);
    $("#submitForm").click(Action.SubmitForm);
    /*杨向东 */
    /*针对于session为空时 判断是不是有iframe*/
    if (self.frameElement != null && (self.frameElement.tagName == "IFRAME" || self.frameElement.tagName == "iframe")) {
        top.window.location.href = "/login/login.htm";
    }
    
    $(document).keydown(function (e) {
        var curKey = e.which;
        if (curKey == 13) {
            Action.SubmitForm();
            return false;
        }
    });
});

var Action = {
    clickCode: function () {      
        this.src = "/Ashx/ValidateCode?time=" + (new Date()).getTime();
        return false;
    },
    SubmitForm: function () {
        //用户名校验
        if ($("#userName").val().length < 1) {
            return false;
        }
        //密码校验
        if ($("#passWord").val().length < 1) {
            return false;
        }       
       
        ////验证码校验
        //if ($.cookie('ValidateCode') != null) {
        //    if ($("#vcode").val().length < 1 || $("#vcode").val() == "点击显示验证码") {
        //        Action.MessagerShow("vcode", "请输入验证码");
        //        return false;
        //    }
        //    if ($.cookie('ValidateCode') != $("#vcode").val()) {
        //        Action.MessagerShow("vcode", "验证码错误");
        //        $("#imgCode").click();
        //        $("#vcode").val("");
        //        return false;
        //    }
        //}
        //else {
        //    Action.MessagerShow("vcode", "验证码错误");
        //    $("#imgCode").click();
        //    return false;
        //}

        //var param = { "emp_Name": $("#UserName").val(), "emp_Password": $("#UserPwd").val() }
        //var url="/Login/CheckLogin";
        //$.ajax({
        //    type:"POST",
        //    url: url,
        //    dataType:"json",
        //    data: param,
        //    beforeSend:Action.Begin,
        //    complete:Action.Complete,
        //    success: function (data) {
        //        Action.Success(data);
        //    },
        //    error: function () {
        //    }
        //});
        $("#loginForm").submit();
    },
    MessagerShow: function (ID, MessageTitle) {
        Colyn.log(MessageTitle);
        $("#" + ID).focus();
    },
    Success: function (data) {
        if (data.flag == "ok") {
            //var sHeight = window.screen.availHeight;
            //if (navigator.userAgent.indexOf("MSIE") > 0) {
            //    sHeight = sHeight - 40;
            //    //关闭当前窗口 (不提醒直接关闭)
            //    window.open('', '_top');
            //    window.top.close();
            //} else {
            //    window.close();
            //}
               
           window.location.href = '/Home/Index';
           // var $width = $(window).width();
           // var $height = $(window).height();
           // window.open('/Home/Index', '', 'height=' + $height + ',width=' + $width + ',top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no');
        } else if (data.flag == "stop") {
            Colyn.log("该用户已停用，请与管理员联系");
        } else {
            Colyn.log("用户名或密码错误");
        }
    },
    Begin: function () {
      $.loading("正在登陆,请稍后...");
    },
    Complete: function () {
        $.loaded();
    }
};

