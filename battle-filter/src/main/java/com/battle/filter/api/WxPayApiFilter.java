package com.battle.filter.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.battle.filter.element.GoodCostCreateFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.wyc.AttrEnum;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.domain.vo.WxChooseWxPayBean;
import com.wyc.common.filter.Filter;
import com.wyc.common.filter.pay.ChooseWxPayFilter;
import com.wyc.common.session.SessionManager;

public class WxPayApiFilter extends Filter{

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		WxChooseWxPayBean wxChooseWxPayBean = sessionManager.getObject(WxChooseWxPayBean.class);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		resultVo.setData(wxChooseWxPayBean);
		
		return resultVo;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		String goodId = httpServletRequest.getParameter("goodId");
		sessionManager.setAttribute(AttrEnum.goodId, goodId);
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> classes = new ArrayList<>();
		classes.add(LoginStatusFilter.class);
		classes.add(GoodCostCreateFilter.class);
		classes.add(ChooseWxPayFilter.class);
		return classes;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
