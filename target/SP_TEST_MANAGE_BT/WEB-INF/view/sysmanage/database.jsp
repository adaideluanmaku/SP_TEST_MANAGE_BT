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
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/sysmanage/database.js"></script>

</head>
<body style="background-color: #ffffff;">
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">

<div id="wrapper" >
	<div  class="gray-bg">
		<div id="header_path" class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				<ol class="breadcrumb">
					<li><a>home</a></li>
					<li><a>系统管理</a></li>
					<li class="active"><strong>数据库维护</strong></li>
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
				    	<div class="col-sm-5">
			                <label class="control-label col-sm-4" for="search_data" style="padding-top:7px">条件</label>
			                <div class="col-sm-8">
			                    <input type="text" class="form-control" id="search_data">
			                </div>
			    		</div>
			    		<div class="col-sm-3">
			                <button type="button" id="btn_query" class="btn btn-primary" onclick="table_search()">查询</button>
			    		</div>
				    </div>
				</div>  
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="database_open(1)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <button id="btn_add" type="button" class="btn btn-default" onclick="database_open(2)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
					    </button>
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="database_data(3)">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					</div>
				</div>
				<table id="dict_table" style="table-layout:fixed;"></table>
			</div>
			<!-- 数据表格-结束 -->
		</div>
	</div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="database_data_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新增机构</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<input type="hidden" id="databaseid" name="databaseid" value="">
					<div class="form-group">
			            <label for="databasetype" class="col-sm-4 control-label">数据库类型</label>
			            <div class="col-sm-4">
					        <select class="form-control m-b" name="databasetype" id="databasetype">
	                            <option value="MYSQL" selected="selected"><font style="vertical-align: inherit;">MYSQL</font></option>
	                            <option value="MSSQL"><font style="vertical-align: inherit;">MSSQL</font></option>
	                            <option value="ORACLE"><font style="vertical-align: inherit;">ORACLE</font></option>
	                        </select>
			            </div>
			        </div>
			        <div class="form-group">
						<label for="name" class="col-sm-4 control-label">名称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="name"  name="name">
						</div>
					</div>
					<div class="form-group">
						<label for="kettle_Database_two" class="col-sm-4 control-label">kettle数据库配置名称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="kettle_Database_two"  name="kettle_Database_two" value="">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-4">
						</div>
						<div class="col-sm-6">
							ETL工具写：pass_sqlserver
						</div>
					</div>
			        <div class="form-group">
						<label for="databasename" class="col-sm-4 control-label">databasename</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="databasename"  name="databasename">
						</div>
					</div>
					<div class="form-group">
						<label for="ip" class="col-sm-4 control-label">ip</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="ip"  name="ip">
						</div>
					</div>
					<div class="form-group">
						<label for="username" class="col-sm-4 control-label">username</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username"  name="username">
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-sm-4 control-label">password</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="password"  name="password">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="database_data(1)">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="database_data(2)">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

</body>
</html>

