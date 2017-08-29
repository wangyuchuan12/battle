package com.wyc.common.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public Object loginByJsCode(HttpServletRequest httpServletRequest){
		
		System.out.println(".............sessionId1:"+httpServletRequest.getSession().getId());
	
		String code = httpServletRequest.getParameter("code");
		
		String nickName  = httpServletRequest.getParameter("nickName");
		String gender = httpServletRequest.getParameter("gender");
		String language = httpServletRequest.getParameter("language");
		String city = httpServletRequest.getParameter("city");
		String province = httpServletRequest.getParameter("province");
		String country = httpServletRequest.getParameter("country");
		String avatarUrl = httpServletRequest.getParameter("avatarUrl");
		
		try{
			OpenIdVo openIdVo = userService.getOpenIdFromJsCode(code);
			
			UserInfo userInfo = wxUserInfoService.findByOpenidAndSource(openIdVo.getOpenid(),1);
			
			if(userInfo==null){
				userInfo = new UserInfo();
				userInfo.setCity(city);
				userInfo.setCountry(country);
				userInfo.setHeadimgurl(avatarUrl);
				userInfo.setLanguage(language);
				userInfo.setNickname(nickName);
				userInfo.setOpenid(openIdVo.getOpenid());
				userInfo.setProvince(province);
				userInfo.setSex(gender);
				userInfo.setSource(1);
				wxUserInfoService.add(userInfo);
			}else{
				userInfo.setCity(city);
				userInfo.setCountry(country);
				userInfo.setHeadimgurl(avatarUrl);
				userInfo.setLanguage(language);
				userInfo.setNickname(nickName);
				userInfo.setOpenid(openIdVo.getOpenid());
				userInfo.setProvince(province);
				userInfo.setSex(gender);
				userInfo.setSource(1);
				wxUserInfoService.update(userInfo);
			}
			
			SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
			sessionManager.save(userInfo);
			
			
			System.out.println(".............:"+sessionManager.getObject(UserInfo.class));
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			return resultVo;
		}catch(Exception e){
			e.printStackTrace();
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);;
			return resultVo;
		}
	}
}
