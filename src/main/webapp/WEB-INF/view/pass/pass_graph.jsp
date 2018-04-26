<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>CH+ | PA</title>
<!-- SYS CSS -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/css/style.css" rel="stylesheet">

<!-- SYS JS -->
<!-- 引入 Bootstrap -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/js/jquery-3.2.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<!-- echarts -->
<script type="text/javascript" src="${pageContext.request.contextPath}/baidu_echarts/echarts.js"></script>

<!-- MY JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/pass/pass_graph.js"></script>
</head>
<body style="background-color: #f3f3f4">
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">

<div id="wrapper" >
	<div  class="gray-bg">
		<div class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				<ol class="breadcrumb">
					<li><a>home</a></li>
					<li><a>PASS测试管理</a></li>
					<li class="active"><strong>PASS统计</strong></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
		</div>
		<div class="wrapper wrapper-content">
			<div class="row">
				<div class="col-sm-6">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>
								柱状图  
							</h5>
						</div>
						<div class="ibox-content">
							<div id="echarts" style="width: 500px;height:400px;"></div>	
						</div>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>
								柱状图  
							</h5>
						</div>
						<div class="ibox-content">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>
								柱状图  
							</h5>
						</div>
						<div class="ibox-content">
							<div id="echarts1" style="width: 800px;height:400px;"></div>	
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>

