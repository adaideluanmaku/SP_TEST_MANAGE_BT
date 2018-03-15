<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>CH+ | HOME</title>
<!-- SYS CSS -->
<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/font-awesome/css/font-awesome.css" rel="stylesheet">
<!-- Gritter -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/animate.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/css/style.css" rel="stylesheet">

<!-- SYS JS -->
<!-- 引入 Bootstrap -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/js/jquery-3.2.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${pageContext.request.contextPath}/bootstrap_home/js/inspinia.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/pace/pace.min.js"></script>

<!-- MY JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/homepage.js"></script>

<title>TEST_MANAGE</title>
</head>
<body>
	<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">
	<input id="userid" type="hidden" value="${userid}">
	<input id="loginname" type="hidden" value="${loginname}">
	
	<nav class="navbar-default navbar-static-side" >
		<ul class="nav metismenu" id="side-menu">
			<li class="nav-header">
				<div class="dropdown profile-element">
					<span><img alt="image" class="img-circle" src="${pageContext.request.contextPath}/chimg/hxj-small.png" /></span> 
					<a data-toggle="dropdown" class="dropdown-toggle" href="#"> 
						<span class="clear"> 
							<span class="block m-t-xs"> <strong class="font-bold">啊哦额</strong></span> 
							<span class="text-muted text-xs block">快捷操作<b class="caret"></b></span>
						</span>
					</a>
					<ul class="dropdown-menu animated fadeInRight m-t-xs">
						<li><a href="javascript:alert(1)">Profile</a></li>
						<li><a href="javascript:alert(1)">Contacts</a></li>
						<li><a href="javascript:alert(1)">Mailbox</a></li>
						<li class="divider"></li>
						<li><a href="login.html">Logout</a></li>
					</ul>
				</div>
				<div class="logo-element">CH+</div>
			</li>
			<li class="active"><a href=""> <i class="fa fa-th-large"></i>
					<span class="nav-label">统计管理</span> <span class="fa arrow"></span>
			</a>
				<ul class="nav nav-second-level">
					<li class="active"><a href="javascript:void(0);" onclick="home_graph()">总览</a></li>
				</ul></li>
			<li><a href="index.html#"> <i class="fa fa-bar-chart-o"></i><span
					class="nav-label">PASS测试管理</span> <span class="fa arrow"></span></a>
				<ul class="nav nav-second-level collapse">
					<li><a href="javascript:void(0);" onclick="pass_graph()">PASS统计</a></li>
					<li><a href="javascript:void(0);" onclick="pass_team()">团队管理</a></li>
					<li><a href="javascript:void(0);" onclick="pass_project()">项目管理</a></li>
					<li><a href="javascript:void(0);" onclick="pass_testmng()">案例管理</a></li>
				</ul></li>
			<li><a href="index.html#">
					<i class="fa fa-bar-chart-o"></i>
					<span class="nav-label">PA测试管理</span> <span class="fa arrow"></span>
				</a>
				<ul class="nav nav-second-level collapse">
					<li><a href="javascript:void(0);" onclick="pa_graph()">PA统计</a></li>
					<li><a href="javascript:void(0);" onclick="pa_team()">团队管理</a></li>
					<li><a href="javascript:void(0);" onclick="pa_project()">项目管理</a></li>
					<li><a href="javascript:void(0);" onclick="pa_testmng()">案例管理</a></li>
				</ul></li>
			<li><a href="index.html#">
					<i class="fa fa-bar-chart-o"></i>
					<span class="nav-label">用药研究</span> <span class="fa arrow"></span>
				</a>
				<ul class="nav nav-second-level collapse">
					<li><a href="javascript:void(0);" onclick="prescription()">处方管理</a></li>
				</ul></li>
			<li><a href="index.html#"> <i class="fa fa-bar-chart-o"></i><span
					class="nav-label">政府项目测试管理</span> <span class="fa arrow"></span></a>
				<ul class="nav nav-second-level collapse">
					<li><a href="javascript:void(0);" onclick="zfxm_graph()">政府项目统计</a></li>
					<li><a href="javascript:alert(1)">团队管理</a></li>
					<li><a href="javascript:alert(1)">项目管理</a></li>
					<li><a href="javascript:alert(1)">案例管理</a></li>
				</ul></li>
			<li><a href="index.html#"> <i class="fa fa-bar-chart-o"></i><span
					class="nav-label">学习记录</span> <span class="fa arrow"></span></a>
				<ul class="nav nav-second-level collapse">
					<li><a href="javascript:void(0);" onclick="learn_graph()">学习统计</a></li>
					<li><a href="javascript:alert(1)">学习分类</a></li>
					<li><a href="javascript:alert(1)">学习笔记</a></li>
					<li><a href="javascript:alert(1)">待续</a></li>
				</ul></li>
			<li><a href="index.html#"> <i class="fa fa-bar-chart-o"></i><span
					class="nav-label">工作计划</span> <span class="fa arrow"></span></a>
				<ul class="nav nav-second-level collapse">
					<li><a href="javascript:void(0);" onclick="works_graph()">工作统计</a></li>
					<li><a href="javascript:alert(1)">计划管理</a></li>
					<li><a href="javascript:alert(1)">待续</a></li>
					<li><a href="javascript:alert(1)">待续</a></li>
				</ul></li>
			<li><a href="index.html#"> <i class="fa fa-bar-chart-o"></i><span
					class="nav-label">账户管理</span> <span class="fa arrow"></span></a>
				<ul class="nav nav-second-level collapse">
					<li><a href="javascript:alert(1)">用户信息</a></li>
					<li><a href="javascript:alert(1)">待续</a></li>
					<li><a href="javascript:alert(1)">待续</a></li>
				</ul></li>
			<li><a href="index.html#"> <i class="fa fa-bar-chart-o"></i><span
					class="nav-label">使用工具集</span> <span class="fa arrow"></span></a>
				<ul class="nav nav-second-level collapse">
					<li><a href="javascript:alert(1)">工具1</a></li>
					<li><a href="javascript:alert(1)">工具2</a></li>
					<li><a href="javascript:alert(1)">待续</a></li>
				</ul></li>
		</ul>
	</nav>
	
	<div id="page-wrapper" class="gray-bg">
		<div class="row border-bottom">
			<nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
				<div class="navbar-header">
					<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
					<form role="search" class="navbar-form-custom" action="javascript:void(0)">
						<div class="form-group">
							<input type="text" placeholder="Search for something..."
								class="form-control" name="top-search" id="top-search">
						</div>
					</form>
				</div>
				<ul class="nav navbar-top-links navbar-right">
					<li><span class="m-r-sm text-muted welcome-message">Welcome to INSPINIA+ Admin Theme.</span></li>
					<li><a href="${pageContext.request.contextPath}/login/logout"> <i class="fa fa-sign-out"></i> Log out </a></li>
				</ul>
			</nav>
		</div>

		<div class="row  border-bottom white-bg dashboard-header">
			<iframe id="iframe_box" src="${pageContext.request.contextPath}/homepage/home_graph" frameborder="0" scrolling="yes"
				style="width: 100%; min-height: 690px;"></iframe>
		</div>
		<div class="footer navbar-fixed-bottom">
			<div class="pull-right"> 10GB of <strong>250GB</strong> Free. </div>
			<div>
				<strong>Copyright</strong> Example Company &copy; 2014-2015
			</div>
		</div>
	</div>
	
</body>
</html>