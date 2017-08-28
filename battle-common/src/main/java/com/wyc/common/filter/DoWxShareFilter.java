package com.wyc.common.filter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.common.domain.Href;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.HrefService;
import com.wyc.common.service.ShareService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.domain.Share;
import com.wyc.common.wx.domain.UserInfo;

public class DoWxShareFilter extends Filter{

	@Autowired
	private HrefService hrefService;
	
	@Autowired
	private ShareService shareService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {

		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		UserInfo userInfo = (UserInfo)sessionManager.getObject(UserInfo.class);
		String hrefCode = httpServletRequest.getParameter("href_code");
		
		String url = httpServletRequest.getParameter("url");
		
		String type = httpServletRequest.getParameter("type");
		
		if(CommonUtil.isEmpty(hrefCode)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("hrefCode参数为空");
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			return null;
		}
		
		if(CommonUtil.isEmpty(url)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("url参数为空");
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			return null;
		}
		
		if(CommonUtil.isEmpty(type)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("type参数为空");
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			return null;
		}
		
		Integer typeInt = null;
		
		try{
			typeInt = Integer.parseInt(type);
		}catch(Exception e){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("type转int失败");
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			return null;
		}
		
		
		Href href = hrefService.findOneByCode(hrefCode);
		
		if(CommonUtil.isEmpty(href)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("根据hrefCode返回的链接对象为空");
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			return null;
		}
		
		Integer shareCount = href.getShareCount();
		if(CommonUtil.isEmpty(shareCount)){
			shareCount=0;
		}
		href.setShareCount(shareCount+1);
		
		hrefService.update(href);
		
		Share share = new Share();
		share.setHandTime(new DateTime());
		share.setHrefId(href.getId());
		share.setSponsorOpenid(userInfo.getOpenid());
		share.setType(typeInt);
		share.setUrl(url);
		share.setClickCount(0);
		share = shareService.add(share);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setData(share);
		resultVo.setMsg("分享成功");
		resultVo.setSuccess(true);
		return resultVo;
	}

	

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> list = new ArrayList<>();
		list.add(BaseActionFilter.class);
		return list;
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
