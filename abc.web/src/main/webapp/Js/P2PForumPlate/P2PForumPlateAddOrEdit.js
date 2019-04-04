function P2PForumPlateAddOrEdit() {
    var param = $("#Colyn").serializeArray();
    if ($("#Colyn").form("validate")) {
        $.post("/P2PForumPlate/P2PForumPlateAddOrEditData", param, function (data) {
            if (data.Success) {
               // Colyn.log("添加成功！");
                MyAction.Init();
                Dialog.dialog('close');
            } else {
                //Colyn(data.Msg);
            }
        }, "json");
    }
}