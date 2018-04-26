<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>CH+ | Register</title>
<!-- SYS CSS -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/css/style.css" rel="stylesheet">

<!-- SYS JS -->
<!-- 引入 Bootstrap -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/js/jquery-3.2.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<!-- Sweet alert -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/sweetalert.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/sweetalert/sweetalert.min.js"></script>


<!-- MY JS -->
<script type="text/javascript">
function register_submit(){
	var addurl=$("#addurl").val()+"/login/register";
	$.ajax({
		type:"post",
		url: addurl,
		async:false,
		data:$('#register_form').serialize(),
		success: function(result){
			if(result=='ok'){
				swal("提示!", "注册成功，请想尽办法联系管理员启动账户", "success");
			}else{
				swal("提示!", result, "error");
			}
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
}
</script>
</head>
<body class="gray-bg">
	<input type="hidden" id="addurl" value="${pageContext.request.contextPath}">
    <div class="middle-box text-center loginscreen   animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name">CH+</h1>
            </div>
            <h3>Register to CH+</h3>
            <p>Create account to see it in action.</p>
            <form class="m-t" id="register_form">
                <div class="form-group">
                    <input type="text" name="loginname" class="form-control" placeholder="loginname" required="">
                </div>
                <div class="form-group">
                    <input type="text" name="username" class="form-control" placeholder="username" required="">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="password" required="">
                </div>
                <div class="form-group">
                        <div class="checkbox i-checks"><label> <input type="checkbox"><i></i> Agree the terms and policy </label></div>
                </div>
                <button type="button" class="btn btn-primary block full-width m-b" onclick="register_submit()">Register</button>

                <p class="text-muted text-center"><small>Already have an account?</small></p>
                <a class="btn btn-sm btn-white btn-block" href="${pageContext.request.contextPath}/login.jsp">Login</a>
            </form>
            <p class="m-t"> <small>Inspinia we app framework base on Bootstrap 3 &copy; 2014</small> </p>
        </div>
    </div>
</body>
</html>
