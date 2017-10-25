package com.wyc.common.other.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.common.domain.vo.SubmailMsgSendResultVo;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.util.Request;
import com.wyc.common.util.RequestFactory;
import com.wyc.common.util.Response;
import com.wyc.common.wx.domain.WxContext;

@Service
public class SubmailMsgService {

	@Autowired
    private RequestFactory requestFactory;
	@Autowired
	private WxContext context;
	public String sendMsg(String phonenum,String project)throws Exception{
		String mailAppid = context.getSubmailAppid();
		String mailSignature = context.getSubmailSignature();
		Request request = requestFactory.submailMsgSendResultRequest();
	
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = new HashMap<>();
		String code = CommonUtil.createRandom(true, 4);
		map.put("code", code);
		map.put("time", "120s");
		String json = objectMapper.writeValueAsString(map);
		Response response = request.post("appid="+mailAppid+"&to="+phonenum+"&project="+project+"&signature="+mailSignature+"&vars="+json);
        SubmailMsgSendResultVo submailMsgSendResultVo = response.readObject(SubmailMsgSendResultVo.class);
        if(submailMsgSendResultVo.getStatus().equals("success")){
        	return code;
        }else{
        	return null;
        }
		
        
	}
	
}
