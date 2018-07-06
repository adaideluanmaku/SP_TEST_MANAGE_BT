/*
Navicat Oracle Data Transfer
Oracle Client Version : 12.2.0.1.0

Source Server         : 172.18.7.154_passpa
Source Server Version : 110200
Source Host           : 172.18.7.154:1521
Source Schema         : PASSPA

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2018-02-07 20:02:24
*/


-- ----------------------------
-- Table structure for MC_DICT_ALLERGEN
-- ----------------------------
DROP TABLE  "MC_DICT_ALLERGEN";
CREATE TABLE  "MC_DICT_ALLERGEN" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"ALLERCODE" NVARCHAR2(64) NULL ,
"ALLERNAME" NVARCHAR2(64) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"PASS_ALLERID" NUMBER(11) NULL ,
"PASS_ALLERTYPE" NUMBER(11) NULL ,
"PASS_ALLERNAME" NVARCHAR2(500) NULL ,
"MATCH_TIME" NVARCHAR2(20) NULL ,
"MATCH_USER" NVARCHAR2(32) NULL ,
"UNABLE_MATCH" NUMBER(11) NULL ,
"UNABLE_MATCH_DESC" NVARCHAR2(70) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_COSTITEM
-- ----------------------------
DROP TABLE  "MC_DICT_COSTITEM";
CREATE TABLE  "MC_DICT_COSTITEM" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"ITEMCODE" NVARCHAR2(64) NULL ,
"ITEMNAME" NVARCHAR2(128) NULL ,
"ITEMTYPE" NUMBER(11) NULL ,
"IS_BYX" NUMBER(11) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_DEPT
-- ----------------------------
DROP TABLE  "MC_DICT_DEPT";
CREATE TABLE  "MC_DICT_DEPT" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"IS_CLINIC" NUMBER(11) NULL ,
"IS_INHOSP" NUMBER(11) NULL ,
"IS_EMERGENCY" NUMBER(11) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_DISEASE
-- ----------------------------
DROP TABLE  "MC_DICT_DISEASE";
CREATE TABLE  "MC_DICT_DISEASE" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"DISCODE" NVARCHAR2(64) NULL ,
"DISNAME" NVARCHAR2(255) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"TYPECODE" NVARCHAR2(32) NULL ,
"TYPENAME" NVARCHAR2(128) NULL ,
"DIS_TYPE" NUMBER(11) NULL ,
"IS_MXB" NUMBER(11) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"PASS_ICD_CODE" NVARCHAR2(128) NULL ,
"PASS_ICD_NAME" NVARCHAR2(500) NULL ,
"MATCH_USER" NVARCHAR2(32) NULL ,
"MATCH_TIME" NVARCHAR2(20) NULL ,
"UNABLE_MATCH" NUMBER(11) NULL ,
"UNABLE_MATCH_DESC" NVARCHAR2(70) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_DOCTOR
-- ----------------------------
DROP TABLE  "MC_DICT_DOCTOR";
CREATE TABLE  "MC_DICT_DOCTOR" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL ,
"DOCTORLEVEL" NVARCHAR2(32) NULL ,
"ILEVEL" NUMBER(11) NULL ,
"IS_CLINIC" NUMBER(11) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"PRESPRIV" NUMBER(11) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"PASSWORD" NVARCHAR2(128) NULL ,
"ANTILEVEL" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_DRUG
-- ----------------------------
DROP TABLE  "MC_DICT_DRUG";
CREATE TABLE  "MC_DICT_DRUG" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"DRUGCODE" NVARCHAR2(64) NULL ,
"DRUGNAME" NVARCHAR2(128) NULL ,
"DRUGFORM" NVARCHAR2(128) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"DRUGGROUPCODE" NUMBER(11) NULL ,
"DRUGGROUPNAME" NVARCHAR2(128) NULL ,
"DRGGRP_SEARCHCODE" NVARCHAR2(128) NULL ,
"IS_BASEDRUG" NUMBER(11) NULL ,
"IS_BASEDRUG_P" NUMBER(11) NULL ,
"IS_ANTI" NUMBER(11) NULL ,
"ANTITYPE" NUMBER(11) NULL ,
"ANTILEVEL" NUMBER(11) NULL ,
"DRUGTYPE" NUMBER(11) NULL ,
"DRUGFORMTYPE" NUMBER(11) NULL ,
"SOCIALSECURITY_RATIO" NVARCHAR2(64) NULL ,
"IS_SOCIALSECURITY" NUMBER(11) NULL ,
"SOCIALSECURITY_DESC" NVARCHAR2(255) NULL ,
"JDMTYPE" NUMBER(11) NULL ,
"IS_STIMULANT" NUMBER(11) NULL ,
"STIMULANTINGRED" NVARCHAR2(64) NULL ,
"IS_SOLVENT" NUMBER(11) NULL ,
"IS_SRPREPARATIONS" NUMBER(11) NULL ,
"IS_NEEDSKINTEST" NUMBER(11) NULL ,
"IS_DEARMED" NUMBER(11) NULL ,
"IS_POISON" NUMBER(11) NULL ,
"IS_BLOODMED" NUMBER(11) NULL ,
"IS_SUGARMED" NUMBER(11) NULL ,
"OTCTYPE" NUMBER(11) NULL ,
"HIGH_ALERT_LEVEL" NUMBER(11) NULL ,
"IS_BISECTION_USE" NUMBER(11) NULL ,
"CLASSID" NUMBER(11) NULL ,
"CLASSTITLE" NVARCHAR2(128) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"OPERUSER" NVARCHAR2(32) NULL ,
"OPERTIME" NVARCHAR2(20) NULL ,
"TYPENAME" NVARCHAR2(256) NULL ,
"STATE" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_DRUG_PASS
-- ----------------------------
DROP TABLE  "MC_DICT_DRUG_PASS";
CREATE TABLE  "MC_DICT_DRUG_PASS" (
"PROID" NUMBER(11) NULL ,
"DRUG_UNIQUE_CODE" NVARCHAR2(255) NULL ,
"DRUGCODE" NVARCHAR2(64) NULL ,
"DRUGNAME" NVARCHAR2(128) NULL ,
"DRUGFORM" NVARCHAR2(128) NULL ,
"DRUGSPEC" NVARCHAR2(128) NULL ,
"COMP_NAME" NVARCHAR2(128) NULL ,
"APPROVALCODE" NVARCHAR2(64) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"DOSEUNIT" NVARCHAR2(32) NULL ,
"MATCH_SCHEME" NUMBER(11) NULL ,
"PASS_DRUGCODE" NUMBER(11) NULL ,
"PASS_APPROVALCODE" NVARCHAR2(500) NULL ,
"PASS_NAMETYPE" NUMBER(11) NULL ,
"PASS_DOSEUNIT" NVARCHAR2(256) NULL ,
"PASS_DRUGNAME" NVARCHAR2(500) NULL ,
"PASS_FORM_NAME" NVARCHAR2(128) NULL ,
"PASS_ST_STRENGTH" NVARCHAR2(1000) NULL ,
"PASS_ST_COMP_NAME" NVARCHAR2(500) NULL ,
"PASS_DIVIDEND" NUMBER NULL ,
"PASS_DIVISOR" NUMBER NULL ,
"MENULABEL" NVARCHAR2(256) NULL ,
"MATCH_USER" NVARCHAR2(32) NULL ,
"MATCH_TIME" NVARCHAR2(20) NULL ,
"UNABLE_MATCH" NUMBER(11) NULL ,
"UNABLE_MATCH_DESC" NVARCHAR2(70) NULL ,
"OPRPI_USER" NVARCHAR2(32) NULL ,
"OPRPI_TIME" NVARCHAR2(20) NULL ,
"PASS_UPSTATE" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_DRUG_SUB
-- ----------------------------
DROP TABLE  "MC_DICT_DRUG_SUB";
CREATE TABLE  "MC_DICT_DRUG_SUB" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"DRUGCODE" NVARCHAR2(255) NULL ,
"DRUGNAME" NVARCHAR2(256) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"DRUGFORM" NVARCHAR2(128) NULL ,
"DRUGSPEC" NVARCHAR2(255) NULL ,
"COSTUNIT" NVARCHAR2(128) NULL ,
"DDD" NUMBER NULL ,
"DDDUNIT" NVARCHAR2(128) NULL ,
"DDD_COSTUNIT" NUMBER NULL ,
"ADDDATE" NVARCHAR2(20) NULL ,
"IS_USE" NUMBER(11) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"STATE" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_EXAM
-- ----------------------------
DROP TABLE  "MC_DICT_EXAM";
CREATE TABLE  "MC_DICT_EXAM" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"EXAMCODE" NVARCHAR2(64) NULL ,
"EXAMNAME" NVARCHAR2(128) NULL ,
"TYPE" NUMBER(11) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_FREQUENCY
-- ----------------------------
DROP TABLE  "MC_DICT_FREQUENCY";
CREATE TABLE  "MC_DICT_FREQUENCY" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"FREQUENCY" NVARCHAR2(32) NULL ,
"TIMES" NUMBER(11) NULL ,
"DAYS" NUMBER(11) NULL ,
"PHARMFREQUENCY" NVARCHAR2(32) NULL ,
"UNABLE_MATCH" NUMBER(11) NULL ,
"MATCH_DESC" NVARCHAR2(128) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_LAB
-- ----------------------------
DROP TABLE  "MC_DICT_LAB";
CREATE TABLE  "MC_DICT_LAB" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"LABCODE" NVARCHAR2(64) NULL ,
"LABNAME" NVARCHAR2(128) NULL ,
"TYPE" NUMBER(11) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_LABSUB
-- ----------------------------
DROP TABLE  "MC_DICT_LABSUB";
CREATE TABLE  "MC_DICT_LABSUB" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"ITEMCODE" NVARCHAR2(64) NULL ,
"ITEMNAME" NVARCHAR2(128) NULL ,
"TYPE_ID" NUMBER(11) NULL ,
"MEDSHOW_ID" NUMBER(11) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_OPERATION
-- ----------------------------
DROP TABLE  "MC_DICT_OPERATION";
CREATE TABLE  "MC_DICT_OPERATION" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"OPERATIONCODE" NVARCHAR2(64) NULL ,
"OPERATIONNAME" NVARCHAR2(128) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"TYPENAME" NVARCHAR2(128) NULL ,
"USEANTI" NUMBER(11) NULL ,
"PREMOMENT_LOW" NUMBER NULL ,
"PREMOMENT_HIGH" NUMBER NULL ,
"DRUGTIME" NUMBER(11) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for MC_DICT_ROUTE
-- ----------------------------
DROP TABLE  "MC_DICT_ROUTE";
CREATE TABLE  "MC_DICT_ROUTE" (
"MATCH_SCHEME" NUMBER(11) NULL ,
"ROUTECODE" NVARCHAR2(128) NULL ,
"ROUTENAME" NVARCHAR2(128) NULL ,
"SEARCHCODE" NVARCHAR2(256) NULL ,
"ROUTE_TYPE" NUMBER(11) NULL ,
"ABBREV" NVARCHAR2(10) NULL ,
"IS_SAVE" NUMBER(11) NULL ,
"PASS_ROUTEID" NUMBER(11) NULL ,
"PASS_ROUTE_NAME" NVARCHAR2(256) NULL ,
"MATCH_USER" NVARCHAR2(32) NULL ,
"MATCH_TIME" NVARCHAR2(20) NULL ,
"UNABLE_MATCH" NUMBER(11) NULL ,
"UNABLE_MATCH_DESC" NVARCHAR2(70) NULL ,
"ISSKINTEST" NUMBER(11) NULL ,
"UPDATEDATE" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_CLINIC_ALLERGEN
-- ----------------------------
DROP TABLE "T_MC_CLINIC_ALLERGEN";
CREATE TABLE "T_MC_CLINIC_ALLERGEN" (
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"CLINICNO" NVARCHAR2(32) NULL ,
"ALLERCODE" NVARCHAR2(64) NULL ,
"ALLERNAME" NVARCHAR2(64) NULL ,
"SYMPTOM" NVARCHAR2(255) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_CLINIC_COST
-- ----------------------------
DROP TABLE "T_MC_CLINIC_COST";
CREATE TABLE "T_MC_CLINIC_COST" (
"IID" NUMBER(11) NULL ,
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"CLINICNO" NVARCHAR2(32) NULL ,
"PRESCNO" NVARCHAR2(32) NULL ,
"COSTTYPE" NUMBER(11) NULL ,
"DRUGINDEX" NUMBER(11) NULL ,
"ITEMCODE" NVARCHAR2(128) NULL ,
"ITEMNAME" NVARCHAR2(128) NULL ,
"DRUGFORM" NVARCHAR2(128) NULL ,
"DRUGSPEC" NVARCHAR2(128) NULL ,
"DRUGSCCJ" NVARCHAR2(128) NULL ,
"ITEMUNIT" NVARCHAR2(96) NULL ,
"ITEMNUM" NUMBER NULL ,
"COST" NUMBER NULL ,
"COSTTIME" NVARCHAR2(20) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"MEDGROUPCODE" NVARCHAR2(64) NULL ,
"MEDGROUPNAME" NVARCHAR2(64) NULL ,
"ROUTECODE" NVARCHAR2(128) NULL ,
"PHARMACISTS" NVARCHAR2(32) NULL ,
"PHARMACISTS_" NVARCHAR2(32) NULL ,
"IS_USE" NUMBER(11) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_CLINIC_DISEASE
-- ----------------------------
DROP TABLE "T_MC_CLINIC_DISEASE";
CREATE TABLE "T_MC_CLINIC_DISEASE" (
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"CLINICNO" NVARCHAR2(32) NULL ,
"PRESCNO" NVARCHAR2(32) NULL ,
"DISCODE" NVARCHAR2(64) NULL ,
"DISNAME" NVARCHAR2(255) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_CLINIC_EXAM
-- ----------------------------
DROP TABLE "T_MC_CLINIC_EXAM";
CREATE TABLE "T_MC_CLINIC_EXAM" (
"IENDDATE" NUMBER(11) NULL ,
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"CLINICNO" NVARCHAR2(32) NULL ,
"EXAMCODE" NVARCHAR2(64) NULL ,
"EXAMNAME" NVARCHAR2(128) NULL ,
"REQUESTNO" NVARCHAR2(64) NULL ,
"BODYPART" NVARCHAR2(512) NULL ,
"EXAMRESULT" NCLOB NULL ,
"INSTRUMENT" NVARCHAR2(32) NULL ,
"REPORTTIME" NVARCHAR2(20) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_CLINIC_LAB
-- ----------------------------
DROP TABLE "T_MC_CLINIC_LAB";
CREATE TABLE "T_MC_CLINIC_LAB" (
"IENDDATE" NUMBER(11) NULL ,
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"CLINICNO" NVARCHAR2(32) NULL ,
"REQUESTNO" NVARCHAR2(64) NULL ,
"LABCODE" NVARCHAR2(256) NULL ,
"LABNAME" NVARCHAR2(512) NULL ,
"SAMPLETYPE" NVARCHAR2(32) NULL ,
"SAMPLINGTIME" NVARCHAR2(20) NULL ,
"ITEMCODE" NVARCHAR2(64) NULL ,
"ITEMNAME" NVARCHAR2(128) NULL ,
"LABRESULT" NVARCHAR2(64) NULL ,
"RESULTFLAG" NVARCHAR2(2) NULL ,
"RANGE_0" NVARCHAR2(128) NULL ,
"UNIT" NVARCHAR2(32) NULL ,
"REPORTTIME" NVARCHAR2(20) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"INSTRUMENT" NVARCHAR2(32) NULL ,
"DD" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_CLINIC_ORDER
-- ----------------------------
DROP TABLE "T_MC_CLINIC_ORDER";
CREATE TABLE "T_MC_CLINIC_ORDER" (
"HISCODE" NVARCHAR2(128) NULL ,
"CID" NVARCHAR2(64) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"CLINICNO" NVARCHAR2(32) NULL ,
"PRESCNO" NVARCHAR2(32) NULL ,
"PRESCTYPE" NUMBER(11) NULL ,
"NUM" NVARCHAR2(20) NULL ,
"NUMUNIT" NVARCHAR2(64) NULL ,
"COST" NUMBER NULL ,
"ORDERNO" NVARCHAR2(32) NULL ,
"ORDERTYPE" NUMBER(11) NULL ,
"GROUPSTATE" NUMBER(11) NULL ,
"GROUPTAG" NVARCHAR2(32) NULL ,
"DRUG_UNIQUE_CODE" NVARCHAR2(255) NULL ,
"ORDERCODE" NVARCHAR2(128) NULL ,
"ORDERNAME" NVARCHAR2(256) NULL ,
"DRUGFORM" NVARCHAR2(128) NULL ,
"DRUGSPEC" NVARCHAR2(128) NULL ,
"ROUTECODE" NVARCHAR2(128) NULL ,
"ROUTENAME" NVARCHAR2(128) NULL ,
"SINGLEDOSE" NVARCHAR2(24) NULL ,
"DOSEUNIT" NVARCHAR2(64) NULL ,
"FREQUENCY" NVARCHAR2(32) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"STARTDATETIME" NVARCHAR2(20) DEFAULT ''  NULL ,
"DAYS" NUMBER(11) NULL ,
"PURPOSE" NUMBER(11) NULL ,
"REMARK" NVARCHAR2(255) NULL ,
"ORDERSTATUS" NUMBER(11) NULL ,
"IS_ALLOW" NUMBER(11) NULL ,
"REASONABLE_DESC" NVARCHAR2(128) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_CLINIC_PATIENT
-- ----------------------------
DROP TABLE "T_MC_CLINIC_PATIENT";
CREATE TABLE "T_MC_CLINIC_PATIENT" (
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"IENDDATE" NUMBER(11) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"CLINICNO" NVARCHAR2(32) NULL ,
"PATIENTNAME" NVARCHAR2(32) NULL ,
"SEX" NVARCHAR2(10) NULL ,
"BIRTHDATE" NVARCHAR2(20) NULL ,
"AGE" NVARCHAR2(24) NULL ,
"IAGE" NUMBER(11) NULL ,
"COST" NUMBER NULL ,
"HEIGHT" NVARCHAR2(16) NULL ,
"WEIGHT" NVARCHAR2(16) NULL ,
"IDENTITYCARD" NVARCHAR2(24) NULL ,
"TELEPHONE" NVARCHAR2(48) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"MEDGROUPCODE" NVARCHAR2(64) NULL ,
"MEDGROUPNAME" NVARCHAR2(64) NULL ,
"ENDDATE" NVARCHAR2(20) NULL ,
"DIAGNOSIS" NCLOB NULL ,
"ALLERGEN" NVARCHAR2(255) NULL ,
"PAYCLASS" NVARCHAR2(32) NULL ,
"IS_EMERGENCY" NUMBER(11) NULL ,
"IS_PREG" NUMBER(11) NULL ,
"PREG_STARTTIME" NVARCHAR2(20) NULL ,
"IS_LACT" NUMBER(11) NULL ,
"HEP_DAMAGE" NUMBER(11) NULL ,
"REN_DAMAGE" NUMBER(11) NULL ,
"STANDBY" NVARCHAR2(255) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_INHOSP_ALLERGEN
-- ----------------------------
DROP TABLE "T_MC_INHOSP_ALLERGEN";
CREATE TABLE "T_MC_INHOSP_ALLERGEN" (
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"ALLERCODE" NVARCHAR2(64) NULL ,
"ALLERNAME" NVARCHAR2(64) NULL ,
"SYMPTOM" NVARCHAR2(255) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_INHOSP_COST
-- ----------------------------
DROP TABLE "T_MC_INHOSP_COST";
CREATE TABLE "T_MC_INHOSP_COST" (
"IID" NUMBER(11) NULL ,
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"COSTTYPE" NUMBER(11) NULL ,
"DRUGINDEX" NUMBER(11) NULL ,
"ITEMCODE" NVARCHAR2(128) NULL ,
"ITEMNAME" NVARCHAR2(128) NULL ,
"DRUGFORM" NVARCHAR2(128) NULL ,
"DRUGSPEC" NVARCHAR2(128) NULL ,
"DRUGSCCJ" NVARCHAR2(128) NULL ,
"ITEMUNIT" NVARCHAR2(96) NULL ,
"ITEMNUM" NUMBER NULL ,
"COST" NUMBER NULL ,
"COSTTIME" NVARCHAR2(20) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL ,
"WARDCODE" NVARCHAR2(64) NULL ,
"WARDNAME" NVARCHAR2(64) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"MEDGROUPCODE" NVARCHAR2(64) NULL ,
"MEDGROUPNAME" NVARCHAR2(64) NULL ,
"IS_OUT" NUMBER(11) NULL ,
"IS_USE" NUMBER(11) NULL ,
"ROUTECODE" NVARCHAR2(128) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_INHOSP_DISEASE
-- ----------------------------
DROP TABLE "T_MC_INHOSP_DISEASE";
CREATE TABLE "T_MC_INHOSP_DISEASE" (
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"DISCODE" NVARCHAR2(64) NULL ,
"DISNAME" NVARCHAR2(255) NULL ,
"TREATMENT" NUMBER(11) NULL ,
"IS_HOSPINFECTION" NUMBER(11) NULL ,
"DISEASETYPE" NUMBER(11) NULL ,
"IS_MAIN" NUMBER(11) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_INHOSP_EXAM
-- ----------------------------
DROP TABLE "T_MC_INHOSP_EXAM";
CREATE TABLE "T_MC_INHOSP_EXAM" (
"IENDDATE" NUMBER(11) NULL ,
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"EXAMCODE" NVARCHAR2(64) NULL ,
"EXAMNAME" NVARCHAR2(128) NULL ,
"REQUESTNO" NVARCHAR2(64) NULL ,
"BODYPART" NVARCHAR2(512) NULL ,
"EXAMRESULT" NCLOB NULL ,
"INSTRUMENT" NVARCHAR2(32) NULL ,
"REPORTTIME" NVARCHAR2(20) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_INHOSP_LAB
-- ----------------------------
DROP TABLE "T_MC_INHOSP_LAB";
CREATE TABLE "T_MC_INHOSP_LAB" (
"IENDDATE" NUMBER(11) NULL ,
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"REQUESTNO" NVARCHAR2(64) NULL ,
"LABCODE" NVARCHAR2(256) NULL ,
"LABNAME" NVARCHAR2(512) NULL ,
"SAMPLETYPE" NVARCHAR2(32) NULL ,
"SAMPLINGTIME" NVARCHAR2(20) NULL ,
"ITEMCODE" NVARCHAR2(64) NULL ,
"ITEMNAME" NVARCHAR2(128) NULL ,
"LABRESULT" NVARCHAR2(64) NULL ,
"RESULTFLAG" NVARCHAR2(2) NULL ,
"RANGE_0" NVARCHAR2(128) NULL ,
"UNIT" NVARCHAR2(32) NULL ,
"REPORTTIME" NVARCHAR2(20) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"INSTRUMENT" NVARCHAR2(32) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_INHOSP_OPERATION
-- ----------------------------
DROP TABLE "T_MC_INHOSP_OPERATION";
CREATE TABLE "T_MC_INHOSP_OPERATION" (
"HISCODE" NVARCHAR2(128) NULL ,
"OPRID" NVARCHAR2(64) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"OPERATIONCODE" NVARCHAR2(64) NULL ,
"OPERATIONNAME" NVARCHAR2(128) NULL ,
"INCISIONTYPE" NUMBER(11) NULL ,
"STARTDATE" NVARCHAR2(20) NULL ,
"ENDDATE" NVARCHAR2(20) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_INHOSP_ORDER
-- ----------------------------
DROP TABLE "T_MC_INHOSP_ORDER";
CREATE TABLE "T_MC_INHOSP_ORDER" (
"HISCODE" NVARCHAR2(128) NULL ,
"CID" NVARCHAR2(64) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"ORDERNO" NVARCHAR2(32) NULL ,
"ORDERTYPE" NUMBER(11) NULL ,
"GROUPSTATE" NUMBER(11) NULL ,
"GROUPTAG" NVARCHAR2(32) NULL ,
"IS_TEMP" NUMBER(11) NULL ,
"DRUG_UNIQUE_CODE" NVARCHAR2(255) NULL ,
"ORDERCODE" NVARCHAR2(128) NULL ,
"ORDERNAME" NVARCHAR2(256) NULL ,
"DRUGFORM" NVARCHAR2(128) NULL ,
"DRUGSPEC" NVARCHAR2(128) NULL ,
"ROUTECODE" NVARCHAR2(128) NULL ,
"ROUTENAME" NVARCHAR2(128) NULL ,
"SINGLEDOSE" NVARCHAR2(24) NULL ,
"DOSEUNIT" NVARCHAR2(64) NULL ,
"FREQUENCY" NVARCHAR2(32) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL ,
"WARDCODE" NVARCHAR2(64) NULL ,
"WARDNAME" NVARCHAR2(64) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"EXECUTETIME" NVARCHAR2(20) NULL ,
"STARTDATETIME" NVARCHAR2(20) NULL ,
"ENDDATETIME" NVARCHAR2(20) NULL ,
"PA_ENDDATETIME" NVARCHAR2(20) NULL ,
"PURPOSE" NUMBER(11) NULL ,
"REMARK" NVARCHAR2(255) NULL ,
"IS_OUT" NUMBER(11) NULL ,
"ORDERSTATUS" NUMBER(11) NULL ,
"IS_ALLOW" NUMBER(11) NULL ,
"REASONABLE_DESC" NVARCHAR2(128) NULL ,
"MEDITIME" NUMBER(11) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_INHOSP_PATIENT
-- ----------------------------
DROP TABLE "T_MC_INHOSP_PATIENT";
CREATE TABLE "T_MC_INHOSP_PATIENT" (
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"HOSPITALNO" NVARCHAR2(32) NULL ,
"PATIENTNAME" NVARCHAR2(32) NULL ,
"SEX" NVARCHAR2(10) NULL ,
"BIRTHDATE" NVARCHAR2(20) NULL ,
"AGE" NVARCHAR2(24) NULL ,
"IAGE" NUMBER(11) NULL ,
"COST" NUMBER NULL ,
"HEIGHT" NVARCHAR2(16) NULL ,
"WEIGHT" NVARCHAR2(16) NULL ,
"IDENTITYCARD" NVARCHAR2(24) NULL ,
"TELEPHONE" NVARCHAR2(48) NULL ,
"BEDNO" NVARCHAR2(20) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL ,
"WARDCODE" NVARCHAR2(64) NULL ,
"WARDNAME" NVARCHAR2(64) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"MEDGROUPCODE" NVARCHAR2(64) NULL ,
"MEDGROUPNAME" NVARCHAR2(64) NULL ,
"STARTDATE" NVARCHAR2(20) NULL ,
"ENDDATE" NVARCHAR2(20) NULL ,
"ACCOUNTDATE" NVARCHAR2(20) NULL ,
"IN_DIAGNOSIS" NCLOB NULL ,
"ALLERGEN" NVARCHAR2(255) NULL ,
"NURSINGCLASS" NVARCHAR2(32) NULL ,
"PAYCLASS" NVARCHAR2(32) NULL ,
"IS_PREG" NUMBER(11) NULL ,
"PREG_STARTTIME" NVARCHAR2(20) NULL ,
"IS_LACT" NUMBER(11) NULL ,
"HEP_DAMAGE" NUMBER(11) NULL ,
"REN_DAMAGE" NUMBER(11) NULL ,
"STANDBY" NVARCHAR2(255) NULL ,
"OPERATIONS" NVARCHAR2(512) NULL ,
"INCISIONTYPES" NVARCHAR2(128) NULL ,
"FIRSTDEPTNAME" NVARCHAR2(32) NULL ,
"I_IN" NUMBER(11) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_INHOSP_TEMPERATURE
-- ----------------------------
DROP TABLE "T_MC_INHOSP_TEMPERATURE";
CREATE TABLE "T_MC_INHOSP_TEMPERATURE" (
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"TAKETIME" NVARCHAR2(20) NULL ,
"TEMPERATURE" NUMBER NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_ALLERGEN
-- ----------------------------
DROP TABLE "T_MC_OUTHOSP_ALLERGEN";
CREATE TABLE "T_MC_OUTHOSP_ALLERGEN" (
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"ALLERCODE" NVARCHAR2(64) NULL ,
"ALLERNAME" NVARCHAR2(64) NULL ,
"SYMPTOM" NVARCHAR2(255) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_COST
-- ----------------------------
DROP TABLE "T_MC_OUTHOSP_COST";
CREATE TABLE "T_MC_OUTHOSP_COST" (
"IID" NUMBER(11) NULL ,
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"COSTTYPE" NUMBER(11) NULL ,
"DRUGINDEX" NUMBER(11) NULL ,
"ITEMCODE" NVARCHAR2(128) NULL ,
"ITEMNAME" NVARCHAR2(128) NULL ,
"DRUGFORM" NVARCHAR2(128) NULL ,
"DRUGSPEC" NVARCHAR2(128) NULL ,
"DRUGSCCJ" NVARCHAR2(128) NULL ,
"ITEMUNIT" NVARCHAR2(96) NULL ,
"ITEMNUM" NUMBER NULL ,
"COST" NUMBER NULL ,
"COSTTIME" NVARCHAR2(20) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL ,
"WARDCODE" NVARCHAR2(64) NULL ,
"WARDNAME" NVARCHAR2(64) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"MEDGROUPCODE" NVARCHAR2(64) NULL ,
"MEDGROUPNAME" NVARCHAR2(64) NULL ,
"IS_OUT" NUMBER(11) NULL ,
"IS_USE" NUMBER(11) NULL ,
"ROUTECODE" NVARCHAR2(128) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_DISEASE
-- ----------------------------
DROP TABLE "T_MC_OUTHOSP_DISEASE";
CREATE TABLE "T_MC_OUTHOSP_DISEASE" (
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"DISCODE" NVARCHAR2(64) NULL ,
"DISNAME" NVARCHAR2(255) NULL ,
"TREATMENT" NUMBER(11) NULL ,
"IS_HOSPINFECTION" NUMBER(11) NULL ,
"DISEASETYPE" NUMBER(11) NULL ,
"IS_MAIN" NUMBER(11) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_EXAM
-- ----------------------------
DROP TABLE "T_MC_OUTHOSP_EXAM";
CREATE TABLE "T_MC_OUTHOSP_EXAM" (
"IENDDATE" NUMBER(11) NULL ,
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"EXAMCODE" NVARCHAR2(64) NULL ,
"EXAMNAME" NVARCHAR2(128) NULL ,
"REQUESTNO" NVARCHAR2(64) NULL ,
"BODYPART" NVARCHAR2(512) NULL ,
"EXAMRESULT" NCLOB NULL ,
"INSTRUMENT" NVARCHAR2(32) NULL ,
"REPORTTIME" NVARCHAR2(20) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_LAB
-- ----------------------------
DROP TABLE "T_MC_OUTHOSP_LAB";
CREATE TABLE "T_MC_OUTHOSP_LAB" (
"IENDDATE" NUMBER(11) NULL ,
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"REQUESTNO" NVARCHAR2(64) NULL ,
"LABCODE" NVARCHAR2(256) NULL ,
"LABNAME" NVARCHAR2(512) NULL ,
"SAMPLETYPE" NVARCHAR2(32) NULL ,
"SAMPLINGTIME" NVARCHAR2(20) NULL ,
"ITEMCODE" NVARCHAR2(64) NULL ,
"ITEMNAME" NVARCHAR2(128) NULL ,
"LABRESULT" NVARCHAR2(64) NULL ,
"RESULTFLAG" NVARCHAR2(2) NULL ,
"RANGE_0" NVARCHAR2(128) NULL ,
"UNIT" NVARCHAR2(32) NULL ,
"REPORTTIME" NVARCHAR2(20) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"INSTRUMENT" NVARCHAR2(32) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_OPERATION
-- ----------------------------
DROP TABLE "T_MC_OUTHOSP_OPERATION";
CREATE TABLE "T_MC_OUTHOSP_OPERATION" (
"HISCODE" NVARCHAR2(128) NULL ,
"OPRID" NVARCHAR2(64) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"OPERATIONCODE" NVARCHAR2(64) NULL ,
"OPERATIONNAME" NVARCHAR2(128) NULL ,
"INCISIONTYPE" NUMBER(11) NULL ,
"STARTDATE" NVARCHAR2(20) NULL ,
"ENDDATE" NVARCHAR2(20) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_ORDER
-- ----------------------------
DROP TABLE "T_MC_OUTHOSP_ORDER";
CREATE TABLE "T_MC_OUTHOSP_ORDER" (
"HISCODE" NVARCHAR2(128) NULL ,
"CID" NVARCHAR2(64) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"ORDERNO" NVARCHAR2(32) NULL ,
"ORDERTYPE" NUMBER(11) NULL ,
"GROUPSTATE" NUMBER(11) NULL ,
"GROUPTAG" NVARCHAR2(32) NULL ,
"IS_TEMP" NUMBER(11) NULL ,
"DRUG_UNIQUE_CODE" NVARCHAR2(255) NULL ,
"ORDERCODE" NVARCHAR2(128) NULL ,
"ORDERNAME" NVARCHAR2(256) NULL ,
"DRUGFORM" NVARCHAR2(128) NULL ,
"DRUGSPEC" NVARCHAR2(128) NULL ,
"ROUTECODE" NVARCHAR2(128) NULL ,
"ROUTENAME" NVARCHAR2(128) NULL ,
"SINGLEDOSE" NVARCHAR2(24) NULL ,
"DOSEUNIT" NVARCHAR2(64) NULL ,
"FREQUENCY" NVARCHAR2(32) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL ,
"WARDCODE" NVARCHAR2(64) NULL ,
"WARDNAME" NVARCHAR2(64) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"EXECUTETIME" NVARCHAR2(20) NULL ,
"STARTDATETIME" NVARCHAR2(20) NULL ,
"ENDDATETIME" NVARCHAR2(20) NULL ,
"PA_ENDDATETIME" NVARCHAR2(20) NULL ,
"PURPOSE" NUMBER(11) NULL ,
"REMARK" NVARCHAR2(255) NULL ,
"IS_OUT" NUMBER(11) NULL ,
"ORDERSTATUS" NUMBER(11) NULL ,
"IS_ALLOW" NUMBER(11) NULL ,
"REASONABLE_DESC" NVARCHAR2(128) NULL ,
"MEDITIME" NUMBER(11) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_PATIENT
-- ----------------------------
DROP TABLE "T_MC_OUTHOSP_PATIENT";
CREATE TABLE "T_MC_OUTHOSP_PATIENT" (
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"IENDDATE" NUMBER(11) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"HOSPITALNO" NVARCHAR2(32) NULL ,
"PATIENTNAME" NVARCHAR2(32) NULL ,
"SEX" NVARCHAR2(10) NULL ,
"BIRTHDATE" NVARCHAR2(20) NULL ,
"AGE" NVARCHAR2(24) NULL ,
"IAGE" NUMBER(11) NULL ,
"COST" NUMBER NULL ,
"HEIGHT" NVARCHAR2(16) NULL ,
"WEIGHT" NVARCHAR2(16) NULL ,
"IDENTITYCARD" NVARCHAR2(24) NULL ,
"TELEPHONE" NVARCHAR2(48) NULL ,
"BEDNO" NVARCHAR2(20) NULL ,
"DEPTCODE" NVARCHAR2(64) NULL ,
"DEPTNAME" NVARCHAR2(64) NULL ,
"DOCTORCODE" NVARCHAR2(64) NULL ,
"DOCTORNAME" NVARCHAR2(32) NULL ,
"MEDGROUPCODE" NVARCHAR2(64) NULL ,
"MEDGROUPNAME" NVARCHAR2(64) NULL ,
"WARDCODE" NVARCHAR2(64) NULL ,
"WARDNAME" NVARCHAR2(64) NULL ,
"STARTDATE" NVARCHAR2(20) NULL ,
"ENDDATE" NVARCHAR2(20) NULL ,
"ACCOUNTDATE" NVARCHAR2(20) NULL ,
"IN_DIAGNOSIS" NCLOB NULL ,
"OUT_DIAGNOSIS" NCLOB NULL ,
"ALLERGEN" NVARCHAR2(255) NULL ,
"NURSINGCLASS" NVARCHAR2(32) NULL ,
"PAYCLASS" NVARCHAR2(32) NULL ,
"IS_PREG" NUMBER(11) NULL ,
"PREG_STARTTIME" NVARCHAR2(20) NULL ,
"IS_LACT" NUMBER(11) NULL ,
"HEP_DAMAGE" NUMBER(11) NULL ,
"REN_DAMAGE" NUMBER(11) NULL ,
"STANDBY" NVARCHAR2(255) NULL ,
"OPERATIONS" NVARCHAR2(512) NULL ,
"INCISIONTYPES" NVARCHAR2(128) NULL ,
"FIRSTDEPTNAME" NVARCHAR2(32) NULL ,
"I_IN" NUMBER(11) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for T_MC_OUTHOSP_TEMPERATURE
-- ----------------------------
DROP TABLE "T_MC_OUTHOSP_TEMPERATURE";
CREATE TABLE "T_MC_OUTHOSP_TEMPERATURE" (
"HISCODE" NVARCHAR2(128) NULL ,
"CASEID" NVARCHAR2(64) NULL ,
"PATIENTID" NVARCHAR2(32) NULL ,
"VISITID" NVARCHAR2(32) NULL ,
"TAKETIME" NVARCHAR2(20) NULL ,
"TEMPERATURE" NUMBER NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
