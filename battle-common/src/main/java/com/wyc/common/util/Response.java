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
		byte[] jsonBytes = new byte[this.inputStream.available()];
		inputStream.read(jsonBytes);
		String message = new String(jsonBytes,charsetName);
		logger.debug("readMessageFromWx:"+message);
		this.inputStream.close();
		
		System.out.println(message);
		return message;
	}
	
	public <T>T readObject(Class<T> t)throws Exception{
	    String message = read();
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.readValue(message, t);
	}
}
