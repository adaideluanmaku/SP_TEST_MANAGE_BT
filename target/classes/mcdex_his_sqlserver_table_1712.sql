/*
Navicat SQL Server Data Transfer

Source Server         : 172.18.7.154_sql2008
Source Server Version : 105000
Source Host           : 172.18.7.154:1433
Source Database       : his_passpa
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 105000
File Encoding         : 65001

Date: 2018-06-08 10:45:37
*/


-- ----------------------------
-- Table structure for MC_DICT_ALLERGEN
-- ----------------------------
DROP TABLE [MC_DICT_ALLERGEN]
GO
CREATE TABLE [MC_DICT_ALLERGEN] (
[MATCH_SCHEME] decimal(11) NULL ,
[ALLERCODE] nvarchar(64) NULL ,
[ALLERNAME] nvarchar(64) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[IS_SAVE] decimal(11) NULL ,
[PASS_ALLERID] decimal(11) NULL ,
[PASS_ALLERTYPE] decimal(11) NULL ,
[PASS_ALLERNAME] nvarchar(500) NULL ,
[MATCH_TIME] nvarchar(20) NULL ,
[MATCH_USER] nvarchar(32) NULL ,
[UNABLE_MATCH] decimal(11) NULL ,
[UNABLE_MATCH_DESC] nvarchar(70) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_COSTITEM
-- ----------------------------
DROP TABLE [MC_DICT_COSTITEM]
GO
CREATE TABLE [MC_DICT_COSTITEM] (
[MATCH_SCHEME] decimal(11) NULL ,
[ITEMCODE] nvarchar(64) NULL ,
[ITEMNAME] nvarchar(128) NULL ,
[ITEMTYPE] decimal(11) NULL ,
[IS_BYX] decimal(11) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[IS_SAVE] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_DEPT
-- ----------------------------
DROP TABLE [MC_DICT_DEPT]
GO
CREATE TABLE [MC_DICT_DEPT] (
[MATCH_SCHEME] decimal(11) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[IS_CLINIC] decimal(11) NULL ,
[IS_INHOSP] decimal(11) NULL ,
[IS_EMERGENCY] decimal(11) NULL ,
[IS_SAVE] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_DISEASE
-- ----------------------------
DROP TABLE [MC_DICT_DISEASE]
GO
CREATE TABLE [MC_DICT_DISEASE] (
[MATCH_SCHEME] decimal(11) NULL ,
[DISCODE] nvarchar(64) NULL ,
[DISNAME] nvarchar(255) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[TYPECODE] nvarchar(32) NULL ,
[TYPENAME] nvarchar(128) NULL ,
[DIS_TYPE] decimal(11) NULL ,
[IS_MXB] decimal(11) NULL ,
[IS_SAVE] decimal(11) NULL ,
[PASS_ICD_CODE] nvarchar(128) NULL ,
[PASS_ICD_NAME] nvarchar(500) NULL ,
[MATCH_USER] nvarchar(32) NULL ,
[MATCH_TIME] nvarchar(20) NULL ,
[UNABLE_MATCH] decimal(11) NULL ,
[UNABLE_MATCH_DESC] nvarchar(70) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_DOCTOR
-- ----------------------------
DROP TABLE [MC_DICT_DOCTOR]
GO
CREATE TABLE [MC_DICT_DOCTOR] (
[MATCH_SCHEME] decimal(11) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL ,
[DOCTORLEVEL] nvarchar(32) NULL ,
[ILEVEL] decimal(11) NULL ,
[IS_CLINIC] decimal(11) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[PRESPRIV] decimal(11) NULL ,
[IS_SAVE] decimal(11) NULL ,
[PASSWORD] nvarchar(128) NULL ,
[ANTILEVEL] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_DRUG
-- ----------------------------
DROP TABLE [MC_DICT_DRUG]
GO
CREATE TABLE [MC_DICT_DRUG] (
[MATCH_SCHEME] decimal(11) NULL ,
[DRUGCODE] nvarchar(64) NULL ,
[DRUGNAME] nvarchar(128) NULL ,
[DRUGFORM] nvarchar(128) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[DRUGGROUPCODE] decimal(11) NULL ,
[DRUGGROUPNAME] nvarchar(128) NULL ,
[DRGGRP_SEARCHCODE] nvarchar(128) NULL ,
[IS_BASEDRUG] decimal(11) NULL ,
[IS_BASEDRUG_P] decimal(11) NULL ,
[IS_ANTI] decimal(11) NULL ,
[ANTITYPE] decimal(11) NULL ,
[ANTILEVEL] decimal(11) NULL ,
[DRUGTYPE] decimal(11) NULL ,
[DRUGFORMTYPE] decimal(11) NULL ,
[SOCIALSECURITY_RATIO] nvarchar(64) NULL ,
[IS_SOCIALSECURITY] decimal(11) NULL ,
[SOCIALSECURITY_DESC] nvarchar(255) NULL ,
[JDMTYPE] decimal(11) NULL ,
[IS_STIMULANT] decimal(11) NULL ,
[STIMULANTINGRED] nvarchar(64) NULL ,
[IS_SOLVENT] decimal(11) NULL ,
[IS_SRPREPARATIONS] decimal(11) NULL ,
[IS_NEEDSKINTEST] decimal(11) NULL ,
[IS_DEARMED] decimal(11) NULL ,
[IS_POISON] decimal(11) NULL ,
[IS_BLOODMED] decimal(11) NULL ,
[IS_SUGARMED] decimal(11) NULL ,
[OTCTYPE] decimal(11) NULL ,
[HIGH_ALERT_LEVEL] decimal(11) NULL ,
[IS_BISECTION_USE] decimal(11) NULL ,
[CLASSID] decimal(11) NULL ,
[CLASSTITLE] nvarchar(128) NULL ,
[IS_SAVE] decimal(11) NULL ,
[OPERUSER] nvarchar(32) NULL ,
[OPERTIME] nvarchar(20) NULL ,
[TYPENAME] nvarchar(256) NULL ,
[STATE] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_DRUG_PASS
-- ----------------------------
DROP TABLE [MC_DICT_DRUG_PASS]
GO
CREATE TABLE [MC_DICT_DRUG_PASS] (
[PROID] decimal(11) NULL ,
[DRUG_UNIQUE_CODE] nvarchar(255) NULL ,
[DRUGCODE] nvarchar(64) NULL ,
[DRUGNAME] nvarchar(128) NULL ,
[DRUGFORM] nvarchar(128) NULL ,
[DRUGSPEC] nvarchar(128) NULL ,
[COMP_NAME] nvarchar(128) NULL ,
[APPROVALCODE] nvarchar(64) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[DOSEUNIT] nvarchar(32) NULL ,
[MATCH_SCHEME] decimal(11) NULL ,
[PASS_DRUGCODE] decimal(11) NULL ,
[PASS_APPROVALCODE] nvarchar(500) NULL ,
[PASS_NAMETYPE] decimal(11) NULL ,
[PASS_DOSEUNIT] nvarchar(256) NULL ,
[PASS_DRUGNAME] nvarchar(500) NULL ,
[PASS_FORM_NAME] nvarchar(128) NULL ,
[PASS_ST_STRENGTH] nvarchar(1000) NULL ,
[PASS_ST_COMP_NAME] nvarchar(500) NULL ,
[PASS_DIVIDEND] decimal(38,4) NULL ,
[PASS_DIVISOR] decimal(38,4) NULL ,
[MENULABEL] nvarchar(256) NULL ,
[MATCH_USER] nvarchar(32) NULL ,
[MATCH_TIME] nvarchar(20) NULL ,
[UNABLE_MATCH] decimal(11) NULL ,
[UNABLE_MATCH_DESC] nvarchar(70) NULL ,
[OPRPI_USER] nvarchar(32) NULL ,
[OPRPI_TIME] nvarchar(20) NULL ,
[PASS_UPSTATE] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_DRUG_SUB
-- ----------------------------
DROP TABLE [MC_DICT_DRUG_SUB]
GO
CREATE TABLE [MC_DICT_DRUG_SUB] (
[MATCH_SCHEME] decimal(11) NULL ,
[DRUGCODE] nvarchar(255) NULL ,
[DRUGNAME] nvarchar(256) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[DRUGFORM] nvarchar(128) NULL ,
[DRUGSPEC] nvarchar(255) NULL ,
[COSTUNIT] nvarchar(128) NULL ,
[DDD] decimal(38,4) NULL ,
[DDDUNIT] nvarchar(128) NULL ,
[DDD_COSTUNIT] decimal(38,4) NULL ,
[ADDDATE] nvarchar(20) NULL ,
[IS_USE] decimal(11) NULL ,
[IS_SAVE] decimal(11) NULL ,
[STATE] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_EXAM
-- ----------------------------
DROP TABLE [MC_DICT_EXAM]
GO
CREATE TABLE [MC_DICT_EXAM] (
[MATCH_SCHEME] decimal(11) NULL ,
[EXAMCODE] nvarchar(64) NULL ,
[EXAMNAME] nvarchar(128) NULL ,
[TYPE] decimal(11) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[IS_SAVE] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_FREQUENCY
-- ----------------------------
DROP TABLE [MC_DICT_FREQUENCY]
GO
CREATE TABLE [MC_DICT_FREQUENCY] (
[MATCH_SCHEME] decimal(11) NULL ,
[FREQUENCY] nvarchar(32) NULL ,
[TIMES] decimal(11) NULL ,
[DAYS] decimal(11) NULL ,
[PHARMFREQUENCY] nvarchar(32) NULL ,
[UNABLE_MATCH] decimal(11) NULL ,
[MATCH_DESC] nvarchar(128) NULL ,
[IS_SAVE] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_LAB
-- ----------------------------
DROP TABLE [MC_DICT_LAB]
GO
CREATE TABLE [MC_DICT_LAB] (
[MATCH_SCHEME] decimal(11) NULL ,
[LABCODE] nvarchar(64) NULL ,
[LABNAME] nvarchar(128) NULL ,
[TYPE] decimal(11) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[IS_SAVE] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_LABSUB
-- ----------------------------
DROP TABLE [MC_DICT_LABSUB]
GO
CREATE TABLE [MC_DICT_LABSUB] (
[MATCH_SCHEME] decimal(11) NULL ,
[ITEMCODE] nvarchar(64) NULL ,
[ITEMNAME] nvarchar(128) NULL ,
[TYPE_ID] decimal(11) NULL ,
[MEDSHOW_ID] decimal(11) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[IS_SAVE] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_OPERATION
-- ----------------------------
DROP TABLE [MC_DICT_OPERATION]
GO
CREATE TABLE [MC_DICT_OPERATION] (
[MATCH_SCHEME] decimal(11) NULL ,
[OPERATIONCODE] nvarchar(64) NULL ,
[OPERATIONNAME] nvarchar(128) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[TYPENAME] nvarchar(128) NULL ,
[USEANTI] decimal(11) NULL ,
[PREMOMENT_LOW] decimal(38,4) NULL ,
[PREMOMENT_HIGH] decimal(38,4) NULL ,
[DRUGTIME] decimal(11) NULL ,
[IS_SAVE] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO

-- ----------------------------
-- Table structure for MC_DICT_ROUTE
-- ----------------------------
DROP TABLE [MC_DICT_ROUTE]
GO
CREATE TABLE [MC_DICT_ROUTE] (
[MATCH_SCHEME] decimal(11) NULL ,
[ROUTECODE] nvarchar(128) NULL ,
[ROUTENAME] nvarchar(128) NULL ,
[SEARCHCODE] nvarchar(256) NULL ,
[ROUTE_TYPE] decimal(11) NULL ,
[ABBREV] nvarchar(10) NULL ,
[IS_SAVE] decimal(11) NULL ,
[PASS_ROUTEID] decimal(11) NULL ,
[PASS_ROUTE_NAME] nvarchar(256) NULL ,
[MATCH_USER] nvarchar(32) NULL ,
[MATCH_TIME] nvarchar(20) NULL ,
[UNABLE_MATCH] decimal(11) NULL ,
[UNABLE_MATCH_DESC] nvarchar(70) NULL ,
[ISSKINTEST] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL 
)


GO


-- ----------------------------
-- Table structure for MDC2_DICT_DRUG
-- 马哥要求整个药品三张表，原三表暂时保留
-- ----------------------------
DROP TABLE [MDC2_DICT_DRUG]
GO
CREATE TABLE [MDC2_DICT_DRUG] (
[drugcode] nvarchar(64) NULL ,
[drug_unique_code] nvarchar(255) NULL ,
[drugname] nvarchar(128) NULL ,
[drugform] nvarchar(128) NULL ,
[drugspec] nvarchar(128) NULL ,
[approvalcode] nvarchar(64) NULL ,
[comp_name] nvarchar(128) NULL ,
[doseunit] nvarchar(32) NULL ,
[drugtype] decimal(11) NULL ,
[DRUGGROUPCODE] decimal(11) NULL ,
[DRUGGROUPNAME] nvarchar(128) NULL ,
[DRGGRP_SEARCHCODE] nvarchar(128) NULL ,
[costunit] nvarchar(128) NULL ,
[adddate] nvarchar(20) NULL ,
[is_use] decimal(11) NULL ,
[is_anti] decimal(11) NULL ,
[antitype] decimal(11) NULL ,
[antilevel] decimal(11) NULL ,
[ddd] decimal(38) NULL ,
[dddunit] nvarchar(128) NULL ,
[is_basedrug] decimal(11) NULL ,
[UPDATEDATE] datetime2(7) NULL
)


GO

-- ----------------------------
-- Table structure for T_MC_CLINIC_ALLERGEN
-- ----------------------------
DROP TABLE [T_MC_CLINIC_ALLERGEN]
GO
CREATE TABLE [T_MC_CLINIC_ALLERGEN] (
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[CLINICNO] nvarchar(32) NULL ,
[ALLERCODE] nvarchar(64) NULL ,
[ALLERNAME] nvarchar(64) NULL ,
[SYMPTOM] nvarchar(255) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_CLINIC_COST
-- ----------------------------
DROP TABLE [T_MC_CLINIC_COST]
GO
CREATE TABLE [T_MC_CLINIC_COST] (
[IID] decimal(11) NULL ,
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[CLINICNO] nvarchar(32) NULL ,
[PRESCNO] nvarchar(32) NULL ,
[COSTTYPE] decimal(11) NULL ,
[DRUGINDEX] decimal(11) NULL ,
[ITEMCODE] nvarchar(128) NULL ,
[ITEMNAME] nvarchar(128) NULL ,
[DRUGFORM] nvarchar(128) NULL ,
[DRUGSPEC] nvarchar(128) NULL ,
[DRUGSCCJ] nvarchar(128) NULL ,
[ITEMUNIT] nvarchar(96) NULL ,
[ITEMNUM] decimal(38,4) NULL ,
[COST] decimal(38,4) NULL ,
[COSTTIME] nvarchar(20) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[MEDGROUPCODE] nvarchar(64) NULL ,
[MEDGROUPNAME] nvarchar(64) NULL ,
[ROUTECODE] nvarchar(128) NULL ,
[PHARMACISTS] nvarchar(32) NULL ,
[PHARMACISTS_] nvarchar(32) NULL ,
[IS_USE] decimal(11) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_CLINIC_DISEASE
-- ----------------------------
DROP TABLE [T_MC_CLINIC_DISEASE]
GO
CREATE TABLE [T_MC_CLINIC_DISEASE] (
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[CLINICNO] nvarchar(32) NULL ,
[PRESCNO] nvarchar(32) NULL ,
[DISCODE] nvarchar(64) NULL ,
[DISNAME] nvarchar(255) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_CLINIC_EXAM
-- ----------------------------
DROP TABLE [T_MC_CLINIC_EXAM]
GO
CREATE TABLE [T_MC_CLINIC_EXAM] (
[IENDDATE] decimal(11) NULL ,
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[CLINICNO] nvarchar(32) NULL ,
[EXAMCODE] nvarchar(64) NULL ,
[EXAMNAME] nvarchar(128) NULL ,
[REQUESTNO] nvarchar(64) NULL ,
[BODYPART] nvarchar(512) NULL ,
[EXAMRESULT] nvarchar(MAX) NULL ,
[INSTRUMENT] nvarchar(32) NULL ,
[REPORTTIME] nvarchar(20) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_CLINIC_LAB
-- ----------------------------
DROP TABLE [T_MC_CLINIC_LAB]
GO
CREATE TABLE [T_MC_CLINIC_LAB] (
[IENDDATE] decimal(11) NULL ,
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[CLINICNO] nvarchar(32) NULL ,
[REQUESTNO] nvarchar(64) NULL ,
[LABCODE] nvarchar(256) NULL ,
[LABNAME] nvarchar(512) NULL ,
[SAMPLETYPE] nvarchar(32) NULL ,
[SAMPLINGTIME] nvarchar(20) NULL ,
[ITEMCODE] nvarchar(64) NULL ,
[ITEMNAME] nvarchar(128) NULL ,
[LABRESULT] nvarchar(64) NULL ,
[RESULTFLAG] nvarchar(2) NULL ,
[RANGE_0] nvarchar(128) NULL ,
[UNIT] nvarchar(32) NULL ,
[REPORTTIME] nvarchar(20) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[INSTRUMENT] nvarchar(32) NULL ,
[DD] nvarchar(50) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_CLINIC_ORDER
-- ----------------------------
DROP TABLE [T_MC_CLINIC_ORDER]
GO
CREATE TABLE [T_MC_CLINIC_ORDER] (
[HISCODE] nvarchar(128) NULL ,
[CID] nvarchar(64) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[CLINICNO] nvarchar(32) NULL ,
[PRESCNO] nvarchar(32) NULL ,
[PRESCTYPE] decimal(11) NULL ,
[NUM] nvarchar(20) NULL ,
[NUMUNIT] nvarchar(64) NULL ,
[COST] decimal(38,4) NULL ,
[ORDERNO] nvarchar(32) NULL ,
[ORDERTYPE] decimal(11) NULL ,
[GROUPSTATE] decimal(11) NULL ,
[GROUPTAG] nvarchar(32) NULL ,
[DRUG_UNIQUE_CODE] nvarchar(255) NULL ,
[ORDERCODE] nvarchar(128) NULL ,
[ORDERNAME] nvarchar(256) NULL ,
[DRUGFORM] nvarchar(128) NULL ,
[DRUGSPEC] nvarchar(128) NULL ,
[ROUTECODE] nvarchar(128) NULL ,
[ROUTENAME] nvarchar(128) NULL ,
[SINGLEDOSE] nvarchar(24) NULL ,
[DOSEUNIT] nvarchar(64) NULL ,
[FREQUENCY] nvarchar(32) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[STARTDATETIME] nvarchar(20) NULL ,
[DAYS] decimal(11) NULL ,
[PURPOSE] decimal(11) NULL ,
[REMARK] nvarchar(255) NULL ,
[ORDERSTATUS] decimal(11) NULL ,
[IS_ALLOW] decimal(11) NULL ,
[REASONABLE_DESC] nvarchar(128) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_CLINIC_PATIENT
-- ----------------------------
DROP TABLE [T_MC_CLINIC_PATIENT]
GO
CREATE TABLE [T_MC_CLINIC_PATIENT] (
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[IENDDATE] decimal(11) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[CLINICNO] nvarchar(32) NULL ,
[PATIENTNAME] nvarchar(32) NULL ,
[SEX] nvarchar(10) NULL ,
[BIRTHDATE] nvarchar(20) NULL ,
[AGE] nvarchar(24) NULL ,
[IAGE] decimal(11) NULL ,
[COST] decimal(38,4) NULL ,
[HEIGHT] nvarchar(16) NULL ,
[WEIGHT] nvarchar(16) NULL ,
[IDENTITYCARD] nvarchar(24) NULL ,
[TELEPHONE] nvarchar(48) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[MEDGROUPCODE] nvarchar(64) NULL ,
[MEDGROUPNAME] nvarchar(64) NULL ,
[ENDDATE] nvarchar(20) NULL ,
[DIAGNOSIS] nvarchar(MAX) NULL ,
[ALLERGEN] nvarchar(255) NULL ,
[PAYCLASS] nvarchar(32) NULL ,
[IS_EMERGENCY] decimal(11) NULL ,
[IS_PREG] decimal(11) NULL ,
[PREG_STARTTIME] nvarchar(20) NULL ,
[IS_LACT] decimal(11) NULL ,
[HEP_DAMAGE] decimal(11) NULL ,
[REN_DAMAGE] decimal(11) NULL ,
[STANDBY] nvarchar(255) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_INHOSP_ALLERGEN
-- ----------------------------
DROP TABLE [T_MC_INHOSP_ALLERGEN]
GO
CREATE TABLE [T_MC_INHOSP_ALLERGEN] (
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[ALLERCODE] nvarchar(64) NULL ,
[ALLERNAME] nvarchar(64) NULL ,
[SYMPTOM] nvarchar(255) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_INHOSP_COST
-- ----------------------------
DROP TABLE [T_MC_INHOSP_COST]
GO
CREATE TABLE [T_MC_INHOSP_COST] (
[IID] decimal(11) NULL ,
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[COSTTYPE] decimal(11) NULL ,
[DRUGINDEX] decimal(11) NULL ,
[ITEMCODE] nvarchar(128) NULL ,
[ITEMNAME] nvarchar(128) NULL ,
[DRUGFORM] nvarchar(128) NULL ,
[DRUGSPEC] nvarchar(128) NULL ,
[DRUGSCCJ] nvarchar(128) NULL ,
[ITEMUNIT] nvarchar(96) NULL ,
[ITEMNUM] decimal(38,4) NULL ,
[COST] decimal(38,4) NULL ,
[COSTTIME] nvarchar(20) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL ,
[WARDCODE] nvarchar(64) NULL ,
[WARDNAME] nvarchar(64) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[MEDGROUPCODE] nvarchar(64) NULL ,
[MEDGROUPNAME] nvarchar(64) NULL ,
[IS_OUT] decimal(11) NULL ,
[IS_USE] decimal(11) NULL ,
[ROUTECODE] nvarchar(128) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_INHOSP_DISEASE
-- ----------------------------
DROP TABLE [T_MC_INHOSP_DISEASE]
GO
CREATE TABLE [T_MC_INHOSP_DISEASE] (
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[DISCODE] nvarchar(64) NULL ,
[DISNAME] nvarchar(255) NULL ,
[TREATMENT] decimal(11) NULL ,
[IS_HOSPINFECTION] decimal(11) NULL ,
[DISEASETYPE] decimal(11) NULL ,
[IS_MAIN] decimal(11) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_INHOSP_EXAM
-- ----------------------------
DROP TABLE [T_MC_INHOSP_EXAM]
GO
CREATE TABLE [T_MC_INHOSP_EXAM] (
[IENDDATE] decimal(11) NULL ,
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[EXAMCODE] nvarchar(64) NULL ,
[EXAMNAME] nvarchar(128) NULL ,
[REQUESTNO] nvarchar(64) NULL ,
[BODYPART] nvarchar(512) NULL ,
[EXAMRESULT] nvarchar(MAX) NULL ,
[INSTRUMENT] nvarchar(32) NULL ,
[REPORTTIME] nvarchar(20) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_INHOSP_LAB
-- ----------------------------
DROP TABLE [T_MC_INHOSP_LAB]
GO
CREATE TABLE [T_MC_INHOSP_LAB] (
[IENDDATE] decimal(11) NULL ,
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[REQUESTNO] nvarchar(64) NULL ,
[LABCODE] nvarchar(256) NULL ,
[LABNAME] nvarchar(512) NULL ,
[SAMPLETYPE] nvarchar(32) NULL ,
[SAMPLINGTIME] nvarchar(20) NULL ,
[ITEMCODE] nvarchar(64) NULL ,
[ITEMNAME] nvarchar(128) NULL ,
[LABRESULT] nvarchar(64) NULL ,
[RESULTFLAG] nvarchar(2) NULL ,
[RANGE_0] nvarchar(128) NULL ,
[UNIT] nvarchar(32) NULL ,
[REPORTTIME] nvarchar(20) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[INSTRUMENT] nvarchar(32) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_INHOSP_OPERATION
-- ----------------------------
DROP TABLE [T_MC_INHOSP_OPERATION]
GO
CREATE TABLE [T_MC_INHOSP_OPERATION] (
[HISCODE] nvarchar(128) NULL ,
[OPRID] nvarchar(64) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[OPERATIONCODE] nvarchar(64) NULL ,
[OPERATIONNAME] nvarchar(128) NULL ,
[INCISIONTYPE] decimal(11) NULL ,
[STARTDATE] nvarchar(20) NULL ,
[ENDDATE] nvarchar(20) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_INHOSP_ORDER
-- ----------------------------
DROP TABLE [T_MC_INHOSP_ORDER]
GO
CREATE TABLE [T_MC_INHOSP_ORDER] (
[HISCODE] nvarchar(128) NULL ,
[CID] nvarchar(64) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[ORDERNO] nvarchar(32) NULL ,
[ORDERTYPE] decimal(11) NULL ,
[GROUPSTATE] decimal(11) NULL ,
[GROUPTAG] nvarchar(32) NULL ,
[IS_TEMP] decimal(11) NULL ,
[DRUG_UNIQUE_CODE] nvarchar(255) NULL ,
[ORDERCODE] nvarchar(128) NULL ,
[ORDERNAME] nvarchar(256) NULL ,
[DRUGFORM] nvarchar(128) NULL ,
[DRUGSPEC] nvarchar(128) NULL ,
[ROUTECODE] nvarchar(128) NULL ,
[ROUTENAME] nvarchar(128) NULL ,
[SINGLEDOSE] nvarchar(24) NULL ,
[DOSEUNIT] nvarchar(64) NULL ,
[FREQUENCY] nvarchar(32) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL ,
[WARDCODE] nvarchar(64) NULL ,
[WARDNAME] nvarchar(64) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[EXECUTETIME] nvarchar(20) NULL ,
[STARTDATETIME] nvarchar(20) NULL ,
[ENDDATETIME] nvarchar(20) NULL ,
[PA_ENDDATETIME] nvarchar(20) NULL ,
[PURPOSE] decimal(11) NULL ,
[REMARK] nvarchar(255) NULL ,
[IS_OUT] decimal(11) NULL ,
[ORDERSTATUS] decimal(11) NULL ,
[IS_ALLOW] decimal(11) NULL ,
[REASONABLE_DESC] nvarchar(128) NULL ,
[MEDITIME] decimal(11) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_INHOSP_PATIENT
-- ----------------------------
DROP TABLE [T_MC_INHOSP_PATIENT]
GO
CREATE TABLE [T_MC_INHOSP_PATIENT] (
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[HOSPITALNO] nvarchar(32) NULL ,
[PATIENTNAME] nvarchar(32) NULL ,
[SEX] nvarchar(10) NULL ,
[BIRTHDATE] nvarchar(20) NULL ,
[AGE] nvarchar(24) NULL ,
[IAGE] decimal(11) NULL ,
[COST] decimal(38,4) NULL ,
[HEIGHT] nvarchar(16) NULL ,
[WEIGHT] nvarchar(16) NULL ,
[IDENTITYCARD] nvarchar(24) NULL ,
[TELEPHONE] nvarchar(48) NULL ,
[BEDNO] nvarchar(20) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL ,
[WARDCODE] nvarchar(64) NULL ,
[WARDNAME] nvarchar(64) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[MEDGROUPCODE] nvarchar(64) NULL ,
[MEDGROUPNAME] nvarchar(64) NULL ,
[STARTDATE] nvarchar(20) NULL ,
[ENDDATE] nvarchar(20) NULL ,
[ACCOUNTDATE] nvarchar(20) NULL ,
[IN_DIAGNOSIS] nvarchar(MAX) NULL ,
[ALLERGEN] nvarchar(255) NULL ,
[NURSINGCLASS] nvarchar(32) NULL ,
[PAYCLASS] nvarchar(32) NULL ,
[IS_PREG] decimal(11) NULL ,
[PREG_STARTTIME] nvarchar(20) NULL ,
[IS_LACT] decimal(11) NULL ,
[HEP_DAMAGE] decimal(11) NULL ,
[REN_DAMAGE] decimal(11) NULL ,
[STANDBY] nvarchar(255) NULL ,
[OPERATIONS] nvarchar(512) NULL ,
[INCISIONTYPES] nvarchar(128) NULL ,
[FIRSTDEPTNAME] nvarchar(32) NULL ,
[I_IN] decimal(11) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_INHOSP_TEMPERATURE
-- ----------------------------
DROP TABLE [T_MC_INHOSP_TEMPERATURE]
GO
CREATE TABLE [T_MC_INHOSP_TEMPERATURE] (
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[TAKETIME] nvarchar(20) NULL ,
[TEMPERATURE] decimal(38,4) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_ALLERGEN
-- ----------------------------
DROP TABLE [T_MC_OUTHOSP_ALLERGEN]
GO
CREATE TABLE [T_MC_OUTHOSP_ALLERGEN] (
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[ALLERCODE] nvarchar(64) NULL ,
[ALLERNAME] nvarchar(64) NULL ,
[SYMPTOM] nvarchar(255) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_COST
-- ----------------------------
DROP TABLE [T_MC_OUTHOSP_COST]
GO
CREATE TABLE [T_MC_OUTHOSP_COST] (
[IID] decimal(11) NULL ,
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[COSTTYPE] decimal(11) NULL ,
[DRUGINDEX] decimal(11) NULL ,
[ITEMCODE] nvarchar(128) NULL ,
[ITEMNAME] nvarchar(128) NULL ,
[DRUGFORM] nvarchar(128) NULL ,
[DRUGSPEC] nvarchar(128) NULL ,
[DRUGSCCJ] nvarchar(128) NULL ,
[ITEMUNIT] nvarchar(96) NULL ,
[ITEMNUM] decimal(38,4) NULL ,
[COST] decimal(38,4) NULL ,
[COSTTIME] nvarchar(20) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL ,
[WARDCODE] nvarchar(64) NULL ,
[WARDNAME] nvarchar(64) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[MEDGROUPCODE] nvarchar(64) NULL ,
[MEDGROUPNAME] nvarchar(64) NULL ,
[IS_OUT] decimal(11) NULL ,
[IS_USE] decimal(11) NULL ,
[ROUTECODE] nvarchar(128) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_DISEASE
-- ----------------------------
DROP TABLE [T_MC_OUTHOSP_DISEASE]
GO
CREATE TABLE [T_MC_OUTHOSP_DISEASE] (
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[DISCODE] nvarchar(64) NULL ,
[DISNAME] nvarchar(255) NULL ,
[TREATMENT] decimal(11) NULL ,
[IS_HOSPINFECTION] decimal(11) NULL ,
[DISEASETYPE] decimal(11) NULL ,
[IS_MAIN] decimal(11) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_EXAM
-- ----------------------------
DROP TABLE [T_MC_OUTHOSP_EXAM]
GO
CREATE TABLE [T_MC_OUTHOSP_EXAM] (
[IENDDATE] decimal(11) NULL ,
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[EXAMCODE] nvarchar(64) NULL ,
[EXAMNAME] nvarchar(128) NULL ,
[REQUESTNO] nvarchar(64) NULL ,
[BODYPART] nvarchar(512) NULL ,
[EXAMRESULT] nvarchar(MAX) NULL ,
[INSTRUMENT] nvarchar(32) NULL ,
[REPORTTIME] nvarchar(20) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_LAB
-- ----------------------------
DROP TABLE [T_MC_OUTHOSP_LAB]
GO
CREATE TABLE [T_MC_OUTHOSP_LAB] (
[IENDDATE] decimal(11) NULL ,
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[REQUESTNO] nvarchar(64) NULL ,
[LABCODE] nvarchar(256) NULL ,
[LABNAME] nvarchar(512) NULL ,
[SAMPLETYPE] nvarchar(32) NULL ,
[SAMPLINGTIME] nvarchar(20) NULL ,
[ITEMCODE] nvarchar(64) NULL ,
[ITEMNAME] nvarchar(128) NULL ,
[LABRESULT] nvarchar(64) NULL ,
[RESULTFLAG] nvarchar(2) NULL ,
[RANGE_0] nvarchar(128) NULL ,
[UNIT] nvarchar(32) NULL ,
[REPORTTIME] nvarchar(20) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[INSTRUMENT] nvarchar(32) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_OPERATION
-- ----------------------------
DROP TABLE [T_MC_OUTHOSP_OPERATION]
GO
CREATE TABLE [T_MC_OUTHOSP_OPERATION] (
[HISCODE] nvarchar(128) NULL ,
[OPRID] nvarchar(64) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[OPERATIONCODE] nvarchar(64) NULL ,
[OPERATIONNAME] nvarchar(128) NULL ,
[INCISIONTYPE] decimal(11) NULL ,
[STARTDATE] nvarchar(20) NULL ,
[ENDDATE] nvarchar(20) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_ORDER
-- ----------------------------
DROP TABLE [T_MC_OUTHOSP_ORDER]
GO
CREATE TABLE [T_MC_OUTHOSP_ORDER] (
[HISCODE] nvarchar(128) NULL ,
[CID] nvarchar(64) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[ORDERNO] nvarchar(32) NULL ,
[ORDERTYPE] decimal(11) NULL ,
[GROUPSTATE] decimal(11) NULL ,
[GROUPTAG] nvarchar(32) NULL ,
[IS_TEMP] decimal(11) NULL ,
[DRUG_UNIQUE_CODE] nvarchar(255) NULL ,
[ORDERCODE] nvarchar(128) NULL ,
[ORDERNAME] nvarchar(256) NULL ,
[DRUGFORM] nvarchar(128) NULL ,
[DRUGSPEC] nvarchar(128) NULL ,
[ROUTECODE] nvarchar(128) NULL ,
[ROUTENAME] nvarchar(128) NULL ,
[SINGLEDOSE] nvarchar(24) NULL ,
[DOSEUNIT] nvarchar(64) NULL ,
[FREQUENCY] nvarchar(32) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL ,
[WARDCODE] nvarchar(64) NULL ,
[WARDNAME] nvarchar(64) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[EXECUTETIME] nvarchar(20) NULL ,
[STARTDATETIME] nvarchar(20) NULL ,
[ENDDATETIME] nvarchar(20) NULL ,
[PA_ENDDATETIME] nvarchar(20) NULL ,
[PURPOSE] decimal(11) NULL ,
[REMARK] nvarchar(255) NULL ,
[IS_OUT] decimal(11) NULL ,
[ORDERSTATUS] decimal(11) NULL ,
[IS_ALLOW] decimal(11) NULL ,
[REASONABLE_DESC] nvarchar(128) NULL ,
[MEDITIME] decimal(11) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_PATIENT
-- ----------------------------
DROP TABLE [T_MC_OUTHOSP_PATIENT]
GO
CREATE TABLE [T_MC_OUTHOSP_PATIENT] (
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[IENDDATE] decimal(11) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[HOSPITALNO] nvarchar(32) NULL ,
[PATIENTNAME] nvarchar(32) NULL ,
[SEX] nvarchar(10) NULL ,
[BIRTHDATE] nvarchar(20) NULL ,
[AGE] nvarchar(24) NULL ,
[IAGE] decimal(11) NULL ,
[COST] decimal(38,4) NULL ,
[HEIGHT] nvarchar(16) NULL ,
[WEIGHT] nvarchar(16) NULL ,
[IDENTITYCARD] nvarchar(24) NULL ,
[TELEPHONE] nvarchar(48) NULL ,
[BEDNO] nvarchar(20) NULL ,
[DEPTCODE] nvarchar(64) NULL ,
[DEPTNAME] nvarchar(64) NULL ,
[DOCTORCODE] nvarchar(64) NULL ,
[DOCTORNAME] nvarchar(32) NULL ,
[MEDGROUPCODE] nvarchar(64) NULL ,
[MEDGROUPNAME] nvarchar(64) NULL ,
[WARDCODE] nvarchar(64) NULL ,
[WARDNAME] nvarchar(64) NULL ,
[STARTDATE] nvarchar(20) NULL ,
[ENDDATE] nvarchar(20) NULL ,
[ACCOUNTDATE] nvarchar(20) NULL ,
[IN_DIAGNOSIS] nvarchar(MAX) NULL ,
[OUT_DIAGNOSIS] nvarchar(MAX) NULL ,
[ALLERGEN] nvarchar(255) NULL ,
[NURSINGCLASS] nvarchar(32) NULL ,
[PAYCLASS] nvarchar(32) NULL ,
[IS_PREG] decimal(11) NULL ,
[PREG_STARTTIME] nvarchar(20) NULL ,
[IS_LACT] decimal(11) NULL ,
[HEP_DAMAGE] decimal(11) NULL ,
[REN_DAMAGE] decimal(11) NULL ,
[STANDBY] nvarchar(255) NULL ,
[OPERATIONS] nvarchar(512) NULL ,
[INCISIONTYPES] nvarchar(128) NULL ,
[FIRSTDEPTNAME] nvarchar(32) NULL ,
[I_IN] decimal(11) NULL 
)


GO

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_TEMPERATURE
-- ----------------------------
DROP TABLE [T_MC_OUTHOSP_TEMPERATURE]
GO
CREATE TABLE [T_MC_OUTHOSP_TEMPERATURE] (
[HISCODE] nvarchar(128) NULL ,
[CASEID] nvarchar(64) NULL ,
[PATIENTID] nvarchar(32) NULL ,
[VISITID] nvarchar(32) NULL ,
[TAKETIME] nvarchar(20) NULL ,
[TEMPERATURE] decimal(38,4) NULL 
)


GO
