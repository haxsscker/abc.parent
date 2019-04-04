 /*------------------------------------------------
 * Author:Colyn-yangwu  Date：2014-6-16
-------------------------------------------------*/
function update() {
    if ($('#Colyn').form('validate')) {
      
        param = createParam2([{ formId: 'Colyn' }],"edit","");
        $.AjaxColynText('/System/UpdateSysPara', param, function (data) {
            if (data == "ok") {
                Colyn.log("配置成功！");
            }
            else {
                Colyn.log("配置失败！");
            }
        });
    }
}