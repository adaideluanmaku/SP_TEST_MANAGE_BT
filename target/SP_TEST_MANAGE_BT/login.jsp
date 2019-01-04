<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>CH+ | Login</title>
<!-- SYS CSS -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/css/animate.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/css/style.css" rel="stylesheet">
<!-- SYS JS -->
<script src="${pageContext.request.contextPath}/bootstrap_home/js/jquery-2.1.1.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/js/bootstrap.min.js"></script>
<!-- MY JS -->

<script language="JavaScript">
//当前页面跳出iframe范围（用于session过期后）
if(window !=top){  
    top.location.href=location.href;  
}
</script>
	
</head>
<body class="gray-bg">
	<div class="middle-box text-center loginscreen animated fadeInDown">
		<div>
			<div>
				<h1 class="logo-name">CH+</h1>
			</div>
			<h3>Welcome to CH+</h3>
			<p>
				Perfectly designed and precisely prepared admin theme with over 50
				pages with extra new web app views.
				<!--Continually expanded and constantly improved Inspinia Admin Them (IN+)-->
			</p>
			<p>Login in. To see it in action.</p>
			<form class="m-t" role="form"
				action="${pageContext.request.contextPath}/login/login" method="post">
				<div class="form-group">
					<input type="text" id="loginname" class="form-control" placeholder="loginname"
						name="loginname" required="">
				</div>
				<div class="form-group">
					<input type="password" id="Password" class="form-control" placeholder="Password"
						name="password" required="">
				</div>
				<button type="submit" id="Login" class="btn btn-primary block full-width m-b">Login</button>

				<a href="login.html#"><small>Forgot password?</small></a>
				<p class="text-muted text-center">
					<small>Do not have an account?</small>
				</p>
				<a class="btn btn-sm btn-white btn-block" href="${pageContext.request.contextPath}/register.jsp">Create
					an account</a>
			</form>
			<p class="m-t">
				<small>Inspinia we app framework base on Bootstrap 3 &copy;
					2014</small>
			</p>
		</div>
	</div>

</body>

</html>
