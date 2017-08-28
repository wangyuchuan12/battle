package com.wyc.common.filter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.common.service.TokenService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.smart.service.UserSmartService;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.Token;
import com.wyc.common.wx.domain.UserInfo;

public class UserInfoFilter extends Filter{

	@Autowired
	private UserSmartService userSmartService;
	
	@Autowired
	private TokenService tokenService;

	final static Logger logger = LoggerFactory.getLogger(UserInfoFilter.class);
	@Override
	public Object handlerFilter(SessionManager filterManager) throws Exception {
			
		
			try{
				
				UserInfo userInfo = (UserInfo)filterManager.getObject(UserInfo.class);
				if(userInfo!=null){
					return userInfo;
				}
				
				HttpServletRequest httpServletRequest = filterManager.getHttpServletRequest();
				
				String tokenId = httpServletRequest.getParameter("token");
				
				logger.debug("tokenId:{}",tokenId);
		        Token token = tokenService.findByIdAndInvalidDateGreaterThan(tokenId, new DateTime());
		        if(token!=null){
		            userInfo = userSmartService.getFromDatabase(tokenId);
		            logger.debug("get userInfo from database by token {} , return object is {}",tokenId , userInfo);
		        }
		        String code = httpServletRequest.getParameter("code");
		        userSmartService.setCode(code);
		        String key = userSmartService.generateKey();
		        if(userInfo==null&&key!=null){
		            userInfo = userSmartService.getFromDatabaseByKey(key);
		            logger.debug("get userInfo from database by key {} , return object is {}",key , userInfo);
		        }
		        
	            if(userInfo==null&&code!=null){
	                try {
	                    userInfo = userSmartService.getFromWx();
	                    
	                    token = userSmartService.saveToDatabase(userInfo, key);
	                    logger.debug("save to database success ,the key is {} , the token is {}" , key , token);
	                } catch (Exception e) {
	                    e.printStackTrace();
	               //     logger.error("get userInfo from wx has error");
	                    try{
	                    	userInfo.setNickname(CommonUtil.filterEmoji(userInfo.getNickname()));
		                    //做最后一层保障，保证在数据库当中有userInfo
		                    token = userSmartService.saveToDatabase(userInfo, key);
	                    }catch(Exception e2){
	                    	
	                    	userInfo.setNickname("***");
	                    	
	                    	token = userSmartService.saveToDatabase(userInfo, key);
	                    }
	                    
	                }
	                
	            }  
	            logger.debug("after userInfo:"+userInfo);
	            
	            httpServletRequest.setAttribute("token", token);
	            return userInfo;
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
			
	}


	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> filterClasses = new ArrayList<>();
	//	filterClasses.add(WxConfigFilter.class);
		return filterClasses;
	}


	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		UserInfo userInfo = (UserInfo)sessionManager.getObject(UserInfo.class);
		if(userInfo!=null){
			sessionManager.addNotExecuteFilterClass(UserInfoFilter.class);
		}
		return null;
	}


	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
