package com.ch.redis.v53;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/*
 * Redis操作
 */
@Service
public class Redis_machine {
	@Autowired
	RedisTemplate redisTemplateObject;
	
	public Object _get(String tablename,String key){
		return redisTemplateObject.opsForHash().get(tablename,key);
	}
	public void _set(String tablename,String key,Object obj){
		redisTemplateObject.opsForHash().put(tablename, key, obj);
	}
	public void _remove(String tablename,String key){
		redisTemplateObject.opsForHash().delete(tablename, key);
	}
	
	public void removerTable(String tablename){
		redisTemplateObject.delete(tablename);
		
	}
}
