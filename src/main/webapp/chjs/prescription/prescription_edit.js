var addurl=null;

//表格
var TableInit_drugs_table = null;
var TableInit_allergen_table = null;
var TableInit_disease_table = null;
var TableInit_operation_table = null;
var TableInit_fujiadrug_table = null;
var TableInit_fujiadisease_table = null;
var TableInit_fujiaotherrecip_table = null;
var TableInit_fujiaexaminfo_table = null;
var TableInit_fujialabinfo_table = null;
var TableInit_dictdrug_table = null;

//PASS-JSON解析
var PassClient= null;
var Patient= null;
var ScreenAllergenList= null;
var ScreenMedCondList= null;
var ScreenOperationList= null;
var ScreenDrugList= null;
var InputJsonInfoList= null;

$(document).ready(function() {
	addurl=$("#addurl").val();
	
	//生成表格
	TableInit_drugs_table = new TableInit_drugs();
	TableInit_drugs_table.Init();
	
	TableInit_allergen_table = new TableInit_allergen();
	TableInit_allergen_table.Init();
	
	TableInit_disease_table = new TableInit_disease();
	TableInit_disease_table.Init();
	
	TableInit_operation_table = new TableInit_operation();
	TableInit_operation_table.Init();
	
	TableInit_fujiadrug_table = new TableInit_fujiadrug();
	TableInit_fujiadrug_table.Init();
	
	TableInit_fujiadisease_table = new TableInit_fujiadisease();
	TableInit_fujiadisease_table.Init();
	
	TableInit_fujiaotherrecip_table = new TableInit_fujiaotherrecip();
	TableInit_fujiaotherrecip_table.Init();
	
	TableInit_fujiaexaminfo_table = new TableInit_fujiaexaminfo();
	TableInit_fujiaexaminfo_table.Init();
	
	TableInit_fujialabinfo_table = new TableInit_fujialabinfo();
	TableInit_fujialabinfo_table.Init();
	
	//字典表
	TableInit_dictdrug_table = new TableInit_dict_drug();
	TableInit_dictdrug_table.Init();
	
	//初始化时间插件
	init_time();
	
	//获取JSON数据,解析处理
	var prescription_json=$("#prescription_json").val();
	prescription_json = JSON.parse(prescription_json);
	json_fenjie(prescription_json);
	
//	$('#drugsmessage .input-group').datetimepicker({
//		format: 'YYYY-MM-DD HH:mm:ss',  //YYYY-MM-DD HH:mm:ss
//        locale: moment.locale('zh-cn'),
//	});
});


//医嘱表格对象
var TableInit_drugs =function () {
	var oTableInit=new Object();
//	var address=addurl+"/pass/pass_team";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#drugsmessage #json_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#drugsmessage #json_table').bootstrapTable({
//			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#drugsmessage #toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "client",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			sumMoney: 'total',
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10, 25, 50, 100],        // 可供选择的每页的行数（*）
			search: false,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: false,                  // 是否显示所有的列按钮
			showRefresh: false,                  // 是否显示刷新按钮
			showToggle:false,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{
				checkbox : true,width:30
			} , {
				width:70,
				field : 'RecipNo',
				title : '处方号',
				editable: {
                    type: 'text',
                    title: 'RecipNo',
                    validate: function (v) {
                    }
                }
			}, {
				width:50,
				field : 'Index',
				title : '序号',
				editable: {
                    type: 'text',
                    title: 'Index',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'OrderNo',
				title : '医嘱号',
				editable: {
                    type: 'text',
                    title: 'OrderNo',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'DrugSource',
				title : '药品来源',
				editable: {
                    type: 'text',
                    title: 'DrugSource',
                    validate: function (v) {
                    }
                }
			}, {
				width:150,
				field : 'DrugUniqueCode',
				title : '药品唯一码',
				editable: {
                    type: 'text',
                    title: 'DrugUniqueCode',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'DrugCode',
				title : '药品编码',
				editable: {
                    type: 'text',
                    title: 'DrugCode',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'DrugName',
				title : '药品名称',
				editable: {
                    type: 'text',
                    title: 'DrugName',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'DoseUnit',
				title : '给药单位',
				editable: {
                    type: 'text',
                    title: 'DoseUnit',
                    validate: function (v) {
                    }
                }
			}, {
				width:50,
				field : 'Form',
				title : '剂型',
				editable: {
                    type: 'text',
                    title: 'Form',
                    validate: function (v) {
                    }
                }
			}, {
				width:50,
				field : 'Strength',
				title : '规格',
				editable: {
                    type: 'text',
                    title: 'Strength',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'CompName',
				title : '厂家名称',
				editable: {
                    type: 'text',
                    title: 'CompName',
                    validate: function (v) {
                    }
                }
			}, {
				width:110,
				field : 'RouteSource',
				title : '给药途径来源',
				editable: {
                    type: 'text',
                    title: 'RouteSource',
                    validate: function (v) {
                    }
                }
			}, {
				width:110,
				field : 'RouteCode',
				title : '给药途径编码',
				editable: {
                    type: 'text',
                    title: 'RouteCode',
                    validate: function (v) {
                    }
                }
			}, {
				width:110,
				field : 'RouteName',
				title : '给药途径名称',
				editable: {
                    type: 'text',
                    title: 'RouteName',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'FreqSource',
				title : '频次来源',
				editable: {
                    type: 'text',
                    title: 'FreqSource',
                    validate: function (v) {
                    }
                }
			}, {
				width:50,
				field : 'Frequency',
				title : '频次',
				editable: {
                    type: 'text',
                    title: 'Frequency',
                    validate: function (v) {
                    }
                }
			}, {
				width:50,
				field : 'DosePerTime',
				title : '量/次',
				editable: {
                    type: 'text',
                    title: 'DosePerTime',
                    validate: function (v) {
                    }
                }
			}, {
				width:150,
				field : 'StartTime',
				title : '开嘱时间',
//				formatter: function (value, row, index) {
//					var htmlstr='<div class="input-group date"> '
//						+'<input type="text" class="form-control" value="'+value+'"/>'
//						+'<span class="input-group-addon">'
//						+'<span class="glyphicon glyphicon-calendar"></span> '
//						+'</span>  '
//						+'</div>'
//					
//					return htmlstr;
//			    }
				editable: {
                    type: 'combodate',
                    format :'YYYY-MM-DD HH:mm:ss',
                    viewformat : 'YYYY-MM-DD HH:mm:ss',
                    template:'YYYY-MM-DD HH:mm:ss',
                    pk:1,
                    title:"设置时间",
                },
			}, {
				width:150,
				field : 'EndTime',
				title : '停嘱时间',
				editable: {
                    type: 'combodate',
                    format :'YYYY-MM-DD HH:mm:ss',
                    viewformat : 'YYYY-MM-DD HH:mm:ss',
                    template:'YYYY-MM-DD HH:mm:ss',
                    pk:1,
                    title:"设置时间",
                },
			}, {
				width:150,
				field : 'ExecuteTime',
				title : '执行时间',
				editable: {
                    type: 'combodate',
                    format :'YYYY-MM-DD HH:mm:ss',
                    viewformat : 'YYYY-MM-DD HH:mm:ss',
                    template:'YYYY-MM-DD HH:mm:ss',
                    pk:1,
                    title:"设置时间",
                },
			}, {
				width:70,
				field : 'DeptCode',
				title : '科室编码',
				editable: {
                    type: 'text',
                    title: 'DeptCode',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'DeptName',
				title : '科室名称',
				editable: {
                    type: 'text',
                    title: 'DeptName',
                    validate: function (v) {
                    }
                }
			}, {
				width:110,
				field : 'DoctorCode',
				title : '开嘱医生编码',
				editable: {
                    type: 'text',
                    title: 'DoctorCode',
                    validate: function (v) {
                    }
                }
			}, {
				width:110,
				field : 'DoctorName',
				title : '开嘱医生名称',
				editable: {
                    type: 'text',
                    title: 'DoctorName',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'GroupTag',
				title : '成组标记',
				editable: {
                    type: 'text',
                    title: 'GroupTag',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'IsTempDrug',
				title : '临时用药',
				editable: {
	                type: 'select',
	                pk: 1,
	                source: [
	                    {value: 0, text: '长期'},
	                    {value: 1, text: '临时'},
	                ]
	            }
			}, {
				width:110,
				field : 'OrderType',
				title : '医嘱类别标记',
				editable: {
	                type: 'select',
	                pk: 1,
	                source: [
	                    {value: 0, text: '在用'},
	                    {value: 1, text: '已作废'},
	                    {value: 2, text: '已停嘱'},
	                    {value: 3, text: '出院带药'},
	                ]
	            }
			}, {
				width:110,
				field : 'Pharmacists',
				title : '审核/调配药师',
				editable: {
                    type: 'text',
                    title: 'Pharmacists',
                    validate: function (v) {
                    }
                }
			}, {
				width:110,
				field : 'Pharmacists_',
				title : '核对/发药药师',
				editable: {
                    type: 'text',
                    title: 'Pharmacists_',
                    validate: function (v) {
                    }
                }
			}, {
				width:110,
				field : 'Num',
				title : '药品开出数量',
				editable: {
                    type: 'text',
                    title: 'Num',
                    validate: function (v) {
                    }
                }
			}, {
				width:130,
				field : 'NumUnit',
				title : '药品开出数量单位',
				editable: {
                    type: 'text',
                    title: 'NumUnit',
                    validate: function (v) {
                    }
                }
			}, {
				width:50,
				field : 'Cost',
				title : '费用',
				editable: {
                    type: 'text',
                    title: 'Cost',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'Purpose',
				title : '用药目的',
				editable: {
	                type: 'select',
	                pk: 1,
	                source: [
	                    {value: 0, text: '默认'},
	                    {value: 1, text: '可能预防'},
	                    {value: 2, text: '可能治疗'},
	                    {value: 3, text: '预防'},
	                    {value: 4, text: '治疗'},
	                    {value: 5, text: '预防+治疗'},
	                ]
	            }
			}, {
				width:70,
				field : 'OprCode',
				title : '手术编号',
				editable: {
                    type: 'text',
                    title: 'OprCode',
                    validate: function (v) {
                    }
                }
			}, {
				width:70,
				field : 'MediTime',
				title : '用药时机',
				editable: {
                    type: 'text',
                    title: 'MediTime',
                    validate: function (v) {
                    }
                }
			}, {
				width:110,
				field : 'Remark',
				title : '医嘱备注信息',
				editable: {
                    type: 'text',
                    title: 'Remark',
                    validate: function (v) {
                    }
                }
			} ],
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
//				alert(row.DrugCode)
//				alert(1);
//				alert(row);
//				alert(tr);
//				alert(flied)         
			},

			// 2. 点击前面的复选框进行对应的操作
			// 点击全选框时触发的操作
			onCheckAll:function(rows){
//				alert(2);
//				alert(rows);      
			},
			// 点击每一个单选框时触发的操作
			onCheck:function(row){
//				alert(3);
//				alert(row);      
			},
			// 取消每一个单选框时对应的操作；
			onUncheck:function(row){
//				alert(4);
//				alert(row);      
			},
			
			//行内编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				alert(1)
//                $.ajax({
//                    type: "post",
//                    url: "/Editable/Edit",
//                    data: row,
//                    dataType: 'JSON',
//                    success: function (data, status) {
//                        if (status == "success") {
//                            alert('提交数据成功');
//                        }
//                    },
//                    error: function () {
//                        alert('编辑失败');
//                    },
//                    complete: function () {
//
//                    }
//
//                });
            }
		});
	}
	
	return oTableInit;
};

//过敏原表格对象
var TableInit_allergen =function () {
	var oTableInit=new Object();
//	var address=addurl+"/pass/pass_team";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#allergenmessage #json_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#allergenmessage #json_table').bootstrapTable({
//			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			//data:prescription_json,
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#allergenmessage #toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "client",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10, 25, 150, 150],        // 可供选择的每页的行数（*）
			search: false,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: false,                  // 是否显示所有的列按钮
			showRefresh: false,                  // 是否显示刷新按钮
			showToggle:false,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{checkbox : true,width:30
			},
			{field:'Index',title:'序号',width:100,align:'center',
				editable: {
                    type: 'text',
                    title: 'Index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'AllerSource',title:'过敏原来源',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'AllerSource',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'AllerCode',title:'过敏原编号',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'AllerCode',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'AllerName',title:'过敏原名称',width:200,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'AllerName',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'AllerSymptom',title:'过敏源症状',align:'center',
	        	editable: {
                    type: 'text',
                    title: 'AllerSymptom',
                    validate: function (v) {
                    }
                }
	        }],
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
			},

			// 2. 点击前面的复选框进行对应的操作
			// 点击全选框时触发的操作
			onCheckAll:function(rows){
			},
			// 点击每一个单选框时触发的操作
			onCheck:function(row){
			},
			// 取消每一个单选框时对应的操作；
			onUncheck:function(row){
			},
			
			//行内编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				alert(1)
            }
		});
	}
	
	return oTableInit;
};

//疾病表格对象
var TableInit_disease =function () {
	var oTableInit=new Object();
//	var address=addurl+"/pass/pass_team";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#diseasemessage #json_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#diseasemessage #json_table').bootstrapTable({
//			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			//data:prescription_json,
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#diseasemessage #toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "client",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10, 25, 150, 150],        // 可供选择的每页的行数（*）
			search: false,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: false,                  // 是否显示所有的列按钮
			showRefresh: false,                  // 是否显示刷新按钮
			showToggle:false,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{checkbox : true,width:30
			},
			{field:'RecipNo',title:'处方号',width:100,align:'center',
				editable: {
                    type: 'text',
                    title: 'RecipNo',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'Index',title:'序号',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'Index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'DisSource',title:'疾病来源',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'DisSource',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'DiseaseCode',title:'疾病编号',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'DiseaseCode',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'DiseaseName',title:'疾病名称',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'DiseaseName',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'DisTimeType',title:'诊断时间类型',width:100,align:'center',
	        	editable: {
	                type: 'select',
	                pk: 1,
	                source: [
	                    {value: -1, text: '未知'},
	                    {value: 0, text: '出院诊断'},
	                    {value: 1, text: '入院诊断'},
	                ]
	            }
	        },
	        {field:'Ishospinfection',title:'院内继发感染',align:'center',
	        	editable: {
	                type: 'select',
	                pk: 1,
	                source: [
	                    {value: -1, text: '未知'},
	                    {value: 0, text: '不是'},
	                    {value: 1, text: '是'},
	                ]
	            }
	        }],
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
			},

			// 2. 点击前面的复选框进行对应的操作
			// 点击全选框时触发的操作
			onCheckAll:function(rows){
			},
			// 点击每一个单选框时触发的操作
			onCheck:function(row){
			},
			// 取消每一个单选框时对应的操作；
			onUncheck:function(row){
			},
			
			//行内编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				alert(1)
            }
		});
	}
	
	return oTableInit;
};

//手术表格对象
var TableInit_operation =function () {
	var oTableInit=new Object();
//	var address=addurl+"/pass/pass_team";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#operationmessage #json_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#operationmessage #json_table').bootstrapTable({
//			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			//data:prescription_json,
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#operationmessage #toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "client",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10, 25, 150, 150],        // 可供选择的每页的行数（*）
			search: false,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: false,                  // 是否显示所有的列按钮
			showRefresh: false,                  // 是否显示刷新按钮
			showToggle:false,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{checkbox : true,width:30
			},
			{field:'Index',title:'序号',width:100,align:'center',
				editable: {
                    type: 'text',
                    title: 'Index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'OprCode',title:'手术编号',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'OprCode',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'OprName',title:'手术名称',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'OprName',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'IncisionType',title:'切口类型',width:100,align:'center',
	        	editable: {
	                type: 'select',
	                pk: 1,
	                source: [
	                    {value: 0, text: '0类'},
	                    {value: 1, text: '1类'},
	                    {value: 2, text: '2类'},
	                    {value: 3, text: '3类'},
	                    {value: 4, text: '其他'},
	                ]
	            }
	        },
	        {field:'OprStartDate',title:'手术开始时间',width:150,align:'center',
	        	editable: {
                    type: 'combodate',
                    format :'YYYY-MM-DD HH:mm:ss',
                    viewformat : 'YYYY-MM-DD HH:mm:ss',
                    template:'YYYY-MM-DD HH:mm:ss',
                    pk:1,
                    title:"设置时间",
                },
	        },
	        {field:'OprEndDate',title:'手术结束时间',width:150,align:'center',
	        	editable: {
                    type: 'combodate',
                    format :'YYYY-MM-DD HH:mm:ss',
                    viewformat : 'YYYY-MM-DD HH:mm:ss',
                    template:'YYYY-MM-DD HH:mm:ss',
                    pk:1,
                    title:"设置时间",
                },
	        },
	        {field:'OprMediTime',title:'手术用药时机',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'OprMediTime',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'OprTreatTime',title:'手术预防使用抗菌药物疗程',align:'center',
	        	editable: {
                    type: 'text',
                    title: 'OprTreatTime',
                    validate: function (v) {
                    }
                }
	        }],
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
			},

			// 2. 点击前面的复选框进行对应的操作
			// 点击全选框时触发的操作
			onCheckAll:function(rows){
			},
			// 点击每一个单选框时触发的操作
			onCheck:function(row){
			},
			// 取消每一个单选框时对应的操作；
			onUncheck:function(row){
			},
			
			//行内编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				alert(1)
            }
		});
	}
	
	return oTableInit;
};

//补充药品表格对象
var TableInit_fujiadrug =function () {
	var oTableInit=new Object();
//	var address=addurl+"/pass/pass_team";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#fujiadrug #json_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#fujiadrug #json_table').bootstrapTable({
//			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			//data:prescription_json,
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#fujiadrug #toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "client",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10, 25, 150, 150],        // 可供选择的每页的行数（*）
			search: false,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: false,                  // 是否显示所有的列按钮
			showRefresh: false,                  // 是否显示刷新按钮
			showToggle:false,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{checkbox : true,width:30
			},
			{field:'type',title:'类型',width:100,align:'center',
				formatter: function(value,row,index){
					return "druginfo";
				}
			}, 
			{field:'index',title:'药品序号',width:100,align:'center',
				editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'driprate',title:'滴速',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'driprate',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'driptime',title:'滴注时间',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'driptime',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'duration',title:'持续时间',align:'center',
	        	editable: {
                    type: 'text',
                    title: 'duration',
                    validate: function (v) {
                    }
                }
	        }],
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
			},

			// 2. 点击前面的复选框进行对应的操作
			// 点击全选框时触发的操作
			onCheckAll:function(rows){
			},
			// 点击每一个单选框时触发的操作
			onCheck:function(row){
			},
			// 取消每一个单选框时对应的操作；
			onUncheck:function(row){
			},
			
			//行内编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				alert(1)
            }
		});
	}
	
	return oTableInit;
};

//补充诊断表格对象
var TableInit_fujiadisease =function () {
	var oTableInit=new Object();
//	var address=addurl+"/pass/pass_team";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#fujiadisease #json_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#fujiadisease #json_table').bootstrapTable({
//			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			//data:prescription_json,
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#fujiadisease #toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "client",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10, 25, 150, 150],        // 可供选择的每页的行数（*）
			search: false,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: false,                  // 是否显示所有的列按钮
			showRefresh: false,                  // 是否显示刷新按钮
			showToggle:false,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{checkbox : true,width:30
			},
			{field:'type',title:'类型',width:100,align:'center',
				formatter: function(value,row,index){
					return "diseaseinfo";
				}
			}, 
	        {field:'index',title:'诊断序号',width:100,align:'center',
				editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'starttime',title:'诊断开始时间',width:100,align:'center',
	        	editable: {
                    type: 'combodate',
                    format :'YYYY-MM-DD HH:mm:ss',
                    viewformat : 'YYYY-MM-DD HH:mm:ss',
                    template:'YYYY-MM-DD HH:mm:ss',
                    pk:1,
                    title:"设置时间",
                },
	        },    
	        {field:'endtime',title:'诊断结束时间',align:'center',
	        	editable: {
                    type: 'combodate',
                    format :'YYYY-MM-DD HH:mm:ss',
                    viewformat : 'YYYY-MM-DD HH:mm:ss',
                    template:'YYYY-MM-DD HH:mm:ss',
                    pk:1,
                    title:"设置时间",
                },
	        }],
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
			},

			// 2. 点击前面的复选框进行对应的操作
			// 点击全选框时触发的操作
			onCheckAll:function(rows){
			},
			// 点击每一个单选框时触发的操作
			onCheck:function(row){
			},
			// 取消每一个单选框时对应的操作；
			onUncheck:function(row){
			},
			
			//行内编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				alert(1)
            }
		});
	}
	
	return oTableInit;
};

//补充历史医嘱信息表格对象
var TableInit_fujiaotherrecip =function () {
	var oTableInit=new Object();
//	var address=addurl+"/pass/pass_team";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#fujiaotherrecip #json_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#fujiaotherrecip #json_table').bootstrapTable({
//			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			//data:prescription_json,
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#fujiaotherrecip #toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "client",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10, 25, 150, 150],        // 可供选择的每页的行数（*）
			search: false,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: false,                  // 是否显示所有的列按钮
			showRefresh: false,                  // 是否显示刷新按钮
			showToggle:false,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{checkbox : true,width:30
			},
			{field:'type',title:'类型',width:100,align:'center',
				formatter: function(value,row,index){
					return "otherrecipinfo";
				}
			},  
	        {field:'hiscode',title:'医院编码',width:100,align:'center',
				editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'index',title:'医嘱序号',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'recipno',title:'处方号',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'drugsource',title:'药品来源',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'druguniquecode',title:'药品唯一码',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'drugname',title:'药品名称',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'doseunit',title:'给药单位',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'routesource',title:'给药途径来源',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'routecode',title:'给药途径编码',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'routename',title:'给药途径名称',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        }],
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
			},

			// 2. 点击前面的复选框进行对应的操作
			// 点击全选框时触发的操作
			onCheckAll:function(rows){
			},
			// 点击每一个单选框时触发的操作
			onCheck:function(row){
			},
			// 取消每一个单选框时对应的操作；
			onUncheck:function(row){
			},
			
			//行内编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				alert(1)
            }
		});
	}
	
	return oTableInit;
};

//补充检查信息表格对象
var TableInit_fujiaexaminfo =function () {
	var oTableInit=new Object();
//	var address=addurl+"/pass/pass_team";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#fujiaexaminfo #json_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#fujiaexaminfo #json_table').bootstrapTable({
//			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			//data:prescription_json,
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#fujiaexaminfo #toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "client",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10, 25, 150, 150],        // 可供选择的每页的行数（*）
			search: false,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: false,                  // 是否显示所有的列按钮
			showRefresh: false,                  // 是否显示刷新按钮
			showToggle:false,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{checkbox : true,width:30
			},
			{field:'type',title:'类型',width:100,align:'center',
				formatter: function(value,row,index){
					return "examinfo";
				}
			},  
	        {field:'requestno',title:'申请检查单号',width:100,align:'center',
				editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'labexamcode',title:'申请检查项目编码',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'labexamname',title:'申请检查项目名称',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'startdatetime',title:'申请检查时间',width:100,align:'center',
	        	editable: {
                    type: 'combodate',
                    format :'YYYY-MM-DD HH:mm:ss',
                    viewformat : 'YYYY-MM-DD HH:mm:ss',
                    template:'YYYY-MM-DD HH:mm:ss',
                    pk:1,
                    title:"设置时间",
                },
	        },    
	        {field:'deptcode',title:'申请检查科室编码',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'deptname',title:'申请检查科室名称',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'doctorcode',title:'申请检查医生编码',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'doctorname',title:'申请检查医生名称',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        }],
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
			},

			// 2. 点击前面的复选框进行对应的操作
			// 点击全选框时触发的操作
			onCheckAll:function(rows){
			},
			// 点击每一个单选框时触发的操作
			onCheck:function(row){
			},
			// 取消每一个单选框时对应的操作；
			onUncheck:function(row){
			},
			
			//行内编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				alert(1)
            }
		});
	}
	
	return oTableInit;
};

//补充检查信息表格对象
var TableInit_fujialabinfo =function () {
	var oTableInit=new Object();
//	var address=addurl+"/pass/pass_team";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#fujialabinfo #json_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#fujialabinfo #json_table').bootstrapTable({
//			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			//data:prescription_json,
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#fujialabinfo #toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "client",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10, 25, 150, 150],        // 可供选择的每页的行数（*）
			search: false,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: false,                  // 是否显示所有的列按钮
			showRefresh: false,                  // 是否显示刷新按钮
			showToggle:false,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{checkbox : true,width:30
			},
			{field:'type',title:'类型',width:100,align:'center',
				formatter: function(value,row,index){
					return "labinfo";
				}
			},  
	        {field:'requestno',title:'申请检验单号',width:100,align:'center',
				editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'labexamcode',title:'申请检验项目编码',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'labexamname',title:'申请检验项目名称',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'ch_labeltypedesc',title:'ch_损害类别',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'ch_labresult',title:'ch_labresult',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'ch_resultflag',title:'ch_resultflag',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'ch_range',title:'ch_range',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'ch_unit',title:'ch_unit',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'ch_reporttime',title:'ch_reporttime',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'deptcode',title:'申请检验科室编码',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'deptname',title:'申请检验科室名称',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'doctorcode',title:'申请检验医生编码',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },    
	        {field:'doctorname',title:'申请检查医生名称',width:100,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        }],
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
			},

			// 2. 点击前面的复选框进行对应的操作
			// 点击全选框时触发的操作
			onCheckAll:function(rows){
			},
			// 点击每一个单选框时触发的操作
			onCheck:function(row){
			},
			// 取消每一个单选框时对应的操作；
			onUncheck:function(row){
			},
			
			//行内编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				alert(1)
            }
		});
	}
	
	return oTableInit;
};

//初始化时间插件
function init_time(){
	$('#patientmessage #PregStartDate').datetimepicker({  
        format: 'YYYY-MM-DD HH:mm:ss',  //YYYY-MM-DD HH:mm:ss
        locale: moment.locale('zh-cn'),
//        defaultDate: "1990-1-1 00:00:01"
    });
	$('#patientmessage #Birthday').datetimepicker({  
        format: 'YYYY-MM-DD HH:mm:ss',  //YYYY-MM-DD HH:mm:ss
        locale: moment.locale('zh-cn'),
//        defaultDate: "1990-1-1 00:00:01"
    });
	$('#patientmessage #InHospDate').datetimepicker({  
        format: 'YYYY-MM-DD HH:mm:ss',  //YYYY-MM-DD HH:mm:ss
        locale: moment.locale('zh-cn'),
//        defaultDate: "1990-1-1 00:00:01"
    });
	$('#patientmessage #OutHospDate').datetimepicker({  
        format: 'YYYY-MM-DD HH:mm:ss',  //YYYY-MM-DD HH:mm:ss
        locale: moment.locale('zh-cn'),
//        defaultDate: "1990-1-1 00:00:01"
    });
	$('#patientmessage #UseTime').datetimepicker({  
        format: 'YYYY-MM-DD HH:mm:ss',  //YYYY-MM-DD HH:mm:ss
        locale: moment.locale('zh-cn'),
//        defaultDate: "1990-1-1 00:00:01"
    });
}

//药品字典表表格
var TableInit_dict_drug =function () {
	var oTableInit=new Object();
	var address=addurl+"/dict/drug";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#dict_drug_modal #dict_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#dict_drug_modal #dict_table').bootstrapTable({
			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
//			toolbar: '#drug_mess #toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "server",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10,25,50,100],        				// 可供选择的每页的行数（*）
			search: true,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: false,                  // 是否显示所有的列按钮
			showRefresh: true,                  // 是否显示刷新按钮
			showToggle:false,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
			queryParams : function (params) {
			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
			    		limit: params.limit,   // 页面数量大小
						offset: params.offset, // 当前页码
						search: params.search, // 工具栏查询内容,search:true才有
			    };
			    return temp;
			},
			// 是否显示父子表
			columns : [{checkbox : true,width:30
			},
			{field:'hisname',title:'机构',width:100,align:'center',
	        },    
	        {field:'drug_unique_code',title:'药品编号',width:100,align:'center',
	        },    
	        {field:'drugname',title:'药品名称',width:100,align:'center',
	        },    
	        {field:'drugform',title:'剂型',width:100,align:'center',
	        },    
	        {field:'drugspec',title:'规格',width:100,align:'center',
	        },    
	        {field:'comp_name',title:'厂家',width:100,align:'center',
	        },    
	        {field:'doseunit',title:'给药单位',width:100,align:'center',
	        }],
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
			},

			// 2. 点击前面的复选框进行对应的操作
			// 点击全选框时触发的操作
			onCheckAll:function(rows){
			},
			// 点击每一个单选框时触发的操作
			onCheck:function(row){
			},
			// 取消每一个单选框时对应的操作；
			onUncheck:function(row){
			},
			
			//行内编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				alert(1)
            }
		});
	}
	
	return oTableInit;
};

function dict_drug_modal_yes(){
	$("#dict_drug_modal #dict_table").bootstrapTable('destroy');
}

//给药途径字典表

//频次字典表

//科室字典表

//医生字典表

//疾病字典表

//手术字典表

//分解JSON
function json_fenjie(json){
	PassClient= json.PassClient;
	Patient= json.Patient;
	ScreenAllergenList= json.ScreenAllergenList;
	ScreenMedCondList= json.ScreenMedCondList;
	ScreenOperationList= json.ScreenOperationList;
	ScreenDrugList= json.ScreenDrugList;
	InputJsonInfoList= json.InputJsonInfoList; 
	
	//病人信息赋值
	$('#patientmessage #PatCode').val(Patient.PatCode);
	$('#patientmessage #InHospNo').val(Patient.InHospNo);
	$('#patientmessage #VisitCode').val(Patient.VisitCode);
	$('#patientmessage #Name').val(Patient.Name);
	$('#patientmessage #Sex').val(Patient.Sex);
	$('#patientmessage #Birthday input').val(Patient.Birthday);
	$('#patientmessage #HeightCM').val(Patient.HeightCM);
	$('#patientmessage #WeighKG').val(Patient.WeighKG);
	$('#patientmessage #DeptCode').val(Patient.DeptCode);
	$('#patientmessage #DeptName').val(Patient.DeptName);
	$('#patientmessage #DoctorCode').val(Patient.DoctorCode);
	$('#patientmessage #DoctorName').val(Patient.DoctorName);
	$('#patientmessage #PatStatus').val(Patient.PatStatus);
	$('#patientmessage #IsLactation').val(Patient.IsLactation);
	$('#patientmessage #IsPregnancy').val(Patient.IsPregnancy);
	$('#patientmessage #PregStartDate input').val(Patient.PregStartDate);
	$('#patientmessage #HepDamageDegree').val(Patient.HepDamageDegree);
	$('#patientmessage #RenDamageDegree').val(Patient.RenDamageDegree);
	$('#patientmessage #UseTime input').val(Patient.UseTime);
	$('#patientmessage #CheckMode').val(Patient.CheckMode);
	$('#patientmessage #IsDoSave').val(Patient.IsDoSave);
	$('#patientmessage #Age').val(Patient.Age);
	$('#patientmessage #PayClass').val(Patient.PayClass);
	$('#patientmessage #IsTestEtiology').val(Patient.IsTestEtiology);
	$('#patientmessage #InHospDate input').val(Patient.InHospDate);
	$('#patientmessage #OutHospDate input').val(Patient.OutHospDate);
	$('#patientmessage #IDCard').val(Patient.IDCard);
	$('#patientmessage #Telephone').val(Patient.Telephone);
	
	//医嘱信息赋值
	var ScreenDrugs = ScreenDrugList.ScreenDrugs;
	$('#drugsmessage #json_table').bootstrapTable('load',ScreenDrugs); 
//	for(var i=0; i<ScreenDrugs.length;i++){
//		var ScreenDrug = ScreenDrugs[i];
//
//		$('#drugsmessage #json_table').bootstrapTable('append',{
//			RecipNo: ScreenDrug.RecipNo,
//            Index: ScreenDrug.Index,
//            OrderNo:parseInt(ScreenDrug.OrderNo),
//            DrugSource: ScreenDrug.DrugSource,
//            DrugUniqueCode: ScreenDrug.DrugUniqueCode,
//            DrugCode: ScreenDrug.DrugCode,
//            DrugName: ScreenDrug.DrugName,
//            DoseUnit: ScreenDrug.DoseUnit,
//            Form: ScreenDrug.Form,
//            Strength: ScreenDrug.Strength,
//            CompName: ScreenDrug.CompName,
//            RouteSource: ScreenDrug.RouteSource,
//            RouteCode: ScreenDrug.RouteCode,
//            RouteName: ScreenDrug.RouteName,
//            FreqSource: ScreenDrug.FreqSource,
//            Frequency: ScreenDrug.Frequency,
//            DosePerTime: ScreenDrug.DosePerTime,
//            StartTime: ScreenDrug.StartTime,
//            EndTime: ScreenDrug.EndTime,
//            ExecuteTime: ScreenDrug.ExecuteTime,
//            DeptCode: ScreenDrug.DeptCode,
//            DeptName: ScreenDrug.DeptName,
//            DoctorCode: ScreenDrug.DoctorCode,
//            DoctorName: ScreenDrug.DoctorName,
//            GroupTag: ScreenDrug.GroupTag,
//            IsTempDrug:parseInt(ScreenDrug.IsTempDrug) ,
//            OrderType:parseInt(ScreenDrug.OrderType),
//            Pharmacists: ScreenDrug.Pharmacists,
//            Pharmacists_: ScreenDrug.Pharmacists_,
//            Num: ScreenDrug.Num,
//            NumUnit: ScreenDrug.NumUnit,
//            Cost: ScreenDrug.Cost,
//            Purpose: parseInt(ScreenDrug.Purpose),
//            OprCode: ScreenDrug.OprCode,
//            MediTime: ScreenDrug.MediTime,
//            Remark: ScreenDrug.Remark
//		});
//	}
	
	//过敏原信息赋值
	var ScreenAllergens = ScreenAllergenList.ScreenAllergens;
	$('#allergenmessage #json_table').bootstrapTable('load',ScreenAllergens); 
//	for(var i=0; i<ScreenAllergens.length;i++){
//		var ScreenAllergen = ScreenAllergens[i];
//		$('#allergenmessage #json_table').bootstrapTable('append',{
//			Index: ScreenAllergen.Index,
//			AllerSource: ScreenAllergen.AllerSource,
//			AllerCode: ScreenAllergen.AllerCode,
//			AllerName:ScreenAllergen.AllerName,
//			AllerSymptom:ScreenAllergen.AllerSymptom
//		});
//	}
	
	//疾病信息赋值
	var ScreenMedConds = ScreenMedCondList.ScreenMedConds;
	$('#diseasemessage #json_table').bootstrapTable('load',ScreenMedConds); 
//	for(var i=0; i<ScreenMedConds.length;i++){
//		var ScreenMedCond = ScreenMedConds[i];
//		$('#diseasemessage #json_table').bootstrapTable('append',{
//			RecipNo: ScreenMedCond.RecipNo,
//			Index: ScreenMedCond.Index,
//			DisSource: ScreenMedCond.DisSource,
//			DiseaseCode:ScreenMedCond.DiseaseCode,
//			DiseaseName:ScreenMedCond.DiseaseName,
//			DisTimeType:parseInt(ScreenMedCond.DisTimeType),
//			Ishospinfection:parseInt(ScreenMedCond.Ishospinfection)
//		});
//	}
	
	//手术信息赋值
	var ScreenOperations = ScreenOperationList.ScreenOperations;
	$('#operationmessage #json_table').bootstrapTable('load',ScreenOperations); 
//	for(var i=0; i<ScreenOperations.length;i++){
//		var ScreenOperation = ScreenOperations[i];
//		$('#operationmessage #json_table').bootstrapTable('append',{
//			Index: ScreenOperation.Index,
//			OprCode: ScreenOperation.OprCode,
//			OprName: ScreenOperation.OprName,
//			IncisionType:ScreenOperation.IncisionType,
//			OprStartDate:ScreenOperation.OprStartDate,
//			OprEndDate: ScreenOperation.OprEndDate,
//			OprMediTime:parseInt(ScreenOperation.OprMediTime),
//			OprTreatTime:parseInt(ScreenOperation.OprTreatTime)
//		});
//	}
	
	//附加信息节点赋值
	var InputJsonInfos = InputJsonInfoList.InputJsonInfos;
	for(var i=0; i<InputJsonInfos.length;i++){
		var InputJsonInfo = InputJsonInfos[i];
		if(InputJsonInfo.type=="jsontype"){
			$('#fujiamessage #jsontype').val(InputJsonInfo.screentype);
		}
		if(InputJsonInfo.type=="prtasktype"){
			$('#fujiatask #prtasktype').val(InputJsonInfo.urgent);
		}
		if(InputJsonInfo.type=="druginfo"){
			$('#fujiadrug #json_table').bootstrapTable('load',InputJsonInfo); 
//			$('#fujiadrug #json_table').bootstrapTable('append',{
//				type:"druginfo",
//				index: InputJsonInfo.index,
//				driprate: InputJsonInfo.driprate,
//				driptime: InputJsonInfo.driptime,
//				duration:InputJsonInfo.duration
//			});
		}
		if(InputJsonInfo.type=="diseaseinfo"){
			var aaa = new Array();
			aaa.push(InputJsonInfo);
			$('#fujiadisease #json_table').bootstrapTable('load',aaa); 
//			$('#fujiadisease #json_table').bootstrapTable('append',{
//				type:"diseaseinfo",
//				index: InputJsonInfo.index,
//				starttime: InputJsonInfo.starttime,
//				endtime: InputJsonInfo.endtime
//			});
		}
		if(InputJsonInfo.type=="otherrecipinfo"){
			var aaa = new Array();
			aaa.push(InputJsonInfo);
			$('#fujiaotherrecip #json_table').bootstrapTable('load',aaa); 
//			$('#fujiaotherrecip #json_table').bootstrapTable('append',{
//				type:"otherrecipinfo",
//				hiscode: InputJsonInfo.hiscode,
//				index: InputJsonInfo.index,
//				recipno: InputJsonInfo.recipno,
//				drugsource: InputJsonInfo.drugsource,
//				druguniquecode: InputJsonInfo.druguniquecode,
//				drugname: InputJsonInfo.drugname,
//				doseunit: InputJsonInfo.doseunit,
//				routesource: InputJsonInfo.routesource,
//				routecode: InputJsonInfo.routecode,
//				routename: InputJsonInfo.routename
//			});
		}
		if(InputJsonInfo.type=="examinfo"){
			var aaa = new Array();
			aaa.push(InputJsonInfo);
			$('#fujiaexaminfo #json_table').bootstrapTable('load',aaa); 
//			$('#fujiaexaminfo #json_table').bootstrapTable('append',{
//				type:"examinfo",
//				requestno:InputJsonInfo.requestno,
//				labexamcode: InputJsonInfo.labexamcode,
//				labexamname: InputJsonInfo.labexamname,
//				startdatetime: InputJsonInfo.startdatetime,
//				deptcode: InputJsonInfo.deptcode,
//				deptname: InputJsonInfo.deptname,
//				doctorcode: InputJsonInfo.doctorcode,
//				doctorname: InputJsonInfo.doctorname
//			});
		}
		if(InputJsonInfo.type=="labinfo"){
			var aaa = new Array();
			aaa.push(InputJsonInfo);
			$('#fujialabinfo #json_table').bootstrapTable('load',aaa);
//			$('#fujialabinfo #json_table').bootstrapTable('append',{
//				type:"labinfo",
//				requestno: InputJsonInfo.requestno,
//				labexamcode: InputJsonInfo.labexamcode,
//				labexamname: InputJsonInfo.labexamname,
//				ch_reporttime: InputJsonInfo.ch_reporttime,
//				deptcode: InputJsonInfo.deptcode,
//				deptname: InputJsonInfo.deptname,
//				doctorcode: InputJsonInfo.doctorcode,
//				doctorname: InputJsonInfo.doctorname,
//				ch_resultflag:InputJsonInfo.ch_resultflag,
//				ch_labeltypedesc:InputJsonInfo.ch_labeltypedesc,
//				ch_labresult:InputJsonInfo.ch_labresult,
//				ch_range:InputJsonInfo.ch_range,
//				ch_unit:InputJsonInfo.ch_unit
//			});
		}
	}
}

//合并JSON
function json_hebing(){
	
}


//药品信息操作
function drug_append(){
	var IdSelections = $('#drugsmessage .fixed-table-pagination .pagination-info').text();
	var sum=IdSelections.split('总共 ');
	sum=sum[1].split(' 条');
	parseInt(sum[0]);
	$('#drugsmessage #json_table').bootstrapTable('append',{
		Index: parseInt(sum[0])+1
	})
}
function drug_del(){
	swal({
        title: "Are you sure?",
        text: "You will not be able to recover this imaginary file!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	
    	var IdSelections = $('#drugsmessage #json_table').bootstrapTable('getSelections');
    	if(IdSelections.length==0){
    		return;
    	}
    	var pre_ids='';
    	for(var i=0;i<IdSelections.length;i++){
    		if(i==0){
    			pre_ids=pre_ids+IdSelections[i].pre_id;
    		}else{
    			pre_ids=pre_ids+','+ IdSelections[i].pre_id;
    		}
    	}
    	
    	$('#drugsmessage #json_table').bootstrapTable('removeByUniqueId',pre_ids);
    	
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}
