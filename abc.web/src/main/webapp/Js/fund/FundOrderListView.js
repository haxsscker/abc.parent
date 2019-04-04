/*------------------------------------------------
 * Author:Colyn-董雪姣  Date：2014-12-22
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    MyAction.GetButtonS();
    $("#Audit").click(MyAction.Audit);
    $("#LookUp").click(MyAction.LookUp);
    $("#btnSearch").click(MyAction.Search);
  /*  $("#Release").click(MyAction.Release);
    $("#CancelRelease").click(MyAction.CancelRelease);
    $("#Authorization").click(MyAction.SetBtn);*/
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    GetButtonS: function(){
        $.get("/system/json/getButtonList.json",function(data){
            btns=data;
        });
    },
    Init: function () {
        $("#FundOrderGrid").datagrid({
            url: "/fund/json/GetFundOrderList.json",
            idField: "foOrderId",
            height: $(window).height() - 52,
            pagination: true,
            singleSelect: true,
            rownumbers: true,
            animate: true,
            columns: [[
				{ title: '项目名称', field: 'faFundNo', width: 100 ,align: 'center'},
				{ title: '基金名称', field: 'faFundName', width: 130, align: 'center'}, 
				{ title: '联系人 ', field: 'foUserName', width: 130, align: 'center'},
				{ title: '联系电话 ', field: 'foUserPhone', width: 130, align: 'center'},
				{ title: '充值金额（万） ', field: 'fcRechargeMoney', width: 130, align: 'center'},
				{ title: '充值日期 ', field: 'fcRechargeDate', width: 100, align: 'center'},
				{ title: '审核状态 ', field: 'foOrderState', width: 130, align: 'center',
					formatter: function (value) { if (value == "1") return "已确认"; else if (value == "0") return "待审核";else if(value == "2")  return "已放弃";else return "";},},
				{ title: '审核人 ', field: 'fcCheckEmp', width: 130, align: 'center'},
				{ title: '审核日期 ', field: 'fcCheckDate', width: 100, align: 'center'},
				{ title: '审核意见 ', field: 'fcCheckIdear', width: 100, align: 'center'},
			/*	{title: '发布状态', field: 'faFundState', align: 'center', width: 100}*/
            ]]
        });

        var p = $('#FundOrderGrid').datagrid('getPager');
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
        if (row.foOrderState == "1" || row.foOrderState == "2") {
            Colyn.log("该条记录已审核，不能重复审核");
            return;
        }
        var o = { cpiId: row.foOrderId };
        var Dialog = $.hDialog({
            href: "/fund/fundOrderApplyAuditView?" + getParam(o),
            iconCls: 'icon-add',
            title: "审核",
            maximizable: true,//显示最大化
            width: $(window).width() - 40,
            height: $(window).height() - 50,
            buttons: [{
                text: '确认',
                iconCls: 'icon-user_magnify',
                handler: function () {
                    if ($('#Colyn').form('validate')) {
                        MyAction.ToAudit(row.foOrderId, 1);
                        Dialog.dialog("close");
                    }
                }
            },
               {
                   text: '放弃',
                   iconCls: 'icon-user_magnify',
                   handler: function () {
                       if ($('#Colyn').form('validate')) {
                           MyAction.ToAudit(row.foOrderId, 2);
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
                //审核状态
                scoreTypeId: 1,
                checkStatus: checkStatus,
                checkIdear: $("#check_idear").val()
            };
            $.post("/fund/json/fundOrderApplyAudit.json?" + getParam(o), function (data) {
                if (data.Success) {
                    Colyn.log("审核成功！");
                }
                else {
                    Colyn.log("审核失败！");
                }
                MyAction.Init();
                $('#FundOrderGrid').datagrid('clearSelections');
               
            }, 'json');
        }
    },
    
    
    LookUp: function () {
        var row = MyGrid.selectRow();
        var num = row;
        if (num == null) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        var Dialog = $.hDialog({
            href: "/fund/FundOrderLookUpView?cuiId="+row.foOrderId,
            iconCls: 'icon-add',
            title: "查看",
            width: $(window).width() - 50,
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
    //搜索
    Search: function () {
        var param = createParam3("SearchForm");
        $.post("/fund/json/GetFundOrderList.json", param, function (data) {
        	 $('#FundOrderGrid').datagrid("loadData", data)
        }, 'json');
    },
   
  
    SubmitGetAllBtn: function (RoleID) {
        var rows = $("#setBtn").treegrid("getChildren");
        var arr = { Btns: [] };
        Enumerable.from(rows).forEach(function (s) {
            var n = { MenuId: s.men_Id, buttons: [] };
            n.buttons = Enumerable.from(s).where('t=>t.value=="√"').select('$.key').toArray();
            if (n.buttons.length > 0) {
                arr.Btns.push(n);
            }
        });
        return JSON.stringify(arr);
    }
}
var lastIndex = 0;
