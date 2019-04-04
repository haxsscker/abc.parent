$(function () {
    var Dialog;
    MyAction.Init();
    $("#submit").click(MyAction.SubmitData);

});

var MyAction = {
		// 页面初始化
		Init : function() {
			  var Message =GetRequest('Message');	
			  var status =GetRequest('status');
			  var returnMessage;
			  switch (status) {
	            case "cz": {
	            	returnMessage = "充值";
	            	break;
	            } case "tx": {
	            	returnMessage = "提现";
	            	break;
	            	
	            } case "kh": {
	            		returnMessage = "开户";
	            	break;
	            }case "xh": {
	            		returnMessage = "销户";
	            	break;
	            }case "sq": {
	            		returnMessage = "用户授权";
	            	break;
	            }
	            }
			  if(Message =="000000"){
				  $("#message").html(returnMessage+"成功！");
			  }else{
				  $("#message").html(returnMessage+"失败！"); 
			  }
			},
		SubmitData:function(){
			window.close();
		}
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
 
