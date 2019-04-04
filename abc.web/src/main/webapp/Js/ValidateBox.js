/*************************************************************************************
By Add Author:王群林 Date:2014-6-4 10:52:40
例：
    <input id="vv" class="easyui-validatebox" data-options="required:true,validType:'CHS'" />
    <input id="vv" class="easyui-validatebox" data-options="required:true,validType:['email','length[0,20]']" /> 多重验证

missingMessage：未填写时显示的信息
invalidMessage：无效的数据类型时显示的信息
class="easyui-validatebox" 文本验证
class="easyui-numberbox" 数字验证
required:true 必填

以实现规则如下：
email：匹配 email 正则表达式规则
url：匹配 URL 正则表达式规则
length[0,100]：允许从0到100个字符
remote['http://.../action.do','paramName']：发送 ajax 请求来验证值，成功时返回 'true' 。

**************************************************************************************/


//扩展exsyui验证插件的$.fn.validatebox.defaults.rules方法
$.extend($.fn.validatebox.defaults.rules, {
    //银行卡
    Card: {
        validator: function (value) {
            return /^\d{16}|\d{19}$/.test(value);
        },
        message: '银行卡格式不正确'
    },
    //验证汉字
    CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '只能输入汉字'
    },
    //只能输入汉子字母和数字
    CHSLK: {
        validator: function (value) {
            return /^[A-Za-z0-9\u4e00-\u9fa5]+$/.test(value);
        },
        message: '只能输入汉字、字母和数字'
    },
    //移动手机号码验证
    Mobile: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1[0-9]{10}$/;
            return reg.test(value);
        },
        message: '输入手机号码格式不准确.'
    },
    //开户账户验证
    Account: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message: '输入开户账户格式不准确，例如：13288888888'
    },
    //验证固定电话号码
    Phone: {
        validator: function (value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '格式不正确,请使用下面格式:020-88888888'
    },
    //验证整数或小数 
    IntOrFloat: {
        validator: function (value, param) {
            if (param != undefined) {
                var reg = new RegExp("^\\d+(\\.\\d{0," + param[0] + "})?$", "i");
                //$.fn.validatebox.defaults.rules.IntOrFloat.message = "小数位数错误,请检查";
                return reg.test(value);
            }
            else
                return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '请输入整数或小数，并确保格式正确'
    },
    //验证金额 整数位最多十位，小数为最多为两位，可以无小数位
    Money: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
            return reg.test(value);
        },
        message: '输入金额不合法'
    },
    //验证金额 整数位最多18位，小数为最多为两位，可以无小数位
    Moneyx: {//value值为文本框中的值
    	validator: function (value) {
    		var reg = /^(([0-9]|([1-9][0-9]{0,17}))((\.[0-9]{1,2})?))$/;
    		return reg.test(value);
    	},
    	message: '输入金额不合法'
    },
    //验证金额 可为负数
    Moneyy: {
        validator: function (value) {
            var reg = /^-?(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
            return reg.test(value);
        },
        message: '输入金额不合法'
    },
    NumbersDot: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
            return reg.test(value);
        },
        message: '整数位最多十位，小数为最多为两位，可以无小数位.'
    },
    //验证整数
    Int: {
        validator: function (value) {
            return /^\d+$/i.test(value);
        },
        message: '请输入整数，并确保格式正确'
    },
    //验证正整数
    UnsignedInt: {
        validator: function (value) {
            return /^([0-9]|([1-9][0-9]{0,8}))$/i.test(value);
        },
        message: '请输入正整数，并确保格式正确'
    },
    //验证整数年份
    IntYear: {
        validator: function (value) {
            return /^(19[\d][\d]|20[\d][\d])$/.test(value);
        },
        message: '请输入年份，并确保格式正确'
    },
    //验证QQ,从10000开始
    QQ: {
        validator: function (value) {
            return /^[1-9]\d{4,10}$/i.test(value);
        },
        message: 'QQ号码格式不正确'
    },
    //验证邮箱
    Email: {
        validator: function (value) {
            return /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/i.test(value);
        },
        message: '邮箱格式不正确'
    },

    //验证只能输入大于0的整数
    IsNum: {
        validator: function (value) {
            return /^[1-9]{1}[\d]*$/.test(value);
        },
        message: '只能输入大于0的整数'
    },
    //两个日期比较
    compareDatess: {
        validator: function (value, param) {
            if ($("#" + param).val() == "") {
                param = $("#" + param).datebox("getValue");
            } else {
                param = $("#" + param).val();
            }
            //alert(value + "ddd" + param);
            if (param == null || param.length == 0 || param[0] == null || param[0] == "") {
                return true;
            }
            if (value > param) {

                return true;

            } else {
                return false;
            }
        },
        message: '结束日期应该大于开始日期'
    },
   //两个日期的普判断，普通日期和带有时分秒的日期普安段
    compareDate: {
        validator: function (value, param) {
            if ($("#" + param).val() == "") {
                param = $("#" + param).datebox("getValue");
            } else {
                param = $("#" + param).val();
            }
            if (param == null || param == "") {
                return true;
            }
            var date = new Date(Date.parse(param.replace(/-/g, "/")));
            param = date.format("yyyy-MM-dd hh:mm:ss");
            if (param > value) {

                return true;

            } else {
                return false;
            }
        },
        message: '结束日期应该大于开始日期'
    },
    //大于指定的日期
    moreThanDate: {
        validator: function (value, param) {
        	if (param == null || param == "") {
                return true;
            }
            param = $("input[name='"+param[0]+"']").val();
            if (param == null || param == "") {
                return true;
            }
            var date = new Date(Date.parse(param.replace(/-/g, "/")));
            param = date.format("yyyy-MM-dd hh:mm:ss");
            return value > param;
        },
        message: '{1}'
    },
  //两个日期比较
    compareDatesss: {
        validator: function (value, param) {
        	
            if ($("#" + param).val() == "") {
                param = $("#" + param).datebox("getValue");
            } else {
                param = $("#" + param).val();
            }
           
            if (param == null || param.length == 0 || param[0] == null || param[0] == "") {
                return true;
            }
           
            if (value > param) {

                return true;

            } else {
                return false;
            }
        },
        message: '招标到期日应该在到期日之前'
    },
    //国内邮编验证
    ZipCode: {
        validator: function (value) {
            var reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编必须是非0开始的6位数字.'
    },
    //最小长度
    MinLength: {
        validator: function (value, param) {
            return value.length >= param[0];
        },
        message: '长度不能小于{0}个字符'
    },
    //最大长度
    MaxLength: {
        validator: function (value, param) {
            return param[0] >= value.length;
        },
        message: '请输入最大{0}位字符.'
    },
    //长度范围   /*增加者：吕小东；增加时间：2014年9月17日17:53:26*/
    LengthArea: {
        validator: function (value, param) {
            return value.length >= param[0] && value.length <= param[1];
        },
        message: '请输入{0}-{1}位字符.'
    },
    /*
    验证身份证 增加者：徐大龙；增加时间：2014年11月8日17:37:29
    */
    IdCard: {
        validator: function (value) {
            var card = value;
            //校验长度，类型
            if (isCardNo(card) === false) {
                return false;
            }
            //检查省份
            if (checkProvince(card) === false) {
                return false;
            }
            //校验生日
            if (checkBirthday(card) === false) {
                return false;
            }
            //检验位的检测
            if (checkParity(card) === false) {
                return false;
            }
            return true;

        },
        message: '身份证号码格式不正确'
    },
    //验证年龄
    Age: {
        validator: function (value) {
            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
        },
        message: '年龄必须是0到120之间的整数'
    },
    //验证英文 
    English: {
        validator: function (value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: '请输入英文'
    },
    //验证用户名 
    UserName: {
        validator: function (value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        },
        message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
    },
    //异步校验用户名
    CheName: {
        validator: function (value, param) {
            var data0 = false;
            if (value.length >= param[0] && param[1] >= value.length) {
                $.ajax({
                    type: "POST",
                    async: false,
                    url: "ValidateTest.aspx",
                    dataType: "text",
                    data: { "txtName": value },
                    async: false,
                    success: function (data) {
                        //后台返回true则用户名存在
                        data0 = !data;
                    }
                });
            } else {
                param[2] = "请输入" + param[0] + "-" + param[1] + "位字符.";
                return false;
            }

            param[2] = "用户名称已存在.";
            return data0;
        },
        message: "{2}"
    },
    //异步校验数据
    SycData: {
        /*
            第一个参数:表名
            第二个参数:列名
            第三个参数:where条件
            第四个参数：错误提示信息
            第五个参数：存放修改时需要验证的文本框的初始值
        */
        validator: function (value, param) {
            param.push("")
            var result = false;
            var valid = $(param[4]).val();
            if (valid == value)
                result = true;
            else {
                if (param[2].indexOf("{0}")) {
                    param[2] = param[2].replace("{0}", $("input[name='" + $(param[4]).attr("parentId") + "'").val());
                }
                $.ajax({
                    type: "POST",
                    async: false,
                    url: "/common/asyncRequestHandler.json",
                    dataType: "json",
                    data: { "tbName": param[0], 'colName': param[1], 'vData': value, 'vWhere': param[2], 'message': param[3] },
                    async: false,
                    success: function (data) {
                        var d = data.success;
                        if (data.success == false) {
                            param[5] = data.message;
                        }
                        else
                            result = true;
                    }
                });
            }
            return result;
        },
        message: '{5}'
    },
    //异步根据月份给年化收益率赋值
    SetRate: {
        /*
            第一个参数：需要赋值的文本ID
            第二个参数：产品类别ID
            第三个参数：没有找到对应年化收益率提示信息
        */
        validator: function (value, param) {

            var result = false;
            $.ajax({
                type: "POST",
                async: false,
                url: "/Ashx/SetRate",
                dataType: "json",
                data: { "pId": param[1], 'period': value },
                async: false,
                success: function (data) {
                    var d = data.success;
                    if (data.Success == true) {
                        $(param[0]).val(data.Msg);
                        result = true;
                    }
                    else {
                        $(param[0]).val("");
                        result = false;
                    }
                }
            });
            return result;
        },
        message: '{2}'
    },
    ChkRepeat: {
        validator: function (value, param) {
            var flag = true;
            var arr = [];
            var j = 0;
            $(".evt-chkEqual").each(function (i, v) {
                arr.push(v.value);
            });
            if (chkRepeat(arr)) {
                flag = false;
            }
            return flag;
        },
        message: "不能输入重复值"
    },
    aaabbbfff: {
        validator: function (value, param) {
            var flag = true;
            if (parseFloat(value) > parseFloat($(param[0]).val())) {
                param[1] = "不能大于标准分！";
                flag = false;
            }

            return flag;
        },
        message: "{1}"
    },
    //验证IP地址 
    Ip: {
        validator: function (value) {
            return /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/.test(value);
        },
        message: 'IP地址格式不正确'
    },
    //验证特殊字符
    CheckQuote: {
        validator: function (value) {
            //var items = new Array("~", "`", "!", "#", "$", "%", "^", "&", "*", "{", "}", "[", "]", "(", ")");
            var items = new Array();
            //items.push(":", ";", "'", "|", "\\", "<", ">", "?", "<<", ">>", "||", "//");
            //items.push("admin", "administrators", "administrator", "管理员", "系统管理员");
            //items.push("select", "delete", "update", "insert", "create", "drop", "alter", "trancate");
            value = value.toLowerCase();
            for (var i = 0; i < items.length; i++) {
                if (value.indexOf(items[i]) >= 0) {
                    return false;
                }
            }
            return true;
        }, message: '输入特殊字符,请检查后输入'
    },
    //必须和某个字段相等
    EqualTo: {
        validator: function (value, param) {
            return $(param[0]).val() == value;
        },
        message: '两次输入的密码不匹配'
    },
    //必须小于前一个值(前值为空则不验证）
    ValueLessThan: {
        validator: function (value, param) {
            //param[0]为需要对比控件的id
            var t = $("#" + param[0]).val();
            //第三个参数是否为空 为空验证日期，不为空验证float
            if (param[2] == "") {
                var ts = $("#" + param[0]).datebox("getValue");
                if (ts != "") {
                    var time = new Date(Date.parse(ts.replace(/-/g, "/")));
                    var newtime = new Date(Date.parse(value.replace(/-/g, "/")));
                    return time <= newtime;
                } else {
                    return true;
                }
            } else {
                if (t != "") {
                    var f = parseFloat(t);
                    return f <= parseFloat(value);
                } else {
                    return true;
                }
            }
        },
        message: '{1}结束值不能小于{1}开始值'
    },
    ValueLessThan1: {
        validator: function (value, param) {
            //param[0]为需要对比控件的id
            var t = $("input[name='"+param[0]+"']").val();
            //第三个参数是否为空 为空验证日期，不为空验证float
            if (param[2] == "") {
                var ts = $("input[name='"+param[0]+"']").datebox("getValue");
                if (ts != "") {
                    var time = new Date(Date.parse(ts.replace(/-/g, "/")));
                    var newtime = new Date(Date.parse(value.replace(/-/g, "/")));
                    return time <= newtime;
                } else {
                    return true;
                }
            } else {
                if (t != "") {
                    var f = parseFloat(t);
                    return f <= parseFloat(value);
                } else {
                    return true;
                }
            }
        },
        message: '{1}结束值不能小于{1}开始值'
    },

    //必须大于当前日期 by 徐大龙 Time：2014-11-10
    MoreThanToday: {
        validator: function (value, param) {
            //获取当前日期
            var date = new Date();
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            var time = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
            //转换成时间
            var time = new Date(Date.parse(time.replace(/-/g, "/")));
            var newtime = new Date(Date.parse(value.replace(/-/g, "/")));
            return time < newtime;
        },
        message: '此项必须大于当前日期'
    },
    //必须大于等于当前日期
    MoreThanEqToday: {
        validator: function (value, param) {
        	if(null!=param){
        		//console.log("param:"+param);
        		value=$("input[name='"+param[0]+"']").val();
        		//console.log("input[name]-value:"+value);
        	}
            //获取当前日期
            var date = new Date();
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            var time = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
            //转换成时间
            var time = new Date(Date.parse(time.replace(/-/g, "/")));
            var newtime = new Date(Date.parse(value.replace(/-/g, "/")));
            return time <= newtime;
        },
        message: '此项必须大于等于当前日期'
    },
    //必须大于
    MoreThan: {
        validator: function (value, param) {
            if ($("#" + param[0]).val() != "") {
                return parseFloat($("#" + param[0]).val()) <= parseFloat(value);
            } else {
                return true;
            }
        },
        message: '{1}'
    },
    //必须大于
    MoreThanValue: {
        validator: function (value, param) {
            return parseFloat(param[0]) < parseFloat(value);
        },
        message: '{1}'
    },
    LessTenThan: {
        validator: function (value) {
            return parseFloat(10000) >= parseFloat(value);
        },
        message: '审核额度不可大于1万.'
    },
    //必须小于
    LessThan: {
        validator: function (value, param) {
            if ($(param[0]).val() != "") {
                return parseFloat($(param[0]).val()) >= parseFloat(value);
            } else {
                return true;
            }
        },
        message: '{1}'
    },
    //必须小于 值等于""的时候不判断
    LessThanValue: {
        validator: function (value, param) {
            if ($(param[0]).val() != "") {
                return parseFloat($(param[0]).val()) >= parseFloat(value);
            } else {
                return true;
            }
        },
        message: '{1}'
    },

    //验证日期
    Date: {
        validator: function (value) {
            return /^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$/i.test($.trim(value));
        },
        message: '曰期格式错误,如2012-09-11.'
    },
    //输入范围验证
    Range: {
        validator: function (value, param) {
            return value >= param[0] && value <= param[1];
        },
        message: '请输入{0}到{1}之间的数'
    },
    //过滤特殊字符
    IllegalChar: {
        validator: function (value, param) {
            var pattern = /[`~!@#$%^&*()-+<>?:"{},.\/;'[\]]/im;
            if (pattern.test(value)) {
                return false;
            }
            return true;
        },
        message: '不能输入特殊字符'
    },
    //过滤特殊字符，不允许输入%&
    IllegalCharU: {
        validator: function (value, param) {
            var pattern = /[&]/im;
            if (pattern.test(value)) {
                return false;
            }
            return true;
        },
        message: '不能输入特殊字符'
    },
    //验证组织机构代码
    EntpCode: {
        validator: function (value, param) {
            var ws = [3, 7, 9, 10, 5, 8, 4, 2];
            var str = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
            var reg = /^([0-9A-Z]){8}-[0-9|X]$/;
            if (!reg.test(value)) {
                return false;
            }
            var sum = 0;
            for (var i = 0; i < 8; i++) {
                sum += str.indexOf(value.charAt(i)) * ws[i];
            }
            var C9 = 11 - (sum % 11);
            if (C9 == 11) {
                C9 = '0';
            } else if (C9 == 10) {
                C9 = 'X';
            } else {
                C9 = C9;
            }
            if (C9 != value.substring(9, 10)) {
                return false;
            }
            return true;
        },
        message: '组织机构代码格式不正确'
    },
    //王武验证板块名称
    Repeat: {
        validator: function (value, param) {
            var result = false;
            $.ajax({
                type: "POST",
                url: "/P2PForumPlate/P2PForumPlateName",
                dataType: "json",
                data: { "new_plate_name": $(param[0]).val(), new_plate_id: param[1] },
                async: false,
                success: function (data) {
                    if (data.Success) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            });
            return result;
        },
        message: '板块名称已存在'
    },
    RepeatActBankNo: {
        validator: function (value, param) {
            var result = false;
            $.ajax({
                type: "POST",
                url: "/loan/RepeatActBankNo",
                dataType: "json",
                data: { "mon_bank_card": value, mon_bank_id: param[0]},
                async: false,
                success: function (data) {
                    if (data.Success) {
                        result = false;
                    } else {
                        result = true;
                    }
                }
            });
            return result;
        },
        message: '银行卡已存在'
    }
});


//检查号码是否符合规范，包括长度，类型
isCardNo = function (card) {
    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
    var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
    if (reg.test(card) === false) {
        return false;
    }

    return true;
};

//取身份证前两位,校验省份
checkProvince = function (card) {
    var vcity = {
        11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古",
        21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏",
        33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南",
        42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆",
        51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃",
        63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外"
    };
    var province = card.substr(0, 2);
    if (vcity[province] == undefined) {
        return false;
    }
    return true;
};

//检查生日是否正确
checkBirthday = function (card) {
    var len = card.length;
    //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
    if (len == '15') {
        var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
        var arr_data = card.match(re_fifteen);
        var year = arr_data[2];
        var month = arr_data[3];
        var day = arr_data[4];
        var birthday = new Date('19' + year + '/' + month + '/' + day);
        return verifyBirthday('19' + year, month, day, birthday);
    }
    //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
    if (len == '18') {
        var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
        var arr_data = card.match(re_eighteen);
        var year = arr_data[2];
        var month = arr_data[3];
        var day = arr_data[4];
        var birthday = new Date(year + '/' + month + '/' + day);
        return verifyBirthday(year, month, day, birthday);
    }
    return false;
};

//校验日期
verifyBirthday = function (year, month, day, birthday) {
    var now = new Date();
    var now_year = now.getFullYear();
    //年月日是否合理
    if (birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day) {
        //判断年份的范围（3岁到100岁之间)
        var time = now_year - year;
        if (time >= 3 && time <= 100) {
            return true;
        }
        return false;
    }
    return false;
};

//校验位的检测
checkParity = function (card) {
    //15位转18位
    card = changeFivteenToEighteen(card);
    var len = card.length;
    if (len == '18') {
        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
        var cardTemp = 0, i, valnum;
        for (i = 0; i < 17; i++) {
            cardTemp += card.substr(i, 1) * arrInt[i];
        }
        valnum = arrCh[cardTemp % 11];
        if (valnum == card.substr(17, 1)) {
            return true;
        }
        return false;
    }
    return false;
};

//15位转18位身份证号
changeFivteenToEighteen = function (card) {
    if (card.length == '15') {
        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
        var cardTemp = 0, i;
        card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
        for (i = 0; i < 17; i++) {
            cardTemp += card.substr(i, 1) * arrInt[i];
        }
        card += arrCh[cardTemp % 11];
        return card;
    }
    return card;
};
