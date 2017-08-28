package com.wyc.common.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.common.wx.domain.JsapiTicketBean;

public interface WxJsApiTicketRepository extends CrudRepository<JsapiTicketBean, String>{

}
