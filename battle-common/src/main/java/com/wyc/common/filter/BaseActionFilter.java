package com.wyc.common.filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;
import com.wyc.common.wx.domain.WxContext;


public class BaseActionFilter extends Filter{

	@Autowired
	private WxContext wxContext;
	
	final static Logger logger = LoggerFactory.getLogger(BaseActionFilter.class);
	@Override
	public Object handlerFilter(SessionManager filterManager) throws Exception {
		HttpServletRequest httpServletRequest = filterManager.getHttpServletRequest();
		
		String contextPath = httpServletRequest.getScheme()+"://"+
					  httpServletRequest.getServerName()+":"+
					  httpServletRequest.getServerPort()+
					  httpServletRequest.getContextPath();
		
		httpServletRequest.setAttribute("contextPath", contextPath);
		
		String requestUrl = httpServletRequest.getRequestURL().toString();
		String requestUri = httpServletRequest.getRequestURI().toString();
		
		
		UserInfo userInfo = (UserInfo)filterManager.getObject(UserInfo.class);
		
		
		
		
		
		if(userInfo==null){
			if(requestUri.startsWith("/view")){
				StringBuffer urlBuffer = new StringBuffer();
	            urlBuffer.append(requestUrl);
	            java.util.Map<String, String[]> paramMap = httpServletRequest.getParameterMap();
	            StringBuffer sb = new StringBuffer();
	            sb.append("the parameter is ");
	            for(Entry<String, String[]> entry:paramMap.entrySet()){
	                if(entry.getValue()!=null&&entry.getValue().length>0){
	                    sb.append("&");
	                    sb.append(entry.getKey());
	                    sb.append("=");
	                    sb.append(entry.getValue()[0]);
	                }
	            }
	            
	            if(paramMap.entrySet().size()>0){
	                urlBuffer.append("?");
	            }
	            for(Entry<String, String[]> entry:paramMap.entrySet()){
	                if(entry.getValue()!=null&&entry.getValue().length>0){
	                    urlBuffer.append("&"+entry.getKey()+"="+entry.getValue()[0]);
	                }
	            }
	            if(urlBuffer.toString().contains("&")){
	                urlBuffer.deleteCharAt(urlBuffer.indexOf("&"));
	            }
	            
	            String wxRequestUrl = "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?" +
	            "appid="+wxContext.getAppid()+"&redirect_uri="+urlBuffer.toString()+"&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
	            logger.debug("redirect to url [{}]",wxRequestUrl);
	            
	            filterManager.setEnd(true);
	            filterManager.setReturnValue(wxRequestUrl);
	            return null;
			}else if(requestUri.startsWith("/api")){
				filterManager.setEnd(true);
				
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setMsg("找不到用户信息，请用微信客户端登录");
				filterManager.setReturnValue(resultVo);
				return null;
			}
            
        }
		return null;
	}

	

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> filters = new ArrayList<>();
		filters.add(UserInfoFilter.class);
		return filters;
	}



	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		UserInfo userInfo = (UserInfo)sessionManager.getObject(UserInfo.class);
		if(userInfo!=null){
			sessionManager.addNotExecuteFilterClass(BaseActionFilter.class);
		}
		
		return null;
	}



	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
