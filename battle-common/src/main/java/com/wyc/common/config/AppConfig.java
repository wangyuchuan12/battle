package com.wyc.common.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import javax.transaction.Transactional;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.wyc.ApplicationContextProvider;
import com.wyc.common.domain.Href;
import com.wyc.common.domain.vo.ApplicationProperties;
import com.wyc.common.domain.vo.ShareConfigVo;
import com.wyc.common.service.HrefService;
import com.wyc.common.util.MyLongDateFormat;
import com.wyc.common.util.MySimpleDateFormat;
import com.wyc.common.wx.domain.WxContext;



@Configuration
/*@ComponentScan(basePackages = "com", excludeFilters = {
        @Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebConfig2.class),
        @Filter(type = FilterType.ASSIGNABLE_TYPE, value = DatabaseConfig.class),
    //    @Filter(type = FilterType.ASSIGNABLE_TYPE, value = SessionConfig.class),
        @Filter(type=FilterType.ASSIGNABLE_TYPE,value=GameWebConfig.class)
})*/
@EnableScheduling
public class AppConfig{
	 	@Autowired
	 	private HrefService hrefService;
	 
	    final static Logger logger = LoggerFactory.getLogger(AppConfig.class);
	    @Bean
	    public ApplicationContextProvider applicationContextProvider() {
	        return new ApplicationContextProvider();
	    }
	    
	    @Bean
	    public SockIOPool sockIoPool(){
	        String[] servers =
	        {
	              "127.0.0.1:8888"
	        };
	        System.out.println(".....5");
	        SockIOPool sockIOPool = SockIOPool.getInstance();
	        sockIOPool.setServers(servers);
	        sockIOPool.setNagle(false);
	        sockIOPool.setSocketTO(3000);
	        sockIOPool.setSocketConnectTO(0);
	        sockIOPool.initialize();
	        return sockIOPool;
	        
	        
	    }
	   
	    @Bean
	    public MemCachedClient memcachedClient(){
	        return new MemCachedClient();
	    }
	    
	   
	    
	    @Bean
	    public HttpClient httpGet(ApplicationProperties applicationProperties,WxContext wxc){
	    	

	        FileInputStream instream = null;
	        try {
	            KeyStore keyStore = KeyStore.getInstance("PKCS12");
	            instream =  new FileInputStream(new File(applicationProperties.getProperty("apiclient_cert_path")));
	            keyStore.load(instream,wxc.getMchId().toCharArray());
	            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, wxc.getMchId().toCharArray()).build();
	            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(

	                    sslcontext,new String[] { "TLSv1" },null,

	                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER); //设置httpclient的SSLSocketFactory

	                    CloseableHttpClient httpclient = HttpClients.custom()

	                    .setSSLSocketFactory(sslsf)

	                    .build();
	                    return httpclient;
	        } catch (Exception e) {
	            logger.error("there is has an error:{}",e);
	            try {
	                instream.close();
	            } catch (IOException e1) {
	                // TODO Auto-generated catch block
	                e1.printStackTrace();
	            }
	        }
	        return null;
	    }
	    
	    @Bean
	    public ApplicationProperties applicationProperties() {
	        
	        ApplicationProperties properties = new ApplicationProperties();
	        File databaseConfigFile = new File(
	                "/etc/battle/application.properties");
	        
	        File redisConfigFile = new File(
	                "/etc/battle/redis.properties");
	        
	        
	        try {
	            if (databaseConfigFile.exists()) {
	                properties.load(new FileInputStream(databaseConfigFile));
	            } else {
	                InputStream defaultConfig = this.getClass()
	                        .getResourceAsStream("/application.properties");
	                properties.load(defaultConfig);
	            }
	        } catch (IOException e) {
	            logger.error("Load application.properties error: {}", e);
	        }
	        
	        try {
	            if (redisConfigFile.exists()) {
	                properties.load(new FileInputStream(redisConfigFile));
	            } else {
	                InputStream defaultConfig = this.getClass()
	                        .getResourceAsStream("/redis.properties");
	                properties.load(defaultConfig);
	            }
	        } catch (IOException e) {
	            logger.error("Load application.properties error: {}", e);
	        }
	        return properties;
	    }
	    
	    @Bean
	    @Autowired
	    public WxContext wxContext(ApplicationProperties myProperties){
	        WxContext wxContext = new WxContext();
	        wxContext.setAppid(myProperties.getProperty("appid"));
	        wxContext.setAppsecret(myProperties.getProperty("appsecret"));
	        wxContext.setFilePath(myProperties.getProperty("file_path"));
	        wxContext.setFlag(myProperties.getProperty("flag"));
	        wxContext.setDomainName(myProperties.getProperty("domain_name"));
	        wxContext.setKey(myProperties.getProperty("key"));
	        wxContext.setMchId(myProperties.getProperty("mch_id"));
	        wxContext.setTransferFee(Integer.parseInt(myProperties.getProperty("transfer_fee")));
	        wxContext.setInstantArrival(Integer.parseInt(myProperties.getProperty("instant_arrival")));
	        wxContext.setArrivalNum(Integer.parseInt(myProperties.getProperty("arrival_num")));
	        wxContext.setShareNumShowAnswer(Integer.parseInt(myProperties.getProperty("share_num_show_answer")));
	        wxContext.setSubmailAppid(myProperties.getProperty("submail_appid"));
	        wxContext.setSubmailSignature(myProperties.getProperty("submail_signature"));
	        wxContext.setApplyExpertProjectCode(myProperties.getProperty("apply_expert_project_code"));
	        
	        wxContext.setQnAccessKey(myProperties.getProperty("qn_accessKey"));
	        wxContext.setQnScretKey(myProperties.getProperty("qn_scretKey"));
	        wxContext.setQnBucketname(myProperties.getProperty("qn_bucketname"));
	        wxContext.setQnDomain(myProperties.getProperty("qn_domain"));
//	        wxContext = wxContextService.getWxContextBean();
	        
	        return wxContext;
	    }
	    
	    @Bean
	    public MyLongDateFormat myLongDateFormat(){
	        MyLongDateFormat sdf = new MyLongDateFormat("yyyy-MM-dd HH:mm:ss");
	        return sdf;
	    }
	    
	    @Bean
	    public MySimpleDateFormat mySimpleDateFormat(){
	    	MySimpleDateFormat sdf = new MySimpleDateFormat("MM-dd HH:mm");
	    	return sdf;
	    }
	    
	    
	    @Bean
	    @Transactional
	    public ShareConfigVo initDataBase(){
	    	Href luckDrawListHref = hrefService.findOneByCode("luckDrawList");
	    	if(luckDrawListHref==null){
	    		luckDrawListHref = new Href();
	    		luckDrawListHref.setCode("luckDrawList");
	    		luckDrawListHref.setAddress("/view/draw/luck_draw/list");
	    		luckDrawListHref.setClickCount(0);
	    		luckDrawListHref.setShareCount(0);
	    		hrefService.add(luckDrawListHref);
	    	}
	    	
	    	Href luckDrawInfoHref = hrefService.findOneByCode("luckDrawInfo");
	    	if(luckDrawInfoHref==null){
	    		luckDrawInfoHref = new Href();
	    		luckDrawInfoHref.setCode("luckDrawInfo");
	    		luckDrawInfoHref.setAddress("/view/draw/luck_draw/info");
	    		luckDrawInfoHref.setClickCount(0);
	    		luckDrawInfoHref.setShareCount(0);
	    		hrefService.add(luckDrawInfoHref);
	    	}
	    	
	    	
	    	Href luckDrawAdd2Href = hrefService.findOneByCode("luckDrawAdd2");
	    	if(luckDrawAdd2Href==null){
	    		luckDrawAdd2Href = new Href();
	    		luckDrawAdd2Href.setCode("luckDrawAdd2");
	    		luckDrawAdd2Href.setAddress("/view/draw/luck_draw/add2");
	    		luckDrawAdd2Href.setClickCount(0);
	    		luckDrawAdd2Href.setShareCount(0);
	    		hrefService.add(luckDrawAdd2Href);
	    	}
	    	
	    	Href luckDrawRedPacketOptionHref = hrefService.findOneByCode("luckDrawRedPacketOption");
	    	if(luckDrawRedPacketOptionHref==null){
	    		luckDrawRedPacketOptionHref = new Href();
	    		luckDrawRedPacketOptionHref.setCode("luckDrawRedPacketOption");
	    		luckDrawRedPacketOptionHref.setAddress("/view/draw/luck_draw/red_packet_option");
	    		luckDrawRedPacketOptionHref.setClickCount(0);
	    		luckDrawRedPacketOptionHref.setShareCount(0);
	    		hrefService.add(luckDrawRedPacketOptionHref);
	    	}
	    	
	    	Href luckDrawSubjectCheckHref = hrefService.findOneByCode("luckDrawSubjectCheck");
	    	if(luckDrawSubjectCheckHref==null){
	    		luckDrawSubjectCheckHref = new Href();
	    		luckDrawSubjectCheckHref.setCode("luckDrawSubjectCheck");
	    		luckDrawSubjectCheckHref.setAddress("/view/draw/luck_draw/subject_check");
	    		luckDrawSubjectCheckHref.setClickCount(0);
	    		luckDrawSubjectCheckHref.setShareCount(0);
	    		hrefService.add(luckDrawSubjectCheckHref);
	    	}
	    	
	    	Href drawRoomListHref = hrefService.findOneByCode("drawRoomList");
	    	if(drawRoomListHref==null){
	    		drawRoomListHref = new Href();
	    		drawRoomListHref.setCode("drawRoomList");
	    		drawRoomListHref.setAddress("/view/draw/draw_room/list");
	    		drawRoomListHref.setClickCount(0);
	    		drawRoomListHref.setShareCount(0);
	    		hrefService.add(drawRoomListHref);
	    	}
	    	
	    	Href drawRoomInfoHref = hrefService.findOneByCode("drawRoomInfo");
	    	if(drawRoomInfoHref==null){
	    		drawRoomInfoHref = new Href();
	    		drawRoomInfoHref.setCode("drawRoomInfo");
	    		drawRoomInfoHref.setAddress("/view/draw/draw_room/info");
	    		drawRoomInfoHref.setClickCount(0);
	    		drawRoomInfoHref.setShareCount(0);
	    		hrefService.add(drawRoomInfoHref);
	    	}
	    	
	    	
	    	Href drawRoomAddHref = hrefService.findOneByCode("drawRoomAdd");
	    	if(drawRoomAddHref==null){
	    		drawRoomAddHref = new Href();
	    		drawRoomAddHref.setCode("drawRoomAdd");
	    		drawRoomAddHref.setAddress("/view/draw/draw_room/add");
	    		drawRoomAddHref.setClickCount(0);
	    		drawRoomAddHref.setShareCount(0);
	    		hrefService.add(drawRoomAddHref);
	    	}
	    	
	    	
	    	
	    	Href mainHomeHref = hrefService.findOneByCode("mainHome");
	    	if(mainHomeHref==null){
	    		mainHomeHref = new Href();
	    		mainHomeHref.setCode("mainHome");
	    		mainHomeHref.setAddress("/view/draw/draw_room/mainHome");
	    		mainHomeHref.setClickCount(0);
	    		mainHomeHref.setShareCount(0);
	    		hrefService.add(mainHomeHref);
	    	}
	    	
	    	
	    	Href personalCenterMainHref = hrefService.findOneByCode("personalCenterMain");
	    	if(personalCenterMainHref==null){
	    		personalCenterMainHref = new Href();
	    		personalCenterMainHref.setCode("personalCenterMain");
	    		personalCenterMainHref.setAddress("/view/draw/personal_center/main");
	    		personalCenterMainHref.setClickCount(0);
	    		personalCenterMainHref.setShareCount(0);
	    		hrefService.add(personalCenterMainHref);
	    	}
	    	
	    	
	    	Href personalCenterTakeOutHref = hrefService.findOneByCode("personalCenterTakeOut");
	    	if(personalCenterTakeOutHref==null){
	    		personalCenterTakeOutHref = new Href();
	    		personalCenterTakeOutHref.setCode("personalCenterTakeOut");
	    		personalCenterTakeOutHref.setAddress("/view/draw/personal_center/takeOut");
	    		personalCenterTakeOutHref.setClickCount(0);
	    		personalCenterTakeOutHref.setShareCount(0);
	    		hrefService.add(personalCenterTakeOutHref);
	    	}
	    	
	    	
	    	
	    	Href personalCenterTakeInHref = hrefService.findOneByCode("personalCenterTakeIn");
	    	if(personalCenterTakeInHref==null){
	    		personalCenterTakeInHref = new Href();
	    		personalCenterTakeInHref.setCode("personalCenterTakeIn");
	    		personalCenterTakeInHref.setAddress("/view/draw/personal_center/takeIn");
	    		personalCenterTakeInHref.setClickCount(0);
	    		personalCenterTakeInHref.setShareCount(0);
	    		hrefService.add(personalCenterTakeInHref);
	    	}
	    	
	    	
	    	Href vieDrawInfoHref = hrefService.findOneByCode("vieDrawInfo");
	    	if(vieDrawInfoHref==null){
	    		vieDrawInfoHref = new Href();
	    		vieDrawInfoHref.setCode("vieDrawInfo");
	    		vieDrawInfoHref.setAddress("/view/vie/draw/vie_draw/info");
	    		vieDrawInfoHref.setClickCount(0);
	    		vieDrawInfoHref.setShareCount(0);
	    		hrefService.add(vieDrawInfoHref);
	    	}
	    	
	    	
	    	Href vieDrawVieSetProblemHref = hrefService.findOneByCode("vieSetProblem");
	    	if(vieDrawVieSetProblemHref==null){
	    		vieDrawVieSetProblemHref = new Href();
	    		vieDrawVieSetProblemHref.setCode("vieSetProblem");
	    		vieDrawVieSetProblemHref.setAddress("/view/vie/draw/vie_draw/vie_set_problem");
	    		vieDrawVieSetProblemHref.setClickCount(0);
	    		vieDrawVieSetProblemHref.setShareCount(0);
	    		hrefService.add(vieDrawVieSetProblemHref);
	    	}
	    	
	    	
	    	Href vieDrawAddHref = hrefService.findOneByCode("vieDrawAdd");
	    	if(vieDrawAddHref==null){
	    		vieDrawAddHref = new Href();
	    		vieDrawAddHref.setCode("vieDrawAdd");
	    		vieDrawAddHref.setAddress("/view/vie/draw/vie_draw/add");
	    		vieDrawAddHref.setClickCount(0);
	    		vieDrawAddHref.setShareCount(0);
	    		hrefService.add(vieDrawAddHref);
	    	}
	    	
	    	
	    	Href vieDrawVieAnswerProblemHref = hrefService.findOneByCode("vieAnswerProblem");
	    	if(vieDrawVieAnswerProblemHref==null){
	    		vieDrawVieAnswerProblemHref = new Href();
	    		vieDrawVieAnswerProblemHref.setCode("vieAnswerProblem");
	    		vieDrawVieAnswerProblemHref.setAddress("/view/vie/draw/vie_draw/vie_answer_problem");
	    		vieDrawVieAnswerProblemHref.setClickCount(0);
	    		vieDrawVieAnswerProblemHref.setShareCount(0);
	    		hrefService.add(vieDrawVieAnswerProblemHref);
	    	}
	    	
	    	ShareConfigVo shareConfigVo = new ShareConfigVo();
	    	return shareConfigVo;
	    }
}
