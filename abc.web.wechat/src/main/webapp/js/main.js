
$(function(){
    tablist('#caifu_tab div','div_cur','.caifu_des');

})
//tab选项卡
function tablist(a,b,c){
    $(a).click(function(){
        $(this).addClass(b).siblings().removeClass(b);
       var _index = $(this).index(a);
        $(c).eq(_index).show().siblings(c).hide();
    });
}
/*折叠效果1*/
$(function(){
    $('.baozhang_top').click(function(){
        if( $(this).next().is(':hidden') ) {
            $(this).children('i').removeClass('glyphicon-triangle-right').addClass('glyphicon-triangle-bottom').parent().next().slideUp();
            $(this).children('i').toggleClass('glyphicon-triangle-right').parent().next().slideDown();
        }else{
            $(this).children('i').toggleClass('glyphicon-triangle-right').parent().next().slideDown();
            $(this).children('i').removeClass('glyphicon-triangle-bottom').addClass('glyphicon-triangle-right').parent().next().slideUp();


        }
        return false;
    })
})

/*返回上一页*/
$().ready(function(){
	$("#goBack").click(function(){
		history.go(-1);
	})
});