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


<!-- MY JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/pass/pass_json.js"></script>

</head>
<body style="background-color: #ffffff;">
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">
<input id="testid" type="hidden" value="${testid}">
<input id="testname" type="hidden" value="${testname}">

<div id="wrapper" >
	<div class="panel panel-default">
	    <div class="panel-heading">输入串</div>
	    <div class="panel-body">
	    	<div class="row">
	    		<div class="col-sm-12" >
	    			<textarea class="col-sm-12" rows="10" id="gatherbaseinfo" name="gatherbaseinfo" 
	    			placeholder="请输入JSON串">${fn:escapeXml(testin)}</textarea>
		        </div>
	    	</div>
	    </div>
	</div> 
	<div class="row">
		<div class="col-sm-6">
			<div class="panel panel-default">
			    <div class="panel-heading">预期审查结果</div>
			    <div class="panel-body">
			    	<div class="row" style="margin-bottom:15px" id="_win">
			    		<div class="col-sm-2">
				            <button type="button" class="btn btn-primary" onclick="screen_win()">审查</button>
				        </div>
				        <div class="col-sm-4">
				            <select id="serveradress_a" name="serveradress_a" class="js-example-responsive" style="width:100%"></select>
				        </div>
			    	</div>
			    	<div class="row">
			    		<div class="col-sm-12" >
			    			<textarea class="col-sm-12" rows="10" id="testout" name="gatherresult" 
			    			placeholder="请输入JSON串">${fn:escapeXml(testout)}</textarea>
				        </div>
			    	</div>
			    </div>
			</div> 
		</div>
		<div class="col-sm-6">
			<div class="panel panel-default">
			    <div class="panel-heading">实际审查结果</div>
			    <div class="panel-body">
			    	<div class="row" style="margin-bottom:15px" id="_java">
			    		<div class="col-sm-2">
				            <button type="button" class="btn btn-primary" onclick="screen_java()">审查</button>
				        </div>
				        <div class="col-sm-4">
				            <select id="serveradress_b" name="serveradress_b" class="js-example-responsive" style="width:100%"></select>
				        </div>
			    	</div>
			    	<div class="row">
			    		<div class="col-sm-12" >
			    			<textarea class="col-sm-12" rows="10" id="testout_response" name="gatherresult_java" 
			    			placeholder="请输入JSON串">${fn:escapeXml(testout_response)}</textarea>
				        </div>
			    	</div>
			    </div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
    		<div class="panel panel-default">
			    <div class="panel-heading">操作</div>
			    <div class="panel-body">
			    	<div class="row">
			    		<div class="col-sm-3">
		    				<div class="form-group">
					            <label for="jsontype" class="col-sm-5 control-label" style="padding-top:7px">JSON接口类型</label>
					            <div class="col-sm-4">
					            	<select id='jsontype' name="jsontype" class="js-example-responsive" style="width:100%"></select>
					            </div>
					        </div>
						</div>
						<div class="col-sm-3">
		    				<div class="form-group">
					            <label for="jsonversion" class="col-sm-5 control-label" style="padding-top:7px">JSON版本类型</label>
					            <div class="col-sm-5">
					            	<select id='jsonversion' name="jsonversion" class="js-example-responsive" style="width:100%"></select>
					            </div>
					        </div>
						</div>
						<div class="col-sm-4">
					    	<button type="button" id="duibi" class="btn btn-primary" onclick="open_modal_dialog()">对比json</button>
						</div>
			    	</div>
			    </div>
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
				<h4 class="modal-title">json对比</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<div id="jsonerr"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

</body>
</html>

