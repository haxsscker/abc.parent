/*------------------------------------------------
 * Author:
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#btnSearch").click(MyAction.Search);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#EmailListGrid").datagrid({
            method: "Post",
            url: "/selfprove/json/EmailListView.json",
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "userName", title: "客户名称", width: 150, align: "center",
            	formatter: function (value) {
                if (!value) {
                    return "-";
                }
                else {
                    return value;
                }
            } },
            { field: "userRealName", title: "真实姓名", width: 150, align: "center",
            	formatter: function (value) {
                    if (!value) {
                        return "-";
                    }
                    else {
                        return value;
                    }
                }
            },
            { field: "userEmail", title: "邮箱", width: 350, align: "center",
            	formatter: function (value) {
                    if (!value) {
                        return "-";
                    }
                    else {
                        return value;
                    }
                }
            },
            {
                field: "userRegisterDate", title: "注册日期", width: 150, align: "center",
                formatter: function (value) {
                	 if (!value) {
                         return "-";
                     }
                     else {
                         return creatStringDate(value);
                     }
                }
            },
            {
                field: "userEmailIsbinded", title: "是否激活", width: 150, align: "center",
                formatter: function (value) {
                	if(value == "1"){
                 	   return "已激活";
                    }else{
                 	   return "未激活";
                    }
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#EmailListGrid').datagrid('getPager');
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
    //搜索
    Search: function () {
        var param = createParam3("SearchForm");
        $.post("/selfprove/json/EmailListView.json", param, function (data) {
            $("#EmailListGrid").datagrid("loadData", data)
        }, 'json');
    }

}

//格式化时间
function creatStringDate(t){
	var d=new Date(t);
	var year=d.getFullYear();
	var day=d.getDate();
	var month=+d.getMonth()+1;
	
	var f=year+"-"+formate(month)+"-"+formate(day);
	return f;
	}
	function formate(d){
	return d>9?d:'0'+d;
	}