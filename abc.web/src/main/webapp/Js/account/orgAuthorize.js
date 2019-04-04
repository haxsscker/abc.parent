$(function () {
    var Dialog;
    MyGrid.Resize();
    MyAction.Init();
    $("#submit").click(MyAction.SubmitData);

});

var MyAction = {
	Init:function (){
		    var empId = $("#empId").val();
	        var o2 = { empId: empId};
	                $.post("/account/AuthorizeView.json", o2, function (data) {
	                       if (data.success) {
	                    	   $("#RealName").val(data.data.RealName);
	                    	   $("#Mobile").val(data.data.Mobile);
	                    	   $("#accountMark").val(data.data.accountMark);
	                        }
	                        else {
	                            Colyn.log(data.message);
	                        }
	                    })
	 },	
    SubmitData: function () {
    	    var empId = $("#empId").val();
    	    var accountMark =$("#accountMark").val();
           var  url = "/account/authorize?empId="+empId+"&accountMark="+accountMark;
           window.open(encodeURI(url), 'newwindow', 'height=800, width=900, top=60,left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
    }
}


