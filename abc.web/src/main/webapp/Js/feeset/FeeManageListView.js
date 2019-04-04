/*------------------------------------------------
 * Author:潘健  Date：2014-8-26
-------------------------------------------------*/
$(function () {
    var Dialog;
    MyGrid.Resize();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $("#search").click(MyAction.Search);
    MyAction.Init();
    
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
  
});
var MyAction = {
    Init: function () {
        $("#ManagementFeeGrid").datagrid({
            url: "/feeset/json/actionFeeListView.json?feeType=" + $("#feeType").val(),
            height: $(window).height() - 22,
//            pageSize: 10,
            fitColumns: true,
            rownumbers: true,
            nowrap: false,
            striped: true,
            idField: "sys_fee_Id",  //此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
            remoteSort: true,
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            columns: [[
            {
                field: "sys_min_money", title: "最小金额(元)", width: 150, align: "right", formatter: function (value, row, index) {
                    return formatMoney(value,'￥');
                }
            },
            {
                field: "sys_max_money", title: "最大金额(元)", width: 150, align: "right", formatter: function (value, row, index) {
                    return formatMoney(value,'￥');
                }
            },
            {
                field: "sys_collect_type", title: "收费方式", width: 150, align: "center", formatter: function (value, row, index) {
                    if (value == '1') {
                        return "笔";
                    } else {
                        return "比例";
                    }
                }
            },
            {
                field: "sys_fee_rate", title: "收费值(%/元)", width: 150, align: "right"
                
            },
            { field: "sys_product_name", title: "项目类型", width: 150, align: "center"}

            ]],
            pagination: false,
            singleSelect: true
        });
//        var p = $('#ManagementFeeGrid').datagrid('getPager');
//        $(p).pagination({
//            pageSize: 10,
//            pageList: [5, 10, 15, 20, 30, 50, 100],
//            beforePageText: '第',
//            afterPageText: '页    共 {pages} 页',
//            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
//            onBeforeRefresh: function () {
//                $(this).pagination('loading');
//                $(this).pagination('loaded');
//            }
//        });
    },
    Add: function () {
        var addDialog = $.hDialog({
            href: "/feeset/feeAddOrEdit?feeType=" + $("#feeType").val(),
            iconCls: 'icon-add',
            title: "添加费用",
            // maximizable: true,//显示最大化
            width: 400,//$(window).width() - 50,
            height: 300,//$(window).height() - 50,
            onLoad: function () {
                $('table input').val("");
            },
            submit: function () {

            },
            buttons: [{
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        var o = {
                            modelAction: "Add"
                        }
                        $.AjaxColynJson('/feeset/json/AddFeeData.json?feeType=' + $("#feeType").val()+'&' + getParam(o), param, function (data) {
                            if (data.success) {
                                MyAction.Init();
                                Colyn.log(data.message);
                                addDialog.dialog('close');
                            }else{                         	
                            	Colyn.log(data.message);              	
                            }

                        });
                         $("#ManagementFeeGrid").datagrid("clearSelections");
                    }
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    addDialog.dialog("close");
                }
            }]

        })

    },
   
    Edit: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var o = { feeType: $("#feeType").val(), feeId: row.sys_fee_id };
            var editDialog = $.hDialog({
                href: "/feeset/feeAddOrEdit?feeType=" + $("#feeType").val(),
                iconCls: 'icon-pencil',
                title: titleVal($("#feeType").val()),
                // maximizable: true,//显示最大化
                width: 400,//$(window).width() - 50,
                height: 300,//$(window).height() - 50,
                onLoad: function () {
                	if(row.sys_collect_type==1){
                		 $("#feeRate").text("收费金额(元)：");
                	}
                	$('#Colyn').form("load", row);
                },
                submit: function () {

                },
                buttons: [{
                    text: '确认修改',
                    iconCls: 'icon-pencil',
                    handler: function () {
                        if ($('#Colyn').form('validate')) {
                            var param = $('#Colyn').serializeArray();
                            param = convertArray(param);

                            $.AjaxColynJson('/feeset/json/editFeeData.json?feeType=' + $("#feeType").val()+'&' + getParam(o), param, function (data) {
                                if (data.success) {
                                    editDialog.dialog('close');
                                    MyAction.Init();

                                    Colyn.log("修改成功！");
                                }
                                else {
                                	Colyn.log(data.message);
                                }

                            });
                            $("#ManagementFeeGrid").datagrid("clearSelections");

                        }
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        editDialog.dialog("close");
                    }
                }]

            });
        } else {
            Colyn.log("请选择一条记录进行操作");
        }
    },
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            $.messager.confirm("删除内容", "确认要删除选中的内容吗？", function (r) {
                if (r) {
                    $.AjaxColynJson('/feeset/json/delFeeData.json', { id: row.sys_fee_id }, function (data) {
                        if (data.success) {
                            Colyn.log("删除成功！")
                        }
                        else {
                            Colyn.log(data.Msg);
                        }
                    });
                    MyAction.Init();
                    $('#ManagementFeeGrid').datagrid('clearSelections');
                }
            })
        }
        else {
            Colyn.log("请选择一条记录进行操作");
        }
    },
    //搜索
    Search: function () {
        var minVal = $("#min_amount").val();
        var maxVal = $("#max_amount").val();
        var moneystart = $("#moneystart").val();
        var moneystarts = $("#moneystarts").val();
        var selFeeType = $("#selFeeType").val();
        //if ((minVal != "" && maxVal == "") || (minVal == "" && maxVal != "")) {
        //    Colyn.log("区间的最大值和最小值必须同时输入");
        //    return;
        //}
 
        if (parseFloat(minVal) >= parseFloat(maxVal)) {
             return false;
        }

        var param = createParam3("SearchForm");
        $.post("/feeset/json/actionFeeListView.json?feeType=" + $("#feeType").val() +"&selFeeType=" + selFeeType, param, function (data) {
             $("#ManagementFeeGrid").datagrid("loadData", data)
        }, 'json');
    }
}
function titleVal(feeType){
	var titilVal;
	switch (feeType){ 
	case "1" :  titilVal ="修改手续费"; 
	break; 
	case "2" :  titilVal ="修改管理费"; 
	break; 
	case "3" :  titilVal ="修改担保费"; 
	break; 
	case "4" :  titilVal ="修改转让费"; 
	break; 
	default :titilVal ="修改手续费";
	break; 
	} 
	return titilVal;
}