$(document).ready(function(){
	//根据以前存在的cookie设置皮肤  
	var cookie_skin = $.cookie("MyCssSkin");   
	if (cookie_skin) {                        
		$("#cssfile").attr("href", "/css/skin/" + cookie_skin + ".css");
	}
});
