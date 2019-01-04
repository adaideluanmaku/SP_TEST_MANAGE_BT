package com.ch.thread.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/*
 * 需要配置spring配置线程池
 */
@Component
public class FileCutter1 {
    
    @Autowired
    private TaskExecutor taskExecutor;
    public static Map<String, Integer> threadMap=new HashMap();//线程状态 0启动，1是关闭
    public void filesMng(String fileName) {
        this.taskExecutor.execute(new CutFilesThread(fileName));
        //1，设置线程状态，0运行，1停止,
        //2，MAP里面放线程名，然后后面线程结束了就删除MAP的线程名，总开关判断MAP为空，则没有线程运行，如果MAP不为空，则等待全部线程结束（采用这个模式）
        threadMap.put(fileName, 0);
    }
    private class CutFilesThread implements Runnable {
        private String fileName;
        private CutFilesThread(String fileName) {
            super();
            this.fileName = fileName;
        }
        @Override
        public void run() {
			if(fileName=="aa"){
				System.out.println("aaa======");
				for(int i=0;i<5;i++){
					System.out.println("aaa======"+i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(fileName=="bb"){
				System.out.println("bbb======");
				for(int i=0;i<2;i++){
					System.out.println("bbb======"+i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			threadMap.remove(fileName);
        }
    }
	public static Map threadMap() {
		// TODO Auto-generated method stub
		return null;
	}
}