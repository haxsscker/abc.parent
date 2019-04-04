function deleteName() {
    if ($("#listname option:selected").attr("selected")) {
        $("#listname option:selected").remove(); //删除Select中选中的值
    } else {
        $.messager.alert("系统提示", "请选择接收人！");
    }
}
function deleteNamelist() {

    $("#listname option").remove(); //删除Select所有Option
}
function addName() {
   var Dialogt = $.hDialog({
        href: "/P2PMailManagement/MailManagementAddUsreName?t=1",
        iconCls: 'icon-add',
        title: "接收人选择",
        maximizable: true, //显示最大化
        width: 400, //$(window).width() - 50,
        height: $(window).height() - 50, //$(window).height() - 50,
        buttons: [
            {
                text: '确认添加',
                iconCls: 'icon-ok',
                handler: function () {
                    var rows = $("#OrgLoanCheckIdearGrid").datagrid("getSelections");
                    if (rows.length > 0) {
                        $("#listname option").remove();
                        $(rows).each(function(i, row) {
                            $("#listname").append("<option value='" + row.id + "," + row.type + "'>" + row.name + "</option>");
                        });
                        Dialogt.dialog("close");
                    } else {
                        $.messager.alert("系统提示", "请选择接收人！");
                    }
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function() {
                    Dialogt.dialog("close");
                }
            }
        ]
    });
    
}