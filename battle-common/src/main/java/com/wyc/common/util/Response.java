package com.wyc.common.util;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Response {

    
	private InputStream inputStream;
	private String charsetName = "UTF-8";
	final static Logger logger = LoggerFactory.getLogger(Response.class);
	public Response(InputStream inputStream){
		this.inputStream = inputStream;
	}
	
	public Response(InputStream inputStream , String charsetName){
		this.inputStream = inputStream;
		this.charsetName = charsetName;
	}
	
	public InputStream getInputStream(){
		return inputStream;
	}
	
	public String read()throws Exception{
		Integer length = this.inputStream.available();
		if(length<10){
			length=100;
		}
		byte[] jsonBytes = new byte[length];
		inputStream.read(jsonBytes);
		String message = new String(jsonBytes,charsetName);
		this.inputStream.close();
		logger.debug(message);
		return message;
	}
	
	public <T>T readObject(Class<T> t)throws Exception{
	    String message = read();
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.readValue(message, t);
	}
}
