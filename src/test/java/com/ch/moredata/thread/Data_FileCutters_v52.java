package com.ch.moredata.thread;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.ch.moredata.pa.v52.Mc_clinic_allergen;
import com.ch.moredata.pa.v52.Mc_clinic_caseid_ienddate;
import com.ch.moredata.pa.v52.Mc_clinic_cost;
import com.ch.moredata.pa.v52.Mc_clinic_disease;
import com.ch.moredata.pa.v52.Mc_clinic_drugcost_caseid;
import com.ch.moredata.pa.v52.Mc_clinic_drugcost_costtime;
import com.ch.moredata.pa.v52.Mc_clinic_drugorder_detail;
import com.ch.moredata.pa.v52.Mc_clinic_drugorder_main;
import com.ch.moredata.pa.v52.Mc_clinic_exam;
import com.ch.moredata.pa.v52.Mc_clinic_lab;
import com.ch.moredata.pa.v52.Mc_clinic_operation;
import com.ch.moredata.pa.v52.Mc_clinic_patient_baseinfo;
import com.ch.moredata.pa.v52.Mc_clinic_patient_medinfo;
import com.ch.moredata.pa.v52.Mc_clinic_prescription;
import com.ch.moredata.pa.v52.Mc_hosp_dept_hosday;
import com.ch.moredata.pa.v52.Mc_indtmp_syqd;
import com.ch.moredata.pa.v52.Mc_outhosp_allergen;
import com.ch.moredata.pa.v52.Mc_outhosp_caseid_ienddate;
import com.ch.moredata.pa.v52.Mc_outhosp_cost;
import com.ch.moredata.pa.v52.Mc_outhosp_disease;
import com.ch.moredata.pa.v52.Mc_outhosp_drugcost_caseid;
import com.ch.moredata.pa.v52.Mc_outhosp_drugcost_costtime;
import com.ch.moredata.pa.v52.Mc_outhosp_drugcostdistinct;
import com.ch.moredata.pa.v52.Mc_outhosp_drugorder_detail;
import com.ch.moredata.pa.v52.Mc_outhosp_drugorder_main;
import com.ch.moredata.pa.v52.Mc_outhosp_exam;
import com.ch.moredata.pa.v52.Mc_outhosp_lab;
import com.ch.moredata.pa.v52.Mc_outhosp_operation;
import com.ch.moredata.pa.v52.Mc_outhosp_order;
import com.ch.moredata.pa.v52.Mc_outhosp_patient_baseinfo;
import com.ch.moredata.pa.v52.Mc_outhosp_patient_medinfo;
import com.ch.moredata.pa.v52.Mc_outhosp_temperature;
import com.ch.moredata.pa.v52.Pharm_screenresults;
import com.ch.moredata.pa.v52.Tmp_drugcasid;
import com.ch.moredata.pa.v52.Tmp_indtmp_clinicpres;
import com.ch.moredata.pa.v52.Tmp_indtmp_clinicpt;
import com.ch.moredata.pa.v52.Tmp_indtmp_cost;
import com.ch.moredata.pa.v52.Tmp_indtmp_costpt;
import com.ch.moredata.pa.v52.Tmp_indtmp_num;
import com.ch.moredata.pa.v52.Tmp_indtmp_numpt;
import com.ch.moredata.pa.v52.Tmp_outtmp_hosppt;
import com.ch.moredata.pa.v52.Tmp_outtmp_hospptcost;
import com.ch.moredata.pa.v52.Tmp_outtmp_hospptnum;
import com.ch.moredata.pa.v52.Tmp_report_drugnum;
import com.ch.moredata.pa.v52.Tmp_report_hosp_drug_cost;

@Component
public class Data_FileCutters_v52 {
	private static Logger log = Logger.getLogger(Data_FileCutters_v52.class);
	
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
	@Autowired
	Mc_hosp_dept_hosday mc_hosp_dept_hosday;
	@Autowired
	Pharm_screenresults pharm_screenresults;
    @Autowired
    private TaskExecutor taskExecutor;
	public static Map<String, Integer> threadMap=new HashMap();//线程状态 0启动，1是关闭
	
    public void filesMng(String threadName,String date,int datecount,String yuan_date,String databasename) {
    	//执行线程
        this.taskExecutor.execute(new CutFilesThread(threadName,date,datecount,yuan_date,databasename));
        //1，设置线程状态，0运行，1停止,
        //2，MAP里面放线程名，然后后面线程结束了就删除MAP的线程名，总开关判断MAP为空，则没有线程运行，如果MAP不为空，则等待全部线程结束（采用这个模式）
        threadMap.put(threadName, 0);
    }
    private class CutFilesThread implements Runnable {
        private String threadName;String date;int datecount;String yuan_date;String databasename;
        
        private CutFilesThread(String threadName,String date,int datecount,String yuan_date,String databasename) {
            super();
            this.threadName = threadName;
            this.date = date;
            this.datecount = datecount;
            this.yuan_date = yuan_date;
            this.databasename = databasename;
        }
        @Override
        public void run() {
        	if(threadName=="Mc_clinic_allergen"){
        		mc_clinic_allergen.clinic_allergen(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_caseid_ienddate"){
        		mc_clinic_caseid_ienddate.clinic_caseid_ienddate(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_cost"){
        		mc_clinic_cost.clinic_cost(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_disease"){
        		mc_clinic_disease.clinic_disease(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_drugcost_caseid"){
        		mc_clinic_drugcost_caseid.clinic_drugcost_caseid(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_drugcost_costtime"){
        		mc_clinic_drugcost_costtime.clinic_drugcost_costtime(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_drugorder_detail"){
        		mc_clinic_drugorder_detail.clinic_drugorder_detail(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_drugorder_main"){
        		mc_clinic_drugorder_main.clinic_drugorder_main(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_exam"){
        		mc_clinic_exam.clinic_exam(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_lab"){
        		mc_clinic_lab.clinic_lab(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_operation"){
        		mc_clinic_operation.clinic_operation(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_patient_baseinfo"){
        		mc_clinic_patient_baseinfo.clinic_patient_baseinfo(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_patient_medinfo"){
        		mc_clinic_patient_medinfo.clinic_patient_medinfo(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_clinic_prescription"){
        		mc_clinic_prescription.clinic_prescription(date, datecount,yuan_date,databasename);
        	}
    		
        	if(threadName=="Mc_indtmp_syqd"){
        		mc_indtmp_syqd.indtmp_syqd(date, datecount,yuan_date,databasename);
        	}
    		
        	if(threadName=="Mc_outhosp_allergen"){
        		mc_outhosp_allergen.outhosp_allergen(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_caseid_ienddate"){
        		mc_outhosp_caseid_ienddate.outhosp_caseid_ienddate(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_cost"){
        		mc_outhosp_cost.outhosp_cost(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_disease"){
        		mc_outhosp_disease.outhosp_disease(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_drugcost_caseid"){
        		mc_outhosp_drugcost_caseid.outhosp_drugcost_caseid(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_drugcost_costtime"){
        		mc_outhosp_drugcost_costtime.outhosp_drugcost_costtime(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_drugcostdistinct"){
        		mc_outhosp_drugcostdistinct.outhosp_drugcostdistinct(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_drugorder_detail"){
        		mc_outhosp_drugorder_detail.outhosp_drugorder_detail(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_drugorder_main"){
        		mc_outhosp_drugorder_main.outhosp_drugorder_main(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_exam"){
        		mc_outhosp_exam.outhosp_exam(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_lab"){
        		mc_outhosp_lab.outhosp_lab(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_operation"){
        		mc_outhosp_operation.outhosp_operation(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_order"){
        		mc_outhosp_order.outhosp_order(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_patient_baseinfo"){
        		mc_outhosp_patient_baseinfo.outhosp_patient_baseinfo(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_patient_medinfo"){
        		mc_outhosp_patient_medinfo.outhosp_patient_medinfo(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Mc_outhosp_temperature"){
        		mc_outhosp_temperature.outhosp_temperature(date, datecount,yuan_date,databasename);
        	}
        	
        	if(threadName=="Tmp_drugcasid"){
        		tmp_drugcasid.drugcasid(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Tmp_indtmp_clinicpres"){
        		tmp_indtmp_clinicpres.indtmp_clinicpres(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Tmp_indtmp_clinicpt"){
        		tmp_indtmp_clinicpt.indtmp_clinicpt(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Tmp_indtmp_cost"){
        		tmp_indtmp_cost.indtmp_cost(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Tmp_indtmp_costpt"){
        		tmp_indtmp_costpt.indtmp_costpt(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Tmp_indtmp_num"){
        		tmp_indtmp_num.indtmp_num(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Tmp_indtmp_numpt"){
        		tmp_indtmp_numpt.indtmp_numpt(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Tmp_outtmp_hosppt"){
        		tmp_outtmp_hosppt.outtmp_hosppt(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Tmp_outtmp_hospptcost"){
        		tmp_outtmp_hospptcost.outtmp_hospptcost(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Tmp_outtmp_hospptnum"){
        		tmp_outtmp_hospptnum.outtmp_hospptnum(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Tmp_report_drugnum"){
        		tmp_report_drugnum.report_drugnum(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Tmp_report_hosp_drug_cost"){
        		tmp_report_hosp_drug_cost.report_hosp_drug_cost(date, datecount,yuan_date,databasename);
        	}
        	
        	if(threadName=="Mc_hosp_dept_hosday"){
        		mc_hosp_dept_hosday.hosp_dept_hosday(date, datecount,yuan_date,databasename);
        	}
        	if(threadName=="Pharm_screenresults"){
        		pharm_screenresults._screenresults(date, datecount,yuan_date,databasename);
        	}
//        	threadMap.put(threadName, 1);
        	threadMap.remove(threadName);
        }
    }
}