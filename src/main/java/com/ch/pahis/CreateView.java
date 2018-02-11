package com.ch.pahis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreateView {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	
	public void createV(String hiscode){
		String sql=null;
		//重置视图
		System.out.println("开始重置字典表");
		sql="create or replace view MDC2_DICT_ALLERGEN_VIEW as SELECT '"+hiscode+"' AS hiscode ,allercode ,allername "
				+ "FROM mc_dict_allergen mda ";//MATCH_SCHEME
		jdbcTemplate_oracle.update(sql);
		
		sql="create or replace view MDC2_DICT_COST_ITEM_VIEW as SELECT '"+hiscode+"' AS hiscode , mdc.itemcode AS "
				+ "itemcode ,mdc.itemname AS itemname ,3 AS itemtype FROM    mc_dict_costitem mdc";
		jdbcTemplate_oracle.update(sql);
		
		sql="create or replace view MDC2_DICT_DEPT_VIEW as SELECT '"+hiscode+"'AS hiscode, deptcode, deptname, "
				+ "is_clinic, is_inhosp, is_emergency FROM mc_dict_dept ";//MATCH_SCHEME
		jdbcTemplate_oracle.update(sql);
		
		sql="create or replace view MDC2_DICT_DISEASE_VIEW as SELECT '"+hiscode+"'AS hiscode, discode, disname FROM "
				+ "mc_dict_disease mdd ";//MATCH_SCHEME
		jdbcTemplate_oracle.update(sql);
		
		sql="create or replace view MDC2_DICT_DOCTOR_VIEW as SELECT '"+hiscode+"'AS hiscode, doctorcode, doctorname, "
				+ "deptcode, deptname, ilevel, doctorlevel, 1 AS is_clinic, 1 AS prespriv, ilevel AS antilevel FROM "
				+ "mc_dict_doctor ";//MATCH_SCHEME
		jdbcTemplate_oracle.update(sql);
		
		sql="create or replace view MDC2_DICT_DRUG_VIEW as SELECT '"+hiscode+"'AS hiscode,a.drugcode, "
				+ "c.drug_unique_code, b.drugname, b.drugform, b.drugspec, c.approvalcode, c.comp_name, a.drugtype, "
				+ "a.DRUGGROUPCODE, a.DRUGGROUPNAME, a.DRGGRP_SEARCHCODE, c.doseunit, "
				+ "b.costunit, b.adddate, b.is_use, a.is_anti, a.antitype, a.antilevel, b.ddd, b.dddunit, "
				+ "a.is_basedrug,88 AS unitprice FROM mc_dict_drug a, mc_dict_drug_sub b, mc_dict_drug_pass c "
				+ "where a.drugcode=b.drugcode and a.drugcode=c.drugcode ";//MATCH_SCHEME
		jdbcTemplate_oracle.update(sql);
		
		sql="create or replace view MDC2_DICT_EXAM_VIEW as SELECT '"+hiscode+"' AS hiscode,examcode, mde.examname "
				+ "FROM mc_dict_exam mde";//MATCH_SCHEME
		jdbcTemplate_oracle.update(sql);
		
		sql="create or replace view MDC2_DICT_FREQUENCY_VIEW as SELECT '"+hiscode+"' AS hiscode,mdf.frequency FROM "
				+ "mc_dict_frequency mdf";
		jdbcTemplate_oracle.update(sql);
		
		sql="create or replace view MDC2_DICT_LAB_ITEM_VIEW as SELECT '"+hiscode+"' AS hiscode,itemcode, itemname "
				+ "FROM mc_dict_labsub ";//MATCH_SCHEME
		jdbcTemplate_oracle.update(sql);
		
		sql="create or replace view MDC2_DICT_LAB_VIEW as SELECT '"+hiscode+"' AS hiscode,labcode, labname FROM "
				+ "mc_dict_lab ";//MATCH_SCHEME
		jdbcTemplate_oracle.update(sql);
		
		sql="create or replace view MDC2_DICT_OPERATION_VIEW as SELECT '"+hiscode+"' AS hiscode,operationcode,"
				+ "mdo.operationname FROM mc_dict_operation mdo";
		jdbcTemplate_oracle.update(sql);
		
		sql="create or replace view MDC2_DICT_ROUTE_VIEW as SELECT '"+hiscode+"' AS hiscode,routecode, routename "
				+ "FROM mc_dict_route ";//MATCH_SCHEME
		jdbcTemplate_oracle.update(sql);
			
		System.out.println("开始重置业务视图");
		//门诊业务数据
		sql="CREATE OR REPLACE VIEW MDC2_MZ_ALLERGEN_VIEW (HISCODE, PATIENTID, CLINICNO, ALLERCODE, "
				+ "ALLERNAME, SYMPTOM) AS SELECT hiscode,patientid, clinicno, allercode, allername, "
				+ "symptom FROM t_mc_clinic_allergen";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW  MDC2_MZ_COST_VIEW   (  HISCODE  ,   PATIENTID  ,   CLINICNO  ,   PRESCNO  ,   ITEMCODE  ,   ITEMNAME  ,   DRUGFORM  ,   DRUGSPEC  ,   DRUGSCCJ  ,   ITEMUNIT  ,   ITEMNUM  ,   COST  ,   COSTTIME  ,   DEPTCODE  ,   DEPTNAME  ,   DOCTORCODE  ,   DOCTORNAME  ,   MEDGROUPCODE  ,   MEDGROUPNAME  ,   ROUTECODE  ,   COSTTYPE  ,   PHARMACISTS  ,   PHARMACISTS_  ,   DRUGINDEX  ) AS  " + 
				"  SELECT  hiscode , " + 
				"            patientid , " + 
				"            clinicno , " + 
				"            prescno , " + 
				"            itemcode , " + 
				"            itemname , " + 
				"            drugform , " + 
				"            drugspec , " + 
				"            drugsccj , " + 
				"            itemunit , " + 
				"            itemnum , " + 
				"            cost , " + 
				"            costtime , " + 
				"            deptcode , " + 
				"            deptname , " + 
				"            doctorcode , " + 
				"            doctorname , " + 
				"            medgroupcode , " + 
				"            medgroupname , " + 
				"            routecode , " + 
				"            costtype, " + 
				"            pharmacists, " + 
				"            pharmacists_, " + 
				"						DRUGINDEX " + 
				" " + 
				"    FROM    t_mc_clinic_cost";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_MZ_DISEASE_VIEW ( HISCODE ,  PRESCNO ,  PATIENTID ,  CLINICNO , "
				+ " DISCODE ,  DISNAME ) AS SELECT hiscode,prescno, patientid, clinicno, discode, disname FROM "
				+ "t_mc_clinic_disease";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW  MDC2_MZ_EXAM_VIEW  ( HISCODE ,  PATIENTID ,  CLINICNO ,  EXAMCODE ,  EXAMNAME ,  REQUESTNO ,  BODYPART ,  EXAMRESULT ,  REPORTTIME ,  DOCTORNAME ) AS  " + 
				"  SELECT   hiscode,patientid , " + 
				"            clinicno , " + 
				"           examcode , " + 
				"            examname , " + 
				"             requestno , " + 
				"            bodypart , " + 
				"            examresult , " + 
				"             reporttime , " + 
				"             doctorname as doctorname " + 
				"    FROM    T_MC_CLINIC_EXAM";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_MZ_LAB_VIEW ( HISCODE ,  PATIENTID ,  CLINICNO ,  REQUESTNO ,  LABCODE ,  LABNAME ,  SAMPLETYPE ,  SAMPLINGTIME ,  ITEMCODE ,  ITEMNAME ,  LABRESULT ,  RESULTFLAG ,  RANGE_0 ,  UNIT ,  REPORTTIME ,  DOCTORNAME ) AS  " + 
				"  SELECT   hiscode,patientid , " + 
				"            clinicno , " + 
				"           clinicno as  requestno , " + 
				"            labcode , " + 
				"            labname , " + 
				"            sampletype , " + 
				"           samplingtime , " + 
				"            itemcode , " + 
				"           itemname , " + 
				"           labresult , " + 
				"            resultflag , " + 
				"            range_0 , " + 
				"            unit , " + 
				"            reporttime , " + 
				"            doctorname " + 
				"    FROM    t_mc_clinic_lab";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_MZ_ORDERS_VIEW  ( HISCODE ,  PRESCNO ,  PRESCTYPE ,  CID ,  PATIENTID ,  CLINICNO ,  ORDERNO ,  ORDERTYPE ,  GROUPTAG ,  DRUG_UNIQUE_CODE ,  ORDERCODE ,  ORDERNAME ,  DRUGFORM ,  DRUGSPEC ,  ROUTECODE ,  ROUTENAME ,  SINGLEDOSE ,  DOSEUNIT ,  FREQUENCY ,  NUM ,  NUMUNIT ,  COST ,  DEPTCODE ,  DEPTNAME ,  DOCTORCODE ,  DOCTORNAME ,  STARTDATETIME ,  DAYS ,  REMARK ,  PURPOSE ,  REASONABLE_DESC ) AS  " + 
				"  SELECT  hiscode , " + 
				"            prescno , " + 
				"            presctype, " + 
				"            cid , " + 
				"            patientid , " + 
				"            clinicno , " + 
				"            orderno , " + 
				"            ordertype  , " + 
				"            grouptag, " + 
				"            drug_unique_code , " + 
				"            ordercode , " + 
				"            ordername , " + 
				"            drugform , " + 
				"            drugspec , " + 
				"            routecode , " + 
				"            routename , " + 
				"            singledose , " + 
				"            doseunit , " + 
				"            frequency, " + 
				"            num , " + 
				"            numunit , " + 
				"            cost , " + 
				"            deptcode , " + 
				"            deptname , " + 
				"            doctorcode , " + 
				"            doctorname , " + 
				"           startdatetime , " + 
				"            days, " + 
				"           REMARK, " + 
				"           purpose, " + 
				"reasonable_desc " + 
				"    FROM    T_MC_CLINIC_ORDER";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW  MDC2_MZ_PATIENT_VIEW  ( HISCODE ,  PATIENTID ,  CLINICNO ,  PATIENTNAME ,  SEX ,  BIRTHDATE ,  AGE ,  HEIGHT ,  WEIGHT ,  IDENTITYCARD ,  TELEPHONE ,  ENDDATE ,  DEPTCODE ,  DEPTNAME ,  DOCTORCODE ,  DOCTORNAME ,  MEDGROUPCODE ,  MEDGROUPNAME ,  PAYCLASS ,  IS_EMERGENCY ,  IS_PREG ,  PREG_STARTTIME ,  IS_LACT ,  HEP_DAMAGE ,  REN_DAMAGE ,  STANDBY ) AS  " + 
				"  SELECT  hiscode , " + 
				"            patientid , " + 
				"            clinicno , " + 
				"           patientname , " + 
				"            sex , " + 
				"            birthdate , " + 
				"            age , " + 
				"            height , " + 
				"            weight , " + 
				"            identitycard , " + 
				"            telephone , " + 
				"            enddate , " + 
				"            deptcode , " + 
				"            deptname , " + 
				"            doctorcode , " + 
				"            doctorname , " + 
				"            medgroupcode , " + 
				"            medgroupname , " + 
				"            payclass , " + 
				"            is_emergency , " + 
				"            is_preg , " + 
				"            preg_starttime , " + 
				"            is_lact , " + 
				"            hep_damage , " + 
				"            ren_damage , " + 
				"            standby " + 
				"    FROM    t_mc_clinic_patient";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW  MDC2_MZ_PRESC_VIEW  ( HISCODE ,  PATIENTID ,  CLINICNO ,  PRESCNO ,  PRESCTYPE ,  PRESCNAME ,  COST ,  DEPTCODE ,  DEPTNAME ,  DOCTORCODE ,  DOCTORNAME ,  MEDGROUPCODE ,  MEDGROUPNAME ,  ENDDATE ,  PHARM_REVIEW ,  PHARM_MIX ,  PHARM_CHECK ,  PHARM_DISPENSING ,  REMARK ) AS  " + 
				"  SELECT hiscode,patientid, " + 
				"clinicno, " + 
				"prescno, " + 
				" presctype, " + 
				" prescname, " + 
				"sum(cost)cost, " + 
				"deptcode, " + 
				"deptname, " + 
				"doctorcode, " + 
				"doctorname, " + 
				"medgroupcode, " + 
				"medgroupname, " + 
				"max(enddate) enddate, " + 
				" pharm_review, " + 
				"  pharm_mix, " + 
				" pharm_check, " + 
				" pharm_dispensing, " + 
				"remark FROM " + 
				"( " + 
				"SELECT a.hiscode, " + 
				"a.patientid, " + 
				"a.clinicno, " + 
				"a.PRESCNO as prescno, " + 
				" 1 as presctype, " + 
				"'处方名称' as  prescname, " + 
				"a.cost, " + 
				"b.deptcode, " + 
				"b.deptname, " + 
				"b.doctorcode, " + 
				"b.doctorname, " + 
				"b.medgroupcode, " + 
				"b.medgroupname, " + 
				"costtime enddate, " + 
				"'shys' pharm_review, " + 
				"'tpys'  pharm_mix, " + 
				"'hdys'  pharm_check, " + 
				"'fyys'  pharm_dispensing, " + 
				"'共3剂，每日1剂，水煎400ml，分早晚两次空腹温服。 'remark " + 
				"    FROM t_mc_clinic_cost  a LEFT JOIN t_mc_clinic_patient  b ON a.caseid=b.caseid " + 
				"    )aa " + 
				"GROUP BY hiscode,patientid, " + 
				"clinicno, " + 
				"prescno, " + 
				" presctype, " + 
				" prescname, " + 
				"deptcode, " + 
				"deptname, " + 
				"doctorcode, " + 
				"doctorname, " + 
				"medgroupcode, " + 
				"medgroupname, " + 
				" pharm_review, " + 
				"  pharm_mix, " + 
				" pharm_check, " + 
				" pharm_dispensing, " + 
				"remark";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW  MDC2_ZY_ALLERGEN_VIEW  ( HISCODE ,  PATIENTID ,  VISITID ,  ALLERCODE , "
				+ " ALLERNAME ,  SYMPTOM ) AS  SELECT   hiscode,patientid , visitid, allercode ,allername , "
				+ "symptom FROM  t_mc_outhosp_allergen UNION ALL SELECT   hiscode,patientid , visitid,  "
				+ "allercode ,  allername , symptom  FROM t_mc_inhosp_allergen";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_ZY_COST_VIEW ( HISCODE ,  PATIENTID ,  VISITID ,  ITEMCODE , "
				+ " ITEMNAME ,  DRUGFORM ,  DRUGSPEC ,  DRUGSCCJ ,  ITEMUNIT ,  ITEMNUM ,  COST ,  COSTTIME , "
				+ " DEPTCODE ,  DEPTNAME ,  WARDCODE ,  WARDNAME ,  DOCTORCODE ,  DOCTORNAME ,  MEDGROUPCODE , "
				+ " MEDGROUPNAME ,  IS_OUT ,  COSTTYPE ,  ROUTECODE ,  DRUGINDEX ) AS SELECT   hiscode ,  "
				+ "patientid ,  visitid , itemcode ,  itemname ,  drugform ,  drugspec ,   drugsccj , itemunit , "
				+ "itemnum , cost , costtime ,deptcode , deptname , deptcode AS wardcode ,  deptname AS wardname , "
				+ " doctorcode ,  doctorname ,  medgroupcode , medgroupname ,  is_out , costtype , routecode,"
				+ " DRUGINDEX FROM t_mc_outhosp_cost UNION ALL SELECT  hiscode , patientid ,  visitid ,  "
				+ "itemcode ,  itemname , drugform , drugspec , drugsccj , itemunit , itemnum ,cost ,  costtime ,  "
				+ "deptcode ,  deptname , deptcode AS wardcode ,  deptname AS wardname , doctorcode , doctorname ,"
				+ " medgroupcode , medgroupname ,  is_out , costtype , routecode, DRUGINDEX FROM  t_mc_inhosp_cost";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_ZY_DISEASE_VIEW ( HISCODE ,  PATIENTID ,  VISITID ,  DISCODE ,  DISNAME ,  TREATMENT ,  DISEASETYPE ,  IS_HOSPINFECTION ,  IS_MAIN ) AS  " + 
				"  SELECT  hiscode , " + 
				"            patientid , " + 
				"            visitid , " + 
				"            discode , " + 
				"            disname , " + 
				"            treatment , " + 
				"            diseasetype , " + 
				"            is_hospinfection , " + 
				"            is_main " + 
				"    FROM    t_mc_outhosp_disease " + 
				"    UNION ALL " + 
				"    SELECT  hiscode , " + 
				"            patientid , " + 
				"            visitid , " + 
				"            discode , " + 
				"            disname , " + 
				"            treatment , " + 
				"            diseasetype , " + 
				"            is_hospinfection , " + 
				"            is_main " + 
				"    FROM    t_mc_inhosp_disease";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_ZY_EXAM_VIEW ( HISCODE ,  PATIENTID ,  VISITID ,  REQUESTNO ,  EXAMCODE ,  EXAMNAME ,  BODYPART ,  EXAMRESULT ,  REPORTTIME ,  DOCTORNAME ) AS  " + 
				"  SELECT  hiscode , " + 
				"            patientid , " + 
				"            visitid , " + 
				"            requestno , " + 
				"            examcode , " + 
				"            examname , " + 
				"            bodypart , " + 
				"            examresult , " + 
				"            reporttime , " + 
				"            doctorname " + 
				"    FROM    t_mc_outhosp_exam " + 
				"    UNION ALL " + 
				"    SELECT  hiscode , " + 
				"           patientid , " + 
				"            visitid , " + 
				"            requestno , " + 
				"            examcode , " + 
				"            examname , " + 
				"            bodypart , " + 
				"            examresult , " + 
				"            reporttime , " + 
				"            doctorname " + 
				"    FROM    t_mc_inhosp_exam";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_ZY_LAB_VIEW ( HISCODE ,  PATIENTID ,  VISITID ,  LABCODE ,  REQUESTNO ,  LABNAME ,  SAMPLETYPE ,  SAMPLINGTIME ,  DOCTORNAME ,  REPORTTIME ,  UNIT ,  RANGE_0 ,  RESULTFLAG ,  LABRESULT ,  ITEMNAME ,  ITEMCODE ) AS  " + 
				"  SELECT  hiscode , " + 
				"            patientid , " + 
				"            visitid , " + 
				"            labcode , " + 
				"            requestno , " + 
				"            labname , " + 
				"            sampletype , " + 
				"            samplingtime , " + 
				"            doctorname , " + 
				"            reporttime , " + 
				"            unit , " + 
				"             range_0, " + 
				"            resultflag , " + 
				"            labresult , " + 
				"            itemname , " + 
				"            itemcode " + 
				"    FROM    t_mc_outhosp_lab " + 
				"    UNION ALL " + 
				"    SELECT  hiscode , " + 
				"            patientid , " + 
				"            visitid , " + 
				"            labcode , " + 
				"            requestno , " + 
				"            labname , " + 
				"            sampletype , " + 
				"            samplingtime , " + 
				"            doctorname , " + 
				"            reporttime , " + 
				"            unit , " + 
				"                range_0, " + 
				"            resultflag , " + 
				"            labresult , " + 
				"            itemname , " + 
				"            itemcode " + 
				"    FROM   t_mc_inhosp_lab";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_ZY_MED_VIEW ( HISCODE ,  HOSPITALNO ,  PATIENTID ,  VISITID ,  BIRTHPLACE ,  NATION ,  WORKADDRESS ,  CONTACTADDRESS ,  BLOODTYPE ,  BLOODPRESSURE ,  BMI ,  BSA ,  SHENGAO ,  TIZHONG ,  TELEPHONE ,  BLSH ,  BSZHUSU ,  BSXBS ,  BSJWBS ,  BSJZS ,  BSYWBLFYJCZS ,  BSJWYYS ,  BSBFJBYYYQK ,  GMYAOPIN ,  GMFANYING ,  IN_DIAGNOSIS ,  OUT_DIAGNOSIS ) AS  " + 
				"  SELECT " + 
				"hiscode, " + 
				"hospitalno, " + 
				"patientid, " + 
				"visitid, " + 
				"  '籍贯_四川成都'  birthplace, " + 
				" '民族_汉族'  nation, " + 
				" '工作地址_高新区德商国际' as workaddress, " + 
				" '联系地址_高新区德商国际55号' contactaddress, " + 
				" '血型_B' bloodtype, " + 
				" '血压_120' bloodpressure, " + 
				" '体重指数_1.2' bmi, " + 
				" '体表面积_140' bsa, " + 
				" height AS shengao, " + 
				"weight as tizhong, " + 
				" '系电话_16520114847' telephone, " + 
				" '不良嗜好_烟酒毒' blsh, " + 
				" '主诉_右腿骨折' bszhusu, " + 
				" '现病史_粉碎性骨折' bsxbs, " + 
				" '既往病史_脑震荡' bsjwbs, " + 
				" '家族史_无'  bsjzs, " + 
				" '药物不良反应及处置史_青霉素过敏' bsywblfyjczs, " + 
				" '往用药史_一袋头孢' bsjwyys, " + 
				" '伴发疾病与用药情况_青霉素' bsbfjbyyyqk, " + 
				" '过敏药品_青霉素' gmyaopin, " + 
				" '过敏症状_全身红斑' gmfanying, " + 
				" '入院诊断_右腿骨折' in_diagnosis, " + 
				" '出院诊断_右腿骨折' out_diagnosis " + 
				"FROM t_mc_inhosp_patient " + 
				"UNION ALL " + 
				"SELECT " + 
				"hiscode, " + 
				"hospitalno, " + 
				"patientid, " + 
				"visitid, " + 
				"  '籍贯_四川成都' birthplace, " + 
				" '民族_汉族' nation, " + 
				" '工作地址_高新区德商国际' as workaddress, " + 
				"'联系地址_高新区德商国际55号' contactaddress, " + 
				" '血型_B' bloodtype, " + 
				" '血压_120' bloodpressure, " + 
				" '体重指数_1.2' bmi, " + 
				" '体表面积_140' bsa, " + 
				" height AS  shengao, " + 
				"weight as tizhong, " + 
				" '联系电话_16520114847' telephone, " + 
				" '不良嗜好_烟酒毒' blsh, " + 
				" '主诉_右腿骨折' bszhusu, " + 
				" '现病史_粉碎性骨折' bsxbs, " + 
				" '既往病史_脑震荡' bsjwbs, " + 
				" '家族史_无' bsjzs, " + 
				" '药物不良反应及处置史_青霉素过敏' bsywblfyjczs, " + 
				" '既往用药史_一袋头孢' bsjwyys, " + 
				" '伴发疾病与用药情况_青霉素' bsbfjbyyyqk, " + 
				" '过敏药品_青霉素' gmyaopin, " + 
				" '过敏症状_全身红斑' gmfanying, " + 
				" '入院诊断_右腿骨折' in_diagnosis, " + 
				" '出院诊断_右腿骨折' out_diagnosis " + 
				"FROM t_mc_outhosp_patient";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_ZY_OPERATION_VIEW ( HISCODE ,  OPRID ,  PATIENTID ,  VISITID ,  OPERATIONCODE ,  OPERATIONNAME ,  INCISIONTYPE ,  STARTDATE ,  ENDDATE ,  DOCTORCODE ,  DOCTORNAME ,  DEPTCODE ,  DEPTNAME ) AS  " + 
				"  SELECT   hiscode,oprid , " + 
				"            patientid , " + 
				"            visitid, " + 
				"            operationcode , " + 
				"            operationname , " + 
				"            incisiontype , " + 
				"            startdate , " + 
				"            enddate , " + 
				"            doctorcode , " + 
				"            doctorname , " + 
				"            deptcode , " + 
				"            deptname " + 
				"    FROM    t_mc_outhosp_operation  UNION  ALL " + 
				" " + 
				"    SELECT " + 
				"            hiscode,oprid , " + 
				"            patientid , " + 
				"            visitid, " + 
				"            operationcode , " + 
				"            operationname , " + 
				"            incisiontype , " + 
				"            startdate , " + 
				"            enddate , " + 
				"            doctorcode , " + 
				"            doctorname , " + 
				"            deptcode , " + 
				"            deptname  FROM  t_mc_inhosp_operation";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_ZY_ORDERS_VIEW ( HISCODE ,  CID ,  PATIENTID ,  VISITID ,  ORDERNO ,  ORDERTYPE ,  GROUPTAG ,  IS_TEMP ,  DRUG_UNIQUE_CODE ,  ORDERCODE ,  ORDERNAME ,  DRUGFORM ,  DRUGSPEC ,  ROUTECODE ,  ROUTENAME ,  SINGLEDOSE ,  DOSEUNIT ,  FREQUENCY ,  DEPTCODE ,  DEPTNAME ,  WARDCODE ,  WARDNAME ,  DOCTORCODE ,  DOCTORNAME ,  EXECUTETIME ,  STARTDATETIME ,  ENDDATETIME ,  PURPOSE ,  REMARK ,  IS_OUT ,  IS_ALLOW ,  REASONABLE_DESC ,  MEDITIME ) AS  " + 
				" SELECT " + 
				"	hiscode, " + 
				"	cid, " + 
				"	patientid, " + 
				"	visitid, " + 
				"	orderno, " + 
				"	ordertype,  " + 
				"to_char(grouptag) as grouptag, " + 
				"	is_temp, " + 
				"	drug_unique_code, " + 
				"	ordercode, " + 
				"	ordername,   " + 
				"to_char(drugform) as drugform, " + 
				"to_char(drugspec) as drugspec,  " + 
				"	routecode, " + 
				"	routename,  " + 
				"to_char(singledose) as singledose,   " + 
				"to_char(doseunit) as doseunit,  " + 
				"	frequency, " + 
				"	deptcode, " + 
				"	deptname, " + 
				"	deptcode AS wardcode, " + 
				"	deptname AS wardname, " + 
				"	doctorcode, " + 
				"	doctorname, " + 
				"	executetime, " + 
				"	startdatetime, " + 
				"	enddatetime, " + 
				"	purpose, " + 
				"	remark, " + 
				"	is_out, " + 
				"	is_allow,  " + 
				"to_char(reasonable_desc) as reasonable_desc,  " + 
				"	meditime " + 
				"FROM " + 
				"	t_mc_outhosp_order " + 
				"UNION ALL " + 
				"	SELECT " + 
				"		hiscode, " + 
				"		cid, " + 
				"		patientid, " + 
				"		visitid, " + 
				"		orderno, " + 
				"		ordertype,  " + 
				"to_char(grouptag) as grouptag,  " + 
				"		is_temp, " + 
				"		drug_unique_code, " + 
				"		ordercode, " + 
				"		ordername,  " + 
				"to_char(drugform) as drugform,  " + 
				"to_char(drugspec) as drugspec,   " + 
				"		routecode, " + 
				"		routename,  " + 
				"to_char(singledose) as singledose, " + 
				"to_char(doseunit) as doseunit,  " + 
				"		frequency, " + 
				"		deptcode, " + 
				"		deptname, " + 
				"		deptcode AS wardcode, " + 
				"		deptname AS wardname, " + 
				"		doctorcode, " + 
				"		doctorname, " + 
				"		executetime, " + 
				"		startdatetime, " + 
				"		enddatetime, " + 
				"		purpose, " + 
				"		remark, " + 
				"		is_out, " + 
				"		is_allow,  " + 
				"to_char(reasonable_desc) as reasonable_desc, " + 
				"		meditime " + 
				"	FROM " + 
				"		t_mc_inhosp_order";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_ZY_PATIENT_VIEW ( HISCODE ,  PATIENTID ,  VISITID ,  HOSPITALNO ,  PATIENTNAME ,  SEX ,  BIRTHDATE ,  AGE ,  HEIGHT ,  WEIGHT ,  IDENTITYCARD ,  TELEPHONE ,  BEDNO ,  DEPTCODE ,  DEPTNAME ,  DOCTORCODE ,  DOCTORNAME ,  WARDCODE ,  WARDNAME ,  MEDGROUPCODE ,  MEDGROUPNAME ,  STARTDATE ,  ENDDATE ,  IS_LACT ,  REN_DAMAGE ,  HEP_DAMAGE ,  PREG_STARTTIME ,  IS_PREG ,  PAYCLASS ,  NURSINGCLASS ,  I_IN ,  ACCOUNTDATE ,  STANDBY ) AS  " + 
				"SELECT " + 
				"	hiscode, " + 
				"	patientid, " + 
				"	visitid, " + 
				"	hospitalno, " + 
				"	SUBSTR (patientname, 1, 10) AS patientname, " + 
				"	sex, " + 
				"	birthdate, " + 
				"	age, " + 
				"	height, " + 
				"	weight, " + 
				"	SUBSTR (identitycard, 1, 24) AS identitycard, " + 
				"	telephone, " + 
				"	bedno, " + 
				"	deptcode, " + 
				"	deptname, " + 
				"	doctorcode, " + 
				"	doctorname, " + 
				"	wardcode AS wardcode, " + 
				"	wardname AS wardname, " + 
				"	medgroupcode, " + 
				"	medgroupname, " + 
				"	startdate, " + 
				"	enddate, " + 
				"	is_lact, " + 
				"	ren_damage, " + 
				"	hep_damage, " + 
				"	preg_starttime, " + 
				"	is_preg, " + 
				"	payclass, " + 
				"	nursingclass, " + 
				"	i_in,  " + 
				"to_char(accountdate) as accountdate, " + 
				"	'' STANDBY " + 
				"FROM " + 
				"	t_mc_outhosp_patient " + 
				"UNION ALL " + 
				"	SELECT " + 
				"		hiscode, " + 
				"		patientid, " + 
				"		visitid, " + 
				"		hospitalno, " + 
				"		SUBSTR (patientname, 1, 10) AS patientname, " + 
				"		sex, " + 
				"		birthdate, " + 
				"		age, " + 
				"		height, " + 
				"		weight, " + 
				"		SUBSTR (identitycard, 1, 24) AS identitycard, " + 
				"		telephone, " + 
				"		bedno, " + 
				"		deptcode, " + 
				"		deptname, " + 
				"		doctorcode, " + 
				"		doctorname, " + 
				"		wardcode AS wardcode, " + 
				"		wardname AS wardname, " + 
				"		medgroupcode, " + 
				"		medgroupname, " + 
				"		startdate, " + 
				"		enddate, " + 
				"		is_lact, " + 
				"		ren_damage, " + 
				"		hep_damage, " + 
				"		preg_starttime, " + 
				"		is_preg, " + 
				"		payclass, " + 
				"		nursingclass, " + 
				"		i_in, " + 
				"		to_char(accountdate) as accountdate, " + 
				"		'' STANDBY " + 
				"	FROM " + 
				"		t_mc_inhosp_patient";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_ZY_TEMPERATURE_VIEW ( HISCODE ,  PATIENTID ,  VISITID ,  TAKETIME ,  TEMPERATURE ) AS  " + 
				"  SELECT HISCODE, PATIENTID , VISITID , TAKETIME , TEMPERATURE  " + 
				"FROM   T_MC_INHOSP_TEMPERATURE " + 
				" " + 
				"UNION ALL " + 
				"SELECT HISCODE, PATIENTID , VISITID , TAKETIME , TEMPERATURE  " + 
				"FROM   T_MC_OUTHOSP_TEMPERATURE";
		jdbcTemplate_oracle.update(sql);
		
		sql="CREATE OR REPLACE FORCE VIEW MDC2_ZY_TRANSFERRED_VIEW ( HISCODE ,  PATIENTID ,  VISITID ,  DEPT_STAYED ,  ADMISSION_DATE_TIME ,  DISCHARGE_DATE_TIME ,  DEPT_TRANSFERED_TO ,  DOCTOR_IN_CHARGE ) AS  " + 
				"  SELECT " + 
				"hiscode, " + 
				"patientid, " + 
				"visitid, " + 
				"deptcode as dept_stayed, " + 
				"startdate AS  admission_date_time, " + 
				"enddate as discharge_date_time, " + 
				"deptcode dept_transfered_to, " + 
				"doctorcode as doctor_in_charge FROM t_mc_outhosp_patient " + 
				" " + 
				"UNION ALL " + 
				" " + 
				"SELECT " + 
				"hiscode, " + 
				"patientid, " + 
				"visitid, " + 
				"deptcode as dept_stayed, " + 
				"startdate AS  admission_date_time, " + 
				"enddate as discharge_date_time, " + 
				"deptcode dept_transfered_to, " + 
				"doctorcode as doctor_in_charge FROM t_mc_inhosp_patient";
		jdbcTemplate_oracle.update(sql);
		
		System.out.println("视图创建结束");
	}
}
