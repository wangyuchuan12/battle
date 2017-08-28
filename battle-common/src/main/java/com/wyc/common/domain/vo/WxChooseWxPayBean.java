package com.wyc.common.domain.vo;
import java.math.BigDecimal;

import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@ParamEntityAnnotation
public class WxChooseWxPayBean {
	@IdAnnotation
	private String id;
	@ParamAnnotation
	private String signature;
	
	@ParamAnnotation
    private String nonceStr;
	
	@ParamAnnotation
    private String appId;
	

	@ParamAnnotation
	private String pack;
	
	@ParamAnnotation
	private String timestamp;
	
	
	@ParamAnnotation
	private String signType;
	
	
	@ParamAnnotation
	private String outTradeNo;
	
	
	@ParamAnnotation
	private String paySign;

	@ParamAnnotation
	private String prepayId;
	
	@ParamAnnotation
	private BigDecimal cost;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	
	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	

	

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	
}
