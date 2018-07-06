$(document).ready(function() {
	$('body').append(
   		 '<div class="modal fade" id="loadingModal">'
   		    +'<div style="width: 200px;height:20px; z-index: 20000; position: absolute; text-align: center; left: 50%; top: 50%;margin-left:-100px;margin-top:-10px">'
   		        +'<div class="progress progress-striped active" style="margin-bottom: 0;">'
   		            +'<div class="progress-bar" style="width: 100%;"></div>'
   		        +'</div>'
   		        +'<h5>正在加载...</h5>'
   		    +'</div>'
   		+'</div>'
    )
    
    //backdrop 为 static 时，点击模态对话框的外部区域不会将其关闭。
    //keyboard 为 false 时，按下 Esc 键不会关闭 Modal。
//    $('#loadingModal').modal({backdrop: 'static', keyboard: false});
});

//增加等待效果
function onloading(){  
	//显示
	$('#loadingModal').modal({backdrop: 'static', keyboard: false});
	$("#loadingModal").modal('show');
}  

//关闭等待效果
function removeload(){  
	//隐藏
	$("#loadingModal").modal('hide');
}  