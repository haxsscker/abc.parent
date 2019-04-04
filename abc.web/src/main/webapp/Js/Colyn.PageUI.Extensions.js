/*------------------------------------------------
 * Author:Colyn-王群林  Date：2013-11-26 13:59:21
-------------------------------------------------*/

(function ($) {
    function guidDialogId() {
        var s4 = function () {
            return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
        };
        return "colyn-" + (s4() + s4() + "-" + s4() + "-" + s4() + "-" + s4() + "-" + s4() + s4() + s4());
    }

    $.NewGuid = guidDialogId().replace("colyn-", '');

    $.hDialog = function (options) {
        options = $.extend({}, $.hDialog.defaults, options || {});

        var dialogId = guidDialogId();
        if (options.id)
            dialogId = options.id;

        var defaultBtn = [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: options.submit
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                $("#" + dialogId).dialog("close");
            }
        }];

        if (!options.showBtns)
            defaultBtn = [];

        if (options.buttons.length == 0)
            options.buttons = defaultBtn;

        if (options.max) {
            var winWidth = $(window).width();
            var winHeight = $(window).height();
            options.width = winWidth - 20;
            options.height = winHeight - 20;
        }


        var $dialog = $('<div/>').css('padding', options.boxPadding).appendTo($('body'));

        var dialog = $dialog.dialog($.extend(options, {
            onClose: function () {
                dialog.dialog('destroy');
            }
            //onLoad: function () { removeStyle(); }//首次加载是去除掉一些样式
        })).attr('id', dialogId);
        $dialog.find('.dialog-button').css('text-align', options.align);

        return dialog;
    };

    $.hDialog.defaults = $.extend({}, $.fn.dialog.defaults, {
        boxPadding: '3px',
        align: 'right', //按钮对齐方式
        href: '',
        id: '',
        onBeforeOpen: function () {

        },

        content: '',
        height: 200,
        width: 400,
        collapsible: false,
        minimizable: false,
        maximizable: true,
        closable: true,
        modal: true,
        shadow: false,
        mask: true,
        cache: false,
        closed: false,
        showBtns: true,
        shadow: true,
        buttons: [],
        submit: function () {
            return false;
        },
        onBeforeClose: function () {
            $(this).find(".combo-f").each(function () {
                var panel = $(this).data().combo.panel;
                panel.panel("destroy");
            });
            $(this).empty();
        },
        onMove: function (left, right) {
            $('.validatebox-tip').remove();
        }

    });

    $.hWindow = function (options) {
        var windowId = guidDialogId();

        options = $.extend({}, $.hDialog.defaults, options || {});
        if (!options.href && !options.content) {
            alert('缺少必要的参数 href or content');
            return false;
        }

        var $dialog = $('<div/>').attr('id', windowId).appendTo($('body'));

        if (options.max) {
            var winWidth = $(window).width();
            var winHeight = $(window).height();
            options.width = winWidth - 20;
            options.height = winHeight - 20;
        }

        var win = $dialog.window($.extend(options, {
            onClose: function () {
                win.window('destroy');
            }
        })).window('refresh').attr('id', windowId);
        return win;
    };

    $.hWindow.defaults = $.extend({}, $.fn.window.defaults, {
        href: '',
        content: '',
        height: 300,
        width: 400,
        collapsible: false, 	//折叠
        closable: true,         //显示右上角关闭按钮
        minimizable: false, 	//最小化
        maximizable: false, 	//最大化
        resizable: false, 	    //是否允许改变窗口大小
        title: '窗口标题', 	    //窗口标题
        modal: true, 		    //模态	
        draggable: true,        //允许拖动
        max: false,
        onBeforeClose: function () {
            $(this).find(".combo-f").each(function () {
                var panel = $(this).data().combo.panel;
                alert(panel.html());
                panel.panel("destroy");
            });
            $(this).empty();
        }
    });

    $.extend($.fn.datagrid.methods, {
        getSelectedIndex: function (jq) {
            var row = $(jq).datagrid('getSelected');
            if (row)
                return $(jq).datagrid('getRowIndex', row);
            else
                return -1;
        },
        checkRows: function (jq, idValues) {
            if (idValues && idValues.length > 0) {
                var rows = $(jq).datagrid('getRows');
                var keyFild = $(jq).datagrid('options').idField;
                $.each(rows, function (i, n) {
                    if ($.inArray(n[keyFild], idValues)) {
                        $(jq).datagrid('checkRow', row);
                    }
                })
            }
            return jq;
        }
    });

    //扩展 combobox 方法 selectedIndex
    $.extend($.fn.combobox.methods, {
        selectedIndex: function (jq, index) {
            if (!index)
                index = 0;
            var data = $(jq).combobox('options').data;
            var vf = $(jq).combobox('options').valueField;
            $(jq).combobox('setValue', eval('data[index].' + vf));
        }
    });

    //释放IFRAME内存
    $.fn.panel.defaults = $.extend({}, $.fn.panel.defaults, {
        onBeforeDestroy: function () {
            var frame = $('iframe', this);
            if (frame.length > 0) {
                frame[0].contentWindow.document.write('');
                frame[0].contentWindow.close();
                frame.remove();
                if ($.browser.msie) {
                    CollectGarbage();
                }
            }
        }
    });

    //tree 方法扩展 全选、取消全选
    $.extend($.fn.tree.methods, {
        checkedAll: function (jq, target) {
            var data = $(jq).tree('getChildren');
            if (target)
                data = $(jq).tree('getChildren', target);
            $.each(data, function (i, n) {
                $(jq).tree('check', n.target);
            });
        }
    });


    $.extend($.fn.tree.methods, {
        uncheckedAll: function (jq) {
            var data = $(jq).tree('getChildren');
            $.each(data, function (i, n) {
                $(jq).tree('uncheck', n.target);
            });
        }
    });


    jQuery.fn.clear = function () {
        var $form = $(this);
        $form.find('INPUT:text, INPUT:password, INPUT:file, SELECT, TEXTAREA,.combo-value').val('');
        $form.find('INPUT:checkbox, INPUT:radio').removeAttr('checked').removeAttr('selected');
        return this;
    };



    //杨向东 2014.03.03
    /*扩张后台没有treegrid数据时 列表返回无数据*/
    mytreeview = $.extend({}, $.fn.treegrid.defaults.view, {
        onAfterRender: function (target) {
            $.fn.treegrid.defaults.view.onAfterRender.call(this, target);
            var opts = $(target).treegrid('options');
            var vc = $(target).treegrid('getPanel').children('div.treegrid-view');
            vc.children('div.treegrid-empty').remove();
            if (!$(target).treegrid('getRows').length) {
                var d = $('<div class="treegrid-empty" style="border-bottom:1px solid #cccccc;padding-bottom:5px;"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
                d.css({
                    position: 'absolute',
                    left: 0,
                    top: 30,
                    width: '100%',
                    textAlign: 'center'
                });
            }
        }
    })


    //杨向东 2013.12.26
    /*扩张后台没有treegrid数据时 列表返回无数据*/
    myview = $.extend({}, $.fn.datagrid.defaults.view, {
        onAfterRender: function (target) {
            $.fn.datagrid.defaults.view.onAfterRender.call(this, target);
            var opts = $(target).datagrid('options');
            var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
            vc.children('div.datagrid-empty').remove();
            if (!$(target).datagrid('getRows').length) {
                var d = $('<div class="datagrid-empty" style="border-bottom:1px solid #cccccc;padding-bottom:5px;"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
                d.css({
                    position: 'absolute',
                    left: 0,
                    top: 30,
                    width: '100%',
                    textAlign: 'center'
                });
            }
        }
    })
})(jQuery);
