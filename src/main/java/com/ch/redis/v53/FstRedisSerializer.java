package com.ch.redis.v53;


import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * 
 * <ul>
 * <li>项目名称：passrh_dao </li>
 * <li>类名称：  FstRedisSerializer </li>
 * <li>类描述： 序列化反序列化的工具  </li>
 * <li>创建人：周应强 </li>
 * <li>创建时间：2018年9月17日 </li>
 * <li>修改备注：</li>
 * </ul>
 */
public class FstRedisSerializer implements RedisSerializer{

	private static Logger logger = LoggerFactory.getLogger(FstRedisSerializer.class);
	
	private static FSTConfiguration configuration = FSTConfiguration.createStructConfiguration();
	/**
	 * 序列化 的方法
	 */
	public byte[] serialize(Object obj) throws SerializationException {
		//return configuration.asByteArray(obj);
		ByteArrayOutputStream out = null;
		FSTObjectOutput fout = null;
		try {
			out = new ByteArrayOutputStream();
			fout = new FSTObjectOutput(out);
			fout.writeObject(obj);
			fout.flush();
			return out.toByteArray();
		} catch (IOException e) {
			logger.error("FST序列化出错：",e);
			return null;
		}finally {
			if(fout != null)
			try {
				fout.close();
			} catch (IOException e) {}
		}
	}

	/**
	 * 反序列化的方法
	 */
	public Object deserialize(byte[] bytes) throws SerializationException {
		//return configuration.asObject(sec);
		FSTObjectInput in = null;
		try {
			in = new FSTObjectInput(new ByteArrayInputStream(bytes));
			return in.readObject();
		} catch (Exception e) {
			logger.error("FST反序列化出错：",e);
			return null;
		} finally {
			if(in != null)
			try {
				in.close();
			} catch (IOException e) {}
		}
	}
	

}
