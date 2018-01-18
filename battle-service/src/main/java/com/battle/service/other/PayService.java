package com.battle.service.other;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import javax.management.RuntimeErrorException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.ApplyForm;
import com.wyc.common.domain.PaySuccess;
import com.wyc.common.domain.vo.TransfersResultVo;
import com.wyc.common.service.ApplyFormService;
import com.wyc.common.service.PaySuccessService;
import com.wyc.common.util.Constant;
import com.wyc.common.util.MD5Util;
import com.wyc.common.util.Request;
import com.wyc.common.util.RequestFactory;
import com.wyc.common.util.Response;
import com.wyc.common.util.XmlUtil;
import com.wyc.common.wx.domain.Article;
import com.wyc.common.wx.domain.WxContext;
import com.wyc.common.wx.service.SendMessageService;

@Service
public class PayService {
	
	@Autowired
	private RequestFactory requestFactory;
	
	@Autowired
	private PaySuccessService paySuccessService;
	
	@Autowired
	private WxContext wxContext;
	
	@Autowired
	private ApplyFormService applyFormService;
	
	@Autowired
	private SendMessageService sendMessageService;
	//因为之前已经有一次提现失败，再一次提现
	public TransfersResultVo reTakeout(ApplyForm applyForm,String remoteAddress)throws Exception{
		if(applyForm.getType()==Constant.APPLY_FORM_TYPE_TAKE_OUT){
			
			//如果退款三次以上就让他返回,需要手动处理，避免重复给同一个人汇款
			if(applyForm.getErrorCount()>3){
				applyForm.setHandleTime(new DateTime());
				applyForm.setStatus(Constant.APPLY_FORM_STATUS_MANUAL);
				applyFormService.update(applyForm);
				
				return null;
			}
			TransfersResultVo resultVo = transfers(applyForm.getOpenid(), applyForm.getRealHandleAmount(), remoteAddress, "提现");
			if(resultVo!=null&resultVo.getResultCode().equals("SUCCESS")){
				applyForm.setStatus(Constant.APPLY_FORM_STATUS_SUCCESS);
				applyForm.setHandleTime(new DateTime());
				applyForm.setTradeOutNo(applyForm.getTradeOutNo()+","+resultVo.getOutTradeNo());
				applyFormService.update(applyForm);
				
				List<Article> articles = new ArrayList<>();
				
				Article article = new Article();
				article.setDescription("你申请的金额"+applyForm.getRealHandleAmount()+"元已经入账，点击查看详情");
				article.setTitle("红包提现入账通知");
				article.setUrl(wxContext.getDomainName()+"/view/draw/personal_center/main");
				articles.add(article);
				
				sendMessageService.sendImgMessage(applyForm.getOpenid(), articles);
				return resultVo;
			}else{
				applyForm.setErrorCount(applyForm.getErrorCount()+1);
				applyForm.setStatus(Constant.APPLY_FORM_STATUS_FAILURE);
				applyForm.setHandleTime(new DateTime());
				applyForm.setErrCode(resultVo.getErrCode());
				applyFormService.update(applyForm);
				return resultVo;
			}
		}else{
			throw new Exception("不是申请提现的表单，无法进行此操作");
		}
	}
	
	public TransfersResultVo transfers(String openid,BigDecimal amount,String remoteAddress,String desc)throws Exception{
		String appid = wxContext.getAppid();
		String mchid = wxContext.getMchId();
		String deviceInfo = "wyc";
		String nonceStr = "1add1a30ac87aa2db72f57a2375d8f22";
		Calendar now = Calendar.getInstance();
        String outTradeNo = now.get(Calendar.YEAR)
                +"0"+(now.get(Calendar.MONTH) + 1)
                +"0"+now.get(Calendar.DAY_OF_MONTH)
                +"0"+now.get(Calendar.HOUR_OF_DAY)
                +"0"+now.get(Calendar.MINUTE)
                +"0"+now.get(Calendar.SECOND)
                +"0"+now.get(Calendar.MILLISECOND)
                +"0"+new Random().nextInt(1000)+"";
       
        String checkName = "NO_CHECK";
        String spbillCreateIp = remoteAddress;
        
        BigDecimal totalFree = amount.multiply(new BigDecimal(100));
        
        Long totalFeeLong = totalFree.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("mch_appid", appid);
        map.put("mchid", mchid);
     //   map.put("device_info", deviceInfo);
        map.put("nonce_str", nonceStr);
        map.put("partner_trade_no", outTradeNo);
        map.put("openid",openid);
        map.put("check_name", checkName);
        map.put("amount", totalFeeLong+"");
        map.put("desc", desc);
        map.put("spbill_create_ip", spbillCreateIp);
        
        String sign = MD5Util.createMd5Sign(map,wxContext.getKey()).toUpperCase();
        
        
        StringBuffer sb2 = new StringBuffer();
        sb2.append("<xml>");
        sb2.append("<mch_appid>"+appid+"</mch_appid>");
        sb2.append("<mchid>"+mchid+"</mchid>");
     //   sb2.append("<device_info>"+deviceInfo+"</device_info>");
        sb2.append("<nonce_str>"+nonceStr+"</nonce_str>");
        sb2.append("<partner_trade_no>"+outTradeNo+"</partner_trade_no>");
        sb2.append("<openid>"+openid+"</openid>");
        sb2.append("<check_name>"+checkName+"</check_name>");
        sb2.append("<amount>"+totalFeeLong+"</amount>");
        sb2.append("<desc>"+desc+"</desc>");
        sb2.append("<spbill_create_ip>"+spbillCreateIp+"</spbill_create_ip>");
        sb2.append("<sign>"+sign+"</sign>");
        sb2.append("</xml>");
        
        Request request = requestFactory.transfers();
        
        Response response = request.post(sb2.toString());
        SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(response.getInputStream());
		TransfersResultVo resultVo = XmlUtil.xmlToObject(document,TransfersResultVo.class);
		
		return resultVo;
	}
	
	
	
	public PaySuccess refund(String outTradeNo)throws Exception{
        Calendar now = Calendar.getInstance();
        String appid = wxContext.getAppid();
        String mchId = wxContext.getMchId();
        String nonceStr = "1add1a30ac87aa2db72f57a2375d8fec";
        PaySuccess paySuccess = paySuccessService.findOneByOutTradeNo(outTradeNo);
        if(paySuccess!=null&&Long.parseLong(paySuccess.getTotalFee())!=0){
            String refundFee = paySuccess.getTotalFee();
            String totalFee = paySuccess.getTotalFee();
            String outRefundNo = now.get(Calendar.YEAR)
                    +"-"+(now.get(Calendar.MONTH) + 1)
                    +"-"+now.get(Calendar.DAY_OF_MONTH)
                    +"-"+now.get(Calendar.HOUR_OF_DAY)
                    +"-"+now.get(Calendar.MINUTE)
                    +"-"+now.get(Calendar.SECOND)
                    +"-"+now.get(Calendar.MILLISECOND)
                    +"-"+new Random().nextInt(1000)+"";
            TreeMap<String, String> map = new TreeMap<String, String>();
            map.put("appid", appid);
            map.put("mch_id", mchId);
            map.put("nonce_str", nonceStr);
            map.put("op_user_id", mchId);
            map.put("out_refund_no", outRefundNo);
            map.put("out_trade_no", outTradeNo);
            map.put("refund_fee", refundFee);
            map.put("total_fee", totalFee);
          //  map.put("transaction_id","");
            String sign = MD5Util.createMd5Sign(map,wxContext.getKey()).toUpperCase();
            
            
            StringBuffer sb2 = new StringBuffer();
            sb2.append("<xml>");
            sb2.append("<appid>"+appid+"</appid>");
            sb2.append("<mch_id>"+mchId+"</mch_id>");
            sb2.append("<nonce_str>"+nonceStr+"</nonce_str>");
            sb2.append("<op_user_id>"+mchId+"</op_user_id>");
            sb2.append("<out_refund_no>"+outRefundNo+"</out_refund_no>");
            sb2.append("<out_trade_no>"+outTradeNo+"</out_trade_no>");
            sb2.append("<refund_fee>"+refundFee+"</refund_fee>");
            sb2.append("<total_fee>"+totalFee+"</total_fee>");
            
            sb2.append("<transaction_id></transaction_id>");
            sb2.append("<sign>"+sign+"</sign>");
            sb2.append("</xml>");
            
            Request request = requestFactory.getRefundRequest();
            Response response = request.post(sb2.toString());
            String content = response.read();
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(new StringReader(content));
            Element rootElement = document.getRootElement();
            String resultCode = rootElement.getChildText("result_code");
            if(resultCode.equals("SUCCESS")){
                paySuccess.setIsRefund(1);
                paySuccess = paySuccessService.save(paySuccess);
            }else{
                throw new RuntimeException("退款失败");
            }
        }
        return paySuccess;
    }
}
