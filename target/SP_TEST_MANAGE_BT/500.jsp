<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>INSPINIA | 500 Error</title>

   <!-- SYS CSS -->
	<link href="${pageContext.request.contextPath}/bootstrap_home/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/bootstrap_home/font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/bootstrap_home/css/animate.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/bootstrap_home/css/style.css" rel="stylesheet">
	<!-- SYS JS -->
	<script src="${pageContext.request.contextPath}/bootstrap_home/js/jquery-2.1.1.js"></script>
	<script src="${pageContext.request.contextPath}/bootstrap_home/js/bootstrap.min.js"></script>
</head>

<body class="gray-bg">


    <div class="middle-box text-center animated fadeInDown">
        <h1>500</h1>
        <h3 class="font-bold">Internal Server Error</h3>
        <div class="error-desc">
          	 无权限操作该功能.<br/>
        </div>
    </div>

</body>

</html>
