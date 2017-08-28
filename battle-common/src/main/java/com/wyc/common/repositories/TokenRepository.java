package com.wyc.common.repositories;

import org.joda.time.DateTime;
import org.springframework.data.repository.CrudRepository;

import com.wyc.common.wx.domain.Token;



public interface TokenRepository extends CrudRepository<Token, String>{
    public Token findByIdAndInvalidDateGreaterThan(String id , DateTime dateTime);
    public Token findByTokenKeyAndInvalidDateGreaterThan(String tokenKey , DateTime dateTime);
    public Token findByTokenKey(String tokenKey);
}
