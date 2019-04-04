$(function () {
    MyGrid.Resize();
    MyTransferTree.Init();
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
            //{ loanId: "person", pId: 0, name: "个人信用贷", href: "/loan/loanAddView?category=1" },
            //{ loanId: "car",    pId: 0, name: "汽车抵押贷", href: "/loan/loanAddView?category=2" },
            //{ loanId: "house",  pId: 0, name: "房产抵押贷", href: "/loan/loanAddView?category=3" },
            //{ loanId: "cust",   pId: 0, name: "企业经营贷", href: "/loan/loanAddView?category=4" },
            { loanId: "perspmCustom",   pId: 0, name: "个人贷", href: "/loan/loanCustomAddView?loanType=1" },
            { loanId: "custCustom",   pId: 0, name: "企业贷", href: "/loan/loanCustomAddView?loanType=2"}
        ];
        $.fn.zTree.init($("#MenuTree"), setting, nodes);
        MyTransferTree.zTreeClick(null, "cust", nodes[0]);
    },
    zTreeClick: function (event, treeId, treeNode) {
        $(".panel-title").attr("title", treeNode.name);
        $(".layout-panel-center .panel-title").html(treeNode.name);
        //这是去除返回的那个图标
        $(".icon-arrow_rotate_clockwise").attr("class", null);
        var url = treeNode.href + window.location.search.replace("?","&");
        createFrame(".ManageTemp", url);
    }
};
