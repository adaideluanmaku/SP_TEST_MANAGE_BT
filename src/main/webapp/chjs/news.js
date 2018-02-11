$(document).ready(function() {
	//默认打开的数据
	testmessae();
});

function testmessae() {
	$.ajax({
		type : "POST",
		url : $("#addurl").val() + "/log/testmessage",
		async : false,
		data : {},
		success : function(result) {
			$("#news #new_boxs").remove();
			var aa = result.testmng.length;
			var ccc = '<div class="wrapper wrapper-content animated fadeInRight" id="new_boxs">';
			for (var i = 0; i < aa; i++) {// result.testmng.length
				// var testmng = result.testmng[i]
				if (aa <= 500) {
					ccc= ccc +
					'<div class="row">'
					+ '<div class="col-lg-12">'
					+ '<div class="ibox">'
					+ '<div class="ibox-content">'
					+ '<h2>'
					+ '<font style="vertical-align: inherit;">'
					+ '<font style="vertical-align: inherit;"> '
					+ i
					+ '工程名： </font> testmng.projectname'
					+ '<font style="vertical-align: inherit;"> 修改时间： </font>testmng.inserttime'
					+ '</font>'
					+ '</h2>'
					+ '<p>'
					+ '<font style="vertical-align: inherit;">'
					+ '<font style="vertical-align: inherit;"> 案例名称:testmng.testname</font>'
					+ '<font style="vertical-align: inherit;">案例编号:testmng.testno</font>'
					+ '<font style="vertical-align: inherit;">逻辑描述: testmng.testtext</font>'
					+ '</font>' + '</p>'
					+ '</div>' + '</div>'
					+ '</div>' + '</div>';

				} else {
					ccc = ccc +
					'<div class="row">'
					+ '<div class="col-lg-12">'
					+ '<div class="ibox">'
					+ '<div class="ibox-content">'
					+ '<h2>'
					+ '<font style="vertical-align: inherit;">'
					+ '<font style="vertical-align: inherit;"> 工程名： </font> testmng.projectname'
					+ '<font style="vertical-align: inherit;"> 修改时间： </font>testmng.inserttime'
					+ '</font>'
					+ '</h2>'
					+ '<p>'
					+ '<font style="vertical-align: inherit;">'
					+ '<font style="vertical-align: inherit;"> 案例名称:testmng.testname</font>'
					+ '<font style="vertical-align: inherit;">案例编号:testmng.testno</font>'
					+ '<font style="vertical-align: inherit;">逻辑描述: testmng.testtext.substring(0, 500)</font>'
					+ '</font>' + '</p>'
					+ '</div>' + '</div>'
					+ '</div>' + '</div>';
					}

				}
			
				ccc = ccc + '</div>' 
				$("#news").append(ccc)
				// 更新父类的iframe高度
				parent.iframewh(logheight());
			},
			error : function(XMLResponse) {
				alert(XMLResponse.responseText)
			}
		})
		return false;
};

function learnmessage(){
	$.ajax({
		type:"POST",
		url:$("#addurl").val()+"/log/learnmessage",
		async:false,
		data:{},
		success: function(result){
			$("#news #new_boxs").remove();
			var aa = result.learn.length;
			var ccc = '<div class="wrapper wrapper-content animated fadeInRight" id="new_boxs">';
			for(var i=0; i<aa;i++){
				var learn=result.learn[i]
				if(aa<=500){
					ccc = ccc +
					'<div class="row">'
					+ '<div class="col-lg-12">'
					+ '<div class="ibox">'
					+ '<div class="ibox-content">'
					+ '<h2>'
					+ '<font style="vertical-align: inherit;">'
					+ '<font style="vertical-align: inherit;"> '
					+ i
					+ '分类名称： </font> learn.learngroup'
					+ '<font style="vertical-align: inherit;"> 修改时间： </font>learn.inserttime'
					+ '</font>'
					+ '</h2>'
					+ '<p>'
					+ '<font style="vertical-align: inherit;">'
					+ '<font style="vertical-align: inherit;"> 标题:learn.learnname</font>'
					+ '<font style="vertical-align: inherit;">内容:learn.learntext</font>'
					+ '<font style="vertical-align: inherit;">逻辑描述: testmng.testtext</font>'
					+ '</font>' + '</p>'
					+ '</div>' + '</div>'
					+ '</div>' + 
//							+'<input type="hidden" value="'+learn.learntext+'">'+
					'</div>';
				}else{
					ccc = ccc +
					'<div class="row">'
					+ '<div class="col-lg-12">'
					+ '<div class="ibox">'
					+ '<div class="ibox-content">'
					+ '<h2>'
					+ '<font style="vertical-align: inherit;">'
					+ '<font style="vertical-align: inherit;"> '
					+ i
					+ '分类名称： </font> learn.learngroup'
					+ '<font style="vertical-align: inherit;"> 修改时间： </font>learn.inserttime'
					+ '</font>'
					+ '</h2>'
					+ '<p>'
					+ '<font style="vertical-align: inherit;">'
					+ '<font style="vertical-align: inherit;"> 标题:learn.learnname</font>'
					+ '<font style="vertical-align: inherit;">内容:learn.learntext</font>'
					+ '<font style="vertical-align: inherit;">逻辑描述: learn.learntext.substring(0,500)</font>'
					+ '</font>' + '</p>'
					+ '</div>' + '</div>'
					+ '</div>' + 
	//							+'<input type="hidden" value="'+learn.learntext+'">'+
					'</div>';
				}
			}
			ccc = ccc + '</div>'
			$("#news").append(ccc);
//			$(".box_2 #num2").val(result.num);
			
			// 更新父类的iframe高度
			parent.iframewh(logheight());
		},
		error:function(XMLResponse){
			alert(XMLResponse.responseText)
		}
	})
	return false;
};

// 获取页面元素高度和宽度
function logheight() {
	var o = document.getElementById("new_boxs");
	var h = o.offsetHeight; // 高度
	var w = o.offsetWidth; // 宽度

	if (h < 700) {
		$("#new_boxs").attr("style", "min-height:690px");
		h = 690;
	}

	return h;
};