package com.wyc.common.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.repositories.TokenRepository;
import com.wyc.common.wx.domain.Token;



@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    
    public Token findByIdAndInvalidDateGreaterThan(String id , DateTime dateTime){
        return tokenRepository.findByIdAndInvalidDateGreaterThan(id , dateTime);
    }
    
    public Token findByKeyAndInvalidDateGreaterThan(String key , DateTime dateTime){
        return tokenRepository.findByTokenKeyAndInvalidDateGreaterThan(key , dateTime);
    }
    
    public Token add(Token token){
        token.setId(UUID.randomUUID().toString());
        token.setCreateAt(new DateTime());
        token.setUpdateAt(new DateTime());
        return tokenRepository.save(token);
    }
    
    public Token save(Token token){
        token.setUpdateAt(new DateTime());
        return tokenRepository.save(token);
    }

    public void delete(String id) {
       tokenRepository.delete(id);
    }
    
    public Token findByTokenKey(String tokenKey){
        return tokenRepository.findByTokenKey(tokenKey);
    }
}
