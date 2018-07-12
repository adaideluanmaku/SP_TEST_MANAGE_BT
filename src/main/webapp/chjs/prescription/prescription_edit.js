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
var TableInit_dictroute_table  = null;
var TableInit_dictfre_table = null;
var TableInit_dictdept_table = null;
var TableInit_dictdoctor_table = null;
var TableInit_dictallergen_table = null;
var TableInit_dictdisease_table = null;
var TableInit_dictoperation_table  = null;
var TableInit_dictexam_table  = null;
var TableInit_dictlab_table  = null;

//PASS-JSON解析
var PassClient= null;
var Patient= null;
var ScreenAllergenList= null;
var ScreenMedCondList= null;
var ScreenOperationList= null;
var ScreenDrugList= null;
var InputJsonInfoList= null;

//下拉单数据
var hospital=null;

$(document).ready(function() {
	addurl=$("#addurl").val();
	
	//下拉单数据
	_select2();
	
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
	
	TableInit_dictroute_table = new TableInit_dict_route();
	TableInit_dictroute_table.Init();
	
	TableInit_dictfre_table = new TableInit_dict_fre();
	TableInit_dictfre_table.Init();
	
	TableInit_dictdept_table = new TableInit_dict_dept();
	TableInit_dictdept_table.Init();
	
	TableInit_dictdoctor_table = new TableInit_dict_doctor();
	TableInit_dictdoctor_table.Init();
	
	TableInit_dictallergen_table = new TableInit_dict_allergen();
	TableInit_dictallergen_table.Init();
	
	TableInit_dictdisease_table = new TableInit_dict_disease();
	TableInit_dictdisease_table.Init();
	
	TableInit_dictoperation_table = new TableInit_dict_operation();
	TableInit_dictoperation_table.Init();
	
	TableInit_dictexam_table = new TableInit_dict_exam();
	TableInit_dictexam_table.Init();
	
	TableInit_dictlab_table = new TableInit_dict_lab();
	TableInit_dictlab_table.Init();
	
	//初始化时间插件
	init_time();
	
	//获取JSON数据,解析处理
	var prescription_json=null;
	if($("#prescription_json").val()!=''){
		prescription_json=$("#prescription_json").val();
		prescription_json = JSON.parse(prescription_json);
	}
	json_fenjie(prescription_json);
	
//	$('#drugsmessage .input-group').datetimepicker({
//		format: 'YYYY-MM-DD HH:mm:ss',  //YYYY-MM-DD HH:mm:ss
//        locale: moment.locale('zh-cn'),
//	});
	
	//鼠标浮动提示窗口-触发事件
	$(document).on('mouseover mouseout','table td',function(){ 
		if(event.type == 'mouseover'){//鼠标悬浮
			if($(this).text().length>5){//浮动窗口
				$('body').append('<div id="title" style="max-width:400px;max-height:200px;position: absolute;'
						+'top:0px;left:0px;background-color: #fff2e8;/*自动换行*/	word-wrap: break-word;' 
						+'overflow: hidden;text-overflow: ellipsis;'
						+'border: 1px solid #c0c0c0; z-index:9999;"></div>');
				
				if($(this).find('a').length > 0){
					$('#title').text($(this).find('a').text().substring(0,300));
				}else{
					$('#title').text($(this).text().substring(0,300));
				}
				
				$(this).mousemove(function(e) { 
					var xx = e.originalEvent.x || e.originalEvent.layerX || 0; 
					var yy = e.originalEvent.y || e.originalEvent.layerY || 0; 
					var newxx=null;
					var newyy=null;
					
					//如果提示框在最下面超过页面高度，则靠上显示
					var bodyheight=document.body.offsetHeight;
					var bodywidth=document.body.offsetWidth;
					if((yy+30+$('#title').height())<bodyheight){
//						newxx=xx+20;
						newyy=yy+10;
					}else{
//						newxx=xx+20;
						newyy=yy+10-$('#title').height();
					}
					if((xx+20+$('#title').width())<bodywidth){
						newxx=xx+20;
//						newyy=yy+10;
					}else{
						newxx=xx-20-$('#title').width();
//						newyy=yy+10-200;
					}
					
					$('#title').css('left',newxx+'px');
					$('#title').css('top',newyy+'px');
					
				}); 
			}
		}else if(event.type == 'mouseout'){//鼠标离开
			$('#title').remove();
		}
	}); 
	
});

//=========================================医生开处方信息界面，列表数据==============================
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
//			pagelabel: "json",					//数据类型
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
//			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			idField: "RoleId",
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
				field : 'select',
				checkbox : true,width:30
			} ,	{
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
	                type: 'select',
	                pk: 1,
	                source: [
	                    {value: 'PASS_INNER_PASS', text: 'PASS_INNER_PASS'},
	                    {value: 'USER', text: 'USER'},
	                ]
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
				width:150,
				field : 'DrugName',
				title : '药品名称',
				editable: {
                    type: 'text',
                    title: 'DrugName',
                    validate: function (v) {
                    },
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
	                type: 'select',
	                pk: 1,
	                source: [
	                    {value: 'PASS_INNER_PASS', text: 'PASS_INNER_PASS'},
	                    {value: 'USER', text: 'USER'},
	                ]
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
	                type: 'select',
	                pk: 1,
	                source: [
	                    {value: 'PASS_INNER_PASS', text: 'PASS_INNER_PASS'},
	                    {value: 'USER', text: 'USER'},
	                ]
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
			},
			onDblClickRow: function (row, tr, field) {
//				alert(tr[0].rowIndex)//获取点击的行数ID
                if(field=='DrugName'){
                	dict_drug_modal_open('bb',tr[0].rowIndex-1);
                }
                if(field=='RouteName'){
                	dict_route_modal_open('bb',tr[0].rowIndex-1);
                }
                if(field=='Frequency'){
                	dict_fre_modal_open('bb',tr[0].rowIndex-1);
                }
                if(field=='DeptName'){
                	dict_dept_modal_open('bb',tr[0].rowIndex-1);
                }
                if(field=='DoctorName'){
                	dict_doctor_modal_open('bb',tr[0].rowIndex-1);
                }
                if(field=='Pharmacists'){
                	dict_doctor_modal_open('bb_1',tr[0].rowIndex-1);
                }
                if(field=='Pharmacists_'){
                	dict_doctor_modal_open('bb_2',tr[0].rowIndex-1);
                }
            },
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
//			pagelabel: "json",					//数据类型
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
			clickToSelect: true,               // 是否启用点击选中行
			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
//			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{field : 'select',checkbox : true,width:30
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
	                type: 'select',
	                pk: 1,
	                source: [
	                    {value: 'PASS_INNER_PASS', text: 'PASS_INNER_PASS'},
	                    {value: 'USER', text: 'USER'},
	                ]
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
			onDblClickRow: function (row, tr, field) {
//				alert(tr[0].rowIndex)//获取点击的行数ID
                if(field=='AllerName'){
                	dict_allergen_modal_open('cc',tr[0].rowIndex-1);
                }
            },
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
//			pagelabel: "json",					//数据类型
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
//			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{field : 'select',checkbox : true,width:30
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
	                type: 'select',
	                pk: 1,
	                source: [
	                    {value: 'PASS_INNER_PASS', text: 'PASS_INNER_PASS'},
	                    {value: 'USER', text: 'USER'},
	                ]
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
	        {field:'DiseaseName',title:'疾病名称',width:250,align:'center',
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
			onDblClickRow: function (row, tr, field) {
//				alert(tr[0].rowIndex)//获取点击的行数ID
                if(field=='DiseaseName'){
                	dict_disease_modal_open('dd',tr[0].rowIndex-1);
                }
            },
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
//			pagelabel: "json",					//数据类型
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
//			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{field:'select',checkbox : true,width:30
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
	        {field:'OprName',title:'手术名称',width:200,align:'center',
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
			onDblClickRow: function (row, tr, field) {
//				alert(tr[0].rowIndex)//获取点击的行数ID
                if(field=='OprName'){
                	dict_operation_modal_open('ee',tr[0].rowIndex-1);
                }
            },
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
//			pagelabel: "json",					//数据类型
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
//			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{field:'select',checkbox : true,width:30
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
//			pagelabel: "json",					//数据类型
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
//			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{field:'select',checkbox : true,width:30
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
			onDblClickRow: function (row, tr, field) {
//				alert(tr[0].rowIndex)//获取点击的行数ID
                if(field=='DrugName'){
                	dict_drug_modal_open('dd',tr[0].rowIndex-1);
                }
                if(field=='RouteName'){
                	dict_route_modal_open('dd',tr[0].rowIndex-1);
                }
            },
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
//			pagelabel: "json",					//数据类型
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
//			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{field:'select',checkbox : true,width:30
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
	        {field:'drugname',title:'药品名称',width:200,align:'center',
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
			onDblClickRow: function (row, tr, field) {
//				alert(tr[0].rowIndex)//获取点击的行数ID
                if(field=='drugname'){
                	dict_drug_modal_open('ll',tr[0].rowIndex-1);
                }
                if(field=='routename'){
                	dict_route_modal_open('ll',tr[0].rowIndex-1);
                }
            },
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
//			pagelabel: "json",					//数据类型
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
//			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{field:'select',checkbox : true,width:30
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
	        {field:'labexamname',title:'申请检查项目名称',width:250,align:'center',
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
			onDblClickRow: function (row, tr, field) {
//				alert(tr[0].rowIndex)//获取点击的行数ID
                if(field=='labexamname'){
                	dict_exam_modal_open('mm',tr[0].rowIndex-1);
                }
                if(field=='deptname'){
                	dict_dept_modal_open('mm',tr[0].rowIndex-1);
                }
                if(field=='doctorname'){
                	dict_doctor_modal_open('mm',tr[0].rowIndex-1);
                }
            },
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
//			pagelabel: "json",					//数据类型
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
//			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
//			queryParams : function (params) {
//			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			    };
//			    return temp;
//			},
			// 是否显示父子表
			columns : [{field:'select',checkbox : true,width:30
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
	        {field:'labexamname',title:'申请检验项目名称',width:250,align:'center',
	        	editable: {
                    type: 'text',
                    title: 'index',
                    validate: function (v) {
                    }
                }
	        },
	        {field:'startdatetime',title:'申请时间',width:100,align:'center',
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
	        {field:'deptname',title:'申请检验科室名称',width:200,align:'center',
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
	        {field:'doctorname',title:'申请检查医生名称',width:200,align:'center',
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
			onDblClickRow: function (row, tr, field) {
//				alert(tr[0].rowIndex)//获取点击的行数ID
                if(field=='labexamname'){
                	dict_lab_modal_open('nn',tr[0].rowIndex-1);
                }
                if(field=='deptname'){
                	dict_dept_modal_open('nn',tr[0].rowIndex-1);
                }
                if(field=='doctorname'){
                	dict_doctor_modal_open('nn',tr[0].rowIndex-1);
                }
            },
		});
	}
	
	return oTableInit;
};

//=========================================时间插件初始化==============================
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

//=========================================用户双击弹出选择字典表数据时==============================
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
//			pagelabel: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#dict_drug_modal #toolbar',                // 工具按钮用哪个容器
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
//			uniqueId: "id",                  	// 每一行的唯一标识，一般为主键列
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
			columns : [{field:'select',radio : true,width:30
			},
//			{field:'ID',title:'ID',width:50,
//				formatter: function (value, row, index) {
//					return index+1;
//				}
//			},
			{field:'hisname',title:'机构',width:100,align:'center',
	        },    
	        {field:'drugcode',title:'药品编号',width:100,align:'center',
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
	        //编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				if(row.drug_unique_code == '' || row.hiscode == '' || row.doseunit == ''){
					swal({title: "提示",text: "机构、药品编码、给药单位不能为空."});
					return;
				}
//				alert(1)
                $.ajax({
                    type: "post",
                    url: $("#addurl").val()+"/dict/updatedrug",
                    data: row,
                    dataType: 'JSON',
                    success: function (data, status) {
//                    	swal({title: "提示",text: ""});
                    },
                    error: function () {
                        alert('编辑失败');
                    },
                    complete: function () {

                    }

                });
            }
		});
	}
	
	return oTableInit;
};

function dict_drug_open(type){
	if(type==1){
		$("#dict_drug_data_modal #hiscode").attr("readonly",false);
		$("#dict_drug_data_modal #drugcode").attr("readonly",false);
		$("#dict_drug_data_modal #drugspec").attr("readonly",false);
		
		$('#dict_drug_data_modal #btn_add').show();
		$('#dict_drug_data_modal #btn_update').hide();
	}
	if(type==2){
		var IdSelections = $('#dict_drug_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		$('#dict_drug_data_modal #hiscode').val(IdSelections[0].hiscode)
		$('#dict_drug_data_modal #drugcode').val(IdSelections[0].drugcode)
		$('#dict_drug_data_modal #drug_unique_code').val(IdSelections[0].drug_unique_code)
		$('#dict_drug_data_modal #drugname').val(IdSelections[0].drugname)
		$('#dict_drug_data_modal #drugform').val(IdSelections[0].drugform)
		$('#dict_drug_data_modal #drugspec').val(IdSelections[0].drugspec)
		$('#dict_drug_data_modal #comp_name').val(IdSelections[0].comp_name)
		$('#dict_drug_data_modal #doseunit').val(IdSelections[0].doseunit)
		
		$("#dict_drug_data_modal #hiscode").attr("readonly",true);
		$("#dict_drug_data_modal #drugcode").attr("readonly",true);
		$("#dict_drug_data_modal #drugspec").attr("readonly",true);
		
		$('#dict_drug_data_modal #btn_add').hide();
		$('#dict_drug_data_modal #btn_update').show();
	}
	
	$('#dict_drug_data_modal').modal('show');
}

function dict_drug_data(type){
	var queryurl=addurl+"/dict/drug";
	var inserturl=addurl+'/dict/insertdrug';
	var updateurl=addurl+'/dict/updatedrug';
	var deleteurl=addurl+'/dict/deletedrug';
	
	
	if(type==1){
		$.ajax({
			type:"POST",
			url:inserturl,
			async:false,
			cache:true,
			data:$('#dict_drug_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_drug_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_drug_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==2){
		$.ajax({
			type:"POST",
			url:updateurl,
			async:false,
			cache:true,
			data:$('#dict_drug_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_drug_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_drug_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==3){
		var IdSelections = $('#dict_drug_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		$.ajax({
			type:"POST",
			url:deleteurl,
			async:false,
			cache:true,
			data:{hiscode:IdSelections[0].hiscode,drugcode:IdSelections[0].drugcode,
				drug_unique_code:IdSelections[0].drug_unique_code,drugname:IdSelections[0].drugname,
				drugform:IdSelections[0].drugform,drugspec:IdSelections[0].drugspec,
				comp_name:IdSelections[0].comp_name,doseunit:IdSelections[0].doseunit},
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_drug_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_drug_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	
}


function dict_drug_delete(){
	var IdSelections=$("#dict_drug_modal #dict_table").bootstrapTable('getSelections');
	if(IdSelections.length==0){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	swal({
        title: "提示",
        text: "请确认是否进行删除数据操作",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	
    	$('#drugsmessage #json_table').bootstrapTable('removeSelectionId_CH');
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

function dict_drug_modal_open(pagelabel,rowIndex){
	if(pagelabel=='bb'){
		$("#dict_drug_modal #pagelabel").val(pagelabel);
		$("#dict_drug_modal #rowid").val(rowIndex);
	}
	if(pagelabel=='ll'){
		$("#dict_drug_modal #pagelabel").val(pagelabel);
		$("#dict_drug_modal #rowid").val(rowIndex);
	}
	
	//区分处理时字典数据维护还是选取字典数据
	if(rowIndex<0 || rowIndex==undefined){
		$("#dict_drug_modal #queren").hide();
	}else{
		$("#dict_drug_modal #queren").show();
	}
	
	$("#dict_drug_modal #dict_table").bootstrapTable('refresh',{silent:true});
	$('#dict_drug_modal').modal('show');
}
function dict_drug_modal_yes(){
	var IdSelections=$("#dict_drug_modal #dict_table").bootstrapTable('getSelections');
	if(IdSelections.length!=1){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	
	var pagelabel = $("#dict_drug_modal #pagelabel").val();
	if(pagelabel == 'bb'){
		$("#drugsmessage #json_table").bootstrapTable('updateRow',{
			index:$("#dict_drug_modal #rowid").val(),
			row:{
				DrugSource: "USER",
	            DrugUniqueCode: IdSelections[0].drug_unique_code,
	            DrugCode: IdSelections[0].drugcode,
	            DrugName: IdSelections[0].drugname,
	            DoseUnit: IdSelections[0].doseunit,
	            Form: IdSelections[0].drugform,
	            Strength: IdSelections[0].drugspec,
	            CompName: IdSelections[0].comp_name,
			}
		});
	}
	
	if(pagelabel == 'll'){
		$("#fujiaotherrecip #json_table").bootstrapTable('updateRow',{
			index:$("#dict_drug_modal #rowid").val(),
			row:{
				drugsource: "USER",
				druguniquecode: IdSelections[0].drug_unique_code,
	            drugname: IdSelections[0].drugname,
	            doseunit: IdSelections[0].doseunit,
			}
		});
	}
	
	$('#dict_drug_modal').modal('hide');
}

//给药途径字典表
var TableInit_dict_route =function () {
	var oTableInit=new Object();
	var address=addurl+"/dict/route";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#dict_route_modal #dict_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#dict_route_modal #dict_table').bootstrapTable({
			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			pagelabel: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#dict_route_modal #toolbar',                // 工具按钮用哪个容器
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
//			uniqueId: "id",                  	// 每一行的唯一标识，一般为主键列
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
			columns : [{field:'select',radio : true,width:30
			},
			{field:'hisname',title:'机构',width:100,align:'center',
	        },  
	        {field:'routecode',title:'给药途径编号',width:100,align:'center',
	        },    
	        {field:'routename',title:'给药途径名称',width:100,align:'center',
	        } ],
			
		});
	}
	
	return oTableInit;
};

function dict_route_open(type){
	if(type==1){
		$("#dict_route_data_modal #hiscode").attr("readonly",false);
		$("#dict_route_data_modal #routecode").attr("readonly",false);
		
		$('#dict_route_data_modal #btn_add').show();
		$('#dict_route_data_modal #btn_update').hide();
	}
	if(type==2){
		var IdSelections = $('#dict_route_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		$('#dict_route_data_modal #hiscode').val(IdSelections[0].hiscode)
		$('#dict_route_data_modal #routecode').val(IdSelections[0].routecode)
		$('#dict_route_data_modal #routename').val(IdSelections[0].routename)
		
		$("#dict_route_data_modal #hiscode").attr("readonly",true);
		$("#dict_route_data_modal #routecode").attr("readonly",true);
		
		$('#dict_route_data_modal #btn_add').hide();
		$('#dict_route_data_modal #btn_update').show();
	}
	
	$('#dict_route_data_modal').modal('show');
}

function dict_route_data(type){
	var queryurl=addurl+"/dict/route";
	var inserturl=addurl+'/dict/insertroute';
	var updateurl=addurl+'/dict/updateroute';
	var deleteurl=addurl+'/dict/deleteroute';
	
	
	if(type==1){
		$.ajax({
			type:"POST",
			url:inserturl,
			async:false,
			cache:true,
			data:$('#dict_route_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_route_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_route_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==2){
		$.ajax({
			type:"POST",
			url:updateurl,
			async:false,
			cache:true,
			data:$('#dict_route_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_route_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_route_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==3){
		var IdSelections = $('#dict_route_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		$.ajax({
			type:"POST",
			url:deleteurl,
			async:false,
			cache:true,
			data:{hiscode:IdSelections[0].hiscode,routecode:IdSelections[0].routecode,
				routename:IdSelections[0].routename},
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_route_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_route_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
}

function dict_route_modal_open(pagelabel,rowIndex){
	if(pagelabel=='bb'){
		$("#dict_route_modal #pagelabel").val(pagelabel);
		$("#dict_route_modal #rowid").val(rowIndex);
	}
	if(pagelabel=='ll'){
		$("#dict_route_modal #pagelabel").val(pagelabel);
		$("#dict_route_modal #rowid").val(rowIndex);
	}
	
	//区分处理时字典数据维护还是选取字典数据
	if(rowIndex<0 || rowIndex==undefined){
		$("#dict_route_modal #queren").hide();
	}else{
		$("#dict_route_modal #queren").show();
	}
	
	$("#dict_route_modal #dict_table").bootstrapTable('refresh',{silent:true});
	$('#dict_route_modal').modal('show');
}
function dict_route_modal_yes(){
	var IdSelections=$("#dict_route_modal #dict_table").bootstrapTable('getSelections');
	if(IdSelections.length!=1){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	
	var pagelabel = $("#dict_route_modal #pagelabel").val();
	if(pagelabel=='bb'){
		$("#drugsmessage #json_table").bootstrapTable('updateRow',{
			index:$("#dict_route_modal #rowid").val(),
			row:{
				RouteSource: "USER",
	            RouteCode: IdSelections[0].routecode,
	            RouteName: IdSelections[0].routename,
			}
		});
	}
	if(pagelabel=='ll'){
		$("#fujiaotherrecip #json_table").bootstrapTable('updateRow',{
			index:$("#dict_route_modal #rowid").val(),
			row:{
				routesource: "USER",
				routecode: IdSelections[0].routecode,
				routename: IdSelections[0].routename,
			}
		});
	}
	$('#dict_route_modal').modal('hide');
}

//频次字典表
var TableInit_dict_fre =function () {
	var oTableInit=new Object();
	var address=addurl+"/dict/fre";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#dict_fre_modal #dict_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#dict_fre_modal #dict_table').bootstrapTable({
			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			pagelabel: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#dict_fre_modal #toolbar',                // 工具按钮用哪个容器
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
//			uniqueId: "id",                  	// 每一行的唯一标识，一般为主键列
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
			columns : [{field:'select',radio : true,width:30
			},
			{field:'hisname',title:'机构',width:100,align:'center',
	        },  
	        {field:'frequency',title:'频次',width:100,align:'center',
	        }  ],
			
		});
	}
	
	return oTableInit;
};

function dict_fre_open(type){
	if(type==1){
		$('#dict_fre_data_modal #btn_add').show();
		$('#dict_fre_data_modal #btn_update').hide();
	}
	if(type==2){
		var IdSelections = $('#dict_fre_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		$('#dict_fre_data_modal #hiscode').val(IdSelections[0].hiscode)
		$('#dict_fre_data_modal #frequency').val(IdSelections[0].frequency)
		
		$('#dict_fre_data_modal #btn_add').hide();
		$('#dict_fre_data_modal #btn_update').show();
	}
	
	$('#dict_fre_data_modal').modal('show');
}

function dict_fre_data(type){
	var queryurl=addurl+"/dict/fre";
	var inserturl=addurl+'/dict/insertfre';
	var updateurl=addurl+'/dict/updatefre';
	var deleteurl=addurl+'/dict/deletefre';
	
	
	if(type==1){
		$.ajax({
			type:"POST",
			url:inserturl,
			async:false,
			cache:true,
			data:$('#dict_fre_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_fre_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_fre_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==2){
		$.ajax({
			type:"POST",
			url:updateurl,
			async:false,
			cache:true,
			data:$('#dict_fre_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_fre_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_fre_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==3){
		var IdSelections = $('#dict_fre_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		$.ajax({
			type:"POST",
			url:deleteurl,
			async:false,
			cache:true,
			data:{hiscode:IdSelections[0].hiscode,frequency:IdSelections[0].frequency},
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_fre_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_fre_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
}

function dict_fre_modal_open(pagelabel,rowIndex){
	if(pagelabel=='bb'){
		$("#dict_fre_modal #pagelabel").val(pagelabel);
		$("#dict_fre_modal #rowid").val(rowIndex);
	}
	
	//区分处理时字典数据维护还是选取字典数据
	if(rowIndex<0 || rowIndex==undefined){
		$("#dict_fre_modal #queren").hide();
	}else{
		$("#dict_fre_modal #queren").show();
	}
	
	$("#dict_fre_modal #dict_table").bootstrapTable('refresh',{silent:true});
	$('#dict_fre_modal').modal('show');
}
function dict_fre_modal_yes(){
	var IdSelections=$("#dict_fre_modal #dict_table").bootstrapTable('getSelections');
	if(IdSelections.length!=1){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	
	var pagelabel = $("#dict_fre_modal #pagelabel").val();
	if(pagelabel == 'bb'){
		$("#drugsmessage #json_table").bootstrapTable('updateRow',{
			index:$("#dict_fre_modal #rowid").val(),
			row:{
				FreqSource: "USER",
	            Frequency: IdSelections[0].frequency,
			}
		});
	}
	
	$('#dict_fre_modal').modal('hide');
}
//科室字典表
var TableInit_dict_dept =function () {
	var oTableInit=new Object();
	var address=addurl+"/dict/dept";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#dict_dept_modal #dict_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#dict_dept_modal #dict_table').bootstrapTable({
			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			pagelabel: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#dict_dept_modal #toolbar',                // 工具按钮用哪个容器
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
//			uniqueId: "id",                  	// 每一行的唯一标识，一般为主键列
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
			columns : [{field:'select',radio : true,width:30
			},
			{field:'hisname',title:'机构',width:100,align:'center',
	        },  
	        {field:'deptcode',title:'科室编号',width:100,align:'center',
	        },    
	        {field:'deptname',title:'科室名称',width:100,align:'center',
	        }],
			
		});
	}
	
	return oTableInit;
};

function dict_dept_open(type){
	if(type==1){
		$("#dict_dept_data_modal #hiscode").attr("readonly",false);
		$("#dict_dept_data_modal #deptcode").attr("readonly",false);
		
		$('#dict_dept_data_modal #btn_add').show();
		$('#dict_dept_data_modal #btn_update').hide();
	}
	if(type==2){
		var IdSelections = $('#dict_dept_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		$('#dict_dept_data_modal #hiscode').val(IdSelections[0].hiscode)
		$('#dict_dept_data_modal #deptcode').val(IdSelections[0].deptcode)
		$('#dict_dept_data_modal #deptname').val(IdSelections[0].deptname)
		
		$("#dict_dept_data_modal #hiscode").attr("readonly",true);
		$("#dict_dept_data_modal #deptcode").attr("readonly",true);
		
		$('#dict_dept_data_modal #btn_add').hide();
		$('#dict_dept_data_modal #btn_update').show();
	}
	
	$('#dict_dept_data_modal').modal('show');
}

function dict_dept_data(type){
	var queryurl=addurl+"/dict/dept";
	var inserturl=addurl+'/dict/insertdept';
	var updateurl=addurl+'/dict/updatedept';
	var deleteurl=addurl+'/dict/deletedept';
	
	
	if(type==1){
		$.ajax({
			type:"POST",
			url:inserturl,
			async:false,
			cache:true,
			data:$('#dict_dept_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_dept_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_dept_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==2){
		$.ajax({
			type:"POST",
			url:updateurl,
			async:false,
			cache:true,
			data:$('#dict_dept_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_dept_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_dept_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==3){
		var IdSelections = $('#dict_dept_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		$.ajax({
			type:"POST",
			url:deleteurl,
			async:false,
			cache:true,
			data:{hiscode:IdSelections[0].hiscode,deptcode:IdSelections[0].deptcode,
				deptname:IdSelections[0].deptname},
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_dept_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_dept_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
}

function dict_dept_modal_open(pagelabel,rowIndex){
	if(pagelabel=='patientmessage'){
		$("#dict_dept_modal #pagelabel").val(pagelabel);
	}
	if(pagelabel=='bb'){
		$("#dict_dept_modal #pagelabel").val(pagelabel);
		$("#dict_dept_modal #rowid").val(rowIndex);
	}
	if(pagelabel=='mm'){
		$("#dict_dept_modal #pagelabel").val(pagelabel);
		$("#dict_dept_modal #rowid").val(rowIndex);
	}
	if(pagelabel=='nn'){
		$("#dict_dept_modal #pagelabel").val(pagelabel);
		$("#dict_dept_modal #rowid").val(rowIndex);
	}
	
	//区分处理时字典数据维护还是选取字典数据
	if(rowIndex<0 || rowIndex==undefined){
		$("#dict_dept_modal #queren").hide();
	}else{
		$("#dict_dept_modal #queren").show();
	}
	
	$("#dict_dept_modal #dict_table").bootstrapTable('refresh',{silent:true});
	$('#dict_dept_modal').modal('show');
}
function dict_dept_modal_yes(){
	var IdSelections=$("#dict_dept_modal #dict_table").bootstrapTable('getSelections');
	if(IdSelections.length!=1){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	
	var pagelabel = $("#dict_dept_modal #pagelabel").val();
	if(pagelabel=='patientmessage'){
		$('#patientmessage #DeptCode').val(IdSelections[0].deptcode);
		$('#patientmessage #DeptName').val(IdSelections[0].deptname);
	}
	if(pagelabel=='bb'){
		$("#drugsmessage #json_table").bootstrapTable('updateRow',{
			index:$("#dict_dept_modal #rowid").val(),
			row:{
				DeptCode: IdSelections[0].deptcode,
	            DeptName: IdSelections[0].deptname,
			}
		});
	}
	if(pagelabel=='mm'){
		$("#fujiaexaminfo #json_table").bootstrapTable('updateRow',{
			index:$("#dict_dept_modal #rowid").val(),
			row:{
				deptcode: IdSelections[0].deptcode,
				deptname: IdSelections[0].deptname,
			}
		});
	}
	if(pagelabel=='nn'){
		$("#fujialabinfo #json_table").bootstrapTable('updateRow',{
			index:$("#dict_dept_modal #rowid").val(),
			row:{
				deptcode: IdSelections[0].deptcode,
				deptname: IdSelections[0].deptname,
			}
		});
	}
	$('#dict_dept_modal').modal('hide');
}
//医生字典表
var TableInit_dict_doctor =function () {
	var oTableInit=new Object();
	var address=addurl+"/dict/doctor";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#dict_doctor_modal #dict_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#dict_doctor_modal #dict_table').bootstrapTable({
			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			pagelabel: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#dict_doctor_modal #toolbar',                // 工具按钮用哪个容器
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
//			uniqueId: "id",                  	// 每一行的唯一标识，一般为主键列
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
			columns : [{field:'select',radio : true,width:30
			},
			{field:'hisname',title:'机构',width:100,align:'center',
	        },  
	        {field:'doctorcode',title:'医生编号',width:100,align:'center',
	        },    
	        {field:'doctorname',title:'医生名称',width:100,align:'center',
	        }],
			
		});
	}
	
	return oTableInit;
};

function dict_doctor_open(type){
	if(type==1){
		$("#dict_doctor_data_modal #hiscode").attr("readonly",false);
		$("#dict_doctor_data_modal #doctorcode").attr("readonly",false);
		
		$('#dict_doctor_data_modal #btn_add').show();
		$('#dict_doctor_data_modal #btn_update').hide();
	}
	if(type==2){
		var IdSelections = $('#dict_doctor_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		$('#dict_doctor_data_modal #hiscode').val(IdSelections[0].hiscode)
		$('#dict_doctor_data_modal #doctorcode').val(IdSelections[0].doctorcode)
		$('#dict_doctor_data_modal #doctorname').val(IdSelections[0].doctorname)
		
		$("#dict_doctor_data_modal #hiscode").attr("readonly",true);
		$("#dict_doctor_data_modal #doctorcode").attr("readonly",true);
		
		$('#dict_doctor_data_modal #btn_add').hide();
		$('#dict_doctor_data_modal #btn_update').show();
	}
	
	$('#dict_doctor_data_modal').modal('show');
}

function dict_doctor_data(type){
	var queryurl=addurl+"/dict/doctor";
	var inserturl=addurl+'/dict/insertdoctor';
	var updateurl=addurl+'/dict/updatedoctor';
	var deleteurl=addurl+'/dict/deletedoctor';
	
	
	if(type==1){
		$.ajax({
			type:"POST",
			url:inserturl,
			async:false,
			cache:true,
			data:$('#dict_doctor_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_doctor_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_doctor_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==2){
		$.ajax({
			type:"POST",
			url:updateurl,
			async:false,
			cache:true,
			data:$('#dict_doctor_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_doctor_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_doctor_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==3){
		var IdSelections = $('#dict_doctor_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		$.ajax({
			type:"POST",
			url:deleteurl,
			async:false,
			cache:true,
			data:{hiscode:IdSelections[0].hiscode,doctorcode:IdSelections[0].doctorcode,
				doctorname:IdSelections[0].doctorname},
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_doctor_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_doctor_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
}

function dict_doctor_modal_open(pagelabel,rowIndex){
	if(pagelabel=='patientmessage'){
		$("#dict_doctor_modal #pagelabel").val(pagelabel)
	}
	
	if(pagelabel=='bb'){
		$("#dict_doctor_modal #pagelabel").val(pagelabel)
		$("#dict_doctor_modal #rowid").val(rowIndex);
		
	}
	if(pagelabel=='bb_1'){
		$("#dict_doctor_modal #pagelabel").val(pagelabel)
		$("#dict_doctor_modal #rowid").val(rowIndex);
		
	}
	if(pagelabel=='bb_2'){
		$("#dict_doctor_modal #pagelabel").val(pagelabel)
		$("#dict_doctor_modal #rowid").val(rowIndex);
		
	}
	if(pagelabel=='mm'){
		$("#dict_doctor_modal #pagelabel").val(pagelabel)
		$("#dict_doctor_modal #rowid").val(rowIndex);
		
	}
	if(pagelabel=='nn'){
		$("#dict_doctor_modal #pagelabel").val(pagelabel)
		$("#dict_doctor_modal #rowid").val(rowIndex);
		
	}
	
	//区分处理时字典数据维护还是选取字典数据
	if(rowIndex<0 || rowIndex==undefined){
		$("#dict_doctor_modal #queren").hide();
	}else{
		$("#dict_doctor_modal #queren").show();
	}
	
	$("#dict_doctor_modal #dict_table").bootstrapTable('refresh',{silent:true});
	$('#dict_doctor_modal').modal('show');
}
function dict_doctor_modal_yes(){
	var IdSelections=$("#dict_doctor_modal #dict_table").bootstrapTable('getSelections');
	if(IdSelections.length!=1){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	
	var pagelabel = $("#dict_doctor_modal #pagelabel").val();
	if(pagelabel == 'patientmessage'){
		$('#patientmessage #DoctorCode').val(IdSelections[0].doctorcode);
		$('#patientmessage #DoctorName').val(IdSelections[0].doctorname);
	}
	if(pagelabel == 'bb'){
		$("#drugsmessage #json_table").bootstrapTable('updateRow',{
			index:$("#dict_doctor_modal #rowid").val(),
			row:{
				DoctorCode: IdSelections[0].doctorcode,
				DoctorName: IdSelections[0].doctorname,
			}
		});
	}
	if(pagelabel == 'bb_1'){
		$("#drugsmessage #json_table").bootstrapTable('updateRow',{
			index:$("#dict_doctor_modal #rowid").val(),
			row:{
				Pharmacists: IdSelections[0].doctorname,
			}
		});
	}
	if(pagelabel == 'bb_2'){
		$("#drugsmessage #json_table").bootstrapTable('updateRow',{
			index:$("#dict_doctor_modal #rowid").val(),
			row:{
				Pharmacists_: IdSelections[0].doctorname,
			}
		});
	}
	if(pagelabel == 'mm'){
		$("#fujiaexaminfo #json_table").bootstrapTable('updateRow',{
			index:$("#dict_doctor_modal #rowid").val(),
			row:{
				doctorcode: IdSelections[0].doctorcode,
				doctorname: IdSelections[0].doctorname,
			}
		});
		
	}
	if(pagelabel == 'nn'){
		$("#fujialabinfo #json_table").bootstrapTable('updateRow',{
			index:$("#dict_doctor_modal #rowid").val(),
			row:{
				doctorcode: IdSelections[0].doctorcode,
				doctorname: IdSelections[0].doctorname,
			}
		});
		
	}
	
	$('#dict_doctor_modal').modal('hide');
}

//过敏原字典表
var TableInit_dict_allergen =function () {
	var oTableInit=new Object();
	var address=addurl+"/dict/allergen";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#dict_allergen_modal #dict_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#dict_allergen_modal #dict_table').bootstrapTable({
			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			pagelabel: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#dict_allergen_modal #toolbar',                // 工具按钮用哪个容器
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
//			uniqueId: "id",                  	// 每一行的唯一标识，一般为主键列
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
			columns : [{field:'select',radio : true,width:30
			},
			{field:'hisname',title:'机构',width:100,align:'center',
	        },  
	        {field:'allercode',title:'过敏原编号',width:100,align:'center',
	        },    
	        {field:'allername',title:'过敏原名称',width:100,align:'center',
	        }],
			
		});
	}
	
	return oTableInit;
};

function dict_allergen_open(type){
	if(type==1){
		$("#dict_allergen_data_modal #hiscode").attr("readonly",false);
		$("#dict_allergen_data_modal #allercode").attr("readonly",false);
		
		$('#dict_allergen_data_modal #btn_add').show();
		$('#dict_allergen_data_modal #btn_update').hide();
	}
	if(type==2){
		var IdSelections = $('#dict_allergen_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		$('#dict_allergen_data_modal #hiscode').val(IdSelections[0].hiscode)
		$('#dict_allergen_data_modal #allercode').val(IdSelections[0].allercode)
		$('#dict_allergen_data_modal #allername').val(IdSelections[0].allername)
		
		$("#dict_allergen_data_modal #hiscode").attr("readonly",true);
		$("#dict_allergen_data_modal #allercode").attr("readonly",true);
		
		$('#dict_allergen_data_modal #btn_add').hide();
		$('#dict_allergen_data_modal #btn_update').show();
	}
	
	$('#dict_allergen_data_modal').modal('show');
}

function dict_allergen_data(type){
	var queryurl=addurl+"/dict/allergen";
	var inserturl=addurl+'/dict/insertallergen';
	var updateurl=addurl+'/dict/updateallergen';
	var deleteurl=addurl+'/dict/deleteallergen';
	
	
	if(type==1){
		$.ajax({
			type:"POST",
			url:inserturl,
			async:false,
			cache:true,
			data:$('#dict_allergen_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_allergen_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_allergen_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==2){
		$.ajax({
			type:"POST",
			url:updateurl,
			async:false,
			cache:true,
			data:$('#dict_allergen_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_allergen_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_allergen_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==3){
		var IdSelections = $('#dict_allergen_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		$.ajax({
			type:"POST",
			url:deleteurl,
			async:false,
			cache:true,
			data:{hiscode:IdSelections[0].hiscode,allercode:IdSelections[0].allercode,
				allername:IdSelections[0].allername},
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_allergen_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_allergen_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
}

function dict_allergen_modal_open(pagelabel,rowIndex){
	if(pagelabel=='cc'){
		$("#dict_allergen_modal #pagelabel").val(pagelabel);
		$("#dict_allergen_modal #rowid").val(rowIndex);
		
	}
	
	//区分处理时字典数据维护还是选取字典数据
	if(rowIndex<0 || rowIndex==undefined){
		$("#dict_allergen_modal #queren").hide();
	}else{
		$("#dict_allergen_modal #queren").show();
	}
	
	$("#dict_allergen_modal #dict_table").bootstrapTable('refresh',{silent:true});
	$('#dict_allergen_modal').modal('show');
}
function dict_allergen_modal_yes(){
	var IdSelections=$("#dict_allergen_modal #dict_table").bootstrapTable('getSelections');
	if(IdSelections.length!=1){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	
	var pagelabel=$("#dict_allergen_modal #pagelabel").val();
	if(pagelabel=='cc'){
		$("#allergenmessage #json_table").bootstrapTable('updateRow',{
			index:$("#dict_allergen_modal #rowid").val(),
			row:{
//				Index: aller_work_row+1,
				AllerSource: "USER",
				AllerCode: IdSelections[0].allercode,
				AllerName:IdSelections[0].allername,
				AllerSymptom:""
			}
		});
	}
	
	$('#dict_allergen_modal').modal('hide');
}
//疾病字典表
var TableInit_dict_disease =function () {
	var oTableInit=new Object();
	var address=addurl+"/dict/disease";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#dict_disease_modal #dict_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#dict_disease_modal #dict_table').bootstrapTable({
			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			pagelabel: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#dict_disease_modal #toolbar',                // 工具按钮用哪个容器
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
//			uniqueId: "id",                  	// 每一行的唯一标识，一般为主键列
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
			columns : [{field:'select',radio : true,width:30
			},
			{field:'hisname',title:'机构',width:100,align:'center',
	        },  
	        {field:'discode',title:'疾病编号',width:100,align:'center',
	        },    
	        {field:'disname',title:'疾病名称',width:100,align:'center',
	        }],
			
		});
	}
	
	return oTableInit;
};

function dict_disease_open(type){
	if(type==1){
		$("#dict_disease_data_modal #hiscode").attr("readonly",false);
		$("#dict_disease_data_modal #discode").attr("readonly",false);
		
		$('#dict_disease_data_modal #btn_add').show();
		$('#dict_disease_data_modal #btn_update').hide();
	}
	if(type==2){
		var IdSelections = $('#dict_disease_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		$('#dict_disease_data_modal #hiscode').val(IdSelections[0].hiscode)
		$('#dict_disease_data_modal #discode').val(IdSelections[0].discode)
		$('#dict_disease_data_modal #disname').val(IdSelections[0].disname)
		
		$("#dict_disease_data_modal #hiscode").attr("readonly",true);
		$("#dict_disease_data_modal #discode").attr("readonly",true);
		
		$('#dict_disease_data_modal #btn_add').hide();
		$('#dict_disease_data_modal #btn_update').show();
	}
	
	$('#dict_disease_data_modal').modal('show');
}

function dict_disease_data(type){
	var queryurl=addurl+"/dict/disease";
	var inserturl=addurl+'/dict/insertdisease';
	var updateurl=addurl+'/dict/updatedisease';
	var deleteurl=addurl+'/dict/deletedisease';
	
	
	if(type==1){
		$.ajax({
			type:"POST",
			url:inserturl,
			async:false,
			cache:true,
			data:$('#dict_disease_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_disease_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_disease_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==2){
		$.ajax({
			type:"POST",
			url:updateurl,
			async:false,
			cache:true,
			data:$('#dict_disease_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_disease_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_disease_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==3){
		var IdSelections = $('#dict_disease_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		$.ajax({
			type:"POST",
			url:deleteurl,
			async:false,
			cache:true,
			data:{hiscode:IdSelections[0].hiscode,discode:IdSelections[0].discode,
				disname:IdSelections[0].disname},
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_disease_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_disease_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
}

function dict_disease_modal_open(pagelabel,rowIndex){
	if(pagelabel=='dd'){
		$("#dict_disease_modal #pagelabel").val(pagelabel);
		$("#dict_disease_modal #rowid").val(rowIndex);
	}
	
	//区分处理时字典数据维护还是选取字典数据
	if(rowIndex<0 || rowIndex==undefined){
		$("#dict_disease_modal #queren").hide();
	}else{
		$("#dict_disease_modal #queren").show();
	}
	
	$("#dict_disease_modal #dict_table").bootstrapTable('refresh',{silent:true});
	$('#dict_disease_modal').modal('show');
}
function dict_disease_modal_yes(){
	var IdSelections=$("#dict_disease_modal #dict_table").bootstrapTable('getSelections');
	if(IdSelections.length!=1){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	
	var pagelabel = $("#dict_disease_modal #pagelabel").val();
	if(pagelabel=='dd'){
		$("#diseasemessage #json_table").bootstrapTable('updateRow',{
			index:$("#dict_disease_modal #rowid").val(),
			row:{
//				Index: dis_work_row+1,
				DisSource: 'USER',
				DiseaseCode:IdSelections[0].discode,
				DiseaseName:IdSelections[0].disname,
				DisTimeType:0,
				Ishospinfection:-1
			}
		});
	}
	
	$('#dict_disease_modal').modal('hide');
}
//手术字典表
var TableInit_dict_operation =function () {
	var oTableInit=new Object();
	var address=addurl+"/dict/operation";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#dict_operation_modal #dict_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#dict_operation_modal #dict_table').bootstrapTable({
			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			pagelabel: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#dict_operation_modal #toolbar',                // 工具按钮用哪个容器
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
//			uniqueId: "id",                  	// 每一行的唯一标识，一般为主键列
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
			columns : [{field:'select',radio : true,width:30
			},
			{field:'hisname',title:'机构',width:100,align:'center',
	        },  
	        {field:'operationcode',title:'手术编号',width:100,align:'center',
	        },    
	        {field:'operationname',title:'手术名称',width:100,align:'center',
	        }],
			
		});
	}
	
	return oTableInit;
};

function dict_operation_open(type){
	if(type==1){
		$("#dict_operation_data_modal #hiscode").attr("readonly",false);
		$("#dict_operation_data_modal #operationcode").attr("readonly",false);
		
		$('#dict_operation_data_modal #btn_add').show();
		$('#dict_operation_data_modal #btn_update').hide();
	}
	if(type==2){
		var IdSelections = $('#dict_operation_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		$('#dict_operation_data_modal #hiscode').val(IdSelections[0].hiscode)
		$('#dict_operation_data_modal #operationcode').val(IdSelections[0].operationcode)
		$('#dict_operation_data_modal #operationname').val(IdSelections[0].operationname)
		
		$("#dict_operation_data_modal #hiscode").attr("readonly",true);
		$("#dict_operation_data_modal #operationcode").attr("readonly",true);
		
		$('#dict_operation_data_modal #btn_add').hide();
		$('#dict_operation_data_modal #btn_update').show();
	}
	
	$('#dict_operation_data_modal').modal('show');
}

function dict_operation_data(type){
	var queryurl=addurl+"/dict/operation";
	var inserturl=addurl+'/dict/insertoperation';
	var updateurl=addurl+'/dict/updateoperation';
	var deleteurl=addurl+'/dict/deleteoperation';
	
	
	if(type==1){
		$.ajax({
			type:"POST",
			url:inserturl,
			async:false,
			cache:true,
			data:$('#dict_operation_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_operation_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_operation_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==2){
		$.ajax({
			type:"POST",
			url:updateurl,
			async:false,
			cache:true,
			data:$('#dict_operation_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_operation_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_operation_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==3){
		var IdSelections = $('#dict_operation_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		$.ajax({
			type:"POST",
			url:deleteurl,
			async:false,
			cache:true,
			data:{hiscode:IdSelections[0].hiscode,operationcode:IdSelections[0].operationcode,
				operationname:IdSelections[0].operationname},
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_operation_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_operation_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
}

function dict_operation_modal_open(pagelabel,rowIndex){
	if(pagelabel=='ee'){
		$("#dict_operation_modal #pagelabel").val(pagelabel);
		$("#dict_operation_modal #rowid").val(rowIndex);
	}
	
	//区分处理时字典数据维护还是选取字典数据
	if(rowIndex<0 || rowIndex==undefined){
		$("#dict_operation_modal #queren").hide();
	}else{
		$("#dict_operation_modal #queren").show();
	}
	
	$("#dict_operation_modal #dict_table").bootstrapTable('refresh',{silent:true});
	$('#dict_operation_modal').modal('show');
}
function dict_operation_modal_yes(){
	var IdSelections=$("#dict_operation_modal #dict_table").bootstrapTable('getSelections');
	if(IdSelections.length!=1){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	
	var pagelabel = $("#dict_operation_modal #pagelabel").val();
	if(pagelabel=='ee'){
		$("#operationmessage #json_table").bootstrapTable('updateRow',{
			index:$("#dict_operation_modal #rowid").val(),
			row:{
//				Index: opr_work_row+1,
				OprCode: IdSelections[0].operationcode,
				OprName: IdSelections[0].operationname,
				IncisionType:'1',
				OprStartDate:'',
				OprEndDate: '',
				OprMediTime:-1,
				OprTreatTime:0
			}
		});
	}
	$('#dict_operation_modal').modal('hide');
}

//检查字典表
var TableInit_dict_exam =function () {
	var oTableInit=new Object();
	var address=addurl+"/dict/exam";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#dict_exam_modal #dict_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#dict_exam_modal #dict_table').bootstrapTable({
			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			pagelabel: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#dict_exam_modal #toolbar',                // 工具按钮用哪个容器
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
//			uniqueId: "id",                  	// 每一行的唯一标识，一般为主键列
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
			columns : [{field:'select',radio : true,width:30
			},
			{field:'hisname',title:'机构',width:100,align:'center',
	        },  
	        {field:'examcode',title:'检查编号',width:100,align:'center',
	        },    
	        {field:'examname',title:'检查名称',width:100,align:'center',
	        }],
			
		});
	}
	
	return oTableInit;
};

function dict_exam_open(type){
	if(type==1){
		$("#dict_exam_data_modal #hiscode").attr("readonly",false);
		$("#dict_exam_data_modal #examcode").attr("readonly",false);
		
		$('#dict_exam_data_modal #btn_add').show();
		$('#dict_exam_data_modal #btn_update').hide();
	}
	if(type==2){
		var IdSelections = $('#dict_exam_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		$('#dict_exam_data_modal #hiscode').val(IdSelections[0].hiscode)
		$('#dict_exam_data_modal #examcode').val(IdSelections[0].examcode)
		$('#dict_exam_data_modal #examname').val(IdSelections[0].examname)
		
		$("#dict_exam_data_modal #hiscode").attr("readonly",true);
		$("#dict_exam_data_modal #examcode").attr("readonly",true);
		
		$('#dict_exam_data_modal #btn_add').hide();
		$('#dict_exam_data_modal #btn_update').show();
	}
	
	$('#dict_exam_data_modal').modal('show');
}

function dict_exam_data(type){
	var queryurl=addurl+"/dict/exam";
	var inserturl=addurl+'/dict/insertexam';
	var updateurl=addurl+'/dict/updateexam';
	var deleteurl=addurl+'/dict/deleteexam';
	
	
	if(type==1){
		$.ajax({
			type:"POST",
			url:inserturl,
			async:false,
			cache:true,
			data:$('#dict_exam_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_exam_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_exam_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==2){
		$.ajax({
			type:"POST",
			url:updateurl,
			async:false,
			cache:true,
			data:$('#dict_exam_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_exam_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_exam_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==3){
		var IdSelections = $('#dict_exam_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		$.ajax({
			type:"POST",
			url:deleteurl,
			async:false,
			cache:true,
			data:{hiscode:IdSelections[0].hiscode,examcode:IdSelections[0].examcode,
				examname:IdSelections[0].examname},
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_exam_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_exam_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
}

function dict_exam_modal_open(pagelabel,rowIndex){
	if(pagelabel=='mm'){
		$("#dict_exam_modal #pagelabel").val(pagelabel);
		$("#dict_exam_modal #rowid").val(rowIndex);
	}
	
	//区分处理时字典数据维护还是选取字典数据
	if(rowIndex<0 || rowIndex==undefined){
		$("#dict_exam_modal #queren").hide();
	}else{
		$("#dict_exam_modal #queren").show();
	}
	
	$("#dict_exam_modal #dict_table").bootstrapTable('refresh',{silent:true});
	$('#dict_exam_modal').modal('show');
}
function dict_exam_modal_yes(){
	var IdSelections=$("#dict_exam_modal #dict_table").bootstrapTable('getSelections');
	if(IdSelections.length!=1){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	
	var pagelabel = $("#dict_exam_modal #pagelabel").val();
	if(pagelabel=='mm'){
		$("#fujiaexaminfo #json_table").bootstrapTable('updateRow',{
			index:$("#dict_exam_modal #rowid").val(),
			row:{
				labexamcode:IdSelections[0].examcode,
				labexamname:IdSelections[0].examname,
			}
		});
	}
	
	$('#dict_exam_modal').modal('hide');
}

//检验字典表
var TableInit_dict_lab =function () {
	var oTableInit=new Object();
	var address=addurl+"/dict/lab";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#dict_lab_modal #dict_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#dict_lab_modal #dict_table').bootstrapTable({
			url: address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			pagelabel: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#dict_lab_modal #toolbar',                // 工具按钮用哪个容器
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
			clickToSelect: true,               // 是否启用点击选中行
//			height: 400,                        //1.450, 行高
												//2.如果没有设置height属性，表格自动根据记录条数觉得表格高度
												//3.在tab标签样式里面会导致高度出错，自己CSS写固定高度
//			uniqueId: "id",                  	// 每一行的唯一标识，一般为主键列
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
			columns : [{field:'select',radio : true,width:30
			},
			{field:'hisname',title:'机构',width:100,align:'center',
	        },  
	        {field:'labcode',title:'检验编号',width:100,align:'center',
	        },    
	        {field:'labname',title:'检验名称',width:100,align:'center',
	        }],
			
		});
	}
	
	return oTableInit;
};

function dict_lab_open(type){
	if(type==1){
		$("#dict_lab_data_modal #hiscode").attr("readonly",false);
		$("#dict_lab_data_modal #labcode").attr("readonly",false);
		
		$('#dict_lab_data_modal #btn_add').show();
		$('#dict_lab_data_modal #btn_update').hide();
	}
	if(type==2){
		var IdSelections = $('#dict_lab_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		$('#dict_lab_data_modal #hiscode').val(IdSelections[0].hiscode)
		$('#dict_lab_data_modal #labcode').val(IdSelections[0].labcode)
		$('#dict_lab_data_modal #labname').val(IdSelections[0].labname)
		
		$("#dict_lab_data_modal #hiscode").attr("readonly",true);
		$("#dict_lab_data_modal #labcode").attr("readonly",true);
		
		$('#dict_lab_data_modal #btn_add').hide();
		$('#dict_lab_data_modal #btn_update').show();
	}
	
	$('#dict_lab_data_modal').modal('show');
}

function dict_lab_data(type){
	var queryurl=addurl+"/dict/lab";
	var inserturl=addurl+'/dict/insertlab';
	var updateurl=addurl+'/dict/updatelab';
	var deleteurl=addurl+'/dict/deletelab';
	
	
	if(type==1){
		$.ajax({
			type:"POST",
			url:inserturl,
			async:false,
			cache:true,
			data:$('#dict_lab_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_lab_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_lab_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==2){
		$.ajax({
			type:"POST",
			url:updateurl,
			async:false,
			cache:true,
			data:$('#dict_lab_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_lab_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_lab_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
	if(type==3){
		var IdSelections = $('#dict_lab_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		$.ajax({
			type:"POST",
			url:deleteurl,
			async:false,
			cache:true,
			data:{hiscode:IdSelections[0].hiscode,labcode:IdSelections[0].labcode,
				labname:IdSelections[0].labname},
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#dict_lab_modal #dict_table').bootstrapTable('refresh', {url: queryurl});
					$('#dict_lab_data_modal').modal('hide');
				}else{
					swal("", result,"error");
				}
			},
			error:function(XMLResponse){
				alert(XMLResponse.responseText)
			}
		});
	}
}

function dict_lab_modal_open(pagelabel,rowIndex){
	if(pagelabel=='nn'){
		$("#dict_lab_modal #pagelabel").val(pagelabel);
		$("#dict_lab_modal #rowid").val(rowIndex);
	}
	
	//区分处理时字典数据维护还是选取字典数据
	if(rowIndex<0 || rowIndex==undefined){
		$("#dict_lab_modal #queren").hide();
	}else{
		$("#dict_lab_modal #queren").show();
	}
	
	$("#dict_lab_modal #dict_table").bootstrapTable('refresh',{silent:true});
	$('#dict_lab_modal').modal('show');
}
function dict_lab_modal_yes(){
	var IdSelections=$("#dict_lab_modal #dict_table").bootstrapTable('getSelections');
	if(IdSelections.length!=1){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	
	var pagelabel = $("#dict_lab_modal #pagelabel").val();
	if(pagelabel == 'nn'){
		$("#fujialabinfo #json_table").bootstrapTable('updateRow',{
			index:$("#dict_lab_modal #rowid").val(),
			row:{
				labexamcode:IdSelections[0].labcode,
				labexamname:IdSelections[0].labname,
			}
		});
	}
	$('#dict_lab_modal').modal('hide');
}


//===========================================JSON操作：分解===================================
//分解JSON
function json_fenjie(json){
	if(json==null){
		PassClient= {};
		Patient= {};
		ScreenAllergenList= {};
		ScreenMedCondList= {};
		ScreenOperationList= {};
		ScreenDrugList= {};
		InputJsonInfoList= {}; 
		
		return;
	}
	
	PassClient= json.PassClient;
	Patient= json.Patient;
	ScreenAllergenList= json.ScreenAllergenList;
	ScreenMedCondList= json.ScreenMedCondList;
	ScreenOperationList= json.ScreenOperationList;
	ScreenDrugList= json.ScreenDrugList;
	InputJsonInfoList= json.InputJsonInfoList; 
	
	//机构编码复制
	$("#HospID").val(PassClient.HospID);
	
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
			var aaa = new Array();
			aaa.push(InputJsonInfo);
//			$('#fujiadrug #json_table').bootstrapTable('load',aaa); 
			$('#fujiadrug #json_table').bootstrapTable('append',aaa); 
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
			$('#fujiadisease #json_table').bootstrapTable('append',aaa); 
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
			$('#fujiaotherrecip #json_table').bootstrapTable('append',aaa); 
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
			$('#fujiaexaminfo #json_table').bootstrapTable('append',aaa); 
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
			$('#fujialabinfo #json_table').bootstrapTable('append',aaa);
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

//===========================================JSON操作：合并===================================
//合并JSON
function json_hebing(){
	var json = {};
	
	//保存客户端信息
	PassClient.HospID=$('#HospID').val();
	json['PassClient']=PassClient;
	
	//保存病人信息
	var Patient={};
	Patient['PatCode']=$('#patientmessage #PatCode').val();
	Patient['InHospNo']=$('#patientmessage #InHospNo').val();
	Patient['VisitCode']=$('#patientmessage #VisitCode').val();
	Patient['Name']=$('#patientmessage #Name').val();
	Patient['Sex']=$('#patientmessage #Sex').val();
	Patient['Birthday']=$('#patientmessage #Birthday input').val();
	Patient['HeightCM']=$('#patientmessage #HeightCM').val();
	Patient['WeighKG']=$('#patientmessage #WeighKG').val();
	Patient['DeptCode']=$('#patientmessage #DeptCode').val();
	Patient['DeptName']=$('#patientmessage #DeptName').val();
	Patient['DoctorCode']=$('#patientmessage #DoctorCode').val();
	Patient['DoctorName']=$('#patientmessage #DoctorName').val();
	Patient['PatStatus']=parseInt($('#patientmessage #PatStatus').val());
	Patient['IsLactation']=parseInt($('#patientmessage #IsLactation').val());
	Patient['IsPregnancy']=parseInt($('#patientmessage #IsPregnancy').val());
	Patient['PregStartDate']=$('#patientmessage #PregStartDate input').val();
	Patient['HepDamageDegree']=parseInt($('#patientmessage #HepDamageDegree').val());
	Patient['RenDamageDegree']=parseInt($('#patientmessage #RenDamageDegree').val());
	Patient['UseTime']=$('#patientmessage #UseTime input').val();
	Patient['CheckMode']=$('#patientmessage #CheckMode').val();
	Patient['IsDoSave']=parseInt($('#patientmessage #IsDoSave').val());
	Patient['Age']=$('#patientmessage #Age').val();
	Patient['PayClass']=$('#patientmessage #PayClass').val();
	Patient['IsTestEtiology']=parseInt($('#patientmessage #IsTestEtiology').val());
	Patient['InHospDate']=$('#patientmessage #InHospDate input').val();
	Patient['OutHospDate']=$('#patientmessage #OutHospDate input').val();
	Patient['IDCard']=$('#patientmessage #IDCard').val();
	Patient['Telephone']=$('#patientmessage #Telephone').val();
	json['Patient']=Patient;
	
	//保存过敏原信息
	var ScreenAllergenList={};
	ScreenAllergenList["ScreenAllergens"] = $('#allergenmessage #json_table').bootstrapTable('getData');
	json["ScreenAllergenList"]=ScreenAllergenList;
	
	//保存疾病信息
	var ScreenMedCondList={};
	var disrows=$('#diseasemessage #json_table').bootstrapTable('getData');
	var ScreenMedConds=new Array();
	for(var i=0;i<disrows.length;i++){
		var disrow=disrows[i];
		disrow["DisTimeType"]=parseInt(disrow.DisTimeType);
		disrow["Ishospinfection"]=parseInt(disrow.Ishospinfection);
		ScreenMedConds[i]=disrow;
	}
	ScreenMedCondList["ScreenMedConds"] =ScreenMedConds;
	json["ScreenMedCondList"]=ScreenMedCondList;
	
	//保存手术信息
	var ScreenOperationList={};
	var oprrows=$('#operationmessage #json_table').bootstrapTable('getData');
	var ScreenOperations=new Array();
	for(var i=0;i<oprrows.length;i++){
		var oprrow=oprrows[i];
		oprrow["OprMediTime"]=parseInt(oprrow.OprMediTime);
		oprrow["OprTreatTime"]=parseInt(oprrow.OprTreatTime);
		ScreenOperations[i]=oprrow;
	}
	ScreenOperationList["ScreenOperations"] = ScreenOperations;
	json["ScreenOperationList"]=ScreenOperationList;
	
	//保存医嘱信息
	var ScreenDrugList={};
	var odrrows=$('#drugsmessage #json_table').bootstrapTable('getData');
	var ScreenDrugs=new Array();
	for(var i=0;i<odrrows.length;i++){
		var odrrow=odrrows[i];
		//字符串转整型
		odrrow['OrderNo']=parseInt(odrrow.OrderNo);
		odrrow['IsTempDrug']=parseInt(odrrow.IsTempDrug);
		odrrow['OrderType']=parseInt(odrrow.OrderType);
		odrrow['Purpose']=parseInt(odrrow.Purpose);
		if(odrrow.CompName==undefined){
			odrrow['CompName']='';
		}
		if(odrrow.FreqSource==undefined){
			odrrow['FreqSource']='USER';
		}
		if(odrrow.MediTime==undefined){
			odrrow['MediTime']='';
		}
		if(odrrow.RouteSource==undefined){
			odrrow['RouteSource']='USER';
		}
		
		ScreenDrugs[i]=odrrow;
	}
	ScreenDrugList['ScreenDrugs'] = ScreenDrugs;
	json['ScreenDrugList']=ScreenDrugList;
	
	//保存附加信息
//	json["InputJsonInfoList"]=InputJsonInfoList;
	var InputJsonInfoList={};
	var InputJsonInfos=new Array();
	
	var jsontype={};
	jsontype["type"]="jsontype";
	jsontype["screentype"]=$('#fujiamessage #jsontype').val();
	InputJsonInfos.push(jsontype);
	
	var prtasktype={};
	prtasktype["type"]="prtasktype";
	prtasktype["urgent"]=$('#fujiatask #prtasktype').val();
	InputJsonInfos.push(prtasktype);
	
	var fujiadrug=$('#fujiadrug #json_table').bootstrapTable('getData');
	for(var i=0;i<fujiadrug.length;i++){
		if(fujiadrug[i] != undefined){
			InputJsonInfos.push(fujiadrug[i]);
		}
	}
	
	var fujiadisease=$('#fujiadisease #json_table').bootstrapTable('getData');
	for(var i=0;i<fujiadisease.length;i++){
		if(fujiadisease[i] != undefined){
			InputJsonInfos.push(fujiadisease[i]);
		}
	}
	
	var fujiaotherrecip=$('#fujiaotherrecip #json_table').bootstrapTable('getData');
	for(var i=0;i<fujiaotherrecip.length;i++){
		if(fujiaotherrecip[i] != undefined){
			InputJsonInfos.push(fujiaotherrecip[i]);
		}
	}
	
	var fujiaexaminfo=$('#fujiaexaminfo #json_table').bootstrapTable('getData');
	for(var i=0;i<fujiaexaminfo.length;i++){
		if(fujiaexaminfo[i] != undefined){
			InputJsonInfos.push(fujiaexaminfo[i]);
		}
	}
		
	var fujialabinfo=$('#fujialabinfo #json_table').bootstrapTable('getData');
	for(var i=0;i<fujialabinfo.length;i++){
		if(fujialabinfo[i] != undefined){
			InputJsonInfos.push(fujialabinfo[i]);
		}
	}
	
	InputJsonInfoList["InputJsonInfos"]=InputJsonInfos;
	json["InputJsonInfoList"]=InputJsonInfoList;
	
//	console.log(JSON.stringify(json))
	
	var updateaddress=addurl+'/prescription/update';
	
	$.ajax({
		type:"POST",
		url:updateaddress,
		async:false,
		cache:true,
		data:{
			pre_id: $('#pre_id').val(),
//			patientname:$('#patientname').val(),
			prescription_json:JSON.stringify(json),
			prescriptiontype:$('#prescriptiontype').val(),
		},
		success: function(result){
//			swal({
//	            title: "提示",
//	            text: result
//	        });
			swal({ 
				  title: "提示", 
				  text: result, 
//				  confirmButtonColor: "#DD6B55",
				  confirmButtonText: "OK", 
				  closeOnConfirm: false, 
				},
				function(isConfirm){ 
					location.reload();
				});
//			location.reload();
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
}

//==========================================页面列表新增、删除功能=============================
//药品信息操作
function drug_append(){
	var str = $('#drugsmessage .fixed-table-pagination .pagination-info').text();
	var sum=str.split('总共 ');
	sum=sum[1].split(' 条');
	parseInt(sum[0]);
	$('#drugsmessage #json_table').bootstrapTable('append',{
		  RecipNo: '',
		  Index: parseInt(sum[0])+1,
		  OrderNo:0,
		  DrugSource: '',
		  DrugUniqueCode: '',
		  DrugCode: '',
		  DrugName: '',
		  DoseUnit: '',
		  Form: '',
		  Strength: '',
		  CompName: '',
		  RouteSource: '',
		  RouteCode: '',
		  RouteName: '',
		  FreqSource: '',
		  Frequency: '',
		  DosePerTime: '',
		  StartTime: '',
		  EndTime: '',
		  ExecuteTime: '',
		  DeptCode: '',
		  DeptName: '',
		  DoctorCode: '',
		  DoctorName: '',
		  GroupTag: '',
		  IsTempDrug:0 ,
		  OrderType:0,
		  Pharmacists: '',
		  Pharmacists_: '',
		  Num: '',
		  NumUnit: '',
		  Cost: '',
		  Purpose: 0,
		  OprCode: '',
		  MediTime: '',
		  Remark: ''
	})
}
function drug_del(){
	var IdSelections=$("#drugsmessage #json_table").bootstrapTable('getSelections');
	if(IdSelections.length==0){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	swal({
        title: "提示",
        text: "请确认是否进行删除数据操作",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	$('#drugsmessage #json_table').bootstrapTable('removeSelectionId_CH');
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

//过敏原信息操作
function allergen_append(){
	var str = $('#allergenmessage .fixed-table-pagination .pagination-info').text();
	var sum=str.split('总共 ');
	sum=sum[1].split(' 条');
	parseInt(sum[0]);
	$('#allergenmessage #json_table').bootstrapTable('append',{
		Index: parseInt(sum[0])+1,
		AllerSource: '',
		AllerCode: '',
		AllerName:'',
		AllerSymptom:''
	})
}
function allergen_del(){
	var IdSelections=$("#allergenmessage #json_table").bootstrapTable('getSelections');
	if(IdSelections.length==0){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	swal({
        title: "提示",
        text: "请确认是否进行删除数据操作",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	$('#allergenmessage #json_table').bootstrapTable('removeSelectionId_CH');
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

//疾病信息操作
function disease_append(){
	var str = $('#diseasemessage .fixed-table-pagination .pagination-info').text();
	var sum=str.split('总共 ');
	sum=sum[1].split(' 条');
	parseInt(sum[0]);
	$('#diseasemessage #json_table').bootstrapTable('append',{
		RecipNo: '',
		Index: parseInt(sum[0])+1,
		DisSource: '',
		DiseaseCode:'',
		DiseaseName:'',
		DisTimeType:0,
		Ishospinfection:0
	})
}
function disease_del(){
	var IdSelections=$("#diseasemessage #json_table").bootstrapTable('getSelections');
	if(IdSelections.length==0){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	swal({
        title: "提示",
        text: "请确认是否进行删除数据操作",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	$('#diseasemessage #json_table').bootstrapTable('removeSelectionId_CH');
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

//手术信息操作
function operation_append(){
	var str = $('#operationmessage .fixed-table-pagination .pagination-info').text();
	var sum=str.split('总共 ');
	sum=sum[1].split(' 条');
	parseInt(sum[0]);
	$('#operationmessage #json_table').bootstrapTable('append',{
		Index: parseInt(sum[0])+1,
		OprCode: '',
		OprName: '',
		IncisionType:'',
		OprStartDate:'',
		OprEndDate: '',
		OprMediTime:0,
		OprTreatTime:0
	})
}
function operation_del(){
	var IdSelections=$("#operationmessage #json_table").bootstrapTable('getSelections');
	if(IdSelections.length==0){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	swal({
        title: "提示",
        text: "请确认是否进行删除数据操作",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	$('#operationmessage #json_table').bootstrapTable('removeSelectionId_CH');
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

//附加-补充药品信息操作
function fujia_drug_append(){
	var str = $('#fujiadrug .fixed-table-pagination .pagination-info').text();
	var sum=str.split('总共 ');
	sum=sum[1].split(' 条');
	parseInt(sum[0]);
	$('#fujiadrug #json_table').bootstrapTable('append',{
		type:"druginfo",
		index: parseInt(sum[0])+1,
		driprate: '',
		driptime: '',
		duration:''
	})
}
function fujia_drug_del(){
	var IdSelections=$("#fujiadrug #json_table").bootstrapTable('getSelections');
	if(IdSelections.length==0){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	swal({
        title: "提示",
        text: "请确认是否进行删除数据操作",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	$('#fujiadrug #json_table').bootstrapTable('removeSelectionId_CH');
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

//附加-补充疾病信息操作
function fujia_disease_append(){
	var str = $('#fujiadisease .fixed-table-pagination .pagination-info').text();
	var sum=str.split('总共 ');
	sum=sum[1].split(' 条');
	parseInt(sum[0]);
	$('#fujiadisease #json_table').bootstrapTable('append',{
		type:"diseaseinfo",
		index: parseInt(sum[0])+1,
		starttime: '',
		endtime: ''
	})
}
function fujia_disease_del(){
	var IdSelections=$("#fujiadisease #json_table").bootstrapTable('getSelections');
	if(IdSelections.length==0){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	swal({
        title: "提示",
        text: "请确认是否进行删除数据操作",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	$('#fujiadisease #json_table').bootstrapTable('removeSelectionId_CH');
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

//附加-补充历史医嘱信息操作
function fujia_otherrecip_append(){
	var str = $('#fujiaotherrecip .fixed-table-pagination .pagination-info').text();
	var sum=str.split('总共 ');
	sum=sum[1].split(' 条');
	parseInt(sum[0]);
	$('#fujiaotherrecip #json_table').bootstrapTable('append',{
		type:"otherrecipinfo",
		hiscode: '',
		index: '',
		recipno: '',
		drugsource: '',
		druguniquecode:'',
		drugname: '',
		doseunit: '',
		routesource: '',
		routecode: '',
		routename: ''
	})
}
function fujia_otherrecip_del(){
	var IdSelections=$("#fujiaotherrecip #json_table").bootstrapTable('getSelections');
	if(IdSelections.length==0){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	swal({
        title: "提示",
        text: "请确认是否进行删除数据操作",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	$('#fujiaotherrecip #json_table').bootstrapTable('removeSelectionId_CH');
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

//附加-补充检查信息操作
function fujia_examinfo_append(){
	var str = $('#fujiaexaminfo .fixed-table-pagination .pagination-info').text();
	var sum=str.split('总共 ');
	sum=sum[1].split(' 条');
	parseInt(sum[0]);
	$('#fujiaexaminfo #json_table').bootstrapTable('append',{
		type:"examinfo",
		requestno:'',
		labexamcode: '',
		labexamname:'',
		startdatetime: '',
		deptcode: '',
		deptname:'',
		doctorcode:'',
		doctorname: ''
	})
}
function fujia_examinfo_del(){
	var IdSelections=$("#fujiaexaminfo #json_table").bootstrapTable('getSelections');
	if(IdSelections.length==0){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	swal({
        title: "提示",
        text: "请确认是否进行删除数据操作",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	$('#fujiaexaminfo #json_table').bootstrapTable('removeSelectionId_CH');
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

//附加-补充检验信息操作
function fujia_labinfo_append(){
	var str = $('#fujialabinfo .fixed-table-pagination .pagination-info').text();
	var sum=str.split('总共 ');
	sum=sum[1].split(' 条');
	parseInt(sum[0]);
	$('#fujialabinfo #json_table').bootstrapTable('append',{
		type:"labinfo",
		requestno: '',
		labexamcode: '',
		labexamname: '',
		startdatetime: '',
		ch_reporttime: '',
		deptcode: '',
		deptname: '',
		doctorcode: '',
		doctorname: '',
		ch_resultflag:'',
		ch_labeltypedesc:'',
		ch_labresult:'',
		ch_range:'',
		ch_unit:''
	})
}
function fujia_labinfo_del(){
	var IdSelections=$("#fujialabinfo #json_table").bootstrapTable('getSelections');
	if(IdSelections.length==0){
		swal({
            title: "提示",
            text: "请选择一条数据进行操作"
        });
		return
	}
	swal({
        title: "提示",
        text: "请确认是否进行删除数据操作",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	$('#fujialabinfo #json_table').bootstrapTable('removeSelectionId_CH');
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

//========================================初始化下拉单数据==========================
function _select2(){
	var addresshospital = addurl + '/dict/select2';
	var hospital={};
	
	$.ajax({
		type:"POST",
		url:addresshospital,
		async:false,
		cache:true,
		data:{},
		success: function(result){
			hospital=result[0].hospital;
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
	
	$('#dict_drug_data_modal #hiscode1').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#dict_drug_data_modal"),//modal默认不显示，解决modal显示后下拉单样式问题
		data :hospital
//		ajax: {
//			url: addresshospital,
//			processResults: function (data) {
//			// Tranforms the top-level key of the response object from 'items' to 'results'
//				return {
//					results: data[0].hospital
//				};
//			}
//		}
	});
	
	$('#dict_route_data_modal #hiscode2').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#dict_route_data_modal"),//modal默认不显示，解决modal显示后下拉单样式问题
		data :hospital
	});
	
	$('#dict_fre_data_modal #hiscode3').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#dict_fre_data_modal"),//modal默认不显示，解决modal显示后下拉单样式问题
		data :hospital
	});
	
	$('#dict_dept_data_modal #hiscode4').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#dict_dept_data_modal"),//modal默认不显示，解决modal显示后下拉单样式问题
		data :hospital
	});
	
	$('#dict_doctor_data_modal #hiscode5').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#dict_doctor_data_modal"),//modal默认不显示，解决modal显示后下拉单样式问题
		data :hospital
	});
	
	$('#dict_allergen_data_modal #hiscode6').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#dict_allergen_data_modal"),//modal默认不显示，解决modal显示后下拉单样式问题
		data :hospital
	});
	
	$('#dict_disease_data_modal #hiscode7').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#dict_disease_data_modal"),//modal默认不显示，解决modal显示后下拉单样式问题
		data :hospital
	});
	
	$('#dict_operation_data_modal #hiscode8').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#dict_operation_data_modal"),//modal默认不显示，解决modal显示后下拉单样式问题
		data :hospital
	});
	
	$('#dict_exam_data_modal #hiscode9').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#dict_exam_data_modal"),//modal默认不显示，解决modal显示后下拉单样式问题
		data :hospital
	});
	
	$('#dict_lab_data_modal #hiscode10').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#dict_lab_data_modal"),//modal默认不显示，解决modal显示后下拉单样式问题
		data :hospital
	});
}