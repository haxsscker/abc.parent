$(function () {
    $("#CustSaveBtn").click(MyAction.CustSaveBtn);
});
var MyAction = {
    CustSaveBtn: function () {
        var fun_fund_no = $("#fun_fund_no").val();
        if (fun_fund_no == "" || fun_fund_no.length>50) {
            var txt1 = document.getElementById("fun_fund_no");
            txt1.focus();
            return;
        }
        var fun_fund_name = $("#fun_fund_name").val();
        if (fun_fund_name == "" || fun_fund_name.length > 125) {
            var txt1 = document.getElementById("fun_fund_name");
            txt1.focus();
            return;
        }
        var fun_fund_comp = $("#fun_fund_comp").val();
        if (fun_fund_comp == "" || fun_fund_comp.length > 64) {
            var txt1 = document.getElementById("fun_fund_comp");
            txt1.focus();
            return;
        }
        var fun_fund_money = $("#fun_fund_money").val();
        if (fun_fund_money != "") {
            if (isNaN(fun_fund_money)) {
                var txt1 = document.getElementById("fun_fund_money");
                txt1.focus();
                return;
            }
            else {
                var arr = new Array();
                arr = fun_fund_money.split(".");
                if (arr.length > 1) {
                    var result = arr[1].length;
                    if (result > 2) {
                        var txt1 = document.getElementById("fun_fund_money");
                        txt1.focus();
                        return;
                    }
                }
            }
        }
        else {
            var txt1 = document.getElementById("fun_fund_money");
            txt1.focus();
            return;
        }

        var fun_fund_period = $("#fun_fund_period").val();
        if (fun_fund_period != "") {
            if (isNaN(fun_fund_period)) {
                var txt1 = document.getElementById("fun_fund_period");
                txt1.focus();
                return;
            }
            else {
                var arr = new Array();
                arr = fun_fund_period.split(".");
                if (arr.length > 1) {
                    var result = arr[1].length;
                    if (result > 2) {
                        var txt1 = document.getElementById("fun_fund_period");
                        txt1.focus();
                        return;
                    }
                }
            }
        }
        else {
            var txt1 = document.getElementById("fun_fund_period");
            txt1.focus();
            return;
        }

        
        var fun_fund_rate = $("#fun_fund_rate").val();
        if (fun_fund_rate != "") {
            if (isNaN(fun_fund_rate)) {
                var txt1 = document.getElementById("fun_fund_rate");
                txt1.focus();
                return;
            }
            else {
                var arr = new Array();
                arr = fun_fund_rate.split(".");
                if (arr.length > 1) {
                    var result = arr[1].length;
                    if (result > 2) {
                        var txt1 = document.getElementById("fun_fund_rate");
                        txt1.focus();
                        return;
                    }
                }
            }

        }
        else {
            var txt1 = document.getElementById("fun_fund_rate");
            txt1.focus();
            return;
        }

        var fun_min_money = $("#fun_min_money").val();
        if (fun_min_money != "") {
            if (isNaN(fun_min_money)) {
                var txt1 = document.getElementById("fun_min_money");
                txt1.focus();
                return;
            }
            else {
                var arr = new Array();
                arr = fun_min_money.split(".");
                if (arr.length > 1) {
                    var result = arr[1].length;
                    if (result > 2) {
                        var txt1 = document.getElementById("fun_min_money");
                        txt1.focus();
                        return;
                    }
                }
            }
        }
        
        var fun_fund_industry = $("#fun_fund_industry").val();
        if (fun_fund_industry.length > 65) {
            var txt1 = document.getElementById("fun_fund_industry");
            txt1.focus();
            return;
        }
        
        var fun_fund_type = $("#fun_fund_type").val();
        if (fun_fund_type.length > 65) {
            var txt1 = document.getElementById("fun_fund_type");
            txt1.focus();
            return;
        }

        var fun_martgage_rate = $("#fun_martgage_rate").val();
        if (fun_martgage_rate != "") {
            if (isNaN(fun_martgage_rate)) {
                var txt1 = document.getElementById("fun_martgage_rate");
                txt1.focus();
                return;
            }
            else {
                var arr = new Array();
                arr = fun_martgage_rate.split(".");
                if (arr.length > 1) {
                    var result = arr[1].length;
                    if (result > 2) {
                        var txt1 = document.getElementById("fun_martgage_rate");
                        txt1.focus();
                        return;
                    }
                }
            }
        }
        var fun_min_money1 = $("#fun_min_money1").val();
        if (fun_min_money1 != "") {
            if (isNaN(fun_min_money1)) {
                var txt1 = document.getElementById("fun_min_money1");
                txt1.focus();
                return;
            }
            else {
                var arr = new Array();
                arr = fun_min_money1.split(".");
                if (arr.length > 1) {
                    var result = arr[1].length;
                    if (result > 2) {
                        var txt1 = document.getElementById("fun_min_money1");
                        txt1.focus();
                        return;
                    }
                }
            }
        }

        var fun_max_money = $("#fun_max_money").val();
        if (fun_max_money != "") {
            if (isNaN(fun_max_money)) {
                var txt1 = document.getElementById("fun_max_money");
                txt1.focus();
                return;
            }
            else {
                var arr = new Array();
                arr = fun_max_money.split(".");
                if (arr.length > 1) {
                    var result = arr[1].length;
                    if (result > 2) {
                        var txt1 = document.getElementById("fun_max_money");
                        txt1.focus();
                        return;
                    }
                }
            }
        }
        var fun_profite_yields = $("#fun_profite_yields").val();
        if (fun_profite_yields != "") {
            if (isNaN(fun_profite_yields)) {
                var txt1 = document.getElementById("fun_profite_yields");
                txt1.focus();
                return;
            }
            else {
                var arr = new Array();
                arr = fun_profite_yields.split(".");
                if (arr.length > 1) {
                    var result = arr[1].length;
                    if (result > 2) {
                        var txt1 = document.getElementById("fun_profite_yields");
                        txt1.focus();
                        return;
                    }
                }
            }
        }

        $("#fund_profit").find("tr").each(function () {
            $(this).find("td").each(function () {
                $(this).find("input").each(function () {
                    var ainput = $(this).val();
                    if (ainput != "") {
                        if (isNaN(ainput)) {
                            var txt1 = $(this);
                            txt1.focus();
                            return false;
                        }
                        else {
                            var arr = new Array();
                            arr = ainput.split(".");
                            if (arr.length > 1) {
                                var result = arr[1].length;
                                if (result > 2) {
                                    var txt1 = $(this);
                                    txt1.focus();
                                    return false;
                                }
                            }
                        }
                    }
                })

            });
        });

        if (true) {
                var param = createParam2([{ formId: 'main', relaClass: "main" }, { formId: 'fund_profit', isAnyRow: true }]);
                console.log(param);
                $.AjaxColynJson("/loan/ScoreTypeAddsData?modelAction=Add", param, function (data) {
                    if (data.Success) {
                        Colyn.log("添加成功！");
                        actionPageReturn("/loan/WelfareListView?MenuId=296&MenuName=有限合伙维护");
                    }
                    else {
                        Colyn.log(data.Msg);
                        actionPageReturn("/loan/WelfareListView?MenuId=296&MenuName=有限合伙维护");
                    }
                });
                //jQuery('#LoanGrid').datagrid('clearSelections')
            }
    }
}
