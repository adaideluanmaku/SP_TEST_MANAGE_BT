var addurl=null;
var TableInit_serverip=null;

$(document).ready(function() {
	addurl=$("#addurl").val();
	
	//控制制作HIS数据页面输入框
	modal_dialog_HIS_data_change();
	
	//初始化查询下拉单
	_select2();
	
	//声明一个表格对象，初始化表格
	var oTable=new TableInit();
	oTable.Init();
	
	TableInit_serverip = new TableInit_serverip();
	TableInit_serverip.Init();
	
	//初始化时间插件
	init_time();
	
	//初始化所有表单校验规则
	all_form_validator();
	
	//鼠标浮动提示窗口-触发事件
	$(document).on('mouseover mouseout','table td',function(){ 
		if(event.type == 'mouseover'){//鼠标悬浮
			if($(this).text().length>5){//浮动窗口
				$('body').append('<div id="title" style="max-width:400px;max-height:200px;position: absolute;'
						+'top:0px;left:0px;background-color: #fff2e8;/*自动换行*/	word-wrap: break-word;' 
						+'overflow: hidden;text-overflow: ellipsis;'
						+'border: 1px solid #c0c0c0; z-index:9999;"></div>');
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
					
					if($(this).find('a').length > 0){
						$('#title').text($(this).find('a').text().substring(0,300));
					}else{
						$('#title').text($(this).text().substring(0,300));
					}
				}); 
			}
		}else if(event.type == 'mouseout'){//鼠标离开
			$('#title').remove();
		}
	}); 
//	$(document).on('mouseover mouseout','#table_data td',function(){ 
//		if(event.type == 'mouseover'){//鼠标悬浮
//			if($(this).text().length>10){//浮动窗口
//				$('body').append('<div id="title" style="max-width:400px;max-height:200px;position: absolute;'
//						+'top:0px;left:0px;background-color: #fff2e8;/*自动换行*/	word-wrap: break-word;' 
//						+'overflow: hidden;text-overflow: ellipsis;'
//						+'border: 1px solid #c0c0c0;"></div>');
//				$(this).mousemove(function(e) { 
//					var xx = e.originalEvent.x || e.originalEvent.layerX || 0; 
//					var yy = e.originalEvent.y || e.originalEvent.layerY || 0; 
//					//如果提示框在最下面超过页面高度，则靠上显示
//					var bodyheight=document.body.offsetHeight;
//					if((yy+10+$('#title').height())<bodyheight){
//						$('#title').css('left',xx+20+'px');
//						$('#title').css('top',yy+10+'px');
//					}else{
//						$('#title').css('left',xx+20+'px');
//						$('#title').css('top',yy+10-200+'px');
//					}
//					
//					$('#title').text($(this).text());
//				}); 
//			}
//		}else if(event.type == 'mouseout'){//鼠标离开
//			$('#title').remove();
//		}
//	}); 
	
});

//创建一个表格对象
var TableInit =function () {
	var oTableInit=new Object();
	var address=addurl+"/pa/testmng_query";
	
	//初始化表格
	oTableInit.Init = function(){
		$('#table_data').bootstrapTable({
			url:address,         				// 请求后台的URL（*）
			method: 'post',                     // 请求方式（*）
//			dataType: "json",					//数据类型
			contentType:"application/x-www-form-urlencoded; charset=UTF-8", //get请求时可不设置，POST请求时需要HTTP内容类型
			toolbar: '#toolbar',                // 工具按钮用哪个容器
			striped: true,                      // 是否显示行间隔色
			cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   // 是否显示分页（*）
			sortable: true,                     // 是否启用排序
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
					sort: params.sort,  //排序列名  
				    order: params.order,//排位命令（desc，asc） 
//					search: params.search, // 工具栏查询内容,search:true才有
					
					searchdata: $("#searchdata").val(),
					projectid: $("#table_div #projectid_search").select2('val'),
			    };
			    return temp;
			},
			// 是否显示父子表
			columns : [ {
				field:'ID',
				checkbox : true
			},{
				field:'projectname',title:'项目名称',width:100,halign:'center'
			},{
				field:'testresult',title:'测试',width:80,halign:'center',sortable : true
			},{
				field:'testname',title:'案例名称',width:100,halign:'center'
			},{
				field:'testno',title:'案例编号',width:80,halign:'center'
			},{
				field:'testtext',title:'逻辑描述',width:250,halign:'center'
			},{
				field:'testin',title:'输入条件',width:250,halign:'center'
			},{
				field:'testout',title:'预期结果',width:250,halign:'center'
			},{
				field:'remark',title:'备注',width:100,halign:'center'
			},{
				field:'selenium_share_status',title:'selenium公共脚本',width:200,halign:'center',
				formatter: function (value, row, index) {
                    if (value ==0) {
                        return '否';
                    }else{
                    	 return '是';
                    }
                }
			},{
				field:'status',title:'状态',width:50,halign:'center',
				formatter: function (value, row, index) {
                    if (value ==1) {
                        return '启用';
                    }else{
                    	 return '停用';
                    }
                }
			},{
				field:'username',title:'姓名',width:80,halign:'center'
			},{
				field:'inserttime',title:'创建日期',width:130,halign:'center'
			}, {
				field : '',
				title : '操作',
				width : 50,
				align : 'center',
				formatter: function(value,row,index){
//					if(row.testin==undefined || row.testin==''){
//						return '--'
//					}
					
                    return '<a href="'+addurl+'/prescription/prescription_edit?prescriptiontype=2&testid='+row.testid+'" target="_blank">打开</a>';
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
			}
		});
	}
	
	return oTableInit;
};

//查询功能调用该方法
function table_search(){
	var address=addurl+"/pa/testmng_query";
	$('#table_data').bootstrapTable('refresh', {url: address});
}

function open_dialog(){
	//清空表单
	document.getElementById('dialog_form').reset();
	$('#modal_dialog #projectid').select2('val','0');
	$('#modal_dialog #teamid').select2('val','0');
	
	//重置表单校验,销毁重构
	$('#dialog_form').data('bootstrapValidator').destroy();
    $('#dialog_form').data('bootstrapValidator', null);
    all_form_validator();
    
	$('#modal_dialog button').show();
	$('#modal_dialog #btn_update').hide();
	$('#modal_dialog').modal('show');

}

//每次点击都要后台取数据
function _select2(){
	var addressprojectgroup = addurl + '/pa/projectgroup';

	//每次点击都要后台取数据
	$('#modal_dialog #projectid').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#modal_dialog"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: addressprojectgroup,
			data: function (params) {
	             return {
	            	 teamid:$('#modal_dialog #teamid').select2('val')
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
	$('#table_div #projectid_search').select2({
		placeholder: "--请选择--",
		allowClear: true,
		ajax: {
			url: addressprojectgroup,
			data: function (params) {
	             return {
	            	 teamid:$('#table_div #teamid_search').select2('val')
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
	//每次点击都要后台取数据
	$('#modal_dialog_HIS_data #projectid').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#modal_dialog_HIS_data"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: addressprojectgroup,
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data
				};
			}
		}
	});
	
	var addressteamgroup = addurl + '/pa/teamgroup';
	$('#table_div #teamid_search').select2({
		placeholder: "--请选择--",//默认初始值
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
	$('#modal_dialog #teamid').select2({
		placeholder: "--请选择--",
//		placeholder: { id: "0", text: "--请选择--" },//默认初始值
		allowClear: true,
		dropdownParent: $("#modal_dialog"),//modal默认不显示，解决modal显示后下拉单样式问题
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
	$('#modal_dialog #teamid').on("select2:select", function(e) {
		$('#modal_dialog #projectid').select2('val','0');
	})
	
	$("#modal_dialog #status").select2({dropdownParent: $("#modal_dialog"),data:[{id:0,text:'停用'},{id:1,text:'启动'}]});
	$("#modal_dialog #status").val('1').trigger("change");
	
	$("#modal_dialog #selenium_share_status").select2({dropdownParent: $("#modal_dialog"),data:[{id:0,text:'否'},{id:1,text:'是'}]});
	$("#modal_dialog #selenium_share_status").val('0').trigger("change");
	
	
}

function add_data(){
	if($('#dialog_form #projectid').select2('val')==null){
		swal("", '请选择项目',"error");
		return
	}
	
	var queryaddress=addurl+'/pa/testmng_query';
	var addaddress=addurl+'/pa/testmng_add';
	
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
			table_search()
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
    	var queryaddress=addurl+'/pa/testmng_query';
    	var deladdress=addurl+'/pa/testmng_del';
    	
    	var testids=new Array();
    	for(var i=0;i<IdSelections.length;i++){
    		testids.push(IdSelections[i].testid);
    	}
    	
    	$.ajax({
    		type:"POST",
    		url:deladdress,
    		async:false,
    		cache:true,
    		data:{testids:testids},
    		success: function(result){
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
	//清空表单
	document.getElementById('dialog_form').reset();
	$('#modal_dialog #projectid').select2('val','0');
	$('#modal_dialog #teamid').select2('val','0');
	
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
	
	$('#modal_dialog #testid').val(IdSelections[0].testid);
	$('#modal_dialog #projectid').html('<option value="' + IdSelections[0].projectid + '">' + IdSelections[0].projectname + '</option>').trigger("change");
	$("#modal_dialog #status").val(IdSelections[0].status).trigger("change");
	$("#modal_dialog #selenium_share_status").val(IdSelections[0].selenium_share_status).trigger("change");
	$('#modal_dialog #testname').val(IdSelections[0].testname);
	$('#modal_dialog #testno').val(IdSelections[0].testno);
	$('#modal_dialog #testtext').val(IdSelections[0].testtext);
	$('#modal_dialog #testin').val(IdSelections[0].testin);
	$('#modal_dialog #testout').val(IdSelections[0].testout);
	$('#modal_dialog #remark').val(IdSelections[0].remark);
	$('#modal_dialog #testout_response').val(IdSelections[0].testout_response);
	
	$('#modal_dialog').modal('show');
}

function update_data(){
	var queryaddress=addurl+'/pa/testmng_query';
	var addaddress=addurl+'/pa/testmng_update';
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
				$('#modal_dialog').modal('hide');
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

//初始化时间插件
function init_time(){
	$('#modal_dialog_HIS_data #datetime1').datetimepicker({  
        format: 'YYYY-MM-DD',  //YYYY-MM-DD HH:mm:ss
        locale: moment.locale('zh-cn'),
        defaultDate: "2010-01-01"
    });
}


function open_modal_dialog_HIS_data(){
	$('#modal_dialog_HIS_data').modal('show');
}

function create_data(){
	var address=addurl+"/pa/date_to_oracle";
	//loading
	parent.onloading();
	
	$.ajax({
		type:"POST",
		url:address,
		async:true,
		data:{hiscodes1:$("#modal_dialog_HIS_data #hiscodes1").val(), 
			datetime1:$("#modal_dialog_HIS_data #datetime1 input").val(),
			sum_date1:$("#modal_dialog_HIS_data #sum_date1").val(),
			count1:$("#modal_dialog_HIS_data #count1").val(),
			mz1:$("#modal_dialog_HIS_data #mz1").val(),
			zy1:$("#modal_dialog_HIS_data #zy1").val(),
			cy1:$("#modal_dialog_HIS_data #cy1").val(),
			dict1:$("#modal_dialog_HIS_data #dict1").val(),
			createview1:$("#modal_dialog_HIS_data #createview1").val(),
			trunca1:$("#modal_dialog_HIS_data #trunca1").val(),
//			anlisum:$("#modal_dialog_HIS_data #anlisum").val(),
			projectid1:$('#modal_dialog_HIS_data #projectid').select2('val'),
			match_scheme1:$("#modal_dialog_HIS_data #match_scheme1").val(),
			createTB1:$("#modal_dialog_HIS_data #createTB1").val(),
			passorpa_hisdata1:0},
			
		success: function(result){
			//loading
			parent.removeload();
			swal("" ,result,"success");
			
		},
		error:function(XMLResponse){
			parent.removeload();
			alert(XMLResponse.responseText)
		}
	});
	return false;
}

function modal_dialog_HIS_data_change(){
	$("#modal_dialog_HIS_data #createview1").attr("disabled",true);
	$("#modal_dialog_HIS_data #mz1").attr("disabled",true);
	$("#modal_dialog_HIS_data #zy1").attr("disabled",true);
	$("#modal_dialog_HIS_data #cy1").attr("disabled",true);
	$("#modal_dialog_HIS_data #dict1").attr("disabled",true);
	
	$('#modal_dialog_HIS_data #hiscodes1').bind('input propertychange', function() {
		if($('#modal_dialog_HIS_data #hiscodes1').val()!=''){
			$("#modal_dialog_HIS_data #createview1").attr("disabled",false);
		}else{
			$("#modal_dialog_HIS_data #createview1").val(0);
			$("#modal_dialog_HIS_data #createview1").attr("disabled",true);
		}
		if($('#modal_dialog_HIS_data #hiscodes1').val()!='' && $("#modal_dialog_HIS_data #sum_date1").val() !=''
			&& $("#modal_dialog_HIS_data #count1").val() != ''){
			$("#modal_dialog_HIS_data #mz1").attr("disabled",false);
			$("#modal_dialog_HIS_data #zy1").attr("disabled",false);
			$("#modal_dialog_HIS_data #cy1").attr("disabled",false);
		}else{
			$("#modal_dialog_HIS_data #mz1").val(0);
			$("#modal_dialog_HIS_data #zy1").val(0);
			$("#modal_dialog_HIS_data #cy1").val(0);
			$("#modal_dialog_HIS_data #mz1").attr("disabled",true);
			$("#modal_dialog_HIS_data #zy1").attr("disabled",true);
			$("#modal_dialog_HIS_data #cy1").attr("disabled",true);
		}
	});
	$('#modal_dialog_HIS_data #sum_date1').bind('input propertychange', function() {
		if($('#modal_dialog_HIS_data #sum_date1').val()!='' && $("#modal_dialog_HIS_data #hiscodes1").val() !=''
			&& $("#modal_dialog_HIS_data #count1").val() != ''){
			$("#modal_dialog_HIS_data #mz1").attr("disabled",false);
			$("#modal_dialog_HIS_data #zy1").attr("disabled",false);
			$("#modal_dialog_HIS_data #cy1").attr("disabled",false);
		}else{
			$("#modal_dialog_HIS_data #mz1").val(0);
			$("#modal_dialog_HIS_data #zy1").val(0);
			$("#modal_dialog_HIS_data #cy1").val(0);
			$("#modal_dialog_HIS_data #mz1").attr("disabled",true);
			$("#modal_dialog_HIS_data #zy1").attr("disabled",true);
			$("#modal_dialog_HIS_data #cy1").attr("disabled",true);
		}
	});
	$('#modal_dialog_HIS_data #count1').bind('input propertychange', function() {
		if($('#modal_dialog_HIS_data #count1').val()!='' && $("#modal_dialog_HIS_data #hiscodes1").val() !=''
			&& $("#modal_dialog_HIS_data #sum_date1").val() != ''){
			$("#modal_dialog_HIS_data #mz1").attr("disabled",false);
			$("#modal_dialog_HIS_data #zy1").attr("disabled",false);
			$("#modal_dialog_HIS_data #cy1").attr("disabled",false);
		}else{
			$("#modal_dialog_HIS_data #mz1").val(0);
			$("#modal_dialog_HIS_data #zy1").val(0);
			$("#modal_dialog_HIS_data #cy1").val(0);
			$("#modal_dialog_HIS_data #mz1").attr("disabled",true);
			$("#modal_dialog_HIS_data #zy1").attr("disabled",true);
			$("#modal_dialog_HIS_data #cy1").attr("disabled",true);
		}
	});	
	$('#modal_dialog_HIS_data #match_scheme1').bind('input propertychange', function() {
		if($('#modal_dialog_HIS_data #match_scheme1').val()!=''){
			$("#modal_dialog_HIS_data #dict1").attr("disabled",false);
		}else{
			$("#modal_dialog_HIS_data #dict1").val(0);
			$("#modal_dialog_HIS_data #dict1").attr("disabled",true);
		}
	});
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

function open_modal_dialog_serverip_all(){
	var projectid=$("#table_div #projectid_search").select2('val');
	if(projectid==null || projectid==''){
		swal({
            title: "警告",
            text: "请选择项目数据进行操作."
        });
		return;
	}
	
	$('#modal_dialog_serverip').modal('show');
	$('#modal_dialog_serverip #btn_add3').hide();
	$('#modal_dialog_serverip #btn_add2').hide();
	$('#modal_dialog_serverip #btn_add1').show();
	
}
function screen_all(){
	var IdSelectionsIP = $('#modal_dialog_serverip #table_data').bootstrapTable('getSelections');
	if(IdSelectionsIP.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	
	var projectid=$("#table_div #projectid_search").select2('val');
	
	parent.onloading();//加载loading 
	$.ajax({
		type:"POST",
		url:addurl+"/pa/pa_screen_all",
		async:true,
		data:{projectid:projectid,search_data:$("#table_div #searchdata").val(),serveraddress:IdSelectionsIP[0].serveraddress},
		cache:true,
		success: function(result){
			parent.removeload();
			swal({
	            title: "警告",
	            text: result
	        });
			table_search();
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
	
	return false;
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
	
	var obj = JSON.parse(IdSelections[0].testin);
	var VisitCode=obj.Patient.VisitCode;//医院流水号,审查时使用
	
	if(VisitCode=='' || VisitCode==null){
		swal({
            title: "警告",
            text: "选择的数据里面门诊/住院流水号为空！"
        });
		return;
	}
	
	
	$('#modal_dialog_serverip').modal('show');
	$('#modal_dialog_serverip #btn_add3').hide();
	$('#modal_dialog_serverip #btn_add2').show();
	$('#modal_dialog_serverip #btn_add1').hide();
	
}

function screen_one(){
	var IdSelectionsIP = $('#modal_dialog_serverip #table_data').bootstrapTable('getSelections');
	if(IdSelectionsIP.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	
	var IdSelections = $('#table_div #table_data').bootstrapTable('getSelections');
	
	var testid=IdSelections[0].testid;
	var obj = JSON.parse(IdSelections[0].testin);
	var VisitCode=obj.Patient.VisitCode;//医院流水号,审查时使用
	
	parent.onloading();
	$.ajax({
		type:"POST",
		url:addurl+"/pa/pa_screen",
		async:true,
		data:{state:1,testid:testid,VisitCode:VisitCode,serveraddress:IdSelectionsIP[0].serveraddress},
		cache:true,
		success: function(result){
			parent.removeload();
			$('#modal_dialog_serverip').modal('hide');
			$('#pa_result_modal_dialog #jsonerr').html(result);
			$('#pa_result_modal_dialog').modal('show');
			
			table_search();
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
	return false;
}

function open_modal_dialog_serverip_one_win(){
	var IdSelections = $('#table_data').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	$('#modal_dialog_serverip').modal('show');
	$('#modal_dialog_serverip #btn_add3').show();
	$('#modal_dialog_serverip #btn_add2').hide();
	$('#modal_dialog_serverip #btn_add1').hide();
	
}

function screen_one_win(){
	var IdSelectionsIP = $('#modal_dialog_serverip #table_data').bootstrapTable('getSelections');
	if(IdSelectionsIP.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	
	var IdSelections = $('#table_div #table_data').bootstrapTable('getSelections');
	
	var testid=IdSelections[0].testid;
	var obj = JSON.parse(IdSelections[0].testin);
	var VisitCode=obj.Patient.VisitCode;//医院流水号,审查时使用
	
	parent.onloading();
	$.ajax({
		type:"POST",
		url:addurl+"/pa/pa_screen",
		async:true,
		data:{state:2,testid:testid,VisitCode:VisitCode,serveraddress:IdSelectionsIP[0].serveraddress},
		cache:true,
		success: function(result){
			parent.removeload();
			$('#modal_dialog_serverip').modal('hide');
			swal({
	            title: "警告",
	            text: "审查结束,请到.net程序数据库查看结果"
	        });
			table_search();
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
	return false;
}

function pa_redis_clear(){
	$.ajax({
		type:"POST",
		url:addurl+"/pa/pa_redis_clear_sd",
		async:false,
		cache:true,
		data:{},
		success: function(result){
			swal({
	            title: "提示",
	            text: result
	        });
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
}

