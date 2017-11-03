package com.wyc.common.filter.pay;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.common.domain.vo.PayCostVo;
import com.wyc.common.domain.vo.ResultVo;
import com.wyc.common.domain.vo.WxChooseWxPayBean;
import com.wyc.common.filter.Filter;
import com.wyc.common.session.SessionManager;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.util.MD5Util;
import com.wyc.common.util.Request;
import com.wyc.common.util.RequestFactory;
import com.wyc.common.util.Response;
import com.wyc.common.wx.domain.UserInfo;
import com.wyc.common.wx.domain.WxContext;

public class ChooseWxPayFilter extends Filter{

	@Autowired
	private RequestFactory requestFactory;
	
	
	@Autowired
	private WxContext wxContext;
	@Override
	public Object handlerFilter(SessionManager sessionManager) throws Exception {
		
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		
		UserInfo userInfo = (UserInfo) sessionManager.getObject(UserInfo.class);
		
		String openid = userInfo.getOpenid();
		
		PayCostVo payCostVo = (PayCostVo)sessionManager.getObject(PayCostVo.class);
	
		
		BigDecimal costBigDecimail = payCostVo.getCost();
		
		if(costBigDecimail.floatValue()==0){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("输入的参数cost数值不能为0");
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			sessionManager.save(resultVo);
			return null;
		}
		
		
		String body =payCostVo.getBody();
		
		String detail = payCostVo.getDetail();
		
		if(CommonUtil.isEmpty(body)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("支付body不能为空");
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			sessionManager.save(resultVo);
			return null;
		}
		
		
		
		if(CommonUtil.isEmpty(detail)){
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("支付detail不能为空");
			sessionManager.setReturn(true);
			sessionManager.setReturnValue(resultVo);
			sessionManager.save(resultVo);
			return null;
		}
		
		
		String outTradeNo = payCostVo.getOutTradeNo();
 
        Request request = requestFactory.payUnifiedorder();
        String appid = wxContext.getAppid();
        String attach = "paytest";
        String mchId = wxContext.getMchId();
        String nonceStr = payCostVo.getNonceStr();
        String notifyUrl = wxContext.getDomainName()+payCostVo.getNotifyUrl();
        String spbillCreateIp = httpServletRequest.getRemoteAddr();
        String datetime = String.valueOf(System.currentTimeMillis() / 1000);
        BigDecimal totalFee = costBigDecimail.multiply(new BigDecimal(100));
        Long totalFeeLong = totalFee.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        String tradeType = "JSAPI";
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("openid", openid);
        map.put("body", body);
        map.put("out_trade_no", outTradeNo);
        map.put("total_fee", totalFeeLong+"");
        map.put("notify_url", notifyUrl);
        map.put("trade_type", tradeType);
        map.put("appid", appid);
        map.put("mch_id", mchId);
        map.put("spbill_create_ip", spbillCreateIp);
        map.put("nonce_str", nonceStr);
        map.put("attach", attach);
        map.put("detail", detail);
        String sign = MD5Util.createMd5Sign(map,wxContext.getKey()).toUpperCase();
        StringBuffer sb2 = new StringBuffer();
        sb2.append("<xml>");
        sb2.append("<appid>"+appid+"</appid>");
        sb2.append("<attach>"+attach+"</attach>");
        sb2.append("<body>"+body+"</body>");
        sb2.append("<mch_id>"+mchId+"</mch_id>");
        sb2.append("<nonce_str>"+nonceStr+"</nonce_str>");
        sb2.append("<notify_url>"+notifyUrl+"</notify_url>");
        sb2.append("<openid>"+openid+"</openid>");
        sb2.append("<out_trade_no>"+outTradeNo+"</out_trade_no>");
        sb2.append("<spbill_create_ip>"+spbillCreateIp+"</spbill_create_ip>");
        sb2.append("<total_fee>"+totalFeeLong+"</total_fee>");
        sb2.append("<detail>"+detail+"</detail>");
        sb2.append("<trade_type>"+tradeType+"</trade_type>");
        sb2.append("<sign>"+sign+"</sign>");
        sb2.append("</xml>");
        Response response = request.post(sb2.toString());
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(new StringReader(response.read()));
        Element rootElement = document.getRootElement();
        
        String returnCode = rootElement.getChildText("return_code");
        
        if(returnCode.equals("SUCCESS")){
	        String prepayId = rootElement.getChildText("prepay_id");
	        datetime = String.valueOf(System.currentTimeMillis() / 1000);
	        SortedMap<String, String> map2  = new TreeMap<String, String>();
	        map2.put("appId", wxContext.getAppid());
	        map2.put("nonceStr", nonceStr);
	        map2.put("package", "prepay_id="+prepayId);
	        map2.put("signType", "MD5");
	        map2.put("timeStamp", datetime);
	        String paySign = MD5Util.createMd5Sign(map2,wxContext.getKey()).toUpperCase();
	        
	        WxChooseWxPayBean wxChooseWxPayBean = new WxChooseWxPayBean();
	        
	        
	        wxChooseWxPayBean.setPrepayId(prepayId);
	        wxChooseWxPayBean.setPack("prepay_id="+prepayId);
	        wxChooseWxPayBean.setNonceStr(nonceStr);
	        wxChooseWxPayBean.setPaySign(paySign);
	        wxChooseWxPayBean.setSignType("MD5");
	        wxChooseWxPayBean.setTimestamp(datetime);
	        wxChooseWxPayBean.setAppId(wxContext.getAppid());
	        
	        wxChooseWxPayBean.setOutTradeNo(outTradeNo);
	        wxChooseWxPayBean.setCost(costBigDecimail);
	       
			return wxChooseWxPayBean;
        }else{
        	ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setErrorMsg("支付失败了");
			sessionManager.setEnd(true);
			sessionManager.setReturnValue(resultVo);
			sessionManager.save(resultVo);
			return null;
        }
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		return null;
	}

	@Override
	public Object handlerPre(SessionManager sessionManager) throws Exception {
		HttpServletRequest httpServletRequest = sessionManager.getHttpServletRequest();
		String orderId = httpServletRequest.getParameter("orderId");
		
		return null;
	}

	@Override
	public Object handlerAfter(SessionManager sessionManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
