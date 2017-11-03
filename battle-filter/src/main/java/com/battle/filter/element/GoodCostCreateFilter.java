package com.battle.filter.element;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.battle.service.other.GoodPayConfigService;
import com.wyc.AttrEnum;
import com.wyc.common.domain.Good;
import com.wyc.common.domain.Order;
import com.wyc.common.domain.vo.PayCostVo;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.filter.Filter;
import com.wyc.common.service.GoodService;
import com.wyc.common.service.OrderService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;

public class GoodCostCreateFilter extends Filter{

	@Autowired
	private GoodPayConfigService goodPayConfigService;
	
	@Autowired
	private GoodService goodService;
	
	@Autowired
	private OrderService orderService;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		String goodId = (String)sessionManager.getAttribute(AttrEnum.goodId);
		
		Good good = goodService.findOne(goodId);
		
		if(!good.getCostType().equals(Good.AMOUNT_COST_TYPE)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("该商品不是金额支付商品");
			
			return resultVo;
		}
		
		PayCostVo payCostVo = goodPayConfigService.createGoodPayCostVo(goodId);
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		
		
		
		Order order = goodPayConfigService.createGoodOrder(good);
		order.setCostMoney(good.getCostMoney());
		order.setCostType(good.getCostType());
		order.setOrderType(Order.GOOD_ORDER_TYPE);
		order.setOutTradeNo(payCostVo.getOutTradeNo());
		order.setAccountId(userInfo.getAccountId());
		order.setIsPayFromBalance(0);
		orderService.add(order);
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
