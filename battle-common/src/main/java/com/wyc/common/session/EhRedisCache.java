package com.wyc.common.session;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;


import net.sf.ehcache.Element;

public class EhRedisCache implements Cache{
	 private static final Logger LOG = LoggerFactory.getLogger(EhRedisCache.class);

	    private String name;

	    private net.sf.ehcache.Cache ehCache;

	    private RedisTemplate<String, Object> redisTemplate;

	     private long liveTime = 1*60*60; //默认1h=1*60*60

	    @Override
	    public String getName() {
	        return this.name;
	    }

	    @Override
	    public Object getNativeCache() {
	        return this;
	    }

	    @Override
	    public ValueWrapper get(Object key) {
	    	
	    	System.out.println("................get");
	         Element value = ehCache.get(key);
	         LOG.info("Cache L1 (ehcache) :{}={}",key,value);
	         if (value!=null) {
	             return (value != null ? new SimpleValueWrapper(value.getObjectValue()) : null);
	         } 
	         //TODO 这样会不会更好？访问10次EhCache 强制访问一次redis 使得数据不失效
	         final String keyStr = key.toString();  
	         Object objectValue = redisTemplate.execute(new RedisCallback<Object>() {  
	            public Object doInRedis(org.springframework.data.redis.connection.RedisConnection connection)  
	                    throws DataAccessException {  
	                byte[] key = keyStr.getBytes();  
	                byte[] value = (byte[])connection.get(key);  
	                if (value == null) {  
	                    return null;  
	                }  
	                //每次获得，重置缓存过期时间
	                if (liveTime > 0) {  
	                    connection.expire(key, liveTime);  
	                }  
	                return toObject(value);  
	            }
	        },true);  
	         ehCache.put(new Element(key, objectValue));//取出来之后缓存到本地
	         LOG.info("Cache L2 (redis) :{}={}",key,objectValue);
	         return  (objectValue != null ? new SimpleValueWrapper(objectValue) : null);

	    }

	    @Override
	    public void put(Object key, Object value) {
	    	
	    	System.out.println("................put");
	        ehCache.put(new Element(key, value));
	        final String keyStr =  key.toString(); 
	        final Object valueStr = value;  
	        redisTemplate.execute(new RedisCallback<Long>() {  
	            public Long doInRedis(RedisConnection connection)  
	                    throws DataAccessException {  
	                byte[] keyb = keyStr.getBytes();  
	                byte[] valueb = toByteArray(valueStr);  
	                connection.set(keyb, valueb);  
	                if (liveTime > 0) {  
	                    connection.expire(keyb, liveTime);  
	                }  
	                return 1L;  
	            }  
	        },true);  

	    }

	    @Override
	    public void evict(Object key) {
	    	System.out.println("................evvict");
	        ehCache.remove(key);
	        final String keyStr =  key.toString();  
	        redisTemplate.execute(new RedisCallback<Long>() {  
	            public Long doInRedis(RedisConnection connection)  
	                    throws DataAccessException {  
	                return connection.del(keyStr.getBytes());  
	            }  
	        },true); 
	    }

	    @Override
	    public void clear() {
	    	System.out.println("................clear");
	        ehCache.removeAll();
	        redisTemplate.execute(new RedisCallback<String>() {  
	            public String doInRedis(RedisConnection connection)  
	                    throws DataAccessException {  
	                connection.flushDb();  
	                return "clear done.";  
	            }  
	        },true);
	    }

	    public net.sf.ehcache.Cache getEhCache() {
	    	System.out.println("................getEhCache");
	        return ehCache;
	    }

	    public void setEhCache(net.sf.ehcache.Cache ehCache) {
	    	System.out.println("................setEhCache");
	        this.ehCache = ehCache;
	    }

	    public RedisTemplate<String, Object> getRedisTemplate() {
	    	System.out.println("................getRedisTemplate");
	        return redisTemplate;
	    }

	    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
	    	System.out.println("................setRedisTemplate");
	        this.redisTemplate = redisTemplate;
	    }

	    public long getLiveTime() {
	    	System.out.println("................getLiveTime");
	        return liveTime;
	    }

	    public void setLiveTime(long liveTime) {
	    	System.out.println("................setLiveTime");
	        this.liveTime = liveTime;
	    }

	    public void setName(String name) {
	    	System.out.println("................setName");
	        this.name = name;
	    }
	    /** 
	     * 描述 : Object转byte[]. <br> 
	     * @param obj 
	     * @return 
	     */  
	    private byte[] toByteArray(Object obj) {
	    	System.out.println("................toByteArray");
	        byte[] bytes = null;  
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	        try {  
	            ObjectOutputStream oos = new ObjectOutputStream(bos);  
	            oos.writeObject(obj);  
	            oos.flush();  
	            bytes = bos.toByteArray();  
	            oos.close();  
	            bos.close();  
	        } catch (IOException ex) {  
	            ex.printStackTrace();  
	        }  
	        return bytes;  
	    }  

	    /** 
	     * 描述 :  byte[]转Object . <br> 
	     * @param bytes 
	     * @return 
	     */  
	    private Object toObject(byte[] bytes) {  
	    	System.out.println("................toObject");
	        Object obj = null;  
	        try {  
	            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);  
	            ObjectInputStream ois = new ObjectInputStream(bis);  
	            obj = ois.readObject();  
	            ois.close();  
	            bis.close();  
	        } catch (IOException ex) {  
	            ex.printStackTrace();  
	        } catch (ClassNotFoundException ex) {  
	            ex.printStackTrace();  
	        }  
	        return obj;  
	    }

		@Override
		public <T> T get(Object key, Class<T> type) {
			System.out.println("................get");
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ValueWrapper putIfAbsent(Object key, Object value) {
			System.out.println("................putIfAbsent");
			// TODO Auto-generated method stub
			return null;
		}  
}
