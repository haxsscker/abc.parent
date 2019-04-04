
$(function () {
    MyGrid.Resize();
    MyAction.Init();
    $("#Copy").click(MyAction.Copy);
    $("#Restore").click(MyAction.Restore);
    $("#Initialize").click(MyAction.Initialize);
    $("#Del").click(MyAction.Del);
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
    //对于菜单头 鼠标滑过事件
    MyAction.MyMouseOverMenu();

});
var MyAction = {
    Init: function () {
        $("#Grid").datagrid({
            url: "/Database/DataBasePageLoad?order=desc",
            sortName: 'BackUpID',
            idField: 'BackUpID',
            methord: 'post',
            sortOrder: 'desc',
            pageSize: 20,
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            rownumbers:true,
            columns: [[
               { field: "BackUpID", title: "编号", width: 150, align: "center", sortable: true },
               { field: "BackUpDataBaseName", title: "备份文件", width: 150, align: "center" },
               { field: "BackUpDataBaseName_log", title: "备份log文件", width: 150, align: "center" },
               {
                   field: "BackUpDate", title: "备份日期", width: 150, align: "center", formatter: function (value, row, index) {
                       return convertToDate(value)
                   }
               },
               { field: "BackUpPath", title: "数据库路径", width: 150, align: "center" }
            ]],
            fit: true,
            pagination: true,
            rownumbers: true,
            fitColumns: true,
            singleSelect: true,
            //编辑列表头操作。
            onHeaderContextMenu: function (e) {
                e.preventDefault();
                MyAction.CreateColumnMenu(e);
                $("#tmenu").show();
            }
        })
    },
    //复制
    Copy: function () {
        //备份数据库
        var URL = "/Database/DatabaseBackup";
        $.post(URL, function (data) {
            var data = eval('(' + data + ')');
            //数据与服务器进行交互 刷新页面
            MyAction.Init();
            Colyn.log("数据库备份成功");
        });
    },
    //还原
    Restore: function () {
        var row = MyGrid.selectRow();
        var num = row.length;
        if (num == 0) {
            Colyn.log("请选择一条记录进行操作");
            return;
        }
        else if (num > 1) {
            Colyn.log("您选择了多条记录,只能选择一条记录进行修改");
            return;
        }
        else {
            $.messager.confirm('警告', '你真的要还原数据库嘛??', function (r) {
                if (r) {
                    var URL = '/Database/DatabaseRestore?RestorePath=' + row[0].BackUpPath;
                    $.post(URL, function (data) {
                        var data = eval('(' + data + ')');
                        //数据与服务器进行交互 刷新页面
                        MyAction.Init();
                        Colyn.log("数据库还原成功");
                        $('#Grid').datagrid('clearSelections');
                    });
                }
            });
        }

    },
    //删除
    Del: function () {
       // alert(MyGrid.selectRow());
        var row = MyGrid.selectRow();
       // alert(row.length);
        if (row.length > 0) {
            $.messager.confirm('提示信息', '您确认要删除选中的记录吗?', function (data) {
                if (data) {
                    $.ajax({
                        url: '/Database/SysBackupDelete?ids=' + row,
                        type: 'post',
                        error: function () {
                            Colyn.log("删除失败");
                            $('#Grid').datagrid('clearSelections');
                        },
                        success: function (re) {
                            var data = eval('(' + re + ')');
                            if (data.success) {
                                Colyn.log("删除成功！")
                                MyAction.Init();
                                $('#Grid').datagrid('clearSelections');
                            } else {
                                Colyn.log("删除失败");
                            }
                        }
                    });
                }
            });
        } else {
            Colyn.log("请先选择要删除的记录");
        }
    },
    //初始化
    Initialize: function () {
        var editDialog = $.hDialog({
            href: "/Html/Database/DataBaseList.html?" + Math.random(),
            maximizable: true,//显示最大化
            width: $(window).width() - 100,
            height: $(window).height() - 100,
            iconCls: 'icon-add',
            buttons: [{
                text: '初始化',
                iconCls: 'icon-ok',
                handler: function () {//初始化事件
                    //获取列表框里的数据表名
                 var dataBaseName = $("#content table").attr("id");
                 var list = $("#content table tr td");
                 var ids = [];
                 list.each(function (e) {
                     ids.push(list[e].innerHTML);
                 });
                 if (list.length > 0) {
                     $.messager.confirm('提示信息', '您确认要初始化表吗?', function (data) {
                         if (data) {
                             $.ajax({
                                 url: '/Database/InitDataBaseTable?dataBase='+dataBaseName+'&&tables=' + MyAction.Arr2str(ids),
                                 type: 'post',
                                 error: function () {
                                     Colyn.log("初始化失败");
                                     $('#Grid').datagrid('clearSelections');
                                 },
                                 success: function (re) {
                                     var data = eval('(' + re + ')');
                                     if (data.success) {
                                         editDialog.dialog("close");
                                         Colyn.log("初始化成功！")
                                         MyAction.Init();
                                         $('#Grid').datagrid('clearSelections');
                                     } else {
                                         Colyn.log("初始化失败");
                                     }
                                 }
                             });
                         }
                     });
                 } else {
                     Colyn.log("请先选择要初始化的表");
                  }
               }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    editDialog.dialog("close");
                }
            }],
            title: "初始化数据库列表",
            onLoad: function () {
                MyAction.DataBase();
            }
        })
    },
   
    //GetSelectedArr: function () {
    //    var ids = [];
    //    var rows = MyGrid.selectRow();
    //    for (var i = 0; i < rows.length; i++) {
    //        ids.push(rows[i].BackUpID);
    //    }
    //    return ids;
    //},
    //GetSelectedID: function () {
    //    var ids = MyAction.GetSelectedArr();
    //    return ids.join(',');
    //},
    //Arr2str: function (arr) {
    //    return arr.join(',');
    //},
    CreateColumnMenu: function (e) {
        var tmenu = $("#tmenu");
        //不要每次都加载
        var isCalss = tmenu.attr("class");
        if (isCalss) {
            //已经存在
        } else {
            //首次加载
            var fields = $('#Grid').datagrid('getColumnFields');
            for (var i = 0; i < fields.length; i++) {
                //获取里面的属性title
                var fildOption = $('#Grid').datagrid('getColumnOption', fields[i]);
                if (!fildOption.hidden) {
                    $('<div class="selectcheck" style="margin:0px;padding:0px;width:80px;position:relative;left:0;top:0;float:left;" field="' + fields[i] + '"><input type="checkbox" checked="true" id="' + fields[i] + '"/><label for="' + fields[i] + '">' + fildOption.title + '</label></div>').appendTo(tmenu);

                } else {
                    $('<div class="nocheck" style="margin:0px;padding:0px;width:80px;position:relative;float:left;left:0;top:0;" field="' + fields[i] + '"><input type="checkbox"  id="' + fields[i] + '"/><label for="' + fields[i] + '">' + fildOption.title + '</label></div>').appendTo(tmenu);
                }
                tmenu.attr("class", "menu menu-text");
            }
        }
        tmenu.css({ left: e.pageX, top: e.pageY });
        MyAction.MyClickHeadMenu();
    },//对列表头进行操作。
    MyClickHeadMenu: function () {
        $("#tmenu div input[type='checkbox']").live("click", function () {
            if ($(this).attr("checked")) {
                $(this).parent().attr("class", "selectcheck");
                $(this).attr("checked", true);
            } else {
                $(this).parent().attr("class", "nocheck");
                $(this).attr("checked", false);
            }
        });
    },//点击时
    MyMouseOverMenu: function () {   //离开时
        var explorer = window.navigator.userAgent;
        var tmenu = $("#tmenu");
        tmenu.bind("mouseleave", function () {
            if (explorer.indexOf("Chrome") >= 0) {
                tmenu.attr("onmouseleave", "$(this).slideUp();");
                $(this).slideUp();
            }
            var thischeck = $("#tmenu div input[type='checkbox']").parent();
            thischeck.each(function (index) {
                var field;
                var classs;
                //判断是不是ie或者是谷歌
                if (document.all && window.ActiveXObject || explorer.indexOf("Chrome") >= 0) {
                    //ie
                    field = thischeck[index].attributes[2].nodeValue;
                    classs = thischeck[index].attributes[0].nodeValue;
                } else {
                    //非ie
                    field = thischeck[index].attributes[0].nodeValue;
                    classs = thischeck[index].attributes[2].nodeValue;
                }
                //根据里面的class 来判断
                if (classs == "selectcheck") {
                    return $('#Grid').datagrid('showColumn', field);
                } else {
                    return $('#Grid').datagrid('hideColumn', field);
                }
            });
            tmenu.hide();
        });
    },  
    DataBase: function () {  //获取数据库
        $('#DataBase').combobox({
            url: '/Database/GetDataBaseList',
            valueField: 'Id',
            textField: 'Name',
            onSelect: function (rec) {//获取数据库表
                $("#content table tr").remove();//相当于刷新
                var url = '/Database/GetDataBaseTableList?name=' + rec.Name;
                var id = rec.Name;
                $('#DataBaseTable').combobox({
                    url: url,
                    valueField: 'Id',
                    textField: 'Name',
                    onSelect: function (rec) {//获取数据库表
                        var table = $("#content table").attr("id", id);//数据库表名
                          table.append("<tr id="+rec.Id+"><td>" + rec.Name + "</td></tr>");
                    },
                    onUnselect: function (rec) {//取消数据库列表
                        $("#content table tr#"+rec.Id).remove();
                    }
                });
            }
        });
    }
 
}



