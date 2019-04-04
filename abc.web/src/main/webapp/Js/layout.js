var SYS_config = { "theme": { "title": "默认皮肤", "name": "default" }, "showType": "tree", "gridRows": "20" };
var onlyOpenTitle = "欢迎使用";
var opentabs = 50; //最多打开选项卡个数
var menu;
$(function () {
$.get("/Home/json/getLeftMenuTree.json", function(data){
	menus = data;

    //初始页面打开显示右下角提示信息
    //var arrShake = $.shake.show($("#ccc"), "red", 100);
    $('#loading').fadeOut();
    if (menus) {
        initNav();
        tabClose();
        top.addTab('欢迎使用', '/Home/Desktop', 'icon-house_star'); //添加默认TAB
    } else {
        $.messager.alert("系统提示", "<font color=red><b>权限不足！</b></font>", "warning", function () {
            location.href = '/System/LoginOut';
        });
    }

    $('body').layout({
        onExpand: function () {
            $('body').layout('resize');
        }
    });

    //Tab右键菜单
    $('#closeMenu').menu({
        onClick: function (item) {
            closeTab(item.id);
        }
    });

    //刷新
    $("#MenuRefresh").click(function () {
        $.post("/Home/json/getLeftMenuTree.json?roleId=12", function (data) {
            $('#wnav').tree('loadData', data);
        }, 'json');
    });

    //关闭所有菜单
    $("#MenuIn").click(function () {
        $('#wnav').tree('collapseAll');
    });

    //展开所有菜单
    $("#MenuOut").click(function () {
        $('#wnav').tree('expandAll');
    });

    //点击系统右下角提示信息
    $("#ccc").click(function () {
        addTab("系统提示", '/DBCustManage/CustMaintenceView?MenuId=98&MenuName=客户维护', 'icon-cog');
        $.shake.clear(arrShake[0], arrShake[1], arrShake[2], arrShake[3]);
    });

    //后台页面头部大菜单提示效果
    $(".evt-menu a").click(function () {
        var $this = $(this);
        var _title = $this.find("p").html();
        var _url = $this.attr("href");
        addTab(_title, _url, 'icon-cog');
        return false;
    });
});
});

/*************************************************************************************
功能：页面右下角信息闪动提示
使用方法：$.shake.show(ele,className,times); $.shake.clear()
*************************************************************************************/
 (function ($) {
    $.extend({
        //信息提示
        shake: {
            //ele:闪烁的对象 , cls:需要附加在上面的class, times:闪烁次数
            show: function (ele, cls, times) {
                var _title = document.title;
                var _step = 0;
                ele.addClass("white");
                var i = 0, t = false, o = ele.attr("class") + " ", c = "", times = times || 2;
                if (t) return;
                t = setInterval(function () {
                    i++;
                    _step++;
                    if (_step > 1) {
                        _step = 0;
                        document.title = "【】" + _title;
                    } else if (_step == 1) {
                        document.title = "【新消息】" + _title;
                    }
                    c = i % 2 ? o + cls : o;
                    ele.attr("class", c);

                    if (i == 2 * times) {
                        $.shake.clear(t, ele, cls, _title);
                    }
                }, 600);

                return [t, ele, cls, _title];
            },
            clear: function (t, ele, cls, _title) {
                clearInterval(t);
                ele.removeClass(cls).removeClass("white");
                document.title = _title;
            }
        }
    })
})(jQuery);

//手风琴效果导航
var Accordion = {
    addNav: function (data) {
        $.each(data, function (i, sm) {
            var menulist = "";
            menulist += '<ul class="smallicon menuItem">';
            $.each(sm.children, function (j, o) {
            	alert(o.attributes.url);
                menulist += '<li><div><a ref="' + o.id + '" href="#" rel="' + o.attributes.url + '?MenuId=' + o.id + '" ><span class="img icon ' + o.iconCls + '" iconCls="' + o.iconCls + '" >&nbsp;</span><span class="nav">' + o.text + '</span></a></div></li> ';
            });
            menulist += '</ul>';

            $('#wnav').accordion('add', {
                title: sm.text,
                content: menulist,
                iconCls: sm.iconCls,
                border: false,
                selected: (i == 0)
            });
        });
    },
    Init: function (menuData) {
        $(".leftMenu").css("display", "none");
        $("#wnav").accordion({
            fit: true,
            border: false
        });

        Accordion.addNav(menuData);

        $('.menuItem li').live({
            click: function () {
                var a = $(this).children('div').children('a');
                var tabTitle = $(a).children('.nav').text();

                var url = $(a).attr("rel");
                var menuid = $(a).attr("ref");
                var icon = $(a).children('span').attr('iconCls');

                addTab(tabTitle, url, icon);
                $('.accordion li div').removeClass("selected");
                $(this).children('div').addClass("selected");
            }
        });
    }
};

//左侧树形菜单
var treeNav = {
    init: function () {
        $("#wnav").tree({
            animate: true,
            lines: true,
            data: menus,
            onClick: function (node) {
                if (node.attributes.url != "#" && node.attributes.url != '') {
                    addTab(node.text, node.attributes.url + '?MenuId=' + node.id + "&MenuName=" + node.text, node.iconCls);
                } else {
                    $('#wnav').tree('toggle', node.target);
                }
            }
        });
    }
};

var findMenu = function (id) {
    var m;
    $.each(menus, function (i, n) {
        if (id == n.id)
            m = n;
    });
    return m;
};

var AccordionTree = {
    arrayObj: [],
    init: function () {
        $.each(menus, function (i, n) {
            $('#wnav').append('<div style="padding:0px;" title="' + n.text + '" data-options="border:false,iconCls:\'' + n.iconCls + '\'"><ul id="nt' + i + '"></ul></div>');
            AccordionTree.arrayObj[i] = 0;
        });

        $("#wnav").accordion({
            fit: true,
            border: false,
            onSelect: function (t, i) {
                if (AccordionTree.arrayObj[i] == 0) {
                    AccordionTree.arrayObj[i] = 1;
                } else {
                    return;
                }

                $('#nt' + i).tree({
                    lines: false,
                    animate: true,
                    data: menus[i].children,
                    onClick: function (node) {
                        if (node.attributes.url != "" && node.attributes.url != '#') {
                            addTab(node.text, node.attributes.url + '?MenuId=' + node.id, node.iconCls);
                        } else {
                            $('#nt' + i).tree('toggle', node.target);
                        }
                    }
                });
            }
        });
    }
};

function createFrame(url) {
    return '<iframe scrolling="no" frameborder="0" style="width:100%;height:100%;" src="' + url + '"></iframe>';
}

function tabClose() {
    /*双击关闭TAB选项卡*/
    $(".tabs-inner").live('dblclick', function () {
        var subtitle = $(this).children(".tabs-closable").text();
        if (subtitle != onlyOpenTitle && subtitle != "")
            $('#tabs').tabs('close', subtitle);
    });
}

function closeTab(action) {
    var alltabs = $('#tabs').tabs('tabs');
    var currentTab = $('#tabs').tabs('getSelected');
    var allTabtitle = [];
    $.each(alltabs, function (i, n) {
        allTabtitle.push($(n).panel('options').title);
    });
    switch (action) {
        case "refresh":
            var iframe = $(currentTab.panel('options').content);
            var src = iframe.attr('src');
            $('#tabs').tabs('update', {
                tab: currentTab,
                options: {
                    content: createFrame(src)
                }
            });
            break;
        case "close":
            var currtab_title = currentTab.panel('options').title;
            $('#tabs').tabs('close', currtab_title);
            break;
        case "closeall":
            $.each(allTabtitle, function (i, n) {
                if (n != onlyOpenTitle) {
                    $('#tabs').tabs('close', n);
                }
            });
            break;
        case "closeother":
            var currtab_title = currentTab.panel('options').title;
            $.each(allTabtitle, function (i, n) {
                if (n != currtab_title && n != onlyOpenTitle) {
                    $('#tabs').tabs('close', n);
                }
            });
            break;
        case "closeright":
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);

            if (tabIndex == alltabs.length - 1) {
                alert('后边无选项卡');
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (i > tabIndex) {
                    if (n != onlyOpenTitle) {
                        $('#tabs').tabs('close', n);
                    }
                }
            });

            break;
        case "closeleft":
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);
            if (tabIndex == 1) {
                //alert('首个选项卡不能关闭');
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (i < tabIndex) {
                    if (n != onlyOpenTitle) {
                        $('#tabs').tabs('close', n);
                    }
                }
            });

            break;
        case "exit":
            $('#closeMenu').menu('hide');
            break;
    }
}
