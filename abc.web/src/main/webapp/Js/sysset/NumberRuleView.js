$(function () {
//    setVal();
//    $(":input").blur(function () { setVal();});
    $("#btnUpdate").click(function () {
        var param = $('#Colyn').serializeArray();                      
        param = convertArray(param);
        $.AjaxColynJson("/sysset/json/NumberRuleUpdate.json", param, function (data) {
            if (data.Success) {
                //提示成功
                $("#demoNumberStr").innerText(data.data);
                Colyn.log(data.message);
            } else {
                Colyn.log(data.message);
            }
        })
    })
  
});
//function setVal() {
//    var date = new Date();
//    var year = date.getFullYear();
//    var month = date.getMonth() + 1;
//    var day = date.getDay();
//    month = (month < 10 ? "0" + month : month);
//    day = (day < 10 ? "0" + day : day);
//    $("span").each(function (i, $e) {
//        
//       var name=$(this).attr("id");     
//       var v = $("input[name=" + name + "]").val();
//       
//       var newValue = v.replace(/\$year\$?/, year).replace(/\$?month\$/, month).replace(/\$?day\$/, day).replace("000n$","0001");
//       
//      
//       this.innerText = "预览："+ newValue;
//    })
//}

