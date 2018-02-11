<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>CH_TMANAGE | Register</title>
	
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
		rel="stylesheet">
	<link
		href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css"
		rel="stylesheet">
	<link
		href="${pageContext.request.contextPath}/css/plugins/iCheck/custom.css"
		rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/animate.css"
		rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/style.css"
		rel="stylesheet">
	<!-- Mainly scripts -->
	<script src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<!-- iCheck -->
	<script
		src="${pageContext.request.contextPath}/js/plugins/iCheck/icheck.min.js"></script>
	<script>
		$(document).ready(function() {
			$('.i-checks').iCheck({
				checkboxClass : 'icheckbox_square-green',
				radioClass : 'iradio_square-green',
			});
		});
	</script>
</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen   animated fadeInDown">
        <div>
            <div>

                <h1 class="logo-name">CH+</h1>

            </div>
            <h3>Register to CH+</h3>
            <p>Create account to see it in action.</p>
            <form class="m-t" role="form" action="login.html">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Name" required="">
                </div>
                <div class="form-group">
                    <input type="email" class="form-control" placeholder="Email" required="">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="Password" required="">
                </div>
                <div class="form-group">
                        <div class="checkbox i-checks"><label> <input type="checkbox"><i></i> Agree the terms and policy </label></div>
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">Register</button>

                <p class="text-muted text-center"><small>Already have an account?</small></p>
                <a class="btn btn-sm btn-white btn-block" href="${pageContext.request.contextPath}/login.jsp">Login</a>
            </form>
            <p class="m-t"> <small>Inspinia we app framework base on Bootstrap 3 &copy; 2014</small> </p>
        </div>
    </div>

    
</body>

</html>
