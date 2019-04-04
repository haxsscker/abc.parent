$(function () {
    var FuzzySearch;
    MyGrid.Resize();
    MyAction.Init();
    $.parser.parse($("#Grid").parent());
    $("#FuzzySearch").click(MyAction.FuzzySearch);
    $("#PreciseSearch").click(MyAction.PreciseSearch);
    $("#Export").click(MyAction.Export);
    //模糊查询提交数据
    MyAction.SearchData();
    $(window).resize(function () {
        MyGrid.RefreshPanl();
    });
    //对于菜单头 鼠标滑过事件
    MyAction.MyMouseOverMenu();
});


var MyAction = {
    Init: function () {
        $("#Grid").datagrid({
            url: "/Log/LogPageLoad?order=desc",
            sortName: 'Id',
            idField: 'Id',
            methord: 'post',
            sortOrder: 'desc',
            pageSize: 20,
            striped: true, //奇偶行是否区分
            view: myview,//重写当没有数据时
            emptyMsg: '没有找到数据',//返回数据字符
            frozenColumns: [[
                  { field: 'ck', checkbox: true }
            ]],
            columns: [[
                { field: "Id", title: "编号", width: 150, align: "center", sortable: true },
                { field: "UserName", title: "用户名", width: 150, align: "center", sortable: true },
                { field: "Controller", title: "控制器", width: 150, align: "center",hidden:true },
                { field: "Action", title: "方法", width: 150, align: "center" ,hidden:true},
                { field: "Contenxt", title: "内容", width: 180, align: "center" },
                { field: "IpAddress", title: "IP地址", width: 150, align: "center" },
                {
                    field: "GetDate", title: "操作时间", width: 150, align: "center", formatter: function (value, row, index) {
                        return convertToDate(value)
                    }
                }

            ]],
            fit: true,
            pagination: true,
            rownumbers: true,
            fitColumns: true,
            singleSelect: false,
            //编辑列表头操作。
            onHeaderContextMenu: function (e) {
                e.preventDefault();
                MyAction.CreateColumnMenu(e);
                $("#tmenu").show();
            }
        })
    },
    //模糊查询
    FuzzySearch: function () {
        FuzzySearch = parent.$.hDialog({
            href: "/Html/Log/mohuLog.html?" + Math.random(),
            maximizable: false,//显示最大化
            width: $(window).width() - 400,
            height: $(window).height() - 300,
            iconCls: 'icon-add',
            title: "模糊查询",
            showBtns: false,//不显示下面的按钮
            onLoad: function () {
                parent.$("#Dlg-Edit").form("clear");
            },
            submit: function () {
                return false;
            }
        })


    },
    //精确查询
    PreciseSearch: function () {
        var searchDialog = parent.$.hDialog({
            href: "/Html/Log/jingqueLog.html?" + Math.random(),
            maximizable: false,//显示最大化
            width: $(window).width() - 380,
            iconCls: 'icon-search',
            title: "精确查询",
            onLoad: function () {
                MyAction.SetCard();
            },
            submit: function () {
                //获取参数
                var UserName = parent.$("#UserName").val();
                var GetDate = parent.$("#GetDate").datebox('getValue');
                if (UserName == "" && GetDate == "") {
                    return;
                }
                else {
                    searchDialog.dialog('close');
                    return $("#Grid").datagrid({ url: '/Person/SeachLogInfo/', queryParams: { UserName: UserName, GetDate: GetDate } });
                }

            }
        })
    },
    Export: function () {
        window.open("/Person/LogExport");
    },//导出
    SearchData: function () {
        //提交数据
        parent.$("#btn-key-search").live("click", function () {
            //获取参数
            var parameter_key = parent.$("#parameter_key").val();
            if (parameter_key == "") {
                return;
            } else {
                FuzzySearch.dialog('close');
                return $('#Grid').datagrid({ url: '/Person/SearchKeyLogInfo/', queryParams: { parameter_key: parameter_key } });
            }
        });
    },//用于模糊搜索
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
    MyMouseOverMenu: function () {
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
    }//离开时
}