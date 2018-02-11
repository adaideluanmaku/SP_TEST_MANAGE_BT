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

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chcss/users/users.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/users/users.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>USERS_MANAGE</title>
</head>
<body>
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">
<div class="doc">
	<div class="box_1">
		<div id="view_1" style="background-color: slategray;">用户信息</div>
		<div id="view_2">待续</div>
		<div id="view_3">待续</div>
	</div>
	<div class="box_2">
	<input id="box_type" type="hidden" value="0">
		<div class="search" >
			<div style="float: left; margin-right: 10px"><input id="search_data" class="easyui-textbox" style="width:200px" prompt="请输入登录名"></div>
			<a id="box_search_button" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</div>
		<div id="box_db" style="margin-top: 10px"></div>
		<div id="box_button">
			<a href="javascript:void(0)" id="add_" class="easyui-linkbutton" iconCls="icon-add" plain="true" >Add</a>
			<a href="javascript:void(0)" id="del_" class="easyui-linkbutton" iconCls="icon-remove" plain="true">delete</a>
		</div>
	</div>
</div>
<div id="box_1_dialog">
	<input id="dialogtype" type="hidden" value="0">
	<!-- 文本框 -->
	<form id="users_form">
	<input id="userid" name="userid" type="hidden" value="">
	<div style="margin-bottom:20px; text-align: center; padding-top:10px;">
		<table cellspacing="10">
			<tr>
				<td style="text-align: left;">
					<div >登录名: </div>
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<input id="loginname" name="loginname" class="easyui-textbox" required="true" validType="length[1,100]" style="width:300px;height:32px;">
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<div>用户名 : </div>
				</td >
			</tr>
			<tr>
				<td style="text-align: left;">
				 	<input id="username" name="username" class="easyui-textbox" multiline="true" required="true" validType="length[1,100]" style="width:300px;height:32px;">
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<div>密码 : </div>
				</td >
			</tr>
			<tr>
				<td style="text-align: left;">
				 	<input id="password" name="password" class="easyui-textbox" multiline="true" required="true" validType="length[1,100]" style="width:300px;height:32px;">
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<div >selenium浏览器本地路径 : </div>
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<input id="browserpath" name="browserpath" class="easyui-textbox" validType="length[1,100]" style="width:300px;height:32px;">
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<div >pa审查地址 : </div>
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<input id="pa_screen" name="pa_screen" class="easyui-textbox" validType="length[1,100]" style="width:300px;height:32px;">
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<div >pa-win审查地址 : </div>
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<input id="pa_screen_win" name="pa_screen_win" class="easyui-textbox" validType="length[1,100]" style="width:300px;height:32px;">
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<div >备注 : </div>
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<input id="remark" name="remark" class="easyui-textbox" multiline="true" validType="length[1,10000]" style="width:350px;height:100px;">
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
</body>
</html>