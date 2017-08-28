package com.wyc.common.smart.service;

import com.wyc.common.wx.domain.Token;

public interface SmartService<T>{
   public  T getFromWx()throws Exception;
   public  T getFromDatabase(String token)throws Exception;
   public  Token saveToDatabase(T t , String tokenKey)throws Exception;
   public T getFromDatabaseByOther();
   
   public T getFromDatabaseByKey(String key);
   public String generateKey(String ... args);
}
