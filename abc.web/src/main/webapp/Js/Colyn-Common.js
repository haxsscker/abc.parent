/*------------------------------------------------
 * Author:Colyn-王群林  Date：2013-11-26 13:58:55
-------------------------------------------------*/
document.write('<script type="text/javascript" src="/Js/ValidateBox.js" ></script>');
document.write('<script type="text/javascript" src="/Js/Colyn-Com.min.js" ></script>');

/*杨向东 2014.1.25
全局的ajax访问，处理ajax清求时sesion超时*/
$.ajaxSetup({
    contentType: 'application/x-www-form-urlencoded;charset=utf-8',
    complete: function (XMLHttpRequest, textStatus) {
        var sessionstatus = XMLHttpRequest.getResponseHeader('sessionstatus'); //通过XMLHttpRequest取得响应头，sessionstatus，
        if (sessionstatus == 'timeout') {
            if (window.top == window.self) {//不存在父页面
            } else {
                Colyn.log('你已超时重新登录');
                top.window.location.href = '/Login/Login';
            }
        }
        else {

        }
    }
});

//提示信息
var Colyn = {
    log: function (msg, href) {
        $.jGrowl.defaults.closerTemplate = '<div>[ 关闭所有 ]</div>';
        if (href != null || href != undefined) {
            $.jGrowl(msg, { position: 'bottom-right', href: href });
        }
        else
            $.jGrowl(msg, { position: 'bottom-right' });
    }
};

//获取地址栏参数
function getUrl(key) {
    var s = '';
    var url = window.location.search;
    if (url.indexOf('?') != -1) {
        var str = url.substr(1);
        var strs = str.split('&');
        for (var i = 0; i < strs.length; i++) {
            if ([strs[i].split('=')[0]] == key) {
                s = strs[i].split('=')[1];
            }
        };
        return s;
    }
};



//跳转页面
function gotoUrl(href, target) {
    if (target) {
        target.window.location.href = href;
    }
    else {
        window.location.href = href;
    };
    return false;
};

//操作成功后返回另一个页面
function actionPageReturn(href) {
    setTimeout(function () {
        window.location.href = href;
    }, 2000)
}

//数组去重
Array.prototype.distinct = function () {
    var a = [], b = [];
    for (var prop in this) {
        var d = this[prop];
        if (d === a[prop]) continue; //防止循环到prototype  
        if (b[d] != 1) {
            a.push(d);
            b[d] = 1;
        }
    };
    return a;
};
//number数值转化成为货币格式 
function formatMoney(money, symbol) {
    var places = 2;
    symbol = symbol !== undefined ? symbol : "";
    var thousand = ",";
    var decimal = ".";
    var number = money,
      negative = number < 0 ? "-" : "",
      i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
      j = (j = i.length) > 3 ? j % 3 : 0;
    return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
};

//将数值保留两位小数，并加上(后缀，默认不加时用%作为后缀)
function formatPercent(v, suf) {
    suf = suf !== undefined ? suf : "%";
    if (v != null) {
        return v.toFixed(2) + suf;
    } else {
        return "";
    }
}

//将数值不保留小数，并加上(后缀，默认不加时用%作为后缀)
function formatPercento(v, suf) {
    suf = suf !== undefined ? suf : "%";
    if (v != null) {
        return v.toFixed(0) + suf;
    } else {
        return "";
    }
}
// 将金钱数值格式化为html span标签，传入class名称定义样式，并在数值后加上后缀
function formatMoneyHtml(v, className, suf) {
    return "<span class='" + className + "'>" + formatPercent(v, suf) + "</span>";
}

/*******************参数说明*****************************  
场景:一个页面多个表单提交 查询class底下所有input,适用于一个对象多个TR
arrForm:容器数组 formClass为需要提交的容器的ID relaClass:在容器之外但是还是最终要提交到此实体中的Class isAnyRow:是否是多行提交
action:需要操作的动作 Add,Edit等
keyId:主键Id
********************************************************/
function createParam4(arrForm, action, keyId) {
    //arrForm:[{ formClass:"",relaClass:""},{formId: 'house', isAnyRow: true },{formId:""}]
    var obj = {};
    for (var i = 0; i < arrForm.length; i++) {
        var currForm = arrForm[i];
        var o = {}, anyrow = [], query = '', _flag = true; var form;
        var _selector = (currForm.isAnyRow == true ? "" : " :input[name]");
        if (currForm.isAnyRow == true) {
            form = $("." + currForm.formClass + _selector);
        } else {
            form = $("#" + currForm.formClass + _selector);
        }

        if (form) {
            form.each(function (i, v) {
                if (!currForm.isAnyRow) {
                    o[v.name] = returnValue(v)[0];
                } else {
                    var row = {};
                    _flag = false;
                    var _isPush = false;
                    $(this).find(":input[name]").each(function (i, v) {
                        var va = returnValue(v)[0];
                        if (returnValue(v)[1]) {
                            _isPush = true;
                        }
                        row[v.name] = va;

                    });
                    if (_isPush) {
                        anyrow.push(row);
                    }
                }
            });
        }
        if (currForm.relaClass) {
            $("." + currForm.relaClass).each(function (i, v) {
                var rV;
                if ($(this).hasClass("easyui-datebox")) {
                    var dateboxValue = $(this).datebox("getValue");
                    if (dateboxValue.length > 0) {
                        rV = [dateboxValue, true];
                        v.name = $(v).attr("comboname");
                    } else
                        rV = ['', false];
                } else {
                    rV = returnValue(v);
                }
                if (rV[1]) {
                    if (_flag)
                        o[v.name] = rV[0];
                    else
                        anyrow[v.name] = rV[0];
                }
            });
        }
        obj[currForm.formClass] = JSON.stringify(_flag == true ? o : anyrow);
    }
    obj.action = action;
    obj.keyId = keyId;
    return obj;
}


//创建内页框架
function createFrame(obj, url) {
    var s = '<iframe id="contentIframe" scrolling="yes" frameborder="0"  style="width:100%;height:100%;" src="' + url + '" ></iframe>';
    $(obj).html(s);
};

//创建内页框架
function createFrame2(obj, url) {
    var h = $('#layout').height() - 30;
    var s = '<iframe id="contentIframe" scrolling="no" frameborder="0"  style="width:100%;height:' + h + 'px" src="' + url + '" ></iframe>';
    $(obj).html(s);

};

//通用 只要在页面里添加class=GridView
var MyGrid = {
    Resize: function () {
        var WH = {
            width: $(window).width(),
            height: $(window).height()
        };
        $('#layout').width(WH.width - 10).height(WH.height - 10).layout();
        var center = $('#layout').layout('panel', 'center');
        center.panel({
            onResize: function (w, h) {
                $('.GridView').datagrid('resize', { width: w - 6, height: h - 36 });
            }
        });
    },
    RefreshPanl: function () {
        MyGrid.Resize();
        $('#layout').layout('resize');
    },
    selectRow: function () {
        return $('.GridView').datagrid('getSelected');
    }
}

////搜索按钮,点击显示层
//$('#Search[title="搜索"]').live('click', function () {
//    //打开
//    $('.SearchForm').toggle('slow', function () {
//        var _h = $('.SearchForm').height();
//        var _h2 = $('#layout').height();
//        $('.GridView').datagrid('resize', { height: _h2 - _h - 40 });
//    });
//});

////搜索隐藏按钮，点击隐藏层
//$('.SearchHide').live('click', function () {
//    var s = createParam3('SearchForm');
//    //关闭
//    $('.SearchForm').slideUp('slow', function () {
//        var _h = $('#layout').height();
//        $('.GridView').datagrid('resize', { height: _h - 34 });
//    });

//});

//折叠刷新功能
var LayoutResize = {
    Resize: function () {
        var WH = {
            width: $(window).width(),
            height: $(window).height()
        }
        $('#layout').width(WH.width - 20).height(WH.height - 20).layout();
        var center = $('#layout').layout('panel', 'center');
    },
    RefreshPanl: function () {
        LayoutResize.Resize();
        $('#layout').layout('resize');
    }
};

$(function () {
    //变更时用的
    var $saveMainArr = [];

    getDataChangeInit();
    //首次显示 如果文本框单位里有值  去掉灰色的样式 
    removeStyle();
    //合计
    getSum();
    //文本框里单位的显示
    getUnit();
    //提醒还剩余多少字符
    remainderChar();

    //以下注释代码块为控制初第一个tbody后其他tbody全部隐藏
    //$("tbody .folding2").each(function (i, e) {
    //    if ($(this).hasClass('sjtop')) {
    //        if (i == 0) {
    //            $(this).removeClass('sjdown').addClass('sjtop').parents('tr:first').siblings().show();
    //        } else {
    //            $(this).removeClass('sjtop').addClass('sjdown').parents('tr:first').siblings().hide();

    //        }
    //    }
    //})

    //$("tbody .folding").each(function (i, e) {
    //    if ($(this).hasClass('sjtop')) {
    //        if (i == 0) {
    //            $(this).removeClass('sjdown').addClass('sjtop').parents('tbody:first').siblings().show();

    //        } else {
    //            $(this).removeClass('sjtop').addClass('sjdown').parents('tbody:first').siblings().hide();

    //        }
    //    }
    //})


    //收缩功能
    //第一种，一个table，一个多个tbody
    $('.folding2').live('click', function () {
        if ($(this).hasClass('sjtop')) {
            $(this).removeClass('sjtop').addClass('sjdown').parents('tr:first').siblings().not($(".noHide")).hide();
        }
        else {
            $(this).removeClass('sjdown').addClass('sjtop').parents('tr:first').siblings().not($(".noHide")).show();
        }
    });

    //第二种，多个table，多个tbody ，第一个tbody是标题行
    $('.folding').live('click', function () {
        if ($(this).hasClass('sjtop')) {
            $(this).removeClass('sjtop').addClass('sjdown').parents('tbody:first').siblings().not($(".noHide")).hide();
            //$('#Colyn table:first').nextAll().find('tr:first').css('border-top','none');
        }
        else {
            $(this).removeClass('sjdown').addClass('sjtop').parents('tbody:first').siblings().not($(".noHide")).show();
        }
    });

    //搜索按钮,点击显示层 再次点击影藏
    $('#Search[title="搜索"]').toggle(
        function () {
            //打开
            $('.SearchForm').slideDown('slow', function () {
                var _h = $('.SearchForm').height();
                var _h2 = $('#layout').height();
                $('.GridView').datagrid('resize', { height: _h2 - _h - 40 });
            });
        }, function () {
            var s = createParam3('SearchForm');
            //关闭
            $('.SearchForm').slideUp('slow', function () {
                var _h = $('#layout').height();
                $('.GridView').datagrid('resize', { height: _h - 34 });
            });
        }
    );

    //增加一行功能
    $('.AddRows').live('click', function () {


        var $this = $(this);
        var $tbody = $this.parents('tbody:first');
        var $thisTr = $this.parents('tr:first');
        var $html = $thisTr.clone(true, true);
        //序号
        var xh = $this.attr('lIndex');
        $this.attr('lIndex', parseInt(xh) + 1);
        $html.children('.xh').html(xh);

        //删除按钮
        $html.children('.edit').html('<img src="/Images/icon/16/bullet_minus.png" alt="" title="删除" class="DelRows" />');

        $tbody.append($html);

        $.parser.parse($("input").parent());
        // $thisTr.after($html);
        $html.children().each(function () {
            if (!$(this).hasClass('evt_unit')) {
                $(this).clear();
            } else {
                var $child = $(this).children();
                if ($child.hasClass('defaultUnit')) {
                    $child.addClass('gray');
                    $child.val($child.attr('_value'))
                }
            }
        });

        getSum();
    });

    $('.DelRows').unbind('click').live('click', function () {
        $(this).parents('tr:first').remove();
        getSum();
    });


    //增加一行功能
    $(".AddRows1").live("click", function () {
        var $this = $(this);
        var $table = $this.parents("table:first");
        var tle = $this.attr("trTemple");
        if (tle != undefined) {
            $html = $(trTemple[tle]);
        }
        //序号
        var addNum = $this.attr("lIndex");
        $this.attr("lIndex", parseInt(addNum) + 1);
        ////删除按钮
        $html.children().children(".edit").html('<img src="/Images/icon/16/bullet_minus.png" alt="" title="删除" class="DelRows1" />');
        $table.append($html);
        $.parser.parse($html);
        $html.children().each(function () {
            var $child = $(this).children();
            $child.clear();
            if ($child.hasClass("defaultUnit")) {
                $child.addClass("gray");
                $child.val($child.attr("_value"))
            }
        });
        var $tr = $(".evt-except");
        $tr.insertAfter($(".place:last"))
    });
    $(".DelRows1").bind("click").live("click", function () {
        $(this).parents("tbody:first").remove();
    });

    ///去除td中含有table时出现的多余的线条
    removeBorderInTable();

    $('.goto-next-node').live('click', function () {
        //保存跳到下一步
        var title = parent.$('.panel-title').html();;
        var treeObj = parent.$.fn.zTree.getZTreeObj('MenuTree');
        var sNodes = treeObj.getSelectedNodes();
        if (sNodes.length > 0) {
            var node = sNodes[0].getNextNode();
            if (node == null) {
                return;
            }
            treeObj.selectNode(node);
            parent.$('.panel-title').attr('title', node.name);
            parent.$('.layout-panel-center .panel-title').html(title + '【' + node.name + '】');
            var s = '<iframe id="contentIframe" scrolling="yes" frameborder="0"  style="width:100%;height:100%;" src="' + node.href + '" ></iframe>';
            parent.$('.ManageTemp').html(s);
        }
    });
});

///去除td中含有table时出现的多余的线条
function removeBorderInTable() {
    //去除多个table之间的边框
    $('#Colyn table:first').nextAll().find('tr:first').css('border-top', 'none');
    //$('#Colyn table:eq(1)').find('tr:first').css('background', 'red');
    //$('#Colyn table').css('background', 'red');

    //去除内table边框
    $('.neitable tr:first').css({ 'border-top': 'none' });
    $('.neitable td:last').css({ 'border-bottom': 'none' });
    $('.neitable tr:last').css({ 'border-bottom': 'none' });
    $('.neitable tr:last td:first').css({ 'border-bottom': 'none' });
    $('.neitable tr:first td:first').css({ 'border-left': 'none', 'border-right': 'none' });

    $('.neitable tr').find('td:first').css({ 'border-left': 'none' });
    $('.neitable tr').find('td:last').css({ 'border-right': 'none' });
    $('.neitable tr td:last').css({ 'border-right': 'none', 'border-left': 'none' });
    $('.neitable tr').css({ 'border-left': 'none', 'border-right': 'none' });
    $('.neitable').css('border', 'none');
}

///返回按钮提示，返回上级菜单
$('.icon-arrow_rotate_clockwise').live('mouseover', function () {
    $(this).attr('title', '返回上级菜单');
});

//tab页返回
$('.tabPageReturn').live('click', function () {
    var url = $('.tabPageReturn').attr('url');
    parent.location.href = url;
});

//文本域输入框里 还剩余多少字符。
function remainderChar() {
    $('.evt-keyup').live('keyup', function (event) {
        var numCount = $(this).attr('_length');
        var strText = '还可以输入';
        var haveText = getLength($(this).val());
        $(this).siblings('.showText').text(strText + (numCount - haveText) + '字');
        if (haveText > numCount) {
            $(this).siblings('.showText').text('已经不可以输入了...');
            var strValue = $(this).val();
            $(this).val(strValue.substring(0, numCount));
        }
    });
}
//中文字符和英文字符
function getLength(s) {
    var charLength = 0;
    for (var i = 0; i < s.length; i++) {
        var sonChar = s.charAt(i);
        encodeURI(sonChar).length > 2 ? charLength += 1 : charLength += 1;
    };
    return charLength;
}
//传入参数对象 返回连接参数 
function getParam(o) {
    var param = '';
    if (o == "unfinded" || o == "") {
        param = "";
    }
    for (var _o in o) {
        param += _o + "=" + encodeURI(o[_o]) + "&"
    }
    return param.substring(0, param.length - 1) + "&" + Math.random();
}
//文本输入框里单位的切换
function getUnit() {
    $('.cli_blu').live('focus', function () {
        if (this.value == $(this).attr("_value")) {
            this.value = '';
            $(this).removeClass('gray');
            //该class用于筛选含有默认单位的文本框
            $(this).addClass('defaultUnit');
            $(this).removeClass('grayleft');
        }
    });

    $('.cli_blu').live('blur', function () {
        if (this.value == '') {
            this.value = $(this).attr("_value");
            $(this).addClass('gray');
            if ((this.value.indexOf('请输入') > -1)) {
                $(this).addClass('grayleft');
            }
        }
    })
}

//合计的统计
function getSum() {
    var curValue = 0;
    var curSum = 0;
    $(".sum").each(function () {
        //当前每个合计
        var sum = 0;
        $("." + this.id).each(function () {
            var val = getTypeValue(this);
            sum += parseFloat((val == "" || isNaN(val)) ? a = "0" : a = val);
        });
        setTypeValue(this, sum);
    });
    $(".evt-sum").live("keyup", function () {
        var _id = $(this).attr("_id");
        var val = getTypeValue(this);
        var perValue = parseFloat((val == "" || isNaN(val)) ? a = "0" : a = val);
        var value = parseFloat(perValue - curValue);
        setTypeValue(document.getElementById(_id), curSum + value);
    });

    $(".evt-sum").bind("focus", function () {
        var val = getTypeValue(this);
        curValue = parseFloat((val == "" || isNaN(val)) ? a = "0" : a = val);
        var _id = $(this).attr("_id");
        curSum = parseFloat(getTypeValue(document.getElementById(_id)));
    });
}

/*****************************************************
数据变更抓取(针对于主表操作的抓取)
用法  在对应的页面上主表的tbody中加入class 为.mainDataChange 
在对应的js里调用getDataChange方法 
 使用方法 
getDataChangeInit()

变更的数据返回 直接获取 $saveMainArr
*****************************************************/
function getDataChangeInit() {
    ////第一次获取到的数组
    var $orArr = [];
    //修改后的数组
    var $modArr = [];
    $(".mainDataChange :input,.textarea-text").bind("focus", function () {
        var $obj = $(this);
        var objType = getObjType($obj);
        var obj = { name: '', value: '' };
        var count = 0;
        obj = getSelectObj($obj, objType);
        $.each($orArr, function (i, e) {
            if (e.name == obj.name) {
                count++;
                return false;
            }
        });
        if (count > 0) {

        } else {
            $orArr.push({
                name: obj.name,
                value: obj.value
            });
        }
        $saveMainArr = $modArr;
    });
    $(function () {
        $(".mainDataChange :input[type=hidden][name].havechange").each(function () {
            var $obj = $(this);
            var objType = getObjType($obj);
            var obj = { name: '', value: '' };
            var count = 0;
            obj = getSelectObj($obj, objType);
            $.each($orArr, function (i, e) {
                if (e.name == obj.name) {
                    count++;
                    return false;
                }
            });
            if (count > 0) {

            } else {
                $orArr.push({
                    name: obj.name,
                    value: obj.value
                });
            }
            $saveMainArr = $modArr;
        });
    });
    $("#editOrgBtn").click(function () {
        var lend = $("#gov_max_lend_money").val();
        var guar = $("#gov_max_guar_money").val();
        $(".havechange[name=gov_max_lend_money]").val(lend);
        $(".havechange[name=gov_max_guar_money]").val(guar);
        $(".mainDataChange :input[type=hidden][name].havechange").each(function () {
            var $obj = $(this);
            var objType = getObjType($obj);
            var obj = { name: '', value: '' };
            obj = getSelectObj($obj, objType);
            $modArr = getMainMethod($modArr, $orArr, obj, $obj);
            $saveMainArr = $modArr;
        });
        var removeArr = [];
        for (var i = 0; i < $saveMainArr.length; i++) {
            if ($saveMainArr[i].gov_filed_old == $saveMainArr[i].gov_fild_new) {
                removeArr.push($saveMainArr[i]);
            }
        }
        for (var i = 0; i < removeArr.length; i++) {
            $saveMainArr.splice($.inArray(removeArr[i], $saveMainArr), 1);
        }
    });
    $(".mainDataChange :input,.textarea-text").bind("change", function () {
        var $obj = $(this);
        var objType = getObjType($obj);
        var obj = { name: '', value: '' };
        obj = getSelectObj($obj, objType);
        $modArr = getMainMethod($modArr, $orArr, obj, $obj);
        $saveMainArr = $modArr;
    });
    $('.mainDataChange .easyui-datebox').datebox({
        onSelect: function (date) {
            var $obj = $(this);
            var obj = { name: '', value: '' };
            obj.name = $obj.parent().prev().text().split('：')[0];
            obj.value = $(this).datebox('getValue');
            $modArr = getMainMethod($modArr, $orArr, obj, $obj);
            $saveMainArr = $modArr;
        }
    });
    $saveMainArr = $modArr;
    return $saveMainArr;
}
//主要的判断的方法
function getMainMethod($modArr, $orArr, obj, $obj) {
    var count = 0;
    var count1 = 0;
    for (var i = 0; i < $modArr.length; i++) {
        if ($modArr[i].cre_ChangeItem == obj.name) {
            for (var j = 0; j < $orArr.length; j++) {
                if ($orArr[j].name == obj.name && $orArr[j].value == obj.value) {
                    count++;
                    break;
                }
            }
            if (count > 0)//和原来的第一次重复了
            {

            } else {
                $modArr[i].cre_ChangeItem = obj.name;
                $modArr[i].cre_ChangeContent = obj.value;
                count1++;//覆盖
            }
            break;
        }
    }

    //没有改变之前的那个数据
    if (count == 0 && count1 == 0 && $modArr.length != 0) {
        var arr = getModArr($orArr, obj, $obj);
        $.each(arr, function (i, e) {
            $modArr.push(e);
        });

    }

    if ($modArr.length == 0) {//第一次 加入
        var arr = getModArr($orArr, obj, $obj);
        $.each(arr, function (i, e) {
            $modArr.push(e);
        });

    }

    //变更后和第一次重复了
    if (count > 0) {
        var tempArr = [];
        //删除之前的变更的数据
        $.each($modArr, function (i, e) {
            if (e.cre_ChangeItem != obj.name) {
                tempArr.push(e);

            }
        });
        $modArr = [];
        $.each(tempArr, function (j, k) {
            $modArr.push({
                gov_filed_old: getObjCurContent($orArr, k.cre_ChangeItem),//修改前内容
                gov_fild_new: k.cre_ChangeContent,//变更后内容
                //cre_ChangeItem: k.cre_ChangeItem,//b变更项
                gov_filed: k.cre_DataField,//变更字段
                //cre_IsMainTable: 1//从表操作类型(主表) 
            });
        });
    }
    return $modArr;
}
//获取修改的对象
function getModArr($orArr, obj, $obj) {
    var $modArr = [];
    var cre_DataField;
    if ($obj.attr("name") == undefined) {
        cre_DataField = $obj.attr("comboname") == undefined ? $obj.attr("numberboxname") : $obj.attr("comboname");
    } else {
        cre_DataField = $obj.attr("name");
    }
    $modArr.push({
        gov_filed_old: getObjCurContent($orArr, obj.name),//修改前内容
        gov_fild_new: obj.value,//变更后内容
        //cre_ChangeItem: obj.name,//b变更项
        gov_filed: cre_DataField,//变更字段
        //cre_SubType: 1//从表操作类型
    });
    return $modArr;
}
//获取修改前对象的值
function getObjCurContent(obj, name) {
    var content;
    $.each(obj, function (i, e) {
        if (e.name == name) {
            content = e.value;
            return false;
        }
    });
    return content;
}
//获取类型
function getObjType(obj) {
    return obj.get(0).tagName.toLowerCase();
}
//获取选择的对象
function getSelectObj(obj, type) {
    var $obj = { name: '', value: '' };
    if (type == "input") {
        var objType = obj.attr("type");
        var cou = 0;
        var cou1 = 0;
        switch (objType) {
            case "radio": $.each(obj.parent().children(":radio"), function (i, e) {
                if ($(e).attr("checked") == "checked") {
                    $obj.value = $(e).val();
                    $obj.name = $($(e).prevAll("label").first()).text();
                    cou1++;
                }
            });
                if (cou1 == 0) {
                    $obj.value = "";
                    $obj.name = $($(obj).prevAll("label").first()).text();
                }
                break;
            case "checkbox": $.each(obj.parent().children(":checkbox"), function (i, e) {
                if ($(e).attr("checked") == "checked") {
                    $obj.value += $(e).val() + ",";
                    $obj.name = $($(e).prevAll("label").first()).text();
                    cou++;
                }
            });//表示什么都没有选
                if (cou == 0) {
                    $obj.value = "";
                    $obj.name = $($(obj).prevAll("label").first()).text();
                }
                break;
            case "text": if (obj.parent().attr("class") == "combo datebox") {
                $obj.value = obj.parent().children(".combo-value").val();
                $obj.name = obj.parent().parent().prev().text().split('：')[0];
            } else {
                $obj.value = obj.val(); $obj.name = obj.parent().prev().text().split('：')[0];
            }
                break;
            case "hidden":
                $obj.value = obj.val();
                $obj.name = obj.attr("name");
                break;
        }
    } else if (type == "select") {
        $.each(obj.children(), function (i, e) {
            if ($(e).attr("selected") == "selected") {
                $obj.value = $(e).val();
                $obj.name = $($(e).parent().parent().prev()).text().split('：')[0];
            }
        });
    } else if (type == "textarea") {
        $obj.value = obj.val();
        $obj.name = obj.prev().text() == "" ? obj.parent().first().text().split('：')[0] : obj.prev().text().split('：')[0];
        if ($obj.name.indexOf("可以输入") != -1) {
            $obj.name = obj.parent().prev().text();
            if ($obj.name == "") {
                $obj.name = obj.parent().parent().prev().find(".icon-bi").text();
            }
        }
    }

    return $obj;
}

//数据变更提交公共方法
function getAjaxDataChange(url, obj) {
    $.AjaxColynText(url, obj, function (data) {
        data = JSON.parse(data);
        Colyn.log(data.Msg);
        setTimeout(function () {
            if (data.Success == "True") {
                step();
            } else {

            }
        }, 1000);
    });
}

function chkRepeat(arr) {
    var hash = {};
    for (var i in arr) {
        if (hash[arr[i]]) {
            return true;
        }
        // 不存在该元素，则赋值为true，可以赋任意值，相应的修改if判断条件即可
        hash[arr[i]] = true;
    }
    return false;
}

function getParamImgObj(arr) {
    if (arr.length > 0)
        return "&imgObj=" + JSON.stringify(imgObj);
    else
        return "";
}

//审核返回之前的页面
function auditPageReturn(href) {
    setTimeout(function () {
        parent.window.location.href = href;
    }, 800)

}
/*
场景 ：根据标签对象类型 取值
*/
function getTypeValue(obj) {
    if (obj.tagName.toLowerCase() == "label") {
        return obj.innerText;
    } else if (obj.tagName.toLowerCase() == "input") {
        return $(obj).val();
    } else {
        return $(obj).val();
    }
}
/*
场景 ：场景 ：根据标签对象类型 赋值
*/
function setTypeValue(obj, val) {
    if (obj.tagName.toLowerCase() == "label") {
        $(obj).html(val);
    } else if (obj.tagName.toLowerCase() == "input") {
        obj.value = val;
    } else {
        $(obj).html(val);
    }
}
//首次显示 如果文本框单位里有值  去掉灰色的样式 
function removeStyle() {
    $('.cli_blu').each(function () {
        if (this.value == "") {
            setTypeValue($(this).get(0), $(this).attr("_value"));
        } else {
            if (this.value != $(this).attr("_value")) {
                $(this).removeClass('gray');
                $(this).removeClass('grayleft');
            }
        }
    });
}

//客户信息列表页中链接
function CustOnClickEvt(cin_Id, cin_NO) {
    return "<a href='#' onclick =\"CustDetail('" + cin_Id + "')\">" + cin_NO + "</a>";
}

//列表页中，点击链接查看客户信息的详情
function CustDetail(CustId) {
    var href = "/DBCustManage/CustInfoLookUpView?cinId=" + CustId;
    var editDialog = $.hDialog({
        content: "<iframe src='" + href + "' height='" + ($(window).height() - 78) + "' width='" + ($(window).width() - 20) + "'  frameborder='0'  scrolling='no' ></iframe>",
        iconCls: 'icon-add',
        title: "客户详细信息",
        width: $(window).width(),
        height: $(window).height(),
        buttons: [
            {
                text: '返回',
                iconCls: 'icon-cancel',
                handler: function () {
                    editDialog.dialog("close");
                }
            }]
    })
}

//上传影像资料
function getUpload(imgObj, classId, className, dataId, mess1, mess2) {
    var imgarr = [];
    $.each(imgObj, function (i, e) {

        e.file_DataId = dataId;
        e.file_ClassId = classId;
        e.file_ClassName = className;
        e.file_CreateTime = CurrentTime();
        imgarr.push(e);
    });
    var imgdata = getParamImgObj(imgarr);
    if (imgdata != "") {
        $.post("/P2PUC/FileUploadData", imgdata, function (data) {

            if (data.Success) {
                Colyn.log(mess1);
            } else {
                Colyn.log(mess2);
            }
        }, 'json');
    } else {
        Colyn.log(mess1);
    }
}

//从数据库获取表的字段状态，判断是否能进行审核之类的操作
function getDataStatus(keyId, keyName, tableName, fieldName, fieldValue) {
}
function getParamImgObj(arr) {
    if (arr.length > 0)
        return "&imgObj=" + JSON.stringify(imgObj);
    else
        return "";
}

//获取当前时间
function CurrentTime() {
    var now = new Date();

    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日

    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分

    var clock = year + "/";

    if (month < 10)
        clock += "0";

    clock += month + "/";

    if (day < 10)
        clock += "0";

    clock += day + " ";

    if (hh < 10)
        clock += "0";

    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm;
    return clock;
}

//获取系统当前日期 by 徐大龙 time：2014-11-10 18:20:53
function CurrentDate(data) {
    var now = new Date(Date.parse(data.replace(/-/g, "/")));
    //var now = new Date();

    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
    if (month < 10)
        month = "0" + month;
    if (day < 10)
        day = "0" + day;
    return year.toString() + month.toString() + day.toString();
}

//担保业务列表页中链接
function GuarOnClickEvt(cin_Id, cin_NO) {
    return "<a href='#' onclick =\"GuarDetail('" + cin_Id + "')\">" + cin_NO + "</a>";
}

//担保业务查看详情
function GuarDetail(ginId) {
    var href = "/DBCustManage/CustInfoLookUpView?cinId=" + ginId;
    var editDialog = $.hDialog({
        content: "<iframe src='" + href + "' height='" + ($(window).height() - 78) + "' width='" + ($(window).width() - 20) + "'  frameborder='0'  scrolling='no' ></iframe>",
        iconCls: 'icon-add',
        title: "客户详细信息",
        width: $(window).width(),
        height: $(window).height(),
        buttons: [
            {
                text: '返回',
                iconCls: 'icon-cancel',
                handler: function () {
                    editDialog.dialog("close");
                }
            }]
    })
}

//功能：将浮点数四舍五入，取小数点后2位,但是如果小于2位的，小数点后补0
function changeTwoDecimal(x) {
    var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        Colyn.log('function:changeTwoDecimal->parameter error');
        return false;
    }
    f_x = Math.round(f_x * 100) / 100;
    var s_x = f_x.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = s_x.length;
        s_x += '.';
    }
    while (s_x.length <= pos_decimal + 2) {
        s_x += '0';
    }
    return s_x;
}

//截取字符串
function CutString(conStr, len) {
    if (conStr == null)
        return "";
    var realLength = conStr.length;
    if (realLength > len) {
        var newStr = conStr.substr(0, len) + "....";
        return newStr + " <img title='" + conStr + "' src='/Images/icon/16/search.png'/>";
    }
    else
        return conStr;
}

//获取性别
function GetSex(sex) {
    if (sex == null)
        return "";
    if (sex == 0)
        return "女";
    else
        return "男";
}

//获取审核状态
function GetStatus(status) {
    if (status == null || status == 0)
        return "待审核";
    if (status == 1)
        return "审核已通过";
    if (status == 2)
        return "审核未通过";
}

//获取项目审核状态 1：同意 2：退回 3：否决

function GetProAuditStatus(status) {
    if (status == 1)
        return "同意";
    if (status == 2)
        return "退回";
    if (status == 3)
        return "否决";
    return "";
}

//获取邮箱激活状态
function GetEmailStatus(status) {
    if (status == null || status == 0)
        return "未激活";
    if (status == 1)
        return "已激活";
}

//获取手机认证状态
function GetMobileStatus(status) {
    if (status == null || status == 0)
        return "未认证";
    if (status == 1)
        return "已认证";
}

//获取格式化的时间
function GetFormatDate(date) {
    if (date == null)
        return "";
    else
        return convertToDate(date, "yyyy-MM-dd");
}


(function ($, w) {
    //动态添加行模板 - 处理日期错误
    w.trTemple = {
        //项目申请-汽车抵押贷 汽车信息模板
        tr2: '<tbody  class="carInfo"><tr class="stop"><td colspan="6" class="lefts edit"><img src="/Images/icon/16/bullet_plus.png" alt="新增" title="添加" class="AddRowsCar" trtemple="tr2" lindex="2" /></td></tr>'
            + '<tr><td class="leftTD">汽车品牌：</td><td class="rightTD"><input type="hidden" id="pro_car_id" name="pro_car_id" value="0" />'
            + '<input type="text" class="text-input w80 easyui-validatebox" data-options="required:true" name="pro_car_brand" /></td>'
            + '<td class="leftTD">汽车车系：</td><td class="rightTD"><input type="text" class="text-input w80" name="pro_car_type" /></td>'
            + '<td class="leftTD">汽车排量：</td><td class="rightTD"><input type="text" class="text-input w80 easyui-validatebox" data-options="required:false,validType:[\'IntOrFloat\']" name="pro_car_output" /></td></tr>'
            + '<tr><td class="leftTD">汽车颜色：</td><td class="rightTD"><input type="text" class="text-input w80" name="pro_car_color" /></td>'
            + '<td class="leftTD">购买年份：</td><td class="rightTD"><input type="text" class="text-input w80 easyui-validatebox" data-options="required:false,validType:[\'IntYear\']" name="pro_buy_year" /></td>'
            + '<td class="leftTD">上牌日期：</td><td class="rightTD"><input type="text" class="text-input easyui-datebox w80" name="pro_card_year" /></td></tr>'
            + '<tr><td class="leftTD">里程数：</td><td class="rightTD"><input type="text" class="text-input w80 easyui-validatebox" data-options="required:false,validType:[\'IntOrFloat\']" name="pro_car_run" /></td>'
            + '<td class="leftTD">评估价格：</td><td class="rightTD"><input type="text" class="text-input w80 easyui-validatebox" data-options="required:false,validType:[\'Money\']" name="pro_assess_money" /></td>'
            + '<td class="leftTD">汽车现址：</td><td class="rightTD"><input type="text" class="text-input w80" name="pro_car_address" /></td></tr></tbody>',
        //项目申请-房产抵押贷 房产信息模板
        tr3: '<tbody class="houseInfo"><tr><td colspan="6" class="lefts edit"><img src="/Images/icon/16/bullet_plus.png" alt="新增" title="添加" class="AddRowsHouse" trtemple="tr2" lindex="2" /></td></tr>'
            + '<tr><td class="leftTD">房屋面积：</td><td class="rightTD"><input type="hidden" id="pro_house_id" name="pro_house_id" value="0" /><input type="text" class="text-input w80 easyui-validatebox" data-options="required:true,validType:[\'IntOrFloat\']" name="pro_house_measure" /></td>'
            + '<td class="leftTD">占地面积：</td><td class="rightTD"><input type="text" class="text-input w80 easyui-validatebox" data-options="validType:[\'IntOrFloat\']" name="pro_cover_measure" /></td>'
            + '<td class="leftTD">房产证号：</td><td class="rightTD"><input type="text" class="text-input w80 easyui-validatebox" data-options="required:true" name="pro_house_no" /></td></tr>'
            + '<tr><td class="leftTD">所在小区：</td><td class="rightTD"><input type="text" class="text-input w80 easyui-validatebox" data-options="required:true" name="pro_house_area" /></td>'
            + '<td class="leftTD">房龄：</td><td class="rightTD"><input type="text" class="text-input w80 easyui-validatebox" data-options="validType:[\'IntOrFloat\']" name="pro_house_age" /><span>年</span></td>'
            + '<td class="leftTD">是否按揭：</td><td class="rightTD"><select class="text-input w80" name="pro_house_mortgage"><option value="false">否</option><option value="true">是</option></select></td></tr>'
            + '<tr><td class="leftTD">评估价格：</td><td class="rightTD" colspan="5"><input type="text" class="text-input w80 easyui-validatebox" data-options="validType:[\'Money\']" name="pro_assess_money" /></td></tr></tbody>'
    }
})(jQuery, window);

//根据字段判断是否 1：是 0：否
function GetIsOrNot(status) {
    if (status != null) {
        if (status == 0)
            return "否";
        if (status == 1)
            return "是";
    }
    return "";
}

//获取还款状态 1：正常还款 2：平台代还 3：强制还款
function GetPayStatus(status) {
    if (status != null) {
        if (status == 1)
            return "正常还款";
        if (status == 2)
            return "平台代还";
        if (status == 3)
            return "强制还款";
    }
    return "";
}

//获取还款方式 1：等额本息 2：按月还息到期还本 3：等额本金
function GetPayType(paytype) {
    if (paytype != null) {
        if (paytype == 1)
            return "等额本息";
        if (paytype == 1)
            return "按月还息到期还本";
        if (paytype == 2)
            return "等额本金";
    }
    return "";
}

//获取项目状态 0：待审核 1：待平台审核 2：待担保审核 3：已退回 4：已否决 5：待发布、6：招标中 7：满标待审 8：已流标 9：待划转 10：还款中 11：已结清
function GetProStatus(paytype, prostatus) {
    if (paytype != null) {
        if (paytype == 0)
            return "待审核";
        if (paytype == 1)
            return "待平台审核";
        if (paytype == 2)
            return "已退回";
        if (paytype == 3)
            return "已否决";
        if (paytype == 4)
            return "已否决";
        if (paytype == 5)
            return "待发布";
        if (paytype == 6)
            return "招标中";
        if (paytype == 7)
            return "满标待审";
        if (paytype == 8)
            return "已流标";
        if (paytype == 9)
            return "待划转";
        if (paytype == 10)
            return "还款中";
        if (paytype == 11)
            return "已结清";
        if (paytype == 21)
            return "同意";
        if (paytype == 22)
            return "退回";
        if (paytype == 23)
            return "否决";
        //0：待审核 1：同意 2：退回 3：否决
        if (paytype == 20)
            return "待审核";
        //if (paytype == 31)
        //    return "同意";
        //if (paytype == 32)
        //    return "退回";
        //if (paytype == 33)
        //    return "否决";
    }
    return "待发送";
}

//期限类型 1：年 2：月 3：日
function GetPayType(period, periodtype) {
    if (period != "" || period != null) {
        if (periodtype != null || periodtype != "") {
            if (periodtype == 1)
                return period + "年";
            if (periodtype == 2)
                return period + "个月";
            if (periodtype == 3)
                return period + "日";
        }
    }
    else
        return "-";
}

//获取所属行业
function GetIndustry(value) {
    if (value != null) {
        switch (value) {
            case "a":
                return "农、林、牧、渔业";
            case "b":
                return "采矿业";
            case "c":
                return "制造业";
            case "d":
                return "电力、燃气及水的生产和供应业";
            case "e":
                return "建筑业";
            case "f":
                return "交通运输、仓储和邮政业";
            case "g":
                return "信息传输、计算机服务和软件业";
            case "h":
                return "批发和零售业";
            case "i":
                return "住宿和餐饮业";
            case "j":
                return "金融业";
            case "k":
                return "房地产业";
            case "l":
                return "租赁和商务服务业";
            case "m":
                return "科学研究、技术服务和地质勘查业";
            case "n":
                return "水利、环境和公共设施管理业";
            case "o":
                return "居民服务和其他服务业";
            case "p":
                return "教育";
            case "q":
                return "卫生、社会保障和社会福利业";
            case "r":
                return "文化、体育和娱乐业";
            case "s":
                return "公共管理和社会组织";
            case "t":
                return "国际组织";
            default:
                return "-";
        }
    }
    return "";
}


var otpions = {
    toolbars: [[
       'source', '|',
        'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'removeformat', '|',
        'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', '|',
        'fontfamily', 'fontsize', '|', 'indent', '|', 'justifyleft', 'justifycenter',
        'justifyright', 'justifyjustify', '|', 'link', 'unlink', '|', 'horizontal', 'spechars', '|',
        'inserttable', 'deletetable', 'mergecells', 'splittocells', 'simpleupload', 'insertimage'
    ]],
    initialFrameWidth: '99%',
    initialFrameHeight: 150,
    scaleEnabled: true
}





