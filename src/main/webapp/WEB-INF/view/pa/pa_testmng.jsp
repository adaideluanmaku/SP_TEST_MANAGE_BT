<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>INSPINIA | project</title>
<!-- SYS CSS -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap_home/css/style.css" rel="stylesheet">

<!-- SYS JS -->
<!-- 引入 Bootstrap -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/js/jquery-3.2.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-table/bootstrap-table.js"></script>
<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-table/bootstrap-table.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-table/bootstrap-table-zh-CN.js"></script>

<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-dialog/bootstrap-dialog.min.css" rel="stylesheet" >
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-dialog/bootstrap-dialog.min.js"></script>
    
<!-- bootstrapValidator -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap_home/bootstrapValidator/css/bootstrapValidator.css"/>
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrapValidator/js/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrapValidator/js/language/zh_CN.js"></script>

<!-- bootstrap-datetimepicker -->
<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />  
<script src="${pageContext.request.contextPath}/bootstrap_home/moment/min/moment-with-locales.min.js"></script>  
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>  

<!-- Sweet alert -->
<link href="${pageContext.request.contextPath}/bootstrap_home/css/sweetalert.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap_home/js/plugins/sweetalert/sweetalert.min.js"></script>

<!-- select2 -->
<link href="${pageContext.request.contextPath}/bootstrap_home/bootstrap-select2/css/select2.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-select2/js/select2.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap_home/bootstrap-select2/js/i18n/zh-CN.js"></script>


<!-- MY JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/pa/pa_testmng.js"></script>

</head>
<body style="background-color: #ffffff;">
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">

<div id="wrapper" >
	<div  class="gray-bg">
		<div class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				<ol class="breadcrumb">
					<li><a>home</a></li>
					<li><a>PA测试管理</a></li>
					<li class="active"><strong>案例管理</strong></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
		</div>
		<div class="animated fadeInRight">
			<div id="table_div">
				<!-- 列表表格-开始制作 -->
				<div class="panel-body" style="padding-bottom:0px;">
					<div class="panel panel-default">
					    <div class="panel-heading">查询条件</div>
					    <div class="panel-body">
					    	<div class="row">
					    		<div class="col-sm-3">
					    			<label class="control-label col-sm-4" for="txt_search_departmentname" style="padding-top:7px">案例名称</label>
					                <div class="col-sm-8">
					                    <input type="text" class="form-control" id="searchdata">
					                </div>
					    		</div>
					    		<div class="col-sm-3">
					    			<label for="teamid_search" class="col-sm-4 control-label" style="padding-top:7px">团队名称</label>
						            <div class="col-sm-8">
						            	<select id='teamid_search' name="teamid_search" class="js-data-example-ajax" style="width:100%"></select>
						            </div>
					    		</div>
					    		<div class="col-sm-3">
					    			<label for="projectid" class="col-sm-4 control-label" style="padding-top:7px">项目名称</label>
						            <div class="col-sm-8">
						            	<select id='projectid_search' name="projectid_search" class="js-data-example-ajax" style="width:100%"></select>
						            </div>
					    		</div>
					    		<div class="col-sm-3">
					    			<div class="col-sm-3" style="text-align: left;">
					                    <button type="button" id="btn_query" class="btn btn-primary" onclick="table_search()">查询</button>
					                </div>
					    		</div>
					    	</div>
					    </div>
					</div>  
					<div id="toolbar" class="btn-group">
						<div class="col-sm-12">
							<button id="btn_add" type="button" class="btn btn-default" onclick="open_dialog()">
					        	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
						    </button>
						    <button id="btn_edit" type="button" class="btn btn-default" onclick="edit_dialog()">
						        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
						    </button>
						    <button id="btn_delete" type="button" class="btn btn-default" onclick="del_data()">
						        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
						    </button>
						    <div  class="btn-group">
								<button id="dropdownMenu1" type="button"
									class="btn btn-default dropdown-toggle" data-toggle="dropdown"
									aria-haspopup="true" aria-expanded="true">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>工具
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
									<li><a href="#" onclick="open_modal_dialog_serverip_all()">审查所有案例</a></li>
									<li><a href="#" onclick="open_modal_dialog_serverip_one()">审查单个案例</a></li>
									<li><a href="#" onclick="open_modal_dialog_serverip_one_win()">.net审查单个案例</a></li>
									<li><a href="#" onclick="pa_redis_clear()">清空redis缓存</a></li>
									<li><a href="#" onclick="open_modal_dialog_HIS_data()">DATA TO ORACLE</a></li>
									<li><a href="#">Another action</a></li>
									<li><a href="#">Something else here</a></li>
									<li role="separator" class="divider"></li>
									<li><a href="#">Separated link</a></li>
								</ul>
							</div>
						</div>
					</div>
					<!-- 增加表格样式：style="table-layout: fixed"时，设置列宽才能生效 -->
					<table id="table_data" style="table-layout:fixed; "></table>
				</div>
				<!-- 数据表格-结束 -->
			</div>
		</div>
	</div>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="modal_dialog" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">团队维护</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<form class="form-horizontal" role="form" id="dialog_form">
					<input id="testid" type="hidden" name="testid" value="">
					<div class="form-group">
			            <label for="teamid" class="col-sm-2 control-label">团队名称</label>
			            <div class="col-sm-4">
			            	<select id='teamid' name="teamid" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
					<div class="form-group">
			            <label for="projectid" class="col-sm-2 control-label">项目名称</label>
			            <div class="col-sm-4">
			            	<select id='projectid' name="projectid" class="js-data-example-ajax" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
			            <label for="status" class="col-sm-2 control-label">案例状态</label>
			            <div class="col-sm-4">
			            	<select id='status' name="status" class="js-example-responsive" style="width:100%"></select>
			            </div>
			        </div>
			        <div class="form-group">
			            <label for="selenium_share_status" class="col-sm-2 control-label">selenium公共脚本</label>
			            <div class="col-sm-4">
			            	<select id='selenium_share_status' name="selenium_share_status" class="js-example-responsive" style="width:100%"></select>
			            </div>
			        </div>
					<div class="form-group">
						<label for="testname" class="col-sm-2 control-label">案例名称</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="testname"  name="testname">
						</div>
					</div>
					<div class="form-group">
						<label for="testno" class="col-sm-2 control-label">案例编号 :1-1(格式范例)</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="testno"  name="testno">
						</div>
					</div>
					<div class="form-group">
						<label for="testtext" class="col-sm-2 control-label">逻辑描述</label>
						<div class="col-sm-10">
							<textarea class="col-sm-12" rows="10" id="testtext" name="testtext"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="testin" class="col-sm-2 control-label">输入条件</label>
						<div class="col-sm-10">
							<textarea class="col-sm-12" rows="10" id="testin" name="testin"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="testout" class="col-sm-2 control-label">预期结果</label>
						<div class="col-sm-10">
							<textarea class="col-sm-12" rows="10" id="testout" name="testout"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="remark" class="col-sm-2 control-label">备注</label>
						<div class="col-sm-10">
							<textarea class="col-sm-12" rows="10" id="remark" name="remark"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="testout_response" class="col-sm-2 control-label">实际结果</label>
						<div class="col-sm-10">
							<textarea class="col-sm-12" rows="10" id="testout_response" name="testout_response" readonly></textarea>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="add_data()">确定</button>
				<button type="button" class="btn btn-primary" id="btn_update" onclick="update_data()">更新</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="modal_dialog_HIS_data" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">团队维护</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<div class="row" style="margin-bottom : 15px">
					<h4>将数据导入到ORACLE数据库，模拟HIS数据。</h4><br>
					<h4>N家HISCODE*数据集*单天循环次数*天数=总数</h4>
				</div>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-6">
				      	<div class="input-group">
				            <span class="input-group-addon">N家机构</span>
				            <input type="text" class="form-control" id="hiscodes1" name="hiscodes1">
				        </div>
				    </div>
				    <div class="col-sm-6">
				    	范例：单：HISCODE001。多：HISCODE001,HISCODE002
				    </div>
				</div>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-6">
				      	<div class="input-group">
				            <span class="input-group-addon">开始时间</span>
				            <!--指定 date标记-->  
				            <div class="input-group date" id="datetime1" name="datetime1">  
				                <input type="text" class="form-control" />  
				                <span class="input-group-addon">  
				                    <span class="glyphicon glyphicon-calendar"></span>  
				                </span>  
				            </div>  
				        </div>
				    </div>
				     <div class="col-sm-3" style="margin-top : 7px">
				    	范例：2012-01-01
				    </div>
				</div>
				<hr>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-8">
				      	<!-- <div class="input-group">
				            <span class="input-group-addon">循环1次数据集</span>
				            <select class="form-control m-b" name="anlisum" id="anlisum">
	                            <option value="1" selected="selected"><font style="vertical-align: inherit;">23条PASS各模块案例</font></option>
	                            <option value="2"><font style="vertical-align: inherit;">2235条PASS全案例</font></option>
	                            <option value="3"><font style="vertical-align: inherit;">343条PA全案例</font></option>
	                        </select>
				        </div> -->
				        <div class="input-group">
				            <span class="input-group-addon">循环1次数据集</span>
				            <select id='projectid' name="projectid" class="form-control m-b js-data-example-ajax" style="width:100%"></select>
			            </div>
				    </div>
				</div>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-6">
				      	<div class="input-group">
				            <span class="input-group-addon">单天循环次数</span>
				            <input type="text" class="form-control" id="count1" name="count1">
				        </div>
				    </div>
				    <div class="col-sm-6">
				      	<div class="input-group">
				            <span class="input-group-addon">循环天数</span>
				            <input type="text" class="form-control" id="sum_date1" name="sum_date1">
				        </div>
				    </div>
				</div>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-8">
				      	<div class="input-group">
				            <span class="input-group-addon">重新创建所有表和结构</span>
				            <select class="form-control m-b" name="createTB1" id="createTB1">
	                            <option value="0" selected="selected"><font style="vertical-align: inherit;">关</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">开</font></option>
	                        </select>
				        </div>
				    </div>
				</div>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-8">
				      	<div class="input-group">
				            <span class="input-group-addon">先清空业务表</span>
				            <select class="form-control m-b" name="trunca1" id="trunca1">
	                            <option value="0" selected="selected"><font style="vertical-align: inherit;">关</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">开</font></option>
	                        </select>
				        </div>
				    </div>
				</div>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-8">
				      	<div class="input-group">
				            <span class="input-group-addon">创建视图</span>
				            <select class="form-control m-b" name="createview1" id="createview1">
	                            <option value="0" selected="selected"><font style="vertical-align: inherit;">关</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">开</font></option>
	                        </select>
				        </div>
				    </div>
				</div>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-8">
				      	<div class="input-group">
				            <span class="input-group-addon">门诊数据</span>
				            <select class="form-control m-b" name="mz1" id="mz1">
	                            <option value="0" selected="selected"><font style="vertical-align: inherit;">关</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">开</font></option>
	                        </select>
				        </div>
				    </div>
				</div>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-8">
				      	<div class="input-group">
				            <span class="input-group-addon">住院数据</span>
				            <select class="form-control m-b" name="zy1" id="zy1">
	                            <option value="0" selected="selected"><font style="vertical-align: inherit;">关</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">开</font></option>
	                        </select>
				        </div>
				    </div>
				</div>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-8">
				      	<div class="input-group">
				            <span class="input-group-addon">出院数据</span>
				            <select class="form-control m-b" name="cy1" id="cy1">
	                            <option value="0" selected="selected"><font style="vertical-align: inherit;">关</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">开</font></option>
	                        </select>
				        </div>
				    </div>
				</div>
				<hr>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-6">
				      	<div class="input-group">
				            <span class="input-group-addon">配对方案编号</span>
				            <input type="text" class="form-control" id="match_scheme1" name="match_scheme1">
				        </div>
				    </div>
				    <div class="col-sm-6">
				    	范例：单：4 。多：4,5,6
				    </div>
				</div>
				<div class="row" style="margin-bottom : 15px">
					<div class="col-sm-8">
				      	<div class="input-group">
				            <span class="input-group-addon">字典表数据</span>
				            <select class="form-control m-b" name="dict1" id="dict1">
	                            <option value="0" selected="selected"><font style="vertical-align: inherit;">关</font></option>
	                            <option value="1"><font style="vertical-align: inherit;">开</font></option>
	                        </select>
				        </div>
				    </div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add" onclick="create_data()">制作数据</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="modal_dialog_serverip" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">选择审查地址</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<div class="row">
					<div class="col-sm-12" style="margin-bottom:15px">
						<!-- <div class="form-group">
				            <label for="serverid" class="col-sm-3 control-label">服务地址名称</label>
				            <div class="col-sm-4">
				            	<select id='serverid' name="serverid" class="js-data-example-ajax" style="width:100%"></select>
				            </div>
				        </div> -->
				        
				        <!-- 增加表格样式：style="table-layout: fixed"时，设置列宽才能生效 -->
						<table id="table_data" style="table-layout:fixed; "></table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="btn_add1" onclick="screen_all()">审查全部</button>
				<button type="button" class="btn btn-primary" id="btn_add2" onclick="screen_one()">审查勾选</button>
				<button type="button" class="btn btn-primary" id="btn_add3" onclick="screen_one_win()">审查勾选</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="pa_result_modal_dialog" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" height="600px">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">json对比</h4>
			</div>
			<div class="modal-body" style="height:400px;overflow:auto">
				<div id="jsonerr"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

</body>
</html>

