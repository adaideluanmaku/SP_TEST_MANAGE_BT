var addurl=null;
$(document).ready(function() {
	addurl=$("#addurl").val();
	
	//声明一个表格对象，初始化表格
	var oTable=new TableInit_database();
	oTable.Init();
	
});

//数据库配置表格
var TableInit_database =function () {
	var oTableInit=new Object();
	
	//初始化表格
	oTableInit.Init = function(){
		$("#table_div #dict_table").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#table_div #dict_table').bootstrapTable({
			url: addurl+"/dict/database",         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			pagelabel: "json",					//数据类型
//			data:[{dbColName1:"aa"}],			//JSON数据
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#table_div #toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "server",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10,25,50,100],        				// 可供选择的每页的行数（*）
			search: false,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: false,                  // 是否显示所有的列按钮
			showRefresh: false,                  // 是否显示刷新按钮
			showToggle:false,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: true,               // 是否启用点击选中行
			height:tableheight(),                        //1.450, 行高
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
						serachdata:$('#table_div #search_data').val(),
			    };
			    return temp;
			},
			// 是否显示父子表
			columns : [{field:'select',checkbox : true,width:30
			},
			{field:'name',title:'name',width:120,align:'center',
	        },
	        {field:'kettle_Database_two',title:'kettle_Database_two',width:120,align:'center',
	        },
			{field:'databasetype',title:'databasetype',width:120,align:'center',
	        },    
	        {field:'databasename',title:'databasename',width:120,align:'center',
	        },
	        {field:'ip',title:'ip',width:100,align:'center',
	        },
	        {field:'username',title:'username',width:100,align:'center',
	        },    
	        {field:'password',title:'password',width:100,align:'center',
	        }
	        ],
	        // 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
				$('#dict_table').bootstrapTable('uncheckAll');//取消选中所有行
			},
		});
	}
	
	return oTableInit;
};

//查询功能调用该方法
function table_search(){
	$('#dict_table').bootstrapTable('refresh', {url: addurl+"/dict/database"});
}

function database_open(type){
//	$("#database_data_modal #kettle_Database_two").attr("readonly",true);
	
	if(type==1){
		//清空form表单
		document.getElementById("dialog_form").reset();
		$("#database_data_modal #name").attr("readonly",false);
		
		$('#database_data_modal #btn_add').show();
		$('#database_data_modal #btn_update').hide();
	}
	if(type==2){
		var IdSelections = $('#table_div #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		
		$('#database_data_modal #name').val(IdSelections[0].name)
		$('#database_data_modal #kettle_Database_two').val(IdSelections[0].kettle_Database_two)
		$('#database_data_modal #databaseid').val(IdSelections[0].databaseid)
		$('#database_data_modal #databasetype').val(IdSelections[0].databasetype)
		$('#database_data_modal #databasename').val(IdSelections[0].databasename)
		$('#database_data_modal #ip').val(IdSelections[0].ip)
		$('#database_data_modal #username').val(IdSelections[0].username)
		$('#database_data_modal #password').val(IdSelections[0].password)
		
		$("#database_data_modal #name").attr("readonly",true);
		
		$('#database_data_modal #btn_add').hide();
		$('#database_data_modal #btn_update').show();
	}
	
	$('#database_data_modal').modal('show');
}

function database_data(type){
	if(type==1){
		$.ajax({
			type:"POST",
			url:addurl+'/dict/insertdatabase',
			async:false,
			cache:true,
			data:$('#database_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#table_div #dict_table').bootstrapTable('refresh', {url: addurl+"/dict/database"});
					$('#database_data_modal').modal('hide');
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
			url:addurl+'/dict/updatedatabase',
			async:false,
			cache:true,
			data:$('#database_data_modal #dialog_form').serialize(),
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#table_div #dict_table').bootstrapTable('refresh', {url: addurl+"/dict/database"});
					$('#database_data_modal').modal('hide');
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
		var IdSelections = $('#database_modal #dict_table').bootstrapTable('getSelections');
		if(IdSelections.length != 1){
			swal({
	            title: "警告",
	            text: "请选择一条数据进行操作."
	        });
			return;
		}
		
		$.ajax({
			type:"POST",
			url:addurl+'/dict/deletedatabase',
			async:false,
			cache:true,
			data:{databaseid:IdSelections[0].databaseid},
			success: function(result){
				if(result=='ok'){
					swal("" ,result,"success");
//					//清空表单
//					document.getElementById("prescription_dialog_form").reset();
					$('#table_div #dict_table').bootstrapTable('refresh', {url: addurl+"/dict/database"});
					$('#database_data_modal').modal('hide');
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

function tableheight(){
	var _height=0;
	//获取父级窗口高度
	_height=$(window.parent.window).height()-60-40-120-100;
	if(_height<300){
		_height=300;
	}
	return _height;
}