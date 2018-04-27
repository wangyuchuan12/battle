package com.wyc.common.api;

import java.math.BigDecimal;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wyc.common.domain.Account;
import com.wyc.common.domain.vo.LoginVo;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.AccountService;
import com.wyc.common.service.WxUserInfoService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
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
	
	@Autowired
	private AccountService accountService;
	
	final static Logger logger = LoggerFactory.getLogger(LoginApi.class);
	
	@Transactional
	@ResponseBody
	@RequestMapping(value="loginByJsCode")
	public Object loginByJsCode(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		String token = null;
		UserInfo userInfo = null;
		token = httpServletRequest.getParameter("token");
		if(!CommonUtil.isEmpty(token)){
			userInfo = wxUserInfoService.findByToken(token);
		}
		
		
			
		if(userInfo==null){
			String code =httpServletRequest.getParameter("code");
			
			OpenIdVo openIdVo = null;
			
			try{
				openIdVo = userService.getOpenIdFromJsCode(code);
			}catch(Exception e){
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("重新登录");
				resultVo.setErrorCode(0);
				
				logger.error("登录时获取openId错误");
				
				return resultVo;
			}
			
			if(openIdVo==null){
				logger.error("登录时获取的openId对象为空");
				ResultVo resultVo = new ResultVo();
				resultVo.setSuccess(false);
				resultVo.setErrorMsg("重新登录");
				resultVo.setErrorCode(1);
				return resultVo;
			}
			
			String openId = openIdVo.getOpenid();
			
			
			
			userInfo = wxUserInfoService.findByOpenidAndSource(openId,1);
		}
		
		if(userInfo!=null){
			LoginVo loginVo = new LoginVo();
			token = UUID.randomUUID().toString();
			loginVo.setToken(userInfo.getToken());
			loginVo.setUserInfo(userInfo);
			
			String accountId = userInfo.getAccountId();
			Account account;
			if(CommonUtil.isEmpty(accountId)){
				account = initAccount();
				userInfo.setAccountId(account.getId());
				
				wxUserInfoService.update(userInfo);
			}else{
				account = accountService.fineOne(accountId);
				if(account==null){
					account = initAccount();
					userInfo.setAccountId(account.getId());
					
					wxUserInfoService.update(userInfo);
				}
			}
			
			sessionManager.save(userInfo);
			
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
	
	
	@Transactional
	@ResponseBody
	@RequestMapping(value="testSave")
	public Object testSave(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = new UserInfo();
		userInfo.setNickname("王煜川");
		sessionManager.save(userInfo);
		return userInfo;
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping(value="testGet")
	public Object testGet(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		return userInfo;
	}
	
	
	@Transactional
	@ResponseBody
	@RequestMapping(value="accountInfo")
	public ResultVo accountInfo(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		if(userInfo==null){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("该用户没有登录，不能获取账户信息");
			return resultVo;
		}else{
			String accountId = userInfo.getAccountId();
			Account account;
			if(CommonUtil.isEmpty(accountId)){
				account = initAccount();
				userInfo.setAccountId(accountId);
				wxUserInfoService.update(userInfo);
			}else{
				account = accountService.fineOneSync(userInfo.getAccountId());
				if(account==null){
					account = initAccount();
					userInfo.setAccountId(accountId);
					wxUserInfoService.update(userInfo);
				}
			}
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(true);
			resultVo.setData(account);
			return resultVo;
		}
	}
	
	private Account initAccount(){
		Account account = new Account();
		account.setAmountBalance(new BigDecimal(0));
		account.setCanTakeOutCount(2);
		account.setEmpiricalValue(new BigDecimal(0));
		account.setIntegral(0);
		account.setLoveLife(5);
		account.setLoveLifeLimit(1000);
		account.setMasonry(0);
		account.setWisdomCount(0L);
		account.setWisdomLimit(1000000L);
		account.setReceiveGiftCount(0);
		account.setGiftCount(0);
		accountService.add(account);
		return account;
	}
	
	
	@Transactional
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
			Account account = initAccount();
			
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
			userInfo.setAccountId(account.getId());
			userInfo.setIsGod(0);
			userInfo.setToken(UUID.randomUUID().toString());
			userInfo.setIsCreateFrendGroup(0);
			userInfo.setIsSyncDan(0);
			wxUserInfoService.add(userInfo);

			
		}else{
//			ResultVo resultVo = new ResultVo();
//			resultVo.setSuccess(false);
//			
//			resultVo.setErrorMsg("用户已存在，无需注册");
//			
//			resultVo.setErrorCode(403);
//			
//			return resultVo;
		}
		
		
		
		
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		
		return resultVo;
	}
}
