package com.wyc.common.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.MyResource;
import com.wyc.common.domain.vo.ApplicationProperties;
import com.wyc.common.repositories.ResourceRepository;
@Service
public class MyResourceService {
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private ApplicationProperties properties;
    private Logger logger = LoggerFactory.getLogger(MyResourceService.class);
    public void add(MyResource myResource){
        myResource.setCreateAt(new DateTime());
        resourceRepository.save(myResource);
    }
    
    public MyResource findOne(String id){
        return resourceRepository.findOne(id);
    }
    
    public Iterable<MyResource> findAll(){
        return resourceRepository.findAll();
    }
    
    
    public void addToWebpath(MyResource myResource,InputStream inputStream,boolean isSave){
    	File file = new File(properties.getProperty("file_path"));
        if(!file.exists()){
            
            file.mkdirs();
            logger.info("mkdir "+file.getAbsolutePath());
        }
        String fileName = UUID.randomUUID()+"."+myResource.getSuffix();
        File resource = new File(file.getAbsoluteFile(),fileName);
        myResource.setUrl("/file/"+fileName);
        myResource.setSystemUrl(resource.getAbsolutePath());
        logger.debug("save image to {}",resource.getAbsoluteFile());
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(resource);
            byte[] buffer = new byte[1024];
            int i = 0;
            while((i=inputStream.read(buffer))>0){
                fileOutputStream.write(buffer,0,i);
                fileOutputStream.flush();
            }
            if(isSave){
            	add(myResource);
            }
        } catch (Exception e) {
            logger.error("has an error:"+e.getMessage());
        }finally{
            try {
                fileOutputStream.close();
                inputStream.close();
            } catch (Exception e2) {
                logger.error("has an error:"+e2.getMessage());
            }
        } 
    }
    
    public void addToWebpath(MyResource myResource,InputStream inputStream){
    	addToWebpath(myResource,inputStream,true);
    }

    public Iterable<MyResource> findAll(Iterable<String> resourceIds) {
       return resourceRepository.findAll(resourceIds);
    }

    public MyResource save(MyResource myResource) {
        myResource.setUpdateAt(new DateTime());
        return resourceRepository.save(myResource);
        
    }
}
