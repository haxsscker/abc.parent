/*------------------------------------------------
* Author:Colyn-潘健  Date：2014-6-11 15:45:44
-------------------------------------------------*/
$(function () {
    LayoutResize.Resize();
    MyCustTree.Init();
    $(window).resize(function () {
        LayoutResize.RefreshPanl();
    });
});

var MyCustTree = {
    Init: function () {
        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: MyCustTree.zTreeClick
            }
        };
        var o = {
            cinId: getUrl("cinId")
        };
        var nodes = [
            { id: 1, pId: 0, name: "基本资料", href: "/P2PSelfProve/CommpanyBaseInfoView" },
            { id: 2, pId: 0, name: "公司领导", href: "/P2PSelfProve/CommpanyLeadInfoView" },
            { id: 3, pId: 0, name: "单位资料", href: "/P2PSelfProve/CommpanyCapitalInfoView" }
        ];
        MyCustTree.zTreeClick(null, 1, nodes[0]);
        $.fn.zTree.init($("#MenuTree"), setting, nodes);
        var treeObj = $.fn.zTree.init($("#MenuTree"), setting, nodes);
        var node = treeObj.getNodeByTId(nodes[0].id.toString())
        treeObj.selectNode(node);
    },
    zTreeClick: function (event, treeId, treeNode) {
        var title = $(".panel-title").html();
        $(".panel-title").attr("title", treeNode.name);
        $(".layout-panel-center .panel-title").html(title + "【" + treeNode.name + "】");
        var url = treeNode.href;
        createFrame(".ManageTemp", url);
    }
}

//窗体的返回事件
var PageReturn = {
    returnUrl: function () {
        //客户列表页面
        window.location.href = "/P2PSelfProve/AccountManagementListView?MenuID=" + getUrl("MenuID") + "&MenuName=" + getUrl("MenuName");
    }
}