/*------------------------------------------------
 * Author:fangrui  Date：2015-01-04
-------------------------------------------------*/

$(function () {
    var redId = $("#redId").val();
    $("#PersonalRewardLookUpGrid").datagrid({
        url: "/redrewardmanage/json/PersonalRewardLookUpDataView.json?redId = " + redId,
        pageSize: 10,
        fitColumns: true,
        rownumbers: true,
        nowrap: false,
        striped: true,
        remoteSort: true,
        view: myview,//重写当没有数据时
        emptyMsg: '没有找到数据',//返回数据字符
        columns: [[
         { field: "userName", title: "用户名", width: 100, align: "center" },
            {
                field: "redName", title: "红包标题", width: 100, align: "center",

            },
            {
                field: "redAmt", title: "发放金额", width: 100, align: "right"
                	/*, formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }*/

            },
            {
                field: "ruAmount", title: "使用金额", width: 100, align: "right"
                	/*, formatter: function (value, rowData, index) {
                    return formatMoney(value, '￥');
                }*/

            },
            {
                field: "redUseScope", title: "使用范围", width: 100, align: "center"
                	/*, formatter: function formatProduct(val, rowData, i) {
                    if (val == "" || val == null) { return "-" }
                    else
                    {
                        var obj = $.ajax({ url: "/redrewardmanage/GetProductData?red_use=" + val, async: false });
                        return obj.responseText;
                    }
                }*/
            },
            {
                field: "redSendtime", title: "发放日期", width: 100, align: "center",
                formatter: function (value) {
                	if(value != null){
                		return creatStringDate(value);
                	}else{
                		return "-";
                	}return creatStringDate(value); 
            	}
            },
            {
                field: "redClosetime", title: "到期日期", width: 100, align: "center",
                formatter: function (value) {
                	if(value != null){
                		return creatStringDate(value);
                	}else{
                		return "-";
                	} 
            	}
            },
            {
                field: "rsState", title: "状态", width: 100, align: "center",
                formatter: function (v, r, i) {
                    if (v == "0") return "失效";
                    else if (v == "1") return "未使用"
                    else if (v == "2") return "已使用";
                    else return "";
                }
            }
        ]],
        pagination: true,
        singleSelect: true,
        onLoadSuccess: function (data) {
            if (data.total == 0) {
                var body = $(this).data().datagrid.dc.body2;
                body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;"></td></tr>');
            }
        }

    })
    var p = $('#PersonalRewardLookUpGrid').datagrid('getPager');
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
});

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
