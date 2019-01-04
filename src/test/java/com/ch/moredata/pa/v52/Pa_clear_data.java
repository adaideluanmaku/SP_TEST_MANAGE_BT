package com.ch.moredata.pa.v52;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.dao.SpringJdbc_mysql_pass;
import com.ch.pahis.Sys_pa;

@Service
public class Pa_clear_data {
	private static Logger log = Logger.getLogger(Pa_clear_data.class);
	
	JdbcTemplate jdbcTemplate_mysql;
	@Autowired
	SpringJdbc_mysql_pass springJdbc_mysql;
	@Autowired
	Sys_pa sys_pa;
	
	@Autowired
	Mc_clinic_allergen mc_clinic_allergen;
	@Autowired
	Mc_clinic_caseid_ienddate mc_clinic_caseid_ienddate;
	@Autowired
	Mc_clinic_cost mc_clinic_cost;
	@Autowired
	Mc_clinic_disease mc_clinic_disease;
	@Autowired
	Mc_clinic_drugcost_caseid mc_clinic_drugcost_caseid;
	@Autowired
	Mc_clinic_drugcost_costtime mc_clinic_drugcost_costtime;
	@Autowired
	Mc_clinic_drugorder_detail mc_clinic_drugorder_detail;
	@Autowired
	Mc_clinic_drugorder_main mc_clinic_drugorder_main;
	@Autowired
	Mc_clinic_exam mc_clinic_exam;
	@Autowired
	Mc_clinic_lab mc_clinic_lab;
	@Autowired
	Mc_clinic_operation mc_clinic_operation;
	@Autowired
	Mc_clinic_patient_baseinfo mc_clinic_patient_baseinfo;
	@Autowired
	Mc_clinic_patient_medinfo mc_clinic_patient_medinfo;
	@Autowired
	Mc_clinic_prescription mc_clinic_prescription;
	@Autowired
	Mc_indtmp_syqd mc_indtmp_syqd;
	@Autowired
	Mc_outhosp_allergen mc_outhosp_allergen;
	@Autowired
	Mc_outhosp_caseid_ienddate mc_outhosp_caseid_ienddate;
	@Autowired
	Mc_outhosp_cost mc_outhosp_cost;
	@Autowired
	Mc_outhosp_disease mc_outhosp_disease;
	@Autowired
	Mc_outhosp_drugcost_caseid mc_outhosp_drugcost_caseid;
	@Autowired
	Mc_outhosp_drugcost_costtime mc_outhosp_drugcost_costtime;
	@Autowired
	Mc_outhosp_drugcostdistinct mc_outhosp_drugcostdistinct;
	@Autowired
	Mc_outhosp_drugorder_detail mc_outhosp_drugorder_detail;
	@Autowired
	Mc_outhosp_drugorder_main mc_outhosp_drugorder_main;
	@Autowired
	Mc_outhosp_exam mc_outhosp_exam;
	@Autowired
	Mc_outhosp_lab mc_outhosp_lab;
	@Autowired
	Mc_outhosp_operation mc_outhosp_operation;
	@Autowired
	Mc_outhosp_order mc_outhosp_order;
	@Autowired
	Mc_outhosp_patient_baseinfo mc_outhosp_patient_baseinfo;
	@Autowired
	Mc_outhosp_patient_medinfo mc_outhosp_patient_medinfo;
	@Autowired
	Mc_outhosp_temperature mc_outhosp_temperature;
	@Autowired
	Tmp_drugcasid tmp_drugcasid;
	@Autowired
	Tmp_indtmp_clinicpres tmp_indtmp_clinicpres;
	@Autowired
	Tmp_indtmp_clinicpt tmp_indtmp_clinicpt;
	@Autowired
	Tmp_indtmp_cost tmp_indtmp_cost;
	@Autowired
	Tmp_indtmp_costpt tmp_indtmp_costpt;
	@Autowired
	Tmp_indtmp_num tmp_indtmp_num;
	@Autowired
	Tmp_indtmp_numpt tmp_indtmp_numpt;
	@Autowired
	Tmp_outtmp_hosppt tmp_outtmp_hosppt;
	@Autowired
	Tmp_outtmp_hospptcost tmp_outtmp_hospptcost;
	@Autowired
	Tmp_outtmp_hospptnum tmp_outtmp_hospptnum;
	@Autowired
	Tmp_report_drugnum tmp_report_drugnum;
	@Autowired
	Tmp_report_hosp_drug_cost tmp_report_hosp_drug_cost;
	
	public void clear(){
		jdbcTemplate_mysql=springJdbc_mysql.getJdbcTemplate();
		if(jdbcTemplate_mysql==null){
			log.info("数据库连接失败");
			return;
		}
		
		String sql="";
		sql="truncate Mc_clinic_allergen";
		jdbcTemplate_mysql.update(sql);
		
		sql="truncate Mc_clinic_caseid_ienddate ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_cost ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_disease ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_drugcost_caseid ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_drugcost_costtime ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_drugorder_detail ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_drugorder_main ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_exam ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_lab ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_operation ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_patient_baseinfo ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_patient_medinfo ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_clinic_prescription ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_indtmp_syqd ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_allergen ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_caseid_ienddate ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_cost ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_disease ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_drugcost_caseid ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_drugcost_costtime ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_drugcostdistinct ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_drugorder_detail ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_drugorder_main ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_exam ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_lab ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_operation ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_order ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_patient_baseinfo ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_patient_medinfo ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Mc_outhosp_temperature ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_drugcasid ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_indtmp_clinicpres ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_indtmp_clinicpt ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_indtmp_cost ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_indtmp_costpt ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_indtmp_num ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_indtmp_numpt ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_outtmp_hosppt ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_outtmp_hospptcost ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_outtmp_hospptnum ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_report_drugnum ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Tmp_report_hosp_drug_cost ";
		jdbcTemplate_mysql.update(sql );
		
		sql="delete from Mc_hosp_dept_hosday where ienddate<>19000101";
		jdbcTemplate_mysql.update(sql );
		
	}
}
