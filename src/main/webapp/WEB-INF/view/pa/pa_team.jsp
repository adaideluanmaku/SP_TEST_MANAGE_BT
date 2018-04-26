<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>INSPINIA | team</title>
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


<!-- MY JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/pa/pa_team.js"></script>

</head>
<body style="background-color: #ffffff;">
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">

<div id="wrapper" >
	<div  class="gray-bg">
		<div class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				<ol class="breadcrumb">
					<li><a>home</a></li>
					<li><a>PA测试管理</a></li>
					<li class="active"><strong>团队管理</strong></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
		</div>
		<div class="animated fadeInRight">
			<div id="table_div">
				<!-- 列表表格-开始制作 -->
				<div class="panel-body" style="padding-bottom:0px;">
					<div class="panel panel-default">
					    <div class="panel-heading">查询条件</div>
					    <div class="panel-body">
			                <label class="control-label col-sm-1" for="txt_search_departmentname" style="padding-top:7px">团队名称</label>
			                <div class="col-sm-3">
			                    <input type="text" class="form-control" id="teamname">
			                </div>
			                <div class="col-sm-2" style="text-align: left;">
			                    <button type="button" id="btn_query" class="btn btn-primary" onclick="table_search()">查询</button>
			                </div>
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
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>工具
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
									<li><a href="#">Action</a></li>
									<li><a href="#">Another action</a></li>
									<li><a href="#">Something else here</a></li>
									<li role="separator" class="divider"></li>
									<li><a href="#">Separated link</a></li>
								</ul>
							</div>
						</div>
					</div>
					<!-- 增加表格样式：style="table-layout: fixed"时，设置列宽才能生效 -->
					<table id="table_data" style="table-layout:fixed; "></table>
				</div>
				<!-- 数据表格-结束 -->
			</div>
		</div>
	</div>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="modal_dialog" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">团队维护</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<input type="hidden" id="teamid" name="teamid" value="">
					<div class="form-group">
						<label for="teamname" class="col-sm-2 control-label">团队名称</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="teamname"  name="teamname">
						</div>
					</div>
					<div class="form-group">
						<label for="remark" class="col-sm-2 control-label">备注</label>
						<div class="col-sm-10">
							<textarea class="col-sm-12" rows="10" id="remark" name="remark"></textarea>
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

</body>
</html>

