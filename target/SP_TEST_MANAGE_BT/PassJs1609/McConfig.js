/*
* McConfig.js
* Copyright 1997-2016 Medicom Inc. All rights reserved.
* Product: PASS
* Version: 4.1.1 Standard
* Date: 2016-11-16 
*/

//请把IP地址改为美康合理用药服务器IP地址
this.mcWebServiceUrl = "http://172.18.7.160:9099/pass/rs";
//this.mcWebServiceUrl = "http://172.18.7.92:8580/pass/rs";
//this.mcWebServiceUrl = "http://172.18.2.179:9099/pass/rs";
this.mcPrWebServiceUrl = "";

this.mcWindowWidth = 800; //PASS弹出窗口-宽(px)  700---1000
this.mcWindowHeight = 500; //PASS弹出窗口-高(px) 400---600

this.MC_global_CheckMode = "mz"; //（工作站类型）门诊，住院，药房....(由HIS传,必须是字符串格式)
//门诊 mz 住院zy 住院药房zyyf 门诊药房mzyf 静脉滴注重心 iv 临床药学pa

this.MC_Is_SyncCheck = false;//同步审查
this.MC_Window_Left = -1;//-1(居中),大于0则表明距离左端的距离(px)
this.MC_Window_Top = -1;//-1(居中),大于0则表明距离顶端的距离(px)
/***************************************************************************
PASS IM config
*****************************************************************************/

/*******************************************调试模式设置*********************************************/
this.IsDebugDrugInfo = false;  //《查询》传入的查询数据
this.IsDebugPatient = false;   //《审查》传入的病人基本信息
this.IsDebugRecipes = false;   //《审查》传入的处方
this.IsDebugAllergens = false; //《审查》传入的过敏史
this.IsDebugMedConds = false;  //《审查》传入的病生状态 
this.IsDebugOperation = false; //《审查》传入的手术信息
this.IsDebugResult = false;    //《返回值》
this.IsDebugExecTime = false;  //《审查执行时间》

this.DebugShowDiv_Drug = "ShowResultDiv";     //显示调试传入的查询数据的控件名
this.DebugShowDiv_Patient = "ShowResultDiv";  //显示调试信息的控件名
this.DebugShowDiv_Recipes = "ShowResultDiv";  //显示调试传入的处方的控件名
this.DebugShowDiv_Allergens = "ShowResultDiv"; //显示调试传入的过敏史的控件名
this.DebugShowDiv_MedConds = "ShowResultDiv";  //显示调试传入的病生状态的控件名
this.DebugShowDiv_Operation = "ShowResultDiv"; //显示调试传入的手术信息的控件名
this.DebugShowDiv_Result = "ShowResultDiv";    //显示返回值调试的控件名