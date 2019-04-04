/* 
* @Author: 奥拓思维
* @Date:   2014-12-22 09:36:39
* @Last Modified by:   anchen
* @Last Modified time: 2014-12-25 12:07:53
* @前台调用方法：
*     $(function(){
*        message();
*     })
*/


    //首页新闻公告滚动
    function message(){
         $('.nr').marquee({
             interval: 3000,                 //间隔时间（毫秒）
             speed: 500,                     //移动速度（毫秒）
             showNum: 1,                     //显示个数
             stepLen: 1,                     //每次滚动步长
             type: 'vertical'             //水平滚动 - horizontal / 垂直滚动 - vertical
        });
     }

    //首页bannerjs
   function banner(){
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
    }


  //首页table js-a
	function cc(){
		   $(".alsbg-s li").click(function(){
				$(this).children("a").addClass("current4").parents().siblings().children('a').removeClass("current4");
				var _index = $(this).index();
				$("._con .dk_nei").eq(_index).show().siblings().hide();
		   })	
	}

  //首页table js-a
	function bb(){
          $(".a-jibenx li").click(function(){
		        $(this).children("a").addClass("a-current3").parents().siblings().children('a').removeClass("a-current3");
				var _index = $(this).index();
				$(".a-cc .a-bb").eq(_index).show().siblings().hide();
	      })	
	}

  /*
   *我的账户-账户总览tab选项卡
   *@ $obj : $(".tf-all-tab a")  $con : $("._tf_con")  $cur :样式class
   *调用方法 ：account_tabs($(".tf-all-tab a"),$("._tf_con"),"tf-cur2")
   */
  function account_tabs($obj,$con,$cur){
    $obj.click(function() {
        /* Act on the event */
        $(this).addClass($cur).siblings().removeClass($cur);
        var _index = $(this).index();
        $con.eq(_index).show().siblings().hide();
    });
  }

  /*
   *@我的账户：列表实现 全选和单选
   * @Date：2014-12-23
   */
  
  function checkall (){
    //全选的实现
    $(".check-all").click(function(){
      $(".ids").prop("checked", this.checked);
    });
    $(".ids").click(function(){
      var option = $(".ids");
      option.each(function(i){
        if(!this.checked){
          $(".check-all").prop("checked", false);
          return false;
        }else{
          $(".check-all").prop("checked", true);
        }
      });
    });
  }

  /*
   *@ZeroClipboard跨浏览器复制插件
   *@使用方法：
   *  1、把ZeroClipboard.swf这个flash文件放到js文件夹里；
   *     跟ZeroClipboard.js这个文件放在同一个目录；
   *     要是flash文件不在js目录，那么请修改ZeroClipboard.js中的 moviePath: './js/ZeroClipboard.swf',
      2、页面上引入插件文件ZeroClipboard.js，代码如下：
         <script type="text/javascript" src="ZeroClipboard.js"></script>
      3、初始化插件，代码如下：ZeroInit();

   */
  function ZeroInit() {  
    var clip = null;  
    clip = new ZeroClipboard.Client();  // 新建一个对象 
    clip.setHandCursor(true);   // 设置鼠标为手型  
    clip.addEventListener('mouseOver', function (client){    
      clip.setText( document.getElementById('fe_text').value ); // 设置要复制的文本。
    });
    clip.addEventListener('complete', function (client, text) {   
      layer.msg("复制成功，您可以粘贴发送给您的好友了",1,{type:1}); //提示信息
    });
    clip.glue('clip_button'); //这个 button 的id 不一定要求是一个 input 按钮，也可以是其他 DOM 元素。
  }

  /*
   *我的账户，左边菜单点击展开js
   */
  function zh_left_menu(){
    $(".apply-menu dt").click(function(){
        $(this).parent().addClass("tf-show").children("dt").find("i").removeClass().addClass('fa fa-minus tf-hui mr5').parents().siblings().removeClass("tf-show").children("dt").find("i").removeClass().addClass('fa fa-plus tf-hui mr5');
    })
  }