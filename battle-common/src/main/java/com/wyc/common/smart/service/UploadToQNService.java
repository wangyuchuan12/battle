package com.wyc.common.smart.service;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.wyc.common.domain.MyResource;
import com.wyc.common.service.MyResourceService;

@Service
public class UploadToQNService {
  //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "0aPSy8Q-S-e7eb7OsYc6xjRO2PjLivG774Jp7tI5";
    String SECRET_KEY = "nL8W-aLnv1hxZeZECQRmsln15kO8F-EvmMbltCBM";
    //要上传的空间
    String bucketname = "picture";
    
  //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();
    @Autowired
    private MyResourceService resourceService;
    private Logger logger = LoggerFactory.getLogger(UploadToQNService.class);
    private String upload(String filePath , String key) throws IOException{
        String token = auth.uploadToken(bucketname,key);
        //调用put方法上传
        Response res = uploadManager.put(filePath, key, token);
        //打印返回的信息
        return res.bodyString(); 

    }
    
    public void syncResource(MyResource myResource){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String returnBody = upload(myResource.getSystemUrl(), myResource.getId());
            HashMap<String, String> hashMap = objectMapper.readValue(returnBody, HashMap.class);
            myResource.setUrl("http://7xlw44.com1.z0.glb.clouddn.com/"+hashMap.get("key"));
            resourceService.save(myResource);
        } catch (Exception e) {
            logger.error("has error:{}",e);
        }
    }
}
