package com.wyc.common.config.scoket;
import com.alibaba.druid.support.json.JSONUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

/**
 * @author xiaojf 2017/3/2 9:55.
 */
@Component
public class CountWebSocketHandler extends TextWebSocketHandler {
    private static long count = 0;
    private static Map<String,WebSocketSession> sessionMap = new HashMap<String, WebSocketSession>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        session.sendMessage(new TextMessage(session.getPrincipal().getName()+",你是第" + (sessionMap.size()) + "位访客")); //p2p

    	
    	System.out.println("...........handleTextMessage:"+message);
        Object parse = JSONUtils.parse(message.getPayload());

        Collection<WebSocketSession> sessions = sessionMap.values();
        for (WebSocketSession ws : sessions) {//广播
            ws.sendMessage(message);
        }

        sendMessage(sessionMap.keySet(),"你好");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	
    	System.out.println("...........afterConnectionEstablished:");
        sessionMap.put(session.getPrincipal().getName(),session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	
    	System.out.println("...........afterConnectionClosed:"+status);
        sessionMap.remove(session.getPrincipal().getName());
        super.afterConnectionClosed(session, status);
    }

    /**
     * 发送消息
     * @author xiaojf 2017/3/2 11:43
     */
    public static void sendMessage(String username,String message) throws IOException {
    	System.out.println("...........sendMessage:"+message);
        sendMessage(Arrays.asList(username),Arrays.asList(message));
    }

    /**
     * 发送消息
     * @author xiaojf 2017/3/2 11:43
     */
    public static void sendMessage(Collection<String> acceptorList,String message) throws IOException {
    	System.out.println("...........sendMessage:"+message);
        sendMessage(acceptorList,Arrays.asList(message));
    }

    /**
     * 发送消息，p2p 群发都支持
     * @author xiaojf 2017/3/2 11:43
     */
    public static void sendMessage(Collection<String> acceptorList, Collection<String> msgList) throws IOException {
    	System.out.println("...........sendMessage1:"+msgList+","+acceptorList);
        if (acceptorList != null && msgList != null) {
            for (String acceptor : acceptorList) {
                WebSocketSession session = sessionMap.get(acceptor);
                if (session != null) {
                    for (String msg : msgList) {
                        session.sendMessage(new TextMessage(msg.getBytes()));
                    }
                }
            }
        }
    }
}