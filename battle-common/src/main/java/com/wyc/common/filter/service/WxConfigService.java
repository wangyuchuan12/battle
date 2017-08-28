package com.wyc.common.filter.service;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.vo.WxConfigBean;
import com.wyc.common.session.SessionManager;
import com.wyc.common.smart.service.WxJsApiTicketSmartService;
import com.wyc.common.wx.domain.JsapiTicketBean;
import com.wyc.common.wx.domain.WxContext;

@Service
public class WxConfigService {
	
	final static Logger logger = LoggerFactory.getLogger(WxConfigService.class);
	
	@Autowired
	private WxContext wxContext;
	@Autowired
	private WxJsApiTicketSmartService wxJsApiTicketSmartService;
	public JsapiTicketBean getJsapiTicketBean(SessionManager sessionManager)throws Exception{
		
		JsapiTicketBean jsapiTicketBean = (JsapiTicketBean)sessionManager.getObject(JsapiTicketBean.class);
		
		if(jsapiTicketBean==null){
			jsapiTicketBean = wxJsApiTicketSmartService.getFromDatabase();
		}
		
		if(jsapiTicketBean==null){
            jsapiTicketBean = wxJsApiTicketSmartService.getFromWx();
            jsapiTicketBean = wxJsApiTicketSmartService.addToDataBase(jsapiTicketBean);
            sessionManager.remove(WxConfigBean.class);
        }
		
        if(!wxJsApiTicketSmartService.currentIsAvailable()){
            String id = jsapiTicketBean.getId();
            jsapiTicketBean = wxJsApiTicketSmartService.getFromWx();
            jsapiTicketBean.setId(id);
            jsapiTicketBean = wxJsApiTicketSmartService.saveToDataBase(jsapiTicketBean);
        }
		return jsapiTicketBean;
	}
	
	
	public WxConfigBean getWxConfigBean(SessionManager sessionManager , JsapiTicketBean jsapiTicketBean)throws Exception{
		
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
        String datetime = String.valueOf(System.currentTimeMillis() / 1000);
       
        StringBuffer decript = new StringBuffer();
        String url = null;
        if(httpServletRequest.getQueryString()!=null&&!httpServletRequest.getQueryString().toLowerCase().equals("null")){
            url = httpServletRequest.getRequestURL().toString()+"?"+httpServletRequest.getQueryString();
        }else{
            url = httpServletRequest.getRequestURL().toString();
        }
        logger.debug("config url is {}",url);
        String noncestr = "Wm3WZYTPz0wzccnW"+Math.random();
        decript.append("jsapi_ticket=");
        decript.append(jsapiTicketBean.getTicket()+"&");
        decript.append("noncestr=");
        decript.append(noncestr+"&");
        decript.append("timestamp=");
        decript.append(datetime+"&");
        decript.append("url=");
        decript.append(url);
    
        logger.debug("decript:{}",decript);
        logger.debug("jsapi_ticket:{}",jsapiTicketBean.getTicket());
        logger.debug("noncestr:{}",noncestr);
        logger.debug("datetime:{}",datetime);
        logger.debug("url:{}",url);
        digest.reset();
        digest.update(decript.toString().getBytes());
        byte messageDigest[] = digest.digest();
        StringBuffer digestBuffer = new StringBuffer();
        for(byte b :messageDigest){
            String shaHex = Integer.toHexString(b & 0xFF);
            if (shaHex.length() < 2) {
                digestBuffer.append(0);
            }
            digestBuffer.append(shaHex);
        }
        logger.debug("signature:{}",digestBuffer.toString());
        
        WxConfigBean wxConfigBean = new WxConfigBean();
        httpServletRequest.setAttribute("signature", digestBuffer.toString());
        httpServletRequest.setAttribute("noncestr", noncestr);
        httpServletRequest.setAttribute("appId", wxContext.getAppid());
        httpServletRequest.setAttribute("datetime", datetime);
        wxConfigBean.setAppId(wxContext.getAppid());
        wxConfigBean.setSignature(digestBuffer.toString());
        wxConfigBean.setNoncestr(noncestr);
        wxConfigBean.setDatetime(datetime);
		return wxConfigBean;
	}
}
