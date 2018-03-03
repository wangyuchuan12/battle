package com.wyc.common.config;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.wyc.common.session.EhRedisCache;

import net.sf.ehcache.CacheManager;
import redis.clients.jedis.JedisPoolConfig;
//@EnableWebMvc
@Configuration
/*@ComponentScan(basePackages="com.wyc",
				excludeFilters={
					@Filter(type=FilterType.ASSIGNABLE_TYPE,value=AppConfig.class),
					@Filter(type=FilterType.ASSIGNABLE_TYPE,value=DatabaseConfig.class),
					@Filter(type=FilterType.ASSIGNABLE_TYPE,value=WebConfig2.class),
					@Filter(type=FilterType.ASSIGNABLE_TYPE,value=GameWebConfig.class)
})*/
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableRedisHttpSession
public class SessionConfig {

	@Bean
	public RedisHttpSessionConfiguration redisHttpSessionConfiguration(){
		RedisHttpSessionConfiguration redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();
		redisHttpSessionConfiguration.setMaxInactiveIntervalInSeconds(120);
		
		return redisHttpSessionConfiguration;
	}
	
	@Bean(name="redisConnFactory")
	public JedisConnectionFactory jedisConnectionFactory() throws FileNotFoundException, IOException{
		
		Properties properties = new Properties();
        File redisConfigFile = new File("/etc/battle/redis.properties");
        InputStream defaultConfig = this.getClass().getResourceAsStream("/redis.properties");

        if (redisConfigFile.exists()) {
            properties.load(new FileInputStream(redisConfigFile));
        } else {
            properties.load(defaultConfig);
        }
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(properties.getProperty("redis.ip"));
		jedisConnectionFactory.setPort(Integer.parseInt(properties.getProperty("redis.port")));
		jedisConnectionFactory.setPassword(properties.getProperty("redis.password"));
		return jedisConnectionFactory;
	}
	
	@Bean
	public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory){
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory);
		
		return stringRedisTemplate;
	}
	
	@Bean
    public ConfigureRedisAction configureRedisAction(){
    	return ConfigureRedisAction.NO_OP;
    }
	 
	@Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("www.fisherman7.com");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
	
	
	@Bean(name = "sessionKeyGenerator")
    public KeyGenerator firstParamKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
            	
            
                StringBuilder sb = new StringBuilder();
                for(Object param:params){
                	sb.append("_"+param.toString());
                }
                
                String key =  method.getName()+sb.toString();
                
                
                System.out.println("...............method:"+method.getDeclaringClass().getName());
                return key;
            }
        };
    }
	
	/*
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		ehCacheManagerFactoryBean.setShared(true);
		ehCacheManagerFactoryBean.setCacheManagerName("ehcacheManager");
		return ehCacheManagerFactoryBean;
	}
	
	@Bean
	public EhCacheFactoryBean cacheBean(CacheManager ehCacheManagerFactoryBean){
		EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
		ehCacheFactoryBean.setCacheName("ehCache");
		ehCacheFactoryBean.setCacheManager(ehCacheManagerFactoryBean);
		return ehCacheFactoryBean;
	}
	
	@Bean
	public JedisPoolConfig jedisPoolConfig(){
		
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(100);
		jedisPoolConfig.setMaxIdle(30);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		jedisPoolConfig.setMaxWaitMillis(1000);
		return jedisPoolConfig;
	}
	
	@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory){
		RedisTemplate redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}
	
	@Bean
	public SimpleCacheManager simpleCacheManager(RedisTemplate redisTemplate){
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		
		List<org.springframework.cache.Cache> caches = new ArrayList<>();
		EhRedisCache ehRedisCache = new EhRedisCache();
		caches.add(ehRedisCache);
		simpleCacheManager.setCaches(caches);
		return simpleCacheManager;
	}*/

  
}
