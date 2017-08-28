package com.wyc.common.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.util.Request;
import com.wyc.common.util.RequestFactory;
import com.wyc.common.util.Response;
import com.wyc.common.wx.domain.JsapiTicketBean;
@Service
public class JsapiTicketService {
    @Autowired
    private RequestFactory requestFactory;
    public JsapiTicketBean getJsapiTicketBean(String accessToken)throws Exception{
        Request request = requestFactory.jsapiTicketRequest(accessToken, "jsapi");
        Response response = request.get(null);
        JsapiTicketBean jsapiTicketBean = response.readObject(JsapiTicketBean.class);
        return jsapiTicketBean;
    }
}
