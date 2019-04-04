$(function () {
    var Dialog;
    MyGrid.Resize();
    MyAction.Init();
    $("#submit").click(MyAction.SubmitData);

});

var MyAction = {
		// 页面初始化
		Init : function() {
			    var empId = $("#empId").val();
		        var o2 = { empId: empId };
		                $.post("/moneyManage/json/RechangeAndToCash.json", o2, function (data) {
		                       if (data.success) {
		                            $("#mount").html(data.data.mount);
		                        }
		                        else {
		                            Colyn.log(data.message);
		                        }
		                    })
		                
			},
     
    SubmitData: function () {
    	    var empId = $("#empId").val();
    	    var money =$("#money").val();
    	    var mount =parseFloat($("#mount").html());
    	    var type =$("#type").val();
	        var o2 = { empId: empId ,money:money};
	        if(money==""||money==null){
	           Colyn.log("请输入金额!");
          	   return;	
	        }
	        switch (type) {
            case "1": {
            	var  url = "/moneyManage/rechargeMoney?money="+money+"&empId="+empId;
                window.open(url, 'newwindow', 'height=800, width=900, top=60,left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
            	break;
            } case "2": {
               if(money>mount){
            	   Colyn.log("提现金额不能大于可用余额!");
            	   return;
            	}
               var CardType =$("#CardType").val();
               var CardNo =$("#CardNo").val();
               var BankCode =$("#BankCode").val();
               var province =$("#province").val();
               var city =$("#city").val();
            	var  url = "/moneyManage/withdrawMoney?money="+money+"&empId="+empId+"&city="+city+"&CardType="+CardType+"&CardNo="+CardNo+"&BankCode="+BankCode+"&province="+province;
                window.open(url, 'newwindow', 'height=800, width=900, top=60,left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
            	break;
            	
            }
            }
    }
}




$('#type').change(function(){
	var type =$("#type").val();
	 switch (type) {
     case "1": {
    	 document.getElementById("div").style.display="none"; 
    	 break;
     } case "2": {
    	//查询省级城市
    	selectProvince("0");
    	document.getElementById("div").style.display=""; 
     	break;
     }
	 }
});

function selectProvince(province){
	var selOpt = $("#city option");
	selOpt.remove();
	var ProviceData ={province:province};
	$.post("/account/CardCityBaseList.json", ProviceData, function (data) {
        	for(var i=0; i<data.data.length; i++){
				 var  newOption   = document.createElement("option");           
				 newOption.value=data.data[i].cityCode ;           
				 newOption.text=data.data[i].cityName;   
				 if(province=="0"){
					 document.getElementById("province").options.add(newOption);   
				 }else{
				 document.getElementById("city").options.add(newOption);
				 }
			} 
         
     })
}


$('#province').change(function(){
	var province =$("#province").val();
	if(province==''){
		var  newOption   = document.createElement("option");           
		 newOption.value='';           
		 newOption.text='请选择';           
		 document.getElementById("city").options.add(newOption); 
	 }else{
		 selectProvince(province);
	 }
});


