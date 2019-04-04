 /*------------------------------------------------
 * Author:徐大龙  Date：2014-10-27
-------------------------------------------------*/
var dataBaseName;
$(function () {

    MyAction.intit();
    $("#databaseBackup").live("click", function () {
        dataBaseName = $("#backName").text();
        $.AjaxColynText('/sysset/AutoBackup?dataName=' + dataBaseName, function (data) {
            data = JSON.parse(data);
            Colyn.log(data.Msg);
            MyAction.init();
        });
    });

});

var MyAction = {
    init: function () {
        var myDate = new Date();

        var m = (myDate.getMonth() + 1).toString();

        var t = myDate.getDate().toString();

        var ss = Math.floor(Math.random() * 1000).toString();

        //获取数据库名
        $.post("/sysset/GetDatabaseName", function (data) {

            $("#backName").text("DatabaseBackup/" + data + m + t + ss);
        })

    }
}

