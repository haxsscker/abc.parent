/*
功能：地区二级联动
开发者：吕小东；
开发时间：2014年9月28日11:03:35
*/





var MyArea = {
    /*
    方法：初始化地区
        参数说明：
            1.AreaA：一级地区id；
            2.AreaB：二级地区id；
            3.SelectArea：隐藏域（保存二级地区选中的值（从数据库读取））
    */
    Init: function (AreaA, AreaB, SelectArea) {
        var html = $.ajax({ url: "/common/json/GetAreaInfoData.json?modelAction=0", async: false }).responseText;
        var htmlAreaInfo = "";
        if (html.indexOf('|') > -1) {
            var areaInfos = html.split('|');
            for (var i = 0; i < areaInfos.length - 1; i++) {
                if (areaInfos[i].indexOf(',') > -1) {
                    var areaInfosB = areaInfos[i].split(',');
                    htmlAreaInfo += "<option value='" + areaInfosB[0] + "'>" + areaInfosB[1] + "</option>";
                }
            }
        }
        $("#" + AreaA).html(htmlAreaInfo);
        if ($("#" + AreaA).hasClass("addAll")) {
            $("#" + AreaA).prepend('<option value="">全部</option>')
        }
        if ($("#" + SelectArea).val() != "") {
            var rvalue = $.ajax({ url: "/common/json/GetSuperAreaInfoData.json?modelAction=" + $("#" + SelectArea).val(), async: false }).responseText;
            if (rvalue != "") {
                $("#" + AreaA).val(rvalue);
            }
        }
        MyArea.AreaChange(AreaA, AreaB);
        //选中[开户行地区]
        if ($("#" + SelectArea).val() != "") {
            if ($("#" + AreaB).css("display") != "none") {
                $("#" + AreaB).val($("#" + SelectArea).val());
            }
        }
    },
    /*
    方法：一级地区改变
        参数说明：
            1.AreaA：一级地区id；
            2.AreaB：二级地区id；
    */
    AreaChange: function (AreaA, AreaB) {
        var html = $.ajax({ url: "/common/json/GetAreaInfoData.json?modelAction=1&modelActionB=" + $("#" + AreaA).val(), async: false }).responseText;
        var htmlAreaInfo = "";
        if (html.indexOf('|') > -1) {
            var areaInfos = html.split('|');
            for (var i = 0; i < areaInfos.length - 1; i++) {
                if (areaInfos[i].indexOf(',') > -1) {
                    var areaInfosB = areaInfos[i].split(',');
                    htmlAreaInfo += "<option value='" + areaInfosB[0] + "'>" + areaInfosB[1] + "</option>";
                }
            }
        }
        $("#" + AreaB).html(htmlAreaInfo);
        if ($("#" + AreaB).hasClass("addAll")) {
            $("#" + AreaB).prepend('<option value="">全部</option>')
        }
    },
    /*
    方法：开户行行别
        参数说明：
            1.开户行行别下拉列表
    */
    BankLevel: function (bLevel) {
        var bl = "";
        bl += "<option value='0102'>中国工商银行</option>";
        bl += "<option value='0103'>中国农业银行</option>";
        bl += "<option value='0104'>中国银行</option>";
        bl += "<option value='0105'>中国建设银行</option>";
        bl += "<option value='0301'>交通银行</option>";
        bl += "<option value='0308'>招商银行</option>";
        bl += "<option value='0309'>兴业银行</option>";
        bl += "<option value='0303'>中国光大银行</option>";
        bl += "<option value='0305'>中国民生银行</option>";
        bl += "<option value='0306'>广东发展银行</option>";
        bl += "<option value='0307'>深圳发展银行</option>";
        bl += "<option value='0310'>上海浦东发展银行</option>";
        bl += "<option value='0304'>华夏银行</option>";
        bl += "<option value='0302'>中信实业银行</option>";
        bl += "<option value='0402'>其他农村信用合作社</option>";
        bl += "<option value='0313'>其他城市商业银行</option>";
        bl += "<option value='0203'>中国农业发展银行</option>";
        bl += "<option value='0314'>江阴市农村商业银行股份有限公司</option>";
        bl += "<option value='0315'>恒丰银行</option>";
        bl += "<option value='0403'>国家邮政局邮政储汇局</option>";
        bl += "<option value='0969'>澳门地区中国银行澳门分行</option>";
        bl += "<option value='0671'>香港地区渣打银行</option>";
        bl += "<option value='0410'>深圳平安银行</option>";
        bl += "<option value='0316'>浙商银行股份有限公司</option>";
        bl += "<option value='0318'>渤海银行股份有限公司</option>";
        bl += "<option value='0502'>东亚银行(中国)有限公司</option>";
        bl += "<option value='0319'>徽商银行</option>";
        bl += "<option value='0317'>江苏连云港东方农村合作银行</option>";
        bl += "<option value='0322'>上海农村商业银行</option>";
        bl += "<option value='0320'>村镇银行</option>";
        bl += "<option value='0321'>重庆三峡银行股份有限公司</option>";
        bl += "<option value='0401'>城市信用社</option>";
        bl += "<option value='0501'>汇丰银行</option>";
        bl += "<option value='0503'>南洋商业银行</option>";
        bl += "<option value='0504'>恒生银行</option>";
        bl += "<option value='0505'>中国银行（香港）</option>";
        bl += "<option value='0506'>集友银行</option>";
        bl += "<option value='0507'>创兴银行</option>";
        bl += "<option value='0509'>星展银行</option>";
        bl += "<option value='0510'>永亨银行</option>";
        bl += "<option value='0514'>中信银行</option>";
        bl += "<option value='0531'>花旗银行</option>";
        bl += "<option value='0532'>美国银行</option>";
        bl += "<option value='0533'>摩根大通银行</option>";
        bl += "<option value='0534'>美国建东银行</option>";
        bl += "<option value='0561'>三菱东京日联银行</option>";
        bl += "<option value='0562'>日本日联银行</option>";
        bl += "<option value='0563'>三井住友银行</option>";
        bl += "<option value='0564'>瑞穗实业银行</option>";
        bl += "<option value='0565'>日本山口银行</option>";
        bl += "<option value='0591'>外换银行</option>";
        bl += "<option value='0592'>韩国新韩银行</option>";
        bl += "<option value='0593'>友利银行</option>";
        bl += "<option value='0594'>韩国产业银行</option>";
        bl += "<option value='0595'>新韩银行</option>";
        bl += "<option value='0596'>企业银行</option>";
        bl += "<option value='0597'>韩亚银行</option>";
        bl += "<option value='0616'>首都银行</option>";
        bl += "<option value='0621'>华侨银行</option>";
        bl += "<option value='0622'>大华银行</option>";
        bl += "<option value='0623'>星展银行</option>";
        bl += "<option value='0631'>盘谷银行</option>";
        bl += "<option value='0641'>奥地利奥合国际银行</option>";
        bl += "<option value='0651'>比利时联合银行</option>";
        bl += "<option value='0652'>比利时富通银行</option>";
        bl += "<option value='0661'>荷兰银行</option>";
        bl += "<option value='0662'>荷兰安智银行</option>";
        bl += "<option value='0672'>英国苏格兰皇家银行</option>";
        bl += "<option value='068'>永隆银行</option>";
        bl += "<option value='0681'>瑞典商业银行</option>";
        bl += "<option value='0682'>瑞典北欧斯安银行</option>";
        bl += "<option value='0691'>法国兴业银行</option>";
        bl += "<option value='0692'>法国巴黎银行</option>";
        bl += "<option value='0694'>东方汇理银行</option>";
        bl += "<option value='0695'>法国外贸银行</option>";
        bl += "<option value='0711'>德国德累斯登银行</option>";
        bl += "<option value='0712'>德意志银行</option>";
        bl += "<option value='0713'>德国商业银行</option>";
        bl += "<option value='0714'>德国西德银行</option>";
        bl += "<option value='0715'>德国巴伐利亚州银行</option>";
        bl += "<option value='0716'>德国北德意志州银行</option>";
        bl += "<option value='0717'>中德住房储蓄银行</option>";
        bl += "<option value='0732'>意大利联合圣保罗银行</option>";
        bl += "<option value='0741'>瑞士信贷银行</option>";
        bl += "<option value='0742'>瑞士银行</option>";
        bl += "<option value='0751'>加拿大丰业银行</option>";
        bl += "<option value='0752'>蒙特利尔银行</option>";
        bl += "<option value='0761'>澳大利亚和新西兰银行</option>";
        bl += "<option value='0771'>摩根士丹利国际银行</option>";
        bl += "<option value='0775'>华美银行</option>";
        bl += "<option value='0776'>荷兰合作银行</option>";
        bl += "<option value='0781'>厦门国际银行</option>";
        bl += "<option value='0782'>法国巴黎银行</option>";
        bl += "<option value='0786'>韩亚银行</option>";
        bl += "<option value='0787'>华一银行</option>";

        $("#" + bLevel).html(bl);//[开户行行别]下拉列表初始化
    },
    /*
    方法：开户行行别
    参数说明：
        1.开户行行别下拉列表
*/
MyBankLevel: function (bLevel) {
    var bl = "";
    bl += "<option value='中国工商银行'>中国工商银行</option>";
    bl += "<option value='中国农业银行'>中国农业银行</option>";
    bl += "<option value='中国银行'>中国银行</option>";
    bl += "<option value='中国建设银行'>中国建设银行</option>";
    bl += "<option value='交通银行'>交通银行</option>";
    bl += "<option value='招商银行'>招商银行</option>";
    bl += "<option value='兴业银行'>兴业银行</option>";
    bl += "<option value='中国光大银行'>中国光大银行</option>";
    bl += "<option value='中国民生银行'>中国民生银行</option>";
    bl += "<option value='广东发展银行'>广东发展银行</option>";
    bl += "<option value='深圳发展银行'>深圳发展银行</option>";
    bl += "<option value='上海浦东发展银行'>上海浦东发展银行</option>";
    bl += "<option value='华夏银行'>华夏银行</option>";
    bl += "<option value='中信实业银行'>中信实业银行</option>";
    bl += "<option value='其他农村信用合作社'>其他农村信用合作社</option>";
    bl += "<option value='其他城市商业银行'>其他城市商业银行</option>";
    bl += "<option value='中国农业发展银行'>中国农业发展银行</option>";
    bl += "<option value='江阴市农村商业银行股份有限公司'>江阴市农村商业银行股份有限公司</option>";
    bl += "<option value='恒丰银行'>恒丰银行</option>";
    bl += "<option value='国家邮政局邮政储汇局'>国家邮政局邮政储汇局</option>";
    bl += "<option value='澳门地区中国银行澳门分行'>澳门地区中国银行澳门分行</option>";
    bl += "<option value='香港地区渣打银行'>香港地区渣打银行</option>";
    bl += "<option value='深圳平安银行'>深圳平安银行</option>";
    bl += "<option value='浙商银行股份有限公司'>浙商银行股份有限公司</option>";
    bl += "<option value='渤海银行股份有限公司'>渤海银行股份有限公司</option>";
    bl += "<option value='东亚银行(中国)有限公司'>东亚银行(中国)有限公司</option>";
    bl += "<option value='徽商银行'>徽商银行</option>";
    bl += "<option value='江苏连云港东方农村合作银行'>江苏连云港东方农村合作银行</option>";
    bl += "<option value='上海农村商业银行'>上海农村商业银行</option>";
    bl += "<option value='村镇银行'>村镇银行</option>";
    bl += "<option value='重庆三峡银行股份有限公司'>重庆三峡银行股份有限公司</option>";
    bl += "<option value='城市信用社'>城市信用社</option>";
    bl += "<option value='汇丰银行'>汇丰银行</option>";
    bl += "<option value='南洋商业银行'>南洋商业银行</option>";
    bl += "<option value='恒生银行'>恒生银行</option>";
    bl += "<option value='中国银行（香港）'>中国银行（香港）</option>";
    bl += "<option value='集友银行'>集友银行</option>";
    bl += "<option value='创兴银行'>创兴银行</option>";
    bl += "<option value='星展银行'>星展银行</option>";
    bl += "<option value='永亨银行'>永亨银行</option>";
    bl += "<option value='中信银行'>中信银行</option>";
    bl += "<option value='花旗银行'>花旗银行</option>";
    bl += "<option value='美国银行'>美国银行</option>";
    bl += "<option value='摩根大通银行'>摩根大通银行</option>";
    bl += "<option value='美国建东银行'>美国建东银行</option>";
    bl += "<option value='三菱东京日联银行'>三菱东京日联银行</option>";
    bl += "<option value='日本日联银行'>日本日联银行</option>";
    bl += "<option value='三井住友银行'>三井住友银行</option>";
    bl += "<option value='瑞穗实业银行'>瑞穗实业银行</option>";
    bl += "<option value='日本山口银行'>日本山口银行</option>";
    bl += "<option value='外换银行'>外换银行</option>";
    bl += "<option value='韩国新韩银行'>韩国新韩银行</option>";
    bl += "<option value='友利银行'>友利银行</option>";
    bl += "<option value='韩国产业银行'>韩国产业银行</option>";
    bl += "<option value='新韩银行'>新韩银行</option>";
    bl += "<option value='企业银行'>企业银行</option>";
    bl += "<option value='韩亚银行'>韩亚银行</option>";
    bl += "<option value='首都银行'>首都银行</option>";
    bl += "<option value='华侨银行'>华侨银行</option>";
    bl += "<option value='大华银行'>大华银行</option>";
    bl += "<option value='星展银行'>星展银行</option>";
    bl += "<option value='盘谷银行'>盘谷银行</option>";
    bl += "<option value='奥地利奥合国际银行'>奥地利奥合国际银行</option>";
    bl += "<option value='比利时联合银行'>比利时联合银行</option>";
    bl += "<option value='比利时富通银行'>比利时富通银行</option>";
    bl += "<option value='荷兰银行'>荷兰银行</option>";
    bl += "<option value='荷兰安智银行'>荷兰安智银行</option>";
    bl += "<option value='英国苏格兰皇家银行'>英国苏格兰皇家银行</option>";
    bl += "<option value='永隆银行'>永隆银行</option>";
    bl += "<option value='瑞典商业银行'>瑞典商业银行</option>";
    bl += "<option value='瑞典北欧斯安银行'>瑞典北欧斯安银行</option>";
    bl += "<option value='法国兴业银行'>法国兴业银行</option>";
    bl += "<option value='法国巴黎银行'>法国巴黎银行</option>";
    bl += "<option value='东方汇理银行'>东方汇理银行</option>";
    bl += "<option value='法国外贸银行'>法国外贸银行</option>";
    bl += "<option value='德国德累斯登银行'>德国德累斯登银行</option>";
    bl += "<option value='德意志银行'>德意志银行</option>";
    bl += "<option value='德国商业银行'>德国商业银行</option>";
    bl += "<option value='德国西德银行'>德国西德银行</option>";
    bl += "<option value='德国巴伐利亚州银行'>德国巴伐利亚州银行</option>";
    bl += "<option value='德国北德意志州银行'>德国北德意志州银行</option>";
    bl += "<option value='中德住房储蓄银行'>中德住房储蓄银行</option>";
    bl += "<option value='意大利联合圣保罗银行'>意大利联合圣保罗银行</option>";
    bl += "<option value='瑞士信贷银行'>瑞士信贷银行</option>";
    bl += "<option value='瑞士银行'>瑞士银行</option>";
    bl += "<option value='加拿大丰业银行'>加拿大丰业银行</option>";
    bl += "<option value='蒙特利尔银行'>蒙特利尔银行</option>";
    bl += "<option value='澳大利亚和新西兰银行'>澳大利亚和新西兰银行</option>";
    bl += "<option value='摩根士丹利国际银行'>摩根士丹利国际银行</option>";
    bl += "<option value='华美银行'>华美银行</option>";
    bl += "<option value='荷兰合作银行'>荷兰合作银行</option>";
    bl += "<option value='厦门国际银行'>厦门国际银行</option>";
    bl += "<option value='法国巴黎银行'>法国巴黎银行</option>";
    bl += "<option value='韩亚银行'>韩亚银行</option>";
    bl += "<option value='华一银行'>华一银行</option>";

    $("#" + bLevel).html(bl);//[开户行行别]下拉列表初始化
}
}