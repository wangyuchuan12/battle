package com.battle.filter.element;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.LoginVo;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.UserInfo;

public class LoginStatusFilter extends Filter{

	@Autowired
	private WxUserInfoService userInfoService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		String token = (String)sessionManager.getAttribute(AttrEnum.token);
		
		
		LoginVo loginVo = sessionManager.getObject(LoginVo.class);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		
		if(userInfo!=null){
			return userInfo;
		}
		if(loginVo!=null&&loginVo.getToken().equals(token)){
			sessionManager.save(loginVo.getUserInfo());
		}else if(token!=null){
			if(userInfo==null){
				userInfo = userInfoService.findByToken(token);
		
			}
			
			if(userInfo==null){
				ResultVo resultVo = new ResultVo();
				resultVo.setErrorCode(401);
				resultVo.setErrorMsg("用户未登陆");
				sessionManager.setReturnValue(resultVo);
				sessionManager.setEnd(true);
			}
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setErrorCode(401);
			resultVo.setErrorMsg("用户未登陆");
			sessionManager.setReturnValue(resultVo);
			sessionManager.setEnd(true);
		}
		
		sessionManager.save(userInfo);
		return null;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		String token = httpServletRequest.getParameter("token");
		if(!CommonUtil.isEmpty(token)){
			sessionManager.setAttribute(AttrEnum.token, token);
		}
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
