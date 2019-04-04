// JavaScript Document
/*banner js*/
jQuery(".banner").hover(function() {
    jQuery(this).find(".prev,.next").stop(true, true).fadeTo("show", 0.5)
},
function() {
    jQuery(this).find(".prev,.next").fadeOut()
});
jQuery(".banner").slide({
    titCell: ".hd ul",
    mainCell: ".bd ul",
    effect: "fold",
    autoPlay: true,
    autoPage: true,
    trigger: "click",
    startFun: function(i) {
        var curLi = jQuery(".banner .bd li").eq(i);
        if ( !! curLi.attr("_src")) {
            curLi.css("background-image", curLi.attr("_src")).removeAttr("_src")
        }
    }
});
/*banner js end*/

/*折叠*/
function Huifold (obj,obj_c,speed,obj_type,Event){
	if(obj_type == 2){
		//$(obj+":first").find("b").html("-");
		//$(obj_c+":first").show();
	}			
	$(obj).bind(Event,function(){
		if($(this).next().is(":visible")){
			if(obj_type == 2){
				return false;
			}else{
				$(this).next().slideUp(speed).end().removeClass("selected");
				$(this).find("b").html("+");					
			}
		}
		else{
			if(obj_type == 3){
				$(this).next().slideDown(speed).end().addClass("selected");
				$(this).find("b").html("-");
			}else{
				$(obj_c).slideUp(speed);
				$(obj).removeClass("selected");
				$(obj).find("b").html("+");
				$(this).next().slideDown(speed).end().addClass("selected");
				$(this).find("b").html("-");
			}				
		}
	});
}




/*圆圈百分比*/
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		define(['jquery'], factory);
	} else {
		factory(jQuery);
	}
}(function ($) {
	$(function () {
		//init popover model
		if ($(".tag[data-toggle=popover]").length > 0) {
			$(".tag[data-toggle=popover]").popover();
		}

		if ($(".data-tips[data-toggle=popover]").length > 0) {
			$(".data-tips[data-toggle=popover]").popover();
		}
	});

	$(window).on('load', function () {
		$('.knob').knob();
	});
}));

/*基本信息 js*/
$(function(){
	$(".j-dj").toggle(function(){
	
		$(this).parents().next(".j-zhankai").show(500);

	},function(){
		$(this).parents().next(".j-zhankai").hide(500);

	})
})
/**
 * js切换tab
 */
$(function(){
	$('.tabs-cur .tab a').click(function(){
		var className = $(this).parent().attr("name");
		$(this).addClass("active");
		$('.'+className).css("display","block");
		$("#shuaxin").attr("href","/account/myAccount/bindAccount?tab_name="+className); 
		$(this).parent().siblings().each(function(){
			$(this).find('a').removeClass("active");
			$("."+$(this).attr("name")).css("display","none");
		});
	});
})

/*判断浏览器是否低于ie7*/
//$(document).ready(function() {
//    $("body").iealert();
//});


/*导航条js*/
$(function(){
    //显示
    $(".ui-nav li").mouseover(function(){
        $(this).children("ul").show();
    })
    //隐藏
    $(".ui-nav li").mouseout(function(){
        $(this).children("ul").hide();
    })
})


