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

Date: 2018-06-08 11:11:21
*/


-- ----------------------------
-- View structure for MDC2_DICT_ALLERGEN_VIEW
-- ----------------------------
DROP VIEW [MDC2_DICT_ALLERGEN_VIEW]
GO
CREATE VIEW [MDC2_DICT_ALLERGEN_VIEW] AS 
SELECT 'chview' AS hiscode ,allercode ,allername FROM mc_dict_allergen mda
GO

-- ----------------------------
-- View structure for MDC2_DICT_COST_ITEM_VIEW
-- ----------------------------
DROP VIEW [MDC2_DICT_COST_ITEM_VIEW]
GO
CREATE VIEW [MDC2_DICT_COST_ITEM_VIEW] AS 
SELECT 'chview' AS hiscode , mdc.itemcode AS itemcode ,mdc.itemname AS itemname ,3 AS itemtype FROM    mc_dict_costitem mdc
GO

-- ----------------------------
-- View structure for MDC2_DICT_DEPT_VIEW
-- ----------------------------
DROP VIEW [MDC2_DICT_DEPT_VIEW]
GO
CREATE VIEW [MDC2_DICT_DEPT_VIEW] AS 
SELECT 'chview'AS hiscode, deptcode, deptname, is_clinic, is_inhosp, is_emergency FROM mc_dict_dept
GO

-- ----------------------------
-- View structure for MDC2_DICT_DISEASE_VIEW
-- ----------------------------
DROP VIEW [MDC2_DICT_DISEASE_VIEW]
GO
CREATE VIEW [MDC2_DICT_DISEASE_VIEW] AS 
SELECT 'chview'AS hiscode, discode, disname FROM mc_dict_disease mdd
GO

-- ----------------------------
-- View structure for MDC2_DICT_DOCTOR_VIEW
-- ----------------------------
DROP VIEW [MDC2_DICT_DOCTOR_VIEW]
GO
CREATE VIEW [MDC2_DICT_DOCTOR_VIEW] AS 
SELECT 'chview'AS hiscode, doctorcode, doctorname, deptcode, deptname, ilevel, doctorlevel, 1 AS is_clinic, 1 AS prespriv, ilevel AS antilevel FROM mc_dict_doctor
GO

-- ----------------------------
-- View structure for MDC2_DICT_DRUG_VIEW2
-- ----------------------------
DROP VIEW [MDC2_DICT_DRUG_VIEW_BAK]
GO
CREATE VIEW [MDC2_DICT_DRUG_VIEW_BAK] AS 
SELECT 'chview'AS hiscode,a.drugcode, c.drug_unique_code, b.drugname, b.drugform, b.drugspec, c.approvalcode, c.comp_name, a.drugtype, a.DRUGGROUPCODE, a.DRUGGROUPNAME, a.DRGGRP_SEARCHCODE, c.doseunit, b.costunit, b.adddate, b.is_use, a.is_anti, a.antitype, a.antilevel, b.ddd, b.dddunit, a.is_basedrug,case when a.DRUGGROUPCODE<100 then 100/(a.DRUGGROUPCODE)+1.4 when a.DRUGGROUPCODE>100 and a.DRUGGROUPCODE<1000 then 1000/(a.DRUGGROUPCODE)+1.7 when  a.DRUGGROUPCODE>1000 and a.DRUGGROUPCODE<10000 then 10000/(a.DRUGGROUPCODE)+1.7 else 10.8 end as unitprice , '2050-12-12' as updatedate FROM	mc_dict_drug a left join	mc_dict_drug_sub b on a.drugcode=b.drugcode and a.match_scheme=b.match_scheme left join 	mc_dict_drug_pass c on a.drugcode = c.drugcode and c.match_scheme=b.match_scheme;
GO


-- ----------------------------
-- View structure for MDC2_DICT_DRUG_VIEW
-- 马哥要求修改视图，原三药品表暂时保留
-- ----------------------------
DROP VIEW [MDC2_DICT_DRUG_VIEW]
GO
CREATE VIEW [MDC2_DICT_DRUG_VIEW] AS 
SELECT 
'chview' AS hiscode,
	a.drugcode,
	a.drug_unique_code,
	a.drugname,
	a.drugform,
	a.drugspec,
	a.approvalcode,
	a.comp_name,
	a.doseunit,
a.drugtype,
a.DRUGGROUPCODE,
a.DRUGGROUPNAME,
a.DRGGRP_SEARCHCODE,
	a.costunit,
	a.adddate,
	a.is_use,
	a.is_anti,
	a.antitype,
	a.antilevel,
	a.ddd,
	a.dddunit,
	a.is_basedrug,
	88 AS unitprice
FROM MDC2_DICT_DRUG a
GO

-- ----------------------------
-- View structure for MDC2_DICT_EXAM_VIEW
-- ----------------------------
DROP VIEW [MDC2_DICT_EXAM_VIEW]
GO
CREATE VIEW [MDC2_DICT_EXAM_VIEW] AS 
SELECT 'chview' AS hiscode,examcode, mde.examname FROM mc_dict_exam mde
GO

-- ----------------------------
-- View structure for MDC2_DICT_FREQUENCY_VIEW
-- ----------------------------
DROP VIEW [MDC2_DICT_FREQUENCY_VIEW]
GO
CREATE VIEW [MDC2_DICT_FREQUENCY_VIEW] AS 
SELECT 'chview' AS hiscode,mdf.frequency FROM mc_dict_frequency mdf
GO

-- ----------------------------
-- View structure for MDC2_DICT_LAB_ITEM_VIEW
-- ----------------------------
DROP VIEW [MDC2_DICT_LAB_ITEM_VIEW]
GO
CREATE VIEW [MDC2_DICT_LAB_ITEM_VIEW] AS 
SELECT 'chview' AS hiscode,itemcode, itemname FROM mc_dict_labsub
GO

-- ----------------------------
-- View structure for MDC2_DICT_LAB_VIEW
-- ----------------------------
DROP VIEW [MDC2_DICT_LAB_VIEW]
GO
CREATE VIEW [MDC2_DICT_LAB_VIEW] AS 
SELECT 'chview' AS hiscode,labcode, labname FROM mc_dict_lab
GO

-- ----------------------------
-- View structure for MDC2_DICT_OPERATION_VIEW
-- ----------------------------
DROP VIEW [MDC2_DICT_OPERATION_VIEW]
GO
CREATE VIEW [MDC2_DICT_OPERATION_VIEW] AS 
SELECT 'chview' AS hiscode,operationcode,mdo.operationname FROM mc_dict_operation mdo
GO

-- ----------------------------
-- View structure for MDC2_DICT_ROUTE_VIEW
-- ----------------------------
DROP VIEW [MDC2_DICT_ROUTE_VIEW]
GO
CREATE VIEW [MDC2_DICT_ROUTE_VIEW] AS 
SELECT 'chview' AS hiscode,routecode, routename FROM mc_dict_route
GO

-- ----------------------------
-- View structure for MDC2_MZ_ALLERGEN_VIEW
-- ----------------------------
DROP VIEW [MDC2_MZ_ALLERGEN_VIEW]
GO
CREATE VIEW [MDC2_MZ_ALLERGEN_VIEW] AS 
SELECT      0 as hiscode,patientid, clinicno, allercode, allername,symptom
FROM        t_mc_clinic_allergen
GO

-- ----------------------------
-- View structure for MDC2_MZ_COST_VIEW
-- ----------------------------
DROP VIEW [MDC2_MZ_COST_VIEW]
GO
CREATE VIEW [MDC2_MZ_COST_VIEW] AS 
SELECT  0 as hiscode ,
            patientid ,
            clinicno ,
            prescno ,
            itemcode ,
            itemname ,
            drugform ,
            drugspec ,
            drugsccj ,
            itemunit ,
            itemnum ,
            cost ,
            costtime ,
            deptcode ,
            deptname ,
            doctorcode ,
            doctorname ,
            medgroupcode ,
            medgroupname ,
            routecode ,
            costtype,
            pharmacists,
            pharmacists_,
						DRUGINDEX

    FROM    t_mc_clinic_cost
GO

-- ----------------------------
-- View structure for MDC2_MZ_DISEASE_VIEW
-- ----------------------------
DROP VIEW [MDC2_MZ_DISEASE_VIEW]
GO
CREATE VIEW [MDC2_MZ_DISEASE_VIEW] AS 
SELECT      0 as hiscode,prescno, patientid, clinicno, discode, disname
FROM         t_mc_clinic_disease
GO

-- ----------------------------
-- View structure for MDC2_MZ_EXAM_VIEW
-- ----------------------------
DROP VIEW [MDC2_MZ_EXAM_VIEW]
GO
CREATE VIEW [MDC2_MZ_EXAM_VIEW] AS 
SELECT   0 as hiscode,patientid ,
            clinicno ,
           examcode ,
            examname ,
             requestno ,
            bodypart ,
            examresult ,
             reporttime ,
             doctorname as doctorname
    FROM    T_MC_CLINIC_EXAM
GO

-- ----------------------------
-- View structure for MDC2_MZ_LAB_VIEW
-- ----------------------------
DROP VIEW [MDC2_MZ_LAB_VIEW]
GO
CREATE VIEW [MDC2_MZ_LAB_VIEW] AS 
SELECT   0 as hiscode,patientid ,
            clinicno ,
           clinicno as  requestno ,
            labcode ,
            labname ,
            sampletype ,
           samplingtime ,
            itemcode ,
           itemname ,
           labresult ,
            resultflag ,
            range_0 as range ,
            unit ,
            reporttime ,
            doctorname
    FROM    t_mc_clinic_lab
GO

-- ----------------------------
-- View structure for MDC2_MZ_ORDERS_VIEW
-- ----------------------------
DROP VIEW [MDC2_MZ_ORDERS_VIEW]
GO
CREATE VIEW [MDC2_MZ_ORDERS_VIEW] AS 
SELECT  0 as hiscode ,
            prescno ,
            presctype,
            cid ,
            patientid ,
            clinicno ,
            orderno ,
            ordertype  ,
            grouptag,
            drug_unique_code ,
            ordercode ,
            ordername ,
            drugform ,
            drugspec ,
            routecode ,
            routename ,
            singledose ,
            doseunit ,
            frequency,
            num ,
            numunit ,
            cost ,
            deptcode ,
            deptname ,
            doctorcode ,
            doctorname ,
           startdatetime ,
            days,
           REMARK,
           purpose,
reasonable_desc,
is_allow

    FROM    T_MC_CLINIC_ORDER
GO

-- ----------------------------
-- View structure for MDC2_MZ_PATIENT_VIEW
-- ----------------------------
DROP VIEW [MDC2_MZ_PATIENT_VIEW]
GO
CREATE VIEW [MDC2_MZ_PATIENT_VIEW] AS 
SELECT  0 as hiscode ,
            patientid ,
            clinicno ,
           patientname ,
            case when sex='f' or sex='female' then '女' 
									when sex='m' or sex='male' then '男'
									when len(sex)>4 then left(sex,4)
									else sex end as sex,
            birthdate ,
            age ,
            height ,
            weight ,
            identitycard ,
            telephone ,
            enddate ,
            --allergen ,
            --diagnosis ,
            deptcode ,
            deptname ,
            doctorcode ,
            doctorname ,
            medgroupcode ,
            medgroupname ,
            payclass ,
            is_emergency ,
            --warn ,
            is_preg ,
            preg_starttime ,
            is_lact ,
            hep_damage ,
            ren_damage ,
            standby
            --usetime ,
            --'cardno' AS cardno
    FROM    t_mc_clinic_patient
GO

-- ----------------------------
-- View structure for MDC2_MZ_PRESC_VIEW
-- ----------------------------
DROP VIEW [MDC2_MZ_PRESC_VIEW]
GO
CREATE VIEW [MDC2_MZ_PRESC_VIEW] AS 
SELECT 0 as hiscode,patientid,
clinicno,
prescno,
 presctype,
 prescname,
sum(cost)cost,
deptcode,
deptname,
doctorcode,
doctorname,
medgroupcode,
medgroupname,
max(enddate) enddate,
 pharm_review,
  pharm_mix,
 pharm_check,
 pharm_dispensing,
remark FROM
(
SELECT a.hiscode,
a.patientid,
a.clinicno,
a.PRESCNO as prescno,
 1 as presctype,
'处方名称' as  prescname,
a.cost,
b.deptcode,
b.deptname,
b.doctorcode,
b.doctorname,
b.medgroupcode,
b.medgroupname,
costtime enddate,
'shys' pharm_review,
'tpys'  pharm_mix,
'hdys'  pharm_check,
'fyys'  pharm_dispensing,
'共3剂，每日1剂，水煎400ml，分早晚两次空腹温服。 'remark
    FROM t_mc_clinic_cost  a LEFT JOIN t_mc_clinic_patient  b ON a.caseid=b.caseid
    )aa


GROUP BY hiscode,patientid,
clinicno,
prescno,
 presctype,
 prescname,
deptcode,
deptname,
doctorcode,
doctorname,
medgroupcode,
medgroupname,
 pharm_review,
  pharm_mix,
 pharm_check,
 pharm_dispensing,
remark
GO

-- ----------------------------
-- View structure for MDC2_ZY_ALLERGEN_VIEW
-- ----------------------------
DROP VIEW [MDC2_ZY_ALLERGEN_VIEW]
GO
CREATE VIEW [MDC2_ZY_ALLERGEN_VIEW] AS 
SELECT   0 as hiscode,patientid ,
            visitid,
            allercode ,
            allername ,
            symptom
    FROM    t_mc_outhosp_allergen

    UNION ALL
SELECT   0 as hiscode,patientid ,
            visitid,
            allercode ,
            allername ,
            symptom  FROM t_mc_inhosp_allergen
GO

-- ----------------------------
-- View structure for MDC2_ZY_COST_VIEW
-- ----------------------------
DROP VIEW [MDC2_ZY_COST_VIEW]
GO
CREATE VIEW [MDC2_ZY_COST_VIEW] AS 
SELECT   0 as hiscode ,
            patientid ,
            visitid ,
            itemcode ,
            itemname ,
            drugform ,
            drugspec ,
            drugsccj ,
            itemunit ,
            itemnum ,
            cost ,
            costtime ,
            deptcode ,
            deptname ,
            deptcode AS wardcode ,
            deptname AS wardname ,
            doctorcode ,
            doctorname ,
            medgroupcode ,
            medgroupname ,
            is_out ,
            costtype ,
            routecode,
					DRUGINDEX
    FROM    t_mc_outhosp_cost

    UNION ALL
    SELECT  0 as hiscode ,
            patientid ,
            visitid ,
            itemcode ,
            itemname ,
            drugform ,
            drugspec ,
            drugsccj ,
            itemunit ,
            itemnum ,
            cost ,
            costtime ,
            deptcode ,
            deptname ,
            deptcode AS wardcode ,
            deptname AS wardname ,
            doctorcode ,
            doctorname ,
            medgroupcode ,
            medgroupname ,
            is_out ,
            costtype ,
            routecode,
		DRUGINDEX
    FROM    t_mc_inhosp_cost
GO

-- ----------------------------
-- View structure for MDC2_ZY_DISEASE_VIEW
-- ----------------------------
DROP VIEW [MDC2_ZY_DISEASE_VIEW]
GO
CREATE VIEW [MDC2_ZY_DISEASE_VIEW] AS 
SELECT  0 as hiscode ,
            patientid ,
            visitid ,
            discode ,
            disname ,
            treatment ,
            diseasetype ,
            is_hospinfection ,
            is_main
    FROM    t_mc_outhosp_disease
    UNION ALL
    SELECT  0 as hiscode ,
            patientid ,
            visitid ,
            discode ,
            disname ,
            treatment ,
            diseasetype ,
            is_hospinfection ,
            is_main
    FROM    t_mc_inhosp_disease
GO

-- ----------------------------
-- View structure for MDC2_ZY_EXAM_VIEW
-- ----------------------------
DROP VIEW [MDC2_ZY_EXAM_VIEW]
GO
CREATE VIEW [MDC2_ZY_EXAM_VIEW] AS 
SELECT  0 as hiscode ,
            patientid ,
            visitid ,
            requestno ,
            examcode ,
            examname ,
            bodypart ,
            examresult ,
            reporttime ,
            doctorname
    FROM    t_mc_outhosp_exam
    UNION ALL
    SELECT  0 as hiscode ,
           patientid ,
            visitid ,
            requestno ,
            examcode ,
            examname ,
            bodypart ,
            examresult ,
            reporttime ,
            doctorname
    FROM    t_mc_inhosp_exam
GO

-- ----------------------------
-- View structure for MDC2_ZY_LAB_VIEW
-- ----------------------------
DROP VIEW [MDC2_ZY_LAB_VIEW]
GO
CREATE VIEW [MDC2_ZY_LAB_VIEW] AS 
SELECT  0 as hiscode ,
            patientid ,
            visitid ,
            labcode ,
            requestno ,
            labname ,
            sampletype ,
            samplingtime ,
            doctorname ,
            reporttime ,
            unit ,
             range_0 as range,
            resultflag ,
            labresult ,
            itemname ,
            itemcode
    FROM    t_mc_outhosp_lab
    UNION ALL
    SELECT  0 as hiscode ,
            patientid ,
            visitid ,
            labcode ,
            requestno ,
            labname ,
            sampletype ,
            samplingtime ,
            doctorname ,
            reporttime ,
            unit ,
                range_0 as range,
            resultflag ,
            labresult ,
            itemname ,
            itemcode
    FROM   t_mc_inhosp_lab
GO

-- ----------------------------
-- View structure for MDC2_ZY_MED_VIEW
-- ----------------------------
DROP VIEW [MDC2_ZY_MED_VIEW]
GO
CREATE VIEW [MDC2_ZY_MED_VIEW] AS 
SELECT
0 as hiscode,
hospitalno,
patientid,
visitid,
 '籍贯_四川成都'birthplace,
'民族_汉族'nation,
'工作地址_高新区德商国际' as workaddress,
'联系地址_高新区德商国际55号'contactaddress,
'血型_B'bloodtype,
'血压_120'bloodpressure,
'体重指数_1.2'bmi,
'体表面积_140'bsa,
 height AS  shengao,
weight as tizhong,
'联系电话_16520114847'telephone,
'不良嗜好_烟酒毒'blsh,
'主诉_右腿骨折'bszhusu,
'现病史_粉碎性骨折'bsxbs,
'既往病史_脑震荡'bsjwbs,
'家族史_无'bsjzs,
'药物不良反应及处置史_青霉素过敏'bsywblfyjczs,
'既往用药史_一袋头孢'bsjwyys,
'伴发疾病与用药情况_青霉素'bsbfjbyyyqk,
'过敏药品_青霉素'gmyaopin,
'过敏症状_全身红斑'gmfanying,
'入院诊断_右腿骨折'in_diagnosis,
'出院诊断_右腿骨折'out_diagnosis
FROM t_mc_inhosp_patient


UNION ALL


SELECT
0 as hiscode,
hospitalno,
patientid,
visitid,
 '籍贯_四川成都'birthplace,
'民族_汉族'nation,
'工作地址_高新区德商国际' as workaddress,
'联系地址_高新区德商国际55号'contactaddress,
'血型_B'bloodtype,
'血压_120'bloodpressure,
'体重指数_1.2'bmi,
'体表面积_140'bsa,
 height AS  shengao,
weight as tizhong,
'联系电话_16520114847'telephone,
'不良嗜好_烟酒毒'blsh,
'主诉_右腿骨折'bszhusu,
'现病史_粉碎性骨折'bsxbs,
'既往病史_脑震荡'bsjwbs,
'家族史_无'bsjzs,
'药物不良反应及处置史_青霉素过敏'bsywblfyjczs,
'既往用药史_一袋头孢'bsjwyys,
'伴发疾病与用药情况_青霉素'bsbfjbyyyqk,
'过敏药品_青霉素'gmyaopin,
'过敏症状_全身红斑'gmfanying,
'入院诊断_右腿骨折'in_diagnosis,
'出院诊断_右腿骨折'out_diagnosis
FROM t_mc_outhosp_patient
GO

-- ----------------------------
-- View structure for MDC2_ZY_OPERATION_VIEW
-- ----------------------------
DROP VIEW [MDC2_ZY_OPERATION_VIEW]
GO
CREATE VIEW [MDC2_ZY_OPERATION_VIEW] AS 
SELECT  0 as  hiscode,oprid ,
            patientid ,
            visitid,
            operationcode ,
            operationname ,
            incisiontype ,
            startdate ,
            enddate ,
            doctorcode ,
            doctorname ,
            deptcode ,
            deptname
    FROM    t_mc_outhosp_operation  UNION  ALL

    SELECT
            0 as hiscode,oprid ,
            patientid ,
            visitid,
            operationcode ,
            operationname ,
            incisiontype ,
            startdate ,
            enddate ,
            doctorcode ,
            doctorname ,
            deptcode ,
            deptname  FROM  t_mc_inhosp_operation
GO

-- ----------------------------
-- View structure for MDC2_ZY_ORDERS_VIEW
-- ----------------------------
DROP VIEW [MDC2_ZY_ORDERS_VIEW]
GO
CREATE VIEW [MDC2_ZY_ORDERS_VIEW] AS 
SELECT 0 as  hiscode,cid ,
            patientid ,
            visitid ,
            orderno ,
            ordertype ,
            grouptag ,
            is_temp ,
            drug_unique_code ,
            ordercode ,
            ordername ,
            drugform ,
            drugspec ,
            routecode ,
            routename ,
            singledose ,
            doseunit ,
            frequency ,
            deptcode ,
            deptname ,
            deptcode AS wardcode ,
            deptname AS wardname ,
            doctorcode ,
            doctorname ,
            executetime ,
            startdatetime ,
            enddatetime ,
            purpose ,
            remark ,
            is_out ,
            is_allow ,
            reasonable_desc ,
            meditime
    FROM    t_mc_outhosp_order


    UNION ALL


    SELECT   0 as  hiscode,cid ,
            patientid ,
            visitid ,
            orderno ,
            ordertype ,
            grouptag ,
            is_temp ,
            drug_unique_code ,
            ordercode ,
            ordername ,
            drugform ,
            drugspec ,
            routecode ,
            routename ,
            singledose ,
            doseunit ,
            frequency ,
            deptcode ,
            deptname ,
            deptcode AS wardcode ,
            deptname AS wardname ,
            doctorcode ,
            doctorname ,
            executetime ,
            startdatetime ,
            enddatetime ,
            purpose ,
            remark ,
            is_out ,
            is_allow ,
            reasonable_desc ,
            meditime
    FROM    t_mc_inhosp_order
GO

-- ----------------------------
-- View structure for MDC2_ZY_PATIENT_VIEW
-- ----------------------------
DROP VIEW [MDC2_ZY_PATIENT_VIEW]
GO
CREATE VIEW [MDC2_ZY_PATIENT_VIEW] AS 
SELECT  0 as hiscode ,
            patientid ,
            visitid ,
            hospitalno ,
           left(patientname,  10) AS patientname ,
             case when sex='f' or sex='female' then '女' 
									when sex='m' or sex='male' then '男'
									when len(sex)>4 then left(sex,4)
									else sex end as sex,
            birthdate ,
            age ,
            height ,
            weight ,
            left(identitycard,  24) AS identitycard ,
             telephone ,
            bedno ,
            deptcode ,
            deptname ,
            doctorcode ,
            doctorname ,
            wardcode AS wardcode ,
            wardname AS wardname ,
            medgroupcode ,
            medgroupname ,
            startdate ,
            enddate ,
            is_lact ,
            ren_damage ,
            hep_damage ,
            preg_starttime ,
            is_preg ,
            payclass ,
            nursingclass ,
            i_in ,
            accountdate,
            '' standby
    FROM     t_mc_outhosp_patient
    UNION ALL
    SELECT 0 as  hiscode ,
            patientid ,
            visitid ,
            hospitalno ,
            left(patientname, 10)
                as patientname ,
             case when sex='f' or sex='female' then '女' 
									when sex='m' or sex='male' then '男'
									when len(sex)>4 then left(sex,4)
									else sex end as sex,
            birthdate ,
            age ,
            height ,
            weight ,
            left(identitycard, 24) AS identitycard ,
            telephone ,
            bedno ,
            deptcode ,
            deptname ,
            doctorcode ,
            doctorname ,
            wardcode AS wardcode ,
            wardname AS wardname ,
            medgroupcode ,
            medgroupname ,
            startdate ,
            enddate ,
            is_lact ,
            ren_damage ,
            hep_damage ,
            preg_starttime ,
            is_preg ,
            payclass ,
            nursingclass ,
            i_in ,
            accountdate,
            standby
    FROM     t_mc_inhosp_patient
GO

-- ----------------------------
-- View structure for MDC2_ZY_TEMPERATURE_VIEW
-- ----------------------------
DROP VIEW [MDC2_ZY_TEMPERATURE_VIEW]
GO
CREATE VIEW [MDC2_ZY_TEMPERATURE_VIEW] AS 
SELECT 0 as HISCODE,"PATIENTID","VISITID","TAKETIME","TEMPERATURE"
FROM   T_MC_INHOSP_TEMPERATURE

UNION ALL
SELECT 0 as HISCODE,"PATIENTID","VISITID","TAKETIME","TEMPERATURE"
FROM   T_MC_OUTHOSP_TEMPERATURE
GO

-- ----------------------------
-- View structure for MDC2_ZY_TRANSFERRED_VIEW
-- ----------------------------
DROP VIEW [MDC2_ZY_TRANSFERRED_VIEW]
GO
CREATE VIEW [MDC2_ZY_TRANSFERRED_VIEW] AS 
SELECT
0 as hiscode,
patientid,
visitid,
deptcode as dept_stayed,
startdate AS  admission_date_time,
enddate as discharge_date_time,
deptcode dept_transfered_to,
doctorcode as doctor_in_charge FROM t_mc_outhosp_patient

UNION ALL

SELECT
0 as hiscode,
patientid,
visitid,
deptcode as dept_stayed,
startdate AS  admission_date_time,
enddate as discharge_date_time,
deptcode dept_transfered_to,
doctorcode as doctor_in_charge FROM t_mc_inhosp_patient
GO
