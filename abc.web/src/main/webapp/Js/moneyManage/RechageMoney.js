$(function () {
    var Dialog;
    MyGrid.Resize();
    MyAction.Init();
});

var MyAction = {
		// 页面初始化
		Init : function() {
			    var empId = GetRequest('empId');
			    var money = GetRequest('money');
		        var o2 = { empId: empId ,money:money};
		                $.post("/moneyManage/RechargeMoney.json", o2, function (data) {
		                       if (data.success) {
		                    	  $("#wrap").html(data.message); 
		                        }
		                        else {
		                            Colyn.log(data.message);
		                        }
		                    })
			},
     
  
}

   function GetRequest(name) {
	   var url = location.search;//获取url中"?"符后的字串
	   var theRequest = new Object();
	   if (url.indexOf("?") != -1) {
	      var str = url.substr(1);
	      strs = str.split("&");
	      for(var i = 0; i < strs.length; i ++) {
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
	      }
	   }
	   
	   var returnStr =theRequest[name];
	   return returnStr;
	}

