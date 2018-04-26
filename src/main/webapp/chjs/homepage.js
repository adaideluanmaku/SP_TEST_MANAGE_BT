var addurl = null;

$(document).ready(function() {
	addurl = $("#addurl").val();

});

function updateiframe(){
	var height=$("#side-menu").height()-100;
	if(height>790){
		$("#iframe_box").attr("style","width:100%; height:"+height+"px");
	}
}
// 首页目录页面跳转
function home_graph() {
	$("#iframe_box").attr("src", addurl + "/homepage/home_graph");
}

// PASS目录页面跳转
function pass_graph() {
	$("#iframe_box").attr("src", addurl + "/homepage/pass_graph");
}

function pass_team() {
	$("#iframe_box").attr("src", addurl + "/homepage/pass_team");
}

function pass_project() {
	$("#iframe_box").attr("src", addurl + "/homepage/pass_project");
}

function pass_testmng() {
	$("#iframe_box").attr("src", addurl + "/homepage/pass_testmng");
}

function pass_tools() {
	$("#iframe_box").attr("src", addurl + "/homepage/pass_tools");
}
// PA目录页面跳转
function pa_graph() {
	$("#iframe_box").attr("src", addurl + "/homepage/pa_graph");
}

function pa_team() {
	$("#iframe_box").attr("src", addurl + "/homepage/pa_team");
}

function pa_project() {
	$("#iframe_box").attr("src", addurl + "/homepage/pa_project");
}

function pa_testmng() {
	$("#iframe_box").attr("src", addurl + "/homepage/pa_testmng");
}

//工作目录页面跳转
function prescription() {
	$("#iframe_box").attr("src", addurl + "/homepage/prescription");
}

// 政府项目目录页面跳转
function zfxm_graph() {
	$("#iframe_box").attr("src", addurl + "/homepage/zfxm_graph");
}

// 学习目录页面跳转
function learn_graph() {
	$("#iframe_box").attr("src", addurl + "/homepage/learn_graph");
}

// 工作目录页面跳转
function works_graph() {
	$("#iframe_box").attr("src", addurl + "/homepage/works_graph");
}

//系统管理页面跳转
function sysmanage_serverip() {
	$("#iframe_box").attr("src", addurl + "/homepage/sysmanage_serverip");
}
function sysmanage_users() {
	$("#iframe_box").attr("src", addurl + "/homepage/sysmanage_users");
}
