/*------------------------------------------------
 * Author:Colyn-王群林  Date：2013-12-3 14:42:27
-------------------------------------------------*/
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#Add").click(MyAction.Add);
    $("#Edit").click(MyAction.Edit);
    $("#Del").click(MyAction.Del);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
});

var MyAction = {
    Init: function () {
        $("#BtnGrid").datagrid({
            url: "/System/GetBtns",
            idField: "btn_Id",
            height: $(window).height() - 52,
            pagination: false,
            nowrap: false,
            rownumbers: true,
            fitColumns:true,
            animate: true,
            singleSelect: true,
            collapsible: false,
            columns: [[
                { title: '编号', field: 'btn_Id', width: 50 },
                {
                    title: '图片', field: 'btn_Icon', width: 80, align: 'center',
                    formatter: function (v, d, i) {
                        return '<span class="icon ' + v + '">&nbsp;</span>';
                    }
                },
                { title: '按钮名称', field: 'btn_Name', align: 'center', width: 130 },
                { title: '按钮标识', field: 'btn_Tag', align: 'center', width: 130 },
                { title: '描述', field: 'btn_Note', align: 'center', width: 130 },
                { title: '排序', field: 'btn_Sort', align: 'center', width: 100 }
            ]],
            onLoadSuccess: function (data) {
                if (data.rows.length < 1) {
                    var body = $(this).data().datagrid.dc.body2;
                    body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;" colspan="6">没有数据</td></tr>');
                }
            }
        });
    },
    Add: function () {
        var addDialog = $.hDialog({
            href: "/Html/System/Button.html?" + Math.random(),
            width: 460,
            height: 340,
            iconCls: 'icon-add',
            title: "添加按钮",
            onLoad: function () {
                MyAction.InitCtrl();
            },
            submit: function () {
                if ($('#Colyn').form('validate')) {
                    var param = $('#Colyn').serializeArray();
                    param = convertArray(param);
                    $.AjaxColynJson('/System/ActionBtn?modelAction=Add', param, function (data) {
                        if (data.Success) {
                            Colyn.log("添加成功！");
                            MyAction.Init();
                        }
                        else {
                            Colyn.log(data.Msg);
                        }
                        addDialog.dialog('close');
                    });
                }
            }
        })
    },
    Edit: function () {
        var row = MyGrid.selectRow();
        if (row) {
            var editDialog = $.hDialog({
                href: "/Html/System/Button.html?" + Math.random(),
                width: 440,
                height: 340,
                iconCls: 'icon-add',
                title: "修改按钮",
                onLoad: function () {
                    MyAction.InitCtrl(row.btn_Id);
                    $('#Colyn').form("load",row);
                    $('#Icon').val(row.iconClsBtnIcon);
                    $('#Icon').attr('class', "icon " + row.btn_Icon);
                },
                submit: function () {
                    if ($('#Colyn').form('validate')) {
                        var param = $('#Colyn').serializeArray();
                        param = convertArray(param);
                        $.AjaxColynJson('/System/ActionBtn?modelAction=Edit', param, function (data) {
                            if (data.Success) {
                                Colyn.log("修改成功！")
                                MyAction.Init();
                            }
                            else {
                                Colyn.log(data.Msg);
                            }
                            editDialog.dialog('close');
                        });
                    }
                }
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    Del: function () {
        var row = MyGrid.selectRow();
        if (row) {
            if(row.BtnTag=="Browser"){
                Colyn.log("浏览按钮不能删除！");
                return false;
            }
            $.messager.confirm("删除用户", "确认要删除选中的按钮吗？", function (r) {
                if (r) {
                    $.AjaxColynJson('/System/BtnDel', { id: row.btn_Id }, function (data) {
                        if (data.Success) {
                            Colyn.log("删除成功！")
                            MyAction.Init();
                            jQuery('#BtnGrid').datagrid('clearSelections')
                        }
                        else {
                            Colyn.log(data.Msg)
                        }
                    });
                }
            })
        }
        else {
            Colyn.log("请选择一条数据进行操作");
        }
    },
    InitCtrl: function (BtnId) {
        //加载图标
        $('#btn_Icon').click(function () {
            var iconDialog = $.hDialog({
                iconCls: 'icon-application_view_icons',
                href: '/Images/Icon.htm?v=' + Math.random(),
                title: '选取图标', width: 800, height: 400, showBtns: false,
                onLoad: function () {
                        $('#iconlist li').attr('style', 'float:left;border:1px solid #fff;margin:2px;width:16px;cursor:pointer').click(function () {
                        var iconCls = $(this).find('span').attr('class').replace('icon ', '');
                        $('#btn_Icon').val(iconCls);
                        $('#Icon').val($(this).attr('title'));                       
                        $('#Icon').attr('class', "icon " + iconCls);
                        iconDialog.dialog('close');
                    }).hover(function () {
                        $(this).css({ 'border': '1px solid red' });
                    }, function () {
                        $(this).css({ 'border': '1px solid #fff' });
                    });
                }
            });
        });
    }
}