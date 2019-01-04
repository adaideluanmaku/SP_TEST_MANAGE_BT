package com.ch.dll;

import java.io.BufferedReader;
import java.io.FileInputStream;


import java.io.FileReader;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * 需要特别注意：如果DLL版本是32位，那么只能用32位JDK。如果是64位，只能用64位JDK运行
 * @author 陈辉
 *
 */
public interface PASS4Invoke extends Library {
	//相对路径
	String filePath = "D:\\PASSDLL\\PASS4Invoke.dll";
	
	PASS4Invoke instanceDll = (PASS4Invoke) Native.loadLibrary(filePath, PASS4Invoke.class);
    
	//工程根目录
//	PASS4Invoke instanceDll=(PASS4Invoke) Native.loadLibrary("PASS4Invoke",PASS4Invoke.class);
//	PASS4Invoke instanceDll1=(PASS4Invoke) Native.loadLibrary("PASSWeb",PASS4Invoke.class);
	
	//init
	public int MDC_Init(String pcCheckMode,	String pcHisCode, String pcDoctorCode);
	
	//patient
	public int MDC_SetPatient(String pcPatCode,
			String pcInHospNo,
			String pcVisitCode,
			String pcName,
			String pcSex,
			String pcBirthday,
			String pcHeightCM,
			String pcWeighKG,
			String pcDeptCode,
			String pcDeptName,
			String pcDoctorCode,
			String pcDoctorName,
			int piPatStatus,
			int piIsLactation,
			int piIsPregnancy,
			String pcPregStartDate,
			int piHepDamageDegree,
			int piRenDamageDegree);

	//drug
	public int MDC_AddScreenDrug(String pcIndex,
			int    piOrderNo,
			String pcDrugUniqueCode,
			String pcDrugName,
			String pcDosePerTime,
			String pcDoseUnit,
			String pcFrequency,
			String pcRouteCode,
			String pcRouteName,
			String pcStartTime,
			String pcEndTime,
			String pcExecuteTime,
			String pcGroupTag,
			String pcIsTempDrug,
			String pcOrderType,
			String pcDeptCode,
			String pcDeptName,
			String pcDoctorCode,
			String pcDoctorName,
			String pcRecipNo,
			String pcNum,
			String pcNumUnit,
			String pcPurpose,
			String pcOprCode,
			String pcMediTime,
			String pcRemark
			);

	//aller
	public int MDC_AddAller(String pcIndex,
			String pcAllerCode,
			String pcAllerName,
			String pcAllerSymptom);
	
	//disease
	public int MDC_AddMedCond(String pcIndex,
			String pcDiseaseCode,
			String pcDiseaseName,
			String pcRecipNo);

	//operation
	public int MDC_AddOperation(String pcIndex,
			String pcOprCode,
			String pcOprName,
			String pcIncisionType,
			String pcOprStartDate,
			String pcOprEndDate);


	//screen
	public int MDC_DoCheck(int piShowMode,int piIsSave);

	//获取药品医嘱警示级别
	public int MDC_GetWarningCode(String pcIndex);
	
	//获取药品医嘱警示级别
	public int MDC_ShowWarningHint(String pcIndex);
	
	//关闭一条药品医嘱的审查结果提示窗口函数
	public int MDC_CloseWarningHint();
	
	//获取审查结果条数函数
	public int MDC_GetResultItemCount(String pcIndex);
	
	//获取药品说明书查询项目有效性函数
	public String MDC_GetDrugRefEnabled(String pcDrugUniqueCode, 
			int piQueryType);

	//查询药品信息函数
	public int MDC_GetDrugQueryInfo(String pcDrugUniqueCode, 
			String pcDrugName,
			int	   piQueryType,
			int	   x,
			int	   y);
	
	//传入一个药品信息函数
	public int MDC_DoSetDrug(String pcDrugUniqueCode, 
			String pcDrugName);

	//查询已传入药品说明书有效性函数
	public int MDC_DoRefDrugEnable(int piQueryType);
	
	//查询某一个药品信息函数
	public int MDC_DoRefDrug(int piQueryType);
	
	//关闭浮动窗口函数
	public int MDC_CloseDrugHint();
	
	//调用药研究窗口函数
	public int MDC_DoMediStudy(String pcUseTime);
	
	//获取PASS系统最后一次错误信息函数
	public String MDC_GetLastError();
	
	//PASS退出函数
	public int MDC_Quit();
}
