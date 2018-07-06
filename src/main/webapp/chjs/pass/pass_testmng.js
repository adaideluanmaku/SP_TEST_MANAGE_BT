var addurl=null;
var TableInit_serverip=null;

$(document).ready(function() {
	addurl=$("#addurl").val();
	
	//初始化整个页面所有下拉单
	_select2();
	
	//初始化所有表格open_dialog
	var oTable=new TableInit();
	oTable.Init();
	
	TableInit_serverip = new TableInit_serverip();
	TableInit_serverip.Init();
	
	//初始化所有表单校验规则
	all_form_validator();
	
	//鼠标浮动提示窗口-触发事件
	$(document).on('mouseover mouseout','table td',function(){ 
		if(event.type == 'mouseover'){//鼠标悬浮
			if($(this).text().length>5){//浮动窗口
				$('body').append('<div id="title" style="max-width:400px;max-height:200px;position: absolute;'
						+'top:0px;left:0px;background-color: #fff2e8;/*自动换行*/	word-wrap: break-word;' 
						+'overflow: hidden;text-overflow: ellipsis;'
						+'border: 1px solid #c0c0c0; z-index:9999; "></div>');
				
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

//创建一个表格对象
var TableInit =function () {
	var oTableInit=new Object();
	var address=addurl+"/pass/testmng_query";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#table_div #table_data").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#table_div #table_data').bootstrapTable({
			url:address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: true,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
//			sortName : 'testno',				//默认排序的字段名
			sidePagination: "server",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10, 25, 50, 100],        // 可供选择的每页的行数（*）
//			search: true,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
//			strictSearch: true,				
//			showColumns: true,                  // 是否显示所有的列按钮
//			showRefresh: true,                  // 是否显示刷新按钮
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: true,               // 是否启用点击选中行
			height: tableheight(),                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			showToggle:true,                    // 是否显示详细视图和列表视图的切换按钮
//			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			
			showExport: true,  			// 是否显示导出按钮
			exportDataType: "selected",		// 'basic', 'all', 'selected'.
		    exportTypes:['excel','xml'],  	// 导出文件类型
		    exportOptions:{  
				ignoreColumn: [0],  	// 忽略某一列
				fileName: '测试案例',  	// 文件名称设置
				worksheetName: 'sheet1',  // 表格工作区名称
//				tableName: '测试导出文档',  
		    },
		    
			// 传递参数（*）,组织表格参数和页面查询参数
			queryParams : function (params) {
			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
					limit: params.limit,   // 页面数量大小
					offset: params.offset, // 当前页码
					sort: params.sort,  //排序列名  
				    order: params.order,//排位命令（desc，asc） 
//					search: params.search, // 工具栏查询内容,search:true才有
					
					searchdata: $("#searchdata").val(),
					projectid: $("#table_div #projectid_search").select2('val'),
					anlitype:$("#table_div #anlitype").select2('val'),
					moduleid:$("#table_div #moduleid").select2('val'),
			    };
			    return temp;
			},
			// 是否显示父子表
			columns : [ {
				field:'select',
				checkbox : true
			},{
				field:'projectname',title:'项目名称',//width:100,
			},{
				field:'testresult',title:'测试',sortable : true//dth:100,
			},{
				field:'modulename',title:'模块名称',//width:100,
			},{
				field:'testno',title:'案例编号',sortable : true//width:80,
			},{
				field:'testname',title:'案例名称',//width:150,
			},{
				field:'testtext',title:'逻辑描述',halign:'center',
				formatter: function (value, row, index) {
					if(value!=null){
						if (value.length>30) {
	                    	return '<div style="width:200px;">'+value+'</div>';
	                    }else{
	                    	return value;
	                    }
					}else{
						return value;
					}
                }
			},{
				field:'testin',title:'输入条件',halign:'center',
				formatter: function (value, row, index) {
					if(value!=null){
						if (value.length>30) {
	                    	return '<div style="width:200px;">'+value+'</div>';
	                    }else{
	                    	return value;
	                    }
					}else{
						return value;
					}
                }
			},{
				field:'testout',title:'预期结果',halign:'center',
				formatter: function (value, row, index) {
					if(value!=null){
						if (value.length>30) {
	                    	return '<div style="width:200px;"><xmp style="margin: auto">'+value+'</xmp></div>';
	                    }else{
	                    	return value;
	                    }
					}else{
						return value;
					}
                }
			},{
				field:'remark',title:'备注',//width:100,
				formatter: function (value, row, index) {
					if(value!=null){
						if (value.length>30) {
	                    	return '<div style="width:200px;">'+value+'</div>';
	                    }else{
	                    	return value;
	                    }
					}else{
						return value;
					}
                    
                }
			},{
				field:'username',title:'用户',//width:100,
			},{
				field:'inserttime',title:'创建日期',//width:130,
			}, {
				field : '',title : '查看JSON',//width : 100,
				formatter: function(value,row,index){
                    return '<a href="'+addurl+'/pass/pass_json?testid='+row.testid+'" target="_blank">打开</a>';
				}
			},{
				field:'status',title:'状态',//width:50,
				formatter: function (value, row, index) {
                    if (value ==1) {
                        return '启用';
                    }else{
                    	 return '停用';
                    }
                }
			}, {
				field : '',
				title : '操作',
				align : 'center',
				formatter: function(value,row,index){
//					if(row.testin==undefined || row.testin==''){
//						return '--'
//					}
					
                    return '<a href="'+addurl+'/prescription/prescription_edit?prescriptiontype=3&testid='+row.testid+'" target="_blank">打开</a>';
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
			}
		});
	}
	
	return oTableInit;
};

//查询功能调用该方法
function table_search(){
	var address=addurl+"/pass/testmng_query";
	$('#table_div #table_data').bootstrapTable('refresh', {url: address});
}

function open_dialog(){
	//清空表单
	document.getElementById('dialog_form').reset();
	$('#modal_dialog_add_update #anlitypeaa').select2('val','0');
	$('#modal_dialog_add_update #projectidaa').select2('val','0');
	$('#modal_dialog_add_update #teamidaa').select2('val','0');
	$('#modal_dialog_add_update #moduleid').select2('val','0');
	$('#modal_dialog_add_update #status').select2('val','1');
	$('#modal_dialog_add_update #selenium_share_status').select2('val','0');
	
	//重置表单校验,销毁重构
	$('#modal_dialog_add_update #dialog_form').data('bootstrapValidator').destroy();
    $('#modal_dialog_add_update #dialog_form').data('bootstrapValidator', null);
    all_form_validator();
	
	$('#modal_dialog_add_update button').show();
	$('#modal_dialog_add_update #btn_update').hide();
	$('#modal_dialog_add_update').modal('show');

}

//每次点击都要后台取数据
function _select2(){
	var addressteamgroup = addurl + '/pass/teamgroup';
	$('#table_div #teamid_search').select2({
		placeholder: "--请选择--",
//		placeholder: { id: "0", text: "--请选择--" },//默认初始值
		allowClear: true,
		dropdownParent: $("#table_div"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: addressteamgroup,
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data
				};
			}
		}
	});
	//选中team下拉后，初始化project的数据
	$('#table_div #teamid_search').on("select2:select", function(e) {
		$('#table_div #projectid_search').select2('val','0');
	})
	
	var addressprojectgroup = addurl + '/pass/projectgroup';
	$('#table_div #projectid_search').select2({
		placeholder: "--请选择--",
		allowClear: true,
		ajax: {
			url: addressprojectgroup,
			contentType:"application/json",
			data: function (params) {
	             return {
	            	 teamid:$('#table_div #teamid_search').select2('val')
	             };
	        },
			processResults: function (data) {
				console.log($('#table_div #teamid_search').select2('val'))
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data
				};
			}
		}
	});
	
	var address_select2 = addurl + '/pass/_select2';
	$('#table_div #anlitype').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#table_div"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: address_select2,
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data[0].anlitypes
				};
			}
		}
	});
	
	var addressmodulenamegroup = addurl + '/pass/modulenamegroup';
	var modulenamegroupdata={}
	$.ajax({
		type:"POST",
		url:addressmodulenamegroup,
		async:false,
		success: function (result) {
			modulenamegroupdata=result
		},
	});
	$('#modal_dialog_add_update #moduleid').select2({
		placeholder: "--请选择--",
//		placeholder: { id: "0", text: "--请选择--" },//默认初始值
		allowClear: true,
		dropdownParent: $("#modal_dialog_add_update"),//modal默认不显示，解决modal显示后下拉单样式问题
		data:modulenamegroupdata
	});
	$('#table_div #moduleid').select2({
		placeholder: "--请选择--",
//		placeholder: { id: "0", text: "--请选择--" },//默认初始值
		allowClear: true,
		dropdownParent: $("#table_div"),//modal默认不显示，解决modal显示后下拉单样式问题
		data:modulenamegroupdata
	});
	
	var addressteamgroup = addurl + '/pass/teamgroup';
	$('#modal_dialog_add_update #teamidaa').select2({
		placeholder: "--请选择--",
//		placeholder: { id: "0", text: "--请选择--" },//默认初始值
		allowClear: true,
		dropdownParent: $("#modal_dialog_add_update"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: addressteamgroup,
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data
				};
			}
		}
	});
	//选中team下拉后，初始化project的数据
	$('#modal_dialog_add_update #teamidaa').on("select2:select", function(e) {
		$('#modal_dialog_add_update #projectid').select2('val','0');
	})
	
	var addressprojectgroup = addurl + '/pass/projectgroup';
	$('#modal_dialog_add_update #projectidaa').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#modal_dialog_add_update"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: addressprojectgroup,
			data: function (params) {//后台查数据
	             return {
	            	 teamid:$('#modal_dialog_add_update #teamidaa').select2('val')
	             };
	        },
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data
				};
			}
		}
	});
	
	var address_select2 = addurl + '/pass/_select2';
	$('#modal_dialog_add_update #anlitypeaa').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#modal_dialog_add_update"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: address_select2,
//			data: function (params) {//后台查数据
//	             return {
//	            	 teamid:$('#modal_dialog_add_update #anlitype').select2('val')
//	             };
//	        },
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data[0].anlitypes
				};
			}
		}
	});
	
	var addressteamgroup = addurl + '/pass/teamgroup';
	$('#modal_dialog_tools #teamid').select2({
		placeholder: "--请选择--",
//		placeholder: { id: "0", text: "--请选择--" },//默认初始值
		allowClear: true,
		dropdownParent: $("#modal_dialog_tools"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: addressteamgroup,
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data
				};
			}
		}
	});
	//选中team下拉后，初始化project的数据
	$('#modal_dialog_tools #teamid').on("select2:select", function(e) {
		$('#modal_dialog_tools #projectid').select2('val','0');
	})
	
	var addressprojectgroup = addurl + '/pass/projectgroup';
	$('#modal_dialog_tools #projectid').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#modal_dialog_tools"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: addressprojectgroup,
			data: function (params) {
	             return {
	            	 teamid:$('#modal_dialog_tools #teamid').select2('val')
	             };
	        },
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data
				};
			}
		}
	});
	
	var address_select2 = addurl + '/pass/_select2';
	$('#modal_dialog_serverip #jsonversion').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#modal_dialog_serverip"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: address_select2,
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data[0].jsonversions
				};
			}
		}
	});
	$('#modal_dialog_serverip #jsontype').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#modal_dialog_serverip"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: address_select2,
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data[0].jsontyles
				};
			}
		}
	});
	
	$('#modal_dialog_tools #_daoshuju').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#modal_dialog_tools"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: address_select2,
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data[0].daoshujus
				};
			}
		}
	});
	
	$("#modal_dialog_add_update #status").select2({dropdownParent: $("#modal_dialog_add_update"),data:[{id:0,text:'停用'},{id:1,text:'启动'}]});
	$("#modal_dialog_add_update #status").val(1).trigger('change');
	
}

function add_data(){
	if($('#modal_dialog_add_update #projectidaa').select2('val')==null){
		swal("", '请选择项目',"error");
		return
	}
	
	var queryaddress=addurl+'/pass/testmng_query';
	var addaddress=addurl+'/pass/testmng_add';
	
	 //获取表单对象
    $("#modal_dialog_add_update #dialog_form").bootstrapValidator('validate');//提交验证  
    if (!$("#modal_dialog_add_update #dialog_form").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码  
    	return;
    }  
    
	$.ajax({
		type:"POST",
		url:addaddress,
		async:false,
		cache:true,
		data:$('#modal_dialog_add_update #dialog_form').serialize(),
		success: function(result){
			if(result=='ok'){
				swal("" ,result,"success");
//				//清空表单
//				document.getElementById("prescription_dialog_form").reset();
				$('#table_div #table_data').bootstrapTable('refresh', {url: queryaddress});
				$('#modal_dialog_add_update').modal('hide');
			}else{
				swal("", result,"error");
			}
			table_search()
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		},
//		complete : function(XMLHttpRequest, textStatus) {
//			alert(XMLHttpRequest)
//			alert(textStatus)
//	        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
//	        if (sessionstatus == "timeout") {
	            // 如果超时就处理 ，指定要跳转的页面
//	            window.location.replace("login.jsp");
//	            location.href = "login.jsp";
//	        }
//	    }
	});
}

function del_data(){
	var IdSelections = $('#table_data').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请至少选择一条数据进行操作."
        });
		return;
	}
	
	swal({
        title: "Are you sure?",
        text: "You will not be able to recover this imaginary file!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	var queryaddress=addurl+'/pass/testmng_query';
    	var deladdress=addurl+'/pass/testmng_del';
    	
    	var testids=new Array();
    	for(var i=0;i<IdSelections.length;i++){
    		testids.push(IdSelections[i].testid);
    	}
    	
    	parent.onloading();
    	$.ajax({
    		type:"POST",
    		url:deladdress,
    		async:false,
    		cache:true,
    		data:{testids:testids},
    		success: function(result){
    			parent.removeload();
    			if(result=='ok'){
    				swal("" ,result,"success");
    				$('#table_div #table_data').bootstrapTable('refresh', {url: queryaddress});
    			}else{
    				swal("", result,"error");
    			}
    		},
    		error:function(XMLResponse){
    			alert(XMLResponse.responseText)
    		}
    	});
    });
}

function edit_dialog(){
	//清空表单
	document.getElementById('dialog_form').reset();
	$('#modal_dialog_add_update #projectidaa').select2('val','0');
	$('#modal_dialog_add_update #teamidaa').select2('val','0');
	
	$('#modal_dialog_add_update button').show();
	$('#modal_dialog_add_update #btn_add').hide();
	
	var IdSelections = $('#table_div #table_data').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	
	var anlitypename='';
	if(IdSelections[0].anlitype==1){
		anlitypename='自动案例'
	}else if(IdSelections[0].anlitype==2){
		anlitypename='手动案例'
	}else if(IdSelections[0].anlitype==3){
		anlitypename='说明书'
	}else if(IdSelections[0].anlitype==4){
		anlitypename='浮动窗口'
	}else if(IdSelections[0].anlitype==5){
		anlitypename='详细信息'
	}else if(IdSelections[0].anlitype==6){
		anlitypename='用药理由'
	}else if(IdSelections[0].anlitype==7){
		anlitypename='右键菜单'
	}else if(IdSelections[0].anlitype==8){
		anlitypename='模块菜单'
	}else if(IdSelections[0].anlitype==9){
		anlitypename='自由自定义'
	}else if(IdSelections[0].anlitype==10){
		anlitypename='中药材专论'
	}else if(IdSelections[0].anlitype==11){
		anlitypename='用药指导单'
	}else {
		anlitypename='未定位'
	}
	$('#modal_dialog_add_update #anlitypeaa').html('<option value="' + IdSelections[0].anlitype + '">' + anlitypename + '</option>').trigger("change");
	$('#modal_dialog_add_update #testid').val(IdSelections[0].testid);
	$('#modal_dialog_add_update #projectidaa').html('<option value="' + IdSelections[0].projectid + '">' + IdSelections[0].projectname + '</option>').trigger("change");
	$("#modal_dialog_add_update #status").val(IdSelections[0].status).trigger("change");
//	$('#modal_dialog_add_update #moduleid').html('<option value="' + IdSelections[0].moduleid + '">' + IdSelections[0].modulename + '</option>').trigger("change");
	$('#modal_dialog_add_update #moduleid').val(IdSelections[0].moduleid).trigger("change");
	$('#modal_dialog_add_update #modulename').val(IdSelections[0].modulename);
	$('#modal_dialog_add_update #testname').val(IdSelections[0].testname);
	$('#modal_dialog_add_update #testno').val(IdSelections[0].testno);
	$('#modal_dialog_add_update #testtext').val(IdSelections[0].testtext);
	$('#modal_dialog_add_update #testin').val(IdSelections[0].testin);
	$('#modal_dialog_add_update #testout').val(IdSelections[0].testout);
	$('#modal_dialog_add_update #remark').val(IdSelections[0].remark);
	$('#modal_dialog_add_update #testout_response').val(IdSelections[0].testout_response);
	
	$('#modal_dialog_add_update').modal('show');
}

function update_data(){
	var queryaddress=addurl+'/pass/testmng_query';
	var addaddress=addurl+'/pass/testmng_update';
	$.ajax({
		type:"POST",
		url:addaddress,
		async:false,
		cache:true,
		data:$('#modal_dialog_add_update #dialog_form').serialize(),
		success: function(result){
			if(result=='ok'){
				swal("" ,result,"success");
				$('#modal_dialog_add_update #table_data').bootstrapTable('refresh', {url: queryaddress});
				$('#modal_dialog_add_update').modal('hide');
			}else{
				swal("", result,"error");
			}
			table_search()
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
}

//初始化表单所有输入框的验证功能
function all_form_validator(){
	$("#dialog_form").bootstrapValidator({  
        live: 'enabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证  
        excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的  
//        submitButtons: '#prescription_dialog_form1 #btn_add',//指定提交按钮，如果验证失败则变成disabled，但我没试成功，反而加了这句话非submit按钮也会提交到action指定页面  
        message: '通用的验证失败消息',//好像从来没出现过  
        feedbackIcons: {//根据验证结果显示的各种图标  
            valid: 'glyphicon glyphicon-ok',  
            invalid: 'glyphicon glyphicon-remove',  
            validating: 'glyphicon glyphicon-refresh'  
        },  
        fields: {  
        	testmngname: {
                validators: {  
                    notEmpty: {//检测非空,radio也可用  
                        message: '文本框必须输入'  
                    },  
                    stringLength: {//检测长度  
                        min: 1,  
//                      max: 30,  
                        message: '长度必须大于1'  
                    },  
                }  
            } ,
        }  
    });  
}

function open_modal_dialog_serverip_all(){
	var projectid= $("#table_div #projectid_search").select2('val');
	var anlitype= $("#table_div #anlitype").select2('val');
	if(projectid==null || anlitype==null){
		swal({
            title: "警告",
            text: "请选择项目和案例类型"
        });
		return;
	}
	
	$('#modal_dialog_serverip').modal('show');
	$('#modal_dialog_serverip #btn_add1').show();
	$('#modal_dialog_serverip #btn_add2').hide();
	
}

function open_modal_dialog_serverip_one(){
	var IdSelections = $('#table_div #table_data').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	
	$('#modal_dialog_serverip').modal('show');
	$('#modal_dialog_serverip #btn_add2').show();
	$('#modal_dialog_serverip #btn_add1').hide();
}

function screen_all(){
	var sreenaddress=addurl+'/pass/testmng_screen_all';
	var address=addurl+"/pass/testmng_query";
	
	var IdSelections = $('#modal_dialog_serverip #table_data').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	
	var jsontype=$("#modal_dialog_serverip #jsontype").select2('val');
	var jsonversion=$("#modal_dialog_serverip #jsonversion").select2('val');
	
	if(jsontype==null || jsonversion==null){
		swal({
            title: "警告",
            text: "请选择json类型和版本"
        });
		return;
	}
	
	var projectid= $("#table_div #projectid_search").select2('val');
	var anlitype= $("#table_div #anlitype").select2('val');
	
	parent.onloading();
	$.ajax({
		type:"POST",
		url:sreenaddress,
		async:true,
//		cache:true,
		data:{
			serveraddress:IdSelections[0].serveraddress,
			searchdata: $("#table_div #searchdata").val(),
			projectid: projectid,
			anlitype: anlitype,
			jsontype:jsontype,
			jsonversion:jsonversion
		},
		success: function (result) {
			parent.removeload();
			if(result=='ok'){
				swal("" ,result,"success");
				
				$('#table_div #table_data').bootstrapTable('refresh', {url: address});
				$('#modal_dialog_serverip').modal('hide');
			}else{
				swal("", result,"error");
			}
			
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
	return false;
}

function screen_one(){
	var sreenaddress=addurl+'/pass/testmng_screen_one';
	var address=addurl+"/pass/testmng_query";

	var IdSelections = $('#modal_dialog_serverip #table_data').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	var jsontype=$("#modal_dialog_serverip #jsontype").select2('val');
	var jsonversion=$("#modal_dialog_serverip #jsonversion").select2('val');
	
	if(jsontype==null || jsonversion==null){
		swal({
            title: "警告",
            text: "请选择json类型和版本"
        });
		return;
	}
	
	var IdSelections_testid = $('#table_div #table_data').bootstrapTable('getSelections');
	parent.onloading();
	$.ajax({
		type:"POST",
		url:sreenaddress,
		async:true,
		cache:true,
		data:{
			testid: IdSelections_testid[0].testid,
			jsontype:jsontype,
			jsonversion:jsonversion,
			serveraddress:IdSelections[0].serveraddress,
		},
		success: function (result) {
			parent.removeload();
			if(result=='ok'){
				swal("" ,result,"success");
				
				$('#table_div #table_data').bootstrapTable('refresh', {url: address});
				$('#modal_dialog_serverip').modal('hide');
			}else{
				swal("", result,"error");
			}
			
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
	return false;
}

//创建一个表格对象
var TableInit_serverip =function () {
	var oTableInit=new Object();
	var address=addurl+"/sysmanage/server_query";
	
	//初始化表格
	oTableInit.Init = function(){
		$('#modal_dialog_serverip #table_data').bootstrapTable({
			url:address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
//			toolbar: '#toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
			sidePagination: "server",           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
			pageNumber:1,                       // 初始化加载第一页，默认第一页
			pageSize: 10,                       // 每页的记录行数（*）
			pageList: [10, 25, 50, 100],        // 可供选择的每页的行数（*）
			search: true,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
//			strictSearch: true,				
//			showColumns: true,                  // 是否显示所有的列按钮
//			showRefresh: true,                  // 是否显示刷新按钮
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
			height: 480,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			showToggle:true,                    // 是否显示详细视图和列表视图的切换按钮
//			cardView: false,                    // 是否显示详细视图
			detailView: false, 					//是否显示父子表
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
			queryParams : function (params) {
			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
					limit: params.limit,   // 页面数量大小
					offset: params.offset, // 当前页码
//					sort: params.sort,  //排序列名  
//				    sortOrder: params.order,//排位命令（desc，asc） 
					searchdata: params.search, // 工具栏查询内容,search:true才有
					
//					searchdata: $("#searchdata").val(),
			    };
			    return temp;
			},
			// 是否显示父子表
			columns : [ {
				checkbox : true,
			} ,{
				field : 'servername',
				title : '服务地址名称',
				width : 150,
				align : "center",
				editable: {
                    type: 'text',
                    title: 'servername',
                    validate: function (v) {
                    }
                }
			}, {
				field : 'serveraddress',
				title : '服务地址',
				editable: {
                    type: 'text',
                    title: 'serveraddress',
                    validate: function (v) {
                    }
                }
			}],
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
				if($(tr).find('input').prop("checked")){//check类型的input
					$(tr).attr('class','');//改变样式
					$(tr).find('input').prop("checked",false);//改变勾选状态
					row.ID=false;//改变勾选值
				}else{
					$(tr).attr('class','selected');
					$(tr).find('input').prop("checked",true);
					row.ID=true;
				}
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
			//编辑保存
			onEditableSave: function (field, row, oldValue, $el) {
				var updateaddress=addurl+"/sysmanage/server_update";
                $.ajax({
                    type: "post",
                    url: updateaddress,
                    data: {serverid:row.serverid,servername:row.servername,serveraddress:row.serveraddress},
                    async:false,
            		cache:true,
                    success: function (result) {
//                        if (status == "success") {
//                        	swal("" ,'数据提交成功',"success");
//                        }
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

function open_modal_dialg_tools(){
	$('#modal_dialog_tools #_daoshuju').select2('val','0');
	$('#modal_dialog_tools #projectid').select2('val','0');
	$('#modal_dialog_tools #teamid').select2('val','0');
	$('#modal_dialog_tools').modal('show');
}

function sqlserver_data(){
	var _daoshuju=$('#modal_dialog_tools #_daoshuju').select2('val');
	var projectid=$('#modal_dialog_tools #projectid').select2('val');
	if(_daoshuju==null || projectid==null || projectid==0){
		swal("" ,"请合理选择数据","error");
		return;
	}
	var address=addurl+"/pass/sqlserver_data";
	
	swal({
        title: "Are you sure?",
        text: "重新导入数据后，将覆盖原来的所有数据!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes",
        closeOnConfirm: false
    }, function () {
    	
    	swal.close();
    	
    	//loading
    	parent.onloading();
    	
    	$.ajax({
    		type:"POST",
    		url:address,
    		async:true,
//    		cache:true,
    		data:{
    			dao: _daoshuju,
    			projectid:projectid
    		},
    		success: function (result) {
    			//loading
    			parent.removeload();
    			if(result=='ok'){
    				swal("" ,result,"success");
//    				$('#modal_dialog_tools').modal('hide');
    				table_search()
    			}else{
    				swal("", result,"error");
    			}
    			
    		},
    		error:function(XMLResponse){
    			alert(XMLResponse.responseText)
    		}
    	});
    	return false;
    });
	
}

function anli_copy(){
	var IdSelections = $('#table_data').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请至少选择一条数据进行操作."
        });
		return;
	}
	
	var testid=IdSelections[0].testid;
	
	swal({
        title: "Are you sure?",
        text: "You will not be able to recover this imaginary file!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, I do!",
        closeOnConfirm: false
    }, function () {
    	$.ajax({
    		type:"POST",
    		url:addurl+"/pass/testmng_copy",
    		async:false,
    		data:{testid:testid},
    		cache:true,
    		success: function(result){
    			if(result=='ok'){
    				swal("" ,result,"success");
    				table_search();
    			}else{
    				swal("", result,"error");
    			}
    		},
    		error:function(XMLResponse){
    			alert(XMLResponse.responseText)
    		}
    	});
    });
	
}

function tableheight(){
//	alert(window.screen.availWidth)
//	alert(document.body.clientHeight)
//	alert(document.getElementById("header_path").offsetHeight)
//	alert(document.getElementById("search_div").offsetHeight)
//	alert(document.getElementById("toolbar").offsetHeight)
	
	var _height = document.body.clientHeight-document.getElementById("header_path").offsetHeight
	-document.getElementById("search_div").offsetHeight-document.getElementById("toolbar").offsetHeight;
	
	if(window.screen.availWidth/160%1==0){
		_height=_height-10;
	}else{
		_height=_height-30;
	}
	
	return _height;
}