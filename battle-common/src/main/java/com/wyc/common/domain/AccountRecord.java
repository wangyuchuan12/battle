package com.wyc.common.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="p_account_record")
public class AccountRecord {
	@Id
    private String id;
	
	
	//引用账户
	@Column(name="account_id")
	private String accountId;
	
	//金额
	@Column
	private BigDecimal amount;
	
	//智慧豆数量
	@Column(name="wisom_count")
	private Long wisdomCount;
	
	@Column(name="empirical_value")
	private BigDecimal empiricalValue;
	
	//入账还是出账0为入账，1为出账
	@Column(name="in_or_out")
	private Integer inOrOut;
	
	//付款或者收款方式 0为人民币
	@Column(name="pay_model")
	private Integer payModel;
	
	//支付编号
	@Column(name="out_trade_no")
	private String outTradeNo;
	
	//0为微信支付平台-本平台 ,1微信支付平台-微信账户 ，2本平台-微信账户
	@Column
	private Integer type;
	
	//用户的openid
	@Column
	private String openid;
	
	//备注
	@Column
	private String remarks;
	
	@Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getInOrOut() {
		return inOrOut;
	}
	public void setInOrOut(Integer inOrOut) {
		this.inOrOut = inOrOut;
	}
	public Integer getPayModel() {
		return payModel;
	}
	public void setPayModel(Integer payModel) {
		this.payModel = payModel;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public Long getWisdomCount() {
		return wisdomCount;
	}
	public void setWisdomCount(Long wisdomCount) {
		this.wisdomCount = wisdomCount;
	}
	public BigDecimal getEmpiricalValue() {
		return empiricalValue;
	}
	public void setEmpiricalValue(BigDecimal empiricalValue) {
		this.empiricalValue = empiricalValue;
	}
	
	
}
