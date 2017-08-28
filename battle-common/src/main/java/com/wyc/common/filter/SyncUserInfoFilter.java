package com.wyc.common.filter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.smart.service.UserSmartService;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;
public class SyncUserInfoFilter extends Filter{

	@Autowired
	private UserSmartService userSmartService;
	
	@Autowired
	private WxUserInfoService userService;
	final static Logger logger = LoggerFactory.getLogger(SyncUserInfoFilter.class);
	@Override
	public Object handlerFilter(SessionManager filterManager) throws Exception {
		UserInfo userInfo = (UserInfo)filterManager.getObject(UserInfo.class);
		try{
			String id = userInfo.getId();
			String token = userInfo.getToken();
			String name = userInfo.getNickname();
			String imgUrl = userInfo.getHeadimgurl();
			
			Long count = userInfo.getCount();
			userInfo = userSmartService.getFromAccessToken(userInfo.getOpenid());
			
			if(CommonUtil.isEmpty(userInfo.getNickname())){
				userInfo.setNickname(name);
			}
			if(CommonUtil.isEmpty(userInfo.getHeadimgurl())){
				userInfo.setHeadimgurl(imgUrl);
			}
			
			userInfo.setCount(count);
			userInfo.setId(id);
			userInfo.setToken(token);
			String nickname = CommonUtil.filterEmoji(userInfo.getNickname());
            
            userInfo.setNickname(nickname);
            try{
            	userService.update(userInfo);
            }catch(Exception e){
            	userInfo.setNickname(name);
            	userService.update(userInfo);
            }
		}catch(Exception e){
			logger.error("同步用户的时候出错了{}",e);
		}
		
		return userInfo;
	}

	

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> filterClasses = new ArrayList<>();
		filterClasses.add(UserInfoFilter.class);
		return filterClasses;
	}



	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
