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

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chcss/works/works.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/works/works.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WORKS</title>
</head>
<body>
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">
<input id="loginname" type="hidden" value="${sessionScope.loginname}">
<div class="doc">
	<div class="box_1">
		<div id="view_1" style="background-color: slategray;">计划管理</div>
		<div id="view_2">待续</div>
		<div id="view_3">待续</div>
	</div>
	<div class="box_2">
		<div class="search">
			开始时间：<input id="year" name="dept" value="">年
			<input id="month" name="dept" value="">月
			姓名：<input id="username" class="easyui-textbox" style="width:100px"  prompt="请输入姓名">
			标题：<input id="workname" class="easyui-textbox" style="width:200px"  prompt="请输入标题">
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
	<form id="workds_form">
	<input id="workid_" name="workid" type="hidden" value="">
	<div style="margin-bottom:20px; text-align: center; padding-top:10px;">
		<table>
			<tr>
				<td style="text-align: left;width:300px">
					开始时间：<input  id="starttime_" name="starttime"  type= "text" class= "easyui-datebox" required ="required"> </input>
				</td>
				<td style="text-align: left;width:300px">
					结束日期：<input  id="endtime_" name="endtime"  type= "text" class= "easyui-datebox" required ="required"> </input>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td style="text-align: left;">
					<div>人员 : </div>
				</td >
			</tr>
			<tr>
				<td style="text-align: left;">
					<div><input id="add_select" name="userid"></div>
				</td >
			</tr>
			<tr>
				<td style="text-align: left;">
					<div>标题 : </div>
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<input id="workname_" name="workname" class="easyui-textbox" required="true" validType="length[1,100]" style="width:300px;" >
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<div>工作内容 : </div>
				</td >
			</tr>
			<tr>
				<td style="text-align: left;">
					<input id="worktext_" name="worktext" class="easyui-textbox" multiline="true" required="true" validType="length[1,10000]" style="width:550px;height:200px;">
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
</body>
</html>