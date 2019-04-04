/*------------------------------------------------
 * Author:李冬冬  Date：2014-8-19 
-------------------------------------------------*/


var MyAction = {
    Init: function () {
        $("#ScheduleStepGird").datagrid({
            method: "GET",
            url: "/Demo/Json/P2PScheduleStepListView.json",
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "step_name", title: "环节名称", width: 150, align: "center" },
            { field: "operate_emp", title: "操作人", width: 150, align: "center" },
            { field: "operate_date", title: "操作日期", width: 150, align: "center" },
            { field: "operate_mark", title: "备注", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#ScheduleStepGird').datagrid('getPager');
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

MyAction.Init();


