$(function () {
    MyGrid.Resize();
    MyTransferTree.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyTransferTree = {
    Init: function () {

        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: MyTransferTree.zTreeClick
            }
        };
        var nodes = [
            { id: "cust", pId: 0, name: "注册送红包", href: "/redrewardmanage/NewerRewardListView" },
            { id: "bussi", pId: 0, name: "投资返送红包记录", href: "/redrewardmanage/NewerSpecialRewardListView" },
            { id: "bussi", pId: 0, name: "推荐送红包", href: "/redrewardmanage/InvitatRewardListView" },
            { id: "bussi", pId: 0, name: "积分兑换红包", href: "/redrewardmanage/SuccessRewardListView" },
            { id: "bussi", pId: 0, name: "活动派送红包", href: "/redrewardmanage/PersonalRewardListView" }
            //,{ id: "bussi", pId: 0, name: "项目奖励红包", href: "/redrewardmanage/ProjectRewardListView" }
        ];

        $.fn.zTree.init($("#MenuTree"), setting, nodes);
        MyTransferTree.zTreeClick(null, "cust", nodes[0]);
    },
    zTreeClick: function (event, treeId, treeNode) {
        $(".panel-title").attr("title", treeNode.name);
        $(".layout-panel-center .panel-title").html(treeNode.name);
        //这是去除返回的那个图标
        $(".icon-arrow_rotate_clockwise").attr("class", null);
        var url = treeNode.href;
        createFrame(".ManageTemp", url);
    }
}

var PageReturn = {

    returnUrl: function () {
        return;

    }
}