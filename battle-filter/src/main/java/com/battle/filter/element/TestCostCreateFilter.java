package com.battle.filter.element;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.wyc.common.domain.vo.PayCostVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;

public class TestCostCreateFilter extends Filter{

	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		Calendar now = Calendar.getInstance();
		String outTradeNo = now.get(Calendar.YEAR)
                +"-"+(now.get(Calendar.MONTH) + 1)
                +"-"+now.get(Calendar.DAY_OF_MONTH)
                +"-"+now.get(Calendar.HOUR_OF_DAY)
                +"-"+now.get(Calendar.MINUTE)
                +"-"+now.get(Calendar.SECOND)
                +"-"+now.get(Calendar.MILLISECOND)
                +"-"+new Random().nextInt(10000)+"";
		PayCostVo payCostVo = new PayCostVo();
		payCostVo.setBody("body");
		payCostVo.setCost(new BigDecimal("0.01"));
		payCostVo.setDetail("detail");
		payCostVo.setNonceStr("jingyingfanwei12");
		payCostVo.setNotifyUrl("www.chengxihome.com");
		payCostVo.setOutTradeNo(outTradeNo);
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
