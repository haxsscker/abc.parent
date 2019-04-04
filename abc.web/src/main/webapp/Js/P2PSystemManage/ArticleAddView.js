try{
    UE.getEditor('Ueditor').destroy();
} catch (e) {
    UE.getEditor('Ueditor');
}

$('#sys_class_id').combobox({   
    url:'/site/json/showColumnView.json',    
    valueField:'id',    
    textField:'text',
    onLoadSuccess: function () {
    	var dataValue = $('#sys_class_id').attr('data-value');
    	if(dataValue.length == 0) {
    		var data = $('#sys_class_id').combobox('getData');
    		if (data.length > 0) {
    			$('#sys_class_id').combobox('select', data[0].id);
    		}
    	} else {
    		$('#sys_class_id').combobox('setValue', dataValue);
    	}
    }
});  

