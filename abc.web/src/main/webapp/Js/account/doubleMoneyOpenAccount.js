$(function () {
    var Dialog;
    MyGrid.Resize();
    $("#submit").click(MyAction.SubmitData);
    $("#closeSubmit").click(MyAction.closeSubmit);
});

var MyAction = {
    SubmitData: function () {
       var AccountMark = $("#AccountMark").val();
	   var empId=$("#empId").val();
	   var AccountNo=$("#AccountNo").val();
	   if(null == AccountNo || AccountNo == ""){
		   Colyn.log("对公账号不能为空！");
		   return false;
	   }
 	   var AccountName=$("#AccountName").val();
 	   if(null == AccountName || AccountName == ""){
		   Colyn.log("对公账户名不能为空！");
		   return false;
	   }
	   var AccountBk =$("#AccountBk").val();
	   if(null == AccountBk || AccountBk == ""){
		   Colyn.log("清算行号不能为空！");
		   return false;
	   }
       var  url = "/account/dryOpenAccount?AccountNo="+AccountNo+"&AccountName="+AccountName+"&AccountBk="+AccountBk+"&empId="+empId+"&AccountMark="+AccountMark;
       MyAction.openWin(encodeURI(url), 'newwindow', 'height=500, width=800, top=90,left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
    },
	 
    closeSubmit: function () {
       var  url = "/account/closeAccount";
       MyAction.openWin(encodeURI(url), 'newwindow', 'height=500, width=800, top=90,left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	},
	//授权
	authorize: function (type,category){
	   var url = "/account/authorize?TxnTyp="+type+"&TransTyp="+category;
	   MyAction.openWin(encodeURI(url), 'newwindow', 'height=500, width=800, top=90,left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	},
	openWin: function (url,text,winInfo){
	 	var winObj = window.open(url,text,winInfo);
	 	var loop = setInterval(function() {     
	 	    if(winObj.closed) {    
	 	       clearInterval(loop);
	 	       document.location.reload();
	 	    }    
	 	}, 1);   
	 }
}


