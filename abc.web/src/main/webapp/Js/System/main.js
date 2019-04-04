$(function () {
    MyGrid.Resize();
    //MyAction.Init();
});

var MyAction = {
    Init: function () {
        $("#MainGrid").datagrid({
            url: "/Home/GetDeskToDo",
            idField: "gin_NO",
            height: $(window).height() - 52,
            pagination: true,
            nowrap: false,
            rownumbers: true,
            fitColumns:true,
            animate: true,
            singleSelect: true,
            collapsible: false,           
            columns: [
               [{
                   field: "gin_NO",
                   title: "担保编号",
                   width: 350,
                   align: "center"
               }, {
                   field: "men_Name",
                   title: "所属环节",
                   width: 100,
                   align: "center"
               }, {
                   field: "gin_Amount",
                   title: "担保金额",
                   width: 100,
                   align: "center"
               }, {
                   field: "gin_GuarExpireTime",
                   title: "到期时间",
                   width: 100,
                   align: "center"
               }]
            ],
            onLoadSuccess: function (data) {
                if (data.rows.length < 1) {
                    var body = $(this).data().datagrid.dc.body2;
                    body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;" colspan="6">没有数据</td></tr>');
                }
            }
        });
    }
}
function remind(){
	top.addTab("到期项目管理", '/remind/LoanExpireListView?MenuId=147&MenuName=项目过期提醒', 'icon-cog'); //添加默认TAB
}