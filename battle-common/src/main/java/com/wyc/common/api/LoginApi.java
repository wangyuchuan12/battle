package com.wyc.common.api;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wyc.common.domain.vo.LoginVo;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.OpenIdVo;
import com.wyc.common.wx.domain.UserInfo;
import com.wyc.common.wx.service.UserService;


@Controller
@RequestMapping(value="/api/common/login")
public class LoginApi{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WxUserInfoService wxUserInfoService;
	@ResponseBody
	@RequestMapping(value="loginByJsCode")
	public Object loginByJsCode(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		String code =httpServletRequest.getParameter("code");
		
		OpenIdVo openIdVo = userService.getOpenIdFromJsCode(code);
		
		String openId = openIdVo.getOpenid();
		
		
		
		UserInfo userInfo = wxUserInfoService.findByOpenidAndSource(openId,1);
		
		sessionManager.save(userInfo);
		
		if(userInfo!=null){
			
			LoginVo loginVo = new LoginVo();
			String token = UUID.randomUUID().toString();
			loginVo.setToken(token);
			loginVo.setUserInfo(userInfo);
			userInfo.setToken(token);
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setData(loginVo);
			
			return resultVo;
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("用户未注册");
			
			resultVo.setErrorCode(401);
			return resultVo;
		}
	
	}
	
	
	@ResponseBody
	@RequestMapping(value="registerUserByJsCode")
	public Object registerUser(HttpServletRequest httpServletRequest)throws Exception{
		String code = httpServletRequest.getParameter("code");
		String nickName  = httpServletRequest.getParameter("nickName");
		String gender = httpServletRequest.getParameter("gender");
		String language = httpServletRequest.getParameter("language");
		String city = httpServletRequest.getParameter("city");
		String province = httpServletRequest.getParameter("province");
		String country = httpServletRequest.getParameter("country");
		String avatarUrl = httpServletRequest.getParameter("avatarUrl");
	
		
		OpenIdVo openIdVo = userService.getOpenIdFromJsCode(code);
		
		String openId = openIdVo.getOpenid();
		
		UserInfo userInfo = wxUserInfoService.findByOpenidAndSource(openId,1);
		
		if(userInfo==null){
			userInfo = new UserInfo();
			userInfo.setCity(city);
			userInfo.setCountry(country);
			userInfo.setHeadimgurl(avatarUrl);
			userInfo.setLanguage(language);
			userInfo.setNickname(nickName);
			userInfo.setOpenid(openId);
			userInfo.setProvince(province);
			userInfo.setSex(gender);
			userInfo.setSource(1);
			wxUserInfoService.add(userInfo);
			
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			
			return resultVo;
		}else{
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			
			resultVo.setErrorMsg("用户已存在，无需注册");
			
			resultVo.setErrorCode(403);
			
			return resultVo;
		}
	}
}
