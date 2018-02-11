//websocket连接
var websocket;

$(document).ready(function(){
	var addurl=$("#addurl").val();
	//进入页面后自动连接后台
	var userid=Number($("#userid").val());
	var from=userid;
	var fromName=$("#loginname").val();

	
//---------------------------web-socket处理方式----------------------------------
	var path =window.location.host+$("#addurl").val();
	//-------------高级浏览器连接方式--------------
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://" + path + "/ws?userid="+userid);
	} else if ('MozWebSocket' in window) {
		websocket = new MozWebSocket("ws://" + path + "/ws"+userid);
	} else {
		websocket = new SockJS("http://" + path + "/ws/sockjs"+userid);
	}
	
	//开始连接
	websocket.onopen = function(event) {
		console.log("WebSocket:已连接");
		console.log(event);
	};
	//接收消息
	websocket.onmessage = function(event) {
		var data=JSON.parse(event.data);
		console.log("WebSocket:收到一条消息",data);
		
		var loginname=$("#loginname").val()
		var userid=$("#userid").val()
		
		//1:用户发送的消息
		if(data.datatype==1){
			if($("#websocket-div #touid").val() ==data.uid){//当前窗口打开中
				$("#websocket-div #content").append('<label style="color: #0000ff;">'+data.fromName+"&nbsp;"+data.date+'</label><div class="msgtype">'+data.text+'</div>');
				//聊天记录布局处理后,在布局完成后再来调整滚动条
				scrollToBottom();
			}else{
				if(userid==data.touid){
					//未读消息
					$("#user-div #"+data.fromName).css('border-left','7px solid #FF0099');
					if($('#user-div').val()==0){
						//未读消息
						$("#menus1_8 #new").text(data.fromName+"有新消息");
						$("title").text(data.fromName+"有新消息");
					}
				}
			}
		}
		
		//2:用户上线更新用户列表
		if(data.datatype==2){
			console.log(data.users)
			$('#user-div div').remove();
			var data1=data.users;
	    	for(var i=0;i<data1.length;i++){
	    		var user=data1[i];
	    		if(loginname==user.loginname){
	    			continue;
	    		}
	    		
	    		if(user.upanddown==1){
	    			//上线用户
	    			$('#user-div').append(
	    					'<div id="'+user.loginname+'" style="width:100%;height:30px;text-align: center; padding-top: 5px;font-size: 20px;cursor: pointer;">'
	    					+'<input type="hidden" id="user-toid" value="'+user.userid+'">'
	    					+'<label style="color:#e28d8d">'+user.loginname+'</label>'
	    					+'</div>'
	    				)
	    		}else{
	    			//下线用户
	    			$('#user-div').append(
	    					'<div id="'+user.loginname+'" style="width:100%;height:30px;text-align: center; padding-top: 5px;font-size: 20px;cursor: pointer; ">'
	    					+'<input type="hidden" id="user-toid" value="'+user.userid+'">'
	    					+'<label >'+user.loginname+'</label>'
	    					+'</div>'
	    				)
	    		}
			}
	    	
	    	//给未读信息做标记
    		$.ajax({
				type:"POST",
				url:addurl+"/websocket/messagesstate",
				async:true,
				cache:true,
				data:{userid:userid},
				success: function(result){
					var user_message=result.user_message;
					var sys_message=result.sys_message;
					//某个用户未读消息
					for(var i=0;i<user_message.length;i++){
						var user=user_message[i];
						$("#user-div #"+user.loginname).css('border-left','7px solid #FF0099');
					}
					//系统未读消息
			    	if(user_message.length>0){
			    		$("#menus1_8 #new").text("有新消息");
						$("title").text("有新消息");
					}
					//系统未读消息
			    	if(sys_message>0){
						//系统未读消息
						$("#menus1_9 #new").text("系统有新通知");
					}
				},
				error:function(XMLResponse){
					alert(XMLResponse.responseText)
				}
			});
			return false;
			
		}
		
		//3:接收系统广播
		if(data.datatype==3){
//			if($('#broadcast-div').val()==1){
//				$("#broadcast-div #content1").append('<label style="color: #0000ff;">系统消息&nbsp;'+data.date+'</label><div class="msgtype">'+data.text+'</div>');
//				//聊天记录布局处理后,在布局完成后再来调整滚动条
//				scrollToBottom1();
//			}else{
//				//按钮提示未读消息
//				$("#menus1_9 #new").text("系统有新通知");
//				$("title").text("系统有新通知");
//			}
			if($('#broadcast-div').val() != 1){
				//按钮提示未读消息
				$("#menus1_9 #new").text("系统有新通知");
				$("title").text("系统有新通知");
			}else{
				$("#broadcast-div #content1").append('<label style="color: #0000ff;">系统消息&nbsp;'+data.date+'</label><div class="msgtype">'+data.text+'</div>');
//				//聊天记录布局处理后,在布局完成后再来调整滚动条
				scrollToBottom1();
			}
		}
		
		//4:系统推送，强制刷新用户界面
		if(data.datatype==4){
			if(fromName=='admin'){
				return;
			}
			
			//默认为 false，从客户端缓存里取当前页。true, 则以 GET 方式，从服务端取最新的页面, 相当于客户端点击 F5("刷新")
//			location.reload([false]);
			location.replace(document.referrer);
		}
		
		//5:通知news页面
		if(data.datatype==5){
			$("#menus1_1 #log").text("有新消息");
		}
		
		//6:通知works页面
		if(data.datatype==6){
			$("#menus1_7 #work").text("有新消息");
		}
		
		//7:用户给自己传递的消息
		if(data.datatype==7){
//			data_json=data.text;
			aller(data.text);
		}
		
	};
	
	//异常
	websocket.onerror = function(event) {
		console.log("WebSocket:发生错误 ");
		console.log(event);
	};
	//关闭连接
	websocket.onclose = function(event) {
		console.log("WebSocket:已关闭");
		console.log(event);
	}
	
//-------------------------------所有的窗口功能------------------------------
	
	//USER列表窗口
//	$('#user-div').dialog(
//		{    
//		title: '用户列表',    
//	    width: 200,    
//	    height: 600,  
//	    left:1200,
//	    closed: true,//true窗口关闭，false窗口打开
//	    modal:false,//弹出后，只能操作本窗口
//	    buttons:[{
//			text:'关闭',
//			handler:function(){
//				$('#user-div').val(0)
//				$('#user-div').dialog({closed: true});
//			}
//		}],
//		//X关闭增加自己的功能
//		onClose:function(){
//			$('#user-div').val(0)
//		},
//	});
	
	//websocket-对话窗口
//	$('#websocket-div').dialog(
//		{    
//		title: '聊天室',    
//	    width: 600,    
//	    height: 500,    
//	    closed: true,//true窗口关闭，false窗口打开
//	    modal:false,//弹出后，只能操作本窗口
//	    buttons:[{
//			text:'关闭',
//			handler:function(){
//				$("#websocket-div #touid").val("");
//				$('#websocket-div').dialog({closed: true});
//			}
//		}],
//		//X关闭增加自己的功能
//		onClose:function(){
//			$("#websocket-div #touid").val("");
//		},
//	});
	
	//easy-ui 键盘回车事件,keyup键盘弹起事件，keydown键盘按下事件
//	$("#usermsg").textbox('textbox').bind('keyup', function(e){
//		if (e.keyCode == 13){	// 当按下回车键时接受输入的值。
//			sendMsg();
//		}
//	});
	
	//获取某个用户的聊天记录
//	$('#user-div').on('click','div',function(){
//		//先清空数据
//		$("#websocket-div #content").empty();
//		//已读消息
//		$(this).css('border-left','');
//		
//		$('#websocket-div').dialog({closed: true});
//		
//		var userid=$('#userid').val();
//		var touid=$(this).children('#user-toid').val();
//		$('#user-div div').css("background-color","");
//		$(this).css("background-color","#c1c1c1");
//		
//		$("#websocket-msg-touser").text("与 "+$(this).text()+" 聊天");
//		if(userid !=touid){
//			$('#touid').val(touid);
//			
//			$.ajax({
//				type:"POST",
//				url:addurl+"/websocket/messages",
//				async:true,
//				cache:true,
//				data:{userid:userid,touserid:touid},
//				success: function(result){
//					var result=eval(result)
//					for(var i=0;i<result.length;i++){
//						var data=result[i];
//						if(userid==data.userid){
//							$("#websocket-div #content").append('<label style="color: #a5c35a;">我&nbsp;'+new Date().Format("yyyy-MM-dd hh:mm:ss")+'</label><div class="msgtype">'+data.message+'</div>');
//						}else{
//							$("#websocket-div #content").append('<label style="color: #0000ff;">'+data.loginname+"&nbsp;"+data.inserttime+'</label><div class="msgtype">'+data.message+'</div>');
//						}
//					}
//					$('#websocket-div').dialog({closed: false});
//					//聊天记录布局处理后,在布局完成后再来调整滚动条
//					scrollToBottom();
//				},
//				error:function(XMLResponse){
//					alert(XMLResponse.responseText)
//				}
//			});
//			return false;
//		}
//	});
	
//	//广播-对话窗口
//	$('#broadcast-div').dialog(
//		{    
//		title: '系统消息',    
//	    width: 600,    
//	    height: 500,    
//	    closed: true,//true窗口关闭，false窗口打开
//	    modal:false,//弹出后，只能操作本窗口
//	    buttons:[{
//			text:'关闭',
//			handler:function(){
//				$('#broadcast-div').val(0)
//				$('#broadcast-div').dialog({closed: true});
//			}
//		}],
//		//X关闭增加自己的功能
//		onClose:function(){
//			$('#broadcast-div').val(0)
//		},
//	});
	
	//如果组件被隐藏后，JS找不到页面组件会报错，增加对用户的判断来决定是否执行这段代码
//	if($("#loginname").val() == "admin"){
//		$("#usermsg1").textbox('textbox').bind('keyup', function(e){
//			if (e.keyCode == 13){	// 当按下回车键时发送输入的值。
//				sendsysMsg();
//			}
//		});
//	}
	
});


////向后台发送消息给某个用户
//function sendMsg(){
//	var addurl=$("#addurl").val();
//	var userid=Number($("#userid").val());
//	if(userid==-1){
//		location.href=path;
//	}
//	var loginname=$("#loginname").val();
//	var touid=$('#websocket-div #touid').val();
//	var msg=$("#websocket-div #usermsg").textbox('getValue');
//	
//	if(getByteLen(msg)==0){
//		return;
//	}
//	$.ajax({
//		type:"POST",
//		url:addurl+"/websocket/touser",
//		async:true,
//		cache:true,
//		data:{userid:userid,fromName:loginname,touid:touid,text:msg},
//		success: function(result){
//			$("#websocket-div #content").append('<label style="color: #a5c35a;">我&nbsp;'+new Date().Format("yyyy-MM-dd hh:mm:ss")+'</label><div class="msgtype">'+msg+'</div>');
//			$("#websocket-div #usermsg").textbox('setValue','');
//			
//			//聊天记录布局处理后,在布局完成后再来调整滚动条
//			scrollToBottom();
//		},
//		error:function(XMLResponse){
//			alert(XMLResponse.responseText)
//		}
//	});
//	return false;
//}
//
////向后台发送消息给全部用户
//function sendsysMsg(){
//	var addurl=$("#addurl").val();
//	var userid=Number($("#userid").val());
//	var loginname=$("#loginname").val();
//	var msg=$("#broadcast-div #usermsg1").textbox('getValue');
//	if(getByteLen(msg)==0){
//		return;
//	}
//	$.ajax({
//		type:"POST",
//		url:addurl+"/websocket/broadcasts",
//		async:true,
//		cache:true,
//		data:{userid:userid,loginname:loginname,text:msg},
//		success: function(result){
////			$("#broadcast-div #content1").append('<label style="color: #0000ff;">系统消息&nbsp;'+data.date+'</label><div class="msgtype">'+data.text+'</div>');
////			//聊天记录布局处理后,在布局完成后再来调整滚动条
////			scrollToBottom1();
//		},
//		error:function(XMLResponse){
//			alert(XMLResponse.responseText)
//		}
//	});
//	
//	$("#broadcast-div #usermsg1").textbox('setValue','');
//	return false;
//}

//向后台发送消息，通过推送消息到用户并强制刷新用户页面
function sendsysreload(){
	var addurl=$("#addurl").val();
	$.ajax({
		type:"POST",
		url:addurl+"/websocket/sysreload",
		async:true,
		cache:true,
		data:{},
		success: function(result){
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	});
	return false;
}

//获取键盘事件 暂时没用
function key_send(event){
	var code;
	 if(window.event){
		 code = window.event.keyCode; // IE
	 }else{
		 code = e.which; // Firefox
	 }
	if(code==13){ 
		return true;        
	}
}

//滚动条置低布局改变
function scrollToBottom(){
	var div = document.getElementById('content');
	div.scrollTop = div.scrollHeight;
}
function scrollToBottom1(){
	var div = document.getElementById('content1');
	div.scrollTop = div.scrollHeight;
}
//格式化日期
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//清空数据
function clearAll(){
	$("#websocket-div #content").empty();
}

//字符和汉字长度算法,最总换算成字符长度
function getByteLen(val) {
	var sum=1;
	for(var i=0;i<val.length;i++){
		if(val[i]==' '){
			sum=i+1;
		}
	}
	if(sum==val.length){
		return 0;
	}
	
	if(val==null){
		return 0;
	}
	
	var len = 0;
	for (var i = 0; i < val.length; i++) {
		var a = val.charAt(i);
		if (a.match(/[^\x00-\xff]/ig) != null) {
			len += 2;
		}else {
			len += 1;
		}
	}
	return len;
}
