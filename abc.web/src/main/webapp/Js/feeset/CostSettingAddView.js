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

          // { id: 1, href: "/feeset/FeeManageListView?feeType=1", pId: 0, name: "手续费管理" },
            { id: 2, href: "/feeset/FeeManageListView?feeType=2", pId: 0, name: "服务费管理" },
          //    { id: 3, href: "/feeset/FeeManageListView?feeType=3", pId: 0, name: "担保费管理" },
               { id: 4, href: "/feeset/FeeManageListView?feeType=4", pId: 0, name: "转让费管理" }
               //{ id: 5, href: "/feeset/FeeManageListView?feeType=5", pId: 0, name: "收购费管理" }
        ];
        MyDepTree.zTreeClick(null, 1, nodes[0]);
        $.fn.zTree.init($("#MenuTree"), setting, nodes);
    },
    zTreeClick: function (event, treeId, treeNode) {
        $(".panel-title").attr("title", treeNode.name);
        $(".layout-panel-center .panel-title").html(treeNode.name);

        var url = treeNode.href;
        // createFrame(".aaaa", url);
        createFrame(".ManageTemp", url);
    }
}