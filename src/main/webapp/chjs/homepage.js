var addurl = null;

$(document).ready(function() {
	addurl = $("#addurl").val();
	updateiframe();
});

function updateiframe(){
//	var height=$("#side-menu").height()-100;
//	if(height>790){
//		$("#iframe_box").attr("style","width:100%; height:"+height+"px");
//	}
	
//	alert(window.screen.availWidth)
//	alert(document.body.clientHeight)
//	alert(document.getElementById("bookheader").offsetHeight)
//	alert(document.getElementById("bookcenter").offsetHeight)
//	alert(document.getElementById("bookfooter").offsetHeight)
	var iframe_height = document.body.clientHeight-document.getElementById("bookheader").offsetHeight-document.getElementById("bookfooter").offsetHeight;
//	alert(window.screen.availWidth/160)
	if(window.screen.availWidth/160%1==0){
		iframe_height=iframe_height-10;
	}else{
		iframe_height=iframe_height-30;
	}
	
	$("#iframe_box").attr("style","width:100%; height:"+iframe_height+"px");
	
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

function zfxm_team() {
	$("#iframe_box").attr("src", addurl + "/homepage/zfxm_team");
}

function zfxm_project() {
	$("#iframe_box").attr("src", addurl + "/homepage/zfxm_project");
}

function zfxm_testmng() {
	$("#iframe_box").attr("src", addurl + "/homepage/zfxm_testmng");
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
