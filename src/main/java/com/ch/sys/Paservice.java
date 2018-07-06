package com.ch.sys;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

/**
 * <ul>
 * <li>�����ƣ�  Passservice </li>
 * <li>��������PASSWEB�ӿڵ��ã�httpclientģ����ýӿ�   </li>
 * <li>�����ˣ�</li>
 * <li>����ʱ�䣺2016��6��13�� </li>
 * <li>�޸ı�ע��</li>
 * </ul>
 */
@Service
public class Paservice {
	/**
	 * ���ó�ʱʱ��
	 */
	private static int WS_TIMEOUT = 10 * 1000;

	public boolean getPaResult(String jsonin,String url) throws TimeoutException, UnsupportedEncodingException {
		String jsonin1=null;
		if(url.contains("Mc_DoCommand")){
			//PASS-win-screen
			jsonin1="psJSONStr=PHARMSCREEN|"+URLEncoder.encode(jsonin, "UTF-8");
//			jsonin1="psJSONStr=PHARMSCREEN|"+jsonin;
		}else{
			//PASS-java-screen
//			jsonin1=URLEncoder.encode(jsonin, "UTF-8");
			jsonin1 = jsonin;
		}
				
		boolean result = false;
		try {
			//����һ���ͻ�������
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url);
			
			// ��������ʹ��䳬ʱʱ��
//			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(WS_TIMEOUT).setConnectTimeout(WS_TIMEOUT).build();
//			httppost.setConfig(requestConfig);
			
			//�ж��Ƿ���json���룬�������ֵ���PASS�ӿں�redis�ӿ�
			if(StringUtils.isNoneBlank(jsonin1)){
				// ������������,�����ַ����ȡ���http.UTF-8
				
//				StringEntity stringEntity = new StringEntity(jsonin1, ContentType.APPLICATION_FORM_URLENCODED);
				StringEntity stringEntity = new StringEntity(jsonin1,HTTP.UTF_8);
				
				stringEntity.setContentType("application/x-www-form-urlencoded");
//				stringEntity.setContentEncoding("UTF-8");
				httppost.setEntity(stringEntity);
			}else{
				StringEntity stringEntity = new StringEntity(HTTP.UTF_8);
				stringEntity.setContentType("application/x-www-form-urlencoded");
//				stringEntity.setContentEncoding("UTF-8");
				httppost.setEntity(stringEntity);
			}
			
			//���������������
			HttpResponse httpResponse = httpClient.execute(httppost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// ����Ӧ�Ľ���л�ȡ��Ӧ�������
				HttpEntity httpEntity = httpResponse.getEntity();
				String aa=EntityUtils.toString(httpEntity);
				System.out.println("接口结果："+aa);
				result = true;
//				EntityUtils.toString(httpEntity);
			}else{
				httpResponse.getStatusLine().toString();
				result = false;
				System.out.println(result);
//				httpResponse.getStatusLine().toString();
			}
//			System.out.println("PASS输入串："+jsonin);
//			System.out.println("PASS输出串："+result);
			httpClient.close();
		} 
//		catch (ConnectTimeoutException ex) {
//			throw new TimeoutException("���ӳ�ʱ");
//		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}
}
