var addurl=null;
$(document).ready(function() {
	addurl=$("#addurl").val();
});
/***************************************************
 *初始化函数
 ***************************************************/
function MCInit() {
	if($('#HospID').val()==''){
		swal({
            title: "提示",
            text: "请输入机构编码"
        });
		return
	}
	var pass = new Params_MC_PASSclient_In();
	pass.HospID = $('#HospID').val(); //医院编码           
	pass.UserID = $('#aa #DoctorCode').val();
	pass.UserName = "";
	pass.DeptID = "";
	pass.DeptName = "";
	pass.CheckMode = MC_global_CheckMode;
	MCPASSclient = pass;
}
/***************************************************
/*查询函数
 ***************************************************/
function HisQueryData(IdSelections) {
	var drug = new Params_MC_queryDrug_In();
	drug.ReferenceCode = IdSelections.DrugUniqueCode; // 药品编号
	drug.CodeName = IdSelections.DrugName; // 药品名称
	drug.CodeSource=IdSelections.DrugSource;
	MC_global_queryDrug = drug;
}

function HisQueryDataChm(IdSelections) {
	var drug = new Params_MC_queryDrug_In();
	drug.ReferenceCode = IdSelections.DrugUniqueCode; // 药品编号
	drug.CodeName = IdSelections.DrugName; // 药品名称
	drug.CodeSource=IdSelections.DrugSource;
	MC_global_queryDrug = drug;
}

/***************************************************
 *审查函数
 ***************************************************/
function HisScreenData() {
	/***************************************************************************
	 * 患者基本信息
	 **************************************************************************/
	var patient = new Params_MC_Patient_In();
	patient.PatCode = $('#patientmessage #PatCode').val(); // 编号
	patient.InHospNo = $('#patientmessage #InHospNo').val(); // 门诊号/住院号
	patient.VisitCode = $('#patientmessage #VisitCode').val(); // 门诊号/住院次数
	patient.Name = $('#patientmessage #Name').val(); // 姓名
	patient.Sex = $('#patientmessage #Sex').val(); // 性别
	patient.Birthday = $('#patientmessage #Birthday input').val(); // 出生日期
	patient.HeightCM = $('#patientmessage #HeightCM').val(); // 身高（cm）
	patient.WeighKG = $('#patientmessage #WeighKG').val(); // 体重（kg）
	patient.DeptCode = $('#patientmessage #DeptCode').val(); // 科室编码
	patient.DeptName = $('#patientmessage #DeptName').val(); // 科室
	patient.DoctorCode = $('#patientmessage #DoctorCode').val(); // 主管医生编码
	patient.DoctorName = $('#patientmessage #DoctorName').val(); // 主管医生姓名
	patient.PatStatus = parseInt($('#patientmessage #PatStatus').val()); // 状态 0-出院，1-住院（默认），2-门诊，3-急诊
	patient.IsLactation = parseInt($('#patientmessage #IsLactation').val()); // 是否哺乳 -1-无法获取哺乳状态（默认） 0-不是 1-是
	patient.IsPregnancy = parseInt($('#patientmessage #IsPregnancy').val()); // 是否妊娠 -1-无法获取妊娠状态（默认） 0-不是 1-是 2
	patient.PregStartDate = $('#patientmessage #PregStartDate input').val(); // 妊娠开始日期，不为空，则计算妊娠期，否则出全期数据
	patient.HepDamageDegree = parseInt($('#patientmessage #HepDamageDegree')
			.val()); // -1-无法获取肝损害状态（默认） 0-无肝损害 1-存在肝损害，但损害程度不明确
	// 2-轻度肝损害 3-中度肝损害 4-重度肝损害
	patient.RenDamageDegree = parseInt($('#patientmessage #RenDamageDegree')
			.val()); // -1-无法获取肾损害状态（默认） 0-无肾损害 1-存在肾损害，但损害程度不明确
	// 2-轻度肾损害 3-中度肾损害 4-重度肾损害
	patient.UseTime = $('#patientmessage #UseTime input').val(); // 审查时间
	patient.CheckMode = $('#patientmessage #CheckMode').val(); // 审查模式,HIS传入
	patient.IsDoSave = parseInt($('#patientmessage #IsDoSave').val()); // 是否采集 0-不采集 1-采集
	MCpatientInfo = patient;

	/***************************************************************************
	 * 医嘱（处方，病人需要使用的药品）信息
	 **************************************************************************/
	var arrayDrug = new Array(); // 可能包含多条

	var odrrows = null;
	odrrows=$('#drugsmessage #json_table').bootstrapTable('getSelections');
	if(odrrows.length==0){
		odrrows=$('#drugsmessage #json_table').bootstrapTable('getData');
	}
	
	for (var i = 0; i < odrrows.length; i++) {
		var odrrow = odrrows[i];
		var drug;
		{
			drug = new Params_Mc_Drugs_In();
			drug.Index = odrrow.Index; // 药品序号
			drug.DrugSource = odrrow.DrugSource; 
			drug.OrderNo = parseInt(odrrow.OrderNo); // 医嘱号
			drug.DrugUniqueCode = odrrow.DrugUniqueCode; // 药品编码
			drug.DrugName = odrrow.DrugName; // 药品名称
			drug.DosePerTime = odrrow.DosePerTime; // 单次用量
			drug.DoseUnit = odrrow.DoseUnit; // 给药单位
			drug.FreqSource = odrrow.FreqSource;
			drug.Frequency = odrrow.Frequency; // 用药频次
			drug.RouteSource = odrrow.RouteSource; 
			drug.RouteCode = odrrow.RouteCode; // 给药途径编码
			drug.RouteName = odrrow.RouteName; // 给药途径名称
			drug.StartTime = odrrow.StartTime; // 开嘱时间
			drug.EndTime = odrrow.EndTime; // 停嘱时间
			drug.ExecuteTime = odrrow.ExecuteTime; // 执行时间
			drug.GroupTag = odrrow.GroupTag; // 成组标记
			drug.IsTempDrug = parseInt(odrrow.IsTempDrug); // 是否临时用药 0-长期 1-临时
			drug.OrderType = parseInt(odrrow.OrderType); // 医嘱类别标记 0-在用（默认），1-已作废，2-已停嘱，3-出院带药
			drug.DeptCode = odrrow.DeptCode; // 开嘱科室编码
			drug.DeptName = odrrow.DeptName; // 开嘱科室名称
			drug.DoctorCode = odrrow.DoctorCode; // 开嘱医生编码
			drug.DoctorName = odrrow.DoctorName; // 开嘱医生姓名
			drug.RecipNo = odrrow.RecipNo; // 处方号
			drug.Num = odrrow.Num; // 药品开出数量
			drug.NumUnit = odrrow.NumUnit; // 药品开出数量单位
			drug.Purpose = parseInt(odrrow.Purpose); // 用药目的(1预防，2治疗，3预防+治疗, 0默认)
			drug.OprCode = odrrow.OprCode; // 手术编号，如果对应多手术，用'，'隔开，表示该药为该编号对应的手术用药
			drug.MediTime = odrrow.MediTime; // 用药时机 （术前、术中、术后）？（0- 未使用 1- 0.5h以内 2- 0.5-2h
			// 3-大于2h）
			drug.Remark = odrrow.Remark; // 医嘱备注
			drug.driprate = "";// 滴速(120滴/分钟)
			drug.driptime = "";// 滴时间(滴100分钟)
			arrayDrug[arrayDrug.length] = drug;
		}
	}
	McDrugsArray = arrayDrug;

	/***************************************************************************
	 * 病生状态(疾病，诊断)信息
	 **************************************************************************/
	var arrayMedCond = new Array();
	var disrows = null;
	disrows=$('#diseasemessage #json_table').bootstrapTable('getSelections');
	if(disrows.length==0){
		disrows=$('#diseasemessage #json_table').bootstrapTable('getData');
	}
	for (var i = 0; i < disrows.length; i++) {
		var disrow = disrows[i];
		var medcond;
		medcond = new Params_Mc_MedCond_In();
		medcond.Index = disrow.Index; // 诊断序号
		medcond.DisSource = disrow.DisSource;
		medcond.DiseaseCode = disrow.DiseaseCode; // 诊断编码
		medcond.DiseaseName = disrow.DiseaseName; // 诊断名称
		medcond.RecipNo = disrow.RecipNo; // 处方号
		medcond.starttime = ""; // 开始时间
		medcond.endtime = ""; // 结束时间
		arrayMedCond[arrayMedCond.length] = medcond;
	}
	McMedCondArray = arrayMedCond;
	/***************************************************************************
	 * 过敏信息
	 **************************************************************************/
	var arrayAllergen = new Array();

	var ScreenMedCondList = {};
	var allerrows = null;
	allerrows=$('#allergenmessage #json_table').bootstrapTable('getSelections');
	if(allerrows.length==0){
		allerrows=$('#allergenmessage #json_table').bootstrapTable('getData');
	}
	for (var i = 0; i < allerrows.length; i++) {
		var allerrow = allerrows[i];
		var allergen = new Params_Mc_Allergen_In();
		allergen.Index = allerrow.Index; // 序号
		allergen.AllerSource = allerrow.AllerSource;
		allergen.AllerCode = allerrow.AllerCode; // 编码
		allergen.AllerName = allerrow.AllerName; // 名称
		allergen.AllerSymptom = allerrow.AllerSymptom; // 过敏症状
		arrayAllergen[arrayAllergen.length] = allergen;
	}

	McAllergenArray = arrayAllergen;
	/***************************************************************************
	 * 手术信息
	 **************************************************************************/
	var arrayoperation = new Array();

	var oprrows = null;
	oprrows=$('#operationmessage #json_table').bootstrapTable('getSelections');
	if(oprrows.length==0){
		oprrows=$('#operationmessage #json_table').bootstrapTable('getData');
	}
	for (var i = 0; i < oprrows.length; i++) {
		var oprrow = oprrows[i];

		var operation = new Params_Mc_Operation_In();
		operation.Index = oprrow.Index; // 序号
		operation.OprCode = oprrow.OprCode; // 编码
		operation.OprName = oprrow.OprName; // 名称
		operation.IncisionType = oprrow.IncisionType; // 切口类型
		operation.OprStartDate = oprrow.OprStartDate; // 手术开始时间（YYYY-MM-DD
		operation.OprEndDate = oprrow.OprEndDate; // 手术结束时间 （YYYY-MM-DD
		arrayoperation[arrayoperation.length] = operation;
	}

	McOperationArray = arrayoperation;
}

// HIS用callback方式处理PASS审查返回值
// 传入的_drugindex 不为空，则返回该_drugindex的结果
function HIS_dealwithPASSCheck(result) {
	/*
	 * result:审查后严重问题级（0,1,2,3,4） // 4-禁忌（黄灯） // 3-严重（橙灯） // 2-慎用（红灯） //
	 * 1-关注（黑灯） // <=0-没问题（蓝灯）
	 */
//	if (result > 0) {
//		alert("有问题:"+result);
//		var json = MDC_GetResultDetail("");
//	} else {
//		alert("没问题:"+result);
//	}

	/*
	 * 通过药品序号，循环获取单药警告 
	 * var slcode1 = MDC_getWarningCode("1320451");
	 * alert("返回结果是:"+slcode1);
	 */
	var alldata=null;
	var imageurl='';
	alldata=$('#drugsmessage #json_table').bootstrapTable('getData');
	for(var i=0; i<alldata.length;i++){
		$("#drugsmessage #json_table").bootstrapTable('updateCell',{index:i,field:'jingshi',value:imageurl});
	}
	
	alldata=$('#drugsmessage #json_table').bootstrapTable('getSelectionsIndex_CH');
	if(alldata.length==0){
		alldata=$('#drugsmessage #json_table').bootstrapTable('getDataIndex_CH');
	}
	
	for(var i=0; i<alldata.length;i++){
		onedata=alldata[i];
//		alert(onedata.DrugUniqueCode);
		var slcode=-1;
		try{
			slcode = MDC_getWarningCode(onedata.Index);
		}catch(e){
			console.log(e.message);
			swal({
	            title: "警告",
	            text: "医疗机构传入错误，或服务器连接错误！(可通过F12查看响应结果)."
	        });
			return;
		}
		
		imageurl='';
		//兼容DEMO中无项目根目录，直接在当前文件夹下找图片
		if(addurl == undefined){
			if(slcode>0 && slcode<5){
				imageurl='<img src="image_ch/'+slcode+'.gif">';
			}else if(slcode == 0){
				imageurl='<img src="image_ch/5.gif">';
			}else{
				imageurl='';
			}
		}else{
			if(slcode>0 && slcode<5){
				imageurl='<img src="'+addurl+'/image_ch/'+slcode+'.gif">';
			}else if(slcode == 0){
				imageurl='<img src="'+addurl+'/image_ch/5.gif">';
			}else{
				imageurl='';
			}
		}
		
		
		$("#drugsmessage #json_table").bootstrapTable('updateCell',{index:onedata.index_ch,field:'jingshi',value:imageurl});
	}
	
	// 调用PR审查返回值(HIS前提得设置调用pr)
//	if (mc_product_switch.PR == 1) {
//		for (var i = 0; i < McDrugsArray.length; i++) {
//			var status = MDC_GetSysPrStatus(McDrugsArray[i].RecipNo);// 获取系统审查状态(不区分同步，异步)
//			alert("pr异步系统状态返回值(1过,0不过)" + status);
//		}
//	}// end if

}

function js_screen() {
	MCInit();
	HisScreenData();
	MDC_DoCheck(HIS_dealwithPASSCheck);

}

function js_shuomingshu(){
	var IdSelections = $('#drugsmessage #json_table').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	
    MCInit(); 
	HisQueryData(IdSelections[0]);
	MDC_DoRefDrug(11);
	
}

function js_fudongchuangkou(){
	var IdSelections = $('#drugsmessage #json_table').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	
	MCInit();
	HisQueryData(IdSelections[0]);
	MDC_DoRefDrug(51);
}

function js_zhongyaocaizhuanluan(){
	var IdSelections = $('#drugsmessage #json_table').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	
	MCInit();
	HisQueryDataChm(IdSelections[0]);
	MDC_DoPassCommand(24);
}

function js_yongyaozhidaodan(){
	var IdSelections = $('#drugsmessage #json_table').bootstrapTable('getSelections');
//	if(IdSelections.length >0){
//		swal({
//            title: "警告",
//            text: "请选择一条数据进行操作."
//        });
//		return;
//	}
	
	MCInit();
	HisPGQueryData();
	MDC_DoPassCommand(34);
}

function HisPGQueryData() { // 用药指导单单独使用，不传usetime值
	/***************************************************************************
	 * 患者基本信息
	 **************************************************************************/
	var patient = new Params_MC_Patient_In();
	patient.PatCode = $('#patientmessage #PatCode').val(); // 编号
	patient.InHospNo = $('#patientmessage #InHospNo').val(); // 门诊号/住院号
	patient.VisitCode = $('#patientmessage #VisitCode').val(); // 门诊号/住院次数
	patient.Name = $('#patientmessage #Name').val(); // 姓名
	patient.Sex = $('#patientmessage #Sex').val(); // 性别
	patient.Birthday = $('#patientmessage #Birthday input').val(); // 出生日期
	patient.HeightCM = $('#patientmessage #HeightCM').val(); // 身高（cm）
	patient.WeighKG = $('#patientmessage #WeighKG').val(); // 体重（kg）
	patient.DeptCode = $('#patientmessage #DeptCode').val(); // 科室编码
	patient.DeptName = $('#patientmessage #DeptName').val(); // 科室
	patient.DoctorCode = $('#patientmessage #DoctorCode').val(); // 主管医生编码
	patient.DoctorName = $('#patientmessage #DoctorName').val(); // 主管医生姓名
	patient.PatStatus = parseInt($('#patientmessage #PatStatus').val()); // 状态 0-出院，1-住院（默认），2-门诊，3-急诊
	patient.IsLactation = parseInt($('#patientmessage #IsLactation').val()); // 是否哺乳 -1-无法获取哺乳状态（默认） 0-不是 1-是
	patient.IsPregnancy = parseInt($('#patientmessage #IsPregnancy').val()); // 是否妊娠 -1-无法获取妊娠状态（默认） 0-不是 1-是 2
	patient.PregStartDate = $('#patientmessage #PregStartDate input').val(); // 妊娠开始日期，不为空，则计算妊娠期，否则出全期数据
	patient.HepDamageDegree = parseInt($('#patientmessage #HepDamageDegree')
			.val()); // -1-无法获取肝损害状态（默认） 0-无肝损害 1-存在肝损害，但损害程度不明确
	// 2-轻度肝损害 3-中度肝损害 4-重度肝损害
	patient.RenDamageDegree = parseInt($('#patientmessage #RenDamageDegree')
			.val()); // -1-无法获取肾损害状态（默认） 0-无肾损害 1-存在肾损害，但损害程度不明确
	// 2-轻度肾损害 3-中度肾损害 4-重度肾损害
	patient.UseTime = $('#patientmessage #UseTime input').val(); // 审查时间
	patient.CheckMode = $('#patientmessage #CheckMode').val(); // 审查模式,HIS传入
	patient.IsDoSave = parseInt($('#patientmessage #IsDoSave').val()); // 是否采集 0-不采集 1-采集
	MCpatientInfo = patient;

	/***************************************************************************
	 * 医嘱（处方，病人需要使用的药品）信息
	 **************************************************************************/
	var arrayDrug = new Array(); // 可能包含多条
	var odrrows=null;
	odrrows=$('#drugsmessage #json_table').bootstrapTable('getSelections');
	if(odrrows.length==0){
		odrrows = $('#drugsmessage #json_table').bootstrapTable('getData');
	}
	
	for (var i = 0; i < odrrows.length; i++) {
		var odrrow = odrrows[i];
		var drug;
		{
			drug = new Params_Mc_Drugs_In();
			drug.Index = odrrow.Index; // 药品序号
			drug.OrderNo = parseInt(odrrow.OrderNo); // 医嘱号
			drug.DrugUniqueCode = odrrow.DrugUniqueCode; // 药品编码
			drug.DrugName = odrrow.DrugName; // 药品名称
			drug.DosePerTime = odrrow.DosePerTime; // 单次用量
			drug.DoseUnit = odrrow.DoseUnit; // 给药单位
			drug.Frequency = odrrow.Frequency; // 用药频次
			drug.RouteCode = odrrow.RouteCode; // 给药途径编码
			drug.RouteName = odrrow.RouteName; // 给药途径名称
			drug.StartTime = odrrow.StartTime; // 开嘱时间
			drug.EndTime = odrrow.EndTime; // 停嘱时间
			drug.ExecuteTime = odrrow.ExecuteTime; // 执行时间
			drug.GroupTag = odrrow.GroupTag; // 成组标记
			drug.IsTempDrug = parseInt(odrrow.IsTempDrug); // 是否临时用药 0-长期 1-临时
			drug.OrderType = parseInt(odrrow.OrderType); // 医嘱类别标记 0-在用（默认），1-已作废，2-已停嘱，3-出院带药
			drug.DeptCode = odrrow.DeptCode; // 开嘱科室编码
			drug.DeptName = odrrow.DeptName; // 开嘱科室名称
			drug.DoctorCode = odrrow.DoctorCode; // 开嘱医生编码
			drug.DoctorName = odrrow.DoctorName; // 开嘱医生姓名
			drug.RecipNo = odrrow.RecipNo; // 处方号
			drug.Num = odrrow.Num; // 药品开出数量
			drug.NumUnit = odrrow.NumUnit; // 药品开出数量单位
			drug.Purpose = parseInt(odrrow.Purpose); // 用药目的(1预防，2治疗，3预防+治疗, 0默认)
			drug.OprCode = odrrow.OprCode; // 手术编号，如果对应多手术，用'，'隔开，表示该药为该编号对应的手术用药
			drug.MediTime = odrrow.MediTime; // 用药时机 （术前、术中、术后）？（0- 未使用 1- 0.5h以内 2- 0.5-2h
			// 3-大于2h）
			drug.Remark = odrrow.Remark; // 医嘱备注
			drug.driprate = "";// 滴速(120滴/分钟)
			drug.driptime = "";// 滴时间(滴100分钟)
			arrayDrug[arrayDrug.length] = drug;
		}
	}
	McDrugsArray = arrayDrug;

	/***************************************************************************
	 * 病生状态(疾病，诊断)信息
	 **************************************************************************/
	var arrayMedCond = new Array();
	var disrows = $('#diseasemessage #json_table').bootstrapTable('getData');
	for (var i = 0; i < disrows.length; i++) {
		var disrow = disrows[i];
		var medcond;
		medcond = new Params_Mc_MedCond_In();
		medcond.Index = disrow.Index; // 诊断序号
		medcond.DiseaseCode = disrow.DiseaseCode; // 诊断编码
		medcond.DiseaseName = disrow.DiseaseName; // 诊断名称
		medcond.RecipNo = disrow.RecipNo; // 处方号
		medcond.starttime = ""; // 开始时间
		medcond.endtime = ""; // 结束时间
		arrayMedCond[arrayMedCond.length] = medcond;
	}
	McMedCondArray = arrayMedCond;
	/***************************************************************************
	 * 过敏信息
	 **************************************************************************/
	var arrayAllergen = new Array();

	var ScreenMedCondList = {};
	var allerrows = $('#allergenmessage #json_table').bootstrapTable('getData');
	for (var i = 0; i < allerrows.length; i++) {
		var allerrow = allerrows[i];
		var allergen = new Params_Mc_Allergen_In();
		allergen.Index = allerrow.Index; // 序号
		allergen.AllerCode = allerrow.AllerCode; // 编码
		allergen.AllerName = allerrow.AllerName; // 名称
		allergen.AllerSymptom = allerrow.AllerSymptom; // 过敏症状
		arrayAllergen[arrayAllergen.length] = allergen;
	}

	McAllergenArray = arrayAllergen;
	/***************************************************************************
	 * 手术信息
	 **************************************************************************/
	var arrayoperation = new Array();

	var oprrows = $('#operationmessage #json_table').bootstrapTable('getData');
	for (var i = 0; i < oprrows.length; i++) {
		var oprrow = oprrows[i];

		var operation = new Params_Mc_Operation_In();
		operation.Index = oprrow.Index; // 序号
		operation.OprCode = oprrow.OprCode; // 编码
		operation.OprName = oprrow.OprName; // 名称
		operation.IncisionType = oprrow.IncisionType; // 切口类型
		operation.OprStartDate = oprrow.OprStartDate; // 手术开始时间（YYYY-MM-DD
		operation.OprEndDate = oprrow.OprEndDate; // 手术结束时间 （YYYY-MM-DD
		arrayoperation[arrayoperation.length] = operation;
	}

	McOperationArray = arrayoperation;
	
	//======================================================
    //检查信息
    //======================================================
	var arrayexam = new Array();
	var exams = $('#fujiaexaminfo #json_table').bootstrapTable('getData');
	for (var i = 0; i < exams.length; i++) {
		var exam = exams[i];

		exam = new Params_Mc_Exam_In(); //检查
        exam.RequestNo = exam.requestno;    // 检验/检查申请单序号
        exam.LabExamCode = exam.labexamcode;  // 检验/检查申请项目编码
        exam.LabExamName = exam.labexamname;  // 检验/检查申请项目名称
        exam.StartDateTime = exam.startdatetime;// 检验/检查申请单下达时间
        exam.DeptCode = exam.deptcode;     // 申请科室
        exam.DeptName = exam.deptname;     // 申请科室名称
        exam.DoctorCode = exam.doctorcode;   // 申请医生编码
        exam.DoctorName = exam.doctorname;   // 申请医生名称
        arrayexam[arrayexam.length] = exam;
	}
    McExamArray = arrayexam;

    //======================================================
    //检验信息
    //======================================================
    var arraylab = new Array();
	var labs = $('#fujialabinfo #json_table').bootstrapTable('getData');
	
    for (var i = 0; i < labs.length; i++) {
		var lab = labs[i];

		lab = new Params_Mc_Lab_In(); //检验99
        lab.RequestNo = lab.requestno;    // 检验/检查申请单序号
        lab.LabExamCode = lab.labexamcode;  // 检验/检查申请项目编码
        lab.LabExamName = lab.labexamname;  // 检验/检查申请项目名称
        lab.StartDateTime = lab.startdatetime;// 检验/检查申请单下达时间
        lab.DeptCode = lab.deptcode;     // 申请科室
        lab.DeptName = lab.deptname;     // 申请科室名称
        lab.DoctorCode =lab.doctorcode;   // 申请医生编码
        lab.DoctorName =lab.doctorname;   // 申请医生名称
        arraylab[arraylab.length] = lab;
	}
    
    McLabArray = arraylab;
}

function js_youjiancaidan(){
	var IdSelections = $('#drugsmessage #json_table').bootstrapTable('getSelections');
	if(IdSelections.length != 1){
		swal({
            title: "警告",
            text: "请选择一条数据进行操作."
        });
		return;
	}
	MCInit();
	HisQueryData(IdSelections[0]);
	McPASS.McRightMenu();
	
}
