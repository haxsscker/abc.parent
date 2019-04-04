///添加行
function AddRow(parameters) {
	var index = (parseInt($(parameters).attr('_Row')) + 1);
	var html = '';
	html += '<tr id="' + index + '">';
	html += ' <td class="leftTD">期限：</td>';
	html += ' <td class="rightTD">';
	html += ' <input type="text" class="text-input w120 " id="min' + index
			+ '"  name="pdo_min_period" />至 ';
	html += ' <input type="text" class="text-input w120 " id="max' + index
			+ '"  name="pdo_max_period" />月';
	html += '</td>';
	html += '<td class="leftTD">年化收益率：</td>';
	html += ' <td class="rightTD">';
	html += '<input type="text" class="text-input w120 " id="n' + index
			+ '"  name="pdo_product_rate" />%';
	html += '<td class="edit">';
	html += '<img src="/Images/icon/16/bullet_minus.png" style="cursor: pointer;" onclick="RowDelete('
			+ index + ')" title="删除"/>';
	html += '</td>';
	html += '</tr>';
	$("#rate").append(html);
	$(parameters).attr('_Row', (parseInt($(parameters).attr('_Row')) + 1));
	$('#n' + index).validatebox({
		required : true,
		validType : 'IntOrFloat'
	});
	$('#min' + index).validatebox(
			{
				required : true,
				validType : [ 'IntOrFloat', 'Range[1,50]',
						'LessThan[\'#max' + index + '\',\'最小期限必须小于最大期限\']' ]
			});// Int','LessThanValue[\'#sys_max_score\',\'开始分值必须小于结束分值\']'
	$('#max' + index).validatebox(
			{
				required : true,
				validType : [ 'IntOrFloat', 'Range[1,50]',
						'MoreThan[\'min' + index + '\',\'最大期限必须大于最小期限\']' ]
			});
}

// 删除行
function RowDelete(id) {
	$("#" + id).remove();
}
$(function() {
	$("#btnAdd").click(
			function() {
				if ($('#Colyn').form('validate')) {
					var param = createParam2([ {
						formId : 'main',
						relaClass : "main"
					}, {
						formId : 'rate',
						isAnyRow : true
					} ]);
					// console.log(param);
					$.AjaxColynJson("/product/json/productAddView.json="
							+ pdo_product_id, param, function(data) {
						if (data.success) {
							Colyn.log("添加成功！");
							// addDialog.dialog('close');

						} else {
							Colyn.log("添加失败");
							// addDialog.dialog('close');
						}
					}, "json");
					// setTimeout(
					// window.location.href = "/product/ProductMenuAddView",
					// 6000);
				}
			});
});