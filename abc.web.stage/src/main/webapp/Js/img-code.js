/*异步加载验证码图片*/
function showImgCode(obj,url){
	$.ajax({
	        type:'POST',
	        data:{}, //参数
	         dataType:'json', 
	         async:false,
	         url: url,
	         success: function(res) {
		         if(res.success){
		        	 var img = new Image();
		        	//将图片的Base64编码设置给src
		        	 img.src="data:image/jpeg;base64,"+res.data; 
		        	 if (img.complete) {// 判断图片是否在缓存中
		        		 console.log("图片在缓存中..........");
		        		 obj.attr('src',"data:image/jpeg;base64,"+res.data);
		        	    } else {// 图片加载到浏览器的缓存中
		        	    	console.log("图片加载到浏览器的缓存中...........");
		        	        img.onload = function () {
		        	        	obj.attr('src',this.src);
		        	        	 img.onload = null;
		        	        };
		        	    }
		         }
	         },
			complete: function(XMLHttpRequest, textStatus) {	
			        console.log(XMLHttpRequest);
					console.log(textStatus);
			},
	         error:function(data,type, err){
	             console.log("ajax错误类型："+type);
	             console.log(err);
	         }
	      });
}

$(function(){
	//短信图形验证码
	if($("img[class$='smsImgCode']").length > 0){
		$("img[class$='smsImgCode']").attr("src","");
		$("img[class$='smsImgCode']").attr("alt","不显示请点击");
		$("img[class$='smsImgCode']").bind("click",function(){
			var obj = $(this);
			showImgCode(obj,'/securityCode/json/smsImgCode.json');
		});
		//$("img[class$='smsImgCode']").click();
	}
	//普通图形验证码
	if($("img[class$='simpleImgCode']").length > 0){
		$("img[class$='simpleImgCode']").attr("src","");
		$("img[class$='simpleImgCode']").attr("alt","不显示请点击");
		$("img[class$='simpleImgCode']").bind("click",function(){
			var obj = $(this);
			showImgCode(obj,'/securityCode/json/simpleCode.json');
		});
		//$("img[class$='simpleImgCode']").click();
	}
	
});
