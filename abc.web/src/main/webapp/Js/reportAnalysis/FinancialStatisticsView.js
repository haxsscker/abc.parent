/*------------------------------------------------
 * Author:潘健  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});