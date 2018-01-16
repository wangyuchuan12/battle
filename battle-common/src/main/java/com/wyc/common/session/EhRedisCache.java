package com.wyc.common.session;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.lambdaworks.redis.RedisConnection;
import com.wyc.common.config.AppConfig;

public class EhRedisCache implements Cache{
	final static Logger logger = LoggerFactory.getLogger(EhRedisCache.class);

    private String name;


    private RedisTemplate<String, Object> redisTemplate;

     private long liveTime = 1*60*60; //默认1h=1*60*60
     
    @Override
    public String getName() {
    	System.out.println("........getName");
        return this.name;
    }

    @Override
    public Object getNativeCache() {
    	System.out.println("........getNativeCache");
        return this;
    }

    @Override
    public ValueWrapper get(Object key) {
        System.out.println("........get");
    	return null;
    }

    @Override
    public void put(Object key, Object value) {
    	System.out.println("........put");
    }

    @Override
    public void evict(Object key) {
    	System.out.println("........evict");
    }

    @Override
    public void clear() {
    	System.out.println("........clear");
    }

	@Override
	public <T> T get(Object key, Class<T> type) {
		System.out.println("get2");
		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		System.out.println("putIfAbsent");
		return null;
	}
}
