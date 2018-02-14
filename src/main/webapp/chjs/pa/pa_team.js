var addurl=null;
$(document).ready(function() {
	addurl=$("#addurl").val();
	
	//声明一个表格对象，初始化表格
	var oTable=new TableInit();
	oTable.Init();
	
	//查询按钮功能
	$("#btn_query").click(function(){
		oTable.search();
	})
	
	//操作已勾选数据
	$("#btn_delete").click(function(){
		var ids = oTable.getIdSelections();//返回一个包含所有选中行中id列值的数组
		alert(ids);
	})
	
	//声明一个dialog窗口
	var oDialog = new DialogInit();
	
	$("#btn_add").click(function(){
		oDialog.Init();
//		$('#myModal').modal('show');
	})
	
	//根据div高度更新table高度
//	$('#tb_departments').bootstrapTable('resetView',{height:document.getElementById("tableview").offsetHeight-111});	
});

//创建一个表格对象
var TableInit =function () {
	var oTableInit=new Object();
	var address=addurl+"/pass/pass_team";
	
	//初始化表格
	oTableInit.Init = function(){
		$('#tb_departments').bootstrapTable({
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
			search: true,                       // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大(陈辉-验证可以提交查询数据到服务端)
			strictSearch: true,				
			showColumns: true,                  // 是否显示所有的列按钮
			showRefresh: true,                  // 是否显示刷新按钮
			minimumCountColumns: 2,             // 最少允许的列数
			clickToSelect: false,               // 是否启用点击选中行
			height: 450,                        //450, 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
			showToggle:true,                    // 是否显示详细视图和列表视图的切换按钮
			cardView: false,                    // 是否显示详细视图
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
					
					departmentname: $("#txt_search_departmentname").val(),
					statu: $("#txt_search_statu").val(),
			    };
			    return temp;
			},
			// 是否显示父子表
			columns : [ {
				checkbox : true
			} , {
				field : 'Name',
				title : '部门名称',
				width : 300,
				
			}, {
				field : 'ParentName',
				title : '上级部门',
				width : 300
			}, {
				field : 'Level',
				title : '部门级别',
				width : 300
			}, {
				field : 'Desc',
				title : '描述',
				width : 300
			}, {
				field : 'Desc0',
				title : '描述0',
				width : 300,
			}, {
				field : 'Desc1',
				title : '描述1',
				width : 300,
				formatter: function (value, row, index) {
                    if (value != "") {
                        return '111';
                    }
                    else
                        return '222';
                }
			}, {
				field : 'Desc2',
				title : '描述2',
				visible: false,
				width : 300
			}, {
				field : 'Desc3',
				title : '描述3',
				width : 300
			}, {
				field : 'Desc4',
				title : '描述4',
				width : 300
			}, {
				field : 'Desc5',
				title : '描述5',
				width : 300
			}, {
				field : 'Desc6',
				title : '描述6',
				width : 300
			}, {
				field : 'Desc7',
				title : '描述7',
				width : 300
			} ],
			
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
		$('#tb_departments').bootstrapTable('refresh', {url: address});
	}
	
	//返回当前列表已选数据，返回的是数组
	oTableInit.getIdSelections = function(){
		//返回整个数据
		var IdSelections = $('#tb_departments').bootstrapTable('getSelections');
		
		//返回具体字段数据
		for(var i=0;i<IdSelections.length;i++){
			IdSelections[i]=IdSelections[i].ID;
		}
		return IdSelections;
	}
	
	//消息提示框
	oTableInit.dialog = function(){
		BootstrapDialog.show({
			title : 'More dialog sizes',				//标题
			closable: false,							//点击空白处禁止关闭
//			message : 'Hi Apple!',
			message: function(dialog) {
                var $content = $('<div><button class="btn btn-success">Revert button status right now.</button></div>');
                return $content;
            },
			size : BootstrapDialog.SIZE_NORMAL, 		// 默认尺寸
			buttons : [ {
				label : '发&nbsp;&nbsp;&nbsp;送',
				action : function(dialog) {
					dialog.close();
				}
			}, {
				label : '取消',
				action : function(dialog) {
					dialog.close();
				}
			},{
				label : 'Normal',
				action : function(dialog) {
					dialog.setTitle('Normal');
					dialog.setSize(BootstrapDialog.SIZE_NORMAL);
				}
			}, {
				label : 'Small',
				action : function(dialog) {
					dialog.setTitle('Small');
					dialog.setSize(BootstrapDialog.SIZE_SMALL);
				}
			}, {
				label : 'Wide',
				action : function(dialog) {
					dialog.setTitle('Wide');
					dialog.setSize(BootstrapDialog.SIZE_WIDE);
				}
			}, {
				label : 'Large',
				action : function(dialog) {
					dialog.setTitle('Large');
					dialog.setSize(BootstrapDialog.SIZE_LARGE);
				}
			}]
		});
		
		return dialog;
	};
	
	return oTableInit;
};


//创建一个模态框dialog对象
var DialogInit = function(){
	var oDialogInit = new Object();

	//表单提示窗口
	oDialogInit.Init = function(){
		BootstrapDialog.show({
			title : 'More dialog sizes',				//标题
			closable: false,							//点击空白处禁止关闭
//			message : 'Hi Apple!',						//dialog提示语
			message: function(dialog) {
                var content = $(document.getElementById("myModal").innerHTML);
                return content;
            },
			size : BootstrapDialog.SIZE_NORMAL, 		// 默认尺寸
			buttons : [ {
				label : '发&nbsp;&nbsp;&nbsp;送',
				action : function(dialog) {
					dialog.close();
				}
			}, {
				label : '取消',
				action : function(dialog) {
					dialog.close();
				}
			},{
				label : 'Normal',
				action : function(dialog) {
					dialog.setTitle('Normal');
					dialog.setSize(BootstrapDialog.SIZE_NORMAL);
				}
			}, {
				label : 'Small',
				action : function(dialog) {
					dialog.setTitle('Small');
					dialog.setSize(BootstrapDialog.SIZE_SMALL);
				}
			}, {
				label : 'Wide',
				action : function(dialog) {
					dialog.setTitle('Wide');
					dialog.setSize(BootstrapDialog.SIZE_WIDE);
				}
			}, {
				label : 'Large',
				action : function(dialog) {
					dialog.setTitle('Large');
					dialog.setSize(BootstrapDialog.SIZE_LARGE);
				}
			}]
		});
	};
	
	return oDialogInit;
	
}