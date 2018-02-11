<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/themes/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chcss/pa/pa.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/pa/pa.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WORKS</title>
</head>
<body>
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">
<input id="loginname" type="hidden" value="${sessionScope.loginname}">
<div class="doc">
	<div class="box_1">
		<div id="view_1" style="background-color: slategray;">案例管理</div>
		<div id="view_2">待续</div>
		<div id="view_3">待续</div>
	</div>
	<div class="box_2">
		<div class="search">
			标题：<input id="serchdata" class="easyui-textbox" style="width:200px"  prompt="请输入标题">
			<a id="box_12_search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</div>
		<div id="box_12_dg" style="margin-top: 10px"></div>
		<div id="box_12_button">
			<a href="javascript:void(0)" id="add" class="easyui-linkbutton" iconCls="icon-add" plain="true" >Add</a>
			<a href="javascript:void(0)" id="del" class="easyui-linkbutton" iconCls="icon-remove" plain="true">delete</a>
		</div>
	</div>
</div>

<div id="box_1_dialog">
	<input id="dialogtype" type="hidden" value="0">
	<!-- 文本框 -->
	<form id="pa_form">
	<input id="id" name="id" type="hidden" value="">
	<div style="margin-bottom:20px; text-align: center; padding-top:10px;">
		<table>
			<tr>
				<td style="text-align: left;">
					<div>版本号： </div>
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<input  id="version" name="version" >
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<div>案例类型： </div>
				</td >
			</tr>
			<tr>
				<td style="text-align: left;">
					<input  id="anlitype" name="anlitype"  type= "text" class= "easyui-textbox" required ="required" >
				</td >
			</tr>
			<tr>
				<td style="text-align: left;">
					<div>案例名称： </div>
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<input  id="anliname" name="anliname"  type= "text" class= "easyui-textbox" required ="required">
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<div>输入串 : </div>
				</td >
			</tr>
			<tr>
				<td style="text-align: left;">
					<input id="gatherbaseinfo" name="gatherbaseinfo" class="easyui-textbox" multiline="true" required="true" style="width:550px;height:200px;">
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
</body>
</html>