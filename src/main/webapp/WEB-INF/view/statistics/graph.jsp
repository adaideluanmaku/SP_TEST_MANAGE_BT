<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>CH+ | graph</title>
<!-- SYS CSS -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/css/animate.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/css/style.css" rel="stylesheet">
<!-- SYS JS -->
<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}/bootstrap_home/js/jquery-2.1.1.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Flot -->
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/flot/jquery.flot.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/flot/jquery.flot.pie.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/flot/jquery.flot.time.js"></script>
<!-- Custom and plugin javascript -->
<script src="${pageContext.request.contextPath}/bootstrap_home/js/inspinia.js"></script>
<!-- Flot demo data -->
<script src="${pageContext.request.contextPath}/bootstrap_home/js/demo/flot-demo.js"></script>

<!-- MY JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/statistics/graph.js"></script>

</head>
<body>
	<div id="wrapper" >
		<div  class="gray-bg">
			<div class="row wrapper border-bottom white-bg page-heading">
				<div class="col-lg-10">
					<h2>Flot Charts</h2>
					<ol class="breadcrumb">
						<li><a href="index.html">Home</a></li>
						<li><a>Graphs</a></li>
						<li class="active"><strong>Flot Charts</strong></li>
					</ol>
				</div>
				<div class="col-lg-2"></div>
			</div>
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="row">
					<div class="col-sm-6">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>
									柱状图  
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="dropdown-toggle" data-toggle="dropdown"
										href="graph_flot.html#"> <i class="fa fa-wrench"></i>
									</a>
									<ul class="dropdown-menu dropdown-user">
										<li><a href="graph_flot.html#">Config option 1</a></li>
										<li><a href="graph_flot.html#">Config option 2</a></li>
									</ul>
									<a class="close-link"> <i class="fa fa-times"></i>
									</a>
								</div>
							</div>
							<div class="ibox-content">
								<div class="flot-chart">
									<div class="flot-chart-content" id="flot-bar-chart"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>线型图</h5>
								<div class="ibox-tools">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="dropdown-toggle" data-toggle="dropdown"
										href="graph_flot.html#"> <i class="fa fa-wrench"></i>
									</a>
									<ul class="dropdown-menu dropdown-user">
										<li><a href="graph_flot.html#">Config option 1</a></li>
										<li><a href="graph_flot.html#">Config option 2</a></li>
									</ul>
									<a class="close-link"> <i class="fa fa-times"></i>
									</a>
								</div>
							</div>
							<div class="ibox-content">

								<div class="flot-chart">
									<div class="flot-chart-content" id="flot-line-chart"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>饼状图</h5>
								<div class="ibox-tools">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="dropdown-toggle" data-toggle="dropdown"
										href="graph_flot.html#"> <i class="fa fa-wrench"></i>
									</a>
									<ul class="dropdown-menu dropdown-user">
										<li><a href="graph_flot.html#">Config option 1</a></li>
										<li><a href="graph_flot.html#">Config option 2</a></li>
									</ul>
									<a class="close-link"> <i class="fa fa-times"></i>
									</a>
								</div>
							</div>
							<div class="ibox-content">
								<div class="flot-chart">
									<div class="flot-chart-pie-content" id="flot-pie-chart"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>实时监控</h5>
								<div class="ibox-tools">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="dropdown-toggle" data-toggle="dropdown"
										href="graph_flot.html#"> <i class="fa fa-wrench"></i>
									</a>
									<ul class="dropdown-menu dropdown-user">
										<li><a href="graph_flot.html#">Config option 1</a></li>
										<li><a href="graph_flot.html#">Config option 2</a></li>
									</ul>
									<a class="close-link"> <i class="fa fa-times"></i>
									</a>
								</div>
							</div>
							<div class="ibox-content">

								<div class="flot-chart">
									<div class="flot-chart-content" id="flot-line-chart-moving"></div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>历史统计</h5>
								<div class="ibox-tools">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="dropdown-toggle" data-toggle="dropdown"
										href="graph_flot.html#"> <i class="fa fa-wrench"></i>
									</a>
									<ul class="dropdown-menu dropdown-user">
										<li><a href="graph_flot.html#">Config option 1</a></li>
										<li><a href="graph_flot.html#">Config option 2</a></li>
									</ul>
									<a class="close-link"> <i class="fa fa-times"></i>
									</a>
								</div>
							</div>
							<div class="ibox-content">
								<div class="flot-chart">
									<div class="flot-chart-content" id="flot-line-chart-multi"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>
</body>
</html>

