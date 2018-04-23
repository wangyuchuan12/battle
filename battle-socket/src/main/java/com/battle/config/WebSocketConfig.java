package com.battle.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.battle.socket.SocketHandler;
import com.battle.socket.HandshakeInterceptor;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Autowired
	private HandshakeInterceptor handshakeInterceptor;
	
	@Autowired
	private SocketHandler socketHandler;
	
	
    @Bean
    public ServerEndpointExporter serverEndpointExporter(ApplicationContext context) {
        return new ServerEndpointExporter();
    }
    

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	
    	System.out.println("看看这里有没有被调用");
        registry.addHandler(socketHandler, "/socket").setAllowedOrigins("*").addInterceptors(handshakeInterceptor);
        
    }
}
