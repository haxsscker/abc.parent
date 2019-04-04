function change() {
    var checkValue = $("#cycle").val();

    if (checkValue == "1") {

        $("#tdidleft").css("display", "block");
        $("#tdidright").css("display", "block");
        $("#tdidleft").html("奖励次数：");
        $("#Minute").html("");
    }
    else if (checkValue == "2") {
        $("#tdidleft").css("display", "block");
        $("#tdidright").css("display", "block");
        $("#tdidleft").html("间隔时间：");
        $("#Minute").html("分钟");
    }
    else {
        $("#trid").css("display", "none");
        $("#tdidleft").css("display", "none");
        $("#tdidright").css("display", "none");
        $("#Minute").html("");
    }
}