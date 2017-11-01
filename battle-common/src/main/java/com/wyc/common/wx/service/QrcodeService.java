package com.wyc.common.wx.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.common.domain.MyResource;
import com.wyc.common.smart.service.AccessTokenSmartService;
import com.wyc.common.smart.service.UploadToQNService;
import com.wyc.common.util.Request;
import com.wyc.common.util.RequestFactory;
import com.wyc.common.util.Response;
import com.wyc.common.wx.domain.AccessTokenBean;
import com.wyc.common.wx.domain.WxContext;

@Service
public class QrcodeService {
	@Autowired
	private RequestFactory requestFactory;

	@Autowired
	private AccessTokenSmartService accessTokenSmartService;

	
	public MyResource createWxaqrcode(String path)throws Exception{
		AccessTokenBean accessTokenBean = accessTokenSmartService.get();
		Request request = requestFactory.getwxacodeRequest(accessTokenBean.getAccessToken());
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> params = new HashMap<>();
		params.put("path", path);
		
		Response response = request.post(objectMapper.writeValueAsString(params));
		MyResource myResource = response.readImg();
		
		return myResource;
		
	}
}
