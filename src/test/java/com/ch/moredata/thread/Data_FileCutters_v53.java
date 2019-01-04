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
import com.ch.moredata.pass.v53.Sacl_pat_allergen;
import com.ch.moredata.pass.v53.Sacl_pat_disease;
import com.ch.moredata.pass.v53.Sacl_pat_drugorder;
import com.ch.moredata.pass.v53.Sacl_pat_exam;
import com.ch.moredata.pass.v53.Sacl_pat_info;
import com.ch.moredata.pass.v53.Sacl_pat_lab;
import com.ch.moredata.pass.v53.Sacl_pat_operation;
import com.ch.moredata.pass.v53.Sacl_screenresult;

@Component
public class Data_FileCutters_v53 {
	private static Logger log = Logger.getLogger(Data_FileCutters_v53.class);
	
	@Autowired
	Sacl_pat_allergen sacl_pat_allergen;
	@Autowired
	Sacl_pat_disease sacl_pat_disease;
	@Autowired
	Sacl_pat_drugorder sacl_pat_drugorder;
	@Autowired
	Sacl_pat_exam sacl_pat_exam;
	@Autowired
	Sacl_pat_info sacl_pat_info;
	@Autowired
	Sacl_pat_lab sacl_pat_lab;
	@Autowired
	Sacl_pat_operation sacl_pat_operation;
	@Autowired
	Sacl_screenresult sacl_screenresult;
	
    @Autowired
    private TaskExecutor taskExecutor;
	public static Map<String, Integer> threadMap=new HashMap();//线程状态 0启动，1是关闭
	
    public void filesMng(String threadName,String date,int datecount) {
    	//执行线程
        this.taskExecutor.execute(new CutFilesThread(threadName,date,datecount));
        //1，设置线程状态，0运行，1停止,
        //2，MAP里面放线程名，然后后面线程结束了就删除MAP的线程名，总开关判断MAP为空，则没有线程运行，如果MAP不为空，则等待全部线程结束（采用这个模式）
        threadMap.put(threadName, 0);
    }
    private class CutFilesThread implements Runnable {
        private String threadName;String date;int datecount;
        
        private CutFilesThread(String threadName,String date,int datecount) {
            super();
            this.threadName = threadName;
            this.date = date;
            this.datecount = datecount;
        }
        @Override
        public void run() {
        	if(threadName=="Sacl_pat_allergen"){
        		sacl_pat_allergen.pat_allergen(date, datecount);
        	}
        	if(threadName=="Sacl_pat_disease"){
        		sacl_pat_disease.pat_disease(date, datecount);
        	}
        	if(threadName=="Sacl_pat_drugorder"){
        		sacl_pat_drugorder.pat_drugorder(date, datecount);
        	}
        	if(threadName=="Sacl_pat_exam"){
        		sacl_pat_exam.pat_exam(date, datecount);
        	}
        	if(threadName=="Sacl_pat_info"){
        		sacl_pat_info.pat_info(date, datecount);
        	}
        	if(threadName=="Sacl_pat_lab"){
        		sacl_pat_lab.pat_lab(date, datecount);
        	}
        	if(threadName=="Sacl_pat_operation"){
        		sacl_pat_operation.pat_operation(date, datecount);
        	}
        	if(threadName=="Sacl_screenresult"){
        		sacl_screenresult._screenresult(date, datecount);
        	}
        	System.out.println(threadName+"：线程执行结束！！！");
        	threadMap.remove(threadName);
        }
    }
}