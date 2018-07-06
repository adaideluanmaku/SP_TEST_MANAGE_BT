var addurl=null;
$(document).ready(function() {
	addurl=$('#addurl').val();
	_select2();
});

function open_modal_dialog(){
	var address=addurl+'/pass/json_duibi';
		
	var testid=$('#testid').val();
	var testname=$('#testname').val();
	var testout=$('#testout').val();
	var testout_response=$('#testout_response').val();
	var jsontype=$('#jsontype').select2('val');
	var jsonversion=$('#jsonversion').select2('val');
	
	$.ajax({
		type : 'POST',
		url : address,
		async : false,
		cache : true,
		data : {testid:testid,testname:testname,testout:testout,testout_response:testout_response,
			jsontype:jsontype,jsonversion:jsonversion},
		success : function(result) {
			$('#jsonerr').empty();
			var jsonerr=result;
			if(jsonerr.length==0){
				$('#jsonerr').append('<font color="red">未获取到对比结果</font><br>')
			}else{
				for(var i=0;i<jsonerr.length;i++){
					if((i+1)%2==1){
						$('#jsonerr').append('<font color="red">'+jsonerr[i]+'</font><br>')
					}
					if((i+1)%2==0){
						$('#jsonerr').append('<font color="blue">'+jsonerr[i]+'</font><br>')
						$('#jsonerr').append('<p>--------------------------------------------------</p><br>')
					}
				}
			}
			$('#modal_dialog').modal('show');
		},
		error : function(XMLResponse) {
			alert(XMLResponse.responseText)
		}
	});
}

function _select2(){
	var address_select2 = addurl + '/pass/_select2';
	$('#_win #serveradress_a').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#_win"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: address_select2,
//			data: function (params) {//后台查数据
//	             return {
//	            	 teamid:$('#modal_dialog_add_update #anlitype').select2('val')
//	             };
//	        },
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data[0].serverips
				};
			}
		}
	});
	
	var address_select2 = addurl + '/pass/_select2';
	$('#_java #serveradress_b').select2({
		placeholder: "--请选择--",
		allowClear: true,
		dropdownParent: $("#_java"),//modal默认不显示，解决modal显示后下拉单样式问题
		ajax: {
			url: address_select2,
//			data: function (params) {//后台查数据
//	             return {
//	            	 teamid:$('#modal_dialog_add_update #anlitype').select2('val')
//	             };
//	        },
			processResults: function (data) {
			// Tranforms the top-level key of the response object from 'items' to 'results'
				return {
					results: data[0].serverips
				};
			}
		}
	});
	
	$("#jsonversion").select2({data:[{id:'1609',text:'1609'},{id:'1712',text:'1712'}]});
	$("#jsonversion").val('1609').trigger('change');
	
	$("#jsontype").select2({data:[{id:'1',text:'screen'},{id:'2',text:'query'},{id:'3',text:'detail'}]});
	$("#jsontype").val('1').trigger('change');
	
}

function screen_java(){
	var address=addurl+'/pass/screen_java';
	if($('#serveradress_b').select2('val')==null){
		swal("", "请选择请求地址","error");
		return;
	}
	$.ajax({
		type : 'POST',
		url : address,
		async : false,
		cache : true,
		data : {testin:$('#gatherbaseinfo').val(),serverid:$('#serveradress_b').select2('val'),
			testid:$('#testid').val()},
		success : function(result) {
			$('#testout_response').val(result);
			swal("" ,'ok',"success");
		},
		error : function(XMLResponse) {
			alert(XMLResponse.responseText)
		}
	});
}

function screen_win(){
	var address=addurl+'/pass/screen_win';
	if($('#serveradress_a').select2('val')==null){
		swal("", "请选择请求地址","error");
		return;
	}
	$.ajax({
		type : 'POST',
		url : address,
		async : false,
		cache : true,
		data : {testin:$('#gatherbaseinfo').val(),serverid:$('#serveradress_a').select2('val'),
			testid:$('#testid').val()},
		success : function(result) {
			$('#testout').val(result);
			swal("" ,'ok',"success");
		},
		error : function(XMLResponse) {
			alert(XMLResponse.responseText)
		}
	});
}