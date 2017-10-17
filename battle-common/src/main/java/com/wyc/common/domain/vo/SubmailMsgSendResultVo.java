package com.wyc.common.domain.vo;

public class SubmailMsgSendResultVo {
	
	private String status;
	private String send_id;
	private String fee;
	private String sms_credits;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSend_id() {
		return send_id;
	}
	public void setSend_id(String send_id) {
		this.send_id = send_id;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getSms_credits() {
		return sms_credits;
	}
	public void setSms_credits(String sms_credits) {
		this.sms_credits = sms_credits;
	}
}
