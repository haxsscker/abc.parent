$(function () {    
    $("input:not(.easyui-datebox,.combo-text),textarea,.SearchForm input:not(.easyui-datebox)").each(function () {
        var $this = $(this);
        $this.validatebox();
        var opt = $.data(this, "validatebox").options; //$(this).data().validatebox.options;
        if (opt.validType) {
            if (typeof opt.validType == "string") {
                opt.validType = [opt.validType, "CheckQuote"];
            } else {
                opt.validType.push("CheckQuote");
            }
        }
        else {
            opt.validType = "CheckQuote";
        }
        $this.validatebox(opt);
    });
    getUnit();
    removeStyle();
    getSum();
    remainderChar();
});

var MyValid = {
    Init: function () {
        $("input:not(.easyui-datebox,.combo-text),textarea,.SearchForm input:not(.easyui-datebox)").each(function () {
            var $this = $(this);
            $this.validatebox();
            var opt = $.data(this, "validatebox").options; //$(this).data().validatebox.options;
            if (opt.validType) {
                if (typeof opt.validType == "string") {
                    opt.validType = [opt.validType, "CheckQuote"];
                } else {
                    opt.validType.push("CheckQuote");
                }
            }
            else {
                opt.validType = "CheckQuote";
            }
            $this.validatebox(opt);
        });
        getUnit();
        removeStyle();
        getSum();
        remainderChar();
    }
};
