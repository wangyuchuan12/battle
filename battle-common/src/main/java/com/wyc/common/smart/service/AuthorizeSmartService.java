package com.wyc.common.smart.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.service.TokenService;
import com.wyc.common.service.WxAuthorizeService;
import com.wyc.common.wx.domain.Authorize;
import com.wyc.common.wx.domain.Token;
import com.wyc.common.wx.service.OauthService;
@Service
public class AuthorizeSmartService implements SmartService<Authorize>{
    private String code;
    private String openid;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private WxAuthorizeService wxAuthorizeService;
    @Autowired
    private TokenService tokenService;
    final static Logger logger = LoggerFactory.getLogger(AuthorizeSmartService.class);
    public void setCode(String code) {
        this.code = code;
    }
    public void setOpenid(String openid){
        this.openid = openid;
    }
    @Override
    public Authorize getFromWx() throws Exception {
        Authorize authorize = oauthService.getAuthorizeByCode(code);
        logger.debug("get authorize from wx,the object is {}",authorize);
        return authorize;
    }

    @Override
    public Authorize getFromDatabase(String token) {
        Authorize authorize = wxAuthorizeService.findByToken(token);
        logger.debug("get authorize from database,the object is {}",authorize);
        return authorize;
    }

    @Override
    public Token saveToDatabase(Authorize t , String tokenKey){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, Integer.parseInt(t.getExpires_in())-100);
        Token token = tokenService.findByTokenKey(tokenKey);
        if(token==null){
            token = new Token();
            token.setStatus(1);
            token.setInvalidDate(new DateTime(calendar.getTime()));
            token.setTokenKey(tokenKey);
            t.setToken(token.getId());
            token = tokenService.add(token);
        }else{
            token.setStatus(1);
            token.setInvalidDate(new DateTime(calendar.getTime()));
            token.setTokenKey(tokenKey);
            t.setToken(token.getId());
            token = tokenService.save(token);
        }
        Authorize authorize = wxAuthorizeService.findByToken(token.getId());
        if(authorize==null){
            authorize = new Authorize();
            authorize.setAccess_token(t.getAccess_token());
            authorize.setExpires_in(t.getExpires_in());
            authorize.setOpenid(t.getOpenid());
            authorize.setRefresh_token(t.getRefresh_token());
            authorize.setScope(t.getScope());
            authorize.setToken(t.getId());
            authorize.setUnionid(t.getUnionid());
            wxAuthorizeService.add(authorize);
        }else{
            t.setToken(t.getId());
            wxAuthorizeService.save(t);
        }
        logger.debug("save the Authorize to database,the Authorize is {},the token is {}",t,token.getId());
        return token;
    }

    @Override
    public Authorize getFromDatabaseByOther() {
        Authorize authorize = wxAuthorizeService.findByOpenid(openid);
        return authorize;
    }

    @Override
    public String generateKey(String... args) {
        if(code==null){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("authorize_");
        sb.append(code+"_");
        for(String arg:args){
            sb.append(arg);
            sb.append("-");
        }
        return sb.toString();
    }


    @Override
    public Authorize getFromDatabaseByKey(String key) {
        Token token = tokenService.findByKeyAndInvalidDateGreaterThan(key, new DateTime());
        if(token!=null){
            Authorize authorize = wxAuthorizeService.findByToken(token.getId());
            return authorize;
        }
        return null;
    }

}
