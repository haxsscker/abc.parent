$(function () {
    var Dialog;
    MyGrid.Resize();
    MyAction.Init();
});

var MyAction = {
		// 页面初始化
		/*Init : function() {
			    var empId = GetRequest('empId');
			    var RealName = GetRequest('RealName');
			    var requestRealName = GetRequest1('RealName');
			    var IdentificationNo = GetRequest('IdentificationNo');
			    var Mobile = GetRequest('Mobile');
			    var Email = GetRequest('Email');
		        var o2 = {empId: empId ,RealName:RealName,IdentificationNo:IdentificationNo,Mobile:Mobile,Email:Email,requestRealName:requestRealName};
		                $.post("/account/doubleDryOpenAccount.json", o2, function (data) {
		                    })
			},
     */
  
}

   function GetRequest(name) {
	   var url = decodeURI(location.search);//获取url中"?"符后的字串
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
      function GetRequest1(name) {
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

