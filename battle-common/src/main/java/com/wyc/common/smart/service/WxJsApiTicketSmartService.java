package com.wyc.common.smart.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.service.WxJsApiTicketService;
import com.wyc.common.wx.domain.AccessTokenBean;
import com.wyc.common.wx.domain.JsapiTicketBean;
import com.wyc.common.wx.service.JsapiTicketService;
@Service
public class WxJsApiTicketSmartService {
    @Autowired
    private WxJsApiTicketService wxJsApiTicketService;
    @Autowired
    private JsapiTicketService jsapiTicketService;
    @Autowired
    private AccessTokenSmartService accessTokenSmartService;
    public  JsapiTicketBean getFromWx()throws Exception{
        AccessTokenBean accessToken = accessTokenSmartService.getFromWx();
        return jsapiTicketService.getJsapiTicketBean(accessToken.getAccessToken());
    }
    public  JsapiTicketBean getFromDatabase()throws Exception{
        return wxJsApiTicketService.getJsapiTicketBean();
    }
    
    public JsapiTicketBean saveToDataBase(JsapiTicketBean jsapiTicketBean)throws Exception{
        return wxJsApiTicketService.save(jsapiTicketBean);
    }
    
    public JsapiTicketBean addToDataBase(JsapiTicketBean jsapiTicketBean)throws Exception{
        return wxJsApiTicketService.add(jsapiTicketBean);
    }
    
    public boolean currentIsAvailable()throws Exception{
        JsapiTicketBean jsapiTicketBean = getFromDatabase();
        
        if(jsapiTicketBean==null){
        	return false;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(jsapiTicketBean.getBeginDateTime().toDate());
        calendar.add(Calendar.SECOND, Integer.parseInt(jsapiTicketBean.getExpires_in())-100);
        if(calendar.getTime().getTime()<new Date().getTime()){
            return false;
        }else{
            return true;
        }
    }
}
