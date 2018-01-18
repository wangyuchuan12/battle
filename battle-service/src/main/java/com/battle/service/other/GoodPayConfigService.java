package com.battle.service.other;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.Account;
import com.wyc.common.domain.Good;
import com.wyc.common.domain.Order;
import com.wyc.common.domain.vo.PayCostVo;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.service.AccountService;
import com.wyc.common.service.GoodService;
import com.wyc.common.service.OrderService;

@Service
public class GoodPayConfigService {

	@Autowired
	private GoodService goodService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AccountService accountService;
	
	
	public Order createGoodOrder(Good good){
		Order order = new Order();
		order.setCostType(good.getCostType());
		order.setOrderType(Order.GOOD_ORDER_TYPE);
		
		if(good.getType()==Good.BEAN_TYPE){
			order.setBeanNum(good.getBeanNum());
		}else if(good.getType()==Good.LOVE_TYPE){
			order.setLoveNum(good.getLoveNum());
		}else if(good.getType()==Good.MASONRY_TYPE){
			order.setMasonryNum(good.getMasonryNum());
		}
		
		order.setDetail(good.getDetail());
		order.setImgUrl(good.getImgUrl());
		order.setName(good.getName());
		order.setSpec(good.getSpec());
		order.setIsToAccount(0);
		order.setIsPay(0);
		order.setGoodId(good.getId());
		
		
		return order;
	}
	
	//结算订单
	public ResultVo settlementOrder(Order order){
		
		Account account = accountService.fineOneSync(order.getAccountId());
		
		
		Long beanNum = order.getBeanNum();
		Long loveNum = order.getLoveNum();
		
		Long masonryNum = order.getMasonryNum();
		BigDecimal amountNum = order.getAmountNum();
		Integer costBean = order.getCostBean();
		Integer costMasonry = order.getCostMasonry();
		BigDecimal costMoney = order.getCostMoney();
		
		if(order.getIsToAccount()==1){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("已经到账，无需再结算");
			return resultVo;
		}
		
		
		
		if(order.getIsPay()==0){
			Long accountBean = account.getWisdomCount();
			Integer accountMasonry = account.getMasonry();
			BigDecimal amountBalance = account.getAmountBalance();
			
			if(order.getCostType() == Order.BEAN_COST_TYPE){
				if(costBean==null||costBean==0){
					ResultVo resultVo = new ResultVo();
					resultVo.setSuccess(false);
					resultVo.setErrorMsg("支付豆子不能为空");
					return resultVo;
				}
				
				if(accountBean<costBean){
					ResultVo resultVo = new ResultVo();
					resultVo.setSuccess(false);
					resultVo.setErrorMsg("账户豆子余额小于支付豆子");
					return resultVo;
				}
				
				accountBean = accountBean-costBean;
				account.setWisdomCount(accountBean);
				
			}else if(order.getCostType()==Order.MASONRY_COST_TYPE){
				if(costMasonry==null||costMasonry==0){
					ResultVo resultVo = new ResultVo();
					resultVo.setSuccess(false);
					resultVo.setErrorMsg("支付砖石不能为空");
					return resultVo;
				}
				if(accountMasonry<costMasonry){
					ResultVo resultVo = new ResultVo();
					resultVo.setSuccess(false);
					resultVo.setErrorMsg("账户砖石余额小于支付的砖石");
					return resultVo;
				}
				accountMasonry = accountMasonry - costMasonry;
				account.setMasonry(accountMasonry);
			}else if(order.getCostType()==Order.AMOUNT_COST_TYPE){
				if(order.getIsPayFromBalance()!=1){
					ResultVo resultVo = new ResultVo();
					resultVo.setSuccess(false);
					resultVo.setErrorMsg("如果支付不是余额中扣款，必须是已经支付");
					return resultVo;
				}
				
				if(costMoney==null||costMoney.intValue()<=0){
					ResultVo resultVo = new ResultVo();
					resultVo.setSuccess(false);
					resultVo.setErrorMsg("支付金额不能为空");
					return resultVo;
				}
				
				if(amountBalance.compareTo(costMoney)<0){
					ResultVo resultVo = new ResultVo();
					resultVo.setSuccess(false);
					resultVo.setErrorMsg("账户余额不足");
					return resultVo;
				}
				
				amountBalance = amountBalance.subtract(costMoney);
				account.setAmountBalance(amountBalance);
			}
		}
		
		Long accountBean = account.getWisdomCount();
		Integer accountLove = account.getLoveLife();
		Integer accountMasonry = account.getMasonry();
		BigDecimal amountBalance = account.getAmountBalance();
		
		
		Long wisdomLimit = account.getWisdomLimit();
		Integer loveLifeLimit = account.getLoveLifeLimit();
		
		
		if(beanNum!=null&&beanNum>0){
			if(accountBean==null){
				accountBean = 0L;
			}
			accountBean = beanNum+accountBean;
			if(accountBean>wisdomLimit){
				accountBean = wisdomLimit;
			}
			account.setWisdomCount(accountBean);
		}
		
		if(loveNum!=null&&loveNum>0){
			if(accountLove==null){
				accountLove = 0;
			}
			accountLove = accountLove+loveNum.intValue();
			if(accountLove>loveLifeLimit){
				accountLove = loveLifeLimit;
			}
			account.setLoveLife(accountLove);
		}
		
		if(masonryNum!=null&&masonryNum>0){
			if(accountMasonry==null){
				accountMasonry = 0;
			}
			accountMasonry = accountMasonry+masonryNum.intValue();
			
			account.setMasonry(accountMasonry);
		}
		
		if(amountNum!=null&&amountNum.intValue()>0){
			if(amountBalance==null){
				amountBalance = new BigDecimal(0);
			}
			amountBalance = amountBalance.add(amountNum);
			
			account.setAmountBalance(amountBalance);
		}
		
		order.setIsPay(1);
		order.setIsToAccount(1);
		
		orderService.update(order);
		
		accountService.update(account);
		
		ResultVo resultVo = new ResultVo();
		resultVo.setSuccess(true);
		return resultVo;
	}
	
	public PayCostVo createGoodPayCostVo(String goodId){
		Good good = goodService.findOne(goodId);
		
		Calendar now = Calendar.getInstance();
		String outTradeNo = now.get(Calendar.YEAR)
                +"0"+(now.get(Calendar.MONTH) + 1)
                +"0"+now.get(Calendar.DAY_OF_MONTH)
                +"0"+now.get(Calendar.HOUR_OF_DAY)
                +"0"+now.get(Calendar.MINUTE)
                +"0"+now.get(Calendar.SECOND)
                +"0"+now.get(Calendar.MILLISECOND)
                +"0"+new Random().nextInt(10000)+"";
		PayCostVo payCostVo = new PayCostVo();
		payCostVo.setBody(good.getName());
		payCostVo.setCost(good.getCostMoney());
		payCostVo.setDetail("detail");
		payCostVo.setNonceStr("jingyingfanwei12");
		payCostVo.setNotifyUrl("/api/battle/paySuccess");
		payCostVo.setOutTradeNo(outTradeNo);
		payCostVo.setPayType(0);
		return payCostVo;
	}
	
}
