/*------------------------------------------------
* Author:徐大龙  Date：2014-10-27
-------------------------------------------------*/
$(function () {
    $("#dataBaseList").combobox({
        url: '/System/DataRecoveryView',
        valueField: 'id',
        textField: 'text',
        onLoadSuccess: function () {

        },
        width: 180
    });

    $("#dataRecovery").live("click", function () {
        var dataName = $("#dataBaseList").combobox('getText');
        if (dataName == "") {
            Colyn.log("请选择需要恢复的数据库！");
        } else {
            var par = {
                dataName: dataName
            }
            $.AjaxColynText('/System/DataRecovery?' + getParam(par), function (data) {
                data = JSON.parse(data);
                Colyn.log(data.Msg);
            });
        }
    });
});


