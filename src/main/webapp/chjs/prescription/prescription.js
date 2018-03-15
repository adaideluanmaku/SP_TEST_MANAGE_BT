var addurl=null;
$(document).ready(function() {
	addurl=$('#addurl').val();
	
	//声明一个表格对象，初始化表格
	var oTable=new TableInit();
	oTable.Init();
	
	//查询按钮功能
	$('#btn_query').click(function(){
		oTable.search();
	})
	
	//鼠标浮动提示窗口-触发事件
	$(document).on('mouseover mouseout','#table_prescription td',function(){ 
		if(event.type == 'mouseover'){//鼠标悬浮
			if($(this).text().length>10){//浮动窗口
				$('body').append('<div id="title" style="max-width:400px;max-height:200px;position: absolute;'
						+'top:0px;left:0px;background-color: #fff2e8;/*自动换行*/	word-wrap: break-word;' 
						+'overflow: hidden;text-overflow: ellipsis;'
						+'border: 1px solid #c0c0c0;"></div>');
				$(this).mousemove(function(e) { 
					var xx = e.originalEvent.x || e.originalEvent.layerX || 0; 
					var yy = e.originalEvent.y || e.originalEvent.layerY || 0; 
					//如果提示框在最下面超过页面高度，则靠上显示
					var bodyheight=document.body.offsetHeight;
					if((yy+10+200)<bodyheight){
						$('#title').css('left',xx+20+'px');
						$('#title').css('top',yy+10+'px');
					}else{
						$('#title').css('left',xx+20+'px');
						$('#title').css('top',yy+10-200+'px');
					}
					
					$('#title').text($(this).text());
				}); 
			}
		}else if(event.type == 'mouseout'){//鼠标离开
			$('#title').remove();
		}
	}); 
	
	
	//初始化所有表单校验规则
	all_form_validator();
    
});

//=========================
//创建一个表格对象
var TableInit =function () {
	var oTableInit=new Object();
	var queryaddress=addurl+'/prescription/query';
	
	//初始化表格
	oTableInit.Init = function(){
		$("#table_prescription").bootstrapTable('destroy'); // 销毁数据表格,不销毁可能有数据缓存问题
		$('#table_prescription').bootstrapTable({
			url:queryaddress,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
			contentType:'application/x-www-form-urlencoded; charset=UTF-8', //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: false,                    // 是否启用排序
			sortOrder: 'asc',                   // 排序方式
			sidePagination: 'server',           // 分页方式：client客户端分页，server服务端分页（*），两种分页JSON结构不同
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
			height: 450,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
//			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
//			fixedColumns: true,					//固定列,引入bootstrap-table-fixed-columns.js
//	        fixedNumber:2,						//固定前两列,引入bootstrap-table-fixed-columns.js
			// 传递参数（*）,组织表格参数和页面查询参数
			queryParams : function (params) {
			    var temp = {   // 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
					limit: params.limit,   // 页面数量大小
					offset: params.offset, // 当前页码,已乘以当前页总数
//					sort: params.sort,  //排序列名  
//				    sortOrder: params.order,//排位命令（desc，asc） 
//					search: params.search, // 工具栏查询内容,search:true才有
					
					patientname: $('#formSearch #patientname').val(),
			    };
			    return temp;
			},
			// 是否显示父子表
			columns : [ {
				checkbox : true,
			} , {
				field : 'patientname',
				title : '病人名称',
				width : 100,
				align : 'center'
				
			}, {
				field : 'prescription_json',
				title : '处方数据',
				width : 500,
				align : 'center',
			}, {
				field : '',
				title : '操作',
				width : 50,
				align : 'center',
				formatter: function(value,row,index){
					if(row.prescription_json==undefined || row.prescription_json==''){
						return '--'
					}
					
                    return '<a href="'+addurl+'/prescription/prescription_edit?pre_id='+row.pre_id+'&patientname='+row.patientname+'" target="_blank">打开</a>';
				}
			}],
			
			// 1.点击每行进行函数的触发
			onDblClickRow : function(row, $element ,field){
//				alert(1);
//				alert(row);
//				alert($element);
//				alert(field);
			},
			
			// 1.点击每行进行函数的触发
			onClickRow : function(row, tr,flied){
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
			}
		});
	}
	
	//查询功能调用该方法
	oTableInit.search = function(){
		$('#table_prescription').bootstrapTable('refresh', {url: queryaddress});
	}
	
	return oTableInit;
};

function open_prescription_dialog(){
	//清空表单
	document.getElementById('prescription_dialog_form').reset();
	//重置表单校验,销毁重构
	$("#prescription_dialog_form").data('bootstrapValidator').destroy();
    $('#prescription_dialog_form').data('bootstrapValidator', null);
    all_form_validator();
    
	$('#prescription_dialog button').show();
	$('#prescription_dialog #btn_update').hide();
	$('#prescription_dialog').modal('show');
}

function add_prescription_data(){
	var queryaddress=addurl+'/prescription/query';
	var addaddress=addurl+'/prescription/add';
	
	 //获取表单对象
    $("#prescription_dialog_form").bootstrapValidator('validate');//提交验证  
    if (!$("#prescription_dialog_form").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码  
    	return;
    }  
    
	$.ajax({
		type:"POST",
		url:addaddress,
		async:false,
		cache:true,
		data:$('#prescription_dialog_form').serialize(),
		success: function(result){
			if(result==1){
//				//清空表单
//				document.getElementById("prescription_dialog_form").reset();
				$('#prescription_dialog').modal('hide');
				$('#table_prescription').bootstrapTable('refresh', {url: queryaddress});
			}
			
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
}

function del_prescription_data(){
	swal({
        title: "Are you sure?",
        text: "You will not be able to recover this imaginary file!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
    	
    	var queryaddress=addurl+'/prescription/query';
    	var deladdress=addurl+'/prescription/del';
    	
    	var IdSelections = $('#table_prescription').bootstrapTable('getSelections');
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
    	
    	$.ajax({
    		type:"POST",
    		url:deladdress,
    		async:false,
    		cache:true,
    		data:{pre_id:pre_ids},
    		success: function(result){
    			$('#table_prescription').bootstrapTable('refresh', {url: queryaddress});
    		},
    		error:function(XMLResponse){
    			alert(XMLResponse.responseText)
    		}
    	});
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

function edit_prescription_dialog(){
	$('#prescription_dialog button').show();
	$('#prescription_dialog #btn_add').hide();
	
	var IdSelections = $('#table_prescription').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	
	$('#prescription_dialog_form #pre_id').val(IdSelections[0].pre_id);
	$('#prescription_dialog_form #patientname').val(IdSelections[0].patientname);
	$('#prescription_dialog_form #prescription_json').val(IdSelections[0].prescription_json);
	
	$('#prescription_dialog').modal('show');
}

function update_prescription_data(){
	var queryaddress=addurl+'/prescription/query';
	var addaddress=addurl+'/prescription/update';
	$.ajax({
		type:"POST",
		url:addaddress,
		async:false,
		cache:true,
		data:$('#prescription_dialog_form').serialize(),
		success: function(result){
			if(result==1){
				$('#prescription_dialog').modal('hide');
				$('#table_prescription').bootstrapTable('refresh', {url: queryaddress});
			}
			
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
}

function all_form_validator(){
	$("#prescription_dialog_form").bootstrapValidator({  
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
        	patientname: {
                validators: {  
                    notEmpty: {//检测非空,radio也可用  
                        message: '文本框必须输入'  
                    },  
                    stringLength: {//检测长度  
                        min: 1,  
//                        max: 30,  
                        message: '长度必须大于1'  
                    },  
                }  
            } ,
            prescription_json: {
	            validators: {  
	                notEmpty: {//检测非空,radio也可用  
	                    message: '文本框必须输入'  
	                },  
	            }  
	        } 
        }  
    });  
}
