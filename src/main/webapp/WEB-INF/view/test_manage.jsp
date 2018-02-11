<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>CH_TMANAGE | Dashboard</title>

<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css"
	rel="stylesheet">

<!-- Toastr style -->
<link
	href="${pageContext.request.contextPath}/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">

<!-- Gritter -->
<link
	href="${pageContext.request.contextPath}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">

<link href="${pageContext.request.contextPath}/css/animate.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet">

<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script
	src="${pageContext.request.contextPath}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<!-- Flot -->
<script
	src="${pageContext.request.contextPath}/js/plugins/flot/jquery.flot.js"></script>
<script
	src="${pageContext.request.contextPath}/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/plugins/flot/jquery.flot.spline.js"></script>
<script
	src="${pageContext.request.contextPath}/js/plugins/flot/jquery.flot.resize.js"></script>
<script
	src="${pageContext.request.contextPath}/js/plugins/flot/jquery.flot.pie.js"></script>

<!-- Peity -->
<script
	src="${pageContext.request.contextPath}/js/plugins/peity/jquery.peity.min.js"></script>
<script src="${pageContext.request.contextPath}/js/demo/peity-demo.js"></script>

<!-- Custom and plugin javascript -->
<script src="${pageContext.request.contextPath}/js/inspinia.js"></script>
<script
	src="${pageContext.request.contextPath}/js/plugins/pace/pace.min.js"></script>

<!-- jQuery UI -->
<script
	src="${pageContext.request.contextPath}/js/plugins/jquery-ui/jquery-ui.min.js"></script>

<!-- GITTER -->
<script
	src="${pageContext.request.contextPath}/js/plugins/gritter/jquery.gritter.min.js"></script>

<!-- Sparkline -->
<script
	src="${pageContext.request.contextPath}/js/plugins/sparkline/jquery.sparkline.min.js"></script>

<!-- Sparkline demo data  -->
<script
	src="${pageContext.request.contextPath}/js/demo/sparkline-demo.js"></script>

<!-- ChartJS-->
<script
	src="${pageContext.request.contextPath}/js/plugins/chartJs/Chart.min.js"></script>

<!-- Toastr -->
<script
	src="${pageContext.request.contextPath}/js/plugins/toastr/toastr.min.js"></script>

<!-- CH JS -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/chjs/test_manage.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/chjs/websocket/websocket-connect.js"></script>
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/themes/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/chcss/test_manage.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/chjs/test_manage.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/chcss/websocket/websocket-connect.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/chjs/websocket/websocket-connect.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/chcss/userorders/userorders.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/chjs/userorders/userorders.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/chjs/loading/loading.js"></script> --%>

<script>
	$(document).ready(
			function() {
				setTimeout(function() {
					toastr.options = {
						closeButton : true,
						progressBar : true,
						showMethod : 'slideDown',
						timeOut : 4000
					};
					toastr.success('Responsive Admin Theme',
							'Welcome to INSPINIA');

				}, 1300);

				var data1 = [ [ 0, 4 ], [ 1, 8 ], [ 2, 5 ], [ 3, 10 ],
						[ 4, 4 ], [ 5, 16 ], [ 6, 5 ], [ 7, 11 ], [ 8, 6 ],
						[ 9, 11 ], [ 10, 30 ], [ 11, 10 ], [ 12, 13 ],
						[ 13, 4 ], [ 14, 3 ], [ 15, 3 ], [ 16, 6 ] ];
				var data2 = [ [ 0, 1 ], [ 1, 0 ], [ 2, 2 ], [ 3, 0 ], [ 4, 1 ],
						[ 5, 3 ], [ 6, 1 ], [ 7, 5 ], [ 8, 2 ], [ 9, 3 ],
						[ 10, 2 ], [ 11, 1 ], [ 12, 0 ], [ 13, 2 ], [ 14, 8 ],
						[ 15, 0 ], [ 16, 0 ] ];
				$("#flot-dashboard-chart").length
						&& $.plot($("#flot-dashboard-chart"), [ data1, data2 ],
								{
									series : {
										lines : {
											show : false,
											fill : true
										},
										splines : {
											show : true,
											tension : 0.4,
											lineWidth : 1,
											fill : 0.4
										},
										points : {
											radius : 0,
											show : true
										},
										shadowSize : 2
									},
									grid : {
										hoverable : true,
										clickable : true,
										tickColor : "#d5d5d5",
										borderWidth : 1,
										color : '#d5d5d5'
									},
									colors : [ "#1ab394", "#1C84C6" ],
									xaxis : {},
									yaxis : {
										ticks : 4
									},
									tooltip : false
								});

				var doughnutData = [ {
					value : 300,
					color : "#a3e1d4",
					highlight : "#1ab394",
					label : "App"
				}, {
					value : 50,
					color : "#dedede",
					highlight : "#1ab394",
					label : "Software"
				}, {
					value : 100,
					color : "#A4CEE8",
					highlight : "#1ab394",
					label : "Laptop"
				} ];

				var doughnutOptions = {
					segmentShowStroke : true,
					segmentStrokeColor : "#fff",
					segmentStrokeWidth : 2,
					percentageInnerCutout : 45, // This is 0 for Pie charts
					animationSteps : 100,
					animationEasing : "easeOutBounce",
					animateRotate : true,
					animateScale : false
				};

				/* var ctx = document.getElementById("doughnutChart").getContext(
						"2d");
				var DoughnutChart = new Chart(ctx).Doughnut(doughnutData,
						doughnutOptions); */

				var polarData = [ {
					value : 300,
					color : "#a3e1d4",
					highlight : "#1ab394",
					label : "App"
				}, {
					value : 140,
					color : "#dedede",
					highlight : "#1ab394",
					label : "Software"
				}, {
					value : 200,
					color : "#A4CEE8",
					highlight : "#1ab394",
					label : "Laptop"
				} ];

				var polarOptions = {
					scaleShowLabelBackdrop : true,
					scaleBackdropColor : "rgba(255,255,255,0.75)",
					scaleBeginAtZero : true,
					scaleBackdropPaddingY : 1,
					scaleBackdropPaddingX : 1,
					scaleShowLine : true,
					segmentShowStroke : true,
					segmentStrokeColor : "#fff",
					segmentStrokeWidth : 2,
					animationSteps : 100,
					animationEasing : "easeOutBounce",
					animateRotate : true,
					animateScale : false
				};
				/* var ctx = document.getElementById("polarChart")
						.getContext("2d");
				var Polarchart = new Chart(ctx).PolarArea(polarData,
						polarOptions); */

			});
</script>
<script>
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o), m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, 'script', '//www.google-analytics.com/analytics.js',
			'ga');

	ga('create', 'UA-4625583-2', 'webapplayers.com');
	ga('send', 'pageview');
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>TEST_MANAGE</title>
</head>
<body>
	<input id="addurl" type="hidden"
		value="${pageContext.request.contextPath}">
	<input id="userid" type="hidden" value="${userid}">
	<input id="loginname" type="hidden" value="${loginname}">

	<div id="wrapper">
		<nav class="navbar-default navbar-static-side" role="navigation">
		<div class="sidebar-collapse">
			<ul class="nav metismenu" id="side-menu">
				<li class="nav-header">
					<div class="dropdown profile-element">
						<span> <img alt="image" class="img-circle"
							src="${pageContext.request.contextPath}/chimg/hxj-small.png" />
						</span> <a data-toggle="dropdown" class="dropdown-toggle"
							href="index.html#"> <span class="clear"> <span
								class="block m-t-xs"> <strong class="font-bold">David
										Williams</strong>
							</span> <span class="text-muted text-xs block">Art Director <b
									class="caret"></b></span>
						</span>
						</a>
						<ul class="dropdown-menu animated fadeInRight m-t-xs">
							<li><a href="profile.html">Profile</a></li>
							<li><a href="contacts.html">Contacts</a></li>
							<li><a href="mailbox.html">Mailbox</a></li>
							<li class="divider"></li>
							<li><a href="login.html">Logout</a></li>
						</ul>
					</div>
					<div class="logo-element">CH+</div>
				</li>
				<li class="active">
					<a href="">
						<i class="fa fa-th-large"></i><span class="nav-label">信息采集</span>
						<span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li class="active"><a href="javascript:void(0);" onclick="testmess()">案例信息</a></li>
						<li><a href="javascript:void(0);" onclick="learnmess()">学习笔记</a></li>
						<li><a href="${pageContext.request.contextPath}/login/denglu">NEWS</a></li>
					</ul></li>
				<li>
                     <a href="index.html#">
                     <i class="fa fa-bar-chart-o"></i><span class="nav-label">项目管理</span>
                     <span class="fa arrow"></span></a>
                     <ul class="nav nav-second-level collapse">
                         <li><a href="graph_flot.html">团队维护</a></li>
                         <li><a href="graph_morris.html">项目维护</a></li>
                         <li><a href="graph_rickshaw.html">案例维护</a></li>
                     </ul>
                </li>
				<li>
                     <a href="index.html#">
                     <i class="fa fa-bar-chart-o"></i><span class="nav-label">学习记录</span>
                     <span class="fa arrow"></span></a>
                     <ul class="nav nav-second-level collapse">
                         <li><a href="graph_flot.html">学习分类</a></li>
                         <li><a href="graph_morris.html">学习笔记</a></li>
                         <li><a href="graph_rickshaw.html">待续</a></li>
                     </ul>
                </li>
                <li>
                     <a href="index.html#">
                     <i class="fa fa-bar-chart-o"></i><span class="nav-label">工作计划</span>
                     <span class="fa arrow"></span></a>
                     <ul class="nav nav-second-level collapse">
                         <li><a href="graph_flot.html">计划管理</a></li>
                         <li><a href="graph_morris.html">待续</a></li>
                         <li><a href="graph_rickshaw.html">待续</a></li>
                     </ul>
                </li>
                <li>
                     <a href="index.html#">
                     <i class="fa fa-bar-chart-o"></i><span class="nav-label">账户管理</span>
                     <span class="fa arrow"></span></a>
                     <ul class="nav nav-second-level collapse">
                         <li><a href="graph_flot.html">用户信息</a></li>
                         <li><a href="graph_morris.html">待续</a></li>
                         <li><a href="graph_rickshaw.html">待续</a></li>
                     </ul>
                </li>
			</ul>

		</div>
		</nav>

		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0">
				<div class="navbar-header">
					<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
						href="${pageContext.request.contextPath}/login/denglu#"><i
						class="fa fa-bars"></i> </a>
					<form role="search" class="navbar-form-custom"
						action="search_results.html">
						<div class="form-group">
							<input type="text" placeholder="Search for something..."
								class="form-control" name="top-search" id="top-search">
						</div>
					</form>
				</div>
				<ul class="nav navbar-top-links navbar-right">
					<li><span class="m-r-sm text-muted welcome-message">Welcome
							to INSPINIA+ Admin Theme.</span></li>
					<li class="dropdown"><a class="dropdown-toggle count-info"
						data-toggle="dropdown" href="index.html#"> <i
							class="fa fa-envelope"></i> <span class="label label-warning">16</span>
					</a>
						<ul class="dropdown-menu dropdown-messages">
							<li>
								<div class="dropdown-messages-box">
									<a href="profile.html" class="pull-left"> <img alt="image"
										class="img-circle"
										src="${pageContext.request.contextPath}/img/a7.jpg">
									</a>
									<div class="media-body">
										<small class="pull-right">46h ago</small> <strong>Mike
											Loreipsum</strong> started following <strong>Monica Smith</strong>. <br>
										<small class="text-muted">3 days ago at 7:58 pm -
											10.06.2014</small>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<li>
								<div class="dropdown-messages-box">
									<a href="profile.html" class="pull-left"> <img alt="image"
										class="img-circle"
										src="${pageContext.request.contextPath}/img/a4.jpg">
									</a>
									<div class="media-body ">
										<small class="pull-right text-navy">5h ago</small> <strong>Chris
											Johnatan Overtunk</strong> started following <strong>Monica
											Smith</strong>. <br> <small class="text-muted">Yesterday
											1:21 pm - 11.06.2014</small>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<li>
								<div class="dropdown-messages-box">
									<a href="profile.html" class="pull-left"> <img alt="image"
										class="img-circle"
										src="${pageContext.request.contextPath}/img/profile.jpg">
									</a>
									<div class="media-body ">
										<small class="pull-right">23h ago</small> <strong>Monica
											Smith</strong> love <strong>Kim Smith</strong>. <br> <small
											class="text-muted">2 days ago at 2:30 am - 11.06.2014</small>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<li>
								<div class="text-center link-block">
									<a href="mailbox.html"> <i class="fa fa-envelope"></i> <strong>Read
											All Messages</strong>
									</a>
								</div>
							</li>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle count-info"
						data-toggle="dropdown" href="index.html#"> <i
							class="fa fa-bell"></i> <span class="label label-primary">8</span>
					</a>
						<ul class="dropdown-menu dropdown-alerts">
							<li><a href="mailbox.html">
									<div>
										<i class="fa fa-envelope fa-fw"></i> You have 16 messages <span
											class="pull-right text-muted small">4 minutes ago</span>
									</div>
							</a></li>
							<li class="divider"></li>
							<li><a href="profile.html">
									<div>
										<i class="fa fa-twitter fa-fw"></i> 3 New Followers <span
											class="pull-right text-muted small">12 minutes ago</span>
									</div>
							</a></li>
							<li class="divider"></li>
							<li><a href="grid_options.html">
									<div>
										<i class="fa fa-upload fa-fw"></i> Server Rebooted <span
											class="pull-right text-muted small">4 minutes ago</span>
									</div>
							</a></li>
							<li class="divider"></li>
							<li>
								<div class="text-center link-block">
									<a href="notifications.html"> <strong>See All
											Alerts</strong> <i class="fa fa-angle-right"></i>
									</a>
								</div>
							</li>
						</ul></li>


					<li><a href="${pageContext.request.contextPath}/login/logout"> <i class="fa fa-sign-out"></i>
							Log out
					</a></li>
					<li><a class="right-sidebar-toggle"> <i
							class="fa fa-tasks"></i>
					</a></li>
				</ul>

				</nav>
			</div>

			<div class="row  border-bottom white-bg dashboard-header">
                 <div class="col-sm-12">
                 	<iframe id="iframe_box"
					src="${pageContext.request.contextPath}/log/log" frameborder="0"
					scrolling="no" class="iframe_box" style="width:100%" ></iframe>
                 </div>
            </div>
		</div>
		
		<div class="small-chat-box fadeInRight animated">
			<div class="heading" draggable="true">
				<small class="chat-date pull-right"> 02.19.2015 </small> Small chat
			</div>

			<div class="content">

				<div class="left">
					<div class="author-name">
						Monica Jackson <small class="chat-date"> 10:02 am </small>
					</div>
					<div class="chat-message active">Lorem Ipsum is simply dummy
						text input.</div>

				</div>
				<div class="right">
					<div class="author-name">
						Mick Smith <small class="chat-date"> 11:24 am </small>
					</div>
					<div class="chat-message">Lorem Ipsum is simpl.</div>
				</div>
				<div class="left">
					<div class="author-name">
						Alice Novak <small class="chat-date"> 08:45 pm </small>
					</div>
					<div class="chat-message active">Check this stock char.</div>
				</div>
				<div class="right">
					<div class="author-name">
						Anna Lamson <small class="chat-date"> 11:24 am </small>
					</div>
					<div class="chat-message">The standard chunk of Lorem Ipsum</div>
				</div>
				<div class="left">
					<div class="author-name">
						Mick Lane <small class="chat-date"> 08:45 pm </small>
					</div>
					<div class="chat-message active">I belive that. Lorem Ipsum
						is simply dummy text.</div>
				</div>
			</div>
		</div>
		<div id="small-chat">
			<span class="badge badge-warning pull-right">5</span> <a
				class="open-small-chat"> <i class="fa fa-comments"></i>
			</a>
		</div>
		<div id="right-sidebar">
			<div class="sidebar-container">
				<ul class="nav nav-tabs navs-3">
					<li class="active"><a data-toggle="tab"
						href="index.html#tab-1"> Notes </a></li>
					<li><a data-toggle="tab" href="index.html#tab-2"> Projects
					</a></li>
					<li class=""><a data-toggle="tab" href="index.html#tab-3">
							<i class="fa fa-gear"></i>
					</a></li>
				</ul>

				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> Latest Notes
							</h3>
							<small><i class="fa fa-tim"></i> You have 10 new message.</small>
						</div>

						<div>

							<div class="sidebar-message">
								<a href="index.html#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="${pageContext.request.contextPath}/img/a1.jpg">

										<div class="m-t-xs">
											<i class="fa fa-star text-warning"></i> <i
												class="fa fa-star text-warning"></i>
										</div>
									</div>
									<div class="media-body">

										There are many variations of passages of Lorem Ipsum
										available. <br> <small class="text-muted">Today
											4:21 pm</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="index.html#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="${pageContext.request.contextPath}/img/a2.jpg">
									</div>
									<div class="media-body">
										The point of using Lorem Ipsum is that it has a more-or-less
										normal. <br> <small class="text-muted">Yesterday
											2:45 pm</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="index.html#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="${pageContext.request.contextPath}/img/a3.jpg">

										<div class="m-t-xs">
											<i class="fa fa-star text-warning"></i> <i
												class="fa fa-star text-warning"></i> <i
												class="fa fa-star text-warning"></i>
										</div>
									</div>
									<div class="media-body">
										Mevolved over the years, sometimes by accident, sometimes on
										purpose (injected humour and the like). <br> <small
											class="text-muted">Yesterday 1:10 pm</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="index.html#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="${pageContext.request.contextPath}/img/a4.jpg">
									</div>

									<div class="media-body">
										Lorem Ipsum, you need to be sure there isn't anything
										embarrassing hidden in the <br> <small class="text-muted">Monday
											8:37 pm</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="index.html#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="${pageContext.request.contextPath}/img/a8.jpg">
									</div>
									<div class="media-body">

										All the Lorem Ipsum generators on the Internet tend to repeat.
										<br> <small class="text-muted">Today 4:21 pm</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="index.html#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="${pageContext.request.contextPath}/img/a7.jpg">
									</div>
									<div class="media-body">
										Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor
										sit amet..", comes from a line in section 1.10.32. <br> <small
											class="text-muted">Yesterday 2:45 pm</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="index.html#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="${pageContext.request.contextPath}/img/a3.jpg">

										<div class="m-t-xs">
											<i class="fa fa-star text-warning"></i> <i
												class="fa fa-star text-warning"></i> <i
												class="fa fa-star text-warning"></i>
										</div>
									</div>
									<div class="media-body">
										The standard chunk of Lorem Ipsum used since the 1500s is
										reproduced below. <br> <small class="text-muted">Yesterday
											1:10 pm</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="index.html#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="${pageContext.request.contextPath}/img/a4.jpg">
									</div>
									<div class="media-body">
										Uncover many web sites still in their infancy. Various
										versions have. <br> <small class="text-muted">Monday
											8:37 pm</small>
									</div>
								</a>
							</div>
						</div>

					</div>

					<div id="tab-2" class="tab-pane">

						<div class="sidebar-title">
							<h3>
								<i class="fa fa-cube"></i> Latest projects
							</h3>
							<small><i class="fa fa-tim"></i> You have 14 projects. 10
								not completed.</small>
						</div>

						<ul class="sidebar-list">
							<li><a href="index.html#">
									<div class="small pull-right m-t-xs">9 hours ago</div>
									<h4>Business valuation</h4> It is a long established fact that
									a reader will be distracted.

									<div class="small">Completion with: 22%</div>
									<div class="progress progress-mini">
										<div style="width: 22%;"
											class="progress-bar progress-bar-warning"></div>
									</div>
									<div class="small text-muted m-t-xs">Project end: 4:00 pm
										- 12.06.2014</div>
							</a></li>
							<li><a href="index.html#">
									<div class="small pull-right m-t-xs">9 hours ago</div>
									<h4>Contract with Company</h4> Many desktop publishing packages
									and web page editors.

									<div class="small">Completion with: 48%</div>
									<div class="progress progress-mini">
										<div style="width: 48%;" class="progress-bar"></div>
									</div>
							</a></li>
							<li><a href="index.html#">
									<div class="small pull-right m-t-xs">9 hours ago</div>
									<h4>Meeting</h4> By the readable content of a page when looking
									at its layout.

									<div class="small">Completion with: 14%</div>
									<div class="progress progress-mini">
										<div style="width: 14%;"
											class="progress-bar progress-bar-info"></div>
									</div>
							</a></li>
							<li><a href="index.html#"> <span
									class="label label-primary pull-right">NEW</span>
									<h4>The generated</h4> <!--<div class="small pull-right m-t-xs">9 hours ago</div>-->
									There are many variations of passages of Lorem Ipsum available.
									<div class="small">Completion with: 22%</div>
									<div class="small text-muted m-t-xs">Project end: 4:00 pm
										- 12.06.2014</div>
							</a></li>
							<li><a href="index.html#">
									<div class="small pull-right m-t-xs">9 hours ago</div>
									<h4>Business valuation</h4> It is a long established fact that
									a reader will be distracted.

									<div class="small">Completion with: 22%</div>
									<div class="progress progress-mini">
										<div style="width: 22%;"
											class="progress-bar progress-bar-warning"></div>
									</div>
									<div class="small text-muted m-t-xs">Project end: 4:00 pm
										- 12.06.2014</div>
							</a></li>
							<li><a href="index.html#">
									<div class="small pull-right m-t-xs">9 hours ago</div>
									<h4>Contract with Company</h4> Many desktop publishing packages
									and web page editors.

									<div class="small">Completion with: 48%</div>
									<div class="progress progress-mini">
										<div style="width: 48%;" class="progress-bar"></div>
									</div>
							</a></li>
							<li><a href="index.html#">
									<div class="small pull-right m-t-xs">9 hours ago</div>
									<h4>Meeting</h4> By the readable content of a page when looking
									at its layout.

									<div class="small">Completion with: 14%</div>
									<div class="progress progress-mini">
										<div style="width: 14%;"
											class="progress-bar progress-bar-info"></div>
									</div>
							</a></li>
							<li><a href="index.html#"> <span
									class="label label-primary pull-right">NEW</span>
									<h4>The generated</h4> <!--<div class="small pull-right m-t-xs">9 hours ago</div>-->
									There are many variations of passages of Lorem Ipsum available.
									<div class="small">Completion with: 22%</div>
									<div class="small text-muted m-t-xs">Project end: 4:00 pm
										- 12.06.2014</div>
							</a></li>

						</ul>

					</div>

					<div id="tab-3" class="tab-pane">

						<div class="sidebar-title">
							<h3>
								<i class="fa fa-gears"></i> Settings
							</h3>
							<small><i class="fa fa-tim"></i> You have 14 projects. 10
								not completed.</small>
						</div>

						<div class="setings-item">
							<span> Show notifications </span>
							<div class="switch">
								<div class="onoffswitch">
									<input type="checkbox" name="collapsemenu"
										class="onoffswitch-checkbox" id="example"> <label
										class="onoffswitch-label" for="example"> <span
										class="onoffswitch-inner"></span> <span
										class="onoffswitch-switch"></span>
									</label>
								</div>
							</div>
						</div>
						<div class="setings-item">
							<span> Disable Chat </span>
							<div class="switch">
								<div class="onoffswitch">
									<input type="checkbox" name="collapsemenu" checked
										class="onoffswitch-checkbox" id="example2"> <label
										class="onoffswitch-label" for="example2"> <span
										class="onoffswitch-inner"></span> <span
										class="onoffswitch-switch"></span>
									</label>
								</div>
							</div>
						</div>
						<div class="setings-item">
							<span> Enable history </span>
							<div class="switch">
								<div class="onoffswitch">
									<input type="checkbox" name="collapsemenu"
										class="onoffswitch-checkbox" id="example3"> <label
										class="onoffswitch-label" for="example3"> <span
										class="onoffswitch-inner"></span> <span
										class="onoffswitch-switch"></span>
									</label>
								</div>
							</div>
						</div>
						<div class="setings-item">
							<span> Show charts </span>
							<div class="switch">
								<div class="onoffswitch">
									<input type="checkbox" name="collapsemenu"
										class="onoffswitch-checkbox" id="example4"> <label
										class="onoffswitch-label" for="example4"> <span
										class="onoffswitch-inner"></span> <span
										class="onoffswitch-switch"></span>
									</label>
								</div>
							</div>
						</div>
						<div class="setings-item">
							<span> Offline users </span>
							<div class="switch">
								<div class="onoffswitch">
									<input type="checkbox" checked name="collapsemenu"
										class="onoffswitch-checkbox" id="example5"> <label
										class="onoffswitch-label" for="example5"> <span
										class="onoffswitch-inner"></span> <span
										class="onoffswitch-switch"></span>
									</label>
								</div>
							</div>
						</div>
						<div class="setings-item">
							<span> Global search </span>
							<div class="switch">
								<div class="onoffswitch">
									<input type="checkbox" checked name="collapsemenu"
										class="onoffswitch-checkbox" id="example6"> <label
										class="onoffswitch-label" for="example6"> <span
										class="onoffswitch-inner"></span> <span
										class="onoffswitch-switch"></span>
									</label>
								</div>
							</div>
						</div>
						<div class="setings-item">
							<span> Update everyday </span>
							<div class="switch">
								<div class="onoffswitch">
									<input type="checkbox" name="collapsemenu"
										class="onoffswitch-checkbox" id="example7"> <label
										class="onoffswitch-label" for="example7"> <span
										class="onoffswitch-inner"></span> <span
										class="onoffswitch-switch"></span>
									</label>
								</div>
							</div>
						</div>

						<div class="sidebar-content">
							<h4>Settings</h4>
							<div class="small">I belive that. Lorem Ipsum is simply
								dummy text of the printing and typesetting industry. And
								typesetting industry. Lorem Ipsum has been the industry's
								standard dummy text ever since the 1500s. Over the years,
								sometimes by accident, sometimes on purpose (injected humour and
								the like).</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>