package com.ch.sysuntils;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.stereotype.Service;

import com.ch.service.Prescriptionbean;

/**
 * 运行kettle脚本
 * @author 陈辉
 *
 */
@Service
public class KettleUtils {
	private static Logger log = Logger.getLogger(KettleUtils.class);
	/**
	 * 通过文件方式执行etl转换
	 * 
	 * @param filename 
	 */
	public boolean runTran(String filename) {
		boolean is_success = false;
		try {
			KettleEnvironment.init();
			EnvUtil.environmentInit();
			TransMeta transMeta = new TransMeta(filename);
			Trans trans = new Trans(transMeta);
			trans.execute(null); 
			trans.waitUntilFinished();
			
            if (trans.getErrors() > 0) {  
                System.out.println(trans.getErrors());
                throw new KettleException();
            } 
            is_success = true;
		} catch (KettleException e) {
			e.printStackTrace();
			System.out.println(e); 
			is_success = false;
			log.debug(e);
		}
		return is_success;
	}

	/**
	 * java 通过文件方式执行job
	 * 
	 * @param fileName
	 */
	public void runJob(String fileName) {  
		try {  
			KettleEnvironment.init();
			JobMeta jobMeta = new JobMeta(fileName, null);  
			Job job = new Job(null, jobMeta);
			job.start();  
			job.waitUntilFinished();  
			if (job.getErrors() > 0) {  
				System.out.println("decompress fail!");  
			}
			job.getResult();
		} catch (KettleException e) {  
			System.out.println(e);  
			log.debug(e);
		}  
	}  
	
	  /**  
     * 通过文件方式执行etl转换
     * @param params 
     * @param ktrPath 
     */  
    public boolean runTransfer(String[] params, String ktrPath) {  
    	boolean is_success = false;
    	Trans trans = null;  
        try {  
            KettleEnvironment.init();
            EnvUtil.environmentInit();  
            TransMeta transMeta = new TransMeta(ktrPath);  
            trans = new Trans(transMeta);  
              
            trans.execute(params);  
            trans.waitUntilFinished();  
            if (trans.getErrors() > 0) {  
                throw new Exception("脚本内部步骤错误数>0");  
            }  
            is_success = true;
        } catch (Exception e) {  
            e.printStackTrace(); 
            log.debug("脚本异常："+e);
        }  
        return is_success;
    }  
  
    /** 
     * java 通过文件方式执行job
     *  
     * @param jobname 
     * String fName= "D:\\kettle\\informix_to_am_4.ktr"; 
     */  
    public static void runJob(Map<String, String> map, String jobPath) {  
        try {  
            KettleEnvironment.init();  
            JobMeta jobMeta = new JobMeta(jobPath, null);  
            Job job = new Job(null, jobMeta);
            for(Entry<String, String> entry : map.entrySet()){
//    			System.out.println(entry.getKey());
    			job.setVariable(entry.getKey(), entry.getValue());  
    		}
            job.start();  
            job.waitUntilFinished();  	
            if (job.getErrors() > 0) {  
                throw new Exception(  
                        "There are errors during job exception!()");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            log.debug(e);
        }  
    }  

}
