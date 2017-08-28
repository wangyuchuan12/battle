package com.wyc.common.smart.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import com.wyc.common.util.CommonUtil;
import com.wyc.common.util.ObjectsTranscoder;

import redis.clients.jedis.Jedis;

@Service
public class RedisService {
	private static final Class<Serializable> T = null;
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;
	private Jedis getJedis(){
		
		return jedisConnectionFactory.getShardInfo().createResource();
	}
	
	public void del(byte[] key){
		this.getJedis().del(key);
	}
	
	public void del(String key){
		this.getJedis().del(key);
	}
	
	public void set(byte[]key,byte[]value,int liveTime){
		this.set(key, value);
		this.getJedis().expire(key, liveTime);
	}
	
	public void setObject(String key,Object obj)throws Exception{
		 String value = ObjectsTranscoder.serializationObject(obj);
		 this.set(key, value);
	}
	
	public <T> T getObject(String key ,Class<T> type){
		String value = this.get(key);
		if(CommonUtil.isEmpty(value)){
			return null;
		}
		T t = ObjectsTranscoder.deserializationObject(value,type);
		return t;
	}
	
	public <T> void set(String key , Map<String, T> map,Class<T> clazz){
		 String value = ObjectsTranscoder.serializationMap(map, clazz);
		 this.set(key, value);
	}
	
	public <T> void set(String key , List<T> list,Class<T> clazz){
		 String value = ObjectsTranscoder.serializationList(list, clazz);
		 this.set(key, value);
	}
	
	public List getList(String key){
		String value = this.get(key);
		return ObjectsTranscoder.deserializationList(value, List.class);
	}
	
	public Map getMap(String key){
		String value = this.get(key);
		return ObjectsTranscoder.deserializationMap(value, Map.class);
	}
	
	public <T> T get(String key , Class<T> type){
		byte[] b = this.get(key.getBytes());
	//	return (T) ObjectsTranscoder.deserialize(b, type);
		return null;
	}
	
	
	public void set(String key,String value,int liveTime){
		this.set(key, value);
		this.getJedis().expire(key, liveTime);
	}
	
	public void set(String key,String value){
		this.getJedis().set(key, value);
	}
	
	public void set(byte[] key,byte[] value){
		try{
			this.getJedis().set(key, value);
		}catch(Exception e){
			
		}
		
	}
	
	public String get(String key){
		try{
			String value = this.getJedis().get(key);
			return value;
		}catch(Exception e){
			
		}
		
		return null;
		
	}
	
	public byte[] get(byte[]key){
		return this.getJedis().get(key);
	}
	
	//通过正则表达式匹配keys
	public Set<String> keys(String pattern){
		return this.getJedis().keys(pattern);
	}
	
	//检查key是否已经存在
	public boolean exists(String key){
		return this.getJedis().exists(key);
	}
	
	//清空redis数据
	public String flushDB(){
		return this.getJedis().flushDB();
	}
	
	//查看redis里有多少数据
	public long dbSize(){
		return this.getJedis().dbSize();
	}
	
	//检查是否连接成功
	public String ping(){
		return this.getJedis().ping();
	}
	
}
