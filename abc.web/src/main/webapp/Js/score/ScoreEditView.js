//下拉框选中时间
function changeScore() {
    $.post("/score/json/getScoreItem.json?scoreCode=" + $("#scoreCode").val(),  function (data) {
        if (data != "") {
//            data = eval("(" + data + ")");
            data = data.data;
            $("#jf").html(data.scoreValue);
            $("#jfvalueid").val(data.scoreValue);
            $("#allScoreId").html(parseInt(data.scoreValue) + parseInt($("#HiddenAllScoreId").val()));
        }
        else
        {
            var a = $("#scoreCode").val();
            if (a == "0") {
                $("#jf").html("0");
                $("#allScoreId").html(parseInt($("#HiddenAllScoreId").val()));
            }
        }
    }, "json");
}