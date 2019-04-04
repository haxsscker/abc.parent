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
            { id: 1, pId: 0, name: "基本资料", href: "/selfprove/BaseInfoView?" + getParam(o) },
            { id: 2, pId: 0, name: "房产资料", href: "/selfprove/HousePropertyInfoView?" + getParam(o) },
            { id: 3, pId: 0, name: "单位资料", href: "/selfprove/CompanyInfoView?" + getParam(o) },
            { id: 4, pId: 0, name: "私营业主资料", href: "/selfprove/PrivateOwnersInfoView?" + getParam(o) },
            //{ id: 5, pId: 0, name: "财务状况资料", href: "/P2PSelfProve/FinancialConditionInfoView?" + getParam(o) },
            { id: 6, pId: 0, name: "配偶资料", href: "/selfprove/SpouseInfoView?" + getParam(o) },
            { id: 7, pId: 0, name: "联系方式", href: "/selfprove/ContactInformationInfoView?" + getParam(o) },
            { id: 8, pId: 0, name: "教育背景", href: "/selfprove/EducationInfoView?" + getParam(o) }];
//            { id: 9, pId: 0, name: "其他信息", href: "/selfprove/OtherInfoView?" + getParam(o) }];


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
        window.location.href = "/selfprove/AccountManagementListView?MenuID=" + getUrl("MenuID") + "&MenuName=" + getUrl("MenuName");
    }
}