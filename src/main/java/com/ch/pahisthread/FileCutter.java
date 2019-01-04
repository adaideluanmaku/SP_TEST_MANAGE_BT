package com.ch.pahisthread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.ch.pahis.Mc_dict_allergen;
import com.ch.pahis.Mc_dict_costitem;
import com.ch.pahis.Mc_dict_dept;
import com.ch.pahis.Mc_dict_disease;
import com.ch.pahis.Mc_dict_doctor;
import com.ch.pahis.Mc_dict_drug;
import com.ch.pahis.Mc_dict_drug_pass;
import com.ch.pahis.Mc_dict_drug_sub;
import com.ch.pahis.Mc_dict_exam;
import com.ch.pahis.Mc_dict_frequency;
import com.ch.pahis.Mc_dict_lab;
import com.ch.pahis.Mc_dict_labsub;
import com.ch.pahis.Mc_dict_operation;
import com.ch.pahis.Mc_dict_route;
import com.ch.pahis.T_mc_clinic_allergen;
import com.ch.pahis.T_mc_clinic_cost;
import com.ch.pahis.T_mc_clinic_disease;
import com.ch.pahis.T_mc_clinic_exam;
import com.ch.pahis.T_mc_clinic_lab;
import com.ch.pahis.T_mc_clinic_order;
import com.ch.pahis.T_mc_clinic_patient;
import com.ch.pahis.T_mc_inhosp_allergen;
import com.ch.pahis.T_mc_inhosp_cost;
import com.ch.pahis.T_mc_inhosp_disease;
import com.ch.pahis.T_mc_inhosp_exam;
import com.ch.pahis.T_mc_inhosp_lab;
import com.ch.pahis.T_mc_inhosp_operation;
import com.ch.pahis.T_mc_inhosp_order;
import com.ch.pahis.T_mc_inhosp_patient;
import com.ch.pahis.T_mc_inhosp_temperature;
import com.ch.pahis.T_mc_outhosp_allergen;
import com.ch.pahis.T_mc_outhosp_cost;
import com.ch.pahis.T_mc_outhosp_disease;
import com.ch.pahis.T_mc_outhosp_exam;
import com.ch.pahis.T_mc_outhosp_lab;
import com.ch.pahis.T_mc_outhosp_operation;
import com.ch.pahis.T_mc_outhosp_order;
import com.ch.pahis.T_mc_outhosp_patient;
import com.ch.pahis.T_mc_outhosp_temperature;
import com.ch.passhis.Pass_T_mc_clinic_lab;
import com.ch.passhis.Pass_T_mc_inhosp_lab;
import com.ch.passhis.Pass_T_mc_outhosp_lab;

@Component
public class FileCutter {
	private static Logger log = Logger.getLogger(FileCutter.class);
    @Autowired
    private TaskExecutor taskExecutor;
    
	
	@Autowired
	T_mc_clinic_allergen t_mc_clinic_allergen;
	@Autowired
	T_mc_clinic_cost t_mc_clinic_cost;
	@Autowired
	T_mc_clinic_disease t_mc_clinic_disease;
	@Autowired
	T_mc_clinic_exam t_mc_clinic_exam;
	@Autowired
	T_mc_clinic_lab t_mc_clinic_lab;
	@Autowired
	T_mc_clinic_order t_mc_clinic_order;
	@Autowired
	T_mc_clinic_patient t_mc_clinic_patient;
	
	@Autowired
	T_mc_inhosp_allergen t_mc_inhosp_allergen;
	@Autowired
	T_mc_inhosp_cost t_mc_inhosp_cost;
	@Autowired
	T_mc_inhosp_disease t_mc_inhosp_disease;
	@Autowired
	T_mc_inhosp_exam t_mc_inhosp_exam;
	@Autowired
	T_mc_inhosp_lab t_mc_inhosp_lab;
	@Autowired
	T_mc_inhosp_operation t_mc_inhosp_operation;
	@Autowired
	T_mc_inhosp_order t_mc_inhosp_order;
	@Autowired
	T_mc_inhosp_patient t_mc_inhosp_patient;
	@Autowired
	T_mc_inhosp_temperature t_mc_inhosp_temperature;
	
	@Autowired
	T_mc_outhosp_allergen t_mc_outhosp_allergen;
	@Autowired
	T_mc_outhosp_cost t_mc_outhosp_cost;
	@Autowired
	T_mc_outhosp_disease t_mc_outhosp_disease;
	@Autowired
	T_mc_outhosp_exam t_mc_outhosp_exam;
	@Autowired
	T_mc_outhosp_lab t_mc_outhosp_lab;
	@Autowired
	T_mc_outhosp_operation t_mc_outhosp_operation;
	@Autowired
	T_mc_outhosp_order t_mc_outhosp_order;
	@Autowired
	T_mc_outhosp_patient t_mc_outhosp_patient;
	@Autowired
	T_mc_outhosp_temperature t_mc_outhosp_temperature;
	
	@Autowired
	Pass_T_mc_clinic_lab pass_t_mc_clinic_lab;
	@Autowired
	Pass_T_mc_inhosp_lab pass_t_mc_inhosp_lab;
	@Autowired
	Pass_T_mc_outhosp_lab pass_t_mc_outhosp_lab;
	
	public static Map<String, Integer> threadMap=new HashMap();//线程状态 0启动，1是关闭
    public void filesMng(String threadName,int trunca, int count, int sum_date,List anlilist,String hiscode,
    		String ienddate,String costtime,String startdate,int passorpa_hisdata1,String enddate,
    		int countzy,int countcy,int database,int inhosptiwen,int outhosptiwen) {
        this.taskExecutor.execute(new CutFilesThread(threadName,trunca, count, sum_date, anlilist, hiscode, 
        		ienddate,costtime,startdate,passorpa_hisdata1,enddate,countzy,countcy,database,inhosptiwen,
        		outhosptiwen));
        
        threadMap.put(threadName, 0);
    }
    private class CutFilesThread implements Runnable {
        private String threadName;int trunca; int count; int sum_date;List anlilist;String hiscode;String ienddate;
        String costtime;String startdate;int passorpa_hisdata1;String enddate;int countzy;int countcy;int database;
        int inhosptiwen;int outhosptiwen;
        
        private CutFilesThread(String threadName,int trunca, int count, int sum_date,List anlilist,
        		String hiscode,String ienddate,String costtime,String startdate,int passorpa_hisdata1,
        		String enddate,int countzy,int countcy,int database,int inhosptiwen,int outhosptiwen) {
            super();
            this.threadName = threadName;
            this.trunca = trunca;
            this.count = count;
            this.sum_date = sum_date;
            this.anlilist = anlilist;
            this.hiscode = hiscode;
            this.ienddate = ienddate;
            this.costtime = costtime;
            this.startdate = startdate;
            this.passorpa_hisdata1 = passorpa_hisdata1;
            this.enddate = enddate;
            this.countzy = countzy;
            this.countcy = countcy;
            this.database = database;
            this.inhosptiwen = inhosptiwen;
            this.outhosptiwen = outhosptiwen;
        }
        @Override
        public void run() {
        	//门诊
        	if(threadName=="t_mc_clinic_allergen"){
        		t_mc_clinic_allergen.clinic_allergen(trunca, count, sum_date, anlilist, hiscode, ienddate,database);
        		log.info("======>t_mc_clinic_allergen 导入结束");
        	}
        	if(threadName=="t_mc_clinic_cost"){
        		t_mc_clinic_cost.clinic_cost(trunca,count, sum_date,anlilist,hiscode,ienddate,costtime,database);
        		log.info("======>t_mc_clinic_cost 导入结束");
        	}
        	if(threadName=="t_mc_clinic_disease"){
        		t_mc_clinic_disease.clinic_disease(trunca, count, sum_date, anlilist, hiscode, ienddate,database);
        		log.info("======>t_mc_clinic_disease 导入结束");
        	}
        	if(threadName=="t_mc_clinic_exam"){
        		t_mc_clinic_exam.clinic_exam(trunca, count, sum_date, anlilist, hiscode, ienddate, startdate,database);
        		log.info("======>t_mc_clinic_exam 导入结束");
        	}
        	if(threadName=="t_mc_clinic_lab"){
        		if(passorpa_hisdata1==0){
    				t_mc_clinic_lab.clinic_lab(trunca, count, sum_date, anlilist, hiscode, ienddate, startdate,database);
    			}else{
    				pass_t_mc_clinic_lab.clinic_lab(trunca, count, sum_date, anlilist, hiscode, ienddate, startdate,database);
    			}
        		log.info("======>t_mc_clinic_lab 导入结束");
        	}
        	if(threadName=="t_mc_clinic_order"){
        		t_mc_clinic_order.clinic_order(trunca, count, sum_date, anlilist, hiscode, ienddate,database);
        		log.info("======>t_mc_clinic_order 导入结束");
        	}
        	if(threadName=="t_mc_clinic_patient"){
        		t_mc_clinic_patient.clinic_patient(trunca, count, sum_date, anlilist, hiscode, ienddate, enddate,database);
        		log.info("======>t_mc_clinic_patient 导入结束");
        	}
        	
        	//住院
        	if(threadName=="t_mc_inhosp_allergen"){
        		t_mc_inhosp_allergen.inhosp_allergen(trunca, countzy, sum_date, anlilist, hiscode, ienddate,database);
        		log.info("======>t_mc_inhosp_allergen 导入结束");
        	}
        	
        	if(threadName=="t_mc_inhosp_cost"){
        		t_mc_inhosp_cost.inhosp_cost(trunca, countzy, sum_date, anlilist, hiscode, ienddate, costtime,database);
        		log.info("======>t_mc_inhosp_cost 导入结束");
        	}
        	if(threadName=="t_mc_inhosp_disease"){
        		t_mc_inhosp_disease.inhosp_disease(trunca, countzy, sum_date, anlilist, hiscode, ienddate, costtime,database);
        		log.info("======>t_mc_inhosp_disease 导入结束");
        	}
        	if(threadName=="t_mc_inhosp_exam"){
        		t_mc_inhosp_exam.inhosp_exam(trunca, countzy, sum_date, anlilist, hiscode, ienddate, startdate,database);
        		log.info("======>t_mc_inhosp_exam 导入结束");
        	}
        	
        	if(threadName=="t_mc_inhosp_lab"){
        		if(passorpa_hisdata1==0){
    				t_mc_inhosp_lab.inhosp_lab(trunca, countzy, sum_date, anlilist, hiscode, ienddate, startdate,database);	
    			}else{
    				pass_t_mc_inhosp_lab.inhosp_lab(trunca, countzy, sum_date, anlilist, hiscode, ienddate, startdate,database);
    			}
        		log.info("======>t_mc_inhosp_lab 导入结束");
        	}
        	if(threadName=="t_mc_inhosp_operation"){
        		t_mc_inhosp_operation.inhosp_operation(trunca, countzy, sum_date, anlilist, hiscode, ienddate, enddate,database);
        		log.info("======>t_mc_inhosp_operation 导入结束");
        	}
        	if(threadName=="t_mc_inhosp_order"){
        		t_mc_inhosp_order.inhosp_order(trunca, countzy, sum_date, anlilist, hiscode, ienddate, startdate,database);
        		log.info("======>t_mc_inhosp_order 导入结束");
        	}
        	if(threadName=="t_mc_inhosp_patient"){
        		t_mc_inhosp_patient.inhosp_patient(trunca, countzy, sum_date, anlilist, hiscode, ienddate, enddate, startdate,database);
        		log.info("======>t_mc_inhosp_patient 导入结束");
        	}
        	if(threadName=="t_mc_inhosp_temperature"){
        		t_mc_inhosp_temperature.inhosp_temperature(trunca, countzy, sum_date, anlilist, hiscode, ienddate, startdate,database,inhosptiwen);
        		log.info("======>t_mc_inhosp_temperature 导入结束");
        	}
        	
        	//出院
        	if(threadName=="t_mc_outhosp_allergen"){
        		t_mc_outhosp_allergen.outhosp_allergen(trunca, countcy, sum_date, anlilist, hiscode, ienddate,database);
        		log.info("======>t_mc_outhosp_allergen 导入结束");
        	}
        	if(threadName=="t_mc_outhosp_cost"){
        		t_mc_outhosp_cost.outhosp_cost(trunca, countcy, sum_date, anlilist, hiscode, ienddate, costtime,database);
        		log.info("======>t_mc_outhosp_cost 导入结束");
        	}
        	if(threadName=="t_mc_outhosp_disease"){
        		t_mc_outhosp_disease.outhosp_disease(trunca, countcy, sum_date, anlilist, hiscode, ienddate,database);
        		log.info("======>t_mc_outhosp_disease 导入结束");
        	}
        	if(threadName=="t_mc_outhosp_exam"){
        		t_mc_outhosp_exam.outhosp_exam(trunca, countcy, sum_date, anlilist, hiscode, ienddate, startdate,database);
        		log.info("======>t_mc_outhosp_exam 导入结束");
        	}
        	if(threadName=="t_mc_outhosp_lab"){
        		if(passorpa_hisdata1==0){
    				t_mc_outhosp_lab.outhosp_lab(trunca, countcy, sum_date, anlilist, hiscode, ienddate, startdate,database);	
    			}else{
    				pass_t_mc_outhosp_lab.outhosp_lab(trunca, countcy, sum_date, anlilist, hiscode, ienddate, startdate,database);
    			}
        		log.info("======>t_mc_outhosp_lab 导入结束");
        	}
        	if(threadName=="t_mc_outhosp_operation"){
        		t_mc_outhosp_operation.outhosp_operation(trunca, countcy, sum_date, anlilist, hiscode, ienddate,enddate,database);
        		log.info("======>t_mc_outhosp_operation 导入结束");
        	}
        	if(threadName=="t_mc_outhosp_order"){
        		t_mc_outhosp_order.outhosp_order(trunca, countcy, sum_date, anlilist, hiscode, ienddate, startdate,database);
        		log.info("======>t_mc_outhosp_order 导入结束");
        	}
        	if(threadName=="t_mc_outhosp_patient"){
        		t_mc_outhosp_patient.outhosp_patient(trunca, countcy, sum_date, anlilist, hiscode, ienddate, enddate, startdate,database);
        		log.info("======>t_mc_outhosp_patient 导入结束");
        	}
        	if(threadName=="t_mc_outhosp_temperature"){
        		t_mc_outhosp_temperature.outhosp_temperature(trunca, countcy, sum_date, anlilist, hiscode, ienddate, startdate,database,outhosptiwen);
        		log.info("======>t_mc_outhosp_temperature 导入结束");
        	}
			
        	threadMap.put(threadName, 1);
        }
    }
}