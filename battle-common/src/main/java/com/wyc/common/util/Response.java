package com.wyc.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.common.domain.MyResource;
import com.wyc.common.smart.service.UploadToQNService;
import com.wyc.common.wx.domain.WxContext;

public class Response {

	@Autowired
	private WxContext wxContext;
    
	private InputStream inputStream;
	
	@Autowired
	private UploadToQNService uploadToQNService;
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
	
	public MyResource readImg()throws Exception{

		
		try {
            String path = saveToImgByInputStream(inputStream,wxContext.getFilePath(),UUID.randomUUID().toString());
            
            MyResource myResource = new MyResource();
            myResource.setSystemUrl(path);
            myResource.setId(UUID.randomUUID().toString());
            
            uploadToQNService.syncResource(myResource);
            
            return myResource;
        } catch (Exception e) {
           e.printStackTrace();
          
        } finally {
        }
		return null;
	}

	public static String saveToImgByInputStream(InputStream instreams,String imgPath,String imgName){
        if(instreams != null){
            try {
                File file=new File(imgPath,imgName);//可以是任何图片格式.jpg,.png等
                FileOutputStream fos=new FileOutputStream(file);
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();      
                
               return file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
        return null;
    }
	
	public <T>T readObject(Class<T> t)throws Exception{
	    String message = read();
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.readValue(message, t);
	}
}
