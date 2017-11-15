package com.battle.api;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.battle.filter.api.WxPayApiFilter;
import com.battle.filter.element.LoginStatusFilter;
import com.battle.service.other.GoodPayConfigService;
import com.battle.service.other.PayService;
import com.wyc.annotation.HandlerAnnotation;
import com.wyc.common.domain.Good;
import com.wyc.common.domain.Order;
import com.wyc.common.domain.PaySuccess;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.domain.vo.TransfersResultVo;
import com.wyc.common.service.GoodService;
import com.wyc.common.service.OrderService;
import com.wyc.common.service.PaySuccessService;
import com.wyc.common.session.SessionManager;
import com.wyc.common.wx.domain.UserInfo;


@Controller
@RequestMapping(value="/api/battle/")
public class WxPayApi{

	@Autowired
	private GoodService goodService;
	
	@Autowired
	private GoodPayConfigService goodPayConfigService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaySuccessService paySuccessService;
	
	@Autowired
	private PayService payService;
	@RequestMapping(value="wxPayConfig")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=WxPayApiFilter.class)
	public Object wxPayConfig(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		ResultVo resultVo = (ResultVo)sessionManager.getObject(ResultVo.class);
		
		return resultVo;
		
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping(value="paySuccess")
	public Object paySuccess(HttpServletRequest httpServletRequest)throws Exception{
		SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(httpServletRequest.getInputStream());
        Element rootElement = document.getRootElement();
        
        Class<PaySuccess> bean = PaySuccess.class;
        PaySuccess paySuccess = bean.newInstance();
        for(Field field:bean.getDeclaredFields()){
           Column column = field.getAnnotation(Column.class);
           if(column!=null){
               String name = column.name();
               if(name.equals("")){
                   name = field.getName();
               }
               String value = rootElement.getChildText(name);
               field.setAccessible(true);
               field.set(paySuccess, value);
           }
        }
        
        
        PaySuccess paySuccess2 = paySuccessService.findOneByOutTradeNo(paySuccess.getOutTradeNo());
        
        if(paySuccess2==null){
        	paySuccessService.add(paySuccess);
        	
        	Order order = orderService.findOneByOutTradeNo(paySuccess.getOutTradeNo());
        	
        	if(order.getIsPay()==0&&paySuccess.getResultCode().equalsIgnoreCase("SUCCESS")&&paySuccess.getNonceStr().equals("jingyingfanwei12")){
        		order.setIsPay(1);
             
        		goodPayConfigService.settlementOrder(order);
        		
        	}
        	
        } 
        
       
 
        return null;
	}
	
	
	@RequestMapping(value="masonryPay")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object masonryPay(HttpServletRequest httpServletRequest)throws Exception{
		
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		String goodId = httpServletRequest.getParameter("goodId");
		Good good = goodService.findOne(goodId);
		
		if(!good.getCostType().equals(Good.MASONRY_COST_TYPE)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("该商品不是砖石支付的");
			return resultVo;
		}
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		
		Order order = goodPayConfigService.createGoodOrder(good);
		
		order.setCostMasonry(good.getCostMasonry());
		order.setIsPay(0);
		order.setIsToAccount(0);
		order.setAccountId(userInfo.getAccountId());
		
		orderService.add(order);
		
		ResultVo resultVo = goodPayConfigService.settlementOrder(order);
		
		return resultVo;
	}
	
	@RequestMapping(value="beanPay")
	@ResponseBody
	@Transactional
	@HandlerAnnotation(hanlerFilter=LoginStatusFilter.class)
	public Object beanPay(HttpServletRequest httpServletRequest)throws Exception{
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		String goodId = httpServletRequest.getParameter("goodId");
		Good good = goodService.findOne(goodId);
		
		if(!good.getCostType().equals(Good.BEAN_COST_TYPE)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("该商品不是智慧豆支付的");
			return resultVo;
		}
		
		UserInfo userInfo = sessionManager.getObject(UserInfo.class);
		
		
		Order order = goodPayConfigService.createGoodOrder(good);
		order.setCostType(good.getCostType());
		order.setCostBean(good.getCostBean());
		order.setAccountId(userInfo.getAccountId());
		
		orderService.add(order);
		
		ResultVo resultVo = goodPayConfigService.settlementOrder(order);
		
		return resultVo;
	}
	
	@RequestMapping(value="transfers")
	@ResponseBody
	@Transactional
	public Object transfers(HttpServletRequest httpServletRequest)throws Exception{
		String openid = "o6hwf0S9JT_Ff0LVBORFsBrhAtpM";
		BigDecimal amount = new BigDecimal(1);
		TransfersResultVo transfersResultVo = payService.transfers(openid, amount, "10.2.3.10", "测试付款");
		
		return transfersResultVo;
	}
}
