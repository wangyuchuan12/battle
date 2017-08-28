package com.wyc.common.domain.vo;

import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.NameAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@ParamEntityAnnotation
public class TransfersResultVo {
	
	@IdAnnotation
	private String id;
	
	@NameAnnotation(name="return_code")
	@ParamAnnotation
	private String returnCode;
	
	@NameAnnotation(name="return_msg")
	@ParamAnnotation
	private String returnMsg;
	
	@NameAnnotation(name="return_code")
	@ParamAnnotation
	private String resultCode;
	
	@NameAnnotation(name="error_code")
	@ParamAnnotation
	private String errCode;
	
	@NameAnnotation(name="error_code_desc")
	@ParamAnnotation
	private String errCodeDes;

	@NameAnnotation(name="out_trade_no")
	@ParamAnnotation
	private String outTradeNo;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	
	
	
}
