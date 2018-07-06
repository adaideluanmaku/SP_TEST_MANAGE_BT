<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>INSPINIA | project</title>
<!-- SYS CSS -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/css/style.css" rel="stylesheet">

<!-- SYS JS -->
<!-- 引入 Bootstrap -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/js/jquery-3.2.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-table/bootstrap-table.js"></script>
<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-table/bootstrap-table.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-table/bootstrap-table-zh-CN.js"></script>

<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-dialog/bootstrap-dialog.min.css" rel="stylesheet" >
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-dialog/bootstrap-dialog.min.js"></script>
    
<!-- bootstrapValidator -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap_home/bootstrapValidator/css/bootstrapValidator.css"/>
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrapValidator/js/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrapValidator/js/language/zh_CN.js"></script>

<!-- Sweet alert -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/sweetalert.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/sweetalert/sweetalert.min.js"></script>

<!-- select2 -->
<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-select2/css/select2.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-select2/js/select2.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-select2/js/i18n/zh-CN.js"></script>

<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap3-editable/js/bootstrap-editable.js"></script>
<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap3-editable/js/bootstrap-table-editable.js"></script>

<!-- bootstrap-table-export -->
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-table-export/bootstrap-table-export.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-table-export/tableExport.js"></script>

<!-- MY JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/pass/pass_testmng.js"></script>

</head>
<body style="background-color: #ffffff;">
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">

<div id="wrapper" >
	<div  class="gray-bg">
		<div id="header_path" class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				<ol class="breadcrumb">
					<li><a>home</a></li>
					<li><a>PASS测试管理</a></li>
					<li class="active"><strong>案例管理</strong></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
		</div>
		<div class="animated fadeInRight">
			<!-- 列表表格-开始制作 -->
			<div id="table_div" class="panel-body" style="padding-bottom:0px;">
				<div id="search_div" class="panel panel-default">
				    <div class="panel-heading">查询条件</div>
				    <div class="panel-body">
				    	<div class="row" style="margin-bottom:15px">
				    		<div class="col-sm-3">
			    				<label for="teamid_search" class="control-label  col-sm-4" style="padding-top:7px">团队名称</label>
			    				<div class="col-sm-8">
			    					<select id="teamid_search" name="teamid_search" class="js-data-example-ajax" style="width:100px"></select>
			    				</div>
				    		</div>
				    		<div class="col-sm-3">
				    			<label for="projectid_search" class="control-label col-sm-4" style="padding-top:7px">项目名称</label>
					           	<div class="col-sm-8">
					           		<select id="projectid_search" name="projectid_search" class="js-data-example-ajax" style="width:100px"></select>	
					           	</div>
					            
				    		</div>
				    		<div class="col-sm-3">
				    			<label for="anlitype" class="control-label col-sm-4" style="padding-top:7px">案例类型</label>
				    			<div class="col-sm-8">
				    				<select id="anlitype" name="anlitype" class="js-data-example-ajax" style="width:100px"></select>
				    			</div>
				    		</div>
				    	</div>
				    	<div class="row" style="margin-bottom:15px">
				    		<div class="col-sm-3">
				    			<label class="control-label col-sm-4" for="moduleid" style="padding-top:7px">模块名称</label>
				                <div class="col-sm-8">
				                    <select id="moduleid" name="moduleid" class="js-data-example-ajax" style="width:150px"></select>
				                </div>
				    		</div>
				    		<div class="col-sm-3">
				    			<label class="control-label col-sm-4" for="searchdata" style="padding-top:7px">案例名称</label>
				                <div class="col-sm-8">
				                    <input id="searchdata" type="text" class="form-control" >
				                </div>
				    		</div>
				    		<div class="col-sm-3">
				    			<button type="button" id="btn_query" class="btn btn-primary" onclick="table_search()">查询</button>
				    		</div>
				    	</div>
				    	<!-- <div class="col-sm-6">
				    	 	<div class="row" style="margin-bottom:15px">
					    		<label for="anlitype" class="col-sm-2 control-label" style="padding-top:7px">案例类型</label>
					            <div class="col-sm-4">
					            	<select id="anlitype" name="anlitype" class="js-data-example-ajax" style="width:100%"></select>
					            </div>
				    			<label class="control-label col-sm-2" for="txt_search_departmentname" style="padding-top:7px">案例名称</label>
				                <div class="col-sm-4">
				                    <input type="text" class="form-control" id="searchdata">
				                </div>
					    	</div>
					    	<div class="row">
					    		<label for="teamid_search" class="col-sm-2 control-label" style="padding-top:7px">团队名称</label>
					            <div class="col-sm-4">
					            	<select id='teamid_search' name="teamid_search" class="js-data-example-ajax" style="width:100%"></select>
					            </div>
					    		<label for="projectid_search" class="col-sm-2 control-label" style="padding-top:7px">项目名称</label>
					            <div class="col-sm-4">
					            	<select id='projectid_search' name="projectid_search" class="js-data-example-ajax" style="width:100%"></select>
					            </div>
					    	</div>
				    	</div>
				    	<div class="col-sm-6">
				    		<div class="col-sm-3" style="text-align: left;">
			                    <button type="button" id="btn_query" class="btn btn-primary" onclick="table_search()">查询</button>
			                </div>
				    	</div>-->
				    </div> 
				</div>  
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="open_dialog()">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <button id="btn_edit" type="button" class="btn btn-default" onclick="edit_dialog()">
					        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
					    </button>
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="del_data()">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					    <div  class="btn-group">
							<button id="dropdownMenu1" type="button"
								class="btn btn-default dropdown-toggle" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="true">
								<span class="glyphicon glyphicon-sort-by-attributes" aria-hidden="true"></span>工具
							</button>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								<li><a href="#" onclick="open_modal_dialog_serverip_all()">审查所有案例</a></li>
								<li><a href="#" onclick="open_modal_dialog_serverip_one()">审查勾选案例</a></li>
								<li><a href="#" onclick="open_modal_dialg_tools()">导入案例数据</a></li>
								<li role="separator" class="divider"></li>
								<li><a href="#">Separated link</a></li>
							</ul>
						</div>
						<button id="anli_copy" type="button" class="btn btn-default" onclick="anli_copy()">
					        <span class="glyphicon glyphicon-retweet" aria-hidden="true"></span>复制案例
					    </button>
					</div>
				</div>
				<!-- 增加表格样式：style="table-layout: fixed"时，设置列宽才能生效 -->
				<table id="table_data"></table>
			</div>
			<!-- 数据表格-结束 -->
		</div>
	</div>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="modal_dialog_add_update" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">案例维护</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<input id="testid" type="hidden" name="testid" value="">
					<input id="modulename" type="hidden" name="modulename" value="">
					<div class="form-group">
			            <label for="anlitypeaa" class="col-sm-2 control-label">案例类型</label>
			            <div class="col-sm-4">
			            	<select id='anlitypeaa' name="anlitype" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
					<div class="form-group">
			            <label for="teamidaa" class="col-sm-2 control-label">团队名称</label>
			            <div class="col-sm-4">
			            	<select id='teamidaa' name="teamid" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
					<div class="form-group">
			            <label for="projectidaa" class="col-sm-2 control-label">项目名称</label>
			            <div class="col-sm-4">
			            	<select id='projectidaa' name="projectid" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
			            <label for="status" class="col-sm-2 control-label">案例状态</label>
			            <div class="col-sm-4">
			            	<select id='status' name="status" class="js-example-responsive" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
			            <label for="moduleid" class="col-sm-2 control-label">模块名称</label>
			            <div class="col-sm-4">
							<select id="moduleid"  name="moduleid" class="js-data-example-ajax" style="width:100%"></select>
						</div>
			        </div>
					<div class="form-group">
						<label for="testname" class="col-sm-2 control-label">案例名称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="testname"  name="testname">
						</div>
					</div>
					<div class="form-group">
						<label for="testno" class="col-sm-2 control-label">案例编号 :1-1(格式范例)</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="testno"  name="testno">
						</div>
					</div>
					<div class="form-group">
						<label for="testtext" class="col-sm-2 control-label">逻辑描述</label>
						<div class="col-sm-10">
							<textarea class="col-sm-12" rows="10" id="testtext" name="testtext"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="testin" class="col-sm-2 control-label">输入条件</label>
						<div class="col-sm-10">
							<textarea class="col-sm-12" rows="10" id="testin" name="testin"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="testout" class="col-sm-2 control-label">预期结果</label>
						<div class="col-sm-10">
							<textarea class="col-sm-12" rows="10" id="testout" name="testout"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="remark" class="col-sm-2 control-label">备注</label>
						<div class="col-sm-10">
							<textarea class="col-sm-12" rows="10" id="remark" name="remark"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="testout_response" class="col-sm-2 control-label">实际结果</label>
						<div class="col-sm-10">
							<textarea class="col-sm-12" rows="10" id="testout_response" name="testout_response" readonly></textarea>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="add_data()">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="update_data()">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="modal_dialog_serverip" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">选择审查地址</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<div class="row">
					<div class="col-sm-12" style="margin-bottom:15px">
						<div class="form-group">
				            <label for="jsontype" class="col-sm-3 control-label">JSON接口类型</label>
				            <div class="col-sm-4">
				            	<select id='jsontype' name="jsontype" class="js-example-responsive" style="width:100%"></select>
				            </div>
				        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12" style="margin-bottom:15px">
						<div class="form-group">
				            <label for="jsonversion" class="col-sm-3 control-label">JSON版本类型</label>
				            <div class="col-sm-4">
				            	<select id='jsonversion' name="jsonversion" class="js-example-responsive" style="width:100%"></select>
				            </div>
				        </div>
					</div>
				</div>
				<div class="row">
					<!-- 增加表格样式：style="table-layout: fixed"时，设置列宽才能生效 -->
					<table id="table_data" style="table-layout:fixed; "></table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add1" onclick="screen_all()">审查全部</button>
				<button type="button" class="btn btn-primary" id="btn_add2" onclick="screen_one()">审查勾选</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="modal_dialog_tools" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">工具箱</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<div class="row">
		    		<label for="teamid" class="col-sm-2 control-label" style="padding-top:7px">团队名称</label>
		            <div class="col-sm-4">
		            	<select id='teamid' name="teamid" class="js-data-example-ajax" style="width:100%"></select>
		            </div>
		    		<label for="projectid" class="col-sm-2 control-label" style="padding-top:7px">项目名称</label>
		            <div class="col-sm-4">
		            	<select id='projectid' name="projectid" class="js-data-example-ajax" style="width:100%"></select>
		            </div>
		    	</div>
		    	<div class="row">
					 <label for="_daoshuju" class="col-sm-2 control-label" style="padding-top:7px">数据范围</label>
		            <div class="col-sm-4">
		            	<select id='_daoshuju' name="_daoshuju" class="js-data-example-ajax" style="width:100%"></select>
		            </div>
				</div>
				<hr>
		    	<div class="row">
					<div class="col-sm-2">
						<button id="insert-data" type="button" class="btn btn-default" onclick="sqlserver_data()">导入数据</button>
					</div>
					<div class="col-sm-6" style="padding-top:7px">
						请选择导入目的地
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
</body>
</html>

