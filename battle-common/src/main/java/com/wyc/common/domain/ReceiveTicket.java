package com.wyc.common.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;


//领取票
@Entity(name="receive_ticket")
public class ReceiveTicket {
	 @Id
	 private String id;

	 //领取类型 0智慧豆 1爱心 2提现机会 3现金
	 @Column
	 private int type;
	 
	 //数量 如果是现金已元为单位，允许小数点，智慧豆，爱心，提现机会不能有小数点
	 @Column
	 private BigDecimal amount;
	 
	 //开始生效时间
	 @Column(name="start_time")
	 private DateTime startTime;
	 
	 //有效时间
	 @Column(name="time_long")
	 private Long timeLong;
	 
	 //0未激活 1已生效 2已使用 3已过期
	 @Column
	 private int status;
	 
	 //账户id
	 @Column(name="account_id")
	 private String accountId;
	 
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


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public DateTime getStartTime() {
		return startTime;
	}


	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}


	public Long getTimeLong() {
		return timeLong;
	}


	public void setTimeLong(Long timeLong) {
		this.timeLong = timeLong;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	 
	 
	 
}
