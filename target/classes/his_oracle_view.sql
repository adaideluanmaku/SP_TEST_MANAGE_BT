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

Date: 2018-02-07 20:02:45
*/


-- ----------------------------
-- View structure for MDC2_DICT_ALLERGEN_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_ALLERGEN_VIEW" AS 
SELECT 'chview' AS hiscode ,allercode ,allername, '2050-12-12' as updatedate FROM mc_dict_allergen mda;

-- ----------------------------
-- View structure for MDC2_DICT_COST_ITEM_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_COST_ITEM_VIEW" AS 
SELECT 'chview' AS hiscode , itemcode , itemname ,itemtype, '2050-12-12' as updatedate FROM    mc_dict_costitem ;

-- ----------------------------
-- View structure for MDC2_DICT_DEPT_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_DEPT_VIEW" AS 
SELECT 'chview'AS hiscode, deptcode, deptname, is_clinic, is_inhosp, is_emergency, '2050-12-12' as updatedate FROM mc_dict_dept;

-- ----------------------------
-- View structure for MDC2_DICT_DISEASE_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_DISEASE_VIEW" AS 
SELECT 'chview'AS hiscode, discode, disname,'2050-12-12' as updatedate FROM mc_dict_disease mdd;

-- ----------------------------
-- View structure for MDC2_DICT_DOCTOR_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_DOCTOR_VIEW" AS 
SELECT 'chview'AS hiscode, doctorcode, doctorname, deptcode, deptname, ilevel, doctorlevel, is_clinic, prespriv,antilevel,'2050-12-12' as updatedate FROM mc_dict_doctor;

-- ----------------------------
-- View structure for MDC2_DICT_DRUG_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_DRUG_VIEW" AS 
SELECT 'chview'AS hiscode,a.drugcode, c.drug_unique_code, b.drugname, b.drugform, b.drugspec, c.approvalcode, c.comp_name, a.drugtype, a.DRUGGROUPCODE, a.DRUGGROUPNAME, a.DRGGRP_SEARCHCODE, c.doseunit, b.costunit, b.adddate, b.is_use, a.is_anti, a.antitype, a.antilevel, b.ddd, b.dddunit, a.is_basedrug,case when a.DRUGGROUPCODE<100 then 100/(a.DRUGGROUPCODE)+1.4 when a.DRUGGROUPCODE>100 and a.DRUGGROUPCODE<1000 then 1000/(a.DRUGGROUPCODE)+1.7 when  a.DRUGGROUPCODE>1000 and a.DRUGGROUPCODE<10000 then 10000/(a.DRUGGROUPCODE)+1.7 else 10.8 end as unitprice , '2050-12-12' as updatedate FROM	mc_dict_drug a left join	mc_dict_drug_sub b on a.drugcode=b.drugcode and a.match_scheme=b.match_scheme left join 	mc_dict_drug_pass c on a.drugcode = c.drugcode and c.match_scheme=b.match_scheme;

-- ----------------------------
-- View structure for MDC2_DICT_EXAM_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_EXAM_VIEW" AS 
SELECT 'chview' AS hiscode,examcode, mde.examname , '2050-12-12' as updatedate FROM mc_dict_exam mde;

-- ----------------------------
-- View structure for MDC2_DICT_FREQUENCY_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_FREQUENCY_VIEW" AS 
SELECT 'chview' AS hiscode,mdf.frequency, '2050-12-12' as updatedate FROM mc_dict_frequency mdf;

-- ----------------------------
-- View structure for MDC2_DICT_LAB_ITEM_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_LAB_ITEM_VIEW" AS 
SELECT 'chview' AS hiscode,itemcode, itemname, '2050-12-12' as updatedate FROM mc_dict_labsub;

-- ----------------------------
-- View structure for MDC2_DICT_LAB_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_LAB_VIEW" AS 
SELECT 'chview' AS hiscode,labcode, labname, '2050-12-12' as updatedate FROM mc_dict_lab;

-- ----------------------------
-- View structure for MDC2_DICT_OPERATION_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_OPERATION_VIEW" AS 
SELECT 'chview' AS hiscode,operationcode,mdo.operationname, '2050-12-12' as updatedate FROM mc_dict_operation mdo;

-- ----------------------------
-- View structure for MDC2_DICT_ROUTE_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_DICT_ROUTE_VIEW" AS 
SELECT 'chview' AS hiscode,routecode, routename, '2050-12-12' as updatedate FROM mc_dict_route;


-- ----------------------------
-- View structure for MDC2_MZ_ALLERGEN_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_MZ_ALLERGEN_VIEW" AS 
SELECT hiscode,patientid, clinicno, allercode, allername, symptom FROM t_mc_clinic_allergen;

-- ----------------------------
-- View structure for MDC2_MZ_COST_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_MZ_COST_VIEW" AS 
SELECT  hiscode ,             patientid ,             clinicno ,             prescno ,             itemcode ,             itemname ,             drugform ,             drugspec ,             drugsccj ,             itemunit ,             itemnum ,             cost ,             costtime ,             deptcode ,             deptname ,             doctorcode ,             doctorname ,             medgroupcode ,             medgroupname ,             routecode ,             costtype,             pharmacists,             pharmacists_, 						DRUGINDEX      FROM    t_mc_clinic_cost;

-- ----------------------------
-- View structure for MDC2_MZ_DISEASE_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_MZ_DISEASE_VIEW" AS 
SELECT hiscode,prescno, patientid, clinicno, discode, disname FROM t_mc_clinic_disease;

-- ----------------------------
-- View structure for MDC2_MZ_EXAM_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_MZ_EXAM_VIEW" AS 
SELECT   hiscode,patientid ,             clinicno ,            examcode ,             examname ,              requestno ,             bodypart ,             examresult ,              reporttime ,              doctorname as doctorname     FROM    T_MC_CLINIC_EXAM;

-- ----------------------------
-- View structure for MDC2_MZ_LAB_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_MZ_LAB_VIEW" AS 
SELECT   hiscode,patientid ,             clinicno ,            clinicno as  requestno ,             labcode ,             labname ,             sampletype ,            samplingtime ,             itemcode ,            itemname ,            labresult ,             resultflag ,             range_0 ,             unit ,             reporttime ,             doctorname     FROM    t_mc_clinic_lab;

-- ----------------------------
-- View structure for MDC2_MZ_ORDERS_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_MZ_ORDERS_VIEW" AS 
SELECT  hiscode ,             prescno ,             presctype,             cid ,             patientid ,             clinicno ,             orderno ,             ordertype  ,             grouptag,             drug_unique_code ,             ordercode ,             ordername ,             drugform ,             drugspec ,             routecode ,             routename ,             singledose ,             doseunit ,             frequency,             num ,             numunit ,             cost ,             deptcode ,             deptname ,             doctorcode ,             doctorname ,            startdatetime ,             days,            REMARK,            purpose, reasonable_desc     FROM    T_MC_CLINIC_ORDER;

-- ----------------------------
-- View structure for MDC2_MZ_PATIENT_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_MZ_PATIENT_VIEW" AS 
SELECT  hiscode ,             patientid ,             clinicno ,            patientname ,             sex ,             birthdate ,             age ,             height ,             weight ,             identitycard ,             telephone ,             enddate ,             deptcode ,             deptname ,             doctorcode ,             doctorname ,             medgroupcode ,             medgroupname ,             payclass ,             is_emergency ,             is_preg ,             preg_starttime ,             is_lact ,             hep_damage ,             ren_damage ,             standby     FROM    t_mc_clinic_patient;

-- ----------------------------
-- View structure for MDC2_MZ_PRESC_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_MZ_PRESC_VIEW" AS 
SELECT hiscode,patientid, clinicno, prescno,  presctype,  prescname, sum(cost)cost, deptcode, deptname, doctorcode, doctorname, medgroupcode, medgroupname, max(enddate) enddate,  pharm_review,   pharm_mix,  pharm_check,  pharm_dispensing, remark FROM ( SELECT a.hiscode, a.patientid, a.clinicno, a.PRESCNO as prescno,  1 as presctype, '处方名称' as  prescname, a.cost, b.deptcode, b.deptname, b.doctorcode, b.doctorname, b.medgroupcode, b.medgroupname, costtime enddate, 'shys' pharm_review, 'tpys'  pharm_mix, 'hdys'  pharm_check, 'fyys'  pharm_dispensing, '共3剂，每日1剂，水煎400ml，分早晚两次空腹温服。 'remark     FROM t_mc_clinic_cost  a LEFT JOIN t_mc_clinic_patient  b ON a.caseid=b.caseid     )aa GROUP BY hiscode,patientid, clinicno, prescno,  presctype,  prescname, deptcode, deptname, doctorcode, doctorname, medgroupcode, medgroupname,  pharm_review,   pharm_mix,  pharm_check,  pharm_dispensing, remark;

-- ----------------------------
-- View structure for MDC2_ZY_ALLERGEN_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_ZY_ALLERGEN_VIEW" AS 
SELECT   hiscode,patientid , visitid, allercode ,allername , symptom FROM  t_mc_outhosp_allergen UNION ALL SELECT   hiscode,patientid , visitid,  allercode ,  allername , symptom  FROM t_mc_inhosp_allergen;

-- ----------------------------
-- View structure for MDC2_ZY_COST_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_ZY_COST_VIEW" AS 
SELECT   hiscode ,  patientid ,  visitid , itemcode ,  itemname ,  drugform ,  drugspec ,   drugsccj , itemunit , itemnum , cost , costtime ,deptcode , deptname , deptcode AS wardcode ,  deptname AS wardname ,  doctorcode ,  doctorname ,  medgroupcode , medgroupname ,  is_out , costtype , routecode, DRUGINDEX FROM t_mc_outhosp_cost UNION ALL SELECT  hiscode , patientid ,  visitid ,  itemcode ,  itemname , drugform , drugspec , drugsccj , itemunit , itemnum ,cost ,  costtime ,  deptcode ,  deptname , deptcode AS wardcode ,  deptname AS wardname , doctorcode , doctorname , medgroupcode , medgroupname ,  is_out , costtype , routecode, DRUGINDEX FROM  t_mc_inhosp_cost;

-- ----------------------------
-- View structure for MDC2_ZY_DISEASE_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_ZY_DISEASE_VIEW" AS 
SELECT  hiscode ,             patientid ,             visitid ,             discode ,             disname ,             treatment ,             diseasetype ,             is_hospinfection ,             is_main     FROM    t_mc_outhosp_disease     UNION ALL     SELECT  hiscode ,             patientid ,             visitid ,             discode ,             disname ,             treatment ,             diseasetype ,             is_hospinfection ,             is_main     FROM    t_mc_inhosp_disease;

-- ----------------------------
-- View structure for MDC2_ZY_EXAM_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_ZY_EXAM_VIEW" AS 
SELECT  hiscode ,             patientid ,             visitid ,             requestno ,             examcode ,             examname ,             bodypart ,             examresult ,             reporttime ,             doctorname     FROM    t_mc_outhosp_exam     UNION ALL     SELECT  hiscode ,            patientid ,             visitid ,             requestno ,             examcode ,             examname ,             bodypart ,             examresult ,             reporttime ,             doctorname     FROM    t_mc_inhosp_exam;

-- ----------------------------
-- View structure for MDC2_ZY_LAB_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_ZY_LAB_VIEW" AS 
SELECT  hiscode ,             patientid ,             visitid ,             labcode ,             requestno ,             labname ,             sampletype ,             samplingtime ,             doctorname ,             reporttime ,             unit ,              range_0,             resultflag ,             labresult ,             itemname ,             itemcode     FROM    t_mc_outhosp_lab     UNION ALL     SELECT  hiscode ,             patientid ,             visitid ,             labcode ,             requestno ,             labname ,             sampletype ,             samplingtime ,             doctorname ,             reporttime ,             unit ,                 range_0,             resultflag ,             labresult ,             itemname ,             itemcode     FROM   t_mc_inhosp_lab;

-- ----------------------------
-- View structure for MDC2_ZY_MED_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_ZY_MED_VIEW" AS 
SELECT hiscode, hospitalno, patientid, visitid,   '籍贯_四川成都'  birthplace,  '民族_汉族'  nation,  '工作地址_高新区德商国际' as workaddress,  '联系地址_高新区德商国际55号' contactaddress,  '血型_B' bloodtype,  '血压_120' bloodpressure,  '体重指数_1.2' bmi,  '体表面积_140' bsa,  height AS shengao, weight as tizhong,  '系电话_16520114847' telephone,  '不良嗜好_烟酒毒' blsh,  '主诉_右腿骨折' bszhusu,  '现病史_粉碎性骨折' bsxbs,  '既往病史_脑震荡' bsjwbs,  '家族史_无'  bsjzs,  '药物不良反应及处置史_青霉素过敏' bsywblfyjczs,  '往用药史_一袋头孢' bsjwyys,  '伴发疾病与用药情况_青霉素' bsbfjbyyyqk,  '过敏药品_青霉素' gmyaopin,  '过敏症状_全身红斑' gmfanying,  '入院诊断_右腿骨折' in_diagnosis,  '出院诊断_右腿骨折' out_diagnosis FROM t_mc_inhosp_patient UNION ALL SELECT hiscode, hospitalno, patientid, visitid,   '籍贯_四川成都' birthplace,  '民族_汉族' nation,  '工作地址_高新区德商国际' as workaddress, '联系地址_高新区德商国际55号' contactaddress,  '血型_B' bloodtype,  '血压_120' bloodpressure,  '体重指数_1.2' bmi,  '体表面积_140' bsa,  height AS  shengao, weight as tizhong,  '联系电话_16520114847' telephone,  '不良嗜好_烟酒毒' blsh,  '主诉_右腿骨折' bszhusu,  '现病史_粉碎性骨折' bsxbs,  '既往病史_脑震荡' bsjwbs,  '家族史_无' bsjzs,  '药物不良反应及处置史_青霉素过敏' bsywblfyjczs,  '既往用药史_一袋头孢' bsjwyys,  '伴发疾病与用药情况_青霉素' bsbfjbyyyqk,  '过敏药品_青霉素' gmyaopin,  '过敏症状_全身红斑' gmfanying,  '入院诊断_右腿骨折' in_diagnosis,  '出院诊断_右腿骨折' out_diagnosis FROM t_mc_outhosp_patient;

-- ----------------------------
-- View structure for MDC2_ZY_OPERATION_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_ZY_OPERATION_VIEW" AS 
SELECT   hiscode,oprid ,             patientid ,             visitid,             operationcode ,             operationname ,             incisiontype ,             startdate ,             enddate ,             doctorcode ,             doctorname ,             deptcode ,             deptname     FROM    t_mc_outhosp_operation  UNION  ALL      SELECT             hiscode,oprid ,             patientid ,             visitid,             operationcode ,             operationname ,             incisiontype ,             startdate ,             enddate ,             doctorcode ,             doctorname ,             deptcode ,             deptname  FROM  t_mc_inhosp_operation;

-- ----------------------------
-- View structure for MDC2_ZY_ORDERS_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_ZY_ORDERS_VIEW" AS 
SELECT 	hiscode, 	cid, 	patientid, 	visitid, 	orderno, 	ordertype,  to_char(grouptag) as grouptag, 	is_temp, 	drug_unique_code, 	ordercode, 	ordername,   to_char(drugform) as drugform, to_char(drugspec) as drugspec,  	routecode, 	routename,  to_char(singledose) as singledose,   to_char(doseunit) as doseunit,  	frequency, 	deptcode, 	deptname, 	deptcode AS wardcode, 	deptname AS wardname, 	doctorcode, 	doctorname, 	executetime, 	startdatetime, 	enddatetime, 	purpose, 	remark, 	is_out, 	is_allow,  to_char(reasonable_desc) as reasonable_desc,  	meditime FROM 	t_mc_outhosp_order UNION ALL 	SELECT 		hiscode, 		cid, 		patientid, 		visitid, 		orderno, 		ordertype,  to_char(grouptag) as grouptag,  		is_temp, 		drug_unique_code, 		ordercode, 		ordername,  to_char(drugform) as drugform,  to_char(drugspec) as drugspec,   		routecode, 		routename,  to_char(singledose) as singledose, to_char(doseunit) as doseunit,  		frequency, 		deptcode, 		deptname, 		deptcode AS wardcode, 		deptname AS wardname, 		doctorcode, 		doctorname, 		executetime, 		startdatetime, 		enddatetime, 		purpose, 		remark, 		is_out, 		is_allow,  to_char(reasonable_desc) as reasonable_desc, 		meditime 	FROM 		t_mc_inhosp_order;

-- ----------------------------
-- View structure for MDC2_ZY_PATIENT_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_ZY_PATIENT_VIEW" AS 
SELECT 	hiscode, 	patientid, 	visitid, 	hospitalno, 	SUBSTR (patientname, 1, 10) AS patientname, 	sex, 	birthdate, 	age, 	height, 	weight, 	SUBSTR (identitycard, 1, 24) AS identitycard, 	telephone, 	bedno, 	deptcode, 	deptname, 	doctorcode, 	doctorname, 	wardcode AS wardcode, 	wardname AS wardname, 	medgroupcode, 	medgroupname, 	startdate, 	enddate, 	is_lact, 	ren_damage, 	hep_damage, 	preg_starttime, 	is_preg, 	payclass, 	nursingclass, 	i_in,  to_char(accountdate) as accountdate, 	'' STANDBY FROM 	t_mc_outhosp_patient UNION ALL 	SELECT 		hiscode, 		patientid, 		visitid, 		hospitalno, 		SUBSTR (patientname, 1, 10) AS patientname, 		sex, 		birthdate, 		age, 		height, 		weight, 		SUBSTR (identitycard, 1, 24) AS identitycard, 		telephone, 		bedno, 		deptcode, 		deptname, 		doctorcode, 		doctorname, 		wardcode AS wardcode, 		wardname AS wardname, 		medgroupcode, 		medgroupname, 		startdate, 		enddate, 		is_lact, 		ren_damage, 		hep_damage, 		preg_starttime, 		is_preg, 		payclass, 		nursingclass, 		i_in, 		to_char(accountdate) as accountdate, 		'' STANDBY 	FROM 		t_mc_inhosp_patient;

-- ----------------------------
-- View structure for MDC2_ZY_TEMPERATURE_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_ZY_TEMPERATURE_VIEW" AS 
SELECT HISCODE, PATIENTID , VISITID , TAKETIME , TEMPERATURE  FROM   T_MC_INHOSP_TEMPERATURE  UNION ALL SELECT HISCODE, PATIENTID , VISITID , TAKETIME , TEMPERATURE  FROM   T_MC_OUTHOSP_TEMPERATURE;

-- ----------------------------
-- View structure for MDC2_ZY_TRANSFERRED_VIEW
-- ----------------------------
CREATE OR REPLACE FORCE VIEW "MDC2_ZY_TRANSFERRED_VIEW" AS 
SELECT hiscode, patientid, visitid, deptcode as dept_stayed, startdate AS  admission_date_time, enddate as discharge_date_time, deptcode dept_transfered_to, doctorcode as doctor_in_charge FROM t_mc_outhosp_patient  UNION ALL  SELECT hiscode, patientid, visitid, deptcode as dept_stayed, startdate AS  admission_date_time, enddate as discharge_date_time, deptcode dept_transfered_to, doctorcode as doctor_in_charge FROM t_mc_inhosp_patient;
