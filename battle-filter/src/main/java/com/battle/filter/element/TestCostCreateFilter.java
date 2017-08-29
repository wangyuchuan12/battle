package com.battle.filter.element;

import java.math.BigDecimal;
import java.util.List;

import com.wyc.common.domain.vo.PayCostVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;

public class TestCostCreateFilter extends Filter{

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		PayCostVo payCostVo = new PayCostVo();
		payCostVo.setBody("body");
		payCostVo.setCost(new BigDecimal("1"));
		payCostVo.setDetail("detail");
		payCostVo.setNonceStr("1234");
		payCostVo.setNotifyUrl("www.chengxihome.com");
		payCostVo.setOutTradeNo("123456");
		payCostVo.setPayType(0);
		return payCostVo;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		// TODO Auto-generated method stub
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
