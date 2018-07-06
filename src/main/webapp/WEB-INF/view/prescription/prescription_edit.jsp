<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>INSPINIA | prescription</title>
<!-- SYS CSS -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/css/style.css" rel="stylesheet">

<!-- SYS JS -->
<!-- 引入 Bootstrap -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/js/jquery-2.1.1.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-table/bootstrap-table.js"></script>
<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-table/bootstrap-table.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-table/bootstrap-table-zh-CN.js"></script>

<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap3-editable/js/bootstrap-editable.js"></script>
<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap3-editable/js/bootstrap-table-editable.js"></script>

<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-dialog/bootstrap-dialog.min.css" rel="stylesheet" >
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-dialog/bootstrap-dialog.min.js"></script>
    
<!-- bootstrap-datetimepicker -->
<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />  
<script src="${pageContext.request.contextPath}/bootstrap_home/moment/min/moment-with-locales.min.js"></script>  
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>  

<!-- Sweet alert -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/sweetalert.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/sweetalert/sweetalert.min.js"></script>
<!-- 等待效果 -->
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/pace/pace.min.js"></script>

<!-- select2 -->
<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-select2/css/select2.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-select2/js/select2.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-select2/js/i18n/zh-CN.js"></script>

<!-- PASSJS -->
<link href="${pageContext.request.contextPath}/PassJs/McCssAll.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/PassJs/McConfig.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/PassJs/McJsAll.js"></script>

<!-- MY JS -->
<link href="${pageContext.request.contextPath}/chcss/prescription/prescription_edit.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/prescription/prescription_edit.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/prescription/passjs_screen.js"></script>

</head>
<body style="background-color: #f3f3f4">
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">
<input id="prescription_json" type="hidden" value="${fn:escapeXml(prescription_json) }">
<input id="pre_id" type="hidden" value="${pre_id}">
<input id="patientname" type="hidden" value="${patientname}">
<input id="prescriptiontype" type="hidden" value="${prescriptiontype}">
<div id="wrapper" >
	<div class="panel panel-default">
	    <div class="panel-heading">处方操作：调用PASS嵌入端</div>
	    <div class="panel-body">
	    	<div class="col-sm-2">
		      	<div class="input-group">
		            <span class="input-group-addon">机构编码</span>
		            <input type="text" class="form-control" id="HospID" name="HospID">
		        </div>
		    </div>
	        <div class="col-sm-2" style="text-align: left;">
	            <button type="button" id="prescription_save" class="btn btn-primary" onclick="json_hebing()">保存</button>
	        </div>
	         <div class="col-sm-2" style="text-align: left;">
	            <button type="button" class="btn btn-primary" onclick="js_screen()">审查</button>
	        </div>
	        <div class="col-sm-2" style="text-align: left;">
	            <button type="button" class="btn btn-primary" onclick="js_shuomingshu()">说明书</button>
	        </div>
	        <div class="col-sm-2" style="text-align: left;">
	            <button type="button" class="btn btn-primary" onclick="js_fudongchuangkou()">浮动窗口</button>
	        </div>
	    </div>
	</div>  
	<div class="panel">
    	<div  class="gray-bg" style="background-color: #ffffff">
			<div class="nav nav-tabs" style="overflow-x:auto" id="tags">
				<ul class="nav nav-tabs nav-justified">
			      <li role="presentation" class="active"><a href="#aa" data-toggle="tab"  style="height:58px">病人基本信息</a></li>
			      <li role="presentation"><a href="#bb" data-toggle="tab" style="height:58px">药品信息</a></li>
			      <li role="presentation"><a href="#cc" data-toggle="tab" style="height:58px">过敏原</a></li>
			      <li role="presentation"><a href="#dd" data-toggle="tab" style="height:58px">疾病</a></li>
			      <li role="presentation"><a href="#ee" data-toggle="tab" style="height:58px">手术</a></li>
			     <!--  <li role="presentation"><a href="#ff" data-toggle="tab" style="height:58px">检验</a></li>
			      <li role="presentation"><a href="#gg" data-toggle="tab" style="height:58px">检查</a></li> -->
			      <li role="presentation"><a href="#hh" data-toggle="tab"  style="height:58px">附加-信息类型</a></li>
			      <li role="presentation"><a href="#ii" data-toggle="tab" style="height:58px">附加-任务类型</a></li>
			      <li role="presentation"><a href="#jj" data-toggle="tab" style="height:58px">附加-补充药品</a></li>
			      <li role="presentation"><a href="#kk" data-toggle="tab" style="height:58px">附加-补充诊断</a></li>
			      <li role="presentation"><a href="#ll" data-toggle="tab" style="height:58px">附加-补充历史医嘱</a></li>
			      <li role="presentation"><a href="#mm" data-toggle="tab" style="height:58px">附加-补充检查</a></li>
			      <li role="presentation"><a href="#nn" data-toggle="tab" style="height:58px">附加-补充检验</a></li>
			    </ul>
			</div>
    	</div>
	</div>  
</div>

<div class="tab-content">
	<div class="tab-pane fade in active" id="aa">
		<div class="container-fluid" id="patientmessage">
			<div class="row" style="margin-bottom : 15px">
				<div class="col-sm-3">
			      	<div class="input-group">
			            <span class="input-group-addon">门诊/住院唯一标识</span>
			            <input type="text" class="form-control" id="VisitCode" name="VisitCode">
			        </div>
			    </div>
				<div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">病人号</span>
			            <input type="text" class="form-control" id="PatCode" name="PatCode">
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">门诊/住院号</span>
			            <input type="text" class="form-control" id="InHospNo" name="InHospNo">
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">费别</span>
			            <input type="text" class="form-control" id="PayClass" name="PayClass" >
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">病人状态</span>
			            <select class="form-control m-b" name="PatStatus" id="PatStatus">
                            <option value="1" selected="selected"><font style="vertical-align: inherit;">住院</font></option>
                            <option value="2"><font style="vertical-align: inherit;">门诊</font></option>
                            <option value="3"><font style="vertical-align: inherit;">急诊</font></option>
                            <option value="0"><font style="vertical-align: inherit;">出院</font></option>
                        </select>
			        </div>
			    </div>
			</div>
			<div class="row" style="margin-bottom : 15px">
				<div class="col-sm-3">
			      	<div class="input-group">
			            <span class="input-group-addon">姓名</span>
			            <input type="text" class="form-control" id="Name" name="Name">
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">性别</span>
			            <select class="form-control m-b" name="Sex" id="Sex">
	                            <option value="" selected="selected"><font style="vertical-align: inherit;">无</font></option>
	                            <option value="男"><font style="vertical-align: inherit;">男</font></option>
	                            <option value="女"><font style="vertical-align: inherit;">女</font></option>
	                            <option value="male"><font style="vertical-align: inherit;">male</font></option>
	                            <option value="female"><font style="vertical-align: inherit;">female</font></option>
	                            <option value="m"><font style="vertical-align: inherit;">m</font></option>
	                            <option value="f"><font style="vertical-align: inherit;">f</font></option>
	                        </select>
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">身份证</span>
			            <input type="text" class="form-control" id="IDCard" name="IDCard">
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">联系方式</span>
			            <input type="text" class="form-control" id="Telephone" name="Telephone">
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">年龄</span>
			            <input type="text" class="form-control" id="Age" name="Age">
			        </div>
			    </div>
			</div>
			<div class="row" style="margin-bottom : 15px">
				<div class="col-sm-3">
			      	<div class="input-group">
			            <span class="input-group-addon">出生日期</span>
			            <div class="input-group date" id="Birthday" name="Birthday">  
			                <input type="text" class="form-control" />  
			                <span class="input-group-addon">  
			                    <span class="glyphicon glyphicon-calendar"></span>  
			                </span>  
			            </div>
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">身高cm</span>
			            <input type="text" class="form-control" id="HeightCM" name="HeightCM">
			        </div>
			    </div>
			     <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">体重kg</span>
			            <input type="text" class="form-control" id="WeighKG" name="WeighKG">
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">病原学检查</span>
			            <select class="form-control m-b" name="IsTestEtiology" id="IsTestEtiology">
	                            <option value="0" selected="selected"><font style="vertical-align: inherit;">未做过</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">做过</font></option>
	                        </select>
			        </div>
			    </div>
			</div>
			<div class="row" style="margin-bottom : 15px">
				<div class="col-sm-3">
			      	<div class="input-group">
			            <span class="input-group-addon">妊娠开始时间</span>
			        	<!--指定 date标记-->  
			            <div class="input-group date" id="PregStartDate" name="PregStartDate">  
			                <input type="text" class="form-control" />  
			                <span class="input-group-addon">  
			                    <span class="glyphicon glyphicon-calendar"></span>  
			                </span>  
			            </div>  
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">是否妊娠</span>
			            <select class="form-control m-b" name="IsPregnancy" id="IsPregnancy">
	                            <option value="-1" selected="selected"><font style="vertical-align: inherit;">无法获取妊娠状态</font></option>
	                            <option value="0"><font style="vertical-align: inherit;">不是</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">是</font></option>
	                        </select>
			        </div>
			    </div>
				<div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">是否哺乳</span>
			            <select class="form-control m-b" name="IsLactation" id="IsLactation">
	                            <option value="-1" selected="selected"><font style="vertical-align: inherit;">无法获取哺乳状态</font></option>
	                            <option value="0"><font style="vertical-align: inherit;">不是</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">是</font></option>
	                        </select>
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">肝损害程度</span>
			            <select class="form-control m-b" name="HepDamageDegree" id="HepDamageDegree">
	                            <option value="-1" selected="selected"><font style="vertical-align: inherit;">无法获取肝损害状态</font></option>
	                            <option value="0"><font style="vertical-align: inherit;">无肝损害</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">存在肝损害，但损害程度不明确</font></option>
	                            <option value="2"><font style="vertical-align: inherit;">轻度肝损害</font></option>
	                            <option value="3"><font style="vertical-align: inherit;">中度肝损害</font></option>
	                            <option value="4"><font style="vertical-align: inherit;">重度肝损害</font></option>
	                        </select>
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">肝损害程度</span>
			            <select class="form-control m-b" name="RenDamageDegree" id="RenDamageDegree">
	                            <option value="-1" selected="selected"><font style="vertical-align: inherit;">无法获取肾损害状态</font></option>
	                            <option value="0"><font style="vertical-align: inherit;">无肾损害</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">存在肾损害，但损害程度不明确</font></option>
	                            <option value="2"><font style="vertical-align: inherit;">轻度肾损害</font></option>
	                            <option value="3"><font style="vertical-align: inherit;">中度肾损害</font></option>
	                            <option value="4"><font style="vertical-align: inherit;">重度肾损害</font></option>
	                        </select>
			        </div>
			    </div>
			</div>
			<div class="row" style="margin-bottom : 15px">
				<div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">科室编号</span>
			            <input type="text" class="form-control" id="DeptCode" name="DeptCode">
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">科室名称</span>
			            <input type="text" class="form-control" id="DeptName" name="DeptName" ondblclick="dict_dept_modal_open('patientmessage',-1)">
			        </div>
			    </div>
			     <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">医生编号</span>
			            <input type="text" class="form-control" id="DoctorCode" name="DoctorCode">
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">医生姓名</span>
			            <input type="text" class="form-control" id="DoctorName" name="DoctorName" ondblclick="dict_doctor_modal_open('patientmessage',-1)">
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">采集</span>
			            <select class="form-control m-b" name="IsDoSave" id="IsDoSave">
	                            <option value="1" selected="selected"><font style="vertical-align: inherit;">是</font></option>
	                            <option value="2"><font style="vertical-align: inherit;">否</font></option>
	                        </select>
			        </div>
			    </div>
			</div>
			<div class="row" style="margin-bottom : 15px">
				<div class="col-sm-3">
			      	<div class="input-group">
			            <span class="input-group-addon">住院时间</span>
			            <div class="input-group date" id="InHospDate" name="InHospDate">  
			                <input type="text" class="form-control" />  
			                <span class="input-group-addon">  
			                    <span class="glyphicon glyphicon-calendar"></span>  
			                </span>  
			            </div>  
			        </div>
			    </div>
			    <div class="col-sm-3">
			      	<div class="input-group">
			            <span class="input-group-addon">出院时间</span>
			            <div class="input-group date" id="OutHospDate" name="OutHospDate">  
			                <input type="text" class="form-control" />  
			                <span class="input-group-addon">  
			                    <span class="glyphicon glyphicon-calendar"></span>  
			                </span>  
			            </div>  
			        </div>
			    </div>
			    <div class="col-sm-3">
			      	<div class="input-group">
			            <span class="input-group-addon">审查时间</span>
			            <div class="input-group date" id="UseTime" name="UseTime">  
			                <input type="text" class="form-control" />  
			                <span class="input-group-addon">  
			                    <span class="glyphicon glyphicon-calendar"></span>  
			                </span>  
			            </div>  
			        </div>
			    </div>
			    <div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">审查模式</span>
			            <input type="text" class="form-control" id="CheckMode" name="CheckMode">
			        </div>
			    </div>
			</div>
		</div>
	</div>
	
	<div class="tab-pane fade" id="bb">
		<div class="container-fluid" id="drugsmessage" style="background-color: #f3f3f4 ">
			<div id="toolbar" class="btn-group">
				<div class="col-sm-12">
					<button id="btn_add" type="button" class="btn btn-default" onclick="drug_append()">
			        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				    </button>
				    <button id="btn_delete" type="button" class="btn btn-default" onclick="drug_del();">
				        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
				    </button>
				    <button id="btn_dict_drug" type="button" class="btn btn-default" data-toggle="modal" onclick="dict_drug_modal_open()">
				        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>药品字典表维护
				    </button>
				    <button id="btn_dict_route" type="button" class="btn btn-default" data-toggle="modal" onclick="dict_route_modal_open()">
				        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>给药途径字典表维护
				    </button>
				     <button id="btn_dict_fre" type="button" class="btn btn-default" data-toggle="modal" onclick="dict_fre_modal_open()">
				        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>频次字典表维护
				    </button>
				    <button id="btn_dict_dept" type="button" class="btn btn-default" data-toggle="modal" onclick="dict_dept_modal_open()">
				        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>科室字典表维护
				    </button>
				    <button id="btn_dict_doctor" type="button" class="btn btn-default" data-toggle="modal" onclick="dict_doctor_modal_open()">
				        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>医生字典表维护
				    </button>
				</div>
			</div>
			<!-- 信息表格 -->
	        <table id="json_table" style="table-layout:fixed; "></table>
		</div>
		
	</div>
	
	<div class="tab-pane fade" id="cc">
		<div class="container-fluid" id="allergenmessage" style="background-color: #f3f3f4 ">
			<div id="toolbar" class="btn-group">
				<div class="col-sm-12">
					<button id="btn_add" type="button" class="btn btn-default" onclick="allergen_append()">
			        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				    </button>
				    <button id="btn_delete" type="button" class="btn btn-default" onclick="allergen_del()">
				        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
				    </button>
				    <button id="btn_dict_doctor" type="button" class="btn btn-default" data-toggle="modal" onclick="dict_allergen_modal_open()">
				        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>过敏原字典表维护
				    </button>
				</div>
			</div>
			<!-- 信息表格 -->
	        <table id="json_table" style="table-layout:fixed; "></table>
		</div>
	</div>
	
	<div class="tab-pane fade" id="dd">
		<div class="container-fluid" id="diseasemessage" style="background-color: #f3f3f4 ">
			<div id="toolbar" class="btn-group">
				<div class="col-sm-12">
					<button id="btn_add" type="button" class="btn btn-default" onclick="disease_append()">
			        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				    </button>
				    <button id="btn_delete" type="button" class="btn btn-default" onclick="disease_del()">
				        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
				    </button>
				    <button id="btn_dict_doctor" type="button" class="btn btn-default" data-toggle="modal" onclick="dict_disease_modal_open()">
				        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>疾病字典表维护
				    </button>
				</div>
			</div>
			<!-- 信息表格 -->
	        <table id="json_table" style="table-layout:fixed; "></table>
		</div>
	</div>
	
	<div class="tab-pane fade" id="ee">
		<div class="container-fluid" id="operationmessage" style="background-color: #f3f3f4 ">
			<div id="toolbar" class="btn-group">
				<div class="col-sm-12">
					<button id="btn_add" type="button" class="btn btn-default"  onclick="operation_append()">
			        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				    </button>
				    <button id="btn_delete" type="button" class="btn btn-default" onclick="operation_del()">
				        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
				    </button>
				    <button id="btn_dict_doctor" type="button" class="btn btn-default" data-toggle="modal" onclick="dict_operation_modal_open()">
				        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>手术字典表维护
				    </button>
				</div>
			</div>
			<!-- 信息表格 -->
	        <table id="json_table" style="table-layout:fixed; "></table>
		</div>
	</div>
	
	<div class="tab-pane fade" id="ff">
		<div class="container-fluid" id="jianyanmessage" style="background-color: #f3f3f4 ">
			<div class="row">
				<div class="col-sm-2">无用</div>
			</div>
		</div>
	</div>
	
	<div class="tab-pane fade" id="gg">
		<div class="container-fluid" id="jianchamessage" style="background-color: #f3f3f4 ">
			<div class="row">
				<div class="col-sm-2">无用</div>
			</div>
		</div>
	</div>
	
	<div class="tab-pane fade" id="hh">
		<div class="container-fluid" id="fujiamessage" style="background-color: #f3f3f4 ">
			<div class="row">
				<div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">信息类型</span>
			            <select class="form-control m-b" name="jsontype" id="jsontype" style="width:200px">
                            <option value="1" selected="selected"><font style="vertical-align: inherit;">补充信息</font></option>
                            <option value="2"><font style="vertical-align: inherit; ">完整的审查输入信息</font></option>
                        </select>
			        </div>
			    </div>
			</div>
		</div>
	</div>
	
	<div class="tab-pane fade" id="ii">
		<div class="container-fluid" id="fujiatask" style="background-color: #f3f3f4 ">
			<div class="row">
				<div class="col-sm-2">
			      	<div class="input-group">
			            <span class="input-group-addon">任务类型</span>
			            <select class="form-control m-b" name="prtasktype" id="prtasktype">
                            <option value="0" selected="selected"><font style="vertical-align: inherit;">普通</font></option>
                            <option value="1"><font style="vertical-align: inherit; ">加急</font></option>
                        </select>
			        </div>
			    </div>
			</div>
		</div>
	</div>
	
	<div class="tab-pane fade" id="jj">
		<div class="container-fluid" id="fujiadrug" style="background-color: #f3f3f4 ">
			<div id="toolbar" class="btn-group">
				<div class="col-sm-12">
					<button id="btn_add" type="button" class="btn btn-default" onclick="fujia_drug_append()">
			        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				    </button>
				    <button id="btn_delete" type="button" class="btn btn-default" onclick="fujia_drug_del()">
				        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
				    </button>
				</div>
			</div>
			<!-- 信息表格 -->
	        <table id="json_table" style="table-layout:fixed; "></table>
		</div>
	</div>
	
	<div class="tab-pane fade" id="kk">
		<div class="container-fluid" id="fujiadisease" style="background-color: #f3f3f4 ">
			<div id="toolbar" class="btn-group">
				<div class="col-sm-12">
					<button id="btn_add" type="button" class="btn btn-default" onclick="fujia_disease_append()">
			        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				    </button>
				    <button id="btn_delete" type="button" class="btn btn-default" onclick="fujia_disease_del()">
				        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
				    </button>
				</div>
			</div>
			<!-- 信息表格 -->
	        <table id="json_table" style="table-layout:fixed; "></table>
		</div>
	</div>
	
	<div class="tab-pane fade" id="ll">
		<div class="container-fluid" id="fujiaotherrecip" style="background-color: #f3f3f4 ">
			<div id="toolbar" class="btn-group">
				<div class="col-sm-12">
					<button id="btn_add" type="button" class="btn btn-default" onclick="fujia_otherrecip_append()">
			        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				    </button>
				    <button id="btn_delete" type="button" class="btn btn-default" onclick="fujia_otherrecip_del()">
				        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
				    </button>
				</div>
			</div>
			<!-- 信息表格 -->
	        <table id="json_table" style="table-layout:fixed; "></table>
		</div>
	</div>
	
	<div class="tab-pane fade" id="mm">
		<div class="container-fluid" id="fujiaexaminfo" style="background-color: #f3f3f4 ">
			<div id="toolbar" class="btn-group">
				<div class="col-sm-12">
					<button id="btn_add" type="button" class="btn btn-default" onclick="fujia_examinfo_append()">
			        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				    </button>
				    <button id="btn_delete" type="button" class="btn btn-default" onclick="fujia_examinfo_del()">
				        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
				    </button>
				    <button id="btn_dict_exam" type="button" class="btn btn-default" data-toggle="modal" onclick="dict_exam_modal_open()">
				        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>检查字典表维护
				    </button>
				</div>
			</div>
			<!-- 信息表格 -->
	        <table id="json_table" style="table-layout:fixed; "></table>
		</div>
	</div>
	
	<div class="tab-pane fade" id="nn">
		<div class="container-fluid" id="fujialabinfo" style="background-color: #f3f3f4 ">
			<div id="toolbar" class="btn-group">
				<div class="col-sm-12">
					<button id="btn_add" type="button" class="btn btn-default" onclick="fujia_labinfo_append()">
			        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				    </button>
				    <button id="btn_delete" type="button" class="btn btn-default" onclick="fujia_labinfo_del()">
				        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
				    </button>
				    <button id="btn_dict_lab" type="button" class="btn btn-default" data-toggle="modal" onclick="dict_lab_modal_open()">
				        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>检验字典表维护
				    </button>
				</div>
			</div>
			<!-- 信息表格 -->
	        <table id="json_table" style="table-layout:fixed; "></table>
		</div>
</div>

<!-- 模态框（Modal）字典表 -->
<div class="modal fade" id="dict_drug_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog"  aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">药品字典表</h4>
			</div>
			<div class="modal-body">
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="dict_drug_open(1)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <button id="btn_add" type="button" class="btn btn-default" onclick="dict_drug_open(2)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
					    </button>
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="dict_drug_data(3)">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					</div>
				</div>
				<table id="dict_table" style="table-layout:fixed;"></table>
			</div>
			<div class="modal-footer">
				<input type="hidden" id="pagelabel" value="-1"/>
				<input type="hidden" id="rowid" value="-1"/>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="queren" type="button" class="btn btn-primary" onclick="dict_drug_modal_yes()">确认</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<!-- 模态框（Modal）字典表 -->
<div class="modal fade" id="dict_route_modal" tabindex="-1" role="dialog"  aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">给药途径字典表</h4>
			</div>
			<div class="modal-body">
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="dict_route_open(1)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <button id="btn_add" type="button" class="btn btn-default" onclick="dict_route_open(2)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
					    </button>
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="dict_route_data(3)">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					</div>
				</div>
				<table id="dict_table" style="table-layout:fixed;"></table>
			</div>
			<div class="modal-footer">
				<input type="hidden" id="pagelabel" value="-1"/>
				<input type="hidden" id="rowid" value="-1"/>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="queren" type="button" class="btn btn-primary" onclick="dict_route_modal_yes()">确认</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<!-- 模态框（Modal）字典表 -->
<div class="modal fade" id="dict_fre_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog"  aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">频次字典表</h4>
			</div>
			<div class="modal-body">
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="dict_fre_open(1)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <!-- <button id="btn_add" type="button" class="btn btn-default" onclick="dict_fre_open(2)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
					    </button> -->
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="dict_fre_data(3)">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					</div>
				</div>
				<table id="dict_table" style="table-layout:fixed;"></table>
			</div>
			<div class="modal-footer">
				<input type="hidden" id="pagelabel" value="-1"/>
				<input type="hidden" id="rowid" value="-1"/>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="queren" type="button" class="btn btn-primary" onclick="dict_fre_modal_yes()">确认</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<!-- 模态框（Modal）字典表 -->
<div class="modal fade" id="dict_dept_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog"  aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">科室字典表</h4>
			</div>
			<div class="modal-body">
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="dict_dept_open(1)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <button id="btn_add" type="button" class="btn btn-default" onclick="dict_dept_open(2)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
					    </button>
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="dict_dept_data(3)">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					</div>
				</div>
				<table id="dict_table" style="table-layout:fixed;"></table>
			</div>
			<div class="modal-footer">
				<input type="hidden" id="pagelabel" value="-1"/>
				<input type="hidden" id="rowid" value="-1"/>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="queren" type="button" class="btn btn-primary" onclick="dict_dept_modal_yes()">确认</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<!-- 模态框（Modal）字典表 -->
<div class="modal fade" id="dict_doctor_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog"  aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">医生字典表</h4>
			</div>
			<div class="modal-body">
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="dict_doctor_open(1)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <button id="btn_add" type="button" class="btn btn-default" onclick="dict_doctor_open(2)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
					    </button>
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="dict_doctor_data(3)">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					</div>
				</div>
				<table id="dict_table" style="table-layout:fixed;"></table>
			</div>
			<div class="modal-footer">
				<input type="hidden" id="pagelabel" value="-1"/>
				<input type="hidden" id="rowid" value="-1"/>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="queren" type="button" class="btn btn-primary" onclick="dict_doctor_modal_yes()">确认</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal）字典表 -->
<div class="modal fade" id="dict_allergen_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog"  aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">过敏原字典表</h4>
			</div>
			<div class="modal-body">
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="dict_allergen_open(1)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <button id="btn_add" type="button" class="btn btn-default" onclick="dict_allergen_open(2)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
					    </button>
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="dict_allergen_data(3)">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					</div>
				</div>
				<table id="dict_table" style="table-layout:fixed;"></table>
			</div>
			<div class="modal-footer">
				<input type="hidden" id="pagelabel" value="-1"/>
				<input type="hidden" id="rowid" value="-1"/>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="queren" type="button" class="btn btn-primary" onclick="dict_allergen_modal_yes()">确认</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal）字典表 -->
<div class="modal fade" id="dict_disease_modal" tabindex="-1"  data-backdrop="static" data-keyboard="false" role="dialog"  aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">疾病字典表</h4>
			</div>
			<div class="modal-body">
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="dict_disease_open(1)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <button id="btn_add" type="button" class="btn btn-default" onclick="dict_disease_open(2)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
					    </button>
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="dict_disease_data(3)">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					</div>
				</div>
				<table id="dict_table" style="table-layout:fixed;"></table>
			</div>
			<div class="modal-footer">
				<input type="hidden" id="pagelabel" value="-1"/>
				<input type="hidden" id="rowid" value="-1"/>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="queren" type="button" class="btn btn-primary" onclick="dict_disease_modal_yes()">确认</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal）字典表 -->
<div class="modal fade" id="dict_operation_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog"  aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">手术字典表</h4>
			</div>
			<div class="modal-body">
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="dict_operation_open(1)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <button id="btn_add" type="button" class="btn btn-default" onclick="dict_operation_open(2)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
					    </button>
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="dict_operation_data(3)">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					</div>
				</div>
				<table id="dict_table" style="table-layout:fixed;"></table>
			</div>
			<div class="modal-footer">
				<input type="hidden" id="pagelabel" value="-1"/>
				<input type="hidden" id="rowid" value="-1"/>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="queren" type="button" class="btn btn-primary" onclick="dict_operation_modal_yes()">确认</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal）字典表 -->
<div class="modal fade" id="dict_exam_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog"  aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">检查字典表</h4>
			</div>
			<div class="modal-body">
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="dict_exam_open(1)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <button id="btn_add" type="button" class="btn btn-default" onclick="dict_exam_open(2)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
					    </button>
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="dict_exam_data(3)">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					</div>
				</div>
				<table id="dict_table" style="table-layout:fixed;"></table>
			</div>
			<div class="modal-footer">
				<input type="hidden" id="pagelabel" value="-1"/>
				<input type="hidden" id="rowid" value="-1"/>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="queren" type="button" class="btn btn-primary" onclick="dict_exam_modal_yes()">确认</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal）字典表 -->
<div class="modal fade" id="dict_lab_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog"  aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">检验字典表</h4>
			</div>
			<div class="modal-body">
				<div id="toolbar" class="btn-group">
					<div class="col-sm-12">
						<button id="btn_add" type="button" class="btn btn-default" onclick="dict_lab_open(1)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					    </button>
					    <button id="btn_add" type="button" class="btn btn-default" onclick="dict_lab_open(2)">
				        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
					    </button>
					    <button id="btn_delete" type="button" class="btn btn-default" onclick="dict_lab_data(3)">
					        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					    </button>
					</div>
				</div>
				<table id="dict_table" style="table-layout:fixed;"></table>
			</div>
			<div class="modal-footer">
				<input type="hidden" id="pagelabel" value="-1"/>
				<input type="hidden" id="rowid" value="-1"/>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="queren" type="button" class="btn btn-primary" onclick="dict_lab_modal_yes()">确认</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="dict_drug_data_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新增药品</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<div class="form-group">
			            <label for="hiscode" class="col-sm-2 control-label">机构名称</label>
			            <div class="col-sm-4">
			            	<select id="hiscode" name="hiscode" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
						<label for="drugcode" class="col-sm-2 control-label">药品编码</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="drugcode"  name="drugcode">
						</div>
					</div>
					<div class="form-group">
						<label for="drug_unique_code" class="col-sm-2 control-label">药品唯一码</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="drug_unique_code"  name="drug_unique_code">
						</div>
					</div>
					<div class="form-group">
						<label for="drugname" class="col-sm-2 control-label">药品名称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="drugname"  name="drugname">
						</div>
					</div>
					<div class="form-group">
						<label for="drugform" class="col-sm-2 control-label">给药途径</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="drugform"  name="drugform">
						</div>
					</div>
					<div class="form-group">
						<label for="drugspec" class="col-sm-2 control-label">药品规格</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="drugspec"  name="drugspec">
						</div>
					</div>
					<div class="form-group">
						<label for="comp_name" class="col-sm-2 control-label">药品厂家</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="comp_name"  name="comp_name">
						</div>
					</div>
					<div class="form-group">
						<label for="doseunit" class="col-sm-2 control-label">给药单位</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="doseunit"  name="doseunit">
						</div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="dict_drug_data(1)">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="dict_drug_data(2)">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="dict_route_data_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新增给药途径</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<div class="form-group">
			            <label for="hiscode" class="col-sm-2 control-label">机构名称</label>
			            <div class="col-sm-4">
			            	<select id="hiscode" name="hiscode" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
						<label for="routecode" class="col-sm-2 control-label">给药途径编码</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="routecode"  name="routecode">
						</div>
					</div>
					<div class="form-group">
						<label for="routename" class="col-sm-2 control-label">给药途径名称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="routename"  name="routename">
						</div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="dict_route_data(1)">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="dict_route_data(2)">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="dict_fre_data_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新增频次</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<div class="form-group">
			            <label for="hiscode" class="col-sm-2 control-label">机构名称</label>
			            <div class="col-sm-4">
			            	<select id="hiscode" name="hiscode" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
						<label for="frequency" class="col-sm-2 control-label">频次</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="frequency"  name="frequency">
						</div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="dict_fre_data(1)">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="dict_fre_data(2)">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="dict_dept_data_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新增科室</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<div class="form-group">
			            <label for="hiscode" class="col-sm-2 control-label">机构名称</label>
			            <div class="col-sm-4">
			            	<select id="hiscode" name="hiscode" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
						<label for="deptcode" class="col-sm-2 control-label">科室编号</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="deptcode"  name="deptcode">
						</div>
					</div>
					<div class="form-group">
						<label for="deptname" class="col-sm-2 control-label">科室名称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="deptname"  name="deptname">
						</div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="dict_dept_data(1)">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="dict_dept_data(2)">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="dict_doctor_data_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新增科室</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<div class="form-group">
			            <label for="hiscode" class="col-sm-2 control-label">机构名称</label>
			            <div class="col-sm-4">
			            	<select id="hiscode" name="hiscode" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
						<label for="doctorcode" class="col-sm-2 control-label">医生编号</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="doctorcode"  name="doctorcode">
						</div>
					</div>
					<div class="form-group">
						<label for="doctorname" class="col-sm-2 control-label">医生名称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="doctorname"  name="doctorname">
						</div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="dict_doctor_data(1)">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="dict_doctor_data(2)">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="dict_allergen_data_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新增过敏原</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<div class="form-group">
			            <label for="hiscode" class="col-sm-2 control-label">机构名称</label>
			            <div class="col-sm-4">
			            	<select id="hiscode" name="hiscode" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
						<label for="allercode" class="col-sm-2 control-label">过敏原编号</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="allercode"  name="allercode">
						</div>
					</div>
					<div class="form-group">
						<label for="allername" class="col-sm-2 control-label">过敏原名称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="allername"  name="allername">
						</div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="dict_allergen_data(1)">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="dict_allergen_data(2)">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="dict_disease_data_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新增科室</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<div class="form-group">
			            <label for="hiscode" class="col-sm-2 control-label">机构名称</label>
			            <div class="col-sm-4">
			            	<select id="hiscode" name="hiscode" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
						<label for="discode" class="col-sm-2 control-label">疾病编号</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="discode"  name="discode">
						</div>
					</div>
					<div class="form-group">
						<label for="disname" class="col-sm-2 control-label">疾病名称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="disname"  name="disname">
						</div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="dict_disease_data(1)">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="dict_disease_data(2)">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="dict_operation_data_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新增科室</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<div class="form-group">
			            <label for="hiscode" class="col-sm-2 control-label">机构名称</label>
			            <div class="col-sm-4">
			            	<select id="hiscode" name="hiscode" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
						<label for="operationcode" class="col-sm-2 control-label">手术编号</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="operationcode"  name="operationcode">
						</div>
					</div>
					<div class="form-group">
						<label for="operationname" class="col-sm-2 control-label">手术名称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="operationname"  name="operationname">
						</div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="dict_operation_data(1)">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="dict_operation_data(2)">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="dict_exam_data_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新增检查</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<div class="form-group">
			            <label for="hiscode" class="col-sm-2 control-label">机构名称</label>
			            <div class="col-sm-4">
			            	<select id="hiscode" name="hiscode" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
						<label for="examcode" class="col-sm-2 control-label">检查编号</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="examcode"  name="examcode">
						</div>
					</div>
					<div class="form-group">
						<label for="examname" class="col-sm-2 control-label">检查编号</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="examname"  name="examname">
						</div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="dict_exam_data(1)">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="dict_exam_data(2)">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="dict_lab_data_modal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新增检验</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<div class="form-group">
			            <label for="hiscode" class="col-sm-2 control-label">机构名称</label>
			            <div class="col-sm-4">
			            	<select id="hiscode" name="hiscode" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
						<label for="labcode" class="col-sm-2 control-label">检验编号</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="labcode"  name="labcode">
						</div>
					</div>
					<div class="form-group">
						<label for="labname" class="col-sm-2 control-label">检验名称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="labname"  name="labname">
						</div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="dict_lab_data(1)">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="dict_lab_data(2)">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</body>
</html>

