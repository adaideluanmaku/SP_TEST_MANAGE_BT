package com.ch.progress;

import java.util.List;  

import javax.servlet.http.HttpServletRequest;  
  
import org.apache.commons.fileupload.FileItem;  
import org.apache.commons.fileupload.FileUpload;  
import org.apache.commons.fileupload.FileUploadBase;  
import org.apache.commons.fileupload.FileUploadException;  
import org.apache.commons.fileupload.servlet.ServletFileUpload;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.web.multipart.MaxUploadSizeExceededException;  
import org.springframework.web.multipart.MultipartException;  
import org.springframework.web.multipart.commons.CommonsMultipartResolver;  
  
/**
 * 通过Spring配置指定到这个class
 * 启动文件上传下载中，数据传输监听器
 * @author 陈辉
 *
 */
public class CustomMultipartResolver extends CommonsMultipartResolver {  
      
    @Autowired    
    private FileListenner fileListenner;    
        
    public void setFileListenner(FileListenner fileListenner1){    
        this.fileListenner=fileListenner1;    
    }    
        
    @Override    
    public MultipartParsingResult parseRequest(HttpServletRequest request)    
            throws MultipartException {    
        String encoding = determineEncoding(request);    
        FileUpload fileUpload = prepareFileUpload(encoding); 
        
        //调用文件监听器
        fileListenner.setSession(request.getSession()); 
        //开始文件监听
        fileUpload.setProgressListener(fileListenner);    
        try {    
        	//循环读取文件上传下载进度，并调用FileListenner的update方法保存
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);    
            return parseFileItems(fileItems, encoding);    
        }    
        catch (FileUploadBase.SizeLimitExceededException ex) {    
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);    
        }    
        catch (FileUploadException ex) {    
            throw new MultipartException("Could not parse multipart servlet request", ex);    
        }    
    }    
   
}  