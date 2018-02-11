$(document).ready(function() {
});

//iframe自适应高度
function iframewh(aheight) {
	$("#iframe_box").attr("style", "height:" + aheight + "px;width:100%")
}

function testmess() {
	//获取iframe里面的JS函数
//	document.getElementById("iframe_box").contentWindow.testmessae();
	$(window.parent.document).contents().find("#iframe_box")[0].contentWindow.testmessae();
}

function learnmess() {
	//获取iframe里面的JS函数
	document.getElementById("iframe_box").contentWindow.learnmessage();
}

/*function learnmes1s1() {
	//获取iframe里面的JS函数
	document.getElementById("iframe_box").contentWindow.learnmessage();

}*/


