package com.ch.dll;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import com.ch.dll.PASS4Invoke;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RunDll {
	public String mcinit(String path,String checkmode, String hiscode){
		System.setProperty("jna.encoding","GB18030");
		
		// 调用dll
		String pcCheckMode=checkmode;
		String pcHisCode=hiscode;
		String pcDoctorCode="";
		
		int runback = PASS4Invoke.instanceDll.MDC_Init(pcCheckMode, pcHisCode, pcDoctorCode);
		System.out.println("DLL初始化->[{" + runback + "}]");
		
		return "DLL初始化->[{" + runback + "}]";
	}
	
	public void data(String json) throws UnsupportedEncodingException{
		System.setProperty("jna.encoding","GB18030");
		
		JSONObject jsonobj = JSONObject.fromObject(json);
		
		JSONObject Patient=jsonobj.getJSONObject("Patient");
		JSONObject ScreenDrugList=jsonobj.getJSONObject("ScreenDrugList");
		JSONObject ScreenAllergenList=jsonobj.getJSONObject("ScreenAllergenList");
		JSONObject ScreenMedCondList=jsonobj.getJSONObject("ScreenMedCondList");
		JSONObject ScreenOperationList=jsonobj.getJSONObject("ScreenOperationList");
		
		int runback=0;
		//patient
		String pcPatCode=Patient.getString("PatCode");
		String pcInHospNo=Patient.getString("InHospNo");
		String pcVisitCode=Patient.getString("VisitCode");
		String pcName=Patient.getString("Name");
		String pcSex=Patient.getString("Sex");
		String pcBirthday=Patient.getString("Birthday");
		String pcHeightCM=Patient.getString("HeightCM");
		String pcWeighKG=Patient.getString("WeighKG");
		String pcDeptCode=Patient.getString("DeptCode");
		String pcDeptName=Patient.getString("DeptName");
		String pcDoctorCode1=Patient.getString("DoctorCode");
		String pcDoctorName=Patient.getString("DoctorName");
		int piPatStatus=Integer.parseInt(Patient.getString("PatStatus"));
		int piIsLactation=Integer.parseInt(Patient.getString("IsLactation"));
		int piIsPregnancy=Integer.parseInt(Patient.getString("IsPregnancy"));
		String pcPregStartDate=Patient.getString("PregStartDate");
		int piHepDamageDegree=Integer.parseInt(Patient.getString("HepDamageDegree"));
		int piRenDamageDegree=Integer.parseInt(Patient.getString("RenDamageDegree"));
		
		runback=PASS4Invoke.instanceDll.MDC_SetPatient(pcPatCode, pcInHospNo, pcVisitCode, pcName, pcSex, pcBirthday, pcHeightCM, pcWeighKG, pcDeptCode, pcDeptName, pcDoctorCode1, pcDoctorName, piPatStatus, piIsLactation, piIsPregnancy, pcPregStartDate, piHepDamageDegree, piRenDamageDegree);
		System.out.println("病人信息赋值结束->[{" + runback + "}]");
		
		//drug
		JSONArray ScreenDrugs=(JSONArray)ScreenDrugList.get("ScreenDrugs");
		for(int i=0;i<ScreenDrugs.size();i++){
			JSONObject ScreenDrug = ScreenDrugs.getJSONObject(i);
			
			String pcIndex = ScreenDrug.getString("Index");
			int    piOrderNo = Integer.parseInt(ScreenDrug.getString("OrderNo"));
			String pcDrugUniqueCode = ScreenDrug.getString("DrugUniqueCode");
			String pcDrugName = ScreenDrug.getString("DrugName");
			String pcDosePerTime = ScreenDrug.getString("DosePerTime");
			String pcDoseUnit = ScreenDrug.getString("DoseUnit");
			String pcFrequency = ScreenDrug.getString("Frequency");
			String pcRouteCode = ScreenDrug.getString("RouteCode");
			String pcRouteName = ScreenDrug.getString("RouteName");
			String pcStartTime = ScreenDrug.getString("StartTime");
			String pcEndTime = ScreenDrug.getString("EndTime");
			String pcExecuteTime = ScreenDrug.getString("ExecuteTime");
			String pcGroupTag = ScreenDrug.getString("GroupTag");
			String pcIsTempDrug = ScreenDrug.getString("IsTempDrug");
			String pcOrderType = ScreenDrug.getString("OrderType");
			String pcDeptCode1 = ScreenDrug.getString("DeptCode");
			String pcDeptName1 = ScreenDrug.getString("DeptName");
			String pcDoctorCode2 = ScreenDrug.getString("DoctorCode");
			String pcDoctorName1 = ScreenDrug.getString("DoctorName");
			String pcRecipNo = ScreenDrug.getString("RecipNo");
			String pcNum = ScreenDrug.getString("Num");
			String pcNumUnit = ScreenDrug.getString("NumUnit");
			String pcPurpose= ScreenDrug.getString("Purpose");
			String pcOprCode= ScreenDrug.getString("OprCode");
			String pcMediTime= ScreenDrug.getString("MediTime");
			String pcRemark= ScreenDrug.getString("Remark");
			
			runback=PASS4Invoke.instanceDll.MDC_AddScreenDrug(pcIndex, piOrderNo, pcDrugUniqueCode, pcDrugName, pcDosePerTime, pcDoseUnit, pcFrequency, pcRouteCode, pcRouteName, pcStartTime, pcEndTime, pcExecuteTime, pcGroupTag, pcIsTempDrug, pcOrderType, pcDeptCode1, pcDeptName1, pcDoctorCode2, pcDoctorName1, pcRecipNo, pcNum, pcNumUnit, pcPurpose, pcOprCode, pcMediTime, pcRemark);
			System.out.println("药品信息赋值结束->[{" + runback + "}]");
		}
		
		//aller
		JSONArray ScreenAllergens=(JSONArray)ScreenAllergenList.get("ScreenAllergens");
		for(int i=0;i<ScreenAllergens.size();i++){
			JSONObject ScreenAllergen = ScreenAllergens.getJSONObject(i);
			
			String pcIndex = ScreenAllergen.getString("Index");
			String pcAllerCode = ScreenAllergen.getString("AllerCode");
			String pcAllerName = ScreenAllergen.getString("AllerName");
			String pcAllerSymptom = ScreenAllergen.getString("AllerSymptom");
			
			runback=PASS4Invoke.instanceDll.MDC_AddAller(pcIndex, pcAllerCode, pcAllerName, pcAllerSymptom);
			System.out.println("过敏原信息赋值结束->[{" + runback + "}]");
		}
		
		//disease
		JSONArray ScreenMedConds=(JSONArray)ScreenMedCondList.get("ScreenMedConds");
		for(int i=0;i<ScreenMedConds.size();i++){
			JSONObject ScreenMedCond = ScreenMedConds.getJSONObject(i);
			
			String pcIndex = ScreenMedCond.getString("Index");
			String pcDiseaseCode = ScreenMedCond.getString("DiseaseCode");
			String pcDiseaseName = ScreenMedCond.getString("DiseaseName");
			String pcRecipNo = ScreenMedCond.getString("RecipNo");
			
			runback=PASS4Invoke.instanceDll.MDC_AddMedCond(pcIndex, pcDiseaseCode, pcDiseaseName, pcRecipNo);
			System.out.println("疾病信息赋值结束->[{" + runback + "}]");
		}
		
		//operation
		JSONArray ScreenOperations=(JSONArray)ScreenOperationList.get("ScreenOperations");
		for(int i=0;i<ScreenOperations.size();i++){
			JSONObject ScreenOperation = ScreenOperations.getJSONObject(i);
			
			String pcIndex = ScreenOperation.getString("Index");
			String pcOprCode = ScreenOperation.getString("OprCode");
			String pcOprName = ScreenOperation.getString("OprName");
			String pcIncisionType = ScreenOperation.getString("IncisionType");
			String pcOprStartDate = ScreenOperation.getString("OprStartDate");
			String pcOprEndDate = ScreenOperation.getString("OprEndDate");
			
			runback=PASS4Invoke.instanceDll.MDC_AddOperation(pcIndex, pcOprCode, pcOprName, pcIncisionType, pcOprStartDate, pcOprEndDate);
			System.out.println("手术信息赋值结束->[{" + runback + "}]");
		}
		
	}
	
	public void screen(){
		//screen
		int runback=PASS4Invoke.instanceDll.MDC_DoCheck(1,0);
		System.out.println("调审查结果->[{" + runback + "}]");
	}
	
	public void mcquit(){
		//退出
		int runback=PASS4Invoke.instanceDll.MDC_Quit();
		System.out.println("退出->[{" + runback + "}]");
	}
	
	public void DoMediStudy(String json){
		System.setProperty("jna.encoding","GB18030");
		
		String usetime="";
		if(StringUtils.isNotBlank(json)){
			JSONObject jsonobj = JSONObject.fromObject(json);
			JSONObject Patient=jsonobj.getJSONObject("Patient");
			usetime=Patient.getString("UseTime");
		}
		
		
		int runback=PASS4Invoke.instanceDll.MDC_DoMediStudy(usetime);
		System.out.println("用药研究窗口->[{" + runback + "}]");
	}
	
}
