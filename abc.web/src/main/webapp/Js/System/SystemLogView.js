$(function () {
    MyGrid.Resize();
    MyDepTree.Init();
    LayoutResize.Resize();
    $(window).resize(function () {
        LayoutResize.RefreshPanl();
    });
});

var MyDepTree = {
    Init: function () {
        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: MyDepTree.zTreeClick
            }
        };
        var nodes = [
          
            { id: 1, href: "/System/LogUserLoginView", pId: 0, name: "用户登录日志" },
            { id:2,href: "/System/LogSystemErrorView", pId: 0, name: "系统错误日志" },
            { id:3,href: "/System/LogDateOperaView", pId: 0, name: "数据操作日志" },
        ];
        MyDepTree.zTreeClick(null, "LogUserLoginView",nodes[0]);
        $.fn.zTree.init($("#MenuTree"), setting, nodes);
    },
    zTreeClick: function (event, treeId, treeNode) {
        $(".panel-title").attr("title", treeNode.name);
        $(".layout-panel-center .panel-title").html(treeNode.name);

        var url = treeNode.href + "?MenuId=" + getUrl("MenuId");
       // createFrame(".aaaa", url);
        createFrame2(".ManageTemp", url);
    }
}