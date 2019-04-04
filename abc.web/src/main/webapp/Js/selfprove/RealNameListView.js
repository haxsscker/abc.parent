/*------------------------------------------------
 * Author:潘健  Date：2014-8-20 
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    $("#Audit").click(MyAction.Audit);
    $("#LookUp").click(MyAction.LookUp);
    $("#btnSearch").click(MyAction.Search);
    MyAction.Init();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        var param = createParam3("SearchForm");
        $("#RealNameListGrid").datagrid({
            url: "/selfprove/json/GetRealNameAuthList.json?" + param,
            height: $(window).height() - 52,
            pageSize: 10,
            fitColumns: false,
            rownumbers: true,
            nowrap: false,
            striped: true,
            //idField: "loan_id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            { field: "userName", title: "客户名称", width: 100, align: "center" },
            { field: "rnpName", title: "真实姓名", width: 100, align: "center" },
            { 
            	field: "rnpDocType", title: "证件类型", width: 100, align: "center",
            	formatter:function (value) {
            		if(value == "1"){
                		return "身份证";
                	}else if(value == "2"){
                		return "临时身份证";
                	}else if(value == "3"){
                		return "户口簿";
                	}else if(value == "4"){
                		return "护照";
                	}else{
                		return "军官证";
                	}
            	}
        	},
            { field: "rnpDocNo", title: "证件号码", width: 150, align: "center" },
            { 
            	field: "rnpReviewState", title: "认证状态", width: 100, align: "center",
            	formatter:function (value) {
                	if(value == 0){
                		return "待审核";
                	}else if(value == 1){
                		return "审核已通过";
                	}else if(value == 2){
                		return "审核未通过";
                	}else{
                		return "-";
                	}
                }
        	},
            {
                field: "rnpReviewNote", title: "审核意见", width: 150, align: "center",
                formatter: function (value, rowData, index) {
                    return CutString(value, 10);
                }
            }
            ]],
            pagination: true,
            singleSelect: true
        })
        var p = $('#RealNameListGrid').datagrid('getPager');
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
    //审核
    Audit: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        if (row.rnpReviewState == "1" || row.rnpReviewState == "2") {
            Colyn.log("认证已审核");
            return;
        }
        var o = { cpiId: row.rnpId };
        var Dialog = $.hDialog({
            href: "/selfprove/RealNameExamineView?" + getParam(o),
            iconCls: 'icon-add',
            title: "实名认证",
            maximizable: true,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '同意',
                iconCls: 'icon-user_magnify',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                        MyAction.ToAudit(row.rnpId, 1);
                        Dialog.dialog("close");
                    }
                }
            },
               {
                   text: '不同意',
                   iconCls: 'icon-user_magnify',
                   handler: function () {
                       if ($('#Colyn').form('validate')) {
                           MyAction.ToAudit(row.rnpId, 2);
                           Dialog.dialog("close");
                       }
                   }
               }, {
                   text: '关闭',
                   iconCls: 'icon-cancel',
                   handler: function () {
                       Dialog.dialog("close");
                   }
               }]
        })
    },
    ToAudit: function (cpiId, checkStatus) {
        if ($('#Colyn').form('validate')) {
            var o = {
                cpiId: cpiId,
                //积分类型为实名认证
                scoreTypeId: 1,
                checkStatus: checkStatus,
                checkIdear: $("#check_idear").val()
            };
            $.post("/selfprove/json/RealNameExamineData.json?" + getParam(o), function (data) {
                if (data.success) {
                    Colyn.log("审核成功！");
                }
                else {
                    Colyn.log(data.Msg);
                }
                MyAction.Init();
                $('#RealNameListGrid').datagrid('clearSelections');
               
            }, 'json');
        }
    },
    //查看
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        //var o = { cpiId: row.rnpId };
        var Dialog = $.hDialog({
            href: "/selfprove/RealNameExamineLookUpView?cpiId =" + row.rnpId,
            iconCls: 'icon-add',
            title: "实名认证查看",
            maximizable: true,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '关闭',
                   iconCls: 'icon-cancel',
                   handler: function () {
                       Dialog.dialog("close");
                   }
               }]
        })
    },
    //搜索
    Search: function () {
        var param = createParam3("SearchForm");
        $.post("/selfprove/json/GetRealNameAuthList.json", param, function (data) {
            $("#RealNameListGrid").datagrid("loadData", data)
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