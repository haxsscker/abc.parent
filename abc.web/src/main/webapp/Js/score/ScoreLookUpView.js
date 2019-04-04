/*------------------------------------------------
 * Author:潘健  Date：2014-8-19 
-------------------------------------------------*/


var MyAction = {
    Init: function () {
        $("#IntegralDetailsGrid").datagrid({
            method: "POST",
            url: "/score/json/getUserScoreDetail.json?userId=" + $('#cuiId').val(),
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "scoreName", title: "积分类型", width: 150, align: "center" },
            { field: "scoreValue", title: "变动积分", width: 150, align: "center" },
            { field: "createTime", title: "调整日期", width: 150, align: "center"
//                , formatter: function (value) { if (!(value == null)) { return convertToDate(value, "yyyy-MM-dd"); } else return "-"; }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#IntegralDetailsGrid').datagrid('getPager');
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
    }
}

$(document).ready(function() {
    MyAction.Init();
})


