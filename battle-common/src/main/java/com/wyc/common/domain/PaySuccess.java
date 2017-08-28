package com.wyc.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.NameAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;
@Entity(name="pay_success")
@ParamEntityAnnotation
public class PaySuccess {
    @Id
    @IdAnnotation
    private String id;
    
    @NameAnnotation(name="order_id")
    @ParamAnnotation
    @Column(name="order_id")
    private String orderId;
    @ParamAnnotation
    @Column
    private String appid;
    @ParamAnnotation
    @Column
    private String attach;
    
    @NameAnnotation(name="bank_type")
    @ParamAnnotation
    @Column(name="bank_type")
    private String bankType;
    
    @NameAnnotation(name="cash_fee")
    @ParamAnnotation
    @Column(name="cash_fee")
    private String cashFee;
    
    @NameAnnotation(name="is_subscribe")
    @ParamAnnotation
    @Column(name="is_subscribe")
    private String isSubscribe;
    
    @NameAnnotation(name="mch_id")
    @ParamAnnotation
    @Column(name="mch_id")
    private String mchId;
    
    @NameAnnotation(name="nonce_str")
    @ParamAnnotation
    @Column(name="nonce_str")
    private String nonceStr;
    @ParamAnnotation
    @Column
    private String openid;
    
    @NameAnnotation(name="out_trade_no")
    @ParamAnnotation
    @Column(name="out_trade_no")
    private String outTradeNo;
    
    @NameAnnotation(name="result_code")
    @ParamAnnotation
    @Column(name="result_code")
    private String resultCode;
    
    @NameAnnotation(name="return_code")
    @ParamAnnotation
    @Column(name="return_code")
    private String returnCode;
    @ParamAnnotation
    @Column
    private String sign;
    
    @NameAnnotation(name="time_end")
    @ParamAnnotation
    @Column(name="time_end")
    private String timeEnd;
    
    @NameAnnotation(name="total_fee")
    @ParamAnnotation
    @Column(name="total_fee")
    private String totalFee;
    
    @NameAnnotation(name="trade_type")
    @ParamAnnotation
    @Column(name="trade_type")
    private String tradeType;
    
    @NameAnnotation(name="transaction_id")
    @ParamAnnotation
    @Column(name="transaction_id")
    private String transactionId;
    
    @NameAnnotation(name="is_refund")
    @Column
    private Integer isRefund;
    
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;
    
    
    
    public Integer getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}
	public DateTime getCreateAt() {
        return createAt;
    }
    public void setCreateAt(DateTime createAt) {
        this.createAt = createAt;
    }
    public DateTime getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(DateTime updateAt) {
        this.updateAt = updateAt;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getAttach() {
        return attach;
    }
    public void setAttach(String attach) {
        this.attach = attach;
    }
    public String getBankType() {
        return bankType;
    }
    public void setBankType(String bankType) {
        this.bankType = bankType;
    }
    public String getCashFee() {
        return cashFee;
    }
    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }
    public String getIsSubscribe() {
        return isSubscribe;
    }
    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }
    public String getMchId() {
        return mchId;
    }
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }
    public String getNonceStr() {
        return nonceStr;
    }
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getOutTradeNo() {
        return outTradeNo;
    }
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getReturnCode() {
        return returnCode;
    }
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
    public String getTotalFee() {
        return totalFee;
    }
    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
    public String getTradeType() {
        return tradeType;
    }
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    
    
}
