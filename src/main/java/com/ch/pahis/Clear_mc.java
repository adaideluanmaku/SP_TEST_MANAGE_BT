package com.ch.pahis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Clear_mc {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;

	public void cleardate() {
		String sql = null;

		// 制造t_mc_clinic_allergen数据

		System.out.println("开始清理业务数据");
		sql = "truncate table t_mc_clinic_allergen";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_clinic_cost";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_clinic_disease";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_clinic_exam";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_clinic_lab";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_clinic_order";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_clinic_patient";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_inhosp_allergen";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_inhosp_cost";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_inhosp_disease";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_inhosp_exam";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_inhosp_lab";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_inhosp_operation";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_inhosp_order";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_inhosp_patient";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_inhosp_temperature";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_outhosp_allergen";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_outhosp_cost";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_outhosp_disease";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_outhosp_exam";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_outhosp_lab";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_outhosp_operation";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_outhosp_order";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_outhosp_patient";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table t_mc_outhosp_temperature";
		jdbcTemplate_oracle.update(sql);
		System.out.println("===业务表清理结束===");
	}

	public void creattable() {
		String sql = null;

		System.out.println("开始创建HIS库所有表");
		System.out.println("开始创建字典表");

		try {
			sql = "DROP TABLE   MC_DICT_ALLERGEN";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_ALLERGEN，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_ALLERGEN  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " ALLERCODE  NVARCHAR2(64) NULL , " + " ALLERNAME  NVARCHAR2(64) NULL , "
				+ " SEARCHCODE  NVARCHAR2(128) NULL , " + " IS_SAVE  NUMBER(11) NULL , "
				+ " PASS_ALLERID  NUMBER(11) NULL , " + " PASS_ALLERTYPE  NUMBER(11) NULL , "
				+ " PASS_ALLERNAME  NVARCHAR2(500) NULL , " + " MATCH_TIME  NVARCHAR2(20) NULL , "
				+ " MATCH_USER  NVARCHAR2(32) NULL , " + " UNABLE_MATCH  NUMBER(11) NULL , "
				+ " UNABLE_MATCH_DESC  NVARCHAR2(70) NULL , " + " CREATEDATE  DATE NULL  " + ") " + "LOGGING "
				+ "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_COSTITEM";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_COSTITEM，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_COSTITEM  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " ITEMCODE  NVARCHAR2(64) NULL , " + " ITEMNAME  NVARCHAR2(128) NULL , "
				+ " ITEMTYPE  NUMBER(11) NULL , " + " IS_BYX  NUMBER(11) NULL , "
				+ " SEARCHCODE  NVARCHAR2(128) NULL , " + " IS_SAVE  NUMBER(11) NULL , " + " INSERTTIME  DATE NULL  "
				+ ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_DEPT";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_DEPT，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_DEPT  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " DEPTCODE  NVARCHAR2(64) NULL , " + " DEPTNAME  NVARCHAR2(64) NULL , "
				+ " SEARCHCODE  NVARCHAR2(128) NULL , " + " IS_CLINIC  NUMBER(11) NULL , "
				+ " IS_INHOSP  NUMBER(11) NULL , " + " IS_EMERGENCY  NUMBER(11) NULL , "
				+ " IS_SAVE  NUMBER(11) NULL , " + " INSERTTIME  DATE NULL  " + ") " + "LOGGING " + "NOCOMPRESS "
				+ "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_DISEASE";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_DISEASE，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_DISEASE  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " DISCODE  NVARCHAR2(64) NULL , " + " DISNAME  NVARCHAR2(255) NULL , "
				+ " SEARCHCODE  NVARCHAR2(128) NULL , " + " TYPECODE  NVARCHAR2(32) NULL , "
				+ " TYPENAME  NVARCHAR2(128) NULL , " + " DIS_TYPE  NUMBER(11) NULL , " + " IS_MXB  NUMBER(11) NULL , "
				+ " IS_SAVE  NUMBER(11) NULL , " + " PASS_ICD_CODE  NVARCHAR2(128) NULL , "
				+ " PASS_ICD_NAME  NVARCHAR2(500) NULL , " + " MATCH_USER  NVARCHAR2(32) NULL , "
				+ " MATCH_TIME  NVARCHAR2(20) NULL , " + " UNABLE_MATCH  NUMBER(11) NULL , "
				+ " UNABLE_MATCH_DESC  NVARCHAR2(70) NULL , " + " CREATEDATE  DATE NULL  " + ") " + "LOGGING "
				+ "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_DOCTOR";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_DOCTOR，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_DOCTOR  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " DOCTORCODE  NVARCHAR2(64) NULL , " + " DOCTORNAME  NVARCHAR2(32) NULL , "
				+ " DEPTCODE  NVARCHAR2(64) NULL , " + " DEPTNAME  NVARCHAR2(64) NULL , "
				+ " DOCTORLEVEL  NVARCHAR2(32) NULL , " + " ILEVEL  NUMBER(11) NULL , "
				+ " IS_CLINIC  NUMBER(11) NULL , " + " SEARCHCODE  NVARCHAR2(128) NULL , "
				+ " PRESPRIV  NUMBER(11) NULL , " + " IS_SAVE  NUMBER(11) NULL , " + " PASSWORD  NVARCHAR2(128) NULL , "
				+ " ANTILEVEL  NUMBER(11) NULL , " + " CREATEDATE  DATE NULL  " + ") " + "LOGGING " + "NOCOMPRESS "
				+ "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_DRUG";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_DRUG，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_DRUG  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " DRUGCODE  NVARCHAR2(64) NULL , " + " DRUGNAME  NVARCHAR2(128) NULL , "
				+ " DRUGFORM  NVARCHAR2(128) NULL , " + " SEARCHCODE  NVARCHAR2(128) NULL , "
				+ " DRUGGROUPCODE  NUMBER(11) NULL , " + " DRUGGROUPNAME  NVARCHAR2(128) NULL , "
				+ " DRGGRP_SEARCHCODE  NVARCHAR2(128) NULL , " + " IS_BASEDRUG  NUMBER(11) NULL , "
				+ " IS_BASEDRUG_P  NUMBER(11) NULL , " + " IS_ANTI  NUMBER(11) NULL , "
				+ " ANTITYPE  NUMBER(11) NULL , " + " ANTILEVEL  NUMBER(11) NULL , " + " DRUGTYPE  NUMBER(11) NULL , "
				+ " DRUGFORMTYPE  NUMBER(11) NULL , " + " SOCIALSECURITY_RATIO  NVARCHAR2(64) NULL , "
				+ " IS_SOCIALSECURITY  NUMBER(11) NULL , " + " SOCIALSECURITY_DESC  NVARCHAR2(255) NULL , "
				+ " JDMTYPE  NUMBER(11) NULL , " + " IS_STIMULANT  NUMBER(11) NULL , "
				+ " STIMULANTINGRED  NVARCHAR2(64) NULL , " + " IS_SOLVENT  NUMBER(11) NULL , "
				+ " IS_SRPREPARATIONS  NUMBER(11) NULL , " + " IS_NEEDSKINTEST  NUMBER(11) NULL , "
				+ " IS_DEARMED  NUMBER(11) NULL , " + " IS_POISON  NUMBER(11) NULL , "
				+ " IS_BLOODMED  NUMBER(11) NULL , " + " IS_SUGARMED  NUMBER(11) NULL , "
				+ " OTCTYPE  NUMBER(11) NULL , " + " HIGH_ALERT_LEVEL  NUMBER(11) NULL , "
				+ " IS_BISECTION_USE  NUMBER(11) NULL , " + " CLASSID  NUMBER(11) NULL , "
				+ " CLASSTITLE  NVARCHAR2(128) NULL , " + " IS_SAVE  NUMBER(11) DEFAULT 0  NULL , "
				+ " OPERUSER  NVARCHAR2(32) NULL , " + " OPERTIME  NVARCHAR2(20) NULL , "
				+ " TYPENAME  NVARCHAR2(256) NULL , " + " STATE  NUMBER(11) NULL , " + " CREATEDATE  DATE NULL  " + ") "
				+ "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_DRUG_PASS";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_DRUG_PASS，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_DRUG_PASS  ( " + " PROID  NUMBER(11) NULL , "
				+ " DRUG_UNIQUE_CODE  NVARCHAR2(255) NULL , " + " DRUGCODE  NVARCHAR2(64) NULL , "
				+ " DRUGNAME  NVARCHAR2(128) NULL , " + " DRUGFORM  NVARCHAR2(128) NULL , "
				+ " DRUGSPEC  NVARCHAR2(128) NULL , " + " COMP_NAME  NVARCHAR2(128) NULL , "
				+ " APPROVALCODE  NVARCHAR2(64) NULL , " + " SEARCHCODE  NVARCHAR2(128) NULL , "
				+ " DOSEUNIT  NVARCHAR2(32) NULL , " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " PASS_DRUGCODE  NUMBER(11) NULL , " + " PASS_APPROVALCODE  NVARCHAR2(500) NULL , "
				+ " PASS_NAMETYPE  NUMBER(11) NULL , " + " PASS_DOSEUNIT  NVARCHAR2(256) NULL , "
				+ " PASS_DRUGNAME  NVARCHAR2(500) NULL , " + " PASS_FORM_NAME  NVARCHAR2(128) NULL , "
				+ " PASS_ST_STRENGTH  NVARCHAR2(1000) NULL , " + " PASS_ST_COMP_NAME  NVARCHAR2(500) NULL , "
				+ " PASS_DIVIDEND  NUMBER NULL , " + " PASS_DIVISOR  NUMBER NULL , "
				+ " MENULABEL  NVARCHAR2(256) NULL , " + " MATCH_USER  NVARCHAR2(32) NULL , "
				+ " MATCH_TIME  NVARCHAR2(20) NULL , " + " UNABLE_MATCH  NUMBER(11) NULL , "
				+ " UNABLE_MATCH_DESC  NVARCHAR2(70) NULL , " + " OPRPI_USER  NVARCHAR2(32) NULL , "
				+ " OPRPI_TIME  NVARCHAR2(20) NULL , " + " PASS_UPSTATE  NUMBER(11) NULL , "
				+ " CREATEDATE  DATE NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_DRUG_SUB";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_DRUG_SUB，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_DRUG_SUB  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " DRUGCODE  NVARCHAR2(255) NULL , " + " DRUGNAME  NVARCHAR2(256) NULL , "
				+ " SEARCHCODE  NVARCHAR2(128) NULL , " + " DRUGFORM  NVARCHAR2(128) NULL , "
				+ " DRUGSPEC  NVARCHAR2(255) NULL , " + " COSTUNIT  NVARCHAR2(128) NULL , " + " DDD  NUMBER NULL , "
				+ " DDDUNIT  NVARCHAR2(128) NULL , " + " DDD_COSTUNIT  NUMBER NULL , "
				+ " ADDDATE  NVARCHAR2(20) NULL , " + " IS_USE  NUMBER(11) NULL , " + " IS_SAVE  NUMBER(11) NULL , "
				+ " STATE  NUMBER(11) NULL , " + " INSERTTIME  DATE NULL  " + ") " + "LOGGING " + "NOCOMPRESS "
				+ "NOCACHE ";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_EXAM";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_EXAM，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_EXAM  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " EXAMCODE  NVARCHAR2(64) NULL , " + " EXAMNAME  NVARCHAR2(128) NULL , " + " TYPE  NUMBER(11) NULL , "
				+ " SEARCHCODE  NVARCHAR2(128) NULL , " + " IS_SAVE  NUMBER(11) NULL , " + " INSERTTIME  DATE NULL  "
				+ ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_FREQUENCY";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_FREQUENCY，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_FREQUENCY  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " FREQUENCY  NVARCHAR2(32) NULL , " + " TIMES  NUMBER(11) NULL , " + " DAYS  NUMBER(11) NULL , "
				+ " PHARMFREQUENCY  NVARCHAR2(32) NULL , " + " UNABLE_MATCH  NUMBER(11) NULL , "
				+ " MATCH_DESC  NVARCHAR2(128) NULL , " + " IS_SAVE  NUMBER(11) NULL , " + " CREATEDATE  DATE NULL  "
				+ ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_LAB";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_LAB，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_LAB  ( " + " MATCH_SCHEME  NUMBER(11) NULL , " + " LABCODE  NVARCHAR2(64) NULL , "
				+ " LABNAME  NVARCHAR2(128) NULL , " + " TYPE  NUMBER(11) NULL , "
				+ " SEARCHCODE  NVARCHAR2(128) NULL , " + " IS_SAVE  NUMBER(11) NULL , " + " INSERTTIME  DATE NULL  "
				+ ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_LABSUB";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_LABSUB，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_LABSUB  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " ITEMCODE  NVARCHAR2(64) NULL , " + " ITEMNAME  NVARCHAR2(128) NULL , "
				+ " TYPE_ID  NUMBER(11) NULL , " + " MEDSHOW_ID  NUMBER(11) NULL , "
				+ " SEARCHCODE  NVARCHAR2(128) NULL , " + " IS_SAVE  NUMBER(11) NULL , " + " INSERTTIME  DATE NULL  "
				+ ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_OPERATION";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_OPERATION，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_OPERATION  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " OPERATIONCODE  NVARCHAR2(64) NULL , " + " OPERATIONNAME  NVARCHAR2(128) NULL , "
				+ " SEARCHCODE  NVARCHAR2(128) NULL , " + " TYPENAME  NVARCHAR2(128) NULL , "
				+ " USEANTI  NUMBER(11) NULL , " + " PREMOMENT_LOW  NUMBER NULL , " + " PREMOMENT_HIGH  NUMBER NULL , "
				+ " DRUGTIME  NUMBER(11) NULL , " + " IS_SAVE  NUMBER(11) NULL , " + " CREATEDATE  DATE NULL  " + ") "
				+ "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   MC_DICT_ROUTE";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到MC_DICT_ROUTE，开始创建");
		}

		sql = "CREATE TABLE   MC_DICT_ROUTE  ( " + " MATCH_SCHEME  NUMBER(11) NULL , "
				+ " ROUTECODE  NVARCHAR2(128) NULL , " + " ROUTENAME  NVARCHAR2(128) NULL , "
				+ " SEARCHCODE  NVARCHAR2(128) NULL , " + " ROUTE_TYPE  NUMBER(11) NULL , "
				+ " ABBREV  NVARCHAR2(10) NULL , " + " IS_SAVE  NUMBER(11) NULL , "
				+ " PASS_ROUTEID  NUMBER(11) NULL , " + " PASS_ROUTE_NAME  NVARCHAR2(256) NULL , "
				+ " MATCH_USER  NVARCHAR2(32) NULL , " + " MATCH_TIME  NVARCHAR2(20) NULL , "
				+ " UNABLE_MATCH  NUMBER(11) NULL , " + " UNABLE_MATCH_DESC  NVARCHAR2(70) NULL , "
				+ " ISSKINTEST  NUMBER(11) NULL , " + " CREATEDATE  DATE NULL  " + ") " + "LOGGING " + "NOCOMPRESS "
				+ "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		System.out.println("创建字典表结束");
		System.out.println("开始创建业务表");

		try {
			sql = "DROP TABLE   T_MC_CLINIC_ALLERGEN";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_CLINIC_ALLERGEN，开始创建");
		}

		sql = "CREATE TABLE   T_MC_CLINIC_ALLERGEN  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " CLINICNO  NVARCHAR2(32) NULL , " + " ALLERCODE  NVARCHAR2(64) NULL , "
				+ " ALLERNAME  NVARCHAR2(64) NULL , " + " SYMPTOM  NVARCHAR2(255) NULL  " + ") " + "LOGGING "
				+ "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_CLINIC_COST";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_CLINIC_COST，开始创建");
		}

		sql = "CREATE TABLE   T_MC_CLINIC_COST  ( " + " IID  NUMBER(11) NULL , " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " CLINICNO  NVARCHAR2(32) NULL , " + " PRESCNO  NVARCHAR2(32) NULL , "
				+ " COSTTYPE  NUMBER(11) NULL , " + " DRUGINDEX  NUMBER(11) NULL , "
				+ " ITEMCODE  NVARCHAR2(128) NULL , " + " ITEMNAME  NVARCHAR2(128) NULL , "
				+ " DRUGFORM  NVARCHAR2(128) NULL , " + " DRUGSPEC  NVARCHAR2(128) NULL , "
				+ " DRUGSCCJ  NVARCHAR2(128) NULL , " + " ITEMUNIT  NVARCHAR2(96) NULL , " + " ITEMNUM  NUMBER NULL , "
				+ " COST  NUMBER NULL , " + " COSTTIME  NVARCHAR2(20) NULL , " + " DEPTCODE  NVARCHAR2(64) NULL , "
				+ " DEPTNAME  NVARCHAR2(64) NULL , " + " DOCTORCODE  NVARCHAR2(64) NULL , "
				+ " DOCTORNAME  NVARCHAR2(32) NULL , " + " MEDGROUPCODE  NVARCHAR2(64) NULL , "
				+ " MEDGROUPNAME  NVARCHAR2(64) NULL , " + " ROUTECODE  NVARCHAR2(128) NULL , "
				+ " PHARMACISTS  NVARCHAR2(32) NULL , " + " PHARMACISTS_  NVARCHAR2(32) NULL , "
				+ " IS_USE  NUMBER(11) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_CLINIC_DISEASE";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_CLINIC_DISEASE，开始创建");
		}

		sql = "CREATE TABLE   T_MC_CLINIC_DISEASE  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " CLINICNO  NVARCHAR2(32) NULL , " + " PRESCNO  NVARCHAR2(32) NULL , "
				+ " DISCODE  NVARCHAR2(64) NULL , " + " DISNAME  NVARCHAR2(255) NULL  " + ") " + "LOGGING "
				+ "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_CLINIC_EXAM";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_CLINIC_EXAM，开始创建");
		}

		sql = "CREATE TABLE   T_MC_CLINIC_EXAM  ( " + " IENDDATE  NUMBER(11) NULL , "
				+ " HISCODE  NVARCHAR2(128) NULL , " + " CASEID  NVARCHAR2(64) NULL , "
				+ " PATIENTID  NVARCHAR2(32) NULL , " + " CLINICNO  NVARCHAR2(32) NULL , "
				+ " EXAMCODE  NVARCHAR2(64) NULL , " + " EXAMNAME  NVARCHAR2(128) NULL , "
				+ " REQUESTNO  NVARCHAR2(64) NULL , " + " BODYPART  NVARCHAR2(512) NULL , "
				+ " EXAMRESULT  NCLOB NULL , " + " INSTRUMENT  NVARCHAR2(32) NULL , "
				+ " REPORTTIME  NVARCHAR2(20) NULL , " + " DOCTORCODE  NVARCHAR2(64) NULL , "
				+ " DOCTORNAME  NVARCHAR2(32) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_CLINIC_LAB";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_CLINIC_LAB，开始创建");
		}

		sql = "CREATE TABLE   T_MC_CLINIC_LAB  ( " + " IENDDATE  NUMBER(11) NULL , "
				+ " HISCODE  NVARCHAR2(128) NULL , " + " CASEID  NVARCHAR2(64) NULL , "
				+ " PATIENTID  NVARCHAR2(32) NULL , " + " CLINICNO  NVARCHAR2(32) NULL , "
				+ " REQUESTNO  NVARCHAR2(64) NULL , " + " LABCODE  NVARCHAR2(256) NULL , "
				+ " LABNAME  NVARCHAR2(512) NULL , " + " SAMPLETYPE  NVARCHAR2(32) NULL , "
				+ " SAMPLINGTIME  NVARCHAR2(20) NULL , " + " ITEMCODE  NVARCHAR2(64) NULL , "
				+ " ITEMNAME  NVARCHAR2(128) NULL , " + " LABRESULT  NVARCHAR2(64) NULL , "
				+ " RESULTFLAG  NVARCHAR2(2) NULL , " + " RANGE_0  NVARCHAR2(128) NULL , "
				+ " UNIT  NVARCHAR2(32) NULL , " + " REPORTTIME  NVARCHAR2(20) NULL , "
				+ " DOCTORCODE  NVARCHAR2(64) NULL , " + " DOCTORNAME  NVARCHAR2(32) NULL , "
				+ " INSTRUMENT  NVARCHAR2(32) NULL , " + " DD  NVARCHAR2(50) NULL  " + ") " + "LOGGING " + "NOCOMPRESS "
				+ "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_CLINIC_ORDER";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_CLINIC_ORDER，开始创建");
		}

		sql = "CREATE TABLE   T_MC_CLINIC_ORDER  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CID  NVARCHAR2(64) NULL , " + " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " CLINICNO  NVARCHAR2(32) NULL , " + " PRESCNO  NVARCHAR2(32) NULL , "
				+ " PRESCTYPE  NUMBER(11) NULL , " + " NUM  NVARCHAR2(20) NULL , " + " NUMUNIT  NVARCHAR2(64) NULL , "
				+ " COST  NUMBER NULL , " + " ORDERNO  NVARCHAR2(32) NULL , " + " ORDERTYPE  NUMBER(11) NULL , "
				+ " GROUPSTATE  NUMBER(11) NULL , " + " GROUPTAG  NVARCHAR2(32) NULL , "
				+ " DRUG_UNIQUE_CODE  NVARCHAR2(255) NULL , " + " ORDERCODE  NVARCHAR2(128) NULL , "
				+ " ORDERNAME  NVARCHAR2(256) NULL , " + " DRUGFORM  NVARCHAR2(128) NULL , "
				+ " DRUGSPEC  NVARCHAR2(128) NULL , " + " ROUTECODE  NVARCHAR2(128) NULL , "
				+ " ROUTENAME  NVARCHAR2(128) NULL , " + " SINGLEDOSE  NVARCHAR2(24) NULL , "
				+ " DOSEUNIT  NVARCHAR2(64) NULL , " + " FREQUENCY  NVARCHAR2(32) NULL , "
				+ " DEPTCODE  NVARCHAR2(64) NULL , " + " DEPTNAME  NVARCHAR2(64) NULL , "
				+ " DOCTORCODE  NVARCHAR2(64) NULL , " + " DOCTORNAME  NVARCHAR2(32) NULL , "
				+ " STARTDATETIME  NVARCHAR2(20) NULL , " + " DAYS  NUMBER(11) NULL , " + " PURPOSE  NUMBER(11) NULL , "
				+ " REMARK  NVARCHAR2(255) NULL , " + " ORDERSTATUS  NUMBER(11) NULL , "
				+ " IS_ALLOW  NUMBER(11) NULL , " + " REASONABLE_DESC  NVARCHAR2(128) NULL  " + ") " + "LOGGING "
				+ "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_CLINIC_PATIENT";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_CLINIC_PATIENT，开始创建");
		}

		sql = "CREATE TABLE   T_MC_CLINIC_PATIENT  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " IENDDATE  NUMBER(11) NULL , "
				+ " PATIENTID  NVARCHAR2(32) NULL , " + " CLINICNO  NVARCHAR2(32) NULL , "
				+ " PATIENTNAME  NVARCHAR2(32) , " + " SEX  NVARCHAR2(10) NULL , " + " BIRTHDATE  NVARCHAR2(20) NULL , "
				+ " AGE  NVARCHAR2(24) NULL , " + " IAGE  NUMBER(11) NULL , " + " COST  NUMBER NULL , "
				+ " HEIGHT  NVARCHAR2(16) NULL , " + " WEIGHT  NVARCHAR2(16) NULL , "
				+ " IDENTITYCARD  NVARCHAR2(24) NULL , " + " TELEPHONE  NVARCHAR2(48) NULL , "
				+ " DEPTCODE  NVARCHAR2(64) NULL , " + " DEPTNAME  NVARCHAR2(64) NULL , "
				+ " DOCTORCODE  NVARCHAR2(64) NULL , " + " DOCTORNAME  NVARCHAR2(32) NULL , "
				+ " MEDGROUPCODE  NVARCHAR2(64) NULL , " + " MEDGROUPNAME  NVARCHAR2(64) NULL , "
				+ " ENDDATE  NVARCHAR2(20) NULL , " + " DIAGNOSIS  NCLOB NULL , " + " ALLERGEN  NVARCHAR2(255) NULL , "
				+ " PAYCLASS  NVARCHAR2(32) NULL , " + " IS_EMERGENCY  NUMBER(11) NULL , "
				+ " IS_PREG  NUMBER(11) NULL , " + " PREG_STARTTIME  NVARCHAR2(20) NULL , "
				+ " IS_LACT  NUMBER(11) NULL , " + " HEP_DAMAGE  NUMBER(11) NULL , " + " REN_DAMAGE  NUMBER(11) NULL , "
				+ " STANDBY  NVARCHAR2(255) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_INHOSP_ALLERGEN";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_INHOSP_ALLERGEN，开始创建");
		}

		sql = "CREATE TABLE   T_MC_INHOSP_ALLERGEN  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " VISITID  NVARCHAR2(32) NULL , " + " ALLERCODE  NVARCHAR2(64) NULL , "
				+ " ALLERNAME  NVARCHAR2(64) NULL , " + " SYMPTOM  NVARCHAR2(255) NULL  " + ") " + "LOGGING "
				+ "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_INHOSP_COST";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_INHOSP_COST，开始创建");
		}

		sql = "CREATE TABLE   T_MC_INHOSP_COST  ( " + " IID  NUMBER(11) NULL , " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " VISITID  NVARCHAR2(32) NULL , " + " COSTTYPE  NUMBER(11) NULL , " + " DRUGINDEX  NUMBER(11) NULL , "
				+ " ITEMCODE  NVARCHAR2(128) NULL , " + " ITEMNAME  NVARCHAR2(128) NULL , "
				+ " DRUGFORM  NVARCHAR2(128) NULL , " + " DRUGSPEC  NVARCHAR2(128) NULL , "
				+ " DRUGSCCJ  NVARCHAR2(128) NULL , " + " ITEMUNIT  NVARCHAR2(96) NULL , " + " ITEMNUM  NUMBER NULL , "
				+ " COST  NUMBER NULL , " + " COSTTIME  NVARCHAR2(20) NULL , " + " DEPTCODE  NVARCHAR2(64) NULL , "
				+ " DEPTNAME  NVARCHAR2(64) NULL , " + " WARDCODE  NVARCHAR2(64) NULL , "
				+ " WARDNAME  NVARCHAR2(64) NULL , " + " DOCTORCODE  NVARCHAR2(64) NULL , "
				+ " DOCTORNAME  NVARCHAR2(32) NULL , " + " MEDGROUPCODE  NVARCHAR2(64) NULL , "
				+ " MEDGROUPNAME  NVARCHAR2(64) NULL , " + " IS_OUT  NUMBER(11) NULL , " + " IS_USE  NUMBER(11) NULL , "
				+ " ROUTECODE  NVARCHAR2(128) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_INHOSP_DISEASE";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_INHOSP_DISEASE，开始创建");
		}

		sql = "CREATE TABLE   T_MC_INHOSP_DISEASE  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " VISITID  NVARCHAR2(32) NULL , " + " DISCODE  NVARCHAR2(64) NULL , "
				+ " DISNAME  NVARCHAR2(255) NULL , " + " TREATMENT  NUMBER(11) NULL , "
				+ " IS_HOSPINFECTION  NUMBER(11) NULL , " + " DISEASETYPE  NUMBER(11) NULL , "
				+ " IS_MAIN  NUMBER(11) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_INHOSP_EXAM";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_INHOSP_EXAM，开始创建");
		}

		sql = "CREATE TABLE   T_MC_INHOSP_EXAM  ( " + " IENDDATE  NUMBER(11) NULL , "
				+ " HISCODE  NVARCHAR2(128) NULL , " + " CASEID  NVARCHAR2(64) NULL , "
				+ " PATIENTID  NVARCHAR2(32) NULL , " + " VISITID  NVARCHAR2(32) NULL , "
				+ " EXAMCODE  NVARCHAR2(64) NULL , " + " EXAMNAME  NVARCHAR2(128) NULL , "
				+ " REQUESTNO  NVARCHAR2(64) NULL , " + " BODYPART  NVARCHAR2(512) NULL , "
				+ " EXAMRESULT  NCLOB NULL , " + " INSTRUMENT  NVARCHAR2(32) NULL , "
				+ " REPORTTIME  NVARCHAR2(20) NULL , " + " DOCTORCODE  NVARCHAR2(64) NULL , "
				+ " DOCTORNAME  NVARCHAR2(32) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_INHOSP_LAB";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_INHOSP_LAB，开始创建");
		}

		sql = "CREATE TABLE   T_MC_INHOSP_LAB  ( " + " IENDDATE  NUMBER(11) NULL , "
				+ " HISCODE  NVARCHAR2(128) NULL , " + " CASEID  NVARCHAR2(64) NULL , "
				+ " PATIENTID  NVARCHAR2(32) NULL , " + " VISITID  NVARCHAR2(32) NULL , "
				+ " REQUESTNO  NVARCHAR2(64) NULL , " + " LABCODE  NVARCHAR2(256) NULL , "
				+ " LABNAME  NVARCHAR2(512) NULL , " + " SAMPLETYPE  NVARCHAR2(32) NULL , "
				+ " SAMPLINGTIME  NVARCHAR2(20) NULL , " + " ITEMCODE  NVARCHAR2(64) NULL , "
				+ " ITEMNAME  NVARCHAR2(128) NULL , " + " LABRESULT  NVARCHAR2(64) NULL , "
				+ " RESULTFLAG  NVARCHAR2(2) NULL , " + " RANGE_0  NVARCHAR2(128) NULL , "
				+ " UNIT  NVARCHAR2(32) NULL , " + " REPORTTIME  NVARCHAR2(20) NULL , "
				+ " DOCTORCODE  NVARCHAR2(64) NULL , " + " DOCTORNAME  NVARCHAR2(32) NULL , "
				+ " INSTRUMENT  NVARCHAR2(32) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_INHOSP_OPERATION";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_INHOSP_OPERATION，开始创建");
		}

		sql = "CREATE TABLE   T_MC_INHOSP_OPERATION  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " OPRID  NVARCHAR2(64) NULL , " + " CASEID  NVARCHAR2(64) NULL , "
				+ " PATIENTID  NVARCHAR2(32) NULL , " + " VISITID  NVARCHAR2(32) NULL , "
				+ " OPERATIONCODE  NVARCHAR2(64) NULL , " + " OPERATIONNAME  NVARCHAR2(128) NULL , "
				+ " INCISIONTYPE  NUMBER(11) NULL , " + " STARTDATE  NVARCHAR2(20) NULL , "
				+ " ENDDATE  NVARCHAR2(20) NULL , " + " DOCTORCODE  NVARCHAR2(64) NULL , "
				+ " DOCTORNAME  NVARCHAR2(32) NULL , " + " DEPTCODE  NVARCHAR2(64) NULL , "
				+ " DEPTNAME  NVARCHAR2(64) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_INHOSP_ORDER";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_INHOSP_ORDER，开始创建");
		}

		sql = "CREATE TABLE   T_MC_INHOSP_ORDER  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CID  NVARCHAR2(64) NULL , " + " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " VISITID  NVARCHAR2(32) NULL , " + " ORDERNO  NVARCHAR2(32) NULL , "
				+ " ORDERTYPE  NUMBER(11) NULL , " + " GROUPSTATE  NUMBER(11) NULL , "
				+ " GROUPTAG  NVARCHAR2(32) NULL , " + " IS_TEMP  NUMBER(11) NULL , "
				+ " DRUG_UNIQUE_CODE  NVARCHAR2(255) NULL , " + " ORDERCODE  NVARCHAR2(128) NULL , "
				+ " ORDERNAME  NVARCHAR2(256) NULL , " + " DRUGFORM  NVARCHAR2(128) NULL , "
				+ " DRUGSPEC  NVARCHAR2(128) NULL , " + " ROUTECODE  NVARCHAR2(128) NULL , "
				+ " ROUTENAME  NVARCHAR2(128) NULL , " + " SINGLEDOSE  NVARCHAR2(24) NULL , "
				+ " DOSEUNIT  NVARCHAR2(64) NULL , " + " FREQUENCY  NVARCHAR2(32) NULL , "
				+ " DEPTCODE  NVARCHAR2(64) NULL , " + " DEPTNAME  NVARCHAR2(64) NULL , "
				+ " WARDCODE  NVARCHAR2(64) NULL , " + " WARDNAME  NVARCHAR2(64) NULL , "
				+ " DOCTORCODE  NVARCHAR2(64) NULL , " + " DOCTORNAME  NVARCHAR2(32) NULL , "
				+ " EXECUTETIME  NVARCHAR2(20) NULL , " + " STARTDATETIME  NVARCHAR2(20) NULL, "
				+ " ENDDATETIME  NVARCHAR2(20) NULL , " + " PA_ENDDATETIME  NVARCHAR2(20) NULL , "
				+ " PURPOSE  NUMBER(11) NULL , " + " REMARK  NVARCHAR2(255) NULL , " + " IS_OUT  NUMBER(11) NULL , "
				+ " ORDERSTATUS  NUMBER(11) NULL , " + " IS_ALLOW  NUMBER(11) NULL , "
				+ " REASONABLE_DESC  NVARCHAR2(128) NULL , " + " MEDITIME  NUMBER(11) NULL  " + ") " + "LOGGING "
				+ "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_INHOSP_PATIENT";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_INHOSP_PATIENT，开始创建");
		}

		sql = "CREATE TABLE   T_MC_INHOSP_PATIENT  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " VISITID  NVARCHAR2(32) NULL , " + " HOSPITALNO  NVARCHAR2(32) NULL , "
				+ " PATIENTNAME  NVARCHAR2(32), " + " SEX  NVARCHAR2(10) NULL , " + " BIRTHDATE  NVARCHAR2(20) NULL , "
				+ " AGE  NVARCHAR2(24) NULL , " + " IAGE  NUMBER(11) NULL , " + " COST  NUMBER NULL , "
				+ " HEIGHT  NVARCHAR2(16) NULL , " + " WEIGHT  NVARCHAR2(16) NULL , "
				+ " IDENTITYCARD  NVARCHAR2(24) NULL , " + " TELEPHONE  NVARCHAR2(48) NULL , "
				+ " BEDNO  NVARCHAR2(20) NULL , " + " DEPTCODE  NVARCHAR2(64) NULL , "
				+ " DEPTNAME  NVARCHAR2(64) NULL , " + " WARDCODE  NVARCHAR2(64) NULL , "
				+ " WARDNAME  NVARCHAR2(64) NULL , " + " DOCTORCODE  NVARCHAR2(64) NULL , "
				+ " DOCTORNAME  NVARCHAR2(32) NULL , " + " MEDGROUPCODE  NVARCHAR2(64) NULL , "
				+ " MEDGROUPNAME  NVARCHAR2(64) NULL , " + " STARTDATE  NVARCHAR2(20) NULL , "
				+ " ENDDATE  NVARCHAR2(20) NULL , " + " ACCOUNTDATE  NVARCHAR2(20) NULL , "
				+ " IN_DIAGNOSIS  NCLOB NULL , " + " ALLERGEN  NVARCHAR2(255) NULL , "
				+ " NURSINGCLASS  NVARCHAR2(32) NULL , " + " PAYCLASS  NVARCHAR2(32) NULL , "
				+ " IS_PREG  NUMBER(11) NULL , " + " PREG_STARTTIME  NVARCHAR2(20) NULL , "
				+ " IS_LACT  NUMBER(11) NULL , " + " HEP_DAMAGE  NUMBER(11) NULL , " + " REN_DAMAGE  NUMBER(11) NULL , "
				+ " STANDBY  NVARCHAR2(255) NULL , " + " OPERATIONS  NVARCHAR2(512) NULL , "
				+ " INCISIONTYPES  NVARCHAR2(128) NULL , " + " FIRSTDEPTNAME  NVARCHAR2(32) NULL , "
				+ " I_IN  NUMBER(11) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_INHOSP_TEMPERATURE";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_INHOSP_TEMPERATURE，开始创建");
		}

		sql = "CREATE TABLE   T_MC_INHOSP_TEMPERATURE  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " VISITID  NVARCHAR2(32) NULL , " + " TAKETIME  NVARCHAR2(20) NULL , " + " TEMPERATURE  NUMBER NULL  "
				+ ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_OUTHOSP_ALLERGEN";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_OUTHOSP_ALLERGEN，开始创建");
		}

		sql = "CREATE TABLE   T_MC_OUTHOSP_ALLERGEN  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " VISITID  NVARCHAR2(32) NULL , " + " ALLERCODE  NVARCHAR2(64) NULL , "
				+ " ALLERNAME  NVARCHAR2(64) NULL , " + " SYMPTOM  NVARCHAR2(255) NULL  " + ") " + "LOGGING "
				+ "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_OUTHOSP_COST";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_OUTHOSP_COST，开始创建");
		}

		sql = "CREATE TABLE   T_MC_OUTHOSP_COST  ( " + " IID  NUMBER(11) NULL , " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " VISITID  NVARCHAR2(32) NULL , " + " COSTTYPE  NUMBER(11) NULL , " + " DRUGINDEX  NUMBER(11) NULL , "
				+ " ITEMCODE  NVARCHAR2(128) NULL , " + " ITEMNAME  NVARCHAR2(128) NULL , "
				+ " DRUGFORM  NVARCHAR2(128) NULL , " + " DRUGSPEC  NVARCHAR2(128) NULL , "
				+ " DRUGSCCJ  NVARCHAR2(128) NULL , " + " ITEMUNIT  NVARCHAR2(96) NULL , " + " ITEMNUM  NUMBER NULL , "
				+ " COST  NUMBER NULL , " + " COSTTIME  NVARCHAR2(20) NULL , " + " DEPTCODE  NVARCHAR2(64) NULL , "
				+ " DEPTNAME  NVARCHAR2(64) NULL , " + " WARDCODE  NVARCHAR2(64) NULL , "
				+ " WARDNAME  NVARCHAR2(64) NULL , " + " DOCTORCODE  NVARCHAR2(64) NULL , "
				+ " DOCTORNAME  NVARCHAR2(32) NULL , " + " MEDGROUPCODE  NVARCHAR2(64) NULL , "
				+ " MEDGROUPNAME  NVARCHAR2(64) NULL , " + " IS_OUT  NUMBER(11) NULL , " + " IS_USE  NUMBER(11) NULL , "
				+ " ROUTECODE  NVARCHAR2(128) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_OUTHOSP_DISEASE";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_OUTHOSP_DISEASE，开始创建");
		}

		sql = "CREATE TABLE   T_MC_OUTHOSP_DISEASE  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " VISITID  NVARCHAR2(32) NULL , " + " DISCODE  NVARCHAR2(64) NULL , "
				+ " DISNAME  NVARCHAR2(255) NULL , " + " TREATMENT  NUMBER(11) NULL , "
				+ " IS_HOSPINFECTION  NUMBER(11) NULL , " + " DISEASETYPE  NUMBER(11) NULL , "
				+ " IS_MAIN  NUMBER(11) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_OUTHOSP_EXAM";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_OUTHOSP_EXAM，开始创建");
		}

		sql = "CREATE TABLE   T_MC_OUTHOSP_EXAM  ( " + " IENDDATE  NUMBER(11) NULL , "
				+ " HISCODE  NVARCHAR2(128) NULL , " + " CASEID  NVARCHAR2(64) NULL , "
				+ " PATIENTID  NVARCHAR2(32) NULL , " + " VISITID  NVARCHAR2(32) NULL , "
				+ " EXAMCODE  NVARCHAR2(64) NULL , " + " EXAMNAME  NVARCHAR2(128) NULL , "
				+ " REQUESTNO  NVARCHAR2(64) NULL , " + " BODYPART  NVARCHAR2(512) NULL , "
				+ " EXAMRESULT  NCLOB NULL , " + " INSTRUMENT  NVARCHAR2(32) NULL , "
				+ " REPORTTIME  NVARCHAR2(20) NULL , " + " DOCTORCODE  NVARCHAR2(64) NULL , "
				+ " DOCTORNAME  NVARCHAR2(32) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_OUTHOSP_LAB";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_OUTHOSP_LAB，开始创建");
		}

		sql = "CREATE TABLE   T_MC_OUTHOSP_LAB  ( " + " IENDDATE  NUMBER(11) NULL , "
				+ " HISCODE  NVARCHAR2(128) NULL , " + " CASEID  NVARCHAR2(64) NULL , "
				+ " PATIENTID  NVARCHAR2(32) NULL , " + " VISITID  NVARCHAR2(32) NULL , "
				+ " REQUESTNO  NVARCHAR2(64) NULL , " + " LABCODE  NVARCHAR2(256) NULL , "
				+ " LABNAME  NVARCHAR2(512) NULL , " + " SAMPLETYPE  NVARCHAR2(32) NULL , "
				+ " SAMPLINGTIME  NVARCHAR2(20) NULL , " + " ITEMCODE  NVARCHAR2(64) NULL , "
				+ " ITEMNAME  NVARCHAR2(128) NULL , " + " LABRESULT  NVARCHAR2(64) NULL , "
				+ " RESULTFLAG  NVARCHAR2(2) NULL , " + " RANGE_0  NVARCHAR2(128) NULL , "
				+ " UNIT  NVARCHAR2(32) NULL , " + " REPORTTIME  NVARCHAR2(20) NULL , "
				+ " DOCTORCODE  NVARCHAR2(64) NULL , " + " DOCTORNAME  NVARCHAR2(32) NULL , "
				+ " INSTRUMENT  NVARCHAR2(32) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_OUTHOSP_OPERATION";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_OUTHOSP_OPERATION，开始创建");
		}

		sql = "CREATE TABLE   T_MC_OUTHOSP_OPERATION  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " OPRID  NVARCHAR2(64) NULL , " + " CASEID  NVARCHAR2(64) NULL , "
				+ " PATIENTID  NVARCHAR2(32) NULL , " + " VISITID  NVARCHAR2(32) NULL , "
				+ " OPERATIONCODE  NVARCHAR2(64) NULL , " + " OPERATIONNAME  NVARCHAR2(128) NULL , "
				+ " INCISIONTYPE  NUMBER(11) NULL , " + " STARTDATE  NVARCHAR2(20) NULL , "
				+ " ENDDATE  NVARCHAR2(20) NULL , " + " DOCTORCODE  NVARCHAR2(64) NULL , "
				+ " DOCTORNAME  NVARCHAR2(32) NULL , " + " DEPTCODE  NVARCHAR2(64) NULL , "
				+ " DEPTNAME  NVARCHAR2(64) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_OUTHOSP_ORDER";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_OUTHOSP_ORDER，开始创建");
		}

		sql = "CREATE TABLE   T_MC_OUTHOSP_ORDER  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CID  NVARCHAR2(64) NULL , " + " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " VISITID  NVARCHAR2(32) NULL , " + " ORDERNO  NVARCHAR2(32) NULL , "
				+ " ORDERTYPE  NUMBER(11) NULL , " + " GROUPSTATE  NUMBER(11) NULL , "
				+ " GROUPTAG  NVARCHAR2(32) NULL , " + " IS_TEMP  NUMBER(11) NULL , "
				+ " DRUG_UNIQUE_CODE  NVARCHAR2(255) NULL , " + " ORDERCODE  NVARCHAR2(128) NULL , "
				+ " ORDERNAME  NVARCHAR2(256) NULL , " + " DRUGFORM  NVARCHAR2(128) NULL , "
				+ " DRUGSPEC  NVARCHAR2(128) NULL , " + " ROUTECODE  NVARCHAR2(128) NULL , "
				+ " ROUTENAME  NVARCHAR2(128) NULL , " + " SINGLEDOSE  NVARCHAR2(24) NULL , "
				+ " DOSEUNIT  NVARCHAR2(64) NULL , " + " FREQUENCY  NVARCHAR2(32) NULL , "
				+ " DEPTCODE  NVARCHAR2(64) NULL , " + " DEPTNAME  NVARCHAR2(64) NULL , "
				+ " WARDCODE  NVARCHAR2(64) NULL , " + " WARDNAME  NVARCHAR2(64) NULL , "
				+ " DOCTORCODE  NVARCHAR2(64) NULL , " + " DOCTORNAME  NVARCHAR2(32) NULL , "
				+ " EXECUTETIME  NVARCHAR2(20) NULL , " + " STARTDATETIME  NVARCHAR2(20) NULL, "
				+ " ENDDATETIME  NVARCHAR2(20) NULL , " + " PA_ENDDATETIME  NVARCHAR2(20) NULL , "
				+ " PURPOSE  NUMBER(11) NULL , " + " REMARK  NVARCHAR2(255) NULL , " + " IS_OUT  NUMBER(11) NULL , "
				+ " ORDERSTATUS  NUMBER(11) NULL , " + " IS_ALLOW  NUMBER(11) NULL , "
				+ " REASONABLE_DESC  NVARCHAR2(128) NULL , " + " MEDITIME  NUMBER(11) NULL  " + ") " + "LOGGING "
				+ "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_OUTHOSP_PATIENT";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_OUTHOSP_PATIENT，开始创建");
		}

		sql = "CREATE TABLE   T_MC_OUTHOSP_PATIENT  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " IENDDATE  NUMBER(11) NULL , "
				+ " PATIENTID  NVARCHAR2(32) NULL , " + " VISITID  NVARCHAR2(32) NULL , "
				+ " HOSPITALNO  NVARCHAR2(32) NULL , " + " PATIENTNAME  NVARCHAR2(32) , "
				+ " SEX  NVARCHAR2(10) NULL , " + " BIRTHDATE  NVARCHAR2(20) NULL , " + " AGE  NVARCHAR2(24) NULL , "
				+ " IAGE  NUMBER(11) NULL , " + " COST  NUMBER NULL , " + " HEIGHT  NVARCHAR2(16) NULL , "
				+ " WEIGHT  NVARCHAR2(16) NULL , " + " IDENTITYCARD  NVARCHAR2(24) NULL , "
				+ " TELEPHONE  NVARCHAR2(48) NULL , " + " BEDNO  NVARCHAR2(20) NULL , "
				+ " DEPTCODE  NVARCHAR2(64) NULL , " + " DEPTNAME  NVARCHAR2(64) NULL , "
				+ " DOCTORCODE  NVARCHAR2(64) NULL , " + " DOCTORNAME  NVARCHAR2(32) NULL , "
				+ " MEDGROUPCODE  NVARCHAR2(64) NULL , " + " MEDGROUPNAME  NVARCHAR2(64) NULL , "
				+ " WARDCODE  NVARCHAR2(64) NULL , " + " WARDNAME  NVARCHAR2(64) NULL , "
				+ " STARTDATE  NVARCHAR2(20) NULL , " + " ENDDATE  NVARCHAR2(20) NULL , "
				+ " ACCOUNTDATE  NVARCHAR2(20) NULL , " + " IN_DIAGNOSIS  NCLOB NULL , "
				+ " OUT_DIAGNOSIS  NCLOB NULL , " + " ALLERGEN  NVARCHAR2(255) NULL , "
				+ " NURSINGCLASS  NVARCHAR2(32) NULL , " + " PAYCLASS  NVARCHAR2(32) NULL , "
				+ " IS_PREG  NUMBER(11) NULL , " + " PREG_STARTTIME  NVARCHAR2(20) NULL , "
				+ " IS_LACT  NUMBER(11) NULL , " + " HEP_DAMAGE  NUMBER(11) NULL , " + " REN_DAMAGE  NUMBER(11) NULL , "
				+ " STANDBY  NVARCHAR2(255) NULL , " + " OPERATIONS  NVARCHAR2(512) NULL , "
				+ " INCISIONTYPES  NVARCHAR2(128) NULL , " + " FIRSTDEPTNAME  NVARCHAR2(32) NULL , "
				+ " I_IN  NUMBER(11) NULL  " + ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		try {
			sql = "DROP TABLE   T_MC_OUTHOSP_TEMPERATURE";
			jdbcTemplate_oracle.update(sql);
		} catch (Exception e) {
			System.out.println("未找到T_MC_OUTHOSP_TEMPERATURE，开始创建");
		}

		sql = "CREATE TABLE   T_MC_OUTHOSP_TEMPERATURE  ( " + " HISCODE  NVARCHAR2(128) NULL , "
				+ " CASEID  NVARCHAR2(64) NULL , " + " PATIENTID  NVARCHAR2(32) NULL , "
				+ " VISITID  NVARCHAR2(32) NULL , " + " TAKETIME  NVARCHAR2(20) NULL , " + " TEMPERATURE  NUMBER NULL  "
				+ ") " + "LOGGING " + "NOCOMPRESS " + "NOCACHE";
		jdbcTemplate_oracle.update(sql);

		System.out.println("创建业务表结束");

		// System.out.println("开始修改所有表属性");
		//
		// sql="ALTER TABLE MC_DICT_ALLERGEN ADD CHECK ( MATCH_SCHEME IS NOT
		// NULL); " +
		// "ALTER TABLE MC_DICT_ALLERGEN ADD CHECK ( ALLERCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_ALLERGEN ADD CHECK ( ALLERNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_ALLERGEN ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_ALLERGEN ADD CHECK ( MATCH_SCHEME IS NOT NULL);
		// " +
		// "ALTER TABLE MC_DICT_ALLERGEN ADD CHECK ( ALLERCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_ALLERGEN ADD CHECK ( ALLERNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_ALLERGEN ADD CHECK ( IS_SAVE IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_ALLERGEN ADD PRIMARY KEY ( MATCH_SCHEME ,
		// ALLERCODE );"
		// + "ALTER TABLE MC_DICT_COSTITEM ADD CHECK ( MATCH_SCHEME IS NOT
		// NULL); " +
		// "ALTER TABLE MC_DICT_COSTITEM ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_COSTITEM ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_COSTITEM ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_COSTITEM ADD CHECK ( MATCH_SCHEME IS NOT NULL);
		// " +
		// "ALTER TABLE MC_DICT_COSTITEM ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_COSTITEM ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_COSTITEM ADD CHECK ( IS_SAVE IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_COSTITEM ADD PRIMARY KEY ( MATCH_SCHEME ,
		// ITEMCODE );"
		// + "ALTER TABLE MC_DICT_DEPT ADD CHECK ( MATCH_SCHEME IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_DEPT ADD CHECK ( DEPTCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DEPT ADD CHECK ( DEPTNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DEPT ADD CHECK ( MATCH_SCHEME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DEPT ADD CHECK ( DEPTCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DEPT ADD CHECK ( DEPTNAME IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_DEPT ADD PRIMARY KEY ( DEPTCODE , MATCH_SCHEME
		// );"
		// + "ALTER TABLE MC_DICT_DISEASE ADD CHECK ( MATCH_SCHEME IS NOT NULL);
		// " +
		// "ALTER TABLE MC_DICT_DISEASE ADD CHECK ( DISCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DISEASE ADD CHECK ( DISNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DISEASE ADD CHECK ( IS_MXB IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DISEASE ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DISEASE ADD CHECK ( MATCH_SCHEME IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_DISEASE ADD CHECK ( DISCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DISEASE ADD CHECK ( DISNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DISEASE ADD CHECK ( IS_MXB IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DISEASE ADD CHECK ( IS_SAVE IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_DISEASE ADD PRIMARY KEY ( MATCH_SCHEME ,
		// DISCODE );"
		// + "ALTER TABLE MC_DICT_DOCTOR ADD CHECK ( MATCH_SCHEME IS NOT NULL);
		// " +
		// "ALTER TABLE MC_DICT_DOCTOR ADD CHECK ( DOCTORCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DOCTOR ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DOCTOR ADD CHECK ( PASSWORD IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DOCTOR ADD CHECK ( MATCH_SCHEME IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_DOCTOR ADD CHECK ( DOCTORCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DOCTOR ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DOCTOR ADD CHECK ( PASSWORD IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_DOCTOR ADD PRIMARY KEY ( MATCH_SCHEME ,
		// DOCTORCODE );"
		// + "ALTER TABLE MC_DICT_DRUG ADD CHECK ( MATCH_SCHEME IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_DRUG ADD CHECK ( DRUGCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DRUG ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DRUG ADD CHECK ( MATCH_SCHEME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DRUG ADD CHECK ( DRUGCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DRUG ADD CHECK ( IS_SAVE IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_DRUG ADD PRIMARY KEY ( MATCH_SCHEME , DRUGCODE
		// );"
		// + "ALTER TABLE MC_DICT_DRUG_PASS ADD CHECK ( PROID IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DRUG_PASS ADD CHECK ( DRUG_UNIQUE_CODE IS NOT
		// NULL); " +
		// "ALTER TABLE MC_DICT_DRUG_PASS ADD CHECK ( DRUGCODE IS NOT NULL); " +
		//// "ALTER TABLE MC_DICT_DRUG_PASS ADD CHECK ( DOSEUNIT IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_DRUG_PASS ADD CHECK ( MATCH_SCHEME IS NOT NULL);
		// " +
		// "ALTER TABLE MC_DICT_DRUG_PASS ADD CHECK ( PROID IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DRUG_PASS ADD CHECK ( DRUG_UNIQUE_CODE IS NOT
		// NULL); " +
		// "ALTER TABLE MC_DICT_DRUG_PASS ADD CHECK ( DRUGCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DRUG_PASS ADD CHECK ( MATCH_SCHEME IS NOT
		// NULL);"
		// + "ALTER TABLE MC_DICT_DRUG_PASS ADD PRIMARY KEY ( PROID ,
		// DRUG_UNIQUE_CODE , MATCH_SCHEME );"//DOSEUNIT ,
		// + "ALTER TABLE MC_DICT_DRUG_SUB ADD CHECK ( MATCH_SCHEME IS NOT
		// NULL); " +
		// "ALTER TABLE MC_DICT_DRUG_SUB ADD CHECK ( DRUGCODE IS NOT NULL); " +
		//// "ALTER TABLE MC_DICT_DRUG_SUB ADD CHECK ( DRUGSPEC IS NOT NULL); "
		// +
		//// "ALTER TABLE MC_DICT_DRUG_SUB ADD CHECK ( COSTUNIT IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_DRUG_SUB ADD CHECK ( IS_USE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DRUG_SUB ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_DRUG_SUB ADD CHECK ( MATCH_SCHEME IS NOT NULL);
		// " +
		// "ALTER TABLE MC_DICT_DRUG_SUB ADD PRIMARY KEY ( MATCH_SCHEME ,
		// DRUGCODE );"
		// + "ALTER TABLE MC_DICT_EXAM ADD CHECK ( MATCH_SCHEME IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_EXAM ADD CHECK ( EXAMCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_EXAM ADD CHECK ( EXAMNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_EXAM ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_EXAM ADD CHECK ( MATCH_SCHEME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_EXAM ADD CHECK ( EXAMCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_EXAM ADD CHECK ( EXAMNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_EXAM ADD CHECK ( IS_SAVE IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_EXAM ADD PRIMARY KEY ( EXAMCODE , MATCH_SCHEME
		// );"
		// + "ALTER TABLE MC_DICT_FREQUENCY ADD CHECK ( MATCH_SCHEME IS NOT
		// NULL); " +
		// "ALTER TABLE MC_DICT_FREQUENCY ADD CHECK ( FREQUENCY IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_FREQUENCY ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_FREQUENCY ADD CHECK ( MATCH_SCHEME IS NOT NULL);
		// " +
		// "ALTER TABLE MC_DICT_FREQUENCY ADD CHECK ( FREQUENCY IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_FREQUENCY ADD CHECK ( IS_SAVE IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_FREQUENCY ADD PRIMARY KEY ( MATCH_SCHEME ,
		// FREQUENCY );"
		// + "ALTER TABLE MC_DICT_LAB ADD CHECK ( MATCH_SCHEME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_LAB ADD CHECK ( LABCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_LAB ADD CHECK ( LABNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_LAB ADD CHECK ( MATCH_SCHEME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_LAB ADD CHECK ( LABCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_LAB ADD CHECK ( LABNAME IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_LAB ADD PRIMARY KEY ( LABNAME , LABCODE ,
		// MATCH_SCHEME );"
		// + "ALTER TABLE MC_DICT_LABSUB ADD CHECK ( MATCH_SCHEME IS NOT NULL);
		// " +
		// "ALTER TABLE MC_DICT_LABSUB ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_LABSUB ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_LABSUB ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_LABSUB ADD CHECK ( MATCH_SCHEME IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_LABSUB ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_LABSUB ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_LABSUB ADD CHECK ( IS_SAVE IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_LABSUB ADD PRIMARY KEY ( ITEMCODE , ITEMNAME ,
		// MATCH_SCHEME );"
		// + "ALTER TABLE MC_DICT_OPERATION ADD CHECK ( MATCH_SCHEME IS NOT
		// NULL); " +
		// "ALTER TABLE MC_DICT_OPERATION ADD CHECK ( OPERATIONCODE IS NOT
		// NULL); " +
		// "ALTER TABLE MC_DICT_OPERATION ADD CHECK ( OPERATIONNAME IS NOT
		// NULL); " +
		// "ALTER TABLE MC_DICT_OPERATION ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_OPERATION ADD CHECK ( CREATEDATE IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_OPERATION ADD CHECK ( MATCH_SCHEME IS NOT NULL);
		// " +
		// "ALTER TABLE MC_DICT_OPERATION ADD CHECK ( OPERATIONCODE IS NOT
		// NULL); " +
		// "ALTER TABLE MC_DICT_OPERATION ADD CHECK ( OPERATIONNAME IS NOT
		// NULL); " +
		// "ALTER TABLE MC_DICT_OPERATION ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_OPERATION ADD CHECK ( CREATEDATE IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_OPERATION ADD PRIMARY KEY ( MATCH_SCHEME ,
		// OPERATIONCODE );"
		// + "ALTER TABLE MC_DICT_ROUTE ADD CHECK ( MATCH_SCHEME IS NOT NULL); "
		// +
		// "ALTER TABLE MC_DICT_ROUTE ADD CHECK ( ROUTECODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_ROUTE ADD CHECK ( IS_SAVE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_ROUTE ADD CHECK ( MATCH_SCHEME IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_ROUTE ADD CHECK ( ROUTECODE IS NOT NULL); " +
		// "ALTER TABLE MC_DICT_ROUTE ADD CHECK ( IS_SAVE IS NOT NULL);"
		// + "ALTER TABLE MC_DICT_ROUTE ADD PRIMARY KEY ( MATCH_SCHEME ,
		// ROUTECODE );"
		// + "ALTER TABLE T_MC_CLINIC_ALLERGEN ADD CHECK ( HISCODE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_CLINIC_ALLERGEN ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_ALLERGEN ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_CLINIC_ALLERGEN ADD CHECK ( CLINICNO IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_CLINIC_ALLERGEN ADD CHECK ( ALLERNAME IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_CLINIC_ALLERGEN ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_ALLERGEN ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_ALLERGEN ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_CLINIC_ALLERGEN ADD CHECK ( CLINICNO IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_CLINIC_ALLERGEN ADD CHECK ( ALLERNAME IS NOT
		// NULL);"
		// + "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( IID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( CLINICNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( PRESCNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( COSTTYPE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( COSTTIME IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( IS_USE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( IID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( CLINICNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( PRESCNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( COSTTYPE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( COSTTIME IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_COST ADD CHECK ( IS_USE IS NOT NULL);"
		// + "ALTER TABLE T_MC_CLINIC_DISEASE ADD CHECK ( HISCODE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_CLINIC_DISEASE ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_DISEASE ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_CLINIC_DISEASE ADD CHECK ( CLINICNO IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_DISEASE ADD CHECK ( DISNAME IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_DISEASE ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_DISEASE ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_DISEASE ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_CLINIC_DISEASE ADD CHECK ( CLINICNO IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_DISEASE ADD CHECK ( DISNAME IS NOT NULL);"
		// + "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( CLINICNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( EXAMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( EXAMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( REPORTTIME IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( CLINICNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( EXAMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( EXAMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_EXAM ADD CHECK ( REPORTTIME IS NOT NULL);"
		// + "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( CLINICNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( LABCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( LABNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( REPORTTIME IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( CLINICNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( LABCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( LABNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_LAB ADD CHECK ( REPORTTIME IS NOT NULL);"+
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( CID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( PATIENTID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( CLINICNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( PRESCNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( ORDERTYPE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( DRUG_UNIQUE_CODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( ORDERCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( ORDERNAME IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( CID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( PATIENTID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( CLINICNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( PRESCNO IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( ORDERTYPE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( DRUG_UNIQUE_CODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( ORDERCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( ORDERNAME IS NOT NULL); "
		// +
		//// "ALTER TABLE T_MC_CLINIC_ORDER ADD CHECK ( STARTDATETIME IS NOT
		// NULL);"+
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( CLINICNO IS NOT NULL); "
		// +
		//// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( PATIENTNAME IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( ENDDATE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( IS_EMERGENCY IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( CLINICNO IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( ENDDATE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_CLINIC_PATIENT ADD CHECK ( IS_EMERGENCY IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ALLERGEN ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ALLERGEN ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ALLERGEN ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_ALLERGEN ADD CHECK ( VISITID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ALLERGEN ADD CHECK ( ALLERNAME IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_ALLERGEN ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ALLERGEN ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ALLERGEN ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_ALLERGEN ADD CHECK ( VISITID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ALLERGEN ADD CHECK ( ALLERNAME IS NOT
		// NULL);"
		// + "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( IID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( COSTTYPE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( COST IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( COSTTIME IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( IS_OUT IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( IS_USE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( IID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( COSTTYPE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( COST IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( COSTTIME IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( IS_OUT IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_COST ADD CHECK ( IS_USE IS NOT NULL);"
		// + "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( HISCODE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( VISITID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( DISNAME IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( DISEASETYPE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( IS_MAIN IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( VISITID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( DISNAME IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( DISEASETYPE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_DISEASE ADD CHECK ( IS_MAIN IS NOT NULL);"
		// + "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( EXAMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( EXAMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( REPORTTIME IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( EXAMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( EXAMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_EXAM ADD CHECK ( REPORTTIME IS NOT NULL);"
		// + "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( LABCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( LABNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( REPORTTIME IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( LABCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( LABNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_LAB ADD CHECK ( REPORTTIME IS NOT NULL);"
		// + "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( HISCODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( OPRID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( PATIENTID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( VISITID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( OPERATIONNAME IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( HISCODE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( OPRID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( PATIENTID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( VISITID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_OPERATION ADD CHECK ( OPERATIONNAME IS NOT
		// NULL);"
		// + "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( CID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( PATIENTID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( ORDERTYPE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( IS_TEMP IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( DRUG_UNIQUE_CODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( ORDERCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( ORDERNAME IS NOT NULL); "
		// +
		//// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( STARTDATETIME IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( IS_OUT IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( CID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( PATIENTID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( ORDERTYPE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( IS_TEMP IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( DRUG_UNIQUE_CODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( ORDERCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( ORDERNAME IS NOT NULL); "
		// +
		//// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( STARTDATETIME IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_ORDER ADD CHECK ( IS_OUT IS NOT NULL);"
		// + "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( HISCODE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( VISITID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( HOSPITALNO IS NOT NULL);
		// " +
		//// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( PATIENTNAME IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( STARTDATE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( VISITID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( HOSPITALNO IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_PATIENT ADD CHECK ( STARTDATE IS NOT NULL);"
		// + "ALTER TABLE T_MC_INHOSP_TEMPERATURE ADD CHECK ( HISCODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_TEMPERATURE ADD CHECK ( CASEID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_TEMPERATURE ADD CHECK ( PATIENTID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_TEMPERATURE ADD CHECK ( VISITID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_TEMPERATURE ADD CHECK ( HISCODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_TEMPERATURE ADD CHECK ( CASEID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_INHOSP_TEMPERATURE ADD CHECK ( PATIENTID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_INHOSP_TEMPERATURE ADD CHECK ( VISITID IS NOT
		// NULL);"
		// + "ALTER TABLE T_MC_OUTHOSP_ALLERGEN ADD CHECK ( HISCODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ALLERGEN ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_ALLERGEN ADD CHECK ( PATIENTID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ALLERGEN ADD CHECK ( VISITID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_ALLERGEN ADD CHECK ( ALLERNAME IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ALLERGEN ADD CHECK ( HISCODE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_ALLERGEN ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_ALLERGEN ADD CHECK ( PATIENTID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ALLERGEN ADD CHECK ( VISITID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_ALLERGEN ADD CHECK ( ALLERNAME IS NOT
		// NULL);"
		// + "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( IID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( PATIENTID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( COSTTYPE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( COST IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( COSTTIME IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( IS_OUT IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( IS_USE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( IID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( PATIENTID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( COSTTYPE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( COST IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( COSTTIME IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( IS_OUT IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_COST ADD CHECK ( IS_USE IS NOT NULL);"
		// + "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( HISCODE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( VISITID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( DISNAME IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( DISEASETYPE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( VISITID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( DISNAME IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_DISEASE ADD CHECK ( DISEASETYPE IS NOT
		// NULL);"
		// + "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( PATIENTID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( EXAMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( EXAMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( REPORTTIME IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( PATIENTID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( EXAMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( EXAMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_EXAM ADD CHECK ( REPORTTIME IS NOT NULL);"
		// + "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( LABCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( LABNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( REPORTTIME IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( PATIENTID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( LABCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( LABNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( ITEMCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( ITEMNAME IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_LAB ADD CHECK ( REPORTTIME IS NOT NULL);"
		// + "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( HISCODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( OPRID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( CASEID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( PATIENTID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( VISITID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( OPERATIONNAME IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( HISCODE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( OPRID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( CASEID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( PATIENTID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( VISITID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_OPERATION ADD CHECK ( OPERATIONNAME IS NOT
		// NULL);"
		// + "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( CID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( PATIENTID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( ORDERTYPE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( IS_TEMP IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( DRUG_UNIQUE_CODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( ORDERCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( ORDERNAME IS NOT NULL); "
		// +
		//// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( STARTDATETIME IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( IS_OUT IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( HISCODE IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( CID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( CASEID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( PATIENTID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( VISITID IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( ORDERTYPE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( IS_TEMP IS NOT NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( DRUG_UNIQUE_CODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( ORDERCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( ORDERNAME IS NOT NULL); "
		// +
		//// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( STARTDATETIME IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_ORDER ADD CHECK ( IS_OUT IS NOT NULL);"
		// + "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( HISCODE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( VISITID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( HOSPITALNO IS NOT
		// NULL); " +
		//// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( PATIENTNAME IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( STARTDATE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( ENDDATE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( HISCODE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( CASEID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( PATIENTID IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( VISITID IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( HOSPITALNO IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( STARTDATE IS NOT NULL);
		// " +
		// "ALTER TABLE T_MC_OUTHOSP_PATIENT ADD CHECK ( ENDDATE IS NOT NULL); "
		// +
		// "ALTER TABLE T_MC_OUTHOSP_TEMPERATURE ADD CHECK ( HISCODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_TEMPERATURE ADD CHECK ( CASEID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_TEMPERATURE ADD CHECK ( PATIENTID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_TEMPERATURE ADD CHECK ( VISITID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_TEMPERATURE ADD CHECK ( HISCODE IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_TEMPERATURE ADD CHECK ( CASEID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_TEMPERATURE ADD CHECK ( PATIENTID IS NOT
		// NULL); " +
		// "ALTER TABLE T_MC_OUTHOSP_TEMPERATURE ADD CHECK ( VISITID IS NOT
		// NULL);";
		//
		// String[] sqls=sql.split(";");
		// for(int i=0;i<sqls.length;i++){
		// String sqlsone=sqls[i];
		// jdbcTemplate_oracle.update(sqlsone);
		// }
		// System.out.println("修改所有表属性结束");

		System.out.println("创建HIS库所有表结束");
	}

	public void cleardict() {
		String sql = null;

		// 制造t_mc_clinic_allergen数据

		System.out.println("开始清理字典表数据");
		sql = "truncate table mc_dict_allergen";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_costitem";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_dept";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_disease";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_doctor";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_drug";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_drug_sub";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_drug_pass";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_exam";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_frequency";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_labsub";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_lab";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_operation";
		jdbcTemplate_oracle.update(sql);

		sql = "truncate table mc_dict_route";
		jdbcTemplate_oracle.update(sql);
		
		System.out.println("===字典表清理结束===");
	}
}
