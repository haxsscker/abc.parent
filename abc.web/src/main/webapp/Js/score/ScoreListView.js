/*------------------------------------------------
 * Author:潘健  Date：2014-8-25 
 -------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#LookUp").click(MyAction.LookUp);
    $("#Edit").click(MyAction.Edit);
    $("#Search").click(MyAction.Search);
    $("#Export").click(MyAction.Export)
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        var param = createParam3("SearchForm");
        $("#IntegralListGrid").datagrid({
            method: "GET",
            url: "/score/json/getScoreList.json?" + param,
          //  height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,            //自动扩展或收缩以适应网格列的宽度和防止水平滚动的大小。
            rownumbers: true,            //True to show a row number column.
            nowrap: false,              //在一行显示数据。设置为true可以提高加载性能。
            striped: true,              //行与行显示background-color不同
            idField: "userId",         //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,           //从服务器定义要排序的数据。
            view: myview,               //重写当没有数据时
            emptyMsg: '没有找到数据',    //返回数据字符
            columns: [
                [
                    { field: "userName", title: "客户名称", width: 150, align: "center" },
                    { field: "userRealName", title: "真实姓名", width: 150, align: "center" },
                    { field: "userScore", title: "总积分", width: 150, align: "center" },
                    { field: "userScoreLastmodifytime", title: "最后调整时间", width: 150, align: "center"
//                        ,
//                        formatter: function (value) {
//                            if (!(value == null)) {
//                                return convertToDate(value, "yyyy-MM-dd");
//                            } else return "-";
//                        }
                    }
                ]
            ],
            pagination: true,
            singleSelect: true
        })
        var p = $('#IntegralListGrid').datagrid('getPager');
        $(p).pagination({
            pageSize: 10,
            pageList: [5, 10, 15, 20, 30, 50, 100],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            onBeforeRefresh: function () {
                $(this).pagination('loading');
                $(this).pagination('loaded');
            }
        });
    },
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/score/scoreLookUpView?cuiId=" + row.userId,
            iconCls: 'icon-add',
            title: "客户积分明细",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        Dialog.dialog("close");
                    }
                }
            ]
        })
    },
    Edit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/score/scoreEditView?userId=" + row.userId,
            iconCls: 'icon-add',
            title: "修改用户积分",
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [
                {
                    text: '确认修改',
                    iconCls: 'icon-edit',
                    handler: function () {
                        if ($("#colyn").form("validate")) {
                            var scoreCode = $("#scoreCode").val();
                            console.log(scoreCode);
                            $.post("/score/json/modifyUserScore.json?userId=" + row.userId + "&scoreCode=" + scoreCode, function (data) {
                                if (data.success) {
                                    Colyn.log("修改成功");
                                    MyAction.Init();
                                } else {
                                    Colyn.log(data.success);
                                }
                            }, "json");
                            Dialog.dialog("close");
                            $('#IntegralListGrid').datagrid('reload');
                        }
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        Dialog.dialog("close");
                    }
                }
            ]
        })
    },
    Search: function () {
        var param = createParam3("SearchForm");
//        var o = { modelAction: "Search", userName: $('#cst_user_name').val(), userRealName: $('#cst_real_name').val()};
        $.post("/score/json/getScoreList.json?", param, function (data) {
//        $.post("/score/json/getScoreList.json?t=" + new Date() + "&" + getParam(o), function (data) {
            $("#IntegralListGrid").datagrid("loadData", data);
        }, "json");
    },
    Export: function(){
    	$("#form1").submit();
    }
}