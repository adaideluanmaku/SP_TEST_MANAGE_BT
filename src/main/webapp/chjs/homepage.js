var addurl=null;

$(document).ready(function() {
	addurl=$("#addurl").val();
	
});

//首页目录页面跳转
function home_graph(){
	$("#iframe_box").attr("src",addurl+"/homepage/home_graph");
}

//PASS目录页面跳转
function pass_graph(){
	$("#iframe_box").attr("src",addurl+"/homepage/pass_graph");
}

function pass_team(){
	$("#iframe_box").attr("src",addurl+"/homepage/pass_team");
}

//PA目录页面跳转
function pa_graph(){
	$("#iframe_box").attr("src",addurl+"/homepage/pa_graph");
}

//政府项目目录页面跳转
function zfxm_graph(){
	$("#iframe_box").attr("src",addurl+"/homepage/zfxm_graph");
}

//学习目录页面跳转
function learn_graph(){
	$("#iframe_box").attr("src",addurl+"/homepage/learn_graph");
}

//工作目录页面跳转
function works_graph(){
	$("#iframe_box").attr("src",addurl+"/homepage/works_graph");
}

