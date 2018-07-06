var addurl=null;
$(document).ready(function() {
	addurl=$("#addurl").val();
	
	//声明一个表格对象，初始化表格
	var oTable=new TableInit();
	oTable.Init();
	
	//初始化所有表单校验规则
	all_form_validator();
	
});

//创建一个表格对象
var TableInit =function () {
	var oTableInit=new Object();
	var address=addurl+"/zfxm/project_query";
	
	//初始化表格
	oTableInit.Init = function(){
		$("#table_data").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#table_data').bootstrapTable({
			url:address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: "asc",                   // 排序方式
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
			// 传递参数（*）,组织表格参数和页面查询参数
			queryParams : function (params) {
			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
					limit: params.limit,   // 页面数量大小
					offset: params.offset, // 当前页码
//					sort: params.sort,  //排序列名  
//				    sortOrder: params.order,//排位命令（desc，asc） 
//					search: params.search, // 工具栏查询内容,search:true才有
					
					projectname: $("#projectname").val(),
			    };
			    return temp;
			},
			// 是否显示父子表
			columns : [ {
				field : 'select',
				checkbox : true
			} , {
				field : 'teamname',
				title : '团队名称',
				width : 200,
				
			} ,{
				field : 'projectid',
				title : '项目ID',
				width : 100,
				
			}, {
				field : 'projectname',
				title : '项目名称',
				width : 200
			}, {
				field : 'remark',
				title : '备注',
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
			}
		});
	}
	
	return oTableInit;
};

//查询功能调用该方法
function table_search(){
	var address=addurl+"/zfxm/project_query";
	$('#table_data').bootstrapTable('refresh', {url: address});
}

function open_dialog(){
	//初始化下拉单
	teamgroup();
	
	//清空表单
	document.getElementById('dialog_form').reset();
	//重置表单校验,销毁重构
	$('#dialog_form').data('bootstrapValidator').destroy();
    $('#dialog_form').data('bootstrapValidator', null);
    all_form_validator();
    
	$('#modal_dialog button').show();
	$('#modal_dialog #btn_update').hide();
	$('#modal_dialog').modal('show');
	

}

function teamgroup(){
	var address = addurl + '/zfxm/teamgroup';

	//每次点击都要后台取数据
	$('#dialog_form #teamid').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#modal_dialog"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: address,
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data
				};
			}
		}
	});
	
	////后台取一次数据
//	$.ajax({
//		type : "POST",
//		url : address,
//		async : false,
//		cache : true,
//		data : {},
//		success : function(result) {
//			console.log(result)
//			console.log(result.results)
//			var aaa=result.results;
//			$("#teamid").select2({
//				dropdownParent: $("#modal_dialog"),//modal默认不显示，解决modal显示后下拉单样式问题
//				data : aaa
//			});	
//		},
//		error : function(XMLResponse) {
//			alert(XMLResponse.responseText)
//		}
//	});
	
}

function add_data(){
	if($('#dialog_form #teamid').select2('val')==null){
		swal("", '请选择团队',"error");
		return
	}
	
	var queryaddress=addurl+'/zfxm/project_query';
	var addaddress=addurl+'/zfxm/project_add';
	
	console.log($('#teamid').val())
	 //获取表单对象
    $("#dialog_form").bootstrapValidator('validate');//提交验证  
    if (!$("#dialog_form").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码  
    	return;
    }  
    
	$.ajax({
		type:"POST",
		url:addaddress,
		async:false,
		cache:true,
		data:$('#dialog_form').serialize(),
		success: function(result){
			if(result=='ok'){
				swal("" ,result,"success");
//				//清空表单
//				document.getElementById("prescription_dialog_form").reset();
				$('#table_data').bootstrapTable('refresh', {url: queryaddress});
				$('#modal_dialog').modal('hide');
			}else{
				swal("", result,"error");
			}
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
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
    	var queryaddress=addurl+'/zfxm/project_query';
    	var deladdress=addurl+'/zfxm/project_del';
    	
    	var projectids=new Array();
    	for(var i=0;i<IdSelections.length;i++){
    		projectids.push(IdSelections[i].projectid);
    	}
    	
    	parent.onloading();
    	$.ajax({
    		type:"POST",
    		url:deladdress,
    		async:false,
    		cache:true,
    		data:{projectids:projectids},
    		success: function(result){
    			parent.removeload();
    			if(result=='ok'){
    				swal("" ,result,"success");
    				$('#table_data').bootstrapTable('refresh', {url: queryaddress});
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
	teamgroup();
	
	$('#modal_dialog button').show();
	$('#modal_dialog #btn_add').hide();
	
	var IdSelections = $('#table_data').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	
	$('#dialog_form #teamid').html('<option value="' + IdSelections[0].teamid + '">' + IdSelections[0].teamname + '</option>').trigger("change");
//	$('#dialog_form #teamid').val(IdSelections[0].teamid).trigger("change");
	$('#dialog_form #projectid').val(IdSelections[0].projectid);
	$('#dialog_form #projectname').val(IdSelections[0].projectname);
	$('#dialog_form #remark').val(IdSelections[0].remark);
	
	$('#modal_dialog').modal('show');
}

function update_data(){
	var queryaddress=addurl+'/zfxm/project_query';
	var addaddress=addurl+'/zfxm/project_update';
	$.ajax({
		type:"POST",
		url:addaddress,
		async:false,
		cache:true,
		data:$('#dialog_form').serialize(),
		success: function(result){
			if(result=='ok'){
				swal("" ,result,"success");
				$('#table_data').bootstrapTable('refresh', {url: queryaddress});
			}else{
				swal("", result,"error");
			}
			$('#modal_dialog').modal('hide');
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
        	projectname: {
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
