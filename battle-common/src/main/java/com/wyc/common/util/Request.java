package com.wyc.common.util;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Request {
	private String charsetName = "UTF-8";
	@Autowired
	private HttpClient httpClient;
	private URL url;
	final static Logger logger = LoggerFactory.getLogger(Request.class);
	public Request(URL url){
		this.url = url;
		
	}
	
	public Response get(Map<String, String> params) throws Exception{
	        HttpGet httpGet = new HttpGet(url.toString());
	        HttpParams hp = httpGet.getParams();    
	        hp.getParameter("true");
	        if(params!=null){
        	        for(Entry<String, String> param:params.entrySet()){
        	            hp.setParameter(param.getKey(), param.getValue());
        	        }
	        }
	        HttpResponse httpResponse = httpClient.execute(httpGet);
	        HttpEntity httpEntity = httpResponse.getEntity();
		logger.debug("request success");
		return new Response(httpEntity.getContent(),charsetName);
	}
	
	public Response post(String data) throws IOException{
	        HttpPost httpPost = new HttpPost(url.toString());
	        StringEntity entity = new StringEntity(data,charsetName);
	        entity.setContentEncoding(charsetName);
	        httpPost.setEntity(entity);
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        
	        HttpEntity httpEntity = httpResponse.getEntity();
		return new Response(httpEntity.getContent(),charsetName);
		
	}
}
