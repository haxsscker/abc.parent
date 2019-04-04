/*------------------------------------------------
 * Author:吕小东  Date：2016年7月17日19:54:53
-------------------------------------------------*/
$(function () {
    $("#btnUpdate").click(MyAction.Edit);//修改
});

var MyAction = {
  
    Edit: function () {
        if ($('#Colyn').form('validate')) {
            var param = $('#Colyn').serializeArray();
            param = convertArray(param);
            $.AjaxColynJson('/sysset/json/messageAddOrEditData.json', param, function (data) {
                if (data.success) {
                    Colyn.log("修改成功！");
                }
                else {
                    Colyn.log(data.Msg);
                }
            });
        }
      
    }
}