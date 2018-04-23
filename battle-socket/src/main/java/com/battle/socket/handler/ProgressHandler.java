package com.battle.socket.handler;
import com.alibaba.druid.support.json.JSONUtils;
import com.battle.domain.DataView;
import com.battle.service.DataViewService;
import com.battle.socket.OnlineListener;
import com.wyc.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

/**
 * @author xiaojf 2017/3/2 9:55.
 */
@Service
public class ProgressHandler extends TextWebSocketHandler {
    private Map<String,WebSocketSession> sessionMap = new HashMap<String, WebSocketSession>();

    @Autowired
	private DataViewService dataViewService;
    
    @Autowired
    private OnlineListener onlineListener;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        session.sendMessage(new TextMessage(session.getPrincipal().getName()+",你是第" + (sessionMap.size()) + "位访客")); //p2p

        Object parse = JSONUtils.parse(message.getPayload());

        System.out.println(".......message:"+parse);
        
        
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	
    	Map<String, Object> attributes = session.getAttributes();
    	Object token = attributes.get("token");
    	Object userId = attributes.get("userId");
    	sessionMap.put(token.toString(),session);
 
    	
    	
    	
    	DataView dataView = dataViewService.findOneByCode(DataView.ONELINE_NUM_CODE);
    	String value = dataView.getValue();
    	Integer num = 0;
    	if(CommonUtil.isNotEmpty(value)){
    		num = Integer.parseInt(value);
    	}
    	num++;
    	dataView.setValue(num+"");
    	dataViewService.update(dataView);
    	
    	
    	onlineListener.onLine(userId.toString());
    	
    
    	
        super.afterConnectionEstablished(session);
    }

    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    	Map<String, Object> attributes = session.getAttributes();
    	Object token = attributes.get("token");
    	Object userId = attributes.get("userId");

    	
    	
    	
    	DataView dataView = dataViewService.findOneByCode(DataView.ONELINE_NUM_CODE);
    	String value = dataView.getValue();
    	Integer num = 0;
    	if(CommonUtil.isNotEmpty(value)){
    		num = Integer.parseInt(value);
    	}
    	num--;
    	if(num<0){
    		num = 0;
    	}
    	dataView.setValue(num+"");
    	dataViewService.update(dataView);
    	
    	onlineListener.downLine(userId.toString());
    	
    	sessionMap.remove(token.toString());
        super.afterConnectionClosed(session, status);
    }

    /**
     * 发送消息
     * @author xiaojf 2017/3/2 11:43
     */
    public void sendMessage(String token,String message) throws IOException {
        sendMessage(Arrays.asList(token),Arrays.asList(message));
    }

    /**
     * 发送消息
     * @author xiaojf 2017/3/2 11:43
     */
    public void sendMessage(Collection<String> acceptorList,String message) throws IOException {
        sendMessage(acceptorList,Arrays.asList(message));
       
    }
    

    /**
     * 发送消息，p2p 群发都支持
     * @author xiaojf 2017/3/2 11:43
     */
    public void sendMessage(Collection<String> acceptorList, Collection<String> msgList) throws IOException {
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