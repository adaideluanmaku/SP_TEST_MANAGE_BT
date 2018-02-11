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

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chcss/learn/learn.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/learn/learn.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LEARN</title>
</head>
<body>
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">
<div class="doc">
	<div class="box_1">
		<div id="view_1" style="background-color: slategray;">学习分类</div>
		<div id="view_2">学习笔记</div>
		<div id="view_3">待续</div>
	</div>
	<div class="box_2">
		<input id="box_type" type="hidden" value="0">
		<div class="search">
			<!-- 下拉单 -->
			<div id="ComboBox" style="float: left; margin-right: 10px">
				<input id="ComboBox_right" class="easyui-combobox"/>
			</div>
			<div style="float: left; margin-right: 10px">
				<input id="search_data" class="easyui-textbox" style="width:200px" prompt="请输入分类名称">
			</div>
			<a id="box_search_button" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</div>
		<!-- 表格 -->
		<div id="box_db" style="margin-top: 10px"></div>
		<!-- 表格功能按钮 -->
		<div id="button_">
			<a href="javascript:void(0)" id="add_" class="easyui-linkbutton" iconCls="icon-add" plain="true">Add</a>
			<a href="javascript:void(0)" id="del_" class="easyui-linkbutton" iconCls="icon-remove" plain="true">delete</a>
		</div>
	</div>
</div>
<!-- 对话框 -->
<div id="learngroup_dialog">
	<!-- 文本框 -->
	<div style="margin-bottom:20px; text-align: center; padding-top:10px;">
		分类 : <input id="learngroup" class="easyui-textbox" required="true" validType="length[1,100]" style="width:200px;height:32px;">
	</div>
</div>

<!-- 对话框 -->
<div id="learn_dialog">
	<!-- 文本框 -->
	<div style="margin-bottom:20px; text-align: center; padding-top:10px;">
		<form id="learn_from">
		<input id="learn_dialog_type" type="hidden" value="0">
		<input id="learnid" name="learnid" type="hidden" value="">
		<table>
			<tr>
				<td style="text-align: left;">
					<!-- 下拉单 -->
					<input id="ComboBox_right" name="learngroupid" required="true" class="easyui-combobox" value=""/>
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<div >标题 : </div>
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<input id="learnname" name="learnname" class="easyui-textbox" required="true" validType="length[1,100]" style="width:200px;height:32px;">
				</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<div>内容 : </div>
				</td >
			</tr>
			<tr>
				<td style="text-align: left;">
				 	<input id="learntext" name="learntext" class="easyui-textbox" multiline="true" required="true" validType="length[1,10000]" style="width:580px;height:380px;">
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
<div id="dlg_right_file">
	<!-- 进度条 -->
	<!-- 文本框 -->
	<div style="margin-bottom:20px; text-align: center; padding-top:10px;">
		<table>
			<tr>
				<td style="text-align: left;">
					<div>附件 : </div>
				</td >
			</tr>
			<tr>
				<td>
				<!-- 上传附件 -->
				<form id="form_file" enctype="multipart/form-data" method="POST">
					<input id="learnid" type="hidden" value="" name="learnid">
			        <div>  
			            <input id="learnfile1" name="learnfile1" class="easyui-filebox" style="width:300px">
			            <!-- <input type="submit" value="提交"> -->
			            <a id="file_button" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a>
			        </div>  
			 	</form>
			 	<div style="margin: 0 auto; color: red">只能上传图片</div>
				</td>
			</tr>
		</table>
	</div>
</div>
<!-- 附件显示查看或者下载 -->
<div id="file_dialog" class="easyui-resizable">
	<input id="learnid" type="hidden" value="">
	<input id="fileid" type="hidden" value="">
	<img id="file_img" src="">
	<p id="file_p"></p>
</div>
<!-- 进度条显示
<div id="progressbar_box">
	<table>
		<tr>
			<td>
				<div id="progressbar" class="easyui-progressbar" data-options="value:0" style="width:400px;"></div>
			</td>
		</tr>
	</table>
</div>
-->
</body>
</html>