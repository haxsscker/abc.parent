 /*------------------------------------------------
 * Author:Colyn-yangwu  Date：2014-6-16
-------------------------------------------------*/
$(function () {
    $("#initDB").click(MyAction.initDB);
    $(".class_checked").each(function(){
        $(this).click(MyAction.Click);
    });
});


var MyAction = {
    initDB: function () {
        var tableNames = [];
        $(":checkbox").each(function () {         
            if ($(this).attr("checked")) {
                tableNames.push($(this).attr("name"));
            }  
        });
        if ($("#autoBackup").attr("checked")) {
            $.AjaxColynText("/System/AutoBackup", function (data) {
                if (data == "ok") {
                    Colyn.log("自动备份成功");
                } else {
                    Colyn.log("自动备份失败");
                }
            });
        }
        param = { tables:  tableNames.join() };
        $.AjaxColynText("/System/InitDB", param, function (data) {
            if (data == "ok") {
                Colyn.log("初始化成功");
            } else {
                Colyn.log("初始化失败");
            }
        });
    },
    Click: function(){
        if ($(this).attr("checked")) {
            $(this).siblings("span").hide();
            $(this).siblings("span").find(":checkbox").attr("checked", false);
        } else {
            $(this).siblings("span").show();
        }
    }
}
var lastIndex = 0;