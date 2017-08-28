package com.wyc.common.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.repositories.WxJsApiTicketRepository;
import com.wyc.common.wx.domain.JsapiTicketBean;
@Service
public class WxJsApiTicketService {
    @Autowired
    private WxJsApiTicketRepository wxJsApiTicketRepository;
    public JsapiTicketBean add(JsapiTicketBean jsapiTicketBean){
        jsapiTicketBean.setBeginDateTime(new DateTime());
        jsapiTicketBean.setCreateAt(new DateTime());
        jsapiTicketBean.setUpdateAt(new DateTime());
        jsapiTicketBean.setId(UUID.randomUUID().toString());
        return wxJsApiTicketRepository.save(jsapiTicketBean);
    }
    
    public JsapiTicketBean save(JsapiTicketBean jsapiTicketBean){
        jsapiTicketBean.setBeginDateTime(new DateTime());
        jsapiTicketBean.setUpdateAt(new DateTime());
        return wxJsApiTicketRepository.save(jsapiTicketBean);
    }
    
    public JsapiTicketBean getJsapiTicketBean(){
        Iterable<JsapiTicketBean> jsapiTicketBeans = wxJsApiTicketRepository.findAll();
      
        for(JsapiTicketBean jsapiTicketBean:jsapiTicketBeans){
            return jsapiTicketBean;
        }
        return null;
    }
}
