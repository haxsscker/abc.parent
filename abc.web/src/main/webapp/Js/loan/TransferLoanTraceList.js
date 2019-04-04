
var MyAction = {
    Init: function () {
        $("#TransferLoanTraceListGrid").datagrid({
            method: "GET",
            url: "/loan/json/TransferLoanTraceRecordList.json?bidId="+bidId,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "transferLoanTraceOperation", title: "环节名称", width: 150, align: "center" },
            { field: "oldTransferLoanState", title: "旧的状态", width: 150, align: "center",
            	formatter:function(i,v){
            		if(v.oldTransferLoanState==undefined){
            			return '-';
            		}
            		return v.oldTransferLoanState;
            } },
            { field: "newTransferLoanState", title: "新的状态", width: 150, align: "center" },
            { field: "creatorName", title: "创建人", width: 150, align: "center",
            	formatter:function(i,v){
	        		if(v.creatorName==undefined){
	        			return '-';
	        		}
	        		return v.creatorName;
            	}  
            },
            { field: "createtime", title: "操作日期", width: 150, align: "center" },
            { field: "note", title: "备注", width: 150, align: "center" }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#TransferLoanTraceListGrid').datagrid('getPager');
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


