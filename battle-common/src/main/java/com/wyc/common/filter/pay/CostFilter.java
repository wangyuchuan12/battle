//package com.wyc.common.filter.pay;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Random;
//
//import com.wyc.common.domain.Order;
//import com.wyc.common.domain.vo.PayCostVo;
//import com.wyc.common.filter.Filter;
//import com.wyc.common.session.SessionManager;
//import com.wyc.common.util.Constant;
//
//
//public class CostFilter extends Filter{
//	@Override
//	public Object handlerFilter(SessionManager sessionManager) throws Exception {
//
//		String orderId = sessionManager.getAttribute("orderId").toString();
//		
//		Order order = sessionManager.findOne(Order.class, orderId);
//		
//
//        Calendar now = Calendar.getInstance();
//        
//        String outTradeNo = now.get(Calendar.YEAR)
//                +"-"+(now.get(Calendar.MONTH) + 1)
//                +"-"+now.get(Calendar.DAY_OF_MONTH)
//                +"-"+now.get(Calendar.HOUR_OF_DAY)
//                +"-"+now.get(Calendar.MINUTE)
//                +"-"+now.get(Calendar.SECOND)
//                +"-"+now.get(Calendar.MILLISECOND)
//                +"-"+new Random().nextInt(10000)+"";
//        
//        
//        order.setOutTradeNo(outTradeNo);
//		PayCostVo payCostVo = new PayCostVo();
//		
//		payCostVo.setOutTradeNo(outTradeNo);
//		
//		String noticStr = "2add1a30ac87aa2db72f57a2375d8f25";
//		payCostVo.setNonceStr(noticStr);
//		payCostVo.setNotifyUrl(Constant.PAY_ORDER_NONCE_URL);
//		payCostVo.setCost(order.getAmountNum());
//		payCostVo.setBody(order.getName());
//		payCostVo.setDetail(order.getDetail());
//
//		sessionManager.update(order);
//		return payCostVo;
//	}
//
//	@Override
//	public List<Class<? extends Filter>> dependClasses() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object handlerPre(SessionManager sessionManager) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object handlerAfter(SessionManager sessionManager) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
