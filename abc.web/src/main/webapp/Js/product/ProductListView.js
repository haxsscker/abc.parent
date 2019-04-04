/*------------------------------------------------
 * Author:徐大龙  Date：2014-8-25 15:47:56
-------------------------------------------------*/

///添加行
function AddRow(parameters) {
	var index = (parseInt($(parameters).attr('_Row')) + 1);
	var html = '';
	html += '<tr id="' + index + '">';
	html += ' <td class="leftTD">期限：</td>';
	html += ' <td class="rightTD">';
	html += ' <input type="text" class="text-input w120 " id="min' + index
			+ '"  name="c" />至 ';
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
	alert(id);
	$("#" + id).remove();
}
$(function() {
	MyGrid.Resize();
	$("#Add").click(MyAction.Add);
	$("#Edit").click(MyAction.Edit);
	$("#Del").click(MyAction.Del);
	$("#LookUp").click(MyAction.LookUp);
	$("#btnSearch").click(MyAction.Search);
	MyAction.Init();
	$(window).resize(function() {
		MyGrid.RefreshPanl();
	});
	// 禁止回车事件响应
	$(this).keydown(function(e) {
		var key = window.event ? e.keyCode : e.which;
		// alert(key.toString());
		if (key.toString() == "13") {
			return false;
		}
	});
});

var MyAction = {
	Init : function() {
		$("#ProductMaintainListGrid").datagrid({
			method : "GET",
			url : "/product/json/actionProductListView.json?"+createParam3("SearchForm"),
			height : $(window).height() - 52,
			pageSize : 10,
			fitColumns : true,
			rownumbers : true,
			nowrap : false,
			striped : true,
			idField : "pdo_product_rate_Id", // 此字段为主键，当无该字段页面设计时不要进行赋值，否则json无法绑定
			remoteSort : true,
			view : myview,// 重写当没有数据时
			emptyMsg : '没有找到数据',// 返回数据字符
			columns : [ [ {
				field : "pdo_product_name",
				title : "产品名称",
				width : 150,
				align : "center"
			}, {
				field : "pdo_min_period",
				title : "最小期限",
				width : 150,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value == "") {
						return "-";
					} else {
						return value + "月";
					}
				}
			}, {
				field : "pdo_max_period",
				title : "最大期限",
				width : 150,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value == "") {
						return "-";
					} else {
						return value + "月";
					}
				}
			}, {
				field : "pdo_product_rate",
				title : "年化收益率",
				width : 150,
				align : "center",
				formatter : function(value, rowData, index) {
					if (value == "") {
						return "-";
					} else {
						return formatPercent(value);
					}
				}
			}, {
				field : "pdo_product_mark",
				title : "备注",
				width : 250,
				align : "center",
				formatter : function(value, rowData, index) {
					return CutString(value, 10);
				}
			} ] ],
			pagination : true,
			singleSelect : true
		});
		var p = $('#ProductMaintainListGrid').datagrid('getPager');
		$(p).pagination({
			pageSize : 10,
			pageList : [ 5, 10, 15, 20, 30, 50, 100 ],
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onBeforeRefresh : function() {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			}
		});
	},
	// 添加
	Add : function() {
		var addDialog = $.hDialog({
			href : "/product/ProductAddView",
			iconCls : 'icon-add',
			title : "产品添加",
			// maximizable: true,//显示最大化
			width : $(window).width() - 50,
			height : $(window).height() - 50,
			onLoad : function() {

			},
			buttons : [
					{
						text : '确认添加',
						iconCls : 'icon-ok',
						handler : function() {
							if ($('#Colyn').form('validate')) {
								var param = createParam2([ {
									formId : 'main',
									relaClass : "main"
								}, {
									formId : 'rate',
									isAnyRow : true
								} ]);
								// console.log(param);
								$.AjaxColynJson(
										"/product/json/productAddView.json",
										param, function(data) {
											if (data.success) {
												Colyn.log(data.message);
												addDialog.dialog("close");
												MyAction.Init();
											} else {
												Colyn.log(data.message);
												addDialog.dialog("close");
											}

										}, "json");

							}
						}
					}, {
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function() {
							addDialog.dialog("close");
						}
					} ]
		})
	},
	// 编辑
	Edit : function() {
		var row = MyGrid.selectRow();
		var num = row;
		if (num == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		} else {
			/*
			 * var Dialog = $.hDialog({ href :
			 * "/product/productEditView?pdo_product_id=" + row.pdo_product_id,
			 * iconCls : 'icon-add', title : "产品详情", width : 750, height :
			 * $(window).height() - 50, // onLoad: function () { //
			 * MyAction.GetTemplate(row.gsc_Id, row.gte_Id); // }, buttons : [ {
			 * text : '关闭', iconCls : 'icon-cancel', handler : function() {
			 * Dialog.dialog("close"); } } ] });
			 */
			$
					.AjaxColynJson(
							"/product/json/ProductCheckYn.json",
							{
								pdo_product_id : row.pdo_product_id,
							},
							function(data) {
								if (data.success) {
									var editDialog = $
											.hDialog({
												href : "/product/productEditView?pdo_product_id="
														+ row.pdo_product_id
														+ "&actionMode=1",// 显示修改页面
												// maximizable: true,//显示最大化
												width : $(window).width() - 50,
												height : $(window).height() - 50,
												iconCls : 'icon-pencil',
												title : "产品修改",
												onLoad : function() {
												},
												buttons : [
														{
															text : '确认修改',
															iconCls : 'icon-pencil',
															handler : function() {
																if ($('#Colyn')
																		.form(
																				'validate')) {
																	var param = createParam2([
																			{
																				formId : 'main',
																				relaClass : "main"
																			},
																			{
																				formId : 'rate',
																				isAnyRow : true
																			} ]);
																	// console.log(param);
																	$
																			.AjaxColynJson(
																					"/product/json/productAddView.json?actionMode=2",
																					param,
																					function(
																							data) {
																						if (data.success) {
																							Colyn
																									.log("修改成功！");
																							editDialog
																									.dialog("close");
																							MyAction
																									.Init();
																							jQuery(
																									'#ProductMaintainListGrid')
																									.datagrid(
																											'clearSelections');
																						} else {
																							Colyn
																									.log("修改失败！");
																						}
																					},
																					"json");

																}
															}
														},
														{
															text : '关闭',
															iconCls : 'icon-cancel',
															handler : function() {
																editDialog
																		.dialog("close");
															}
														} ]
											});
								} else {
									Colyn.log(data.Msg);
								}
							});

		}
	},
	// 查看
	LookUp : function() {
		var row = MyGrid.selectRow();
		var num = row;
		if (num == null) {
			Colyn.log("请选择一条记录进行操作");
			return;
		}
		var Dialog = $.hDialog({
			href : "/product/ProductLookUpView?pdo_product_id="
					+ row.pdo_product_id,
			iconCls : 'icon-add',
			title : "产品详情",
			width : 750,
			height : $(window).height() - 50,
			// onLoad: function () {
			// MyAction.GetTemplate(row.gsc_Id, row.gte_Id);
			// },
			buttons : [ {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					Dialog.dialog("close");
				}
			} ]
		});
	},
	Del : function() {
		var row = MyGrid.selectRow();
		if (row) {
			/*
			 * if (row.pdo_product_id == "1" || row.pdo_product_id == "2" ||
			 * row.pdo_product_id == "3" || row.pdo_product_id == "4") {
			 * Colyn.log("产品信息不可删除！"); return; }
			 */
			$.messager.confirm("删除产品信息", "确认要删除选中的产品信息吗？", function(r) {
				if (r) {
					$.AjaxColynJson('/product/json/ProductDeleteView.json', {
						pdo_product_id : row.pdo_product_id,
						pdo_product_rate_Id : row.pdo_product_rate_Id
					}, function(data) {
						if (data.success) {
							Colyn.log("删除成功");
							MyAction.Init();
							jQuery('#ProductMaintainListGrid').datagrid(
									'clearSelections');
						} else {
							Colyn.log("删除失败");
						}
					});
				}
			});
		} else {
			Colyn.log("请选择需要删除的信息");
		}
	},
	Search : function() {
		var param = createParam3("SearchForm");
		$.post("/product/json/actionProductListView.json?t=" + new Date(),
				param, function(data) {
					$("#ProductMaintainListGrid").datagrid("loadData", data);
				}, "json");
	}

};
