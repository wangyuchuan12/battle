package com.battle.config;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.battle.domain.DataView;
import com.battle.service.DataViewService;

@Configuration
public class InitDataConfig{

	@Autowired
	private DataViewService dataViewService;
	
	public InitDataConfig() {
		
	}
	
	
	/*
	@Bean
    public HandshakeInterceptor handshakeInterceptor(){
    	
    	HandshakeInterceptor handshakeInterceptor = new HandshakeInterceptor();
    	
    	return handshakeInterceptor;
    }
    
    @Bean
    public SocketHandler socketHandler(){
    	SocketHandler socketHandler = new SocketHandler();
    	
    	return socketHandler;
    }*/
	
	
	@Bean
	@Transactional
	public DataView initData(){
		
		DataView onelineNumDataView = dataViewService.findOneByCode(DataView.ONELINE_NUM_CODE);
		
		if(onelineNumDataView==null){
			onelineNumDataView = new DataView();
			onelineNumDataView.setCode(DataView.ONELINE_NUM_CODE);
			onelineNumDataView.setName("在线人数统计");
			onelineNumDataView.setValue("0");
			
			dataViewService.add(onelineNumDataView);
		}
		
		return onelineNumDataView;
	}
}
