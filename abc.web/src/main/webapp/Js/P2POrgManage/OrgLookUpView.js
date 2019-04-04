/*------------------------------------------------
 * Author:徐大龙  Date：2014年10月18日15:09:22
-------------------------------------------------*/

$(function () {
    var columsNames = $("#columsNames").val().split('|');
    var oldValues = $("#oldValues").val().split('|');
    var newValues = $("#newValues").val().split('|');
    var spans = $("span[name]");
    for (var i = 0; i < columsNames.length - 1; i++) {
        if (columsNames[i] == "gov_logo") {
            var img = $("img[name=" + columsNames[i] + "]");
            var src = '<span style="color: red; font-weight: bold; font-size: 16px" title="变更前：' + oldValues[i] + ' 变更后：' + newValues[i] + '">!</span>';
            img.after(src);
        } else {
            var html = $("span[name=" + columsNames[i] + "]");
            var content = '<span style="color: red; font-weight: bold; font-size: 16px" title="变更前：' + oldValues[i] + ' 变更后：' + newValues[i] + '">!</span>';
            html.after(content);
        }

    }
});

